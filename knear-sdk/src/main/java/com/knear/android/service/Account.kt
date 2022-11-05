package com.knear.android.service

import com.knear.android.provider.JsonRpcProvider
import com.knear.android.provider.model.NearRequestParams
import com.knear.android.provider.response.functioncall.FunctionCallResponse
import com.knear.android.provider.response.functioncall.FunctionCallResponse.Companion.toFunctionCallResponse
import com.knear.android.scheme.*
import com.knear.android.provider.response.functioncall.transaction.FunctionCallTransactionResponse
import com.knear.android.provider.response.functioncall.transaction.FunctionCallTransactionResponse.Companion.toFunctionCallTransactionResponse
import com.knear.android.provider.response.sendmoney.SendMoney
import com.knear.android.provider.response.sendmoney.SendMoney.Companion.toSendMoney
import com.knear.android.scheme.action.Action
import com.knear.android.scheme.action.FunctionCallAction
import com.knear.android.scheme.action.TransferAction
import com.knear.android.service.MethodUtils.Companion.convertAmountForSendingNear
import com.syntifi.crypto.key.encdec.Base58
import com.syntifi.crypto.key.hash.Sha256
import com.syntifi.near.borshj.Borsh
import okhttp3.Response
import java.util.*

class Account(
    private var accountId: String,
    private var networkId: String,
    private var rcpEndpoint: String,
    private var keyPair: KeyPairEd25519
) {
    private val jsonRpc: JsonRpcProvider = JsonRpcProvider(rcpEndpoint)


    fun sendMoney(receiverId: String, amount: String): SendMoney {
        val actions = listOf(TransferAction(amount.convertAmountForSendingNear()))
        val response = this.signAndSendTransaction(receiverId, actions, "broadcast_tx_commit")
        return response?.toSendMoney() ?: SendMoney()
    }

    private fun signAndSendTransaction(
        receiverId: String,
        actions: Collection<Action>,
        method: String
    ): Response? {
        var signedTransaction = signTransaction(receiverId, actions)

        val borshTx = Borsh.serialize(signedTransaction)
        val params = listOf(Base64.getEncoder().encodeToString(borshTx))

        val signatureTx = Borsh.serialize(signedTransaction.signature)
        val transactionTx = Borsh.serialize(signedTransaction.transaction)
        val transactionActions = Borsh.serialize(signedTransaction.transaction.actions.elementAt(0))

        return jsonRpc.sendTransaction(method, params)
    }

    private fun signTransaction(
        receiverId: String,
        actions: Collection<Action>
    ): SignedTransaction {
        val account = jsonRpc.viewAccessKey(accountId, keyPair.publicKey.toString())
        val block = jsonRpc.block()

        val nonce = account.result.nonce
        val headerHash = Base58.decode(block.result.header.hash)

        val nextNonce = nonce + 1L
        val pubKey = this.keyPair.publicKey

        val transaction = Transaction(
            accountId,
            pubKey,
            nextNonce,
            receiverId,
            headerHash,
            actions,
        );

        val serializedTx: ByteArray = Borsh.serialize(transaction)
        val hashedTx: ByteArray = Sha256.digest(serializedTx)
        val signedTx: ByteArray = this.keyPair.sign(hashedTx)

        val serializedbase64 = Base64.getEncoder().encodeToString(serializedTx)
        val valid = this.keyPair.verify(hashedTx, signedTx)

        return SignedTransaction(
            transaction,
            Signature(KeyType.ED25519, signedTx)
        )
    }

    fun functionCallTransaction(
        contractId: String,
        methodName: String,
        args: String,
        gas: Long,
        attachedDeposit: String
    ): FunctionCallTransactionResponse {
        val actions = listOf(
            FunctionCallAction(
                methodName,
                args,
                gas,
                attachedDeposit.convertAmountForSendingNear()
            )
        )
        val response = this.signAndSendTransaction(contractId, actions, "broadcast_tx_commit")
        return response?.toFunctionCallTransactionResponse() ?: FunctionCallTransactionResponse();
    }

    fun functionCall(
        accountId: String,
        contractId: String,
        methodName: String,
        args: String,
        gas: Long,
        attachedDeposit: String
    ): FunctionCallResponse {
        val encodedArgs = Base64.getEncoder().encodeToString(args.toByteArray())
        val block = jsonRpc.block()

        var requestParams = NearRequestParams(
            requestType = "call_function",
            blockId = block.result.header.hash,
            accountId = contractId,
            methodName = methodName,
            argsBase64 = encodedArgs
        )
        val response = this.jsonRpc.sendJsonRpc("query", requestParams);
        return response?.toFunctionCallResponse() ?: FunctionCallResponse();
    }
}

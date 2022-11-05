package com.knear.android

import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.knear.android.provider.JsonRpcProvider
import com.knear.android.provider.response.block.Block
import com.knear.android.provider.response.blockchanges.BlockChangesResult
import com.knear.android.provider.response.chunkdetails.ChunkDetailsResult
import com.knear.android.provider.response.functioncall.FunctionCallResponse
import com.knear.android.provider.response.functioncall.transaction.FunctionCallTransactionResponse
import com.knear.android.provider.response.gasprice.GasPriceResult
import com.knear.android.provider.response.genesisconfig.GenesisConfigResult
import com.knear.android.provider.response.networkstatus.NetworkStatusResult
import com.knear.android.provider.response.protocolconfig.ProtocolConfigResult
import com.knear.android.provider.response.sendmoney.SendMoney
import com.knear.android.provider.response.sendmoney.transactionstatus.TransactionStatus
import com.knear.android.provider.response.viewaccesskey.ViewAccessKey
import com.knear.android.provider.response.viewaccesskeychangeall.ViewAccessKeyChangesAllResult
import com.knear.android.provider.response.viewaccesskeychanges.ViewAccessKeyChangesResult
import com.knear.android.provider.response.viewaccesskeylist.ViewAccessKeyList
import com.knear.android.provider.response.viewaccount.ViewAccountResult
import com.knear.android.provider.response.viewaccountchanges.ViewAccountChangesResult
import com.knear.android.provider.response.viewcontractcode.ViewContractCodeResult
import com.knear.android.provider.response.viewcontractcodechanges.ViewContractCodeChangesResult
import com.knear.android.provider.response.viewcontractstate.ViewContractStateResult
import com.knear.android.provider.response.viewcontractstatechanges.ViewContractStateChangesResult
import com.knear.android.service.Account
import com.knear.android.service.AndroidKeyStore
import com.knear.android.scheme.KeyPairEd25519
import com.knear.android.scheme.PublicKey

open class NearService(
    private val walletUrl: String,
    private val rcpEndpoint: String,
    private var sharedPreferences: SharedPreferences
) {
    private val referrerParam: String by lazy { "Android SDK" }
    private val titleParam: String by lazy { "Android SDK" }
    val jsonRpcProvider = JsonRpcProvider(rcpEndpoint)

    fun startLogin(publicKey: PublicKey, redirectUrl: String): Uri {

        return Uri.parse(walletUrl)
            .buildUpon()
            .appendQueryParameter("referrer", referrerParam)
            .appendQueryParameter("title", titleParam)
            .appendQueryParameter(
                "public_key",
                publicKey.toString()
            )
            .appendQueryParameter("success_url", redirectUrl)
            .build()
    }

    fun finishLogging(networkId: String, secretKey: KeyPairEd25519, accountId: String) {
        val androidKeyStore = AndroidKeyStore(this.sharedPreferences)
        androidKeyStore.setAccountId(accountId)
        androidKeyStore.setNetworkId(networkId)
        androidKeyStore.setKey(networkId, accountId, secretKey)
    }

    fun viewAccount(accountId: String): ViewAccountResult {
        return jsonRpcProvider.viewAccount(accountId)
    }

    fun viewAccountChanges(accountIdList: List<String>, blockId: Int): ViewAccountChangesResult {
        return jsonRpcProvider.viewAccountChanges(accountIdList, blockId)
    }

    fun viewContractStateChanges(
        accountIdList: List<String>,
        keyPrefix64: String,
        blockId: Int
    ): ViewContractStateChangesResult {
        return jsonRpcProvider.viewContractStateChanges(accountIdList, keyPrefix64, blockId)
    }

    fun viewContractCode(accountId: String): ViewContractCodeResult {
        return jsonRpcProvider.viewContractCode(accountId)
    }

    fun viewContractState(accountId: String): ViewContractStateResult {
        return jsonRpcProvider.viewContractState(accountId)
    }

    fun viewContractCodeChanges(
        accountIdList: List<String>,
        blockId: Int
    ): ViewContractCodeChangesResult {
        return jsonRpcProvider.viewContractCodeChanges(accountIdList, blockId)
    }

    fun viewAccessKey(accountId: String): ViewAccessKey {
        val keyPair = getAccountKeyPair(accountId)
        return jsonRpcProvider.viewAccessKey(accountId, keyPair?.publicKey.toString())
    }

    fun viewAccessKeyList(accountId: String): ViewAccessKeyList {
        val keyPair = getAccountKeyPair(accountId)
        return jsonRpcProvider.viewAccessKeyList(accountId, keyPair?.publicKey.toString())
    }

    fun viewAccessKeyChange(
        accountId: String,
        currentAccessKey: String
    ): ViewAccessKeyChangesResult {
        return jsonRpcProvider.viewAccessKeyChanges(accountId, currentAccessKey)
    }

    fun viewAccessKeyChangeAll(
        accountIdList: List<String>,
        currentAccessKey: String
    ): ViewAccessKeyChangesAllResult {
        return jsonRpcProvider.viewAccessKeyChangesAll(accountIdList, currentAccessKey)
    }

    fun blockDetails(blockId: Int): Block {
        return jsonRpcProvider.blockDetails(blockId)
    }

    fun blockChanges(blockId: Int): BlockChangesResult {
        return jsonRpcProvider.blockChanges(blockId)
    }

    fun chunkDetails(chunkId: String): ChunkDetailsResult {
        return jsonRpcProvider.chunkDetails(chunkId)
    }


    fun gasPrice(blockId: Int): GasPriceResult {
        return jsonRpcProvider.gasPrice(blockId)
    }


    fun getGenesisConfig(): GenesisConfigResult {
        return jsonRpcProvider.getGenesisConfig()
    }

    fun getProtocolConfig(): ProtocolConfigResult {
        return jsonRpcProvider.getProtocolConfig()
    }

    fun getNetworkStatus(): NetworkStatusResult {
        return jsonRpcProvider.getNetworkStatus()
    }

    fun sendMoney(accountId: String, receiverId: String, amount: String): SendMoney {
        val androidKeyStore = AndroidKeyStore(this.sharedPreferences)
        val networkId =
            androidKeyStore.getNetworkId() ?: throw Error("sendMoney requires account logging")
        val keyPair = androidKeyStore.getKey(networkId, accountId)

        if (keyPair != null) {
            Log.i("NearService.sendMoney", "Sending $amount NEAR to $receiverId from $accountId")
            val account = Account(accountId, networkId, rcpEndpoint, keyPair)
            return account.sendMoney(receiverId, amount)
        }

        return SendMoney()
    }

    fun getAccountKeyPair(accountId: String): KeyPairEd25519? {
        val androidKeyStore = AndroidKeyStore(this.sharedPreferences)
        val networkId =
            androidKeyStore.getNetworkId() ?: throw Error("sendMoney requires account logging")
        return androidKeyStore.getKey(networkId, accountId)
    }

    fun transactionStatus(resultHash: String, accountId: String): TransactionStatus {
        return jsonRpcProvider.transactionStatus(resultHash, accountId)
    }

    fun stake(accountId: String, stakingKey: String, amount: String) {

    }

    fun deploy(
        accountId: String,
        wasmFile: String,
        initFunction: String,
        initArgs: String,
        initGas: String
    ) {

    }

    fun callViewFunctionTransaction(
        accountId: String,
        contractName: String,
        methodName: String,
        args: String = "{}",
        gas: Long = 30000000000000,
        attachedDeposit: String = "0"
    ): FunctionCallTransactionResponse {
        val androidKeyStore = AndroidKeyStore(this.sharedPreferences)
        val networkId = androidKeyStore.getNetworkId()
            ?: throw Error("Call Contract Function Transaction requires account logging")
        val keyPair = androidKeyStore.getKey(networkId, accountId)

        if (keyPair != null) {
            Log.i("NearService.", "callViewFunctionTransaction: $contractName.$methodName($args)")
            val account = Account(accountId, networkId, rcpEndpoint, keyPair)
            return account.functionCallTransaction(
                contractName,
                methodName,
                args,
                gas,
                attachedDeposit
            )

        }

        return FunctionCallTransactionResponse()
    }

    fun callViewFunction(
        accountId: String,
        contractName: String,
        methodName: String,
        args: String = "{}",
        gas: Long = 3000000000000,
        attachedDeposit: String = "0"
    ): FunctionCallResponse {
        val androidKeyStore = AndroidKeyStore(this.sharedPreferences)
        val networkId = androidKeyStore.getNetworkId()
            ?: throw Error("Call Contract Function requires account logging")
        val keyPair = androidKeyStore.getKey(networkId, accountId)

        if (keyPair != null) {
            Log.i("NearService.", "$contractName.$methodName($args)")
            val account = Account(accountId, networkId, rcpEndpoint, keyPair)
            return account.functionCall(
                accountId,
                contractName,
                methodName,
                args,
                gas,
                attachedDeposit
            )
        }

        return FunctionCallResponse()
    }

    fun clean() {

    }
}

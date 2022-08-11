package com.knear.android.provider

import android.util.Log
import com.knear.android.provider.model.*
import com.knear.android.provider.response.block.Block
import com.knear.android.provider.response.block.Block.Companion.toBlock
import com.knear.android.provider.response.blockchanges.BlockChangesResult
import com.knear.android.provider.response.blockchanges.BlockChangesResult.Companion.toBlockChanges
import com.knear.android.provider.response.chunkdetails.ChunkDetailsResult
import com.knear.android.provider.response.chunkdetails.ChunkDetailsResult.Companion.toChunkDetails
import com.knear.android.provider.response.gasprice.GasPriceResult
import com.knear.android.provider.response.gasprice.GasPriceResult.Companion.gasPriceResult
import com.knear.android.provider.response.genesisconfig.GenesisConfigResult
import com.knear.android.provider.response.genesisconfig.GenesisConfigResult.Companion.getGenesisConfigResult
import com.knear.android.provider.response.networkstatus.NetworkStatusResult
import com.knear.android.provider.response.networkstatus.NetworkStatusResult.Companion.getNetworkStatus
import com.knear.android.provider.response.protocolconfig.ProtocolConfigResult
import com.knear.android.provider.response.protocolconfig.ProtocolConfigResult.Companion.getProtocolConfigResult
import com.knear.android.provider.response.sendmoney.transactionstatus.TransactionStatus
import com.knear.android.provider.response.sendmoney.transactionstatus.TransactionStatus.Companion.getTransactionStatus
import com.knear.android.provider.response.viewaccesskey.ViewAccessKey
import com.knear.android.provider.response.viewaccesskey.ViewAccessKey.Companion.toViewAccessKey
import com.knear.android.provider.response.viewaccesskeychangeall.ViewAccessKeyChangesAllResult
import com.knear.android.provider.response.viewaccesskeychangeall.ViewAccessKeyChangesAllResult.Companion.toViewAccessKeyChangesAll
import com.knear.android.provider.response.viewaccesskeychanges.ViewAccessKeyChangesResult
import com.knear.android.provider.response.viewaccesskeychanges.ViewAccessKeyChangesResult.Companion.toViewAccessKeyChanges
import com.knear.android.provider.response.viewaccesskeylist.ViewAccessKeyList
import com.knear.android.provider.response.viewaccesskeylist.ViewAccessKeyList.Companion.toViewAccessKeyList
import com.knear.android.provider.response.viewaccount.ViewAccountResult
import com.knear.android.provider.response.viewaccount.ViewAccountResult.Companion.toViewAccount
import com.knear.android.provider.response.viewaccountchanges.ViewAccountChangesResult
import com.knear.android.provider.response.viewaccountchanges.ViewAccountChangesResult.Companion.toViewAccountChanges
import com.knear.android.provider.response.viewcontractcode.ViewContractCodeResult
import com.knear.android.provider.response.viewcontractcode.ViewContractCodeResult.Companion.toViewContractCode
import com.knear.android.provider.response.viewcontractcodechanges.ViewContractCodeChangesResult
import com.knear.android.provider.response.viewcontractcodechanges.ViewContractCodeChangesResult.Companion.toViewContractCodeChanges
import com.knear.android.provider.response.viewcontractstate.ViewContractStateResult
import com.knear.android.provider.response.viewcontractstate.ViewContractStateResult.Companion.toViewContractState
import com.knear.android.provider.response.viewcontractstatechanges.ViewContractStateChangesResult
import com.knear.android.provider.response.viewcontractstatechanges.ViewContractStateChangesResult.Companion.toViewContractStateChanges
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class JsonRpcProvider (private val rcpEndpoint: String) {
    private var nextId = 123

    fun viewAccessKey(accountId: String, base58PublicKey: String) : ViewAccessKey {
        val params = NearRequestParams(
            finality = "optimistic",
            requestType = "view_access_key",
            accountId = accountId,
            publicKey = base58PublicKey
        )
        val response = this.sendJsonRpc("query", params)
        return response?.toViewAccessKey() ?: ViewAccessKey()
    }

    fun viewAccessKeyList(accountId: String, base58PublicKey: String) : ViewAccessKeyList {
        val params = NearRequestParams(
            finality = "optimistic",
            requestType = "view_access_key_list",
            accountId = accountId,
            publicKey = base58PublicKey
        )
        val response = this.sendJsonRpc("query", params)
        return response?.toViewAccessKeyList()?: ViewAccessKeyList()
    }

    fun viewAccessKeyChanges(accountId: String, base58PublicKey: String) : ViewAccessKeyChangesResult {
        val keys = Keys(accountId, base58PublicKey)
        val paramsList = listOf(keys)
        val viewAccessKeyChangesParams = ViewAccessKeyChangesParams("single_access_key_changes",paramsList)
        val response = this.sendJsonRpc("EXPERIMENTAL_changes",  viewAccessKeyChangesParams)
        return response?.toViewAccessKeyChanges()?: ViewAccessKeyChangesResult()
    }

    fun viewAccessKeyChangesAll(accountIdList: List<String>, base58PublicKey: String) : ViewAccessKeyChangesAllResult {
        val viewAccessKeyChangesAllParams = ViewAccessKeyChangesAllParams("all_access_key_changes",accountIdList,base58PublicKey)
        val response = this.sendJsonRpc("EXPERIMENTAL_changes",  viewAccessKeyChangesAllParams)
        return response?.toViewAccessKeyChangesAll()?: ViewAccessKeyChangesAllResult()
    }

    fun viewAccount(accountId: String): ViewAccountResult {
        val params = NearRequestParams(
            finality = "final",
            requestType = "view_account",
            accountId = accountId
        )
        val response = this.sendJsonRpc("query", params)
        return response?.toViewAccount()?: ViewAccountResult()
    }

    fun viewAccountChanges(accountIdList: List<String>, blockId: Int): ViewAccountChangesResult {
        val viewAccountChangesParams = ViewAccountChangesParams("account_changes", accountIdList, blockId)
        val response = this.sendJsonRpc("EXPERIMENTAL_changes",  viewAccountChangesParams)
        return response?.toViewAccountChanges()?: ViewAccountChangesResult()
    }

    fun viewContractStateChanges(accountIdList: List<String>, keyPrefix64:String, blockId: Int): ViewContractStateChangesResult {
        val viewContractStateChangesParams = ViewContractStateChangesParams("data_changes", accountIdList, keyPrefix64,blockId)
        val response = this.sendJsonRpc("EXPERIMENTAL_changes",  viewContractStateChangesParams)
        return response?.toViewContractStateChanges()?: ViewContractStateChangesResult()
    }


    fun viewContractCode(accountId: String): ViewContractCodeResult {
        val params = NearRequestParams(
            finality = "final",
            requestType = "view_code",
            accountId = accountId
        )
        val response = this.sendJsonRpc("query", params)
        return response?.toViewContractCode()?: ViewContractCodeResult()
    }

    fun viewContractState(accountId: String): ViewContractStateResult {
        val params = NearRequestParams(
            finality = "final",
            requestType = "view_state",
            accountId = accountId
        )
        val response = this.sendJsonRpc("query", params)
        return response?.toViewContractState()?: ViewContractStateResult()
    }

    fun viewContractCodeChanges(accountIdList: List<String>, blockId: Int): ViewContractCodeChangesResult {
        val viewAccountChangesParams = ViewContractCodeChangesParams("contract_code_changes", accountIdList, blockId)
        val response = this.sendJsonRpc("EXPERIMENTAL_changes",  viewAccountChangesParams)
        return response?.toViewContractCodeChanges()?: ViewContractCodeChangesResult()
    }

    fun block() : Block {
        val params = NearRequestParams(finality = "final")
        val response : Response? = this.sendJsonRpc("block", params)
        return response?.toBlock()?: Block()
    }

    fun blockDetails(blockId: Int): Block {
        val params = BlockDetailsParams(blockId)
        val response : Response? = this.sendJsonRpc("block", params)
        return response?.toBlock()?: Block()
    }

    fun blockChanges(blockId: Int): BlockChangesResult {
        val params = BlockDetailsParams(blockId)
        val response : Response? = this.sendJsonRpc("EXPERIMENTAL_changes_in_block", params)
        return response?.toBlockChanges()?: BlockChangesResult()
    }

    fun chunkDetails(blockId: String): ChunkDetailsResult {
        val params = ChunkDetailsParams(blockId)
        val response : Response? = this.sendJsonRpc("chunk", params)
        return response?.toChunkDetails()?: ChunkDetailsResult()
    }

    fun sendTransaction(method: String, params: List<String>): Response? {
        return sendJsonRpc(method, params)
    }

    private fun sendJsonRpc(
        method: String,
        viewAccessKeyChangesParams: ViewAccessKeyChangesParams
    ): Response? {
        val nearRequestBody = ViewAccessKeyChangesRequestParams(method, viewAccessKeyChangesParams, ++this.nextId)
        val client = OkHttpClient()
        val jsonRequest = nearRequestBody.toString()
        val requestJsonBody : RequestBody = jsonRequest
            .toRequestBody("application/json; charset=utf-8".toMediaType())
        val requestBuilder = Request.Builder()
        val postRequest = requestBuilder.url("$rcpEndpoint").post(requestJsonBody)
        return try {
            client.newCall(postRequest.build()).execute()
        } catch ( ex :Throwable ) {
            Log.i("JsonRpcProvider.sendJsonRcp", ex.message!!)
            null
        }
    }

    private fun sendJsonRpc(
        method: String,
        viewChangesParams: Any
    ): Response? {
        val nearRequestBody = ViewAccessKeyChangesAllRequestParams(method, viewChangesParams, ++this.nextId)
        val client = OkHttpClient()
        val jsonRequest = nearRequestBody.toString()
        val requestJsonBody : RequestBody = jsonRequest
            .toRequestBody("application/json; charset=utf-8".toMediaType())
        val requestBuilder = Request.Builder()
        val postRequest = requestBuilder.url("$rcpEndpoint").post(requestJsonBody)
        return try {
            client.newCall(postRequest.build()).execute()
        } catch ( ex :Throwable ) {
            Log.i("JsonRpcProvider.sendJsonRcp", ex.message!!)
            null
        }
    }

    fun sendJsonRpc(method: String, params: NearRequestParams) : Response? {
        val nearRequestBody = NearRequest(method, params, ++this.nextId)

        val client = OkHttpClient()

        val jsonString = nearRequestBody.toString()
        val requestJsonBody : RequestBody = jsonString
            .toRequestBody("application/json".toMediaType())

        val requestBuilder = Request.Builder()
        val postRequest = requestBuilder.url("$rcpEndpoint").post(requestJsonBody)

        return try {
            client.newCall(postRequest.build()).execute()
        } catch ( ex :Throwable ) {
            Log.i("JsonRpcProvider.sendJsonRcp", ex.message!!)
            null
        }

    }

    private fun sendJsonRpc(method: String, params: List<String>) : Response? {
        val nearRequestBody = NearRequestParamList(method, params, ++this.nextId)

        val client = OkHttpClient()

        val jsonRequest = nearRequestBody.toString()
        val requestJsonBody : RequestBody = jsonRequest
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val requestBuilder = Request.Builder()
        val postRequest = requestBuilder.url("$rcpEndpoint").post(requestJsonBody)

        return try {
            client.newCall(postRequest.build()).execute()
        } catch ( ex :Throwable ) {
            Log.i("JsonRpcProvider.sendJsonRcp", ex.message!!)
            null
        }
    }

    fun transactionStatus(transactionHashResult:String, accountId:String): TransactionStatus {
        val paramsList = listOf(transactionHashResult, accountId)
        val response = this.sendJsonRpc("tx", paramsList)
        return response?.getTransactionStatus()?: TransactionStatus()
    }

    fun gasPrice(blockId: Int): GasPriceResult {
        val paramsList = listOf(blockId)
        val response = this.sendJsonRpc("gas_price", paramsList)
        return response?.gasPriceResult()?: GasPriceResult()
    }

    fun getGenesisConfig(): GenesisConfigResult {
        val response = this.sendJsonRpc("EXPERIMENTAL_genesis_config", listOf())
        return response?.getGenesisConfigResult()?: GenesisConfigResult()
    }

    fun getProtocolConfig(): ProtocolConfigResult {
        val response = this.sendJsonRpc("EXPERIMENTAL_protocol_config", ProtocolConfigParams("final"))
        return response?.getProtocolConfigResult()?: ProtocolConfigResult()
    }

    fun getNetworkStatus(): NetworkStatusResult {
        val response = this.sendJsonRpc("status", listOf())
        return response?.getNetworkStatus()?: NetworkStatusResult()
    }
}

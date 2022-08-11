package com.knear.android.service

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.knear.android.NearService
import com.knear.android.scheme.KeyPair
import com.knear.android.scheme.KeyPairEd25519

class NearMainService(val activity:Activity) {

    private var networkId = "wallet.testnet.near.org"
    private var accountId: String = ""
    private val walletUrl = "https://wallet.testnet.near.org/login/"
    private val rcpEndpoint = "https://rpc.testnet.near.org"
    private val redirectUri = "near://success-auth"

    private var allKeys: String = ""
    private var publicKey: String = ""

    private var nearService : NearService = NearService(walletUrl, rcpEndpoint, activity.getPreferences(AppCompatActivity.MODE_PRIVATE))
    private lateinit var privateKey: KeyPairEd25519
    private var androidKeyStore: AndroidKeyStore = AndroidKeyStore(activity.getPreferences(AppCompatActivity.MODE_PRIVATE))

    fun login(email:String){
        this.androidKeyStore.setAccountId(email)
        this.androidKeyStore.setNetworkId(networkId)

        val networkId = androidKeyStore.getNetworkId()
        val accountId = androidKeyStore.getAccountId();

        if(!networkId.isNullOrEmpty() && !accountId.isNullOrEmpty()) {
            this.accountId = accountId
            this.networkId = networkId
        }
        this.privateKey = KeyPair.fromRandom("ed25519")
        loginOAuth()
    }

    private fun loginOAuth() {
        val loggingUri = this.nearService.startLogin(this.privateKey.publicKey, redirectUri)

        this.androidKeyStore.setAccountId(accountId)
        this.androidKeyStore.setNetworkId(networkId)
        this.androidKeyStore.setKey(networkId, accountId, this.privateKey)
        val intent = Intent(Intent.ACTION_VIEW, loggingUri)
        activity.startActivity(intent)
    }

    fun attemptLogin(uri: Uri?):Boolean {
        var success = false
        uri?.let {
            if (it.toString().startsWith(redirectUri)) {
                val currentAccountId = uri.getQueryParameter("account_id")
                val currentPublicKey = uri.getQueryParameter("public_key")
                val currentKeys = uri.getQueryParameter("all_keys")

                if (!currentAccountId.isNullOrEmpty() && !currentKeys.isNullOrEmpty() && !currentPublicKey.isNullOrEmpty()) {
                    accountId = currentAccountId
                    allKeys = currentKeys
                    publicKey = currentPublicKey

                    androidKeyStore.getKey(networkId, accountId)?.let { this.privateKey = it

                    this.nearService.finishLogging(networkId, this.privateKey, accountId)
                        success = true
                    }
                }
            }
        }
        return success
    }

    fun sendTransaction(receiver: String, amount: String) = this.nearService.sendMoney(accountId, receiver, amount)

    fun callViewFunction(
        contractName: String,
        total: String,
        totalSupplyArgs: String
    ) = nearService.callViewFunction(accountId,contractName, total,totalSupplyArgs)

    fun callViewFunctionTransaction(contractName: String, methodName: String, balanceOfArgs: String) =
        nearService.callViewFunctionTransaction(accountId, contractName, methodName, balanceOfArgs)

    fun viewAccessKey() = nearService.viewAccessKey(accountId)
    fun viewAccessKeyLists() = nearService.viewAccessKeyList(accountId)
    fun viewAccessKeyChangesAll(accountIdList:List<String>) = nearService.viewAccessKeyChangeAll(accountIdList,publicKey)
    fun viewAccessKeyChange(currentAccessKey:String) = nearService.viewAccessKeyChange(accountId, currentAccessKey)
    fun transactionStatus(txResultHash:String) = nearService.transactionStatus(txResultHash, accountId)
    fun viewAccount() = nearService.viewAccount(accountId)
    fun viewAccountChanges(accountIdList: List<String>, blockId:Int) = nearService.viewAccountChanges(accountIdList, blockId)
    fun viewContractCode() = nearService.viewContractCode(accountId)
    fun viewContractState() = nearService.viewContractState(accountId)
    fun viewContractStateChanges(accountIdList: List<String>, keyPrefix:String, blockId:Int) = nearService.viewContractStateChanges(accountIdList, keyPrefix, blockId)
    fun viewContractCodeChanges(accountIdList: List<String>, blockId:Int) = nearService.viewContractCodeChanges(accountIdList, blockId)
    fun getBlockDetails(blockId: Int) = nearService.blockDetails(blockId)
    fun getBlockChanges(blockId: Int) = nearService.blockChanges(blockId)
    fun getChunkHash(chunksHash:String) = nearService.chunkDetails(chunksHash)
    fun gasPrice(blockId: Int) = nearService.gasPrice(blockId)
    fun getGenesisConfiguration() = nearService.getGenesisConfig()
    fun getProtocolConfig() = nearService.getProtocolConfig()
    fun getNetworkStatus() = nearService.getNetworkStatus()
}

package com.knear.android.service

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.knear.android.NearService
import com.knear.android.scheme.KeyPair
import com.knear.android.scheme.KeyPairEd25519
import com.knear.android.user.NearUserInformationListener
import com.knear.android.user.domain.model.UserInformation
import com.knear.android.user.view.NearUserViewModel
import com.knear.android.user.view.NearUserViewModelFactory

class NearMainService(
    private val activity: Activity,
    lifecycleOwner: ViewModelStoreOwner,
    private val nearUserInformationListener: NearUserInformationListener?
) {

    private var networkId = "wallet.near.org"
    private var accountId: String = ""
    private val walletUrl = "https://wallet.near.org/login/"
    private val rpcEndpoint = "https://rpc.mainnet.near.org"
    private val redirectUri = "near://success-auth"

    private var allKeys: String = ""
    private var publicKey: String = ""

    private val nearUserViewModel =
        ViewModelProvider(lifecycleOwner, NearUserViewModelFactory(activity.application))[NearUserViewModel::class.java]

    private var nearService : NearService = NearService(walletUrl, rpcEndpoint, activity)
    private var privateKey: KeyPairEd25519 = KeyPairEd25519("","")
    private var androidKeyStore: AndroidKeyStore = AndroidKeyStore(activity)

    fun loginOAuth() {
        this.privateKey = KeyPair.fromRandom("ed25519")
        val loggingUri = this.nearService.startLogin(this.privateKey.publicKey, redirectUri)
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

                    this.androidKeyStore.setAccountId(accountId)
                    this.androidKeyStore.setNetworkId(networkId)
                    this.androidKeyStore.setKey(networkId, accountId, this.privateKey)

                    androidKeyStore.getKey(networkId, accountId)?.let { this.privateKey = it

                        this.nearService.finishLogging(networkId, this.privateKey, accountId)
                        success = true
                    }
                }
            }
        }

        if (success){
            saveUserSession()
        }

        return success
    }
    fun logout(){
        removeUserSession()
    }
    fun sendTransaction(receiver: String, amount: String) = this.nearService.sendMoney(accountId, receiver, amount)
    fun callViewFunction(
        contractName: String,
        methodName: String,
        totalSupplyArgs: String
    ) = nearService.callViewFunction(accountId = accountId,contractName = contractName, methodName = methodName, args = totalSupplyArgs)
    fun callViewFunctionTransaction(contractName: String, methodName: String, balanceOfArgs: String) =
        nearService.callViewFunctionTransaction(accountId, contractName, methodName, balanceOfArgs)
    fun callFunctionSwapNearToWNear(attachedDeposit:String) =
        nearService.callViewFunctionTransaction(accountId, "wrap.near", "near_deposit", "{}", attachedDeposit = attachedDeposit)
    fun callFunctionSwapWNearToNear(arguments:String, attachedDeposit: String) =
        nearService.callViewFunctionTransaction(accountId, "wrap.near", "near_withdraw", args = arguments, attachedDeposit = attachedDeposit)
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
    private fun saveUserSession() {
        nearUserViewModel.saveUserInformation(
            UserInformation(
                accountId = accountId,
                publicKey = publicKey,
                isLoggedIn = true
            )
        )
    }

    fun getUserInformation() {
        nearUserViewModel.getAllUsers.observe(activity as LifecycleOwner) { userList ->
            userList.map {
                accountId = it.accountId
                nearUserInformationListener?.userInformationResponse(
                    UserInformation(
                        it.accountId,
                        it.publicKey,
                        it.isLoggedIn)
                )
            }
        }
    }

    private fun removeUserSession() {
        nearUserViewModel.removeUserInformation()
    }
}

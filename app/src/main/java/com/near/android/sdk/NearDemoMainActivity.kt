package com.near.android.sdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.knear.android.service.MethodUtils.Companion.getDecodedAsciiValue
import com.knear.android.service.NearMainService
import com.jose.lujan.near.android.sdk.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class NearDemoMainActivity : AppCompatActivity() {

    private var loginFragment: LoginFragment = LoginFragment.getInstance()
    private var transactionFragment: TransactionFragment = TransactionFragment.getInstance()
    private lateinit var nearMainService: NearMainService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near_demo_main)
        supportFragmentManager.beginTransaction().add(R.id.demo_fragment_container, loginFragment)
            .commit()
        nearMainService = NearMainService(this)
    }

    fun login(email: String) {
        nearMainService.login(email)
    }

    override fun onResume() {
        super.onResume()
        val uri = intent.data

        if (nearMainService.attemptLogin(uri)) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.demo_fragment_container, transactionFragment).commit()
        }
    }

    fun sendTransaction() {
        CoroutineScope(Dispatchers.IO).launch {
            val transaction = nearMainService.sendTransaction("android-test-23.testnet", "1")
            withContext(Dispatchers.Main) {
                transactionFragment.updateTxResponse(transaction)
            }
        }
    }

    fun requestCallFunction() {
        CoroutineScope(Dispatchers.IO).launch {
            val callFunctionResponse = callFunction()
            withContext(Dispatchers.Main) {
                transactionFragment.updateCallFunctionResponse(callFunctionResponse)
            }
        }
    }

    private fun callFunction(): StringBuilder {
        val sbResponse: StringBuilder = StringBuilder("")
        var balanceOfArgs = "{ \"tokenOwner\": \"android-test-22.testnet\" }"
        val contractName = "android-test-22.testnet"
        val balanceOfResponse =
            this.nearMainService.callViewFunction(contractName, "balanceOf", balanceOfArgs)
        if (balanceOfResponse.error == null) {
            val functionResult = balanceOfResponse.result.result!!.getDecodedAsciiValue()
            Log.i("NearService", "$contractName.balanceOf($balanceOfArgs): $functionResult")
            Log.i("NearService", "Logs: ${balanceOfResponse.result.logs}")
            sbResponse.appendLine("$contractName.balanceOf($balanceOfArgs): $functionResult")
            sbResponse.appendLine("Logs: ${balanceOfResponse.result.logs}")
        } else {
            Log.e(
                "NearService",
                "$contractName.balanceOf($balanceOfArgs) ${balanceOfResponse.error?.message}"
            )
            sbResponse.appendLine("$contractName.balanceOf($balanceOfArgs) ${balanceOfResponse.error?.message}")
        }

        val totalSupplyArgs = "{}"
        val totalSupplyResponse =
            this.nearMainService.callViewFunction(contractName, "totalSupply", totalSupplyArgs)
        if (totalSupplyResponse.error == null) {
            val functionResult = totalSupplyResponse.result.result!!.getDecodedAsciiValue()
            Log.i("NearService", "$contractName.totalSupply($totalSupplyArgs): $functionResult")
            Log.i("NearService", "Logs: ${totalSupplyResponse.result.logs}")
            sbResponse.appendLine("$contractName.totalSupply($totalSupplyArgs): $functionResult")
            sbResponse.appendLine("Logs: ${totalSupplyResponse.result.logs}")
        } else {
            Log.e(
                "NearService",
                "$contractName.totalSupply($totalSupplyArgs) ${totalSupplyResponse.error?.message}"
            )
            sbResponse.appendLine("$contractName.totalSupply($totalSupplyArgs) ${totalSupplyResponse.error?.message}")
        }

        val transferFromArgs =
            "{ \"from\": \"android-test-22.testnet\", \"to\": \"android-test-23.testnet\", \"tokens\": \"1\" }"
        val transferFromResponse =
            this.nearMainService.callViewFunction(contractName, "transferFrom", transferFromArgs)
        if (transferFromResponse.error == null) {
            val functionResult = totalSupplyResponse.result.result!!.getDecodedAsciiValue()
            Log.i("NearService", "$contractName.transferFrom($transferFromArgs): $functionResult")
            Log.i("NearService", "Logs: ${totalSupplyResponse.result.logs}")
            sbResponse.appendLine("$contractName.transferFrom($transferFromArgs): $functionResult")
            sbResponse.appendLine("Logs: ${totalSupplyResponse.result.logs}")
        } else {
            Log.e(
                "NearService",
                "$contractName.transferFrom($transferFromArgs) ${transferFromResponse.error?.message}"
            )
            sbResponse.appendLine("$contractName.transferFrom($transferFromArgs) ${transferFromResponse.error?.message}")
        }

        balanceOfArgs = "{ \"tokenOwner\": \"android-test-22.testnet\" }"
        val callViewFunctionTransactionResponse = this.nearMainService.callViewFunctionTransaction(
            contractName,
            "balanceOf",
            balanceOfArgs
        )
        if (callViewFunctionTransactionResponse.result.status.Failure == null) {
            Log.i(
                "NearService.",
                "Hash: ${callViewFunctionTransactionResponse.result.transaction.hash}"
            )
            sbResponse.appendLine("Hash: ${callViewFunctionTransactionResponse.result.transaction.hash}")
            callViewFunctionTransactionResponse.result.status.SuccessValue?.let {
                val decodedValue = String(Base64.getDecoder().decode(it))
                Log.i("NearService.", "SuccessValue: $decodedValue {$it}")
                sbResponse.appendLine("SuccessValue: $decodedValue {$it}")
            }
        }

        val invalidBalanceOfArgs = "{ tokenOwner: android-test-22.testnet }"
        val errorResponse = this.nearMainService.callViewFunctionTransaction(
            contractName,
            "balanceOf",
            invalidBalanceOfArgs
        )
        Log.i(
            "NearService.",
            "Hash: ${callViewFunctionTransactionResponse.result.transaction.hash}"
        )
        sbResponse.appendLine("Hash: ${callViewFunctionTransactionResponse.result.transaction.hash}")
        if (errorResponse.result.status.Failure != null) {
            Log.e(
                "NearService.",
                "Error: ${errorResponse.result.status.Failure!!.ActionError!!.kind.FunctionCallError!!.ExecutionError}"
            )
            sbResponse.appendLine("Error: ${errorResponse.result.status.Failure!!.ActionError!!.kind.FunctionCallError!!.ExecutionError}")
        }
        return sbResponse
    }

}

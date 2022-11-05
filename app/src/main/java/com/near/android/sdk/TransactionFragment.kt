package com.near.android.sdk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.knear.android.provider.response.sendmoney.SendMoney
import com.jose.lujan.near.android.sdk.R
import kotlinx.android.synthetic.main.fragment_transaction.view.*

class TransactionFragment : Fragment() {

    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_transaction, container, false)

        v.transaction_btn_cv.setOnClickListener {
            (activity as NearDemoMainActivity).sendTransaction()
            v.tx_progress.visibility = View.VISIBLE
        }
        v.call_function_btn_cv.setOnClickListener {
            (activity as NearDemoMainActivity).requestCallFunction()
            v.tx_progress.visibility = View.VISIBLE
        }

        return v
    }

    fun updateTxResponse(callFunctionResponse: SendMoney) {
        v.tx_progress.visibility = View.GONE
        callFunctionResponse.result.let {
            v.serviceReponseTv.text = "Success with hash: $it"
        }

        callFunctionResponse.error?.let {
            v.serviceReponseTv.text = "Error: ${it.message}"
        }
    }

    fun updateCallFunctionResponse(callFunctionResponse: StringBuilder) {
        v.tx_progress.visibility = View.GONE
        callFunctionResponse.let {
            v.serviceReponseTv.text = "Success: $it"
        }
    }

    companion object {
        @JvmStatic
        fun getInstance() = TransactionFragment()
    }
}

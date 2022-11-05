package com.near.android.sdk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jose.lujan.near.android.sdk.R
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_login, container, false)

        v.login_btn_cv.setOnClickListener {
            val email = v.user_email_et.text.toString()

            if (email.isNotEmpty()) {
                (activity as NearDemoMainActivity).login(email)
            }
        }

        return v
    }

    companion object {
        @JvmStatic
        fun getInstance() = LoginFragment()
    }
}

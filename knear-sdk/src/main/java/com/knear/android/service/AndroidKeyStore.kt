package com.knear.android.service

import android.app.Activity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.knear.android.scheme.KeyPair
import com.knear.android.scheme.KeyPairEd25519


class AndroidKeyStore(activityContext: Activity) {
    private val activityContext = activityContext
    private val editor: SharedPreferences.Editor

    init {
        getSharedPreference().edit().also { this.editor = it }
    }

    private fun getSharedPreference(): SharedPreferences {
        return activityContext.getSharedPreferences("NearAndroidKeystore", AppCompatActivity.MODE_PRIVATE)
    }

    fun setAccountId(accountId: String) {
        editor.putString("accountId", accountId)
        editor.commit()
    }

    fun getAccountId() : String? {
        return this.getSharedPreference().getString("accountId", "")
    }

    fun setNetworkId(networkId: String) {
        editor.putString("networkId", networkId)
        editor.commit()
    }

    fun getNetworkId(): String? {
        return this.getSharedPreference().getString("networkId", "")
    }

    fun setKey(networkId: String, accountId: String, secretKey: KeyPairEd25519) {
        editor.putString("$accountId:$networkId", secretKey.toString())
        editor.putString("$accountId:$networkId:public", secretKey.publicKey.toString())
        editor.commit()
    }

    fun getKey(networkId: String, accountId: String) : KeyPairEd25519? {
        val secretKey = getSharedPreference().getString("$accountId:$networkId", "")
        val publicKey = getSharedPreference().getString("$accountId:$networkId:public", "")
        if(secretKey.isNullOrEmpty()) {
            return null
        }

        if(publicKey.isNullOrEmpty()) {
            return null
        }

        return KeyPair.fromStringKeyPair(secretKey, publicKey)
    }

    fun removeKey(networkId: String, accountId: String) {
        editor.remove("$accountId:$networkId")
        editor.remove("$accountId:$networkId:public")
        editor.commit()
    }

    fun clear() {
        editor.clear()
        editor.commit()
    }

    override fun toString(): String {
        return "AndroidKeyStore"
    }
}

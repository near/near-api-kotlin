package com.knear.android.scheme

import com.iwebpp.crypto.TweetNaclFast
import com.syntifi.crypto.key.Ed25519PrivateKey
import com.syntifi.crypto.key.encdec.Base58

class KeyPairEd25519(ed25519SecretKey: String, ed25519PublicKey: String) : KeyPair() {
    var publicKey: PublicKey
    private var secretKey: String
    private var data: ByteArray

    init {
        val publicKeyAsBytes = Base58.decode(ed25519PublicKey)
        this.data = Base58.decode(ed25519SecretKey)
        this.publicKey = PublicKey(KeyType.ED25519, publicKeyAsBytes)
        this.secretKey = ed25519SecretKey
    }

    companion object {
        fun fromRandom(): KeyPairEd25519 {
            val keyPair = TweetNaclFast.Signature.keyPair();

            val encodedSecret = Base58.encode(keyPair.secretKey)
            val encodedPublic = Base58.encode(keyPair.publicKey)
            return KeyPairEd25519(encodedSecret, encodedPublic)
        }
    }

    fun sign(message: ByteArray): ByteArray {
        var privateKey = Ed25519PrivateKey(this.data);
        return privateKey.sign(message)
        /*
        val signature = TweetNaclFast.Signature(this.publicKey.data, decodedPrivateKey)
        var signedMsg = signature.detached(message)
        return signedMsg*/
    }

    fun verify(message: ByteArray, signature: ByteArray): Boolean {
        val twSignature = TweetNaclFast.Signature(this.publicKey.data, this.data)

        return twSignature.detached_verify(message, signature)
    }

    override fun toString(): String {
        return "ed25519:${this.secretKey}"
    }
}

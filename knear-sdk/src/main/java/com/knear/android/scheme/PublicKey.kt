package com.knear.android.scheme

import com.syntifi.crypto.key.encdec.Base58
import com.syntifi.near.borshj.Borsh
import com.syntifi.near.borshj.annotation.BorshField

class PublicKey (kType: KeyType, publicKey: ByteArray ) : Borsh {
    @BorshField(order = 1)
    var keyType: KeyType = kType
    @BorshField(order = 2)
    var data: ByteArray = publicKey

    companion object {
        private fun strToKeyType(value: String) : KeyType {
            return when(value.lowercase()) {
                "ed25519" -> KeyType.ED25519
                else -> {
                    throw Error("Unknown key type $value")
                }
            }
        }

        fun fromString(encodedKey: String) : PublicKey {
            val parts: List<String> = encodedKey.split(':')
            return when (parts.size) {
                1 -> {
                    val decodedPublicKey = Base58.decode(parts[0])
                    PublicKey(KeyType.ED25519, decodedPublicKey)
                }
                2 -> {
                    val decodedPublicKey = Base58.decode(parts[1])
                    PublicKey(strToKeyType(parts[0]), decodedPublicKey)
                }
                else -> {
                    throw Error("Invalid encoded key format, must be <curve>:<encoded key>")
                }
            }
        }
    }

    private fun keyTypeToStr(kType: KeyType) : String{
        when(kType) {
            KeyType.ED25519 -> return "ed25519"
            else -> {
                throw Error("Unknown key type $kType")
            }
        }
    }

    override fun toString(): String {
        val publicKey =  Base58.encode(this.data)
        return "${this.keyTypeToStr(this.keyType)}:${publicKey}"
    }
/*
    fun verify(message: ByteArray, signature: ByteArray): Boolean {
        when(this.keyType) {
            KeyType.ED25519 -> {
                val twSignature = TweetNaclFast.Signature(this.data, )
                return twSignature.detached_verify(message, signature)
            }
            else -> {
                throw Error("Unknown key type ${this.keyType}")
            }
        }
    }
 */
}

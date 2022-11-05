package com.knear.android.scheme

open class KeyPair {
    companion object {
        fun fromRandom(curve: String): KeyPairEd25519 {
            when (curve.uppercase()) {
                "ED25519" -> return KeyPairEd25519.fromRandom()
                else -> {
                    throw Error("Unknown curve $curve")
                }
            }
        }

        fun removeKeyType(encodedKey: String): String {
            val parts: List<String> = encodedKey.split(':')
            when (parts.size) {
                1 -> {
                    return parts[0]
                }
                2 -> {
                    when (parts[0].uppercase()) {
                        "ED25519" -> return parts[1]
                        else -> {
                            throw Error("Unknown curve $parts[0]")
                        }
                    }
                }
                else -> {
                    throw Error("Invalid encoded key format, must be <curve>:<encoded key>")
                }
            }
        }

        fun fromString(encodedSecretKey: String, encodedPublicKey: String): KeyPairEd25519 {
            val parts: List<String> = encodedSecretKey.split(':')
            when (parts.size) {
                1 -> {
                    return KeyPairEd25519(parts[0], encodedPublicKey)
                }
                2 -> {
                    when (parts[0].uppercase()) {
                        "ED25519" -> return KeyPairEd25519(parts[1], encodedPublicKey)
                        else -> {
                            throw Error("Unknown curve $parts[0]")
                        }
                    }
                }
                else -> {
                    throw Error("Invalid encoded key format, must be <curve>:<encoded key>")
                }
            }
        }

        fun fromStringKeyPair(secretKeyEncoded: String, publicKeyEncoded: String): KeyPairEd25519 {
            val keyPair =
                KeyPairEd25519(removeKeyType(secretKeyEncoded), removeKeyType(publicKeyEncoded))
            return keyPair
        }
    }

}

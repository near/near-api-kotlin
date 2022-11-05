package com.knear.android.service

import com.google.gson.Gson
import java.math.BigInteger

class MethodUtils {

    companion object {

        fun String.convertAmountForSendingNear(): BigInteger {
            val inputNear = this.toBigDecimal()
            val constValueInBI = StringUtils.HARD_CODED_FOR_CONVERTING_NEAR_VALUE.toBigDecimal()
            return (inputNear * constValueInBI).toBigInteger()
        }

        fun ByteArray.getDecodedAsciiValue(): String {
            if (this !== null) {
                /**
                 * Near returns an array of bytes as result, is an ASCII code of
                 * near-sdk-rs and near-sdk-as
                 */
                var unsignedToBytes: List<Byte> =
                    this.map { byte: Byte -> (byte.toInt() and 0xff).toByte() }
                val asciiValueBytes = unsignedToBytes.toByteArray()
                val stringValue = asciiValueBytes.toString(Charsets.US_ASCII);
                // Deserialized as string
                return Gson().fromJson(stringValue, String::class.java)
            }
            return ""
        }


    }
}

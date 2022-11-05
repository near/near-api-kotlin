package com.knear.android.scheme

import com.syntifi.crypto.key.encdec.Base58
import com.syntifi.near.borshj.Borsh
import com.syntifi.near.borshj.annotation.BorshField


class Signature(
    @BorshField(order = 1)
    var type: KeyType,
    @BorshField(order = 2)
    var data: ByteArray
) : Borsh {
    override fun toString(): String {
        return Base58.encode(data)
    }
}

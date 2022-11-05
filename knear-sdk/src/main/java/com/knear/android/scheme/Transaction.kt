package com.knear.android.scheme

import com.knear.android.scheme.action.Action
import com.syntifi.near.borshj.Borsh
import com.syntifi.near.borshj.annotation.BorshField

open class Transaction(
    @BorshField(order = 1)
    var signerId: String,
    @BorshField(order = 2)
    var publicKey: PublicKey,
    @BorshField(order = 3)
    var nonce: Long,
    @BorshField(order = 4)
    var receiverId: String,
    @BorshField(order = 5)
    var blockHash: ByteArray,
    @BorshField(order = 6)
    var actions: Collection<Action>,
) : Borsh

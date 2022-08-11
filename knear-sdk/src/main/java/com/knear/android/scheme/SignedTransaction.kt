package com.knear.android.scheme

import com.syntifi.near.borshj.Borsh
import com.syntifi.near.borshj.annotation.BorshField

class SignedTransaction(
    @BorshField(order = 1)
    val transaction: Transaction,
    @BorshField(order = 2)
    val signature: Signature
) : Borsh

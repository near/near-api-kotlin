package com.knear.android.scheme.action

import com.syntifi.near.borshj.annotation.BorshField
import java.math.BigInteger

open class TransferAction(
    @BorshField(order = 1)
    private val depositValue: BigInteger
) : Action

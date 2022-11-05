package com.knear.android.scheme.action

import com.syntifi.near.borshj.annotation.BorshField
import java.math.BigInteger

class FunctionCallAction(
    @BorshField(order = 1)
    private val methodName: String,
    @BorshField(order = 2)
    private val args: String,
    @BorshField(order = 3)
    private val gas: Long,
    @BorshField(order = 4)
    private val depositValue: BigInteger,
) : Action

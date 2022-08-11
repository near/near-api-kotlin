package com.knear.android.scheme.action

import com.knear.android.scheme.PublicKey
import java.math.BigInteger

class StakeAction(
    private var stake: BigInteger,
    private var publicKey: PublicKey
) : Action

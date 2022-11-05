package com.knear.android.scheme.action

import com.syntifi.near.borshj.Borsh
import com.syntifi.near.borshj.annotation.BorshSubTypes

@BorshSubTypes(
    BorshSubTypes.BorshSubType(
        `when` = Action.CREATE_ACCOUNT.toInt(),
        `use` = CreateAccountAction::class
    ),
    BorshSubTypes.BorshSubType(
        `when` = Action.DEPLOY_CONTRACT.toInt(),
        use = DeployContractAction::class
    ),
    BorshSubTypes.BorshSubType(
        `when` = Action.FUNCTION_CALL.toInt(),
        use = FunctionCallAction::class
    ),
    BorshSubTypes.BorshSubType(`when` = Action.STAKE.toInt(), use = StakeAction::class),
    BorshSubTypes.BorshSubType(`when` = Action.ADD_KEY.toInt(), use = AddKeyAction::class),
    BorshSubTypes.BorshSubType(`when` = Action.DELETE_KEY.toInt(), use = DeleteKeyAction::class),
    BorshSubTypes.BorshSubType(
        `when` = Action.DELETE_ACCOUNT.toInt(),
        use = DeleteAccountAction::class
    ),
    BorshSubTypes.BorshSubType(`when` = Action.TRANSFER.toInt(), `use` = TransferAction::class)
)
open interface Action : Borsh {
    companion object {
        const val CREATE_ACCOUNT: Byte = 0
        const val DEPLOY_CONTRACT: Byte = 1
        const val FUNCTION_CALL: Byte = 2
        const val TRANSFER: Byte = 3
        const val STAKE: Byte = 4
        const val ADD_KEY: Byte = 5
        const val DELETE_KEY: Byte = 6
        const val DELETE_ACCOUNT: Byte = 7
    }
}

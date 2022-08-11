package com.knear.android.provider.response.genesisconfig

data class ActionCreationConfig(
    val add_key_cost: AddKeyCost,
    val create_account_cost: CreateAccountCost,
    val delete_account_cost: DeleteAccountCost,
    val delete_key_cost: DeleteKeyCost,
    val deploy_contract_cost: DeployContractCost,
    val deploy_contract_cost_per_byte: DeployContractCostPerByte,
    val function_call_cost: FunctionCallCostX,
    val function_call_cost_per_byte: FunctionCallCostPerByteX,
    val stake_cost: StakeCost,
    val transfer_cost: TransferCost
)

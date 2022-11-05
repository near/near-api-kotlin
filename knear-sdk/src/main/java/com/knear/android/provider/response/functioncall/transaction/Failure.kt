package com.knear.android.provider.response.functioncall.transaction

import com.google.gson.annotations.SerializedName


data class Failure(

    @SerializedName("ActionError") var ActionError: ActionError? = ActionError()

)


package com.knear.android.provider.model

class ViewAccessKeyChangesParams(
    val changes_type: String,
    val keys: List<Keys>,
    val finality: String = "final"
)

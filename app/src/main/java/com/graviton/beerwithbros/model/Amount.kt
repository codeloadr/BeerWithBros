package com.graviton.beerwithbros.model

import com.google.gson.annotations.SerializedName

data class Amount(
    @SerializedName("value") var value: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

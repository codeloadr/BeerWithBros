package com.graviton.beerwithbros.model

import com.google.gson.annotations.SerializedName

data class Volume(
    @SerializedName("value") var value: Int? = null,
    @SerializedName("unit") var unit: String? = null
)

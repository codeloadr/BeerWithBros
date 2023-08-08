package com.graviton.beerwithbros.model

import com.google.gson.annotations.SerializedName

data class MashTemp(
    @SerializedName("temp") var temp: Temp? = Temp(),
    @SerializedName("duration") var duration: Int? = null
)
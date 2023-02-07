package com.architjoshi.nyschools.network.model

import com.google.gson.annotations.SerializedName

data class School(
    @SerializedName("dbn")
    val id: String,
    @SerializedName("school_name")
    val name: String,
    @SerializedName("neighborhood")
    val neighborhood: String,
    @SerializedName("city")
    val city: String
)
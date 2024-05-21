package com.cashir.blackmarketdata.api

data class GroupedWeapon (
    val category: String?,
    val russianName: String,
    val prPrice4_0: Int,
    val bmPrice4_0: Int,
    val prPrice4_1: Int,
    val bmPrice4_1: Int,
    val prPrice5_0: Int,
    val bmPrice5_0: Int,
    val prPrice5_1: Int,
    val bmPrice5_1: Int,
)

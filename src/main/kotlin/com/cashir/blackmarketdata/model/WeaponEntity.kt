package com.cashir.blackmarketdata.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "weapon")
data class WeaponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Enumerated(EnumType.STRING)
    val category: Category?,
    @Enumerated(EnumType.STRING)
    val city: City?,
    val russianName: String?,
    val tier: Int,
    val enchantment: Int,
    val prPrice: Int,
    val bmPrice: Int,
    val albionId: String? = null,
)

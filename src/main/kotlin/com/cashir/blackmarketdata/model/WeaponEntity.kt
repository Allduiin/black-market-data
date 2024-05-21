package com.cashir.blackmarketdata.model

import com.cashir.blackmarketdata.api.AlbionDataItem
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
    val russianName: String,
    val tier: Int,
    val enchantment: Int,
    val prPrice: Int,
    val bmPrice: Int,
    val albionId: String? = null,
) {
    constructor(item: AlbionDataItem) : this(
        category = Category.fromAlbionId(item.itemId),
        russianName = getRussianName(item.itemId),
        tier = getTier(item.itemId),
        enchantment = getEnchantment(item.itemId),
        prPrice =  item.sellPriceMin / 10 * 6,
        bmPrice = item.sellPriceMin,
        albionId = item.itemId
    )

    companion object {
        private fun getEnchantment(itemId: String): Int {
            val enchantmentPattern = "@(\\d+)".toRegex()
            val matchResult = enchantmentPattern.find(itemId)
            return matchResult?.groupValues?.get(1)?.toIntOrNull() ?: 0
        }

        private fun getTier(itemId: String): Int {
            val tierPattern = "T(\\d)".toRegex()
            val matchResult = tierPattern.find(itemId)
            return matchResult?.groupValues?.get(1)?.toIntOrNull() ?: throw IllegalArgumentException("Invalid itemId format")
        }

        private fun getRussianName(itemId: String): String {
            val regex = """T\d_([^@]+)@?\d*""".toRegex()
            return regex.find(itemId)?.groupValues?.get(1) ?: itemId
        }
    }
}

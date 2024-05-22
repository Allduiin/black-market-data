package com.cashir.blackmarketdata.service.weapon

import com.cashir.blackmarketdata.api.GroupedWeapon
import com.cashir.blackmarketdata.model.WeaponEntity
import org.springframework.stereotype.Service

private const val TWO_HANDED = "2H_"
private const val MAIN_HANDED = "MAIN_"
private const val EMPTY = ""

@Service
class WeaponConverter {
    fun convertToGroupedWeapons(weaponEntities: List<WeaponEntity>): List<GroupedWeapon> =
        weaponEntities.groupBy { it.russianName }.map { (name, weapons) ->
            fun getPrice(prices: Map<Pair<Int, Int>, WeaponEntity>, tier: Int, enchantment: Int, isPr: Boolean): Int {
                return prices[tier to enchantment]?.let { if (isPr) it.prPrice else it.bmPrice } ?: 0
            }

            val prices = weapons.associateBy { it.tier to it.enchantment }

            GroupedWeapon(
                city = weapons.first().city?.humanReadableName,
                category = weapons.first().category?.humanReadableName,
                russianName = name,
                prPrice4_0 = getPrice(prices, 4, 0, true),
                bmPrice4_0 = getPrice(prices, 4, 0, false),
                prPrice4_1 = getPrice(prices, 4, 1, true),
                bmPrice4_1 = getPrice(prices, 4, 1, false),
                prPrice5_0 = getPrice(prices, 5, 0, true),
                bmPrice5_0 = getPrice(prices, 5, 0, false),
                prPrice5_1 = getPrice(prices, 5, 1, true),
                bmPrice5_1 = getPrice(prices, 5, 1, false),
                prPrice6_0 = getPrice(prices, 6, 0, true),
                bmPrice6_0 = getPrice(prices, 6, 0, false),
                prPrice6_1 = getPrice(prices, 6, 1, true),
                bmPrice6_1 = getPrice(prices, 6, 1, false)
            )
        }.sortedBy {
            it.category + it.russianName
                .replace(TWO_HANDED, EMPTY)
                .replace(MAIN_HANDED, EMPTY)
        }
}

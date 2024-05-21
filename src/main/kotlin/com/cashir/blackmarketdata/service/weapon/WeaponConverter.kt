package com.cashir.blackmarketdata.service.weapon

import com.cashir.blackmarketdata.api.GroupedWeapon
import com.cashir.blackmarketdata.model.WeaponEntity
import org.springframework.stereotype.Service

@Service
class WeaponConverter {
    fun convertToGroupedWeapons(weaponEntities: List<WeaponEntity>): List<GroupedWeapon> =
        weaponEntities.groupBy { it.russianName }.map { (name, weapons) ->
            fun getPrice(prices: Map<Pair<Int, Int>, WeaponEntity>, tier: Int, enchantment: Int, isPr: Boolean): Int {
                return prices[tier to enchantment]?.let { if (isPr) it.prPrice else it.bmPrice } ?: 0
            }

            val prices = weapons.associateBy { it.tier to it.enchantment }

            GroupedWeapon(
                category = weapons.first().category,
                russianName = name,
                prPrice4_0 = getPrice(prices, 4, 0, true),
                bmPrice4_0 = getPrice(prices, 4, 0, false),
                prPrice4_1 = getPrice(prices, 4, 1, true),
                bmPrice4_1 = getPrice(prices, 4, 1, false),
                prPrice5_0 = getPrice(prices, 5, 0, true),
                bmPrice5_0 = getPrice(prices, 5, 0, false),
                prPrice5_1 = getPrice(prices, 5, 1, true),
                bmPrice5_1 = getPrice(prices, 5, 1, false)
            )
        }
}
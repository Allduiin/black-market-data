package com.cashir.blackmarketdata.service.albiondata

import com.cashir.blackmarketdata.model.City
import org.springframework.stereotype.Service

private const val WEAPONS_SEPARATOR = "%2C"
private const val WEAPONS_POSTFIX = ".json"

@Service
class AlbionWeaponParamsService {
    fun buildAlbionWeaponsUrlParam(city: City): String {
        val weaponsWithTiers = city.weaponCategories.flatMap { it.albionIdList }.flatMap { generateVariations(it) }
        return weaponsWithTiers.joinToString(separator = WEAPONS_SEPARATOR, postfix = WEAPONS_POSTFIX)
    }

    fun generateVariations(weapon: String): List<String> = listOf(
        "T4_$weapon",
        "T4_$weapon@1",
        "T5_$weapon",
        "T5_$weapon@1",
        "T6_$weapon",
        "T6_$weapon@1",
    )
}

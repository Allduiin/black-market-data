package com.cashir.blackmarketdata.service.albiondata

import com.cashir.blackmarketdata.util.BRIDGEWATCH_WEAPONS_MAP
import com.cashir.blackmarketdata.util.FORTSTERLING_WEAPONS_MAP
import com.cashir.blackmarketdata.util.LUMHURST_WEAPONS_MAP
import com.cashir.blackmarketdata.util.MARTLOK_WEAPONS_MAP
import com.cashir.blackmarketdata.util.THETFORD_WEAPONS_MAP
import org.springframework.stereotype.Service

private const val WEAPONS_SEPARATOR = "%2C"
private const val WEAPONS_POSTFIX = ".json"

private const val THETFORD = "Thetford"
private const val FORT_STERLING = "Fort Sterling"
private const val LYMHURST = "Lymhurst"
private const val BRIDGEWATCH = "Bridgewatch"
private const val MARTLOK = "Martlok"

private val CITY_MAP = mapOf(
    THETFORD to THETFORD_WEAPONS_MAP,
    FORT_STERLING to FORTSTERLING_WEAPONS_MAP,
    LYMHURST to LUMHURST_WEAPONS_MAP,
    BRIDGEWATCH to BRIDGEWATCH_WEAPONS_MAP,
    MARTLOK to MARTLOK_WEAPONS_MAP
)

@Service
class AlbionWeaponParamsService {
    fun buildAlbionWeaponsUrlParam(city: String): String {
        if (!CITY_MAP.containsKey(city)) throw RuntimeException("Unexpected city please use only royal cities")
        val weaponsWithTiers = CITY_MAP[city]!!.values.flatMap { it.keys }.flatMap { generateVariations(it) }
        return weaponsWithTiers.joinToString(separator = WEAPONS_SEPARATOR, postfix = WEAPONS_POSTFIX)
    }

    private fun generateVariations(weapon: String): List<String> = listOf(
        "T4_$weapon",
        "T4_$weapon@1",
        "T5_$weapon",
        "T5_$weapon@1",
        "T6_$weapon"
    )
}

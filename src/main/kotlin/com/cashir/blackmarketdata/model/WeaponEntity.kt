package com.cashir.blackmarketdata.model

import com.cashir.blackmarketdata.api.AlbionDataItem
import jakarta.persistence.Entity
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
    val category: String?,
    val russianName: String,
    val tier: Int,
    val enchantment: Int,
    val prPrice: Int,
    val bmPrice: Int,
    val albionId: String? = null,
) {
    constructor(item: AlbionDataItem) : this(
        category = Categories.fromAlbionId(item.itemId)?.name,
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

enum class Categories(val albionIdList: List<String>) {
    MACE(listOf("MAIN_MACE", "2H_MACE", "2H_FLAIL", "MAIN_ROCKMACE_KEEPER", "MAIN_MACE_HELL", "2H_MACE_MORGANA", "2H_DUALMACE_AVALON")),
    NATURESTAFF(listOf("MAIN_NATURESTAFF", "2H_NATURESTAFF", "2H_WILDSTAFF", "MAIN_NATURESTAFF_KEEPER", "2H_NATURESTAFF_HELL", "2H_NATURESTAFF_KEEPER", "MAIN_NATURESTAFF_AVALON")),
    FIRESTAFF(listOf("MAIN_FIRESTAFF", "2H_FIRESTAFF", "2H_INFERNOSTAFF", "MAIN_FIRESTAFF_KEEPER", "2H_FIRESTAFF_HELL", "2H_INFERNOSTAFF_MORGANA", "2H_FIRE_RINGPAIR_AVALON")),
    HAMMER(listOf("MAIN_HAMMER", "2H_POLEHAMMER", "2H_HAMMER", "2H_HAMMER_UNDEAD", "2H_DUALHAMMER_HELL", "2H_RAM_KEEPER", "2H_HAMMER_AVALON")),
    SPEAR(listOf("MAIN_SPEAR", "2H_SPEAR", "2H_GLAIVE", "MAIN_SPEAR_KEEPER", "2H_HARPOON_HELL", "2H_TRIDENT_UNDEAD", "MAIN_SPEAR_LANCE_AVALON", "2H_GLAIVE_CRYSTAL")),
    HOLYSTUFF(listOf("MAIN_HOLYSTAFF", "2H_HOLYSTAFF", "2H_DIVINESTAFF", "MAIN_HOLYSTAFF_MORGANA", "2H_HOLYSTAFF_HELL", "2H_HOLYSTAFF_UNDEAD", "MAIN_HOLYSTAFF_AVALON")),
    BOW(listOf("2H_BOW", "2H_WARBOW", "2H_LONGBOW", "2H_LONGBOW_UNDEAD", "2H_BOW_HELL", "2H_BOW_KEEPER", "2H_BOW_AVALON")),
    SWORD(listOf("MAIN_SWORD", "2H_CLAYMORE", "2H_DUALSWORD", "MAIN_SCIMITAR_MORGANA", "2H_CLEAVER_HELL", "2H_DUALSCIMITAR_UNDEAD", "2H_CLAYMORE_AVALON", "MAIN_SWORD_CRYSTAL")),
    ARCANESTUFF(listOf("MAIN_ARCANESTAFF", "2H_ARCANESTAFF", "2H_ENIGMATICSTAFF", "MAIN_ARCANESTAFF_UNDEAD", "2H_ARCANESTAFF_HELL", "2H_ENIGMATICORB_MORGANA", "2H_ARCANE_RINGPAIR_AVALON", "2H_ARCANESTAFF_CRYSTAL")),
    CROSSBOW(listOf("2H_CROSSBOW", "2H_CROSSBOWLARGE", "MAIN_1HCROSSBOW", "2H_REPEATINGCROSSBOW_UNDEAD", "2H_DUALCROSSBOW_HELL", "2H_CROSSBOWLARGE_MORGANA", "2H_CROSSBOW_CANNON_AVALON")),
    CURSEDSTAFF(listOf("MAIN_CURSEDSTAFF", "2H_CURSEDSTAFF", "2H_DEMONICSTAFF", "MAIN_CURSEDSTAFF_UNDEAD", "2H_SKULLORB_HELL", "2H_CURSEDSTAFF_MORGANA", "MAIN_CURSEDSTAFF_AVALON")),
    DAGGER(listOf("MAIN_DAGGER", "2H_DAGGERPAIR", "2H_CLAWPAIR", "MAIN_RAPIER_MORGANA", "MAIN_DAGGER_HELL", "2H_DUALSICKLE_UNDEAD", "2H_DAGGER_KATAR_AVALON")),
    FROSTSTUFF(listOf("MAIN_FROSTSTAFF", "2H_FROSTSTAFF", "2H_GLACIALSTAFF", "MAIN_FROSTSTAFF_KEEPER", "2H_ICEGAUNTLETS_HELL", "2H_ICECRYSTAL_UNDEAD", "MAIN_FROSTSTAFF_AVALON", "2H_FROSTSTAFF_CRYSTAL")),
    QUARTERSTAFF(listOf("2H_QUARTERSTAFF", "2H_IRONCLADEDSTAFF", "2H_DOUBLEBLADEDSTAFF", "2H_COMBATSTAFF_MORGANA", "2H_TWINSCYTHE_HELL", "2H_ROCKSTAFF_KEEPER", "2H_QUARTERSTAFF_AVALON", "2H_DOUBLEBLADEDSTAFF_CRYSTAL")),
    AXE(listOf("MAIN_AXE", "2H_AXE", "2H_HALBERD", "2H_HALBERD_MORGANA", "2H_SCYTHE_HELL", "2H_DUALAXE_KEEPER", "2H_AXE_AVALON", "2H_SCYTHE_CRYSTAL"));

    companion object {
        fun fromAlbionId(albionId: String): Categories? {
            val regex = """T\d_([^@]+)@?\d*""".toRegex()
            val cleanedId = regex.find(albionId)?.groupValues?.get(1) ?: albionId

            return entries.find { it.albionIdList.contains(cleanedId) }
        }
    }
}

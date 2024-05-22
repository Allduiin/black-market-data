package com.cashir.blackmarketdata.model


private const val THETFORD_NAME = "Thetford"
private const val FORT_STERLING_NAME = "Fort Sterling"
private const val LYMHURST_NAME = "Lymhurst"
private const val BRIDGEWATCH_NAME = "Bridgewatch"
private const val MARTLOK_NAME = "Martlok"

enum class City(
    val humanReadableName: String,
    val weaponCategories: List<Category>
) {
    THETFORD(THETFORD_NAME, listOf(Category.MACE, Category.NATURE_STAFF, Category.FIRE_STAFF)),
    FORT_STERLING(FORT_STERLING_NAME, listOf(Category.HAMMER, Category.SPEAR, Category.HOLY_STUFF)),
    LYMHURST(LYMHURST_NAME, listOf(Category.BOW, Category.SWORD, Category.ARCANE_STUFF)),
    BRIDGEWATCH(BRIDGEWATCH_NAME, listOf(Category.CROSSBOW, Category.CURSED_STAFF, Category.DAGGER)),
    MARTLOK(MARTLOK_NAME, listOf(Category.FROST_STUFF, Category.QUARTER_STAFF, Category.AXE));

    companion object {
        fun fromFormattedName(name: String): City =
            entries.find { it.humanReadableName == name } ?: throw RuntimeException("Unsupported city exception")

        fun fromCategory(category: Category?): City? =
            City.entries.find { it.weaponCategories.contains(category) }
    }
}
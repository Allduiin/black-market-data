package com.cashir.blackmarketdata.service.albiondata

import com.cashir.blackmarketdata.api.AlbionDataItem
import com.cashir.blackmarketdata.model.Category
import com.cashir.blackmarketdata.model.City
import com.cashir.blackmarketdata.model.WeaponEntity
import com.cashir.blackmarketdata.util.RUSSIAN_ID_MAP
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.Response
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AlbionWeaponResponseHandler {
    private val objectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun handleAlbionDataResponse(response: Response): List<WeaponEntity> =
        response.runCatching {
            objectMapper.readValue<List<AlbionDataItem>>(requireNotNull(response.body?.string()))
        }.fold(
            onSuccess = {
                it.map { item ->
                    val category = Category.fromAlbionId(item.itemId)
                    WeaponEntity(
                        category = category,
                        city = City.fromCategory(category),
                        russianName = getRussianName(item.itemId),
                        tier = getTier(item.itemId),
                        enchantment = getEnchantment(item.itemId),
                        prPrice = item.sellPriceMin / 10 * 6,
                        bmPrice = item.sellPriceMin,
                        albionId = item.itemId
                    )
                }
            },
            onFailure = { processError(response, it) }
        )

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

    private fun getRussianName(itemId: String): String? {
        val regex = """T\d_([^@]+)@?\d*""".toRegex()
        val formated = regex.find(itemId)?.groupValues?.get(1)
        return RUSSIAN_ID_MAP[formated]
    }

    private fun processError(response: Response, ex: Throwable): List<WeaponEntity> {
        logger.error(response.body?.string(), ex)
        return emptyList()
    }
}

package com.cashir.blackmarketdata.service.albiondata

import com.cashir.blackmarketdata.api.AlbionDataItem
import com.cashir.blackmarketdata.model.WeaponEntity
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
            onSuccess = { item -> item.map { WeaponEntity(it) } },
            onFailure = { processError(response, it) }
        )

    private fun processError(response: Response, ex: Throwable): List<WeaponEntity> {
        logger.error(response.body?.string(), ex)
        return emptyList()
    }
}

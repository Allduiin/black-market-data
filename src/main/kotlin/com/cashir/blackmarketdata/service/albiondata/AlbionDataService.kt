package com.cashir.blackmarketdata.service.albiondata

import com.cashir.blackmarketdata.model.WeaponEntity
import com.cashir.blackmarketdata.okhttp.AlbionHttpClient
import org.springframework.stereotype.Service

private const val DATA_PROJECT_API_URL = "https://europe.albion-online-data.com"
private const val WEAPON_ENDPOINT = "/api/v2/stats/Prices/"
private const val STANDARD_QUERY_PARAMS = "?locations=Black%20Market&qualities=1"

@Service
class AlbionDataService(
    private val webclientService: AlbionHttpClient,
    private val albionWeaponParamsService: AlbionWeaponParamsService,
    private val albionWeaponResponseHandler: AlbionWeaponResponseHandler,
) {
    fun loadWeapons(city: String): List<WeaponEntity> {
        val urlEncodedWeaponIds = albionWeaponParamsService.buildAlbionWeaponsUrlParam(city)
        val apiUrl = "$DATA_PROJECT_API_URL$WEAPON_ENDPOINT$urlEncodedWeaponIds$STANDARD_QUERY_PARAMS"
        val albionWeaponResponse = webclientService.sendGetWebclientRequest(apiUrl)
        return albionWeaponResponseHandler.handleAlbionDataResponse(albionWeaponResponse)
    }
}

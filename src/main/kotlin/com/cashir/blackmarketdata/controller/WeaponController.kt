package com.cashir.blackmarketdata.controller

import com.cashir.blackmarketdata.model.City
import com.cashir.blackmarketdata.service.weapon.WeaponService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/weapons")
class WeaponController(
    private val weaponService: WeaponService
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/get-all")
    fun getDataFromDb(): String {
        logger.info("Get grouped weapons from db data")
        return weaponService.getGroupedWeapons()
    }

    @GetMapping("/reload-data")
    fun updateData(@RequestParam("city") cityName: String): String {
        logger.info("Reload data from Black Market for $cityName weapons")
        val city: City = City.fromFormattedName(cityName)
        return weaponService.reloadData(city)
    }

    @GetMapping("/reload-all")
    fun updateData(): String {
        logger.info("Reload data from Black Market for all cities")
        City.entries.forEach { updateData(it.humanReadableName) }
        logger.info("Reloaded data from Black Market for all cities")
        return "Reloaded data from Black Market for all cities"
    }
}

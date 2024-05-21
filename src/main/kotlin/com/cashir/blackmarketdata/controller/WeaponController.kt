package com.cashir.blackmarketdata.controller

import com.cashir.blackmarketdata.service.weapon.WeaponService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/weapons")
class WeaponController(
    private val weaponService: WeaponService
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/get-all")
    fun getDataFromDb() {
        logger.info("Get grouped weapons from db data")
        weaponService.getGroupedWeapons()
    }

    @GetMapping("/reload-data")
    fun updateData(city: String): String {
        logger.info("Reload data from albion data project")
        return weaponService.reloadData(city)
    }
}

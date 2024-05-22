package com.cashir.blackmarketdata.service.weapon

import com.cashir.blackmarketdata.model.City
import com.cashir.blackmarketdata.repository.WeaponRepository
import com.cashir.blackmarketdata.service.albiondata.AlbionDataService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service

@Service
class WeaponService(
    private val weaponConverterService: WeaponConverter,
    private val weaponRepository: WeaponRepository,
    private val albionWeaponController: AlbionDataService
) {
    private val objectMapper = jacksonObjectMapper()

    fun getAllGroupedWeapons(): String {
        val weaponDtos = weaponRepository.findAll()
        val groupedWeapons = weaponConverterService.convertToGroupedWeapons(weaponDtos)
        return objectMapper.writeValueAsString(groupedWeapons)
    }

    fun getGroupedWeaponsByCity(city: City): String {
        val weaponDtos = weaponRepository.findByCity(city)
        val groupedWeapons = weaponConverterService.convertToGroupedWeapons(weaponDtos)
        return objectMapper.writeValueAsString(groupedWeapons)
    }

    fun reloadData(city: City): String {
        val reloadedWeapons = albionWeaponController.loadWeapons(city)
        val savedWeapons = weaponRepository.saveAll(reloadedWeapons)
        val groupedWeapons = weaponConverterService.convertToGroupedWeapons(savedWeapons)
        return objectMapper.writeValueAsString(groupedWeapons)
    }
}

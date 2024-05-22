package com.cashir.blackmarketdata.repository

import com.cashir.blackmarketdata.model.WeaponEntity
import org.springframework.data.jpa.repository.JpaRepository

interface WeaponRepository : JpaRepository<WeaponEntity, Long>
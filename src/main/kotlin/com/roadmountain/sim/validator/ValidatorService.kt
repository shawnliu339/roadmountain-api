package com.roadmountain.sim.validator

import com.roadmountain.sim.repository.RegistrationRepository
import org.springframework.stereotype.Service

@Service
class ValidatorService(private val registrationRepository: RegistrationRepository) {
    fun uniqueSimNumber(simNo: String): Boolean {
        return registrationRepository.findBySimNo(simNo) == null
    }
}

package com.roadmountain.sim.validator

import com.roadmountain.sim.repository.OrderIdRepository
import com.roadmountain.sim.repository.RegistrationRepository
import org.springframework.stereotype.Service

@Service
class ValidatorService(
    private val registrationRepository: RegistrationRepository,
    private val orderIdRepository: OrderIdRepository
) {
    fun uniqueSimNumber(simNo: String): Boolean {
        return registrationRepository.findBySimNo(simNo) == null
    }

    fun existingOrderId(orderId: String): Boolean {
        return orderIdRepository.findById(orderId).isPresent
    }

    fun uniqueOrderId(orderId: String): Boolean {
        return registrationRepository.findByOrderId(orderId) == null
    }
}

package com.roadmountain.sim.admin.order

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class AdminOrderController(private val service: AdminOrderService) {
    @PostMapping("/api/admin/orderids")
    fun uploadCsv(@RequestParam("orderCsv") file: MultipartFile) {
        service.saveOrderIdsFromCsv(file.inputStream.reader())

    }
}
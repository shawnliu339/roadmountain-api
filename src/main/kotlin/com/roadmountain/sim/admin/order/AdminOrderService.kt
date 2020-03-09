package com.roadmountain.sim.admin.order

import com.roadmountain.sim.domain.entity.OrderId
import com.roadmountain.sim.repository.OrderIdRepository
import org.apache.commons.csv.CSVFormat
import org.springframework.stereotype.Service
import java.io.InputStreamReader

@Service
class AdminOrderService(
    private val repository: OrderIdRepository
) {
    fun saveOrderIdsFromCsv(orderCsv: InputStreamReader) {
        val records = CSVFormat.POSTGRESQL_TEXT.withFirstRecordAsHeader().parse(orderCsv)
        records.map { it.get("order-id") }
            .map { it.split("-")[2] }
            .forEach { repository.save(OrderId(orderId = it)) }
    }
}
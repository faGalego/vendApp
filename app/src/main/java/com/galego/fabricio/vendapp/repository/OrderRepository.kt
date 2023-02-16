package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.wrapper.MonthWithTotal
import java.util.*

interface OrderRepository {

    suspend fun insertOrder(customerId: Long, total: Double, createdAt: Date): Long

    suspend fun updateOrder(id: Long, customerId: Long, total: Double, createdAt: Date)

    suspend fun deleteOrder(id: Long)

    suspend fun getTotalGroupingByDate(): List<MonthWithTotal?>

    suspend fun getAmountTotalByMonth(month: String): Double?

    suspend fun getCountOrdersByMonth(month: String): Int
}
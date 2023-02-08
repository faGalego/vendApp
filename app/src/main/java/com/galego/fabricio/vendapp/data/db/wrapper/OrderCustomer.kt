package com.galego.fabricio.vendapp.data.db.wrapper

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.galego.fabricio.vendapp.data.db.entity.CustomerEntity
import com.galego.fabricio.vendapp.data.db.entity.OrderEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderCustomer(
    val customerId: Long,
    val customerName: String,
    val orderId: Long,
    val orderTotal: Double
) : Parcelable
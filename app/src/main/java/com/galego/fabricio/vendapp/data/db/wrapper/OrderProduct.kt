package com.galego.fabricio.vendapp.data.db.wrapper

import androidx.room.Embedded
import androidx.room.Relation
import com.galego.fabricio.vendapp.data.db.entity.OrderProductEntity
import com.galego.fabricio.vendapp.data.db.entity.ProductEntity

data class OrderProduct(

    @Embedded var entity: OrderProductEntity? = null,

    @Relation(parentColumn = "productId", entityColumn = "id", entity = ProductEntity::class)
    var product: ProductEntity? = null
)
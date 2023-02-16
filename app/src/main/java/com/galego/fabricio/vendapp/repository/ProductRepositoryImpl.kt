package com.galego.fabricio.vendapp.repository

import com.galego.fabricio.vendapp.data.db.dao.ProductDao
import com.galego.fabricio.vendapp.data.db.entity.ProductEntity
import com.galego.fabricio.vendapp.data.db.wrapper.BestSeller

class ProductRepositoryImpl(
    private val productDao: ProductDao
) : ProductRepository {

    override suspend fun insertProduct(name: String, price: Double): Long {
        val product = ProductEntity(name = name, price = price)
        return productDao.insert(product)
    }

    override suspend fun updateProduct(id: Long, name: String, price: Double) {
        val product = ProductEntity(id = id, name = name, price = price)
        productDao.update(product)
    }

    override suspend fun deleteProduct(id: Long) {
        productDao.delete(id)
    }

    override suspend fun getAllProducts(): List<ProductEntity> {
        return productDao.getAll()
    }

    override suspend fun getProductById(id: Long): ProductEntity? {
        return productDao.getById(id)
    }

    override suspend fun getCountProducts(): Int {
        return productDao.getCountProducts()
    }

    override suspend fun getBestSeller(): BestSeller? {
        return productDao.getBestSeller()
    }
}
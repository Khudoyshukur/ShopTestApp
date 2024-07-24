package uz.safix.shoptestapp.data.repository

import kotlinx.coroutines.flow.Flow
import uz.safix.shoptestapp.data.db.entity.ProductEntity

/**
 * Created by: androdev
 * Date: 23-07-2024
 * Time: 9:22 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

/*
* For simplicity I am not creating mappers. Just using ProductEntity
* */

interface ProductsRepository {
    fun getProducts(query: String? = null): Flow<List<ProductEntity>>
    suspend fun upsertProduct(entity: ProductEntity)
    suspend fun deleteProduct(id: Long)

    fun getProduct(id: Long): Flow<ProductEntity?>
}
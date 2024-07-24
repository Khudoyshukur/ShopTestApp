package uz.safix.shoptestapp.data.repository.impl

import kotlinx.coroutines.flow.Flow
import uz.safix.shoptestapp.data.db.dao.ProductsDao
import uz.safix.shoptestapp.data.db.entity.ProductEntity
import uz.safix.shoptestapp.data.repository.ProductsRepository
import javax.inject.Inject

/**
 * Created by: androdev
 * Date: 23-07-2024
 * Time: 9:49 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

class ProductsRepositoryImpl @Inject constructor(
    private val dao: ProductsDao
): ProductsRepository {
    override fun getProducts(query: String?): Flow<List<ProductEntity>> {
        if (query.isNullOrBlank()) {
            return dao.getAllProducts()
        }

        return dao.getProductsByQuery(query)
    }

    override suspend fun upsertProduct(entity: ProductEntity) {
        dao.upsert(entity)
    }

    override suspend fun deleteProduct(id: Long) {
        dao.deleteProduct(id)
    }

    override fun getProduct(id: Long): Flow<ProductEntity?> {
        return dao.getProduct(id)
    }
}
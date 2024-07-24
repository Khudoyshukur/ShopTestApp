package uz.safix.shoptestapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.safix.shoptestapp.data.db.entity.ProductEntity

/**
 * Created by: androdev
 * Date: 23-07-2024
 * Time: 9:27 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

@Dao
interface ProductsDao {
    @Query("SELECT * FROM item")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM item WHERE name LIKE '%' || :query || '%'")
    fun getProductsByQuery(query: String): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ProductEntity)

    @Query("DELETE FROM item WHERE id=:id")
    suspend fun deleteProduct(id: Long)

    @Query("SELECT * FROM item WHERE id=:id")
    fun getProduct(id: Long): Flow<ProductEntity?>
}
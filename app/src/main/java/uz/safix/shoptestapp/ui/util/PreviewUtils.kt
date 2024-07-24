package uz.safix.shoptestapp.ui.util

import uz.safix.shoptestapp.data.db.entity.ProductEntity
import java.time.LocalDateTime

/**
 * Created by: androdev
 * Date: 24-07-2024
 * Time: 8:50 AM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

object PreviewUtils {
    fun getProduct(): ProductEntity {
        return ProductEntity(
            name = "Test item",
            tags = listOf("Tags"),
            time = LocalDateTime.now(),
            amount = 5
        )
    }

    fun getProducts(size: Int): List<ProductEntity> {
        return List(size) {
            ProductEntity(
                id = it.toLong(),
                name = "Test item $it",
                tags = listOf("Tags $it"),
                time = LocalDateTime.now(),
                amount = it + 2
            )
        }
    }
}
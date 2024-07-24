package uz.safix.shoptestapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * Created by: androdev
 * Date: 23-07-2024
 * Time: 9:23 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

@Entity(tableName = "item")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("time") val time: LocalDateTime,
    @ColumnInfo("tags") val tags: List<String>,
    @ColumnInfo("amount") val amount: Int
)
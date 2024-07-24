package uz.safix.shoptestapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.safix.shoptestapp.data.AppTypeConverter
import uz.safix.shoptestapp.data.db.dao.ProductsDao
import uz.safix.shoptestapp.data.db.entity.ProductEntity

/**
 * Created by: androdev
 * Date: 23-07-2024
 * Time: 9:26 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

private const val DATABASE_NAME = "products1.db"

@Database(
    entities = [ProductEntity::class],
    version = 1
)
@TypeConverters(AppTypeConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract val productsDao: ProductsDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .createFromAsset("database/data.db")
                    //.fallbackToDestructiveMigration()
                    .build()
            }

            return instance!!
        }
    }
}
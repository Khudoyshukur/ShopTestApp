package uz.safix.shoptestapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.safix.shoptestapp.data.db.AppDatabase
import uz.safix.shoptestapp.data.db.dao.ProductsDao
import uz.safix.shoptestapp.data.repository.ProductsRepository
import uz.safix.shoptestapp.data.repository.impl.ProductsRepositoryImpl
import javax.inject.Singleton

/**
 * Created by: androdev
 * Date: 23-07-2024
 * Time: 9:45 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideProductsDao(appDatabase: AppDatabase): ProductsDao {
        return appDatabase.productsDao
    }

    @Provides
    @Singleton
    fun bindProductsRepo(dao: ProductsDao): ProductsRepository {
        return ProductsRepositoryImpl(dao)
    }
}
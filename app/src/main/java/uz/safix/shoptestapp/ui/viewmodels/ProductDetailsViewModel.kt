package uz.safix.shoptestapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.safix.shoptestapp.data.repository.ProductsRepository
import uz.safix.shoptestapp.ui.screens.AppScreen
import javax.inject.Inject

/**
 * Created by: androdev
 * Date: 24-07-2024
 * Time: 10:09 AM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val productId = savedStateHandle.get<String>(AppScreen.ARG_PRODUCT_ID)!!.toLong()
    val productStream get() = productsRepository.getProduct(productId)
}
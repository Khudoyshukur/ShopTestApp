package uz.safix.shoptestapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import uz.safix.shoptestapp.data.repository.ProductsRepository
import javax.inject.Inject

/**
 * Created by: androdev
 * Date: 23-07-2024
 * Time: 9:57 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
): ViewModel() {
    private val _queryStream = MutableStateFlow("")

    val queryStream get() = _queryStream.asStateFlow()
    val productsStream get() = queryStream.flatMapLatest { productsRepository.getProducts(it) }

    fun deleteProduct(id: Long) = viewModelScope.launch { productsRepository.deleteProduct(id) }
    fun updateQuery(query: String) = viewModelScope.launch { _queryStream.emit(query) }
}
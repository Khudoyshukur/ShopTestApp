package uz.safix.shoptestapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import uz.safix.shoptestapp.data.db.entity.ProductEntity
import uz.safix.shoptestapp.data.repository.ProductsRepository
import uz.safix.shoptestapp.ui.screens.AppScreen
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by: androdev
 * Date: 24-07-2024
 * Time: 10:50 AM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

@HiltViewModel
class UpsertProductViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val productIdToEdit = savedStateHandle.get<String>(AppScreen.ARG_PRODUCT_ID)?.toLong()
    val upsertMode by lazy {
        if (productIdToEdit == null) UpsertMode.Insert else UpsertMode.Update(productIdToEdit)
    }

    private val _nameStream = MutableStateFlow("")
    val nameStream get() = _nameStream.asStateFlow()

    private val _amountStream = MutableStateFlow(0)
    val amountStream get() = _amountStream.asStateFlow()

    private val _datetimeStream = MutableStateFlow(LocalDateTime.now())
    val datetimeStream get() = _datetimeStream.asStateFlow()

    private val _tagsStream = MutableStateFlow<List<String>>(emptyList())
    val tagsStream get() = _tagsStream.asStateFlow()

    val isActionEnabledStream
        get() = if (upsertMode is UpsertMode.Update) {
            MutableStateFlow(true)
        } else {
            combine(
                nameStream, tagsStream
            ) { name, tags -> name.isNotBlank() && tags.isNotEmpty() }
        }

    private val _actionedStream = MutableStateFlow(false)
    val actionedStream get() = _actionedStream.asStateFlow()

    init {
        productIdToEdit?.let{ setProductDetails(it) }
    }

    private fun setProductDetails(productId: Long) = viewModelScope.launch {
        val product = productsRepository.getProduct(productId).firstOrNull() ?: return@launch
        _nameStream.emit(product.name)
        _amountStream.emit(product.amount)
        _datetimeStream.emit(product.time)
        _tagsStream.emit(product.tags)
    }

    fun addTag(tag: String) = viewModelScope.launch {
        _tagsStream.emit(tagsStream.value + listOf(tag))
    }

    fun removeTag(tag: String) = viewModelScope.launch {
        _tagsStream.emit(tagsStream.value.filter { it != tag })
    }

    fun changeName(name: String) = viewModelScope.launch {
        _nameStream.emit(name)
    }

    fun changeAmount(amount: Int) = viewModelScope.launch {
        _amountStream.emit(amount)
    }

    fun performAction() = viewModelScope.launch {
        if (nameStream.value.isBlank() || tagsStream.value.isEmpty()) return@launch

        val product = ProductEntity(
            id = productIdToEdit ?: 0L,
            name = nameStream.value,
            time = datetimeStream.value,
            tags = tagsStream.value,
            amount = amountStream.value
        )
        productsRepository.upsertProduct(product)
        _actionedStream.emit(true)
    }
}

sealed interface UpsertMode {
    data object Insert : UpsertMode
    data class Update(val productId: Long) : UpsertMode
}

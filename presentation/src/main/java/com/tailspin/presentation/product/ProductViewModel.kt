package com.tailspin.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tailspin.domain.model.Item
import com.tailspin.domain.usecase.GetItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val products = searchQuery.flatMapLatest { query ->
        getItemUseCase.searchItem(query)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(), emptyList()
    )

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    fun editItemAmount(item: Item, newAmount: Int) {
        viewModelScope.launch {
            getItemUseCase.editAmountItems(item, newAmount)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            getItemUseCase.deleteItem(item)
        }
    }

}
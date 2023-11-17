package com.caesar.laptopapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caesar.laptopapp.data.LaptopRepository
import com.caesar.laptopapp.model.Laptop
import com.caesar.laptopapp.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel (private val repository: LaptopRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Laptop>>(UiState.Loading)
    val uiState: StateFlow<UiState<Laptop>>
        get() = _uiState

    fun getLaptop(LaptopId: Int) {
        viewModelScope.launch {
            repository.getLaptop(LaptopId)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { laptop ->
                    _uiState.value = UiState.Success(laptop)
                }
        }
    }
}
package com.caesar.laptopapp.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caesar.laptopapp.data.LaptopRepository
import com.caesar.laptopapp.model.Laptop
import com.caesar.laptopapp.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val laptopRepository: LaptopRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Laptop>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Laptop>>>
        get() = _uiState
    private val _query = mutableStateOf("")
    val query: String get() = _query.value

    fun getLaptop() {
        viewModelScope.launch {
            laptopRepository.getLaptop()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { laptop ->
                    _uiState.value = UiState.Success(laptop)
                }
        }
    }

    fun searchLaptop(query: String) {
        _query.value = query
        viewModelScope.launch {
            laptopRepository.searchLaptop(query)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { laptop ->
                    _uiState.value = UiState.Success(laptop)
                }
        }
    }
}
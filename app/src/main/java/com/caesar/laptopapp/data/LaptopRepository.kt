package com.caesar.laptopapp.data

import com.caesar.laptopapp.model.DataLaptop
import com.caesar.laptopapp.model.Laptop
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class LaptopRepository {
    private val laptop = mutableListOf<Laptop>()

    init {
        if (laptop.isEmpty()) {
            laptop.addAll(DataLaptop.laptopStore)
        }
    }

    fun getLaptop(): Flow<List<Laptop>> {
        return flowOf(laptop)
    }

    fun getLaptop(id: Int): Flow<Laptop> {
        return flowOf(laptop.first { it.id == id })
    }

    fun searchLaptop(query: String): Flow<List<Laptop>> {
        return flowOf(laptop.filter { it.name.contains(query, true) })
    }

    companion object {
        @Volatile
        private var instance: LaptopRepository? = null

        fun getInstance(): LaptopRepository =
            instance ?: synchronized(this) {
                LaptopRepository().apply {
                    instance = this
                }
            }
    }
}
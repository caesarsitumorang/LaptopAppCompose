package com.caesar.laptopapp.data

object Injection {
    fun provideRepository(): LaptopRepository {
        return LaptopRepository.getInstance()
    }
}
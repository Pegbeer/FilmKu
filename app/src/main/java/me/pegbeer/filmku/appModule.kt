package me.pegbeer.filmku

import androidx.room.Room
import me.pegbeer.filmku.local.Database
import me.pegbeer.filmku.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeViewModel)
}
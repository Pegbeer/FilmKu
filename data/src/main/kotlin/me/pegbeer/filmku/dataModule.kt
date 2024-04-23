package me.pegbeer.filmku

import androidx.room.Room
import me.pegbeer.filmku.local.Database
import me.pegbeer.filmku.local.DatabaseService
import me.pegbeer.filmku.local.LocalDataService
import me.pegbeer.filmku.remote.ApiService
import me.pegbeer.filmku.remote.AuthenticationInterceptor
import me.pegbeer.filmku.remote.NetworkService
import me.pegbeer.filmku.remote.RemoteDataService
import me.pegbeer.filmku.repository.Repository
import me.pegbeer.filmku.repository.RepositoryImpl
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val baseUrl = "https://api.themoviedb.org"

val dataModule = module {

    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthenticationInterceptor())
            .build()
    }

    single<ApiService> {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(ApiService::class.java)
    }

    single<Database> { Room.databaseBuilder(get(), Database::class.java,"local").build() }

    single<RemoteDataService> { NetworkService(get()) }

    single<LocalDataService> { DatabaseService(get()) }

    single<Repository> { RepositoryImpl(get(),get()) }
}
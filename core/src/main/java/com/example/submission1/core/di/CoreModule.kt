  package com.example.submission1.core.di

import androidx.room.Room
import com.example.submission1.core.BuildConfig.DEBUG
import com.example.submission1.core.data.local.room.FavoriteAnimeDatabase
import com.example.submission1.core.data.remote.RemoteDataSource
import com.example.submission1.core.data.remote.network.ApiService
import com.example.submission1.core.domain.repository.IAnimeRepository
import com.example.submission1.core.utils.NetworkInfo.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<FavoriteAnimeDatabase>().favoriteAnimeDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            FavoriteAnimeDatabase::class.java,
            "FavoriteAnime.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val loggingInterceptor =
            if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(loggingInterceptor))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { com.example.submission1.core.data.local.LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IAnimeRepository> { com.example.submission1.core.data.AnimeRepository(get(), get()) }
}
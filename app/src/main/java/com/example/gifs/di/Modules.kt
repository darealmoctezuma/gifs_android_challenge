package com.example.gifs.di

import com.example.gifs.BuildConfig
import com.example.gifs.data.source.GifsRepository
import com.example.gifs.data.source.remote.GifsService
import com.example.gifs.domain.GetGifsImpl
import com.example.gifs.ui.grid.GridViewModel
import com.example.gifs.data.source.GifsRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY_VALUE = "api_key"

val gridViewModelModule = module {
    viewModel { GridViewModel(get()) }
}

val repositoryModule = module {
    single<GifsRepository> {
        GifsRepositoryImpl(get())
    }
}

val useCasesModule = module {
    single { GetGifsImpl(get()) }
}

val apiModule = module {
    fun provideGifsService(retrofit: Retrofit): GifsService =
        retrofit.create(GifsService::class.java)

    single { provideGifsService(get()) }
}

val networkModule = module {
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()

    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()

    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
}

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val url =
            req.url().newBuilder().addQueryParameter(API_KEY_VALUE, BuildConfig.GIPHY_API_KEY)
                .build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}

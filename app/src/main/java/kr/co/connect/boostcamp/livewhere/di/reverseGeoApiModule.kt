package kr.co.connect.boostcamp.livewhere.di

import io.reactivex.schedulers.Schedulers
import kr.co.connect.boostcamp.livewhere.BuildConfig
import kr.co.connect.boostcamp.livewhere.api.ReverseGeoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val reverseGeoApiModule = module {
    single("reverseApi") {
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .readTimeout(3, TimeUnit.SECONDS)
                    .writeTimeout(3, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor())//http log 확인
                    .build()
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.KakaoLocalUrl)
            .build()
            .create(ReverseGeoApi::class.java)
    }
}
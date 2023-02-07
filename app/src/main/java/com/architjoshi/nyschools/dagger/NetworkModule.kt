package com.architjoshi.nyschools.dagger

import com.architjoshi.nyschools.common.Constants
import com.architjoshi.nyschools.network.api.NYSchoolsAPIService
import com.architjoshi.nyschools.repository.NYCSchoolRepositoryImpl
import com.architjoshi.nyschools.repository.NYSchoolsRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun providesNYSchoolsService(): NYSchoolsAPIService {
        val gson = Gson()
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val okHttpClientBuilder = OkHttpClient.Builder()
            //.addInterceptor(httpLoggingInterceptor)

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClientBuilder.build())
            .baseUrl(Constants.BASE_URL)
            .build()

        return retrofit.create(NYSchoolsAPIService::class.java)
    }

    @Provides
    fun providesNYSchoolsRepository(nySchoolsAPIService: NYSchoolsAPIService): NYSchoolsRepository {
        return NYCSchoolRepositoryImpl(nySchoolsAPIService)
    }
}
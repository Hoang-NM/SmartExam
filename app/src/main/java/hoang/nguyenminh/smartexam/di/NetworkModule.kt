package hoang.nguyenminh.smartexam.di

import hoang.nguyenminh.smartexam.BuildConfig
import hoang.nguyenminh.smartexam.network.SmartExamLocalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoang.nguyenminh.smartexam.network.SmartExamCloudService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.github.com/")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideCloudService(retrofit: Retrofit): SmartExamCloudService =
        retrofit.create(SmartExamCloudService::class.java)

    @Provides
    @Singleton
    fun provideLocalService(retrofit: Retrofit): SmartExamLocalService =
        retrofit.create(SmartExamLocalService::class.java)
}
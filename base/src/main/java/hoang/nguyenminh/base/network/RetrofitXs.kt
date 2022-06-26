package hoang.nguyenminh.base.network

import android.app.Application
import com.google.gson.GsonBuilder
import hoang.nguyenminh.base.appInstance
import hoang.nguyenminh.base.isDebugMode
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitXs {

    private const val READ_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L
    private const val CONNECT_TIMEOUT = 30L
    private const val CACHE_SIZE = (10 * 1024 * 1024).toLong()

    fun httpClient(logLevel: HttpLoggingInterceptor.Level? = null): OkHttpClient =
        OkHttpClient.Builder().apply {
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            cache(defaultCache(appInstance())).interceptors()
                .add(defaultLogger(appInstance(), logLevel))
        }.build()

    fun newInstance(baseUrl: String, logLevel: HttpLoggingInterceptor.Level? = null): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(httpClient(logLevel))
            addCallAdapterFactory(defaultCallFactory())
            addConverterFactory(defaultGsonConverter())
        }.build()

    fun defaultCallFactory(): CallAdapter.Factory = RxJava3CallAdapterFactory.create()

    fun defaultGsonConverter(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create())

    fun defaultCache(application: Application) = Cache(application.cacheDir, CACHE_SIZE)

    fun defaultLogger(
        application: Application,
        logLevel: HttpLoggingInterceptor.Level? = null
    ): Interceptor =
        HttpLoggingInterceptor(logger = {
            val length = it.length
            if (length > 51200 /*50 kB*/)
                Platform.get().log(
                    level = Platform.WARN,
                    message = "Log omitted cause tldr; $length in bytes"
                )
            else Platform.get().log(it)
        }).apply {
            level = logLevel ?: if (application.isDebugMode()) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
}
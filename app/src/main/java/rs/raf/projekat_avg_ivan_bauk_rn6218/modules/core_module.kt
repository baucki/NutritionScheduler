package rs.raf.projekat_avg_ivan_bauk_rn6218.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rs.raf.projekat_avg_ivan_bauk_rn6218.BuildConfig
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.local.DataBase
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.interceptors.ApiKeyInterceptor
import java.util.*
import java.util.concurrent.TimeUnit

val coreModule = module {

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(androidApplication().packageName, Context.MODE_PRIVATE)
    }

    single { Room.databaseBuilder(androidContext(), DataBase::class.java, "DataBase")
        .fallbackToDestructiveMigration()
        .build()
    }

    single(named("mealApi")) { createRetrofit(moshi = get(), httpClient = createOkHttpClient(false), baseUrl = "https://www.themealdb.com/api/json/v1/1/") }
    single(named("nutritionApi")) { createRetrofit(moshi = get(), httpClient = createOkHttpClient(true), baseUrl = "https://api.api-ninjas.com/v1/") }

    single { createMoshi() }

}

fun createMoshi(): Moshi {
    return Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()
}

fun createRetrofit(moshi: Moshi,
                   httpClient: OkHttpClient,
                   baseUrl: String
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .client(httpClient)
        .build()
}

fun createOkHttpClient(needsInterceptor: Boolean): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.readTimeout(60, TimeUnit.SECONDS)
    httpClient.connectTimeout(60, TimeUnit.SECONDS)
    httpClient.writeTimeout(60, TimeUnit.SECONDS)

    if (needsInterceptor) {
        httpClient.addInterceptor(ApiKeyInterceptor("sGp71owOKOw09nHoLiepgsiWpKAMesfnejNkptcJ"))
    }


    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
    }

    return httpClient.build()
}

inline fun <reified T> create(retrofit: Retrofit): T  {
    return retrofit.create(T::class.java)
}


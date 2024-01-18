package rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("x-api-key", apiKey)
            .build()
        return chain.proceed(request)
    }
}
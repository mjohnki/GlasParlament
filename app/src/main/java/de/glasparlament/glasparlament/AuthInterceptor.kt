package de.glasparlament.glasparlament

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {

    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.GBWAnKUlungIEx9xm9byaVl5Gni_X0VMOXp28fIBkUI"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest: Request

        newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        return chain.proceed(newRequest)
    }
}

package git.immutabled.sulf.spigot.discord.auth

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(private val token: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bot $token")
            .build()
        return chain.proceed(request)
    }
}
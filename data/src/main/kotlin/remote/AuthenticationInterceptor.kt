package remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .addHeader("Authorization","bearer $ACCESS_TOKEN")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object{
        private const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2OGIzNTM4ZDQxZDljMzk5M2U3NTM4ZTBmNmNiY2RkNSIsInN1YiI6IjYzMWY3NGQyMGI3MzE2MDA4YTEzYTFiZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.p9BLze-dkmv6yKj4lG80PnbKwrIsPaw15A-NSXdfN0Q"
    }

}
package leopardcat.studio.odkodk.data.api.client

import leopardcat.studio.odkodk.data.api.service.KakaoService
import leopardcat.studio.odkodk.data.api.service.LibSrchService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class KakaoClient {
    companion object {
        private const val KAKAO_URL = "https://dapi.kakao.com/v3/search/"

        private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        fun getKakaoBook(): KakaoService = Retrofit.Builder()
            .baseUrl(KAKAO_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KakaoService::class.java)
    }
}
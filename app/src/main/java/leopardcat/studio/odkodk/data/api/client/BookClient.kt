package leopardcat.studio.odkodk.data.api.client

import leopardcat.studio.odkodk.data.api.service.LibBookService
import leopardcat.studio.odkodk.data.api.service.LibSrchService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BookClient {
    companion object {
        private const val BOOK_URL = "https://data4library.kr/api/"

        private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        fun getBookLibSrch(): LibSrchService = Retrofit.Builder()
            .baseUrl(BOOK_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibSrchService::class.java)

        private val libraryOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        fun getLibraryBookSrch(): LibBookService = Retrofit.Builder()
            .baseUrl(BOOK_URL)
            .client(libraryOkHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibBookService::class.java)
    }
}
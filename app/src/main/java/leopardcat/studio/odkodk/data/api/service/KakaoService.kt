package leopardcat.studio.odkodk.data.api.service

import io.reactivex.Single
import leopardcat.studio.odkodk.data.api.model.BookModel
import leopardcat.studio.odkodk.domain.KakaoApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoService {
    @GET("book")
    fun getKakaoSrch(
        @Header("Authorization") authorization: String,
        @Query("query") query: String
    ): Single<KakaoApiResponse>
}
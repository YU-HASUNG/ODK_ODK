package leopardcat.studio.odkodk.data.api.service

import io.reactivex.Single
import leopardcat.studio.odkodk.data.api.model.BookModel
import retrofit2.http.GET
import retrofit2.http.Query

interface LibSrchService {
    @GET("libSrch")
    fun getLibSrch(
        @Query("authKey") authKey: String,
        @Query("dtl_region") dtl_region: Int,
        @Query("format") format: String
    ): Single<BookModel>
}
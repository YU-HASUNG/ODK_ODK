package leopardcat.studio.odkodk.data.api.service

import io.reactivex.Single
import leopardcat.studio.odkodk.data.api.model.BookModel
import leopardcat.studio.odkodk.data.api.model.LibraryBookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LibBookService {
    @GET("bookExist")
    fun getLibBook(
        @Query("authKey") authKey: String,
        @Query("libCode") libCode: Int,
        @Query("isbn13") isbn13: String,
        @Query("format") format: String
    ): Single<LibraryBookResponse>
}
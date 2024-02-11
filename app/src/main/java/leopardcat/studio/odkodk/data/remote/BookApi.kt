package leopardcat.studio.odkodk.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    @GET("loanItemSrch")
    suspend fun getBooks(
        @Query("authKey") authKey: String,
        @Query("startDt") startDt: String,
        @Query("endDt") endDt: String,
        @Query("gender") gender: String?,
        @Query("age") age: String?,
        @Query("pageNo") page: Int,
        @Query("pageSize") pageCount: Int,
        @Query("format") format: String
    ): BookApiDto

    companion object {
        const val BASE_URL = "http://data4library.kr/api/"
    }
}
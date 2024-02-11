package leopardcat.studio.odkodk.data.api.model

import com.google.gson.annotations.SerializedName

data class BookModel(
    @SerializedName("response") val response: Response
)

data class Response(
    @SerializedName("request") val request: Request,
    @SerializedName("pageNo") val pageNo: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("numFound") val numFound: Int,
    @SerializedName("resultNum") val resultNum: Int,
    @SerializedName("libs") val libs: List<Library>
)

data class Request(
    @SerializedName("pageNo") val pageNo: String,
    @SerializedName("pageSize") val pageSize: String
)

data class Library(
    @SerializedName("lib") val lib: Lib
)

data class Lib(
    @SerializedName("libCode") val libCode: String?,
    @SerializedName("libName") val libName: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("tel") val tel: String?,
    @SerializedName("fax") val fax: String?,
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("closed") val closed: String?,
    @SerializedName("operatingTime") val operatingTime: String?,
    @SerializedName("BookCount") val bookCount: String?
)
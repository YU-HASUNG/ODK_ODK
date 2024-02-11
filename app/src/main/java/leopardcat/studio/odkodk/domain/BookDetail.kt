package leopardcat.studio.odkodk.domain

import com.google.gson.annotations.SerializedName

data class BookDetail (
    val bookname: String?,
    val authors: String?,
    val publisher: String?,
    val isbn13: String?,
    val bookImageURL: String?
)

data class KyoboProduct(
    val productId: String?
)

data class KakaoApiResponse(
    @SerializedName("documents")
    val documents: List<KakaoBook>?,
    @SerializedName("meta")
    val meta: Meta?
)

data class KakaoBook(
    @SerializedName("authors")
    val authors: List<String>?,
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("datetime")
    val datetime: String?,
    @SerializedName("isbn")
    val isbn: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("sale_price")
    val salePrice: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("translators")
    val translators: List<String>?,
    @SerializedName("url")
    val url: String?
)

data class Meta(
    @SerializedName("is_end")
    val isEnd: Boolean,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("total_count")
    val totalCount: Int
)
package leopardcat.studio.odkodk.data.remote

data class BookApiDto(
    val response: BookResponse
)

data class BookResponse(
    val docs: List<BookDto>
)

data class BookDto(
    val doc: BookDetail
)

data class BookDetail(
    val no: Int,
    val ranking: String,
    val bookname: String,
    val authors: String,
    val publisher: String,
    val publication_year: String,
    val isbn13: String,
    val addition_symbol: String,
    val vol: String,
    val class_no: String,
    val class_nm: String,
    val loan_count: String,
    val bookImageURL: String,
    val bookDtlUrl: String
)
package leopardcat.studio.odkodk.domain

data class Book(
    val no: Int,
    val ranking: String,
    val bookname: String,
    val authors: String,
    val publisher: String,
    val publicationYear: String,
    val isbn13: String,
    val additionSymbol: String,
    val vol: String,
    val classNo: String,
    val classNm: String,
    val loanCount: String,
    val bookImageURL: String,
    val bookDtlUrl: String
)
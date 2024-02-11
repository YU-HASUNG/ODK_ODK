package leopardcat.studio.odkodk.data.api.model

data class LibraryBookResponse(
    val response: LibraryResponse
)

data class LibraryResponse(
    val request: LibraryRequest,
    val result: LibraryResult
)

data class LibraryRequest(
    val isbn13: String,
    val libCode: String
)

data class LibraryResult(
    val hasBook: String?,
    val loanAvailable: String?
)
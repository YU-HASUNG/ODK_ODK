package leopardcat.studio.odkodk.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey
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
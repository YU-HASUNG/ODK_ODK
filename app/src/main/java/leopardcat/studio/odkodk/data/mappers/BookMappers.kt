package leopardcat.studio.odkodk.data.mappers

import leopardcat.studio.odkodk.data.local.BookEntity
import leopardcat.studio.odkodk.data.remote.BookDto
import leopardcat.studio.odkodk.domain.Book

fun BookDto.toBookEntity(): BookEntity {
    return BookEntity(
        no = doc.no,
        ranking = doc.ranking,
        bookname = doc.bookname,
        authors = doc.authors,
        publisher = doc.publisher,
        publicationYear = doc.publication_year,
        isbn13 = doc.isbn13,
        additionSymbol = doc.addition_symbol,
        vol = doc.vol,
        classNo = doc.class_no,
        classNm = doc.class_nm,
        loanCount = doc.loan_count,
        bookImageURL = doc.bookImageURL,
        bookDtlUrl = doc.bookDtlUrl
    )
}

fun BookEntity.toBook(): Book {
    return Book(
        no = no,
        ranking = ranking,
        bookname = bookname,
        authors = authors,
        publisher = publisher,
        publicationYear = publicationYear,
        isbn13 = isbn13,
        additionSymbol = additionSymbol,
        vol = vol,
        classNo = classNo,
        classNm = classNm,
        loanCount = loanCount,
        bookImageURL = bookImageURL,
        bookDtlUrl = bookDtlUrl
    )
}
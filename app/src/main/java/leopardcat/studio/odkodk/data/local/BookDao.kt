package leopardcat.studio.odkodk.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BookDao {

    @Upsert
    suspend fun upsertAll(books: List<BookEntity>)

    @Query("SELECT * FROM bookentity")
    fun pagingSource(): PagingSource<Int, BookEntity>

    @Query("DELETE FROM bookentity")
    suspend fun clearAll()
}
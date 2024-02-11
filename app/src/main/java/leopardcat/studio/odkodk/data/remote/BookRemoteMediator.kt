package leopardcat.studio.odkodk.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import leopardcat.studio.odkodk.data.local.BookDatabase
import leopardcat.studio.odkodk.data.local.BookEntity
import leopardcat.studio.odkodk.data.mappers.toBookEntity
import leopardcat.studio.odkodk.viewmodel.MainViewModel
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator(
    private val bookDb: BookDatabase,
    private val bookApi: BookApi,
    private val date: String,
    private val gender: String?,
    private val age: String?
): RemoteMediator<Int, BookEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        1
                    } else {
                        (lastItem.no / state.config.pageSize) + 1
                    }
                }
            }

            if (state.pages.size >= loadKey) {
                // 이미 로드된 페이지인 경우 API 호출을 하지 않음
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            val books = bookApi.getBooks(
                authKey = "~~~",
                startDt = date,
                endDt = date,
                gender = gender,
                age = age,
                page = loadKey,
                pageCount = state.config.pageSize,
                format = "json"
            )
            Log.d("BookAPI", "API 호출 성공. 응답: ${books.response}")

            if(books.response.docs == null) {
                Log.d("BookAPI", "API 응답에 오류가 있습니다.")
//                return MediatorResult.Error(IOException("일일 사용량이 초과되었어요\n 내일 다시 이용해주세요"))
                return MediatorResult.Error(IOException("아직 책을 대출한 사람이 없어요\n"))
            }

            bookDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    bookDb.dao.clearAll()
                }
                val bookEntities = books.response.docs.map { it.toBookEntity() }
                bookDb.dao.upsertAll(bookEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = books.response.docs.isEmpty(),
            )
        } catch(e: IOException) {
            Log.d("BookAPI", "API 호출 실패. 응답: ${e}")
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            Log.d("BookAPI", "API 호출 실패. 응답: ${e}")
            MediatorResult.Error(e)
        }
    }
}
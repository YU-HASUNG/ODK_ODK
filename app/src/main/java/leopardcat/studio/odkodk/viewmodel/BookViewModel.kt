package leopardcat.studio.odkodk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import leopardcat.studio.odkodk.data.local.BookEntity
import leopardcat.studio.odkodk.data.mappers.toBook
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    pager: Pager<Int, BookEntity>
): ViewModel() {

    val bookPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toBook() }
        }
        .cachedIn(viewModelScope)
}
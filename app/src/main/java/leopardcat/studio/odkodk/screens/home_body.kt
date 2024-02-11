package leopardcat.studio.odkodk.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import leopardcat.studio.odkodk.domain.Book
import leopardcat.studio.odkodk.ui.item.BookItem
import leopardcat.studio.odkodk.ui.theme.PinkMain
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@Composable
fun HomeBody(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    books: LazyPagingItems<Book>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(books) { book ->
            if(book != null) {
                BookItem(
                    navHostController,
                    mainViewModel,
                    book = book
                )
            }
        }
        item {
            if(books.loadState.append is LoadState.Loading) {
                CircularProgressIndicator(color = PinkMain)
            }
        }
    }
}
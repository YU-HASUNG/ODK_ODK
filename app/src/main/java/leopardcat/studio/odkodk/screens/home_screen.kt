package leopardcat.studio.odkodk.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import leopardcat.studio.odkodk.domain.Book
import leopardcat.studio.odkodk.ui.shimmer.BookListShimmer
import leopardcat.studio.odkodk.ui.theme.BlackMain
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    books: LazyPagingItems<Book>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = books.loadState) {
        if(books.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (books.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
        .fillMaxSize()
        .background(BlackMain)) {
        HomeHead(navHostController, mainViewModel)
        if(books.loadState.refresh is LoadState.Loading) {
            BookListShimmer()
        } else {
            HomeBody(navHostController, mainViewModel, books)
        }
    }
}
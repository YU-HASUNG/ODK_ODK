package leopardcat.studio.odkodk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import leopardcat.studio.odkodk.navigation.MainNavigation
import leopardcat.studio.odkodk.ui.theme.OdkodkTheme
import leopardcat.studio.odkodk.viewmodel.BookViewModel
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OdkodkTheme {
                val mainViewModel = hiltViewModel<MainViewModel>()
                mainViewModel.setPreference(this)
                mainViewModel.setDate()
                mainViewModel.checkWifi(this)
                mainViewModel.setCoverAds(this)

                val viewModel = hiltViewModel<BookViewModel>()
                val books = viewModel.bookPagingFlow.collectAsLazyPagingItems()
                val items = remember { mutableStateListOf<String>() }
                MainNavigation(mainViewModel = mainViewModel, activity = this, books = books, items = items)
            }
        }
    }
}
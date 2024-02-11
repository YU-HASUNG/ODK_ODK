package leopardcat.studio.odkodk.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import leopardcat.studio.odkodk.domain.Book
import leopardcat.studio.odkodk.screens.DetailScreen
import leopardcat.studio.odkodk.screens.HomeScreen
import leopardcat.studio.odkodk.screens.SearchScreen
import leopardcat.studio.odkodk.screens.StartScreen
import leopardcat.studio.odkodk.screens.UserScreen
import leopardcat.studio.odkodk.screens.UserScreen0
import leopardcat.studio.odkodk.screens.UserScreen1
import leopardcat.studio.odkodk.screens.UserScreen2
import leopardcat.studio.odkodk.screens.WebViewScreen
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@Composable
fun MainNavigation(
    mainViewModel: MainViewModel,
    activity: Activity,
    books: LazyPagingItems<Book>,
    items: SnapshotStateList<String>
) {
    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = START_SCREEN) {
        composable(START_SCREEN) {
            StartScreen(navHostController, mainViewModel)
        }
        composable(HOME_SCREEN) {
            HomeScreen(navHostController, mainViewModel, books)
        }
        composable(SEARCH_SCREEN) {
            SearchScreen(navHostController, mainViewModel, items)
        }
        composable(DETAIL_SCREEN) {
            DetailScreen(navHostController, mainViewModel)
        }
        composable(WEBVIEW_SCREEN) {
            WebViewScreen(navHostController, mainViewModel, activity)
        }

        //USERSCREEN
        composable(USER_SCREEN) {
            UserScreen(navHostController, mainViewModel)
        }
        composable(USER_SCREEN0) {
            UserScreen0(navHostController, mainViewModel)
        }
        composable(USER_SCREEN1) {
            UserScreen1(navHostController, mainViewModel)
        }
        composable(USER_SCREEN2) {
            UserScreen2(navHostController, mainViewModel)
        }
    }
}

const val START_SCREEN = "Start screen"
const val HOME_SCREEN = "Home screen"
const val SEARCH_SCREEN = "Search screen"
const val DETAIL_SCREEN = "Detail screen"
const val WEBVIEW_SCREEN = "WebView screen"

const val USER_SCREEN = "User screen"
const val USER_SCREEN0 = "User screen0"
const val USER_SCREEN1 = "User screen1"
const val USER_SCREEN2 = "User screen2"
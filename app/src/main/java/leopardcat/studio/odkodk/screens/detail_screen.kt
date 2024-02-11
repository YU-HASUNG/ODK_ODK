package leopardcat.studio.odkodk.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import leopardcat.studio.odkodk.ui.theme.BlackMain
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(BlackMain)) {
        DetailHead(navHostController)
        DetailBody(navHostController, mainViewModel)
    }
}
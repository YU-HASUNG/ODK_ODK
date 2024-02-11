package leopardcat.studio.odkodk.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import leopardcat.studio.odkodk.R
import leopardcat.studio.odkodk.navigation.USER_SCREEN0
import leopardcat.studio.odkodk.ui.theme.BlackMain
import leopardcat.studio.odkodk.ui.theme.DarkGrayMain
import leopardcat.studio.odkodk.ui.theme.Obang
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@Composable
fun UserScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxSize()
    ) {
        IntroArea(navHostController, mainViewModel)
    }
}

@Composable
fun IntroArea(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    if(
        (mainViewModel.getPreference()?.getAge("age") == 8888)
        && (mainViewModel.getPreference()?.getSex("sex") == 8888)
        && (mainViewModel.getPreference()?.getLibrary("library") == 8888)
    ) {
        Text(
            text = stringResource(R.string.us_intro_title0),
            textAlign = TextAlign.Center,
            fontFamily = Obang,
            fontSize = 30.sp,
            color = BlackMain,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.us_intro_title1),
            textAlign = TextAlign.Center,
            color = DarkGrayMain,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.us_intro_sub_title1),
            textAlign = TextAlign.Center,
            color = DarkGrayMain,
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        IntroButton(navHostController, stringResource(id = R.string.us_action0))
    } else {
        Text(
            text = stringResource(R.string.us_intro_title2),
            textAlign = TextAlign.Center,
            fontFamily = Obang,
            fontSize = 30.sp,
            color = BlackMain,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.us_intro_title3),
            textAlign = TextAlign.Center,
            color = DarkGrayMain,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.us_intro_sub_title1),
            textAlign = TextAlign.Center,
            color = DarkGrayMain,
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        IntroButton(navHostController, stringResource(id = R.string.us_action1))
    }
}

@Composable
fun IntroButton(
    navHostController: NavHostController,
    action: String
) {
    val onClickAction: () -> Unit = {
        navHostController.navigate(USER_SCREEN0)
    }

    AnimatedVisibility(
        visible = true,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onClickAction,
            colors = ButtonDefaults.buttonColors (
                containerColor = BlackMain,
                contentColor = Color.White
            )
        ) {
            Text(
                text = action,
                color = Color.White
            )
        }
    }
}
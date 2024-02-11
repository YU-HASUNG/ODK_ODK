package leopardcat.studio.odkodk.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import leopardcat.studio.odkodk.R
import leopardcat.studio.odkodk.navigation.USER_SCREEN2
import leopardcat.studio.odkodk.ui.theme.BlackMain
import leopardcat.studio.odkodk.ui.theme.DarkGrayMain
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@Composable
fun UserScreen1(
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
        SexArea(navHostController, mainViewModel)
    }
}


@Composable
fun SexArea(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    Text(
        text = stringResource(R.string.us_sex_title),
        textAlign = TextAlign.Center,
        fontSize = 25.sp,
        color = BlackMain,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(R.string.us_sex_sub_title),
        textAlign = TextAlign.Center,
        color = DarkGrayMain,
        fontSize = 15.sp,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    SexButton(navHostController, mainViewModel, "남자")
    SexButton(navHostController, mainViewModel, "여자")
    SexButton(navHostController, mainViewModel, "다음에 선택할게요")
}

@Composable
fun SexButton(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    sex: String
) {
    val onClickAction: () -> Unit = {
        when(sex) {
            "남자" -> mainViewModel.setSexPref(0)
            "여자" -> mainViewModel.setSexPref(1)
            else -> mainViewModel.setSexPref(9999)
        }
        navHostController.navigate(USER_SCREEN2)
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
                text = sex,
                color = Color.White
            )
        }
    }
}
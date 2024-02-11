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
import leopardcat.studio.odkodk.navigation.USER_SCREEN1
import leopardcat.studio.odkodk.ui.theme.BlackMain
import leopardcat.studio.odkodk.ui.theme.DarkGrayMain
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@Composable
fun UserScreen0(
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
        AgeArea(navHostController, mainViewModel)
    }
}


@Composable
fun AgeArea(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    Text(
        text = stringResource(R.string.us_age_title),
        textAlign = TextAlign.Center,
        fontSize = 25.sp,
        color = BlackMain,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(R.string.us_age_sub_title),
        textAlign = TextAlign.Center,
        color = DarkGrayMain,
        fontSize = 15.sp,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    for (age in 10..60 step 10) {
        AgeButton(navHostController, mainViewModel, age)
    }
    AgeButton(navHostController, mainViewModel, 9999)
}

@Composable
fun AgeButton(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    age: Int
) {
    val onClickAction: () -> Unit = {
        mainViewModel.setAgePref(age)
        navHostController.navigate(USER_SCREEN1)
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
            if(age >= 9999){
                Text(
                    text = "다음에 선택할게요",
                    color = Color.White
                )
            } else if(age >= 60) {
                Text(
                    text = "$age 대 이상",
                    color = Color.White
                )
            } else {
                Text(
                    text = "$age 대",
                    color = Color.White
                )
            }
        }
    }
}
package leopardcat.studio.odkodk.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import leopardcat.studio.odkodk.R
import leopardcat.studio.odkodk.navigation.HOME_SCREEN
import leopardcat.studio.odkodk.navigation.USER_SCREEN
import leopardcat.studio.odkodk.navigation.USER_SCREEN0
import leopardcat.studio.odkodk.ui.ButtonComponent
import leopardcat.studio.odkodk.ui.theme.BlackMain
import leopardcat.studio.odkodk.ui.theme.GrayMain
import leopardcat.studio.odkodk.ui.theme.Obang
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@Composable
fun StartScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {
    val bookComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.start_screen_book_lottie)) //book lottie 버튼 상태

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 20.dp, end = 20.dp)
                .weight(0.5f)
                .clip(CircleShape)
                .clickable {
                    navHostController.navigate(USER_SCREEN0)
                },
//            contentAlignment = Alignment.CenterEnd,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_setting),
                contentDescription = "",
                tint = BlackMain,
                modifier = Modifier.size(30.dp)
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(5f)
        ) {
            LottieAnimation(
                composition = bookComposition,
                modifier = Modifier.fillMaxWidth(),
                iterations = Int.MAX_VALUE
            )
        }

        Text(
            text = "오독오독",
            color = BlackMain,
            fontFamily = Obang,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
        )

        Text(
            text = stringResource(R.string.ss_loading),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            color = BlackMain,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f),
        )

        ButtonComponent(
            modifier = Modifier
                .padding(20.dp)
                .height(60.dp)
                .weight(1f),
        ) {
            if(mainViewModel.getPreference()?.getFinish("finish") == true){
                navHostController.navigate(HOME_SCREEN)
            } else {
                navHostController.navigate(USER_SCREEN)
            }
        }

        Text(
            text = stringResource(R.string.ss_donation),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = GrayMain,
            fontWeight = FontWeight.Thin,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f),
        )
    }
}
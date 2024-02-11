package leopardcat.studio.odkodk.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import leopardcat.studio.odkodk.R
import leopardcat.studio.odkodk.navigation.SEARCH_SCREEN
import leopardcat.studio.odkodk.ui.theme.Obang
import leopardcat.studio.odkodk.ui.theme.PinkMain
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@Composable
fun HomeHead(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 10.dp, end = 30.dp)
                .clickable {
                    navHostController.navigate(SEARCH_SCREEN)
                },
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
        Text(
            text = "오늘의 인기도서",
            color = Color.White,
            fontFamily = Obang,
            fontSize = 30.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        val age = if((mainViewModel.getPreference()?.getAge("age") == 9999) || (mainViewModel.getPreference()?.getAge("age") == 8888)){
            ""
        } else {
            if((mainViewModel.getPreference()?.getAge("age") != null) && (mainViewModel.getPreference()?.getAge("age")!! >= 60)){
                "#" + mainViewModel.getPreference()?.getAge("age").toString() + "대 이상"
            } else {
                "#" + mainViewModel.getPreference()?.getAge("age").toString() + "대"
            }
        }

        val sex = if(mainViewModel.getPreference()?.getSex("sex") == 0){
            "#남성"
        } else if (mainViewModel.getPreference()?.getSex("sex") == 1){
            "#여성"
        } else {
            ""
        }

        Text(
            text = "#${mainViewModel.getPreference()?.getDate("date")}  $age  $sex",
            color = PinkMain,
            fontSize = 15.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, bottom = 14.dp)
        )
    }
}
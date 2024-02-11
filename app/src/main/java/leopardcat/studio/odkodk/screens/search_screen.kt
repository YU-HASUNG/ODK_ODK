package leopardcat.studio.odkodk.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import leopardcat.studio.odkodk.R
import leopardcat.studio.odkodk.domain.KakaoApiResponse
import leopardcat.studio.odkodk.ui.item.KakaoBookItem
import leopardcat.studio.odkodk.ui.theme.BlackMain
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    items: SnapshotStateList<String>,
) {
    val kakaoList by mainViewModel.kakaoList.observeAsState(initial = KakaoApiResponse(null, null))
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    var noItem by remember { mutableStateOf(false) }

    fun performSearch(searchText: String) {
        if(searchText.trim() == "" && searchText.isNotEmpty()){
            return
        }
        text = searchText
        mainViewModel.getKakaoBookList(searchText)
        active = false // 검색 모드 종료
        noItem = true // 검색 결과 없음 결과값 해제
        if (!items.contains(searchText)){
            items.add(searchText) // 아이템 목록에 추가
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackMain)
            .padding(start = 30.dp, top = 30.dp, bottom = 30.dp, end = 30.dp)
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            query = text,
            onQueryChange = {
                text = it
            },
            onSearch = {
                performSearch(text)
            },
            active = active,
            onActiveChange ={
                active = it
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.ss_search),
                    color = Color.Black
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = BlackMain
                )
            },
            trailingIcon = {
                if(active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if(text.isNotEmpty()){
                                text = ""
                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = BlackMain
                    )
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = Color.White,
                dividerColor = BlackMain,
                inputFieldColors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = BlackMain,
                    unfocusedTextColor = BlackMain,
                    cursorColor = BlackMain // Set the cursor color to Black
                )
            )
        ) {
            items.forEach{
                Row(modifier = Modifier
                    .padding(all = 14.dp)
                    .clickable {
                        performSearch(it)
                    }
                ){
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_history),
                        contentDescription = "History Icon",
                        tint = BlackMain
                    )
                    Text(text = it)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))

        if(kakaoList.documents == null) { //초기 상태
//            if(kakaoList.meta?.pageableCount =< 0) {
////                BookListShimmer()
//            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(kakaoList.documents!!.size) {
                    KakaoBookItem(
                        navHostController,
                        mainViewModel,
                        book = kakaoList.documents!![it]
                    )
                }
            }
        }
    }
}
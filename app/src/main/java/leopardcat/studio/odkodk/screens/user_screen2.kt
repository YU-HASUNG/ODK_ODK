package leopardcat.studio.odkodk.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import leopardcat.studio.odkodk.domain.LibraryRegion
import leopardcat.studio.odkodk.ui.theme.BlackMain
import leopardcat.studio.odkodk.ui.theme.DarkGrayMain
import leopardcat.studio.odkodk.ui.theme.PinkMain
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun UserScreen2(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
) {
    val region by mainViewModel.libraryRegion.observeAsState(initial = LibraryRegion(null, null, null))
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxSize()
    ) {
        RegionArea(navHostController, mainViewModel, region)
    }

    BackHandler {
        mainViewModel.setLibraryRegion(LibraryRegion(null, null, null))
        navHostController.popBackStack()
    }
}


@Composable
fun RegionArea(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    region: LibraryRegion,
) {
    val context = LocalContext.current
    if((region.small != null) && (region.library != null)){
        //도서관 선택
        Text(
            text = stringResource(R.string.us_library_title),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            color = BlackMain,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.us_library_sub_title),
            textAlign = TextAlign.Center,
            color = DarkGrayMain,
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        //지역 선택
        Text(
            text = stringResource(R.string.us_region_title),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            color = BlackMain,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.us_region_sub_title),
            textAlign = TextAlign.Center,
            color = DarkGrayMain,
            fontSize = 15.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }


    Spacer(modifier = Modifier.height(16.dp))

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.White)
    ) {
        if(region.big == null){
            //지역 선택
            item {
                Region.values().forEach { region ->
                    RegionButton(navHostController, mainViewModel, region.name)
                }
            }
        } else if ((region.small != null) && (region.library != null)) {
            //도서관 선택
            item {
                if(region.library.isEmpty()) {
                    Toast.makeText(context, "현재 해당 지역에 연계되는 도서관이 없어요", Toast.LENGTH_LONG).show()
                    SubRegionButton(navHostController, mainViewModel,"error", "다음에 선택할게요", 9999)
                } else {
                    region.library.forEach { library ->
                        LibraryButton(navHostController, mainViewModel, library.lib.libName, library.lib.libCode)
                    }
                }
            }
        } else {
            //API 조회 실패 경우
            if((region.small != null)) {
                Toast.makeText(context, "도서관 조회에 실패했습니다.\n다시 시도해 주세요", Toast.LENGTH_LONG).show()
                item {
                    SubRegionButton(navHostController, mainViewModel, "error", "다음에 선택할게요", 9999)
                }
            }
            //상세 지역 선택
            when(region.big) {
                Region.서울.toString() -> {
                    item {
                        // 서울 지역의 세부지역명과 해당 숫자값을 매핑한 데이터
                        seoulSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.부산.toString() -> {
                    item {
                        busanSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.대구.toString() -> {
                    item {
                        daeguSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.인천.toString() -> {
                    item {
                        incheonSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.광주.toString() -> {
                    item {
                        gwangjuSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.대전.toString() -> {
                    item {
                        daejeonSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.울산.toString() -> {
                    item {
                        ulsanSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.세종.toString() -> {
                    item {
                        sejongSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.경기.toString() -> {
                    item {
                        gyeonggiSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.강원.toString() -> {
                    item {
                        gangwonSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.충북.toString() -> {
                    item {
                        chungbukSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.충남.toString() -> {
                    item {
                        chungnamSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.전북.toString() -> {
                    item {
                        jeonbukSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.전남.toString() -> {
                    item {
                        jeonnamSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.경북.toString() -> {
                    item {
                        gyeongbukSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.경남.toString() -> {
                    item {
                        gyeongnamSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
                Region.제주.toString() -> {
                    item {
                        jejuSubregions.forEach { (subregion, subregionCode) ->
                            SubRegionButton(navHostController, mainViewModel, region.big, subregion, subregionCode)
                        }
                    }
                }
            }
        }
    }
}

//지역 버튼
@Composable
fun RegionButton(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    region: String
) {
    val context = LocalContext.current
    val onClickAction: () -> Unit = {
        mainViewModel.setLibraryRegion(LibraryRegion(region, null, null))
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
                text = region,
                color = Color.White
            )
        }
    }
}

//세부지역 버튼
@Composable
fun SubRegionButton(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    region: String,
    subregion: String,
    subregionCode: Int
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    val onClickAction: () -> Unit = {
        if(subregionCode == 9999){
            mainViewModel.setLibraryPref(9999)
            mainViewModel.setLibraryNamePref("")
            moveToHomeScreen(navHostController, mainViewModel, context)
        } else {
            isLoading = true
            mainViewModel.gptLibSrch(LibraryRegion(region, subregionCode, null), subregionCode) //도서관 조회 api
        }
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
            if(isLoading){
                CircularProgressIndicator(
                    color = PinkMain,
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(
                    text = subregion,
                    color = Color.White
                )
            }
        }
    }
}

//도서관 버튼
@Composable
fun LibraryButton(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    library: String?,
    libraryCode: String?
) {
    val context = LocalContext.current
    val onClickAction: () -> Unit = {
        libraryCode?.toInt()?.let { mainViewModel.setLibraryPref(it) }
        library?.let { mainViewModel.setLibraryNamePref(it) }
        moveToHomeScreen(navHostController, mainViewModel, context)
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
            if (library != null) {
                Text(
                    text = library,
                    color = Color.White
                )
            }
        }
    }
}

fun moveToHomeScreen(navHostController: NavHostController, mainViewModel: MainViewModel, context: Context) {
    Toast.makeText(context, "설문을 완료하셨어요!", Toast.LENGTH_LONG).show()
    mainViewModel.setUserFinishPref(true)
    mainViewModel.setLibraryRegion(LibraryRegion(null, null, null)) //정보 초기화
    mainViewModel.restartApp(context)
//    val navOptions = NavOptions.Builder()
//        .setPopUpTo(START_SCREEN, inclusive = false) // 시작 화면 이전까지 스택에서 제거
//        .build()
//    navHostController.navigate(HOME_SCREEN, navOptions)

//    (context as Activity).finish()
//    val intent = Intent(context, MainActivity::class.java)
//    context.startActivity(intent)
}

enum class Region {
    서울,
    부산,
    대구,
    인천,
    광주,
    대전,
    울산,
    세종,
    경기,
    강원,
    충북,
    충남,
    전북,
    전남,
    경북,
    경남,
    제주
}

val seoulSubregions = mapOf(
    "종로구" to 11010,
    "중구" to 11020,
    "용산구" to 11030,
    "성동구" to 11040,
    "광진구" to 11050,
    "동대문구" to 11060,
    "중랑구" to 11070,
    "성북구" to 11080,
    "강북구" to 11090,
    "도봉구" to 11100,
    "노원구" to 11110,
    "은평구" to 11120,
    "서대문구" to 11130,
    "마포구" to 11140,
    "양천구" to 11150,
    "강서구" to 11160,
    "구로구" to 11170,
    "금천구" to 11180,
    "영등포구" to 11190,
    "동작구" to 11200,
    "관악구" to 11210,
    "서초구" to 11220,
    "강남구" to 11230,
    "송파구" to 11240,
    "강동구" to 11250
)

val busanSubregions = mapOf(
    "중구" to 21010,
    "서구" to 21020,
    "동구" to 21030,
    "영도구" to 21040,
    "부산진구" to 21050,
    "동래구" to 21060,
    "남구" to 21070,
    "북구" to 21080,
    "해운대구" to 21090,
    "사하구" to 21100,
    "금정구" to 21110,
    "강서구" to 21120,
    "연제구" to 21130,
    "수영구" to 21140,
    "사상구" to 21150,
    "기장군" to 21310
)

val daeguSubregions = mapOf(
    "중구" to 22010,
    "동구" to 22020,
    "서구" to 22030,
    "남구" to 22040,
    "북구" to 22050,
    "수성구" to 22060,
    "달서구" to 22070,
    "달성군" to 22310
)

val incheonSubregions = mapOf(
    "중구" to 23010,
    "동구" to 23020,
    "남구" to 23030,
    "연수구" to 23040,
    "남동구" to 23050,
    "부평구" to 23060,
    "계양구" to 23070,
    "서구" to 23080,
    "강화군" to 23310,
    "옹진군" to 23320
)

val gwangjuSubregions = mapOf(
    "동구" to 24010,
    "서구" to 24020,
    "남구" to 24030,
    "북구" to 24040,
    "광산구" to 24050
)

val daejeonSubregions = mapOf(
    "동구" to 25010,
    "중구" to 25020,
    "서구" to 25030,
    "유성구" to 25040,
    "대덕구" to 25050
)

val ulsanSubregions = mapOf(
    "중구" to 26010,
    "남구" to 26020,
    "동구" to 26030,
    "북구" to 26040,
    "울주군" to 26310
)

val sejongSubregions = mapOf(
    "세종시" to 29010
)

val gyeonggiSubregions = mapOf(
    "수원시" to 31010,
    "수원시 장안구" to 31011,
    "수원시 권선구" to 31012,
    "수원시 팔달구" to 31013,
    "수원시 영통구" to 31014,
    "성남시" to 31020,
    "성남시 수정구" to 31021,
    "성남시 중원구" to 31022,
    "성남시 분당구" to 31023,
    "의정부시" to 31030,
    "안양시" to 31040,
    "안양시 만안구" to 31041,
    "안양시 동안구" to 31042,
    "부천시" to 31050,
    "광명시" to 31060,
    "평택시" to 31070,
    "동두천시" to 31080,
    "안산시" to 31090,
    "안산시 상록구" to 31091,
    "안산시 단원구" to 31092,
    "고양시" to 31100,
    "고양시 덕양구" to 31101,
    "고양시 일산동구" to 31103,
    "고양시 일산서구" to 31104,
    "과천시" to 31110,
    "구리시" to 31120,
    "남양주시" to 31130,
    "오산시" to 31140,
    "시흥시" to 31150,
    "군포시" to 31160,
    "의왕시" to 31170,
    "하남시" to 31180,
    "용인시" to 31190,
    "용인시 처인구" to 31191,
    "용인시 기흥구" to 31192,
    "용인시 수지구" to 31193,
    "파주시" to 31200,
    "이천시" to 31210,
    "안성시" to 31220,
    "김포시" to 31230,
    "화성시" to 31240,
    "광주시" to 31250,
    "양주시" to 31260,
    "포천시" to 31270,
    "여주시" to 31280,
    "연천군" to 31350,
    "가평군" to 31370,
    "양평군" to 31380
)

val gangwonSubregions = mapOf(
    "춘천시" to 32010,
    "원주시" to 32020,
    "강릉시" to 32030,
    "동해시" to 32040,
    "태백시" to 32050,
    "속초시" to 32060,
    "삼척시" to 32070,
    "홍천군" to 32310,
    "횡성군" to 32320,
    "영월군" to 32330,
    "평창군" to 32340,
    "정선군" to 32350,
    "철원군" to 32360,
    "화천군" to 32370,
    "양구군" to 32380,
    "인제군" to 32390,
    "고성군" to 32400,
    "양양군" to 32410
)

val chungbukSubregions = mapOf(
    "충주시" to 33020,
    "제천시" to 33030,
    "청주시" to 33040,
    "청주시 상당구" to 33041,
    "청주시 서원구" to 33042,
    "청주시 흥덕구" to 33043,
    "청주시 청원구" to 33044,
    "보은군" to 33320,
    "옥천군" to 33330,
    "영동군" to 33340,
    "진천군" to 33350,
    "괴산군" to 33360,
    "음성군" to 33370,
    "단양군" to 33380,
    "증평군" to 33390
)

val chungnamSubregions = mapOf(
    "천안시" to 34010,
    "천안시 동남구" to 34011,
    "천안시 서북구" to 34012,
    "공주시" to 34020,
    "보령시" to 34030,
    "아산시" to 34040,
    "서산시" to 34050,
    "논산시" to 34060,
    "계룡시" to 34070,
    "당진시" to 34080,
    "금산군" to 34310,
    "부여군" to 34330,
    "서천군" to 34340,
    "청양군" to 34350,
    "홍성군" to 34360,
    "예산군" to 34370,
    "태안군" to 34380
)

val jeonbukSubregions = mapOf(
    "전주시" to 35010,
    "전주시 완산구" to 35011,
    "전주시 덕진구" to 35012,
    "군산시" to 35020,
    "익산시" to 35030,
    "정읍시" to 35040,
    "남원시" to 35050,
    "김제시" to 35060,
    "완주군" to 35310,
    "진안군" to 35320,
    "무주군" to 35330,
    "장수군" to 35340,
    "임실군" to 35350,
    "순창군" to 35360,
    "고창군" to 35370,
    "부안군" to 35380
)

val jeonnamSubregions = mapOf(
    "목포시" to 36010,
    "여수시" to 36020,
    "순천시" to 36030,
    "나주시" to 36040,
    "광양시" to 36060,
    "담양군" to 36310,
    "곡성군" to 36320,
    "구례군" to 36330,
    "고흥군" to 36350,
    "보성군" to 36360,
    "화순군" to 36370,
    "장흥군" to 36380,
    "강진군" to 36390,
    "해남군" to 36400,
    "영암군" to 36410,
    "무안군" to 36420,
    "함평군" to 36430,
    "영광군" to 36440,
    "장성군" to 36450,
    "완도군" to 36460,
    "진도군" to 36470,
    "신안군" to 36480
)

val gyeongbukSubregions = mapOf(
    "포항시" to 37010,
    "포항시 남구" to 37011,
    "포항시 북구" to 37012,
    "경주시" to 37020,
    "김천시" to 37030,
    "안동시" to 37040,
    "구미시" to 37050,
    "영주시" to 37060,
    "영천시" to 37070,
    "상주시" to 37080,
    "문경시" to 37090,
    "경산시" to 37100,
    "군위군" to 37310,
    "의성군" to 37320,
    "청송군" to 37330,
    "영양군" to 37340,
    "영덕군" to 37350,
    "청도군" to 37360,
    "고령군" to 37370,
    "성주군" to 37380,
    "칠곡군" to 37390,
    "예천군" to 37400,
    "봉화군" to 37410,
    "울진군" to 37420,
    "울릉군" to 37430
)

val gyeongnamSubregions = mapOf(
    "진주시" to 38030,
    "통영시" to 38050,
    "사천시" to 38060,
    "김해시" to 38070,
    "밀양시" to 38080,
    "거제시" to 38090,
    "양산시" to 38100,
    "창원시" to 38110,
    "창원시 의창구" to 38111,
    "창원시 성산구" to 38112,
    "창원시 마산합포구" to 38113,
    "창원시 마산회원구" to 38114,
    "창원시 진해구" to 38115,
    "의령군" to 38310,
    "함안군" to 38320,
    "창녕군" to 38330,
    "고성군" to 38340,
    "남해군" to 38350,
    "하동군" to 38360,
    "산청군" to 38370,
    "함양군" to 38380,
    "거창군" to 38390,
    "합천군" to 38400
)

val jejuSubregions = mapOf(
    "제주시" to 39010,
    "서귀포시" to 39020
)
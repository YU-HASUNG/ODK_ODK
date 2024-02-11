package leopardcat.studio.odkodk.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import leopardcat.studio.odkodk.viewmodel.MainViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import leopardcat.studio.odkodk.R
import leopardcat.studio.odkodk.data.api.model.LibraryResult
import leopardcat.studio.odkodk.domain.BookDetail
import leopardcat.studio.odkodk.domain.KyoboProduct
import leopardcat.studio.odkodk.navigation.WEBVIEW_SCREEN
import leopardcat.studio.odkodk.ui.KyoboButtonComponent
import leopardcat.studio.odkodk.ui.theme.BlackGrayMain
import leopardcat.studio.odkodk.ui.theme.BlackMain
import leopardcat.studio.odkodk.ui.theme.DarkGrayMain
import leopardcat.studio.odkodk.ui.theme.GrayMain
import leopardcat.studio.odkodk.ui.theme.Obang
import leopardcat.studio.odkodk.ui.theme.PinkMain
import leopardcat.studio.odkodk.util.getYesterday
import leopardcat.studio.odkodk.util.kyoboProductUrl

@Composable
fun DetailBody(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    //observe
    val book by mainViewModel.bookDetail.observeAsState(initial = BookDetail(null, null, null, null, null))
    val productId by mainViewModel.kyoboProduct.observeAsState(initial = KyoboProduct(null))
    val library by mainViewModel.libraryBook.observeAsState(initial = LibraryResult(null, null))

    //lottie
    val greenDot by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.green_dot_lottie)) //book lottie 버튼 상태
    val redDot by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.red_dot_lottie)) //book lottie 버튼 상태

    mainViewModel.getKyoboSearch(book.isbn13.toString())
    mainViewModel.getLibraryBook(book.isbn13.toString())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(BlackMain)
            .padding(start = 30.dp, end = 30.dp)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .weight(8f),
        ) {
            item {
                DBHeader(navHostController, mainViewModel, book, productId)

                Box(modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .height(0.5.dp)
                )

                if(mainViewModel.getPreference()?.getLibrary("library") != 9999 || mainViewModel.getPreference()?.getLibrary("library") != 8888 || library.hasBook != "9999" || library.hasBook != "8888"){
                    DBBody(mainViewModel, greenDot, redDot, library)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .weight(1f), // 원하는 만큼의 여백을 추가
            contentAlignment = Alignment.BottomCenter
        ) {
            DBBottom(productId, book)
        }
    }
}

@Composable
private fun DBHeader(navHostController: NavHostController, mainViewModel: MainViewModel, book: BookDetail, productId: KyoboProduct) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(BlackMain)
            .padding(start = 30.dp, end = 30.dp, bottom = 15.dp)
    ) {
        AsyncImage(
            model = book.bookImageURL,
            contentDescription = book.bookname,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(173.dp)
                .height(260.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(modifier = Modifier.height(20.dp))
        book.bookname?.let {
            Text(
                text = it,
                color = Color.White,
                fontSize = 30.sp,
                maxLines = 3,
                fontFamily = Obang,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        book.authors?.let {
            Text(
                text = it,
                color = GrayMain,
                fontSize = 16.sp,
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        book.publisher?.let {
            Text(
                text = it,
                color = GrayMain,
                fontSize = 16.sp,
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        if((productId.productId == null) || (productId.productId != "9999")){
            if(productId.productId == "S000208779631"){
                if(book.isbn13 != "9791192300818" || book.isbn13 != "1192300815") {
                    //쇼펜하우어 예외처리
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .border(1.dp, GrayMain, CircleShape)
                            .padding(start = 8.dp, top = 5.dp, bottom = 5.dp, end = 8.dp)
                            .clickable {
                                if(productId.productId != null){
                                    mainViewModel.getPreference()?.setProduct("product", productId.productId)
                                    navHostController.navigate(WEBVIEW_SCREEN)
                                }
                            }
                    ) {
                        if(productId.productId == null){
                            CircularProgressIndicator(
                                color = PinkMain,
                                strokeWidth = 1.dp,
                                modifier = Modifier.size(14.dp)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "미리보기",
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .border(1.dp, GrayMain, CircleShape)
                        .padding(start = 8.dp, top = 5.dp, bottom = 5.dp, end = 8.dp)
                        .clickable {
                            if(productId.productId != null){
                                mainViewModel.getPreference()?.setProduct("product", productId.productId)
                                navHostController.navigate(WEBVIEW_SCREEN)
                            }
                        }
                ) {
                    if(productId.productId == null){
                        CircularProgressIndicator(
                            color = PinkMain,
                            strokeWidth = 1.dp,
                            modifier = Modifier.size(14.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "미리보기",
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DBBody(
    mainViewModel: MainViewModel,
    greenDot: LottieComposition?,
    redDot: LottieComposition?,
    library: LibraryResult
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(BlackMain)
            .padding(start = 30.dp, end = 30.dp, top = 15.dp)
    ) {
        Text(
            text = mainViewModel.getPreference()!!.getLibraryName("name"),
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "${getYesterday()} 기준",
            color = PinkMain,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(BlackGrayMain)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 7.dp, bottom = 5.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "도서",
                        color = GrayMain,
                        fontSize = 15.sp,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when(library.hasBook) {
                            null -> {
                                CircularProgressIndicator(
                                    color = PinkMain,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            "Y" -> {
                                Text(
                                    text = "소장",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                LottieAnimation(
                                    composition = greenDot,
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(30.dp),
                                    iterations = Int.MAX_VALUE
                                )
                            }
                            "N" -> {
                                Text(
                                    text = "미소장",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                LottieAnimation(
                                    composition = redDot,
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(30.dp),
                                    iterations = Int.MAX_VALUE
                                )
                            }
                        }
                    }
                }
                Text(
                    text = "|",
                    color = DarkGrayMain,
                    fontSize = 15.sp,
                )
                Column(
                    modifier = Modifier
                        .padding(top = 7.dp, bottom = 5.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "대출",
                        color = GrayMain,
                        fontSize = 15.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when(library.loanAvailable) {
                            null -> {
                                CircularProgressIndicator(
                                    color = PinkMain,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            "Y" -> {
                                Text(
                                    text = "가능",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                LottieAnimation(
                                    composition = greenDot,
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(30.dp),
                                    iterations = Int.MAX_VALUE
                                )
                            }
                            "N" -> {
                                Text(
                                    text = "불가",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                LottieAnimation(
                                    composition = redDot,
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(30.dp),
                                    iterations = Int.MAX_VALUE
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DBBottom(productId: KyoboProduct, book: BookDetail) {
    val context = LocalContext.current
    if((productId.productId == null) || (productId.productId != "9999")) {
        if(productId.productId == "S000208779631"){
            if(book.isbn13 != "9791192300818" || book.isbn13 != "1192300815") {
                //쇼펜하우어 예외처리
            } else {
                KyoboButtonComponent(
                    modifier = Modifier.height(60.dp),
                    product = productId
                ) {
                    if(productId.productId != null) {
                        try{
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(kyoboProductUrl(productId.productId)))
                            context.startActivity(intent)
                        } catch (e: Exception){
                            Toast.makeText(context, R.string.ds_noInternet, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } else {
            KyoboButtonComponent(
                modifier = Modifier.height(60.dp),
                product = productId
            ) {
                if(productId.productId != null) {
                    try{
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(kyoboProductUrl(productId.productId)))
                        context.startActivity(intent)
                    } catch (e: Exception){
                        Toast.makeText(context, R.string.ds_noInternet, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
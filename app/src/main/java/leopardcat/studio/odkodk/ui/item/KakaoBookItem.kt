package leopardcat.studio.odkodk.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import leopardcat.studio.odkodk.domain.Book
import leopardcat.studio.odkodk.domain.BookDetail
import leopardcat.studio.odkodk.domain.KakaoBook
import leopardcat.studio.odkodk.navigation.DETAIL_SCREEN
import leopardcat.studio.odkodk.ui.theme.BlackMain
import leopardcat.studio.odkodk.ui.theme.GrayMain
import leopardcat.studio.odkodk.ui.theme.Obang
import leopardcat.studio.odkodk.ui.theme.PinkMain
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@Composable
fun KakaoBookItem(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    book: KakaoBook
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BlackMain)
            .clickable {
                navHostController.navigate(DETAIL_SCREEN) //DETAIL_SCREEN으로 이동
                var author: String? = null
                author = if(!book.authors.isNullOrEmpty()){
                    book.authors[0]
                } else {
                    "저자정보 없음"
                }
                mainViewModel.setBookDetail( //viewModel에 book 데이터 세팅
                    BookDetail(
                        bookname = book.title,
                        authors = author,
                        publisher = book.publisher,
//                        isbn13 = book.isbn?.substringBefore(" "),
                        isbn13 = book.isbn?.replaceAfter(" ", ""),
                        bookImageURL = book.thumbnail
                    )
                )
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = book.thumbnail,
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                if(!book.title.isNullOrEmpty()){
                    Text(
                        text = book.title,
                        color = Color.White,
                        fontSize = 18.sp,
                        maxLines = 2,
                        fontFamily = Obang,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                    )
                } else {
                    Text(
                        text = "정보없음",
                        color = Color.White,
                        fontSize = 18.sp,
                        maxLines = 2,
                        fontFamily = Obang,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if(!book.authors.isNullOrEmpty()) {
                    Text(
                        text = book.authors[0],
                        color = GrayMain,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(
                        text = "정보없음",
                        color = GrayMain,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if(book.price != null) {
                    Text(
                        text = "${book.price} 원",
                        color = PinkMain,
                        fontSize = 15.sp,
                        modifier = Modifier.fillMaxWidth(),
                    )
                } else {
                    Text(
                        text = "정보없음",
                        color = PinkMain,
                        fontSize = 15.sp,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                    .width(90.dp)
                    .height(35.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                ) {
                    if(book.status == ""){
                        Text(
                            text = "정보없음",
                            color = BlackMain,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "${book.status}",
                            color = BlackMain,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
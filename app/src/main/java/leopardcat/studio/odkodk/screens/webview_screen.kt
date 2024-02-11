package leopardcat.studio.odkodk.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import leopardcat.studio.odkodk.R
import leopardcat.studio.odkodk.util.kyoboPreviewUrl
import leopardcat.studio.odkodk.viewmodel.MainViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel,
    activity: Activity
) {
    val backEnable by remember { mutableStateOf(true) }
    var webView : WebView? = null

    BackHandler(enabled = backEnable) {
        mainViewModel.loadCoverAds(activity)
        navHostController.popBackStack()
    }

    AndroidView(
        modifier = Modifier,
        factory = {context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                webViewClient = object : WebViewClient(){
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//                        backEnable = view!!.canGoBack()
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(kyoboPreviewUrl(mainViewModel.getPreference()?.getProduct("product")!!))
                webView = this
            }
        }, update = {
            webView = it
        })

    Box(
        modifier = Modifier
            .padding(top = 15.dp, start = 15.dp)
            .clip(CircleShape)
            .clickable {
                mainViewModel.loadCoverAds(activity)
                navHostController.popBackStack()
            },
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "",
            tint = Color.Black,
            modifier = Modifier.size(30.dp)
        )
    }
}
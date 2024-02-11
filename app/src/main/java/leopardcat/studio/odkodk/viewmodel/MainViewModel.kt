package leopardcat.studio.odkodk.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import leopardcat.studio.odkodk.R
import leopardcat.studio.odkodk.data.api.client.BookClient
import leopardcat.studio.odkodk.data.api.client.KakaoClient
import leopardcat.studio.odkodk.data.api.model.LibraryResult
import leopardcat.studio.odkodk.domain.BookDetail
import leopardcat.studio.odkodk.domain.KakaoApiResponse
import leopardcat.studio.odkodk.domain.KyoboProduct
import leopardcat.studio.odkodk.domain.LibraryRegion
import leopardcat.studio.odkodk.util.NetworkUtil
import leopardcat.studio.odkodk.util.Preference
import leopardcat.studio.odkodk.util.getDate
import leopardcat.studio.odkodk.util.kyoboSearchUrl
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import kotlin.system.exitProcess

@HiltViewModel
class MainViewModel @javax.inject.Inject constructor(
) : ViewModel() {

    private var prefs: Preference? = null
    val apiKey = ""
    val kakaoApiKey = ""

    //preference
    fun setPreference(applicationContext: Context) {
        prefs = Preference(applicationContext)
    }

    fun getPreference(): Preference? {
        return prefs
    }

    //user 정보 설정
    fun setDate() {
        prefs?.setDate("date", getDate())
    }
    fun setAgePref(age: Int) {
        prefs?.setAge("age", age)
    }

    fun setSexPref(sex: Int) {
        prefs?.setSex("sex", sex)
    }

    fun setLibraryPref(library: Int) {
        prefs?.setLibrary("library", library)
    }

    fun setLibraryNamePref(name: String) {
        prefs?.setLibraryName("name", name)
    }

    fun setUserFinishPref(finish: Boolean) {
        prefs?.setFinish("finish", finish)
    }



    //도서 상세 정보
    private val _bookDetail = MutableLiveData<BookDetail>()
    val bookDetail: LiveData<BookDetail> = _bookDetail

    fun setBookDetail(bookDetail: BookDetail) {
        _bookDetail.postValue(bookDetail)
    }

    //도서관 지역 정보
    private val _libraryRegion = MutableLiveData<LibraryRegion>()
    val libraryRegion: LiveData<LibraryRegion> = _libraryRegion

    fun setLibraryRegion(libraryRegion: LibraryRegion) {
        _libraryRegion.postValue(libraryRegion)
    }

    fun gptLibSrch(libraryRegion: LibraryRegion, name: Int){
        viewModelScope.launch {
            BookClient.getBookLibSrch().getLibSrch(apiKey, name, "json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ it ->
                    if(it.response.libs == null){
                        _libraryRegion.postValue(LibraryRegion(libraryRegion.big, libraryRegion.small, null))
                        println("응답 실패 : ...")
                    } else {
                        _libraryRegion.postValue(LibraryRegion(libraryRegion.big, libraryRegion.small, it.response.libs))
                        println("응답 성공 : " + it.response.libs.map { it.lib.libName })
                    }
                }, { e ->
                    _libraryRegion.postValue(LibraryRegion(libraryRegion.big, libraryRegion.small, null))
                    println("응답 실패 : $e")
                })
        }
    }

    //Kakao 검색 정보
    private val _kakaoList = MutableLiveData<KakaoApiResponse>()
    val kakaoList: LiveData<KakaoApiResponse> = _kakaoList

    fun getKakaoBookList(query: String){
        viewModelScope.launch {
            KakaoClient.getKakaoBook().getKakaoSrch(kakaoApiKey, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ it ->
                    if(it.documents == null){
                        _kakaoList.postValue(KakaoApiResponse(null, null))
                        println("결과 없음 : ...")
                    } else {
                        _kakaoList.postValue(KakaoApiResponse(it.documents, it.meta))
                        println("응답 성공 : kakao 검색")
                    }
                }, { e ->
                    _kakaoList.postValue(KakaoApiResponse(null, null))
                    println("응답 실패 : $e")
                })
        }
    }


    //교보 상세페이지 조회
    private val _kyoboProduct = MutableLiveData<KyoboProduct>()
    val kyoboProduct: LiveData<KyoboProduct> = _kyoboProduct

    @OptIn(DelicateCoroutinesApi::class)
    fun getKyoboSearch(isbn: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val document: Document = Jsoup.connect(kyoboSearchUrl(isbn)).get()
                val aElement: Elements = document.select("div[class='prod_thumb_box size_lg'] a")
                val productId: String = aElement.attr("href").replace("https://product.kyobobook.co.kr/detail/", "")
                _kyoboProduct.postValue(KyoboProduct(productId))
            } catch (e: Exception) {
                _kyoboProduct.postValue(KyoboProduct("9999"))
                e.printStackTrace()
            }
        }
    }

    //도서관 도서 소장여부 조회
    private val _libraryBook = MutableLiveData<LibraryResult>()
    val libraryBook: LiveData<LibraryResult> = _libraryBook

    fun getLibraryBook(isbn: String) {
        viewModelScope.launch {
            BookClient.getLibraryBookSrch().getLibBook(apiKey, prefs!!.getLibrary("library"), isbn,"json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ it ->
                    if(it.response.result != null) {
                        _libraryBook.postValue(LibraryResult(it.response.result.hasBook, it.response.result.loanAvailable))
                        println("응답 성공 : " + it.response.result.hasBook + ", " + it.response.result.loanAvailable)
                    } else {
                        _libraryBook.postValue(LibraryResult("9999", "9999"))
                        println("응답 실패 : null...")
                    }
                }, { e ->
                    _libraryBook.postValue(LibraryResult("9999", "9999"))
                    println("응답 실패 : $e")
                })
        }
    }

    //와이파이 체크
    fun checkWifi(context: Context) {
        if (!NetworkUtil.checkNetworkState(context)) {
            Toast.makeText(context, R.string.cw_wifi, Toast.LENGTH_SHORT).show()
        }
    }

    //전면 광고
    //광고 상태
    private var mInterstitialAd: InterstitialAd? = null
    private val COVER_AD_ID = ""
    private val COVER_AD_TEST_ID = ""

    fun setCoverAds(context: Context) {
        MobileAds.initialize(context) {
            val adRequest = AdRequest.Builder().build()
            //TODO 광고 ID 설정
            InterstitialAd.load(context,
                COVER_AD_TEST_ID,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        mInterstitialAd = interstitialAd
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        mInterstitialAd = null
                    }
                }
            )
        }
    }
    fun loadCoverAds(activity: Activity) {
        mInterstitialAd?.show(activity)
    }


    //앱 재시작
    fun restartApp(context: Context) {
        viewModelScope.launch {
            delay(500)
            val packageManager = context.packageManager
            val intent = packageManager.getLaunchIntentForPackage(context.packageName)
            val componentName = intent?.component
            val mainIntent = Intent.makeRestartActivityTask(componentName)
            context.startActivity(mainIntent)
            exitProcess(0)
        }
    }
}
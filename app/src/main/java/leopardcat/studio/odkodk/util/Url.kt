package leopardcat.studio.odkodk.util


private const val KYOBO_SEARCH_BASE_URL = "https://search.kyobobook.co.kr/search?keyword={isbn}"
private const val KYOBO_PREVIEW_BASE_URL = "https://product.kyobobook.co.kr/book/preview/{productId}"
private const val KYOBO_PRODUCT_BASE_URL = "https://product.kyobobook.co.kr/detail/{productId}"

fun kyoboSearchUrl(isbn: String): String {
    return KYOBO_SEARCH_BASE_URL.replace("{isbn}", isbn)
}

fun kyoboPreviewUrl(productId: String): String {
    return KYOBO_PREVIEW_BASE_URL.replace("{productId}", productId)
}

fun kyoboProductUrl(productId: String): String {
    return KYOBO_PRODUCT_BASE_URL.replace("{productId}", productId)
}
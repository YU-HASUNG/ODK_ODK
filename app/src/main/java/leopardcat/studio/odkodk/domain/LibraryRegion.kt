package leopardcat.studio.odkodk.domain

import leopardcat.studio.odkodk.data.api.model.Library

data class LibraryRegion (
    val big: String?,
    val small: Int?,
    val library: List<Library>?
)
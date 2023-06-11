package com.devcommop.myapplication.ui.components.shorts

import com.devcommop.myapplication.data.model.ShortItem

sealed class ShortsScreenEvents{
    object Refresh: ShortsScreenEvents()
//    object OpenGallery : ShortsScreenEvents()
//    object Play : ShortsScreenEvents()
//    object Pause : ShortsScreenEvents()
    data class LikeShort(val short : ShortItem): ShortsScreenEvents()
    data class CommentShort(val short : ShortItem): ShortsScreenEvents()
    data class ShareShort(val short : ShortItem): ShortsScreenEvents()
    data class ReportShort(val short : ShortItem): ShortsScreenEvents()
    data class DeleteShort(val short : ShortItem): ShortsScreenEvents()

}

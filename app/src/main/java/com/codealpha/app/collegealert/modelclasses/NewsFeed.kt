package com.codealpha.app.collegealert.modelclasses

import android.net.Uri

data class NewsFeed(
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val image: String,
    var documentId: String = ""
) {
}
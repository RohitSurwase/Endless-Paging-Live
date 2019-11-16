package com.rohitss.newsmvvm.utils

import android.content.Context
import com.rohitss.newsmvvm.database.AppDatabase
import com.rohitss.newsmvvm.database.NewsRepository

const val DATABASE_NAME = "my_database.db"
const val DATABASE_VERSION = 1
const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
const val API_KEY = "xI4AA4gcMj9JyFlyQn2dSAj689PGjKjA"
const val THUMBNAIL_FORMAT = "thumbLarge"
const val COVER_IMAGE_FORMAT = "mediumThreeByTwo210"

fun getAppRepository(context: Context): NewsRepository {
    return NewsRepository.getInstance(AppDatabase.getInstance(context).getNewsDAO())
}

fun getSectionList() = arrayOf(
    "business",
    "movies",
    "science",
    "technology",
    "travel",
    "world"
)
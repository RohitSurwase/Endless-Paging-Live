package com.rohitss.newsmvvm.database

import android.util.Log
import com.rohitss.newsmvvm.api.NyNewsApi
import com.rohitss.newsmvvm.model.NewsDAO
import com.rohitss.newsmvvm.model.NewsItem

class NewsRepository private constructor(private val newsDAO: NewsDAO) {

    companion object {
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(newsDao: NewsDAO): NewsRepository {
            return instance ?: synchronized(this) {
                instance ?: NewsRepository(newsDao)
                    .also { instance = it }
            }
        }
    }

    fun getAllNews(section: String) = newsDAO.getAll(section)

    suspend fun getSetLatestNews(section: String) {
        val newsApiResult = try {
            NyNewsApi.create().getAllNews(section)
        } catch (e: Exception) {
            Log.e("NewsRepository", "GetAllNews: ${e.message}", e)
            null
        }

        newsApiResult?.let { apiResult ->
            apiResult.results?.let { list ->
                val newsList = mutableListOf<NewsItem>()
                list.asSequence().filterNotNull()
                    .filterNot { newsApiResult.section == null }
                    .map {
                        NewsItem(
                            id = 0,
                            title = if (it.title.isNullOrEmpty()) "" else it.title!!,
                            thumbnailUrl = it.getImage(true),
                            bylineAuthor = if (it.byline.isNullOrEmpty()) "" else it.byline!!,
                            coverImgUrl = it.getImage(false),
                            abstractContent = if (it.abstract.isNullOrEmpty()) "" else it.abstract!!,
                            publishedDate = if (it.publishedDate.isNullOrEmpty()) "" else it.publishedDate!!,
                            shortUrl = if (it.shortUrl.isNullOrEmpty()) "" else it.shortUrl!!,
                            section = newsApiResult.section!!
                        )
                    }
                    .toList()
                    .forEach {
                        newsList.add(it)
                    }
                if (newsList.isNotEmpty()) {
                    newsDAO.insertAll(newsList)
                } else {
                    Log.e("NewsRepository", "No result found")
                }
            }
        }
    }

    suspend fun getNews(newsId: Long) = newsDAO.getNews(newsId)
}
package com.rohitss.newsmvvm.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rohitss.newsmvvm.database.NewsRepository
import com.rohitss.newsmvvm.model.NewsItem
import kotlinx.coroutines.launch

class MainActViewModel internal constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    fun getLatestNews(section: String, onComplete: () -> Unit): LiveData<List<NewsItem>> {
        viewModelScope.launch { newsRepository.getSetLatestNews(section) }
            .invokeOnCompletion {
                onComplete.invoke()
            }
        return newsRepository.getAllNews(section)
    }
}

class MainActVmFactory(private val newsRepository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        MainActViewModel(newsRepository) as T
}
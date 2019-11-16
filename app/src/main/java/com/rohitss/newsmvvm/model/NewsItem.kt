package com.rohitss.newsmvvm.model

import androidx.lifecycle.LiveData
import androidx.room.*
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "news_table",
    indices = [Index(value = ["shortUrl", "publishedDate"], unique = true)]
)
data class NewsItem(
    @PrimaryKey(autoGenerate = true) @NotNull var id: Long,
    var title: String,
    var thumbnailUrl: String,
    var bylineAuthor: String,
    var coverImgUrl: String,
    var abstractContent: String,
    var publishedDate: String,
    var shortUrl: String,
    var section: String
) {

    constructor () : this(0, "", "", "", "", "", "", "", "")
}

@Dao
interface NewsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(articlesItem: List<NewsItem>)

    @Query("DELETE FROM news_table")
    suspend fun deleteAll()

    @Query("SELECT * from news_table WHERE section = :section ORDER BY publishedDate DESC")
    fun getAll(section: String): LiveData<List<NewsItem>>

    @Query("SELECT * from news_table WHERE id = :newsId")
    suspend fun getNews(newsId: Long): NewsItem
}

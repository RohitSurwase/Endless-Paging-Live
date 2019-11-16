package com.rohitss.newsmvvm.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.api.load
import com.rohitss.newsmvvm.R
import com.rohitss.newsmvvm.utils.getAppRepository
import kotlinx.android.synthetic.main.activity_news_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class NewsDetailsActivity : AppCompatActivity() {
    private var newsId: Long = -1L

    companion object {
        fun launchIntent(context: Context, newsId: Long): Intent {
            val intent = Intent(context, NewsDetailsActivity::class.java)
            intent.putExtra("newsId", newsId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        savedInstanceState?.let {
            newsId = it.getLong("newsId")
        } ?: run {
            newsId = intent.extras?.getLong("newsId") ?: -1L
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val newsItem = getAppRepository(this@NewsDetailsActivity).getNews(newsId)

            with(newsItem) {
                ivCoverImage.load(coverImgUrl) {
                    crossfade(true)
                    placeholder(R.mipmap.ic_launcher)
                }
                tvTitle.text = title
                tvAuthor.text = bylineAuthor

                //Input format 2019-11-15T21:31:57-05:00
                val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                val output = SimpleDateFormat("h:mm a, dd MMM yyyy", Locale.getDefault())
                val formattedTime = try {
                    output.format(input.parse(publishedDate)!!)
                } catch (e: ParseException) {
                    publishedDate
                }

                tvPublishedDate.text = "Published On: $formattedTime"
                tvSource.text = "Read More: $shortUrl"
                tvAbstract.text = abstractContent
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("newsId", newsId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}

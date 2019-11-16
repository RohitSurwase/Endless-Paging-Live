package com.rohitss.newsmvvm.home

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rohitss.newsmvvm.R
import com.rohitss.newsmvvm.adapters.NewsRvAdapter
import com.rohitss.newsmvvm.details.NewsDetailsActivity
import com.rohitss.newsmvvm.model.NewsItem
import com.rohitss.newsmvvm.utils.getAppRepository
import com.rohitss.newsmvvm.utils.getSectionList
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var newsSection: String = "world"
    private val viewModel: MainActViewModel by viewModels {
        MainActVmFactory(getAppRepository(this))
    }

    private val newsRvAdapter by lazy {
        NewsRvAdapter {
            startActivity(NewsDetailsActivity.launchIntent(this, (it.tag as NewsItem).id))
        }.apply { setHasStableIds(true) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "${newsSection.capitalize()} News"

        rvNewsHome.adapter = newsRvAdapter
        viewModel.getLatestNews(newsSection) {
            pbLoading.visibility = View.GONE
        }.observe(this, Observer {
            srlNewsHome.visibility = View.VISIBLE
            newsRvAdapter.data = it
        })

        srlNewsHome.setOnRefreshListener {
            viewModel.getLatestNews(newsSection) {
                pbLoading.visibility = View.GONE
            }.observe(this, Observer {
                srlNewsHome.visibility = View.VISIBLE
                if (srlNewsHome.isRefreshing) srlNewsHome.isRefreshing = false
                newsRvAdapter.data = it
            })
        }

        fabChangeSection.setOnClickListener {
            val sectionList = getSectionList()
            AlertDialog.Builder(this@MainActivity).apply {
                setTitle("Select News Section")
                setSingleChoiceItems(
                    sectionList, sectionList.indexOf(newsSection)
                ) { dialogInterface, i ->
                    newsSection = sectionList[i]
                    supportActionBar?.title = "${newsSection.capitalize()} News"
                    pbLoading.visibility = View.VISIBLE
                    srlNewsHome.visibility = View.GONE
                    viewModel.getLatestNews(newsSection) {
                        pbLoading.visibility = View.GONE
                    }.observe(this@MainActivity, Observer {
                        srlNewsHome.visibility = View.VISIBLE
                        newsRvAdapter.data = it
                    })
                    dialogInterface.dismiss()
                }
                show()
            }
        }
    }
}
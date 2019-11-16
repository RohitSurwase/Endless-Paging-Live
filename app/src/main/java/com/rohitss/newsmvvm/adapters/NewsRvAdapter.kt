package com.rohitss.newsmvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.rohitss.newsmvvm.R
import com.rohitss.newsmvvm.model.NewsItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news_list.view.*

class NewsRvAdapter(private val itemClickListener: (View) -> Unit) :
    RecyclerView.Adapter<NewsRvAdapter.NewsItemViewHolder>() {
    var data: List<NewsItem> = emptyList()
        set(newList) {
            val calculateDiff = DiffUtil.calculateDiff(
                NewsItemDiffCallback(field, newList)
            )
            calculateDiff.dispatchUpdatesTo(this)
            field = newList
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news_list, parent, false),
            itemClickListener
        )
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class NewsItemViewHolder(
        override val containerView: View,
        itemClickListener: (View) -> Unit
    ) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        init {
            itemView.setOnClickListener(itemClickListener)
        }

        fun bind(item: NewsItem) = with(itemView) {
            itemView.tag = item
            tvTitle.text = item.title
            tvAuthor.text = item.bylineAuthor
            ivThumbnail.load(item.thumbnailUrl) {
                crossfade(true)
                placeholder(R.mipmap.ic_launcher)
            }
        }
    }
}

class NewsItemDiffCallback(
    private val oldList: List<NewsItem>,
    private val newList: List<NewsItem>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.id == new.id
                && old.title == new.title
                && old.publishedDate == new.publishedDate
                && old.bylineAuthor == new.bylineAuthor
                && old.shortUrl == new.shortUrl
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }
}
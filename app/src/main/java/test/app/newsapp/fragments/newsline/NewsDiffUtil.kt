package test.app.newsapp.fragments.newsline

import androidx.recyclerview.widget.DiffUtil
import test.app.newsapp.db.NewsData

class NewsDiffUtil: DiffUtil.ItemCallback<NewsData>() {

    override fun areItemsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
        return oldItem.id == newItem.id
    }
}
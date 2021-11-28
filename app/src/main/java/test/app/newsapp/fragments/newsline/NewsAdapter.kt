package test.app.newsapp.fragments.newsline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import test.app.newsapp.R
import test.app.newsapp.db.NewsData

class NewsAdapter(
    private val LayoutTouch: (String, String?) -> Unit,
) : ListAdapter<NewsData, NewsAdapter.NewsViewHolder>(NewsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return if (viewType == WITHOUT_IMAGE)
            NewsViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.newsline_recycler_item, parent, false)
            )
        else
            NewsViewHolderImage(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.newsline_recycler_item_image, parent, false)
            )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        if (holder is NewsViewHolderImage)
            getItem(position).image?.let { url ->
                Picasso
                    .get()
                    .load(url)
                    .placeholder(R.drawable.ic_baseline_image_search_30)
                    .error(R.drawable.ic_baseline_error_outline_30)
                    .into(holder.picture)
            }

        holder.title.text = getItem(position).title

        holder.layout.setOnClickListener {
            LayoutTouch(
                getItem(position).description,
                getItem(position).image,
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).image.isNullOrEmpty())
            WITHOUT_IMAGE
        else WITH_IMAGE
    }

    open inner class NewsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.title_item)
        val layout: LinearLayout = v.findViewById(R.id.layout)
    }

    inner class NewsViewHolderImage(v: View) : NewsViewHolder(v) {
        val picture: ImageView = v.findViewById(R.id.image_item)
    }

    companion object {
        private const val WITH_IMAGE = 1
        private const val WITHOUT_IMAGE = 0
    }


}
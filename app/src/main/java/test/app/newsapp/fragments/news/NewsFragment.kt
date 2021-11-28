package test.app.newsapp.fragments.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import test.app.newsapp.R
import test.app.newsapp.fragments.picture.PictureFragment

class NewsFragment : Fragment() {

    private var description: String? = null
    private var url: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireArguments().let {
            description = it.getString(DESCRIPTION)
            url = it.getString(URL)
        }

        val descriptionText = view.findViewById<TextView>(R.id.description_news)
        val imageView = view.findViewById<ImageView>(R.id.image_news)

        description?.let {
            descriptionText.text = it
        }

        url?.let { imageUrl ->
            Picasso
                .get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_baseline_image_search_30)
                .error(R.drawable.ic_baseline_error_outline_30)
                .into(imageView)

            imageView.setOnClickListener {
                setFragment(PictureFragment.newInstance(imageUrl))
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    companion object {

        fun newInstance(description: String, imageUrl: String?): NewsFragment {
            return NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(DESCRIPTION, description)
                    putString(URL, imageUrl)
                }
            }
        }

        private const val DESCRIPTION = "DESCRIPTION"
        private const val URL = "URL"
    }
}
package test.app.newsapp.fragments.picture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import test.app.newsapp.R
import test.app.newsapp.fragments.news.NewsFragment

class PictureFragment : Fragment() {

    private var url: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireArguments().let {
            it.getString(IMAGE_URL)?.let { text ->
                url = text
                Log.d("URL", url)
            }
        }

        val imageView: ImageView = view.findViewById(R.id.image_view)

        if (url.isNotBlank())
            Picasso
                .get()
                .load(url)
                .placeholder(R.drawable.ic_baseline_image_search_30)
                .error(R.drawable.ic_baseline_error_outline_30)
                .into(imageView)
    }

    companion object {

        fun newInstance(imageUrl: String): PictureFragment {
            return PictureFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE_URL, imageUrl)
                }
            }
        }

        private const val IMAGE_URL = "IMAGE_URL"
    }
}
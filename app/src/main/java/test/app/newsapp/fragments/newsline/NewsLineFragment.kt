package test.app.newsapp.fragments.newsline

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import test.app.newsapp.R
import test.app.newsapp.Store.NewsState
import test.app.newsapp.Store.NewsViewModel
import test.app.newsapp.fragments.news.NewsFragment
import test.app.newsapp.getComponent
import javax.inject.Inject

class NewsLineFragment : Fragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_line, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity(), viewModelProvider)[NewsViewModel::class.java]

        val swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.swipe_layout)
        val reloadText: TextView = view.findViewById(R.id.reload_text)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        reloadText.visibility = View.INVISIBLE

        swipeRefresh.setOnRefreshListener {
            viewModel.getNewsFromApi()
        }

        if (savedInstanceState == null) {
            viewModel.getNewsFromApi()
        }

        val adapter = NewsAdapter(
            LayoutTouch = { description, url ->
                Log.d("DESC", description)
                setFragment(NewsFragment.newInstance(description, url))
            }
        )

        recyclerView.let {
            it.layoutManager = LinearLayoutManager(requireContext()).apply {
                stackFromEnd = true
                reverseLayout = true
            }
            it.adapter = adapter
        }

        viewModel.getData().observe(viewLifecycleOwner, { state ->
            swipeRefresh.isRefreshing = false
            state.let {
                when (it) {
                    is NewsState.Loading -> {
                        adapter.submitList(it.data)
                        reloadText.visibility = View.INVISIBLE
                    }

                    is NewsState.Error -> {
                        reloadText.visibility = View.VISIBLE
                    }

                    is NewsState.NoInternet -> {
                        Toast.makeText(requireContext(), "NO INTERNET", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    companion object {
        fun newInstance(): NewsLineFragment {
            return NewsLineFragment()
        }
    }

    private fun setFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}
package test.app.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import test.app.newsapp.Store.NewsViewModel
import test.app.newsapp.fragments.newsline.NewsLineFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, NewsLineFragment.newInstance())
                .commitAllowingStateLoss()
    }
}
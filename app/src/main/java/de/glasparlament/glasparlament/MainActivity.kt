package de.glasparlament.glasparlament

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.navigation.Navigation
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        AppCenter.start(application, BuildConfig.APP_CENTER_APP_SECRET, Analytics::class.java, Crashes::class.java)

        //Add back navigation in the title bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            //Title bar back press triggers onBackPressed()
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }


        return true
    }

    //Both navigation bar back press and title bar back press will trigger this method
    override fun onBackPressed() {
        Navigation.findNavController(this, R.id.navigationHost).navigateUp()
    }
}

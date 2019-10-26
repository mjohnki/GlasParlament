package de.glasparlament.glasparlament

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (BuildConfig.APP_CENTER_APP_SECRET.isNotEmpty()) {
            AppCenter.start(application, BuildConfig.APP_CENTER_APP_SECRET, Analytics::class.java, Crashes::class.java)
        }

        //Add back navigation in the title bar
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        NavigationUI.setupActionBarWithNavController(this, Navigation.findNavController(this, R.id.navigationHost))
    }

    override fun onSupportNavigateUp(): Boolean {
        Navigation.findNavController(this, R.id.navigationHost).navigateUp()
        return super.onSupportNavigateUp()
    }
}

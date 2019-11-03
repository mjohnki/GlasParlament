package de.glasparlament.glasparlament

import android.os.Bundle
import androidx.navigation.Navigation
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportActionBar?.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        Navigation.findNavController(this, R.id.navigationHost).navigateUp()
        return super.onSupportNavigateUp()
    }
}

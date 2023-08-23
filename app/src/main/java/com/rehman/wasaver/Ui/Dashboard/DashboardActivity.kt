package com.rehman.wasaver.Ui.Dashboard

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.rehman.wasaver.R
import com.rehman.wasaver.Ui.Dashboard.ImagesFragment.ImagesFragment
import com.rehman.wasaver.Ui.Dashboard.SaveFragment.SaveFragment
import com.rehman.wasaver.Ui.Dashboard.VideosFragment.VideosFragment

class DashboardActivity : AppCompatActivity() {

    private lateinit var chipNavigationBar: ChipNavigationBar
    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initViews()

        bottomNav()
    }

    private fun initViews() {
        chipNavigationBar = findViewById(R.id.navigation)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        chipNavigationBar.setItemSelected(R.id.image, true)
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, ImagesFragment()
        ).commit();
    }

    private fun bottomNav() {
        chipNavigationBar.setOnItemSelectedListener { id ->

            var fragment: Fragment? = null
            vibrator.vibrate(30)

            when (id) {
                R.id.image -> fragment = ImagesFragment()
                R.id.video -> fragment = VideosFragment()
                R.id.fav -> fragment = SaveFragment()
            }
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()

            fragment?.let {
                transaction.replace(R.id.fragment_container, it).commit()
            }
        }
    }

}
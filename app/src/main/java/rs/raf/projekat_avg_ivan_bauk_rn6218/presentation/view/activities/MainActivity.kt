package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import rs.raf.projekat_avg_ivan_bauk_rn6218.R
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.user.User
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.ActivityMainBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.viewpager.PagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadPreferences()
        init()
    }

    private fun loadPreferences() {
        sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        val userTxt = sharedPreferences.getString(LoginActivity.PREF_MAIN_KEY, null)
        if (userTxt != null) {
            val parts = userTxt.split("|")
            val username = parts[0]
            val password = parts[1]
            val height = parts[2].toInt()
            val weight = parts[3].toInt()
            UserProperties.username = username
            UserProperties.calories = (448 + (9 * weight) + (3 * height)).toString()
        }
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        binding.viewpagerNonScrollable.adapter =
            PagerAdapter(
                supportFragmentManager
            )

        (findViewById<View>(R.id.navigation_main) as BottomNavigationView).setOnItemSelectedListener(
            NavigationBarView.OnItemSelectedListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.navigation_1 -> binding.viewpagerNonScrollable.setCurrentItem(PagerAdapter.FRAGMENT_1, false)
                    R.id.navigation_2 -> binding.viewpagerNonScrollable.setCurrentItem(PagerAdapter.FRAGMENT_2, false)
                    R.id.navigation_3 -> binding.viewpagerNonScrollable.setCurrentItem(PagerAdapter.FRAGMENT_3, false)
                    R.id.navigation_4 -> binding.viewpagerNonScrollable.setCurrentItem(PagerAdapter.FRAGMENT_4, false)
                    R.id.navigation_5 -> binding.viewpagerNonScrollable.setCurrentItem(PagerAdapter.FRAGMENT_5, false)
                }
                true
            })

    }

    object UserProperties {
        lateinit var username: String
        lateinit var calories: String
    }

}

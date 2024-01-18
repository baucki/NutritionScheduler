package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.file.FileRepository
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            Thread.sleep(2000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        loadPreferences()
        loadUsers()
    }

    private fun loadPreferences() {
        sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        val userTxt = sharedPreferences.getString(LoginActivity.PREF_MAIN_KEY, null)
        if (userTxt == null) {
            intent = Intent(this, LoginActivity::class.java)
            finish()
            startActivity(intent)
        } else {
            intent = Intent (this, MainActivity::class.java)
            val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
            sharedPreferences
                .edit()
                .putString(LoginActivity.PREF_MAIN_KEY, userTxt)
                .apply()
            startActivity(intent)
            finish()


        }
    }

    private fun loadUsers() {
//        var fileRepository = FileRepository.getInstance
        FileRepository.load(this, "users.txt")
    }

}
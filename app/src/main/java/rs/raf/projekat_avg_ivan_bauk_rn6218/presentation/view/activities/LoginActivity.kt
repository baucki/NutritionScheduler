package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.file.FileRepository
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    companion object {
        const val PREF_MAIN_KEY  = "mainKey"
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initUi()
        initListeners()
    }

    private fun initUi() {
        binding.textLoginError.isVisible = false
        binding.textUsernameError.isVisible = false
        binding.textPasswordError.isVisible = false
    }

    private fun initListeners() {
        binding.buttonLogin.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()

            binding.editUsername.setText("")
            binding.editPassword.setText("")

            if (password.length < 4) {
                binding.textPasswordError.text = "Password minimum length is 4"
                binding.textPasswordError.isVisible = true

                binding.textLoginError.isVisible = false
                binding.textUsernameError.isVisible = false
                return@setOnClickListener
            }

            val user = FileRepository.findUser(username, password)

            if (user != null) {
                intent = Intent (this, MainActivity::class.java)
                val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
                sharedPreferences
                    .edit()
                    .putString(PREF_MAIN_KEY, user.username + "|" + user.password + "|" + user.weight + "|" + user.height)
                    .apply()
                startActivity(intent)
                finish()
            } else {
                binding.textPasswordError.text = "Password minimum length is 4"
                binding.textLoginError.text = "Wrong credentials"
                binding.textLoginError.isVisible = true
                binding.textUsernameError.isVisible = false
                binding.textPasswordError.isVisible = false

            }
        }
    }


}
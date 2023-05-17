package com.adrino.moviemate.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.adrino.moviemate.R
import com.adrino.moviemate.carousal.CarousalActivity
import com.adrino.moviemate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    companion object {
        private const val USER_ID = "admin"
        private const val PASSWORD = "admin@123"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initView()
    }

    private fun initView() {
        binding.btnLogin.setOnClickListener {
            validateLoginCredentials()
        }
        binding.etPassword.addTextChangedListener {
            binding.tvError.visibility = View.GONE
        }
        binding.etPassword.addTextChangedListener {
            binding.tvError.visibility = View.GONE
        }
    }

    private fun validateLoginCredentials() {
        if (binding.etUserName.text.toString() == USER_ID
            && binding.etPassword.text.toString() == PASSWORD) {
            startActivity(CarousalActivity.createInstance(this))
            overridePendingTransition(R.anim.enter_from_bottom, R.anim.stay)
        } else {
            binding.tvError.visibility = View.VISIBLE
        }
    }
}
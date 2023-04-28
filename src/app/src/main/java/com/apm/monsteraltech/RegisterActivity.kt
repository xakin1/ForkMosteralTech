package com.apm.monsteraltech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apm.monsteraltech.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
    fun onClickRegister() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun onClickCancel() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
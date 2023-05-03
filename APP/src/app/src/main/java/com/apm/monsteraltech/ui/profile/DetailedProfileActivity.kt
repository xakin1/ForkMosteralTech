package com.apm.monsteraltech.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.apm.monsteraltech.ActionBarActivity
import com.apm.monsteraltech.R
import com.apm.monsteraltech.ui.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DetailedProfileActivity : ActionBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_profile)
        setToolBar()
        findViewById<Button>(R.id.log_out).setOnClickListener{
            signOut()
        }
    }

    private fun signOut() {
        Firebase.auth.signOut()
        val intent = Intent(this@DetailedProfileActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}
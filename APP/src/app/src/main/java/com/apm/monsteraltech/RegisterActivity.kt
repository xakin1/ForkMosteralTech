package com.apm.monsteraltech

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.apm.monsteraltech.dto.User
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.services.UserService
import com.apm.monsteraltech.ui.login.LoginActivity
import com.apm.monsteraltech.ui.login.dataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.DateFormat.getDateTimeInstance
import java.util.*


class RegisterActivity : AppCompatActivity() {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth

    private var serviceFactory = ServiceFactory()
    private var userService = serviceFactory.createService(UserService::class.java)
    // [END declare_auth]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        val name = findViewById<EditText>(R.id.nameRegister)
        val lastName = findViewById<EditText>(R.id.lastNameRegister)
        val email = findViewById<EditText>(R.id.emailRegister)
        val confirmEmail = findViewById<EditText>(R.id.confirmEmailRegister)
        val password = findViewById<EditText>(R.id.passwordRegister)
        val confirmPassword = findViewById<EditText>(R.id.confirmPasswordRegister)

        val buttonCancelar = findViewById<Button>(R.id.cancelButtonRegister)
        val buttonRegistrarse = findViewById<Button>(R.id.confirmButtonRegister)

        buttonRegistrarse.setOnClickListener {
            if (email.text.toString() != confirmEmail.text.toString()) {
                Toast.makeText(this, "Los emails no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.text.toString() != confirmPassword.text.toString()) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            createAccount(email.text.toString(), password.text.toString(), name.text.toString(), lastName.text.toString())

            // Validar los datos del registro y enviarlos al servidor
        }
        buttonCancelar.setOnClickListener {
            // Acción para cancelar el registro
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    fun createAccount(email: String, password: String, name: String, lastName: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    user?.getIdToken(true)?.addOnSuccessListener { result ->

                        val idToken = result.token.toString()
                        lifecycleScope.launch(Dispatchers.IO) {
                            createUserDataBase(name, lastName, user.uid, idToken)

                            saveUserOnDatastore(name, lastName, idToken)
                        }
                        Log.d(TAG, "GetTokenResult result = $idToken")
                    }

                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    suspend fun createUserDataBase(name: String, lastName: String, uid: String, idToken: String)
    {
        val userDto = User(uid, name, lastName, idToken, null)
        userService.addUser(userDto)

        Log.d("User", userDto.firebaseToken)
    }

    fun updateUI(user: FirebaseUser?) {
        if(user != null) moveToMainMenu()
    }

    private fun moveToMainMenu() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun reload() {
        moveToMainMenu()
    }

    private suspend fun saveUserOnDatastore(userName: String, userLastname: String, userFirebaseKey: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("userName")] = userName
            preferences[stringPreferencesKey("userLastname")] = userLastname
            preferences[stringPreferencesKey("userFirebaseKey")] = userFirebaseKey
        }
    }

}


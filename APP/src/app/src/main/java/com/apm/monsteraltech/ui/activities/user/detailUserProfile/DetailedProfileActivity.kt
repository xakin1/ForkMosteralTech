package com.apm.monsteraltech.ui.activities.user.detailUserProfile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import com.apm.monsteraltech.ui.activities.actionBar.ActionBarActivity
import com.apm.monsteraltech.ui.activities.login.login.LoginActivity
import com.apm.monsteraltech.R
import com.apm.monsteraltech.data.dto.FavouriteRequest
import com.apm.monsteraltech.data.dto.User
import com.apm.monsteraltech.services.ServiceFactory
import com.apm.monsteraltech.services.UserService
import com.apm.monsteraltech.ui.activities.login.login.dataStore
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class DetailedProfileActivity : ActionBarActivity() {


    private val serviceFactory = ServiceFactory()
    private val userService = serviceFactory.createService(UserService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_profile)
        setToolBar()

        val textAssociatedWithFacebook = findViewById<TextView>(R.id.text_associated_with_facebook)
        val textAssociatedWithGoogle = findViewById<TextView>(R.id.text_associated_with_google)
        val textUsersName = findViewById<TextView>(R.id.usersName)
        val textDateOfMemberShip = findViewById<TextView>(R.id.dateOfMemberShip)
        val buttonSignOut = findViewById<Button>(R.id.log_out)
        val buttonFacebookSignOut = findViewById<Button>(R.id.log_out_facebook)
        buttonSignOut.visibility = Button.VISIBLE

        if (Firebase.auth.currentUser?.providerData?.get(1)?.providerId == "facebook.com") {
            textAssociatedWithFacebook.text = "Asociada con Facebook"
            buttonSignOut.visibility = Button.GONE
            buttonFacebookSignOut.visibility = Button.VISIBLE
        } else {
            textAssociatedWithFacebook.text = "No asociada con Facebook"
        }

        if (Firebase.auth.currentUser?.providerData?.get(1)?.providerId == "google.com") {
            textAssociatedWithGoogle.text = "Asociada con Google"
        } else {
            textAssociatedWithGoogle.text = "No asociada con Google"
        }


        textUsersName.text =  intent.getStringExtra("userName")

        textDateOfMemberShip.text = Firebase.auth.currentUser?.metadata?.creationTimestamp?.let {
            val date = LocalDate.ofEpochDay(it / 1000 / 60 / 60 / 24)
            date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }


        findViewById<Button>(R.id.log_out).setOnClickListener{
            signOut()
        }

        findViewById<Button>(R.id.log_out_facebook).setOnClickListener{
            signOut()
        }

        findViewById<Button>(R.id.delete_account).setOnClickListener{
            deleteUser()
        }
    }

    private fun signOut() {
        Firebase.auth.signOut()
        val intent = Intent(this@DetailedProfileActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun getUserDataFromDatastore()  = this.dataStore.data.map { preferences  ->
        User(
            id = "",
            name = preferences[stringPreferencesKey("userName")].orEmpty(),
            surname = preferences[stringPreferencesKey("userLastname")].orEmpty(),
            firebaseToken = preferences[stringPreferencesKey("userFirebaseKey")].orEmpty(),
            location = null,
            expirationDatefirebaseToken = null
        )
    }

    private fun deleteUser() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                getUserDataFromDatastore().collect { userData : User ->
                    val userId : String = userService.getUserByToken(userData.firebaseToken).id
                    val response = userService.deleteUser(userId)
                    if (response.isSuccessful) {
                        Firebase.auth.currentUser?.delete()
                        Toast.makeText(this@DetailedProfileActivity, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@DetailedProfileActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@DetailedProfileActivity, "Error al eliminar usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }


}
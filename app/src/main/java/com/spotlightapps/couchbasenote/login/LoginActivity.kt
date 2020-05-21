package com.spotlightapps.couchbasenote.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.spotlightapps.couchbasenote.AppRepository
import com.spotlightapps.couchbasenote.EventObserver
import com.spotlightapps.couchbasenote.R
import com.spotlightapps.couchbasenote.note_list.NoteListActivity
import com.spotlightapps.couchbasenote.utils.PrefUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val factory = LoginViewModelFactory(AppRepository.getInstance(this))
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        subscribeUI()
        btnLogin.setOnClickListener {
            viewModel.onLoginButtonClicked()
        }
        tvSingUp.setOnClickListener {
            viewModel.onSignUpClicked()
        }
    }


    private fun subscribeUI() {
        viewModel.onLoginClicked.observe(this, EventObserver {
            val profile = viewModel.getUser(etName.text.toString())
            profile?.let {
                if (profile.password == etPassword.text.toString().trim()) {
                    PrefUtils.getInstance(context = this).saveCurrentUser(profile.name)
                    navigateToNoteListActivity()
                } else {
                    Snackbar.make(
                        findViewById(R.id.login_base_layout),
                        R.string.wrong_password,
                        Snackbar.LENGTH_SHORT
                    )
                }
            } ?: Toast.makeText(this, "User is not registered", Toast.LENGTH_SHORT).show()


        })

        viewModel.onSignUpClicked.observe(this, EventObserver {
            viewModel.saveUser(etName.text.toString(), etPassword.text.toString())
        })
    }

    private fun navigateToNoteListActivity() {
        startActivity(Intent(this, NoteListActivity::class.java))
    }


}

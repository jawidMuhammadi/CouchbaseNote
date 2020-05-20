package com.spotlightapps.couchbasenote.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.spotlightapps.couchbasenote.AppRepository
import com.spotlightapps.couchbasenote.EventObserver
import com.spotlightapps.couchbasenote.R
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = LoginViewModelFactory(AppRepository.getInstance(context))
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
        viewModel.onLoginClicked.observe(viewLifecycleOwner, EventObserver {
            val profile = viewModel.getUser(etName.text.toString())
            profile?.let {
                Toast.makeText(context, "Welcome back ${profile.name}", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(context, "User not registered", Toast.LENGTH_SHORT).show()


        })

        viewModel.onSignUpClicked.observe(viewLifecycleOwner, EventObserver {
            viewModel.saveUser(etName.text.toString(), etPassword.text.toString())
        })
    }


}

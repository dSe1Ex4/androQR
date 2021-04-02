package x.cross.androqr.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import x.cross.androqr.databinding.ActivityAuthBinding
import x.cross.androqr.repository.LoginStorage
import x.cross.androqr.viewmodels.AuthViewModel
import x.cross.androqr.viewmodels.AuthViewModelFactory

class AuthActivity : BaseActivity() {
    private lateinit var layout: ActivityAuthBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var loginStorage: LoginStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layout = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(layout.root)

        loginStorage = LoginStorage(applicationContext)

        viewModel = ViewModelProvider(this, AuthViewModelFactory(loginStorage))
                .get(AuthViewModel::class.java)

        with(viewModel){
            sessionID.observe(this@AuthActivity){
                startActivity(Intent(this@AuthActivity, MainActivity::class.java))
            }

            errorMsg.observe(this@AuthActivity){
                //TODO: Вывод ошибки

                layout.loadBar.visibility = View.GONE
                layout.bSignIn.isEnabled = true
            }
        }

        with(layout){
            bSignIn.setOnClickListener {
                viewModel.auth(etUsername.text.toString(), etPassword.text.toString())
                bSignIn.isEnabled = false
                loadBar.visibility = View.VISIBLE
            }
            etUsername.setText(loginStorage.userName ?: "")
        }

    }
}
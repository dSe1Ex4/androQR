package x.cross.androqr.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import x.cross.androqr.R
import x.cross.androqr.databinding.ActivityAuthBinding
import x.cross.androqr.model.dto.ErrorCode
import x.cross.androqr.repository.LoginStorage
import x.cross.androqr.viewmodels.AuthViewModel
import x.cross.androqr.viewmodels.AuthViewModelFactory

class AuthActivity : BaseActivity() {
    private lateinit var layout: ActivityAuthBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var loginStorage: LoginStorage

    private var alertDialog: AlertDialog? = null

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
                when(it.code) {
                    ErrorCode.AUTH -> {showDialog(getString(R.string.alert_message_passowrd_error))}
                    ErrorCode.SERVER -> {showDialog(if (it.message.isNullOrEmpty()) getString(R.string.error_500) else it.message)}
                    else -> {showDialog(it.message ?: getString(R.string.error_wtf))}
                }

                showLoading(true)
            }
        }

        with(layout){
            bSignIn.setOnClickListener {
                viewModel.auth(etUsername.text.toString(), etPassword.text.toString())
                showLoading()
            }
            etUsername.setText(loginStorage.userName ?: "")
        }

    }

    private fun showLoading(isLoaded: Boolean=false){
        with(layout){
            layoutLoadbar.visibility = if (isLoaded) View.INVISIBLE else View.VISIBLE
            loadBar.visibility = if (isLoaded) View.INVISIBLE else View.VISIBLE

            bSignIn.isEnabled = isLoaded
            layout.etUsername.isEnabled = isLoaded
            layout.etPassword.isEnabled = isLoaded
        }
    }

    private fun showDialog(msg: String){
        alertDialog =
            AlertDialog.Builder(this@AuthActivity).setTitle(R.string.alert_warning_title)
            .setMessage(msg)
            .setPositiveButton(R.string.alert_ok) { dialog, id ->
                dialog.cancel()
            }.show()
    }

    override fun onDestroy() {
        super.onDestroy()

        alertDialog?.cancel()
    }
}
package x.cross.androqr.ui

import android.content.Context
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
                if (it.code == ErrorCode.AUTH )
                {
                    val builder = AlertDialog.Builder(this@AuthActivity)
                    builder.setTitle(R.string.alert_warning_title)
                            .setMessage(R.string.alert_message_passowrd_error)
                            .setPositiveButton(R.string.alert_ok) {
                                dialog, id ->  dialog.cancel()
                            }
                }
                else if (it.code == ErrorCode.RESPONSE || it.code == ErrorCode.THROWABLE || it.code == ErrorCode.CLIENT)
                {
                    val builder = AlertDialog.Builder(this@AuthActivity)
                    builder.setTitle(R.string.alert_warning_title)
                            .setMessage(it.message)
                            .setPositiveButton(R.string.alert_ok) {
                                dialog, id ->  dialog.cancel()
                            }
                }

                layout.loadBar.visibility = View.GONE
                layout.layoutLoadbar.visibility= View.GONE
                layout.bSignIn.isEnabled = true
                layout.etUsername.isEnabled = true
                layout.etPassword.isEnabled = true
            }
        }

        with(layout){
            bSignIn.setOnClickListener {
                viewModel.auth(etUsername.text.toString(), etPassword.text.toString())
                bSignIn.isEnabled = false
                layoutLoadbar.visibility = View.VISIBLE
                loadBar.visibility = View.VISIBLE
                layout.etUsername.isEnabled = false
                layout.etPassword.isEnabled = false

            }
            etUsername.setText(loginStorage.userName ?: "")
        }

    }
}
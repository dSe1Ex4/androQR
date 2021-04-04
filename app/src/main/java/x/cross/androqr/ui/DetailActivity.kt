package x.cross.androqr.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import x.cross.androqr.databinding.ActivityDetailBinding
import x.cross.androqr.model.PersonData
import x.cross.androqr.model.RoleData
import x.cross.androqr.viewmodels.DetailViewModel
import x.cross.androqr.viewmodels.DetailViewModelFactory
import com.bumptech.glide.Glide
import x.cross.androqr.R
import x.cross.androqr.model.dto.ErrorCode
import x.cross.androqr.repository.LoginStorage
import x.cross.androqr.ui.recycler.ExtraInfoAdapter

class DetailActivity : BaseActivity() {
    private lateinit var layout: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var loginStorage: LoginStorage

    private lateinit var uuid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layout = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(layout.root)

        supportActionBar?.hide()

        uuid = intent.extras?.getString("UUID").toString()

        loginStorage = LoginStorage(applicationContext)

        val textLoad = getString(R.string.load)
        val testPersonData = PersonData(textLoad, textLoad, textLoad, RoleData(textLoad), mapOf(textLoad to textLoad))

        with(layout){
            testPersonData.let {
/*                etName.setText(it.firstName)
                etParentName.setText(it.threeName)
                etSecondName.setText(it.secondName)
                etRole.setText(it.role.name)*/

                floatButToScanner.setOnClickListener { startActivity(Intent(this@DetailActivity, MainActivity::class.java)) }

                rvExtra.apply {
                    layoutManager = LinearLayoutManager(this@DetailActivity)
                    if (it.extra != null){
                        val list: MutableList<Array<String>> = mutableListOf()
                        for ((k, v) in it.extra) {
                            list.add(arrayOf(k, v))
                        }
                        adapter = ExtraInfoAdapter(list.toTypedArray())
                    }
                }
            }
        }

        viewModel = ViewModelProvider(this, DetailViewModelFactory(loginStorage))
            .get(DetailViewModel::class.java)

        viewModel.imgByteArray.observe(this, { bytes ->
            Glide.with(this@DetailActivity)
                    .load(bytes) //источник изображения указан либо как путь к каталогу, URI или URL адреса.
                    .override(300, 300)
                    .circleCrop() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
                    .into(layout.imgPerson)
        })

        viewModel.personData.observe(this, { personData ->
            with(layout){
                val person = viewModel.personData.value

                person?.let {
                    etName.setText(it.firstName)
                    etParentName.setText(it.threeName)
                    etSecondName.setText(it.secondName)
                    etRole.setText(it.role.name)

                    floatButToScanner.setOnClickListener { startActivity(Intent(this@DetailActivity, MainActivity::class.java)) }

                    rvExtra.apply {
                        layoutManager = LinearLayoutManager(this@DetailActivity)
                        if (it.extra != null){
                            val list: MutableList<Array<String>> = mutableListOf()
                            for ((k, v) in it.extra) {
                                list.add(arrayOf(k, v))
                            }
                            adapter = ExtraInfoAdapter(list.toTypedArray())
                        }
                    }
                }
            }
        })

        viewModel.errorMsg.observe(this, { it ->
            if (it.code == ErrorCode.AUTH )
            {
                val builder = AlertDialog.Builder(this@DetailActivity)
                builder.setTitle(R.string.alert_warning_title)
                        .setMessage(R.string.alert_message_passowrd_error)
                        .setPositiveButton(R.string.alert_auth) {
                            dialog, id ->
                            run {
                                startActivity(Intent(this, AuthActivity::class.java))
                                dialog.cancel()
                            }
                        }
            }
            else if (it.code == ErrorCode.RESPONSE || it.code == ErrorCode.THROWABLE || it.code == ErrorCode.RESPONSE)
            {
                val builder = AlertDialog.Builder(this@DetailActivity)
                builder.setTitle(R.string.alert_warning_title)
                        .setMessage(it.message)
                        .setPositiveButton(R.string.alert_ok) {
                            dialog, id ->  dialog.cancel()
                        }
            }
        })

        viewModel.loadData(uuid)
    }
}
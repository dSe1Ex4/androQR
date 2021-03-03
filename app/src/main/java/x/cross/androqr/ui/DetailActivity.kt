package x.cross.androqr.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import x.cross.androqr.databinding.ActivityDetailBinding
import x.cross.androqr.databinding.ActivitySplashBinding
import x.cross.androqr.model.PersonData
import x.cross.androqr.model.RoleData
import x.cross.androqr.model.WeaponData
import x.cross.androqr.ui.viewmodels.DetailViewModel
import x.cross.androqr.ui.viewmodels.DetailViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var view: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(view.root)

        supportActionBar?.hide()

        viewModel = ViewModelProvider(this, DetailViewModelFactory(
                PersonData("NFD3548d9sd8","Koval","Pidor", "NoFather", WeaponData(1, "Dildo")),
                RoleData(1L, "Pidor")))
                .get(DetailViewModel::class.java)

        with(view){
            val person = viewModel.personData

            etName.setText(person.name)
            etParentName.setText(person.parentName)
            etSecondName.setText(person.secondName)
            etRole.setText(person.name)
            etWeapon.setText(person.weapon.name)

            floatButToScanner.setOnClickListener { startActivity(Intent(this@DetailActivity, MainActivity::class.java)) }
        }

    }
}
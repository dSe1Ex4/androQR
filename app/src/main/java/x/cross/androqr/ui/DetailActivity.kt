package x.cross.androqr.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import x.cross.androqr.databinding.ActivityDetailBinding
import x.cross.androqr.databinding.ActivitySplashBinding
import x.cross.androqr.model.PersonData
import x.cross.androqr.model.RoleData
import x.cross.androqr.model.WeaponData
import x.cross.androqr.ui.viewmodels.DetailViewModel
import x.cross.androqr.ui.viewmodels.DetailViewModelFactory
import com.bumptech.glide.Glide
import x.cross.androqr.ui.recycler.ExtraInfoAdapter

class DetailActivity : BaseActivity() {
    private lateinit var view: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(view.root)

        supportActionBar?.hide()

        val media = "https://i.pinimg.com/564x/c0/45/7b/c0457b818a42251b13a40245940c3ffe.jpg"
        Glide.with(this)
                .load(media) //источник изображения указан либо как путь к каталогу, URI или URL адреса.
                .override(300, 300)
                .circleCrop() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
                .into(view.imgPerson)// представление изображения, куда будет помещено настоящее изображение


        val testPersonData = PersonData("NFD3548d9sd8","Koval","Pidor",
                "NoFather",
                WeaponData(1, "Dildo"),
                RoleData(1L, "Pidor"))

        viewModel = ViewModelProvider(this, DetailViewModelFactory(testPersonData))
                .get(DetailViewModel::class.java)

        with(view){
            val person = viewModel.personData

            etName.setText(person.name)
            etParentName.setText(person.parentName)
            etSecondName.setText(person.secondName)
            etRole.setText(person.role.name)

            floatButToScanner.setOnClickListener { startActivity(Intent(this@DetailActivity, MainActivity::class.java)) }

            rvExtra.apply {
                layoutManager = LinearLayoutManager(this@DetailActivity)
                adapter = ExtraInfoAdapter(arrayOf(
                    arrayOf("Weapon:", "Dildo"),
                    arrayOf("Sex:", "Отсутствует"),
                    arrayOf("Like:", "Vitaly"),
                    arrayOf("Like:", "Vitaly"),
                    arrayOf("Like:", "Vitaly"),
                    arrayOf("Like:", "Vitaly"),
                    arrayOf("Like:", "Vitaly"),
                    arrayOf("Like:", "Vitaly"),
                    arrayOf("OneLove:", "Vitaly")
                ))
            }
        }

    }
}
package x.cross.androqr.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import x.cross.androqr.databinding.ActivityDetailBinding
import x.cross.androqr.model.PersonData
import x.cross.androqr.model.RoleData
import x.cross.androqr.ui.viewmodels.DetailViewModel
import x.cross.androqr.ui.viewmodels.DetailViewModelFactory
import com.bumptech.glide.Glide
import okhttp3.ResponseBody
import retrofit2.Response
import x.cross.androqr.R
import x.cross.androqr.model.dto.rest.InfoPerson
import x.cross.androqr.modules.rest.OnDataLoad
import x.cross.androqr.modules.rest.OnInfoLoad
import x.cross.androqr.modules.rest.RestService
import x.cross.androqr.ui.recycler.ExtraInfoAdapter

class DetailActivity : BaseActivity() {
    private lateinit var view: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(view.root)

        supportActionBar?.hide()

        val textLoad = getString(R.string.load)

        val testPersonData = PersonData(textLoad, textLoad, textLoad, RoleData(textLoad))

        viewModel = ViewModelProvider(this, DetailViewModelFactory(testPersonData))
            .get(DetailViewModel::class.java)

        //TODO Заменить на параметры юзера
        RestService.onPersonImgLoad("47a0479900504cb3ab4a1f626d174d2d",
                "de09b163-83f3-4421-a80c-b4021cdf8a0c",
        object : OnDataLoad{
            override fun onLoaded(bytes: ByteArray) {
                Glide.with(this@DetailActivity)
                        .load(bytes) //источник изображения указан либо как путь к каталогу, URI или URL адреса.
                        .override(300, 300)
                        .circleCrop() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
                        .into(view.imgPerson)
            }

            override fun onFailure(throwable: Throwable) {} //TODO Вывод ошибки
            override fun onError(response: Response<ResponseBody>) { //TODO Вывод ошибки

            }
        })

        RestService.onInfoPersonLoad("47a0479900504cb3ab4a1f626d174d2d",
            "de09b163-83f3-4421-a80c-b4021cdf8a0c", object : OnInfoLoad<InfoPerson>{
                override fun onLoaded(info: InfoPerson) {
                    viewModel.personData = PersonData(
                        info.firstName!!, info.secondName!!, info.threeName!!, RoleData(info.role!!)
                    )

                    with(view){
                        val person = viewModel.personData

                        etName.setText(person.firstName)
                        etParentName.setText(person.threeName)
                        etSecondName.setText(person.secondName)
                        etRole.setText(person.role.name)

                        floatButToScanner.setOnClickListener { startActivity(Intent(this@DetailActivity, MainActivity::class.java)) }

                        rvExtra.apply {
                            layoutManager = LinearLayoutManager(this@DetailActivity)
                            if (info.extra != null){
                                val list: MutableList<Array<String>> = mutableListOf()
                                for ((k, v) in info.extra!!) {
                                    list.add(arrayOf(k, v))
                                }
                                adapter = ExtraInfoAdapter(list.toTypedArray())
                            }
                        }
                    }
                }

                override fun onFailure(throwable: Throwable) { //TODO Вывод ошибки

                }

                override fun onError(response: Response<InfoPerson>) { //TODO Вывод ошибки

                }
        })

        with(view){
            val person = viewModel.personData

            etName.setText(person.firstName)
            etParentName.setText(person.threeName)
            etSecondName.setText(person.secondName)
            etRole.setText(person.role.name)

            floatButToScanner.setOnClickListener { startActivity(Intent(this@DetailActivity, MainActivity::class.java)) }

            rvExtra.apply {
                layoutManager = LinearLayoutManager(this@DetailActivity)
                adapter = ExtraInfoAdapter(arrayOf(
                    arrayOf("Транспорт:", "Машина"),
                    arrayOf("Пол:", "Отсутствует"),
                    arrayOf("Ракетка:", "Личная"),
                    arrayOf("Рейтинг:", "5/10"),
                    arrayOf("Проффессия:", "Спортсмен")
                ))
            }
        }

    }
}
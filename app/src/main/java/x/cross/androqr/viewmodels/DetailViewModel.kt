package x.cross.androqr.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Response
import x.cross.androqr.model.PersonData
import x.cross.androqr.model.RoleData
import x.cross.androqr.model.dto.Error
import x.cross.androqr.model.dto.ErrorCode
import x.cross.androqr.model.dto.rest.InfoPerson
import x.cross.androqr.modules.rest.OnDataLoad
import x.cross.androqr.modules.rest.OnInfoLoad
import x.cross.androqr.modules.rest.RestService
import x.cross.androqr.repository.LoginStorage

class DetailViewModel(var loginStorage: LoginStorage) : ViewModel(){
    private val _personData = MutableLiveData<PersonData>()
    val personData: LiveData<PersonData>
        get() = _personData

    private val _imgByteArray = MutableLiveData<ByteArray>()
    val imgByteArray: LiveData<ByteArray>
        get() = _imgByteArray

    private val _errorMsg = MutableLiveData<Error>()
    val errorMsg: LiveData<Error>
        get() = _errorMsg

    fun loadData(uuid: String) {
        RestService.onPersonImgLoad(loginStorage.sessionId!!, uuid,
            object : OnDataLoad {
                override fun onLoaded(bytes: ByteArray) {
                    _imgByteArray.value = bytes
                }

                override fun onFailure(throwable: Throwable) {
                    _errorMsg.value = Error(throwable.localizedMessage, ErrorCode.THROWABLE)
                }
                override fun onError(response: Response<ResponseBody>) {
                    when(response.code()){
                        401 -> _errorMsg.value = Error(response.message(), ErrorCode.AUTH)
                        else ->  _errorMsg.value = Error(response.message(), ErrorCode.RESPONSE)
                    }
                }
            }
        )

        RestService.onInfoPersonLoad(loginStorage.sessionId!!, uuid,
            object : OnInfoLoad<InfoPerson> {
                override fun onLoaded(info: InfoPerson) {
                    _personData.value = PersonData(
                        info.firstName!!, info.secondName!!, info.threeName!!,
                        RoleData(info.role!!), info.extra!!
                    )
                }

                override fun onFailure(throwable: Throwable) {
                    _errorMsg.value = Error(throwable.localizedMessage, ErrorCode.THROWABLE)
                }
                override fun onError(response: Response<InfoPerson>) {
                    when(response.code()){
                        401 -> _errorMsg.value = Error(response.message(), ErrorCode.AUTH)
                        else ->  _errorMsg.value = Error(response.message(), ErrorCode.RESPONSE)
                    }
                }
            }
        )
    }
}
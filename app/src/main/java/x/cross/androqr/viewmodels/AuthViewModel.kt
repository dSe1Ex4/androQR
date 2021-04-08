package x.cross.androqr.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Response
import x.cross.androqr.model.dto.Error
import x.cross.androqr.model.dto.ErrorCode
import x.cross.androqr.model.dto.rest.UserSession
import x.cross.androqr.modules.rest.OnInfoLoad
import x.cross.androqr.modules.rest.RestService
import x.cross.androqr.repository.LoginStorage

class AuthViewModel(val loginStorage: LoginStorage) : ViewModel() {
    private val _sessionID = MutableLiveData<String>()
    val sessionID: LiveData<String>
        get() = _sessionID

    private val _errorMsg = MutableLiveData<Error>()
    val errorMsg: LiveData<Error>
        get() = _errorMsg


    fun auth(login: String, pass: String){
        loginStorage.userName = login

        RestService.onAuth(login, pass, object : OnInfoLoad<UserSession>{
            override fun onLoaded(info: UserSession) {
                with(info.sessionId){
                    _sessionID.value = this
                    loginStorage.sessionId = this
                }
            }

            override fun onFailure(throwable: Throwable) {
                _errorMsg.value = Error(throwable.localizedMessage, ErrorCode.THROWABLE)
            }

            override fun onError(response: Response<UserSession>) {
                when(response.code()){
                    401 -> _errorMsg.value = Error(response.message(), ErrorCode.AUTH)
                    500 -> _errorMsg.value = Error(null, ErrorCode.SERVER)
                    else ->  _errorMsg.value = Error(response.message(), ErrorCode.RESPONSE)
                }
            }
        })
    }

}
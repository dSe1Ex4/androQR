package x.cross.androqr.modules.rest


import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import x.cross.androqr.Config
import x.cross.androqr.model.dto.rest.InfoPerson
import x.cross.androqr.model.dto.rest.RestInfo
import x.cross.androqr.model.dto.rest.UserSession

/**
 * Обьект RestService предоставляет доступ к севису Rest
 */
object RestService {
    /**
    * Функция onAuth отвечает за авторизацию пользавтеля
    */
    fun onAuth(login: String, password: String, onLoaded: OnInfoLoad<UserSession>) {
        RetrofitClient.getClient()
                ?.auth(login, password)
                ?.enqueue(object : Callback<UserSession>{
                    override fun onResponse(call: Call<UserSession>, response: Response<UserSession>) {
                        if (response.isSuccessful){
                            onLoaded.onLoaded(response.body()!!)
                        } else {
                            onLoaded.onError(response)
                        }
                    }

                    override fun onFailure(call: Call<UserSession>, t: Throwable) {
                        onLoaded.onFailure(t)

                        if (Config.DEBUG) {
                            t.message?.let { Log.w("REST Failure", it) }
                        }
                    }
                })
    }
    /**
     * Функция onInfoPersonLoad отвечает за загрузку данных пользователя
     */
    fun onInfoPersonLoad(session_id: String, uuid: String, onLoaded: OnInfoLoad<InfoPerson>) {
        RetrofitClient.getClient()
                ?.personInfo(session_id, uuid)
                ?.enqueue(object : Callback<InfoPerson> {
                    override fun onResponse(call: Call<InfoPerson>, response: Response<InfoPerson>) {
                        if (response.isSuccessful){
                            onLoaded.onLoaded(response.body()!!)
                        } else {
                            onLoaded.onError(response)
                        }
                    }

                    override fun onFailure(call: Call<InfoPerson>, t: Throwable) {
                        onLoaded.onFailure(t)

                        if (Config.DEBUG) {
                            t.message?.let { Log.wtf("REST Failure", it) }
                        }
                    }
                })
    }
    /**
     * Функция onPersonImgLoad отвечает за загрузку фотографии пользователя
     */
    fun onPersonImgLoad(session_id: String, uuid: String, onLoaded: OnDataLoad){
        RetrofitClient.getClient()
                ?.personImg(session_id, uuid)
                ?.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful){
                            onLoaded.onLoaded(response.body()!!.bytes())
                        } else {
                            onLoaded.onError(response)
                        }

                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        onLoaded.onFailure(t)

                        if (Config.DEBUG) {
                            t.message?.let { Log.w("REST Failure", it) }
                        }
                    }
                })
    }
}

/**
 *Итерфейс загрузки данных
 */
interface OnDataLoad {
    /**
     * При загрузке
     */
    fun onLoaded(bytes: ByteArray)
    /**
     * При неправильной загрузке данныйх
     */
    fun onFailure(throwable: Throwable)
    /**
     *При ошибке
     */
    fun onError(response: Response<ResponseBody>)
}
/**
 *Итерфейс загрузки по RestInfo
 */
interface OnInfoLoad<I : RestInfo> {
    /**
     * При загрузке
     */
    fun onLoaded(info: I)
    /**
     * При неправильной загрузке данныйх
     */
    fun onFailure(throwable: Throwable)
    /**
     *При ошибке
     */
    fun onError(response: Response<I>)
}
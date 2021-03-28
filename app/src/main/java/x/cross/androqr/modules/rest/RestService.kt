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

object RestService {
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

interface OnDataLoad {
    fun onLoaded(bytes: ByteArray)
    fun onFailure(throwable: Throwable)
    fun onError(response: Response<ResponseBody>)
}

interface OnInfoLoad<I : RestInfo> {
    fun onLoaded(info: I)
    fun onFailure(throwable: Throwable)
    fun onError(response: Response<I>)
}
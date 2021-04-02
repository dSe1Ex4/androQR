package x.cross.androqr.modules.rest

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import x.cross.androqr.model.dto.rest.InfoPerson
import x.cross.androqr.model.dto.rest.UserSession

interface RetrofitService {
    @FormUrlEncoded
    @POST("/user/auth")
    fun auth(@Field("login") login: String, @Field("password") password: String): Call<UserSession>

    @FormUrlEncoded
    @POST("/user/person_info")
    fun personInfo(@Field("session_id") session_id: String, @Field("uuid") uuid: String): Call<InfoPerson>

    @FormUrlEncoded
    @POST("/user/get_img")
    fun personImg(@Field("session_id") session_id: String, @Field("uuid") uuid: String): Call<ResponseBody>
}
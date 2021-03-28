package x.cross.androqr.modules.rest

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import x.cross.androqr.Config


object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(): RetrofitService? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(Config.REST_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        return retrofit!!.create(RetrofitService::class.java)
    }
}
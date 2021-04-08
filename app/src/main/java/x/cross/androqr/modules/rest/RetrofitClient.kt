package x.cross.androqr.modules.rest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import x.cross.androqr.Config
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*
import javax.security.cert.CertificateException


object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(): RetrofitService? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(Config.REST_API_BASE_URL)
                    .client(getUnsafeOkHttpClient().build()) // УДАЛИТЕ ЕСЛИ ИСПОЛЬЗУЙТЕ НЕ САМОПОДПИСАННЫЙ СЕРТИФИКАТ!
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        return retrofit!!.create(RetrofitService::class.java)
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                    }

                    override fun getAcceptedIssuers(): Array<out X509Certificate> {
                        return arrayOf()
                    }

                }
            )

            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { hostname, _ -> kotlin.run {
                if (Config.REST_API_BASE_URL.contains(hostname)){
                    return@run true
                }
                false
            } }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
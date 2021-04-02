package x.cross.androqr.repository

import android.content.Context
import android.content.SharedPreferences

class LoginStorage(ctx: Context) {
    companion object{
        const val PREF_NAME = "LoginStorage"
        const val PREF_SESSION_ID_NAME = "SESSION_ID"
        const val PREF_USERNAME_NAME = "USERNAME"
    }

    var storage: SharedPreferences? = null
    var context: Context? = null

    var sessionId: String?
        get() = storage!!.getString(PREF_SESSION_ID_NAME, null)
        set(value) = storage!!.edit().putString(PREF_SESSION_ID_NAME, value).apply()

    var userName: String?
        get() = storage!!.getString(PREF_USERNAME_NAME, null)
        set(value) = storage!!.edit().putString(PREF_USERNAME_NAME, value).apply()


    init {
        ctx.let {
            context = it
            storage = it.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }
}
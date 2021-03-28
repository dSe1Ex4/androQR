package x.cross.androqr.model.dto.rest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserSession : RestInfo {
    @Expose
    @SerializedName("session_id")
    var sessionId: String? = null
}
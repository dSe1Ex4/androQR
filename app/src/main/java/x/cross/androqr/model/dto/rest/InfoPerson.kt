package x.cross.androqr.model.dto.rest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class InfoPerson() : RestInfo {
    @Expose
    @SerializedName("eventname")
    var eventName: String? = null

    @Expose
    @SerializedName("role")
    var role: String? = null

    @Expose
    @SerializedName("firstname")
    var firstName: String? = null

    @Expose
    @SerializedName("secondname")
    var secondName: String? = null

    @Expose
    @SerializedName("threename")
    var threeName: String? = null

    @Expose
    @SerializedName("extra")
    var extra: Map<String, String>? = null

    constructor(eventName: String? = null,
                        role: String? = null,
                        firstName: String? = null,
                        secondName: String? = null,
                        threeName: String? = null,
                        extra: Map<String, String>? = null) : this()
}
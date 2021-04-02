package x.cross.androqr.model.dto

data class Error(val message: String?, val code:ErrorCode)

enum class ErrorCode(val id: Int){
    AUTH(401), RESPONSE(400), CLIENT(0), THROWABLE(1)
}
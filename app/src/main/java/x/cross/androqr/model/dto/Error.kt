package x.cross.androqr.model.dto

data class Error(val message: String?, val code:ErrorCode)

enum class ErrorCode(val id: Int){
    /**
     * Ошибка Аунтификации
    * */
    AUTH(401),
    /**
     * Ошибка ответа
     * */
    RESPONSE(400),
    /**
     * Клиентская ошибка
     * */
    CLIENT(0),
    /**
     * Ошибка исключения
     * */
    THROWABLE(1)
}
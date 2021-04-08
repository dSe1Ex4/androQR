package x.cross.androqr

/**
 * Объект глобальной конфигурации проекта
 */
object Config {
    /**
     *Это дебаг
     */
    const val DEBUG = true

    /**
     * Базавая ссылка REST API
     */
    const val REST_API_BASE_URL = "https://212.22.66.109:8080/" //УДАЛИТЕ <.client(getUnsafeOkHttpClient().build())> ЕСЛИ ИСПОЛЬЗУЙТЕ НЕ САМОПОДПИСАННЫЙ СЕРТИФИКАТ! в x.cross.androqr.modules.rest.RetrofitClient
}
package x.cross.androqr.viewmodels

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import x.cross.androqr.repository.LoginStorage

@Suppress("UNCHECKED_CAST")
@Keep
class DetailViewModelFactory(private val loginStorage: LoginStorage): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(loginStorage) as T
    }
}

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(private val loginStorage: LoginStorage): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(loginStorage) as T
    }
}
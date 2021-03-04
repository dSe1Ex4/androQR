package x.cross.androqr.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import x.cross.androqr.model.PersonData
import x.cross.androqr.model.RoleData

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val personData: PersonData): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(personData) as T
    }
}
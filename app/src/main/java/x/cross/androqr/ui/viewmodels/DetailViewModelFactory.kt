package x.cross.androqr.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import x.cross.androqr.model.PersonData
import x.cross.androqr.model.RoleData

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val personData: PersonData,
                             private val roleData: RoleData): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(personData, roleData) as T
    }
}
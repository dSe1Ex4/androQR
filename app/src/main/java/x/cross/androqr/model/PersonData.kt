package x.cross.androqr.model

data class PersonData(val uid: String, val name: String, val secondName: String,
                      val parentName: String="", val weapon: WeaponData, val role: RoleData)
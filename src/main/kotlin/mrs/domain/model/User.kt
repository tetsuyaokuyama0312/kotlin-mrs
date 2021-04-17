package mrs.domain.model

import java.io.Serializable
import javax.persistence.*

/**
 * ユーザーを表すクラス。
 */
@Entity
@Table(name = "usr")
data class User(@Id var userId: String?, var password: String?, var firstName: String?, var lastName: String?,
                @Enumerated(EnumType.STRING) var roleName: RoleName?) : Serializable {
    constructor(roleName: RoleName) : this(null, null, null, null, roleName)
    constructor() : this(null, null, null, null, null)
}
package mrs.app.user

import javax.validation.constraints.NotBlank

/**
 * ユーザー登録フォームを表すクラス。
 */
data class UserForm(@field:NotBlank(message = "必須です") var lastName: String?,
                    @field:NotBlank(message = "必須です") var firstName: String?,
                    @field:NotBlank(message = "必須です") var userId: String?,
                    @field:NotBlank(message = "必須です") var password: String?) {
    constructor() : this(null, null, null, null)
}
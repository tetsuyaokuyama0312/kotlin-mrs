package mrs.domain.service.user

import mrs.domain.model.User

/**
 * ユーザー登録インタフェース。
 */
interface UserRegistrationService {
    /**
     * 指定されたユーザーをリポジトリに登録する。
     *
     * @param user ユーザー
     */
    fun register(user: User)
}
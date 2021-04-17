package mrs.domain.repository.user

import mrs.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * ユーザーのリポジトリインタフェース。
 */
interface UserRepository : JpaRepository<User, String>
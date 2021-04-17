package mrs.domain.service.user.impl

import mrs.domain.model.User
import mrs.domain.repository.user.UserRepository
import mrs.domain.service.user.AlreadyUserRegisteredException
import mrs.domain.service.user.UserRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

/**
 * ユーザー登録サービス実装クラス。
 */
@Service
@Transactional
class UserRegistrationServiceImpl : UserRegistrationService {
    /** パスワードエンコーダー  */
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    /** ユーザーリポジトリ  */
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun register(user: User) {
        // Controllerでvalidate済みなのでuserIdはNotNull
        val alreadyUser = userRepository.findById(requireNotNull(user.userId))
        if (alreadyUser.isPresent) {
            throw AlreadyUserRegisteredException("指定のIDはすでに登録済みです。")
        }

        // パスワードを設定
        user.password = passwordEncoder.encode(user.password)
        // 登録
        userRepository.save(user)
    }
}
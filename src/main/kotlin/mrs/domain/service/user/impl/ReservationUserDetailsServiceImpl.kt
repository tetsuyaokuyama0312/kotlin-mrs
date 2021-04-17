package mrs.domain.service.user.impl

import mrs.domain.repository.user.UserRepository
import mrs.domain.service.user.ReservationUserDetails
import mrs.domain.service.user.ReservationUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * ReservationUserDetails 作成サービスの実装クラス。
 * ユーザー名から Spring Security 認証用ユーザーである ReservationUserDetailsを作成する。
 */
@Service
class ReservationUserDetailsServiceImpl : ReservationUserDetailsService {
    /** ユーザーリポジトリ  */
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(userName: String): ReservationUserDetails {
        // ユーザー取得
        val user = userRepository.findById(userName)
                // ユーザーが取得できなかった場合は例外
                .orElseThrow { UsernameNotFoundException("$userName is not found.") }

        // ユーザーをラップする認証ユーザーを作成して返す
        return ReservationUserDetails(user)
    }
}
package mrs.domain.service.user

import org.springframework.security.core.userdetails.UserDetailsService

/**
 * ReservationUserDetails 作成サービスインタフェース。
 * ユーザー名から Spring Security 認証用ユーザーである ReservationUserDetailsを作成する。
 */
interface ReservationUserDetailsService : UserDetailsService {
    /**
     * ユーザー名から ReservationUserDetailsを作成する。
     *
     * @param userName ユーザー名
     * @return ReservationUserDetails（認証ユーザー）
     */
    override fun loadUserByUsername(userName: String): ReservationUserDetails
}
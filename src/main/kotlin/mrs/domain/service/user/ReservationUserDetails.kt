package mrs.domain.service.user

import mrs.domain.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

/**
 * 会議室予約処理を行うための認証ユーザー。
 */
data class ReservationUserDetails(val user: User) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> =
            AuthorityUtils.createAuthorityList("ROLE_" + user.roleName?.name)

    override fun getPassword() = user.password

    override fun getUsername() = user.userId

    // アカウント期限切れ、アカウントロック、パスワード有効期限切れ、アカウント無効化に関するプロパティは使用しない
    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
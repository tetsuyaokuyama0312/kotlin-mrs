package mrs

import mrs.domain.service.user.ReservationUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Spring Security を使用するための設定を行う Config クラス。
 */
@Configuration
@EnableWebSecurity // Spring Security のWeb連携機能（CSRF対策など）を有効にする
@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize, @PostAuthorize を有効にするため、prePostEnabled を true にする
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    /** ReservationUserDetails 作成サービス  */
    @Autowired
    private lateinit var userDetailsService: ReservationUserDetailsService

    /**
     * パスワードのエンコードアルゴリズムとして BCrypt を使用した BCryptPasswordEncoder を使用する。
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                // /js 以下と /css 以下へのアクセスは常に許可する
                .antMatchers("/js/**", "/css/**").permitAll()
                // 上記以外へのアクセスは認証を要求する
                .antMatchers("/**").authenticated()
                .and()
                // フォーム認証を行う
                .formLogin()
                // ログイン画面
                .loginPage("/loginForm")
                // 認証URL
                .loginProcessingUrl("/login")
                // ユーザー名のリクエストパラメータ名
                .usernameParameter("username")
                // パスワードのリクエストパラメータ名
                .passwordParameter("password")
                // 認証成功時の遷移先
                .defaultSuccessUrl("/rooms", true)
                // 認証失敗時の遷移先
                .failureUrl("/loginForm?error=true")
                // ログイン画面、認証URL、認証失敗時の遷移先へのアクセスは常に許可する
                .permitAll()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        // 指定の UserDetailsService と PasswordEncoder を使用して認証を行う
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }
}
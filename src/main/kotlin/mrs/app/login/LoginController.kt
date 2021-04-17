package mrs.app.login

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * ログイン画面のコントローラ。
 */
@Controller
class LoginController {

    /**
     * ログイン画面へ遷移する。
     *
     * @return ログイン画面の論理パス
     */
    @RequestMapping("loginForm")
    fun loginForm() = "login/loginForm"
}
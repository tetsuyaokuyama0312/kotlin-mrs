package mrs.app.user

import mrs.domain.model.RoleName
import mrs.domain.model.User
import mrs.domain.service.user.AlreadyUserRegisteredException
import mrs.domain.service.user.UserRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

/**
 * ユーザー登録画面のコントローラクラス。
 */
@Controller
@RequestMapping("user")
class UserRegistrationController {
    /** ユーザーリポジトリ  */
    @Autowired
    private lateinit var userRegistrationService: UserRegistrationService

    /**
     * ユーザー登録フォーム作成。
     *
     * @return ユーザー登録フォーム
     */
    @ModelAttribute
    fun setupForm() = UserForm()

    /**
     * ユーザー登録画面へ遷移する。
     *
     * @return ユーザー登録画面の論理パス
     */
    @RequestMapping(method = [RequestMethod.GET])
    fun userForm() = "user/userForm"

    /**
     * ユーザーを登録する。
     *
     * @return ユーザー登録画面の論理パス
     */
    @RequestMapping(method = [RequestMethod.POST])
    fun registerUser(@Validated userForm: UserForm, bindingResult: BindingResult, model: Model): String {
        // エラーがあるかチェック
        if (bindingResult.hasErrors()) {
            // エラーがある場合は登録処理を実施せず、フォーム表示画面へ遷移させる
            return userForm()
        }

        // 登録
        val user = createUserFromUserForm(userForm)
        try {
            userRegistrationService.register(user)
            model.addAttribute("message", "登録が完了しました")
            // 登録完了メッセージを表示するため、リダイレクトはせずにModelのフォームをリセットする
            model.addAttribute("userForm", setupForm())
        } catch (e: AlreadyUserRegisteredException) {
            model.addAttribute("error", e.message)
        }
        return userForm()
    }

    private fun createUserFromUserForm(userForm: UserForm): User {
        val user = User()
        user.userId = userForm.userId
        user.password = userForm.password
        user.firstName = userForm.firstName
        user.lastName = userForm.lastName
        user.roleName = RoleName.USER
        return user
    }
}
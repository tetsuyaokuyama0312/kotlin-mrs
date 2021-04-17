package mrs.domain.service.user

/**
 * ユーザー登録済みを表す例外クラス
 */
class AlreadyUserRegisteredException(message: String) : RuntimeException(message)
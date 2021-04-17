package mrs.app.reservation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * 終了時刻が開始時刻より後かどうかをチェックするバリデーションクラス。
 */
class EndTimeMustBeAfterStartTimeValidator : ConstraintValidator<EndTimeMustBeAfterStartTime, ReservationForm> {
    /** メッセージ  */
    private lateinit var message: String

    override fun initialize(constraintAnnotaion: EndTimeMustBeAfterStartTime) {
        message = constraintAnnotaion.message
    }

    override fun isValid(value: ReservationForm, context: ConstraintValidatorContext): Boolean {
        // 開始時刻
        val startTime = value.startTime
        // 終了時刻
        val endTime = value.endTime
        if (startTime == null || endTime == null) {
            // 入力値がnullの場合はこのValidatorではチェックせず、他のルール（@NotNullなど）に委譲する
            return true
        }

        // 終了時刻が開始時刻より後であるかチェック
        val endTimeAfterStartTime = endTime.isAfter(startTime)
        if (!endTimeAfterStartTime) {
            // 終了時刻が開始時刻より後になっていない場合
            // デフォルトのエラーメッセージの出し方を無効にして、endTimeプロパティに対してエラーメッセージを設定する
            // これは画面でエラーメッセージを表示する際に、フィールドの横にメッセージを表示したいときに必要となる処理である
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate(message).addPropertyNode("endTime").addConstraintViolation()
        }
        return endTimeAfterStartTime
    }
}
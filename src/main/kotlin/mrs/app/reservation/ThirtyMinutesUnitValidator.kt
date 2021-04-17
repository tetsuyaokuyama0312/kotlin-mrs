package mrs.app.reservation

import java.time.LocalTime
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * 時刻が30分単位になっているかをチェックするバリデーションクラス。
 */
class ThirtyMinutesUnitValidator : ConstraintValidator<ThirtyMinutesUnit, LocalTime> {

    override fun initialize(constraintAnnotaion: ThirtyMinutesUnit) {}

    override fun isValid(value: LocalTime?, context: ConstraintValidatorContext): Boolean {
        // 入力値がnullの場合はこのValidatorではチェックせず、他のルール（@NotNullなど）に委譲する
        return if (value == null) true
        // 30分単位かチェック
        else value.minute % 30 == 0
    }
}
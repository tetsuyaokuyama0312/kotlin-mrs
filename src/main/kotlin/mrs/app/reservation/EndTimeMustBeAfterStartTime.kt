package mrs.app.reservation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * 終了時刻が開始時刻より後かどうかをチェックするバリデーション用のアノテーション。
 */
@MustBeDocumented
@Constraint(validatedBy = [EndTimeMustBeAfterStartTimeValidator::class])
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class EndTimeMustBeAfterStartTime(
        /**
         * エラーメッセージ。
         * デフォルトではプロパティファイルから取得する。
         */
        val message: String = "{mrs.app.reservation.EndTimeMustBeStartTime.message}",

        val groups: Array<KClass<*>> = [],

        val payload: Array<KClass<out Payload>> = []) {

    @MustBeDocumented
    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class List(vararg val value: EndTimeMustBeAfterStartTime)
}
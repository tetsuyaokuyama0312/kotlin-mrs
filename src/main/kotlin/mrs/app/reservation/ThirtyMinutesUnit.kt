package mrs.app.reservation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * 時刻が30分単位になっているかをチェックするバリデーション用のアノテーション。
 */
@MustBeDocumented
@Constraint(validatedBy = [ThirtyMinutesUnitValidator::class])
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ThirtyMinutesUnit(
        /**
         * エラーメッセージ。
         * デフォルトではプロパティファイルから取得する。
         */
        val message: String = "{mrs.app.reservation.ThirtyMinutesUnit.message}",

        val groups: Array<KClass<*>> = [],

        val payload: Array<KClass<out Payload>> = []) {

    @MustBeDocumented
    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class List(vararg val value: ThirtyMinutesUnit)
}
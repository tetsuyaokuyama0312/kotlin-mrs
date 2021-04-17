package mrs.app.reservation

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalTime
import javax.validation.constraints.NotNull

/**
 * 予約フォームを表すクラス。
 */
@EndTimeMustBeAfterStartTime(message = "終了時刻は開始時刻より後にしてください")
data class ReservationForm(
        /** 開始時刻  */
        @field: [ThirtyMinutesUnit(message = "30分単位で入力してください")
        DateTimeFormat(pattern = "HH:mm")
        NotNull(message = "必須です")]
        var startTime: LocalTime? = null,

        /** 終了時刻  */
        @field: [ThirtyMinutesUnit(message = "30分単位で入力してください")
        DateTimeFormat(pattern = "HH:mm")
        NotNull(message = "必須です")]
        var endTime: LocalTime? = null
)
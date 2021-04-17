package mrs.domain.model

import java.io.Serializable
import java.time.LocalTime
import java.util.*
import javax.persistence.*

@Entity
data class Reservation(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var reservationId: Int?,
                       var startTime: LocalTime?, var endTime: LocalTime?,
                       @ManyToOne @JoinColumns(JoinColumn(name = "reserved_date"), JoinColumn(name = "room_id")) var reservableRoom: ReservableRoom?,
                       @ManyToOne @JoinColumn(name = "user_id") var user: User?) : Serializable {
    constructor() : this(null, null, null, null, null)

    /**
     * 予約重複判定。
     *
     * @param target 重複判定の相手
     * @return 重複していればtrue
     */
    fun overlap(target: Reservation): Boolean {
        if (!Objects.equals(reservableRoom?.reservableRoomId, target.reservableRoom?.reservableRoomId)) {
            // 2つの予約の日付・部屋が別の場合は重複していない
            return false
        }

        if (startTime == target.startTime && endTime == target.endTime) {
            // 2つの予約の開始時刻と終了時刻が一致する場合は重複とみなす
            return true
        }

        // 2つの開始時刻と終了時刻が交差しているか、または包含関係にあるかどうかチェック
        return target.endTime?.isAfter(startTime) ?: false && endTime?.isAfter(target.startTime) ?: false
    }
}
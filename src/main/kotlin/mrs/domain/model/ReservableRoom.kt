package mrs.domain.model

import java.io.Serializable
import javax.persistence.*

/**
 * 予約可能な会議室を表すデータクラス。
 */
@Entity
data class ReservableRoom(@EmbeddedId var reservableRoomId: ReservableRoomId?,
                          @ManyToOne @JoinColumn(name = "room_id", insertable = false, updatable = false) @MapsId("roomId")
                          var meetingRoom: MeetingRoom?) : Serializable {
    constructor(reservableRoomId: ReservableRoomId) : this(reservableRoomId, null)
    constructor() : this(null, null)
}
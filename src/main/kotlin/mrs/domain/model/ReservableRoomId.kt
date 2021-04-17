package mrs.domain.model

import java.io.Serializable
import java.time.LocalDate
import javax.persistence.Embeddable

@Embeddable
data class ReservableRoomId(var roomId: Int?, var reservedDate: LocalDate?) : Serializable {
    constructor() : this(null, null)
}
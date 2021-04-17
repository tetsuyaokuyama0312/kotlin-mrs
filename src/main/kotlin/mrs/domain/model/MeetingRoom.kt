package mrs.domain.model

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * 会議室を表すデータクラス。
 */
@Entity
data class MeetingRoom(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val roomId: Int? = null,
                       val roomName: String? = null) : Serializable
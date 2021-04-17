package mrs.domain.repository.reservation

import mrs.domain.model.MeetingRoom
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 会議室リポジトリのインタフェース。
 */
interface MeetingRoomRepository : JpaRepository<MeetingRoom, Int>
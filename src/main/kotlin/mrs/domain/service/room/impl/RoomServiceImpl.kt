package mrs.domain.service.room.impl

import mrs.domain.model.MeetingRoom
import mrs.domain.model.ReservableRoom
import mrs.domain.repository.reservation.MeetingRoomRepository
import mrs.domain.repository.room.ReservableRoomRepository
import mrs.domain.service.room.RoomService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * 会議室情報取得サービスの実装クラス。
 */
@Service
@Transactional
class RoomServiceImpl : RoomService {
    /** 予約可能リポジトリ  */
    @Autowired
    private lateinit var reservableRoomRepository: ReservableRoomRepository

    /** 会議室情報リポジトリ  */
    @Autowired
    private lateinit var meetingRoomRepository: MeetingRoomRepository

    override fun findReservableRooms(date: LocalDate): List<ReservableRoom> {
        return reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(date)
    }

    override fun findMeetingRoom(roomId: Int): MeetingRoom {
        return meetingRoomRepository.getOne(roomId)
    }
}
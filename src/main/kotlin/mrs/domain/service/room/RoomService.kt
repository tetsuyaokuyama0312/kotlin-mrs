package mrs.domain.service.room

import mrs.domain.model.MeetingRoom
import mrs.domain.model.ReservableRoom
import java.time.LocalDate

/**
 * 会議室情報取得サービスのインタフェース。
 */
interface RoomService {
    /**
     * 予約可能会議室取得処理
     *
     * @param date 会議室情報取得対象日付
     * @return 予約可能会議室情報一覧
     */
    fun findReservableRooms(date: LocalDate): List<ReservableRoom>

    /**
     * 会議室情報取得処理
     *
     * @param roomId 部屋ID
     * @return 部屋IDに紐づく会議室情報
     */
    fun findMeetingRoom(roomId: Int): MeetingRoom
}
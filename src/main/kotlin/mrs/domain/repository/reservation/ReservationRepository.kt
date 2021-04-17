package mrs.domain.repository.reservation

import mrs.domain.model.ReservableRoomId
import mrs.domain.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 会議室予約一覧リポジトリのインタフェース。
 */
interface ReservationRepository : JpaRepository<Reservation, Int> {
    /**
     * 指定した会議室の予約一覧を取得する。
     * 開始時間の昇順で取得する。
     *
     * @param reservableRoomId 会議室ID
     * @return 指定した会議室の予約一覧
     */
    fun findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId: ReservableRoomId?): List<Reservation>
}
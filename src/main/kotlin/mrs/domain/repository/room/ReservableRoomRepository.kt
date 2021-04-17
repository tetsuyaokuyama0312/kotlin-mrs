package mrs.domain.repository.room

import mrs.domain.model.ReservableRoom
import mrs.domain.model.ReservableRoomId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import javax.persistence.LockModeType

/**
 * 予約可能会議室リポジトリのインタフェース。
 */
interface ReservableRoomRepository : JpaRepository<ReservableRoom, ReservableRoomId> {
    /**
     * 指定された日時で予約可能な部屋一覧を取得する。
     *
     * @param reservedDate 予約したい日時
     * @return 予約可能な部屋一覧
     */
    @Query("SELECT DISTINCT x FROM ReservableRoom x LEFT JOIN FETCH x.meetingRoom " +
            "WHERE x.reservableRoomId.reservedDate = :date ORDER BY x.reservableRoomId.roomId ASC")
    fun findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(@Param("date") reservedDate: LocalDate): List<ReservableRoom>

    /**
     * 予約する会議室を取得する。
     * 予約する会議室のレコードを取得して悲観的ロックを付与する。 更新が競合した場合は、後のリクエストは先のリクエストが完了するまでブロックする。
     *
     * @param reservableRoomId 予約可能会議室のID
     * @return 予約可能会議室
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findOneForUpdateByReservableRoomId(reservableRoomId: ReservableRoomId?): ReservableRoom?
}
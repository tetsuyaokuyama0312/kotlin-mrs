package mrs.domain.service.reservation

import mrs.domain.model.ReservableRoomId
import mrs.domain.model.Reservation

/**
 * 会議室予約サービスのインタフェース。
 */
interface ReservationService {
    /**
     * 会議室予約一覧取得。
     *
     *
     * 指定された部屋IDから予約一覧を取得する。
     *
     * @param reservableRoomId 予約可能部屋のID(予約日付と部屋IDの複合キー)
     * @return 予約一覧のリスト
     */
    fun findReservations(reservableRoomId: ReservableRoomId): List<Reservation>

    /**
     * 会議室予約処理。
     *
     * @param reservation 予約
     * @return 予約エンティティ
     */
    fun reserve(reservation: Reservation): Reservation

    /**
     * 会議室予約キャンセル処理。
     *
     * @param reservation 予約
     */
    fun cancel(reservation: Reservation)

    /**
     * 予約IDから予約オブジェクトを取得する。
     *
     * @param reservationId 予約ID
     * @return 予約
     */
    fun findOne(reservationId: Int): Reservation
}
package mrs.domain.service.reservation.impl

import mrs.domain.model.ReservableRoomId
import mrs.domain.model.Reservation
import mrs.domain.repository.reservation.ReservationRepository
import mrs.domain.repository.room.ReservableRoomRepository
import mrs.domain.service.reservation.AlreadyReservedException
import mrs.domain.service.reservation.ReservationService
import mrs.domain.service.reservation.UnavailableReservationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.parameters.P
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 会議室予約サービスの実装クラス。
 */
@Service
@Transactional
class ReservationServiceImpl : ReservationService {
    /** 会議室予約一覧リポジトリ  */
    @Autowired
    private lateinit var reservationRepository: ReservationRepository

    /** 予約可能部屋取得リポジトリ  */
    @Autowired
    private lateinit var reservableRoomRepository: ReservableRoomRepository

    override fun findReservations(reservableRoomId: ReservableRoomId): List<Reservation> {
        return reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId)
    }

    override fun reserve(reservation: Reservation): Reservation {
        // 予約する部屋のIDを取得
        val reservableRoomId = reservation.reservableRoom?.reservableRoomId

        // 対象の部屋が予約可能かどうかチェック
        reservableRoomRepository.findOneForUpdateByReservableRoomId(reservableRoomId)
                ?: throw UnavailableReservationException("入力の日付・部屋の組み合わせは予約できません。")

        // 重複チェック
        val overlap = reservationRepository
                .findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId)
                .any { it.overlap(reservation) }
        if (overlap) {
            // 重複している場合は例外をスローする
            throw AlreadyReservedException("入力の時間帯はすでに予約済みです。")
        }

        // 予約情報の登録
        reservationRepository.save(reservation)
        return reservation
    }

    @PreAuthorize("hasRole('ADMIN') or #reservation.user.userId == principal.user.userId") // 認可されなかった場合はAccessDeniedExceptionがスローされる
    override fun cancel(@P("reservation") reservation: Reservation) {
        // キャンセル処理
        reservationRepository.delete(reservation)
    }

    override fun findOne(reservationId: Int): Reservation {
        // 予約IDから予約エンティティを取得
        return reservationRepository.getOne(reservationId)
    }
}
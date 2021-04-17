package mrs.domain.service.reservation

/**
 * 予約不可を表す例外クラス
 */
class UnavailableReservationException(message: String) : RuntimeException(message)
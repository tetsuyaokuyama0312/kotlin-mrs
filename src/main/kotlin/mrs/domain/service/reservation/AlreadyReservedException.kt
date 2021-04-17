package mrs.domain.service.reservation

/**
 * 予約済みを表す例外クラス
 * 指定された日付・部屋の組み合わせは予約済みであることを表現する例外クラス
 */
class AlreadyReservedException(message: String) : RuntimeException(message)
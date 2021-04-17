package mrs.app.reservation

import mrs.domain.model.ReservableRoom
import mrs.domain.model.ReservableRoomId
import mrs.domain.model.Reservation
import mrs.domain.service.reservation.AlreadyReservedException
import mrs.domain.service.reservation.ReservationService
import mrs.domain.service.reservation.UnavailableReservationException
import mrs.domain.service.room.RoomService
import mrs.domain.service.user.ReservationUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalTime

/**
 * 会議室予約画面のコントローラクラス。
 */
@Controller
@RequestMapping("reservations/{date}/{roomId}")
class ReservationsController {
    /** 会議室情報取得サービスクラス  */
    @Autowired
    private lateinit var roomService: RoomService

    /** 会議室予約サービスクラス  */
    @Autowired
    private lateinit var reservationService: ReservationService

    /**
     * フォーム作成。
     *
     * @return
     */
    @ModelAttribute
    fun setupForm(): ReservationForm {
        val form = ReservationForm()
        // デフォルト値
        form.startTime = LocalTime.of(9, 0)
        form.endTime = LocalTime.of(10, 0)
        return form
    }

    /**
     * 予約フォーム画面へ遷移する。
     *
     * @param date 予約希望日時
     * @param roomId 部屋ID
     * @param model 画面モデル
     * @return 遷移先URL(予約フォーム画面)
     */
    @RequestMapping(method = [RequestMethod.GET])
    fun reserveForm(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") date: LocalDate,
                    @PathVariable("roomId") roomId: Int, model: Model): String {
        val reservableRoomId = ReservableRoomId(roomId, date)
        val reservations = reservationService.findReservations(reservableRoomId)

        // 24時間を30分毎に分けて作成したLocalTimeオブジェクトを保持するリストを作成
        val timeList = generateSequence(LocalTime.of(0, 0)) { it.plusMinutes(30) }
                .take(24 * 2).toList()

        model.addAttribute("room", roomService.findMeetingRoom(roomId))
        model.addAttribute("reservations", reservations)
        model.addAttribute("timeList", timeList)
        return "reservation/reserveForm"
    }

    /**
     * 予約処理。
     *
     * @param form
     * @param result
     * @return パス
     */
    @RequestMapping(method = [RequestMethod.POST])
    fun reserve(@Validated form: ReservationForm, bindingResult: BindingResult,
                @AuthenticationPrincipal userDetails: ReservationUserDetails,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") date: LocalDate,
                @PathVariable("roomId") roomId: Int, model: Model): String {
        // エラーがあるかチェック
        if (bindingResult.hasErrors()) {
            // エラーがある場合は予約処理を実施せず、フォーム表示画面へ遷移させる
            return reserveForm(date, roomId, model)
        }

        // 予約関連エンティティ作成
        val reservableRoom = ReservableRoom(ReservableRoomId(roomId, date))
        val reservation = Reservation()
        reservation.startTime = form.startTime
        reservation.endTime = form.endTime
        reservation.reservableRoom = reservableRoom
        reservation.user = userDetails.user

        try {
            // 予約処理
            reservationService.reserve(reservation)
        } catch (e: RuntimeException) {
            when (e) {
                is UnavailableReservationException,
                is AlreadyReservedException -> {
                    // 予約不可、もしくは予約済みの場合は、エラーメッセージを表示して画面に戻る
                    model.addAttribute("error", e.message)
                    return reserveForm(date, roomId, model)
                }
                else -> throw e
            }
        }
        return "redirect:/reservations/{date}/{roomId}"
    }

    /**
     * キャンセル処理。
     *
     * @param reservationId
     * @param roomId
     * @param date
     * @param model
     * @return
     */
    @RequestMapping(method = [RequestMethod.POST], params = ["cancel"])
    fun cancel(@RequestParam("reservationId") reservationId: Int,
               @PathVariable("roomId") roomId: Int,
               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") date: LocalDate,
               model: Model): String {
        try {
            // キャンセル処理
            val reservation = reservationService.findOne(reservationId)
            reservationService.cancel(reservation)
        } catch (e: AccessDeniedException) {
            model.addAttribute("error", e.message)
            return reserveForm(date, roomId, model)
        }
        return "redirect:/reservations/{date}/{roomId}"
    }
}
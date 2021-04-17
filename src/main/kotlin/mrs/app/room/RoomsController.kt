package mrs.app.room

import mrs.domain.service.room.RoomService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.time.LocalDate

/**
 * 会議室一覧画面のコントローラクラス。
 */
@Controller
@RequestMapping("rooms")
class RoomsController {
    /** 会議室情報取得サービスクラス  */
    @Autowired
    private lateinit var roomService: RoomService

    @RequestMapping(method = [RequestMethod.GET])
    fun listRooms(model: Model): String {
        val today = LocalDate.now()

        // 予約可能会議室(ReservableRoom)の一覧を取得する
        val rooms = roomService.findReservableRooms(today)
        model.addAttribute("date", today)
        model.addAttribute("rooms", rooms)
        return "room/listRooms"
    }

    @RequestMapping(path = ["{date}"], method = [RequestMethod.GET])
    fun listRooms(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") date: LocalDate,
                  model: Model): String {
        val rooms = roomService.findReservableRooms(date)
        model.addAttribute("rooms", rooms)
        return "room/listRooms"
    }
}
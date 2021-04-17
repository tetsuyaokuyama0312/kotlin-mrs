package mrs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MrsApplication

fun main(args: Array<String>) {
    runApplication<MrsApplication>(*args)
}

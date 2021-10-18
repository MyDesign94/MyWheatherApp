package IS.WheatherApp.core.util

import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val date: Date = Calendar.getInstance().getTime()
    println(date)
    val simpleDateFormat = SimpleDateFormat("EEEE")
    println(simpleDateFormat)
    val dayInString = simpleDateFormat.format(date)
    println(dayInString)

    fun fff(key: String): List<String> {
        return when(key) {
            "Monday" -> listOf<String>("Today", "Tomorrow", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
            "Tuesday" -> listOf<String>("Today", "Tomorrow", "Thursday", "Friday", "Saturday", "Sunday", "Monday")
            "Wednesday" -> listOf<String>("Today", "Tomorrow", "Friday", "Saturday", "Sunday", "Monday", "Tuesday")
            "Thursday" -> listOf<String>("Today", "Tomorrow", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday")
            "Friday" -> listOf<String>("Today", "Tomorrow", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday")
            "Saturday" -> listOf<String>("Today", "Tomorrow", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
            else ->  listOf<String>("Today", "Tomorrow", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        }
    }
    println(fff("Friday"))

}

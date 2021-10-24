package IS.WheatherApp.feature_wheather.domain.util

import android.annotation.SuppressLint
import com.example.weatherapp.WeatherService.WeatherModels.RowTimeWeatherItems
import com.example.weatherapp.WeatherService.WeatherModels.WeatherDataClass
import java.text.SimpleDateFormat
import java.util.* // ktlint-disable no-wildcard-imports

fun collectionsOfItemsRow(weatherData: WeatherDataClass?, dayTime: Int = 0): MutableList<RowTimeWeatherItems> {
    if (weatherData != null) {
        val currentH = if (weatherData.now_dt.split("T")[1].substring(0, 2).toInt() + (weatherData.info.tzinfo.offset/3600) > 23) {
            weatherData.now_dt.split("T")[1].substring(0, 2).toInt() - (24 - (weatherData.info.tzinfo.offset/3600))
        } else {
            weatherData.now_dt.split("T")[1].substring(0, 2).toInt() + (weatherData.info.tzinfo.offset/3600)
        }
        val items = mutableListOf<RowTimeWeatherItems>()
        for (i in currentH.rangeTo(23)) {
            items.add(
                RowTimeWeatherItems(
                    time = weatherData.forecasts[dayTime].hours[i].hour,
                    weather = weatherData.forecasts[dayTime].hours[i].condition,
                    temperature = weatherData.forecasts[dayTime].hours[i].temp.toString()
                )
            )
        }
        for (i in 0 until currentH) {
            items.add(
                RowTimeWeatherItems(
                    time = weatherData.forecasts[dayTime + 1].hours[i].hour,
                    weather = weatherData.forecasts[dayTime + 1].hours[i].condition,
                    temperature = weatherData.forecasts[dayTime + 1].hours[i].temp.toString()
                )
            )
        }
        return items
    } else return listOfAllItems
}

val listOfAllItems = mutableListOf<RowTimeWeatherItems>(
    RowTimeWeatherItems(time = 0, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 1, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 2, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 3, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 4, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 5, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 6, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 7, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 8, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 9, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 10, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 11, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 12, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 13, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 14, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 15, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 16, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 17, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 18, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 19, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 20, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 21, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 22, weather = "S", temperature = "13"),
    RowTimeWeatherItems(time = 23, weather = "S", temperature = "13"),
)

@SuppressLint("SimpleDateFormat")
fun getListOfDaysOfWeek(): List<String> {
    val date: Date = Calendar.getInstance().time
    val simpleDateFormat = SimpleDateFormat("EEEE")
    return when (simpleDateFormat.format(date)) {
        "Monday" -> listOf<String>("Today", "Tomorrow", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        "Tuesday" -> listOf<String>("Today", "Tomorrow", "Thursday", "Friday", "Saturday", "Sunday", "Monday")
        "Wednesday" -> listOf<String>("Today", "Tomorrow", "Friday", "Saturday", "Sunday", "Monday", "Tuesday")
        "Thursday" -> listOf<String>("Today", "Tomorrow", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday")
        "Friday" -> listOf<String>("Today", "Tomorrow", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday")
        "Saturday" -> listOf<String>("Today", "Tomorrow", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
        else -> listOf<String>("Today", "Tomorrow", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    }
}

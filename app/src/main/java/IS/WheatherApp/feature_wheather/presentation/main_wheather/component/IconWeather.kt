package IS.WheatherApp.feature_wheather.presentation.main_wheather.component

import IS.WheatherApp.R

fun iconWeather(item: String, dayTime: String = "d"): Int {
    if (dayTime == "n") {
        when (item) {
            "partly-cloudy" -> return R.drawable.partly_cloudy_night
            "cloudy" -> return R.drawable.ic_cloudy_night
            "overcast" -> return R.drawable.overcast_night
            "drizzle" -> return R.drawable.overcast_night
            "light-rain" -> return R.drawable.light_rain_night
            "rain" -> return R.drawable.rain
            "moderate-rain" -> return R.drawable.moderate_rain
            "heavy-rain" -> return R.drawable.moderate_rain
            "continuous-heavy-rain" -> return R.drawable.moderate_rain
            "showers" -> return R.drawable.moderate_rain
            "wet-snow" -> return R.drawable.wet_snow
            "light-snow" -> return R.drawable.light_snow
            "snow" -> return R.drawable.snow
            "snow-showers" -> return R.drawable.snow_showers
            "hail" -> return R.drawable.hail
            "thunderstorm" -> return R.drawable.thunderstorm
            "thunderstorm-with-rain" -> return R.drawable.thunderstorm
            "thunderstorm-with-hail" -> return R.drawable.thunderstorm
            else -> {
                return R.drawable.clear_night
            }
        }
    } else {
        when (item) {
            "partly-cloudy" -> return R.drawable.partly_cloudy
            "cloudy" -> return R.drawable.ic_cloudy
            "overcast" -> return R.drawable.overcast
            "drizzle" -> return R.drawable.overcast
            "light-rain" -> return R.drawable.light_rain
            "rain" -> return R.drawable.rain
            "moderate-rain" -> return R.drawable.moderate_rain
            "heavy-rain" -> return R.drawable.moderate_rain
            "continuous-heavy-rain" -> return R.drawable.moderate_rain
            "showers" -> return R.drawable.moderate_rain
            "wet-snow" -> return R.drawable.wet_snow
            "light-snow" -> return R.drawable.light_snow
            "snow" -> return R.drawable.snow
            "snow-showers" -> return R.drawable.snow_showers
            "hail" -> return R.drawable.hail
            "thunderstorm" -> return R.drawable.thunderstorm
            "thunderstorm-with-rain" -> return R.drawable.thunderstorm
            "thunderstorm-with-hail" -> return R.drawable.thunderstorm
            else -> {
                return R.drawable.clear
            }
        }
    }
}

fun iconWeatherForTime(item: String, time: Int = 12): Int {
    if (time > 21 || time < 6) {
        when (item) {
            "partly-cloudy" -> return R.drawable.partly_cloudy_night
            "cloudy" -> return R.drawable.ic_cloudy_night
            "overcast" -> return R.drawable.overcast_night
            "drizzle" -> return R.drawable.overcast_night
            "light-rain" -> return R.drawable.light_rain_night
            "rain" -> return R.drawable.rain
            "moderate-rain" -> return R.drawable.moderate_rain
            "heavy-rain" -> return R.drawable.moderate_rain
            "continuous-heavy-rain" -> return R.drawable.moderate_rain
            "showers" -> return R.drawable.moderate_rain
            "wet-snow" -> return R.drawable.wet_snow
            "light-snow" -> return R.drawable.light_snow
            "snow" -> return R.drawable.snow
            "snow-showers" -> return R.drawable.snow_showers
            "hail" -> return R.drawable.hail
            "thunderstorm" -> return R.drawable.thunderstorm
            "thunderstorm-with-rain" -> return R.drawable.thunderstorm
            "thunderstorm-with-hail" -> return R.drawable.thunderstorm
            else -> {
                return R.drawable.clear_night
            }
        }
    } else {
        when (item) {
            "partly-cloudy" -> return R.drawable.partly_cloudy
            "cloudy" -> return R.drawable.ic_cloudy
            "overcast" -> return R.drawable.overcast
            "drizzle" -> return R.drawable.overcast
            "light-rain" -> return R.drawable.light_rain
            "rain" -> return R.drawable.rain
            "moderate-rain" -> return R.drawable.moderate_rain
            "heavy-rain" -> return R.drawable.moderate_rain
            "continuous-heavy-rain" -> return R.drawable.moderate_rain
            "showers" -> return R.drawable.moderate_rain
            "wet-snow" -> return R.drawable.wet_snow
            "light-snow" -> return R.drawable.light_snow
            "snow" -> return R.drawable.snow
            "snow-showers" -> return R.drawable.snow_showers
            "hail" -> return R.drawable.hail
            "thunderstorm" -> return R.drawable.thunderstorm
            "thunderstorm-with-rain" -> return R.drawable.thunderstorm
            "thunderstorm-with-hail" -> return R.drawable.thunderstorm
            else -> {
                return R.drawable.clear
            }
        }
    }
}

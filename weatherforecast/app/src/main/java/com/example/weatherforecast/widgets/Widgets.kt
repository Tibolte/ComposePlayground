package com.example.weatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.weatherforecast.R
import com.example.weatherforecast.model.WeatherItem
import com.example.weatherforecast.utils.formatDate
import com.example.weatherforecast.utils.formatDateTime
import com.example.weatherforecast.utils.formatDecimals

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather.first().icon}.png"

    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDate(weather.dt).split(",").first(),
                modifier = Modifier.padding(5.dp)
            )
            WeatherStateImage(imageUrl = imageUrl)
            Surface(color = Color(0xFFFFC400), shape = CircleShape) {
                Text(
                    text = weather.weather.first().description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(formatDecimals(weather.temp.max) + "º")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray,
                    )
                ) {
                    append(formatDecimals(weather.temp.min) + "º")
                }
            })
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "Weather image",
        modifier = Modifier.size(80.dp)
    )
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.humidity}%", style = MaterialTheme.typography.caption)
        }
        Row() {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.pressure} psi", style = MaterialTheme.typography.caption)
        }
        Row() {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "Wind icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${formatDecimals(weather.speed)} kph",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun SunsetAndSunriseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise icon",
                modifier = Modifier.size(30.dp)
            )
            Text(text = formatDateTime(weather.sunrise), style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Text(text = formatDateTime(weather.sunset), style = MaterialTheme.typography.caption)
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset icon",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
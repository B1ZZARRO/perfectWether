package com.example.perfectweather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.perfectweather.R
import com.example.perfectweather.data.ApiInterface
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.textChosenCity)
        //homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        //})
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val apiService = ApiInterface()
        var Temp = 0
        var FeelsLike = 0
        GlobalScope.launch(Dispatchers.Main) {
            val UrlModel = apiService.getCurrentWeather("Санкт-Петербург").await()
            Temp = UrlModel.main.temp.toInt()-273
            FeelsLike = UrlModel.main.feels_like.toInt()-273
            textChosenCity.text = UrlModel.name
            textTemp.text = "\n" + Temp.toString() + "°C"
            textInfo.text = "\nОщущается как: " + FeelsLike.toString() + "°C" + "\nСкорость ветра: " + UrlModel.wind.speed.toString() + "м/с" + "\nВлажность: " + UrlModel.main.humidity.toString() + "%" + "\n"
            if (UrlModel.weather[0].main == "Snow"){
                imageView.setImageResource(R.drawable.snow)
                textStatus.text = "Снег"
            }
            if (UrlModel.weather[0].main == "Rain"){
                imageView.setImageResource(R.drawable.rain)
                textStatus.text = "Дождь"
            }
            if (UrlModel.weather[0].main == "Clear"){
                imageView.setImageResource(R.drawable.clear)
                textStatus.text = "Ясно"
            }
            if (UrlModel.weather[0].main == "Clouds"){
                imageView.setImageResource(R.drawable.clouds)
                textStatus.text = "Облачно"
            }
            if (UrlModel.weather[0].main == "Mist"){
                imageView.setImageResource(R.drawable.mist)
                textStatus.text = "Туман"
            }
            /*if (UrlModel.weather.component1().main == ""){
                imageView.setImageResource(R.drawable.minirain)
                textStatus.text = ""
            }*/
        }
    }
}
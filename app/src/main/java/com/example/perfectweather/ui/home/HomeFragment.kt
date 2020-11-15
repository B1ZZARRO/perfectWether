package com.example.perfectweather.ui.home

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.perfectweather.MainActivity
import com.example.perfectweather.R
import com.example.perfectweather.data.ApiInterface
import com.example.perfectweather.ui.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var anim: AnimationDrawable

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
        var sharePref : SharedPreferences = requireParentFragment().requireContext().getSharedPreferences(MainActivity().PREF_NAME, MainActivity().PRIVATE_MODE)
        val editor = sharePref.edit()
        val adapter = ArrayAdapter(
            requireParentFragment().requireContext(),
            android.R.layout.simple_spinner_item,
            DashboardFragment().cityName
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        FavoriteCities?.adapter = adapter
        FavoriteCities.setSelection(sharePref.getInt(MainActivity().SELECT_CITY, 0))
        FavoriteCities.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                (parent.getChildAt(0) as TextView).setTextColor(Color.BLACK)
                (parent.getChildAt(0) as TextView).textSize = 20f
                textStatus.text = "Загрузка..."
                textAdvice.text = ""
                textInfo.text = ""
                textTemp.text = ""
                imageView.setBackgroundColor(Color.parseColor("#FAFAFA"))
                val city : String = parent.getItemAtPosition(position).toString()
                editor.putInt(MainActivity().SELECT_CITY, position)
                editor.apply()

                if(position == 0){
                    textInfo.text = ""
                    textTemp.text = ""
                    textStatus.text = "Выберете город"
                    textAdvice.text = ""
                    imageView.setBackgroundColor(Color.parseColor("#FAFAFA"))
                }
                else{
                    val apiService = ApiInterface()
                    GlobalScope.launch(Dispatchers.Main) {
                        try {
                            val UrlModel = apiService.getCurrentWeather(city).await()
                            Temp = UrlModel.main.temp.toInt()-273
                            FeelsLike = UrlModel.main.feels_like.toInt()-273
                            textTemp.text = "\n" + Temp.toString() + "°C"
                            textInfo.text = "\nОщущается как: " + FeelsLike.toString() + "°C" + "\nСкорость ветра: " + UrlModel.wind.speed.toString() + "м/с" + "\nВлажность: " + UrlModel.main.humidity.toString() + "%"
                            if (UrlModel.weather[0].main == "Snow"){
                                textAdvice.text = "Оденьтесь потеплее"
                                imageView.setBackgroundResource(R.drawable.anim_snow)
                                (imageView.background as AnimationDrawable).start()
                                textStatus.text = "Снег"
                            }
                            if (UrlModel.weather[0].main == "Rain"){
                                textAdvice.text = "Возьмите с собой зонт"
                                imageView.setBackgroundResource(R.drawable.anim_rain)
                                (imageView.background as AnimationDrawable).start()
                                textStatus.text = "Дождь"
                            }
                            if (UrlModel.weather[0].main == "Clear"){
                                if(Temp >= 25){
                                    textAdvice.text = "Отличная погода, чтобы пойти на пляж"
                                }
                                if(Temp <= 25 && Temp >= 15){
                                    textAdvice.text = "Можно пойти погулять"
                                }
                                imageView.setBackgroundResource(R.drawable.anim_clear)
                                (imageView.background as AnimationDrawable).start()
                                textStatus.text = "Ясно"
                            }
                            if (UrlModel.weather[0].main == "Clouds"){
                                if(Temp <= 25 && Temp >= 15){
                                    textAdvice.text = "Можно пойти погулять, но возможно скоро будет дождь"
                                }
                                imageView.setBackgroundResource(R.drawable.anim_cloud)
                                (imageView.background as AnimationDrawable).start()
                                textStatus.text = "Облачно"
                            }
                            if (UrlModel.weather[0].main == "Mist"){
                                if(Temp <= 25 && Temp >= 15){
                                    textAdvice.text = "Можно пойти погулять, но ничего не видно"
                                }
                                imageView.setBackgroundResource(R.drawable.anim_mist)
                                (imageView.background as AnimationDrawable).start()
                                textStatus.text = "Туман"
                            }
                            if(Temp < 0){
                                textAdvice.text = "Оденьтесь потеплее"
                            }
                            if(Temp >= 0 && Temp <= 15){
                                textAdvice.text = "Не слишком холодно и не слишком жарко"
                            }
                            /*if (UrlModel.weather.component1().main == ""){
                                imageView.setImageResource(R.drawable.minirain)
                                textStatus.text = ""
                            }*/
                        }
                        catch (e: Exception){
                            textStatus.text = "Ошибка"
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>){ }
        }

    }
}

class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener{
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        textStatus.text = "Hello"
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}
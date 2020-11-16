package com.example.perfectweather.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.perfectweather.R

class DashboardFragment : Fragment() {

    val cityName = arrayOf(
        "Астрахань",
        "Архангельск",
        "Барнаул",
        "Белгород",
        "Владивосток",
        "Воронеж",
        "Иркутск",
        "Казань",
        "Калининград",
        "Калуга",
        "Красногорск",
        "Краснодар",
        "Липецк",
        "Минск",
        "Москва",
        "Мурманск",
        "Нижний Новгород",
        "Новосибирск",
        "Омск",
        "Пермь",
        "Подольск",
        "Ростов на Дону",
        "Самара",
        "Санкт-Петербург",
        "Сочи",
        "Томск",
        "Уфа",
        "Хабаровск",
        "Челябинск",
        "Якутск"
    )

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mdf = DialogDashFragment()
        val manager = activity?.supportFragmentManager
        if (manager != null) {
            mdf.show(manager, "myDialog")
        }
    }
}
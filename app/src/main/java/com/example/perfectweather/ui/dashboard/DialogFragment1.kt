package com.example.perfectweather.ui.dashboard

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class DialogFragment1 : DialogFragment() {

    private val cityName = arrayOf("Москва", "Санкт-Петербург", "Екатеринбург", "Сызрань", "Рязань", "Тула", "Подольск", "Балашиха", "Мытищи", "Королев", "Долгопрудный", "Химки", "Красногорск", "Одинцово", "Троицк")
    //private val checkItems = booleanArrayOf(false, false, false)
    //val cityName = resources.getStringArray(R.array.ArrayCity)
    val checkItems = booleanArrayOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Выбор городов")
                .setMultiChoiceItems(cityName, checkItems){
                        dialog, which, isChecked ->
                    checkItems[which] = isChecked
                    val name = cityName[which]
                    Toast.makeText(activity, name, Toast.LENGTH_LONG).show()
                }
                .setPositiveButton("Готов"){
                        dialog, id ->
                    for(i in cityName.indices){
                        val checked = checkItems[i]
                        if(checked){
                            Log.i("Dialog", cityName[i])
                            //st+=cityName[i]+" "
                        }
                    }
                    ////////////////////
                }
                .setNegativeButton("Отмена"){
                        dialog, _ -> dialog.cancel()
                }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")

        return super.onCreateDialog(savedInstanceState)

    }
}
package com.example.perfectweather.ui.dashboard

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*
import android.R.string
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.ArrayAdapter
import com.example.perfectweather.MainActivity
import com.example.perfectweather.ui.home.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DialogDashFragment : DialogFragment(){
    val checkItems = BooleanArray(30, {false})
    //var sharePref = activity?.getSharedPreferences(MainActivity().PREF_NAME, Context.MODE_PRIVATE)
    //val editor = sharePref?.edit()

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Выбор городов")
                .setMultiChoiceItems(DashboardFragment().cityName, checkItems){
                        dialog, which, isChecked ->
                    checkItems[which] = isChecked
                    val name = DashboardFragment().cityName[which]
                    Toast.makeText(activity, name, Toast.LENGTH_LONG).show()
                }
                .setPositiveButton("Готов"){
                        dialog, id ->
                    var count : Int = 0
                    for(i in DashboardFragment().cityName.indices){
                        if(checkItems[i]){
                            count++
                        }
                    }
                    var citys = arrayOfNulls<String>(count)

                    count = 0
                    for(i in DashboardFragment().cityName.indices){
                        if(checkItems[i]){
                            citys[count] = DashboardFragment().cityName[i].toString()
                            count++
                        }
                    }

                    var sharePref = activity?.getSharedPreferences(MainActivity().PREF_NAME, Context.MODE_PRIVATE)
                    val editor = sharePref?.edit()
                    editor?.putInt(MainActivity().FAVORITES_SIZE, citys.size)
                    for(i in 0..(citys.size - 1)){
                        editor?.putString(MainActivity().FAVORITES_+i.toString(), citys[i].toString())
                    }
                    editor?.apply()
                }
                .setNegativeButton("Отмена"){
                        dialog, _ -> dialog.cancel()
                }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
        super.onCreate(savedInstanceState)
    }

}
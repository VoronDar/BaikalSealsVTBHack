package com.astery.copying.forms_adapter.utils

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.astery.vtb.R

/** simple error window (picture + message) */
class ShowError(text:Int, dr:Int, parent: Fragment) {
    private val view: LinearLayout = parent.layoutInflater.inflate(R.layout.alert_error, null) as LinearLayout

    init{
        view.findViewById<TextView>(R.id.text).text = parent.requireContext().resources.getString(text)
        view.findViewById<ImageView>(R.id.image).setImageDrawable(SD.getDr(dr, parent.requireContext()))
    }

    fun draw():View{
        return view
    }

}
package com.astery.copying.forms_adapter.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.astery.vtb.R

class AlertFormDialogue(fragment: Fragment, title: String, cancelable:Boolean=true):FormDialogue(fragment, title, cancelable) {

    override val view: View = fragment.layoutInflater.inflate(R.layout.info_card,null)
    private val alertDialog:AlertDialog
    private val adb: AlertDialog.Builder = AlertDialog.Builder(fragment.requireContext())

    init {
        adb.setView(view)
        alertDialog = adb.create()
        (view.findViewById(R.id.title) as TextView).text = title
        //alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }
    override fun close(){
        alertDialog.cancel()
    }

    override fun open() {
        alertDialog.show()
    }

    override fun getLayout(): ViewGroup {
        return view.findViewById(R.id.content)
    }

    override fun getCloseItem(): View {
        TODO("Not yet implemented")
    }
}
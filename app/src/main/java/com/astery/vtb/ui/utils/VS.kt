package com.astery.vtb.ui.utils

import android.view.View.GONE
import android.view.View.VISIBLE

/** get visible state*/
class VS {
    companion object {
        fun get(vis: Boolean):Int{
            if (vis) return VISIBLE
            return GONE
        }
    }
}
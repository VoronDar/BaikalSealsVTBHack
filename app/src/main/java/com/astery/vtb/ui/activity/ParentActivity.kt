package com.astery.vtb.ui.activity

import android.os.Bundle
import com.astery.vtb.ui.navigation.FragmentNavController

interface ParentActivity {
    fun move(action: FragmentNavController?, bundle: Bundle?)
}
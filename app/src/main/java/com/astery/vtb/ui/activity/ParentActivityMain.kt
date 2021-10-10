package com.astery.vtb.ui.activity

import android.os.Bundle
import com.astery.vtb.model.GameValue
import com.astery.vtb.ui.navigation.FragmentNavController

interface ParentActivityMain {
    fun move(action: FragmentNavController?, bundle: Bundle?)
    fun toggle()
}
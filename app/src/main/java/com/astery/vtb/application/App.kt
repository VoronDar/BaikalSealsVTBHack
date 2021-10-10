package com.astery.vtb.application

import android.app.Application

class App: Application() {

    val container: AppContainer by lazy {
        AppContainer(this)
    };




}
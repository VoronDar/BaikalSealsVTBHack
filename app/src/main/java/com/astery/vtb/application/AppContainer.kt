package com.astery.vtb.application

import android.content.Context
import com.astery.vtb.local_storage.AppDatabase
import com.astery.vtb.local_storage.rx_utils.Repository

class AppContainer(context: Context) {

    private val lDatabase:AppDatabase = AppDatabase.getDatabase(context);
    var repository = Repository(lDatabase)

}
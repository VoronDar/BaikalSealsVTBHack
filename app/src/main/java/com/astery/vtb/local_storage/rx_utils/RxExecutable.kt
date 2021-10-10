package com.astery.vtb.local_storage.rx_utils

abstract class RxExecutable {
    abstract fun doSomething()
    abstract fun onCompleteListener()
    abstract fun onErrorListener()
}
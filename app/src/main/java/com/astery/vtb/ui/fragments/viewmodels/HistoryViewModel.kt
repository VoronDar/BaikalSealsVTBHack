package com.astery.vtb.ui.fragments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astery.vtb.local_storage.rx_utils.Repository
import com.astery.vtb.model.*
import com.astery.vtb.ui.fragments.HistoryFragment
import io.reactivex.observers.DisposableSingleObserver

class HistoryViewModel: ViewModel() {

    private lateinit var fragment: HistoryFragment
    private lateinit var repository: Repository


    private val inv: MutableLiveData<List<InvestHistory>> = MutableLiveData()
    val investions:LiveData<List<InvestHistory>>
        get() = inv

    private val chr: MutableLiveData<List<ChoresHistory>> = MutableLiveData()
    val chores:LiveData<List<ChoresHistory>>
        get() = chr


    fun setup(fragment: HistoryFragment, repository: Repository) {
        this.fragment = fragment
        this.repository = repository
    }


    fun getInvestHistory(){
        repository.getInvestHistory(object: DisposableSingleObserver<List<InvestHistory>>() {
            override fun onSuccess(t: List<InvestHistory>) {
                inv.value = t

            }

            override fun onError(e: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getChoresHistory(){
        repository.getChoresHistory(object: DisposableSingleObserver<List<ChoresHistory>>() {
            override fun onSuccess(t: List<ChoresHistory>) {
                chr.value = t

            }

            override fun onError(e: Throwable) {
            }
        })
    }

}
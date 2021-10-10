package com.astery.vtb.ui.fragments.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astery.vtb.local_storage.rx_utils.Repository
import com.astery.vtb.model.ActionEntity
import com.astery.vtb.model.ActionType
import com.astery.vtb.model.GameValue
import com.astery.vtb.model.InvestHistory
import com.astery.vtb.repository.preferences.TransportPreferences
import com.astery.vtb.ui.activity.ParentActivityMain
import com.astery.vtb.ui.fragments.ActionsFragment
import io.reactivex.observers.DisposableSingleObserver

class ActionsViewModel: ViewModel() {

    private lateinit var fragment: ActionsFragment
    private lateinit var repository: Repository


    private val act: MutableLiveData<List<ActionEntity>> = MutableLiveData()
    val actions:LiveData<List<ActionEntity>>
        get() = act


    fun setup(fragment: ActionsFragment, repository: Repository){
        this.fragment = fragment
        this.repository = repository


    }

    fun buy(item:ActionEntity, count:Int, price:Float){
        item.purchasedCount+=count
        repository.loadValue(item, null)

        TransportPreferences.setValue(fragment.requireContext(), GameValue.money,
            TransportPreferences.getValue(fragment.requireContext(), GameValue.money)-price.toInt())

        (fragment.requireActivity() as ParentActivityMain).toggle()

        repository.loadValue(InvestHistory(item.id, item.name, false, count, item.actualPrice,
            TransportPreferences.getValue(fragment.requireContext(), GameValue.iteration)), null)


    }

    fun sell(item:ActionEntity, count:Int, price:Float){
        item.purchasedCount-=count
        repository.loadValue(item, null)

        TransportPreferences.setValue(fragment.requireContext(), GameValue.money,
            TransportPreferences.getValue(fragment.requireContext(), GameValue.money)+price.toInt())

        (fragment.requireActivity() as ParentActivityMain).toggle()

        repository.loadValue(InvestHistory(item.id, item.name, true, count, item.actualPrice,
            TransportPreferences.getValue(fragment.requireContext(), GameValue.iteration)), null)

    }

    fun getActions(){
        repository.getActions(object: DisposableSingleObserver<List<ActionEntity>>() {
            override fun onSuccess(t: List<ActionEntity>) {
                Log.i("main", "suc $t")
                act.value = t

            }

            override fun onError(e: Throwable) {
                TODO("Not yet implemented")
            }
        }, TransportPreferences.getValue(fragment.requireContext(), GameValue.level))
    }

    fun getSelectedActions(){
        repository.getSelectedActions(object: DisposableSingleObserver<List<ActionEntity>>() {
            override fun onSuccess(t: List<ActionEntity>) {
                act.value = t

            }

            override fun onError(e: Throwable) {
            }
        })
    }

}
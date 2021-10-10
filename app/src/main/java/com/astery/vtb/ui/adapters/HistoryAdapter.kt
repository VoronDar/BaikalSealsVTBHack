package com.astery.vtb.ui.adapters

import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.astery.vtb.MainViewModel
import com.astery.vtb.R
import com.astery.vtb.databinding.UnitChoresHistoryBinding
import com.astery.vtb.databinding.UnitInvestHistoryBinding
import com.astery.vtb.model.ChoresHistory
import com.astery.vtb.model.GameValue
import com.astery.vtb.model.InvestHistory

class HistoryAdapter(private val parent:Fragment, private val layout: ViewGroup) {
    private val form: LinearLayout = parent.layoutInflater.inflate(R.layout.form, null) as LinearLayout
    private var invest: List<InvestHistory> = ArrayList()
    private var chores: List<ChoresHistory> = ArrayList()

    init{
        layout.addView(form)
    }

    fun addInvests(invest:List<InvestHistory>){
        this.invest = invest.reversed()
    }

    fun addChores(chores:List<ChoresHistory>){
        this.chores = chores
    }

    fun draw(){
        form.removeAllViews()
        // TODO
        var oldIteration = -1
        for (i in invest){
            if (oldIteration != i.iteration)
                form.addView(InvestHistoryItem(parent, i).drawDay())
            else
                form.addView(InvestHistoryItem(parent, i).view)

            oldIteration = i.iteration
        }
    }
}


class InvestHistoryItem(parent:Fragment, private val invest:InvestHistory){
    private val binding = UnitInvestHistoryBinding.inflate(parent.layoutInflater)
    val view:View
    get() = binding.root

    init{
        binding.count.text = invest.count.toString()
        binding.price.text = invest.price.toString()

        binding.text.text = when(invest.isSell){
            true -> "Продажа: ${invest.actionName}"
            false -> "Покупка: ${invest.actionName}"
        }
    }

    fun drawDay():View{
        binding.iteration.visibility = VISIBLE
        binding.iteration.text = MainViewModel.getIteration(invest.iteration)
        return view
    }
}


class InvestChoresItem(parent:Fragment, chores: ChoresHistory){
    private val binding = UnitChoresHistoryBinding.inflate(parent.layoutInflater)
    val view:View
        get() = binding.root

    init{}
}



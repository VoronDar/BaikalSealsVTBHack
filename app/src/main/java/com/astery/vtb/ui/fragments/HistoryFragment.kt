package com.astery.vtb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.astery.copying.forms_adapter.utils.AlertFormDialogue
import com.astery.vtb.R
import com.astery.vtb.application.App
import com.astery.vtb.databinding.ContentActionsBinding
import com.astery.vtb.model.ActionEntity
import com.astery.vtb.model.ActionType
import com.astery.vtb.model.GameValue
import com.astery.vtb.model.InvestHistory
import com.astery.vtb.repository.preferences.TransportPreferences
import com.astery.vtb.ui.adapters.*
import com.astery.vtb.ui.fragments.viewmodels.ActionsViewModel
import com.astery.vtb.ui.fragments.viewmodels.HistoryViewModel

open class HistoryFragment : XFragment() {

    protected val viewModel: HistoryViewModel by viewModels()
    protected lateinit var itemAdapter: HistoryAdapter
    protected val binding:ContentActionsBinding
    get() = bind as ContentActionsBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bin = ContentActionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.card.elevation = 24f

    }

    override fun prepareAdapters(){
        itemAdapter = HistoryAdapter(this, binding.cardContent)
    }

    override fun setViewModelListeners() {
        viewModel.setup(this, (requireActivity().application as App).container.repository)
        viewModel.getInvestHistory()
        viewModel.investions.observe(viewLifecycleOwner,
            { getActions(it)})
    }

    override fun setListeners() {
    }

    override fun onBackPressed(): Boolean {
        return false
    }


    fun getActions(actions :List<InvestHistory>){
        itemAdapter.addInvests(actions)
        itemAdapter.draw()
    }



}
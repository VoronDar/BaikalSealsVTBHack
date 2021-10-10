package com.astery.vtb.ui.fragments

import com.astery.vtb.application.App

class SelectedActionsFragment : ActionsFragment() {
    override fun setViewModelListeners() {
        viewModel.setup(this, (requireActivity().application as App).container.repository)
        viewModel.getSelectedActions()
        viewModel.actions.observe(viewLifecycleOwner,
            { getActions(it)})
    }
}
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
import com.astery.vtb.repository.preferences.TransportPreferences
import com.astery.vtb.ui.adapters.ItemStyle
import com.astery.vtb.ui.adapters.ItemsAdapter
import com.astery.vtb.ui.adapters.LifeItemAdapter
import com.astery.vtb.ui.adapters.Presentable
import com.astery.vtb.ui.fragments.viewmodels.ActionsViewModel

open class ActionsFragment : XFragment() {

    protected val viewModel: ActionsViewModel by viewModels()
    protected lateinit var itemAdapter: ItemsAdapter
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
        itemAdapter = ItemsAdapter(this, binding.cardContent, object: ItemsAdapter.BlockListener{
            override fun click(pos: Int) {
                val dialogue = AlertFormDialogue(this@ActionsFragment, "Укажите количество", true)
                dialogue.getLayout().addView(PurchaseDialogueContainer(layoutInflater, dialogue, viewModel.actions.value?.get(pos)!!,
                TransportPreferences.getValue(requireContext(), GameValue.money),
                    object: PurchaseDialogueContainer.OnSelectListener{
                        override fun done(item: ActionEntity, count: Int, price: Float) {
                            viewModel.buy(item, count, price)
                            Log.i("main", "buy")
                        }
                    }).view)
                dialogue.open()
            }
        }, ItemStyle())

        val list = ArrayList<ItemsAdapter.BlockListener>()
        list.add(object:ItemsAdapter.BlockListener{
            override fun click(pos: Int) {
                val dialogue = AlertFormDialogue(this@ActionsFragment, "Укажите количество", true)
                dialogue.getLayout().addView(PurchaseDialogueContainer(layoutInflater, dialogue, viewModel.actions.value?.get(pos)!!,
                    TransportPreferences.getValue(requireContext(), GameValue.money),
                    object: PurchaseDialogueContainer.OnSelectListener{
                        override fun done(item: ActionEntity, count: Int, price: Float) {
                            viewModel.buy(item, count, price)
                        }
                    }).view)
                dialogue.open()
            }
        })

        list.add(object:ItemsAdapter.BlockListener{
            override fun click(pos: Int) {
                val dialogue = AlertFormDialogue(this@ActionsFragment, "Укажите количество", true)
                dialogue.getLayout().addView(SellDialogueContainer(layoutInflater, dialogue, viewModel.actions.value?.get(pos)!!,
                    object: SellDialogueContainer.OnSelectListener{
                        override fun done(item: ActionEntity, count: Int, price: Float) {
                            viewModel.sell(item, count, price)
                        }
                    }).view)
                dialogue.open()
            }
        })

        itemAdapter.setActions(list)
    }

    override fun setViewModelListeners() {
        viewModel.setup(this, (requireActivity().application as App).container.repository)
        viewModel.getActions()
        viewModel.actions.observe(viewLifecycleOwner,
            { getActions(it)})
    }

    override fun setListeners() {
    }

    override fun onBackPressed(): Boolean {
        return false
    }


    fun getActions(actions :List<ActionEntity>){
        Log.i("main", "there $actions")
        itemAdapter.clear()
        itemAdapter.addItems(actions)
            .draw()
    }



}
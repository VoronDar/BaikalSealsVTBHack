package com.astery.vtb.ui.fragments

import android.view.LayoutInflater
import android.view.View
import com.astery.copying.forms_adapter.utils.AlertFormDialogue
import com.astery.vtb.databinding.UnitBuyBinding
import com.astery.vtb.model.ActionEntity
import com.google.android.material.slider.Slider

class PurchaseDialogueContainer(inflater: LayoutInflater, dialogue: AlertFormDialogue,
                                item:ActionEntity, money:Int, listener:OnSelectListener) {
    private val binding = UnitBuyBinding.inflate(inflater)
    val view:View
    get() = binding.root

    init{

        val max = money/item.actualPrice.toInt()

        var count = 0f

        binding.seekBar.value = 1f
        binding.seekBar.valueFrom = 1f
        binding.seekBar.valueTo = max.toFloat()
        binding.seekBar.stepSize = 1f
        binding.seekBar.addOnSliderTouchListener(object:Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {}

            override fun onStopTrackingTouch(slider: Slider) {
                count = (slider.value.toInt()*item.actualPrice)
                binding.count.text = "Элементов: ${slider.value.toInt()}"
                binding.price.text = count.toString()
            }
        })

        binding.button.setOnClickListener{
            listener.done(item, binding.seekBar.value.toInt(), count)
            dialogue.close()
        }
    }

    interface OnSelectListener{
        fun done(item:ActionEntity, count:Int, price:Float)
    }

}
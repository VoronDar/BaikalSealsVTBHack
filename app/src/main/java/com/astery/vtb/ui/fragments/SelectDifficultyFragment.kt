package com.astery.vtb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.astery.copying.forms_adapter.utils.SD
import com.astery.copying.forms_adapter.utils.SS
import com.astery.vtb.R
import com.astery.vtb.databinding.ContentSelectLevelBinding
import com.astery.vtb.databinding.UnitSelectLevelBinding
import com.astery.vtb.model.GameValue
import com.astery.vtb.repository.preferences.TransportPreferences
import com.astery.vtb.ui.fragments.viewmodels.DifficultyViewModel
import com.astery.vtb.ui.utils.VAnimator
import com.google.android.material.card.MaterialCardView

class SelectDifficultyFragment : XFragment() {

    private val viewModel: DifficultyViewModel by viewModels()
    private val binding:ContentSelectLevelBinding
    get() = bind as ContentSelectLevelBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bin = ContentSelectLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.i("main", "created")

        binding.card.elevation = 24f

        binding.first.text.text = SS.str(requireContext(), R.string.difficulty_0)
        binding.second.text.text = SS.str(requireContext(), R.string.difficulty_1)
        binding.third.text.text = SS.str(requireContext(), R.string.difficulty_2)

        binding.first.image.setImageDrawable(SD.getDr(R.drawable.difficulty_0, requireContext()))
        binding.second.image.setImageDrawable(SD.getDr(R.drawable.difficulty_1, requireContext()))
        binding.third.image.setImageDrawable(SD.getDr(R.drawable.difficulty_2, requireContext()))

        super.onViewCreated(view, savedInstanceState)
    }

    override fun prepareAdapters(){
    }

    override fun setViewModelListeners() {
        viewModel.setFragment(this)
        setSelectedItem(1, binding.first)
    }

    override fun setListeners() {

        clickToMove(binding.next, null, null)

        binding.first.container.setOnClickListener{ setSelectedItem(1, binding.first) }
        binding.second.container.setOnClickListener{ setSelectedItem(2, binding.second) }
        binding.third.container.setOnClickListener{ setSelectedItem(3, binding.third) }
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    private fun setSelectedItem(pos:Int, it:UnitSelectLevelBinding){
        animateSelected(it.container, it.text)
        TransportPreferences.setValue(requireContext(), GameValue.level, pos)
        when (pos){
            1 -> {
                animateDeselected(binding.second.container, binding.second.text)
                animateDeselected(binding.third.container, binding.third.text)
            } 2 ->{
            animateDeselected(binding.first.container, binding.first.text)
            animateDeselected(binding.third.container, binding.third.text)
            }else ->{
            animateDeselected(binding.first.container, binding.first.text)
            animateDeselected(binding.second.container, binding.second.text)
            }
        }

        binding.difficultyName.text = it.text.text
        viewModel.getDescription(pos)

    }

    fun setDescription(str:String){
        binding.difficultyDescription.text = str
    }

    private fun animateSelected(view:MaterialCardView, text:TextView){
        text.setTextColor(requireContext().getColor(R.color.main_color))
        VAnimator.animateTranslationZ(view, true)
    }
    private fun animateDeselected(view:MaterialCardView, text:TextView){
        text.setTextColor(requireContext().getColor(R.color.black))
        VAnimator.animateTranslationZ(view, false)
    }
}
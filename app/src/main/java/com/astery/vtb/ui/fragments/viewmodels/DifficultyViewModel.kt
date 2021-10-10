package com.astery.vtb.ui.fragments.viewmodels

import androidx.lifecycle.ViewModel
import com.astery.copying.forms_adapter.utils.SS
import com.astery.vtb.R
import com.astery.vtb.ui.fragments.SelectDifficultyFragment

class DifficultyViewModel: ViewModel() {

    private lateinit var fragment: SelectDifficultyFragment

    fun setFragment(fragment: SelectDifficultyFragment){
        this.fragment = fragment
    }

    fun getDescription(difficulty:Int){
        val str = when(difficulty){
            1-> SS.str(fragment.requireContext(), R.string.difficulty_description_0)
            2-> SS.str(fragment.requireContext(), R.string.difficulty_description_1)
            else-> SS.str(fragment.requireContext(), R.string.difficulty_description_2)
        }
        fragment.setDescription(str)
    }
}
package com.astery.vtb.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.astery.copying.forms_adapter.navigation.NavigationTransition
import com.astery.copying.forms_adapter.navigation.SharedAxisTransition
import com.astery.vtb.ui.activity.ParentActivity
import com.astery.vtb.ui.navigation.FragmentNavController
import com.google.android.material.transition.MaterialSharedAxis

abstract class XFragment : Fragment() {


    protected var bin: ViewBinding? = null
    protected val bind get() = bin!!


    override fun onDestroyView() {
        super.onDestroyView()
        bin = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModelListeners()
        setListeners()
        prepareAdapters()
    }

    /** set on click listener to this view that change fragments, but it requires bundle at the moment of declaring*/
    protected fun clickToMove(view: View, type: FragmentNavController?, bundle: Bundle?) {
        view.setOnClickListener {
            (activity as ParentActivity).move(type, bundle)
        }
    }

    /** set on click listener to this view that change fragments, but it requires bundle at the moment of moving*/
    protected fun clickToMove(view: View, type: FragmentNavController?, bundle: BundleGettable) {
        view.setOnClickListener {
            (activity as ParentActivity).move(type, bundle.getBundle())
        }
    }

    protected fun move(type: FragmentNavController, bundle: BundleGettable) {
        (activity as ParentActivity).move(type, bundle.getBundle())
    }

    protected fun move(type: FragmentNavController, bundle: Bundle?) {
        (activity as ParentActivity).move(type, bundle)
    }

    /** set transition between two fragments */
    fun setTransition(transition: NavigationTransition) {
        when (transition::class.java.simpleName) {
            "SharedAxisTransition" -> {
                if (transition.isFirst) {
                    exitTransition = MaterialSharedAxis(
                        (transition as SharedAxisTransition).axis,
                        transition.firstParent
                    )
                    reenterTransition = MaterialSharedAxis(transition.axis, !transition.firstParent)
                } else {
                    enterTransition = MaterialSharedAxis(
                        (transition as SharedAxisTransition).axis,
                        transition.firstParent
                    )
                    returnTransition = MaterialSharedAxis(transition.axis, transition.firstParent)
                }
            }
        }
    }

    /** do smt when backPressed
     * @return false if there is no special action for back*/
    abstract fun onBackPressed(): Boolean


    /** set onClick listeners (mostly for applying actions)*/
    protected abstract fun setListeners()

    /** set listeners to viewModel changes */
    protected abstract fun setViewModelListeners()

    /** set units, layout params to adapters*/
    protected abstract fun prepareAdapters()


    interface BundleGettable {
        fun getBundle(): Bundle
    }
}
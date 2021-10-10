package com.astery.vtb.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.astery.copying.forms_adapter.utils.JobListener
import com.astery.vtb.MainViewModel
import com.astery.vtb.R
import com.astery.vtb.application.App
import com.astery.vtb.databinding.ActivityMainBinding
import com.astery.vtb.model.GameValue
import com.astery.vtb.model.InvestHistory
import com.astery.vtb.ui.fragments.XFragment
import com.astery.vtb.ui.navigation.FragmentNavController
import io.reactivex.observers.DisposableSingleObserver

class MainActivity : AppCompatActivity(), ParentActivityMain {

    private lateinit var binding: ActivityMainBinding

    /** navigation */
    private lateinit var navController: FragmentNavController
    private lateinit var currentFragment: XFragment
    private lateinit var fragmentManager: FragmentManager

    private val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("main", "start")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pushFragment()

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.move_development -> move(FragmentNavController.ACTIONS, null)
                R.id.move_calendar -> move(FragmentNavController.SELECTED_ACTIONS, null)
                R.id.move_third -> move(FragmentNavController.HISTORY, null)
            }
            true
        }

        setListeners()
        setViewModelListeners()
    }

    private fun setListeners(){
        binding.next.setOnClickListener{
            viewModel.endGame(object:JobListener{
                override fun done(success: Boolean) {
                    if (success){
                        val intent = Intent(applicationContext, EndActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        viewModel.endDay(object : JobListener {
                            override fun done(success: Boolean) {
                                move(navController, null)

                            }
                        })
                    }
                }
            })
        }

        binding.money.setOnClickListener{
            viewModel.resetEverything()
        }
    }

    private fun setViewModelListeners(){
        viewModel.setup(applicationContext, (application as App).container.repository)
        viewModel.money.observe(this,
            { t -> binding.money.text = t.toString() })
        viewModel.happiness.observe(this,
            { t -> binding.happiness.text = t.toString() })
        viewModel.iteration.observe(this,
            { t -> binding.day.text = MainViewModel.getIteration(t) })
    }

    /** add first fragment */
    private fun pushFragment(){
        Log.i("main", "push")
        binding.bottomNavigation.selectedItemId = R.id.move_development
        navController = FragmentNavController.ACTIONS
        currentFragment = navController.thisFragment!!

        fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.add(R.id.content, currentFragment)
        ft.commit()
    }

    /** change fragments*/
    override fun move(action: FragmentNavController?, bundle: Bundle?) {
        navController = action!!
        val newFragment = navController.thisFragment
        newFragment!!.arguments = bundle

        currentFragment.setTransition(navController.transition!!)
        newFragment.setTransition(navController.transition!!.reverseCopy()!!)

        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.content, newFragment, newFragment.javaClass.simpleName)

        ft.addToBackStack(newFragment.javaClass.simpleName)
        ft.commit()

        currentFragment = newFragment
    }

    override fun toggle() {
        viewModel.setup()
        move(navController, null)
    }
}
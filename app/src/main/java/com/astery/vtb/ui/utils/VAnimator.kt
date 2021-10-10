package com.astery.vtb.ui.utils

import android.animation.ValueAnimator
import android.os.Build
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.RequiresApi

object VAnimator {

    const val ANIMATION_DURATION:Long = 200


    private fun animate(valueAnimator: ValueAnimator, updater:OnUpdate){
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            updater.update(value)
        }
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.duration = ANIMATION_DURATION

        valueAnimator.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun animateTranslationZ(view: View, up:Boolean=true){
        val start = when(up){
            true -> 0f
            false -> 24f
        }
        val end = when(up){
            true -> 24f
            false -> 0f
        }

        if (view.elevation == end) return

        val valueAnimator = ValueAnimator.ofFloat(start, end)
        animate(valueAnimator, object:OnUpdate{
            override fun update(value: Float) {
                view.elevation = value
            }
        })
    }


    interface OnUpdate{
        fun update(value:Float)
    }
}
package com.jh108.notekeeper.animations

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jh108.notekeeper.R
import kotlinx.android.synthetic.main.activity_animations.*

class AnimationActivity : AppCompatActivity(), Animator.AnimatorListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animations)

    }

    fun rotateAnimation(view: View) {

        val rotateAnimator = AnimatorInflater.loadAnimator(this, R.animator.rotate)
        rotateAnimator?.apply {
            setTarget(targetImage)
            addListener(this@AnimationActivity)
            start()
        }
    }

    fun scaleAnimation(view: View) {

        val scaleAnimator = AnimatorInflater.loadAnimator(this, R.animator.scale)
        scaleAnimator?.apply {
            setTarget(targetImage)
            addListener(this@AnimationActivity)
            start()
        }
    }

    fun translateAnimation(view: View) {

        val translateAnimator = AnimatorInflater.loadAnimator(this, R.animator.translate)
        translateAnimator.apply {
            setTarget(targetImage)
            addListener(this@AnimationActivity)
            start()
        }
    }

    fun fadeAnimation(view: View) {

        val fadeAnimator = AnimatorInflater.loadAnimator(this, R.animator.alpha)
        fadeAnimator.setTarget(targetImage)
        fadeAnimator.addListener(this@AnimationActivity)
        fadeAnimator.start()
    }

    override fun onAnimationStart(animation: Animator?) {
        Snackbar.make(activityAnimations, "Animation Started", Snackbar.LENGTH_SHORT).show()
    }
    override fun onAnimationRepeat(animation: Animator?) {
        Snackbar.make(activityAnimations, "Animation Repeated", Snackbar.LENGTH_SHORT).show()
    }
    override fun onAnimationEnd(animation: Animator?) {
        Snackbar.make(activityAnimations, "Animation End", Snackbar.LENGTH_SHORT).show()
    }
    override fun onAnimationCancel(animation: Animator?) {
        Snackbar.make(activityAnimations, "Animation Cancelled", Snackbar.LENGTH_SHORT).show()
    }
}

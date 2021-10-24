package com.project.setech.util.Animations;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.project.setech.R;

/**
 * The Animations class is used to provide a variety of animations for all the activities that require it.
 */
public class Animations implements IAnimations{
    private Context context;

    public Animations(Context context) {
        this.context = context;
    }

    /**
     * This method provides a fading animation effect.
     * @param view A view section of a screen
     * @return anim An animation of the described effect.
     */
    @Override
    public Animation setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);

        return anim;
    }

    /**
     * This method provides a sliding up animation effect.
     * @param view A view section of a screen
     * @return anim An animation of the described effect.
     */
    @Override
    public Animation setSlideUpAnimation(View view) {
        Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        view.startAnimation(slideUp);

        return slideUp;
    }

    /**
     * This method provides a sliding down animation effect.
     * @param view A view section of a screen
     * @return anim An animation of the described effect.
     */
    @Override
    public Animation setSlideDownAnimation(View view) {
        Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        view.startAnimation(slideDown);

        return slideDown;
    }

    /**
     * This method provides a fall down animation effect.
     * @param view A view section of a screen
     * @return anim An animation of the described effect.
     */
    @Override
    public Animation setFallDownAnimation(View view) {
        Animation fallDown = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
        view.startAnimation(fallDown);

        return fallDown;
    }
}

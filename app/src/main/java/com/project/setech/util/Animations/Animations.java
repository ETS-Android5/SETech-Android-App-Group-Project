package com.project.setech.util.Animations;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.project.setech.R;

public class Animations implements IAnimations{
    private Context context;

    public Animations(Context context) {
        this.context = context;
    }

    @Override
    public Animation setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);

        return anim;
    }

    @Override
    public Animation setSlideUpAnimation(View view) {
        Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        view.startAnimation(slideUp);

        return slideUp;
    }

    @Override
    public Animation setSlideDownAnimation(View view) {
        Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        view.startAnimation(slideDown);

        return slideDown;
    }

    @Override
    public Animation setFallDownAnimation(View view) {
        Animation fallDown = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
        view.startAnimation(fallDown);

        return fallDown;
    }
}

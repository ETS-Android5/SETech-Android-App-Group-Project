package com.project.setech.util.Animations;

import android.view.View;
import android.view.animation.Animation;

/**
 * The IAnimations interface class contains methods regarding the animations of the application.
 */
public interface IAnimations {
    public Animation setFadeAnimation(View view);
    public Animation setSlideUpAnimation(View view);
    public Animation setSlideDownAnimation(View view);
    public Animation setFallDownAnimation(View view);
}

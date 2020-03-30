package com.chris.tiantian.util;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

/**
 * Created by jianjianhong on 19-9-2
 */
public class VisibilityAnimation {
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    public static void scaleOut(final View view, final ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(view)
                .scaleX(0.0F)
                .scaleY(0.0F)
                .alpha(0.0F)
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationStart(View view) {
                        if(listener != null)
                            listener.onAnimationStart(view);
                    }


                    public void onAnimationCancel(View view) {
                        if(listener != null)
                            listener.onAnimationCancel(view);
                    }


                    public void onAnimationEnd(View view) {
                        view.setVisibility(View.GONE);
                        if(listener != null)
                            listener.onAnimationEnd(view);
                    }
                })
                .start();
    }

    public static void scaleIn(View view, final ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(view)
                .scaleX(1.0F)
                .scaleY(1.0F)
                .alpha(1.0F)
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        if(listener != null)
                            listener.onAnimationStart(view);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        view.setVisibility(View.VISIBLE);
                        if(listener != null)
                            listener.onAnimationEnd(view);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        if(listener != null)
                            listener.onAnimationCancel(view);
                    }
                })
                .start();
    }


    public static void translOutToDown(final View view, final ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(view)
                .translationY(view.getHeight())
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationStart(View view) {
                        if(listener != null)
                            listener.onAnimationStart(view);
                    }


                    public void onAnimationCancel(View view) {
                        if(listener != null)
                            listener.onAnimationCancel(view);
                    }


                    public void onAnimationEnd(View view) {
                        view.setVisibility(View.GONE);
                        if(listener != null)
                            listener.onAnimationEnd(view);
                    }
                })
                .start();
    }


    public static void translInFromUp(View view, final ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(view)
                .scaleX(1.0F)
                .scaleY(1.0F)
                .alpha(1.0F)
                .translationY(0)
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        if(listener != null)
                            listener.onAnimationStart(view);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        view.setVisibility(View.VISIBLE);
                        if(listener != null)
                            listener.onAnimationEnd(view);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        if(listener != null)
                            listener.onAnimationCancel(view);
                    }
                })
                .start();
    }

    public static void translTo(View view, float x) {
        ViewCompat.animate(view)
                .setDuration(500)
                .translationX(x)
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .start();
    }

    public static void performAnim(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleY", 0, 0.5f, 1);
        animator.setDuration(1000);
        animator.start();
    }
}

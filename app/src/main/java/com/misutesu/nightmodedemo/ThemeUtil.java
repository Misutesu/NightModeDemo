package com.misutesu.nightmodedemo;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;

/**
 * Created by wujiaquan on 2017/4/8.
 */

public class ThemeUtil {

    public interface OnColorChangeListener {
        void onColorChange(int color);
    }

    public static int getColorFromTheme(Resources.Theme theme, @AttrRes int id) {
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(id, typedValue, true);
        return typedValue.data;
    }
    public static void changeColor(int[] colors, @NonNull final OnColorChangeListener onColorChangeListener) {
        ValueAnimator animator = ValueAnimator
                .ofObject(new ArgbEvaluator(), colors[0], colors[1])
                .setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                onColorChangeListener.onColorChange((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }
}

package com.neptuo.vocabularyapp.ui;

import android.graphics.Color;
import android.view.ViewGroup;

import com.neptuo.vocabularyapp.R;

/**
 * Created by Windows10 on 8/10/2015.
 */
public class PercentageConverter {
    public static int mapToColor(int percentage) {
        if (percentage < 20) {
            return Color.parseColor("#E39090");
        } else if (percentage < 40) {
            return Color.parseColor("#E8B493");
        } else if (percentage < 60) {
            return Color.alpha(0);
        } else if (percentage < 80) {
            return Color.parseColor("#83A57C");
        } else {
            return Color.parseColor("#93AAE3");
        }
    }

    public static int mapToSize(int width, int percentage) {
        return (int)(width * ((double)percentage) / 100);
    }
}

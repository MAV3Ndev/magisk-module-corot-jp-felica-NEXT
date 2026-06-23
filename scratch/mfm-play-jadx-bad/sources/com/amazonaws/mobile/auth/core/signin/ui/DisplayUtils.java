package com.amazonaws.mobile.auth.core.signin.ui;

import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.DisplayMetrics;
import com.felicanetworks.mfc.mfi.MfiClientException;

/* JADX INFO: loaded from: classes.dex */
public class DisplayUtils {
    private static int dpMultiplier;
    private static final DisplayMetrics metrics;

    static {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        metrics = displayMetrics;
        dpMultiplier = displayMetrics.densityDpi / MfiClientException.TYPE_MFICLIENT_ALREADY_STARTED;
    }

    public static int dp(int i) {
        return i * dpMultiplier;
    }

    public static Shape getRoundedRectangleShape(int i) {
        float f = i;
        return new RoundRectShape(new float[]{f, f, f, f, f, f, f, f}, null, null);
    }

    public static ShapeDrawable getRoundedRectangleBackground(int i, int i2) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(getRoundedRectangleShape(i));
        shapeDrawable.getPaint().setColor(i2);
        return shapeDrawable;
    }
}

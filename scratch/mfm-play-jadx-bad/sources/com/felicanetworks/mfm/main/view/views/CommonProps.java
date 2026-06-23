package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes3.dex */
public class CommonProps {
    public static final int BALANCE_VALUE_MAX = 99999;
    public static final int BALANCE_VALUE_MIN = 0;
    static final int FADEIN_ANIMATION_TIME = 1000;
    static final String GUIDANDE_ID_FN00000001 = "FN00000001";
    static final String GUIDANDE_ID_FN00000002 = "FN00000002";
    static final String GUIDANDE_ID_FN00000003 = "FN00000003";
    public static final int POINT_VALUE_MAX = 99999999;
    public static final int POINT_VALUE_MIN = 0;

    public enum FontType {
        REGULAR,
        BOLD
    }

    static Typeface getTypeface(Context context, FontType fontType) {
        int iOrdinal = fontType.ordinal();
        if (iOrdinal == 0) {
            return ResourcesCompat.getFont(context, R.font.roboto_regular);
        }
        if (iOrdinal != 1) {
            return null;
        }
        return ResourcesCompat.getFont(context, R.font.roboto_bold);
    }
}

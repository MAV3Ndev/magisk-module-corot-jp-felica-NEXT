package com.felicanetworks.mfm.main.view.views;

import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import com.felicanetworks.mfm.main.R;

/* JADX INFO: loaded from: classes.dex */
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

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.view.views.CommonProps$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$view$views$CommonProps$FontType;

        static {
            int[] iArr = new int[FontType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$view$views$CommonProps$FontType = iArr;
            try {
                iArr[FontType.REGULAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$view$views$CommonProps$FontType[FontType.BOLD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    static Typeface getTypeface(Context context, FontType fontType) {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$view$views$CommonProps$FontType[fontType.ordinal()];
        if (i == 1) {
            return ResourcesCompat.getFont(context, R.font.roboto_regular);
        }
        if (i != 2) {
            return null;
        }
        return ResourcesCompat.getFont(context, R.font.roboto_bold);
    }
}

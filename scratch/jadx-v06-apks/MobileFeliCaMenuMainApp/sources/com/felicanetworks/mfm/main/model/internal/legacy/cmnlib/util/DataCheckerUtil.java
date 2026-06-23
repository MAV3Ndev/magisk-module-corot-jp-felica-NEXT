package com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util;

import androidx.core.view.InputDeviceCompat;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public final class DataCheckerUtil {
    public static final String MATCH_ALPHA = "^[a-zA-Z]+";
    public static final String MATCH_ALPHA_NUM = "^[0-9a-zA-Z]+";
    public static final String MATCH_ALPHA_SIGN = "^[ -~]+";
    public static final String MATCH_ALPHA_SIGN_COMMA = "([ -\\+]|[\\--\\~])+";
    public static final String MATCH_ALPHA_SIGN_EMPTY = "^[ -~]*";
    public static final String MATCH_HEX_NUM = "^[0-9A-F]+";
    public static final String MATCH_NUMBER = "^[0-9]+";
    public static final String MATCH_NUMPOINT = "^[0-9.]+";
    public static final String MATCH_PACKAGE = "^[0-9a-zA-Z.]+";
    static final String[] MATCH_PROTOCOL = {"http:", "https:", "market:"};
    public static final String MATCH_URI = "[#-;?-Za-z!=_~]+";
    public static final String MSG_INVALID_PROTOCOL = "invalid protocol.";

    public static void checkDecNumberFormat(String str) throws DataCheckerException {
        checkFormat(MATCH_NUMBER, str);
    }

    public static void checkHexNumberFormat(String str) throws DataCheckerException {
        checkFormat(MATCH_HEX_NUM, str);
    }

    public static void checkAlphaFormat(String str) throws DataCheckerException {
        checkFormat(MATCH_ALPHA, str);
    }

    public static void checkAlphaNumberFormat(String str) throws DataCheckerException {
        checkFormat(MATCH_ALPHA_NUM, str);
    }

    public static void checkAlphaSignFormat(String str) throws DataCheckerException {
        checkFormat(MATCH_ALPHA_SIGN, str);
    }

    public static void checkAlphaSignEmptyFormat(String str) throws DataCheckerException {
        checkFormat(MATCH_ALPHA_SIGN_EMPTY, str);
    }

    public static void checkAlphaSignCommaFormat(String str) throws DataCheckerException {
        checkFormat(MATCH_ALPHA_SIGN_COMMA, str);
    }

    public static void checkUrlCharFormat(String str) throws DataCheckerException {
        boolean z = false;
        if (str == null) {
            throw new DataCheckerException(DataCheckerUtil.class, 257, 0);
        }
        String[] strArr = MATCH_PROTOCOL;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            if (str.indexOf(strArr[i]) == 0) {
                z = true;
                break;
            }
            i++;
        }
        if (!z) {
            throw new DataCheckerException(DataCheckerUtil.class, 258, 1, str + " " + MSG_INVALID_PROTOCOL);
        }
        if (!str.matches(MATCH_URI)) {
            throw new DataCheckerException(DataCheckerUtil.class, 259, 1, str);
        }
    }

    public static void checkFixValue(String str, String[] strArr) throws DataCheckerException {
        boolean z = false;
        if (str == null) {
            throw new DataCheckerException(DataCheckerUtil.class, InputDeviceCompat.SOURCE_DPAD, 0);
        }
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            if (str.equals(strArr[i])) {
                z = true;
                break;
            }
            i++;
        }
        if (!z) {
            throw new DataCheckerException(DataCheckerUtil.class, 514, 1, str);
        }
    }

    public static void checkByteLength(String str, int i, boolean z) throws DataCheckerException {
        if (z) {
            if (str.getBytes(StandardCharsets.UTF_8).length != i) {
                throw new DataCheckerException(DataCheckerUtil.class, 769, 0, str);
            }
        } else if (str.getBytes(StandardCharsets.UTF_8).length > i) {
            throw new DataCheckerException(DataCheckerUtil.class, 770, 0, str);
        }
    }

    public static void checkLessEqualLength(int i, int i2) throws DataCheckerException {
        if (i <= i2) {
            return;
        }
        throw new DataCheckerException(DataCheckerUtil.class, InputDeviceCompat.SOURCE_GAMEPAD, 0, i + " greater than " + i2);
    }

    public static void checkEqualLength(int i, int i2) throws DataCheckerException {
        if (i == i2) {
            return;
        }
        throw new DataCheckerException(DataCheckerUtil.class, 1281, 0, i + " not equal " + i2);
    }

    public static void checkMidwayLength(int i, int i2, int i3) throws DataCheckerException {
        if (i3 < i2 || i < i2 || i3 < i) {
            throw new DataCheckerException(DataCheckerUtil.class, 1537, 0, i + " not equal " + i2 + "~" + i3);
        }
    }

    public static void checkNumPointFormat(String str) throws DataCheckerException {
        checkFormat(MATCH_NUMPOINT, str);
    }

    public static void checkFormat(String str, String str2) throws DataCheckerException {
        if (str2 == null) {
            throw new DataCheckerException(DataCheckerUtil.class, 1793, 0);
        }
        if (!str2.matches(str)) {
            throw new DataCheckerException(DataCheckerUtil.class, 1794, 1, str2);
        }
    }
}

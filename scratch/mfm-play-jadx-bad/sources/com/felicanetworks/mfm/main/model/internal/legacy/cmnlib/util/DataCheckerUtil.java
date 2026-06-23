package com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util;

import androidx.core.view.InputDeviceCompat;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes3.dex */
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

    public static void checkDecNumberFormat(String target) throws DataCheckerException {
        checkFormat(MATCH_NUMBER, target);
    }

    public static void checkHexNumberFormat(String target) throws DataCheckerException {
        checkFormat(MATCH_HEX_NUM, target);
    }

    public static void checkAlphaFormat(String target) throws DataCheckerException {
        checkFormat(MATCH_ALPHA, target);
    }

    public static void checkAlphaNumberFormat(String target) throws DataCheckerException {
        checkFormat(MATCH_ALPHA_NUM, target);
    }

    public static void checkAlphaSignFormat(String target) throws DataCheckerException {
        checkFormat(MATCH_ALPHA_SIGN, target);
    }

    public static void checkAlphaSignEmptyFormat(String target) throws DataCheckerException {
        checkFormat(MATCH_ALPHA_SIGN_EMPTY, target);
    }

    public static void checkAlphaSignCommaFormat(String target) throws DataCheckerException {
        checkFormat(MATCH_ALPHA_SIGN_COMMA, target);
    }

    public static void checkUrlCharFormat(String target) throws DataCheckerException {
        if (target == null) {
            throw new DataCheckerException(DataCheckerUtil.class, 257, 0);
        }
        for (String str : MATCH_PROTOCOL) {
            if (target.indexOf(str) == 0) {
                if (!target.matches(MATCH_URI)) {
                    throw new DataCheckerException(DataCheckerUtil.class, 259, 1, target);
                }
                return;
            }
        }
        throw new DataCheckerException(DataCheckerUtil.class, 258, 1, target + " invalid protocol.");
    }

    public static void checkFixValue(String target, String[] targetValue) throws DataCheckerException {
        if (target == null) {
            throw new DataCheckerException(DataCheckerUtil.class, InputDeviceCompat.SOURCE_DPAD, 0);
        }
        for (String str : targetValue) {
            if (target.equals(str)) {
                return;
            }
        }
        throw new DataCheckerException(DataCheckerUtil.class, 514, 1, target);
    }

    public static void checkByteLength(String target, int size, boolean fixFlg) throws DataCheckerException {
        if (fixFlg) {
            if (target.getBytes(StandardCharsets.UTF_8).length != size) {
                throw new DataCheckerException(DataCheckerUtil.class, 769, 0, target);
            }
        } else if (target.getBytes(StandardCharsets.UTF_8).length > size) {
            throw new DataCheckerException(DataCheckerUtil.class, 770, 0, target);
        }
    }

    public static void checkLessEqualLength(int source, int length) throws DataCheckerException {
        if (source <= length) {
            return;
        }
        throw new DataCheckerException(DataCheckerUtil.class, InputDeviceCompat.SOURCE_GAMEPAD, 0, source + " greater than " + length);
    }

    public static void checkEqualLength(int source, int length) throws DataCheckerException {
        if (source == length) {
            return;
        }
        throw new DataCheckerException(DataCheckerUtil.class, 1281, 0, source + " not equal " + length);
    }

    public static void checkMidwayLength(int source, int lengthMin, int lengthMax) throws DataCheckerException {
        if (lengthMax < lengthMin || source < lengthMin || lengthMax < source) {
            throw new DataCheckerException(DataCheckerUtil.class, 1537, 0, source + " not equal " + lengthMin + "~" + lengthMax);
        }
    }

    public static void checkNumPointFormat(String target) throws DataCheckerException {
        checkFormat(MATCH_NUMPOINT, target);
    }

    public static void checkFormat(String format, String target) throws DataCheckerException {
        if (target == null) {
            throw new DataCheckerException(DataCheckerUtil.class, 1793, 0);
        }
        if (!target.matches(format)) {
            throw new DataCheckerException(DataCheckerUtil.class, 1794, 1, target);
        }
    }
}

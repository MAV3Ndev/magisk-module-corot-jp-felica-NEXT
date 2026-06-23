package com.felicanetworks.tis.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

/* JADX INFO: loaded from: classes3.dex */
public final class AccessConfig {
    private static final String COMMON_CHIP_TYPE_KEY = "00000013";
    private static final String DEVICE_FUNCTION_SETTINGS_KEY = "00000017";
    private static final String GP2_CHIP = "1";
    private static final String TAP_INTERACTION_UNAVAILABLE = "1";

    private AccessConfig() {
    }

    public static String getChipType() {
        try {
            return CommonConfig.getInstance().getValue(COMMON_CHIP_TYPE_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    public static boolean isGP2Chip() {
        return "1".equals(getChipType());
    }

    public static String getDeviceFunctionSettings() {
        try {
            return CommonConfig.getInstance().getValue(DEVICE_FUNCTION_SETTINGS_KEY);
        } catch (FileNotFoundException | IOException | ParseException unused) {
            return null;
        }
    }

    public static boolean isTisUnavailable() {
        return getDeviceFunctionSettings() != null;
    }
}

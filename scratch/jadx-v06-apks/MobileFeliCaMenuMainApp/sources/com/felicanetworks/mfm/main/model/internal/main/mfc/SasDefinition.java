package com.felicanetworks.mfm.main.model.internal.main.mfc;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class SasDefinition {
    public static final String AREA_CODE = "000074C0";
    public static final String SYSTEM_CODE = "FE00";
    private static final String[][] reading_info = FeliCaParams.READ_INFO_SAS;
    public static final List<ReadingInfo> READING_INFO = new ArrayList(reading_info.length);

    SasDefinition() {
    }

    static {
        for (String[] strArr : reading_info) {
            READING_INFO.add(new ReadingInfo(strArr[0], (int) Long.parseLong(strArr[1], 16), Integer.parseInt(strArr[2], 10), Integer.parseInt(strArr[3], 10), Integer.parseInt(strArr[4], 10)));
        }
    }

    public static class ReadingInfo {
        public final String blockName;
        public final int blockNumber;
        public final int nodeCode;
        public final int readingLength;
        public final int startPosition;

        public ReadingInfo(String str, int i, int i2, int i3, int i4) {
            this.blockName = str;
            this.nodeCode = i;
            this.blockNumber = i2;
            this.startPosition = i3;
            this.readingLength = i4;
        }

        public String toString() {
            return "ReadingInfo[" + this.blockName + ", " + this.nodeCode + ", " + this.blockNumber + ", " + this.startPosition + ", " + this.readingLength + "]";
        }
    }
}

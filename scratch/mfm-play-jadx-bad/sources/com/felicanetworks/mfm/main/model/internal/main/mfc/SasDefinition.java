package com.felicanetworks.mfm.main.model.internal.main.mfc;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
final class SasDefinition {
    public static final String AREA_CODE = "000074C0";
    public static final List<ReadingInfo> READING_INFO;
    public static final String SYSTEM_CODE = "FE00";
    private static final String[][] reading_info;

    SasDefinition() {
    }

    static {
        String[][] strArr = FeliCaParams.READ_INFO_SAS;
        reading_info = strArr;
        READING_INFO = new ArrayList(strArr.length);
        for (String[] strArr2 : strArr) {
            READING_INFO.add(new ReadingInfo(strArr2[0], (int) Long.parseLong(strArr2[1], 16), Integer.parseInt(strArr2[2], 10), Integer.parseInt(strArr2[3], 10), Integer.parseInt(strArr2[4], 10)));
        }
    }

    public static class ReadingInfo {
        public final String blockName;
        public final int blockNumber;
        public final int nodeCode;
        public final int readingLength;
        public final int startPosition;

        public ReadingInfo(String blockName, int nodeCode, int blockNumber, int startPosition, int readingLength) {
            this.blockName = blockName;
            this.nodeCode = nodeCode;
            this.blockNumber = blockNumber;
            this.startPosition = startPosition;
            this.readingLength = readingLength;
        }

        public String toString() {
            return "ReadingInfo[" + this.blockName + ", " + this.nodeCode + ", " + this.blockNumber + ", " + this.startPosition + ", " + this.readingLength + "]";
        }
    }
}

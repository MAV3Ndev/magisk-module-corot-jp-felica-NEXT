package com.felicanetworks.mfw.i.cmn;

/* JADX INFO: loaded from: classes.dex */
public class Asn1Parser {
    private static final int DEPTH_MAX = 10;
    private static final byte LENGTH_MASK = 127;
    private static final byte LENGTH_MASK_MULTI = -128;
    private static final byte TAG_MASK = -1;

    private static int toUnsignedInt(byte b) {
        return b < 0 ? b + 256 : b;
    }

    private static boolean validateTargetType(byte b) {
        int i = b & TAG_MASK;
        return i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 22 || i == 24 || i == 48;
    }

    public static Asn1Object parse(byte[] bArr) {
        if (bArr.length > 65535) {
            return null;
        }
        Asn1Object asn1Object = new Asn1Object((byte) 0);
        asn1Object.setData(bArr);
        Asn1Object multi = parseMulti(asn1Object, 0);
        if (multi.getChildren() == null) {
            return null;
        }
        return multi;
    }

    private static Asn1Object parseMulti(Asn1Object asn1Object, int i) {
        if (i > 10) {
            asn1Object.setChildren(null);
            return asn1Object;
        }
        int iCountElementNum = countElementNum(asn1Object.getData(), asn1Object.getData().length);
        if (iCountElementNum < 0) {
            asn1Object.setChildren(null);
            return asn1Object;
        }
        Asn1Object[] asn1ObjectArr = new Asn1Object[iCountElementNum];
        int i2 = 0;
        int length = 0;
        for (int i3 = 0; i3 < iCountElementNum; i3++) {
            int[] iArr = {-1, -1};
            boolean zCalculateDataLength = calculateDataLength(asn1Object.getData(), length, iArr, asn1Object.getData().length - length);
            int i4 = iArr[0];
            int i5 = iArr[1];
            if (!zCalculateDataLength) {
                while (i2 < i3) {
                    freeAsn1Object(asn1ObjectArr[i2]);
                    i2++;
                }
                asn1Object.setChildren(null);
                return asn1Object;
            }
            Asn1Object single = parseSingle(asn1Object.getData(), length, i4 + i5, i);
            if (single == null) {
                while (i2 < i3) {
                    freeAsn1Object(asn1ObjectArr[i2]);
                    i2++;
                }
                asn1Object.setChildren(null);
                return asn1Object;
            }
            length += single.getData().length + single.getLengthOfLengthField();
            asn1ObjectArr[i3] = single;
        }
        asn1Object.setChildren(asn1ObjectArr);
        return asn1Object;
    }

    private static Asn1Object parseSingle(byte[] bArr, int i, int i2, int i3) {
        if (i2 < 2 || !validateTargetType(bArr[i])) {
            return null;
        }
        int[] iArr = {-1, -1};
        boolean zCalculateDataLength = calculateDataLength(bArr, i, iArr, i2);
        int i4 = iArr[0];
        int i5 = iArr[1];
        if (!zCalculateDataLength || i4 + i5 != i2) {
            return null;
        }
        Asn1Object asn1Object = new Asn1Object(bArr[i]);
        asn1Object.setLengthOfLengthField(i5);
        byte[] bArr2 = new byte[i4];
        System.arraycopy(bArr, i + i5, bArr2, 0, i4);
        asn1Object.setData(bArr2);
        byte[] bArr3 = new byte[i5];
        System.arraycopy(bArr, i, bArr3, 0, i5);
        asn1Object.setTagAndLengthData(bArr3);
        if (asn1Object.getTagType() == 48) {
            asn1Object = parseMulti(asn1Object, i3 + 1);
            if (asn1Object.getChildren() == null) {
                return null;
            }
        }
        return asn1Object;
    }

    private static boolean calculateDataLength(byte[] bArr, int i, int[] iArr, int i2) {
        int i3;
        if (i2 < 2) {
            return false;
        }
        int i4 = i + 1;
        if ((bArr[i4] & (-128)) != 0) {
            int unsignedInt = toUnsignedInt((byte) (bArr[i4] & LENGTH_MASK));
            if (unsignedInt > 2 || unsignedInt == 0 || i2 < (i3 = unsignedInt + 2)) {
                return false;
            }
            if (unsignedInt != 1) {
                if (unsignedInt == 2) {
                    iArr[0] = (toUnsignedInt(bArr[i + 2]) * 256) + toUnsignedInt(bArr[i + 3]);
                }
                return false;
            }
            iArr[0] = toUnsignedInt(bArr[i + 2]);
            iArr[1] = i3;
        } else {
            iArr[0] = toUnsignedInt(bArr[i4]);
            iArr[1] = 2;
        }
        return true;
    }

    private static int countElementNum(byte[] bArr, int i) {
        if (i == 0) {
            return 0;
        }
        int i2 = 0;
        int i3 = 0;
        while (validateTargetType(bArr[i2])) {
            int[] iArr = {-1, -1};
            boolean zCalculateDataLength = calculateDataLength(bArr, i2, iArr, i - i2);
            int i4 = iArr[0];
            int i5 = iArr[1];
            if (!zCalculateDataLength || (i2 = i2 + i4 + i5) > i) {
                return -1;
            }
            i3++;
            if (i2 == i) {
                return i3;
            }
        }
        return -1;
    }

    private static void freeAsn1Object(Asn1Object asn1Object) {
        Asn1Object[] children;
        if (asn1Object == null || (children = asn1Object.getChildren()) == null) {
            return;
        }
        for (Asn1Object asn1Object2 : children) {
            freeAsn1Object(asn1Object2);
        }
    }
}

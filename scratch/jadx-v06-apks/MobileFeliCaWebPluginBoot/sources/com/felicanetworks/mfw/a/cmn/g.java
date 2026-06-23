package com.felicanetworks.mfw.a.cmn;

/* JADX INFO: compiled from: Asn1Parser.java */
/* JADX INFO: loaded from: classes.dex */
public class g {
    private static boolean a(byte[] bArr, int i, int[] iArr, int i2) {
        int i3;
        if (i2 < 2) {
            return false;
        }
        int i4 = i + 1;
        if ((bArr[i4] & (-128)) != 0) {
            int iG = g((byte) (bArr[i4] & 127));
            if (iG > 2 || iG == 0 || i2 < (i3 = iG + 2)) {
                return false;
            }
            if (iG != 1) {
                if (iG == 2) {
                    iArr[0] = (g(bArr[i + 2]) * 256) + g(bArr[i + 3]);
                }
                return false;
            }
            iArr[0] = g(bArr[i + 2]);
            iArr[1] = i3;
        } else {
            iArr[0] = g(bArr[i4]);
            iArr[1] = 2;
        }
        return true;
    }

    private static int b(byte[] bArr, int i) {
        if (i == 0) {
            return 0;
        }
        int i2 = 0;
        int i3 = 0;
        while (h(bArr[i2])) {
            int[] iArr = {-1, -1};
            boolean zA = a(bArr, i2, iArr, i - i2);
            int i4 = iArr[0];
            int i5 = iArr[1];
            if (!zA || (i2 = i2 + i4 + i5) > i) {
                return -1;
            }
            i3++;
            if (i2 == i) {
                return i3;
            }
        }
        return -1;
    }

    private static void c(f fVar) {
        f[] fVarArrA;
        if (fVar == null || (fVarArrA = fVar.a()) == null) {
            return;
        }
        for (f fVar2 : fVarArrA) {
            c(fVar2);
        }
    }

    public static f d(byte[] bArr) {
        if (bArr.length > 65535) {
            return null;
        }
        f fVar = new f((byte) 0);
        fVar.g(bArr);
        e(fVar, 0);
        if (fVar.a() == null) {
            return null;
        }
        return fVar;
    }

    private static f e(f fVar, int i) {
        if (i > 10) {
            fVar.f(null);
            return fVar;
        }
        int iB = b(fVar.b(), fVar.b().length);
        if (iB < 0) {
            fVar.f(null);
            return fVar;
        }
        f[] fVarArr = new f[iB];
        int i2 = 0;
        int length = 0;
        for (int i3 = 0; i3 < iB; i3++) {
            int[] iArr = {-1, -1};
            boolean zA = a(fVar.b(), length, iArr, fVar.b().length - length);
            int i4 = iArr[0];
            int i5 = iArr[1];
            if (!zA) {
                while (i2 < i3) {
                    c(fVarArr[i2]);
                    i2++;
                }
                fVar.f(null);
                return fVar;
            }
            f fVarF = f(fVar.b(), length, i4 + i5, i);
            if (fVarF == null) {
                while (i2 < i3) {
                    c(fVarArr[i2]);
                    i2++;
                }
                fVar.f(null);
                return fVar;
            }
            length += fVarF.b().length + fVarF.c();
            fVarArr[i3] = fVarF;
        }
        fVar.f(fVarArr);
        return fVar;
    }

    private static f f(byte[] bArr, int i, int i2, int i3) {
        if (i2 < 2 || !h(bArr[i])) {
            return null;
        }
        int[] iArr = {-1, -1};
        boolean zA = a(bArr, i, iArr, i2);
        int i4 = iArr[0];
        int i5 = iArr[1];
        if (!zA || i4 + i5 != i2) {
            return null;
        }
        f fVar = new f(bArr[i]);
        fVar.h(i5);
        byte[] bArr2 = new byte[i4];
        System.arraycopy(bArr, i + i5, bArr2, 0, i4);
        fVar.g(bArr2);
        byte[] bArr3 = new byte[i5];
        System.arraycopy(bArr, i, bArr3, 0, i5);
        fVar.i(bArr3);
        if (fVar.e() == 48) {
            fVar = e(fVar, i3 + 1);
            if (fVar.a() == null) {
                return null;
            }
        }
        return fVar;
    }

    private static int g(byte b) {
        return b < 0 ? b + 256 : b;
    }

    private static boolean h(byte b) {
        int i = b & (-1);
        return i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 22 || i == 24 || i == 48;
    }
}

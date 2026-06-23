package com.felicanetworks.mfc;

/* JADX INFO: compiled from: ServiceUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class q1 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static q1 f113a;

    private q1() {
    }

    public static q1 e() {
        if (f113a == null) {
            f113a = new q1();
        }
        return f113a;
    }

    public void a(Block block, boolean z) {
        if (block == null) {
            throw new IllegalArgumentException("The specified Block is null.");
        }
        if (z) {
            switch (block.a()) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case com.felicanetworks.mfw.a.boot.q.CustomAlertDialog_topDark /* 9 */:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                    return;
                default:
                    throw new IllegalArgumentException("The specified Block is invalid for reading.");
            }
        }
    }

    public void b(Block block, Data data) {
        if (block == null) {
            throw new IllegalArgumentException("The specified Block is null.");
        }
        if (data == null) {
            throw new IllegalArgumentException("The specified Data is null.");
        }
        switch (block.a()) {
            case 1:
                if (data.c() == 1) {
                    return;
                }
                break;
            case 3:
                if (data.c() == 2) {
                    return;
                }
                break;
            case 5:
                if (data.c() == 3) {
                    return;
                }
                break;
            case 6:
                int iC = data.c();
                if (iC == 4 || iC == 5) {
                    return;
                }
                break;
            case 7:
                if (data.c() == 5) {
                    return;
                }
                break;
            case com.felicanetworks.mfw.a.boot.q.CustomAlertDialog_topDark /* 9 */:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
                int iC2 = data.c();
                if (iC2 == 6 || iC2 == 7 || iC2 == 8) {
                    return;
                }
                break;
        }
        throw new IllegalArgumentException("The combination of the Block and the Data is invalid.");
    }

    public void c(int i) {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("The Block No must be 0x0000 to 0xffff.");
        }
    }

    public int d(int i) {
        int i2 = i & 63;
        if (i2 == 9) {
            return 1;
        }
        if (i2 == 11) {
            return 2;
        }
        if (i2 == 13) {
            return 3;
        }
        if (i2 == 15) {
            return 4;
        }
        if (i2 == 17) {
            return 5;
        }
        if (i2 == 19) {
            return 6;
        }
        if (i2 == 21) {
            return 7;
        }
        if (i2 == 23) {
            return 8;
        }
        if (i2 == 32) {
            return 9;
        }
        if (i2 == 33) {
            return 10;
        }
        switch (i2) {
            case 40:
                return 11;
            case 41:
                return 12;
            case 42:
                return 13;
            case 43:
                return 14;
            case 44:
                return 15;
            case 45:
                return 16;
            case 46:
                return 17;
            case 47:
                return 18;
            case 48:
                return 19;
            case 49:
                return 20;
            case 50:
                return 21;
            case 51:
                return 22;
            case 52:
                return 23;
            case 53:
                return 24;
            case 54:
                return 25;
            case 55:
                return 26;
            default:
                throw new IllegalArgumentException("The attribute value of the specified Service Code is invalid.");
        }
    }
}

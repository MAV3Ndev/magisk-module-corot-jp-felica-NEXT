package com.felicanetworks.semc.util;

/* JADX INFO: loaded from: classes3.dex */
public class ErrorCodeConverter {
    public static final int EXC_TYPE_GP_EXCEPTION = 1;

    public static int convertException(int i, int i2) {
        int iConvertGpException;
        LogMgr.log(7, "000 exceptionClass=[" + i + "] exceptionType=[" + i2 + "]");
        if (i == 1) {
            iConvertGpException = convertGpException(i2);
        } else {
            LogMgr.log(2, "700 ExceptionClass is not found.");
            iConvertGpException = 900;
        }
        LogMgr.log(7, "999");
        return iConvertGpException;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x006f A[PHI: r5
  0x006f: PHI (r5v4 int) = (r5v3 int), (r5v5 int), (r5v6 int) binds: [B:24:0x0047, B:26:0x004b, B:29:0x0051] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0073  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int convertFromErrorTypeConst(int i) {
        LogMgr.log(7, "000 errorType=[" + i + "]");
        int i2 = 400;
        if (i != -409) {
            if (i == -408 || i == -406) {
                i2 = 900;
            } else if (i == 102) {
                i2 = 102;
            } else if (i == 203) {
                i2 = 203;
            } else if (i == 205) {
                i2 = 205;
            } else if (i == 300 || i == 500) {
                i2 = 300;
            } else if (i != 900) {
                if (i == -302 || i == -301) {
                    i2 = 300;
                } else {
                    int i3 = 200;
                    if (i != 200) {
                        i3 = 201;
                        if (i == 201) {
                            i2 = i3;
                        } else if (i != 400) {
                            i3 = 401;
                            if (i != 401) {
                                switch (i) {
                                    case 600:
                                        break;
                                    case 601:
                                        i2 = 300;
                                        break;
                                    case 602:
                                        i2 = 602;
                                        break;
                                    default:
                                        LogMgr.log(2, "700 Error type is not found. errorType[" + i + "]");
                                        i2 = 900;
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
        LogMgr.log(7, "999");
        return i2;
    }

    private static int convertGpException(int i) {
        LogMgr.log(8, "000 exceptionType=[" + i + "]");
        int i2 = 900;
        if (i != 900) {
            switch (i) {
                case 200:
                    i2 = 200;
                    break;
                case 201:
                    i2 = 201;
                    break;
                case 202:
                    i2 = 202;
                    break;
                case 203:
                    i2 = 203;
                    break;
                case 204:
                    i2 = 204;
                    break;
                default:
                    LogMgr.log(2, "700 ExceptionType is not found.");
                    break;
            }
        }
        LogMgr.log(8, "999");
        return i2;
    }
}

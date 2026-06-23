package com.felicanetworks.mfw.i.cmn;

import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfw.i.fbl.Property;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/* JADX INFO: loaded from: classes.dex */
public class ResUtil {
    private static final String AREA_SUFFIX = ".bin";
    public static final String FILE_CREATION_FAIL = "file creation failed.";
    private static final int[] AREA_SIZE = {1, 49, 27110, 1025, 270};
    private static byte[] sTmp = new byte[AREA_SIZE[2]];

    public static void readArea(String str, int i, int i2, ResUtilListener resUtilListener) {
        LogMgr.log(4, "%s : areaNumber = %s, position = %d, length = %d, listener = %s", "000", str, Integer.valueOf(i), Integer.valueOf(i2), resUtilListener);
        if (i < 0 || i2 < 0 || str == null || resUtilListener == null || Property.sFileDir == null) {
            LogMgr.log(1, "800 areaNumber = %s, position = %d, length = %d, listener = %s, FILE_DIR = %s", str, Integer.valueOf(i), Integer.valueOf(i2), resUtilListener, Property.sFileDir);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [areaNumber = " + str);
            stringBuffer.append(", position = " + i);
            stringBuffer.append(", length = " + i2);
            stringBuffer.append(", listener = " + resUtilListener);
            stringBuffer.append(", FILE_DIR = " + Property.sFileDir + "]");
            throw new SysException((Class<?>) ResUtil.class, "readArea", stringBuffer.toString());
        }
        if (i + i2 > AREA_SIZE[Integer.parseInt(str)]) {
            LogMgr.log(1, "801 areaNumber = %s, position = %d, length = %d", str, Integer.valueOf(i), Integer.valueOf(i2));
            throw new SysException((Class<?>) ResUtil.class, "readArea", "out of bound");
        }
        byte[] bArr = new byte[i2];
        File file = new File(Property.sFileDir, str + AREA_SUFFIX);
        int i3 = 0;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(i);
            while (true) {
                if (i2 <= i3) {
                    break;
                }
                int i4 = randomAccessFile.read(bArr, i3, i2 - i3);
                if (i4 == -1) {
                    LogMgr.log(7, "%s", "001");
                    break;
                }
                i3 += i4;
            }
            randomAccessFile.close();
            if (i3 != i2) {
                LogMgr.log(7, "%s", "002");
                throw new IOException();
            }
        } catch (Exception unused) {
            LogMgr.log(7, "%s", "003");
            try {
                if (file.exists()) {
                    LogMgr.log(7, "%s", "004");
                    if (!file.delete()) {
                        LogMgr.log(7, "%s", "005");
                        throw new Exception();
                    }
                }
                recoverOnReading(file, str, bArr, i, i2);
            } catch (Exception e) {
                LogMgr.log(1, "%s Exception:%s", "803", e.toString());
                throw new SysException((Class<?>) ResUtil.class, "readArea", FILE_CREATION_FAIL);
            }
        }
        resUtilListener.resUtilReadArea(bArr, bArr.length);
        LogMgr.log(4, "%s", "999");
    }

    private static void recoverOnReading(File file, String str, byte[] bArr, int i, int i2) {
        LogMgr.log(4, "%s : target = %s, areaNumber = %s, data = %s, position = %d, length = %d", "000", file, str, bArr, Integer.valueOf(i), Integer.valueOf(i2));
        try {
            if (!file.createNewFile()) {
                LogMgr.log(1, "%s createNewFile() failure", "800");
                throw new SysException((Class<?>) ResUtil.class, "readArea", FILE_CREATION_FAIL);
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                int i3 = 0;
                fileOutputStream.write(sTmp, 0, AREA_SIZE[Integer.parseInt(str)]);
                fileOutputStream.close();
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                randomAccessFile.seek(i);
                while (true) {
                    if (i3 >= i2) {
                        break;
                    }
                    int i4 = randomAccessFile.read(bArr, i3, i2 - i3);
                    if (i4 == -1) {
                        LogMgr.log(7, "%s", "001");
                        break;
                    }
                    i3 += i4;
                }
                randomAccessFile.close();
                if (i3 != i2) {
                    LogMgr.log(1, "%s size unmatch", "803");
                    throw new IOException();
                }
                LogMgr.log(4, "%s", "999");
            } catch (Exception e) {
                try {
                    LogMgr.log(1, "%s Exception:%s", "802", e.toString());
                    file.delete();
                } catch (Exception unused) {
                }
                throw new SysException((Class<?>) ResUtil.class, "readArea", FILE_CREATION_FAIL);
            }
        } catch (Exception e2) {
            LogMgr.log(1, "%s Exception:%s", "801", e2.toString());
            throw new SysException((Class<?>) ResUtil.class, "readArea", FILE_CREATION_FAIL);
        }
    }

    public static void writeToArea(String str, int i, byte[] bArr, int i2, ResUtilListener resUtilListener) {
        LogMgr.log(4, "%s : areaNumber = %s, position = %d, data = %s, length = %d, listener = %s", "000", str, Integer.valueOf(i), bArr, Integer.valueOf(i2), resUtilListener);
        if (i < 0 || i2 < 0 || str == null || bArr == null || resUtilListener == null || bArr.length != i2 || Property.sFileDir == null) {
            LogMgr.log(1, "800 areaNumber = %s, position = %d, data = %s, length = %d, listener = %s, FILE_DIR = %s", str, Integer.valueOf(i), bArr, Integer.valueOf(i2), resUtilListener, Property.sFileDir);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [areaNumber = " + str);
            stringBuffer.append(", position = " + i);
            stringBuffer.append(", length = " + i2);
            stringBuffer.append(", data = " + bArr);
            stringBuffer.append(", listener = " + resUtilListener);
            stringBuffer.append(", FILE_DIR = " + Property.sFileDir + "]");
            throw new SysException((Class<?>) ResUtil.class, "writeToArea", stringBuffer.toString());
        }
        if (i + i2 > AREA_SIZE[Integer.parseInt(str)]) {
            LogMgr.log(1, "801 areaNumber = %s, position = %d, length = %d", str, Integer.valueOf(i), Integer.valueOf(i2));
            throw new SysException((Class<?>) ResUtil.class, "writeToArea", "out of bound");
        }
        File file = new File(Property.sFileDir, str + AREA_SUFFIX);
        try {
        } catch (Exception unused) {
            LogMgr.log(7, "%s", "001");
            try {
                if (file.exists()) {
                    LogMgr.log(7, "%s", "002");
                    if (!file.delete()) {
                        LogMgr.log(7, "%s", "003");
                        throw new Exception();
                    }
                }
                recoverOnWriting(file, str, bArr, i, i2);
            } catch (Exception e) {
                LogMgr.log(1, "%s Exception:%s", "803", e.toString());
                throw new SysException((Class<?>) ResUtil.class, "writeToArea", FILE_CREATION_FAIL);
            }
        }
        if (!file.exists()) {
            throw new Exception();
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        randomAccessFile.seek(i);
        randomAccessFile.write(bArr, 0, i2);
        randomAccessFile.getFD().sync();
        randomAccessFile.close();
        resUtilListener.resUtilWriteArea();
        LogMgr.log(4, "%s", "999");
    }

    private static void recoverOnWriting(File file, String str, byte[] bArr, int i, int i2) {
        LogMgr.log(4, "%s : target = %s, areaNumber = %s, data = %s, position = %d, length = %d", "000", file, str, bArr, Integer.valueOf(i), Integer.valueOf(i2));
        try {
            if (!file.createNewFile()) {
                LogMgr.log(1, "%s createNewFile() failure", "800");
                throw new SysException((Class<?>) ResUtil.class, "writeToArea", FILE_CREATION_FAIL);
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(sTmp, 0, AREA_SIZE[Integer.parseInt(str)]);
                fileOutputStream.close();
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(i);
                randomAccessFile.write(bArr, 0, i2);
                randomAccessFile.getFD().sync();
                randomAccessFile.close();
                LogMgr.log(4, "%s", "999");
            } catch (Exception e) {
                try {
                    LogMgr.log(1, "%s Exception:%s", "802", e.toString());
                    file.delete();
                } catch (Exception unused) {
                }
                throw new SysException((Class<?>) ResUtil.class, "writeToArea", FILE_CREATION_FAIL);
            }
        } catch (Exception e2) {
            LogMgr.log(1, "%s Exception:%s", "801", e2.toString());
            throw new SysException((Class<?>) ResUtil.class, "writeToArea", FILE_CREATION_FAIL);
        }
    }

    public static int getAreaSize(String str) {
        if (str != null && StringUtil.isDecString(str) && Integer.parseInt(str) >= 0) {
            int i = Integer.parseInt(str);
            int[] iArr = AREA_SIZE;
            if (i < iArr.length) {
                return iArr[Integer.parseInt(str)];
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Illegal argument.");
        stringBuffer.append(" [areaNumber = " + str + "]");
        throw new SysException((Class<?>) ResUtil.class, "getAreaSize", stringBuffer.toString());
    }

    public static int getAreaCount() {
        return AREA_SIZE.length;
    }
}

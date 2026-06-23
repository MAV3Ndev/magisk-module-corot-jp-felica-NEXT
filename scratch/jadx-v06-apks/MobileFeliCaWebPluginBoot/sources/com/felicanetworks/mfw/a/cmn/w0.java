package com.felicanetworks.mfw.a.cmn;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/* JADX INFO: compiled from: ResUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class w0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final int[] f181a = {33, 111, 2731, 1057};

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
                throw new c1(w0.class, "closeQuietly");
            }
        }
    }

    private static void b(File file, int i) {
        FileOutputStream fileOutputStream;
        synchronized (w0.class) {
            FileOutputStream fileOutputStream2 = null;
            try {
                try {
                    file.createNewFile();
                    fileOutputStream = new FileOutputStream(file);
                } catch (IOException e) {
                    e = e;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                fileOutputStream.write(new byte[i]);
                a(fileOutputStream);
            } catch (IOException e2) {
                e = e2;
                throw new c1(w0.class, "createAreaFile", e);
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream2 = fileOutputStream;
                a(fileOutputStream2);
                throw th;
            }
        }
    }

    private static void c(String str, String str2) {
        new File(str2 + str + ".bin").delete();
    }

    public static void d(String str) {
        for (int i = 0; i < e(); i++) {
            c(Integer.toString(i), str);
        }
    }

    public static int e() {
        return f181a.length;
    }

    public static int f(String str) {
        if (str != null && b1.k(str) && Integer.parseInt(str) >= 0) {
            int i = Integer.parseInt(str);
            int[] iArr = f181a;
            if (i < iArr.length) {
                return iArr[Integer.parseInt(str)];
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Illegal argument.");
        stringBuffer.append(" [areaNumber = " + str + "]");
        throw new c1(w0.class, "getAreaSize", stringBuffer.toString());
    }

    private static byte[] g(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new c1(w0.class, "getDigest", e);
        }
    }

    public static int h(String str) {
        if (str == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [fileName = " + str + "]");
            throw new c1(w0.class, "getFileSize", stringBuffer.toString());
        }
        synchronized (w0.class) {
            try {
                try {
                    InputStream resourceAsStream = w0.class.getResourceAsStream("/assets/" + str);
                    int i = 0;
                    if (resourceAsStream == null) {
                        a(resourceAsStream);
                        return 0;
                    }
                    while (resourceAsStream.read() != -1) {
                        i++;
                    }
                    a(resourceAsStream);
                    return i;
                } catch (IOException e) {
                    throw new c1(w0.class, "getFileSize", e);
                }
            } catch (Throwable th) {
                a(null);
                throw th;
            }
        }
    }

    public static void i(String str, x0 x0Var, String str2) {
        if (str == null || x0Var == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [areaNumber = " + str);
            stringBuffer.append(", listener = " + x0Var + "]");
            throw new c1(w0.class, "readArea", stringBuffer.toString());
        }
        byte[] bArr = new byte[f(str)];
        int iF = f(str) - 32;
        byte[] bArr2 = new byte[iF];
        byte[] bArr3 = new byte[32];
        RandomAccessFile randomAccessFile = null;
        synchronized (w0.class) {
            try {
                try {
                    File file = new File(str2 + str + ".bin");
                    if (!file.exists()) {
                        byte[] bArr4 = new byte[iF];
                        for (int i = 0; i < iF; i++) {
                            bArr4[i] = 0;
                        }
                        l(str, bArr4, new v0(), str2);
                    }
                    if (file.length() != f(str)) {
                        c(str, str2);
                        throw new u0();
                    }
                    RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "r");
                    try {
                        randomAccessFile2.read(bArr);
                        System.arraycopy(bArr, 0, bArr2, 0, iF);
                        System.arraycopy(bArr, iF, bArr3, 0, 32);
                        if (!Arrays.equals(bArr3, g(bArr2))) {
                            c(str, str2);
                            throw new u0();
                        }
                        a(randomAccessFile2);
                    } catch (u0 unused) {
                        throw new u0();
                    } catch (IOException e) {
                        e = e;
                        throw new c1(w0.class, "readArea", e);
                    } catch (Throwable th) {
                        th = th;
                        randomAccessFile = randomAccessFile2;
                        a(randomAccessFile);
                        throw th;
                    }
                } catch (u0 unused2) {
                } catch (IOException e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        x0Var.b(bArr2, iF);
    }

    public static int j(String str, int i, int i2, byte[] bArr) {
        if (i < 0 || i2 < 0 || str == null || bArr == null || bArr.length != i2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [resourceName = " + str);
            stringBuffer.append(", position = " + i);
            stringBuffer.append(", length = " + i2);
            stringBuffer.append(", data = " + bArr + "]");
            throw new c1(w0.class, "readFile", stringBuffer.toString());
        }
        InputStream resourceAsStream = null;
        synchronized (w0.class) {
            try {
                try {
                    resourceAsStream = w0.class.getResourceAsStream("/assets/" + str);
                    int i3 = 0;
                    if (resourceAsStream != null && k(resourceAsStream, i)) {
                        while (true) {
                            byte b = (byte) resourceAsStream.read();
                            if (b == -1 || i3 >= bArr.length) {
                                break;
                            }
                            bArr[i3] = b;
                            i3++;
                        }
                        return i3;
                    }
                    return 0;
                } catch (IOException e) {
                    throw new c1(w0.class, "readFile", e);
                }
            } finally {
                a(resourceAsStream);
            }
        }
    }

    private static boolean k(InputStream inputStream, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (inputStream.read() == -1) {
                return false;
            }
        }
        return true;
    }

    public static void l(String str, byte[] bArr, x0 x0Var, String str2) {
        byte[] bArr2;
        RandomAccessFile randomAccessFile;
        if (str == null || bArr == null || bArr.length + 32 != f(str) || x0Var == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [areaNumber = " + str);
            stringBuffer.append(", data = " + bArr);
            stringBuffer.append(", listener = " + x0Var + "]");
            throw new c1(w0.class, "writeToArea", stringBuffer.toString());
        }
        RandomAccessFile randomAccessFile2 = null;
        synchronized (w0.class) {
            try {
                try {
                    File file = new File(str2 + str + ".bin");
                    if (!file.exists()) {
                        b(file, f(str));
                    }
                    byte[] bArrG = g(bArr);
                    bArr2 = new byte[bArr.length + bArrG.length];
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    System.arraycopy(bArrG, 0, bArr2, bArr.length, bArrG.length);
                    randomAccessFile = new RandomAccessFile(file, "rw");
                } catch (IOException e) {
                    e = e;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                randomAccessFile.seek(0L);
                randomAccessFile.write(bArr2);
                randomAccessFile.getFD().sync();
                a(randomAccessFile);
            } catch (IOException e2) {
                e = e2;
                throw new c1(w0.class, "writeToArea", e);
            } catch (Throwable th2) {
                randomAccessFile2 = randomAccessFile;
                th = th2;
                a(randomAccessFile2);
                throw th;
            }
        }
        x0Var.a();
    }
}

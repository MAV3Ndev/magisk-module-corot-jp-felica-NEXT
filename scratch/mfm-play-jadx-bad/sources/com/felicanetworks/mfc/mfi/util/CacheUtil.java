package com.felicanetworks.mfc.mfi.util;

import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes3.dex */
public class CacheUtil {
    public static final String DELIMITER = "_";
    public static final String EX_XML = ".xml";

    /* JADX WARN: Can't wrap try/catch for region: R(8:6|(2:37|7)|(7:40|8|(1:10)(1:43)|35|12|25|26)|11|35|12|25|26) */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0036, code lost:
    
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0037, code lost:
    
        com.felicanetworks.mfc.util.LogMgr.printStackTrace(7, r9);
     */
    /* JADX WARN: Not initialized variable reg: 6, insn: 0x0058: MOVE (r3 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]), block:B:28:0x0058 */
    /* JADX WARN: Removed duplicated region for block: B:38:0x005b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String readFile(File file) throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        LogMgr.log(5, "%s", "000");
        BufferedReader bufferedReader3 = null;
        string = null;
        String string = null;
        if (file == null) {
            LogMgr.log(2, "%s File is null.", "700");
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
            } catch (Throwable th) {
                th = th;
                bufferedReader3 = bufferedReader2;
                if (bufferedReader3 != null) {
                    try {
                        bufferedReader3.close();
                    } catch (IOException e) {
                        LogMgr.printStackTrace(7, e);
                    }
                }
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            if (bufferedReader3 != null) {
            }
            throw th;
        }
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                stringBuffer.append(line);
            } catch (IOException e3) {
                e = e3;
                LogMgr.log(2, "%s %s msg:%s", "701", "Failed to read File.", e.getMessage());
                if (bufferedReader != null) {
                }
                LogMgr.log(5, "%s", "999");
                return string;
            }
            bufferedReader.close();
            LogMgr.log(5, "%s", "999");
            return string;
        }
        string = stringBuffer.toString();
        bufferedReader.close();
        LogMgr.log(5, "%s", "999");
        return string;
    }

    public static boolean writeFile(File file, String str) throws Throwable {
        PrintWriter printWriter;
        LogMgr.log(5, "%s", "000");
        boolean z = false;
        if (file == null) {
            LogMgr.log(2, "%s File is null.", "700");
            return false;
        }
        PrintWriter printWriter2 = null;
        try {
            try {
                printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            printWriter.print(str);
            printWriter.flush();
            printWriter.close();
            z = true;
        } catch (IOException e2) {
            e = e2;
            printWriter2 = printWriter;
            LogMgr.log(2, "%s %s msg:%s", "701", "Failed to save File.", e.getMessage());
            if (printWriter2 != null) {
                printWriter2.close();
            }
        } catch (Throwable th2) {
            th = th2;
            printWriter2 = printWriter;
            if (printWriter2 != null) {
                printWriter2.close();
            }
            throw th;
        }
        LogMgr.log(5, "%s", "999");
        return z;
    }

    public static boolean deleteFile(File file) {
        LogMgr.log(5, "%s", "000");
        if (file == null) {
            LogMgr.log(2, "%s File is null.", "700");
            return false;
        }
        boolean zDelete = file.delete();
        if (!zDelete) {
            LogMgr.log(2, "%s Failed to delete %s.", "701", file.getName());
        }
        LogMgr.log(5, "%s", "999");
        return zDelete;
    }

    public static boolean deleteFiles(File dir, String prefix, String exclude) {
        LogMgr.log(5, "000");
        if (dir == null) {
            LogMgr.log(2, "700 dir is null.");
            return false;
        }
        if (prefix == null) {
            LogMgr.log(2, "701 prefix is null.");
            return false;
        }
        if (!dir.exists()) {
            LogMgr.log(2, "702 dir does not exists.");
            return false;
        }
        if (!dir.isDirectory()) {
            LogMgr.log(2, "703 dir is not directory.");
            return false;
        }
        File[] fileArrListFiles = dir.listFiles();
        if (fileArrListFiles == null) {
            LogMgr.log(2, "704 can not get file list.");
            return false;
        }
        boolean zDeleteFile = true;
        for (File file : fileArrListFiles) {
            if (file.isFile()) {
                String name = file.getName();
                if (name.startsWith(prefix) && !name.equals(exclude)) {
                    LogMgr.log(6, "001 files = " + file.getName());
                    zDeleteFile &= deleteFile(file);
                }
            }
        }
        LogMgr.log(5, "999");
        return zDeleteFile;
    }
}

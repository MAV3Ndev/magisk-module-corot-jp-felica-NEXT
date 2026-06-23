package com.felicanetworks.mfc.mfi.util;

import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class CacheUtil {
    public static final String DELIMITER = "_";
    public static final String EX_XML = ".xml";

    /* JADX WARN: Not initialized variable reg: 6, insn: 0x005a: MOVE (r3 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]), block:B:28:0x005a */
    public static String readFile(File file) throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        LogMgr.log(5, "%s", "000");
        String string = null;
        string = null;
        string = null;
        BufferedReader bufferedReader3 = null;
        if (file == null) {
            LogMgr.log(2, "%s File is null.", "700");
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
        } catch (Throwable th) {
            th = th;
            bufferedReader3 = bufferedReader;
        }
        try {
            try {
                bufferedReader2 = new BufferedReader(new FileReader(file));
            } catch (IOException e) {
                LogMgr.printStackTrace(7, e);
            }
        } catch (IOException e2) {
            e = e2;
            bufferedReader2 = null;
        } catch (Throwable th2) {
            th = th2;
            if (bufferedReader3 != null) {
                try {
                    bufferedReader3.close();
                } catch (IOException e3) {
                    LogMgr.printStackTrace(7, e3);
                }
            }
            throw th;
        }
        while (true) {
            try {
                String line = bufferedReader2.readLine();
                if (line == null) {
                    break;
                }
                stringBuffer.append(line);
            } catch (IOException e4) {
                e = e4;
                LogMgr.log(2, "%s %s msg:%s", "701", "Failed to read File.", e.getMessage());
                if (bufferedReader2 != null) {
                    bufferedReader2.close();
                }
                LogMgr.log(5, "%s", "999");
                return string;
            }
            LogMgr.log(5, "%s", "999");
            return string;
        }
        string = stringBuffer.toString();
        bufferedReader2.close();
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
            z = true;
            printWriter.close();
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

    public static boolean deleteFiles(File file, String str, String str2) {
        LogMgr.log(5, "000");
        if (file == null) {
            LogMgr.log(2, "700 dir is null.");
            return false;
        }
        if (str == null) {
            LogMgr.log(2, "701 prefix is null.");
            return false;
        }
        if (!file.exists()) {
            LogMgr.log(2, "702 dir does not exists.");
            return false;
        }
        if (!file.isDirectory()) {
            LogMgr.log(2, "703 dir is not directory.");
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            LogMgr.log(2, "704 can not get file list.");
            return false;
        }
        boolean zDeleteFile = true;
        for (File file2 : fileArrListFiles) {
            if (file2.isFile()) {
                String name = file2.getName();
                if (name.startsWith(str) && !name.equals(str2)) {
                    LogMgr.log(6, "001 files = " + file2.getName());
                    zDeleteFile &= deleteFile(file2);
                }
            }
        }
        LogMgr.log(5, "999");
        return zDeleteFile;
    }
}

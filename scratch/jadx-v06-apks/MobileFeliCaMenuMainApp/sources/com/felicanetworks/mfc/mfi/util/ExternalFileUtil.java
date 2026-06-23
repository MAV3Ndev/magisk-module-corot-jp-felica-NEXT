package com.felicanetworks.mfc.mfi.util;

import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes.dex */
public class ExternalFileUtil {
    private static final String ENCODE_TYPE = "UTF-8";

    public static String getExistFilePath(String str, String str2) {
        LogMgr.log(5, "000");
        if (str == null) {
            LogMgr.log(2, "%s path is null.", "700");
            return null;
        }
        if (str2 == null) {
            LogMgr.log(2, "%s fileName is null.", "701");
            return null;
        }
        try {
            File file = new File(str, str2);
            String absolutePath = file.exists() ? file.getAbsolutePath() : null;
            LogMgr.log(6, "ret=" + absolutePath);
            LogMgr.log(5, "999");
            return absolutePath;
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            return null;
        }
    }

    public static String readExternalFile(String str) throws IOException {
        BufferedReader bufferedReader;
        LogMgr.log(5, "000");
        StringBuffer stringBuffer = new StringBuffer();
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                try {
                    bufferedReader = new BufferedReader(inputStreamReader);
                } finally {
                }
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            String string = stringBuffer.toString();
                            bufferedReader.close();
                            inputStreamReader.close();
                            fileInputStream.close();
                            LogMgr.log(5, "999");
                            return string;
                        }
                        stringBuffer.append(line);
                    } finally {
                    }
                }
            } finally {
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}

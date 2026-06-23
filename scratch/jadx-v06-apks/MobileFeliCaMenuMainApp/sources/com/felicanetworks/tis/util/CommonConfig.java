package com.felicanetworks.tis.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/* JADX INFO: loaded from: classes.dex */
public class CommonConfig {
    private static final String ENCODE_TYPE = "UTF-8";
    private static final String FILE_NAME = "common.cfg";
    private static final String[] FILE_PATHS = {"/product/etc/felica/", "/vendor/etc/felica/", "/system/etc/felica/"};
    private static final int KEY_LENGTH = 8;
    private static CommonConfig sInstance;
    private static Map<String, String> sMap;

    private CommonConfig() {
        LogMgr.log(5, "%s", "000");
        LogMgr.log(5, "%s", "999");
    }

    public static synchronized CommonConfig getInstance() throws IOException, ParseException {
        LogMgr.log(4, "%s", "000");
        if (sInstance == null) {
            LogMgr.log(7, "%s", "001");
            CommonConfig commonConfig = new CommonConfig();
            commonConfig.initialize(FILE_NAME);
            sInstance = commonConfig;
        }
        LogMgr.log(4, "%s", "999");
        return sInstance;
    }

    private void initialize(String str) throws Throwable {
        LogMgr.log(5, "%s", "000");
        try {
            sMap = new HashMap();
            String strFindConfigFile = findConfigFile(str);
            if (strFindConfigFile != null) {
                readConfigFile(strFindConfigFile);
                LogMgr.log(5, "%s", "999");
            } else {
                LogMgr.log(1, "%s", "804");
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            LogMgr.log(1, "%s : catch FileNotFoundException msg = %s", "800", e.getMessage());
            LogMgr.log(1, "%s : %s %s", "805", FILE_NAME, " Not Found");
            throw e;
        } catch (IOException e2) {
            LogMgr.log(1, "%s : catch IOException msg = %s", "802", e2.getMessage());
            throw e2;
        } catch (ParseException e3) {
            LogMgr.log(1, "%s : catch ParseException msg = %s line = %d", "801", e3.getMessage(), Integer.valueOf(e3.getErrorOffset()));
            throw e3;
        } catch (Exception e4) {
            LogMgr.log(1, "%s : catch Exception msg = %s", "803", e4.getMessage());
            throw new FileNotFoundException("Unknown Error");
        }
    }

    public String getValue(String str) throws IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (str == null) {
            LogMgr.log(1, "%s : throw IllegalArgumentException key == null", "800");
            throw new IllegalArgumentException("The specified key value is invalid.");
        }
        if (str.length() != 8) {
            LogMgr.log(1, "%s : throw IllegalArgumentException key_length = %d", "801", Integer.valueOf(str.length()));
            throw new IllegalArgumentException("The specified key value is invalid.");
        }
        LogMgr.log(4, "%s", "999");
        return sMap.get(str);
    }

    private void readConfigFile(String str) throws Throwable {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        LogMgr.log(5, "%s", "000");
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader);
                    int i = 1;
                    while (true) {
                        try {
                            String line = bufferedReader2.readLine();
                            if (line != null) {
                                StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
                                if (stringTokenizer.hasMoreTokens()) {
                                    LogMgr.log(7, "%s", "001");
                                    String strNextToken = stringTokenizer.nextToken();
                                    if (strNextToken.length() != 8) {
                                        LogMgr.log(1, "%s : throw ParseException str = %s line = %d", "800", line, Integer.valueOf(i));
                                        throw new ParseException(line, i);
                                    }
                                    if (stringTokenizer.hasMoreElements()) {
                                        LogMgr.log(7, "%s", "002");
                                        sMap.put(strNextToken, stringTokenizer.nextToken());
                                        i++;
                                    } else {
                                        LogMgr.log(1, "%s : throw ParseException str = %s line = %d", "802", line, Integer.valueOf(i));
                                        throw new ParseException(line, i);
                                    }
                                } else {
                                    LogMgr.log(1, "%s : throw ParseException str = %s line = %d", "801", line, Integer.valueOf(i));
                                    throw new ParseException(line, i);
                                }
                            } else {
                                try {
                                    LogMgr.log(7, "%s", "003");
                                    bufferedReader2.close();
                                } catch (IOException unused) {
                                }
                                try {
                                    LogMgr.log(7, "%s", "004");
                                    inputStreamReader.close();
                                } catch (IOException unused2) {
                                }
                                try {
                                    LogMgr.log(7, "%s", "005");
                                    fileInputStream.close();
                                } catch (IOException unused3) {
                                }
                                LogMgr.log(5, "%s", "999");
                                return;
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    LogMgr.log(7, "%s", "003");
                                    bufferedReader.close();
                                } catch (IOException unused4) {
                                }
                            }
                            if (inputStreamReader != null) {
                                try {
                                    LogMgr.log(7, "%s", "004");
                                    inputStreamReader.close();
                                } catch (IOException unused5) {
                                }
                            }
                            if (fileInputStream != null) {
                                try {
                                    LogMgr.log(7, "%s", "005");
                                    fileInputStream.close();
                                    throw th;
                                } catch (IOException unused6) {
                                    throw th;
                                }
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStreamReader = null;
            }
        } catch (Throwable th4) {
            th = th4;
            fileInputStream = null;
            inputStreamReader = null;
        }
    }

    private String findConfigFile(String str) {
        String str2;
        LogMgr.log(5, "000");
        String[] strArr = FILE_PATHS;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                str2 = null;
                break;
            }
            str2 = strArr[i] + str;
            try {
                if (new File(str2).exists()) {
                    LogMgr.log(7, "001 tempPath = " + str2);
                    break;
                }
                continue;
            } catch (Exception e) {
                LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            }
            i++;
        }
        LogMgr.log(5, "999");
        return str2;
    }
}

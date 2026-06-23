package com.felicanetworks.semc.util;

import com.felicanetworks.semc.FlavorConst;
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

/* JADX INFO: loaded from: classes3.dex */
public class CommonConfig {
    private static final String ENCODE_TYPE = "UTF-8";
    public static final String FILE_NAME = "common.cfg";
    private static final String[] FILE_PATHS = FlavorConst.COMMON_CONFIG_FILE_PATH;
    private static final int KEY_LENGTH = 8;
    private static CommonConfig sInstance;
    private static Map<String, String> sMap;

    private CommonConfig() {
        LogMgr.log(7, "000");
        LogMgr.log(7, "999");
    }

    public static synchronized CommonConfig getInstance() throws IOException, ParseException {
        LogMgr.log(6, "000");
        if (sInstance == null) {
            LogMgr.log(9, "001");
            CommonConfig commonConfig = new CommonConfig();
            commonConfig.initialize(FILE_NAME);
            sInstance = commonConfig;
        }
        LogMgr.log(6, "999");
        return sInstance;
    }

    private void initialize(String str) throws Throwable {
        LogMgr.log(7, "000");
        try {
            sMap = new HashMap();
            String strFindConfigFile = findConfigFile(str);
            if (strFindConfigFile != null) {
                readConfigFile(strFindConfigFile);
                LogMgr.log(7, "999");
            } else {
                LogMgr.log(1, "800");
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            LogMgr.log(1, "801 : catch FileNotFoundException msg = " + e.getMessage());
            LogMgr.log(1, "802 : common.cfg Not Found");
            throw e;
        } catch (IOException e2) {
            LogMgr.log(1, "804 : catch IOException msg = " + e2.getMessage());
            throw e2;
        } catch (ParseException e3) {
            LogMgr.log(1, "803 : catch ParseException msg = " + e3.getMessage() + " line = " + e3.getErrorOffset());
            throw e3;
        } catch (Exception e4) {
            LogMgr.log(1, "805 : catch Exception msg = " + e4.getMessage());
            throw new FileNotFoundException("Unknown Error");
        }
    }

    public String getValue(String str) throws IllegalArgumentException {
        LogMgr.log(6, "000");
        if (str == null) {
            LogMgr.log(1, "800 : throw IllegalArgumentException key == null");
            throw new IllegalArgumentException("The specified key value is invalid.");
        }
        if (str.length() != 8) {
            LogMgr.log(1, "801 : throw IllegalArgumentException key_length = " + str.length());
            throw new IllegalArgumentException("The specified key value is invalid.");
        }
        LogMgr.log(6, "999");
        return sMap.get(str);
    }

    private void readConfigFile(String str) throws Throwable {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        LogMgr.log(7, "000");
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
                                    LogMgr.log(9, "001");
                                    String strNextToken = stringTokenizer.nextToken();
                                    if (strNextToken.length() != 8) {
                                        LogMgr.log(1, "800 : throw ParseException str = " + line + " line = " + i);
                                        throw new ParseException(line, i);
                                    }
                                    if (stringTokenizer.hasMoreElements()) {
                                        LogMgr.log(9, "002");
                                        sMap.put(strNextToken, stringTokenizer.nextToken());
                                        i++;
                                    } else {
                                        LogMgr.log(1, "801 : throw ParseException str = " + line + " line = " + i);
                                        throw new ParseException(line, i);
                                    }
                                } else {
                                    LogMgr.log(1, "801 : throw ParseException str = " + line + " line = " + i);
                                    throw new ParseException(line, i);
                                }
                            } else {
                                try {
                                    LogMgr.log(9, "003");
                                    bufferedReader2.close();
                                } catch (IOException unused) {
                                }
                                try {
                                    LogMgr.log(9, "004");
                                    inputStreamReader.close();
                                } catch (IOException unused2) {
                                }
                                try {
                                    LogMgr.log(9, "005");
                                    fileInputStream.close();
                                } catch (IOException unused3) {
                                }
                                LogMgr.log(7, "999");
                                return;
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    LogMgr.log(9, "003");
                                    bufferedReader.close();
                                } catch (IOException unused4) {
                                }
                            }
                            if (inputStreamReader != null) {
                                try {
                                    LogMgr.log(9, "004");
                                    inputStreamReader.close();
                                } catch (IOException unused5) {
                                }
                            }
                            if (fileInputStream != null) {
                                try {
                                    LogMgr.log(9, "005");
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

    public static String findConfigFile(String str) {
        String str2;
        LogMgr.log(7, "000");
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
                    LogMgr.log(9, "001 tempPath = " + str2);
                    break;
                }
                continue;
            } catch (Exception e) {
                LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            }
            i++;
        }
        LogMgr.log(7, "999");
        return str2;
    }
}

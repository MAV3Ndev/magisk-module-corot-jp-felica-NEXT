package com.felicanetworks.mfsctrl.net;

import com.felicanetworks.common.cmnctrl.net.DataParseException;
import com.felicanetworks.common.cmnctrl.net.DataParser;
import com.felicanetworks.common.cmnctrl.net.NetworkAccessResponseData;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfsctrl.data.AuthResponseData;
import com.felicanetworks.mfslib.MfsAppContext;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes.dex */
public class MfsDataParser extends DataParser implements FunctionCodeInterface {
    private static final int COMMUNICATION_OK = 200;
    private static final String CONTENTS_TYPE = "application/x-www-form-urlencoded";
    private static final char DOUBLE_QUOTATION = '\"';
    private static final String HEADER_CONTENTLENGTH = "content-length";
    private static final String HEADER_CONTENTTYPE = "content-type";
    private static final String HEADER_USERAGENT = "User-Agent";
    private static final String KEY_AUTHFINISHCODE = "authfincode";
    private static final String KEY_DATA_SEPARATOR = ": ";
    private static final String KEY_RESPNSECODE = "rescode";
    private static final String KEY_STARTURL = "surl";
    private static final String RESCODE_NG = "NG";
    private static final String RESCODE_OK = "OK";
    private static final int SIZE_AUTHFINISHCODE = 4;
    private static final int SIZE_RESPNSECODE = 2;
    private MfsAppContext _context;

    @Override // com.felicanetworks.common.cmnctrl.net.DataParser, com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 1;
    }

    @Override // com.felicanetworks.common.cmnctrl.net.DataParser, com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 35;
    }

    public MfsDataParser(MfsAppContext mfsAppContext) {
        super(mfsAppContext);
        this._context = null;
        this._context = mfsAppContext;
    }

    public AuthResponseData parseAuthResponseData(NetworkAccessResponseData networkAccessResponseData) throws DataParseException {
        int i;
        String authData;
        try {
            if (networkAccessResponseData == null) {
                throw new IllegalArgumentException("ResData is null");
            }
            if (200 != networkAccessResponseData.code) {
                throw new DataParseException(2, "ResCode : " + networkAccessResponseData.code);
            }
            String str = networkAccessResponseData.header.get("content-length");
            DataCheckerUtil.checkDecNumberFormat(str);
            if (networkAccessResponseData.data.length != Integer.parseInt(str)) {
                throw new DataParseException(2, "Contents Length not equal ResData Length");
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(networkAccessResponseData.data), "UTF-8"));
            String authData2 = getAuthData(KEY_RESPNSECODE, bufferedReader.readLine());
            if (authData2 == null) {
                throw new DataParseException(2, "Cannot get ResCode");
            }
            if (2 != authData2.length()) {
                throw new DataParseException(2, "ResCode length is Illegal");
            }
            if (authData2.equals(RESCODE_OK)) {
                i = 0;
            } else {
                if (!authData2.equals(RESCODE_NG)) {
                    throw new DataParseException(2, "ResCode is naither  OK nor NG");
                }
                i = 1;
            }
            String line = bufferedReader.readLine();
            String authData3 = null;
            if (1 == i) {
                authData = getAuthData(KEY_AUTHFINISHCODE, line);
                if (authData == null) {
                    throw new DataParseException(2, "Cannot get FinishCode");
                }
                if (4 != authData.length()) {
                    throw new DataParseException(2, "FinishCode length is Illegal");
                }
                DataCheckerUtil.checkAlphaSignFormat(authData);
            } else {
                authData = null;
            }
            bufferedReader.readLine();
            String line2 = bufferedReader.readLine();
            if (i == 0) {
                authData3 = getAuthData(KEY_STARTURL, line2);
                if (authData3 == null) {
                    throw new DataParseException(2, "Cannot get StartUrl");
                }
                DataCheckerUtil.checkUrlCharFormat(authData3);
            }
            return new AuthResponseData(i, authData, authData3);
        } catch (DataParseException e) {
            this._context.logMgr.out(LogMgr.CatExp.WAR, this, e);
            throw e;
        } catch (Exception e2) {
            throw new DataParseException(e2, this._context.logMgr.out(LogMgr.CatExp.WAR, this, e2), 2);
        }
    }

    private String getAuthData(String str, String str2) {
        String str3 = str + KEY_DATA_SEPARATOR;
        if (!str2.startsWith(str3)) {
            return null;
        }
        int iIndexOf = str2.indexOf(34, str3.length());
        int iLastIndexOf = str2.lastIndexOf(34);
        if (-1 == iIndexOf || -1 == iLastIndexOf || iIndexOf == iLastIndexOf) {
            return null;
        }
        return str2.substring(iIndexOf + 1, iLastIndexOf);
    }
}

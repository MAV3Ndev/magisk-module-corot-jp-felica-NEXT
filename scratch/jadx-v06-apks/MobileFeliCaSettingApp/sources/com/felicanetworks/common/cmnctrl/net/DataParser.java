package com.felicanetworks.common.cmnctrl.net;

import com.felicanetworks.common.cmnctrl.packages.PackageAccess;
import com.felicanetworks.common.cmnlib.AppContext;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import com.felicanetworks.common.cmnlib.sg.SgMgr;
import com.felicanetworks.common.cmnlib.util.DataCheckerException;
import com.felicanetworks.common.cmnlib.util.DataCheckerUtil;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class DataParser implements FunctionCodeInterface {
    private static final String CHARCODE = "UTF-8";
    public static final int COM_DATA_LENGTH = 8;
    protected static final String CONNECT_TYPE_GET = "GET";
    public static final String CONNECT_TYPE_POST = "POST";
    public static final String CONTENT_LENGTH_KEY = "content-length";
    public static final String CONTENT_TYPE_KEY = "content-type";
    public static final String CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";
    public static final String USER_AGENT_KEY = "user-agent";
    protected AppContext context;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 2;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 4;
    }

    public DataParser(AppContext appContext) {
        this.context = appContext;
    }

    public NetworkAccessRequestData createErrorLog(String str, String str2) throws DataParseException {
        String str3;
        try {
            String str4 = "ai=" + this.context.sgMgr.getAppId();
            String str5 = (String) this.context.sgMgr.getSgValue(SgMgr.KEY_SRV_LOG_URL);
            int iIntValue = ((Integer) this.context.sgMgr.getSgValue(SgMgr.KEY_SRV_LOG_SESS_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) this.context.sgMgr.getSgValue(SgMgr.KEY_SRV_LOG_READ_TIMEOUT)).intValue();
            String str6 = new PackageAccess(this.context).getAppVersionInfo().versionName;
            DataCheckerUtil.checkLessEqualLength(str6.length(), 8);
            DataCheckerUtil.checkAlphaSignFormat(str6);
            if (str2 == null || "".equals(str2)) {
                str3 = str4 + "&av=" + str6 + "&d=" + str;
            } else {
                str3 = str4 + "&av=" + str6 + "&idm=" + str2 + "&d=" + str;
            }
            byte[] bytes = str3.getBytes();
            HashMap map = new HashMap();
            map.put(USER_AGENT_KEY, this.context.userAgent);
            map.put(CONTENT_LENGTH_KEY, Integer.toString(bytes.length));
            map.put("content-type", CONTENT_TYPE_VALUE);
            return new NetworkAccessRequestData(str5, CONNECT_TYPE_POST, map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new DataParseException(e, this.context.logMgr.out(LogMgr.CatExp.WAR, this, e), 3);
        }
    }

    public boolean parseErrorLog(NetworkAccessResponseData networkAccessResponseData) {
        return networkAccessResponseData.code == 200;
    }

    public NetworkAccessRequestData create() throws NetworkAccessException {
        try {
            String versionURL = this.context.sgMgr.getVersionURL();
            int versionSessionTimeout = this.context.sgMgr.getVersionSessionTimeout();
            int versionReadTimeout = this.context.sgMgr.getVersionReadTimeout();
            String str = new PackageAccess(this.context).getAppVersionInfo().versionName;
            DataCheckerUtil.checkLessEqualLength(str.length(), 8);
            DataCheckerUtil.checkAlphaSignFormat(str);
            byte[] bytes = ("ai=" + this.context.sgMgr.getAppId() + "&av=" + str + "&ct=0&mct=0&il=0&mil=0").getBytes(CHARCODE);
            HashMap map = new HashMap();
            map.put(USER_AGENT_KEY, this.context.userAgent);
            map.put(CONTENT_LENGTH_KEY, Integer.toString(bytes.length));
            return new NetworkAccessRequestData(versionURL, CONNECT_TYPE_GET, map, versionSessionTimeout, versionReadTimeout, bytes);
        } catch (Exception unused) {
            throw new NetworkAccessException(4);
        }
    }

    public Result parse(NetworkAccessResponseData networkAccessResponseData) throws NetworkAccessException {
        try {
            if (networkAccessResponseData.code != 200) {
                throw new NetworkAccessException(4);
            }
            if (Integer.parseInt(networkAccessResponseData.header.get(CONTENT_LENGTH_KEY)) != networkAccessResponseData.data.length) {
                throw new NetworkAccessException(4);
            }
            String[] strArrSplit = new String(networkAccessResponseData.data, CHARCODE).split("\r\n", -1);
            if (strArrSplit.length != 6) {
                throw new NetworkAccessException(4);
            }
            DataCheckerUtil.checkFixValue(strArrSplit[3], new String[]{"0", "2", "1"});
            if (strArrSplit[2].length() != 0) {
                throw new NetworkAccessException(4);
            }
            if (strArrSplit[5].length() != 0) {
                throw new NetworkAccessException(4);
            }
            return new Result(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]), Integer.parseInt(strArrSplit[3]), Integer.parseInt(strArrSplit[4]));
        } catch (NetworkAccessException e) {
            throw e;
        } catch (DataCheckerException e2) {
            if (e2.getErrorId() == 0) {
                throw new NetworkAccessException(4);
            }
            if (1 != e2.getErrorId()) {
                return null;
            }
            throw new NetworkAccessException(4);
        } catch (Exception unused) {
            throw new NetworkAccessException(4);
        }
    }

    public static class Result {
        public static final String COMMUNICATION_MODE_LOG_STOP = "2";
        public static final String COMMUNICATION_MODE_NORMAL = "0";
        public static final String COMMUNICATION_MODE_PUSH_LOG_STOP = "3";
        public static final String COMMUNICATION_MODE_PUSH_STOP = "1";
        private static final int MASK_PREVENT_LOGS = 2;
        private static final int MASK_PREVENT_PUSH = 1;
        public static final String VERSION_UP_KIND_ANY = "2";
        public static final String VERSION_UP_KIND_FORCE = "1";
        public static final String VERSION_UP_KIND_UNNECESSARY = "0";
        public int networkMode;
        public int offlineCheckInterval;
        public int offlineCheckMaxCnt;
        public int versionupInfo;

        private Result(int i, int i2, int i3, int i4) {
            this.offlineCheckMaxCnt = i;
            this.offlineCheckInterval = i2;
            this.versionupInfo = i3;
            this.networkMode = i4;
        }

        public boolean isPreventPush() {
            return (this.networkMode & 1) != 0;
        }

        public boolean isPreventLogs() {
            return (this.networkMode & 2) != 0;
        }

        public String toString() {
            return "Result{offlineCheckMaxCnt=" + this.offlineCheckMaxCnt + ", offlineCheckInterval=" + this.offlineCheckInterval + ", versionupInfo=" + this.versionupInfo + ", networkMode=" + this.networkMode + '}';
        }
    }

    protected DataParseException createDataParseException(Exception exc, boolean z, int i) {
        if (z) {
            if (exc instanceof DataCheckerException) {
                return new DataParseException(exc, this.context.logMgr.out(LogMgr.CatExp.ERR, this, exc), ((DataCheckerException) exc).getErrorId() == 0 ? 0 : 1);
            }
            return new DataParseException(exc, this.context.logMgr.out(LogMgr.CatExp.ERR, this, exc), 3);
        }
        if (i != 0) {
            return new DataParseException(exc, this.context.logMgr.out(LogMgr.CatExp.WAR, this, exc), i, 2);
        }
        return new DataParseException(exc, this.context.logMgr.out(LogMgr.CatExp.WAR, this, exc), 2);
    }
}

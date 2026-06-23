package com.felicanetworks.common.cmnlib.sg;

import android.content.res.Resources;
import android.util.Log;
import com.felicanetworks.common.cmnlib.AppContext;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.util.DataCheckerException;
import com.felicanetworks.common.cmnlib.util.DataCheckerInterface;
import com.felicanetworks.common.cmnlib.util.DataCheckerUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public abstract class SgMgr implements FunctionCodeInterface {
    protected static final int ATTR_ALPHA_NUMBER = 4;
    protected static final int ATTR_ALPHA_SIGN = 8;
    protected static final int ATTR_DEC_NUMBER = 1;
    protected static final int ATTR_HEX_NUMBER = 2;
    protected static final int ATTR_INDIVIDUAL = 128;
    protected static final int ATTR_NONE = 0;
    protected static final int ATTR_URI = 16;
    protected static final int DATAFORM_BIN = 2;
    protected static final int DATAFORM_INT = 1;
    protected static final int DATAFORM_STRING = 0;
    protected static final int DATASIZE_CHANGEABLE = 1;
    protected static final int DATASIZE_FIXED = 0;
    private static final String DISP_ERROR_CODE_FORMAT = "%02X-%03X-%04d-%03X";
    protected static final int ISMANDATERY_MUST = 0;
    protected static final int ISMANDATERY_OPT = 1;
    public static final int KEY_MFC_RETRY_CNT = 16777226;
    public static final int KEY_MFC_TIMEOUT = 16777225;
    public static final int KEY_SRV_LOG_READ_TIMEOUT = 16777268;
    public static final int KEY_SRV_LOG_SESS_TIMEOUT = 16777239;
    public static final int KEY_SRV_LOG_URL = 16777238;
    public static final int KEY_SRV_TIMEZONE = 16777240;
    protected static final int LOGIC_KEY_MASK = 16777216;
    protected static final String LOG_FORMAT_KEY = "key  = %08X : %s";
    private static final String LOG_TAG_STR = "%s";
    protected static final int SGFILE_COLUMN_ATTR_KEY_BASE = 16;
    protected static final int SGFILE_COLUMN_COUNT = 2;
    protected static final int SGFILE_COLUMN_NUMBER_KEY = 0;
    protected static final int SGFILE_COLUMN_NUMBER_VALUE = 1;
    protected static final String SGFILE_FORMAT_SEPARATOR = ",";
    protected static final String SGFILE_SUPPORTED_LINE_FORMAT = "^[0-9A-F]{8},.*";
    protected static final String SGFILE_UNSUPPORTED_LINE_FORMAT = ".*,.*,";
    protected static final int SG_INFO_TABLE_CLM_ATTRIBUTE = 4;
    protected static final int SG_INFO_TABLE_CLM_DATA_TYPE = 6;
    protected static final int SG_INFO_TABLE_CLM_MANDATERY = 5;
    protected static final int SG_INFO_TABLE_CLM_REAL_KEY = 1;
    protected static final int SG_INFO_TABLE_CLM_SIZE = 3;
    protected static final int SG_INFO_TABLE_CLM_SIZE_TYPE = 2;
    protected static final int SG_INFO_TABLE_CLM_VIRTUAL_KEY = 0;
    private static final int UNKNOWN_EXCEPTION_CODE = 4095;
    private static final Map<String, Integer> issuerIndexMap;
    private AppContext context;
    private boolean errorFlg = false;
    protected Map<Integer, Object> sgTable = new HashMap();
    public static final int KEY_MFC_ISSUER_CODE = 16777228;
    public static final int KEY_MFC_CARRIER_CODE = 16777229;
    public static final int KEY_APP_BROWSER_PKGNAME = 16777252;
    public static final int KEY_APP_MARKETAPP_PKGNAME = 16777253;
    public static final int KEY_MFC_FIRST_ISSUE_EXEC_FLG = 16777227;
    private static final int[][] commonSupportedSgInformationTable = {new int[]{KEY_MFC_ISSUER_CODE, 1, 0, 6, 2, 0, 0}, new int[]{KEY_MFC_CARRIER_CODE, 2, 0, 6, 2, 0, 0}, new int[]{KEY_APP_BROWSER_PKGNAME, 3, 1, 64, 128, 1, 0}, new int[]{KEY_APP_MARKETAPP_PKGNAME, 4, 1, 64, 128, 0, 0}, new int[]{KEY_MFC_FIRST_ISSUE_EXEC_FLG, 5, 0, 1, 128, 0, 1}};

    protected abstract void checkParamAdjustment() throws DataCheckerException;

    public abstract String getAppId();

    public abstract String getAppName();

    protected abstract String getAppSgFilePath();

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 1;
    }

    protected abstract String getCommonSgFilePath();

    public abstract String getErrorLogLimit();

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 7;
    }

    protected abstract Map<Integer, DataCheckerInterface> getIndividualSgCheckerTable();

    protected abstract int[][] getIndividualSupportedSgInformationTable();

    public abstract String getManageSystemCode();

    public abstract String getMfcPackageName();

    public abstract String getMfcPermission();

    public abstract String getPlatformKind();

    protected abstract String[] getSgPath(Resources resources);

    protected abstract int[][] getSpecialSgTable();

    public abstract int getVersionReadTimeout();

    public abstract int getVersionSessionTimeout();

    public abstract String getVersionURL();

    static {
        HashMap map = new HashMap();
        issuerIndexMap = map;
        map.put("100001", 0);
        issuerIndexMap.put("100002", 1);
        issuerIndexMap.put("100003", 2);
        issuerIndexMap.put("100007", 3);
        issuerIndexMap.put("100008", 4);
    }

    private Map<Integer, DataCheckerInterface> getcommonSgCheckerTable() {
        HashMap map = new HashMap();
        map.put(Integer.valueOf(KEY_APP_BROWSER_PKGNAME), new PackageNameChecker());
        map.put(Integer.valueOf(KEY_APP_MARKETAPP_PKGNAME), new PackageNameChecker());
        map.put(Integer.valueOf(KEY_MFC_FIRST_ISSUE_EXEC_FLG), new FirstIssueExecFlagChecker());
        return map;
    }

    protected class SystemFileKeyCodeInfo {
        int[] checkData = null;
        DataCheckerInterface indivChecker = null;
        String value = null;

        SystemFileKeyCodeInfo() {
        }
    }

    protected class FirstIssueExecFlagChecker extends ZeroOrOneChecker {
        public FirstIssueExecFlagChecker() {
            super();
        }
    }

    protected class ZeroOrOneChecker extends StringValueChecker {
        private final String[] permissionList;

        public ZeroOrOneChecker() {
            super();
            this.permissionList = new String[]{"0", "1"};
        }

        @Override // com.felicanetworks.common.cmnlib.sg.SgMgr.StringValueChecker
        protected String[] getPermitList() throws DataCheckerException {
            return this.permissionList;
        }
    }

    protected class TimezoneChecker extends StringValueChecker {
        public TimezoneChecker() {
            super();
        }

        @Override // com.felicanetworks.common.cmnlib.sg.SgMgr.StringValueChecker
        protected String[] getPermitList() throws DataCheckerException {
            return TimeZone.getAvailableIDs();
        }
    }

    protected abstract class StringValueChecker implements DataCheckerInterface {
        protected abstract String[] getPermitList() throws DataCheckerException;

        public StringValueChecker() {
        }

        @Override // com.felicanetworks.common.cmnlib.util.DataCheckerInterface
        public void checkAttribute(String str) throws DataCheckerException {
            if (str == null) {
                throw new DataCheckerException();
            }
            String[] permitList = getPermitList();
            Arrays.sort(permitList);
            if (Arrays.binarySearch(permitList, str) < 0) {
                throw new DataCheckerException();
            }
        }
    }

    protected class PackageNameChecker implements DataCheckerInterface {
        public PackageNameChecker() {
        }

        @Override // com.felicanetworks.common.cmnlib.util.DataCheckerInterface
        public void checkAttribute(String str) throws DataCheckerException {
            DataCheckerUtil.checkAlphaSignFormat(str);
        }
    }

    public SgMgr(AppContext appContext) {
        this.context = null;
        this.context = appContext;
    }

    protected AppContext getContext() {
        return this.context;
    }

    public void loadSg() throws SgMgrException {
        try {
            int[][] supportedSgInformationTable = getSupportedSgInformationTable();
            Map<Integer, DataCheckerInterface> sgCheckerTable = getSgCheckerTable();
            String[] sgPath = getSgPath(this.context.androidContext.getResources());
            int length = sgPath.length;
            int i = 0;
            String str = null;
            String str2 = null;
            while (true) {
                if (i >= length) {
                    break;
                }
                String str3 = sgPath[i];
                String str4 = str3 + getCommonSgFilePath();
                try {
                    if (new File(str4).exists()) {
                        try {
                            str = str3 + getAppSgFilePath();
                            str2 = str4;
                            break;
                        } catch (Exception unused) {
                            str2 = str4;
                            i++;
                        }
                    } else {
                        continue;
                    }
                } catch (Exception unused2) {
                }
                i++;
            }
            ArrayList<SystemFileKeyCodeInfo> sgFile = readSgFile(str, supportedSgInformationTable, sgCheckerTable);
            sgFile.addAll(readSgFile(str2, supportedSgInformationTable, sgCheckerTable));
            checkSg(sgFile);
            try {
                checkParamAdjustment();
            } catch (DataCheckerException unused3) {
                this.errorFlg = true;
            }
            checkMandatery(supportedSgInformationTable);
            readSpecialSg();
            isNoError();
        } catch (Exception e) {
            sgLogOut("Unknown error occurred.");
            throw createSgManagerException(e);
        }
    }

    private void isNoError() throws SgMgrException {
        if (this.errorFlg) {
            throw createSgManagerException(null);
        }
    }

    private SgMgrException createSgManagerException(Exception exc) {
        SgMgrException sgMgrException = new SgMgrException(exc);
        sgMgrException.setErrIdentifierCode(String.format(DISP_ERROR_CODE_FORMAT, Integer.valueOf(getFunctionCode()), Integer.valueOf(getClassCode()), Integer.valueOf(sgMgrException.getStackTrace()[1].getLineNumber()), Integer.valueOf(exc == null ? UNKNOWN_EXCEPTION_CODE : sgMgrException.getExceptionCode())));
        return sgMgrException;
    }

    private ArrayList<SystemFileKeyCodeInfo> readSgFile(String str, int[][] iArr, Map<Integer, DataCheckerInterface> map) throws Exception {
        ArrayList<SystemFileKeyCodeInfo> arrayList;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            arrayList = new ArrayList<>();
            bufferedReader = new BufferedReader(new FileReader(str));
        } catch (Throwable th) {
            th = th;
        }
        try {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                if (true == line.matches(SGFILE_SUPPORTED_LINE_FORMAT) && !line.matches(SGFILE_UNSUPPORTED_LINE_FORMAT)) {
                    String[] strArrSplit = line.split(SGFILE_FORMAT_SEPARATOR);
                    int i = (int) Long.parseLong(strArrSplit[0], 16);
                    int length = iArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 < length) {
                            int[] iArr2 = iArr[i2];
                            if (iArr2[1] == i) {
                                SystemFileKeyCodeInfo systemFileKeyCodeInfo = new SystemFileKeyCodeInfo();
                                systemFileKeyCodeInfo.checkData = iArr2;
                                if (2 == strArrSplit.length && strArrSplit[1].length() > 0) {
                                    systemFileKeyCodeInfo.value = strArrSplit[1];
                                }
                                if (128 == iArr2[4]) {
                                    systemFileKeyCodeInfo.indivChecker = map.get(Integer.valueOf(iArr2[0]));
                                }
                                arrayList.add(systemFileKeyCodeInfo);
                            } else {
                                i2++;
                            }
                        }
                    }
                }
            }
            bufferedReader.close();
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
                bufferedReader2.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0083 A[Catch: DataCheckerException -> 0x0104, TRY_LEAVE, TryCatch #0 {DataCheckerException -> 0x0104, blocks: (B:19:0x004b, B:30:0x0063, B:31:0x006b, B:32:0x0071, B:33:0x0077, B:34:0x007d, B:35:0x0083), top: B:53:0x004b }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00a5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0098 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void checkSg(java.util.List<com.felicanetworks.common.cmnlib.sg.SgMgr.SystemFileKeyCodeInfo> r10) {
        /*
            Method dump skipped, instruction units count: 287
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.common.cmnlib.sg.SgMgr.checkSg(java.util.List):void");
    }

    private void checkMandatery(int[][] iArr) throws SgMgrException {
        if (iArr.length != this.sgTable.size()) {
            for (int[] iArr2 : iArr) {
                if (iArr2[5] == 0 && this.sgTable.get(Integer.valueOf(iArr2[0])) == null) {
                    sgLogOut(LOG_FORMAT_KEY, iArr2[1], "Must be corrected.");
                    this.errorFlg = true;
                }
            }
        }
    }

    public Object getSgValue(int i) throws SgMgrException {
        Object obj = this.sgTable.get(Integer.valueOf(i));
        if (obj != null) {
            return obj;
        }
        throw new SgMgrException(String.format("Value is null. key = %d(0x%x)", Integer.valueOf(i), Integer.valueOf(i)));
    }

    protected String getResourceValue(int i) {
        return this.context.androidContext.getString(i);
    }

    private int[][] getSupportedSgInformationTable() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList((Object[]) commonSupportedSgInformationTable.clone()));
        arrayList.addAll(Arrays.asList(getIndividualSupportedSgInformationTable()));
        return (int[][]) arrayList.toArray(new int[0][]);
    }

    private Map<Integer, DataCheckerInterface> getSgCheckerTable() {
        HashMap map = new HashMap();
        map.putAll(getcommonSgCheckerTable());
        map.putAll(getIndividualSgCheckerTable());
        return map;
    }

    protected void sgLogOut(String str, int i, String str2) {
        sgLogOut(String.format(str, Integer.valueOf(i), str2));
    }

    protected void sgLogOut(String str) {
        Log.w(String.format(LOG_TAG_STR, getAppName()), str);
    }

    protected static int getAppIdMask(String str) {
        return Integer.parseInt(str, 16) << 16;
    }

    private void readSpecialSg() throws SgMgrException {
        int[][] specialSgTable = getSpecialSgTable();
        Resources resources = this.context.androidContext.getResources();
        String str = (String) getSgValue(KEY_MFC_ISSUER_CODE);
        Integer num = issuerIndexMap.get(str);
        if (num == null) {
            throw new SgMgrException("Invalid issuerCode. " + str);
        }
        for (int[] iArr : specialSgTable) {
            String str2 = resources.getStringArray(iArr[1])[num.intValue()];
            if (str2.length() <= 0) {
                str2 = null;
            }
            this.sgTable.put(Integer.valueOf(iArr[0]), str2);
        }
    }
}

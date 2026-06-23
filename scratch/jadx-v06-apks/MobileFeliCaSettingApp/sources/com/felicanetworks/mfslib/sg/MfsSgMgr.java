package com.felicanetworks.mfslib.sg;

import android.content.res.Resources;
import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.common.cmnlib.sg.SgMgr;
import com.felicanetworks.common.cmnlib.util.DataCheckerInterface;
import com.felicanetworks.common.cmnview.TransferStateId;
import com.felicanetworks.mfs.R;
import com.felicanetworks.mfslib.MfsAppContext;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MfsSgMgr extends SgMgr {
    public static final int KEY_APP_MAIN_PKGGETURL = 3;
    public static final int KEY_UI_DISCLAMER_CHKBOX = 142;
    public static final int KEY_UI_DISCLAMER_LINK_STRING = 68;
    public static final int KEY_UI_DISCLAMER_MESSAGE_F = 67;
    public static final int KEY_UI_DISCLAMER_URL = 69;
    protected static final int NET_AIM_VERSION_READ_TIMEOUT = 2131296261;
    protected static final int NET_AIM_VERSION_SESSION_TIMEOUT = 2131296260;
    protected static final int NET_AIM_VERSION_URL = 2131493014;
    protected static final int RES_ID_APP_FILEPATH = 2130837511;
    protected static final int RES_ID_APP_ID = 2131493004;
    protected static final int RES_ID_APP_NAME = 2131493008;
    protected static final int RES_ID_APP_SG_FILENAME = 2131493003;
    protected static final int RES_ID_COMMON_SG_FILENAME = 2131493002;
    protected static final int RES_ID_DISCLAMER_SEARCH_STR = 2131493009;
    protected static final int RES_ID_ERROR_LOG_LIMIT = 2131493007;
    protected static final int RES_ID_MANAGE_SYSTEM_CODE = 2131493006;
    protected static final int RES_ID_MFC_PACKAGE_NAME = 2131493010;
    protected static final int RES_ID_MFC_PERMISSION = 2131493011;
    protected static final int RES_ID_MFS_MOVEMENT_RECEIVER_CLASS_NAME = 2131493013;
    protected static final int RES_ID_MFS_MOVEMENT_RECEIVER_PKG_NAME = 2131493012;
    protected static final int RES_ID_PLATFORM_KIND = 2131493005;
    private List<ResSgKey> specialSgList;
    private int[][] supportedIndividualSgInformationTable;

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    protected void checkParamAdjustment() {
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr, com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 1;
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr, com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 37;
    }

    protected static class ResSgKey {
        public final int logicalKey;
        public final int physicalKey;

        public ResSgKey(int i, int i2) {
            this.logicalKey = i;
            this.physicalKey = i2;
        }
    }

    private void createSpecialSgList() {
        ArrayList arrayList = new ArrayList();
        this.specialSgList = arrayList;
        arrayList.add(new ResSgKey(67, R.array.sg_02020405));
        this.specialSgList.add(new ResSgKey(68, R.array.sg_02020406));
        this.specialSgList.add(new ResSgKey(KEY_UI_DISCLAMER_CHKBOX, R.array.sg_02020411));
        this.specialSgList.add(new ResSgKey(69, R.array.sg_02020407));
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    protected int[][] getSpecialSgTable() {
        createSpecialSgList();
        int size = this.specialSgList.size();
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) int.class, size, 2);
        for (int i = 0; i < size; i++) {
            iArr[i][0] = this.specialSgList.get(i).logicalKey;
            iArr[i][1] = this.specialSgList.get(i).physicalKey;
        }
        return iArr;
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    protected Map<Integer, DataCheckerInterface> getIndividualSgCheckerTable() {
        HashMap map = new HashMap();
        map.put(Integer.valueOf(SgMgr.KEY_SRV_TIMEZONE), new SgMgr.TimezoneChecker());
        return map;
    }

    public MfsSgMgr(MfsAppContext mfsAppContext) throws Exception {
        super(mfsAppContext);
        this.supportedIndividualSgInformationTable = new int[][]{new int[]{SgMgr.KEY_MFC_TIMEOUT, InputDeviceCompat.SOURCE_DPAD, 1, 8, 1, 0, 1}, new int[]{SgMgr.KEY_MFC_RETRY_CNT, 514, 1, 2, 1, 0, 1}, new int[]{SgMgr.KEY_SRV_LOG_URL, 778, 1, TransferStateId.COMMANDID_TRANS_FATALERROR, 16, 0, 0}, new int[]{SgMgr.KEY_SRV_LOG_SESS_TIMEOUT, 779, 1, 8, 1, 0, 1}, new int[]{SgMgr.KEY_SRV_TIMEZONE, 780, 1, 64, 128, 0, 0}, new int[]{SgMgr.KEY_SRV_LOG_READ_TIMEOUT, 790, 1, 8, 1, 0, 1}, new int[]{3, 2564, 1, TransferStateId.COMMANDID_TRANS_FATALERROR, 16, 0, 0}};
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    protected int[][] getIndividualSupportedSgInformationTable() {
        int[][] iArr = (int[][]) this.supportedIndividualSgInformationTable.clone();
        int appIdMask = getAppIdMask(getResourceValue(R.string.sg_res_003));
        for (int[] iArr2 : iArr) {
            iArr2[1] = iArr2[1] | appIdMask;
        }
        return iArr;
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    protected String getCommonSgFilePath() {
        return getResourceValue(R.string.sg_res_001);
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    protected String getAppSgFilePath() {
        return getResourceValue(R.string.sg_res_002);
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    protected String[] getSgPath(Resources resources) {
        return resources.getStringArray(R.array.sg_res_041);
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    public String getAppId() {
        return getResourceValue(R.string.sg_res_003);
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    public String getPlatformKind() {
        return getResourceValue(R.string.sg_res_004);
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    public String getManageSystemCode() {
        return getResourceValue(R.string.sg_res_005);
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    public String getErrorLogLimit() {
        return getResourceValue(R.string.sg_res_006);
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    public String getAppName() {
        return getResourceValue(R.string.sg_res_008);
    }

    public String getDisclamerSearchString() {
        return getResourceValue(R.string.sg_res_010);
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    public String getMfcPackageName() {
        return getResourceValue(R.string.sg_res_011);
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    public String getMfcPermission() {
        return getResourceValue(R.string.sg_res_012);
    }

    public String getMfsMovementReceiverPkgName() {
        return getResourceValue(R.string.sg_res_025);
    }

    public String getMfsMovementReceiverClassName() {
        return getResourceValue(R.string.sg_res_026);
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    public String getVersionURL() {
        return getResourceValue(R.string.sg_res_042);
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    public int getVersionSessionTimeout() {
        return Integer.parseInt(getResourceValue(R.integer.sg_res_043));
    }

    @Override // com.felicanetworks.common.cmnlib.sg.SgMgr
    public int getVersionReadTimeout() {
        return Integer.parseInt(getResourceValue(R.integer.sg_res_044));
    }
}

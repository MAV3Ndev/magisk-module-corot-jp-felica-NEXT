package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.CardAdditionalInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.util.ServiceTypeInfoUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class CardJson extends JSONObject {
    private static final String FINISH_DONE = "done";
    private static final HashMap<String, CompleteCardInfo.Finish> FINISH_MAP;
    private static final String FINISH_NOT_YET = "notYet";
    private static final int LEN_APPLET_INSTANCE_AID = 32;
    private static final int LEN_NODECODELIST_MAX = 32;
    private static final int LEN_NODECODESIZE = 4;
    private static final String NAME_ADDITIONAL_INFO = "additionalInfo";
    private static final String NAME_ADDITIONAL_INFO_HASH = "additionalInfoHash";
    private static final String NAME_APPLET_INSTANCE_AID = "appletInstanceAid";
    private static final String NAME_CARD_CATEGORY = "cardCategory";
    private static final String NAME_CARD_IDENTIFIABLE_INFO = "cardIdentifiableInfo";
    private static final String NAME_CARD_STATUS = "cardStatus";
    private static final String NAME_CARD_STATUSES = "cardStatuses";
    private static final String NAME_CARD_TYPE = "cardType";
    private static final String NAME_CID = "cid";
    private static final String NAME_FINISH = "finish";
    private static final String NAME_IDM = "idm";
    private static final String NAME_ISSUER_ID = "issuerId";
    private static final String NAME_ISSUER_INFO = "issuerInfo";
    private static final String NAME_NODE_CODE_LIST = "nodeCodeList";
    private static final String NAME_POSITION = "position";
    private static final String NAME_REISSUE_STATUS = "reissueStatus";
    private static final String NAME_SERVICE_ID = "serviceId";
    private static final String NAME_SERVICE_TYPE = "serviceType";
    private static final String NAME_SE_ID = "seId";
    private static final String NAME_SE_INFO = "seInfo";
    private static final String NAME_SE_TYPE = "seType";
    private static final String NAME_SP_ADDITIONAL_INFO = "spAdditionalInfo";
    private static final String NAME_SUB_STATUS = "subStatus";
    private static final String NAME_TASK = "task";
    private static final String NAME_WALLET_APP_ID = "walletAppId";
    private static final String POSITION_BACKGROUND = "Background";
    private static final String POSITION_FOREGROUND = "Foreground";
    private static final HashMap<String, Integer> POSITION_MAP;
    private static final HashMap<String, Boolean> REISSUE_POSSIBILITY_MAP;
    private static final HashMap<String, CompleteCardInfo.ReissueStatus> REISSUE_STATUS_MAP;
    private static final String REISSUE_STATUS_PENDING = "pending";
    private static final String REISSUE_STATUS_REISSUABLE = "reissuable";
    private static final String REISSUE_STATUS_UNKNOWN = "unknown";
    private static final String REISSUE_STATUS_UNREISSUABLE = "unreissuable";
    private static final String STATUS_ACTIVE = "Active";
    private static final String STATUS_DELETED = "Deleted";
    private static final int STATUS_INT_UNKNOWN = -1;
    private static final String STATUS_IN_PROCESS = "inProcess";
    private static final String STATUS_LOST = "Lost";
    private static final HashMap<String, Integer> STATUS_MAP;
    private static final String TASK_DELETE = "Delete";
    private static final String TASK_DISABLE = "Disable";
    private static final String TASK_ENABLE = "Enable";
    private static final String TASK_ISSUE = "Issue";
    private static final HashMap<String, Integer> TASK_MAP;
    private static final String TASK_PERMANET_DELETE = "PermanentDelete";
    private static final String TASK_SE_ACCESS = "SeAccess";

    public enum CheckType {
        FWS_GET_CARD_LIST,
        FWS_CREATE_CARD,
        FWS_GET_ISSUE_SCRIPT,
        FWS_GET_ENABLE_SCRIPT,
        FWS_GET_DISABLE_SCRIPT,
        FWS_GET_DELETE_SCRIPT,
        FWS_GET_ACCESS_SCRIPT,
        FWS_GET_UPLOAD_TARGET,
        ASSET_INDIVIDUAL_CARD
    }

    static {
        HashMap<String, Integer> map = new HashMap<>();
        STATUS_MAP = map;
        map.put(STATUS_IN_PROCESS, 0);
        map.put(STATUS_ACTIVE, 1);
        map.put(STATUS_LOST, 2);
        map.put(STATUS_DELETED, 3);
        HashMap<String, Integer> map2 = new HashMap<>();
        POSITION_MAP = map2;
        map2.put(POSITION_FOREGROUND, 0);
        map2.put(POSITION_BACKGROUND, 1);
        HashMap<String, CompleteCardInfo.Finish> map3 = new HashMap<>();
        FINISH_MAP = map3;
        map3.put(FINISH_NOT_YET, CompleteCardInfo.Finish.NOT_YET);
        map3.put(FINISH_DONE, CompleteCardInfo.Finish.DONE);
        HashMap<String, Integer> map4 = new HashMap<>();
        TASK_MAP = map4;
        map4.put(TASK_ISSUE, 0);
        map4.put(TASK_ENABLE, 1);
        map4.put(TASK_DISABLE, 2);
        map4.put(TASK_PERMANET_DELETE, 3);
        map4.put(TASK_DELETE, 4);
        map4.put(TASK_SE_ACCESS, 5);
        HashMap<String, Boolean> map5 = new HashMap<>();
        REISSUE_POSSIBILITY_MAP = map5;
        map5.put(REISSUE_STATUS_REISSUABLE, Boolean.TRUE);
        map5.put(REISSUE_STATUS_UNREISSUABLE, Boolean.FALSE);
        map5.put(REISSUE_STATUS_PENDING, Boolean.TRUE);
        map5.put("unknown", Boolean.TRUE);
        HashMap<String, CompleteCardInfo.ReissueStatus> map6 = new HashMap<>();
        REISSUE_STATUS_MAP = map6;
        map6.put(REISSUE_STATUS_REISSUABLE, CompleteCardInfo.ReissueStatus.REISSUABLE);
        map6.put(REISSUE_STATUS_UNREISSUABLE, CompleteCardInfo.ReissueStatus.UNREISSUABLE);
        map6.put(REISSUE_STATUS_PENDING, CompleteCardInfo.ReissueStatus.PENDING);
        map6.put("unknown", CompleteCardInfo.ReissueStatus.UNKNOWN);
    }

    public CardJson(String json) throws JSONException {
        super(json);
    }

    public String getServiceType() throws JSONException {
        return JsonUtil.checkString((JSONObject) this, NAME_SERVICE_TYPE, true, 8);
    }

    public CompleteCardInfo getCardInfo(CheckType checkType, SeInfo deviceSeInfo) throws JSONException {
        boolean zIsChipGP;
        int[] iArr;
        String str;
        int iIntValue;
        int iIntValue2;
        CompleteCardInfo.Finish finish;
        boolean z;
        CompleteCardInfo.ReissueStatus reissueStatus;
        String str2;
        int iIntValue3;
        String strCheckString = JsonUtil.checkString((JSONObject) this, "serviceId", true, 0);
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject((JSONObject) this, NAME_SE_INFO, true);
        String strCheckString2 = JsonUtil.checkString(jSONObjectCheckObject, NAME_SE_ID, true, 0);
        try {
            byte[] bArrHexToByteArray = StringUtil.hexToByteArray(JsonUtil.checkString(jSONObjectCheckObject, NAME_IDM, true, 16));
            String strCheckString3 = JsonUtil.checkString((JSONObject) this, NAME_SERVICE_TYPE, true, 8);
            String strCheckString4 = (checkType == CheckType.ASSET_INDIVIDUAL_CARD || (ServiceTypeInfoUtil.SysType.isPrivate(strCheckString3) && ServiceTypeInfoUtil.MultiCardType.isLocalMultiple(strCheckString3))) ? JsonUtil.checkString((JSONObject) this, NAME_CARD_CATEGORY, true, 0) : null;
            if (strCheckString2.equalsIgnoreCase(deviceSeInfo.getSeId())) {
                zIsChipGP = Property.isChipGP();
            } else {
                zIsChipGP = SeInfo.SE_TYPE_10.equals(JsonUtil.checkString(jSONObjectCheckObject, NAME_SE_TYPE, true, 0));
            }
            if (checkType == CheckType.ASSET_INDIVIDUAL_CARD || zIsChipGP || !ServiceTypeInfoUtil.SysType.isPrivate(strCheckString3)) {
                iArr = null;
            } else {
                JSONArray jSONArrayCheckArray = JsonUtil.checkArray(JsonUtil.checkObject((JSONObject) this, NAME_CARD_IDENTIFIABLE_INFO, true), NAME_NODE_CODE_LIST, true);
                int length = jSONArrayCheckArray.length();
                if (length > 32) {
                    throw new JSONException("nodeCodeList length is invalid.");
                }
                int[] iArr2 = new int[length];
                for (int i = 0; i < length; i++) {
                    try {
                        iArr2[i] = StringUtil.hexToInteger(JsonUtil.checkString(jSONArrayCheckArray, i, true, 4));
                    } catch (NumberFormatException unused) {
                        throw new JSONException("nodeCode is invalid.");
                    }
                }
                iArr = iArr2;
            }
            if (zIsChipGP) {
                String strCheckString5 = JsonUtil.checkString((JSONObject) this, NAME_APPLET_INSTANCE_AID, true, 32);
                try {
                    StringUtil.hexToByteArray(strCheckString5);
                    str = strCheckString5;
                } catch (NumberFormatException unused2) {
                    throw new JSONException("appletInstanceAid is invalid.");
                }
            } else {
                str = null;
            }
            String strCheckString6 = checkType == CheckType.FWS_GET_CARD_LIST ? JsonUtil.checkString((JSONObject) this, NAME_ADDITIONAL_INFO_HASH, true, 0) : null;
            String strCheckString7 = (checkType == CheckType.ASSET_INDIVIDUAL_CARD || checkType == CheckType.FWS_GET_UPLOAD_TARGET) ? null : JsonUtil.checkString(JsonUtil.checkObject((JSONObject) this, NAME_ISSUER_INFO, true), NAME_WALLET_APP_ID, true, 0);
            JSONObject jSONObjectCheckObject2 = JsonUtil.checkObject((JSONObject) this, NAME_CARD_STATUSES, true);
            String strCheckString8 = JsonUtil.checkString(jSONObjectCheckObject2, NAME_CARD_STATUS, true, 0);
            if (checkType == CheckType.FWS_CREATE_CARD) {
                HashMap<String, Integer> map = STATUS_MAP;
                iIntValue = map.containsKey(strCheckString8) ? map.get(strCheckString8).intValue() : -1;
            } else {
                HashMap<String, Integer> map2 = STATUS_MAP;
                if (!map2.containsKey(strCheckString8)) {
                    throw new JSONException("Not supported status :" + strCheckString8);
                }
                iIntValue = map2.get(strCheckString8).intValue();
            }
            JSONObject jSONObjectCheckObject3 = JsonUtil.checkObject(jSONObjectCheckObject2, NAME_SUB_STATUS, true);
            if (1 == iIntValue) {
                String strCheckString9 = JsonUtil.checkString(jSONObjectCheckObject3, NAME_POSITION, true, 0);
                HashMap<String, Integer> map3 = POSITION_MAP;
                if (!map3.containsKey(strCheckString9)) {
                    throw new JSONException("Not supported position :" + strCheckString9);
                }
                iIntValue2 = map3.get(strCheckString9).intValue();
            } else {
                iIntValue2 = 3;
            }
            if (1 == iIntValue || 3 == iIntValue) {
                String strCheckString10 = JsonUtil.checkString(jSONObjectCheckObject3, NAME_FINISH, true, 0);
                HashMap<String, CompleteCardInfo.Finish> map4 = FINISH_MAP;
                if (!map4.containsKey(strCheckString10)) {
                    throw new JSONException("Not supported finish :" + strCheckString10);
                }
                finish = map4.get(strCheckString10);
            } else {
                finish = CompleteCardInfo.Finish.EMPTY;
            }
            if (3 == iIntValue) {
                String strCheckString11 = JsonUtil.checkString(jSONObjectCheckObject3, NAME_REISSUE_STATUS, true, 0);
                HashMap<String, Boolean> map5 = REISSUE_POSSIBILITY_MAP;
                if (!map5.containsKey(strCheckString11)) {
                    throw new JSONException("Not supported reissueStatus :" + strCheckString11);
                }
                boolean zBooleanValue = map5.get(strCheckString11).booleanValue();
                reissueStatus = REISSUE_STATUS_MAP.get(strCheckString11);
                z = zBooleanValue;
            } else {
                z = false;
                reissueStatus = null;
            }
            if (checkType == CheckType.ASSET_INDIVIDUAL_CARD) {
                str2 = strCheckString7;
                iIntValue3 = -1;
            } else {
                str2 = strCheckString7;
                String strCheckString12 = JsonUtil.checkString(jSONObjectCheckObject2, NAME_TASK, true, 0);
                HashMap<String, Integer> map6 = TASK_MAP;
                if (!map6.containsKey(strCheckString12)) {
                    throw new JSONException("Not supported task :" + strCheckString12);
                }
                iIntValue3 = map6.get(strCheckString12).intValue();
            }
            String strCheckString13 = checkType == CheckType.ASSET_INDIVIDUAL_CARD ? null : JsonUtil.checkString((JSONObject) this, NAME_CARD_TYPE, true, 0);
            LogMgr.log(6, "001 cardType : " + strCheckString13);
            return new CompleteCardInfo(getCid(), strCheckString, str2, iIntValue, iIntValue2, iIntValue3, z, strCheckString3, strCheckString6, strCheckString4, str, finish, reissueStatus, strCheckString2, bArrHexToByteArray, iArr, strCheckString13);
        } catch (NumberFormatException unused3) {
            throw new JSONException("idm is invalid.");
        }
    }

    public CardAdditionalInfo getCardAdditionalInfo() throws JSONException {
        return new CardAdditionalInfo(getCid(), JsonUtil.checkString((JSONObject) this, NAME_CARD_TYPE, true, 0), JsonUtil.checkString(JsonUtil.checkObject((JSONObject) this, NAME_ISSUER_INFO, true), NAME_ISSUER_ID, false, 0), JsonUtil.checkObject((JSONObject) this, NAME_ADDITIONAL_INFO, true), JsonUtil.checkString((JSONObject) this, NAME_ADDITIONAL_INFO_HASH, true, 0));
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0023 A[PHI: r0
  0x0023: PHI (r0v3 int) = (r0v2 int), (r0v4 int) binds: [B:5:0x0018, B:7:0x001b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CompleteCardInfo getCardInfoWithSpStatus(CheckType checkType, String mfcVersion, SeInfo deviceSeInfo) throws JSONException {
        CompleteCardInfo cardInfo = getCardInfo(checkType, deviceSeInfo);
        int i = 3;
        if (3 == cardInfo.getCardStatus()) {
            int i2 = AnonymousClass1.$SwitchMap$com$felicanetworks$mfc$mfi$CompleteCardInfo$ReissueStatus[cardInfo.getReissueStatus().ordinal()];
            int i3 = 1;
            if (i2 != 1) {
                i3 = 2;
                if (i2 == 2) {
                    i = i3;
                } else if (i2 != 3) {
                    i = 4;
                    if (i2 != 4) {
                        i = 5;
                    }
                }
            }
        }
        cardInfo.setSpStatus(i);
        JSONObject jSONObjectCheckObject = JsonUtil.checkObject((JSONObject) this, NAME_SP_ADDITIONAL_INFO, false);
        if (jSONObjectCheckObject != null) {
            cardInfo.setSpAdditionalInfo(jSONObjectCheckObject);
        }
        return cardInfo;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfc.mfi.fws.json.CardJson$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfc$mfi$CompleteCardInfo$ReissueStatus;

        static {
            int[] iArr = new int[CompleteCardInfo.ReissueStatus.values().length];
            $SwitchMap$com$felicanetworks$mfc$mfi$CompleteCardInfo$ReissueStatus = iArr;
            try {
                iArr[CompleteCardInfo.ReissueStatus.REISSUABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$CompleteCardInfo$ReissueStatus[CompleteCardInfo.ReissueStatus.UNREISSUABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$CompleteCardInfo$ReissueStatus[CompleteCardInfo.ReissueStatus.PENDING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$CompleteCardInfo$ReissueStatus[CompleteCardInfo.ReissueStatus.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public String getCid() throws JSONException {
        return JsonUtil.checkString((JSONObject) this, NAME_CID, true, 0);
    }
}

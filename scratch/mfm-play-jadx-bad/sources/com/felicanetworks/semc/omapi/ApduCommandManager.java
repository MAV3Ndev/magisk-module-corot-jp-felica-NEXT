package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.SemChipHolder;
import com.felicanetworks.semc.sws.json.JsonConst;
import com.felicanetworks.semc.sws.json.JsonUtil;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ApduCommandManager {
    private static final int MAX_APDU_RESPONSE_LENGTH = 8196;
    private static final int MAX_APDU_RESULT_MESSAGE_LENGTH = 1024;
    private static final String RESULT_CODE_SUCCESS = "0000";
    private static final String RESULT_MSG_SUCCESS = "SW=";
    private ApduResponse mApduResponse;
    private SemChipHolder mChipHolder;
    private GpController mGpController;

    public ApduCommandManager(SemChipHolder semChipHolder) throws IllegalArgumentException {
        LogMgr.log(6, "000");
        if (semChipHolder == null) {
            LogMgr.log(1, "800 Parameter(s) must not be null.");
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        this.mChipHolder = semChipHolder;
        LogMgr.log(6, "999");
    }

    public void sendCommand(JSONArray jSONArray) throws Exception {
        LogMgr.log(6, "000");
        this.mApduResponse = null;
        doSendCommand(jSONArray);
        LogMgr.log(6, "999");
    }

    public JSONArray getApduResponse() throws IllegalArgumentException {
        LogMgr.log(6, "000");
        ApduResponse apduResponse = this.mApduResponse;
        JSONArray apduResponseInfoList = apduResponse != null ? apduResponse.getApduResponseInfoList() : null;
        LogMgr.log(6, "999");
        return apduResponseInfoList;
    }

    private void doSendCommand(JSONArray jSONArray) throws Exception {
        LogMgr.log(8, "000");
        if (jSONArray == null) {
            LogMgr.log(1, "800 commandApduInfo is null.");
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        try {
            this.mApduResponse = new ApduResponse(jSONArray);
            this.mGpController = this.mChipHolder.getGpController();
            int apduCommandInfoListCount = this.mApduResponse.getApduCommandInfoListCount();
            loop0: for (int i = 0; i < apduCommandInfoListCount; i++) {
                this.mApduResponse.setRunApduFlag(false);
                this.mApduResponse.setCurrentApduCommandInfoIdx(i);
                byte[] selectAid = this.mApduResponse.getSelectAid();
                byte selectP2 = this.mApduResponse.getSelectP2();
                this.mApduResponse.removeSelect();
                this.mApduResponse.removeApduCommandList();
                this.mApduResponse.setCurrentApduCommandIdx(0);
                if (!select(selectAid, selectP2)) {
                    break;
                }
                int apduCommandCount = this.mApduResponse.getApduCommandCount();
                for (int i2 = 0; i2 < apduCommandCount; i2++) {
                    this.mApduResponse.setRunApduFlag(false);
                    this.mApduResponse.setCurrentApduCommandIdx(i2);
                    if (!transmit(this.mApduResponse.getApduCommand())) {
                        break loop0;
                    }
                }
            }
            LogMgr.log(8, "999");
        } catch (GpException e) {
            LogMgr.log(1, "801 GpException occurred : " + e.getClass().getSimpleName() + " " + e.getMessage());
            LogMgr.printStackTrace(9, e);
            throw e;
        } catch (IllegalArgumentException e2) {
            e = e2;
            LogMgr.printStackTrace(9, e);
            throw e;
        } catch (InterruptedException e3) {
            e = e3;
            LogMgr.printStackTrace(9, e);
            throw e;
        } catch (JSONException e4) {
            LogMgr.log(1, "802 Error processing C-APDU.");
            LogMgr.printStackTrace(9, e4);
            throw e4;
        } catch (Exception e5) {
            LogMgr.log(1, "803 Unexpected Exception occurred: " + e5.getClass().getSimpleName() + " " + e5.getMessage());
            LogMgr.printStackTrace(9, e5);
            throw e5;
        }
    }

    private boolean select(byte[] bArr, byte b) throws GpException, InterruptedException {
        byte[] bArrSelect;
        LogMgr.log(8, "000");
        if (this.mApduResponse.getSelect() == null) {
            LogMgr.log(8, "select attribute was null, so will not call select.994 ret=[true]");
            return true;
        }
        try {
            LogMgr.log(8, "001");
            bArrSelect = this.mGpController.select(bArr, b);
        } catch (GpException e) {
            if (e.getType() == 203) {
                this.mApduResponse.setSelectResultInfo(e.getApduResultCode(), e.getSwsMessage(), null);
                throw new GpException(203, ObfuscatedMsgUtil.executionPoint(e), e.getMessage());
            }
            byte[] response = e.getResponse();
            String swsMessage = e.getSwsMessage();
            if (response == null) {
                LogMgr.log(1, "802 Failed access to chip");
                this.mApduResponse.setSelectResultInfo(e.getApduResultCode(), swsMessage, null);
                LogMgr.log(8, "995 ret=[false]");
                return false;
            }
            SelectResponse selectResponse = new SelectResponse(response);
            if (selectResponse.isStatusFailed()) {
                this.mApduResponse.setSelectResultInfo("0000", "SW=" + StringUtil.bytesToHexString(selectResponse.getSw()), response);
                LogMgr.log(8, "996 ret=[false]");
                return false;
            }
            bArrSelect = response;
        } catch (InterruptedException e2) {
            LogMgr.log(1, "800 cancel occurred.");
            LogMgr.printStackTrace(9, e2);
            throw e2;
        }
        if (bArrSelect == null) {
            LogMgr.log(1, "803 Failed access to chip");
            this.mApduResponse.setSelectResultInfo("3000", "Response is null.", null);
            LogMgr.log(8, "997 ret=[false]");
            return false;
        }
        if (bArrSelect.length > MAX_APDU_RESPONSE_LENGTH) {
            LogMgr.log(1, "804 Response length is over 8196bytes.");
            this.mApduResponse.setSelectResultInfo("4001", ObfuscatedMsgUtil.omapiExecutionPoint("Channel#getSelectResponse", "ResponseTooLong"), bArrSelect);
            LogMgr.log(8, "998 ret=[false]");
            return false;
        }
        byte[] sw = new SelectResponse(bArrSelect).getSw();
        this.mApduResponse.setSelectResultInfo("0000", "SW=" + StringUtil.bytesToHexString(sw), bArrSelect);
        LogMgr.log(8, "999 ret=[true]");
        return true;
    }

    private boolean transmit(byte[] bArr) throws GpException, InterruptedException {
        LogMgr.log(8, "000");
        this.mApduResponse.setRunApduFlag(true);
        try {
            byte[] bArrTransmit = this.mGpController.transmit(bArr);
            if (bArrTransmit == null) {
                LogMgr.log(8, "001 Response is null.");
                this.mApduResponse.setApduResponseList("4001", "Response is null.", null);
                LogMgr.log(8, "996 ret=[false]");
                return false;
            }
            if (bArrTransmit.length > MAX_APDU_RESPONSE_LENGTH) {
                LogMgr.log(1, "803 Response length is over 8196bytes.");
                this.mApduResponse.setApduResponseList("4001", ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", "ResponseTooLong"), bArrTransmit);
                LogMgr.log(8, "998 ret=[false]");
                return false;
            }
            byte[] sw = new ResponseAnalyzer(bArrTransmit).getSw();
            this.mApduResponse.setApduResponseList("0000", "SW=" + StringUtil.bytesToHexString(sw), bArrTransmit);
            LogMgr.log(8, "999 ret=[true]");
            return true;
        } catch (GpException e) {
            LogMgr.log(1, "802 Failed access to chip");
            if (e.getType() == 203) {
                this.mApduResponse.setApduResponseList(e.getApduResultCode(), e.getSwsMessage(), null);
                throw new GpException(203, ObfuscatedMsgUtil.executionPoint(e), e.getMessage());
            }
            byte[] response = e.getResponse();
            String swsMessage = e.getSwsMessage();
            if (response == null) {
                this.mApduResponse.setApduResponseList(e.getApduResultCode(), swsMessage, null);
                LogMgr.log(8, "995 ret=[false]");
                return false;
            }
            ResponseAnalyzer responseAnalyzer = new ResponseAnalyzer(response);
            if (responseAnalyzer.isStatusFailed()) {
                this.mApduResponse.setApduResponseList("0000", "SW=" + StringUtil.bytesToHexString(responseAnalyzer.getSw()), response);
                LogMgr.log(8, "996 ret=[false]");
                return false;
            }
            LogMgr.log(8, "995 ret=[false]");
            return false;
        } catch (InterruptedException e2) {
            LogMgr.log(1, "800 cancel occurred");
            LogMgr.printStackTrace(9, e2);
            throw e2;
        }
    }

    private static class ApduResponse {
        private static final String KEY_AID = "aid";
        private static final String KEY_APDU_COMMAND = "apduCommand";
        private static final String KEY_APDU_COMMAND_LIST = "apduCommandList";
        private static final String KEY_APDU_ID = "apduId";
        private static final String KEY_APDU_NAME = "apduName";
        private static final String KEY_APDU_RESPONSE = "apduResponse";
        private static final String KEY_APDU_RESPONSE_LIST = "apduResponseList";
        private static final String KEY_APDU_RESULT_CODE = "apduResultCode";
        private static final String KEY_APDU_RESULT_MESSAGE = "apduResultMessage";
        private static final String KEY_P2 = "p2";
        private static final String KEY_SELECT = "select";
        private static final String KEY_SELECT_RESULT_INFO = "selectResultInfo";
        private JSONObject mApduCommandInfo;
        private JSONObject mApduResponse;
        private JSONArray mApduResponseInfoList;
        private JSONArray mApduResponseList;
        private int mCurrentApduCommandIndex;
        private int mCurrentApduCommandInfoIndex;
        private boolean mIsRunApdu;
        private JSONObject mSelect;

        private ApduResponse(JSONArray jSONArray) throws JSONException {
            this.mCurrentApduCommandInfoIndex = 0;
            this.mCurrentApduCommandIndex = 0;
            this.mIsRunApdu = false;
            LogMgr.log(8, "000");
            this.mApduResponseInfoList = jSONArray;
            LogMgr.log(8, "999");
        }

        JSONArray getApduResponseInfoList() throws IllegalArgumentException {
            LogMgr.log(8, "000");
            try {
                prune();
                LogMgr.log(8, "999");
                return this.mApduResponseInfoList;
            } catch (JSONException e) {
                LogMgr.log(2, "700 Unable to generate R-APDU.");
                LogMgr.printStackTrace(9, e);
                throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
            }
        }

        int getApduCommandInfoListCount() {
            LogMgr.log(8, "000");
            LogMgr.log(8, "999");
            return this.mApduResponseInfoList.length();
        }

        int getApduCommandCount() {
            LogMgr.log(8, "000");
            if (this.mApduResponseList != null) {
                LogMgr.log(8, "998");
                return this.mApduResponseList.length();
            }
            LogMgr.log(8, "999");
            return 0;
        }

        void setRunApduFlag(boolean z) {
            this.mIsRunApdu = z;
        }

        void removeSelect() {
            LogMgr.log(8, "000");
            JSONObject jSONObject = this.mApduCommandInfo;
            if (jSONObject != null && jSONObject.has(KEY_SELECT)) {
                this.mApduCommandInfo.remove(KEY_SELECT);
            } else {
                LogMgr.log(8, "No select JsonObject");
            }
            LogMgr.log(8, "999");
        }

        void removeApduCommandList() {
            LogMgr.log(8, "000");
            JSONObject jSONObject = this.mApduCommandInfo;
            if (jSONObject != null && jSONObject.has(KEY_APDU_COMMAND_LIST)) {
                this.mApduCommandInfo.remove(KEY_APDU_COMMAND_LIST);
            } else {
                LogMgr.log(8, "No apduCommandList JsonObject");
            }
            LogMgr.log(8, "999");
        }

        JSONObject getSelect() {
            LogMgr.log(8, "000");
            LogMgr.log(8, "999");
            return this.mSelect;
        }

        byte[] getSelectAid() {
            byte[] bArrHexToByteArray;
            LogMgr.log(8, "000");
            JSONObject jSONObject = this.mSelect;
            if (jSONObject == null || !jSONObject.has(KEY_AID)) {
                bArrHexToByteArray = null;
            } else {
                try {
                    bArrHexToByteArray = StringUtil.hexToByteArray((String) this.mSelect.opt(KEY_AID));
                } catch (ClassCastException unused) {
                    bArrHexToByteArray = null;
                }
            }
            LogMgr.log(8, "999");
            return bArrHexToByteArray;
        }

        byte getSelectP2() throws JSONException {
            LogMgr.log(8, "000");
            JSONObject jSONObject = this.mSelect;
            byte b = 0;
            if (jSONObject != null) {
                String strCheckString = JsonUtil.checkString(jSONObject, KEY_P2, false, 2);
                if (strCheckString != null) {
                    LogMgr.log(9, "001");
                    b = StringUtil.hexToByteArray(strCheckString)[0];
                } else {
                    LogMgr.log(9, "002");
                }
            }
            LogMgr.log(8, "999 p2=" + ((int) b));
            return b;
        }

        byte[] getApduCommand() throws JSONException {
            return StringUtil.hexToByteArray((String) this.mApduResponse.get(KEY_APDU_COMMAND));
        }

        void setCurrentApduCommandInfoIdx(int i) throws JSONException, IllegalArgumentException {
            LogMgr.log(8, "000 idx=[" + i + "]");
            if (i < 0) {
                LogMgr.log(1, "800 idx=[" + i + "]");
                throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
            }
            this.mCurrentApduCommandInfoIndex = i;
            updateApduCommandInfo();
            LogMgr.log(8, "999");
        }

        void setCurrentApduCommandIdx(int i) throws JSONException, IllegalArgumentException {
            LogMgr.log(8, "000 idx=[" + i + "]");
            if (i < 0) {
                LogMgr.log(1, "800 idx=[" + i + "]");
                throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
            }
            this.mCurrentApduCommandIndex = i;
            updateApduResponse();
            LogMgr.log(8, "999");
        }

        void setSelectResultInfo(String str, String str2, byte[] bArr) throws IllegalArgumentException {
            String strBytesToHexString = StringUtil.bytesToHexString(bArr);
            LogMgr.log(8, "000 resultCode=[" + str + "]resultMessage=[" + str2 + "]response=[" + strBytesToHexString + "]");
            try {
                JSONObject jSONObject = new JSONObject();
                if (str == null) {
                    LogMgr.log(9, "001 code is null. Replace code to: 4001");
                    str = "4001";
                }
                jSONObject.put(KEY_APDU_RESULT_CODE, str);
                setApduResultMessage(jSONObject, str2);
                if (strBytesToHexString != null && !str.equals("4001")) {
                    if (strBytesToHexString.length() <= ApduCommandManager.MAX_APDU_RESPONSE_LENGTH) {
                        jSONObject.put(KEY_APDU_RESPONSE, strBytesToHexString);
                    } else {
                        jSONObject.put(KEY_APDU_RESULT_CODE, "4001");
                    }
                }
                this.mApduCommandInfo.put(KEY_SELECT_RESULT_INFO, jSONObject);
                this.mApduResponseInfoList.put(this.mCurrentApduCommandInfoIndex, this.mApduCommandInfo);
            } catch (JSONException e) {
                throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint(e));
            }
        }

        void setApduResponseList(String str, String str2, byte[] bArr) throws IllegalArgumentException {
            String strBytesToHexString = StringUtil.bytesToHexString(bArr);
            LogMgr.log(8, "000 code=[" + str + "] message=[" + str2 + "]response=[" + strBytesToHexString + "]");
            try {
                this.mApduResponse.remove(KEY_APDU_COMMAND);
                if (str == null) {
                    LogMgr.log(9, "001 code is null. Replace code to: 4001");
                    str = "4001";
                }
                this.mApduResponse.put(KEY_APDU_RESULT_CODE, str);
                setApduResultMessage(this.mApduResponse, str2);
                if (strBytesToHexString != null && !str.equals("4001")) {
                    this.mApduResponse.put(KEY_APDU_RESPONSE, strBytesToHexString);
                }
                update();
                LogMgr.log(8, "999");
            } catch (JSONException e) {
                throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint(e));
            }
        }

        /* JADX DEBUG: Duplicate block (B:9:0x0032) to fix multi-entry loop: BACK_EDGE: B:9:0x0032 -> B:7:0x001e */
        private void prune() throws JSONException {
            int i;
            LogMgr.log(8, "000");
            int apduCommandCount = getApduCommandCount();
            int i2 = this.mCurrentApduCommandIndex;
            if (i2 < apduCommandCount - 1) {
                if (!this.mIsRunApdu) {
                    i = i2 + 1;
                    while (i < apduCommandCount) {
                        this.mApduResponseList.getJSONObject(i).remove(KEY_APDU_COMMAND);
                        this.mApduResponseList.getJSONObject(i).put(KEY_APDU_RESULT_CODE, "4000");
                    }
                    this.mApduResponseInfoList.getJSONObject(this.mCurrentApduCommandInfoIndex).put(KEY_APDU_RESPONSE_LIST, this.mApduResponseList);
                }
                i++;
            }
            if (this.mApduCommandInfo.has(KEY_SELECT)) {
                JSONObject jSONObject = this.mApduCommandInfo.getJSONObject(KEY_SELECT);
                jSONObject.remove(KEY_AID);
                jSONObject.remove(KEY_P2);
                jSONObject.put(KEY_APDU_RESULT_CODE, "4000");
                this.mApduResponseInfoList.getJSONObject(this.mCurrentApduCommandInfoIndex).put(KEY_SELECT_RESULT_INFO, jSONObject);
                this.mApduResponseInfoList.getJSONObject(this.mCurrentApduCommandInfoIndex).remove(KEY_SELECT);
            }
            int apduCommandInfoListCount = getApduCommandInfoListCount();
            int i3 = this.mCurrentApduCommandInfoIndex;
            if (i3 < apduCommandInfoListCount - 1) {
                while (true) {
                    i3++;
                    if (i3 >= apduCommandInfoListCount) {
                        break;
                    }
                    if (this.mApduResponseInfoList.getJSONObject(i3).has(KEY_SELECT)) {
                        JSONObject jSONObject2 = this.mApduResponseInfoList.getJSONObject(i3).getJSONObject(KEY_SELECT);
                        jSONObject2.remove(KEY_AID);
                        jSONObject2.remove(KEY_P2);
                        jSONObject2.put(KEY_APDU_RESULT_CODE, "4000");
                        this.mApduResponseInfoList.getJSONObject(i3).put(KEY_SELECT_RESULT_INFO, jSONObject2);
                        this.mApduResponseInfoList.getJSONObject(i3).remove(KEY_SELECT);
                    }
                    if (this.mApduResponseInfoList.getJSONObject(i3).has(KEY_APDU_COMMAND_LIST)) {
                        JSONArray jSONArray = this.mApduResponseInfoList.getJSONObject(i3).getJSONArray(KEY_APDU_COMMAND_LIST);
                        for (int i4 = 0; i4 < jSONArray.length(); i4++) {
                            jSONArray.getJSONObject(i4).remove(KEY_APDU_COMMAND);
                            jSONArray.getJSONObject(i4).put(KEY_APDU_RESULT_CODE, "4000");
                            this.mApduResponseInfoList.getJSONObject(i3).put(KEY_APDU_RESPONSE_LIST, jSONArray);
                            this.mApduResponseInfoList.getJSONObject(i3).remove(KEY_APDU_COMMAND_LIST);
                        }
                    }
                }
            }
            LogMgr.log(8, "999");
        }

        private void update() throws JSONException {
            JSONArray jSONArray;
            LogMgr.log(8, "000");
            JSONArray jSONArray2 = this.mApduResponseList;
            if (jSONArray2 != null && this.mIsRunApdu) {
                jSONArray2.put(this.mCurrentApduCommandIndex, this.mApduResponse);
            }
            if (this.mApduCommandInfo != null && (jSONArray = this.mApduResponseList) != null && jSONArray.length() != 0) {
                this.mApduCommandInfo.put(KEY_APDU_RESPONSE_LIST, this.mApduResponseList);
            }
            JSONArray jSONArray3 = this.mApduResponseInfoList;
            if (jSONArray3 != null) {
                jSONArray3.put(this.mCurrentApduCommandInfoIndex, this.mApduCommandInfo);
            }
            LogMgr.log(8, "999");
        }

        private void updateApduCommandInfo() throws JSONException {
            LogMgr.log(8, "000");
            if (this.mApduResponseInfoList.isNull(this.mCurrentApduCommandInfoIndex)) {
                throw new JSONException(ObfuscatedMsgUtil.executionPoint());
            }
            JSONObject jSONObject = this.mApduResponseInfoList.getJSONObject(this.mCurrentApduCommandInfoIndex);
            this.mApduCommandInfo = jSONObject;
            if (jSONObject == null || jSONObject.isNull(KEY_APDU_COMMAND_LIST)) {
                throw new JSONException(ObfuscatedMsgUtil.executionPoint());
            }
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject(this.mApduCommandInfo, KEY_SELECT, false);
            this.mSelect = jSONObjectCheckObject;
            if (jSONObjectCheckObject != null) {
                JsonUtil.checkString(jSONObjectCheckObject, KEY_AID, false, 10, 32);
                JsonUtil.checkString(this.mSelect, KEY_P2, false, 2);
            }
            this.mApduResponseList = this.mApduCommandInfo.getJSONArray(KEY_APDU_COMMAND_LIST);
            LogMgr.log(6, "999");
        }

        private void updateApduResponse() throws JSONException {
            LogMgr.log(6, "000");
            JSONArray jSONArray = this.mApduResponseList;
            if (jSONArray == null || jSONArray.isNull(this.mCurrentApduCommandIndex)) {
                throw new JSONException(ObfuscatedMsgUtil.executionPoint());
            }
            JSONObject jSONObject = this.mApduResponseList.getJSONObject(this.mCurrentApduCommandIndex);
            this.mApduResponse = jSONObject;
            JsonUtil.checkString(jSONObject, KEY_APDU_ID, true, 32);
            JsonUtil.checkString(this.mApduResponse, KEY_APDU_NAME, false, 0, 255);
            JsonUtil.checkString(this.mApduResponse, KEY_APDU_COMMAND, true, 10, JsonConst.LEN_APDU_COMMAND_MAX);
            LogMgr.log(6, "999");
        }

        private void setApduResultMessage(JSONObject jSONObject, String str) throws JSONException {
            LogMgr.log(8, "000");
            if (str == null) {
                LogMgr.log(9, "001");
            } else if (str.isEmpty()) {
                LogMgr.log(9, "002");
            } else if (str.length() > 1024) {
                LogMgr.log(9, "003");
                jSONObject.put(KEY_APDU_RESULT_MESSAGE, str.substring(0, 1024));
            } else {
                LogMgr.log(9, "004");
                jSONObject.put(KEY_APDU_RESULT_MESSAGE, str);
            }
            LogMgr.log(8, "999");
        }
    }
}

package com.felicanetworks.mfc.mfi.omapi;

import android.os.Build;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ApduCommandManager {
    private static final int MAX_RESPONSE_LENGTH = 2048;
    private static final int SW_LENGTH = 2;
    private ApduResponse mApduResponse;
    private MfiChipHolder mChipHolder;
    private GpController mGpController;

    public ApduCommandManager(MfiChipHolder mfiChipHolder) throws UnsupportedOperationException, IllegalArgumentException {
        LogMgr.log(3, "000");
        if (mfiChipHolder == null) {
            LogMgr.log(1, "800 Parameter(s) must not be null.");
            throw new IllegalArgumentException();
        }
        if (Build.VERSION.SDK_INT < 28) {
            LogMgr.log(1, "801 API is insufficient. API 28+ is needed.");
            throw new UnsupportedOperationException();
        }
        this.mChipHolder = mfiChipHolder;
        LogMgr.log(3, "999");
    }

    public void sendCommand(JSONObject jSONObject) throws JSONException, InterruptedException, IllegalArgumentException, GpException {
        LogMgr.log(3, "000");
        this.mApduResponse = null;
        doSendCommand(jSONObject);
        LogMgr.log(3, "999");
    }

    public JSONObject getApduResponse() throws IllegalArgumentException {
        ApduResponse apduResponse = this.mApduResponse;
        if (apduResponse != null) {
            return apduResponse.getResult();
        }
        return null;
    }

    private void doSendCommand(JSONObject jSONObject) throws JSONException, InterruptedException, IllegalArgumentException, GpException {
        LogMgr.log(4, "000");
        try {
            if (jSONObject == null) {
                LogMgr.log(1, "800 commandApduInfo is null.");
                throw new IllegalArgumentException("commandApduInfo is null.");
            }
            try {
                try {
                    try {
                        ApduResponse apduResponse = new ApduResponse(jSONObject);
                        this.mApduResponse = apduResponse;
                        apduResponse.checkCommandJson();
                        this.mGpController = this.mChipHolder.getGpController();
                        loop0: for (int i = 0; i < this.mApduResponse.getBlockCount(); i++) {
                            this.mApduResponse.setCurrentBlockId(i);
                            this.mApduResponse.setRunApduFlag(false);
                            if (!select(this.mApduResponse.getAid())) {
                                break;
                            }
                            for (int i2 = 0; i2 < this.mApduResponse.getCommandCount(); i2++) {
                                this.mApduResponse.setCurrentCmdId(i2);
                                this.mApduResponse.setRunApduFlag(false);
                                if (!transmit(this.mApduResponse.getCommand())) {
                                    break loop0;
                                }
                            }
                        }
                        LogMgr.log(4, "999");
                    } catch (InterruptedException e) {
                        throw e;
                    }
                } catch (GpException e2) {
                    this.mApduResponse.setErrorMessage(e2.getFwsMessage());
                    throw e2;
                } catch (JSONException e3) {
                    LogMgr.log(1, "801 Error processing C-APDU.");
                    LogMgr.printStackTrace(7, e3);
                    throw e3;
                }
            } catch (IllegalArgumentException e4) {
                throw e4;
            } catch (Exception e5) {
                LogMgr.log(1, "802 Unexpected Exception occurred: " + e5.getClass().getSimpleName() + " " + e5.getMessage());
                this.mApduResponse.setErrorMessage(e5.getClass().getSimpleName());
                throw e5;
            }
        } catch (Throwable th) {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
            throw th;
        }
    }

    private boolean select(byte[] bArr) throws InterruptedException, IllegalArgumentException, GpException {
        byte[] bArrSelect;
        LogMgr.log(6, "000");
        try {
            LogMgr.log(6, "001");
            bArrSelect = this.mGpController.select(bArr);
        } catch (GpException e) {
            byte[] response = e.getResponse();
            if (response != null) {
                SelectResponse selectResponse = new SelectResponse(response);
                if (!selectResponse.isStatusSuccess()) {
                    this.mApduResponse.setSelectResponse(response);
                    this.mApduResponse.setErrorMessage(ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_GET_SELECT_RESPONSE, GpException.FwsErrorMsg.SW + StringUtil.bytesToHexString(selectResponse.getSw())));
                    LogMgr.log(6, "998");
                    return false;
                }
                bArrSelect = response;
            } else {
                LogMgr.log(1, "801 Failed access to chip");
                throw e;
            }
        } catch (InterruptedException e2) {
            LogMgr.log(1, "800 cancel occurred.");
            LogMgr.printStackTrace(7, e2);
            throw e2;
        }
        if (bArr != null) {
            LogMgr.log(6, "002");
            if (bArrSelect.length > 2048) {
                byte[] bArr2 = new byte[2048];
                System.arraycopy(bArrSelect, 0, bArr2, 0, 2048);
                this.mApduResponse.setSelectResponse(bArr2);
                LogMgr.log(1, "803 Response length is over 2048bytes.");
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_GET_SELECT_RESPONSE, GpException.FwsErrorMsg.RESPONSE_TOO_LONG));
            }
            this.mApduResponse.setSelectResponse(bArrSelect);
        }
        LogMgr.log(6, "999");
        return true;
    }

    private boolean transmit(byte[] bArr) throws JSONException, InterruptedException, IllegalArgumentException, GpException {
        LogMgr.log(6, "000");
        if (bArr == null) {
            LogMgr.log(1, "800 apdu key is not found.");
            throw new JSONException("Required key(s) is/are not found.");
        }
        try {
            byte[] bArrTransmit = this.mGpController.transmit(bArr);
            if (bArrTransmit == null) {
                LogMgr.log(6, "Response is null.");
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_TRANSMIT, GpException.FwsErrorMsg.NULL));
            }
            this.mApduResponse.setRunApduFlag(true);
            ResponseAnalyzer responseAnalyzer = new ResponseAnalyzer(bArrTransmit);
            if (!responseAnalyzer.isStatusSuccess()) {
                LogMgr.log(1, "801 Failed access to chip");
                this.mApduResponse.setApduResponse(bArrTransmit);
                this.mApduResponse.setErrorMessage(ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_TRANSMIT, GpException.FwsErrorMsg.SW + StringUtil.bytesToHexString(responseAnalyzer.getSw())));
                return false;
            }
            if (bArrTransmit.length > 2048) {
                byte[] bArr2 = new byte[2048];
                System.arraycopy(bArrTransmit, 0, bArr2, 0, 2048);
                LogMgr.log(1, "803 Response length is over 2048bytes.");
                this.mApduResponse.setApduResponse(bArr2);
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_TRANSMIT, GpException.FwsErrorMsg.RESPONSE_TOO_LONG));
            }
            this.mApduResponse.setApduResponse(bArrTransmit);
            LogMgr.log(6, "999");
            return true;
        } catch (GpException e) {
            LogMgr.log(1, "801 Failed access to chip");
            throw e;
        } catch (InterruptedException e2) {
            LogMgr.log(1, "800 cancel occurred");
            LogMgr.printStackTrace(7, e2);
            throw e2;
        }
    }

    private static class ApduResponse {
        private static final String KEY_AID = "aid";
        private static final String KEY_APDU = "apdu";
        private static final String KEY_APDU_BLOCKLIST = "apduBlockList";
        private static final String KEY_APDU_CMDLIST = "apduCommandList";
        private static final String KEY_APDU_ID = "apduId";
        private static final String KEY_APDU_LIST_ID = "apduListId";
        private static final String KEY_APDU_LIST_INDEX = "apduListIndex";
        private static final String KEY_APDU_NAME = "apduName";
        private static final String KEY_ERROR_MESSAGE = "errorMessage";
        private static final String KEY_SELECT_RESULT = "selectResult";
        private JSONObject mApduBlock;
        private JSONArray mApduBlockList;
        private JSONObject mApduCommand;
        private JSONArray mApduCommandList;
        private int mCurrentBlockId;
        private int mCurrentCmdId;
        private boolean mIsRunApdu;
        private final JSONObject mResult;

        private ApduResponse(JSONObject jSONObject) throws JSONException {
            this.mCurrentBlockId = 0;
            this.mCurrentCmdId = 0;
            this.mIsRunApdu = false;
            LogMgr.log(3, "000");
            this.mResult = new JSONObject(jSONObject.toString());
            LogMgr.log(3, "999");
        }

        void checkCommandJson() throws JSONException {
            LogMgr.log(3, "000");
            refresh();
            if (!this.mResult.has(KEY_APDU_LIST_INDEX) || !this.mResult.has(KEY_APDU_LIST_ID) || !this.mResult.has(KEY_APDU_BLOCKLIST)) {
                throw new JSONException("Required key(s) is/are not found.");
            }
            LogMgr.log(3, "999");
        }

        JSONObject getResult() throws IllegalArgumentException {
            try {
                prune();
                return this.mResult;
            } catch (JSONException e) {
                LogMgr.log(1, "Unable to generate R-APDU.");
                LogMgr.printStackTrace(7, e);
                throw new IllegalArgumentException();
            }
        }

        int getBlockCount() {
            JSONArray jSONArray = this.mApduBlockList;
            if (jSONArray != null) {
                return jSONArray.length();
            }
            return 0;
        }

        int getCommandCount() {
            JSONArray jSONArray = this.mApduCommandList;
            if (jSONArray != null) {
                return jSONArray.length();
            }
            return 0;
        }

        byte[] getAid() {
            byte[] bArrHexToByteArray;
            LogMgr.log(3, "000");
            JSONObject jSONObject = this.mApduBlock;
            if (jSONObject == null || !jSONObject.has(KEY_AID)) {
                bArrHexToByteArray = null;
            } else {
                bArrHexToByteArray = StringUtil.hexToByteArray((String) this.mApduBlock.opt(KEY_AID));
                this.mApduBlock.remove(KEY_AID);
            }
            LogMgr.log(3, "999");
            return bArrHexToByteArray;
        }

        byte[] getCommand() throws JSONException {
            return StringUtil.hexToByteArray((String) this.mApduCommand.get(KEY_APDU));
        }

        void setRunApduFlag(boolean z) {
            this.mIsRunApdu = z;
        }

        void setCurrentBlockId(int i) throws JSONException, IllegalArgumentException {
            LogMgr.log(3, "000 idx: " + i);
            if (i < 0) {
                LogMgr.log(1, "800 idx = " + i);
                throw new IllegalArgumentException();
            }
            this.mCurrentBlockId = i;
            setCurrentCmdId(0);
            LogMgr.log(3, "999");
        }

        void setCurrentCmdId(int i) throws JSONException, IllegalArgumentException {
            LogMgr.log(3, "000 idx: " + i);
            if (i < 0) {
                LogMgr.log(1, "800 idx = " + i);
                throw new IllegalArgumentException();
            }
            this.mCurrentCmdId = i;
            refresh();
            if (!this.mApduCommand.has(KEY_APDU_ID) || !this.mApduCommand.has(KEY_APDU)) {
                throw new JSONException("Required key(s) is/are not found.");
            }
            LogMgr.log(3, "999");
        }

        void setApduResponse(byte[] bArr) throws IllegalArgumentException {
            String strBytesToHexString = StringUtil.bytesToHexString(bArr);
            LogMgr.log(3, "000 response: " + strBytesToHexString);
            try {
                this.mApduCommand.put(KEY_APDU, strBytesToHexString);
                update();
                LogMgr.log(3, "999");
            } catch (JSONException unused) {
                throw new IllegalArgumentException();
            }
        }

        void setSelectResponse(byte[] bArr) throws IllegalArgumentException {
            String strBytesToHexString = StringUtil.bytesToHexString(bArr);
            LogMgr.log(3, "000 response: " + strBytesToHexString);
            try {
                this.mApduBlock.put(KEY_SELECT_RESULT, strBytesToHexString);
                update();
                LogMgr.log(3, "999");
            } catch (JSONException unused) {
                throw new IllegalArgumentException();
            }
        }

        void setErrorMessage(String str) throws IllegalArgumentException {
            LogMgr.log(3, "000 message: " + str);
            try {
                this.mApduBlock.put(KEY_ERROR_MESSAGE, str);
                update();
                LogMgr.log(3, "999");
            } catch (JSONException unused) {
                throw new IllegalArgumentException();
            }
        }

        private void prune() throws JSONException {
            LogMgr.log(3, "000");
            int commandCount = getCommandCount();
            if (this.mCurrentCmdId < commandCount) {
                while (true) {
                    commandCount--;
                    if (commandCount < this.mCurrentCmdId) {
                        break;
                    } else {
                        this.mApduCommandList.remove(commandCount);
                    }
                }
            }
            int blockCount = getBlockCount();
            this.mApduBlockList.getJSONObject(this.mCurrentBlockId).remove(KEY_AID);
            int i = blockCount - 1;
            if (this.mCurrentBlockId < i) {
                while (i >= this.mCurrentBlockId + 1) {
                    this.mApduBlockList.remove(i);
                    i--;
                }
            }
            update();
            LogMgr.log(3, "999");
        }

        private void update() throws JSONException {
            LogMgr.log(4, "000");
            JSONArray jSONArray = this.mApduCommandList;
            if (jSONArray != null && this.mIsRunApdu) {
                jSONArray.put(this.mCurrentCmdId, this.mApduCommand);
            }
            JSONObject jSONObject = this.mApduBlock;
            if (jSONObject != null) {
                jSONObject.put(KEY_APDU_CMDLIST, this.mApduCommandList);
            }
            JSONArray jSONArray2 = this.mApduBlockList;
            if (jSONArray2 != null) {
                jSONArray2.put(this.mCurrentBlockId, this.mApduBlock);
            }
            JSONObject jSONObject2 = this.mResult;
            if (jSONObject2 != null) {
                jSONObject2.put(KEY_APDU_BLOCKLIST, this.mApduBlockList);
            }
            LogMgr.log(4, "999");
        }

        private void refresh() throws JSONException {
            LogMgr.log(4, "000");
            JSONObject jSONObject = this.mResult;
            if (jSONObject == null || jSONObject.isNull(KEY_APDU_BLOCKLIST)) {
                throw new JSONException("Required key(s) is/are not found.");
            }
            JSONArray jSONArray = this.mResult.getJSONArray(KEY_APDU_BLOCKLIST);
            this.mApduBlockList = jSONArray;
            if (jSONArray == null || jSONArray.isNull(this.mCurrentBlockId)) {
                throw new JSONException("Required key(s) is/are not found.");
            }
            JSONObject jSONObject2 = this.mApduBlockList.getJSONObject(this.mCurrentBlockId);
            this.mApduBlock = jSONObject2;
            if (jSONObject2 == null || jSONObject2.isNull(KEY_APDU_CMDLIST)) {
                throw new JSONException("Required key(s) is/are not found.");
            }
            JSONArray jSONArray2 = this.mApduBlock.getJSONArray(KEY_APDU_CMDLIST);
            this.mApduCommandList = jSONArray2;
            if (jSONArray2 == null || jSONArray2.isNull(this.mCurrentCmdId)) {
                throw new JSONException("Required key(s) is/are not found.");
            }
            this.mApduCommand = this.mApduCommandList.getJSONObject(this.mCurrentCmdId);
            LogMgr.log(4, "999");
        }
    }
}

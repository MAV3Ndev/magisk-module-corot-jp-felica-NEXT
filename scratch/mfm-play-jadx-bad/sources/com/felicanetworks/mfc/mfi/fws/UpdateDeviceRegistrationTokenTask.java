package com.felicanetworks.mfc.mfi.fws;

import android.content.Context;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.mfi.CloudMessagingCache;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.fcm.FcmGetTokenFuture;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.json.DeviceRegistrationRequestTokenPayloadJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.fws.json.UpdateDeviceRegistrationTokenRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.UpdateDeviceRegistrationTokenResponseJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class UpdateDeviceRegistrationTokenTask extends AsyncParentTaskBase<Void> {
    private static final List<String> VALID_RESULT_CODE_LIST_UPDATE_DEVICE_REG_TOKEN;
    private StoppableTaskBase mChildTask;
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public Void getResult2() {
        return null;
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(Void result) {
    }

    public class Result {
        public boolean isSuccess = false;
        public int errType = 100;
        public String errMsg = null;

        public Result() {
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_UPDATE_DEVICE_REG_TOKEN = arrayList;
        arrayList.add("0000");
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList.add(FwsConst.RESULT_ILLEGAL_REQUEST_TOKEN);
        arrayList.add(FwsConst.RESULT_INVALID_REQUEST_TOKEN);
        arrayList.add("4000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    public UpdateDeviceRegistrationTokenTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager) {
        super(taskId, executor, listener);
        this.mChildTask = null;
        this.mDataManager = dataManager;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        Context applicationContext;
        String str;
        LogMgr.log(6, "000");
        try {
            applicationContext = FelicaAdapter.getInstance().getApplicationContext();
            str = new FcmGetTokenFuture(applicationContext).get();
        } catch (Exception e) {
            LogMgr.log(2, e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
        if (str == null) {
            LogMgr.log(2, "700 Fcm token could not be obtained.");
            onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
            return;
        }
        if (CloudMessagingCache.getInstance(applicationContext).isCachedToken(str)) {
            LogMgr.log(6, "001 Already has registered.");
            onFinished(true, 0, null);
            return;
        }
        if (isStopped()) {
            LogMgr.log(2, "701 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        if (createSeInfo()) {
            if (isStopped()) {
                LogMgr.log(2, "702 Already has stopped.");
                onFinished(false, 215, null);
                return;
            }
            a aVarPrepareJwsCreator = prepareJwsCreator();
            if (aVarPrepareJwsCreator == null) {
                return;
            }
            if (isStopped()) {
                LogMgr.log(2, "703 Already has stopped.");
                clearJwsCreator(aVarPrepareJwsCreator);
                onFinished(false, 215, null);
                return;
            }
            String strCreateDeviceRegistrationRequestToken = createDeviceRegistrationRequestToken(aVarPrepareJwsCreator, str);
            if (strCreateDeviceRegistrationRequestToken == null) {
                clearJwsCreator(aVarPrepareJwsCreator);
                return;
            }
            if (isStopped()) {
                LogMgr.log(2, "704 Already has stopped.");
                clearJwsCreator(aVarPrepareJwsCreator);
                onFinished(false, 215, null);
                return;
            }
            clearJwsCreator(aVarPrepareJwsCreator);
            if (isStopped()) {
                LogMgr.log(2, "705 Already has stopped.");
                onFinished(false, 215, null);
                return;
            }
            FwsUpdateDeviceRegistrationTokenSubTask fwsUpdateDeviceRegistrationTokenSubTask = new FwsUpdateDeviceRegistrationTokenSubTask(0, this.mFwsClient, strCreateDeviceRegistrationRequestToken);
            setStoppableSubTask(fwsUpdateDeviceRegistrationTokenSubTask);
            fwsUpdateDeviceRegistrationTokenSubTask.start();
            AccessFwsTask.Result result = fwsUpdateDeviceRegistrationTokenSubTask.getResult2();
            LogMgr.log(6, "001 Is UpdateDeviceRegistrationToken success ? " + result.isSuccess);
            if (result.isSuccess) {
                CloudMessagingCache.getInstance(applicationContext).cacheToken(str);
            }
            onFinished(result.isSuccess, result.errType, result.errMsg);
            LogMgr.log(6, "999");
        }
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncParentTaskBase
    protected void setStoppableSubTask(StoppableTaskBase childTask) {
        synchronized (this) {
            this.mChildTask = childTask;
            if (isStopped()) {
                this.mChildTask.stop();
            }
        }
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncParentTaskBase, com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public synchronized void stop() {
        super.stop();
        StoppableTaskBase stoppableTaskBase = this.mChildTask;
        if (stoppableTaskBase != null) {
            stoppableTaskBase.stop();
        }
    }

    private a prepareJwsCreator() {
        a aVar = new a();
        try {
            if (Property.isChipGP()) {
                GpController gpController = this.mChipHolder.getGpController();
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "a", "a");
                aVar.a(gpController);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "a", "a");
            } else {
                Felica felica = this.mChipHolder.getFelica();
                LogMgr.performanceIn(LogMgr.PERFORMANCE_MFC, "a", "a");
                aVar.a(felica);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_MFC, "a", "a");
            }
            return aVar;
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(2, e);
            onFinished(false, 200, "Unknown error.");
            return null;
        }
    }

    private void clearJwsCreator(a jwsCreator) {
        try {
            jwsCreator.a();
        } catch (Exception e) {
            LogMgr.log(2, "700 : Exception");
            LogMgr.printStackTrace(2, e);
        }
    }

    private boolean createSeInfo() {
        LogMgr.log(6, "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
        boolean z = false;
        try {
            try {
                this.mDataManager.createSeInfo(mfiFelicaWrapper);
                mfiFelicaWrapper.close();
                mfiFelicaWrapper.closeSilently();
                z = true;
            } catch (MfiFelicaException e) {
                LogMgr.log(2, "700 MfiFelicaException");
                LogMgr.printStackTrace(7, e);
                onFinished(false, e.getType(), e.getMessage());
            } catch (GpException e2) {
                LogMgr.log(2, "701 GpException");
                onFinished(false, e2.getType(), e2.getMessage());
            } catch (Exception e3) {
                LogMgr.log(2, "702 " + e3.getClass().getSimpleName() + ":" + e3.getMessage());
                LogMgr.printStackTrace(7, e3);
                onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e3));
            }
            LogMgr.log(6, "999");
            return z;
        } finally {
            mfiFelicaWrapper.closeSilently();
        }
    }

    private String createDeviceRegistrationRequestToken(a jwsCreator, String token) {
        String strA;
        LogMgr.log(6, "000");
        try {
            DeviceRegistrationRequestTokenPayloadJson deviceRegistrationRequestTokenPayloadJson = new DeviceRegistrationRequestTokenPayloadJson();
            deviceRegistrationRequestTokenPayloadJson.setSeInfo(this.mDataManager.getSeInfo());
            deviceRegistrationRequestTokenPayloadJson.setRegistrationToken(token);
            LogMgr.log(6, "001");
            strA = jwsCreator.a(deviceRegistrationRequestTokenPayloadJson);
        } catch (JSONException e) {
            LogMgr.log(2, "700 : JSONException");
            LogMgr.printStackTrace(2, e);
            strA = null;
        } catch (Exception e2) {
            LogMgr.log(2, "701 : Exception");
            LogMgr.printStackTrace(2, e2);
            strA = null;
        }
        if (strA == null) {
            LogMgr.log(2, "702 : Request token is null.");
            onFinished(false, 200, "Unknown error.");
        }
        LogMgr.log(6, "999");
        return strA;
    }

    private static class FwsUpdateDeviceRegistrationTokenSubTask extends AccessFwsTask<UpdateDeviceRegistrationTokenResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;
        private final String mRegistrationRequestToken;

        FwsUpdateDeviceRegistrationTokenSubTask(int taskId, FwsClient fwsClient, String registrationRequestToken) {
            super(taskId, fwsClient);
            this.mRegistrationRequestToken = registrationRequestToken;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            UpdateDeviceRegistrationTokenRequestJson updateDeviceRegistrationTokenRequestJson = new UpdateDeviceRegistrationTokenRequestJson();
            updateDeviceRegistrationTokenRequestJson.setRequestId(createRequestId());
            updateDeviceRegistrationTokenRequestJson.setOperationId(FwsParamCreator.createOperationId());
            updateDeviceRegistrationTokenRequestJson.setRegistrationRequestToken(this.mRegistrationRequestToken);
            return updateDeviceRegistrationTokenRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.updateDeviceRegistrationToken(request, 1);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public UpdateDeviceRegistrationTokenResponseJson convertResponse(String response) throws JSONException {
            if (response != null) {
                return new UpdateDeviceRegistrationTokenResponseJson(response);
            }
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return UpdateDeviceRegistrationTokenTask.VALID_RESULT_CODE_LIST_UPDATE_DEVICE_REG_TOKEN;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_UPDATE_DEVICE_REGISTRATION_TOKEN.msg;
        }
    }
}

package com.felicanetworks.mfm.memory_clear;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.ServiceListActivity;
import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.internal.main.MemoryClearFuncEntity;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.SupportServiceType;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.memory_clear.MemoryClearConstants;
import com.felicanetworks.mfm.memory_clear.MemoryClearDisplayServiceFragment;
import com.felicanetworks.mfm.memory_clear.MemoryClearFunc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/* JADX INFO: loaded from: classes3.dex */
public class MemoryClearActivity extends AppCompatActivity {
    private static final int DELAY = 1000;
    private static int _staticCnt;
    private MyHandler _handler;
    private int mNightMode;
    private Thread mRunner;
    private MemoryClearFunc memoryClearFunc;
    MemoryClearConstants.RESULT_CODE mResultCode = MemoryClearConstants.RESULT_CODE.FAILED_CALLER;
    private ActivityStatus mActivityStatus = ActivityStatus.IDLE;
    private MemoryClearCustomDialogFragment mDialogFragment = null;
    private boolean _isFinishOperation = false;

    public enum ActivityStatus {
        IDLE,
        BACKGROUND,
        FOREGROUND
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int i = newConfig.uiMode & 48;
        int i2 = this.mNightMode;
        if (i2 != i) {
            int i3 = 1;
            if (i2 != 16 && i2 == 32) {
                i3 = 2;
            }
            getDelegate().setLocalNightMode(i3);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mNightMode = getResources().getConfiguration().uiMode & 48;
        setContentView(R.layout.activity_memory_clear);
        this._handler = new MyHandler();
        boolean z = true;
        int i = _staticCnt + 1;
        _staticCnt = i;
        if (1 < i) {
            sendMessageFailure(MemoryClearConstants.RESULT_CODE.CANCEL);
            return;
        }
        if (ServiceListActivity.getRunning()) {
            sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_MENU_APP_RUNNING);
            return;
        }
        if (!Sg.load(getApplicationContext())) {
            sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
            return;
        }
        try {
            if (!isCallerVerification()) {
                sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_CALLER);
                return;
            }
            getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(z) { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.1
                @Override // androidx.activity.OnBackPressedCallback
                public void handleOnBackPressed() {
                    MemoryClearActivity.this.onBackPressedDispatch();
                }
            });
            this.memoryClearFunc = new MemoryClearFuncEntity(getApplicationContext());
            sendMessage(MemoryClearConstants.EVENT_ID.CHECK_VERSION_UP);
        } catch (Exception e) {
            LogUtil.error(e);
            sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        setActivityStatus(ActivityStatus.FOREGROUND);
        showDialog(this.mDialogFragment);
        this._handler.resume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        setActivityStatus(ActivityStatus.BACKGROUND);
        this._handler.pause();
        if (isFinishing()) {
            finishOperation();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        setActivityStatus(ActivityStatus.BACKGROUND);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        setActivityStatus(ActivityStatus.BACKGROUND);
        finishOperation();
    }

    public void onBackPressedDispatch() {
        setResultCode(MemoryClearConstants.RESULT_CODE.CANCEL);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkVersionUp() {
        try {
            Thread thread = new Thread(new Runnable() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    MemoryClearActivity.this.memoryClearFunc.checkVersionUp(new MemoryClearFunc.CheckVersionUpListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.2.1
                        @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc.CheckVersionUpListener
                        public void onCompleted(boolean isVersionUp) {
                            if (isVersionUp) {
                                MemoryClearActivity.this.sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_VERSION_UP);
                            } else {
                                MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.TOS);
                            }
                        }

                        @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc.CheckVersionUpListener
                        public void onError(ModelErrorInfo modelErrorInfo) {
                            MemoryClearConstants.RESULT_CODE result_code;
                            if (modelErrorInfo.getType() == ModelErrorInfo.Type.NETWORK_FAILED) {
                                result_code = MemoryClearConstants.RESULT_CODE.FAILED_NETWORK_ERROR;
                            } else {
                                result_code = MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
                            }
                            MemoryClearActivity.this.sendMessageFailure(result_code, MemoryClearActivity.this.getErrorCode(modelErrorInfo));
                        }
                    });
                }
            });
            this.mRunner = thread;
            thread.setName("mRunner_checkVersionUp");
            this.mRunner.start();
        } catch (Exception e) {
            LogUtil.error(e);
            sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkIssueState() {
        try {
            Thread thread = new Thread(new Runnable() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.3
                @Override // java.lang.Runnable
                public void run() {
                    MemoryClearActivity.this.memoryClearFunc.checkIssueState(new MemoryClearFunc.IssueStateListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.3.1
                        @Override // com.felicanetworks.mfm.main.model.CentralFunc.PrecompileListener
                        public void onCompleted(CentralFunc.PrecompileListener.PrecompiledState state) {
                            if (state.getFelicaDeviceState() == CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.INITIALIZED) {
                                MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.GET_SIM, MemoryClearConstants.EVENT_ID.CHECK_ISSUE_STATE);
                            } else if (state.getFelicaDeviceState() == CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.UNINITIALIZED) {
                                MemoryClearActivity.this.sendMessageFailure(MemoryClearConstants.RESULT_CODE.PROCESSED);
                            } else {
                                MemoryClearActivity.this.sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
                            }
                        }

                        @Override // com.felicanetworks.mfm.main.model.CentralFunc.PrecompileListener
                        public void onError(ModelErrorInfo modelErrorInfo) {
                            MemoryClearConstants.RESULT_CODE result_codeConvertResultCodeMapping;
                            if (modelErrorInfo.getType().equals(ModelErrorInfo.Type.EXT_MEMORY_CLEAR_RUNNING)) {
                                MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.EXECUTE);
                                result_codeConvertResultCodeMapping = null;
                            } else if (!modelErrorInfo.getType().equals(ModelErrorInfo.Type.EXT_MFC_DISABLED)) {
                                result_codeConvertResultCodeMapping = MemoryClearActivity.this.convertResultCodeMapping(modelErrorInfo);
                            } else {
                                result_codeConvertResultCodeMapping = MemoryClearConstants.RESULT_CODE.FAILED_MFC_ERROR;
                            }
                            if (result_codeConvertResultCodeMapping != null) {
                                MemoryClearActivity.this.sendMessageFailure(result_codeConvertResultCodeMapping, MemoryClearActivity.this.getErrorCode(modelErrorInfo));
                            }
                        }
                    });
                }
            });
            this.mRunner = thread;
            thread.setName("mRunner_checkCIssueState");
            this.mRunner.start();
        } catch (Exception e) {
            LogUtil.error(e);
            sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getRemainedCard() {
        try {
            Thread thread = new Thread(new Runnable() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    MemoryClearActivity.this.memoryClearFunc.getRemainedDeleteCards(new MemoryClearFunc.GetRemainedCardsListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.4.1
                        /* JADX WARN: Type inference fix 'apply assigned field type' failed
                        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
                        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
                        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
                        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
                        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
                        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
                        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
                        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                         */
                        @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc.GetRemainedCardsListener
                        public void onCompleted(List<MyCardInfo> displayList, List<MyCardInfo> notDisplayList) {
                            ArrayList arrayList = new ArrayList();
                            Iterator<MyCardInfo> it = displayList.iterator();
                            while (it.hasNext()) {
                                String serviceNameFromServiceId = MemoryClearActivity.this.memoryClearFunc.getServiceNameFromServiceId(it.next().getServiceId());
                                if (!TextUtils.isEmpty(serviceNameFromServiceId) && !arrayList.contains(serviceNameFromServiceId)) {
                                    arrayList.add(serviceNameFromServiceId);
                                }
                            }
                            if (!arrayList.isEmpty()) {
                                MemoryClearActivity.this.memoryClearFunc.startUserOperation();
                                MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.SHOW_CARD_DELETE, arrayList);
                            } else if (notDisplayList.isEmpty()) {
                                MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.GET_DISPLAY_SERVICE);
                            } else {
                                MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.CARD_DELETE);
                            }
                        }

                        @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc.GetRemainedCardsListener
                        public void onError(ModelErrorInfo modelErrorInfo) {
                            MemoryClearActivity.this.sendMessageFailure(MemoryClearActivity.this.convertResultCodeMapping(modelErrorInfo), MemoryClearActivity.this.getErrorCode(modelErrorInfo));
                        }
                    });
                }
            });
            this.mRunner = thread;
            thread.setName("mRunner_checkCardDelete");
            this.mRunner.start();
        } catch (Exception e) {
            LogUtil.error(e);
            sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cardDelete() {
        try {
            Thread thread = new Thread(new Runnable() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    MemoryClearActivity.this.memoryClearFunc.deleteCards(new MemoryClearFunc.DeleteListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.5.1
                        @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc.DeleteListener
                        public void onCompleted() {
                            MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.GET_SIM, MemoryClearConstants.EVENT_ID.CARD_DELETE);
                        }

                        @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc.DeleteListener
                        public void onError(ModelErrorInfo modelErrorInfo) {
                            MemoryClearConstants.RESULT_CODE result_codeConvertResultCodeMapping;
                            if (!modelErrorInfo.getType().equals(ModelErrorInfo.Type.FELICA_HTTP_ERROR) && !modelErrorInfo.getType().equals(ModelErrorInfo.Type.FELICA_OPEN_ERROR) && !modelErrorInfo.getType().equals(ModelErrorInfo.Type.LOCKED_FELICA) && !modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFC_OTHER_ERROR) && !modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFIC_INVALID_REQUEST_TOKEN_ERROR) && !modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR) && !modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF) && !modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFIC_VERSION_ERROR) && !modelErrorInfo.getType().equals(ModelErrorInfo.Type.PERM_INSPECT) && !modelErrorInfo.getType().equals(ModelErrorInfo.Type.USED_BY_OTHER_APP)) {
                                result_codeConvertResultCodeMapping = MemoryClearActivity.this.convertResultCodeMapping(modelErrorInfo);
                            } else {
                                result_codeConvertResultCodeMapping = MemoryClearConstants.RESULT_CODE.FAILED_DELETE_CARD;
                            }
                            MemoryClearActivity.this.sendMessageFailure(result_codeConvertResultCodeMapping, MemoryClearActivity.this.getErrorCode(modelErrorInfo));
                        }
                    });
                }
            });
            this.mRunner = thread;
            thread.setName("mRunner_cardDelete");
            this.mRunner.start();
        } catch (Exception e) {
            LogUtil.error(e);
            sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getSimData(final MemoryClearConstants.EVENT_ID event) {
        try {
            this.memoryClearFunc.compile(false, new MemoryClearFunc.CompileListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.6
                @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc.CompileListener
                public void onCompleted() {
                    if (event == MemoryClearConstants.EVENT_ID.CHECK_ISSUE_STATE) {
                        MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.GET_REMAINED_CARD);
                    } else if (event == MemoryClearConstants.EVENT_ID.CARD_DELETE) {
                        MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.GET_DISPLAY_SERVICE);
                    } else {
                        MemoryClearActivity.this.sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
                    }
                }

                @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc.CompileListener
                public void onError(ModelErrorInfo modelErrorInfo) {
                    MemoryClearConstants.RESULT_CODE result_codeConvertResultCodeMapping;
                    if (event == MemoryClearConstants.EVENT_ID.CHECK_ISSUE_STATE) {
                        result_codeConvertResultCodeMapping = MemoryClearActivity.this.convertResultCodeMapping(modelErrorInfo);
                    } else if (event == MemoryClearConstants.EVENT_ID.CARD_DELETE) {
                        result_codeConvertResultCodeMapping = (modelErrorInfo.getType().equals(ModelErrorInfo.Type.FELICA_HTTP_ERROR) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.FELICA_OPEN_ERROR) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.LOCKED_FELICA) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFC_OTHER_ERROR) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFIC_VERSION_ERROR) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.PERM_INSPECT) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.USED_BY_OTHER_APP)) ? MemoryClearConstants.RESULT_CODE.FAILED_DISPLAY_SERVICE : MemoryClearActivity.this.convertResultCodeMapping(modelErrorInfo);
                    } else {
                        result_codeConvertResultCodeMapping = MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
                    }
                    MemoryClearActivity memoryClearActivity = MemoryClearActivity.this;
                    memoryClearActivity.sendMessageFailure(result_codeConvertResultCodeMapping, memoryClearActivity.getErrorCode(modelErrorInfo));
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
            sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDisplayService() {
        try {
            Thread thread = new Thread(new Runnable() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        MemoryClearActivity.this.memoryClearFunc.getAssetList(new MemoryClearFunc.GetAssetListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.7.1
                            @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc.GetAssetListener
                            public void onCompleted(List<AssetInfo> assetInfoList) {
                                AssetInfo next;
                                List<MemoryClearFunc.MemoryClearServiceInfo> memoryClearServiceList = MemoryClearActivity.this.memoryClearFunc.getMemoryClearServiceList();
                                ArrayList arrayList = new ArrayList();
                                for (MemoryClearFunc.MemoryClearServiceInfo memoryClearServiceInfo : memoryClearServiceList) {
                                    String serviceId = memoryClearServiceInfo.getServiceId();
                                    SupportServiceType supportServiceTypeResolve = SupportServiceType.resolve(serviceId);
                                    boolean z = !(supportServiceTypeResolve == SupportServiceType.M1 || supportServiceTypeResolve == SupportServiceType.M2) || memoryClearServiceInfo.hasActiveForegroundCard() || memoryClearServiceInfo.hasActivePendingCard() || !memoryClearServiceInfo.hasActiveBackgroundCard();
                                    Iterator<AssetInfo> it = assetInfoList.iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            next = it.next();
                                            if (TextUtils.equals(serviceId, next.getServiceId())) {
                                                break;
                                            }
                                        } else {
                                            next = null;
                                            break;
                                        }
                                    }
                                    String serviceNameFromServiceId = MemoryClearActivity.this.memoryClearFunc.getServiceNameFromServiceId(serviceId);
                                    if (!TextUtils.isEmpty(serviceNameFromServiceId)) {
                                        arrayList.add(new MemoryClearDisplayServiceFragment.DisplayServiceInfo(serviceId, serviceNameFromServiceId, z, next));
                                    }
                                }
                                if (!arrayList.isEmpty()) {
                                    MemoryClearActivity.this.memoryClearFunc.startUserOperation();
                                    MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.SHOW_DISPLAY_SERVICE, arrayList);
                                } else {
                                    MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.EXECUTE);
                                }
                            }

                            @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc.GetAssetListener
                            public void onError(ModelErrorInfo modelErrorInfo) {
                                MemoryClearConstants.RESULT_CODE result_codeConvertResultCodeMapping;
                                if (modelErrorInfo.getType().equals(ModelErrorInfo.Type.FELICA_INVALID_RESPONSE_ERROR)) {
                                    result_codeConvertResultCodeMapping = MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
                                } else if (!modelErrorInfo.getType().equals(ModelErrorInfo.Type.FELICA_OPEN_ERROR) && !modelErrorInfo.getType().equals(ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR) && !modelErrorInfo.getType().equals(ModelErrorInfo.Type.LOCKED_FELICA) && !modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFC_OTHER_ERROR)) {
                                    result_codeConvertResultCodeMapping = MemoryClearActivity.this.convertResultCodeMapping(modelErrorInfo);
                                } else {
                                    result_codeConvertResultCodeMapping = MemoryClearConstants.RESULT_CODE.FAILED_DISPLAY_SERVICE;
                                }
                                MemoryClearActivity.this.sendMessageFailure(result_codeConvertResultCodeMapping, MemoryClearActivity.this.getErrorCode(modelErrorInfo));
                            }
                        });
                    } catch (Exception e) {
                        LogUtil.error(e);
                        MemoryClearActivity.this.sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
                    }
                }
            });
            this.mRunner = thread;
            thread.setName("mRunner_checkDisplayService");
            this.mRunner.start();
        } catch (Exception e) {
            LogUtil.error(e);
            sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearMemory() {
        try {
            Thread thread = new Thread(new Runnable() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.8
                @Override // java.lang.Runnable
                public void run() {
                    MemoryClearActivity.this.memoryClearFunc.clearMemory(new MemoryClearFunc.ClearMemoryListener() { // from class: com.felicanetworks.mfm.memory_clear.MemoryClearActivity.8.1
                        @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc.ClearMemoryListener
                        public void onCompleted() {
                            MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.SUCCESS);
                        }

                        @Override // com.felicanetworks.mfm.memory_clear.MemoryClearFunc.ClearMemoryListener
                        public void onError(ModelErrorInfo modelErrorInfo) {
                            MemoryClearConstants.RESULT_CODE result_codeConvertResultCodeMapping;
                            if (modelErrorInfo.getType().equals(ModelErrorInfo.Type.FELICA_HTTP_ERROR) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.FELICA_OPEN_ERROR) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.LOCKED_FELICA) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFC_OTHER_ERROR) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFIC_VERSION_ERROR) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.PERM_INSPECT) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.USED_BY_OTHER_APP) || modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFIC_NETWORK_ERROR)) {
                                result_codeConvertResultCodeMapping = MemoryClearConstants.RESULT_CODE.FAILED_MEMORY_CLEAR;
                            } else if (modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFIC_NETWORK_RETRY_ERROR)) {
                                result_codeConvertResultCodeMapping = MemoryClearConstants.RESULT_CODE.FAILED_RETRY_MEMORY_CLEAR;
                            } else if (!modelErrorInfo.getType().equals(ModelErrorInfo.Type.MFC_CANNOT_MEMORY_CLEAR_BY_NOT_INITIALIZE)) {
                                result_codeConvertResultCodeMapping = MemoryClearActivity.this.convertResultCodeMapping(modelErrorInfo);
                            } else {
                                MemoryClearActivity.this.sendMessage(MemoryClearConstants.EVENT_ID.SUCCESS);
                                result_codeConvertResultCodeMapping = null;
                            }
                            if (result_codeConvertResultCodeMapping != null) {
                                MemoryClearActivity.this.sendMessageFailure(result_codeConvertResultCodeMapping, MemoryClearActivity.this.getErrorCode(modelErrorInfo));
                            }
                        }
                    });
                }
            });
            this.mRunner = thread;
            thread.setName("mRunner_clearMemory");
            this.mRunner.start();
        } catch (Exception e) {
            LogUtil.error(e);
            sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
        }
    }

    public void sendMessage(MemoryClearConstants.EVENT_ID event) {
        MyHandler myHandler = this._handler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(event.id(), new SendData(this, getCallingPackage())), 1000L);
    }

    public void sendMessage(MemoryClearConstants.EVENT_ID event, Object packet) {
        MyHandler myHandler = this._handler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(event.id(), new SendData(this, getCallingPackage(), packet)), 1000L);
    }

    public void sendMessageFailure(MemoryClearConstants.RESULT_CODE resultCode) {
        setResultCode(resultCode);
        MyHandler myHandler = this._handler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(MemoryClearConstants.EVENT_ID.FAILED.id(), new SendData(this, getCallingPackage())), 1000L);
    }

    public void sendMessageFailure(MemoryClearConstants.RESULT_CODE resultCode, String errorCode) {
        setResultCode(resultCode);
        MyHandler myHandler = this._handler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(MemoryClearConstants.EVENT_ID.FAILED.id(), new SendData(this, getCallingPackage(), errorCode)), 1000L);
    }

    void cancelMemoryClear(MemoryClearConstants.EVENT_ID event) {
        if (event == MemoryClearConstants.EVENT_ID.TOS) {
            setResultCode(MemoryClearConstants.RESULT_CODE.CANCEL);
            finish();
        } else if (event == MemoryClearConstants.EVENT_ID.SHOW_CARD_DELETE) {
            setResultCode(MemoryClearConstants.RESULT_CODE.CANCEL);
            finish();
        } else if (event == MemoryClearConstants.EVENT_ID.SHOW_DISPLAY_SERVICE) {
            setResultCode(MemoryClearConstants.RESULT_CODE.CANCEL);
            finish();
        }
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.mActivityStatus = activityStatus;
    }

    public ActivityStatus getActivityStatus() {
        return this.mActivityStatus;
    }

    public void showDialog(MemoryClearCustomDialogFragment dialogFragment) {
        if (dialogFragment == null) {
            return;
        }
        if (getActivityStatus() == ActivityStatus.FOREGROUND) {
            if (!dialogFragment.isDismissed()) {
                FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransactionBeginTransaction.add(dialogFragment, dialogFragment.getTargetTag());
                fragmentTransactionBeginTransaction.commitAllowingStateLoss();
            }
            this.mDialogFragment = null;
            return;
        }
        this.mDialogFragment = dialogFragment;
    }

    public MemoryClearCustomDialogFragment getCurrentDialog() {
        MemoryClearCustomDialogFragment memoryClearCustomDialogFragment = (MemoryClearCustomDialogFragment) getSupportFragmentManager().findFragmentByTag("memoryClearDialog");
        if (memoryClearCustomDialogFragment != null) {
            return memoryClearCustomDialogFragment;
        }
        MemoryClearCustomDialogFragment memoryClearCustomDialogFragment2 = (MemoryClearCustomDialogFragment) getSupportFragmentManager().findFragmentByTag("memoryClearProcessingDialog");
        return memoryClearCustomDialogFragment2 == null ? (MemoryClearCustomDialogFragment) getSupportFragmentManager().findFragmentByTag("memoryClearExecutingDialog") : memoryClearCustomDialogFragment2;
    }

    public void setResultCode(MemoryClearConstants.RESULT_CODE code) {
        this.mResultCode = code;
    }

    public MemoryClearConstants.RESULT_CODE getResultCode() {
        return this.mResultCode;
    }

    @Override // android.app.Activity
    public void finish() {
        setResult(getResultCode().code());
        super.finish();
    }

    public void initialize() {
        Thread thread = this.mRunner;
        if (thread == null || !thread.isAlive()) {
            return;
        }
        this.mRunner.interrupt();
    }

    private void finishOperation() {
        if (this._isFinishOperation) {
            return;
        }
        _staticCnt--;
        this._isFinishOperation = true;
        Thread thread = this.mRunner;
        if (thread != null && thread.isAlive()) {
            this.mRunner.interrupt();
        }
        MemoryClearFunc memoryClearFunc = this.memoryClearFunc;
        if (memoryClearFunc != null) {
            memoryClearFunc.mfcExpertDeInitialize();
        }
    }

    private boolean isCallerVerification() {
        return getCallingActivity().getPackageName().equals((String) Sg.getValue(Sg.Key.SETTING_MEMORY_CLEAR_PERMIT_APP_PKG_SETTINGS));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getErrorCode(ModelErrorInfo modelErrorInfo) {
        String string;
        String errorCode = modelErrorInfo.getErrorCode();
        String string2 = "";
        if (errorCode == null) {
            string = "";
        } else {
            string = getString(R.string.dlg_error_text_memory_clear_code, new Object[]{errorCode});
        }
        String mfcErrorCode = modelErrorInfo.getMfcErrorCode();
        if (mfcErrorCode != null) {
            string2 = getString(R.string.dlg_error_text_memory_clear_code_mfc, new Object[]{mfcErrorCode});
        }
        return string + string2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MemoryClearConstants.RESULT_CODE convertResultCodeMapping(ModelErrorInfo modelErrorInfo) {
        ModelErrorInfo.Type type = modelErrorInfo.getType();
        if (type.equals(ModelErrorInfo.Type.OTHER_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.OTHER_RUNTIME_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.BAD_SG)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FELICA_CHIP_TIME_OUT;
        }
        if (type.equals(ModelErrorInfo.Type.MFC_OTHER_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_UNKNOWN_MFC_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.NONSUPPORT_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.LOCKED_FELICA)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_LOCKED_FELICA_CHIP;
        }
        if (type.equals(ModelErrorInfo.Type.FELICA_OPEN_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FELICA_OPEN;
        }
        if (type.equals(ModelErrorInfo.Type.FELICA_INVALID_RESPONSE_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_INVALID_RESPONSE;
        }
        if (type.equals(ModelErrorInfo.Type.NFC_IO_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.NFC_ILLEGALSTATE_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.USED_BY_OTHER_APP)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_USED_MFC_BY_OTHER_APP;
        }
        if (type.equals(ModelErrorInfo.Type.PERM_INSPECT)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_NO_PERMISSION_TO_ACTIVATE_MFC;
        }
        if (type.equals(ModelErrorInfo.Type.FELICA_HTTP_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FELICA_HTTP_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_VERSION_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_VERSION_UP;
        }
        if (type.equals(ModelErrorInfo.Type.MFC_CANNOT_MEMORY_CLEAR_BY_NOT_INITIALIZE)) {
            return MemoryClearConstants.RESULT_CODE.PROCESSED;
        }
        if (type.equals(ModelErrorInfo.Type.NO_CONTENT_DATA)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.DB_ACCESS_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.NET_OTHER_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_NETWORK_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.NETWORK_FAILED)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_NETWORK_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.NET_JSON_ERROR_NO_CASHE)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.NET_JSON_ERROR_USE_CACHE)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_NETWORK_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_NETWORK_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_LOCK_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_LOCKED_FELICA_CHIP;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_OPEN_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FELICA_OPEN;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_OTHER_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_UNKNOWN_MFC_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_LIB_UNAVAILABLE)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_JSON_ERROR_NO_CASHE)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_JSON_ERROR_USE_CACHE)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_LOGIN_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_USED_BY_OTHER_APP)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_USED_MFC_BY_OTHER_APP;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_RUNNING_BY_MFIC;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_INVALID_REQUEST_TOKEN_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_INVALID_TOKEN_REQUEST;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_UNSUPPORTED_CHIP_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_MFI_SERVER_MAINTENANCE;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_UNSUPPORTED_DEVICE_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_CALLER;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_EXCEEDED)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_DISPLAY_SERVICE;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_DISPLAY_SERVICE;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_ISSUE_LIMIT_PER_DEVICE_EXCEEDED)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_DISPLAY_SERVICE;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_INSUFFICIENT_CHIP_SPACE)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_DISPLAY_SERVICE;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_OTHER_SP_CARD_EXIST)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_DISPLAY_SERVICE;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_INSTANCE_NOT_VACANT)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_DISPLAY_SERVICE;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_TYPE_EXIST_UNKNOWN_CARD)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_DISPLAY_SERVICE;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_INSUFFICIENT_ALLOCATED_FREE_SPACE)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_DISPLAY_SERVICE;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_INSIDE_TRANSIT_GATE_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_DISPLAY_SERVICE;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_WARNING)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_UNKNOWN_MFC_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.MFIC_NO_WARNING)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_UNKNOWN_MFC_ERROR;
        }
        if (type.equals(ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_SERVICE)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_PIA_PLAY_SERVICE;
        }
        if (type.equals(ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_STORE)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_PIA_PLAY_STORE;
        }
        if (type.equals(ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_RECOVERABLE_FAILED)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_MFI_SERVER_MAINTENANCE;
        }
        if (type.equals(ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_NONRECOVERABLE_FAILED)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_PIA_VERIFICATION;
        }
        if (type.equals(ModelErrorInfo.Type.EXT_CARD_OPERATION_FATAL_ERROR)) {
            return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
        }
        return MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR;
    }

    private static class SendData {
        private final MemoryClearActivity activity;
        private final String callingPackage;
        private final Object packet;

        SendData(MemoryClearActivity activity, String callingPackage) {
            this.activity = activity;
            this.callingPackage = callingPackage;
            this.packet = null;
        }

        SendData(MemoryClearActivity activity, String callingPackage, Object packet) {
            this.activity = activity;
            this.callingPackage = callingPackage;
            this.packet = packet;
        }

        public String toString() {
            return "SendData{activity=" + this.activity + ", callingPackage='" + this.callingPackage + "', packet='" + this.packet + '}';
        }

        MemoryClearActivity getActivity() {
            return this.activity;
        }

        String getCallingPackage() {
            return this.callingPackage;
        }

        Object getPacket() {
            return this.packet;
        }
    }

    private static class MyHandler extends Handler {
        private final Vector<Message> messageQueueBuffer;
        private boolean paused;

        MyHandler() {
            super(Looper.getMainLooper());
            this.paused = true;
            this.messageQueueBuffer = new Vector<>();
        }

        /* JADX WARN: Unexpected iteration count in SwitchBreakVisitor. Please report as an issue */
        final void processMessage(Message msg) {
            MemoryClearCustomDialogFragment memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog;
            if (msg.obj instanceof SendData) {
                SendData sendData = (SendData) msg.obj;
                MemoryClearActivity activity = sendData.getActivity();
                Object packet = sendData.getPacket();
                if (activity.isDestroyed() || activity.isFinishing()) {
                    return;
                }
                FragmentTransaction fragmentTransactionBeginTransaction = activity.getSupportFragmentManager().beginTransaction();
                int i = 0;
                switch (AnonymousClass9.$SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.resolve(msg.what).ordinal()]) {
                    case 1:
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createMemoryClearProcessingDialog(activity);
                        activity.checkVersionUp();
                        break;
                    case 2:
                        fragmentTransactionBeginTransaction.replace(R.id.fl_content_memory_clear_fragment, new MemoryClearTosFragment());
                        fragmentTransactionBeginTransaction.commit();
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = null;
                        break;
                    case 3:
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createMemoryClearProcessingDialog(activity);
                        activity.checkIssueState();
                        break;
                    case 4:
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createMemoryClearProcessingDialog(activity);
                        if (packet instanceof MemoryClearConstants.EVENT_ID) {
                            activity.getSimData((MemoryClearConstants.EVENT_ID) packet);
                        } else {
                            activity.sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
                        }
                        break;
                    case 5:
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createMemoryClearProcessingDialog(activity);
                        activity.getRemainedCard();
                        break;
                    case 6:
                        ArrayList arrayList = new ArrayList();
                        if (packet instanceof List) {
                            while (true) {
                                List list = (List) packet;
                                if (i < list.size()) {
                                    Object obj = list.get(i);
                                    if (obj instanceof String) {
                                        arrayList.add((String) obj);
                                    }
                                    i++;
                                }
                            }
                        }
                        if (arrayList.isEmpty()) {
                            activity.sendMessage(MemoryClearConstants.EVENT_ID.GET_DISPLAY_SERVICE);
                        } else {
                            fragmentTransactionBeginTransaction.replace(R.id.fl_content_memory_clear_fragment, MemoryClearMfiCardDeleteFragment.newInstance(arrayList));
                            fragmentTransactionBeginTransaction.commit();
                        }
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = null;
                        break;
                    case 7:
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createMemoryClearProcessingDialog(activity);
                        activity.cardDelete();
                        break;
                    case 8:
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createMemoryClearProcessingDialog(activity);
                        activity.getDisplayService();
                        break;
                    case 9:
                        ArrayList arrayList2 = new ArrayList();
                        if (packet instanceof List) {
                            while (true) {
                                List list2 = (List) packet;
                                if (i < list2.size()) {
                                    Object obj2 = list2.get(i);
                                    if (obj2 instanceof MemoryClearDisplayServiceFragment.DisplayServiceInfo) {
                                        arrayList2.add((MemoryClearDisplayServiceFragment.DisplayServiceInfo) obj2);
                                    }
                                    i++;
                                }
                            }
                        }
                        if (arrayList2.isEmpty()) {
                            activity.sendMessage(MemoryClearConstants.EVENT_ID.EXECUTE);
                        } else {
                            fragmentTransactionBeginTransaction.replace(R.id.fl_content_memory_clear_fragment, MemoryClearDisplayServiceFragment.newInstance(arrayList2, activity.getApplicationContext()));
                            fragmentTransactionBeginTransaction.commit();
                        }
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = null;
                        break;
                    case 10:
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createMemoryClearConfirmation(activity);
                        break;
                    case 11:
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createMemoryClearExecutingDialog(activity);
                        activity.clearMemory();
                        break;
                    case 12:
                        activity.setResultCode(MemoryClearConstants.RESULT_CODE.SUCCESS);
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createMemoryClearSuccess(activity, R.string.dlg_text_memory_clear_success);
                        break;
                    case 13:
                        MemoryClearConstants.RESULT_CODE resultCode = activity.getResultCode();
                        if (resultCode.equals(MemoryClearConstants.RESULT_CODE.PROCESSED)) {
                            memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createMemoryClearSuccess(activity, R.string.dlg_text_memory_clear_unnecessary_device);
                            break;
                        } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.CANCEL)) {
                            memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_multiple_start_up);
                            break;
                        } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_CALLER)) {
                            memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_unsupported_device);
                            break;
                        } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_USED_MFC_BY_OTHER_APP)) {
                            memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_used_mfc_by_other_app);
                            break;
                        } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_RUNNING_BY_MFIC)) {
                            memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_running_by_mfic);
                            break;
                        } else {
                            if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_LOCKED_FELICA_CHIP)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_locked_felica_chip);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_FELICA_OPEN)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_felica_open);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_INVALID_RESPONSE)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_felica_invalid_response);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_FELICA_CHIP_TIME_OUT)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = packet instanceof String ? MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_felica_chip_time_out, (String) packet) : MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_felica_chip_time_out);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_NO_PERMISSION_TO_ACTIVATE_MFC)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = packet instanceof String ? MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_no_permission_to_activate_mfc, (String) packet) : MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_no_permission_to_activate_mfc);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_FELICA_HTTP_ERROR)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_felica_http_error);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_MFI_SERVER_MAINTENANCE)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_mfi_server_maintenance);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_INVALID_TOKEN_REQUEST)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_invalid_token_request);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_RETRY_MEMORY_CLEAR)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_retry_network_failed);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_UNKNOWN_MFC_ERROR)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = packet instanceof String ? MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_unknown_mfc_error, (String) packet) : MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_unknown_mfc_error);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_PIA_PLAY_STORE)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_pia_playstore_error);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_PIA_PLAY_SERVICE)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_pia_playservice_error);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_PIA_VERIFICATION)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_pia_integrity_error);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_VERSION_UP)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureVersionupDialog(activity, R.string.dlg_error_text_memory_version_up, activity.getPackageName());
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_MFC_ERROR)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureVersionupDialog(activity, R.string.dlg_error_text_memory_clear_mfc_error, (String) Sg.getValue(Sg.Key.SETTING_MFC_PACKAGE_NAME));
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_APP_START_FAILED)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_app_start_failed);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_NETWORK_ERROR)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = packet instanceof String ? MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_network_failed, (String) packet) : MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_network_failed);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_DELETE_CARD)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = packet instanceof String ? MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_delete_card, (String) packet) : MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_delete_card);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_DISPLAY_SERVICE)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = packet instanceof String ? MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_display_service, (String) packet) : MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_display_service);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_MEMORY_CLEAR)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = packet instanceof String ? MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear, (String) packet) : MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear);
                            } else if (resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = packet instanceof String ? MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_fatal_error, (String) packet) : MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_fatal_error);
                            } else if (!resultCode.equals(MemoryClearConstants.RESULT_CODE.FAILED_MENU_APP_RUNNING)) {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = null;
                            } else {
                                memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = MemoryClearDialogFactory.createFailureDialog(activity, R.string.dlg_error_text_memory_clear_menu_app_running);
                            }
                            break;
                        }
                        break;
                    default:
                        activity.sendMessageFailure(MemoryClearConstants.RESULT_CODE.FAILED_FATAL_ERROR);
                        memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog = null;
                        break;
                }
                MemoryClearCustomDialogFragment currentDialog = activity.getCurrentDialog();
                if (currentDialog == null) {
                    activity.showDialog(memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog);
                    return;
                }
                String targetTag = currentDialog.getTargetTag();
                if (targetTag == null) {
                    activity.showDialog(memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog);
                    return;
                }
                if (targetTag.equals("memoryClearDialog")) {
                    currentDialog.dismiss();
                    activity.showDialog(memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog);
                    return;
                }
                if (!targetTag.equals("memoryClearProcessingDialog")) {
                    if (targetTag.equals("memoryClearExecutingDialog")) {
                        currentDialog.dismiss();
                        activity.showDialog(memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog);
                        return;
                    }
                    return;
                }
                if (memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog != null) {
                    if (memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog.getTargetTag().equals("memoryClearDialog") || memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog.getTargetTag().equals("memoryClearExecutingDialog")) {
                        currentDialog.dismiss();
                        activity.showDialog(memoryClearCustomDialogFragmentCreateMemoryClearProcessingDialog);
                        return;
                    }
                    return;
                }
                currentDialog.dismiss();
            }
        }

        final void resume() {
            this.paused = false;
            while (this.messageQueueBuffer.size() > 0) {
                Message messageLastElement = this.messageQueueBuffer.lastElement();
                this.messageQueueBuffer.removeAllElements();
                sendMessage(messageLastElement);
            }
        }

        final void pause() {
            this.paused = true;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message msg) {
            if (this.paused) {
                Message message = new Message();
                message.copyFrom(msg);
                this.messageQueueBuffer.add(message);
                return;
            }
            processMessage(msg);
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.memory_clear.MemoryClearActivity$9, reason: invalid class name */
    static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID;

        static {
            int[] iArr = new int[MemoryClearConstants.EVENT_ID.values().length];
            $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID = iArr;
            try {
                iArr[MemoryClearConstants.EVENT_ID.CHECK_VERSION_UP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.TOS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.CHECK_ISSUE_STATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.GET_SIM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.GET_REMAINED_CARD.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.SHOW_CARD_DELETE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.CARD_DELETE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.GET_DISPLAY_SERVICE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.SHOW_DISPLAY_SERVICE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.EXECUTE_CONFIRMATION.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.EXECUTE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.SUCCESS.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$memory_clear$MemoryClearConstants$EVENT_ID[MemoryClearConstants.EVENT_ID.FAILED.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public static boolean getRunning() {
        return _staticCnt > 0;
    }
}

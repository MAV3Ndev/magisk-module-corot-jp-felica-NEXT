package com.felicanetworks.mfm.main.presenter.internal;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.felicanetworks.mfm.LockStatusReceiver;
import com.felicanetworks.mfm.MficStatusChangeReceiver;
import com.felicanetworks.mfm.main.LaunchManagementActivity;
import com.felicanetworks.mfm.main.model.CardDetailFunc;
import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.ExtIcCardFunc;
import com.felicanetworks.mfm.main.model.FelicaPocketFunc;
import com.felicanetworks.mfm.main.model.MemoryUsageFunc;
import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.ModelGateway;
import com.felicanetworks.mfm.main.model.NoticeFunc;
import com.felicanetworks.mfm.main.model.PushGateway;
import com.felicanetworks.mfm.main.model.TermsOfServiceFunc;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.model.info.RecommendInfo;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.fix.MfiLoginResultCode;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.SupportServiceType;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.RequestCaster;
import com.felicanetworks.mfm.main.presenter.agent.AgentUtil;
import com.felicanetworks.mfm.main.presenter.agent.CentralFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.ExtIcCardFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.ExtIcCardInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MemoryUsageFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.MfiCardFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceGroupInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.NoticeFuncAgent;
import com.felicanetworks.mfm.main.presenter.agent.NoticeInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.RecommendInfoAgent;
import com.felicanetworks.mfm.main.presenter.agent.TermsOfServiceFuncAgent;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess;
import com.felicanetworks.mfm.main.presenter.internal.notification.NoticeChangeDataListenerLazyKeeper;
import com.felicanetworks.mfm.main.presenter.internal.notification.NotificationController;
import com.felicanetworks.mfm.main.presenter.structure.MfiLoginReadyingDrawStructure;
import com.felicanetworks.mfm.main.presenter.structure.RebootStructure;
import com.felicanetworks.mfm.main.presenter.structure.Structure;
import com.felicanetworks.mfm.main.presenter.structure.StructureType;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class StateController {
    private static StateController _this;
    private static HandlerThread statusControlHandlerThread;
    private StatusControlHandler _sch = null;
    private Context _context = null;

    public enum AppPhase {
        STOPPED,
        START,
        FOR_GROUND,
        BACK_GROUND
    }

    enum LaunchType {
        NORMAL,
        NOTIFICATION,
        EX_IC_CARD
    }

    private StateController() {
    }

    static void initialize(Context context) {
        try {
            cancel();
            StateController stateController = new StateController();
            _this = stateController;
            stateController._context = context;
            HandlerThread handlerThread = new HandlerThread("presenter_main_queue");
            statusControlHandlerThread = handlerThread;
            handlerThread.start();
            _this._sch = new StatusControlHandler(_this._context, statusControlHandlerThread.getLooper());
        } catch (Exception e) {
            LogUtil.warning(new MfmException(StateController.class, 297, e));
        }
    }

    private static void cancel() {
        HandlerThread handlerThread;
        try {
            try {
                if (_this != null && _this._sch != null) {
                    if (_this._sch._provider != null) {
                        CentralFunc centralFunc = _this._sch._provider.getCentralFunc();
                        if (centralFunc != null) {
                            centralFunc.cancel();
                        }
                        NoticeFunc noticeFunc = _this._sch._provider.getNoticeFunc();
                        if (noticeFunc != null) {
                            noticeFunc.cancel();
                        }
                        ExtIcCardFunc extIcCardFunc = _this._sch._provider.getExtIcCardFunc();
                        if (extIcCardFunc != null) {
                            extIcCardFunc.cancel();
                        }
                        MemoryUsageFunc memoryUsageFunc = _this._sch._provider.getMemoryUsageFunc();
                        if (memoryUsageFunc != null) {
                            memoryUsageFunc.cancel();
                        }
                        MfiCardFunc mfiCardFunc = _this._sch._provider.getMfiCardFunc();
                        if (mfiCardFunc != null) {
                            mfiCardFunc.cancel();
                        }
                        CardDetailFunc cardDetailFunc = _this._sch._provider.getCardDetailFunc();
                        if (cardDetailFunc != null) {
                            cardDetailFunc.cancel();
                        }
                    }
                    if (_this._sch._model != null) {
                        _this._sch._model.deinitialize();
                    }
                }
                handlerThread = statusControlHandlerThread;
                if (handlerThread == null) {
                    return;
                }
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 369, e));
                handlerThread = statusControlHandlerThread;
                if (handlerThread == null) {
                    return;
                }
            }
            handlerThread.quitSafely();
            statusControlHandlerThread = null;
        } catch (Throwable th) {
            HandlerThread handlerThread2 = statusControlHandlerThread;
            if (handlerThread2 != null) {
                handlerThread2.quitSafely();
                statusControlHandlerThread = null;
            }
            throw th;
        }
    }

    static boolean setupPreference() {
        if (!ServicePreference.getInstance().setup(PresenterData.getInstance().getContext())) {
            return false;
        }
        Settings.initIdm(ServicePreference.getInstance().loadIDm(_this._context));
        Settings.initIcCode(ServicePreference.getInstance().loadIcCode(_this._context));
        if (Settings.idm().equals(ServicePreference.IDM_DEFAULT)) {
            ServicePreference.getInstance().saveIDm(_this._context, "E" + String.format(Locale.US, "%015X", Long.valueOf(Math.abs(new SecureRandom().nextLong()) & 1152921504606846975L)));
            Settings.initIdm(ServicePreference.getInstance().loadIDm(_this._context));
        }
        return true;
    }

    private static void postHandler(int i, final Object obj, final Object obj2, final Object obj3) {
        StatusControlHandler statusControlHandler;
        StateController stateController = _this;
        if (stateController == null || (statusControlHandler = stateController._sch) == null) {
            return;
        }
        statusControlHandler.sendMessage(statusControlHandler.obtainMessage(i, new HashMap<String, Object>() { // from class: com.felicanetworks.mfm.main.presenter.internal.StateController.1
            {
                put("type", obj);
                put("param", obj2);
                put("args", obj3);
            }
        }));
    }

    public static void postStateEvent(StateMachine.Event event, Structure structure, Object... objArr) {
        postHandler(0, event, structure, objArr);
    }

    static void postStateEvent(StateMachine.Event event, Object... objArr) {
        postHandler(0, event, StatusControlHandler.SYSTEM_SENDER, objArr);
    }

    public static void postAppPhase(AppPhase appPhase, Object... objArr) {
        postHandler(1, appPhase, null, objArr);
    }

    public static void sendLocalTagEvent() {
        StatusControlHandler statusControlHandler;
        StateController stateController = _this;
        if (stateController == null || (statusControlHandler = stateController._sch) == null) {
            return;
        }
        statusControlHandler.sendLocalTagEvent();
    }

    public static AppPhase getAppPhase() {
        StatusControlHandler statusControlHandler;
        AppPhase appPhase = AppPhase.STOPPED;
        StateController stateController = _this;
        return (stateController == null || (statusControlHandler = stateController._sch) == null) ? appPhase : statusControlHandler.getCurrentPhase();
    }

    public static StateMachine getCurrentState() {
        StatusControlHandler statusControlHandler;
        StateMachine stateMachine = StateMachine.P_STATE_NONE;
        StateController stateController = _this;
        return (stateController == null || (statusControlHandler = stateController._sch) == null) ? stateMachine : statusControlHandler.getCurrentState();
    }

    static void tagDiscovered(Tag tag) {
        StatusControlHandler statusControlHandler;
        StateController stateController = _this;
        if (stateController == null || (statusControlHandler = stateController._sch) == null) {
            return;
        }
        if (statusControlHandler.launchType == LaunchType.EX_IC_CARD) {
            StatusControlHandler._nfcTagInfo.saveNfcTag(tag);
        } else {
            postStateEvent(StateMachine.Event.EI_NFC_TAG_RECEIVE, tag);
        }
    }

    private static class StatusControlHandler extends Handler implements StateMachine.OperationExecutor {
        private static final int APP_PHASE = 1;
        private static final String EXTRAS_KEY_NOTICE_ID = "NotificationID";
        private static final String OBJECT_MAP_KEY_ARGS = "args";
        private static final String OBJECT_MAP_KEY_PARAM = "param";
        private static final String OBJECT_MAP_KEY_TYPE = "type";
        private static final int STATE_EVENT = 0;
        private static final Structure SYSTEM_SENDER = new Structure(null) { // from class: com.felicanetworks.mfm.main.presenter.internal.StateController.StatusControlHandler.1
        };
        static NfcTagInfo _nfcTagInfo = new NfcTagInfo();
        private AppPhase _appState;
        String _beforeNotificationID;
        String _cid;
        private Context _context;
        private StateMachine _currentState;
        private Structure _currentStructure;
        private ModelDataUpdateManager _mdum;
        private ModelGateway _model;
        String _notificationID;
        private StateMachine.StructureParams _params;
        private ModelGateway.FunctionProvider _provider;
        String _sid;
        private boolean isPendingRequest;
        public LaunchType launchType;

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S39_common_exit() {
        }

        static class NfcTagInfo {
            private Tag _tag = null;
            private boolean _isHoldNfcTag = false;

            NfcTagInfo() {
            }

            void saveNfcTag(Tag tag) {
                this._tag = tag;
                this._isHoldNfcTag = true;
            }

            void resetNfcTag() {
                this._tag = null;
                this._isHoldNfcTag = false;
            }

            Tag getNfcTag() {
                return this._tag;
            }

            public boolean isHoldNfcTag() {
                return this._isHoldNfcTag;
            }
        }

        StatusControlHandler(Context context, Looper looper) {
            super(looper);
            this._currentState = StateMachine.P_STATE_NONE;
            this.isPendingRequest = false;
            this._params = new StateMachine.StructureParams();
            this._mdum = null;
            this.launchType = LaunchType.NORMAL;
            this._notificationID = null;
            this._sid = null;
            this._cid = null;
            this._beforeNotificationID = null;
            this._context = context;
            this._model = new ModelGateway(context);
            RequestCaster.getInstance().reset();
            this._mdum = new ModelDataUpdateManager();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            Map map = (Map) message.obj;
            Object obj = map.get(OBJECT_MAP_KEY_PARAM);
            Object[] objArr = (Object[]) map.get(OBJECT_MAP_KEY_ARGS);
            int i = message.what;
            if (i == 0) {
                controlState((StateMachine.Event) map.get(OBJECT_MAP_KEY_TYPE), (Structure) obj, objArr);
            } else {
                if (i != 1) {
                    return;
                }
                controlAppPhase((AppPhase) map.get(OBJECT_MAP_KEY_TYPE), objArr);
            }
        }

        AppPhase getCurrentPhase() {
            return this._appState;
        }

        public StateMachine getCurrentState() {
            return this._currentState;
        }

        private void controlAppPhase(AppPhase appPhase, Object[] objArr) {
            int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateController$AppPhase[appPhase.ordinal()];
            if (i != 1 && i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        Intent intent = (Intent) objArr[0];
                        if (this._appState != AppPhase.STOPPED && intent.getStringExtra("NotificationID") != null) {
                            this._notificationID = intent.getStringExtra("NotificationID");
                            this.launchType = LaunchType.NOTIFICATION;
                        }
                        if (LaunchManagementActivity.isDirectLaunch(intent) && AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$LaunchManagementActivity$DirectType[LaunchManagementActivity.findDirectType(intent).ordinal()] == 1) {
                            this._sid = null;
                            this._cid = null;
                            String strFindServiceId = LaunchManagementActivity.findServiceId(intent);
                            this._sid = strFindServiceId;
                            if (!TextUtils.isEmpty(strFindServiceId)) {
                                this._cid = LaunchManagementActivity.findCardId(intent);
                            }
                        }
                        StateController.postStateEvent(StateMachine.Event.EA_START_APPLICATION, objArr);
                    } else {
                        throw new IllegalArgumentException(appPhase.getClass().getName() + "is not type of AppState");
                    }
                } else if (this._currentState == StateMachine.P_STATE_NOTICE_LIST || this._currentState == StateMachine.P_STATE_NOTICE_DETAIL_FROM_SPECIFIC || this._currentState == StateMachine.P_STATE_NOTICE_DETAIL_FROM_LIST) {
                    NotificationController.getInstance(this._context).deleteNotification(this._context);
                }
            }
            this._appState = appPhase;
            if (AppPhase.FOR_GROUND == StateController.getAppPhase() && this.isPendingRequest) {
                this.isPendingRequest = false;
                executeRequest();
            }
        }

        private void controlState(StateMachine.Event event, Structure structure, Object[] objArr) {
            if (event.type() == StateMachine.Event.Type.EVENT_TYPE_USER_ACTION) {
                StructureType type = structure != null ? structure.getType() : null;
                Structure structure2 = this._currentStructure;
                if (type != (structure2 != null ? structure2.getType() : null) && structure != null && structure.getType() != null) {
                    return;
                }
            }
            StateMachine next = this._currentState.next(this, event, objArr);
            if (next != null) {
                this._params.extra = null;
                if (objArr != null) {
                    if (1 <= objArr.length) {
                        this._params.extra = objArr[0];
                    }
                    if (2 <= objArr.length && (objArr[0] instanceof MfmException)) {
                        this._params.specificError = ((Integer) objArr[1]).intValue();
                    }
                }
                this._currentState.exit(this, objArr);
                this._currentState = next;
                this._currentStructure = next.structure(this._params);
                this._currentState.entry(this, objArr);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S00_EA1_next(Object[] objArr) {
            try {
                Intent intent = (Intent) objArr[0];
                if (this._notificationID == null) {
                    this._notificationID = intent.getStringExtra("NotificationID");
                }
                if (this._notificationID != null) {
                    this.launchType = LaunchType.NOTIFICATION;
                } else if (intent.getAction() != null && intent.getAction().equals("android.nfc.action.TECH_DISCOVERED")) {
                    _nfcTagInfo.saveNfcTag((Tag) intent.getParcelableExtra("android.nfc.extra.TAG"));
                    this.launchType = LaunchType.EX_IC_CARD;
                } else {
                    this._params.isFirstPageRecommend = intent.getBooleanExtra(PresenterData.EXTRA_KEY_SPECIFIC_RECOMMEND, false);
                }
            } catch (Exception e) {
                MfmException mfmException = new MfmException(StateController.class, 1042, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S00_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        private void executeRequest() {
            if (this._currentStructure != null) {
                if (AppPhase.BACK_GROUND == StateController.getAppPhase()) {
                    this.isPendingRequest = true;
                } else {
                    RequestCaster.getInstance().request(this._currentStructure);
                }
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S01_common_entry() {
            try {
                try {
                    executeRequest();
                    this._model.initialize(new ModelGateway.InitializeListener() { // from class: com.felicanetworks.mfm.main.presenter.internal.StateController.StatusControlHandler.2
                        @Override // com.felicanetworks.mfm.main.model.ModelGateway.InitializeListener
                        public void onComplete(ModelGateway.FunctionProvider functionProvider, ModelGateway.InitializedState initializedState) {
                            StatusControlHandler.this._provider = functionProvider;
                            StatusControlHandler.this._params.centralFuncAgent = new CentralFuncAgent(StatusControlHandler.this._provider.getCentralFunc(), StatusControlHandler.this._provider.getMfiCardFunc());
                            StatusControlHandler.this._params.extIcCardFuncAgent = new ExtIcCardFuncAgent(StatusControlHandler.this._provider.getExtIcCardFunc());
                            StatusControlHandler.this._params.memoryUsageFuncAgent = new MemoryUsageFuncAgent(StatusControlHandler.this._provider.getMemoryUsageFunc());
                            StatusControlHandler.this._params.noticeFuncAgent = new NoticeFuncAgent(StatusControlHandler.this._provider.getNoticeFunc());
                            StatusControlHandler.this._params.mfiCardFuncAgent = new MfiCardFuncAgent(StatusControlHandler.this._provider.getMfiCardFunc());
                            StatusControlHandler.this._params.termsOfServiceFuncAgent = new TermsOfServiceFuncAgent(StatusControlHandler.this._provider.getTermsOfServiceFunc());
                            StatusControlHandler.this._params.cardDetailFunc = functionProvider.getCardDetailFunc();
                            StatusControlHandler.this._params.deleteCardListFunc = functionProvider.getDeleteCardListFunc();
                            int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$ModelGateway$InitializedState$UpgradeType[initializedState.getUpgradeType().ordinal()];
                            if (i == 1) {
                                StateController.postStateEvent(StateMachine.Event.EM_VERSIONUP_CONFIRM_RESULT_NONE, new Object[0]);
                            } else if (i == 2) {
                                StateController.postStateEvent(StateMachine.Event.EM_VERSIONUP_CONFIRM_RESULT_ASK, new Object[0]);
                            } else {
                                if (i != 3) {
                                    return;
                                }
                                StateController.postStateEvent(StateMachine.Event.EM_VERSIONUP_CONFIRM_RESULT_REQUIRE, new Object[0]);
                            }
                        }

                        @Override // com.felicanetworks.mfm.main.model.ModelGateway.InitializeListener
                        public void onFailure(ModelErrorInfo modelErrorInfo) {
                            if (AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[modelErrorInfo.getType().ordinal()] == 1) {
                                StateController.postStateEvent(StateMachine.Event.EM_DATABASE_ACCESS_ERROR, modelErrorInfo);
                            } else {
                                StateController.postStateEvent(StateMachine.Event.EM_VERSIONUP_CONFIRM_FAILED, modelErrorInfo.getException(), 1);
                            }
                        }
                    });
                } catch (Exception e) {
                    MfmException mfmException = new MfmException(StateController.class, 1168, e);
                    LogUtil.error(mfmException);
                    StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
                }
            } finally {
                PresenterData.setPossibleInterruptAppStart(true);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S01_EV1_next() {
            try {
                this._model.deinitialize();
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 1283, e));
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S01_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S01_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S02_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S02_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S02_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S03_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S03_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S03_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S04_common_entry() {
            try {
                if (ServicePreference.getInstance().loadTutorial(this._context)) {
                    StateController.postStateEvent(StateMachine.Event.EP_JUDGE_TUTORIAL_SHOW, SYSTEM_SENDER, new Object[0]);
                } else {
                    StateController.postStateEvent(StateMachine.Event.EP_JUDGE_TUTORIAL_UNSHOWN, SYSTEM_SENDER, new Object[0]);
                }
            } catch (Exception e) {
                MfmException mfmException = new MfmException(StateController.class, 1396, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S04_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S04_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S05_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S05_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S05_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S06_common_entry() {
            try {
                executeRequest();
                if (this._provider.getCentralFunc() != null) {
                    int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus[this._provider.getCentralFunc().confirmExistMfc(this._context.getPackageManager()).ordinal()];
                    if (i == 1) {
                        this._params.missingAppInfo = 1;
                        StateController.postStateEvent(StateMachine.Event.EM_MFC_DISABLED, new MfmException(StateController.class, 1618, "MFC_DISABLED"), 4);
                    } else if (i == 2) {
                        this._params.missingAppInfo = 1;
                        StateController.postStateEvent(StateMachine.Event.EM_MFC_UNINSTALLED, new Object[0]);
                    } else if (i == 3) {
                        this._provider.getCentralFunc().precompile(new CentralFunc.PrecompileListener() { // from class: com.felicanetworks.mfm.main.presenter.internal.StateController.StatusControlHandler.3
                            @Override // com.felicanetworks.mfm.main.model.CentralFunc.PrecompileListener
                            public void onCompleted(CentralFunc.PrecompileListener.PrecompiledState precompiledState) {
                                ServicePreference.getInstance().saveIDm(StatusControlHandler.this._context, Settings.idm());
                                ServicePreference.getInstance().saveIcCode(StatusControlHandler.this._context, Settings.icCode());
                                int i2 = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$CentralFunc$PrecompileListener$PrecompiledState$FelicaDeviceState[precompiledState.getFelicaDeviceState().ordinal()];
                                if (i2 == 1) {
                                    StateController.postStateEvent(StateMachine.Event.EM_FELICA_INITIALIZE_INCOMPLETE, new Object[0]);
                                } else {
                                    if (i2 == 2) {
                                        StateController.postStateEvent(StateMachine.Event.EM_FELICA_INITIALIZE_COMPLETE, new Object[0]);
                                        return;
                                    }
                                    MfmException mfmException = new MfmException(StateController.class, 2, "NOT_EQUIPPED");
                                    LogUtil.error(mfmException);
                                    StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, mfmException);
                                }
                            }

                            @Override // com.felicanetworks.mfm.main.model.CentralFunc.PrecompileListener
                            public void onError(ModelErrorInfo modelErrorInfo) {
                                switch (AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[modelErrorInfo.getType().ordinal()]) {
                                    case 1:
                                        StateController.postStateEvent(StateMachine.Event.EM_DATABASE_ACCESS_ERROR, modelErrorInfo);
                                        break;
                                    case 2:
                                        StateController.postStateEvent(StateMachine.Event.EM_UNSUPPORT_FELICA_SETTING_APP, modelErrorInfo);
                                        break;
                                    case 3:
                                        StateController.postStateEvent(StateMachine.Event.EM_MFCPERMINSPECT_ERROR, modelErrorInfo);
                                        break;
                                    case 4:
                                        StateController.postStateEvent(StateMachine.Event.EM_FELICA_TIMEOUT_ERROR, modelErrorInfo);
                                        break;
                                    case 5:
                                        StateController.postStateEvent(StateMachine.Event.EM_MFC_OTHER_ERROR, modelErrorInfo);
                                        break;
                                    case 6:
                                        StateController.postStateEvent(StateMachine.Event.EM_OTHER_APP_USING_MFC_ERROR, modelErrorInfo);
                                        break;
                                    case 7:
                                        StateController.postStateEvent(StateMachine.Event.EM_RUNNING_BY_MFIC, modelErrorInfo);
                                        break;
                                    case 8:
                                        StateController.postStateEvent(StateMachine.Event.EM_FELICA_LOCK_ERROR, modelErrorInfo);
                                        break;
                                    case 9:
                                        StateController.postStateEvent(StateMachine.Event.EM_FELICA_OPEN_ERROR, modelErrorInfo);
                                        break;
                                    case 10:
                                        StateController.postStateEvent(StateMachine.Event.EM_FELICA_INVALID_RESPONSE_ERROR, modelErrorInfo);
                                        break;
                                    case 11:
                                        StateController.postStateEvent(StateMachine.Event.EM_FELICA_HTTP_ERROR, modelErrorInfo);
                                        break;
                                    case 12:
                                        StateController.postStateEvent(StateMachine.Event.EM_MFIC_VERSION_ERROR, new Object[0]);
                                        break;
                                    default:
                                        LogUtil.error(modelErrorInfo.getException());
                                        StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, modelErrorInfo.getException());
                                        break;
                                }
                            }
                        });
                    }
                }
            } catch (Exception e) {
                MfmException mfmException = new MfmException(StateController.class, 4, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S06_EV1_next() {
            try {
                if (this._provider.getCentralFunc() != null) {
                    this._provider.getCentralFunc().cancel();
                }
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 1929, e));
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S06_EM15_next() {
            this._params.isFelicaLock = true;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S06_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S06_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S07_common_entry() {
            if (this._provider.getCentralFunc() != null) {
                this._provider.getCentralFunc().mfcFinish();
            }
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S07_1_EP17_next() {
            this._params.missingAppInfo = 2;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S07_1_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S07_1_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S07_2_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S07_2_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S08_common_entry() {
            ServicePreference.getInstance().saveTutorial(this._context, false);
            if (this._provider.getNoticeFunc() != null) {
                this._provider.getNoticeFunc().syncServer(NoticeFunc.SyncType.ALL);
            }
            try {
                int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateController$LaunchType[this.launchType.ordinal()];
                if (i == 1) {
                    StateController.postStateEvent(StateMachine.Event.EP_START_KIND_MYSERVICE, new Object[0]);
                    return;
                }
                if (i != 2) {
                    if (i != 3) {
                        return;
                    }
                    StateController.postStateEvent(StateMachine.Event.EP_START_KIND_CARD_READER, new Object[0]);
                    return;
                }
                if (this._provider.getNoticeFunc() != null) {
                    try {
                        this._params.noticeInfoAgent = new NoticeInfoAgent(this._provider.getNoticeFunc().getNotice(this._notificationID));
                    } catch (IllegalArgumentException unused) {
                        this.launchType = LaunchType.NORMAL;
                        this._notificationID = null;
                        StateController.postStateEvent(StateMachine.Event.EP_START_KIND_MYSERVICE, new Object[0]);
                        return;
                    }
                }
                if (this._provider.getCentralFunc() != null) {
                    this._provider.getCentralFunc().mfcFinish();
                }
                this._mdum.setDirect(true);
                StateController.postStateEvent(StateMachine.Event.EP_START_KIND_NOTICE, new Object[0]);
            } catch (Exception e) {
                MfmException mfmException = new MfmException(StateController.class, 2150, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S08_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S08_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S09_common_entry() {
            try {
                executeRequest();
                if (this._provider.getCentralFunc() != null) {
                    this._params.isFelicaLock = false;
                    LockStatusReceiver.resetReceived();
                    this._params.isEnoughExtCardServiceInfo = false;
                    boolean z = this._currentState == StateMachine.P_STATE_CREATE_SIM_DATA_FROM_CENTRAL && this._params.isForceCheck;
                    this._params.isForceCheck = false;
                    this._params.mfiAccountName = null;
                    this._provider.getCentralFunc().compile(z, new CentralFunc.CompileListener() { // from class: com.felicanetworks.mfm.main.presenter.internal.StateController.StatusControlHandler.4
                        @Override // com.felicanetworks.mfm.main.model.CentralFunc.CompileListener
                        public void onCompleted() {
                            if (StatusControlHandler.this._provider.getCentralFunc() != null) {
                                if (StatusControlHandler.this._provider.getCentralFunc().getCompiledState().getFelicaState() == CentralFunc.CompiledState.FelicaState.LOCK_ERROR) {
                                    StatusControlHandler.this._params.isFelicaLock = true;
                                }
                                StatusControlHandler.this._params.isEnoughExtCardServiceInfo = !StatusControlHandler.this._provider.getCentralFunc().getCompiledState().isWaringLackEmoneyInfo();
                            }
                            StateController.postStateEvent(StateMachine.Event.EM_CREATE_SIM_DATA_COMPLETE, new Object[0]);
                        }

                        @Override // com.felicanetworks.mfm.main.model.CentralFunc.CompileListener
                        public void onError(ModelErrorInfo modelErrorInfo) {
                            int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[modelErrorInfo.getType().ordinal()];
                            if (i == 1) {
                                StateController.postStateEvent(StateMachine.Event.EM_DATABASE_ACCESS_ERROR, modelErrorInfo);
                                return;
                            }
                            if (i == 2) {
                                StateController.postStateEvent(StateMachine.Event.EM_UNSUPPORT_FELICA_SETTING_APP, modelErrorInfo);
                                return;
                            }
                            if (i == 3) {
                                StateController.postStateEvent(StateMachine.Event.EM_MFCPERMINSPECT_ERROR, modelErrorInfo);
                                return;
                            }
                            if (i == 5) {
                                StateController.postStateEvent(StateMachine.Event.EM_MFC_OTHER_ERROR, modelErrorInfo);
                                return;
                            }
                            if (i == 11) {
                                StateController.postStateEvent(StateMachine.Event.EM_FELICA_HTTP_ERROR, modelErrorInfo);
                            } else if (i == 12) {
                                StateController.postStateEvent(StateMachine.Event.EM_MFIC_VERSION_ERROR, new Object[0]);
                            } else {
                                LogUtil.error(modelErrorInfo.getException());
                                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, modelErrorInfo.getException());
                            }
                        }
                    });
                }
            } catch (Exception e) {
                MfmException mfmException = new MfmException(StateController.class, 6, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S09_common_exit() {
            try {
                if (this._provider.getCentralFunc() != null && this._provider.getCentralFunc().getCompiledState().hasWarning()) {
                    this._provider.getCentralFunc().cancel();
                }
                if (this._provider.getMfiCardFunc() != null) {
                    this._provider.getMfiCardFunc().clearRecoveryErrorInfo();
                }
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 2387, e));
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S09_1_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S09_1_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S09_2_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S09_2_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S09_3_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S09_3_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S10_common_entry() {
            try {
                try {
                    if (this.launchType == LaunchType.EX_IC_CARD) {
                        if (this._provider.getCentralFunc() != null && !this._provider.getCentralFunc().getCompiledState().isWaringLackEmoneyInfo()) {
                            StateController.postStateEvent(StateMachine.Event.EP_JUDGE_SCREEN_CARD_READING, new Object[0]);
                            return;
                        }
                        this._params.isFailTransitionExtCard = true;
                    }
                    if (!TextUtils.isEmpty(this._sid) || !TextUtils.isEmpty(this._cid)) {
                        if (SupportServiceType.isMfiSupportService(this._sid) && this._provider.getMfiCardFunc() != null && !this._provider.getMfiCardFunc().isLoggedIn()) {
                            this._params.isWarningNotLogined = true;
                        }
                        if (!this._params.isWarningNotLogined) {
                            String str = this._sid;
                            String str2 = this._cid;
                            MyServiceGroupInfoAgent myServiceGroupInfoAgent = null;
                            this._sid = null;
                            this._cid = null;
                            this._params.isWarningLackServiceInfo = false;
                            this._params.isWarningLackCardInfo = false;
                            this._params.groupInfoForDetail = null;
                            this._params.serviceInfoForDetail = null;
                            this._params.cardInfoForDetail = null;
                            MyServiceInfoAgent myServiceInfoAgent = null;
                            MyServiceInfoAgent myServiceInfoAgent2 = null;
                            for (MyServiceGroupInfoAgent myServiceGroupInfoAgent2 : this._params.centralFuncAgent.getMyServiceGroupInfoList()) {
                                MyServiceInfoAgent myServiceInfoAgentFindService = myServiceGroupInfoAgent2.findService(str);
                                MyServiceInfoAgent myServiceInfoAgentFindDeleteService = myServiceGroupInfoAgent2.findDeleteService(str);
                                if (myServiceInfoAgentFindService == null && myServiceInfoAgentFindDeleteService == null) {
                                    myServiceInfoAgent = myServiceInfoAgentFindService;
                                    myServiceInfoAgent2 = myServiceInfoAgentFindDeleteService;
                                }
                                myServiceGroupInfoAgent = myServiceGroupInfoAgent2;
                                myServiceInfoAgent = myServiceInfoAgentFindService;
                                myServiceInfoAgent2 = myServiceInfoAgentFindDeleteService;
                            }
                            if ((myServiceInfoAgent == null || !myServiceInfoAgent.hasDetailInformation()) && (myServiceInfoAgent2 == null || !myServiceInfoAgent2.hasDetailInformation())) {
                                this._params.isWarningLackServiceInfo = true;
                            } else if (TextUtils.isEmpty(str2)) {
                                if (myServiceInfoAgent != null && myServiceInfoAgent.getLeadType() == MyServiceInfoAgent.LeadType.NONE) {
                                    this._params.groupInfoForDetail = myServiceGroupInfoAgent;
                                    this._params.serviceInfoForDetail = myServiceInfoAgent;
                                    this._mdum.setDirect(true);
                                    StateController.postStateEvent(StateMachine.Event.EP_CARD_DETAIL_EX, new Object[0]);
                                    return;
                                }
                                this._params.isWarningLackServiceInfo = true;
                            } else {
                                MyCardInfoAgent myCardInfoAgentFindCard = myServiceGroupInfoAgent.findCard(str2);
                                MyCardInfoAgent myCardInfoAgentFindDeleteCard = myServiceGroupInfoAgent.findDeleteCard(str2);
                                if (myCardInfoAgentFindCard != null || myCardInfoAgentFindDeleteCard != null) {
                                    if (myCardInfoAgentFindDeleteCard != null) {
                                        this._params.serviceInfoForDetail = myServiceInfoAgent2;
                                        this._params.cardInfoForDetail = myCardInfoAgentFindDeleteCard;
                                        this._mdum.setDirect(true);
                                        StateController.postStateEvent(StateMachine.Event.EP_JUDGE_SCREEN_DELETE_CARD_LIST, new Object[0]);
                                    } else if (myServiceInfoAgent.getLeadType() != MyServiceInfoAgent.LeadType.NONE) {
                                        this._params.isWarningLackServiceInfo = true;
                                    } else {
                                        this._params.groupInfoForDetail = myServiceGroupInfoAgent;
                                        this._params.serviceInfoForDetail = myServiceInfoAgent;
                                        this._params.cardInfoForDetail = myCardInfoAgentFindCard;
                                        this._mdum.setDirect(true);
                                        StateController.postStateEvent(StateMachine.Event.EP_CARD_DETAIL_EX, new Object[0]);
                                    }
                                    return;
                                }
                                this._params.isWarningLackCardInfo = true;
                            }
                        }
                    }
                } catch (Exception e) {
                    MfmException mfmException = new MfmException(StateController.class, 769, e);
                    LogUtil.error(mfmException);
                    StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
                }
                if (this._params.isFirstPageRecommend) {
                    StateController.postStateEvent(StateMachine.Event.EP_JUDGE_SCREEN_RECOMMEND, new Object[0]);
                    return;
                }
                if (isDisplayMyservice()) {
                    StateController.postStateEvent(StateMachine.Event.EP_JUDGE_SCREEN_MYSERVICE, new Object[0]);
                } else {
                    StateController.postStateEvent(StateMachine.Event.EP_JUDGE_SCREEN_RECOMMEND, new Object[0]);
                }
            } finally {
                this.launchType = LaunchType.NORMAL;
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S10_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S10_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S10_EP6_next() {
            this._params.isFirstPageRecommend = true;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S11_common_entry() {
            if (LockStatusReceiver.isReceived()) {
                StateController.postStateEvent(StateMachine.Event.EI_LOCK_STATE_CHANGE, new Object[0]);
            } else {
                executeRequest();
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S11_common_exit() {
            try {
                if (this._provider.getCentralFunc() != null) {
                    this._provider.getCentralFunc().cancel();
                }
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 819, e));
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S11_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public boolean S11_EI3_next(Object[] objArr) {
            if (this._provider.getCentralFunc() != null && !this._provider.getCentralFunc().getCompiledState().isWaringLackEmoneyInfo()) {
                _SXX_EI3_next(objArr);
                return true;
            }
            this._params.isFailTransitionExtCard = true;
            return false;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S11_EV12_next(Object[] objArr) {
            if (objArr == null || objArr.length < 2) {
                return;
            }
            this._params.isFirstPageRecommend = ((Boolean) objArr[0]).booleanValue();
            this._params.isForceCheck = ((Boolean) objArr[1]).booleanValue();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S11_EV14_next(Object[] objArr) {
            if (objArr == null || objArr.length < 1) {
                return;
            }
            this._params.recommendInfoAgent = (RecommendInfoAgent) objArr[0];
            this._params.isFirstPageRecommend = true;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S11_EV42_next(Object[] objArr) {
            if (objArr == null || objArr.length < 2) {
                return;
            }
            this._params.groupInfoForDetail = (MyServiceGroupInfoAgent) objArr[0];
            this._params.serviceInfoForDetail = (MyServiceInfoAgent) objArr[1];
            this._params.cardInfoForDetail = null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S11_EV51_next(Object[] objArr) {
            if (objArr == null || objArr.length < 1) {
                return;
            }
            this._params.serviceInfoForDetail = (MyServiceInfoAgent) objArr[0];
            this._params.cardInfoForDetail = null;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S12_common_entry() {
            executeRequest();
            try {
                try {
                    if (this._provider.getNoticeFunc() != null) {
                        this._notificationID = this._notificationID != null ? this._notificationID : this._beforeNotificationID;
                        NoticeInfo notice = this._provider.getNoticeFunc().getNotice(this._notificationID);
                        if (notice != null) {
                            notice.setReadStatus(this._provider.getNoticeFunc());
                        }
                    }
                    NotificationController.getInstance(this._context).deleteNotification(this._context);
                } catch (Exception e) {
                    LogUtil.warning(new MfmException(StateController.class, 388, e));
                }
            } finally {
                this._beforeNotificationID = this._notificationID;
                this._notificationID = null;
                this.launchType = LaunchType.NORMAL;
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S12_1_EV17_next() {
            common_EV17_next();
        }

        private void common_EV17_next() {
            NoticeInfo notice;
            try {
                if (this._provider.getNoticeFunc() == null || (notice = this._provider.getNoticeFunc().getNotice(this._params.noticeInfoAgent.getId())) == null) {
                    return;
                }
                notice.setDetailAccessStatus(this._provider.getNoticeFunc());
            } catch (Exception e) {
                MfmException mfmException = new MfmException(StateController.class, 278, e);
                LogUtil.error(e);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S12_1_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S12_1_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S12_2_EV17_next() {
            common_EV17_next();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S12_2_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S12_2_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S13_common_entry() {
            executeRequest();
        }

        public void sendLocalTagEvent() {
            if (_nfcTagInfo.isHoldNfcTag()) {
                StateController.postStateEvent(StateMachine.Event.EI_NFC_TAG_RECEIVE, _nfcTagInfo.getNfcTag());
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S13_EV18_next(Object[] objArr) {
            try {
                if (this._provider.getCentralFunc() != null) {
                    ExtIcCardInfoAgent extIcCardInfoAgent = (ExtIcCardInfoAgent) objArr[0];
                    for (RecommendInfo recommendInfo : this._provider.getCentralFunc().getRecommendInfoList()) {
                        if (TextUtils.equals(extIcCardInfoAgent.getId(), recommendInfo.getId())) {
                            this._params.recommendInfoAgent = new RecommendInfoAgent(recommendInfo);
                        }
                    }
                }
            } catch (Exception e) {
                MfmException mfmException = new MfmException(StateController.class, 274, e);
                LogUtil.error(e);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S13_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S13_EI3_next(Object[] objArr) {
            try {
                try {
                    _nfcTagInfo.saveNfcTag((Tag) objArr[0]);
                    if (_nfcTagInfo.isHoldNfcTag()) {
                        while (!this._params.extIcCardFuncAgent.resolveTag(_nfcTagInfo.getNfcTag())) {
                            Thread.sleep(100L);
                        }
                    }
                } catch (Exception e) {
                    MfmException mfmException = new MfmException(StateController.class, 276, e);
                    LogUtil.error(e);
                    StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
                }
            } finally {
                _nfcTagInfo.resetNfcTag();
                this.launchType = LaunchType.NORMAL;
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S14_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S14_1_EV15_next() {
            _SiteAccessConfirm(this._params.recommendInfoAgent);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S14_2_EV15_next() {
            _SiteAccessConfirm(this._params.recommendInfoAgent);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S14_1_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S14_1_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S14_2_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S14_2_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S15_common_entry() {
            executeRequest();
            try {
                NotificationController.getInstance(this._context).deleteNotification(this._context);
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 290, e));
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S15_EV6_next() {
            this._params.isFirstPageRecommend = true;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S15_EV16_next(Object[] objArr) {
            try {
                this._params.noticeInfoAgent = (NoticeInfoAgent) objArr[0];
                this._notificationID = this._params.noticeInfoAgent.getId();
            } catch (Exception e) {
                MfmException mfmException = new MfmException(StateController.class, 257, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S15_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S15_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S16_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S16_1_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S16_1_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S16_2_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S16_2_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S16_3_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S16_3_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S16_4_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S16_4_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S16_5_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S16_5_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S17_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S17_EV6_next() {
            this._params.isFirstPageRecommend = true;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S17_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S17_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S18_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S18_common_exit() {
            try {
                if (this._provider.getNoticeFunc() != null) {
                    this._provider.getNoticeFunc().syncServer(NoticeFunc.SyncType.ACCEPT);
                }
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 309, e));
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S18_EV6_next() {
            this._params.isFirstPageRecommend = true;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S18_EV23_next(Object[] objArr) {
            try {
                if (this._provider.getNoticeFunc() != null) {
                    this._provider.getNoticeFunc().setNoticeSetting(((Boolean) objArr[0]).booleanValue());
                }
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 311, e));
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S18_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S18_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S19_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S19_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S19_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S20_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S20_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S20_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S21_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S34_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S21_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S21_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S22_common_entry() {
            try {
                if (this._params.isFirstPageRecommend) {
                    ((RebootStructure) this._currentStructure).setExtraInfo(new RebootStructure.ExtraInfo(PresenterData.EXTRA_KEY_SPECIFIC_RECOMMEND, true));
                }
                executeRequest();
                this._model.deinitialize();
            } catch (Exception e) {
                LogUtil.warning(e);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S22_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S23_common_entry() {
            try {
                executeRequest();
                this._model.deinitialize();
            } catch (Exception e) {
                LogUtil.warning(e);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S23_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S23_common_exit() {
            StateController.initialize(this._context);
            StateController.postAppPhase(AppPhase.STOPPED, new Object[0]);
            PresenterData.getInstance().setContext(this._context);
            AnalysisManager.stampFinish();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S24_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S24_1_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S24_2_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S25_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S25_EV6_next() {
            this._params.isFirstPageRecommend = true;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S25_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S25_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S26_common_entry() {
            try {
                executeRequest();
                if (isPossibleTransitionRecommendDetail(this._params.recommendInfoAgent.getId())) {
                    StateController.postStateEvent(StateMachine.Event.EP_JUDGE_RECOMMEND_DETAIL_TRANSITABLE, new Object[0]);
                } else {
                    StateController.postStateEvent(StateMachine.Event.EP_JUDGE_RECOMMEND_DETAIL_NOT_TRANSITABLE, new Object[0]);
                }
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 341, e));
            }
        }

        private boolean isPossibleTransitionRecommendDetail(String str) {
            CentralFunc centralFunc = this._provider.getCentralFunc();
            if (centralFunc == null) {
                return false;
            }
            List<MyServiceInfo> myServiceInfoList = centralFunc.getMyServiceInfoList();
            if (myServiceInfoList.isEmpty()) {
                return true;
            }
            Iterator<MyServiceInfo> it = myServiceInfoList.iterator();
            while (it.hasNext()) {
                if (TextUtils.equals(str, it.next().getId())) {
                    return true;
                }
            }
            Iterator<RecommendInfo> it2 = centralFunc.getRecommendInfoList().iterator();
            while (it2.hasNext()) {
                if (TextUtils.equals(str, it2.next().getId())) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S26_1_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S26_1_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S26_2_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S26_2_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S27_common_entry() {
            try {
                if (isPossibleDirectExtCardLaunch()) {
                    StateController.postStateEvent(StateMachine.Event.EP_JUDGE_ExtCard_TRANSITABLE, new Object[0]);
                } else {
                    StateController.postStateEvent(StateMachine.Event.EP_JUDGE_ExtCard_NOT_TRANSITABLE, new Object[0]);
                }
            } catch (Exception e) {
                MfmException mfmException = new MfmException(StateController.class, 342, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S28_common_entry() {
            try {
                if (_nfcTagInfo.getNfcTag() != null) {
                    ((RebootStructure) this._currentStructure).setExtraInfo(new RebootStructure.ExtraInfo("android.nfc.extra.TAG", _nfcTagInfo.getNfcTag()));
                    ((RebootStructure) this._currentStructure).setAction("android.nfc.action.TECH_DISCOVERED");
                }
                executeRequest();
                this._model.deinitialize();
            } catch (Exception e) {
                LogUtil.warning(e);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S28_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        private boolean isPossibleDirectExtCardLaunch() {
            if (this.launchType == LaunchType.EX_IC_CARD && this._provider.getExtIcCardFunc() != null && this._provider.getExtIcCardFunc().isExistTargetCards()) {
                this.launchType = LaunchType.NORMAL;
                return true;
            }
            _nfcTagInfo.resetNfcTag();
            return false;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S29_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S29_EV6_next() {
            this._params.isFirstPageRecommend = true;
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S30_common_entry() {
            try {
                ((MfiCardFunc) Objects.requireNonNull(this._provider.getMfiCardFunc())).reset();
                CentralFunc centralFunc = this._provider.getCentralFunc();
                Objects.requireNonNull(centralFunc);
                executeRequest();
                if (this._params.isFelicaLock) {
                    StateController.postStateEvent(StateMachine.Event.EM_MFI_SERVICE_COMPLETE, new Object[0]);
                    return;
                }
                if (centralFunc.getCompiledState().hasFelicaWarning()) {
                    StateController.postStateEvent(StateMachine.Event.EM_MFI_SERVICE_COMPLETE, new Object[0]);
                    return;
                }
                int i = this._params.resultCodeFromMfiLogin;
                this._params.resultCodeFromMfiLogin = MfiLoginResultCode.NONE.getCode();
                int i2 = this._params.extraAccountCode;
                this._params.extraAccountCode = 0;
                this._provider.getMfiCardFunc().compile(this._provider.getCentralFunc(), ServicePreference.getInstance().loadMfiAccountHash(this._context), i, i2, new MfiCardFunc.CompileListener() { // from class: com.felicanetworks.mfm.main.presenter.internal.StateController.StatusControlHandler.5
                    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.CompileListener
                    public void onChangedAccount(String str, String str2) {
                        MyServiceDatabaseAccess.getInstance().deleteRelatedToAccount();
                        ((NoticeFunc) Objects.requireNonNull(StatusControlHandler.this._provider.getNoticeFunc())).invalidateServiceRegisterState();
                        ServicePreference.getInstance().saveMfiAccountHash(StatusControlHandler.this._context, str);
                    }

                    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.CompileListener
                    public void onRequestActivity(Intent intent) {
                        StatusControlHandler.this._params.startIntentForMfiLogin = intent;
                        StateController.postStateEvent(StateMachine.Event.EM_REQUEST_ACTIVITY, new Object[0]);
                    }

                    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.CompileListener
                    public void onCompleted(List<MyCardInfo> list, MfiCardFunc.CompiledState compiledState) {
                        if (MfiCardFunc.CompiledState.MfiCardState.NEED_CARD_RECOVERY == compiledState.getMfiCardState()) {
                            StateController.postStateEvent(StateMachine.Event.EM_NEED_CARD_RECOVERY, StateMachine.Event.EM_NEED_CARD_RECOVERY);
                        } else {
                            StateController.postStateEvent(StateMachine.Event.EM_MFI_SERVICE_COMPLETE, new Object[0]);
                        }
                    }

                    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.CompileListener
                    public void onError(ModelErrorInfo modelErrorInfo) {
                        StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, modelErrorInfo);
                    }
                });
            } catch (Exception e) {
                MfmException mfmException = new MfmException(StateController.class, 343, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S30_common_marshal() {
            CentralFunc centralFunc = (CentralFunc) Objects.requireNonNull(this._provider.getCentralFunc());
            MfiCardFunc mfiCardFunc = (MfiCardFunc) Objects.requireNonNull(this._provider.getMfiCardFunc());
            NoticeFunc noticeFunc = (NoticeFunc) Objects.requireNonNull(this._provider.getNoticeFunc());
            centralFunc.orderAsset(mfiCardFunc);
            FelicaPocketFunc.CompiledFpState compiledFpState = centralFunc.getCompiledFpState();
            if (AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$FelicaPocketFunc$CompiledFpState$FelicaFpState[compiledFpState.getFelicaFPState().ordinal()] == 1) {
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, compiledFpState.getModelErrorInfo());
                return;
            }
            this._params.centralFuncAgent.marshalServiceInfo();
            this._params.mfiAccountName = MfiPreference.getInstance(this._context).loadMfiAccountName();
            try {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry<String, MyServiceInfo.RegisterState> entry : this._params.centralFuncAgent.getRegisterStatus().entrySet()) {
                    MyServiceInfo.RegisterState registerState = this._params.centralFuncAgent.getRegisterStatus().get(entry.getKey());
                    if (MyServiceInfo.RegisterState.NO_REGISTER == registerState && this._params.centralFuncAgent.hasDeleteNotExistTCard(entry.getKey())) {
                        registerState = MyServiceInfo.RegisterState.REGISTERED;
                    }
                    linkedHashMap.put(entry.getKey(), registerState);
                }
                noticeFunc.setServiceRegisterState(linkedHashMap, centralFunc.getCompiledState(), mfiCardFunc.getCompiledState());
            } catch (MfmException e) {
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, e);
            }
            this._mdum.init();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S30_common_exit(Object[] objArr) {
            try {
                CentralFunc centralFunc = (CentralFunc) Objects.requireNonNull(this._provider.getCentralFunc());
                if (objArr.length == 0 || ((objArr[0] instanceof StateMachine.Event) && objArr[0] != StateMachine.Event.EM_NEED_CARD_RECOVERY)) {
                    centralFunc.mfcFinish();
                }
                if (this._provider.getMfiCardFunc() != null) {
                    this._provider.getMfiCardFunc().cancel();
                }
            } catch (Exception e) {
                MfmException mfmException = new MfmException(StateController.class, 2388, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S30_1_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S30_1_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S30_2_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S30_2_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S30_3_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S30_3_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S35_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S36_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S36_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S36_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S33_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S33_common_exit() {
            try {
                if (this._provider.getCardDetailFunc() != null) {
                    this._provider.getCardDetailFunc().cancel();
                }
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 832, e));
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S33_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S33_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S37_common_entry() {
            try {
                if (this._provider.getTermsOfServiceFunc().isUpgrade()) {
                    StateController.postStateEvent(StateMachine.Event.EM_TOS_SITE_UPGRADE, SYSTEM_SENDER, new Object[0]);
                } else {
                    StateController.postStateEvent(StateMachine.Event.EM_TOS_SITE_NONE_UPGRADE, SYSTEM_SENDER, new Object[0]);
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, null, e);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S37_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S37_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S38_common_entry() {
            try {
                this._provider.getTermsOfServiceFunc().setDisplayedTos(true);
            } catch (Exception e) {
                LogUtil.warning(e);
            }
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S38_EV47_next(Object[] objArr) {
            try {
                TermsOfServiceFunc termsOfServiceFunc = this._provider.getTermsOfServiceFunc();
                if (termsOfServiceFunc != null) {
                    termsOfServiceFunc.savePolicyConsent();
                }
            } catch (Exception e) {
                MfmException mfmException = new MfmException(StateController.class, 919, e);
                LogUtil.error(mfmException);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, mfmException);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S38_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S38_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S39_common_entry() {
            try {
                executeRequest();
                final boolean zLoadTutorial = ServicePreference.getInstance().loadTutorial(this._context);
                this._provider.getMfiCardFunc().mfiLogin(0, zLoadTutorial, new MfiCardFunc.MfiLoginListener() { // from class: com.felicanetworks.mfm.main.presenter.internal.StateController.StatusControlHandler.6
                    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.MfiLoginListener
                    public void onRequestActivity(Intent intent) {
                        StatusControlHandler.this._params.startIntentForMfiLogin = intent;
                        StateController.postStateEvent(StateMachine.Event.EM_REQUEST_ACTIVITY, new Object[0]);
                    }

                    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.MfiLoginListener
                    public void onCompleted(List<MyCardInfo> list, MfiCardFunc.CompiledState compiledState) {
                        StateController.postStateEvent(StateMachine.Event.EM_MFI_LOGIN_RESULT, new Object[0]);
                    }

                    @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.MfiLoginListener
                    public void onError(ModelErrorInfo modelErrorInfo) {
                        if (zLoadTutorial) {
                            StateController.postStateEvent(StateMachine.Event.EM_MFI_LOGIN_RESULT, new Object[0]);
                            return;
                        }
                        int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[modelErrorInfo.getType().ordinal()];
                        if (i != 13 && i != 14) {
                            ((MfiLoginReadyingDrawStructure) StatusControlHandler.this._currentStructure).notifyFailure(modelErrorInfo);
                        } else {
                            StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, modelErrorInfo);
                        }
                    }
                });
            } catch (Exception e) {
                LogUtil.warning(e);
                StateController.postStateEvent(StateMachine.Event.EI_FATAL_ERROR, e);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S39_EV39_next(Object[] objArr) {
            this._params.resultCodeFromMfiLogin = ((Integer) objArr[0]).intValue();
            this._params.extraAccountCode = ((Integer) objArr[1]).intValue();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S39_common_cancel(Object[] objArr) {
            this._params.resultCodeFromMfiLogin = ((Integer) objArr[0]).intValue();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S39_EV41_next(Object[] objArr) {
            this._params.resultCodeFromMfiLogin = ((Integer) objArr[0]).intValue();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S39_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S39_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S40_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S40_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S40_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S41_common_entry() {
            if (this._provider.getCentralFunc() != null) {
                this._provider.getCentralFunc().mfcFinish();
            }
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S41_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S41_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S42_common_entry() {
            if (ServicePreference.getInstance().loadTutorial(this._context)) {
                StateController.postStateEvent(StateMachine.Event.EP_JUDGE_TUTORIAL_NOT_COMPLETE, new Object[0]);
            } else {
                StateController.postStateEvent(StateMachine.Event.EP_JUDGE_TUTORIAL_COMPLETE, new Object[0]);
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S42_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S42_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S43_common_entry() {
            if (this._provider.getMfiCardFunc() == null) {
                return;
            }
            executeRequest();
            this._provider.getMfiCardFunc().executeCardRecovery(new MfiCardFunc.RecoveryListener() { // from class: com.felicanetworks.mfm.main.presenter.internal.StateController.StatusControlHandler.7
                @Override // com.felicanetworks.mfm.main.model.MfiCardFunc.RecoveryListener
                public void onCompleted() {
                    StateController.postStateEvent(StateMachine.Event.EM_COMPLETE_CARD_RECOVERY, new Object[0]);
                }
            });
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S43_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S44_common_entry() {
            if (ServicePreference.getInstance().loadTutorial(this._context)) {
                if (this._params.isFelicaLock) {
                    StateController.postStateEvent(StateMachine.Event.EM_FELICA_LOCK_ERROR, new Object[0]);
                    return;
                } else {
                    StateController.postStateEvent(StateMachine.Event.EP_JUDGE_TUTORIAL_NOT_COMPLETE, new Object[0]);
                    return;
                }
            }
            StateController.postStateEvent(StateMachine.Event.EP_JUDGE_TUTORIAL_COMPLETE, new Object[0]);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S44_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S44_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S45_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S45_common_exit() {
            try {
                if (this._provider.getDeleteCardListFunc() != null) {
                    this._provider.getDeleteCardListFunc().cancel();
                }
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 831, e));
            }
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S45_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S45_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S46_common_entry() {
            executeRequest();
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S46_EI2_next(Object[] objArr) {
            _SXX_EI2_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public void S46_EI3_next(Object[] objArr) {
            _SXX_EI3_next(objArr);
        }

        @Override // com.felicanetworks.mfm.main.presenter.internal.StateMachine.OperationExecutor
        public boolean isModelDataUpdate() {
            return this._mdum.isUpdate();
        }

        private void _SXX_EI2_next(Object[] objArr) {
            boolean z;
            try {
                final NoticeInfo noticeInfo = new NoticeInfo(this._context, (Intent) objArr[0]);
                if (!noticeInfo.valid()) {
                    AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_RECEIVE_MESSAGE, noticeInfo, false);
                    return;
                }
                boolean zCheckNotifyNotification = PushGateway.checkNotifyNotification(this._context, noticeInfo);
                final boolean zSaveData = PushGateway.saveData(this._context, noticeInfo);
                if (zSaveData && zCheckNotifyNotification) {
                    NotificationController.getInstance(this._context).notifyNotification(noticeInfo);
                    z = true;
                } else {
                    z = false;
                }
                AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_RECEIVE_MESSAGE, noticeInfo, Boolean.valueOf(z));
                AgentUtil.runMainThread(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.internal.StateController.StatusControlHandler.8
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            if (NoticeChangeDataListenerLazyKeeper.getListener() != null) {
                                NoticeChangeDataListenerLazyKeeper.getListener().onNewArrival(new NoticeInfoAgent(noticeInfo), zSaveData);
                            }
                        } catch (Exception e) {
                            LogUtil.warning(new MfmException(getClass(), 370, e));
                        }
                    }
                });
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 354, e));
            }
        }

        private void _SXX_EI3_next(Object[] objArr) {
            try {
                _nfcTagInfo.saveNfcTag((Tag) objArr[0]);
                this.launchType = LaunchType.EX_IC_CARD;
            } catch (Exception e) {
                LogUtil.warning(new MfmException(StateController.class, 355, e));
            }
        }

        private void _SiteAccessConfirm(RecommendInfoAgent recommendInfoAgent) {
            ModelGateway.FunctionProvider functionProvider;
            if (recommendInfoAgent == null || (functionProvider = this._provider) == null || functionProvider.getCentralFunc() == null) {
                LogUtil.warning(new MfmException(StateController.class, 356));
                return;
            }
            Iterator<MyServiceInfo> it = this._provider.getCentralFunc().getMyServiceInfoList().iterator();
            while (it.hasNext()) {
                if (TextUtils.equals(recommendInfoAgent.getId(), it.next().getId())) {
                    return;
                }
            }
            this._provider.getCentralFunc().reportRecommend(recommendInfoAgent.getId(), recommendInfoAgent.getVersion(), recommendInfoAgent.getCategoryId(), recommendInfoAgent.getStatus());
        }

        private boolean isDisplayMyservice() {
            boolean z = false;
            try {
                List<MyServiceInfo> myServiceInfoList = this._provider.getCentralFunc().getMyServiceInfoList();
                List<MyCardInfo> myCardInfoList = this._provider.getMfiCardFunc().getMyCardInfoList();
                List<MyCardInfo> deleteCardInfoList = this._provider.getMfiCardFunc().getDeleteCardInfoList();
                for (MyServiceInfo myServiceInfo : myServiceInfoList) {
                    if (!myServiceInfo.isAreaDetect() && !myServiceInfo.isSasDetect() && !myServiceInfo.isOnlyAppDetect()) {
                        int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.resolve(myServiceInfo.getId()).ordinal()];
                        if (i == 1 || i == 2 || i == 3 || i == 4) {
                            if (this._provider.getMfiCardFunc().isLoggedIn()) {
                                Iterator<MyCardInfo> it = myCardInfoList.iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        if (TextUtils.equals(myServiceInfo.getId(), it.next().getServiceId())) {
                                            z = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    z = true;
                }
                if (!deleteCardInfoList.isEmpty()) {
                    return true;
                }
            } catch (Exception e) {
                LogUtil.warning(e);
            }
            return z;
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.internal.StateController$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$LaunchManagementActivity$DirectType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$CentralFunc$PrecompileListener$PrecompiledState$FelicaDeviceState;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$FelicaPocketFunc$CompiledFpState$FelicaFpState;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$ModelGateway$InitializedState$UpgradeType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateController$AppPhase;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateController$LaunchType;

        static {
            int[] iArr = new int[SupportServiceType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType = iArr;
            try {
                iArr[SupportServiceType.M1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.M2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.T1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.S1.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[FelicaPocketFunc.CompiledFpState.FelicaFpState.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$FelicaPocketFunc$CompiledFpState$FelicaFpState = iArr2;
            try {
                iArr2[FelicaPocketFunc.CompiledFpState.FelicaFpState.OTHER_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr3 = new int[LaunchType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateController$LaunchType = iArr3;
            try {
                iArr3[LaunchType.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateController$LaunchType[LaunchType.NOTIFICATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateController$LaunchType[LaunchType.EX_IC_CARD.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr4 = new int[MfcExpert.MfcStatus.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus = iArr4;
            try {
                iArr4[MfcExpert.MfcStatus.MFC_DISABLED.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus[MfcExpert.MfcStatus.MFC_UNINSTALLED.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcExpert$MfcStatus[MfcExpert.MfcStatus.MFC_ENABLED.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr5 = new int[CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$CentralFunc$PrecompileListener$PrecompiledState$FelicaDeviceState = iArr5;
            try {
                iArr5[CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.UNINITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$CentralFunc$PrecompileListener$PrecompiledState$FelicaDeviceState[CentralFunc.PrecompileListener.PrecompiledState.FelicaDeviceState.INITIALIZED.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            int[] iArr6 = new int[ModelErrorInfo.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type = iArr6;
            try {
                iArr6[ModelErrorInfo.Type.DB_ACCESS_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.NONSUPPORT_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.PERM_INSPECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFC_OTHER_ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.USED_BY_OTHER_APP.ordinal()] = 6;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF.ordinal()] = 7;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.LOCKED_FELICA.ordinal()] = 8;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.FELICA_OPEN_ERROR.ordinal()] = 9;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.FELICA_INVALID_RESPONSE_ERROR.ordinal()] = 10;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.FELICA_HTTP_ERROR.ordinal()] = 11;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_VERSION_ERROR.ordinal()] = 12;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.OTHER_ERROR.ordinal()] = 13;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.OTHER_RUNTIME_ERROR.ordinal()] = 14;
            } catch (NoSuchFieldError unused27) {
            }
            int[] iArr7 = new int[ModelGateway.InitializedState.UpgradeType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$ModelGateway$InitializedState$UpgradeType = iArr7;
            try {
                iArr7[ModelGateway.InitializedState.UpgradeType.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$ModelGateway$InitializedState$UpgradeType[ModelGateway.InitializedState.UpgradeType.OPTIONAL_UPGRADE.ordinal()] = 2;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$ModelGateway$InitializedState$UpgradeType[ModelGateway.InitializedState.UpgradeType.REQUIRED_UPGRADE.ordinal()] = 3;
            } catch (NoSuchFieldError unused30) {
            }
            int[] iArr8 = new int[AppPhase.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateController$AppPhase = iArr8;
            try {
                iArr8[AppPhase.STOPPED.ordinal()] = 1;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateController$AppPhase[AppPhase.BACK_GROUND.ordinal()] = 2;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateController$AppPhase[AppPhase.FOR_GROUND.ordinal()] = 3;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$StateController$AppPhase[AppPhase.START.ordinal()] = 4;
            } catch (NoSuchFieldError unused34) {
            }
            int[] iArr9 = new int[LaunchManagementActivity.DirectType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$LaunchManagementActivity$DirectType = iArr9;
            try {
                iArr9[LaunchManagementActivity.DirectType.CARD_DETAIL.ordinal()] = 1;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$LaunchManagementActivity$DirectType[LaunchManagementActivity.DirectType.UNKNOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused36) {
            }
        }
    }

    public static class ModelDataUpdateManager {
        private static final int NON_UPDATE_SPECIFIC_TIME = 3600000;
        boolean isDirect = false;
        long nowTimeMills;

        public void init() {
            this.nowTimeMills = System.currentTimeMillis();
            MficStatusChangeReceiver.resetUpdate();
        }

        public void setDirect(boolean z) {
            this.isDirect = z;
        }

        public boolean isUpdate() {
            boolean z = true;
            if (!this.isDirect && !MficStatusChangeReceiver.isUpdate() && this.nowTimeMills + 3600000 > System.currentTimeMillis()) {
                z = false;
            }
            this.isDirect = false;
            return z;
        }
    }
}

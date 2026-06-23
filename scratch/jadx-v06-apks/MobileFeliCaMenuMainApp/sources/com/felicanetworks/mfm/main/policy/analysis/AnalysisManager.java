package com.felicanetworks.mfm.main.policy.analysis;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.MfiAccountSwitchingActivity;
import com.felicanetworks.mfm.main.ServiceListActivity;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class AnalysisManager {
    private static final String USER_ID_UNKNOWN = "UNKNOWN";
    private static Context _context;
    private static LifecycleTemporary _temporary;
    private static String _userId;
    private static Activity activeRootActivity;
    private static String dynamicLaunchId;
    private static String dynamicLaunchKind;
    private static ServiceListActivity mainRootActivity;
    private static String staticLaunchId;
    private static String staticLaunchKind;
    private static MfiAccountSwitchingActivity subRootActivity;
    private static String switchAccountLaunchId;
    private static HandlerThread analysisHandlerThread = new HandlerThread("analysis_thread") { // from class: com.felicanetworks.mfm.main.policy.analysis.AnalysisManager.1
        {
            start();
        }
    };
    private static int EVENTCOUNT_INIT_VALUE = 0;
    private static int _eventCount = 0;
    private static int EVENT_AMOUNT = 70;
    private static boolean _policy_consent = false;

    /* JADX INFO: Access modifiers changed from: private */
    public static void error(Exception exc) {
    }

    static /* synthetic */ int access$1108() {
        int i = _eventCount;
        _eventCount = i + 1;
        return i;
    }

    private enum AnalysisEnableState {
        UNKNOWN { // from class: com.felicanetworks.mfm.main.policy.analysis.AnalysisManager.AnalysisEnableState.1
            @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisManager.AnalysisEnableState
            int getInt() {
                return 0;
            }
        },
        ENABLE { // from class: com.felicanetworks.mfm.main.policy.analysis.AnalysisManager.AnalysisEnableState.2
            @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisManager.AnalysisEnableState
            int getInt() {
                return 1;
            }
        },
        DISABLE { // from class: com.felicanetworks.mfm.main.policy.analysis.AnalysisManager.AnalysisEnableState.3
            @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisManager.AnalysisEnableState
            int getInt() {
                return 2;
            }
        };

        int getInt() {
            return 0;
        }

        static AnalysisEnableState getEnum(int i) {
            if (ENABLE.getInt() == i) {
                return ENABLE;
            }
            if (DISABLE.getInt() == i) {
                return DISABLE;
            }
            return UNKNOWN;
        }
    }

    public static void setEnabled(boolean z) {
        Storage.saveAnalysisEnableState(z ? AnalysisEnableState.ENABLE : AnalysisEnableState.DISABLE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AnalysisEnableState getEnableState() {
        return Storage.getAnalysisEnableState();
    }

    public static void sendLogs() {
        try {
            AlbController.getBridgeController().sendLogs();
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampCreate(Activity activity) {
        try {
            setContext(activity);
            if (isRootTargetActivity(activity)) {
                if (activity instanceof ServiceListActivity) {
                    mainRootActivity = (ServiceListActivity) activity;
                }
                if ((activity instanceof MfiAccountSwitchingActivity) && (activity.getCallingPackage() == null || !activity.getCallingPackage().equals(_context.getPackageName()))) {
                    switchAccountLaunchId = UUID.randomUUID().toString();
                    subRootActivity = (MfiAccountSwitchingActivity) activity;
                }
                if (activeRootActivity instanceof MfiAccountSwitchingActivity) {
                    if (activeRootActivity.isFinishing()) {
                        if (mainRootActivity == null) {
                            LifecycleTemporary lifecycleTemporary = new LifecycleTemporary(activity.getApplicationContext());
                            _temporary = lifecycleTemporary;
                            lifecycleTemporary.createLaunchId();
                            activeRootActivity = activity;
                        }
                    } else if (activity instanceof ServiceListActivity) {
                        _temporary.createLaunchId();
                    }
                } else if ((activeRootActivity == null || activeRootActivity.isFinishing() || activeRootActivity.getClass() == activity.getClass()) && (((activity instanceof ServiceListActivity) && !activity.isFinishing()) || ((activity instanceof MfiAccountSwitchingActivity) && ((MfiAccountSwitchingActivity) activity).isSingleActivation()))) {
                    LifecycleTemporary lifecycleTemporary2 = new LifecycleTemporary(activity.getApplicationContext());
                    _temporary = lifecycleTemporary2;
                    lifecycleTemporary2.createLaunchId();
                    activeRootActivity = activity;
                }
            }
            _temporary.setCurrentActivity(activity);
        } catch (Exception e) {
            error(e);
        }
    }

    private static boolean isRootTargetActivity(Activity activity) {
        return (activity instanceof ServiceListActivity) || (activity instanceof MfiAccountSwitchingActivity);
    }

    public static void stampResume(Activity activity) {
        try {
            setContext(activity);
            _temporary.setCurrentActivity(activity);
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampPause(Activity activity) {
        try {
            setContext(activity);
            AlbController.getBridgeController().sendLogs();
            if (mainRootActivity == activity && activity.isFinishing()) {
                mainRootActivity = null;
            }
            if (subRootActivity == activity && activity.isFinishing()) {
                subRootActivity = null;
            }
            if (activeRootActivity == activity && activity.isFinishing()) {
                if (activeRootActivity instanceof ServiceListActivity) {
                    if (subRootActivity != null) {
                        activeRootActivity = subRootActivity;
                        return;
                    } else {
                        _temporary = null;
                        activeRootActivity = null;
                        return;
                    }
                }
                if (activeRootActivity instanceof MfiAccountSwitchingActivity) {
                    if (mainRootActivity != null) {
                        activeRootActivity = mainRootActivity;
                        return;
                    } else {
                        _temporary = null;
                        activeRootActivity = null;
                        return;
                    }
                }
                _temporary = null;
                activeRootActivity = null;
            }
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampStart(Activity activity) {
        try {
            setContext(activity);
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampStop(Activity activity) {
        try {
            setContext(activity);
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampDestroy(Activity activity) {
        try {
            setContext(activity);
            if (mainRootActivity == activity) {
                mainRootActivity = null;
            }
            if (subRootActivity == activity) {
                subRootActivity = null;
            }
            if (activeRootActivity == activity) {
                if (activeRootActivity instanceof ServiceListActivity) {
                    if (subRootActivity != null) {
                        activeRootActivity = subRootActivity;
                        return;
                    } else {
                        _temporary = null;
                        activeRootActivity = null;
                        return;
                    }
                }
                if (activeRootActivity instanceof MfiAccountSwitchingActivity) {
                    if (mainRootActivity != null) {
                        activeRootActivity = mainRootActivity;
                        return;
                    } else {
                        _temporary = null;
                        activeRootActivity = null;
                        return;
                    }
                }
                _temporary = null;
                activeRootActivity = null;
            }
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampReceive(Context context) {
        try {
            setContext(context);
            if (_temporary == null) {
                _temporary = new LifecycleTemporary(context);
            }
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampFinish() {
        if (activeRootActivity instanceof ServiceListActivity) {
            staticLaunchId = null;
            dynamicLaunchId = null;
            mainRootActivity = null;
        }
    }

    public static synchronized void stamp(MfmStamp.Event event, Object... objArr) {
        try {
            asyncStamp(event, objArr);
        } catch (Exception e) {
            error(e);
        }
    }

    private static class HandlerSendMessageData {
        private Map<String, String> data;
        private MfmStamp.Event event;
        private String name;
        private MfmStamp.Event.Type type;

        HandlerSendMessageData(MfmStamp.Event event, String str, MfmStamp.Event.Type type, Map<String, String> map) {
            this.event = event;
            this.name = str;
            this.type = type;
            this.data = map;
        }
    }

    private static void asyncStamp(MfmStamp.Event event, Object... objArr) throws Exception {
        event.setup(_temporary.m9clone());
        event.setData(objArr);
        if (event.isTypeScreenMain()) {
            _temporary.setCurrentMainScreenId(event.event());
        }
        event.target();
        String eventName = event.getEventName();
        HashMap map = new HashMap();
        for (MfmStamp.Data data : event.getDataList()) {
            map.put(data.getKey(), data.getValue() != null ? data.getValue() : null);
        }
        MfmStamp.Event.Type type = event.type();
        if (event == MfmStamp.Event.MFS_MOVEMENT || event == MfmStamp.Event.APP_MOVEMENT) {
            type = event.getMfmType();
        }
        Handler handler = new Handler(analysisHandlerThread.getLooper()) { // from class: com.felicanetworks.mfm.main.policy.analysis.AnalysisManager.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                try {
                    super.handleMessage(message);
                    HandlerSendMessageData handlerSendMessageData = (HandlerSendMessageData) message.obj;
                    MfmStamp.Event event2 = handlerSendMessageData.event;
                    String str = handlerSendMessageData.name;
                    MfmStamp.Event.Type type2 = handlerSendMessageData.type;
                    Map<String, String> map2 = handlerSendMessageData.data;
                    if (AnalysisManager._userId == null || AnalysisManager.USER_ID_UNKNOWN.equals(AnalysisManager._userId)) {
                        try {
                            String unused = AnalysisManager._userId = AdvertisingIdClient.getAdvertisingIdInfo(AnalysisManager._context).getId();
                        } catch (Exception unused2) {
                            String unused3 = AnalysisManager._userId = AnalysisManager.USER_ID_UNKNOWN;
                        }
                    }
                    MfmStamp.Data data2 = new MfmStamp.Data(MfmStamp.Data.Global.USER_ID, AnalysisManager._userId);
                    map2.put(data2.getKey(), data2.getValue());
                    switch (AnonymousClass3.$SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[type2.ordinal()]) {
                        case 1:
                            AlbController.getBridgeController().stampUserAction(str, map2);
                            break;
                        case 2:
                        case 3:
                            AlbController.getBridgeController().stampAutoDump(str, map2);
                            break;
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            AlbController.getBridgeController().stampScreen(str, map2);
                            break;
                    }
                    AnalysisManager.postStamp(event2);
                } catch (Exception e) {
                    AnalysisManager.error(e);
                }
            }
        };
        HandlerSendMessageData handlerSendMessageData = new HandlerSendMessageData(event, eventName, type, map);
        Message messageObtain = Message.obtain();
        messageObtain.obj = handlerSendMessageData;
        handler.sendMessage(messageObtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void postStamp(MfmStamp.Event event) throws Exception {
        if (event.immediate()) {
            AlbController.getBridgeController().sendLogs();
        }
    }

    private static void setContext(Context context) {
        if (_context == null) {
            _context = context.getApplicationContext();
        }
    }

    private static class AlbController implements AnalysisLibBridgeInterface {
        private static AlbController _bridgeController;
        private AnalysisLibBridgeInterface _bridge = new Alb4Amazon();

        private AlbController() {
        }

        public static synchronized AlbController getBridgeController() throws Exception {
            if (_bridgeController == null) {
                AlbController albController = new AlbController();
                _bridgeController = albController;
                albController.initialize(AnalysisManager._context);
            }
            return _bridgeController;
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
        public void initialize(Context context) throws Exception {
            int i = AnonymousClass3.$SwitchMap$com$felicanetworks$mfm$main$policy$analysis$AnalysisManager$AnalysisEnableState[AnalysisManager.getEnableState().ordinal()];
            if (i == 1 || i == 2) {
                this._bridge.initialize(context);
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
        public void sendLogs() throws Exception {
            if (AnalysisManager.getPolicyConsent() && AnonymousClass3.$SwitchMap$com$felicanetworks$mfm$main$policy$analysis$AnalysisManager$AnalysisEnableState[AnalysisManager.getEnableState().ordinal()] == 1) {
                this._bridge.sendLogs();
                int unused = AnalysisManager._eventCount = AnalysisManager.EVENTCOUNT_INIT_VALUE;
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
        public void stampUserAction(String str, Map<String, String> map) throws Exception {
            int i = AnonymousClass3.$SwitchMap$com$felicanetworks$mfm$main$policy$analysis$AnalysisManager$AnalysisEnableState[AnalysisManager.getEnableState().ordinal()];
            if (i == 1 || i == 2) {
                this._bridge.stampUserAction(str, map);
                AnalysisManager.access$1108();
                checkEventAmount();
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
        public void stampAutoDump(String str, Map<String, String> map) throws Exception {
            int i = AnonymousClass3.$SwitchMap$com$felicanetworks$mfm$main$policy$analysis$AnalysisManager$AnalysisEnableState[AnalysisManager.getEnableState().ordinal()];
            if (i == 1 || i == 2) {
                this._bridge.stampAutoDump(str, map);
                AnalysisManager.access$1108();
                checkEventAmount();
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
        public void stampScreen(String str, Map<String, String> map) throws Exception {
            int i = AnonymousClass3.$SwitchMap$com$felicanetworks$mfm$main$policy$analysis$AnalysisManager$AnalysisEnableState[AnalysisManager.getEnableState().ordinal()];
            if (i == 1 || i == 2) {
                this._bridge.stampScreen(str, map);
                AnalysisManager.access$1108();
                checkEventAmount();
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
        public int getMaxDataSize() {
            return this._bridge.getMaxDataSize();
        }

        private void checkEventAmount() {
            try {
                if (AnalysisManager._eventCount >= AnalysisManager.EVENT_AMOUNT) {
                    sendLogs();
                }
            } catch (Exception e) {
                AnalysisManager.error(e);
            }
        }
    }

    private static class Storage {
        private static final String STORAGE_FILE_NAME = "MfmAnalysisStorage";
        private static final String STORAGE_KEY_ANALYSIS_ENABLE_STATE = "LogEnable";
        private static final String STORAGE_KEY_FIRST_LAUNCH_FLAG = "FirstLaunchFlag";
        private static final String STORAGE_KEY_POLICY_CONSENT = "PolicyConsent";
        private static Storage _storage;
        private SharedPreferences _sp;

        private static Storage getStrage() {
            if (_storage == null && AnalysisManager._context != null) {
                _storage = new Storage(AnalysisManager._context);
            }
            return _storage;
        }

        private Storage(Context context) {
            this._sp = context.getSharedPreferences(STORAGE_FILE_NAME, 0);
        }

        private void saveInt(String str, int i) {
            this._sp.edit().putInt(str, i).apply();
        }

        private int loadInt(String str) {
            return this._sp.getInt(str, 0);
        }

        public static void saveAnalysisEnableState(AnalysisEnableState analysisEnableState) {
            try {
                getStrage().saveInt(STORAGE_KEY_ANALYSIS_ENABLE_STATE, analysisEnableState.getInt());
            } catch (Exception e) {
                AnalysisManager.error(e);
            }
        }

        public static AnalysisEnableState getAnalysisEnableState() {
            return AnalysisEnableState.getEnum(getStrage().loadInt(STORAGE_KEY_ANALYSIS_ENABLE_STATE));
        }

        public static int getFirstLaunchFlag() {
            int iLoadInt = getStrage().loadInt(STORAGE_KEY_FIRST_LAUNCH_FLAG);
            if (1 != iLoadInt) {
                getStrage().saveInt(STORAGE_KEY_FIRST_LAUNCH_FLAG, 1);
            }
            return iLoadInt;
        }

        public static void savePolicyConsent(boolean z) {
            try {
                getStrage().saveInt(STORAGE_KEY_POLICY_CONSENT, z ? 1 : 0);
            } catch (Exception e) {
                AnalysisManager.error(e);
            }
        }

        public static boolean getPolicyConsent() {
            try {
                return getStrage().loadInt(STORAGE_KEY_POLICY_CONSENT) == 1;
            } catch (Exception unused) {
                return false;
            }
        }
    }

    private static class LifecycleTemporary implements MfmStamp.RequestDataInterface, Cloneable {
        private Context _context;
        private String _currentActivity;
        private HashMap<String, String> _mainScreenIdHolder = new HashMap<>();

        LifecycleTemporary(Context context) {
            this._context = context;
            String unused = AnalysisManager.dynamicLaunchId = null;
            String unused2 = AnalysisManager.dynamicLaunchKind = null;
        }

        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public LifecycleTemporary m9clone() {
            LifecycleTemporary lifecycleTemporary;
            try {
                lifecycleTemporary = (LifecycleTemporary) super.clone();
                try {
                    lifecycleTemporary._mainScreenIdHolder = (HashMap) this._mainScreenIdHolder.clone();
                } catch (CloneNotSupportedException e) {
                    e = e;
                    AnalysisManager.error(e);
                }
            } catch (CloneNotSupportedException e2) {
                e = e2;
                lifecycleTemporary = this;
            }
            return lifecycleTemporary;
        }

        public void createLaunchId() {
            String unused = AnalysisManager.dynamicLaunchId = UUID.randomUUID().toString();
            if (TextUtils.isEmpty(AnalysisManager.staticLaunchId)) {
                String unused2 = AnalysisManager.staticLaunchId = AnalysisManager.dynamicLaunchId;
            }
        }

        public void setCurrentMainScreenId(String str) {
            this._mainScreenIdHolder.put(this._currentActivity, str);
        }

        public void setCurrentActivity(Activity activity) {
            this._currentActivity = activity.getLocalClassName();
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.RequestDataInterface
        public void setLaunchUnits(String str) {
            if (((str.hashCode() == -934938715 && str.equals("reboot")) ? (byte) 0 : (byte) -1) != 0) {
                String unused = AnalysisManager.staticLaunchId = AnalysisManager.dynamicLaunchId;
                String unused2 = AnalysisManager.dynamicLaunchKind = str;
                String unused3 = AnalysisManager.staticLaunchKind = AnalysisManager.dynamicLaunchKind;
            } else {
                String unused4 = AnalysisManager.dynamicLaunchId = AnalysisManager.staticLaunchId;
                String unused5 = AnalysisManager.dynamicLaunchKind = AnalysisManager.staticLaunchKind;
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.RequestDataInterface
        public String getStringData(MfmStamp.RequestDataInterface.Type4String type4String) {
            switch (AnonymousClass3.$SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4String[type4String.ordinal()]) {
                case 1:
                    return AnalysisManager._userId;
                case 2:
                    return AnalysisManager.dynamicLaunchId;
                case 3:
                    return AnalysisManager.switchAccountLaunchId;
                case 4:
                    return this._mainScreenIdHolder.get(this._currentActivity);
                case 5:
                    return Settings.idm();
                case 6:
                    return Settings.icCode();
                case 7:
                    return NoticeDataManager.getInstance(this._context).getPushReceiveStatus();
                case 8:
                    return AnalysisManager.dynamicLaunchKind;
                default:
                    return "";
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.RequestDataInterface
        public int getIntData(MfmStamp.RequestDataInterface.Type4Int type4Int) {
            int i = AnonymousClass3.$SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4Int[type4Int.ordinal()];
            if (i == 1) {
                return Storage.getFirstLaunchFlag();
            }
            if (i == 2) {
                try {
                    return AlbController.getBridgeController().getMaxDataSize();
                } catch (Exception e) {
                    AnalysisManager.error(e);
                }
            }
            return -1;
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.policy.analysis.AnalysisManager$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$AnalysisManager$AnalysisEnableState;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4Int;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4String;

        static {
            int[] iArr = new int[MfmStamp.RequestDataInterface.Type4Int.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4Int = iArr;
            try {
                iArr[MfmStamp.RequestDataInterface.Type4Int.LAUNCH_COUNT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4Int[MfmStamp.RequestDataInterface.Type4Int.MAX_DATA_SIZE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[MfmStamp.RequestDataInterface.Type4String.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4String = iArr2;
            try {
                iArr2[MfmStamp.RequestDataInterface.Type4String.USER_ID.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4String[MfmStamp.RequestDataInterface.Type4String.LAUNCH_ID.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4String[MfmStamp.RequestDataInterface.Type4String.LAUNCH_ID_SWITCH_ACCOUNT.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4String[MfmStamp.RequestDataInterface.Type4String.MAIN_SCREEN_ID.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4String[MfmStamp.RequestDataInterface.Type4String.MFM_IDM.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4String[MfmStamp.RequestDataInterface.Type4String.MFM_IC_CODE.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4String[MfmStamp.RequestDataInterface.Type4String.PUSH_STATUS.ordinal()] = 7;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4String[MfmStamp.RequestDataInterface.Type4String.LAUNCH_KIND.ordinal()] = 8;
            } catch (NoSuchFieldError unused10) {
            }
            int[] iArr3 = new int[AnalysisEnableState.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$AnalysisManager$AnalysisEnableState = iArr3;
            try {
                iArr3[AnalysisEnableState.ENABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$AnalysisManager$AnalysisEnableState[AnalysisEnableState.UNKNOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            int[] iArr4 = new int[MfmStamp.Event.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type = iArr4;
            try {
                iArr4[MfmStamp.Event.Type.USER_ACTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.AUTO_DUMP.ordinal()] = 2;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.RECEIVER_DUMP.ordinal()] = 3;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.SCREEN_MAIN.ordinal()] = 4;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.SCREEN_SUB.ordinal()] = 5;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.SCREEN_ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.SCREEN_FATAL.ordinal()] = 7;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.SCREEN_WARNING.ordinal()] = 8;
            } catch (NoSuchFieldError unused20) {
            }
        }
    }

    public static void setPolicyConsent(boolean z) {
        Storage.savePolicyConsent(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean getPolicyConsent() {
        return Storage.getPolicyConsent();
    }
}

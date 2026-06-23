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

/* JADX INFO: loaded from: classes3.dex */
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
    public static void error(Exception e) {
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
            AnalysisEnableState analysisEnableState = ENABLE;
            if (analysisEnableState.getInt() == i) {
                return analysisEnableState;
            }
            AnalysisEnableState analysisEnableState2 = DISABLE;
            return analysisEnableState2.getInt() == i ? analysisEnableState2 : UNKNOWN;
        }
    }

    public static void setEnabled(boolean enabled) {
        Storage.saveAnalysisEnableState(enabled ? AnalysisEnableState.ENABLE : AnalysisEnableState.DISABLE);
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

    public static void stampCreate(Activity a) {
        try {
            setContext(a);
            if (isRootTargetActivity(a)) {
                if (a instanceof ServiceListActivity) {
                    mainRootActivity = (ServiceListActivity) a;
                }
                if ((a instanceof MfiAccountSwitchingActivity) && (a.getCallingPackage() == null || !a.getCallingPackage().equals(_context.getPackageName()))) {
                    switchAccountLaunchId = UUID.randomUUID().toString();
                    subRootActivity = (MfiAccountSwitchingActivity) a;
                }
                Activity activity = activeRootActivity;
                if (activity instanceof MfiAccountSwitchingActivity) {
                    if (activity.isFinishing()) {
                        if (mainRootActivity == null) {
                            LifecycleTemporary lifecycleTemporary = new LifecycleTemporary(a.getApplicationContext());
                            _temporary = lifecycleTemporary;
                            lifecycleTemporary.createLaunchId();
                            activeRootActivity = a;
                        }
                    } else if (a instanceof ServiceListActivity) {
                        _temporary.createLaunchId();
                    }
                } else if ((activity == null || activity.isFinishing() || activeRootActivity.getClass() == a.getClass()) && (((a instanceof ServiceListActivity) && !a.isFinishing()) || ((a instanceof MfiAccountSwitchingActivity) && ((MfiAccountSwitchingActivity) a).isSingleActivation()))) {
                    LifecycleTemporary lifecycleTemporary2 = new LifecycleTemporary(a.getApplicationContext());
                    _temporary = lifecycleTemporary2;
                    lifecycleTemporary2.createLaunchId();
                    activeRootActivity = a;
                }
            }
            _temporary.setCurrentActivity(a);
        } catch (Exception e) {
            error(e);
        }
    }

    private static boolean isRootTargetActivity(Activity activity) {
        return (activity instanceof ServiceListActivity) || (activity instanceof MfiAccountSwitchingActivity);
    }

    public static void stampResume(Activity a) {
        try {
            setContext(a);
            _temporary.setCurrentActivity(a);
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampPause(Activity a) {
        try {
            setContext(a);
            AlbController.getBridgeController().sendLogs();
            if (mainRootActivity == a && a.isFinishing()) {
                mainRootActivity = null;
            }
            if (subRootActivity == a && a.isFinishing()) {
                subRootActivity = null;
            }
            if (activeRootActivity == a && a.isFinishing()) {
                Activity activity = activeRootActivity;
                if (activity instanceof ServiceListActivity) {
                    MfiAccountSwitchingActivity mfiAccountSwitchingActivity = subRootActivity;
                    if (mfiAccountSwitchingActivity != null) {
                        activeRootActivity = mfiAccountSwitchingActivity;
                        return;
                    } else {
                        _temporary = null;
                        activeRootActivity = null;
                        return;
                    }
                }
                if (activity instanceof MfiAccountSwitchingActivity) {
                    ServiceListActivity serviceListActivity = mainRootActivity;
                    if (serviceListActivity != null) {
                        activeRootActivity = serviceListActivity;
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

    public static void stampStart(Activity a) {
        try {
            setContext(a);
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampStop(Activity a) {
        try {
            setContext(a);
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampDestroy(Activity a) {
        try {
            setContext(a);
            if (mainRootActivity == a) {
                mainRootActivity = null;
            }
            if (subRootActivity == a) {
                subRootActivity = null;
            }
            Activity activity = activeRootActivity;
            if (activity == a) {
                if (activity instanceof ServiceListActivity) {
                    MfiAccountSwitchingActivity mfiAccountSwitchingActivity = subRootActivity;
                    if (mfiAccountSwitchingActivity != null) {
                        activeRootActivity = mfiAccountSwitchingActivity;
                        return;
                    } else {
                        _temporary = null;
                        activeRootActivity = null;
                        return;
                    }
                }
                if (activity instanceof MfiAccountSwitchingActivity) {
                    ServiceListActivity serviceListActivity = mainRootActivity;
                    if (serviceListActivity != null) {
                        activeRootActivity = serviceListActivity;
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

    public static void stampReceive(Context c) {
        try {
            setContext(c);
            if (_temporary == null) {
                _temporary = new LifecycleTemporary(c);
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

    public static synchronized void stamp(final MfmStamp.Event event, final Object... args) {
        try {
            asyncStamp(event, args);
        } catch (Exception e) {
            error(e);
        }
    }

    private static class HandlerSendMessageData {
        private Map<String, String> data;
        private MfmStamp.Event event;
        private String name;
        private MfmStamp.Event.Type type;

        HandlerSendMessageData(MfmStamp.Event event, String name, MfmStamp.Event.Type type, Map<String, String> data) {
            this.event = event;
            this.name = name;
            this.type = type;
            this.data = data;
        }
    }

    private static void asyncStamp(MfmStamp.Event event, final Object... args) throws Exception {
        event.setup(_temporary.m410clone());
        event.setData(args);
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
            public void handleMessage(Message msg) {
                try {
                    super.handleMessage(msg);
                    HandlerSendMessageData handlerSendMessageData = (HandlerSendMessageData) msg.obj;
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

    private static void setContext(Context c) {
        if (_context == null) {
            _context = c.getApplicationContext();
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
            int iOrdinal = AnalysisManager.getEnableState().ordinal();
            if (iOrdinal == 0 || iOrdinal == 1) {
                this._bridge.initialize(context);
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
        public void sendLogs() throws Exception {
            if (AnalysisManager.getPolicyConsent() && AnalysisManager.getEnableState().ordinal() == 1) {
                this._bridge.sendLogs();
                int unused = AnalysisManager._eventCount = AnalysisManager.EVENTCOUNT_INIT_VALUE;
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
        public void stampUserAction(String event, Map<String, String> data) throws Exception {
            int iOrdinal = AnalysisManager.getEnableState().ordinal();
            if (iOrdinal == 0 || iOrdinal == 1) {
                this._bridge.stampUserAction(event, data);
                AnalysisManager.access$1108();
                checkEventAmount();
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
        public void stampAutoDump(String event, Map<String, String> data) throws Exception {
            int iOrdinal = AnalysisManager.getEnableState().ordinal();
            if (iOrdinal == 0 || iOrdinal == 1) {
                this._bridge.stampAutoDump(event, data);
                AnalysisManager.access$1108();
                checkEventAmount();
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.AnalysisLibBridgeInterface
        public void stampScreen(String event, Map<String, String> data) throws Exception {
            int iOrdinal = AnalysisManager.getEnableState().ordinal();
            if (iOrdinal == 0 || iOrdinal == 1) {
                this._bridge.stampScreen(event, data);
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

        private Storage(Context c) {
            this._sp = c.getSharedPreferences(STORAGE_FILE_NAME, 0);
        }

        private void saveInt(String key, int value) {
            this._sp.edit().putInt(key, value).apply();
        }

        private int loadInt(String key) {
            return this._sp.getInt(key, 0);
        }

        public static void saveAnalysisEnableState(AnalysisEnableState state) {
            try {
                getStrage().saveInt(STORAGE_KEY_ANALYSIS_ENABLE_STATE, state.getInt());
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

        /* JADX DEBUG: Method merged with bridge method: clone()Ljava/lang/Object; */
        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public LifecycleTemporary m410clone() {
            LifecycleTemporary lifecycleTemporary;
            try {
                lifecycleTemporary = (LifecycleTemporary) super.clone();
                try {
                    lifecycleTemporary._mainScreenIdHolder = (HashMap) this._mainScreenIdHolder.clone();
                    return lifecycleTemporary;
                } catch (CloneNotSupportedException e) {
                    e = e;
                    AnalysisManager.error(e);
                    return lifecycleTemporary;
                }
            } catch (CloneNotSupportedException e2) {
                e = e2;
                lifecycleTemporary = this;
            }
        }

        public void createLaunchId() {
            String unused = AnalysisManager.dynamicLaunchId = UUID.randomUUID().toString();
            if (TextUtils.isEmpty(AnalysisManager.staticLaunchId)) {
                String unused2 = AnalysisManager.staticLaunchId = AnalysisManager.dynamicLaunchId;
            }
        }

        public void setCurrentMainScreenId(String screen) {
            this._mainScreenIdHolder.put(this._currentActivity, screen);
        }

        public void setCurrentActivity(Activity a) {
            this._currentActivity = a.getLocalClassName();
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.RequestDataInterface
        public void setLaunchUnits(String launchUnits) {
            launchUnits.hashCode();
            if (!launchUnits.equals("reboot")) {
                String unused = AnalysisManager.staticLaunchId = AnalysisManager.dynamicLaunchId;
                String unused2 = AnalysisManager.dynamicLaunchKind = launchUnits;
                String unused3 = AnalysisManager.staticLaunchKind = AnalysisManager.dynamicLaunchKind;
            } else {
                String unused4 = AnalysisManager.dynamicLaunchId = AnalysisManager.staticLaunchId;
                String unused5 = AnalysisManager.dynamicLaunchKind = AnalysisManager.staticLaunchKind;
            }
        }

        @Override // com.felicanetworks.mfm.main.policy.analysis.MfmStamp.RequestDataInterface
        public String getStringData(MfmStamp.RequestDataInterface.Type4String type) {
            switch (AnonymousClass3.$SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4String[type.ordinal()]) {
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
        public int getIntData(MfmStamp.RequestDataInterface.Type4Int type) {
            int i = AnonymousClass3.$SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$RequestDataInterface$Type4Int[type.ordinal()];
            if (i == 1) {
                return Storage.getFirstLaunchFlag();
            }
            if (i != 2) {
                return -1;
            }
            try {
                return AlbController.getBridgeController().getMaxDataSize();
            } catch (Exception e) {
                AnalysisManager.error(e);
                return -1;
            }
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.policy.analysis.AnalysisManager$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
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
            int[] iArr3 = new int[MfmStamp.Event.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type = iArr3;
            try {
                iArr3[MfmStamp.Event.Type.USER_ACTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.AUTO_DUMP.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.RECEIVER_DUMP.ordinal()] = 3;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.SCREEN_MAIN.ordinal()] = 4;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.SCREEN_SUB.ordinal()] = 5;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.SCREEN_ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.SCREEN_FATAL.ordinal()] = 7;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$analysis$MfmStamp$Event$Type[MfmStamp.Event.Type.SCREEN_WARNING.ordinal()] = 8;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    public static void setPolicyConsent(boolean policy) {
        Storage.savePolicyConsent(policy);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean getPolicyConsent() {
        return Storage.getPolicyConsent();
    }
}

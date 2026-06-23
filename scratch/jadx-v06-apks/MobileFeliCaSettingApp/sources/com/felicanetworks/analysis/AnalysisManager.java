package com.felicanetworks.analysis;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.felicanetworks.analysis.MfsStamp;
import com.felicanetworks.common.cmnlib.sg.SgMgr;
import com.felicanetworks.mfs.MobileFeliCaSettings;
import com.felicanetworks.mfslib.MfsAppContext;
import com.felicanetworks.mfslib.log.MfsLogMgr;
import com.felicanetworks.mfslib.sg.MfsSgMgr;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class AnalysisManager {
    private static Context _context;
    private static LifecycleTemporary _temporary;
    private static Activity mfsActivity;

    /* JADX INFO: Access modifiers changed from: private */
    public static void error(Exception exc) {
    }

    public static void stampCreate(Activity activity) {
        try {
            setContext(activity);
            if (mfsActivity == null || mfsActivity.isFinishing()) {
                _temporary = new LifecycleTemporary();
                mfsActivity = activity;
            }
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampRestart(Activity activity) {
        try {
            setContext(activity);
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampPause(Activity activity) {
        try {
            setContext(activity);
            if (mfsActivity == activity && activity.isFinishing()) {
                _temporary = null;
                mfsActivity = null;
            }
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stampDestroy(Activity activity) {
        try {
            setContext(activity);
            if (mfsActivity == activity) {
                _temporary = null;
                mfsActivity = null;
            }
        } catch (Exception e) {
            error(e);
        }
    }

    public static void stamp(MfsStamp.Event event, Object... objArr) {
        try {
            event.setup(_temporary);
            if (objArr != null) {
                event.setData(objArr);
            }
            int i = AnonymousClass1.$SwitchMap$com$felicanetworks$analysis$MfsStamp$Event$Type[event.type().ordinal()];
            if (i == 1) {
                _temporary.setAutoDumpEventData(event);
            } else if (i == 2) {
                _temporary.setCurrentMainScreenId(event.event());
            }
            event.target();
            Intent intent = event.intent();
            intent.setComponent(_temporary.getReceivedComponentName());
            intent.setFlags(32);
            _context.sendBroadcast(intent);
        } catch (Exception e) {
            error(e);
        }
    }

    private static void setContext(Context context) {
        if (_context == null) {
            _context = context.getApplicationContext();
        }
    }

    private static class LifecycleTemporary implements MfsStamp.RequestDataInterface {
        private String _icCode;
        private String _idm;
        private String _issueCode;
        private String _launchAppId;
        private String _launchAppPkg;
        private String _launchId = UUID.randomUUID().toString();
        private String _launchParamCmd;
        private String _mainScreenId;
        private MfsSgMgr _sg;

        public LifecycleTemporary() {
            MfsAppContext mfsAppContext = new MfsAppContext();
            mfsAppContext.androidContext = AnalysisManager._context;
            try {
                MfsSgMgr mfsSgMgr = new MfsSgMgr(mfsAppContext);
                this._sg = mfsSgMgr;
                mfsSgMgr.loadSg();
                mfsAppContext.sgMgr = this._sg;
                mfsAppContext.logMgr = new MfsLogMgr(mfsAppContext);
                this._issueCode = (String) this._sg.getSgValue(SgMgr.KEY_MFC_ISSUER_CODE);
            } catch (Exception e) {
                AnalysisManager.error(e);
            }
        }

        public void setCurrentMainScreenId(String str) {
            this._mainScreenId = str;
        }

        public void setAutoDumpEventData(MfsStamp.Event event) {
            for (MfsStamp.Data data : event._datalist) {
                if (data.getKey().endsWith(MfsStamp.Data.Type.IDM.key())) {
                    this._idm = data.getValue();
                } else if (data.getKey().endsWith(MfsStamp.Data.Type.IC_CODE.key())) {
                    this._icCode = data.getValue();
                } else if (data.getKey().endsWith(MfsStamp.Data.Type.LAUNCH_APP_ID.key())) {
                    this._launchAppId = data.getValue();
                } else if (data.getKey().endsWith(MfsStamp.Data.Type.LAUNCH_CMD.key())) {
                    this._launchParamCmd = data.getValue();
                } else if (data.getKey().endsWith(MfsStamp.Data.Type.LAUNCH_APP_PKG.key())) {
                    this._launchAppPkg = data.getValue();
                }
            }
        }

        public ComponentName getReceivedComponentName() {
            return new ComponentName(this._sg.getMfsMovementReceiverPkgName(), this._sg.getMfsMovementReceiverClassName());
        }

        @Override // com.felicanetworks.analysis.MfsStamp.RequestDataInterface
        public String getStringData(MfsStamp.RequestDataInterface.Type4String type4String) {
            switch (AnonymousClass1.$SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4String[type4String.ordinal()]) {
                case 1:
                    return this._launchId;
                case 2:
                    return this._mainScreenId;
                case 3:
                    return this._idm;
                case 4:
                    return this._icCode;
                case 5:
                    return this._sg.getAppId();
                case 6:
                    return this._launchAppId;
                case 7:
                    return this._launchParamCmd;
                case 8:
                    return this._launchAppPkg;
                case 9:
                    return this._issueCode;
                default:
                    return "";
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // com.felicanetworks.analysis.MfsStamp.RequestDataInterface
        public int getIntData(MfsStamp.RequestDataInterface.Type4Int type4Int) {
            switch (AnonymousClass1.$SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[type4Int.ordinal()]) {
                case 1:
                    try {
                        return Build.VERSION.SDK_INT >= 28 ? (int) AnalysisManager._context.getPackageManager().getPackageInfo(AnalysisManager._context.getPackageName(), 0).getLongVersionCode() : AnalysisManager._context.getPackageManager().getPackageInfo(AnalysisManager._context.getPackageName(), 0).versionCode;
                    } catch (Exception e) {
                        AnalysisManager.error(e);
                    }
                    break;
                case 2:
                    return 1;
                case 3:
                    return 2;
                case 4:
                    return 0;
                case 5:
                    return 102;
                case 6:
                    return 104;
                case 7:
                    return 105;
                case 8:
                    return 106;
                case 9:
                    return 107;
                case 10:
                    return 108;
                case 11:
                    return 109;
                case 12:
                    return MobileFeliCaSettings.Result.FAILED_MFICVERSION;
                case 13:
                    return MobileFeliCaSettings.Result.FAILED_MFCNOTFOUND;
                case 14:
                    return MobileFeliCaSettings.Result.FAILED_MFCDISABLE;
                case 15:
                    return MobileFeliCaSettings.Result.FAILED_MFSVERSION;
                case 16:
                    return MobileFeliCaSettings.Result.FAILED_NOTAPPLICABLEDEVICE;
                case 17:
                    return 110;
                default:
                    return -1;
            }
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.analysis.AnalysisManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$analysis$MfsStamp$Event$Type;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4String;

        static {
            int[] iArr = new int[MfsStamp.RequestDataInterface.Type4Int.values().length];
            $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int = iArr;
            try {
                iArr[MfsStamp.RequestDataInterface.Type4Int.MFS_VERSION_CODE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_ALREADYINIT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_CANCEL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_BADSTART.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FELICAINUSE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FELICALOCK.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_INIT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_COMMUNICATIONERROR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_OVERCROWDING.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_SERVERMAINTENANCE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_MFICVERSION.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_MFCNOTFOUND.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_MFCDISABLE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_MFSVERSION.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_NOTAPPLICABLEDEVICE.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4Int[MfsStamp.RequestDataInterface.Type4Int.RESULT_CODE_FAILED_FATALERROR.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            int[] iArr2 = new int[MfsStamp.RequestDataInterface.Type4String.values().length];
            $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4String = iArr2;
            try {
                iArr2[MfsStamp.RequestDataInterface.Type4String.LAUNCH_ID.ordinal()] = 1;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4String[MfsStamp.RequestDataInterface.Type4String.MAIN_SCREEN_ID.ordinal()] = 2;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4String[MfsStamp.RequestDataInterface.Type4String.MFS_IDM.ordinal()] = 3;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4String[MfsStamp.RequestDataInterface.Type4String.MFS_IC_CODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4String[MfsStamp.RequestDataInterface.Type4String.MFS_APP_ID.ordinal()] = 5;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4String[MfsStamp.RequestDataInterface.Type4String.LAUNCH_APP_ID.ordinal()] = 6;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4String[MfsStamp.RequestDataInterface.Type4String.LAUNCH_PARAM_CMD.ordinal()] = 7;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4String[MfsStamp.RequestDataInterface.Type4String.LAUNCH_APP_PKG.ordinal()] = 8;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$RequestDataInterface$Type4String[MfsStamp.RequestDataInterface.Type4String.ISSUE_CODE.ordinal()] = 9;
            } catch (NoSuchFieldError unused26) {
            }
            int[] iArr3 = new int[MfsStamp.Event.Type.values().length];
            $SwitchMap$com$felicanetworks$analysis$MfsStamp$Event$Type = iArr3;
            try {
                iArr3[MfsStamp.Event.Type.AUTO_DUMP.ordinal()] = 1;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$felicanetworks$analysis$MfsStamp$Event$Type[MfsStamp.Event.Type.SCREEN_MAIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused28) {
            }
        }
    }
}

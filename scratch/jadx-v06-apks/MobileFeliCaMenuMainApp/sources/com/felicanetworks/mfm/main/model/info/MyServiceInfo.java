package com.felicanetworks.mfm.main.model.info;

import android.text.TextUtils;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MyServiceInfo extends ServiceInfo {
    protected Map<DetectionType, DetectionResult> _detection;
    protected MfiServiceFlag _mfiServiceFlag;
    private Service _service;

    public enum DetectionResult {
        INVALID,
        DETECTION,
        NO_DETECTION
    }

    public enum DetectionType {
        APP,
        AREA,
        SAS,
        CARD
    }

    public enum MfiServiceFlag {
        UNKNOW_SERVICE,
        NOT_MFI_SERVICE,
        MFI_SERVICE
    }

    public enum RegisterState {
        NONE,
        NO_REGISTER,
        REGISTERED
    }

    public MyServiceInfo(Service service, Linkage linkage, Map<DetectionType, DetectionResult> map, DatabaseExpert databaseExpert) {
        super(service.getServiceId(), service.getVersion(), service.getServiceName(), service.getProvider(), linkage, databaseExpert);
        this._mfiServiceFlag = MfiServiceFlag.UNKNOW_SERVICE;
        this._detection = map;
        this._service = service;
    }

    public MyServiceInfo(MyServiceInfo myServiceInfo) {
        this(myServiceInfo._service, myServiceInfo._linkage, myServiceInfo._detection, myServiceInfo._db);
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "MyServiceInfo{_detection=" + this._detection + ", _service=" + this._service + ", super=" + super.toString() + '}';
    }

    public RegisterState getRegisterState() {
        DetectionResult detectionResult = this._detection.get(DetectionType.AREA);
        DetectionResult detectionResult2 = this._detection.get(DetectionType.SAS);
        DetectionResult detectionResult3 = this._detection.get(DetectionType.CARD);
        if (DetectionResult.DETECTION == detectionResult || DetectionResult.DETECTION == detectionResult2 || DetectionResult.DETECTION == detectionResult3) {
            return RegisterState.REGISTERED;
        }
        if (DetectionResult.INVALID == detectionResult && DetectionResult.INVALID == detectionResult2 && DetectionResult.INVALID == detectionResult3) {
            return RegisterState.NONE;
        }
        return RegisterState.NO_REGISTER;
    }

    public boolean isAreaDetect() {
        return DetectionResult.DETECTION == this._detection.get(DetectionType.AREA);
    }

    public boolean isSasDetect() {
        return DetectionResult.DETECTION == this._detection.get(DetectionType.SAS);
    }

    public boolean isOnlyAppDetect() {
        return this._detection.get(DetectionType.AREA) == DetectionResult.INVALID && this._detection.get(DetectionType.SAS) == DetectionResult.INVALID && this._detection.get(DetectionType.CARD) == DetectionResult.INVALID && this._detection.get(DetectionType.APP) == DetectionResult.DETECTION;
    }

    public String getServiceType() {
        return this._service.getServiceType();
    }

    public Service getService() {
        return this._service;
    }

    public boolean isMfiService() {
        return this._service.getServiceType().startsWith(Service.SERVICE_TYPE_1_MFI);
    }

    public boolean isType2() {
        return this._service.getServiceType().startsWith(Service.SERVICE_TYPE_2_FULL);
    }

    public MfiServiceFlag getMfiServiceFlag() {
        return this._mfiServiceFlag;
    }

    public RegisterState getAppInstalledState() {
        DetectionResult detectionResult = this._detection.get(DetectionType.APP);
        RegisterState registerState = RegisterState.NONE;
        if (detectionResult == null) {
            return registerState;
        }
        int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$info$MyServiceInfo$DetectionResult[detectionResult.ordinal()];
        if (i == 1) {
            return RegisterState.REGISTERED;
        }
        if (i != 2) {
            return i != 3 ? registerState : RegisterState.NONE;
        }
        return RegisterState.NO_REGISTER;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.info.MyServiceInfo$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$MyServiceInfo$DetectionResult;

        static {
            int[] iArr = new int[DetectionResult.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$MyServiceInfo$DetectionResult = iArr;
            try {
                iArr[DetectionResult.DETECTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$MyServiceInfo$DetectionResult[DetectionResult.NO_DETECTION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$MyServiceInfo$DetectionResult[DetectionResult.INVALID.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static Map<DetectionType, DetectionResult> makeDetectionResult(final DetectionResult detectionResult, final DetectionResult detectionResult2, final DetectionResult detectionResult3, final DetectionResult detectionResult4) {
        return new HashMap<DetectionType, DetectionResult>() { // from class: com.felicanetworks.mfm.main.model.info.MyServiceInfo.1
            {
                put(DetectionType.APP, detectionResult);
                put(DetectionType.AREA, detectionResult2);
                put(DetectionType.SAS, detectionResult3);
                put(DetectionType.CARD, detectionResult4);
            }
        };
    }

    public boolean isShowDetailIcon() {
        return TextUtils.equals("1", this._service.getDetailImagePresence());
    }
}

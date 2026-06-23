package com.felicanetworks.mfm.main.model.info;

import android.text.TextUtils;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
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

    public MyServiceInfo(Service service, Linkage linkage, Map<DetectionType, DetectionResult> detection, DatabaseExpert db) {
        super(service.getServiceId(), service.getVersion(), service.getServiceName(), service.getProvider(), linkage, db);
        this._mfiServiceFlag = MfiServiceFlag.UNKNOW_SERVICE;
        this._detection = detection;
        this._service = service;
    }

    public MyServiceInfo(MyServiceInfo service) {
        this(service._service, service._linkage, service._detection, service._db);
    }

    public MyServiceInfo(MyServiceInfo service, Linkage linkage) {
        this(service._service, linkage, service._detection, service._db);
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
        if (detectionResult != null) {
            int iOrdinal = detectionResult.ordinal();
            if (iOrdinal == 0) {
                return RegisterState.NONE;
            }
            if (iOrdinal == 1) {
                return RegisterState.REGISTERED;
            }
            if (iOrdinal == 2) {
                return RegisterState.NO_REGISTER;
            }
        }
        return registerState;
    }

    public static Map<DetectionType, DetectionResult> makeDetectionResult(final DetectionResult appResult, final DetectionResult areaResult, final DetectionResult sasResult, final DetectionResult cardResult) {
        return new HashMap<DetectionType, DetectionResult>(areaResult, sasResult, cardResult) { // from class: com.felicanetworks.mfm.main.model.info.MyServiceInfo.1
            final /* synthetic */ DetectionResult val$areaResult;
            final /* synthetic */ DetectionResult val$cardResult;
            final /* synthetic */ DetectionResult val$sasResult;

            {
                this.val$areaResult = areaResult;
                this.val$sasResult = sasResult;
                this.val$cardResult = cardResult;
                put(DetectionType.APP, this.val$appResult);
                put(DetectionType.AREA, areaResult);
                put(DetectionType.SAS, sasResult);
                put(DetectionType.CARD, cardResult);
            }
        };
    }

    public boolean isShowDetailIcon() {
        return TextUtils.equals("1", this._service.getDetailImagePresence());
    }
}

package com.felicanetworks.mfm.main.model;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.BannerInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.RecommendInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public interface CentralFunc extends FelicaPocketFunc {

    public interface CardFaceImageListener {
        void onGetImage(Bitmap bitmap);
    }

    public interface CompileListener {
        void onCompleted();

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface OrderAssetListener {
        void onFailure(ModelErrorInfo modelErrorInfo);

        void onSuccess();
    }

    public interface OrderBannerListener {
        void onSuccess(List<BannerInfo> list);
    }

    public interface OrderBannerOsaifuLifePlusListener {
        void onSuccess(Bitmap bitmap);
    }

    public interface ProgressListener {
        void onProgress(int i, int i2);
    }

    @Override // com.felicanetworks.mfm.main.model.FelicaPocketFunc
    void cancel();

    void cancelOrderBannerOsaifuLifePlus();

    void compile(boolean z, CompileListener compileListener);

    MfcExpert.MfcStatus confirmExistMfc(PackageManager packageManager);

    List<String> getAppDetectedServiceIdList();

    List<String> getAreaDetectedServiceIdList();

    void getCardFaceImage(String str, String str2, CardFaceImageListener cardFaceImageListener);

    CompiledState getCompiledState();

    List<MyServiceInfo> getDeleteServiceInfoList();

    Set<String> getDetectedServiceIdList(MfiCardFunc mfiCardFunc);

    List<MyServiceInfo> getMyServiceInfoList();

    List<RecommendInfo> getRecommendInfoList();

    List<String> getSasDetectedServiceIdList();

    Boolean isCompiled();

    void marshalModelData(MfiCardFunc mfiCardFunc);

    void mfcFinish();

    void orderAsset(MfiCardFunc mfiCardFunc);

    void orderBanner(OrderBannerListener orderBannerListener);

    void orderBannerOsaifuLifePlus(OrderBannerOsaifuLifePlusListener orderBannerOsaifuLifePlusListener);

    void precompile(PrecompileListener precompileListener);

    void reportRecommend(String str, String str2, String str3, String str4);

    void setCompileProgressListener(ProgressListener progressListener);

    public interface PrecompileListener {
        void onCompleted(PrecompiledState precompiledState);

        void onError(ModelErrorInfo modelErrorInfo);

        public static class PrecompiledState {
            private FelicaDeviceState _fds;

            public enum FelicaDeviceState {
                NOT_EQUIPPED,
                INITIALIZED,
                UNINITIALIZED
            }

            public String toString() {
                return "PrecompiledState{_fds=" + this._fds + '}';
            }

            public PrecompiledState(FelicaDeviceState felicaDeviceState) {
                this._fds = felicaDeviceState;
            }

            public FelicaDeviceState getFelicaDeviceState() {
                return this._fds;
            }
        }
    }

    public static class CompiledResult {
        public final List<String> areaHitIds = new ArrayList();
        public final List<String> appHitIds = new ArrayList();
        public final List<String> sasHitIds = new ArrayList();
        public final List<String> areaFamily = new ArrayList();
        public final List<String> appFamily = new ArrayList();
        public final List<String> sasFamily = new ArrayList();
        public final List<String> uiccFamily = new ArrayList();
        public final List<Service> master = new ArrayList();

        public CompiledResult(List<String> list, List<String> list2, List<String> list3, List<String> list4, List<String> list5, List<String> list6, List<String> list7, List<Service> list8) {
            this.areaHitIds.addAll(list);
            this.appHitIds.addAll(list2);
            this.sasHitIds.addAll(list3);
            this.areaFamily.addAll(list4);
            this.appFamily.addAll(list5);
            this.sasFamily.addAll(list6);
            this.uiccFamily.addAll(list7);
            this.master.addAll(list8);
        }

        public CompiledResult(CompiledResult compiledResult) {
            this.areaHitIds.addAll(compiledResult.areaHitIds);
            this.appHitIds.addAll(compiledResult.appHitIds);
            this.sasHitIds.addAll(compiledResult.sasHitIds);
            this.areaFamily.addAll(compiledResult.areaFamily);
            this.appFamily.addAll(compiledResult.appFamily);
            this.sasFamily.addAll(compiledResult.sasFamily);
            this.uiccFamily.addAll(compiledResult.uiccFamily);
            this.master.addAll(compiledResult.master);
        }

        public String toString() {
            return "CompiledResult{areaHitIds=" + this.areaHitIds + ", appHitIds=" + this.appHitIds + ", sasHitIds=" + this.sasHitIds + ", areaFamily=" + this.areaFamily + ", appFamily=" + this.appFamily + ", sasFamily=" + this.sasFamily + ", uiccFamily=" + this.uiccFamily + ", master=" + this.master + '}';
        }
    }

    public static class CompiledState {
        private FelicaState _felicaState;
        private NetworkState _networkState;
        private boolean _waringLackEmoneyInfo;
        private boolean _warningContainOldDispInfo;
        private boolean _warningLackDispInfo;

        public enum FelicaState {
            NO_ACCESS,
            NO_PROBLEM,
            LOCK_ERROR,
            OPEN_ERROR,
            TIMEOUT_ERROR,
            USED_BY_OTHER_APP,
            RUNNING_BY_MFIC
        }

        public enum NetworkState {
            NO_CONNECT,
            NO_PROBLEM,
            CONNECT_ERROR
        }

        public String toString() {
            return "CompiledState{_felicaState=" + this._felicaState + ", _networkState=" + this._networkState + ", _warningLackDispInfo=" + this._warningLackDispInfo + ", _warningContainOldDispInfo=" + this._warningContainOldDispInfo + ", _waringLackEmoneyInfo=" + this._waringLackEmoneyInfo + '}';
        }

        public CompiledState(FelicaState felicaState, NetworkState networkState, boolean z, boolean z2, boolean z3) {
            this._felicaState = felicaState;
            this._networkState = networkState;
            this._warningLackDispInfo = z;
            this._warningContainOldDispInfo = z2;
            this._waringLackEmoneyInfo = z3;
        }

        public CompiledState(CompiledState compiledState) {
            this._felicaState = compiledState._felicaState;
            this._networkState = compiledState._networkState;
            this._warningLackDispInfo = compiledState._warningLackDispInfo;
            this._warningContainOldDispInfo = compiledState._warningContainOldDispInfo;
            this._waringLackEmoneyInfo = compiledState._waringLackEmoneyInfo;
        }

        public FelicaState getFelicaState() {
            return this._felicaState;
        }

        public NetworkState getNetworkState() {
            return this._networkState;
        }

        public boolean hasWarning() {
            return hasFelicaWarning() || hasNetworkWarning();
        }

        public boolean hasFelicaWarning() {
            return (FelicaState.NO_PROBLEM == getFelicaState() || FelicaState.NO_ACCESS == getFelicaState()) ? false : true;
        }

        public boolean hasNetworkWarning() {
            return (NetworkState.NO_PROBLEM == getNetworkState() || NetworkState.NO_CONNECT == getNetworkState()) ? false : true;
        }

        public boolean isWarningLackDispInfo() {
            return this._warningLackDispInfo;
        }

        public boolean isWarningContainOldDispInfo() {
            return this._warningContainOldDispInfo;
        }

        public boolean isWaringLackEmoneyInfo() {
            return this._waringLackEmoneyInfo;
        }
    }
}

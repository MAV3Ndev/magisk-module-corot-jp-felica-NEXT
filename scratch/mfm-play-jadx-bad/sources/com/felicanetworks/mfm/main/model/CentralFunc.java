package com.felicanetworks.mfm.main.model;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.BannerInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.info.RecommendInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public interface CentralFunc extends FelicaPocketFunc {

    public interface BackgroundUpdateMyServiceListener {
        void onCompleted();

        void onError(ModelErrorInfo error);
    }

    public interface CardFaceImageListener {
        void onGetImage(Bitmap image);
    }

    public interface CompileListener {
        void onCompleted();

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface OrderBannerListener {
        void onSuccess(List<BannerInfo> list);
    }

    public interface ProgressListener {
        void onProgress(int numerator, int denominator);
    }

    @Override // com.felicanetworks.mfm.main.model.FelicaPocketFunc
    void cancel();

    void compile(String name, boolean isCheckNow, boolean isSimCacheUpdate, final CompileListener listener);

    void compile(boolean isCheckNow, boolean isSimCacheUpdate, CompileListener listener);

    MfcExpert.MfcStatus confirmExistMfc(PackageManager pm);

    List<String> getAppDetectedServiceIdList();

    List<String> getAreaDetectedServiceIdList();

    List<MfcExpert.Asset> getCacheAssetList() throws DatabaseException;

    boolean getCacheAssetListSetting() throws DatabaseException;

    void getCardFaceImage(String cardId, Map<Integer, String> urls, final CardFaceImageListener listener);

    CompiledState getCompiledState();

    List<MyServiceInfo> getDeleteServiceInfoList();

    Set<String> getDetectedServiceIdList(MfiCardFunc mfiCardFunc);

    List<MyServiceInfo> getMyServiceInfoList();

    List<RecommendInfo> getRecommendInfoList();

    List<String> getSasDetectedServiceIdList();

    void interrupt();

    boolean isCached();

    Boolean isCompiled();

    boolean isUpdateCacheError();

    boolean isUpdateCacheRunning();

    void marshalModelData(MfiCardFunc mfiCardFunc);

    void mfcFinish();

    void orderAsset(MfiCardFunc mfiCardFunc);

    void orderBanner(OrderBannerListener listener);

    void precompile(PrecompileListener listener);

    void readQPType(Set<String> cardDetects);

    void registBackgroundUpdateMyServiceListener(BackgroundUpdateMyServiceListener listener);

    void reportRecommend(String serviceId, String serviceVersion, String categoryId, String status);

    void setAsset(List<AssetInfo> assetInfoList);

    void setCompileProgressListener(ProgressListener listener);

    void unRegistBackgroundUpdateMyServiceListener(BackgroundUpdateMyServiceListener listener);

    void updateCache(MfiCardFunc mfiCardFunc);

    void updateErrorInitialization();

    public interface PrecompileListener {
        void onCompleted(PrecompiledState state);

        void onError(ModelErrorInfo error);

        public static class PrecompiledState {
            private FelicaDeviceState _fds;

            public enum FelicaDeviceState {
                NOT_EQUIPPED,
                INITIALIZED,
                UNINITIALIZED,
                CRS_CHECK_AND_RECOVERY
            }

            public String toString() {
                return "PrecompiledState{_fds=" + this._fds + '}';
            }

            public PrecompiledState(FelicaDeviceState fds) {
                this._fds = fds;
            }

            public FelicaDeviceState getFelicaDeviceState() {
                return this._fds;
            }
        }
    }

    public static class CompiledResult {
        public final List<String> appFamily;
        public final List<String> appHitIds;
        public final List<String> areaFamily;
        public final List<String> areaHitIds;
        public final List<Service> master;
        public final List<String> sasFamily;
        public final List<String> sasHitIds;
        public final List<String> uiccFamily;

        public CompiledResult(List<String> areaHitIds, List<String> appHitIds, List<String> sasHitIds, List<String> areaFamily, List<String> appFamily, List<String> sasFamily, List<String> uiccFamily, List<Service> master) {
            ArrayList arrayList = new ArrayList();
            this.areaHitIds = arrayList;
            ArrayList arrayList2 = new ArrayList();
            this.appHitIds = arrayList2;
            ArrayList arrayList3 = new ArrayList();
            this.sasHitIds = arrayList3;
            ArrayList arrayList4 = new ArrayList();
            this.areaFamily = arrayList4;
            ArrayList arrayList5 = new ArrayList();
            this.appFamily = arrayList5;
            ArrayList arrayList6 = new ArrayList();
            this.sasFamily = arrayList6;
            ArrayList arrayList7 = new ArrayList();
            this.uiccFamily = arrayList7;
            ArrayList arrayList8 = new ArrayList();
            this.master = arrayList8;
            arrayList.addAll(areaHitIds);
            arrayList2.addAll(appHitIds);
            arrayList3.addAll(sasHitIds);
            arrayList4.addAll(areaFamily);
            arrayList5.addAll(appFamily);
            arrayList6.addAll(sasFamily);
            arrayList7.addAll(uiccFamily);
            arrayList8.addAll(master);
        }

        public CompiledResult(CompiledResult src) {
            ArrayList arrayList = new ArrayList();
            this.areaHitIds = arrayList;
            ArrayList arrayList2 = new ArrayList();
            this.appHitIds = arrayList2;
            ArrayList arrayList3 = new ArrayList();
            this.sasHitIds = arrayList3;
            ArrayList arrayList4 = new ArrayList();
            this.areaFamily = arrayList4;
            ArrayList arrayList5 = new ArrayList();
            this.appFamily = arrayList5;
            ArrayList arrayList6 = new ArrayList();
            this.sasFamily = arrayList6;
            ArrayList arrayList7 = new ArrayList();
            this.uiccFamily = arrayList7;
            ArrayList arrayList8 = new ArrayList();
            this.master = arrayList8;
            arrayList.addAll(src.areaHitIds);
            arrayList2.addAll(src.appHitIds);
            arrayList3.addAll(src.sasHitIds);
            arrayList4.addAll(src.areaFamily);
            arrayList5.addAll(src.appFamily);
            arrayList6.addAll(src.sasFamily);
            arrayList7.addAll(src.uiccFamily);
            arrayList8.addAll(src.master);
        }

        public String toString() {
            return "CompiledResult{areaHitIds=" + this.areaHitIds + ", appHitIds=" + this.appHitIds + ", sasHitIds=" + this.sasHitIds + ", areaFamily=" + this.areaFamily + ", appFamily=" + this.appFamily + ", sasFamily=" + this.sasFamily + ", uiccFamily=" + this.uiccFamily + ", master=" + this.master + '}';
        }
    }

    public static class CompiledState {
        private FelicaState _felicaState;
        private NetworkState _networkState;
        private boolean _useCache;
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
            return "CompiledState{_felicaState=" + this._felicaState + ", _networkState=" + this._networkState + ", _warningLackDispInfo=" + this._warningLackDispInfo + ", _warningContainOldDispInfo=" + this._warningContainOldDispInfo + ", _waringLackEmoneyInfo=" + this._waringLackEmoneyInfo + ", _useCache=" + this._useCache + '}';
        }

        public CompiledState(FelicaState felicaState, NetworkState networkState, boolean warningLackDispInfo, boolean warningContainOldDispInfo, boolean waringLackEmoneyInfo, boolean useCache) {
            this._felicaState = felicaState;
            this._networkState = networkState;
            this._warningLackDispInfo = warningLackDispInfo;
            this._warningContainOldDispInfo = warningContainOldDispInfo;
            this._waringLackEmoneyInfo = waringLackEmoneyInfo;
            this._useCache = useCache;
        }

        public CompiledState(CompiledState state) {
            this._felicaState = state._felicaState;
            this._networkState = state._networkState;
            this._warningLackDispInfo = state._warningLackDispInfo;
            this._warningContainOldDispInfo = state._warningContainOldDispInfo;
            this._waringLackEmoneyInfo = state._waringLackEmoneyInfo;
            this._useCache = state._useCache;
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

        public boolean isCached() {
            return this._useCache;
        }
    }
}

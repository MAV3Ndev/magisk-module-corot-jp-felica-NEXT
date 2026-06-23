package com.felicanetworks.mfm.main.model.internal.main.mfc;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfc.mfi.CardAdditionalInfo;
import com.felicanetworks.mfc.mfi.CardInfo;
import com.felicanetworks.mfc.mfi.CardInfoWithSpStatus;
import com.felicanetworks.mfc.mfi.LocalPartialCardInfo;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfm.main.model.info.AssetInfo;
import com.felicanetworks.mfm.main.model.info.MyCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfoWithSp;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.info.specific.MySuicaInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.BindException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.BindFelica;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.BindFelicaListener;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccessException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.data.IssueStateData;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.CardFuncUtility;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaStatusManager;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.em.MficApiCallbackInfo;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.IdPicker;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import com.felicanetworks.mfm.main.policy.service.SupportServiceType;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.Card;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardAdditionalInfoListEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardEnableEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardInfoListWithSpStatusEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardIssueEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardListEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardReissuableDeleteEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.LinkageDataListEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.ServerOperationEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.UnsupportMfiService1CardExistEventCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class MfcExpert {
    private static final int MFI_HASH_LENGTH = 43;
    List<AreaItemFelica> _areaInfoList;
    private MfmFelicaAccess _mfmFelicaAccess;
    private ModelContext _modelContext;
    private FeliCaStatusManager _statusManager;
    private List<Card> mfiCardList = new ArrayList();
    MySuicaInfo.Position suicaPosition = MySuicaInfo.Position.UNKNOWN;
    private BindFelica _bindFelica = null;

    public enum ASSET_SELECT_TYPE {
        NON_ASSET,
        COMMON_AREA_NON_CID,
        ORIGINAL_AREA_NON_CID,
        ORIGINAL_AREA_ADD_CID,
        SP_SERVER
    }

    public enum MfcStatus {
        MFC_ENABLED,
        MFC_DISABLED,
        MFC_UNINSTALLED
    }

    public interface MfiStartListener {
        void errorResult(MfcException mfcException);

        void onRequestActivity(Intent intent);

        void onSuccess(boolean z);

        void onSuccessNoLogin();
    }

    public void setSuicaPosition(MySuicaInfo.Position position) {
        this.suicaPosition = position;
    }

    public static class Area {
        public String areaCode;
        public String sysCode;

        public Area(AreaItemFelica areaItemFelica) {
            this.sysCode = areaItemFelica.systemCode;
            this.areaCode = areaItemFelica.areaCode;
        }

        public String toString() {
            return "Area{sysCode='" + this.sysCode + "', areaCode='" + this.areaCode + "'}";
        }
    }

    public static class Sas {
        public String blockName;
        public String cpidSid;

        public Sas(SasReadItem sasReadItem) {
            this.cpidSid = sasReadItem.cpidSid;
            this.blockName = sasReadItem.blockName;
        }

        public String toString() {
            return "Sas{cpidSid='" + this.cpidSid + "', blockName='" + this.blockName + "'}";
        }
    }

    public static class FelicaSettings {
        public String icCode;
        public String idm;
        public boolean isFormated;

        public FelicaSettings(IssueStateData issueStateData) {
            this.isFormated = issueStateData.issueStateResult;
            this.idm = issueStateData.idmData;
            this.icCode = issueStateData.icCode;
        }

        public String toString() {
            return "FelicaSettings{isFormated=" + this.isFormated + ", idm='" + this.idm + "', icCode='" + this.icCode + "'}";
        }
    }

    public static class MemoryUsage {
        public int freeBlocks;
        public int systemCode;
        int systemNum;
        public int usedBlocks;

        public MemoryUsage(SystemBlockCntInfoData systemBlockCntInfoData) {
            this.usedBlocks = systemBlockCntInfoData.usedBlocks;
            this.freeBlocks = systemBlockCntInfoData.freeBlocks;
            this.systemNum = systemBlockCntInfoData.systemNum;
            this.systemCode = systemBlockCntInfoData.systemCode;
        }

        public String toString() {
            return "MemoryUsage{usedBlocks=" + this.usedBlocks + ", freeBlocks=" + this.freeBlocks + ", systemNum=" + this.systemNum + ", systemCode=" + this.systemCode + '}';
        }
    }

    public static class Asset {
        public int balanceLimit;
        public int balanceValue;
        public String cardId;
        public Date date1;
        public Date date2;
        public List<ServiceInfo.History.HistoryData> historyDataList;
        public boolean isDcardMini;
        public int point1;
        public int point2;
        public String serviceId;

        public Asset(String str, String str2, int i, int i2, int i3, int i4, Date date, Date date2, boolean z) {
            this.serviceId = str;
            this.cardId = str2;
            this.balanceValue = i;
            this.balanceLimit = i2;
            this.point1 = i3;
            this.point2 = i4;
            this.date1 = date != null ? (Date) date.clone() : null;
            this.date2 = date2 != null ? (Date) date2.clone() : null;
            this.isDcardMini = z;
            this.historyDataList = new ArrayList();
        }

        public String toString() {
            return "Asset{serviceId='" + this.serviceId + "', cardId='" + this.cardId + ", balanceValue=" + this.balanceValue + ", balanceLimit=" + this.balanceLimit + ", point1=" + this.point1 + ", point2=" + this.point2 + ", date1=" + this.date1 + ", date2=" + this.date2 + ", historyDataList=" + this.historyDataList + '}';
        }
    }

    public static class FpArea {
        private long fpNum;
        private List<FpService> fpServiceList;
        private int totalPocket;

        public FpArea(long j, int i, List<FpService> list) {
            this.fpNum = j;
            this.totalPocket = i;
            this.fpServiceList = list;
        }

        public String toString() {
            return "FpArea{fpNum=" + this.fpNum + ", totalPocket=" + this.totalPocket + ", fpServiceList=" + this.fpServiceList + '}';
        }

        public long getFpNum() {
            return this.fpNum;
        }

        public int getTotalPocket() {
            return this.totalPocket;
        }

        public List<FpService> getFpServiceList() {
            return this.fpServiceList;
        }

        public static class FpService {
            private byte[] fpIndex;
            private byte[] fpServiceNum;

            public FpService(byte[] bArr, byte[] bArr2) {
                if (bArr == null) {
                    this.fpServiceNum = new byte[0];
                } else {
                    this.fpServiceNum = (byte[]) bArr.clone();
                }
                if (bArr2 == null) {
                    this.fpIndex = new byte[0];
                } else {
                    this.fpIndex = (byte[]) bArr2.clone();
                }
            }

            public String toString() {
                return "FpService{fpServiceNum=" + Arrays.toString(this.fpServiceNum) + ", fpIndex=" + Arrays.toString(this.fpIndex) + '}';
            }

            public byte[] getFpServiceNum() {
                return this.fpServiceNum;
            }

            public byte[] getFpIndex() {
                return this.fpIndex;
            }
        }
    }

    public static class CardInfos {
        final List<MyCardInfo> resultCardInfoList = new ArrayList();
        final List<MyCardInfo> resultDeleteCardInfoList = new ArrayList();

        public List<MyCardInfo> getCardInfoList() {
            return this.resultCardInfoList;
        }

        public List<MyCardInfo> getDeleteCardInfoList() {
            return this.resultDeleteCardInfoList;
        }

        public String toString() {
            return "CardInfos{resultCardInfoList='" + this.resultCardInfoList + "', resultDeleteCardInfoList='" + this.resultDeleteCardInfoList + "'}";
        }
    }

    public MyCardInfo.CardStatus getCardStatus(String str) {
        Card mfiCard = getMfiCard(str);
        if (mfiCard == null) {
            return null;
        }
        return getCardStatus(mfiCard);
    }

    private MyCardInfo.CardStatus getCardStatus(Card card) {
        return getCardStatus(card.getCardInfo().getCardStatus());
    }

    private MyCardInfo.CardStatus getCardStatus(int i) {
        if (i == 0) {
            return MyCardInfo.CardStatus.STATUS_IN_PROCESS;
        }
        if (i == 1) {
            return MyCardInfo.CardStatus.STATUS_ACTIVE;
        }
        if (i == 2) {
            return MyCardInfo.CardStatus.STATUS_LOST;
        }
        if (i != 3) {
            return null;
        }
        return MyCardInfo.CardStatus.STATUS_DELETED;
    }

    public void initialize() throws MfcException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        try {
            MfmFelicaAccess.setMfiUser(null);
            if (this._bindFelica != null) {
                this._bindFelica.unbindFelica();
                this._bindFelica = null;
            }
            BindFelica bindFelica = new BindFelica(this._modelContext.getLegacyContext());
            this._bindFelica = bindFelica;
            bindFelica.bindFelica(new BindFelicaListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.1
                @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.BindFelicaListener
                public void notifySucceeded() {
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.BindFelicaListener
                public void notifyFailed(BindException bindException) {
                    LogUtil.warning(bindException);
                    asyncPacket.set(new MfcException(MfcExpert.class, 257, MfcException.CognitiveType.BIND_ERROR));
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException unused) {
            }
        } catch (BindException e) {
            LogUtil.warning(e);
            asyncPacket.set(new MfcException(MfcExpert.class, 258, MfcException.CognitiveType.BIND_ERROR));
        }
        if (asyncPacket.exist()) {
            throw ((MfcException) asyncPacket.get());
        }
    }

    public void clearVariable() {
        MfmFelicaAccess.setMfiUser(null);
        this.mfiCardList.clear();
        setSuicaPosition(MySuicaInfo.Position.UNKNOWN);
    }

    public void deinitialize() {
        BindFelica bindFelica = this._bindFelica;
        if (bindFelica != null) {
            bindFelica.unbindFelica();
        }
    }

    public void felicaStart() throws MfcException {
        this._statusManager.changeState(FeliCaStatusManager.FeliCaState.ACTIVATED);
    }

    public void finishFeliCaAccess() {
        MfmFelicaAccess.setMfiUser(null);
        MfmFelicaAccess.setMfiAdmin(null);
        this._statusManager.forceFinish();
    }

    public void mfcStart() throws MfcException {
        this._statusManager.changeState(FeliCaStatusManager.FeliCaState.OPENED);
    }

    public MfcStatus confirmExistMfc(PackageManager packageManager) {
        try {
            int applicationEnabledSetting = packageManager.getApplicationEnabledSetting((String) Sg.getValue(Sg.Key.SETTING_MFC_PACKAGE_NAME));
            if (applicationEnabledSetting != 1 && applicationEnabledSetting != 0) {
                return MfcStatus.MFC_DISABLED;
            }
            return MfcStatus.MFC_ENABLED;
        } catch (IllegalArgumentException unused) {
            return MfcStatus.MFC_UNINSTALLED;
        }
    }

    public void provisionServerOperation() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        try {
            this._mfmFelicaAccess.provisionServerOperation(new ServerOperationEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.2
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.ServerOperationEventCallback
                public void onSuccess() {
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.ServerOperationEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int i, String str) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3329, new MficApiCallbackInfo(i, str)));
                    countDownLatch.countDown();
                }
            });
        } catch (MfiClientException e) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3330, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3331, e2));
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
            if (asyncPacket.exist()) {
                LogUtil.warning((Throwable) asyncPacket.get());
            }
        } catch (InterruptedException unused) {
        }
    }

    public FelicaSettings getFelicaSettings() throws MfcException {
        try {
            return new FelicaSettings(this._mfmFelicaAccess.getIssueStateResult());
        } catch (FelicaAccessException e) {
            throw new MfcException((Class) getClass(), 769, e);
        }
    }

    public List<Area> getAreaList() throws MfcException {
        ArrayList arrayList = new ArrayList();
        try {
            List<AreaItemFelica> areaInfoList = this._mfmFelicaAccess.getAreaInfoList();
            this._areaInfoList = areaInfoList;
            Iterator<AreaItemFelica> it = areaInfoList.iterator();
            while (it.hasNext()) {
                arrayList.add(new Area(it.next()));
            }
            return arrayList;
        } catch (FelicaAccessException e) {
            throw new MfcException((Class) getClass(), InputDeviceCompat.SOURCE_GAMEPAD, e);
        }
    }

    public List<Sas> getSasList() throws MfcException {
        ArrayList arrayList = new ArrayList();
        try {
            Iterator<SasReadItem> it = this._mfmFelicaAccess.getSasInfoList(this._areaInfoList).iterator();
            while (it.hasNext()) {
                arrayList.add(new Sas(it.next()));
            }
            return arrayList;
        } catch (FelicaAccessException e) {
            throw new MfcException((Class) getClass(), 1281, e);
        }
    }

    public Map<Service, List<MyCardInfo>> createAssetTargetMap(List<Service> list, List<MyCardInfo> list2, boolean z) {
        HashMap map = new HashMap();
        for (Service service : list) {
            SupportServiceType supportServiceTypeResolve = SupportServiceType.resolve(service.getServiceId());
            switch (AnonymousClass13.$SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[supportServiceTypeResolve.ordinal()]) {
                case 1:
                case 2:
                    CardFuncUtility.putServiceCardMap(map, service, null);
                    break;
                case 3:
                case 4:
                    Iterator<MyCardInfo> it = list2.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            MyCardInfo next = it.next();
                            if (service.getServiceId().equals(next.getServiceId()) && next.getCardStatus() == MyCardInfo.CardStatus.STATUS_ACTIVE && next.getCardPosition() == MyCardInfo.CardPosition.POSITION_FOREGROUND) {
                                CardFuncUtility.putServiceCardMap(map, service, next);
                                break;
                            }
                        }
                    }
                    break;
                case 5:
                case 6:
                    if (supportServiceTypeResolve == SupportServiceType.S1 && MySuicaInfo.Position.ENABLE == this.suicaPosition) {
                        CardFuncUtility.putServiceCardMap(map, service, null);
                    } else if (supportServiceTypeResolve != SupportServiceType.S1 || MySuicaInfo.Position.NONE != this.suicaPosition) {
                        if (MySuicaInfo.Position.DISABLE != this.suicaPosition) {
                            for (MyCardInfo myCardInfo : list2) {
                                if (service.getServiceId().equals(myCardInfo.getServiceId()) && myCardInfo.getCardStatus() == MyCardInfo.CardStatus.STATUS_ACTIVE) {
                                    if (myCardInfo.getCardPosition() == MyCardInfo.CardPosition.POSITION_FOREGROUND) {
                                        CardFuncUtility.putServiceCardMap(map, service, myCardInfo);
                                        if (!z) {
                                        }
                                    }
                                    if (myCardInfo.getCardPosition() == MyCardInfo.CardPosition.POSITION_BACKGROUND && z) {
                                        CardFuncUtility.putServiceCardMap(map, service, myCardInfo);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    break;
            }
        }
        return map;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert$13, reason: invalid class name */
    static /* synthetic */ class AnonymousClass13 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType;

        static {
            int[] iArr = new int[SupportServiceType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType = iArr;
            try {
                iArr[SupportServiceType.A1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.A2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.M1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.M2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.S1.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.T1.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.B1.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.B2.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.C1.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.F1.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.US.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    public List<Asset> getAssetList(Map<Service, List<MyCardInfo>> map, boolean z) throws MfcException {
        ArrayList<Asset> arrayList = new ArrayList();
        try {
            List<MyCardInfo> arrayList2 = new ArrayList<>();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            for (Map.Entry<Service, List<MyCardInfo>> entry : map.entrySet()) {
                Service key = entry.getKey();
                ASSET_SELECT_TYPE assetSelectType = getAssetSelectType(key, entry.getValue());
                if (assetSelectType == ASSET_SELECT_TYPE.COMMON_AREA_NON_CID) {
                    if (!arrayList4.contains(key)) {
                        arrayList4.add(key);
                    }
                } else if (assetSelectType == ASSET_SELECT_TYPE.ORIGINAL_AREA_NON_CID) {
                    if (!arrayList3.contains(key)) {
                        arrayList3.add(key);
                    }
                } else if (assetSelectType == ASSET_SELECT_TYPE.ORIGINAL_AREA_ADD_CID) {
                    for (MyCardInfo myCardInfo : (List) Objects.requireNonNull(entry.getValue())) {
                        if (myCardInfo.getServiceId().equals(key.getServiceId()) && !arrayList2.contains(myCardInfo)) {
                            arrayList2.add(myCardInfo);
                        }
                    }
                }
            }
            if (!arrayList4.isEmpty() || !arrayList3.isEmpty() || !arrayList2.isEmpty()) {
                this._mfmFelicaAccess.initializeBalance(Boolean.valueOf(z));
            }
            if (arrayList4.size() > 0) {
                arrayList.addAll(this._mfmFelicaAccess.readCommonAreaBalanceInfo(arrayList4));
            }
            if (arrayList3.size() > 0) {
                arrayList.addAll(this._mfmFelicaAccess.readOriginalAreaBalanceInfo(arrayList3));
            }
            if (arrayList2.size() > 0) {
                arrayList.addAll(this._mfmFelicaAccess.readOriginalAreaWithCidBalanceInfo(arrayList2));
            }
            for (Asset asset : arrayList) {
                if (TextUtils.isEmpty(asset.cardId)) {
                    Iterator<Map.Entry<Service, List<MyCardInfo>>> it = map.entrySet().iterator();
                    while (it.hasNext()) {
                        MyCardInfo myCardInfo2 = (MyCardInfo) IdPicker.pickupWithSid(it.next().getValue(), asset.serviceId);
                        if (myCardInfo2 != null) {
                            asset.cardId = myCardInfo2.getCardId();
                        }
                    }
                }
            }
            return arrayList;
        } catch (FelicaAccessException e) {
            throw new MfcException((Class) getClass(), 1538, e);
        }
    }

    public List<AssetInfo> convertAssetInfoList(List<Asset> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Asset asset : list) {
            arrayList.add(new AssetInfo(asset.serviceId, asset.cardId, asset.balanceValue, asset.balanceLimit, asset.point1, asset.point2, asset.date1, asset.date2, asset.isDcardMini));
        }
        return arrayList;
    }

    public FpArea getFpAreaInfo() throws MfcException {
        try {
            if (this._mfmFelicaAccess.confirmFpStandard()) {
                return this._mfmFelicaAccess.readFpAreaInfo();
            }
            return null;
        } catch (FelicaAccessException e) {
            throw new MfcException((Class) getClass(), 1793, e);
        }
    }

    public List<MemoryUsage> getSystemMemoryUsageList() throws MfcException {
        ArrayList arrayList = new ArrayList();
        try {
            Iterator<SystemBlockCntInfoData> it = this._mfmFelicaAccess.getSysAreaUseCondInfo().iterator();
            while (it.hasNext()) {
                arrayList.add(new MemoryUsage(it.next()));
            }
            return arrayList;
        } catch (FelicaAccessException e) {
            throw new MfcException((Class) getClass(), 2049, e);
        }
    }

    public boolean isAvailable() {
        return MfmFelicaAccess.getMfiUser() != null;
    }

    public void setSilentStartCode(int i) {
        this._mfmFelicaAccess.setSilentStartCode(i);
    }

    public void mfiStart(MfiStartListener mfiStartListener) throws MfcException {
        mfiStart(true, false, FelicaAccess.Layout.LAYOUT_TYPE_SIGN_IN_ONLY, mfiStartListener);
    }

    public void mfiLogin(boolean z, MfiStartListener mfiStartListener) throws MfcException {
        if (z) {
            mfiStart(true, false, FelicaAccess.Layout.LAYOUT_TYPE_SKIPPABLE_SIGN_IN, mfiStartListener);
        } else {
            mfiStart(true, false, FelicaAccess.Layout.LAYOUT_TYPE_SIGN_IN_ONLY, mfiStartListener);
        }
    }

    public void mfiAccountClearStart(boolean z, boolean z2, MfiStartListener mfiStartListener) throws MfcException {
        if (z2) {
            mfiStart(true, z, FelicaAccess.Layout.LAYOUT_TYPE_SIGN_IN_ONLY, mfiStartListener);
        } else {
            mfiStart(true, z, FelicaAccess.Layout.LAYOUT_TYPE_SIGN_IN_WITH_TERMS, mfiStartListener);
        }
    }

    public boolean isLoggedIn() {
        return this._statusManager.isLoggedIn();
    }

    public CardInfos fetchCardList() throws MfcException {
        final CardInfos cardInfos = new CardInfos();
        if (MfmFelicaAccess.getMfiUser() == null) {
            throw new MfcException(getClass(), 2560, MfcException.CognitiveType.ILLEGAL_STATE);
        }
        final AsyncPacket asyncPacket = new AsyncPacket();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            MfmFelicaAccess.getMfiUser().getCardList(new CardListEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.3
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardListEventCallback
                public void onSuccess(Card[] cardArr) {
                    try {
                        MfcExpert.this.mfiCardList.clear();
                        for (Card card : ServiceIdPolicy.filter(cardArr, new ServiceIdPolicy.ServiceIdSelector<Card>() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.3.1
                            @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdSelector
                            public String sid(Card card2) {
                                return card2.getCardInfo().getServiceId();
                            }
                        })) {
                            CardInfo cardInfo = card.getCardInfo();
                            if (MfcExpert.this.isEnableCard(cardInfo)) {
                                MfcExpert.this.checkCardInfo(cardInfo);
                                MfcExpert.this.mfiCardList.add(card);
                                if (!MfcExpert.this.isRecovery(card.getCardInfo())) {
                                    MyCardInfo myCardInfoFromMfiCard = MfcExpert.this.getMyCardInfoFromMfiCard(card);
                                    if (MfcExpert.this.isDeleteNotExist(card.getCardInfo())) {
                                        cardInfos.getDeleteCardInfoList().add(myCardInfoFromMfiCard);
                                    } else {
                                        cardInfos.getCardInfoList().add(myCardInfoFromMfiCard);
                                    }
                                }
                            }
                        }
                    } catch (DataCheckerException e) {
                        LogUtil.warning(e);
                        asyncPacket.set(new MfcException(MfcExpert.class, 2563, MfcException.CognitiveType.DATA_CHECK_ERROR));
                    }
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardListEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int i, String str) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 2561, new MficApiCallbackInfo(i, str)));
                    countDownLatch.countDown();
                }
            });
        } catch (MfiClientException e) {
            asyncPacket.set(new MfcException(MfcExpert.class, 2562, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket.set(new MfcException(MfcExpert.class, 2564, e2));
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
            if (asyncPacket.exist()) {
                LogUtil.warning((Throwable) asyncPacket.get());
                throw ((MfcException) asyncPacket.get());
            }
        } catch (InterruptedException unused) {
        }
        return cardInfos;
    }

    public String getMfiHashValue(String str) throws MfcException {
        Card mfiCard = getMfiCard(str);
        if (mfiCard == null) {
            return null;
        }
        try {
            String additionalInfoHash = mfiCard.getCardInfo().getAdditionalInfoHash();
            if (additionalInfoHash == null) {
                return additionalInfoHash;
            }
            DataCheckerUtil.checkEqualLength(additionalInfoHash.length(), 43);
            DataCheckerUtil.checkAlphaSignFormat(additionalInfoHash);
            return additionalInfoHash;
        } catch (DataCheckerException e) {
            LogUtil.warning(e);
            throw new MfcException(getClass(), 2567, MfcException.CognitiveType.DATA_CHECK_ERROR);
        }
    }

    public List<MyCardAdditionalInfo> fetchCardAdditionalInfoList(String[] strArr) throws MfcException {
        final ArrayList arrayList = new ArrayList();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        if (MfmFelicaAccess.getMfiUser() == null) {
            return null;
        }
        try {
            MfmFelicaAccess.getMfiUser().getCardAdditionalInfoList(strArr, new CardAdditionalInfoListEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.4
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardAdditionalInfoListEventCallback
                public void onSuccess(CardAdditionalInfo[] cardAdditionalInfoArr) {
                    arrayList.addAll(Arrays.asList(cardAdditionalInfoArr));
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardAdditionalInfoListEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int i, String str) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3073, new MficApiCallbackInfo(i, str)));
                    countDownLatch.countDown();
                }
            });
        } catch (MfiClientException e) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3074, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3075, e2));
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
        }
        if (asyncPacket.exist()) {
            LogUtil.warning((Throwable) asyncPacket.get());
            throw ((MfcException) asyncPacket.get());
        }
        return convertAdditionalList(arrayList);
    }

    public List<MyCardInfoWithSp> getCardInfoListWithSpStatus(String str) throws MfcException {
        final ArrayList arrayList = new ArrayList();
        final AsyncPacket asyncPacket = new AsyncPacket();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        if (MfmFelicaAccess.getMfiUser() == null) {
            return new ArrayList();
        }
        try {
            MfmFelicaAccess.getMfiUser().getCardInfoListWithSpStatus(str, new CardInfoListWithSpStatusEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.5
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardInfoListWithSpStatusEventCallback
                public void onSuccess(CardInfoWithSpStatus[] cardInfoWithSpStatusArr) {
                    try {
                        arrayList.addAll(MfcExpert.this.convertCardInfoWithSpList(ServiceIdPolicy.filter(cardInfoWithSpStatusArr, new ServiceIdPolicy.ServiceIdSelector<CardInfoWithSpStatus>() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.5.1
                            @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdSelector
                            public String sid(CardInfoWithSpStatus cardInfoWithSpStatus) {
                                return cardInfoWithSpStatus.getServiceId();
                            }
                        })));
                    } catch (MfmException unused) {
                        asyncPacket.set(new MfcException(MfcExpert.class, 2570, MfcException.CognitiveType.DATA_CHECK_ERROR));
                    }
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardInfoListWithSpStatusEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int i, String str2) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 2566, new MficApiCallbackInfo(i, str2)));
                    countDownLatch.countDown();
                }
            });
        } catch (MfiClientException e) {
            asyncPacket.set(new MfcException((Class) getClass(), 2568, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket.set(new MfcException((Class) getClass(), 2569, e2));
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
        }
        if (!asyncPacket.exist()) {
            return arrayList;
        }
        LogUtil.warning((Throwable) asyncPacket.get());
        throw ((MfcException) asyncPacket.get());
    }

    public int existUnsupportMfiService1Card() throws MfcException {
        final AsyncPacket asyncPacket = new AsyncPacket();
        final AsyncPacket asyncPacket2 = new AsyncPacket();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            this._mfmFelicaAccess.existUnsupportMfiService1Card(new UnsupportMfiService1CardExistEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.6
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.UnsupportMfiService1CardExistEventCallback
                public void onSuccess(boolean z, LocalPartialCardInfo localPartialCardInfo) {
                    if (!z) {
                        asyncPacket.set(3);
                    } else if (localPartialCardInfo != null) {
                        int cardPosition = localPartialCardInfo.getCardPosition();
                        if (cardPosition == 0) {
                            asyncPacket.set(1);
                        } else if (cardPosition == 1) {
                            asyncPacket.set(2);
                        } else {
                            asyncPacket.set(0);
                        }
                    } else {
                        asyncPacket.set(0);
                    }
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.UnsupportMfiService1CardExistEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int i, String str) {
                    asyncPacket2.set(new MfcException(MfcExpert.class, 3585, new MficApiCallbackInfo(i, str)));
                    countDownLatch.countDown();
                }
            });
            try {
                if (!countDownLatch.await(60L, TimeUnit.SECONDS)) {
                    throw new MfcException(MfcExpert.class, 3586, MfcException.CognitiveType.TIMEOUT);
                }
            } catch (InterruptedException unused) {
            }
            if (asyncPacket2.exist()) {
                LogUtil.warning((Throwable) asyncPacket2.get());
                throw ((MfcException) asyncPacket2.get());
            }
            return ((Integer) asyncPacket.get()).intValue();
        } catch (MfiClientException e) {
            throw new MfcException((Class) getClass(), 3588, e);
        } catch (IllegalArgumentException e2) {
            throw new MfcException((Class) getClass(), 3587, e2);
        }
    }

    public boolean isSysAreaUseCondInfo() throws MfcException {
        try {
            boolean zIsSysAreaUseCondInfo = this._mfmFelicaAccess.isSysAreaUseCondInfo();
            this._statusManager.changeState(FeliCaStatusManager.FeliCaState.ACTIVATED);
            if (this._mfmFelicaAccess.getLocalCidList().isEmpty()) {
                return zIsSysAreaUseCondInfo;
            }
            return true;
        } catch (MfiClientException e) {
            throw new MfcException((Class) getClass(), 2051, e);
        } catch (FelicaAccessException e2) {
            throw new MfcException((Class) getClass(), 2052, e2);
        }
    }

    public boolean isSysAreaUseCondInfoNoCidList() throws MfcException {
        try {
            return this._mfmFelicaAccess.isSysAreaUseCondInfo();
        } catch (FelicaAccessException e) {
            throw new MfcException((Class) getClass(), 2050, e);
        }
    }

    public MyCardInfo.CardPosition getCardPosition(String str) {
        Card mfiCard = getMfiCard(str);
        if (mfiCard == null) {
            return null;
        }
        return getCardPosition(mfiCard);
    }

    public void mfiCardEnable(String str) throws MfcException {
        Card mfiCard;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        try {
            mfiCard = getMfiCard(str);
        } catch (MfiClientException e) {
            asyncPacket.set(new MfcException(FelicaAccess.class, 3846, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket.set(new MfcException(FelicaAccess.class, 3847, e2));
            countDownLatch.countDown();
        }
        if (mfiCard == null) {
            throw new MfcException(MfcExpert.class, 3841, MfcException.CognitiveType.ILLEGAL_STATE);
        }
        mfiCard.enable(new CardEnableEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.7
            @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardEnableEventCallback
            public void onSuccess(Card card, Card card2) {
                if (card != null) {
                    MfcExpert.this.updateCard(card);
                    if (card2 != null) {
                        MfcExpert.this.updateCard(card2);
                    }
                } else {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3842, MfcException.CognitiveType.ILLEGAL_STATE));
                }
                countDownLatch.countDown();
            }

            @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardEnableEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
            public void onError(int i, String str2) {
                asyncPacket.set(new MfcException(MfcExpert.class, 3843, new MficApiCallbackInfo(i, str2)));
                countDownLatch.countDown();
            }
        });
        try {
            if (!countDownLatch.await(60L, TimeUnit.SECONDS)) {
                throw new MfcException(MfcExpert.class, 3853, MfcException.CognitiveType.TIMEOUT);
            }
        } catch (InterruptedException unused) {
        }
        if (asyncPacket.exist()) {
            throw ((MfcException) asyncPacket.get());
        }
    }

    public void mfiCardDelete(final String str) throws MfcException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        Card mfiCard = getMfiCard(str);
        if (mfiCard == null) {
            throw new MfcException(MfcException.class, 3848, MfcException.CognitiveType.ILLEGAL_STATE);
        }
        try {
            mfiCard.delete(getLinkageData(2, str)[0], new CardReissuableDeleteEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.8
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardReissuableDeleteEventCallback
                public void onSuccess(Card card) {
                    if (card != null) {
                        MfcExpert.this.updateCard(card);
                    } else {
                        MfcExpert.this.removeCard(str);
                    }
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardReissuableDeleteEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int i, String str2) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3852, new MficApiCallbackInfo(i, str2)));
                    countDownLatch.countDown();
                }
            });
        } catch (MfiClientException e) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3856, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3858, e2));
            countDownLatch.countDown();
        }
        try {
            if (!countDownLatch.await(60L, TimeUnit.SECONDS)) {
                throw new MfcException(MfcExpert.class, 3853, MfcException.CognitiveType.TIMEOUT);
            }
        } catch (InterruptedException unused) {
        }
        if (asyncPacket.exist()) {
            throw ((MfcException) asyncPacket.get());
        }
    }

    public MyCardInfo mfiCardReIssue(String str) throws MfcException {
        final Card mfiCard = getMfiCard(str);
        if (mfiCard == null) {
            throw new MfcException(getClass(), 3857, MfcException.CognitiveType.ILLEGAL_STATE);
        }
        if (MfmFelicaAccess.getMfiUser() == null) {
            throw new MfcException(getClass(), 3861, MfcException.CognitiveType.ILLEGAL_STATE);
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        final AsyncPacket asyncPacket2 = new AsyncPacket();
        try {
            MfmFelicaAccess.getMfiUser().issueCard(getLinkageData(1, str)[0], new CardIssueEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.9
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardIssueEventCallback
                public void onSuccess(Card card) {
                    MfcExpert.this.replaceCard(mfiCard, card);
                    asyncPacket.set(card);
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardIssueEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int i, String str2) {
                    asyncPacket2.set(new MfcException(MfcException.class, 3862, new MficApiCallbackInfo(i, str2)));
                    countDownLatch.countDown();
                }
            });
        } catch (MfiClientException e) {
            asyncPacket2.set(new MfcException(MfcExpert.class, 3866, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket2.set(new MfcException(MfcExpert.class, 3867, e2));
            countDownLatch.countDown();
        }
        try {
            if (!countDownLatch.await(60L, TimeUnit.SECONDS)) {
                throw new MfcException(MfcExpert.class, 3863, MfcException.CognitiveType.TIMEOUT);
            }
        } catch (InterruptedException unused) {
        }
        if (asyncPacket2.exist()) {
            throw ((MfcException) asyncPacket2.get());
        }
        return getMyCardInfoFromMfiCard((Card) asyncPacket.get());
    }

    public String getAccountHash() throws MfcException {
        try {
            return this._mfmFelicaAccess.getCurrentAccountHash();
        } catch (MfiClientException e) {
            throw new MfcException((Class) getClass(), 4017, e);
        }
    }

    public SeInfo getSeInformation() throws MfcException {
        try {
            return this._mfmFelicaAccess.getSeInformation();
        } catch (MfiClientException e) {
            throw new MfcException((Class) getClass(), 2053, e);
        }
    }

    public MfcExpert(ModelContext modelContext) {
        this._modelContext = modelContext;
        MfmFelicaAccess mfmFelicaAccess = new MfmFelicaAccess(modelContext.getLegacyContext());
        this._mfmFelicaAccess = mfmFelicaAccess;
        this._statusManager = new FeliCaStatusManager(mfmFelicaAccess);
    }

    boolean isMfiCardReIssuable(String str) {
        Card mfiCard = getMfiCard(str);
        if (mfiCard == null) {
            return false;
        }
        CardInfo cardInfo = mfiCard.getCardInfo();
        return (cardInfo instanceof CardInfoWithSpStatus) && 1 == ((CardInfoWithSpStatus) cardInfo).getSpStatus();
    }

    private boolean isMfiCardReIssuable(MyCardInfo myCardInfo) {
        return (myCardInfo instanceof MyCardInfoWithSp) && MyCardInfo.CardSpStatus.SP_STATUS_REISSUABLE == myCardInfo.getCardSpStatus();
    }

    private String[] getLinkageData(int i, String str) throws MfcException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        final AsyncPacket asyncPacket2 = new AsyncPacket();
        try {
            MfmFelicaAccess.getMfiAdmin().getLinkageDataList(i, new String[]{str}, new LinkageDataListEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.10
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.LinkageDataListEventCallback
                public void onSuccess(String[] strArr) {
                    asyncPacket.set(strArr);
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.LinkageDataListEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int i2, String str2) {
                    asyncPacket2.set(new MfcException(MfcExpert.class, 4001, new MficApiCallbackInfo(i2, str2)));
                    countDownLatch.countDown();
                }
            });
        } catch (MfiClientException e) {
            asyncPacket2.set(new MfcException(MfcExpert.class, 4004, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket2.set(new MfcException(MfcExpert.class, 4005, e2));
            countDownLatch.countDown();
        }
        try {
            if (!countDownLatch.await(60L, TimeUnit.SECONDS)) {
                throw new MfcException(MfcExpert.class, 4002, MfcException.CognitiveType.TIMEOUT);
            }
        } catch (InterruptedException unused) {
        }
        if (asyncPacket2.exist()) {
            throw ((MfcException) asyncPacket2.get());
        }
        return (String[]) asyncPacket.get();
    }

    public void executeCardRecovery() throws MfcException {
        Iterator<Card> it = getMFServiceRecoveryCardList().iterator();
        while (it.hasNext()) {
            mfiCardDelete(it.next().getCardInfo().getCid());
        }
    }

    private void mfiStart(boolean z, boolean z2, FelicaAccess.Layout layout, MfiStartListener mfiStartListener) throws MfcException {
        this._statusManager.setStartSettings(z, z2, layout, mfiStartListener);
        this._statusManager.changeState(FeliCaStatusManager.FeliCaState.STARTED);
    }

    private ASSET_SELECT_TYPE getAssetSelectType(Service service, List<MyCardInfo> list) {
        ASSET_SELECT_TYPE asset_select_type = ASSET_SELECT_TYPE.NON_ASSET;
        String serviceType = service.getServiceType();
        if (serviceType == null) {
            return asset_select_type;
        }
        if (serviceType.isEmpty()) {
            return ASSET_SELECT_TYPE.COMMON_AREA_NON_CID;
        }
        if (TextUtils.equals(FeliCaParams.SERVICE_ID_SUICA, service.getServiceId())) {
            Iterator<MyCardInfo> it = list.iterator();
            while (it.hasNext()) {
                if (MyCardInfo.SupportMfiServiceType.SUPPORTED_MFI_SERVICE_1 == it.next().getUnsupportedMfiService1()) {
                    if (isFaver4_1()) {
                        return ASSET_SELECT_TYPE.ORIGINAL_AREA_ADD_CID;
                    }
                    return ASSET_SELECT_TYPE.ORIGINAL_AREA_NON_CID;
                }
            }
            return ASSET_SELECT_TYPE.ORIGINAL_AREA_NON_CID;
        }
        if (service.isType1()) {
            return ASSET_SELECT_TYPE.COMMON_AREA_NON_CID;
        }
        if (service.isType2()) {
            if (!service.isOriginalAreaService()) {
                return asset_select_type;
            }
            if (isFaver4_1()) {
                return ASSET_SELECT_TYPE.ORIGINAL_AREA_ADD_CID;
            }
            return ASSET_SELECT_TYPE.ORIGINAL_AREA_NON_CID;
        }
        if (FeliCaParams.SERVICE_ID_SUICA.equals(service.getServiceId())) {
            return ASSET_SELECT_TYPE.ORIGINAL_AREA_NON_CID;
        }
        return ASSET_SELECT_TYPE.COMMON_AREA_NON_CID;
    }

    private MyCardInfo.CardPosition getCardPosition(Card card) {
        return getCardPosition(card.getCardInfo().getCardPosition());
    }

    private MyCardInfo.CardPosition getCardPosition(int i) {
        if (i == 0) {
            return MyCardInfo.CardPosition.POSITION_FOREGROUND;
        }
        if (i == 1) {
            return MyCardInfo.CardPosition.POSITION_BACKGROUND;
        }
        if (i == 2) {
            return MyCardInfo.CardPosition.POSITION_PENDING;
        }
        if (i == 3) {
            return MyCardInfo.CardPosition.POSITION_NOT_APPLICABLE;
        }
        if (i != 4) {
            return null;
        }
        return MyCardInfo.CardPosition.POSITION_NOT_EXIST;
    }

    private List<MyCardAdditionalInfo> convertAdditionalList(List<CardAdditionalInfo> list) throws MfcException {
        ArrayList arrayList = new ArrayList();
        for (CardAdditionalInfo cardAdditionalInfo : list) {
            String cid = cardAdditionalInfo.getCid();
            if (cid == null) {
                throw new MfcException(getClass(), 2817, MfcException.CognitiveType.ILLEGAL_JSON_FORMAT);
            }
            try {
                DataCheckerUtil.checkEqualLength(cid.length(), 63);
                try {
                    DataCheckerUtil.checkAlphaNumberFormat(cid);
                    arrayList.add(new MyCardAdditionalInfo(cid, this._modelContext, cardAdditionalInfo.getAdditionalInfo()));
                } catch (DataCheckerException e) {
                    LogUtil.warning(e);
                    throw new MfcException(getClass(), 2819, MfcException.CognitiveType.ILLEGAL_JSON_FORMAT);
                }
            } catch (DataCheckerException e2) {
                LogUtil.warning(e2);
                throw new MfcException(getClass(), 2818, MfcException.CognitiveType.ILLEGAL_JSON_FORMAT);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isEnableCard(CardInfo cardInfo) {
        ArrayList<Integer[]> arrayList = new ArrayList<Integer[]>() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.11
            {
                add(new Integer[]{1, 0});
                add(new Integer[]{1, 1});
                add(new Integer[]{1, 2});
            }
        };
        ArrayList<Integer[]> arrayList2 = new ArrayList<Integer[]>() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.12
            {
                add(new Integer[]{1, 0});
                add(new Integer[]{1, 1});
                add(new Integer[]{1, 2});
                add(new Integer[]{0, Integer.MAX_VALUE});
                add(new Integer[]{2, Integer.MAX_VALUE});
                add(new Integer[]{3, 4});
                add(new Integer[]{3, 2});
            }
        };
        switch (AnonymousClass13.$SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.resolve(cardInfo.getServiceId()).ordinal()]) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            default:
                return false;
            case 3:
            case 4:
                if (cardInfo.getServiceId().equals(FeliCaParams.SERVICE_ID_DCARD)) {
                    return checkCard(cardInfo, arrayList);
                }
                return false;
            case 5:
                if (CardInfo.CID_UNSUPPORT_MFI_SERVICE_1.equals(cardInfo.getCid())) {
                    return true;
                }
                return checkCard(cardInfo, arrayList2);
            case 6:
                return checkCard(cardInfo, arrayList2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCardInfo(CardInfo cardInfo) throws DataCheckerException {
        String serviceId = cardInfo.getServiceId();
        DataCheckerUtil.checkAlphaNumberFormat(serviceId);
        DataCheckerUtil.checkByteLength(serviceId, 8, true);
        String cid = cardInfo.getCid();
        DataCheckerUtil.checkAlphaNumberFormat(cid);
        DataCheckerUtil.checkByteLength(cid, 63, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MyCardInfoWithSp> convertCardInfoWithSpList(List<CardInfoWithSpStatus> list) throws MfmException {
        ArrayList arrayList = new ArrayList();
        for (CardInfoWithSpStatus cardInfoWithSpStatus : list) {
            if (isDeleteNotExist(cardInfoWithSpStatus) && !isUnreissuable(cardInfoWithSpStatus) && !isNotApplicable(cardInfoWithSpStatus)) {
                checkCardInfo(cardInfoWithSpStatus);
                arrayList.add(getMyCardInfoWithSpFromMfiCard(cardInfoWithSpStatus));
            }
        }
        return arrayList;
    }

    private boolean checkCard(CardInfo cardInfo, List<Integer[]> list) {
        int cardStatus = cardInfo.getCardStatus();
        int cardPosition = cardInfo.getCardPosition();
        boolean z = false;
        for (Integer[] numArr : list) {
            if (numArr[0].intValue() == cardStatus && (numArr[1].intValue() == Integer.MAX_VALUE || numArr[1].intValue() == cardPosition)) {
                z = true;
            }
        }
        if (!z) {
            return false;
        }
        if (isDeleteNotExist(cardInfo)) {
            return (isUnreissuable(cardInfo) || isNotApplicable(cardInfo)) ? false : true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDeleteNotExist(CardInfo cardInfo) {
        return 3 == cardInfo.getCardStatus() && 4 == cardInfo.getCardPosition();
    }

    private boolean isUnreissuable(CardInfo cardInfo) {
        if (cardInfo instanceof CardInfoWithSpStatus) {
            return ((CardInfoWithSpStatus) cardInfo).getSpStatus() == 2;
        }
        return !cardInfo.getReissuePossibility();
    }

    private boolean isNotApplicable(CardInfo cardInfo) {
        return (cardInfo instanceof CardInfoWithSpStatus) && ((CardInfoWithSpStatus) cardInfo).getSpStatus() == 5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MyCardInfo getMyCardInfoFromMfiCard(Card card) {
        return new MyCardInfo(card.getCardInfo().getServiceId(), card.getCardInfo().getCid(), card.getCardInfo().getCardCategory(), getCardStatus(card), getCardPosition(card), card.getCardInfo().getAdditionalInfoHash(), null, this._modelContext.getNetworkExpert());
    }

    private MyCardInfoWithSp getMyCardInfoWithSpFromMfiCard(CardInfoWithSpStatus cardInfoWithSpStatus) throws MfmException {
        return new MyCardInfoWithSp(cardInfoWithSpStatus.getServiceId(), cardInfoWithSpStatus.getCid(), cardInfoWithSpStatus.getCardCategory(), getCardStatus(cardInfoWithSpStatus.getCardStatus()), getCardPosition(cardInfoWithSpStatus.getCardPosition()), cardInfoWithSpStatus.getAdditionalInfoHash(), this._modelContext.getNetworkExpert(), cardInfoWithSpStatus.getSpAdditionalInfo(), MyCardInfo.CardSpStatus.getType(cardInfoWithSpStatus.getSpStatus()));
    }

    private List<Card> getMFServiceRecoveryCardList() {
        ArrayList arrayList = new ArrayList();
        for (Card card : this.mfiCardList) {
            if (isRecovery(card.getCardInfo())) {
                arrayList.add(card);
            }
        }
        return arrayList;
    }

    public boolean isNeedCardRecovery() {
        return !getMFServiceRecoveryCardList().isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isRecovery(CardInfo cardInfo) {
        int i = AnonymousClass13.$SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.resolve(cardInfo.getServiceId()).ordinal()];
        if (i == 5 || i == 6) {
            return cardInfo.getCardStatus() == 2 || cardInfo.getCardStatus() == 0 || (cardInfo.getCardStatus() == 1 && cardInfo.getCardPosition() == 2) || (cardInfo.getCardStatus() == 3 && cardInfo.getCardPosition() == 2);
        }
        return false;
    }

    private Card getMfiCard(String str) {
        for (Card card : this.mfiCardList) {
            if (TextUtils.equals(str, card.getCardInfo().getCid())) {
                return card;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCard(Card card) {
        for (Card card2 : this.mfiCardList) {
            if (TextUtils.equals(card.getCardInfo().getCid(), card2.getCardInfo().getCid())) {
                List<Card> list = this.mfiCardList;
                list.set(list.indexOf(card2), card);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceCard(Card card, Card card2) {
        for (Card card3 : this.mfiCardList) {
            if (TextUtils.equals(card.getCardInfo().getCid(), card3.getCardInfo().getCid())) {
                List<Card> list = this.mfiCardList;
                list.set(list.indexOf(card3), card2);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCard(String str) {
        for (Card card : this.mfiCardList) {
            if (TextUtils.equals(str, card.getCardInfo().getCid())) {
                this.mfiCardList.remove(card);
                return;
            }
        }
    }

    private boolean isFaver4_1() {
        return Settings.getFelicaChipVersion() == Settings.FelicaChipVersion.FAVER_GP_4_1;
    }

    private static class AsyncPacket<T> {
        private T packet;

        private AsyncPacket() {
            this.packet = null;
        }

        void set(T t) {
            this.packet = t;
        }

        boolean exist() {
            return this.packet != null;
        }

        T get() {
            return this.packet;
        }
    }
}

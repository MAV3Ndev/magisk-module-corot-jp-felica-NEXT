package com.felicanetworks.mfm.main.model.internal.main.mfc;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfc.mfi.CachedCardInfo;
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
import com.felicanetworks.mfm.main.model.info.specific.MyQUICPayInfo;
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
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
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
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CachedCard;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CachedCardEnableEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CachedCardListEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.Card;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardAdditionalInfoListEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardEnableEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardInfoListWithSpStatusEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardIssueEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardListEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardReissuableDeleteEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CardsUploadEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.LinkageDataListEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.MemoryClearEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.RemainedCards;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.RemainedCardsEventCallback;
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

/* JADX INFO: loaded from: classes3.dex */
public class MfcExpert {
    private static final int MEMORY_CLEAR_MFIC_API_TIMEOUT = 180;
    private static final int MFI_HASH_LENGTH = 43;
    List<AreaItemFelica> _areaInfoList;
    private MfmFelicaAccess _mfmFelicaAccess;
    private ModelContext _modelContext;
    private FeliCaStatusManager _statusManager;
    private RemainedCards remainedCards;
    private final List<Card> mfiCardList = new ArrayList();
    private final List<CachedCard> mfiCachedCardList = new ArrayList();
    MySuicaInfo.Position suicaPosition = MySuicaInfo.Position.UNKNOWN;
    private BindFelica _bindFelica = null;

    public enum ASSET_SELECT_TYPE {
        NON_ASSET,
        COMMON_AREA_NON_CID,
        ORIGINAL_AREA_NON_CID,
        ORIGINAL_AREA_ADD_CID,
        SP_SERVER
    }

    public enum IssueState {
        ISSUE_STATE_PERFORMED,
        ISSUE_STATE_NO_PERFORMED,
        ISSUE_STATE_MEMORY_CLEAR_RUNNING,
        ISSUE_STATE_CHIP_ERROR
    }

    public enum MfcStatus {
        MFC_ENABLED,
        MFC_DISABLED,
        MFC_UNINSTALLED
    }

    public interface MfiStartListener {
        void errorResult(MfcException exception);

        void onRequestActivity(Intent intent);

        void onSuccess(boolean executedSilentStart);

        void onSuccessNoLogin();
    }

    public void setSuicaPosition(MySuicaInfo.Position suicaPosition) {
        this.suicaPosition = suicaPosition;
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
        public byte[] issue;
        public IssueState issueState;

        public FelicaSettings(IssueStateData issueStateData) {
            this.issue = issueStateData.containerIssueInfo;
            this.issueState = getIssueState(issueStateData.containerIssueInfo);
            this.idm = issueStateData.idmData;
            this.icCode = issueStateData.icCode;
        }

        public FelicaSettings(String idm, String icCode) {
            this.idm = idm;
            this.icCode = icCode;
        }

        public String toString() {
            return "FelicaSettings{issue=" + Arrays.toString(this.issue) + ", issueState=" + this.issueState + ", idm='" + this.idm + "', icCode='" + this.icCode + "'}";
        }

        private IssueState getIssueState(byte[] contIssueInfo) {
            for (byte b : contIssueInfo) {
                if (b != 0) {
                    byte[] bArr = new byte[3];
                    System.arraycopy(contIssueInfo, 2, bArr, 0, 3);
                    byte[] bArr2 = (byte[]) Sg.getValue(Sg.Key.CAREER_CODE);
                    IssueState issueState = IssueState.ISSUE_STATE_CHIP_ERROR;
                    if (Arrays.equals(bArr, bArr2)) {
                        return IssueState.ISSUE_STATE_PERFORMED;
                    }
                    bArr[0] = (byte) (bArr[0] & 127);
                    bArr2[0] = (byte) (bArr2[0] & 127);
                    return Arrays.equals(bArr, bArr2) ? IssueState.ISSUE_STATE_MEMORY_CLEAR_RUNNING : issueState;
                }
            }
            return IssueState.ISSUE_STATE_NO_PERFORMED;
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

        public Asset(String serviceId, String cid, int balanceValue, int balanceLimit, int point1, int point2, Date date1, Date date2, boolean isDcardMini) {
            this.serviceId = serviceId;
            this.cardId = cid;
            this.balanceValue = balanceValue;
            this.balanceLimit = balanceLimit;
            this.point1 = point1;
            this.point2 = point2;
            this.date1 = date1 != null ? (Date) date1.clone() : null;
            this.date2 = date2 != null ? (Date) date2.clone() : null;
            this.isDcardMini = isDcardMini;
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

        public FpArea(long fpNum, int totalPocket, List<FpService> fpServiceList) {
            this.fpNum = fpNum;
            this.totalPocket = totalPocket;
            this.fpServiceList = fpServiceList;
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

            public FpService(byte[] fpServiceNumber, byte[] fpIndexArea) {
                if (fpServiceNumber == null) {
                    this.fpServiceNum = new byte[0];
                } else {
                    this.fpServiceNum = (byte[]) fpServiceNumber.clone();
                }
                if (fpIndexArea == null) {
                    this.fpIndex = new byte[0];
                } else {
                    this.fpIndex = (byte[]) fpIndexArea.clone();
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

    public static class RemainedCardInfo {
        private List<MyCardInfo> displayDeleteCardList = new ArrayList();
        private List<MyCardInfo> notDisplayDeleteCardList = new ArrayList();
        private List<MyCardInfo> displayPermanentDeleteCardList = new ArrayList();
        private List<MyCardInfo> notDisplayPermanentDeleteCardList = new ArrayList();

        public void setDisplayDeleteCardList(List<MyCardInfo> list) {
            this.displayDeleteCardList = list;
        }

        public List<MyCardInfo> getDisplayDeleteCardList() {
            return this.displayDeleteCardList;
        }

        public void setNotDisplayDeleteCardList(List<MyCardInfo> list) {
            this.notDisplayDeleteCardList = list;
        }

        public List<MyCardInfo> getNotDisplayDeleteCardList() {
            return this.notDisplayDeleteCardList;
        }

        public void setDisplayPermanentDeleteCardList(List<MyCardInfo> list) {
            this.displayPermanentDeleteCardList = list;
        }

        public List<MyCardInfo> getDisplayPermanentDeleteCardList() {
            return this.displayPermanentDeleteCardList;
        }

        public void setNotDisplayPermanentDeleteCardList(List<MyCardInfo> list) {
            this.notDisplayPermanentDeleteCardList = list;
        }

        public List<MyCardInfo> getNotDisplayPermanentDeleteCardList() {
            return this.notDisplayPermanentDeleteCardList;
        }

        public String toString() {
            return "RemainedCardInfo{displayDeleteCardList ='" + this.displayDeleteCardList + "', notDisplayDeleteCardList ='" + this.notDisplayDeleteCardList + "', displayPermanentDeleteCardList ='" + this.displayPermanentDeleteCardList + "', notDisplayPermanentDeleteCardList ='" + this.notDisplayPermanentDeleteCardList + "'}";
        }
    }

    private MyCardInfo.CardStatus getCardStatus(Card card) {
        return getCardStatus(card.getCardInfo().getCardStatus());
    }

    private MyCardInfo.CardStatus getCardStatus(int status) {
        if (status == 0) {
            return MyCardInfo.CardStatus.STATUS_IN_PROCESS;
        }
        if (status == 1) {
            return MyCardInfo.CardStatus.STATUS_ACTIVE;
        }
        if (status == 2) {
            return MyCardInfo.CardStatus.STATUS_LOST;
        }
        if (status != 3) {
            return null;
        }
        return MyCardInfo.CardStatus.STATUS_DELETED;
    }

    public void initialize() throws MfcException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        try {
            MfmFelicaAccess.setMfiUser(null);
            BindFelica bindFelica = this._bindFelica;
            if (bindFelica != null) {
                bindFelica.unbindFelica();
                this._bindFelica = null;
            }
            BindFelica bindFelica2 = new BindFelica(this._modelContext.getLegacyContext());
            this._bindFelica = bindFelica2;
            bindFelica2.bindFelica(new BindFelicaListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.1
                @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.BindFelicaListener
                public void notifySucceeded() {
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.BindFelicaListener
                public void notifyFailed(BindException be) {
                    LogUtil.warning(be);
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
        this.mfiCachedCardList.clear();
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
        try {
            if (ServicePreference.getInstance().loadSeid(PresenterData.getInstance().getContext()).isEmpty()) {
                ServicePreference.getInstance().saveSeid(PresenterData.getInstance().getContext(), getSeInformation().getSeId());
            }
        } catch (Exception unused) {
        }
    }

    public void finishFeliCaAccess() {
        MfmFelicaAccess.setMfiUser(null);
        MfmFelicaAccess.setMfiAdmin(null);
        this._statusManager.forceFinish();
    }

    public void forceStop() {
        this._statusManager.forceStop();
    }

    public void mfcStart() throws MfcException {
        this._statusManager.changeState(FeliCaStatusManager.FeliCaState.OPENED);
    }

    public MfcStatus confirmExistMfc(PackageManager pm) {
        try {
            int applicationEnabledSetting = pm.getApplicationEnabledSetting((String) Sg.getValue(Sg.Key.SETTING_MFC_PACKAGE_NAME));
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
                public void onError(int type, String msg) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3329, new MficApiCallbackInfo(type, msg)));
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

    public Map<Service, List<MyCardInfo>> createAssetTargetMap(List<Service> serviceList, List<MyCardInfo> myCardInfoList, boolean isTargetBackgroundCard) {
        HashMap map = new HashMap();
        for (Service service : serviceList) {
            SupportServiceType supportServiceTypeResolve = SupportServiceType.resolve(service.getServiceId());
            switch (AnonymousClass21.$SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[supportServiceTypeResolve.ordinal()]) {
                case 1:
                case 2:
                    CardFuncUtility.putServiceCardMap(map, service, null);
                    break;
                case 3:
                case 4:
                    Iterator<MyCardInfo> it = myCardInfoList.iterator();
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
                            for (MyCardInfo myCardInfo : myCardInfoList) {
                                if (service.getServiceId().equals(myCardInfo.getServiceId()) && myCardInfo.getCardStatus() == MyCardInfo.CardStatus.STATUS_ACTIVE) {
                                    if (myCardInfo.getCardPosition() == MyCardInfo.CardPosition.POSITION_FOREGROUND) {
                                        CardFuncUtility.putServiceCardMap(map, service, myCardInfo);
                                        if (!isTargetBackgroundCard) {
                                        }
                                    }
                                    if (myCardInfo.getCardPosition() == MyCardInfo.CardPosition.POSITION_BACKGROUND && isTargetBackgroundCard) {
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

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert$21, reason: invalid class name */
    static /* synthetic */ class AnonymousClass21 {
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

    public List<Asset> getAssetList(Map<Service, List<MyCardInfo>> map, boolean isHistory) throws MfcException {
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
                this._mfmFelicaAccess.initializeBalance(Boolean.valueOf(isHistory));
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
            try {
                this._modelContext.getOpenedDatabaseExpert().setCacheAssetList(arrayList);
                return arrayList;
            } catch (DatabaseException e) {
                LogUtil.warning(e);
                return arrayList;
            }
        } catch (FelicaAccessException e2) {
            throw new MfcException((Class) getClass(), 1538, e2);
        }
    }

    public List<AssetInfo> convertAssetInfoList(List<Asset> assetList) {
        if (assetList == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Asset asset : assetList) {
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

    public MyQUICPayInfo.QPType getQUICPayID() throws MfcException {
        try {
            return this._mfmFelicaAccess.getQUICPayID();
        } catch (FelicaAccessException e) {
            throw new MfcException((Class) getClass(), 1537, e);
        }
    }

    public boolean isAvailable() {
        return MfmFelicaAccess.getMfiUser() != null;
    }

    public void setSilentStartCode(int code) {
        this._mfmFelicaAccess.setSilentStartCode(code);
    }

    public void mfiStart(MfiStartListener mfiStartListener) throws MfcException {
        mfiStart(true, false, FelicaAccess.Layout.LAYOUT_TYPE_SIGN_IN_ONLY, mfiStartListener);
    }

    public void mfiStart(boolean isBackground, MfiStartListener mfiStartListener) throws MfcException {
        mfiStart(true, false, FelicaAccess.Layout.LAYOUT_TYPE_SIGN_IN_ONLY, isBackground, mfiStartListener);
    }

    public void mfiLogin(boolean isTutorial, MfiStartListener mfiStartListener) throws MfcException {
        if (isTutorial) {
            mfiStart(true, false, FelicaAccess.Layout.LAYOUT_TYPE_SKIPPABLE_SIGN_IN, mfiStartListener);
        } else {
            mfiStart(true, false, FelicaAccess.Layout.LAYOUT_TYPE_SIGN_IN_ONLY, mfiStartListener);
        }
    }

    public void mfiAccountClearStart(boolean doClear, boolean isInternalLaunch, MfiStartListener mfiStartListener) throws MfcException {
        if (isInternalLaunch) {
            mfiStart(true, doClear, FelicaAccess.Layout.LAYOUT_TYPE_SIGN_IN_ONLY, mfiStartListener);
        } else {
            mfiStart(true, doClear, FelicaAccess.Layout.LAYOUT_TYPE_SIGN_IN_WITH_TERMS, mfiStartListener);
        }
    }

    public void mfiStartNoLogin(MfiStartListener mfiStartListener) throws MfcException {
        mfiStart(false, false, FelicaAccess.Layout.LAYOUT_TYPE_SIGN_IN_ONLY, mfiStartListener);
    }

    public FelicaSettings getIDmAndICCode() throws MfcException {
        try {
            IssueStateData issueStateResult = this._mfmFelicaAccess.getIssueStateResult();
            return new FelicaSettings(issueStateResult.idmData, issueStateResult.icCode);
        } catch (FelicaAccessException e) {
            throw new MfcException((Class) getClass(), 2304, e);
        }
    }

    public boolean checkAndRecoverCrsState() throws MfcException {
        try {
            return MfmFelicaAccess.getMfiAdmin().checkAndRecoverCrsState();
        } catch (MfiClientException e) {
            throw new MfcException((Class) getClass(), 2305, e);
        }
    }

    public boolean isChipInitialized() throws MfcException {
        try {
            return MfmFelicaAccess.getMfiAdmin().isChipInitialized();
        } catch (MfiClientException e) {
            throw new MfcException((Class) getClass(), 2306, e);
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
                public void onSuccess(Card[] cards) {
                    try {
                        MfcExpert.this.mfiCardList.clear();
                        for (Card card : ServiceIdPolicy.filter(cards, new ServiceIdPolicy.ServiceIdSelector<Card>() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.3.1
                            /* JADX DEBUG: Method merged with bridge method: sid(Ljava/lang/Object;)Ljava/lang/String; */
                            @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdSelector
                            public String sid(Card item) {
                                return item.getCardInfo().getServiceId();
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
                public void onError(int type, String msg) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 2561, new MficApiCallbackInfo(type, msg)));
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
            if (!countDownLatch.await(60L, TimeUnit.SECONDS)) {
                asyncPacket.set(new MfcException(MfcExpert.class, 2565, MfcException.CognitiveType.TIMEOUT));
            }
            if (asyncPacket.exist()) {
                LogUtil.warning((Throwable) asyncPacket.get());
                throw ((MfcException) asyncPacket.get());
            }
        } catch (InterruptedException unused) {
        }
        return cardInfos;
    }

    public String getMfiHashValue(String cid) throws MfcException {
        Card mfiCard = getMfiCard(cid);
        if (mfiCard == null) {
            return null;
        }
        try {
            String additionalInfoHash = mfiCard.getCardInfo().getAdditionalInfoHash();
            if (additionalInfoHash != null) {
                DataCheckerUtil.checkEqualLength(additionalInfoHash.length(), 43);
                DataCheckerUtil.checkAlphaSignFormat(additionalInfoHash);
            }
            return additionalInfoHash;
        } catch (DataCheckerException e) {
            LogUtil.warning(e);
            throw new MfcException(getClass(), 2567, MfcException.CognitiveType.DATA_CHECK_ERROR);
        }
    }

    public List<MyCardAdditionalInfo> fetchCardAdditionalInfoList(String[] needAdditionalCardIdList) throws MfcException {
        final ArrayList arrayList = new ArrayList();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        if (MfmFelicaAccess.getMfiUser() == null) {
            return null;
        }
        try {
            MfmFelicaAccess.getMfiUser().getCardAdditionalInfoList(needAdditionalCardIdList, new CardAdditionalInfoListEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.4
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardAdditionalInfoListEventCallback
                public void onSuccess(CardAdditionalInfo[] cardAdditionalInfos) {
                    arrayList.addAll(Arrays.asList(cardAdditionalInfos));
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardAdditionalInfoListEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int type, String msg) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3073, new MficApiCallbackInfo(type, msg)));
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

    public List<MyCardInfoWithSp> getCardInfoListWithSpStatus(final String serviceId) throws MfcException {
        final ArrayList arrayList = new ArrayList();
        final AsyncPacket asyncPacket = new AsyncPacket();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        if (MfmFelicaAccess.getMfiUser() == null) {
            return new ArrayList();
        }
        try {
            MfmFelicaAccess.getMfiUser().getCardInfoListWithSpStatus(serviceId, new CardInfoListWithSpStatusEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.5
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardInfoListWithSpStatusEventCallback
                public void onSuccess(CardInfoWithSpStatus[] cardInfoWithSpStatusList) {
                    try {
                        arrayList.addAll(MfcExpert.this.convertCardInfoWithSpList(ServiceIdPolicy.filter(cardInfoWithSpStatusList, new ServiceIdPolicy.ServiceIdSelector<CardInfoWithSpStatus>() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.5.1
                            /* JADX DEBUG: Method merged with bridge method: sid(Ljava/lang/Object;)Ljava/lang/String; */
                            @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdSelector
                            public String sid(CardInfoWithSpStatus item) {
                                return item.getServiceId();
                            }
                        })));
                    } catch (MfmException unused) {
                        asyncPacket.set(new MfcException(MfcExpert.class, 2570, MfcException.CognitiveType.DATA_CHECK_ERROR));
                    }
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardInfoListWithSpStatusEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int type, String msg) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 2566, new MficApiCallbackInfo(type, msg)));
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
                public void onSuccess(boolean exist, LocalPartialCardInfo localPartialCardInfo) {
                    if (!exist) {
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
                public void onError(int i, String s) {
                    asyncPacket2.set(new MfcException(MfcExpert.class, 3585, new MficApiCallbackInfo(i, s)));
                    countDownLatch.countDown();
                }
            });
            try {
                if (!countDownLatch.await(60L, TimeUnit.SECONDS)) {
                    throw new MfcException(MfcExpert.class, 3586, MfcException.CognitiveType.TIMEOUT);
                }
                if (asyncPacket2.exist()) {
                    LogUtil.warning((Throwable) asyncPacket2.get());
                    if (asyncPacket2.get() == null) {
                        throw new MfcException(MfcExpert.class, 3590, MfcException.CognitiveType.ILLEGAL_STATE);
                    }
                    throw ((MfcException) asyncPacket2.get());
                }
                if (asyncPacket.get() == null) {
                    throw new MfcException(MfcExpert.class, 3591, MfcException.CognitiveType.ILLEGAL_STATE);
                }
                return ((Integer) asyncPacket.get()).intValue();
            } catch (InterruptedException e) {
                LogUtil.warning(e);
                this._mfmFelicaAccess.stop();
                throw new MfcException(MfcExpert.class, 3589, MfcException.CognitiveType.INTERRUPT);
            }
        } catch (MfiClientException e2) {
            throw new MfcException((Class) getClass(), 3588, e2);
        } catch (IllegalArgumentException e3) {
            throw new MfcException((Class) getClass(), 3587, e3);
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

    public MyCardInfo.CardPosition getCardPosition(String cid) {
        Card mfiCard = getMfiCard(cid);
        if (mfiCard == null) {
            return null;
        }
        return getCardPosition(mfiCard);
    }

    public void mfiCardEnable(String cid) throws MfcException {
        Card mfiCard;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        try {
            mfiCard = getMfiCard(cid);
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
            public void onSuccess(Card enableCard, Card disableCard) {
                if (enableCard != null) {
                    MfcExpert.this.updateCard(enableCard);
                    if (disableCard != null) {
                        MfcExpert.this.updateCard(disableCard);
                    }
                } else {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3842, MfcException.CognitiveType.ILLEGAL_STATE));
                }
                countDownLatch.countDown();
            }

            @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardEnableEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
            public void onError(int type, String msg) {
                asyncPacket.set(new MfcException(MfcExpert.class, 3843, new MficApiCallbackInfo(type, msg)));
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

    public void mfiCardDelete(final String cid) throws MfcException, InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        Card mfiCard = getMfiCard(cid);
        if (mfiCard == null) {
            throw new MfcException(MfcException.class, 3848, MfcException.CognitiveType.ILLEGAL_STATE);
        }
        try {
            mfiCard.delete(getLinkageData(2, cid)[0], new CardReissuableDeleteEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.8
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardReissuableDeleteEventCallback
                public void onSuccess(Card card) {
                    if (card != null) {
                        MfcExpert.this.updateCard(card);
                    } else {
                        MfcExpert.this.removeCard(cid);
                    }
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardReissuableDeleteEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int i, String s) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3852, new MficApiCallbackInfo(i, s)));
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
        if (!countDownLatch.await(60L, TimeUnit.SECONDS)) {
            throw new MfcException(MfcExpert.class, 3853, MfcException.CognitiveType.TIMEOUT);
        }
        if (asyncPacket.exist()) {
            throw ((MfcException) asyncPacket.get());
        }
    }

    public MyCardInfo mfiCardReIssue(final String cid) throws MfcException, InterruptedException {
        final Card mfiCard = getMfiCard(cid);
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
            String[] linkageData = getLinkageData(1, cid);
            try {
                MfmFelicaAccess.getMfiUser().issueCard(linkageData[0], new CardIssueEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.9
                    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardIssueEventCallback
                    public void onSuccess(Card issuedCard) {
                        MfcExpert.this.replaceCard(mfiCard, issuedCard);
                        asyncPacket.set(issuedCard);
                        countDownLatch.countDown();
                    }

                    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardIssueEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                    public void onError(int i, String s) {
                        asyncPacket2.set(new MfcException(MfcException.class, 3862, new MficApiCallbackInfo(i, s)));
                        countDownLatch.countDown();
                    }
                });
            } catch (MfiClientException e) {
                e = e;
                asyncPacket2.set(new MfcException(MfcExpert.class, 3866, e));
                countDownLatch.countDown();
            } catch (IllegalArgumentException e2) {
                e = e2;
                asyncPacket2.set(new MfcException(MfcExpert.class, 3867, e));
                countDownLatch.countDown();
            }
        } catch (MfiClientException e3) {
            e = e3;
        } catch (IllegalArgumentException e4) {
            e = e4;
        }
        if (!countDownLatch.await(60L, TimeUnit.SECONDS)) {
            throw new MfcException(MfcExpert.class, 3863, MfcException.CognitiveType.TIMEOUT);
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
        this._mfmFelicaAccess = new MfmFelicaAccess(this._modelContext.getLegacyContext());
        this._statusManager = new FeliCaStatusManager(this._mfmFelicaAccess);
    }

    boolean isMfiCardReIssuable(String cid) {
        Card mfiCard = getMfiCard(cid);
        if (mfiCard == null) {
            return false;
        }
        CardInfo cardInfo = mfiCard.getCardInfo();
        return (cardInfo instanceof CardInfoWithSpStatus) && 1 == ((CardInfoWithSpStatus) cardInfo).getSpStatus();
    }

    private boolean isMfiCardReIssuable(MyCardInfo myCardInfo) {
        return (myCardInfo instanceof MyCardInfoWithSp) && MyCardInfo.CardSpStatus.SP_STATUS_REISSUABLE == myCardInfo.getCardSpStatus();
    }

    private String[] getLinkageData(int actionType, String cid) throws MfcException, InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        final AsyncPacket asyncPacket2 = new AsyncPacket();
        try {
            MfmFelicaAccess.getMfiAdmin().getLinkageDataList(actionType, new String[]{cid}, new LinkageDataListEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.10
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.LinkageDataListEventCallback
                public void onSuccess(String[] linkage) {
                    asyncPacket.set(linkage);
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.LinkageDataListEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int i, String s) {
                    asyncPacket2.set(new MfcException(MfcExpert.class, 4001, new MficApiCallbackInfo(i, s)));
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
        if (!countDownLatch.await(60L, TimeUnit.SECONDS)) {
            throw new MfcException(MfcExpert.class, 4002, MfcException.CognitiveType.TIMEOUT);
        }
        if (asyncPacket2.exist()) {
            throw ((MfcException) asyncPacket2.get());
        }
        return (String[]) asyncPacket.get();
    }

    public void executeCardRecovery() throws MfcException, InterruptedException {
        Iterator<Card> it = getMFServiceRecoveryCardList().iterator();
        while (it.hasNext()) {
            mfiCardDelete(it.next().getCardInfo().getCid());
        }
    }

    private void mfiStart(final boolean login, final boolean needClearAccount, final FelicaAccess.Layout layout, final MfiStartListener mfiStartListener) throws MfcException {
        this._statusManager.setStartSettings(login, needClearAccount, layout, mfiStartListener);
        this._statusManager.changeState(FeliCaStatusManager.FeliCaState.STARTED);
    }

    private void mfiStart(final boolean login, final boolean needClearAccount, final FelicaAccess.Layout layout, final boolean isBackground, final MfiStartListener mfiStartListener) throws MfcException {
        this._statusManager.setStartSettings(login, needClearAccount, layout, isBackground, mfiStartListener);
        this._statusManager.changeState(FeliCaStatusManager.FeliCaState.STARTED);
    }

    private ASSET_SELECT_TYPE getAssetSelectType(Service service, List<MyCardInfo> cardList) {
        ASSET_SELECT_TYPE asset_select_type = ASSET_SELECT_TYPE.NON_ASSET;
        String serviceType = service.getServiceType();
        if (serviceType != null) {
            if (serviceType.isEmpty()) {
                return ASSET_SELECT_TYPE.COMMON_AREA_NON_CID;
            }
            if (TextUtils.equals(FeliCaParams.SERVICE_ID_SUICA, service.getServiceId())) {
                Iterator<MyCardInfo> it = cardList.iterator();
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
                if (service.isOriginalAreaService()) {
                    if (isFaver4_1()) {
                        return ASSET_SELECT_TYPE.ORIGINAL_AREA_ADD_CID;
                    }
                    return ASSET_SELECT_TYPE.ORIGINAL_AREA_NON_CID;
                }
            } else {
                if (FeliCaParams.SERVICE_ID_SUICA.equals(service.getServiceId())) {
                    return ASSET_SELECT_TYPE.ORIGINAL_AREA_NON_CID;
                }
                return ASSET_SELECT_TYPE.COMMON_AREA_NON_CID;
            }
        }
        return asset_select_type;
    }

    private MyCardInfo.CardPosition getCardPosition(Card card) {
        return getCardPosition(card.getCardInfo().getCardPosition());
    }

    private MyCardInfo.CardPosition getCardPosition(int position) {
        if (position == 0) {
            return MyCardInfo.CardPosition.POSITION_FOREGROUND;
        }
        if (position == 1) {
            return MyCardInfo.CardPosition.POSITION_BACKGROUND;
        }
        if (position == 2) {
            return MyCardInfo.CardPosition.POSITION_PENDING;
        }
        if (position == 3) {
            return MyCardInfo.CardPosition.POSITION_NOT_APPLICABLE;
        }
        if (position != 4) {
            return null;
        }
        return MyCardInfo.CardPosition.POSITION_NOT_EXIST;
    }

    private List<MyCardAdditionalInfo> convertAdditionalList(List<CardAdditionalInfo> addInfoList) throws MfcException {
        ArrayList arrayList = new ArrayList();
        for (CardAdditionalInfo cardAdditionalInfo : addInfoList) {
            String cid = cardAdditionalInfo.getCid();
            if (cid == null) {
                throw new MfcException(getClass(), 2817, MfcException.CognitiveType.ILLEGAL_JSON_FORMAT);
            }
            try {
                DataCheckerUtil.checkEqualLength(cid.length(), 63);
                try {
                    DataCheckerUtil.checkAlphaNumberFormat(cid);
                    arrayList.add(new MyCardAdditionalInfo(cid, this._modelContext, cardAdditionalInfo.getAdditionalInfo(), cardAdditionalInfo.getAdditionalInfoHash()));
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
        switch (AnonymousClass21.$SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.resolve(cardInfo.getServiceId()).ordinal()]) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return false;
            case 3:
            case 4:
                return checkCard(cardInfo, arrayList);
            case 5:
                if ("D00000000000000000000000000000000000000000000000000000000000001".equals(cardInfo.getCid())) {
                    return true;
                }
                return checkCard(cardInfo, arrayList2);
            case 6:
                return checkCard(cardInfo, arrayList2);
            default:
                return false;
        }
    }

    private boolean isEnableCardForMemoryClearOnly(CardInfo cardInfo) {
        ArrayList<Integer[]> arrayList = new ArrayList<Integer[]>() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.13
            {
                add(new Integer[]{1, 0});
                add(new Integer[]{1, 1});
                add(new Integer[]{1, 2});
                add(new Integer[]{0, Integer.MAX_VALUE});
                add(new Integer[]{2, Integer.MAX_VALUE});
            }
        };
        ArrayList<Integer[]> arrayList2 = new ArrayList<Integer[]>() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.14
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
        switch (AnonymousClass21.$SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.resolve(cardInfo.getServiceId()).ordinal()]) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return false;
            case 3:
            case 4:
                return checkCard(cardInfo, arrayList);
            case 5:
                if ("D00000000000000000000000000000000000000000000000000000000000001".equals(cardInfo.getCid())) {
                    return true;
                }
                return checkCard(cardInfo, arrayList2);
            case 6:
                return checkCard(cardInfo, arrayList2);
            default:
                return false;
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
    public List<MyCardInfoWithSp> convertCardInfoWithSpList(List<CardInfoWithSpStatus> cardInfoWithSpStatusList) throws MfmException {
        ArrayList arrayList = new ArrayList();
        for (CardInfoWithSpStatus cardInfoWithSpStatus : cardInfoWithSpStatusList) {
            if (isDeleteNotExist(cardInfoWithSpStatus) && !isUnreissuable(cardInfoWithSpStatus) && !isNotApplicable(cardInfoWithSpStatus)) {
                checkCardInfo(cardInfoWithSpStatus);
                arrayList.add(getMyCardInfoWithSpFromMfiCard(cardInfoWithSpStatus));
            }
        }
        return arrayList;
    }

    private boolean checkCard(CardInfo cardInfo, List<Integer[]> acceptArrayList) {
        int cardStatus = cardInfo.getCardStatus();
        int cardPosition = cardInfo.getCardPosition();
        boolean z = false;
        for (Integer[] numArr : acceptArrayList) {
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
        return new MyCardInfo(card.getCardInfo().getServiceId(), card.getCardInfo().getCid(), card.getCardInfo().getCardCategory(), getCardStatus(card), getCardPosition(card), card.getCardInfo().getAdditionalInfoHash(), null, this._modelContext.getNetworkExpert(), false);
    }

    private MyCardInfo getMyCardInfoFromMfiCard(CardInfo cardInfo) {
        return new MyCardInfo(cardInfo.getServiceId(), cardInfo.getCid(), cardInfo.getCardCategory(), getCardStatus(cardInfo.getCardStatus()), getCardPosition(cardInfo.getCardPosition()), cardInfo.getAdditionalInfoHash(), null, this._modelContext.getNetworkExpert(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MyCardInfo getMyCardInfoFromMfiCard(CachedCard card) {
        return new MyCardInfo(card.getCachedCardInfo().getServiceId(), card.getCachedCardInfo().getCid(), card.getCachedCardInfo().getCardCategory(), getCardStatus(card.getCachedCardInfo().getCardStatus()), getCardPosition(card.getCachedCardInfo().getCardPosition()), card.getCachedCardInfo().getAdditionalInfoHash(), null, this._modelContext.getNetworkExpert(), true);
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
    public boolean isRecovery(CardInfo card) {
        int i = AnonymousClass21.$SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.resolve(card.getServiceId()).ordinal()];
        if (i == 5 || i == 6) {
            return card.getCardStatus() == 2 || card.getCardStatus() == 0 || (card.getCardStatus() == 1 && card.getCardPosition() == 2) || (card.getCardStatus() == 3 && card.getCardPosition() == 2);
        }
        return false;
    }

    private Card getMfiCard(String cid) {
        for (Card card : this.mfiCardList) {
            if (TextUtils.equals(cid, card.getCardInfo().getCid())) {
                return card;
            }
        }
        return null;
    }

    private CachedCard getMfiCachedCard(String cid) {
        for (CachedCard cachedCard : this.mfiCachedCardList) {
            if (TextUtils.equals(cid, cachedCard.getCachedCardInfo().getCid())) {
                return cachedCard;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCard(Card updateCard) {
        for (Card card : this.mfiCardList) {
            if (TextUtils.equals(updateCard.getCardInfo().getCid(), card.getCardInfo().getCid())) {
                List<Card> list = this.mfiCardList;
                list.set(list.indexOf(card), updateCard);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceCard(Card from, Card to) {
        for (Card card : this.mfiCardList) {
            if (TextUtils.equals(from.getCardInfo().getCid(), card.getCardInfo().getCid())) {
                List<Card> list = this.mfiCardList;
                list.set(list.indexOf(card), to);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCard(String cid) {
        for (Card card : this.mfiCardList) {
            if (TextUtils.equals(cid, card.getCardInfo().getCid())) {
                this.mfiCardList.remove(card);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCachedCard(CachedCard updateCard) {
        for (CachedCard cachedCard : this.mfiCachedCardList) {
            if (TextUtils.equals(updateCard.getCachedCardInfo().getCid(), cachedCard.getCachedCardInfo().getCid())) {
                List<CachedCard> list = this.mfiCachedCardList;
                list.set(list.indexOf(cachedCard), updateCard);
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

        void set(T packet) {
            this.packet = packet;
        }

        boolean exist() {
            return this.packet != null;
        }

        T get() {
            return this.packet;
        }
    }

    public RemainedCardInfo getRemainedCards() throws MfcException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        final AsyncPacket asyncPacket2 = new AsyncPacket();
        try {
            MfmFelicaAccess.getMfiAdmin().getRemainedCards(new RemainedCardsEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.15
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.RemainedCardsEventCallback
                public void onSuccess(RemainedCards cards) {
                    asyncPacket.set(cards);
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.RemainedCardsEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int type, String msg) {
                    asyncPacket2.set(new MfcException(MfcExpert.class, 3937, new MficApiCallbackInfo(type, msg)));
                    countDownLatch.countDown();
                }
            });
        } catch (MfiClientException e) {
            asyncPacket2.set(new MfcException(MfcExpert.class, 3938, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket2.set(new MfcException(MfcExpert.class, 3939, e2));
            countDownLatch.countDown();
        }
        try {
            if (!countDownLatch.await(180L, TimeUnit.SECONDS)) {
                throw new MfcException(MfcExpert.class, 3940, MfcException.CognitiveType.TIMEOUT);
            }
        } catch (InterruptedException unused) {
        }
        if (asyncPacket2.exist()) {
            LogUtil.warning((Throwable) asyncPacket2.get());
            throw ((MfcException) asyncPacket2.get());
        }
        this.remainedCards = (RemainedCards) asyncPacket.get();
        RemainedCardInfo remainedCardInfo = new RemainedCardInfo();
        try {
            CardInfo[] cardInfoListToDelete = this.remainedCards.getCardInfoListToDelete();
            CardInfo[] cardInfoListToPermanentDelete = this.remainedCards.getCardInfoListToPermanentDelete();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (CardInfo cardInfo : cardInfoListToDelete) {
                if (checkEnableCard(cardInfo)) {
                    if (isNotDisplayTarget(cardInfo)) {
                        arrayList2.add(getMyCardInfoFromMfiCard(cardInfo));
                    } else {
                        arrayList.add(getMyCardInfoFromMfiCard(cardInfo));
                    }
                }
            }
            remainedCardInfo.setDisplayDeleteCardList(arrayList);
            remainedCardInfo.setNotDisplayDeleteCardList(arrayList2);
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            for (CardInfo cardInfo2 : cardInfoListToPermanentDelete) {
                if (checkEnableCard(cardInfo2)) {
                    if (isNotDisplayTarget(cardInfo2)) {
                        arrayList4.add(getMyCardInfoFromMfiCard(cardInfo2));
                    } else {
                        arrayList3.add(getMyCardInfoFromMfiCard(cardInfo2));
                    }
                }
            }
            remainedCardInfo.setDisplayPermanentDeleteCardList(arrayList3);
            remainedCardInfo.setNotDisplayPermanentDeleteCardList(arrayList4);
            return remainedCardInfo;
        } catch (DataCheckerException e3) {
            LogUtil.warning(e3);
            throw new MfcException(MfcExpert.class, 3941, MfcException.CognitiveType.DATA_CHECK_ERROR);
        }
    }

    public void uploadCardsToDelete() throws MfcException {
        RemainedCards remainedCards = this.remainedCards;
        if (remainedCards == null || remainedCards.getCardInfoListToDelete().length == 0) {
            return;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        try {
            this.remainedCards.uploadCardsToDelete(new CardsUploadEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.16
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardsUploadEventCallback
                public void onSuccess() {
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardsUploadEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int type, String msg) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3953, new MficApiCallbackInfo(type, msg)));
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardsUploadEventCallback
                public void onError(int i, String s, String[] strings) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3954, new MficApiCallbackInfo(i, s)));
                    countDownLatch.countDown();
                }
            });
        } catch (MfiClientException e) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3955, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3956, e2));
            countDownLatch.countDown();
        }
        try {
            if (!countDownLatch.await(180L, TimeUnit.SECONDS)) {
                throw new MfcException(MfcExpert.class, 3957, MfcException.CognitiveType.TIMEOUT);
            }
        } catch (InterruptedException unused) {
        }
        if (asyncPacket.exist()) {
            LogUtil.warning((Throwable) asyncPacket.get());
            throw ((MfcException) asyncPacket.get());
        }
    }

    public void uploadCardsToPermanentDelete() throws MfcException {
        RemainedCards remainedCards = this.remainedCards;
        if (remainedCards == null || remainedCards.getCardInfoListToPermanentDelete().length == 0) {
            return;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        try {
            this.remainedCards.uploadCardsToPermanentDelete(new CardsUploadEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.17
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardsUploadEventCallback
                public void onSuccess() {
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardsUploadEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int type, String msg) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3958, new MficApiCallbackInfo(type, msg)));
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CardsUploadEventCallback
                public void onError(int i, String s, String[] strings) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3959, new MficApiCallbackInfo(i, s)));
                    countDownLatch.countDown();
                }
            });
        } catch (MfiClientException e) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3960, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3961, e2));
            countDownLatch.countDown();
        }
        try {
            if (!countDownLatch.await(180L, TimeUnit.SECONDS)) {
                throw new MfcException(MfcExpert.class, 3968, MfcException.CognitiveType.TIMEOUT);
            }
        } catch (InterruptedException unused) {
        }
        if (asyncPacket.exist()) {
            LogUtil.warning((Throwable) asyncPacket.get());
            throw ((MfcException) asyncPacket.get());
        }
    }

    public void clearMemory() throws MfcException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        try {
            MfmFelicaAccess.getMfiAdmin().clearMemory(new MemoryClearEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.18
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.MemoryClearEventCallback
                public void onSuccess() {
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.MemoryClearEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int type, String msg) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3969, new MficApiCallbackInfo(type, msg)));
                    countDownLatch.countDown();
                }
            });
        } catch (MfiClientException e) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3970, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3971, e2));
            countDownLatch.countDown();
        } catch (Exception unused) {
            asyncPacket.set(new MfcException(MfcExpert.class, 3972, MfcException.CognitiveType.FATAL_ERROR));
            countDownLatch.countDown();
        }
        try {
            if (!countDownLatch.await(180L, TimeUnit.SECONDS)) {
                throw new MfcException(MfcExpert.class, 3973, MfcException.CognitiveType.TIMEOUT);
            }
        } catch (InterruptedException unused2) {
        }
        if (asyncPacket.exist()) {
            LogUtil.warning((Throwable) asyncPacket.get());
            throw ((MfcException) asyncPacket.get());
        }
    }

    private boolean isNotDisplayTarget(CardInfo card) {
        int i = AnonymousClass21.$SwitchMap$com$felicanetworks$mfm$main$policy$service$SupportServiceType[SupportServiceType.resolve(card.getServiceId()).ordinal()];
        if (i == 3 || i == 4) {
            return card.getCardStatus() == 2 || card.getCardStatus() == 0;
        }
        if (i == 5 || i == 6) {
            return card.getCardStatus() == 2 || card.getCardStatus() == 0 || (card.getCardStatus() == 1 && card.getCardPosition() == 2) || (card.getCardStatus() == 3 && card.getCardPosition() == 2);
        }
        return false;
    }

    private boolean checkEnableCard(CardInfo card) throws DataCheckerException {
        if (!ServiceIdPolicy.isAllowed(card.getServiceId()) || !isEnableCardForMemoryClearOnly(card)) {
            return false;
        }
        checkCardInfo(card);
        return true;
    }

    public CardInfos fetchCacheCardList(boolean integrityCheck) throws MfcException {
        final CardInfos cardInfos = new CardInfos();
        final AsyncPacket asyncPacket = new AsyncPacket();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            this._mfmFelicaAccess.getCachedCardList(integrityCheck, new CachedCardListEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.19
                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CachedCardListEventCallback
                public void onSuccess(CachedCard[] cards) {
                    try {
                        MfcExpert.this.mfiCachedCardList.clear();
                        for (CachedCard cachedCard : ServiceIdPolicy.filter(cards, new ServiceIdPolicy.ServiceIdSelector<CachedCard>() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.19.1
                            /* JADX DEBUG: Method merged with bridge method: sid(Ljava/lang/Object;)Ljava/lang/String; */
                            @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdSelector
                            public String sid(CachedCard item) {
                                return item.getCachedCardInfo().getServiceId();
                            }
                        })) {
                            CachedCardInfo cachedCardInfo = cachedCard.getCachedCardInfo();
                            if (MfcExpert.this.isEnableCard(cachedCardInfo)) {
                                MfcExpert.this.checkCardInfo(cachedCardInfo);
                                MfcExpert.this.mfiCachedCardList.add(cachedCard);
                                if (!MfcExpert.this.isRecovery(cachedCard.getCachedCardInfo())) {
                                    MyCardInfo myCardInfoFromMfiCard = MfcExpert.this.getMyCardInfoFromMfiCard(cachedCard);
                                    if (MfcExpert.this.isDeleteNotExist(cachedCard.getCachedCardInfo())) {
                                        cardInfos.getDeleteCardInfoList().add(myCardInfoFromMfiCard);
                                    } else {
                                        cardInfos.getCardInfoList().add(myCardInfoFromMfiCard);
                                    }
                                }
                            }
                        }
                    } catch (DataCheckerException e) {
                        LogUtil.warning(e);
                        asyncPacket.set(new MfcException(MfcExpert.class, 4067, MfcException.CognitiveType.DATA_CHECK_ERROR));
                    }
                    countDownLatch.countDown();
                }

                @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CachedCardListEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
                public void onError(int type, String msg) {
                    asyncPacket.set(new MfcException(MfcExpert.class, 4065, new MficApiCallbackInfo(type, msg)));
                    countDownLatch.countDown();
                }
            });
        } catch (MfiClientException e) {
            asyncPacket.set(new MfcException(MfcExpert.class, 4066, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket.set(new MfcException(MfcExpert.class, 4068, e2));
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

    public void finishBackground() {
        this._statusManager.finishBackground();
    }

    public void mfiCachedCardEnable(String cid) throws MfcException {
        CachedCard mfiCachedCard;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AsyncPacket asyncPacket = new AsyncPacket();
        try {
            mfiCachedCard = getMfiCachedCard(cid);
        } catch (MfiClientException e) {
            asyncPacket.set(new MfcException(FelicaAccess.class, 3878, e));
            countDownLatch.countDown();
        } catch (IllegalArgumentException e2) {
            asyncPacket.set(new MfcException(FelicaAccess.class, 3879, e2));
            countDownLatch.countDown();
        }
        if (mfiCachedCard == null) {
            throw new MfcException(MfcExpert.class, 3873, MfcException.CognitiveType.ILLEGAL_STATE);
        }
        mfiCachedCard.enable(new CachedCardEnableEventCallback() { // from class: com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.20
            @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CachedCardEnableEventCallback
            public void onSuccess(CachedCard enableCard, CachedCard disableCard) {
                if (enableCard != null) {
                    MfcExpert.this.updateCachedCard(enableCard);
                    if (disableCard != null) {
                        MfcExpert.this.updateCachedCard(disableCard);
                    }
                } else {
                    asyncPacket.set(new MfcException(MfcExpert.class, 3874, MfcException.CognitiveType.ILLEGAL_STATE));
                }
                countDownLatch.countDown();
            }

            @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.CachedCardEnableEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
            public void onError(int type, String msg) {
                asyncPacket.set(new MfcException(MfcExpert.class, 3875, new MficApiCallbackInfo(type, msg)));
                countDownLatch.countDown();
            }
        });
        try {
            if (!countDownLatch.await(60L, TimeUnit.SECONDS)) {
                throw new MfcException(MfcExpert.class, 3885, MfcException.CognitiveType.TIMEOUT);
            }
        } catch (InterruptedException unused) {
        }
        if (asyncPacket.exist()) {
            throw ((MfcException) asyncPacket.get());
        }
    }

    public void cancelBackgroundUpdate() {
        this._statusManager.cancelCardOperation();
        this._modelContext.shutdownMfiCardFuncRunner();
        this._modelContext.shutdownCardDetailFuncRunner();
        this._modelContext.shutdownOrderUpdateCacheRunner();
        this._statusManager.forceFinish();
    }
}

package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.DeviceList;
import com.felicanetworks.mfc.FelicaResultInfo;
import com.felicanetworks.mfc.FelicaResultInfoBlockCountInformationArray;
import com.felicanetworks.mfc.FelicaResultInfoBoolean;
import com.felicanetworks.mfc.FelicaResultInfoByteArray;
import com.felicanetworks.mfc.FelicaResultInfoDataArray;
import com.felicanetworks.mfc.FelicaResultInfoInt;
import com.felicanetworks.mfc.FelicaResultInfoIntArray;
import com.felicanetworks.mfc.FelicaResultInfoKeyInformationArray;
import com.felicanetworks.mfc.FelicaResultInfoNodeInformation;
import com.felicanetworks.mfc.IFSCEventListener;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.IFelicaPushAppNotificationListener;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.PushSegmentParcelableWrapper;
import com.felicanetworks.mfc.mfi.ICachedCardEnableEventCallback;
import com.felicanetworks.mfc.mfi.ICardAccessEventCallback;
import com.felicanetworks.mfc.mfi.ICardAdditionalInfoListEventCallback;
import com.felicanetworks.mfc.mfi.ICardDeleteEventCallback;
import com.felicanetworks.mfc.mfi.ICardDeleteV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardDisableEventCallback;
import com.felicanetworks.mfc.mfi.ICardDisableV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardEnableEventCallback;
import com.felicanetworks.mfc.mfi.ICardEnableV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardIssueEventCallback;
import com.felicanetworks.mfc.mfi.ICardIssueV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardKeyMigrateEventCallback;
import com.felicanetworks.mfc.mfi.ICardListEventCallback;
import com.felicanetworks.mfc.mfi.ICardReissuableDeleteEventCallback;
import com.felicanetworks.mfc.mfi.ICardsUploadEventCallback;
import com.felicanetworks.mfc.mfi.IDataListEventCallback;
import com.felicanetworks.mfc.mfi.IGoogleTosGetEventCallback;
import com.felicanetworks.mfc.mfi.IInitializedEventCallback;
import com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback;
import com.felicanetworks.mfc.mfi.ILoginEventCallback;
import com.felicanetworks.mfc.mfi.ILogoutEventCallback;
import com.felicanetworks.mfc.mfi.IMemoryClearEventCallback;
import com.felicanetworks.mfc.mfi.IMfiTosDataGetEventCallback;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
import com.felicanetworks.mfc.mfi.IRemainedCardsEventCallback;
import com.felicanetworks.mfc.mfi.IServerOperationEventCallback;
import com.felicanetworks.mfc.mfi.ISilentStartEventCallback;
import com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback;
import com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback;
import com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback;

/* JADX INFO: loaded from: classes3.dex */
public interface IMfiFelica extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.IMfiFelica";

    public static class Default implements IMfiFelica {
        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo access(String cardInfoJson, String linkageData, ICardAccessEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo activateFelica(String packageName, IFelicaEventListener listener) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo cancelCardOperation() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo cancelMfiOffline() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo cancelOffline() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoBoolean checkAndRecoverCrsState() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoInt checkChipFormatting() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo checkOnlineAccess() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo clearMemory(IMemoryClearEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo clearMemoryV2(IMemoryClearEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo clearMfiAccount() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo close() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo delete(CardInfo cardInfo, String linkageData, ICardDeleteEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo deleteUnsupportMfiService1Card(String linkageData, IUnsupportMfiService1CardDeleteEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo deleteV2(String cardInfoJson, String linkageData, ICardDeleteV2EventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo disable(CardInfo cardInfo, ICardDisableEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo disableV2(String cardInfoJson, ICardDisableV2EventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo enable(CardInfo cardInfo, ICardEnableEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo enableCachedCard(String cachedCardInfoJson, ICachedCardEnableEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo enableV2(String cardInfoJson, ICardEnableV2EventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoByteArray executeFelicaCommand(byte[] commandPacket, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoInt existEmptySlot() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoBoolean existService() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo existUnsupportMfiService1Card(String serviceId, IUnsupportMfiService1CardExistEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] nodeCodeList, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getCachedCardList(IDataListEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getCachedCardListWithIntegrityCheck(IDataListEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getCardAdditionalInfoList(ICardAdditionalInfoListEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getCardAdditionalInfoListV2(IPipeEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getCardAdditionalInfoListV3(String[] cidList, IDataListEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getCardAdditionalInfoListWithCidList(String[] cidList, IPipeEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getCardInfoListWithSpStatus(String serviceId, IPipeEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getCardInfoListWithSpStatusV3(String serviceId, IDataListEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getCardList(ICardListEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getCardListV2(IPipeEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getCardListV3(IDataListEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoByteArray getContainerId(int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoByteArray getContainerIssueInformation(int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoString getCurrentAccountHash() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getGoogleTos(int code, IGoogleTosGetEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoByteArray getICCode() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoByteArray getIDm() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoInt getInterface() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoInt getKeyVersion(int serviceCode, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] nodeCodeList, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getLinkageDataList(int actionType, String[] cid, ILinkageDataListEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoStringArray getLocalCidList() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoStringArray getLocalCidListV2() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoStringArray getLocalPartialCardInfoList(String serviceId) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoStringArray getLocalPartialCardInfoListForList(String[] serviceIdList) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getMfiTosData(int code, IMfiTosDataGetEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoNodeInformation getNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoNodeInformation getPrivacyNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoBoolean getRFSState() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getRemainedCards(IRemainedCardsEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo getRemainedCardsV2(IRemainedCardsEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoSeInfo getSeInfomation() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoInt getSelectTimeout() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoInt getSystemCode() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoIntArray getSystemCodeList(int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoInt getUnsupportMfiService1CardPosition() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoInt identifyService() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo inactivateFelica() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo initialize(String linkageData, IInitializedEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoBoolean isChipInitialized() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoBoolean isLoginedAccount(String accountName) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo issueCard(String linkageData, ICardIssueEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo issueCardV2(String linkageData, ICardIssueV2EventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo issueCardWithOtp(String linkageData, String otp, ICardIssueV2EventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo login(String accountIssuer, String accountName, ILoginEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo logout(boolean autoMfiServerLogout, ILogoutEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo migrateCardKey(String serviceId, ICardKeyMigrateEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public void notifyError(String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public void notifyResult(byte[] result) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo open() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo provisionServerOperation(IServerOperationEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo push(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfoDataArray read(BlockList blockList, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo reset() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo saveDelete(String cardInfoJson, String linkageData, ICardReissuableDeleteEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo select(int systemCode) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo selectWithCid(int systemCode, String cid) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo selectWithTarget(int target, int systemCode) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo setMfiTosAgreement(int code) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo setNodeCodeSize(int nodeCodeSize, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingData, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener listener, String appIdentificationCode) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo setSelectTimeout(int timeout) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo silentStart(String accountIssuer, String accountName, int code, ISilentStartEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo silentStartForMfiAdmin(String accountIssuer, String accountName, boolean login, int code, ISilentStartForMfiAdminEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo silentStartForMfiAdminV2(String accountIssuer, String accountName, boolean login, int code, int layoutType, ISilentStartForMfiAdminEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo silentStartForMfiAdminV3(String accountIssuer, String accountName, boolean login, boolean skipCheckChipInitialized, int code, ISilentStartForMfiAdminEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo start(String url, DeviceList deviceList, IFSCEventListener fscEventListener, IMfiFelica felica) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public void stop() throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo uploadCardsToDelete(ICardsUploadEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo uploadCardsToDisable(ICardsUploadEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo uploadCardsToPermanentDelete(ICardsUploadEventCallback callback) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiFelica
        public FelicaResultInfo write(BlockDataList blockDataList, int timeout, int retry) throws RemoteException {
            return null;
        }
    }

    FelicaResultInfo access(String cardInfoJson, String linkageData, ICardAccessEventCallback callback) throws RemoteException;

    FelicaResultInfo activateFelica(String packageName, IFelicaEventListener listener) throws RemoteException;

    FelicaResultInfo cancelCardOperation() throws RemoteException;

    FelicaResultInfo cancelMfiOffline() throws RemoteException;

    FelicaResultInfo cancelOffline() throws RemoteException;

    FelicaResultInfoBoolean checkAndRecoverCrsState() throws RemoteException;

    FelicaResultInfoInt checkChipFormatting() throws RemoteException;

    FelicaResultInfo checkOnlineAccess() throws RemoteException;

    FelicaResultInfo clearMemory(IMemoryClearEventCallback callback) throws RemoteException;

    FelicaResultInfo clearMemoryV2(IMemoryClearEventCallback callback) throws RemoteException;

    FelicaResultInfo clearMfiAccount() throws RemoteException;

    FelicaResultInfo close() throws RemoteException;

    FelicaResultInfo delete(CardInfo cardInfo, String linkageData, ICardDeleteEventCallback callback) throws RemoteException;

    FelicaResultInfo deleteUnsupportMfiService1Card(String linkageData, IUnsupportMfiService1CardDeleteEventCallback callback) throws RemoteException;

    FelicaResultInfo deleteV2(String cardInfoJson, String linkageData, ICardDeleteV2EventCallback callback) throws RemoteException;

    FelicaResultInfo disable(CardInfo cardInfo, ICardDisableEventCallback callback) throws RemoteException;

    FelicaResultInfo disableV2(String cardInfoJson, ICardDisableV2EventCallback callback) throws RemoteException;

    FelicaResultInfo enable(CardInfo cardInfo, ICardEnableEventCallback callback) throws RemoteException;

    FelicaResultInfo enableCachedCard(String cachedCardInfoJson, ICachedCardEnableEventCallback callback) throws RemoteException;

    FelicaResultInfo enableV2(String cardInfoJson, ICardEnableV2EventCallback callback) throws RemoteException;

    FelicaResultInfoByteArray executeFelicaCommand(byte[] commandPacket, int timeout, int retry) throws RemoteException;

    FelicaResultInfoInt existEmptySlot() throws RemoteException;

    FelicaResultInfoBoolean existService() throws RemoteException;

    FelicaResultInfo existUnsupportMfiService1Card(String serviceId, IUnsupportMfiService1CardExistEventCallback callback) throws RemoteException;

    FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] nodeCodeList, int timeout, int retry) throws RemoteException;

    FelicaResultInfo getCachedCardList(IDataListEventCallback callback) throws RemoteException;

    FelicaResultInfo getCachedCardListWithIntegrityCheck(IDataListEventCallback callback) throws RemoteException;

    FelicaResultInfo getCardAdditionalInfoList(ICardAdditionalInfoListEventCallback callback) throws RemoteException;

    FelicaResultInfo getCardAdditionalInfoListV2(IPipeEventCallback callback) throws RemoteException;

    FelicaResultInfo getCardAdditionalInfoListV3(String[] cidList, IDataListEventCallback callback) throws RemoteException;

    FelicaResultInfo getCardAdditionalInfoListWithCidList(String[] cidList, IPipeEventCallback callback) throws RemoteException;

    FelicaResultInfo getCardInfoListWithSpStatus(String serviceId, IPipeEventCallback callback) throws RemoteException;

    FelicaResultInfo getCardInfoListWithSpStatusV3(String serviceId, IDataListEventCallback callback) throws RemoteException;

    FelicaResultInfo getCardList(ICardListEventCallback callback) throws RemoteException;

    FelicaResultInfo getCardListV2(IPipeEventCallback callback) throws RemoteException;

    FelicaResultInfo getCardListV3(IDataListEventCallback callback) throws RemoteException;

    FelicaResultInfoByteArray getContainerId(int timeout, int retry) throws RemoteException;

    FelicaResultInfoByteArray getContainerIssueInformation(int timeout, int retry) throws RemoteException;

    FelicaResultInfoString getCurrentAccountHash() throws RemoteException;

    FelicaResultInfo getGoogleTos(int code, IGoogleTosGetEventCallback callback) throws RemoteException;

    FelicaResultInfoByteArray getICCode() throws RemoteException;

    FelicaResultInfoByteArray getIDm() throws RemoteException;

    FelicaResultInfoInt getInterface() throws RemoteException;

    FelicaResultInfoInt getKeyVersion(int serviceCode, int timeout, int retry) throws RemoteException;

    FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] nodeCodeList, int timeout, int retry) throws RemoteException;

    FelicaResultInfo getLinkageDataList(int actionType, String[] cid, ILinkageDataListEventCallback callback) throws RemoteException;

    FelicaResultInfoStringArray getLocalCidList() throws RemoteException;

    FelicaResultInfoStringArray getLocalCidListV2() throws RemoteException;

    FelicaResultInfoStringArray getLocalPartialCardInfoList(String serviceId) throws RemoteException;

    FelicaResultInfoStringArray getLocalPartialCardInfoListForList(String[] serviceIdList) throws RemoteException;

    FelicaResultInfo getMfiTosData(int code, IMfiTosDataGetEventCallback callback) throws RemoteException;

    FelicaResultInfoNodeInformation getNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException;

    FelicaResultInfoNodeInformation getPrivacyNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException;

    FelicaResultInfoBoolean getRFSState() throws RemoteException;

    FelicaResultInfo getRemainedCards(IRemainedCardsEventCallback callback) throws RemoteException;

    FelicaResultInfo getRemainedCardsV2(IRemainedCardsEventCallback callback) throws RemoteException;

    FelicaResultInfoSeInfo getSeInfomation() throws RemoteException;

    FelicaResultInfoInt getSelectTimeout() throws RemoteException;

    FelicaResultInfoInt getSystemCode() throws RemoteException;

    FelicaResultInfoIntArray getSystemCodeList(int timeout, int retry) throws RemoteException;

    FelicaResultInfoInt getUnsupportMfiService1CardPosition() throws RemoteException;

    FelicaResultInfoInt identifyService() throws RemoteException;

    FelicaResultInfo inactivateFelica() throws RemoteException;

    FelicaResultInfo initialize(String linkageData, IInitializedEventCallback callback) throws RemoteException;

    FelicaResultInfoBoolean isChipInitialized() throws RemoteException;

    FelicaResultInfoBoolean isLoginedAccount(String accountName) throws RemoteException;

    FelicaResultInfo issueCard(String linkageData, ICardIssueEventCallback callback) throws RemoteException;

    FelicaResultInfo issueCardV2(String linkageData, ICardIssueV2EventCallback callback) throws RemoteException;

    FelicaResultInfo issueCardWithOtp(String linkageData, String otp, ICardIssueV2EventCallback callback) throws RemoteException;

    FelicaResultInfo login(String accountIssuer, String accountName, ILoginEventCallback callback) throws RemoteException;

    FelicaResultInfo logout(boolean autoMfiServerLogout, ILogoutEventCallback callback) throws RemoteException;

    FelicaResultInfo migrateCardKey(String serviceId, ICardKeyMigrateEventCallback callback) throws RemoteException;

    void notifyError(String msg) throws RemoteException;

    void notifyResult(byte[] result) throws RemoteException;

    FelicaResultInfo open() throws RemoteException;

    FelicaResultInfo provisionServerOperation(IServerOperationEventCallback callback) throws RemoteException;

    FelicaResultInfo push(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) throws RemoteException;

    FelicaResultInfoDataArray read(BlockList blockList, int timeout, int retry) throws RemoteException;

    FelicaResultInfo reset() throws RemoteException;

    FelicaResultInfo saveDelete(String cardInfoJson, String linkageData, ICardReissuableDeleteEventCallback callback) throws RemoteException;

    FelicaResultInfo select(int systemCode) throws RemoteException;

    FelicaResultInfo selectWithCid(int systemCode, String cid) throws RemoteException;

    FelicaResultInfo selectWithTarget(int target, int systemCode) throws RemoteException;

    FelicaResultInfo setMfiTosAgreement(int code) throws RemoteException;

    FelicaResultInfo setNodeCodeSize(int nodeCodeSize, int timeout, int retry) throws RemoteException;

    FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingData, int timeout, int retry) throws RemoteException;

    FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener listener, String appIdentificationCode) throws RemoteException;

    FelicaResultInfo setSelectTimeout(int timeout) throws RemoteException;

    FelicaResultInfo silentStart(String accountIssuer, String accountName, int code, ISilentStartEventCallback callback) throws RemoteException;

    FelicaResultInfo silentStartForMfiAdmin(String accountIssuer, String accountName, boolean login, int code, ISilentStartForMfiAdminEventCallback callback) throws RemoteException;

    FelicaResultInfo silentStartForMfiAdminV2(String accountIssuer, String accountName, boolean login, int code, int layoutType, ISilentStartForMfiAdminEventCallback callback) throws RemoteException;

    FelicaResultInfo silentStartForMfiAdminV3(String accountIssuer, String accountName, boolean login, boolean skipCheckChipInitialized, int code, ISilentStartForMfiAdminEventCallback callback) throws RemoteException;

    FelicaResultInfo start(String url, DeviceList deviceList, IFSCEventListener fscEventListener, IMfiFelica felica) throws RemoteException;

    void stop() throws RemoteException;

    FelicaResultInfo uploadCardsToDelete(ICardsUploadEventCallback callback) throws RemoteException;

    FelicaResultInfo uploadCardsToDisable(ICardsUploadEventCallback callback) throws RemoteException;

    FelicaResultInfo uploadCardsToPermanentDelete(ICardsUploadEventCallback callback) throws RemoteException;

    FelicaResultInfo write(BlockDataList blockDataList, int timeout, int retry) throws RemoteException;

    public static abstract class Stub extends Binder implements IMfiFelica {
        static final int TRANSACTION_access = 60;
        static final int TRANSACTION_activateFelica = 1;
        static final int TRANSACTION_cancelCardOperation = 43;
        static final int TRANSACTION_cancelMfiOffline = 69;
        static final int TRANSACTION_cancelOffline = 29;
        static final int TRANSACTION_checkAndRecoverCrsState = 93;
        static final int TRANSACTION_checkChipFormatting = 54;
        static final int TRANSACTION_checkOnlineAccess = 26;
        static final int TRANSACTION_clearMemory = 92;
        static final int TRANSACTION_clearMemoryV2 = 96;
        static final int TRANSACTION_clearMfiAccount = 34;
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_delete = 42;
        static final int TRANSACTION_deleteUnsupportMfiService1Card = 77;
        static final int TRANSACTION_deleteV2 = 58;
        static final int TRANSACTION_disable = 41;
        static final int TRANSACTION_disableV2 = 57;
        static final int TRANSACTION_enable = 40;
        static final int TRANSACTION_enableCachedCard = 80;
        static final int TRANSACTION_enableV2 = 56;
        static final int TRANSACTION_executeFelicaCommand = 30;
        static final int TRANSACTION_existEmptySlot = 78;
        static final int TRANSACTION_existService = 82;
        static final int TRANSACTION_existUnsupportMfiService1Card = 76;
        static final int TRANSACTION_getBlockCountInformation = 3;
        static final int TRANSACTION_getCachedCardList = 79;
        static final int TRANSACTION_getCachedCardListWithIntegrityCheck = 97;
        static final int TRANSACTION_getCardAdditionalInfoList = 38;
        static final int TRANSACTION_getCardAdditionalInfoListV2 = 52;
        static final int TRANSACTION_getCardAdditionalInfoListV3 = 73;
        static final int TRANSACTION_getCardAdditionalInfoListWithCidList = 62;
        static final int TRANSACTION_getCardInfoListWithSpStatus = 61;
        static final int TRANSACTION_getCardInfoListWithSpStatusV3 = 72;
        static final int TRANSACTION_getCardList = 37;
        static final int TRANSACTION_getCardListV2 = 51;
        static final int TRANSACTION_getCardListV3 = 71;
        static final int TRANSACTION_getContainerId = 4;
        static final int TRANSACTION_getContainerIssueInformation = 5;
        static final int TRANSACTION_getCurrentAccountHash = 33;
        static final int TRANSACTION_getGoogleTos = 86;
        static final int TRANSACTION_getICCode = 6;
        static final int TRANSACTION_getIDm = 7;
        static final int TRANSACTION_getInterface = 8;
        static final int TRANSACTION_getKeyVersion = 9;
        static final int TRANSACTION_getKeyVersionV2 = 31;
        static final int TRANSACTION_getLinkageDataList = 49;
        static final int TRANSACTION_getLocalCidList = 65;
        static final int TRANSACTION_getLocalCidListV2 = 70;
        static final int TRANSACTION_getLocalPartialCardInfoList = 75;
        static final int TRANSACTION_getLocalPartialCardInfoListForList = 87;
        static final int TRANSACTION_getMfiTosData = 84;
        static final int TRANSACTION_getNodeInformation = 10;
        static final int TRANSACTION_getPrivacyNodeInformation = 11;
        static final int TRANSACTION_getRFSState = 12;
        static final int TRANSACTION_getRemainedCards = 89;
        static final int TRANSACTION_getRemainedCardsV2 = 94;
        static final int TRANSACTION_getSeInfomation = 35;
        static final int TRANSACTION_getSelectTimeout = 28;
        static final int TRANSACTION_getSystemCode = 13;
        static final int TRANSACTION_getSystemCodeList = 14;
        static final int TRANSACTION_getUnsupportMfiService1CardPosition = 68;
        static final int TRANSACTION_identifyService = 66;
        static final int TRANSACTION_inactivateFelica = 15;
        static final int TRANSACTION_initialize = 50;
        static final int TRANSACTION_isChipInitialized = 83;
        static final int TRANSACTION_isLoginedAccount = 81;
        static final int TRANSACTION_issueCard = 39;
        static final int TRANSACTION_issueCardV2 = 55;
        static final int TRANSACTION_issueCardWithOtp = 59;
        static final int TRANSACTION_login = 32;
        static final int TRANSACTION_logout = 36;
        static final int TRANSACTION_migrateCardKey = 98;
        static final int TRANSACTION_notifyError = 47;
        static final int TRANSACTION_notifyResult = 46;
        static final int TRANSACTION_open = 16;
        static final int TRANSACTION_provisionServerOperation = 74;
        static final int TRANSACTION_push = 17;
        static final int TRANSACTION_read = 18;
        static final int TRANSACTION_reset = 19;
        static final int TRANSACTION_saveDelete = 63;
        static final int TRANSACTION_select = 20;
        static final int TRANSACTION_selectWithCid = 64;
        static final int TRANSACTION_selectWithTarget = 21;
        static final int TRANSACTION_setMfiTosAgreement = 85;
        static final int TRANSACTION_setNodeCodeSize = 25;
        static final int TRANSACTION_setPrivacy = 22;
        static final int TRANSACTION_setPushNotificationListener = 24;
        static final int TRANSACTION_setSelectTimeout = 27;
        static final int TRANSACTION_silentStart = 53;
        static final int TRANSACTION_silentStartForMfiAdmin = 48;
        static final int TRANSACTION_silentStartForMfiAdminV2 = 67;
        static final int TRANSACTION_silentStartForMfiAdminV3 = 88;
        static final int TRANSACTION_start = 44;
        static final int TRANSACTION_stop = 45;
        static final int TRANSACTION_uploadCardsToDelete = 90;
        static final int TRANSACTION_uploadCardsToDisable = 91;
        static final int TRANSACTION_uploadCardsToPermanentDelete = 95;
        static final int TRANSACTION_write = 23;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IMfiFelica.DESCRIPTOR);
        }

        public static IMfiFelica asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IMfiFelica.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMfiFelica)) {
                return (IMfiFelica) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IMfiFelica.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMfiFelica.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    FelicaResultInfo felicaResultInfoActivateFelica = activateFelica(data.readString(), IFelicaEventListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoActivateFelica, 1);
                    return true;
                case 2:
                    FelicaResultInfo felicaResultInfoClose = close();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoClose, 1);
                    return true;
                case 3:
                    FelicaResultInfoBlockCountInformationArray blockCountInformation = getBlockCountInformation(data.createIntArray(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, blockCountInformation, 1);
                    return true;
                case 4:
                    FelicaResultInfoByteArray containerId = getContainerId(data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, containerId, 1);
                    return true;
                case 5:
                    FelicaResultInfoByteArray containerIssueInformation = getContainerIssueInformation(data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, containerIssueInformation, 1);
                    return true;
                case 6:
                    FelicaResultInfoByteArray iCCode = getICCode();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, iCCode, 1);
                    return true;
                case 7:
                    FelicaResultInfoByteArray iDm = getIDm();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, iDm, 1);
                    return true;
                case 8:
                    FelicaResultInfoInt felicaResultInfoInt = getInterface();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoInt, 1);
                    return true;
                case 9:
                    FelicaResultInfoInt keyVersion = getKeyVersion(data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, keyVersion, 1);
                    return true;
                case 10:
                    FelicaResultInfoNodeInformation nodeInformation = getNodeInformation(data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, nodeInformation, 1);
                    return true;
                case 11:
                    FelicaResultInfoNodeInformation privacyNodeInformation = getPrivacyNodeInformation(data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, privacyNodeInformation, 1);
                    return true;
                case 12:
                    FelicaResultInfoBoolean rFSState = getRFSState();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, rFSState, 1);
                    return true;
                case 13:
                    FelicaResultInfoInt systemCode = getSystemCode();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, systemCode, 1);
                    return true;
                case 14:
                    FelicaResultInfoIntArray systemCodeList = getSystemCodeList(data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, systemCodeList, 1);
                    return true;
                case 15:
                    FelicaResultInfo felicaResultInfoInactivateFelica = inactivateFelica();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoInactivateFelica, 1);
                    return true;
                case 16:
                    FelicaResultInfo felicaResultInfoOpen = open();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoOpen, 1);
                    return true;
                case 17:
                    FelicaResultInfo felicaResultInfoPush = push((PushSegmentParcelableWrapper) _Parcel.readTypedObject(data, PushSegmentParcelableWrapper.CREATOR));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoPush, 1);
                    return true;
                case 18:
                    FelicaResultInfoDataArray felicaResultInfoDataArray = read((BlockList) _Parcel.readTypedObject(data, BlockList.CREATOR), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoDataArray, 1);
                    return true;
                case 19:
                    FelicaResultInfo felicaResultInfoReset = reset();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoReset, 1);
                    return true;
                case 20:
                    FelicaResultInfo felicaResultInfoSelect = select(data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoSelect, 1);
                    return true;
                case 21:
                    FelicaResultInfo felicaResultInfoSelectWithTarget = selectWithTarget(data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoSelectWithTarget, 1);
                    return true;
                case 22:
                    FelicaResultInfo privacy = setPrivacy((PrivacySettingData[]) data.createTypedArray(PrivacySettingData.CREATOR), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, privacy, 1);
                    return true;
                case 23:
                    FelicaResultInfo felicaResultInfoWrite = write((BlockDataList) _Parcel.readTypedObject(data, BlockDataList.CREATOR), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoWrite, 1);
                    return true;
                case 24:
                    FelicaResultInfo pushNotificationListener = setPushNotificationListener(IFelicaPushAppNotificationListener.Stub.asInterface(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, pushNotificationListener, 1);
                    return true;
                case 25:
                    FelicaResultInfo nodeCodeSize = setNodeCodeSize(data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, nodeCodeSize, 1);
                    return true;
                case 26:
                    FelicaResultInfo felicaResultInfoCheckOnlineAccess = checkOnlineAccess();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoCheckOnlineAccess, 1);
                    return true;
                case 27:
                    FelicaResultInfo selectTimeout = setSelectTimeout(data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, selectTimeout, 1);
                    return true;
                case 28:
                    FelicaResultInfoInt selectTimeout2 = getSelectTimeout();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, selectTimeout2, 1);
                    return true;
                case 29:
                    FelicaResultInfo felicaResultInfoCancelOffline = cancelOffline();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoCancelOffline, 1);
                    return true;
                case 30:
                    FelicaResultInfoByteArray felicaResultInfoByteArrayExecuteFelicaCommand = executeFelicaCommand(data.createByteArray(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoByteArrayExecuteFelicaCommand, 1);
                    return true;
                case 31:
                    FelicaResultInfoKeyInformationArray keyVersionV2 = getKeyVersionV2(data.createIntArray(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, keyVersionV2, 1);
                    return true;
                case 32:
                    FelicaResultInfo felicaResultInfoLogin = login(data.readString(), data.readString(), ILoginEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoLogin, 1);
                    return true;
                case 33:
                    FelicaResultInfoString currentAccountHash = getCurrentAccountHash();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, currentAccountHash, 1);
                    return true;
                case 34:
                    FelicaResultInfo felicaResultInfoClearMfiAccount = clearMfiAccount();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoClearMfiAccount, 1);
                    return true;
                case 35:
                    FelicaResultInfoSeInfo seInfomation = getSeInfomation();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, seInfomation, 1);
                    return true;
                case 36:
                    FelicaResultInfo felicaResultInfoLogout = logout(data.readInt() != 0, ILogoutEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoLogout, 1);
                    return true;
                case 37:
                    FelicaResultInfo cardList = getCardList(ICardListEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, cardList, 1);
                    return true;
                case 38:
                    FelicaResultInfo cardAdditionalInfoList = getCardAdditionalInfoList(ICardAdditionalInfoListEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, cardAdditionalInfoList, 1);
                    return true;
                case 39:
                    FelicaResultInfo felicaResultInfoIssueCard = issueCard(data.readString(), ICardIssueEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoIssueCard, 1);
                    return true;
                case 40:
                    FelicaResultInfo felicaResultInfoEnable = enable((CardInfo) _Parcel.readTypedObject(data, CardInfo.CREATOR), ICardEnableEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoEnable, 1);
                    return true;
                case 41:
                    FelicaResultInfo felicaResultInfoDisable = disable((CardInfo) _Parcel.readTypedObject(data, CardInfo.CREATOR), ICardDisableEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoDisable, 1);
                    return true;
                case 42:
                    FelicaResultInfo felicaResultInfoDelete = delete((CardInfo) _Parcel.readTypedObject(data, CardInfo.CREATOR), data.readString(), ICardDeleteEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoDelete, 1);
                    return true;
                case 43:
                    FelicaResultInfo felicaResultInfoCancelCardOperation = cancelCardOperation();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoCancelCardOperation, 1);
                    return true;
                case 44:
                    FelicaResultInfo felicaResultInfoStart = start(data.readString(), (DeviceList) _Parcel.readTypedObject(data, DeviceList.CREATOR), IFSCEventListener.Stub.asInterface(data.readStrongBinder()), asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoStart, 1);
                    return true;
                case 45:
                    stop();
                    reply.writeNoException();
                    return true;
                case 46:
                    notifyResult(data.createByteArray());
                    reply.writeNoException();
                    return true;
                case 47:
                    notifyError(data.readString());
                    reply.writeNoException();
                    return true;
                case 48:
                    FelicaResultInfo felicaResultInfoSilentStartForMfiAdmin = silentStartForMfiAdmin(data.readString(), data.readString(), data.readInt() != 0, data.readInt(), ISilentStartForMfiAdminEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoSilentStartForMfiAdmin, 1);
                    return true;
                case 49:
                    FelicaResultInfo linkageDataList = getLinkageDataList(data.readInt(), data.createStringArray(), ILinkageDataListEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, linkageDataList, 1);
                    return true;
                case 50:
                    FelicaResultInfo felicaResultInfoInitialize = initialize(data.readString(), IInitializedEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoInitialize, 1);
                    return true;
                case 51:
                    FelicaResultInfo cardListV2 = getCardListV2(IPipeEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, cardListV2, 1);
                    return true;
                case 52:
                    FelicaResultInfo cardAdditionalInfoListV2 = getCardAdditionalInfoListV2(IPipeEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, cardAdditionalInfoListV2, 1);
                    return true;
                case 53:
                    FelicaResultInfo felicaResultInfoSilentStart = silentStart(data.readString(), data.readString(), data.readInt(), ISilentStartEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoSilentStart, 1);
                    return true;
                case 54:
                    FelicaResultInfoInt felicaResultInfoIntCheckChipFormatting = checkChipFormatting();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoIntCheckChipFormatting, 1);
                    return true;
                case 55:
                    FelicaResultInfo felicaResultInfoIssueCardV2 = issueCardV2(data.readString(), ICardIssueV2EventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoIssueCardV2, 1);
                    return true;
                case 56:
                    FelicaResultInfo felicaResultInfoEnableV2 = enableV2(data.readString(), ICardEnableV2EventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoEnableV2, 1);
                    return true;
                case 57:
                    FelicaResultInfo felicaResultInfoDisableV2 = disableV2(data.readString(), ICardDisableV2EventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoDisableV2, 1);
                    return true;
                case 58:
                    FelicaResultInfo felicaResultInfoDeleteV2 = deleteV2(data.readString(), data.readString(), ICardDeleteV2EventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoDeleteV2, 1);
                    return true;
                case 59:
                    FelicaResultInfo felicaResultInfoIssueCardWithOtp = issueCardWithOtp(data.readString(), data.readString(), ICardIssueV2EventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoIssueCardWithOtp, 1);
                    return true;
                case 60:
                    FelicaResultInfo felicaResultInfoAccess = access(data.readString(), data.readString(), ICardAccessEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoAccess, 1);
                    return true;
                case 61:
                    FelicaResultInfo cardInfoListWithSpStatus = getCardInfoListWithSpStatus(data.readString(), IPipeEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, cardInfoListWithSpStatus, 1);
                    return true;
                case 62:
                    FelicaResultInfo cardAdditionalInfoListWithCidList = getCardAdditionalInfoListWithCidList(data.createStringArray(), IPipeEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, cardAdditionalInfoListWithCidList, 1);
                    return true;
                case 63:
                    FelicaResultInfo felicaResultInfoSaveDelete = saveDelete(data.readString(), data.readString(), ICardReissuableDeleteEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoSaveDelete, 1);
                    return true;
                case 64:
                    FelicaResultInfo felicaResultInfoSelectWithCid = selectWithCid(data.readInt(), data.readString());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoSelectWithCid, 1);
                    return true;
                case 65:
                    FelicaResultInfoStringArray localCidList = getLocalCidList();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, localCidList, 1);
                    return true;
                case 66:
                    FelicaResultInfoInt felicaResultInfoIntIdentifyService = identifyService();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoIntIdentifyService, 1);
                    return true;
                case 67:
                    FelicaResultInfo felicaResultInfoSilentStartForMfiAdminV2 = silentStartForMfiAdminV2(data.readString(), data.readString(), data.readInt() != 0, data.readInt(), data.readInt(), ISilentStartForMfiAdminEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoSilentStartForMfiAdminV2, 1);
                    return true;
                case 68:
                    FelicaResultInfoInt unsupportMfiService1CardPosition = getUnsupportMfiService1CardPosition();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, unsupportMfiService1CardPosition, 1);
                    return true;
                case 69:
                    FelicaResultInfo felicaResultInfoCancelMfiOffline = cancelMfiOffline();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoCancelMfiOffline, 1);
                    return true;
                case 70:
                    FelicaResultInfoStringArray localCidListV2 = getLocalCidListV2();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, localCidListV2, 1);
                    return true;
                case 71:
                    FelicaResultInfo cardListV3 = getCardListV3(IDataListEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, cardListV3, 1);
                    return true;
                case 72:
                    FelicaResultInfo cardInfoListWithSpStatusV3 = getCardInfoListWithSpStatusV3(data.readString(), IDataListEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, cardInfoListWithSpStatusV3, 1);
                    return true;
                case 73:
                    FelicaResultInfo cardAdditionalInfoListV3 = getCardAdditionalInfoListV3(data.createStringArray(), IDataListEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, cardAdditionalInfoListV3, 1);
                    return true;
                case 74:
                    FelicaResultInfo felicaResultInfoProvisionServerOperation = provisionServerOperation(IServerOperationEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoProvisionServerOperation, 1);
                    return true;
                case 75:
                    FelicaResultInfoStringArray localPartialCardInfoList = getLocalPartialCardInfoList(data.readString());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, localPartialCardInfoList, 1);
                    return true;
                case 76:
                    FelicaResultInfo felicaResultInfoExistUnsupportMfiService1Card = existUnsupportMfiService1Card(data.readString(), IUnsupportMfiService1CardExistEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoExistUnsupportMfiService1Card, 1);
                    return true;
                case 77:
                    FelicaResultInfo felicaResultInfoDeleteUnsupportMfiService1Card = deleteUnsupportMfiService1Card(data.readString(), IUnsupportMfiService1CardDeleteEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoDeleteUnsupportMfiService1Card, 1);
                    return true;
                case 78:
                    FelicaResultInfoInt felicaResultInfoIntExistEmptySlot = existEmptySlot();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoIntExistEmptySlot, 1);
                    return true;
                case 79:
                    FelicaResultInfo cachedCardList = getCachedCardList(IDataListEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, cachedCardList, 1);
                    return true;
                case 80:
                    FelicaResultInfo felicaResultInfoEnableCachedCard = enableCachedCard(data.readString(), ICachedCardEnableEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoEnableCachedCard, 1);
                    return true;
                case 81:
                    FelicaResultInfoBoolean felicaResultInfoBooleanIsLoginedAccount = isLoginedAccount(data.readString());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoBooleanIsLoginedAccount, 1);
                    return true;
                case 82:
                    FelicaResultInfoBoolean felicaResultInfoBooleanExistService = existService();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoBooleanExistService, 1);
                    return true;
                case 83:
                    FelicaResultInfoBoolean felicaResultInfoBooleanIsChipInitialized = isChipInitialized();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoBooleanIsChipInitialized, 1);
                    return true;
                case 84:
                    FelicaResultInfo mfiTosData = getMfiTosData(data.readInt(), IMfiTosDataGetEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, mfiTosData, 1);
                    return true;
                case 85:
                    FelicaResultInfo mfiTosAgreement = setMfiTosAgreement(data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, mfiTosAgreement, 1);
                    return true;
                case 86:
                    FelicaResultInfo googleTos = getGoogleTos(data.readInt(), IGoogleTosGetEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, googleTos, 1);
                    return true;
                case 87:
                    FelicaResultInfoStringArray localPartialCardInfoListForList = getLocalPartialCardInfoListForList(data.createStringArray());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, localPartialCardInfoListForList, 1);
                    return true;
                case 88:
                    FelicaResultInfo felicaResultInfoSilentStartForMfiAdminV3 = silentStartForMfiAdminV3(data.readString(), data.readString(), data.readInt() != 0, data.readInt() != 0, data.readInt(), ISilentStartForMfiAdminEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoSilentStartForMfiAdminV3, 1);
                    return true;
                case 89:
                    FelicaResultInfo remainedCards = getRemainedCards(IRemainedCardsEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, remainedCards, 1);
                    return true;
                case 90:
                    FelicaResultInfo felicaResultInfoUploadCardsToDelete = uploadCardsToDelete(ICardsUploadEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoUploadCardsToDelete, 1);
                    return true;
                case 91:
                    FelicaResultInfo felicaResultInfoUploadCardsToDisable = uploadCardsToDisable(ICardsUploadEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoUploadCardsToDisable, 1);
                    return true;
                case 92:
                    FelicaResultInfo felicaResultInfoClearMemory = clearMemory(IMemoryClearEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoClearMemory, 1);
                    return true;
                case 93:
                    FelicaResultInfoBoolean felicaResultInfoBooleanCheckAndRecoverCrsState = checkAndRecoverCrsState();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoBooleanCheckAndRecoverCrsState, 1);
                    return true;
                case 94:
                    FelicaResultInfo remainedCardsV2 = getRemainedCardsV2(IRemainedCardsEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, remainedCardsV2, 1);
                    return true;
                case 95:
                    FelicaResultInfo felicaResultInfoUploadCardsToPermanentDelete = uploadCardsToPermanentDelete(ICardsUploadEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoUploadCardsToPermanentDelete, 1);
                    return true;
                case 96:
                    FelicaResultInfo felicaResultInfoClearMemoryV2 = clearMemoryV2(IMemoryClearEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoClearMemoryV2, 1);
                    return true;
                case 97:
                    FelicaResultInfo cachedCardListWithIntegrityCheck = getCachedCardListWithIntegrityCheck(IDataListEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, cachedCardListWithIntegrityCheck, 1);
                    return true;
                case 98:
                    FelicaResultInfo felicaResultInfoMigrateCardKey = migrateCardKey(data.readString(), ICardKeyMigrateEventCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoMigrateCardKey, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMfiFelica {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMfiFelica.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo activateFelica(String packageName, IFelicaEventListener listener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(packageName);
                    parcelObtain.writeStrongInterface(listener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] nodeCodeList, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeIntArray(nodeCodeList);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoBlockCountInformationArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoBlockCountInformationArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoByteArray getContainerId(int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoByteArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoByteArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoByteArray getContainerIssueInformation(int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoByteArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoByteArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoByteArray getICCode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoByteArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoByteArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoByteArray getIDm() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoByteArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoByteArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt getInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoInt) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoInt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt getKeyVersion(int serviceCode, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(serviceCode);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoInt) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoInt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoNodeInformation getNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(parentAreaCode);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoNodeInformation) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoNodeInformation.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoNodeInformation getPrivacyNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(parentAreaCode);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoNodeInformation) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoNodeInformation.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoBoolean getRFSState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoBoolean) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoBoolean.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt getSystemCode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoInt) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoInt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoIntArray getSystemCodeList(int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoIntArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoIntArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo inactivateFelica() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo open() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo push(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, pushSegmentParcelableWrapper, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoDataArray read(BlockList blockList, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, blockList, 0);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoDataArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoDataArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo reset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo select(int systemCode) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(systemCode);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo selectWithTarget(int target, int systemCode) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(target);
                    parcelObtain.writeInt(systemCode);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingData, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeTypedArray(privacySettingData, 0);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo write(BlockDataList blockDataList, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, blockDataList, 0);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener listener, String appIdentificationCode) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(listener);
                    parcelObtain.writeString(appIdentificationCode);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo setNodeCodeSize(int nodeCodeSize, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(nodeCodeSize);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo checkOnlineAccess() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo setSelectTimeout(int timeout) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(timeout);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt getSelectTimeout() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoInt) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoInt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo cancelOffline() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoByteArray executeFelicaCommand(byte[] commandPacket, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeByteArray(commandPacket);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoByteArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoByteArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] nodeCodeList, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeIntArray(nodeCodeList);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoKeyInformationArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoKeyInformationArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo login(String accountIssuer, String accountName, ILoginEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(accountIssuer);
                    parcelObtain.writeString(accountName);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoString getCurrentAccountHash() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoString) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoString.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo clearMfiAccount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoSeInfo getSeInfomation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoSeInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoSeInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo logout(boolean z, ILogoutEventCallback iLogoutEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeStrongInterface(iLogoutEventCallback);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardList(ICardListEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardAdditionalInfoList(ICardAdditionalInfoListEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo issueCard(String linkageData, ICardIssueEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(linkageData);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo enable(CardInfo cardInfo, ICardEnableEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, cardInfo, 0);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo disable(CardInfo cardInfo, ICardDisableEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, cardInfo, 0);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo delete(CardInfo cardInfo, String linkageData, ICardDeleteEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, cardInfo, 0);
                    parcelObtain.writeString(linkageData);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo cancelCardOperation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo start(String url, DeviceList deviceList, IFSCEventListener fscEventListener, IMfiFelica felica) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(url);
                    _Parcel.writeTypedObject(parcelObtain, deviceList, 0);
                    parcelObtain.writeStrongInterface(fscEventListener);
                    parcelObtain.writeStrongInterface(felica);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public void stop() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public void notifyResult(byte[] result) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeByteArray(result);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public void notifyError(String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(msg);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo silentStartForMfiAdmin(String str, String str2, boolean z, int i, ISilentStartForMfiAdminEventCallback iSilentStartForMfiAdminEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSilentStartForMfiAdminEventCallback);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getLinkageDataList(int actionType, String[] cid, ILinkageDataListEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(actionType);
                    parcelObtain.writeStringArray(cid);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo initialize(String linkageData, IInitializedEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(linkageData);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardListV2(IPipeEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardAdditionalInfoListV2(IPipeEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo silentStart(String accountIssuer, String accountName, int code, ISilentStartEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(accountIssuer);
                    parcelObtain.writeString(accountName);
                    parcelObtain.writeInt(code);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt checkChipFormatting() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoInt) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoInt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo issueCardV2(String linkageData, ICardIssueV2EventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(linkageData);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo enableV2(String cardInfoJson, ICardEnableV2EventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(cardInfoJson);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo disableV2(String cardInfoJson, ICardDisableV2EventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(cardInfoJson);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo deleteV2(String cardInfoJson, String linkageData, ICardDeleteV2EventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(cardInfoJson);
                    parcelObtain.writeString(linkageData);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo issueCardWithOtp(String linkageData, String otp, ICardIssueV2EventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(linkageData);
                    parcelObtain.writeString(otp);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo access(String cardInfoJson, String linkageData, ICardAccessEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(cardInfoJson);
                    parcelObtain.writeString(linkageData);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardInfoListWithSpStatus(String serviceId, IPipeEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(serviceId);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardAdditionalInfoListWithCidList(String[] cidList, IPipeEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStringArray(cidList);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo saveDelete(String cardInfoJson, String linkageData, ICardReissuableDeleteEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(cardInfoJson);
                    parcelObtain.writeString(linkageData);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo selectWithCid(int systemCode, String cid) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(systemCode);
                    parcelObtain.writeString(cid);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoStringArray getLocalCidList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoStringArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoStringArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt identifyService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoInt) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoInt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo silentStartForMfiAdminV2(String str, String str2, boolean z, int i, int i2, ISilentStartForMfiAdminEventCallback iSilentStartForMfiAdminEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iSilentStartForMfiAdminEventCallback);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt getUnsupportMfiService1CardPosition() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoInt) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoInt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo cancelMfiOffline() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoStringArray getLocalCidListV2() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoStringArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoStringArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardListV3(IDataListEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardInfoListWithSpStatusV3(String serviceId, IDataListEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(serviceId);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardAdditionalInfoListV3(String[] cidList, IDataListEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStringArray(cidList);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo provisionServerOperation(IServerOperationEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoStringArray getLocalPartialCardInfoList(String serviceId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(serviceId);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoStringArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoStringArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo existUnsupportMfiService1Card(String serviceId, IUnsupportMfiService1CardExistEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(serviceId);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo deleteUnsupportMfiService1Card(String linkageData, IUnsupportMfiService1CardDeleteEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(linkageData);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt existEmptySlot() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoInt) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoInt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCachedCardList(IDataListEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo enableCachedCard(String cachedCardInfoJson, ICachedCardEnableEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(cachedCardInfoJson);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoBoolean isLoginedAccount(String accountName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(accountName);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoBoolean) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoBoolean.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoBoolean existService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoBoolean) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoBoolean.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoBoolean isChipInitialized() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoBoolean) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoBoolean.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getMfiTosData(int code, IMfiTosDataGetEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(code);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo setMfiTosAgreement(int code) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(code);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getGoogleTos(int code, IGoogleTosGetEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeInt(code);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoStringArray getLocalPartialCardInfoListForList(String[] serviceIdList) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStringArray(serviceIdList);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoStringArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoStringArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo silentStartForMfiAdminV3(String str, String str2, boolean z, boolean z2, int i, ISilentStartForMfiAdminEventCallback iSilentStartForMfiAdminEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSilentStartForMfiAdminEventCallback);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getRemainedCards(IRemainedCardsEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo uploadCardsToDelete(ICardsUploadEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo uploadCardsToDisable(ICardsUploadEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo clearMemory(IMemoryClearEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoBoolean checkAndRecoverCrsState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoBoolean) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoBoolean.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getRemainedCardsV2(IRemainedCardsEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo uploadCardsToPermanentDelete(ICardsUploadEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo clearMemoryV2(IMemoryClearEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCachedCardListWithIntegrityCheck(IDataListEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo migrateCardKey(String serviceId, ICardKeyMigrateEventCallback callback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiFelica.DESCRIPTOR);
                    parcelObtain.writeString(serviceId);
                    parcelObtain.writeStrongInterface(callback);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }

    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> c) {
            if (parcel.readInt() != 0) {
                return c.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T value, int parcelableFlags) {
            if (value != null) {
                parcel.writeInt(1);
                value.writeToParcel(parcel, parcelableFlags);
            } else {
                parcel.writeInt(0);
            }
        }
    }
}

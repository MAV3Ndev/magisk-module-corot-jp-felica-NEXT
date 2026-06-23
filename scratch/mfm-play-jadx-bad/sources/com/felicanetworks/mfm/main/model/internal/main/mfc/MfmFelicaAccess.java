package com.felicanetworks.mfm.main.model.internal.main.mfc;

import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfc.AreaInformation;
import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.info.specific.MyQUICPayInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccessException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.data.IssueStateData;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmlib.MfmAppContext;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.CachedCardListEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiAdmin;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.ServerOperationEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.UnsupportMfiService1CardExistEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class MfmFelicaAccess extends FelicaAccess {
    private static final int AREA_ZERO = 0;
    private static final int COMMON_SYSTEM_CODE = 65024;
    private static final long MASK = 4294967295L;
    private static final int NOT_AREA = 65535;
    static final int STANDARD_CARD_SERVICECODE = 14728;
    static final int STANDARD_CARD_SYSTEMCODE = 65024;
    private static final int SYSTEM_CODE_SIZE = 2;

    private static final class NodeInfoData implements Comparable<NodeInfoData> {
        private long areaCode;
        private long endServiceCode;

        private NodeInfoData() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:68) call: com.felicanetworks.mfm.main.model.internal.main.mfc.MfmFelicaAccess.NodeInfoData.<init>():void type: THIS */
        /* synthetic */ NodeInfoData(AnonymousClass1 anonymousClass1) {
            this();
        }

        /* JADX DEBUG: Method merged with bridge method: compareTo(Ljava/lang/Object;)I */
        @Override // java.lang.Comparable
        public int compareTo(NodeInfoData another) {
            long j = this.areaCode - another.areaCode;
            if (j > 0) {
                return 1;
            }
            return j < 0 ? -1 : 0;
        }
    }

    MfmFelicaAccess(MfmAppContext context) {
        super(context);
    }

    private int getSystemNumber(byte[] idm) {
        return (idm[0] & 255) >>> 4;
    }

    List<AreaItemFelica> getAreaInfoList() throws FelicaAccessException {
        ArrayList arrayList = new ArrayList();
        try {
            felicaSelect(65039);
            int[] systemCodeList = this.context.felica.getSystemCodeList();
            if (systemCodeList != null && systemCodeList.length != 0) {
                int[] iArr = FeliCaParams.VALID_SYSTEM_CODES;
                if (iArr.length == 0) {
                    return arrayList;
                }
                Arrays.sort(iArr);
                ArrayList arrayList2 = new ArrayList(iArr.length);
                for (int i : systemCodeList) {
                    if (Arrays.binarySearch(iArr, i) >= 0) {
                        arrayList2.add(Integer.valueOf(i));
                    }
                }
                return arrayList2.size() == 0 ? arrayList : doGetAreaCodeInfoList(arrayList2);
            }
            return arrayList;
        } catch (FelicaException e) {
            throw new FelicaAccessException((Class) getClass(), 257, e);
        } catch (Exception e2) {
            throw new FelicaAccessException(getClass(), 258, e2);
        }
    }

    private List<AreaItemFelica> doGetAreaCodeInfoList(List<Integer> sysCodeList) throws FelicaException, IllegalArgumentException, FelicaAccessException {
        ArrayList<AreaItemFelica> arrayList = new ArrayList<>();
        Iterator<Integer> it = sysCodeList.iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            felicaSelect(iIntValue);
            this.context.felica.setNodeCodeSize(4);
            ArrayList<NodeInfoData> areaInformations = readAreaInformations();
            if (!areaInformations.isEmpty()) {
                allNodeInfoListChk(areaInformations);
                addAreaItemFelicaListfromNodeInfoList(arrayList, iIntValue, areaInformations);
            }
        }
        return arrayList;
    }

    private ArrayList<NodeInfoData> readAreaInformations() throws FelicaException, IllegalArgumentException {
        AreaInformation[] areaInformationList;
        ArrayList<NodeInfoData> arrayList = new ArrayList<>();
        NodeInformation nodeInformation = this.context.felica.getNodeInformation(0);
        if (nodeInformation == null || (areaInformationList = nodeInformation.getAreaInformationList()) == null) {
            return arrayList;
        }
        arrayList.addAll(convertToNodeInfoDataList(areaInformationList));
        Collections.sort(arrayList);
        return arrayList;
    }

    private void allNodeInfoListChk(List<NodeInfoData> niList) throws FelicaAccessException {
        if (niList.get(0).areaCode != 0) {
            throw new FelicaAccessException(getClass(), InputDeviceCompat.SOURCE_DPAD, FelicaAccessException.CognitiveType.NOT_EXIST_AREA_ZERO);
        }
    }

    private List<NodeInfoData> convertToNodeInfoDataList(AreaInformation[] areaInfoArray) {
        ArrayList arrayList = new ArrayList();
        for (AreaInformation areaInformation : areaInfoArray) {
            NodeInfoData nodeInfoData = new NodeInfoData(null);
            nodeInfoData.areaCode = ((long) areaInformation.getAreaCode()) & MASK;
            nodeInfoData.endServiceCode = ((long) areaInformation.getEndServiceCode()) & MASK;
            arrayList.add(nodeInfoData);
        }
        return arrayList;
    }

    private void addAreaItemFelicaListfromNodeInfoList(ArrayList<AreaItemFelica> dstList, int systemCode, List<NodeInfoData> nodeInfoList) {
        dstList.ensureCapacity(dstList.size() + nodeInfoList.size());
        String strIntToHexString = CommonUtil.intToHexString(systemCode, 4);
        int i = 0;
        AreaItemFelica areaItemFelica = null;
        long j = 0;
        while (i < nodeInfoList.size()) {
            if (nodeInfoList.get(i).areaCode != 0 && areaItemFelica == null) {
                areaItemFelica = new AreaItemFelica(strIntToHexString, CommonUtil.intToHexString((int) nodeInfoList.get(i).areaCode, 8));
                j = nodeInfoList.get(i).endServiceCode;
                dstList.add(areaItemFelica);
            } else if (nodeInfoList.get(i).areaCode > j) {
                i--;
                areaItemFelica = null;
            }
            i++;
        }
        dstList.trimToSize();
    }

    synchronized boolean isSysAreaUseCondInfo() throws FelicaAccessException {
        try {
            felicaSelect(65039);
            for (int i : this.context.felica.getSystemCodeList()) {
                if (i != 65039) {
                    felicaSelect(i);
                    for (AreaInformation areaInformation : this.context.felica.getNodeInformation(0).getAreaInformationList()) {
                        if (areaInformation.getAreaCode() != 0) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (FelicaException e) {
            LogUtil.warning(e);
            throw new FelicaAccessException((Class) getClass(), InputDeviceCompat.SOURCE_GAMEPAD, e);
        } catch (Exception e2) {
            LogUtil.warning(e2);
            throw new FelicaAccessException(getClass(), 1026, e2);
        }
    }

    List<SystemBlockCntInfoData> getSysAreaUseCondInfo() throws FelicaAccessException {
        ArrayList arrayList = new ArrayList();
        int[] iArr = {0};
        try {
            felicaSelect(65039);
            int[] systemCodeList = this.context.felica.getSystemCodeList();
            if (systemCodeList != null && systemCodeList.length != 0) {
                int[] iArr2 = FeliCaParams.VALID_SYSTEM_CODES;
                if (iArr2.length != 0) {
                    Arrays.sort(iArr2);
                    ArrayList arrayList2 = new ArrayList(iArr2.length);
                    for (int i : systemCodeList) {
                        if (Arrays.binarySearch(iArr2, i) >= 0) {
                            arrayList2.add(Integer.valueOf(i));
                        }
                    }
                    if (arrayList2.size() != 0) {
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            int iIntValue = ((Integer) it.next()).intValue();
                            felicaSelect(iIntValue);
                            this.context.felica.setNodeCodeSize(4);
                            int systemNumber = getSystemNumber(this.context.felica.getIDm());
                            BlockCountInformation[] blockCountInformation = this.context.felica.getBlockCountInformation(iArr);
                            if (blockCountInformation != null && (blockCountInformation[0].getAssignedBlocks() != 65535 || blockCountInformation[0].getFreeBlocks() != 65535)) {
                                arrayList.add(new SystemBlockCntInfoData(blockCountInformation[0].getAssignedBlocks() - blockCountInformation[0].getFreeBlocks(), blockCountInformation[0].getFreeBlocks(), systemNumber, iIntValue));
                            }
                            arrayList.add(new SystemBlockCntInfoData(0, 0, systemNumber, iIntValue));
                        }
                        if (arrayList.size() > 0) {
                            Collections.sort(arrayList);
                        }
                    }
                }
            }
            return arrayList;
        } catch (FelicaException e) {
            LogUtil.warning(e);
            throw new FelicaAccessException((Class) getClass(), InputDeviceCompat.SOURCE_GAMEPAD, e);
        } catch (Exception e2) {
            LogUtil.warning(e2);
            throw new FelicaAccessException(getClass(), 1026, e2);
        }
    }

    void initializeBalance(Boolean isHistory) throws FelicaAccessException {
        try {
            FelicaReaderFactory.createBalanceReader(isHistory);
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new FelicaAccessException(getClass(), 1281, e);
        }
    }

    List<MfcExpert.Asset> readCommonAreaBalanceInfo(List<Service> serviceList) throws FelicaAccessException {
        ArrayList arrayList = new ArrayList();
        try {
            felicaSelect(65024);
            this.context.felica.setNodeCodeSize(4);
            for (Service service : serviceList) {
                BalanceReader balanceReader = FelicaReaderFactory.getBalanceReader(service.getServiceId());
                if (balanceReader != null) {
                    balanceReader.readData(this.context.felica);
                    try {
                        arrayList.add(balanceReader.getBalance(service.getServiceId()));
                    } catch (FelicaReaderException unused) {
                    }
                }
            }
            return arrayList;
        } catch (FelicaException e) {
            throw new FelicaAccessException((Class) getClass(), 1541, e);
        } catch (Exception e2) {
            throw new FelicaAccessException(getClass(), 1542, e2);
        }
    }

    List<MfcExpert.Asset> readOriginalAreaBalanceInfo(List<Service> serviceList) throws FelicaAccessException {
        ArrayList arrayList = new ArrayList();
        try {
            HashMap map = new HashMap();
            Iterator<Service> it = serviceList.iterator();
            while (it.hasNext()) {
                String serviceId = it.next().getServiceId();
                BalanceReader balanceReader = FelicaReaderFactory.getBalanceReader(serviceId);
                if (balanceReader != null) {
                    map.put(serviceId, balanceReader);
                }
            }
            if (map.size() > 0) {
                felicaSelect(65039);
                for (int i : this.context.felica.getSystemCodeList()) {
                    if (i == 3) {
                        felicaSelect(3);
                        this.context.felica.setNodeCodeSize(4);
                        for (Map.Entry entry : map.entrySet()) {
                            BalanceReader balanceReader2 = (BalanceReader) entry.getValue();
                            balanceReader2.initializeDataList();
                            balanceReader2.readData(this.context.felica);
                            try {
                                arrayList.add(balanceReader2.getBalance((String) entry.getKey()));
                            } catch (FelicaReaderException unused) {
                            }
                        }
                    }
                }
            }
            return arrayList;
        } catch (FelicaException e) {
            throw new FelicaAccessException((Class) getClass(), 1542, e);
        } catch (Exception e2) {
            throw new FelicaAccessException(getClass(), 1543, e2);
        }
    }

    List<MfcExpert.Asset> readOriginalAreaWithCidBalanceInfo(List<MyCardInfo> cardInfoList) throws FelicaAccessException {
        int i;
        ArrayList arrayList = new ArrayList();
        for (MyCardInfo myCardInfo : cardInfoList) {
            if (myCardInfo.getCardStatus() == MyCardInfo.CardStatus.STATUS_ACTIVE && ((i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$info$MyCardInfo$CardPosition[myCardInfo.getCardPosition().ordinal()]) == 1 || i == 2)) {
                String cardId = myCardInfo.getCardId();
                String serviceId = myCardInfo.getServiceId();
                BalanceReader balanceReader = FelicaReaderFactory.getBalanceReader(serviceId);
                if (balanceReader != null) {
                    try {
                        felicaSelectWithCid(3, cardId);
                        this.context.felica.setNodeCodeSize(4);
                        balanceReader.initializeDataList();
                        balanceReader.readData(this.context.felica);
                        try {
                            arrayList.add(balanceReader.getBalance(serviceId, cardId));
                        } catch (FelicaReaderException unused) {
                        }
                    } catch (FelicaException e) {
                        throw new FelicaAccessException((Class) getClass(), 1544, e);
                    } catch (Exception e2) {
                        throw new FelicaAccessException(getClass(), 1545, e2);
                    }
                } else {
                    continue;
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.mfc.MfmFelicaAccess$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$MyCardInfo$CardPosition;

        static {
            int[] iArr = new int[MyCardInfo.CardPosition.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$MyCardInfo$CardPosition = iArr;
            try {
                iArr[MyCardInfo.CardPosition.POSITION_FOREGROUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$MyCardInfo$CardPosition[MyCardInfo.CardPosition.POSITION_BACKGROUND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    List<SasReadItem> getSasInfoList(final List<AreaItemFelica> areaItemList) throws FelicaAccessException {
        try {
            if (checkContainSasAreaCode(areaItemList)) {
                felicaSelect(65024);
                this.context.felica.setNodeCodeSize(4);
                SasReader sasReaderCreateSasReader = FelicaReaderFactory.createSasReader();
                sasReaderCreateSasReader.readData(this.context.felica);
                return sasReaderCreateSasReader.getSasReadResultList();
            }
            return new ArrayList(0);
        } catch (FelicaException e) {
            LogUtil.warning(e);
            throw new FelicaAccessException((Class) getClass(), 2049, e);
        } catch (Exception e2) {
            LogUtil.warning(e2);
            throw new FelicaAccessException(getClass(), 2050, e2);
        }
    }

    private boolean checkContainSasAreaCode(List<AreaItemFelica> areaItemList) {
        for (AreaItemFelica areaItemFelica : areaItemList) {
            if (areaItemFelica.systemCode.equals(SasDefinition.SYSTEM_CODE) && areaItemFelica.areaCode.equals(SasDefinition.AREA_CODE)) {
                return true;
            }
        }
        return false;
    }

    boolean confirmFpStandard() throws FelicaAccessException {
        try {
            felicaSelect(65024);
            this.context.felica.getKeyVersion(14728);
            return true;
        } catch (FelicaException e) {
            LogUtil.warning(e);
            if (11 == e.getType()) {
                return false;
            }
            throw new FelicaAccessException((Class) getClass(), 2305, e);
        } catch (Exception e2) {
            LogUtil.warning(e2);
            throw new FelicaAccessException(getClass(), 2306, e2);
        }
    }

    MfcExpert.FpArea readFpAreaInfo() throws FelicaAccessException {
        try {
            FelicaPocketReader felicaPocketReader = new FelicaPocketReader(this.context.felica);
            return felicaPocketReader.parseIndexInfo(felicaPocketReader.readIndexInfo(felicaPocketReader.parseCardInfo(felicaPocketReader.readCardIdInfo())));
        } catch (FelicaException e) {
            LogUtil.warning(e);
            if (e.getType() == 11 || e.getType() == 12 || e.getType() == 13) {
                return null;
            }
            throw new FelicaAccessException((Class) getClass(), 2561, e);
        } catch (Exception e2) {
            LogUtil.warning(e2);
            throw new FelicaAccessException(getClass(), 2562, e2);
        }
    }

    int identifyService() throws MfiClientException {
        return this.context.felica.getMfiClient().identifyService();
    }

    List<String> getLocalCidList() throws MfiClientException {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, this.context.felica.getMfiClient().getLocalCidList());
        return arrayList;
    }

    void provisionServerOperation(ServerOperationEventCallback callback) throws IllegalArgumentException, MfiClientException {
        this.context.felica.getMfiClient().provisionServerOperation(callback);
    }

    void existUnsupportMfiService1Card(UnsupportMfiService1CardExistEventCallback callback) throws MfiClientException {
        this.context.felica.getMfiClient().existUnsupportMfiService1Card(FeliCaParams.SERVICE_ID_SUICA, callback);
    }

    static User getMfiUser() {
        return mfiUser;
    }

    static void setMfiUser(User mfiUser) {
        FelicaAccess.mfiUser = mfiUser;
    }

    static MfiAdmin getMfiAdmin() {
        return _mfiAdmin;
    }

    static void setMfiAdmin(MfiAdmin _mfiAdmin) {
        FelicaAccess._mfiAdmin = _mfiAdmin;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess
    public IssueStateData getIssueStateResult() throws FelicaAccessException {
        return super.getIssueStateResult();
    }

    @Override // com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccess
    public void setSilentStartCode(int code) {
        super.setSilentStartCode(code);
    }

    SeInfo getSeInformation() throws MfiClientException {
        return this.context.felica.getMfiClient().getSeInformation();
    }

    MyQUICPayInfo.QPType getQUICPayID() throws FelicaAccessException {
        try {
            felicaSelect(65024);
            this.context.felica.setNodeCodeSize(4);
            QUICPayReader qUICPayReaderCreateQUICPayReader = FelicaReaderFactory.createQUICPayReader();
            qUICPayReaderCreateQUICPayReader.readData(this.context.felica);
            return qUICPayReaderCreateQUICPayReader.getQPType();
        } catch (FelicaException e) {
            LogUtil.warning(e);
            throw new FelicaAccessException((Class) getClass(), 2817, e);
        } catch (Exception e2) {
            LogUtil.warning(e2);
            throw new FelicaAccessException(getClass(), 2818, e2);
        }
    }

    void getCachedCardList(boolean integrityCheck, CachedCardListEventCallback callback) throws MfiClientException {
        if (integrityCheck) {
            this.context.felica.getMfiClient().getCachedCardListWithIntegrityCheck(callback);
        } else {
            this.context.felica.getMfiClient().getCachedCardList(callback);
        }
    }

    void cancelCardOperation() throws MfiClientException {
        this.context.felica.getMfiClient().cancelCardOperation();
    }
}

package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public class DcmxMiniBalanceReader extends BalanceReader {
    private static final String ALL0_LATESTOFFLINEDATE = "000000";
    private static final int MASK_VALUE_ISSUERCODE = 131071;
    private static final int MASK_VALUE_PATTERNNO = 262143;
    private static final String MAX_MM = "12";
    private static final String MAX_MONTHLYCLEARDATE = "28";
    private static final String MIN_MM = "01";
    private static final String MIN_MONTHLYCLEARDATE = "01";
    private static final String OFFSET_YY = "20";
    private static final String PERMIT_CARDAPLAREA = "0100";
    private static final String PERMIT_ENABLEFLG = "00";
    private static final int PERMIT_ISSUERCODE = 30150;
    private static final int PERMIT_PATTERNNO = 1;
    private static final int READ_INDEX_CARDAPLAREA = 2;
    private static final int READ_INDEX_ENABLEFLG_LASTOFFLINEDATE_OFFLINEUSEVALUE = 3;
    private static final int READ_INDEX_ISSUERCODE_PATTERNNO = 0;
    private static final int READ_INDEX_MONTHLYCLEARDATE_OFFLINEVALUELIMIT = 1;
    private static final int READ_NUM_INDEX = 1;
    private static final int SHIFT_BIT_NUM_ISSUERCODE = 7;
    private static final int SHIFT_BIT_NUM_PATTERNNO = 1;
    private static final int START_BYTE_INDEX = 0;
    private static final int[] GET_INFO_ISSUERCODE = {9, 3};
    private static final int[] GET_INFO_PATTERNNO = {11, 2};
    private static final int[] GET_INFO_MONTHLYCLEARDATE = {4, 1};
    private static final int[] GET_INFO_OFFLINEVALUELIMIT = {10, 3};
    private static final int[] GET_INFO_CARDAPLAREA = {3, 2};
    private static final int[] GET_INFO_ENABLEFLG = {0, 1};
    private static final int[] GET_INFO_LASTOFFLINEDATE = {1, 3};
    private static final int[] GET_INFO_OFFLINEUSEVALUE = {11, 3};

    DcmxMiniBalanceReader() {
        super(new BlockList());
        for (int[] iArr : FeliCaParams.READ_BALANCE_INFO_LIST_DCARD) {
            this.blocklist.add(new Block(iArr[0], iArr[1]));
        }
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.BalanceReader
    public MfcExpert.Asset getBalance(String serviceId) throws FelicaReaderException {
        checkDataExist(getClass(), 577);
        MfcExpert.Asset asset = new MfcExpert.Asset(serviceId, "", -1, -1, -1, -1, null, null, true);
        DcmxMiniDataItem dcmxMiniDataItemCreateDcmxMiniDataList = createDcmxMiniDataList();
        if (!checkDcardMini(dcmxMiniDataItemCreateDcmxMiniDataList)) {
            asset.isDcardMini = false;
            return asset;
        }
        if (!checkData(dcmxMiniDataItemCreateDcmxMiniDataList) || !checkEnableFlg(dcmxMiniDataItemCreateDcmxMiniDataList) || !checkMonthlyClearDate(dcmxMiniDataItemCreateDcmxMiniDataList)) {
            return asset;
        }
        if (checkLatestOfflineDateAllZero(dcmxMiniDataItemCreateDcmxMiniDataList)) {
            asset.balanceValue = Integer.valueOf(dcmxMiniDataItemCreateDcmxMiniDataList.offlineLimitValue).intValue();
            asset.balanceLimit = Integer.valueOf(dcmxMiniDataItemCreateDcmxMiniDataList.offlineLimitValue).intValue();
            return asset;
        }
        setBalanceValueLimit(asset, dcmxMiniDataItemCreateDcmxMiniDataList);
        return asset;
    }

    private DcmxMiniDataItem createDcmxMiniDataList() throws FelicaReaderException {
        DcmxMiniDataItem dcmxMiniDataItem = new DcmxMiniDataItem(-1, -1, "", "", "", "", "", "");
        setIssuerCodePatternNo(dcmxMiniDataItem);
        setMouthlyCrearDateOffLineValueLimit(dcmxMiniDataItem);
        setCardAplArea(dcmxMiniDataItem);
        setEnableFlgLastOfflineDateOfflineUseValue(dcmxMiniDataItem);
        return dcmxMiniDataItem;
    }

    private void setIssuerCodePatternNo(DcmxMiniDataItem dataItem) throws FelicaReaderException {
        byte[] bArrChangeEndian = CommonUtil.changeEndian(getByteData(this.data[0]));
        int[] iArr = GET_INFO_ISSUERCODE;
        dataItem.issureCode = (new BigInteger(getTargetByte(bArrChangeEndian, iArr[0], iArr[1])).intValue() >>> 7) & MASK_VALUE_ISSUERCODE;
        int[] iArr2 = GET_INFO_PATTERNNO;
        dataItem.patternNoCode = (new BigInteger(getTargetByte(bArrChangeEndian, iArr2[0], iArr2[1])).intValue() >>> 1) & MASK_VALUE_PATTERNNO;
    }

    private void setMouthlyCrearDateOffLineValueLimit(DcmxMiniDataItem dataItem) throws FelicaReaderException {
        byte[] bArrChangeEndian = CommonUtil.changeEndian(getByteData(this.data[1]));
        int[] iArr = GET_INFO_MONTHLYCLEARDATE;
        dataItem.monthlyClearDate_DD = CommonUtil.binToHexString(getTargetByte(bArrChangeEndian, iArr[0], iArr[1]));
        int[] iArr2 = GET_INFO_OFFLINEVALUELIMIT;
        dataItem.offlineLimitValue = CommonUtil.binToHexString(getTargetByte(bArrChangeEndian, iArr2[0], iArr2[1]));
    }

    private void setCardAplArea(DcmxMiniDataItem dataItem) throws FelicaReaderException {
        byte[] bArrChangeEndian = CommonUtil.changeEndian(getByteData(this.data[2]));
        int[] iArr = GET_INFO_CARDAPLAREA;
        dataItem.cardAplArea = CommonUtil.binToHexString(getTargetByte(bArrChangeEndian, iArr[0], iArr[1]));
    }

    private void setEnableFlgLastOfflineDateOfflineUseValue(DcmxMiniDataItem dataItem) throws FelicaReaderException {
        byte[] bArrChangeEndian = CommonUtil.changeEndian(getByteData(this.data[3]));
        int[] iArr = GET_INFO_ENABLEFLG;
        dataItem.enableFlag = CommonUtil.binToHexString(getTargetByte(bArrChangeEndian, iArr[0], iArr[1]));
        int[] iArr2 = GET_INFO_LASTOFFLINEDATE;
        dataItem.latestOfflineDate_YYMMDD = CommonUtil.binToHexString(getTargetByte(bArrChangeEndian, iArr2[0], iArr2[1]));
        int[] iArr3 = GET_INFO_OFFLINEUSEVALUE;
        dataItem.offlineUseValue = CommonUtil.binToHexString(getTargetByte(bArrChangeEndian, iArr3[0], iArr3[1]));
    }

    private void setBalanceValueLimit(MfcExpert.Asset brr, DcmxMiniDataItem dmdi) {
        String str;
        String str2 = new DateFormatter("yyyyMMdd", "Asia/Tokyo").format(new Date());
        if (str2.compareTo(OFFSET_YY + dmdi.latestOfflineDate_YYMMDD) < 0) {
            return;
        }
        if (dmdi.monthlyClearDate_DD.compareTo(dmdi.latestOfflineDate_YYMMDD.substring(4, 6)) > 0) {
            str = OFFSET_YY + dmdi.latestOfflineDate_YYMMDD.substring(0, 4) + dmdi.monthlyClearDate_DD;
        } else if (MAX_MM.compareTo(dmdi.latestOfflineDate_YYMMDD.substring(2, 4)) > 0) {
            str = OFFSET_YY + dmdi.latestOfflineDate_YYMMDD.substring(0, 2) + String.format(Locale.US, "%1$02d", Integer.valueOf(Integer.valueOf(dmdi.latestOfflineDate_YYMMDD.substring(2, 4)).intValue() + 1)) + dmdi.monthlyClearDate_DD;
        } else {
            str = OFFSET_YY + String.format(Locale.US, "%1$02d", Integer.valueOf(Integer.valueOf(dmdi.latestOfflineDate_YYMMDD.substring(0, 2)).intValue() + 1)) + "01" + dmdi.monthlyClearDate_DD;
        }
        if (str2.compareTo(str) < 0) {
            brr.balanceValue = Integer.valueOf(dmdi.offlineLimitValue).intValue() - Integer.valueOf(dmdi.offlineUseValue).intValue();
        } else {
            brr.balanceValue = Integer.valueOf(dmdi.offlineLimitValue).intValue();
        }
        brr.balanceLimit = Integer.valueOf(dmdi.offlineLimitValue).intValue();
    }

    private boolean checkDcardMini(DcmxMiniDataItem dataItem) {
        return dataItem.issureCode == PERMIT_ISSUERCODE && dataItem.patternNoCode == 1 && dataItem.cardAplArea.equals(PERMIT_CARDAPLAREA);
    }

    private boolean checkData(DcmxMiniDataItem dmdi) {
        try {
            Integer.valueOf(dmdi.offlineLimitValue);
            Integer.valueOf(dmdi.offlineUseValue);
            Integer.valueOf(dmdi.monthlyClearDate_DD);
            Integer.valueOf(dmdi.latestOfflineDate_YYMMDD);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    private boolean checkEnableFlg(DcmxMiniDataItem dmdi) {
        return dmdi.enableFlag.equals("00");
    }

    private boolean checkMonthlyClearDate(DcmxMiniDataItem dmdi) {
        return "01".compareTo(dmdi.monthlyClearDate_DD) <= 0 && MAX_MONTHLYCLEARDATE.compareTo(dmdi.monthlyClearDate_DD) >= 0;
    }

    private boolean checkLatestOfflineDateAllZero(DcmxMiniDataItem dmdi) {
        return dmdi.latestOfflineDate_YYMMDD.equals(ALL0_LATESTOFFLINEDATE);
    }
}

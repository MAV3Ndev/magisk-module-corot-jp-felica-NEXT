package com.felicanetworks.mfw.i.fbl;

import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfw.i.cmn.ArrayList;
import com.felicanetworks.mfw.i.cmn.DateUtil;
import com.felicanetworks.mfw.i.cmn.ResUtil;
import com.felicanetworks.mfw.i.cmn.ResUtilListener;
import com.felicanetworks.mfw.i.cmn.StringUtil;
import com.felicanetworks.mfw.i.cmn.SysException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class BizResOptr implements ResUtilListener {
    private static final String AREA_PERMIT_EXPIRE = "2";
    private static final String AREA_PERM_AREA_FORMAT_VERSIONUP = "0";
    private static final String AREA_PROBLEM_LOG = "3";
    private static final String AREA_SUFFIX = ".bin";
    private static final String AREA_TAKE_OVER_PARAM = "4";
    private static final String AREA_VERSIONUP = "1";
    private static final int INDEX_ADDTIONAL_INFORMATION = 1;
    private static final int INDEX_EXPIRE_LIST = 311;
    private static final int INDEX_EXPIRE_NUMBER = 9;
    private static final int INDEX_EXPIRE_NUMBER_LIMIT = 1;
    private static final int INDEX_EXPIRE_POINT = 52;
    private static final int INDEX_EXPIRE_POINT_SIZE = 49;
    private static final int INDEX_EXPIRE_TERM = 29;
    private static final int INDEX_LAST_START_DATE = 258;
    private static final int INDEX_MGMTFLAG = 0;
    private static final int INDEX_SERIAL_NUMBER_COUNT = 307;
    private static final int INDEX_START_PARAM = 4;
    private static final int INDEX_START_PARAM_SIZE = 1;
    private static final int INDEX_UPDATE_DATE = 37;
    private static final int INDEX_USED_DATE = 17;
    private static final int INDEX_VERSIONUP_DATE = 1;
    private static final int INDEX_VERSIONUP_NUMBER = 21;
    private static final int INDEX_VERSIONUP_NUMBER_LIMIT = 13;
    private static final int INDEX_VERSIONUP_REQ_DATE = 37;
    private static final int INDEX_VERSIONUP_TERM = 29;
    private static final int LENGTH_ADDTIONAL_INFORMATION = 1024;
    private static final int LENGTH_EXPIRE_EDITFLAG = 1;
    private static final int LENGTH_EXPIRE_NUMBER = 8;
    private static final int LENGTH_EXPIRE_NUMBER_LIMIT = 8;
    private static final int LENGTH_EXPIRE_POINT = 255;
    private static final int LENGTH_EXPIRE_POINT_SIZE = 3;
    private static final int LENGTH_EXPIRE_TERM = 8;
    private static final int LENGTH_LAST_START_DATE = 12;
    private static final int LENGTH_MGMTFLAG = 1;
    private static final int LENGTH_SERIAL_NUMBER = 12;
    private static final int LENGTH_SERIAL_NUMBER_COUNT = 4;
    private static final int LENGTH_START_PARAM = 254;
    private static final int LENGTH_START_PARAM_SIZE = 3;
    private static final int LENGTH_TAKE_OVER_PARAM_EDITFLAG = 1;
    private static final int LENGTH_UPDATE_DATE = 12;
    private static final int LENGTH_USED_DATE = 12;
    private static final int LENGTH_VERSIONUP_DATE = 12;
    private static final int LENGTH_VERSIONUP_EDITFLAG = 1;
    private static final int LENGTH_VERSIONUP_NUMBER = 8;
    private static final int LENGTH_VERSIONUP_NUMBER_LIMIT = 8;
    private static final int LENGTH_VERSIONUP_REQ_DATE = 12;
    private static final int LENGTH_VERSIONUP_TERM = 8;
    public static final String RESOURCE_PERMIT_EXPIRE = "R101";
    public static final String RESOURCE_PERMIT_OFFLINE_UPDATE = "R102";
    public static final String RESOURCE_PERM_AREA_FORMAT_VERSIONUP = "R000";
    public static final String RESOURCE_PROBLEM_LOG = "R201";
    public static final String RESOURCE_TAKE_OVER_PARAM = "R301";
    public static final String RESOURCE_VERSIONUP = "R001";
    public static final String RESOURCE_VERSIONUP_UPDATE = "R002";
    private static final int SIZE_PERMIT_EXPIRE = 2711;
    private static final int SIZE_PERM_AREA_FORMAT_VERSIONUP = 1;
    private static final int SIZE_PROBLEM_LOG = 1025;
    private static final int SIZE_TAKE_OVER_PARAM = 270;
    private static final int SIZE_VERSIONUP = 49;
    private BizResOptrListener mListener;
    private long mPosInFile;
    private byte[] mReadData;
    private static final String CODE_REFERENCE_POSSIBLE = new String(new byte[]{0});
    private static final String CODE_IN_EDIT = new String(new byte[]{1});
    private static final BizResOptr BIZ_RES_OPTR = new BizResOptr();
    private static ByteArrayOutputStream sBuffer = new ByteArrayOutputStream();
    private static final int LENGTH_EXPIRE_LIST = 2400;
    private static byte[] sWritePermitRvctionByte = new byte[LENGTH_EXPIRE_LIST];
    private static byte[] sRemain = new byte[LENGTH_EXPIRE_LIST];
    private static File sCacheFile = new File(Property.sFileDir, "2.bin");
    private ArrayList mResOptrSettingList = new ArrayList();
    private ArrayList mTempResOptrSettingList = new ArrayList();
    private int mIsEditFlag = 0;

    private BizResOptr() {
    }

    public static BizResOptr getInstance() {
        return BIZ_RES_OPTR;
    }

    public void init() {
        setReadInfo(RESOURCE_PERM_AREA_FORMAT_VERSIONUP, 0L);
        startReadWrite();
        if (new String(Property.PERM_AREA_FORMAT_VERSIONUP).equals(new String(this.mReadData))) {
            return;
        }
        try {
            sCacheFile.delete();
        } catch (SecurityException unused) {
        }
        setWriteInfo(RESOURCE_PERM_AREA_FORMAT_VERSIONUP, new ResData());
        startReadWrite();
    }

    public void setListener(BizResOptrListener bizResOptrListener) {
        this.mListener = bizResOptrListener;
    }

    public void setWriteInfo(String str, ResData resData) {
        ResOptrSetting resOptrSetting = new ResOptrSetting();
        resOptrSetting.setId(str);
        resOptrSetting.setRead(false);
        resOptrSetting.setResData(resData);
        resOptrSetting.setBuffered(chkIsBuffWrite(str));
        this.mResOptrSettingList.add(resOptrSetting);
    }

    public void setReadInfo(String str, long j) {
        this.mPosInFile = j;
        ResOptrSetting resOptrSetting = new ResOptrSetting();
        resOptrSetting.setId(str);
        resOptrSetting.setRead(true);
        this.mResOptrSettingList.add(resOptrSetting);
    }

    public ResData getReadData(String str) {
        if (str.equals(RESOURCE_VERSIONUP) || str.equals(RESOURCE_VERSIONUP_UPDATE)) {
            return generateResDataVerUpConfir(this.mReadData);
        }
        if (str.equals(RESOURCE_PERMIT_EXPIRE) || str.equals(RESOURCE_PERMIT_OFFLINE_UPDATE)) {
            try {
                return generateResDataPermitRvction(this.mReadData);
            } catch (Exception unused) {
            }
        } else {
            if (str.equals(RESOURCE_PROBLEM_LOG)) {
                return generatePrblmAnalyze(this.mReadData);
            }
            if (str.equals(RESOURCE_TAKE_OVER_PARAM)) {
                return generateStartParam(this.mReadData);
            }
        }
        return null;
    }

    public String getRvctionUrl() {
        byte[] bArr = this.mReadData;
        if (bArr == null || bArr.length != SIZE_PERMIT_EXPIRE) {
            return null;
        }
        return generateString(bArr, INDEX_EXPIRE_POINT, 255);
    }

    public String getRvctionUsedDate() {
        byte[] bArr = this.mReadData;
        if (bArr == null || bArr.length != SIZE_PERMIT_EXPIRE) {
            return null;
        }
        return generateString(bArr, 17, 12);
    }

    public void startReadWrite() {
        this.mTempResOptrSettingList.clear();
        if (this.mResOptrSettingList.size() > 0) {
            this.mTempResOptrSettingList.addAllList(this.mResOptrSettingList);
            this.mResOptrSettingList.clear();
        }
        executeReadWrite();
    }

    @Override // com.felicanetworks.mfw.i.cmn.ResUtilListener
    public void resUtilReadArea(byte[] bArr, int i) {
        ResOptrSetting resOptrSetting = (ResOptrSetting) this.mTempResOptrSettingList.get(0);
        String id = resOptrSetting.getId();
        if (id.equals(RESOURCE_PERM_AREA_FORMAT_VERSIONUP)) {
            this.mReadData = bArr;
            this.mTempResOptrSettingList.remove(0);
            if (this.mTempResOptrSettingList.size() > 0) {
                executeReadWrite();
                return;
            }
            return;
        }
        if (id.equals(RESOURCE_VERSIONUP) || id.equals(RESOURCE_VERSIONUP_UPDATE) || id.equals(RESOURCE_PERMIT_EXPIRE) || id.equals(RESOURCE_PERMIT_OFFLINE_UPDATE) || id.equals(RESOURCE_TAKE_OVER_PARAM)) {
            String str = new String(new byte[]{bArr[0]});
            if (str.equals(CODE_REFERENCE_POSSIBLE)) {
                this.mReadData = bArr;
                this.mTempResOptrSettingList.remove(0);
                this.mListener.read(resOptrSetting.getId(), 0);
                if (this.mTempResOptrSettingList.size() < 1) {
                    this.mListener = null;
                    return;
                } else {
                    executeReadWrite();
                    return;
                }
            }
            if (str.equals(CODE_IN_EDIT)) {
                this.mTempResOptrSettingList.remove(0);
                this.mListener.read(resOptrSetting.getId(), -1);
                this.mListener = null;
                return;
            }
            return;
        }
        this.mReadData = bArr;
        this.mTempResOptrSettingList.remove(0);
        this.mListener.read(resOptrSetting.getId(), 0);
        if (this.mTempResOptrSettingList.size() < 1) {
            this.mListener = null;
        } else {
            executeReadWrite();
        }
    }

    @Override // com.felicanetworks.mfw.i.cmn.ResUtilListener
    public void resUtilWriteArea() {
        ResOptrSetting resOptrSetting = (ResOptrSetting) this.mTempResOptrSettingList.get(0);
        String id = resOptrSetting.getId();
        int i = this.mIsEditFlag;
        if (i == 0) {
            this.mTempResOptrSettingList.remove(0);
            if (id.equals(RESOURCE_PERM_AREA_FORMAT_VERSIONUP)) {
                return;
            }
            this.mListener.write(resOptrSetting.getId(), 0);
            if (this.mTempResOptrSettingList.size() < 1) {
                this.mListener = null;
                return;
            } else {
                executeReadWrite();
                return;
            }
        }
        if (i != 1) {
            if (i == 2) {
                this.mIsEditFlag = 0;
                if (id.equals(RESOURCE_VERSIONUP) || id.equals(RESOURCE_VERSIONUP_UPDATE)) {
                    ResUtil.writeToArea(AREA_VERSIONUP, 0, CODE_REFERENCE_POSSIBLE.getBytes(), 1, this);
                    return;
                }
                if (id.equals(RESOURCE_PERMIT_EXPIRE) || id.equals(RESOURCE_PERMIT_OFFLINE_UPDATE)) {
                    ResUtil.writeToArea(AREA_PERMIT_EXPIRE, (int) ((ResDataPermitRvction) resOptrSetting.getResData()).getPosInFile(), CODE_REFERENCE_POSSIBLE.getBytes(), 1, this);
                    return;
                } else {
                    if (id.equals(RESOURCE_TAKE_OVER_PARAM)) {
                        ResUtil.writeToArea(AREA_TAKE_OVER_PARAM, 0, CODE_REFERENCE_POSSIBLE.getBytes(), 1, this);
                        return;
                    }
                    return;
                }
            }
            return;
        }
        this.mIsEditFlag = 2;
        if (id.equals(RESOURCE_VERSIONUP)) {
            writeVerUpConfir((ResDataVerUpConfir) resOptrSetting.getResData());
            return;
        }
        if (id.equals(RESOURCE_VERSIONUP_UPDATE)) {
            writeOfflineVerUp((ResDataVerUpConfir) resOptrSetting.getResData());
            return;
        }
        if (id.equals(RESOURCE_PERMIT_EXPIRE)) {
            writePermitRvction((ResDataPermitRvction) resOptrSetting.getResData());
        } else if (id.equals(RESOURCE_PERMIT_OFFLINE_UPDATE)) {
            writeOfflinePermitRvction((ResDataPermitRvction) resOptrSetting.getResData());
        } else if (id.equals(RESOURCE_TAKE_OVER_PARAM)) {
            writeStartParam((ResDataStartParam) resOptrSetting.getResData());
        }
    }

    private boolean chkIsBuffWrite(String str) {
        return str.equals(RESOURCE_VERSIONUP) || str.equals(RESOURCE_VERSIONUP_UPDATE) || str.equals(RESOURCE_PERMIT_EXPIRE) || str.equals(RESOURCE_PERMIT_OFFLINE_UPDATE) || str.equals(RESOURCE_TAKE_OVER_PARAM);
    }

    private void executeReadWrite() {
        ResOptrSetting resOptrSetting = (ResOptrSetting) this.mTempResOptrSettingList.get(0);
        String id = resOptrSetting.getId();
        if (resOptrSetting.isRead()) {
            if (id.equals(RESOURCE_PERM_AREA_FORMAT_VERSIONUP)) {
                ResUtil.readArea(AREA_PERM_AREA_FORMAT_VERSIONUP, 0, 1, this);
                return;
            }
            if (id.equals(RESOURCE_VERSIONUP) || id.equals(RESOURCE_VERSIONUP_UPDATE)) {
                ResUtil.readArea(AREA_VERSIONUP, 0, 49, this);
                return;
            }
            if (id.equals(RESOURCE_PERMIT_EXPIRE) || id.equals(RESOURCE_PERMIT_OFFLINE_UPDATE)) {
                ResUtil.readArea(AREA_PERMIT_EXPIRE, (int) this.mPosInFile, SIZE_PERMIT_EXPIRE, this);
                return;
            } else if (id.equals(RESOURCE_PROBLEM_LOG)) {
                ResUtil.readArea(AREA_PROBLEM_LOG, 0, SIZE_PROBLEM_LOG, this);
                return;
            } else {
                if (id.equals(RESOURCE_TAKE_OVER_PARAM)) {
                    ResUtil.readArea(AREA_TAKE_OVER_PARAM, 0, SIZE_TAKE_OVER_PARAM, this);
                    return;
                }
                return;
            }
        }
        if (resOptrSetting.isBuffered()) {
            if (id.equals(RESOURCE_VERSIONUP)) {
                this.mIsEditFlag = 1;
                ResUtil.writeToArea(AREA_VERSIONUP, 0, CODE_IN_EDIT.getBytes(), 1, this);
                return;
            }
            if (id.equals(RESOURCE_VERSIONUP_UPDATE)) {
                this.mIsEditFlag = 1;
                ResUtil.writeToArea(AREA_VERSIONUP, 0, CODE_IN_EDIT.getBytes(), 1, this);
                return;
            }
            if (id.equals(RESOURCE_PERMIT_EXPIRE)) {
                this.mIsEditFlag = 1;
                ResUtil.writeToArea(AREA_PERMIT_EXPIRE, (int) ((ResDataPermitRvction) resOptrSetting.getResData()).getPosInFile(), CODE_IN_EDIT.getBytes(), 1, this);
                return;
            } else if (id.equals(RESOURCE_PERMIT_OFFLINE_UPDATE)) {
                this.mIsEditFlag = 1;
                ResUtil.writeToArea(AREA_PERMIT_EXPIRE, (int) ((ResDataPermitRvction) resOptrSetting.getResData()).getPosInFile(), CODE_IN_EDIT.getBytes(), 1, this);
                return;
            } else {
                if (id.equals(RESOURCE_TAKE_OVER_PARAM)) {
                    this.mIsEditFlag = 1;
                    ResUtil.writeToArea(AREA_TAKE_OVER_PARAM, 0, CODE_IN_EDIT.getBytes(), 1, this);
                    return;
                }
                return;
            }
        }
        if (id.equals(RESOURCE_PERM_AREA_FORMAT_VERSIONUP)) {
            this.mIsEditFlag = 0;
            ResUtil.writeToArea(AREA_PERM_AREA_FORMAT_VERSIONUP, 0, Property.PERM_AREA_FORMAT_VERSIONUP, 1, this);
        } else if (id.equals(RESOURCE_PROBLEM_LOG)) {
            this.mIsEditFlag = 0;
            writePrblmAnalyze((ResDataPrblmAnalyze) resOptrSetting.getResData());
        }
    }

    private void writeVerUpConfir(ResDataVerUpConfir resDataVerUpConfir) {
        String verUpConfirDate = resDataVerUpConfir.getVerUpConfirDate();
        int offlineVerNumLimit = resDataVerUpConfir.getOfflineVerNumLimit();
        int offlineVerNum = resDataVerUpConfir.getOfflineVerNum();
        int offlineVerData = resDataVerUpConfir.getOfflineVerData();
        String offlineVerUpReqDate = resDataVerUpConfir.getOfflineVerUpReqDate();
        if (verUpConfirDate == null || offlineVerNumLimit == -1 || offlineVerNum == -1 || offlineVerData == -1 || offlineVerUpReqDate == null) {
            clearArea(AREA_VERSIONUP, 1, 48);
            return;
        }
        sBuffer.reset();
        try {
            sBuffer.write(fixBytes(verUpConfirDate.getBytes(), 12));
            sBuffer.write(toValueByte(offlineVerNumLimit, 8));
            sBuffer.write(toValueByte(offlineVerNum, 8));
            sBuffer.write(toValueByte(offlineVerData, 8));
            sBuffer.write(fixBytes(offlineVerUpReqDate.getBytes(), 12));
        } catch (IOException unused) {
        }
        writeArea(AREA_VERSIONUP, 1, sBuffer.toByteArray());
    }

    private void writeOfflineVerUp(ResDataVerUpConfir resDataVerUpConfir) {
        writeArea(AREA_VERSIONUP, 21, toValueByte(resDataVerUpConfir.getOfflineVerNum(), 8));
    }

    private void writePermitRvction(ResDataPermitRvction resDataPermitRvction) {
        int offlineRvctionNumLimit = resDataPermitRvction.getOfflineRvctionNumLimit();
        int offlineRvctionNum = resDataPermitRvction.getOfflineRvctionNum();
        String usedDate = resDataPermitRvction.getUsedDate();
        int offlineRvctionTerm = resDataPermitRvction.getOfflineRvctionTerm();
        String updateDate = resDataPermitRvction.getUpdateDate();
        int rvctionPointSize = resDataPermitRvction.getRvctionPointSize();
        String rvctionPoint = resDataPermitRvction.getRvctionPoint();
        int serialNumCount = resDataPermitRvction.getSerialNumCount();
        String[] rvctionList = resDataPermitRvction.getRvctionList();
        long posInFile = resDataPermitRvction.getPosInFile();
        if (offlineRvctionNumLimit == -1 || offlineRvctionNum == -1 || usedDate == null || offlineRvctionTerm == -1 || updateDate == null || rvctionPointSize == -1 || rvctionPoint == null || serialNumCount == -1) {
            clearArea(AREA_PERMIT_EXPIRE, ((int) posInFile) + 1, 2710);
            return;
        }
        sBuffer.reset();
        try {
            sBuffer.write(toValueByte(offlineRvctionNumLimit, 8));
            sBuffer.write(toValueByte(offlineRvctionNum, 8));
            sBuffer.write(fixBytes(usedDate.getBytes(), 12));
            sBuffer.write(toValueByte(offlineRvctionTerm, 8));
            sBuffer.write(fixBytes(updateDate.getBytes(), 12));
            sBuffer.write(toValueByte(rvctionPointSize, 3));
            sBuffer.write(fixBytes(rvctionPoint.getBytes(), 255));
            sBuffer.write(toValueByte(serialNumCount, 4));
            for (String str : rvctionList) {
                sBuffer.write(fixBytes(str.getBytes(), 12));
            }
            sBuffer.write(sWritePermitRvctionByte, 0, 2400 - (rvctionList.length * 12));
        } catch (IOException unused) {
        }
        writeArea(AREA_PERMIT_EXPIRE, ((int) posInFile) + 1, sBuffer.toByteArray());
    }

    private void writeOfflinePermitRvction(ResDataPermitRvction resDataPermitRvction) {
        byte[] valueByte = toValueByte(resDataPermitRvction.getOfflineRvctionNum(), 8);
        String usedDate = resDataPermitRvction.getUsedDate();
        try {
            sBuffer.reset();
            sBuffer.write(valueByte);
            sBuffer.write(fixBytes(usedDate.getBytes(), 12));
        } catch (IOException unused) {
        }
        writeArea(AREA_PERMIT_EXPIRE, ((int) resDataPermitRvction.getPosInFile()) + 9, sBuffer.toByteArray());
    }

    private void writePrblmAnalyze(ResDataPrblmAnalyze resDataPrblmAnalyze) {
        int mgmtFlag = resDataPrblmAnalyze.getMgmtFlag();
        String addInfo = resDataPrblmAnalyze.getAddInfo();
        if (mgmtFlag == -1 || addInfo == null) {
            clearArea(AREA_PROBLEM_LOG, 0, SIZE_PROBLEM_LOG);
            return;
        }
        sBuffer.reset();
        try {
            sBuffer.write(toValueByte(mgmtFlag, 1));
            sBuffer.write(fixBytes(addInfo.getBytes(), 1024, (byte) 32));
        } catch (IOException unused) {
        }
        writeArea(AREA_PROBLEM_LOG, 0, sBuffer.toByteArray());
    }

    private void writeStartParam(ResDataStartParam resDataStartParam) {
        int startParamSize = resDataStartParam.getStartParamSize();
        String startParam = resDataStartParam.getStartParam();
        String beforeStartDate = resDataStartParam.getBeforeStartDate();
        if (startParamSize == -1 || startParam == null || beforeStartDate == null) {
            clearArea(AREA_TAKE_OVER_PARAM, 1, 269);
            return;
        }
        sBuffer.reset();
        try {
            sBuffer.write(toValueByte(startParamSize, 3));
            sBuffer.write(fixBytes(startParam.getBytes(), 254));
            sBuffer.write(fixBytes(beforeStartDate.getBytes(), 12));
        } catch (IOException unused) {
        }
        writeArea(AREA_TAKE_OVER_PARAM, 1, sBuffer.toByteArray());
    }

    private void writeArea(String str, int i, byte[] bArr) {
        ResUtil.writeToArea(str, i, bArr, bArr.length, this);
    }

    private void clearArea(String str, int i, int i2) {
        ResUtil.writeToArea(str, i, new byte[i2], i2, this);
    }

    private byte[] toValueByte(int i, int i2) {
        return StringUtil.zeroPadding(String.valueOf(i), i2).getBytes();
    }

    private byte[] fixBytes(byte[] bArr, int i) {
        return fixBytes(bArr, i, (byte) 0);
    }

    private byte[] fixBytes(byte[] bArr, int i, byte b) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        for (int length = bArr.length; length < i; length++) {
            bArr2[length] = b;
        }
        return bArr2;
    }

    private ResData generateResDataVerUpConfir(byte[] bArr) {
        ResDataVerUpConfir resDataVerUpConfir = new ResDataVerUpConfir();
        resDataVerUpConfir.setResId(RESOURCE_VERSIONUP);
        resDataVerUpConfir.setVerUpConfirDate(generateString(bArr, 1, 12));
        resDataVerUpConfir.setOfflineVerNumLimit(generateInt(bArr, 13, 8));
        resDataVerUpConfir.setOfflineVerNum(generateInt(bArr, 21, 8));
        resDataVerUpConfir.setOfflineVerData(generateInt(bArr, 29, 8));
        resDataVerUpConfir.setOfflineVerUpReqDate(generateString(bArr, 37, 12));
        return resDataVerUpConfir;
    }

    private ResData generateResDataPermitRvction(byte[] bArr) {
        LogMgr.log(4, "%s : resourceData=%s", "000", bArr);
        if (bArr[0] != 0 && bArr[0] != 1) {
            LogMgr.log(1, "%s invalid edit flag", "800");
            throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
        }
        ResDataPermitRvction resDataPermitRvction = new ResDataPermitRvction();
        resDataPermitRvction.setResId(RESOURCE_PERMIT_EXPIRE);
        resDataPermitRvction.setOfflineRvctionNumLimit(generateInt(bArr, 1, 8));
        if (resDataPermitRvction.getOfflineRvctionNumLimit() > 99999999) {
            LogMgr.log(1, "%s invalid rvction num limit", "801");
            throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
        }
        resDataPermitRvction.setOfflineRvctionNum(generateInt(bArr, 9, 8));
        resDataPermitRvction.setUsedDate(generateString(bArr, 17, 12));
        if (!DateUtil.isValidDateFormat(resDataPermitRvction.getUsedDate())) {
            LogMgr.log(1, "%s invalid used date", "812");
            throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
        }
        resDataPermitRvction.setOfflineRvctionTerm(generateInt(bArr, 29, 8));
        if (resDataPermitRvction.getOfflineRvctionTerm() > 43200) {
            LogMgr.log(1, "%s invalid rvction term", "802");
            throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
        }
        resDataPermitRvction.setUpdateDate(generateString(bArr, 37, 12));
        if (!DateUtil.isValidDateFormat(resDataPermitRvction.getUpdateDate())) {
            LogMgr.log(1, "%s invalid date", "803");
            throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
        }
        resDataPermitRvction.setRvctionPointSize(generateInt(bArr, 49, 3));
        if (resDataPermitRvction.getRvctionPointSize() > 255) {
            LogMgr.log(1, "%s invalid rvction point size", "804");
            throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
        }
        resDataPermitRvction.setRvctionPoint(generateString(bArr, INDEX_EXPIRE_POINT, 255));
        if (!StringUtil.isValidURL(resDataPermitRvction.getRvctionPoint())) {
            LogMgr.log(1, "%s invalid rvction point", "805");
            throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
        }
        if (resDataPermitRvction.getRvctionPointSize() != resDataPermitRvction.getRvctionPoint().length()) {
            LogMgr.log(1, "%s rvction point size unmatch", "806");
            throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
        }
        int iGenerateInt = generateInt(bArr, INDEX_SERIAL_NUMBER_COUNT, 4);
        if (iGenerateInt > 200) {
            LogMgr.log(1, "%s invalid serial number size", "807");
            throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
        }
        if (iGenerateInt != -1) {
            resDataPermitRvction.setSerialNumCount(iGenerateInt);
            String[] strArr = new String[iGenerateInt];
            byte[] bArrDivide = divide(bArr, INDEX_EXPIRE_LIST, LENGTH_EXPIRE_LIST);
            for (int i = 0; i < iGenerateInt; i++) {
                strArr[i] = generateString(bArrDivide, i * 12, 12);
                if (strArr[i] == null) {
                    LogMgr.log(1, "%s invalid serial number(all 0x00)", "808");
                    throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
                }
                if (strArr[i].length() != 12) {
                    LogMgr.log(1, "%s invalid serial number(partial 0x00)", "809");
                    throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
                }
                if (!StringUtil.isDecOrAlpha(strArr[i])) {
                    LogMgr.log(1, "%s invalid serial number(invalid char)", "810");
                    throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
                }
            }
            int i2 = iGenerateInt * 12;
            int i3 = LENGTH_EXPIRE_LIST - i2;
            if (i3 > 0) {
                System.arraycopy(bArrDivide, i2, sRemain, 0, i3);
                if (!isAllNull(sRemain)) {
                    int i4 = 0;
                    while (true) {
                        byte[] bArr2 = sRemain;
                        if (i4 < bArr2.length) {
                            bArr2[i4] = 0;
                            i4++;
                        } else {
                            LogMgr.log(1, "%s invalid data(remain)", "811");
                            throw new SysException(BizResOptr.class, "generateResDataPermitRvction");
                        }
                    }
                }
            }
            resDataPermitRvction.setRvctionList(strArr);
        }
        LogMgr.log(4, "%s", "999");
        return resDataPermitRvction;
    }

    private ResData generatePrblmAnalyze(byte[] bArr) {
        ResDataPrblmAnalyze resDataPrblmAnalyze = new ResDataPrblmAnalyze();
        resDataPrblmAnalyze.setResId(RESOURCE_PROBLEM_LOG);
        resDataPrblmAnalyze.setMgmtFlag(divide(bArr, 0, 1)[0]);
        resDataPrblmAnalyze.setAddInfo(generateString(bArr, 1, 1024));
        return resDataPrblmAnalyze;
    }

    private ResData generateStartParam(byte[] bArr) {
        ResDataStartParam resDataStartParam = new ResDataStartParam();
        resDataStartParam.setResId(RESOURCE_TAKE_OVER_PARAM);
        resDataStartParam.setStartParamSize(generateInt(bArr, 1, 3));
        resDataStartParam.setStartParam(generateString(bArr, 4, 254));
        resDataStartParam.setBeforeStartDate(generateString(bArr, INDEX_LAST_START_DATE, 12));
        return resDataStartParam;
    }

    private int generateInt(byte[] bArr, int i, int i2) {
        byte[] bArrDivide = divide(bArr, i, i2);
        if (isAllNull(bArrDivide)) {
            return -1;
        }
        return Integer.parseInt(new String(removeNull(bArrDivide)));
    }

    private String generateString(byte[] bArr, int i, int i2) {
        byte[] bArrDivide = divide(bArr, i, i2);
        if (isAllNull(bArrDivide)) {
            return null;
        }
        return new String(removeNull(bArrDivide));
    }

    private byte[] divide(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    private boolean isAllNull(byte[] bArr) {
        for (byte b : bArr) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    private byte[] removeNull(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] == 0) {
                byte[] bArr2 = new byte[i];
                System.arraycopy(bArr, 0, bArr2, 0, i);
                return bArr2;
            }
        }
        return bArr;
    }
}

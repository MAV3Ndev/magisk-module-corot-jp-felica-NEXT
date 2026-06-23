package com.felicanetworks.mfc.mfi.omapi;

import android.os.Build;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.LocalPartialCardInfo;
import com.felicanetworks.mfc.mfi.LocalPartialCardInfoJson;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.UnsupportedMfiService1CardCache;
import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.util.MfiUtil;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import com.google.common.base.Ascii;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class AppletManager {
    private static final int APDU_HEADER_LENGTH = 5;
    private static final int LE_LENGTH = 1;
    private static final int MAX_DATA_LENGTH = 255;
    private ByteBuffer mByteBuffer;
    private GpController mGpController;
    private static final byte[] GET_TYPE_F_CURRENT_PROTOCOL_DATA = {-128, -54, 3, 0, 0};
    private static final byte[] FELICA_PARTIAL_AID = {-96, 0, 0, 6, -128, 2, 0, 0, 0, 3, 0, 0, 0, 0, 0};
    private static final byte[] GET_PACKAGE_KEY_TOKEN = {-83, -9, 42, -48, 52, 90, 107, -1, -72, -101, Ascii.GS, 46, 47, -52, -63, -74, 49, 32, -12, 62, -27, 40, -102, -74, -92, 51, -23, -69, -52, 89, -72, -96};

    public interface AppletType {
        public static final int AMSD = 3;
        public static final int CLSD = 4;
        public static final int ISD = 2;
        public static final int MANAGEMET_SYSTTEM = 0;
        public static final int SSD = 5;
        public static final int SYSTEM_0 = 1;
        public static final int TOP_LEVEL_SD = 6;
    }

    private interface JudgeInstanceUserStrategy {
        boolean judge(String cid, String serviceId, FlavorConst.ServiceParam serviceParam);
    }

    public AppletManager(GpController controller) throws UnsupportedOperationException, IllegalArgumentException {
        LogMgr.log(4, "000");
        if (controller == null) {
            LogMgr.log(1, "800 Parameter(s) must not be null.");
            throw new IllegalArgumentException();
        }
        if (Build.VERSION.SDK_INT < 28) {
            LogMgr.log(1, "801 API is insufficient. API 28+ is needed.");
            throw new UnsupportedOperationException();
        }
        this.mGpController = controller;
        this.mByteBuffer = new ByteBuffer(261);
        LogMgr.log(4, "999");
    }

    public AppletInfo getAppletInfo(int appletType) throws InterruptedException, GpException {
        AppletInfo seAppletInfo;
        LogMgr.log(4, "000");
        switch (appletType) {
            case 0:
            case 1:
                seAppletInfo = getSeAppletInfo(appletType);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                seAppletInfo = getSdAppletInfo(appletType);
                break;
            default:
                seAppletInfo = null;
                break;
        }
        LogMgr.log(4, "999");
        return seAppletInfo;
    }

    private AppletInfo getSeAppletInfo(int appletType) throws InterruptedException, GpException {
        AppletInfo system0AppletInfo;
        LogMgr.log(6, "000 applet Type = " + appletType);
        if (appletType == 0) {
            system0AppletInfo = getManagementSystemAppletInfo();
        } else {
            system0AppletInfo = appletType == 1 ? getSystem0AppletInfo() : null;
        }
        LogMgr.log(6, "999");
        return system0AppletInfo;
    }

    private AppletInfo getManagementSystemAppletInfo() throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        ManagementSystemAppletInfo managementSystemAppletInfo = new ManagementSystemAppletInfo();
        try {
            try {
                setSelectResponse(FlavorConst.MANAGEMENT_SYSTEM_AID, managementSystemAppletInfo);
                LogMgr.log(6, "001");
                GetAppletKeyInfoCommand getAppletKeyInfoCommand = new GetAppletKeyInfoCommand();
                getAppletKeyInfoCommand.setGpController(this.mGpController);
                getAppletKeyInfoCommand.setAid(FlavorConst.MANAGEMENT_SYSTEM_AID);
                byte b = GetAppletKeyInfoCommand.GET_INIT_KEY1INFO_P1P2[0];
                byte b2 = GetAppletKeyInfoCommand.GET_INIT_KEY1INFO_P1P2[1];
                byte[] bArr = GET_PACKAGE_KEY_TOKEN;
                getAppletKeyInfoCommand.setParameters(b, b2, (byte) bArr.length, bArr);
                getAppletKeyInfoCommand.set(this.mByteBuffer);
                LogMgr.log(6, "002");
                Response responseExecute = getAppletKeyInfoCommand.execute();
                if (responseExecute != null) {
                    LogMgr.log(6, "003");
                    managementSystemAppletInfo.setGetAppletKeyInfoResponse(responseExecute);
                }
                LogMgr.log(6, "999");
                return managementSystemAppletInfo;
            } catch (GpException e) {
                LogMgr.log(1, "801 : GpException");
                throw e;
            } catch (IllegalArgumentException e2) {
                LogMgr.log(1, "800 : Response format error");
                LogMgr.printStackTrace(7, e2);
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
            } catch (InterruptedException e3) {
                LogMgr.log(1, "800 : InterruptedException");
                throw e3;
            }
        } finally {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }

    private AppletInfo getSystem0AppletInfo() throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        System0AppletInfo system0AppletInfo = new System0AppletInfo();
        try {
            try {
                setSelectResponse(FlavorConst.SYSTEM0_AID, system0AppletInfo);
                LogMgr.log(6, "001");
                GetAppletKeyInfoCommand getAppletKeyInfoCommand = new GetAppletKeyInfoCommand();
                getAppletKeyInfoCommand.setGpController(this.mGpController);
                getAppletKeyInfoCommand.setAid(FlavorConst.SYSTEM0_AID);
                byte b = GetAppletKeyInfoCommand.GET_INIT_KEY1INFO_P1P2[0];
                byte b2 = GetAppletKeyInfoCommand.GET_INIT_KEY1INFO_P1P2[1];
                byte[] bArr = GET_PACKAGE_KEY_TOKEN;
                getAppletKeyInfoCommand.setParameters(b, b2, (byte) bArr.length, bArr);
                getAppletKeyInfoCommand.set(this.mByteBuffer);
                LogMgr.log(6, "002");
                Response responseExecute = getAppletKeyInfoCommand.execute();
                if (responseExecute != null) {
                    LogMgr.log(6, "003");
                    system0AppletInfo.setGetAppletKeyInfoResponse(responseExecute);
                }
                LogMgr.log(6, "999");
                return system0AppletInfo;
            } catch (GpException e) {
                LogMgr.log(1, "801 : GpException");
                throw e;
            } catch (IllegalArgumentException e2) {
                LogMgr.log(1, "800 : Response format error");
                LogMgr.printStackTrace(7, e2);
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
            } catch (InterruptedException e3) {
                LogMgr.log(1, "800 : InterruptedException");
                throw e3;
            }
        } finally {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:11:0x007f */
    private AppletInfo getSdAppletInfo(int i) throws InterruptedException, GpException {
        ClsdAppletInfo clsdAppletInfo;
        byte[] bArr;
        SdAppletInfo sdAppletInfo;
        LogMgr.log(6, "000");
        GetDataCommand getDataCommand = new GetDataCommand();
        getDataCommand.setGpController(this.mGpController);
        LogMgr.log(6, "001 Applet Type " + i);
        try {
            if (i == 4) {
                try {
                    clsdAppletInfo = new ClsdAppletInfo();
                    getDataCommand.setAid(FlavorConst.CLSD_AID);
                    getDataCommand.setParameters(GetDataCommand.SD_MANAGEMEMT_DATA_P1P2[0], GetDataCommand.SD_MANAGEMEMT_DATA_P1P2[1]);
                    getDataCommand.set(this.mByteBuffer);
                    clsdAppletInfo.setGetDataResponse(getDataCommand.execute());
                    byte[] vseIdOblationMethod = clsdAppletInfo.getVseIdOblationMethod();
                    LogMgr.log(7, "VSEID OblationMethod = " + Arrays.toString(vseIdOblationMethod));
                    if (Arrays.equals(vseIdOblationMethod, ClsdAppletInfo.VSEID_OBTAIN_METHOD_01)) {
                        bArr = GetDataCommand.VSEID01_DATA_P1P2;
                    } else {
                        bArr = Arrays.equals(vseIdOblationMethod, ClsdAppletInfo.VSEID_OBTAIN_METHOD_02) ? GetDataCommand.VSEID02_DATA_P1P2 : null;
                    }
                    if (bArr != null) {
                        getDataCommand.setParameters(bArr[0], bArr[1]);
                        getDataCommand.set(this.mByteBuffer);
                        clsdAppletInfo.setGetVseIdDataResponse(getDataCommand.execute(), bArr);
                    }
                } catch (GpException e) {
                    LogMgr.log(1, "801 : GpException");
                    throw e;
                } catch (IllegalArgumentException e2) {
                    LogMgr.log(1, "800 : Response format error");
                    LogMgr.printStackTrace(7, e2);
                    throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
                } catch (InterruptedException e3) {
                    LogMgr.log(1, "800 : InterruptedException");
                    throw e3;
                }
            } else {
                clsdAppletInfo = null;
            }
            if (i == 2) {
                IsdAppletInfo isdAppletInfo = new IsdAppletInfo();
                getDataCommand.setAid(FlavorConst.ISD_AID);
                sdAppletInfo = isdAppletInfo;
            } else if (i == 3) {
                AmsdAppletInfo amsdAppletInfo = new AmsdAppletInfo();
                getDataCommand.setAid(FlavorConst.AMSD_AID);
                sdAppletInfo = amsdAppletInfo;
            } else if (i == 5) {
                SsdAppletInfo ssdAppletInfo = new SsdAppletInfo();
                getDataCommand.setAid(FlavorConst.FNSSD_AID);
                sdAppletInfo = ssdAppletInfo;
            } else if (i != 6) {
                sdAppletInfo = clsdAppletInfo;
            } else {
                TopLevelSdAppletInfo topLevelSdAppletInfo = new TopLevelSdAppletInfo();
                getDataCommand.setAid(Property.getTopLevelSdAid());
                sdAppletInfo = topLevelSdAppletInfo;
            }
            getDataCommand.setParameters(GetDataCommand.SD_KEYVERSION_P1P2[0], GetDataCommand.SD_KEYVERSION_P1P2[1]);
            getDataCommand.set(this.mByteBuffer);
            Response responseExecute = getDataCommand.execute();
            if (sdAppletInfo != null && responseExecute != null) {
                sdAppletInfo.setGetAppletKeyInfoResponse(responseExecute);
            }
            getDataCommand.setParameters(GetDataCommand.SEQUENCECOUNTER_P1P2[0], GetDataCommand.SEQUENCECOUNTER_P1P2[1]);
            getDataCommand.set(this.mByteBuffer);
            Response responseExecute2 = getDataCommand.execute();
            if (sdAppletInfo != null && responseExecute2 != null) {
                sdAppletInfo.setSequenceCounteResponse(responseExecute2);
            }
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
            LogMgr.log(6, "999");
            return sdAppletInfo;
        } catch (Throwable th) {
            GpController gpController2 = this.mGpController;
            if (gpController2 != null) {
                gpController2.closeChannel();
            }
            throw th;
        }
    }

    private void setSelectResponse(byte[] aid, SeAppletInfo seAppletInfo) throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        GpController gpController = this.mGpController;
        byte[] bArrSelect = gpController != null ? gpController.select(aid) : null;
        if (bArrSelect != null) {
            LogMgr.log(6, "002");
            seAppletInfo.setSelectResponse(new SelectResponse(bArrSelect));
        }
        LogMgr.log(6, "999");
    }

    public SeAppletInfo getSeAppletInfo(byte[] aid) throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        if (aid == null) {
            LogMgr.log(1, "800 : AID is null.");
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        }
        try {
            SelectResponse selectResponse = this.mGpController != null ? new SelectResponse(this.mGpController.select(aid)) : null;
            SeAppletInfo seAppletInfo = new SeAppletInfo();
            seAppletInfo.setSelectResponse(selectResponse);
            LogMgr.log(6, "999");
            return seAppletInfo;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "801 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public byte[] getAppletKeyVersion(byte[] aid) throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        LogMgr.log(6, "001 AID :");
        LogMgr.logArray(6, aid);
        byte[] keyVersion = null;
        try {
            try {
                try {
                    GetAppletKeyInfoCommand getAppletKeyInfoCommand = new GetAppletKeyInfoCommand();
                    getAppletKeyInfoCommand.setGpController(this.mGpController);
                    getAppletKeyInfoCommand.setAid(aid);
                    byte b = GetAppletKeyInfoCommand.GET_INIT_KEY1INFO_P1P2[0];
                    byte b2 = GetAppletKeyInfoCommand.GET_INIT_KEY1INFO_P1P2[1];
                    byte[] bArr = GET_PACKAGE_KEY_TOKEN;
                    getAppletKeyInfoCommand.setParameters(b, b2, (byte) bArr.length, bArr);
                    getAppletKeyInfoCommand.set(this.mByteBuffer);
                    Response responseExecute = getAppletKeyInfoCommand.execute();
                    if (responseExecute != null) {
                        GetAppletKeyInfoResponse getAppletKeyInfoResponse = new GetAppletKeyInfoResponse(responseExecute.getResponse());
                        if (!getAppletKeyInfoResponse.isStatusSuccess()) {
                            LogMgr.log(1, "800 : Failed Send GetAppletKeyInfo command.");
                            throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getAppletKeyInfoResponse.getSw())), null);
                        }
                        keyVersion = getAppletKeyInfoResponse.getKeyVersion();
                    }
                    LogMgr.log(4, "999");
                    return keyVersion;
                } catch (InterruptedException e) {
                    LogMgr.log(1, "800 : InterruptedException");
                    throw e;
                }
            } catch (GpException e2) {
                LogMgr.log(1, "801 : GpException");
                throw e2;
            } catch (IllegalArgumentException e3) {
                LogMgr.log(1, "800 : Response format error");
                LogMgr.printStackTrace(7, e3);
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
            }
        } finally {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IGET]}, finally: {[IGET, INVOKE, IF] complete} */
    public byte[] getEmptyInstanceAid() throws InterruptedException, GpException {
        LogMgr.log(4, "000");
        byte[] aid = null;
        try {
            try {
                try {
                    if (Property.isGetInstanceStatusCommandSupported()) {
                        GetInstanceStatusCommand getInstanceStatusCommand = new GetInstanceStatusCommand();
                        getInstanceStatusCommand.setGpController(this.mGpController);
                        getInstanceStatusCommand.setAid(FlavorConst.MANAGEMENT_SYSTEM_AID);
                        getInstanceStatusCommand.setSearchCriteriaSelectable();
                        getInstanceStatusCommand.set(this.mByteBuffer);
                        GetInstanceStatusResponse getInstanceStatusResponse = new GetInstanceStatusResponse(getInstanceStatusCommand.execute().getResponse());
                        if (getInstanceStatusResponse.isStatusSuccess()) {
                            List<InstanceStatus> instanceStatusList = getInstanceStatusResponse.getInstanceStatusList();
                            if (!instanceStatusList.isEmpty()) {
                                aid = instanceStatusList.get(0).getAid();
                            }
                        } else if (getInstanceStatusResponse.isInstanceNotFound()) {
                            LogMgr.log(2, "700 : Instance not found.");
                        } else {
                            LogMgr.log(1, "801 : Failed Send getInstanceStatus command.");
                            throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getInstanceStatusResponse.getSw())), null);
                        }
                    } else {
                        SelectResponse selectResponse = new SelectResponse(this.mGpController.select(FELICA_PARTIAL_AID));
                        if (!selectResponse.isSelectTable()) {
                            while (true) {
                                if (!this.mGpController.selectNext()) {
                                    break;
                                }
                                SelectResponse selectResponse2 = new SelectResponse(this.mGpController.getCurrentSelectResponse());
                                if (selectResponse2.isSelectTable()) {
                                    aid = selectResponse2.getAid();
                                    break;
                                }
                            }
                        } else {
                            return selectResponse.getAid();
                        }
                    }
                    GpController gpController = this.mGpController;
                    if (gpController != null) {
                        gpController.closeChannel();
                    }
                    LogMgr.log(4, "Empty AID :");
                    LogMgr.logArray(4, aid);
                    LogMgr.log(4, "999");
                    return aid;
                } catch (IllegalArgumentException e) {
                    LogMgr.log(1, "800 : Response format error");
                    LogMgr.printStackTrace(7, e);
                    throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
                }
            } catch (GpException e2) {
                LogMgr.log(1, "803 : GpException");
                throw e2;
            } catch (InterruptedException e3) {
                LogMgr.log(1, "802 : InterruptedException");
                throw e3;
            }
        } finally {
            GpController gpController2 = this.mGpController;
            if (gpController2 != null) {
                gpController2.closeChannel();
            }
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IGET]}, finally: {[IGET, INVOKE, IF] complete} */
    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [701=5, 702=5] */
    public MigrateCardInstanceInfo getMigrateCardInstanceAid(int systemCode, String migrateCardIdm) throws InterruptedException, GpException {
        LogMgr.log(4, "000");
        try {
            try {
                try {
                    try {
                        byte[] bArrHexToByteArray = StringUtil.hexToByteArray(migrateCardIdm);
                        if (bArrHexToByteArray == null) {
                            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
                        }
                        if (Property.isGetInstanceStatusCommandSupported()) {
                            GetInstanceStatusCommand getInstanceStatusCommand = new GetInstanceStatusCommand();
                            getInstanceStatusCommand.setGpController(this.mGpController);
                            getInstanceStatusCommand.setAid(FlavorConst.MANAGEMENT_SYSTEM_AID);
                            getInstanceStatusCommand.setSearchCriteriaPersonalized();
                            getInstanceStatusCommand.setSearchCriteriaSystemCode(systemCode);
                            boolean zIsMoreDataAvailable = true;
                            while (true) {
                                if (!zIsMoreDataAvailable) {
                                    break;
                                }
                                getInstanceStatusCommand.set(this.mByteBuffer);
                                GetInstanceStatusResponse getInstanceStatusResponse = new GetInstanceStatusResponse(getInstanceStatusCommand.execute().getResponse());
                                if (getInstanceStatusResponse.isStatusSuccess()) {
                                    for (InstanceStatus instanceStatus : getInstanceStatusResponse.getInstanceStatusList()) {
                                        if (Arrays.equals(bArrHexToByteArray, instanceStatus.getIDm())) {
                                            MigrateCardInstanceInfo migrateCardInstanceInfo = new MigrateCardInstanceInfo();
                                            LogMgr.log(4, "MigrateCard AID :");
                                            LogMgr.logArray(4, instanceStatus.getAid());
                                            LogMgr.log(4, "MigrateCard CID :");
                                            LogMgr.logArray(4, instanceStatus.getCid());
                                            migrateCardInstanceInfo.setAid(instanceStatus.getAid());
                                            migrateCardInstanceInfo.setHasCid(!isNoCidKey(instanceStatus.getCid()));
                                            LogMgr.log(4, "996");
                                            return migrateCardInstanceInfo;
                                        }
                                    }
                                    zIsMoreDataAvailable = getInstanceStatusResponse.isMoreDataAvailable();
                                    if (!getInstanceStatusCommand.increaseIndex()) {
                                        LogMgr.log(2, "701 : Max Index");
                                        break;
                                    }
                                } else {
                                    if (!getInstanceStatusResponse.isInstanceNotFound()) {
                                        LogMgr.log(1, "800 : Failed Send getInstanceStatus command.");
                                        throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getInstanceStatusResponse.getSw())), null);
                                    }
                                    LogMgr.log(2, "700 : Instance not found.");
                                }
                            }
                        } else {
                            SelectResponse selectResponse = new SelectResponse(this.mGpController.select(FELICA_PARTIAL_AID));
                            if (selectResponse.isPersonalized() && Arrays.equals(bArrHexToByteArray, selectResponse.getIdm())) {
                                MigrateCardInstanceInfo migrateCardInstanceInfo2 = new MigrateCardInstanceInfo();
                                LogMgr.log(4, "MigrateCard AID :");
                                LogMgr.logArray(4, selectResponse.getAid());
                                LogMgr.log(4, "MigrateCard CID :");
                                LogMgr.logArray(4, selectResponse.getCid());
                                migrateCardInstanceInfo2.setAid(selectResponse.getAid());
                                migrateCardInstanceInfo2.setHasCid(!isNoCidKey(selectResponse.getCid()));
                                LogMgr.log(4, "997");
                                GpController gpController = this.mGpController;
                                if (gpController != null) {
                                    gpController.closeChannel();
                                }
                                return migrateCardInstanceInfo2;
                            }
                            while (this.mGpController.selectNext()) {
                                SelectResponse selectResponse2 = new SelectResponse(this.mGpController.getCurrentSelectResponse());
                                if (selectResponse2.isPersonalized() && Arrays.equals(bArrHexToByteArray, selectResponse2.getIdm())) {
                                    MigrateCardInstanceInfo migrateCardInstanceInfo3 = new MigrateCardInstanceInfo();
                                    LogMgr.log(4, "MigrateCard AID :");
                                    LogMgr.logArray(4, selectResponse2.getAid());
                                    LogMgr.log(4, "MigrateCard CID :");
                                    LogMgr.logArray(4, selectResponse2.getCid());
                                    migrateCardInstanceInfo3.setAid(selectResponse2.getAid());
                                    migrateCardInstanceInfo3.setHasCid(!isNoCidKey(selectResponse2.getCid()));
                                    LogMgr.log(4, "998");
                                    GpController gpController2 = this.mGpController;
                                    if (gpController2 != null) {
                                        gpController2.closeChannel();
                                    }
                                    return migrateCardInstanceInfo3;
                                }
                            }
                            LogMgr.log(2, "702 : Instance not found.");
                        }
                        GpController gpController3 = this.mGpController;
                        if (gpController3 != null) {
                            gpController3.closeChannel();
                        }
                        LogMgr.log(4, "999");
                        return null;
                    } catch (IllegalArgumentException e) {
                        LogMgr.log(1, "801 : Response format error");
                        LogMgr.printStackTrace(7, e);
                        throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
                    }
                } catch (NumberFormatException unused) {
                    LogMgr.log(2, "703 : NumberFormatException");
                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
                }
            } catch (GpException e2) {
                LogMgr.log(1, "803 : GpException");
                throw e2;
            } catch (InterruptedException e3) {
                LogMgr.log(1, "802 : InterruptedException");
                throw e3;
            }
        } finally {
            GpController gpController4 = this.mGpController;
            if (gpController4 != null) {
                gpController4.closeChannel();
            }
        }
    }

    public byte[] getContainerIssueInfoDirectly(byte[] aid) throws InterruptedException, GpException {
        LogMgr.log(4, "000");
        LogMgr.log(4, "AID :");
        LogMgr.logArray(4, aid);
        byte[] containerIssueInfo = null;
        try {
            try {
                try {
                    SelectResponse selectResponse = new SelectResponse(this.mGpController.select(aid));
                    if (selectResponse.isPersonalized()) {
                        GetContainerIssueInformationCommand getContainerIssueInformationCommand = new GetContainerIssueInformationCommand();
                        getContainerIssueInformationCommand.setGpController(this.mGpController);
                        getContainerIssueInformationCommand.setIdm(selectResponse.getIdm());
                        getContainerIssueInformationCommand.set(this.mByteBuffer);
                        GetContainerIssueInformationResponse getContainerIssueInformationResponse = new GetContainerIssueInformationResponse();
                        getContainerIssueInformationResponse.set(getContainerIssueInformationCommand.execute().getResponse());
                        if (!getContainerIssueInformationResponse.isStatusSuccess()) {
                            LogMgr.log(1, "802 : Failed send GetContainerIssueInformation command.");
                            throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getContainerIssueInformationResponse.getSw())), null);
                        }
                        containerIssueInfo = getContainerIssueInformationResponse.getContainerIssueInfo();
                    }
                    LogMgr.log(4, "999");
                    return containerIssueInfo;
                } catch (IllegalArgumentException e) {
                    LogMgr.log(1, "800 : Response format error");
                    LogMgr.printStackTrace(7, e);
                    throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
                }
            } catch (GpException e2) {
                LogMgr.log(1, "801 : GpException");
                throw e2;
            } catch (InterruptedException e3) {
                LogMgr.log(1, "800 : InterruptedException");
                throw e3;
            }
        } finally {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [807=4] */
    public boolean isOnlyArea0(byte[] aid) throws InterruptedException {
        GpController gpController;
        LogMgr.log(4, "000");
        LogMgr.log(4, "AID :");
        LogMgr.logArray(4, aid);
        boolean zIsOnlyArea0 = false;
        try {
            try {
                try {
                    SelectResponse selectResponse = new SelectResponse(this.mGpController.select(aid));
                    if (selectResponse.isPersonalized()) {
                        CheckOnlyArea0Command checkOnlyArea0Command = new CheckOnlyArea0Command();
                        checkOnlyArea0Command.setGpController(this.mGpController);
                        checkOnlyArea0Command.setAid(selectResponse.getAid());
                        checkOnlyArea0Command.setIdm(selectResponse.getIdm());
                        checkOnlyArea0Command.set(this.mByteBuffer);
                        CheckOnlyArea0Response checkOnlyArea0Response = new CheckOnlyArea0Response();
                        checkOnlyArea0Response.set(checkOnlyArea0Command.execute().getResponse());
                        if (checkOnlyArea0Response.isStatusSuccess()) {
                            zIsOnlyArea0 = checkOnlyArea0Response.isOnlyArea0();
                        } else {
                            LogMgr.log(2, "700 : Failed send RequestCodeList(check Area0 only) command.");
                        }
                    }
                } catch (IllegalArgumentException e) {
                    LogMgr.log(2, "701 : Response format error");
                    LogMgr.printStackTrace(7, e);
                    gpController = this.mGpController;
                    if (gpController != null) {
                    }
                }
            } catch (GpException unused) {
                LogMgr.log(2, "702 : GpException");
                gpController = this.mGpController;
                if (gpController != null) {
                }
            } catch (InterruptedException e2) {
                LogMgr.log(1, "800 : InterruptedException");
                throw e2;
            }
            LogMgr.log(4, "999");
            return zIsOnlyArea0;
        } finally {
            GpController gpController2 = this.mGpController;
            if (gpController2 != null) {
                gpController2.closeChannel();
            }
        }
    }

    public SeAppletInfo getIndividualSpCardAppletInfo() throws InterruptedException, GpException {
        SeAppletInfo seAppletInfo;
        LogMgr.log(4, "000");
        SeAppletInfo seAppletInfo2 = null;
        try {
            try {
                try {
                    if (Property.isGetInstanceStatusCommandSupported()) {
                        seAppletInfo = new SeAppletInfo();
                        GetInstanceStatusCommand getInstanceStatusCommand = new GetInstanceStatusCommand();
                        getInstanceStatusCommand.setGpController(this.mGpController);
                        getInstanceStatusCommand.setAid(FlavorConst.MANAGEMENT_SYSTEM_AID);
                        getInstanceStatusCommand.setSearchCriteriaSystemCode(3);
                        getInstanceStatusCommand.setSearchCriteriaPersonalized();
                        boolean zIsMoreDataAvailable = true;
                        loop0: while (true) {
                            if (!zIsMoreDataAvailable) {
                                break;
                            }
                            getInstanceStatusCommand.set(this.mByteBuffer);
                            GetInstanceStatusResponse getInstanceStatusResponse = new GetInstanceStatusResponse(getInstanceStatusCommand.execute().getResponse());
                            if (getInstanceStatusResponse.isStatusSuccess()) {
                                for (InstanceStatus instanceStatus : getInstanceStatusResponse.getInstanceStatusList()) {
                                    if (MfiClientConst.NO_CID_INSTANCE_KEY.equals(instanceStatus.getCidString())) {
                                        seAppletInfo.setSelectResponse(new SelectResponse(this.mGpController.select(instanceStatus.getAid())));
                                        LogMgr.log(4, "997");
                                        break loop0;
                                    }
                                }
                                zIsMoreDataAvailable = getInstanceStatusResponse.isMoreDataAvailable();
                                if (!getInstanceStatusCommand.increaseIndex()) {
                                    LogMgr.log(2, "701 : Instance not found.");
                                    break;
                                }
                            } else if (getInstanceStatusResponse.isInstanceNotFound()) {
                                LogMgr.log(2, "700 : Instance not found.");
                            } else {
                                LogMgr.log(1, "801 : Failed Send getInstanceStatus command.");
                                throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getInstanceStatusResponse.getSw())), null);
                            }
                        }
                        this.mGpController.closeChannel();
                        LogMgr.log(4, "999");
                        return seAppletInfo2;
                    }
                    SelectResponse selectResponse = new SelectResponse(this.mGpController.select(FELICA_PARTIAL_AID));
                    seAppletInfo = new SeAppletInfo();
                    if (selectResponse.isPersonalized()) {
                        LogMgr.logArray(6, selectResponse.getAid());
                        seAppletInfo.setSelectResponse(selectResponse);
                        if (selectResponse.getSystemCode() == 3 && MfiClientConst.NO_CID_INSTANCE_KEY.equals(seAppletInfo.getCidString())) {
                            LogMgr.log(4, "998");
                            return seAppletInfo;
                        }
                    } else {
                        LogMgr.log(6, "001 LifeCycleState is not PERSONALIZED");
                    }
                    while (true) {
                        if (!this.mGpController.selectNext()) {
                            break;
                        }
                        SelectResponse selectResponse2 = new SelectResponse(this.mGpController.getCurrentSelectResponse());
                        if (selectResponse2.isPersonalized()) {
                            LogMgr.logArray(6, selectResponse2.getAid());
                            seAppletInfo.setSelectResponse(selectResponse2);
                            if (selectResponse2.getSystemCode() == 3 && MfiClientConst.NO_CID_INSTANCE_KEY.equals(seAppletInfo.getCidString())) {
                                seAppletInfo2 = seAppletInfo;
                                break;
                            }
                        } else {
                            LogMgr.log(6, "002 LifeCycleState is not PERSONALIZED");
                        }
                    }
                    this.mGpController.closeChannel();
                    LogMgr.log(4, "999");
                    return seAppletInfo2;
                } catch (GpException e) {
                    throw e;
                }
            } catch (IllegalArgumentException e2) {
                LogMgr.log(1, "800 : Response format error");
                LogMgr.printStackTrace(7, e2);
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
            } catch (InterruptedException e3) {
                throw e3;
            }
        } finally {
            this.mGpController.closeChannel();
        }
    }

    public String[] getLocalCidList() throws InterruptedException, GpException {
        String cidString;
        String cidString2;
        LogMgr.log(4, "000");
        ArrayList arrayList = new ArrayList();
        try {
            try {
                try {
                    if (Property.isGetInstanceStatusCommandSupported()) {
                        GetInstanceStatusCommand getInstanceStatusCommand = new GetInstanceStatusCommand();
                        getInstanceStatusCommand.setGpController(this.mGpController);
                        getInstanceStatusCommand.setAid(FlavorConst.MANAGEMENT_SYSTEM_AID);
                        getInstanceStatusCommand.setSearchCriteriaPersonalized();
                        boolean zIsMoreDataAvailable = true;
                        while (true) {
                            if (!zIsMoreDataAvailable) {
                                break;
                            }
                            getInstanceStatusCommand.set(this.mByteBuffer);
                            GetInstanceStatusResponse getInstanceStatusResponse = new GetInstanceStatusResponse(getInstanceStatusCommand.execute().getResponse());
                            if (getInstanceStatusResponse.isStatusSuccess()) {
                                Iterator<InstanceStatus> it = getInstanceStatusResponse.getInstanceStatusList().iterator();
                                while (it.hasNext()) {
                                    String cidString3 = getCidString(it.next().getCid());
                                    if (cidString3 != null) {
                                        arrayList.add(cidString3);
                                    }
                                }
                                zIsMoreDataAvailable = getInstanceStatusResponse.isMoreDataAvailable();
                                if (!getInstanceStatusCommand.increaseIndex()) {
                                    LogMgr.log(2, "701 : Max Index");
                                    break;
                                }
                            } else if (getInstanceStatusResponse.isInstanceNotFound()) {
                                LogMgr.log(2, "700 : Instance not found.");
                            } else {
                                LogMgr.log(1, "801 : Failed Send getInstanceStatus command.");
                                throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getInstanceStatusResponse.getSw())), null);
                            }
                        }
                    } else {
                        SelectResponse selectResponse = new SelectResponse(this.mGpController.select(FELICA_PARTIAL_AID));
                        if (selectResponse.isPersonalized() && (cidString2 = getCidString(selectResponse.getCid())) != null) {
                            arrayList.add(cidString2);
                        }
                        while (this.mGpController.selectNext()) {
                            SelectResponse selectResponse2 = new SelectResponse(this.mGpController.getCurrentSelectResponse());
                            if (selectResponse2.isPersonalized() && (cidString = getCidString(selectResponse2.getCid())) != null) {
                                arrayList.add(cidString);
                            }
                        }
                    }
                    if (arrayList.isEmpty()) {
                        return new String[0];
                    }
                    Object[] array = arrayList.toArray();
                    return (String[]) Arrays.copyOf(array, array.length, String[].class);
                } catch (InterruptedException e) {
                    LogMgr.log(1, "801 : InterruptedException");
                    throw e;
                }
            } catch (GpException e2) {
                LogMgr.log(1, "802 : GpException");
                throw e2;
            } catch (IllegalArgumentException e3) {
                LogMgr.log(1, "800 : Response format error");
                LogMgr.printStackTrace(7, e3);
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
            }
        } finally {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IGET]}, finally: {[IGET, INVOKE, IF] complete} */
    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [1131=7, 1132=7] */
    public int getUnsupportMfiService1CardPosition() throws InterruptedException, GpException {
        LogMgr.log(4, "000");
        try {
            try {
                try {
                    if (Property.isGetInstanceStatusCommandSupported()) {
                        GetInstanceStatusCommand getInstanceStatusCommand = new GetInstanceStatusCommand();
                        getInstanceStatusCommand.setGpController(this.mGpController);
                        getInstanceStatusCommand.setAid(FlavorConst.MANAGEMENT_SYSTEM_AID);
                        getInstanceStatusCommand.setSearchCriteriaPersonalized();
                        getInstanceStatusCommand.setSearchCriteriaSystemCode(3);
                        boolean zIsMoreDataAvailable = true;
                        while (true) {
                            if (!zIsMoreDataAvailable) {
                                break;
                            }
                            getInstanceStatusCommand.set(this.mByteBuffer);
                            GetInstanceStatusResponse getInstanceStatusResponse = new GetInstanceStatusResponse(getInstanceStatusCommand.execute().getResponse());
                            if (getInstanceStatusResponse.isStatusSuccess()) {
                                for (InstanceStatus instanceStatus : getInstanceStatusResponse.getInstanceStatusList()) {
                                    if (isNoCidKey(instanceStatus.getCid())) {
                                        if (instanceStatus.isActivated()) {
                                            return 0;
                                        }
                                        GpController gpController = this.mGpController;
                                        if (gpController != null) {
                                            gpController.closeChannel();
                                        }
                                        return 1;
                                    }
                                }
                                zIsMoreDataAvailable = getInstanceStatusResponse.isMoreDataAvailable();
                                if (!getInstanceStatusCommand.increaseIndex()) {
                                    LogMgr.log(2, "701 : Max Index");
                                    break;
                                }
                            } else {
                                if (!getInstanceStatusResponse.isInstanceNotFound()) {
                                    LogMgr.log(1, "801 : Failed Send getInstanceStatus command.");
                                    throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getInstanceStatusResponse.getSw())), null);
                                }
                                LogMgr.log(2, "700 : Instance not found.");
                            }
                        }
                    } else {
                        SelectResponse selectResponse = new SelectResponse(this.mGpController.select(FELICA_PARTIAL_AID));
                        if (selectResponse.isPersonalized() && selectResponse.getSystemCode() == 3 && isNoCidKey(selectResponse.getCid())) {
                            if (selectResponse.isActivated()) {
                                GpController gpController2 = this.mGpController;
                                if (gpController2 != null) {
                                    gpController2.closeChannel();
                                }
                                return 0;
                            }
                            GpController gpController3 = this.mGpController;
                            if (gpController3 != null) {
                                gpController3.closeChannel();
                            }
                            return 1;
                        }
                        while (this.mGpController.selectNext()) {
                            SelectResponse selectResponse2 = new SelectResponse(this.mGpController.getCurrentSelectResponse());
                            if (selectResponse2.isPersonalized() && selectResponse2.getSystemCode() == 3 && isNoCidKey(selectResponse2.getCid())) {
                                if (selectResponse2.isActivated()) {
                                    GpController gpController4 = this.mGpController;
                                    if (gpController4 != null) {
                                        gpController4.closeChannel();
                                    }
                                    return 0;
                                }
                                GpController gpController5 = this.mGpController;
                                if (gpController5 != null) {
                                    gpController5.closeChannel();
                                }
                                return 1;
                            }
                        }
                    }
                    LogMgr.log(1, "801 : Target instance not found error");
                    UnsupportedMfiService1CardCache.getInstance().cacheNotExistUnsupportedMfiService1Card();
                    throw new GpException(MfiClientException.TYPE_CARD_NOT_FOUND, ObfuscatedMsgUtil.executionPoint(), null);
                } catch (GpException e) {
                    LogMgr.log(1, "802 : GpException");
                    throw e;
                }
            } catch (IllegalArgumentException e2) {
                LogMgr.log(1, "800 : Response format error");
                LogMgr.printStackTrace(7, e2);
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
            } catch (InterruptedException e3) {
                LogMgr.log(1, "801 : InterruptedException");
                throw e3;
            }
        } finally {
            GpController gpController6 = this.mGpController;
            if (gpController6 != null) {
                gpController6.closeChannel();
            }
        }
    }

    private String getCidString(byte[] cid) {
        LogMgr.log(6, "000");
        LogMgr.logArray(6, cid);
        if (cid == null || cid.length != 63) {
            return null;
        }
        boolean z = true;
        boolean z2 = true;
        for (byte b : cid) {
            if (b != 0) {
                z = false;
            }
            if (b != -1) {
                z2 = false;
            }
        }
        if (z || z2) {
            return null;
        }
        return new String(cid, Charset.forName("US-ASCII"));
    }

    private boolean isNoCidKey(byte[] cid) {
        LogMgr.log(4, "000");
        if (cid == null || cid.length != 63) {
            LogMgr.log(4, "999");
            return false;
        }
        boolean z = true;
        for (byte b : cid) {
            if (b != 0) {
                z = false;
            }
        }
        LogMgr.log(4, "999");
        return z;
    }

    public int[] getProtocolData() throws InterruptedException, GpException {
        LogMgr.log(4, "000");
        int[] activatedSystemCodeList = null;
        try {
            try {
                LogMgr.log(6, "001");
                GpController gpController = this.mGpController;
                if (gpController != null && gpController.select(FlavorConst.MANAGEMENT_SYSTEM_AID) != null) {
                    byte[] bArrTransmit = this.mGpController.transmit(GET_TYPE_F_CURRENT_PROTOCOL_DATA);
                    GetTypeFCurrentProtocolDataResponse getTypeFCurrentProtocolDataResponse = new GetTypeFCurrentProtocolDataResponse(bArrTransmit);
                    if (!getTypeFCurrentProtocolDataResponse.isStatusSuccess()) {
                        LogMgr.log(1, "800 : Failed Send GetTypeFCurrentProtocolData command.");
                        throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getTypeFCurrentProtocolDataResponse.getSw())), null);
                    }
                    if (bArrTransmit != null) {
                        activatedSystemCodeList = getTypeFCurrentProtocolDataResponse.getActivatedSystemCodeList();
                    }
                }
                LogMgr.log(4, "999");
                return activatedSystemCodeList;
            } catch (GpException e) {
                LogMgr.log(1, "803 : GpException");
                throw e;
            } catch (IllegalArgumentException e2) {
                LogMgr.log(1, "801 : Response format error");
                LogMgr.printStackTrace(7, e2);
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
            } catch (InterruptedException e3) {
                LogMgr.log(1, "802 : InterruptedException");
                throw e3;
            }
        } finally {
            GpController gpController2 = this.mGpController;
            if (gpController2 != null) {
                gpController2.closeChannel();
            }
        }
    }

    public LocalPartialCardInfoJson[] getLocalPartialCardInfoList(String serviceId, FlavorConst.ServiceParam serviceParam) throws JSONException, InterruptedException, GpException {
        LogMgr.log(4, "000");
        try {
            try {
                try {
                    ArrayList arrayList = new ArrayList();
                    for (FelicaInstanceInfo felicaInstanceInfo : getPersonalizedInstanceInfoList(serviceId, serviceParam)) {
                        arrayList.add(createLocalPartialCardInfoJson(felicaInstanceInfo.getIdm(), felicaInstanceInfo.isActivated(), getCidString(felicaInstanceInfo.getCid()), serviceId));
                    }
                    LocalPartialCardInfoJson[] localPartialCardInfoJsonArr = new LocalPartialCardInfoJson[arrayList.size()];
                    if (!arrayList.isEmpty()) {
                        arrayList.toArray(localPartialCardInfoJsonArr);
                    }
                    return localPartialCardInfoJsonArr;
                } catch (GpException e) {
                    LogMgr.log(1, "804 : GpException");
                    throw e;
                } catch (JSONException e2) {
                    LogMgr.log(1, "805 : localPartialCardInfo is illegal.");
                    throw e2;
                }
            } catch (IllegalArgumentException e3) {
                LogMgr.log(1, "802 : Response format error");
                LogMgr.printStackTrace(7, e3);
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
            } catch (InterruptedException e4) {
                LogMgr.log(1, "803 : InterruptedException");
                throw e4;
            }
        } finally {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCorrectCidForLocalPartialCardInfo(String cid, String serviceId, FlavorConst.ServiceParam serviceParam) {
        if (cid == null) {
            return serviceParam == FlavorConst.ServiceParam.INDIVIDUAL_SP_1;
        }
        if (serviceId != null) {
            return serviceId.equals(MfiUtil.getServiceIdFromCid(cid));
        }
        return false;
    }

    private LocalPartialCardInfoJson createLocalPartialCardInfoJson(byte[] idm, boolean isActivated, String cid, String serviceId) throws JSONException {
        LocalPartialCardInfo localPartialCardInfo;
        String strBytesToHexString = StringUtil.bytesToHexString(idm);
        if (isActivated) {
            localPartialCardInfo = new LocalPartialCardInfo(cid, strBytesToHexString, 0, serviceId);
        } else {
            localPartialCardInfo = new LocalPartialCardInfo(cid, strBytesToHexString, 1, serviceId);
        }
        return new LocalPartialCardInfoJson(localPartialCardInfo);
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [1488=4] */
    public boolean hasNoOwner(byte[] aid) throws InterruptedException {
        GpController gpController;
        LogMgr.log(4, "000");
        LogMgr.log(4, "AID :");
        LogMgr.logArray(4, aid);
        boolean zHasNoOwner = false;
        try {
            try {
                try {
                    SelectResponse selectResponse = new SelectResponse(this.mGpController.select(aid));
                    if (selectResponse.isPersonalized()) {
                        CheckKeyCombinationCommand checkKeyCombinationCommand = new CheckKeyCombinationCommand();
                        checkKeyCombinationCommand.setGpController(this.mGpController);
                        checkKeyCombinationCommand.setAid(selectResponse.getAid());
                        checkKeyCombinationCommand.setIdm(selectResponse.getIdm());
                        checkKeyCombinationCommand.set(this.mByteBuffer);
                        CheckKeyCombinationResponse checkKeyCombinationResponse = new CheckKeyCombinationResponse();
                        checkKeyCombinationResponse.set(checkKeyCombinationCommand.execute().getResponse());
                        if (checkKeyCombinationResponse.isStatusSuccess()) {
                            zHasNoOwner = checkKeyCombinationResponse.hasNoOwner();
                        } else {
                            LogMgr.log(2, "700 : Failed send RequestCodeList(check Area0 only) command.");
                        }
                    }
                } catch (IllegalArgumentException e) {
                    LogMgr.log(2, "701 : Response format error");
                    LogMgr.printStackTrace(7, e);
                    gpController = this.mGpController;
                    if (gpController != null) {
                    }
                }
            } catch (GpException unused) {
                LogMgr.log(2, "702 : GpException");
                gpController = this.mGpController;
                if (gpController != null) {
                }
            } catch (InterruptedException e2) {
                LogMgr.log(1, "800 : InterruptedException");
                throw e2;
            }
            LogMgr.log(4, "999");
            return zHasNoOwner;
        } finally {
            GpController gpController2 = this.mGpController;
            if (gpController2 != null) {
                gpController2.closeChannel();
            }
        }
    }

    public List<FelicaInstanceInfo> getPersonalizedNoCidInstanceInfoList(int systemCode) throws InterruptedException, GpException {
        return getPersonalizedInstanceInfoList(new NotUsedInstance(), systemCode, null, null);
    }

    public List<FelicaInstanceInfo> getPersonalizedInstanceInfoList(String serviceId, FlavorConst.ServiceParam serviceParam) throws InterruptedException, GpException {
        return getPersonalizedInstanceInfoList(new AlreadyUsedInstance(), serviceParam.systemCode, serviceId, serviceParam);
    }

    private List<FelicaInstanceInfo> getPersonalizedInstanceInfoList(JudgeInstanceUserStrategy judgeInstanceUserStrategy, int systemCode, String serviceId, FlavorConst.ServiceParam serviceParam) throws InterruptedException, GpException {
        LogMgr.log(4, "000");
        ArrayList arrayList = new ArrayList();
        try {
            try {
                try {
                    if (Property.isGetInstanceStatusCommandSupported()) {
                        GetInstanceStatusCommand getInstanceStatusCommand = new GetInstanceStatusCommand();
                        getInstanceStatusCommand.setGpController(this.mGpController);
                        getInstanceStatusCommand.setAid(FlavorConst.MANAGEMENT_SYSTEM_AID);
                        getInstanceStatusCommand.setSearchCriteriaPersonalized();
                        getInstanceStatusCommand.setSearchCriteriaSystemCode(systemCode);
                        boolean zIsMoreDataAvailable = true;
                        while (true) {
                            if (!zIsMoreDataAvailable) {
                                break;
                            }
                            getInstanceStatusCommand.set(this.mByteBuffer);
                            GetInstanceStatusResponse getInstanceStatusResponse = new GetInstanceStatusResponse(getInstanceStatusCommand.execute().getResponse());
                            if (getInstanceStatusResponse.isStatusSuccess()) {
                                for (InstanceStatus instanceStatus : getInstanceStatusResponse.getInstanceStatusList()) {
                                    if (judgeInstanceUserStrategy.judge(getCidString(instanceStatus.getCid()), serviceId, serviceParam)) {
                                        LogMgr.log(6, "001");
                                        arrayList.add(new FelicaInstanceInfo(instanceStatus));
                                    }
                                }
                                zIsMoreDataAvailable = getInstanceStatusResponse.isMoreDataAvailable();
                                if (!getInstanceStatusCommand.increaseIndex()) {
                                    LogMgr.log(2, "701 : Max Index");
                                    break;
                                }
                            } else if (getInstanceStatusResponse.isInstanceNotFound()) {
                                LogMgr.log(2, "700 : Instance not found.");
                            } else {
                                LogMgr.log(1, "801 : Failed Send getInstanceStatus command.");
                                throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getInstanceStatusResponse.getSw())), null);
                            }
                        }
                    } else {
                        SelectResponse selectResponse = new SelectResponse(this.mGpController.select(FELICA_PARTIAL_AID));
                        if (selectResponse.isPersonalized() && selectResponse.getSystemCode() == systemCode && judgeInstanceUserStrategy.judge(getCidString(selectResponse.getCid()), serviceId, serviceParam)) {
                            LogMgr.log(6, "002");
                            arrayList.add(new FelicaInstanceInfo(selectResponse));
                        }
                        while (this.mGpController.selectNext()) {
                            SelectResponse selectResponse2 = new SelectResponse(this.mGpController.getCurrentSelectResponse());
                            if (selectResponse2.isPersonalized() && selectResponse2.getSystemCode() == systemCode && judgeInstanceUserStrategy.judge(getCidString(selectResponse2.getCid()), serviceId, serviceParam)) {
                                LogMgr.log(6, "003");
                                arrayList.add(new FelicaInstanceInfo(selectResponse2));
                            }
                        }
                    }
                    return arrayList;
                } catch (IllegalArgumentException e) {
                    LogMgr.log(1, "802 : Response format error");
                    LogMgr.printStackTrace(7, e);
                    throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
                }
            } catch (GpException e2) {
                LogMgr.log(1, "804 : GpException");
                throw e2;
            } catch (InterruptedException e3) {
                LogMgr.log(1, "803 : InterruptedException");
                throw e3;
            }
        } finally {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
            LogMgr.log(4, "999");
        }
    }

    private class AlreadyUsedInstance implements JudgeInstanceUserStrategy {
        private AlreadyUsedInstance() {
        }

        @Override // com.felicanetworks.mfc.mfi.omapi.AppletManager.JudgeInstanceUserStrategy
        public boolean judge(String cid, String serviceId, FlavorConst.ServiceParam serviceParam) {
            return AppletManager.this.isCorrectCidForLocalPartialCardInfo(cid, serviceId, serviceParam);
        }
    }

    private class NotUsedInstance implements JudgeInstanceUserStrategy {
        @Override // com.felicanetworks.mfc.mfi.omapi.AppletManager.JudgeInstanceUserStrategy
        public boolean judge(String cid, String serviceId, FlavorConst.ServiceParam serviceParam) {
            return cid == null;
        }

        private NotUsedInstance() {
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean checkExistTargetSystem(int systemCode, String cid, byte[] aid) throws InterruptedException, GpException {
        LogMgr.log(4, "000");
        boolean z = true;
        if (cid == null) {
            LogMgr.log(1, "800 : cid is null.");
            throw new IllegalArgumentException();
        }
        try {
            if (aid == null) {
                LogMgr.log(1, "801 : aid is null.");
                throw new IllegalArgumentException();
            }
            try {
                if (this.mGpController == null) {
                    LogMgr.log(1, "802 : mGpController is null.");
                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
                }
                SelectResponse selectResponse = new SelectResponse(this.mGpController.select(aid));
                LogMgr.log(6, "001 : SelectResponse#getCid:");
                LogMgr.logArray(6, selectResponse.getCid());
                if (cid.equals(getCidString(selectResponse.getCid())) && selectResponse.isPersonalized()) {
                    LogMgr.log(6, "002 : SelectResponse#getSystemCode:" + selectResponse.getSystemCode());
                    if (systemCode != selectResponse.getSystemCode()) {
                    }
                } else {
                    z = false;
                }
                return z;
            } catch (GpException e) {
                LogMgr.log(1, "804 : GpException");
                throw e;
            } catch (InterruptedException e2) {
                LogMgr.log(1, "803 : InterruptedException");
                throw e2;
            }
        } finally {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
            LogMgr.log(4, "999");
        }
    }

    public LocalPartialCardInfoJson[] getLocalPartialCardInfoList(Map<String, FlavorConst.ServiceParam> serviceMap) throws JSONException, InterruptedException, GpException {
        LogMgr.log(4, "000");
        try {
            try {
                try {
                    try {
                        ArrayList arrayList = new ArrayList();
                        for (FelicaInstanceInfoEx felicaInstanceInfoEx : getPersonalizedInstanceInfoExList(serviceMap)) {
                            FlavorConst.ServiceParam serviceParam = serviceMap.get(felicaInstanceInfoEx.getServiceId());
                            if (serviceParam != null && serviceParam.systemCode == felicaInstanceInfoEx.getSystemCode()) {
                                arrayList.add(createLocalPartialCardInfoJson(felicaInstanceInfoEx.getIdm(), felicaInstanceInfoEx.isActivated(), getCidString(felicaInstanceInfoEx.getCid()), felicaInstanceInfoEx.getServiceId()));
                            }
                        }
                        LocalPartialCardInfoJson[] localPartialCardInfoJsonArr = new LocalPartialCardInfoJson[arrayList.size()];
                        if (!arrayList.isEmpty()) {
                            arrayList.toArray(localPartialCardInfoJsonArr);
                        }
                        return localPartialCardInfoJsonArr;
                    } catch (GpException e) {
                        LogMgr.log(1, "804 : GpException");
                        throw e;
                    }
                } catch (InterruptedException e2) {
                    LogMgr.log(1, "803 : InterruptedException");
                    throw e2;
                }
            } catch (IllegalArgumentException e3) {
                LogMgr.log(1, "802 : Response format error");
                LogMgr.printStackTrace(7, e3);
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
            } catch (JSONException e4) {
                LogMgr.log(1, "805 : localPartialCardInfo is illegal.");
                throw e4;
            }
        } finally {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }

    private List<FelicaInstanceInfoEx> getPersonalizedInstanceInfoExList(Map<String, FlavorConst.ServiceParam> serviceMap) throws InterruptedException, GpException {
        String serviceIdFromServiceMap;
        LogMgr.log(4, "000");
        ArrayList arrayList = new ArrayList();
        try {
            try {
                try {
                    if (Property.isGetInstanceStatusCommandSupported()) {
                        HashSet hashSet = new HashSet();
                        Iterator<FlavorConst.ServiceParam> it = serviceMap.values().iterator();
                        while (it.hasNext()) {
                            hashSet.add(Integer.valueOf(it.next().systemCode));
                        }
                        for (Integer num : (Integer[]) hashSet.toArray(new Integer[0])) {
                            int iIntValue = num.intValue();
                            GetInstanceStatusCommand getInstanceStatusCommand = new GetInstanceStatusCommand();
                            getInstanceStatusCommand.setGpController(this.mGpController);
                            getInstanceStatusCommand.setAid(FlavorConst.MANAGEMENT_SYSTEM_AID);
                            getInstanceStatusCommand.setSearchCriteriaPersonalized();
                            getInstanceStatusCommand.setSearchCriteriaSystemCode(iIntValue);
                            boolean zIsMoreDataAvailable = true;
                            while (true) {
                                if (zIsMoreDataAvailable) {
                                    getInstanceStatusCommand.set(this.mByteBuffer);
                                    GetInstanceStatusResponse getInstanceStatusResponse = new GetInstanceStatusResponse(getInstanceStatusCommand.execute().getResponse());
                                    if (getInstanceStatusResponse.isStatusSuccess()) {
                                        for (InstanceStatus instanceStatus : getInstanceStatusResponse.getInstanceStatusList()) {
                                            String serviceIdFromServiceMap2 = getServiceIdFromServiceMap(serviceMap, getCidString(instanceStatus.getCid()), instanceStatus.getSystemCode());
                                            if (serviceIdFromServiceMap2 != null) {
                                                LogMgr.log(6, "001");
                                                arrayList.add(new FelicaInstanceInfoEx(instanceStatus, serviceIdFromServiceMap2));
                                            }
                                        }
                                        zIsMoreDataAvailable = getInstanceStatusResponse.isMoreDataAvailable();
                                        if (!getInstanceStatusCommand.increaseIndex()) {
                                            LogMgr.log(2, "701 : Max Index");
                                            break;
                                        }
                                    } else if (getInstanceStatusResponse.isInstanceNotFound()) {
                                        LogMgr.log(2, "700 : Instance not found.");
                                    } else {
                                        LogMgr.log(1, "801 : Failed Send getInstanceStatus command.");
                                        throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getInstanceStatusResponse.getSw())), null);
                                    }
                                }
                            }
                        }
                    } else {
                        byte[] bArrSelect = this.mGpController.select(FELICA_PARTIAL_AID);
                        SelectResponse selectResponse = null;
                        do {
                            if (selectResponse == null) {
                                selectResponse = new SelectResponse(bArrSelect);
                            } else {
                                selectResponse = new SelectResponse(this.mGpController.getCurrentSelectResponse());
                            }
                            if (selectResponse.isPersonalized() && (serviceIdFromServiceMap = getServiceIdFromServiceMap(serviceMap, getCidString(selectResponse.getCid()), selectResponse.getSystemCode())) != null) {
                                LogMgr.log(6, "002");
                                arrayList.add(new FelicaInstanceInfoEx(selectResponse, serviceIdFromServiceMap));
                            }
                        } while (this.mGpController.selectNext());
                    }
                    return arrayList;
                } catch (IllegalArgumentException e) {
                    LogMgr.log(1, "802 : Response format error");
                    LogMgr.printStackTrace(7, e);
                    throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
                }
            } catch (GpException e2) {
                LogMgr.log(1, "804 : GpException");
                throw e2;
            } catch (InterruptedException e3) {
                LogMgr.log(1, "803 : InterruptedException");
                throw e3;
            }
        } finally {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
            LogMgr.log(4, "999");
        }
    }

    private String getServiceIdFromServiceMap(Map<String, FlavorConst.ServiceParam> serviceMap, String cid, int systemCode) {
        if (cid == null) {
            for (Map.Entry<String, FlavorConst.ServiceParam> entry : serviceMap.entrySet()) {
                if (systemCode == FlavorConst.ServiceParam.INDIVIDUAL_SP_1.systemCode && entry.getValue() == FlavorConst.ServiceParam.INDIVIDUAL_SP_1) {
                    return entry.getKey();
                }
            }
            return null;
        }
        String serviceIdFromCid = MfiUtil.getServiceIdFromCid(cid);
        if (serviceMap.containsKey(serviceIdFromCid) && systemCode == serviceMap.get(serviceIdFromCid).systemCode) {
            return serviceIdFromCid;
        }
        return null;
    }
}

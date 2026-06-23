package com.felicanetworks.mfc.mfi.omapi;

import android.os.Build;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class CrsManager {
    private static final int APDU_HEADER_LENGTH = 5;
    private static final byte APPLICATION_AID_TAG = 79;
    private static final int LE_LENGTH = 1;
    private static final int MAX_AID_LENGTH = 16;
    private static final int MAX_DATA_LENGTH = 255;
    private static final int MIN_AID_LENGTH = 5;
    private final byte[] FELICA_PARTIAL_AID = {-96, 0, 0, 6, -128, 2, 0, 0, 0, 3, 0, 0, 0, 0, 0};
    private final byte[] TAGLIST = {92, 3, APPLICATION_AID_TAG, -97, 112};
    private ByteBuffer mByteBuffer;
    private GpController mGpController;

    public CrsManager(GpController controller) throws UnsupportedOperationException, IllegalArgumentException {
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

    public List<byte[]> getActivatedAidList() throws InterruptedException, GpException {
        LogMgr.log(4, "000");
        GetStatusCommand getStatusCommand = new GetStatusCommand();
        getStatusCommand.setGpController(this.mGpController);
        ByteBuffer byteBuffer = new ByteBuffer(261);
        byteBuffer.setLength(0);
        byteBuffer.append(getAidDataToken(this.FELICA_PARTIAL_AID));
        byteBuffer.append(this.TAGLIST);
        int length = byteBuffer.getLength();
        byte[] bArr = new byte[length];
        byteBuffer.copy(0, bArr, 0, byteBuffer.getLength());
        getStatusCommand.setParameters(GetStatusCommand.GET_FIRST_OCCURRENCE_P1P2[0], GetStatusCommand.GET_FIRST_OCCURRENCE_P1P2[1], (byte) length, bArr);
        getStatusCommand.setAid(FlavorConst.CRS_AID);
        getStatusCommand.set(this.mByteBuffer);
        try {
            GetStatusResponse getStatusResponse = new GetStatusResponse(getStatusCommand.execute().getResponse());
            if (!getStatusResponse.isStatusSuccess()) {
                LogMgr.log(1, "800 : Failed Send GetStatus command.");
                throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getStatusResponse.getSw())), null);
            }
            LogMgr.log(4, "999");
            return getStatusResponse.getActivatedAidList();
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
    }

    public void activateApplet(byte[] aid) throws InterruptedException, GpException {
        LogMgr.log(4, "000");
        if (aid == null) {
            LogMgr.log(1, "800 : AID is null.");
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        }
        SetStatusCommand setStatusCommand = new SetStatusCommand();
        setStatusCommand.setGpController(this.mGpController);
        setStatusCommand.setAid(FlavorConst.CRS_AID);
        setStatusCommand.setParameters(SetStatusCommand.ACTIVATE_P1P2[0], SetStatusCommand.ACTIVATE_P1P2[1]);
        setStatusCommand.setDataToken(getAidDataToken(aid));
        setStatusCommand.set(this.mByteBuffer);
        try {
            ResponseAnalyzer responseAnalyzer = new ResponseAnalyzer(setStatusCommand.execute().getResponse());
            if (!responseAnalyzer.isStatusSuccess()) {
                LogMgr.log(1, "801 : Failed Send SetStatus command.");
                throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(responseAnalyzer.getSw())), null);
            }
            LogMgr.log(4, "999");
        } catch (GpException e) {
            LogMgr.log(1, "804 : GpException");
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(1, "802 : Response format error");
            LogMgr.printStackTrace(7, e2);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        } catch (InterruptedException e3) {
            LogMgr.log(1, "803 : InterruptedException");
            throw e3;
        }
    }

    public void deactivateApplet(byte[] aid) throws InterruptedException, GpException {
        LogMgr.log(4, "000");
        if (aid != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(aid);
            deactivateApplet(arrayList);
        }
        LogMgr.log(4, "999");
    }

    public void deactivateApplet(List<byte[]> aidList) throws InterruptedException, GpException {
        byte[] aidDataToken;
        LogMgr.log(4, "000");
        SetStatusCommand setStatusCommand = new SetStatusCommand();
        setStatusCommand.setGpController(this.mGpController);
        setStatusCommand.setAid(FlavorConst.CRS_AID);
        setStatusCommand.setParameters(SetStatusCommand.DEACTIVATE_P1P2[0], SetStatusCommand.DEACTIVATE_P1P2[1]);
        ByteBuffer byteBuffer = new ByteBuffer(261);
        byteBuffer.setLength(0);
        for (byte[] bArr : aidList) {
            if (!Arrays.equals(bArr, FlavorConst.SYSTEM0_AID) && (aidDataToken = getAidDataToken(bArr)) != null) {
                byteBuffer.append(aidDataToken);
            }
        }
        int length = byteBuffer.getLength();
        byte[] bArr2 = new byte[length];
        byteBuffer.copy(0, bArr2, 0, byteBuffer.getLength());
        if (length == 0) {
            LogMgr.log(6, "001: No target AID data. Skip send SET STATUS (DEACTIVATE).");
            return;
        }
        setStatusCommand.setDataToken(bArr2);
        setStatusCommand.set(this.mByteBuffer);
        try {
            ResponseAnalyzer responseAnalyzer = new ResponseAnalyzer(setStatusCommand.execute().getResponse());
            if (!responseAnalyzer.isStatusSuccess()) {
                LogMgr.log(1, "800 : Failed Send SetStatus command.");
                throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(responseAnalyzer.getSw())), null);
            }
            LogMgr.log(4, "999");
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
    }

    public synchronized Map<Integer, Map<String, InstanceStatus>> createCrsStatusTable() throws InterruptedException, GpException {
        HashMap map;
        LogMgr.log(4, "000");
        map = new HashMap();
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
                                updateCidInstanceStatusTable(map, it.next());
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
                } else {
                    SelectResponse selectResponse = new SelectResponse(this.mGpController.select(this.FELICA_PARTIAL_AID));
                    if (selectResponse.isPersonalized()) {
                        updateCidInstanceStatusTable(map, selectResponse);
                        LogMgr.logArray(6, selectResponse.getAid());
                    } else {
                        LogMgr.log(6, "001 LifeCycleState is not PERSONALIZED");
                    }
                    while (this.mGpController.selectNext()) {
                        SelectResponse selectResponse2 = new SelectResponse(this.mGpController.getCurrentSelectResponse());
                        if (selectResponse2.isPersonalized()) {
                            updateCidInstanceStatusTable(map, selectResponse2);
                            LogMgr.logArray(6, selectResponse2.getAid());
                        } else {
                            LogMgr.log(6, "002 LifeCycleState is not PERSONALIZED");
                        }
                    }
                }
                this.mGpController.closeChannel();
                LogMgr.log(4, "999");
            } catch (GpException e) {
                throw e;
            } catch (IllegalArgumentException e2) {
                LogMgr.log(1, "800 : Response format error");
                LogMgr.printStackTrace(7, e2);
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
            } catch (InterruptedException e3) {
                throw e3;
            }
        } catch (Throwable th) {
            this.mGpController.closeChannel();
            throw th;
        }
        return map;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean checkAndRecoverCrsState() throws InterruptedException, GpException {
        int[] protocolData;
        LogMgr.log(6, "000");
        boolean z = false;
        if (Property.isChipGP() && ((protocolData = new AppletManager(this.mGpController).getProtocolData()) == null || protocolData.length < 2 || protocolData[0] != 65024 || protocolData[1] != 65039)) {
            List<byte[]> activatedAidList = getActivatedAidList();
            if (activatedAidList != null) {
                Iterator<byte[]> it = activatedAidList.iterator();
                while (it.hasNext()) {
                    if (Arrays.equals(it.next(), FlavorConst.SYSTEM0_AID)) {
                        break;
                    }
                }
                activateApplet(FlavorConst.SYSTEM0_AID);
                if (activatedAidList != null) {
                    deactivateApplet(activatedAidList);
                }
                activateApplet(FlavorConst.MANAGEMENT_SYSTEM_AID);
                z = true;
            } else {
                activateApplet(FlavorConst.SYSTEM0_AID);
                if (activatedAidList != null) {
                }
                activateApplet(FlavorConst.MANAGEMENT_SYSTEM_AID);
                z = true;
            }
        }
        LogMgr.log(6, "999");
        return z;
    }

    private void updateCidInstanceStatusTable(Map<Integer, Map<String, InstanceStatus>> table, SelectResponse selectResponse) throws GpException {
        InstanceStatus instanceStatus = new InstanceStatus();
        instanceStatus.setSystemCode(selectResponse.getSystemCodeInByte());
        instanceStatus.setIDm(selectResponse.getIdm());
        instanceStatus.setAid(selectResponse.getAid());
        instanceStatus.setCid(selectResponse.getCid());
        this.mByteBuffer.setLength(0);
        this.mByteBuffer.append(selectResponse.getApplicationLifeCycleState());
        this.mByteBuffer.append(selectResponse.getContactlessActivationLifeState());
        int length = this.mByteBuffer.getLength();
        byte[] bArr = new byte[length];
        this.mByteBuffer.copy(0, bArr, 0, length);
        instanceStatus.setLifeCycleActivateState(bArr);
        updateCidInstanceStatusTable(table, instanceStatus);
    }

    private void updateCidInstanceStatusTable(Map<Integer, Map<String, InstanceStatus>> table, InstanceStatus instanceStatus) throws GpException {
        LogMgr.log(6, "000");
        try {
            Map<String, InstanceStatus> map = table.containsKey(Integer.valueOf(instanceStatus.getSystemCode())) ? table.get(Integer.valueOf(instanceStatus.getSystemCode())) : null;
            if (map == null) {
                map = new HashMap<>();
            }
            String cidString = instanceStatus.getCidString();
            if (cidString != null) {
                map.put(cidString, instanceStatus);
                table.put(Integer.valueOf(instanceStatus.getSystemCode()), map);
                LogMgr.log(6, "001 SystemCode : " + StringUtil.intToHexString(instanceStatus.getSystemCode(), 4));
                LogMgr.log(6, "002 CID : " + cidString);
                LogMgr.log(6, "003 AID : ");
                LogMgr.logArray(6, instanceStatus.getAid());
                LogMgr.log(6, "004 CRS Activate State : ".concat(instanceStatus.isActivated() ? "ACTIVATED" : "NOT ACTIVATED"));
                LogMgr.log(6, "999");
                return;
            }
            LogMgr.log(1, "800 : INVALID CID");
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        } catch (GpException e) {
            LogMgr.log(1, "802 : GpException");
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e2);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    private byte[] getAidDataToken(byte[] aid) {
        byte[] bArr;
        LogMgr.log(6, "000");
        ByteBuffer byteBuffer = new ByteBuffer(261);
        if (aid == null || aid.length <= 5 || aid.length > 16) {
            bArr = null;
        } else {
            LogMgr.log(6, "001");
            byteBuffer.setLength(0);
            byteBuffer.append(APPLICATION_AID_TAG);
            byteBuffer.append((byte) aid.length);
            byteBuffer.append(aid);
            int length = byteBuffer.getLength();
            bArr = new byte[length];
            byteBuffer.copy(0, bArr, 0, length);
        }
        LogMgr.log(6, "999");
        return bArr;
    }
}

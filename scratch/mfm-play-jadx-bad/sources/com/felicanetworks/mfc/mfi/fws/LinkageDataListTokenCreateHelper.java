package com.felicanetworks.mfc.mfi.fws;

import android.os.Build;
import com.felicanetworks.mfc.mfi.BadPropertyException;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.SeInfoEx;
import com.felicanetworks.mfc.mfi.fws.json.DeviceInfoJson;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataListRequestTokenPayloadJson;
import com.felicanetworks.mfc.mfi.fws.json.ReadSeResultJson;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.DeviceIdentificationData;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.ClsdAppletInfo;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.omapi.SdAppletInfo;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class LinkageDataListTokenCreateHelper {
    static LinkageDataListRequestTokenPayloadJson createPayloadForResetChip(final MfiChipHolder chipHolder, final DataManager dataManager) throws Throwable {
        LinkageDataListRequestTokenPayloadJson linkageDataListRequestTokenPayloadJson = new LinkageDataListRequestTokenPayloadJson();
        try {
            linkageDataListRequestTokenPayloadJson.setCnonce(FwsParamCreator.createRandomNumber());
            linkageDataListRequestTokenPayloadJson.setActionTypeCidList(6, null);
            linkageDataListRequestTokenPayloadJson.setSeInfo(dataManager.getSeInfo());
            linkageDataListRequestTokenPayloadJson.setDeviceInfo(createDeviceInfo());
            SeInfoEx seInfoEx = getSeInfoEx(chipHolder, dataManager);
            AppletManager appletManager = getAppletManager(chipHolder);
            linkageDataListRequestTokenPayloadJson.setSeDetailInfo(getTsmConfiguration(appletManager), seInfoEx.getManagementAreaIDm(), createContainerInfo(seInfoEx, appletManager), null, null);
            return linkageDataListRequestTokenPayloadJson;
        } catch (BadPropertyException e) {
            LogMgr.log(2, "701 BadPropertyException");
            throw new MfiFelicaException(e.getType(), e.getMessage());
        } catch (GpException e2) {
            LogMgr.log(2, "702 GpException");
            throw new MfiFelicaException(e2.getType(), e2.getMessage());
        } catch (InterruptedException unused) {
            LogMgr.log(1, "703 InterruptedException");
            throw new MfiFelicaException(215, null);
        } catch (JSONException e3) {
            LogMgr.log(1, "700 JSONException");
            LogMgr.printStackTrace(2, e3);
            throw new MfiFelicaException(200, "Unknown error.");
        }
    }

    static LinkageDataListRequestTokenPayloadJson createPayloadForPermanentDeleteCards(final MfiChipHolder chipHolder, final DataManager dataManager, final String[] cidList, final List<ReadSeResultJson> readSeResultJsonList) throws Throwable {
        LogMgr.log(6, "000");
        LinkageDataListRequestTokenPayloadJson linkageDataListRequestTokenPayloadJson = new LinkageDataListRequestTokenPayloadJson();
        try {
            linkageDataListRequestTokenPayloadJson.setCnonce(FwsParamCreator.createRandomNumber());
            linkageDataListRequestTokenPayloadJson.setActionTypeCidList(5, cidList, readSeResultJsonList);
            linkageDataListRequestTokenPayloadJson.setSeInfo(dataManager.getSeInfo());
            SeInfoEx seInfoEx = getSeInfoEx(chipHolder, dataManager);
            AppletManager appletManager = getAppletManager(chipHolder);
            linkageDataListRequestTokenPayloadJson.setSeDetailInfo(getTsmConfiguration(appletManager), seInfoEx.getManagementAreaIDm(), createContainerInfo(seInfoEx, appletManager), null, null);
            LogMgr.log(6, "999");
            return linkageDataListRequestTokenPayloadJson;
        } catch (GpException e) {
            LogMgr.log(2, "701 GpException");
            throw new MfiFelicaException(e.getType(), e.getMessage());
        } catch (InterruptedException unused) {
            LogMgr.log(1, "702 InterruptedException");
            throw new MfiFelicaException(215, null);
        } catch (JSONException e2) {
            LogMgr.log(1, "700 JSONException");
            LogMgr.printStackTrace(2, e2);
            throw new MfiFelicaException(200, "Unknown error.");
        }
    }

    private static AppletManager getAppletManager(final MfiChipHolder chipHolder) throws GpException {
        if (Property.isChipGP()) {
            return new AppletManager(chipHolder.getGpController());
        }
        return null;
    }

    private static JSONObject createDeviceInfo() throws BadPropertyException, JSONException {
        LogMgr.log(6, "000");
        DeviceInfoJson deviceInfoJson = new DeviceInfoJson();
        deviceInfoJson.setDeviceType(Property.getDeviceType());
        deviceInfoJson.setDeviceName(Build.MODEL);
        deviceInfoJson.setDeviceManufacturer(Build.MANUFACTURER);
        String str = new DeviceIdentificationData().get();
        if (str != null) {
            deviceInfoJson.setDeviceIdentificationData(str);
        }
        deviceInfoJson.setPlatformType("02");
        deviceInfoJson.setCarrierId(Property.getCareerIdentifyCode());
        deviceInfoJson.setDeviceId(Property.getMobileDeviceInformation());
        LogMgr.log(6, "999");
        return deviceInfoJson;
    }

    private static SeInfoEx getSeInfoEx(final MfiChipHolder chipHolder, final DataManager dataManager) throws Throwable {
        LogMgr.log(6, "000");
        MfiFelicaWrapper mfiFelicaWrapper = null;
        try {
            MfiFelicaWrapper mfiFelicaWrapper2 = new MfiFelicaWrapper(chipHolder);
            try {
                mfiFelicaWrapper2.open();
                SeInfoEx seInfoExCreateSeInfoEx = dataManager.createSeInfoEx(mfiFelicaWrapper2);
                mfiFelicaWrapper2.closeSilently();
                LogMgr.log(6, "999");
                return seInfoExCreateSeInfoEx;
            } catch (Throwable th) {
                th = th;
                mfiFelicaWrapper = mfiFelicaWrapper2;
                if (mfiFelicaWrapper != null) {
                    mfiFelicaWrapper.closeSilently();
                }
                LogMgr.log(6, "999");
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static String getTsmConfiguration(final AppletManager appletManager) throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        if (!Property.isChipGP()) {
            LogMgr.log(6, "997");
            return null;
        }
        ClsdAppletInfo clsdAppletInfo = (ClsdAppletInfo) getSdAppletInfo(appletManager);
        if (clsdAppletInfo != null) {
            LogMgr.log(6, "998");
            return StringUtil.bytesToHexString(clsdAppletInfo.getSdManagementDataTsmSetting());
        }
        LogMgr.log(6, "999");
        return null;
    }

    private static SdAppletInfo getSdAppletInfo(final AppletManager appletManager) throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        ClsdAppletInfo clsdAppletInfo = (ClsdAppletInfo) appletManager.getAppletInfo(4);
        LogMgr.log(6, "999");
        return clsdAppletInfo;
    }

    private static JSONObject createContainerInfo(final SeInfoEx seInfoEx, final AppletManager appletManager) throws JSONException, InterruptedException, GpException {
        String containerIssueInformation;
        LogMgr.log(6, "000");
        if (Property.isChipGP()) {
            containerIssueInformation = StringUtil.bytesToHexString(appletManager.getContainerIssueInfoDirectly(FlavorConst.MANAGEMENT_SYSTEM_AID));
        } else {
            containerIssueInformation = seInfoEx.getContainerIssueInformation();
        }
        LogMgr.log(6, "999");
        return new LinkageDataListRequestTokenPayloadJson().createContainerInfoJson(seInfoEx.getIcCode(), seInfoEx.getContainerId(), containerIssueInformation);
    }
}

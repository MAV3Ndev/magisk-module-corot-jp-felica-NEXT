package com.felicanetworks.semc;

import com.felicanetworks.semc.permit.SpAppInfo;
import com.felicanetworks.semc.util.LogMgr;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class DataManager {
    private String mSeid = null;
    Map<String, String> mClientControlInfo = null;
    private String mAndroidOsVersion = null;
    private String mSepId = null;
    private String mDeviceName = null;
    private String mCarrierId = null;
    private String mSpAppIdForNotify = null;
    private SpAppInfo mCallerSpAppInfo = null;
    private String mSemClientVersion = null;
    private String mSemClientVersionAdditionalInfo = null;
    private String mClientId = null;
    private String mProfileId = null;
    private String mDeviceManufacturer = null;
    private String mDeviceIdentificationData = null;
    private String mChipType = null;
    private String mSeReaderName = null;
    private String mIntegrityVerificationUniqueValue = null;
    private String mPackageName = null;
    private String mPlayIntegrityToken = null;
    private boolean mIsCalledFromSemcBackground = false;

    public synchronized String getSeid() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mSeid;
    }

    public synchronized void setSeid(String str) {
        LogMgr.log(6, "000 seid=[" + str + "]");
        this.mSeid = str;
        LogMgr.log(6, "999");
    }

    public synchronized Map<String, String> getClientControlInfo() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mClientControlInfo;
    }

    public synchronized void setClientControlInfo(Map<String, String> map) {
        LogMgr.log(6, "000");
        this.mClientControlInfo = map;
        LogMgr.log(6, "999");
    }

    public synchronized String getAndroidOsVersion() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mAndroidOsVersion;
    }

    public synchronized void setAndroidOsVersion(String str) {
        LogMgr.log(6, "000 AndroidOsVersion=[" + str + "]");
        this.mAndroidOsVersion = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getSepId() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mSepId;
    }

    public synchronized void setSepId(String str) {
        LogMgr.log(6, "000 SepId=[" + str + "]");
        this.mSepId = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getDeviceName() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mDeviceName;
    }

    public synchronized void setDeviceName(String str) {
        LogMgr.log(6, "000 DeviceName=[" + str + "]");
        this.mDeviceName = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getCarrierId() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 CarrierId=[" + this.mCarrierId + "]");
        return this.mCarrierId;
    }

    public synchronized void setCarrierId(String str) {
        LogMgr.log(6, "000 CarrierId=[" + str + "]");
        this.mCarrierId = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getSpAppIdForNotify() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mSpAppIdForNotify;
    }

    public synchronized void setSpAppIdForNotify(String str) {
        LogMgr.log(6, "000 id=" + str);
        this.mSpAppIdForNotify = str;
        LogMgr.log(6, "999");
    }

    public synchronized SpAppInfo getCallerSpAppInfo() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mCallerSpAppInfo;
    }

    public synchronized void setCallerSpAppInfo(SpAppInfo spAppInfo) {
        LogMgr.log(6, "000");
        this.mCallerSpAppInfo = spAppInfo;
        LogMgr.log(6, "999");
    }

    public synchronized String getSemClientVersion() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mSemClientVersion;
    }

    public synchronized String getSemClientVersionAdditionalInfo() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mSemClientVersionAdditionalInfo;
    }

    public synchronized void setSemClientVersion(String str) {
        LogMgr.log(6, "000 SemClientVersion=[" + str + "]");
        this.mSemClientVersion = str;
        LogMgr.log(6, "999");
    }

    public synchronized void setSemClientVersionAdditionalInfo(String str) {
        LogMgr.log(6, "000 SemClientVersionAdditionalInfo=[" + str + "]");
        this.mSemClientVersionAdditionalInfo = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getClientId() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mClientId;
    }

    public synchronized void setClientId(String str) {
        LogMgr.log(6, "000 ClientId=[" + str + "]");
        this.mClientId = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getProfileId() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mProfileId;
    }

    public synchronized void setProfileId(String str) {
        LogMgr.log(6, "000 ProfileId=[" + str + "]");
        this.mProfileId = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getDeviceManufacturer() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mDeviceManufacturer;
    }

    public synchronized void setDeviceManufacturer(String str) {
        LogMgr.log(6, "000 deviceManufacturer=[" + str + "]");
        this.mDeviceManufacturer = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getDeviceIdentificationData() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mDeviceIdentificationData;
    }

    public synchronized void setDeviceIdentificationData(String str) {
        LogMgr.log(6, "000 deviceIdentificationDat=[" + str + "]");
        this.mDeviceIdentificationData = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getChipType() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mChipType;
    }

    public synchronized void setChipType(String str) {
        LogMgr.log(6, "000 chipType=[" + str + "]");
        this.mChipType = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getSeReaderName() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mSeReaderName;
    }

    public synchronized void setSeReaderName(String str) {
        LogMgr.log(6, "000 seReaderName=[" + str + "]");
        this.mSeReaderName = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getIntegrityVerificationUniqueValue() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mIntegrityVerificationUniqueValue;
    }

    public synchronized void setIntegrityVerificationUniqueValue(String str) {
        LogMgr.log(6, "000");
        this.mIntegrityVerificationUniqueValue = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getPackageName() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mPackageName;
    }

    public synchronized void setPackageName(String str) {
        LogMgr.log(6, "000");
        this.mPackageName = str;
        LogMgr.log(6, "999");
    }

    public synchronized String getPlayIntegrityToken() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mPlayIntegrityToken;
    }

    public synchronized void setPlayIntegrityToken(String str) {
        LogMgr.log(6, "000");
        this.mPlayIntegrityToken = str;
        LogMgr.log(6, "999");
    }

    public synchronized void setIsCalledFromSemcBackground(boolean z) {
        LogMgr.log(6, "000");
        this.mIsCalledFromSemcBackground = z;
        LogMgr.log(6, "999");
    }

    public synchronized boolean getIsCalledFromSemcBackground() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mIsCalledFromSemcBackground;
    }
}

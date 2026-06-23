package com.felicanetworks.mfm.main.model.info;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Service {
    public static final String SERVICE_TYPE_1_MFI = "M";
    public static final String SERVICE_TYPE_2_FULL = "MF";
    public static final String SERVICE_TYPE_2_SIMPLE = "MS";
    private String cardCategory;
    private byte[] cardFaceImage;
    private String color;
    private String contactEmail;
    private String contactName;
    private String contactPhone;
    private String contactUrl;
    private String detailImagePresence;
    private List<DetailLinkageApp> detailLinkageAppList;
    private List<DetailLinkageWeb> detailLinkageWebList;
    private String downloadType;
    private String downloadUrl;
    private String linkType;
    private String pkg;
    private String priority;
    private String provider;
    private String serviceId;
    private String serviceName;
    private String serviceType;
    private String sigHash;
    private String version;
    private String webUrl;

    public Service(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, byte[] bArr, String str16, String str17, String str18, String str19, List<DetailLinkageApp> list, List<DetailLinkageWeb> list2) {
        this.detailLinkageAppList = new ArrayList();
        this.detailLinkageWebList = new ArrayList();
        this.serviceId = str;
        this.version = str2;
        this.serviceName = str3;
        this.provider = str4;
        this.linkType = str5;
        this.pkg = str6;
        this.sigHash = str7;
        this.downloadType = str8;
        this.downloadUrl = str9;
        this.webUrl = str10;
        this.priority = str11;
        this.color = str12;
        this.serviceType = str13;
        this.cardCategory = str14;
        this.detailImagePresence = str15;
        this.cardFaceImage = bArr;
        this.contactName = str16;
        this.contactPhone = str17;
        this.contactUrl = str18;
        this.contactEmail = str19;
        this.detailLinkageAppList = list;
        this.detailLinkageWebList = list2;
    }

    public void setServiceId(String str) {
        this.serviceId = str;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public void setServiceName(String str) {
        this.serviceName = str;
    }

    public void setProvider(String str) {
        this.provider = str;
    }

    public void setLinkType(String str) {
        this.linkType = str;
    }

    public void setPkg(String str) {
        this.pkg = str;
    }

    public void setSigHash(String str) {
        this.sigHash = str;
    }

    public void setDownloadType(String str) {
        this.downloadType = str;
    }

    public void setDownloadUrl(String str) {
        this.downloadUrl = str;
    }

    public void setWebUrl(String str) {
        this.webUrl = str;
    }

    public void setPriority(String str) {
        this.priority = str;
    }

    public void setColor(String str) {
        this.color = str;
    }

    public void setServiceType(String str) {
        this.serviceType = str;
    }

    public void setCardCategory(String str) {
        this.cardCategory = str;
    }

    public void setDetailImagePresence(String str) {
        this.detailImagePresence = str;
    }

    public void setCardFaceImage(byte[] bArr) {
        this.cardFaceImage = bArr;
    }

    public void setContactName(String str) {
        this.contactName = str;
    }

    public void setContactPhone(String str) {
        this.contactPhone = str;
    }

    public void setContactUrl(String str) {
        this.contactUrl = str;
    }

    public void setContactEmail(String str) {
        this.contactEmail = str;
    }

    public void setDetailLinkageAppList(List<DetailLinkageApp> list) {
        this.detailLinkageAppList = list;
    }

    public void setDetailLinkageWebList(List<DetailLinkageWeb> list) {
        this.detailLinkageWebList = list;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public String getVersion() {
        return this.version;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getProvider() {
        return this.provider;
    }

    public String getLinkType() {
        return this.linkType;
    }

    public String getPkg() {
        return this.pkg;
    }

    public String getSigHash() {
        return this.sigHash;
    }

    public String getDownloadType() {
        return this.downloadType;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public String getWebUrl() {
        return this.webUrl;
    }

    public String getPriority() {
        return this.priority;
    }

    public String getColor() {
        return this.color;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public String getCardCategory() {
        return this.cardCategory;
    }

    public String getDetailImagePresence() {
        return this.detailImagePresence;
    }

    public byte[] getCardFaceImage() {
        return this.cardFaceImage;
    }

    public String getContactName() {
        return this.contactName;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }

    public String getContactUrl() {
        return this.contactUrl;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public List<DetailLinkageApp> getDetailLinkageAppList() {
        return this.detailLinkageAppList;
    }

    public List<DetailLinkageWeb> getDetailLinkageWebList() {
        return this.detailLinkageWebList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DatabaseExpert$Service{id:");
        sb.append(this.serviceId);
        sb.append(",version:");
        sb.append(this.version);
        sb.append(",name:");
        sb.append(this.serviceName);
        sb.append(",provider:");
        sb.append(this.provider);
        sb.append(",linkType:");
        sb.append(this.linkType);
        sb.append(",pkg:");
        sb.append(this.pkg);
        sb.append(",sigHash:");
        sb.append(this.sigHash);
        sb.append(",downloadType:");
        sb.append(this.downloadType);
        sb.append(",downloadUrl:");
        sb.append(this.downloadUrl);
        sb.append(",webUrl:");
        sb.append(this.webUrl);
        sb.append(",priority:");
        sb.append(this.priority);
        sb.append(",color:");
        sb.append(this.color);
        sb.append(",serviceType:");
        sb.append(this.serviceType);
        sb.append(",cardCategory:");
        sb.append(this.cardCategory);
        sb.append(",detailImagePresence:");
        sb.append(this.detailImagePresence);
        sb.append(",cardFaceImage size:");
        byte[] bArr = this.cardFaceImage;
        sb.append(bArr == null ? "null" : Integer.valueOf(bArr.length));
        sb.append(",contactName:");
        sb.append(this.contactName);
        sb.append(",contactPhone:");
        sb.append(this.contactPhone);
        sb.append(",contactUrl:");
        sb.append(this.contactUrl);
        sb.append(",contactEmail:");
        sb.append(this.contactEmail);
        sb.append(",detailLinkageAppList size:");
        sb.append(this.detailLinkageAppList.size());
        sb.append(",detailLinkageWebList:");
        sb.append(this.detailLinkageWebList.size());
        sb.append(",}");
        return sb.toString();
    }

    public boolean isMfi() {
        return this.serviceType.startsWith(SERVICE_TYPE_1_MFI);
    }

    public boolean isType1() {
        return this.serviceType.startsWith(SERVICE_TYPE_2_SIMPLE);
    }

    public boolean isType2() {
        return this.serviceType.startsWith(SERVICE_TYPE_2_FULL);
    }

    public boolean isOriginalAreaService() {
        String str = this.serviceType;
        return str != null && str.length() == 8 && "P".equals(String.valueOf(this.serviceType.charAt(2)));
    }
}

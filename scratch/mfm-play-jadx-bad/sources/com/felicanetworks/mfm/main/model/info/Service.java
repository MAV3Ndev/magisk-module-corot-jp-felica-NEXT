package com.felicanetworks.mfm.main.model.info;

import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class Service implements ServiceIdPolicy.ServiceHolder {
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

    public Service(String serviceId, String version, String serviceName, String provider, String linkType, String pkg, String sigHash, String downloadType, String downloadUrl, String webUrl, String priority, String color, String serviceType, String cardCategory, String detailImagePresence, byte[] cardFaceImage, String contactName, String contactPhone, String contactUrl, String contactEmail, List<DetailLinkageApp> detailLinkageAppList, List<DetailLinkageWeb> detailLinkageWebList) {
        this.detailLinkageAppList = new ArrayList();
        new ArrayList();
        this.serviceId = serviceId;
        this.version = version;
        this.serviceName = serviceName;
        this.provider = provider;
        this.linkType = linkType;
        this.pkg = pkg;
        this.sigHash = sigHash;
        this.downloadType = downloadType;
        this.downloadUrl = downloadUrl;
        this.webUrl = webUrl;
        this.priority = priority;
        this.color = color;
        this.serviceType = serviceType;
        this.cardCategory = cardCategory;
        this.detailImagePresence = detailImagePresence;
        this.cardFaceImage = cardFaceImage;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactUrl = contactUrl;
        this.contactEmail = contactEmail;
        this.detailLinkageAppList = detailLinkageAppList;
        this.detailLinkageWebList = detailLinkageWebList;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public void setSigHash(String sigHash) {
        this.sigHash = sigHash;
    }

    public void setDownloadType(String downloadType) {
        this.downloadType = downloadType;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setCardCategory(String cardCategory) {
        this.cardCategory = cardCategory;
    }

    public void setDetailImagePresence(String detailImagePresence) {
        this.detailImagePresence = detailImagePresence;
    }

    public void setCardFaceImage(byte[] cardFaceImage) {
        this.cardFaceImage = cardFaceImage;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setDetailLinkageAppList(List<DetailLinkageApp> detailLinkageAppList) {
        this.detailLinkageAppList = detailLinkageAppList;
    }

    public void setDetailLinkageWebList(List<DetailLinkageWeb> detailLinkageWebList) {
        this.detailLinkageWebList = detailLinkageWebList;
    }

    @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceHolder
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

    @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceHolder
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
        StringBuilder sb = new StringBuilder("DatabaseExpert$Service{id:");
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

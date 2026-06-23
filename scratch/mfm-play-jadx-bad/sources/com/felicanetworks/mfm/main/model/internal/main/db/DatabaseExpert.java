package com.felicanetworks.mfm.main.model.internal.main.db;

import android.text.TextUtils;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.main.model.info.CommonInfo;
import com.felicanetworks.mfm.main.model.info.ContactInfo;
import com.felicanetworks.mfm.main.model.info.DetailLinkageApp;
import com.felicanetworks.mfm.main.model.info.DetailLinkageWeb;
import com.felicanetworks.mfm.main.model.info.GeneralInfo;
import com.felicanetworks.mfm.main.model.info.LinkageApplicationInfo;
import com.felicanetworks.mfm.main.model.info.LinkageInfo;
import com.felicanetworks.mfm.main.model.info.MfiCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.MyCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.model.internal.main.pkg.PackageExpert;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.semc.sws.SwsClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class DatabaseExpert {
    public static int ERR_INDEX_CACHE_APP = 328;
    public static int ERR_INDEX_CACHE_AREA = 326;
    public static int ERR_INDEX_CACHE_ASSET = 329;
    public static int ERR_INDEX_CACHE_SAS = 327;
    public static int EX_ERR_INFO_CACHE = 3276;
    private ModelContext _modelContext;
    private MfmDatabaseAccess mfmdb = null;

    public static abstract class ServiceId {
        public String id;
        public String version;

        protected ServiceId(String id, String version) {
            this.id = id;
            this.version = version;
        }
    }

    public static class AreaId extends ServiceId {
        public String areaCode;
        public String cache;
        public String sysCode;

        public AreaId(String sysCode, String areaCode, String id, String version, String cache) {
            super(id, version);
            this.sysCode = sysCode;
            this.areaCode = areaCode;
            this.cache = cache;
        }

        public String toString() {
            return "DatabaseExpert$AreaId{id:" + this.id + ",version:" + this.version + ",sysCode:" + this.sysCode + ",areaCode:" + this.areaCode + ",cache:" + this.cache + "}";
        }
    }

    public static class AppId extends ServiceId {
        public String pkgName;
        public String sigHash;

        public AppId(String pkgName, String sigHash, String id, String version) {
            super(id, version);
            this.pkgName = pkgName;
            this.sigHash = sigHash;
        }

        public String toString() {
            return "DatabaseExpert$AppId{id:" + this.id + ",version:" + this.version + ",pkgName:" + this.pkgName + ",sigHash:" + this.sigHash + "}";
        }
    }

    public static class MultiId extends ServiceId {
        public String cache;
        public String code;
        public String type;

        public MultiId(String type, String code, String id, String version, String cache) {
            super(id, version);
            this.type = type;
            this.code = code;
            this.cache = cache;
        }

        public String toString() {
            return "DatabaseExpert$MultiId{id:" + this.id + ",version:" + this.version + ",type:" + this.type + ",code:" + this.code + ",cache:" + this.cache + "}";
        }
    }

    public static class ServiceWithIcon extends Service {
        public byte[] icon;
        byte[] iconEx1;
        byte[] iconEx2;

        public enum IconType {
            ICON { // from class: com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.ServiceWithIcon.IconType.1
                @Override // com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.ServiceWithIcon.IconType
                String Id() {
                    return "0";
                }
            },
            ICONEx1 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.ServiceWithIcon.IconType.2
                @Override // com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.ServiceWithIcon.IconType
                String Id() {
                    return "1";
                }
            },
            ICONEx2 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.ServiceWithIcon.IconType.3
                @Override // com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.ServiceWithIcon.IconType
                String Id() {
                    return "2";
                }
            };

            String Id() {
                return null;
            }
        }

        public ServiceWithIcon(String id, String version, String name, String provider, String linkType, String pkg, String sigHash, String downloadType, String downloadUrl, String webUrl, String priority, String color, byte[] icon, byte[] iconEx1, byte[] iconEx2, String serviceType, String cardCategory, String detailImagePresence, byte[] cardFaceImage, String contactName, String contactPhone, String contactUrl, String contactEmail, List<DetailLinkageApp> linkageAppInfoList, List<DetailLinkageWeb> linkageWebInfoList) {
            super(id, version, name, provider, linkType, pkg, sigHash, downloadType, downloadUrl, webUrl, priority, color, serviceType, cardCategory, detailImagePresence, cardFaceImage, contactName, contactPhone, contactUrl, contactEmail, linkageAppInfoList, linkageWebInfoList);
            this.icon = (byte[]) icon.clone();
            this.iconEx1 = (byte[]) iconEx1.clone();
            this.iconEx2 = (byte[]) iconEx2.clone();
        }

        @Override // com.felicanetworks.mfm.main.model.info.Service
        public String toString() {
            return super.toString() + "with icon{icon size:" + this.icon.length + ",iconEx1 size:" + this.iconEx1.length + ",iconEx2 size:" + this.iconEx2.length + "}";
        }
    }

    public static class IdsSettings {
        public int interval;
        public String lastUpdate;

        public IdsSettings(int interval, String lastUpdate) {
            this.interval = interval;
            this.lastUpdate = lastUpdate;
        }

        public String toString() {
            return "DatabaseExpert$IdsSettings{interval:" + this.interval + ",lastUpdate:" + this.lastUpdate + "}";
        }
    }

    public static class UpgradeSettings {
        static final String DEFAULT_LAST_CHECK_DATE = "000000000000";
        public int chkCnt;
        public int interval;
        public String lastChkDate;
        public int maxCnt;

        public UpgradeSettings(int interval, String lastChkDate, int chkCnt, int maxCnt) {
            this.interval = interval;
            this.lastChkDate = lastChkDate;
            this.chkCnt = chkCnt;
            this.maxCnt = maxCnt;
        }

        public boolean isDefault() {
            return DEFAULT_LAST_CHECK_DATE.equals(this.lastChkDate);
        }

        public String toString() {
            return "DatabaseExpert$UpgradeSettings{interval:" + this.interval + ",lastChkDate:" + this.lastChkDate + ",chkCnt:" + this.chkCnt + ",maxCnt:" + this.maxCnt + "}";
        }
    }

    public static class TosVersionSettings extends UpgradeSettings {
        public final int version;

        public TosVersionSettings(int interval, String lastChkDate, int chkCnt, int maxCnt, int version) {
            super(interval, lastChkDate, chkCnt, maxCnt);
            this.version = version;
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.UpgradeSettings
        public String toString() {
            return "TosVersionSettings{interval=" + this.interval + ", lastChkDate='" + this.lastChkDate + "', chkCnt=" + this.chkCnt + ", maxCnt=" + this.maxCnt + ", version=" + this.version + '}';
        }
    }

    public static class BookmarkSettings {
        public String lastUpdateDate;

        public BookmarkSettings() {
            this(SwsClient.URL_SERVICE_ID);
        }

        public BookmarkSettings(String lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
        }

        public String toString() {
            return "DatabaseExpert$BookmarkSettings{lastUpdateDate:" + this.lastUpdateDate + ",}";
        }
    }

    public static class Recommend extends ServiceId {
        public String categoryId;
        public String categoryName;
        public String detail;
        public String downloadType;
        public String downloadUrl;
        public String linkType;
        public String name;
        public String procedure1;
        public String procedure2;
        public String procedure3;
        public String procedure4;
        public String procedure5;
        public String provider;
        public String status;
        public String summary;
        public String webUrl;

        public Recommend(String id, String version, String categoryId, String categoryName, String name, String provider, String status, String linkType, String downloadType, String downloadUrl, String webUrl, String summary, String detail, String procedure1, String procedure2, String procedure3, String procedure4, String procedure5) {
            super(id, version);
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.name = name;
            this.provider = provider;
            this.status = status;
            this.linkType = linkType;
            this.downloadType = downloadType;
            this.downloadUrl = downloadUrl;
            this.webUrl = webUrl;
            this.summary = summary;
            this.detail = detail;
            this.procedure1 = procedure1;
            this.procedure2 = procedure2;
            this.procedure3 = procedure3;
            this.procedure4 = procedure4;
            this.procedure5 = procedure5;
        }

        public String toString() {
            return "DatabaseExpert$Recommend{id:" + this.id + ",version:" + this.version + ",categoryId:" + this.categoryId + ",categoryName:" + this.categoryName + ",name:" + this.name + ",provider:" + this.provider + ",status:" + this.status + ",linkType:" + this.linkType + ",downloadType:" + this.downloadType + ",downloadUrl:" + this.downloadUrl + ",webUrl:" + this.webUrl + ",summary:" + this.summary + ",detail:" + this.detail + ",procedure1:" + this.procedure1 + ",procedure2:" + this.procedure2 + ",procedure3:" + this.procedure3 + ",procedure4:" + this.procedure4 + ",procedure5:" + this.procedure5 + "}";
        }
    }

    public static class FpSetting {
        public String lastUpdateDate;

        public FpSetting() {
            this(SwsClient.URL_SERVICE_ID);
        }

        public FpSetting(String lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
        }

        public String toString() {
            return "FpSetting{lastUpdateDate='" + this.lastUpdateDate + "'}";
        }
    }

    public static class FelicaPocketService {
        private static final long NEED_SHOW_VALUE = 1;
        private static final String POINT_SERVICE = "01";
        public String fpProviderName;
        public String fpServiceIconUrl;
        public String fpServiceKind;
        public String fpServiceName;
        public String fpServiceNumber;
        public long needShowValue;
        public String pointUnitName;
        public long serviceEnable;
        public String webUrl;

        public FelicaPocketService(String fpServiceNumber, String fpServiceName, String fpServiceIconUrl, String fpProviderName, int serviceEnable, String webUrl, String fpServiceKind, int needShowValue, String pointUnitName) {
            this.fpServiceNumber = fpServiceNumber;
            this.fpServiceName = fpServiceName;
            this.fpServiceIconUrl = fpServiceIconUrl;
            this.fpProviderName = fpProviderName;
            this.serviceEnable = serviceEnable;
            this.webUrl = webUrl;
            this.fpServiceKind = fpServiceKind;
            this.needShowValue = needShowValue;
            this.pointUnitName = pointUnitName;
        }

        public String toString() {
            return "FelicaPocketService{fpServiceNumber='" + this.fpServiceNumber + "', fpServiceName='" + this.fpServiceName + "', fpServiceIconUrl='" + this.fpServiceIconUrl + "', fpProviderName='" + this.fpProviderName + "', serviceEnable=" + this.serviceEnable + ", webUrl='" + this.webUrl + "', fpServiceKind=" + this.fpServiceKind + ", needShowValue=" + this.needShowValue + ", pointUnitName='" + this.pointUnitName + "'}";
        }

        public boolean isPointService() {
            return "01".equals(this.fpServiceKind);
        }

        public boolean needShowValue() {
            return NEED_SHOW_VALUE == this.needShowValue;
        }
    }

    public static class BannerSettings {
        public String lastCheckDate;
        public BannerPos pos;
        public String upToDate;

        public BannerSettings(BannerPos pos) {
            this(pos, "000000000000", SwsClient.URL_SERVICE_ID);
        }

        public BannerSettings(BannerPos pos, String upToDate, String lastCheckDate) {
            this.pos = pos;
            this.upToDate = upToDate;
            this.lastCheckDate = lastCheckDate;
        }

        public String toString() {
            return "DatabaseExpert$BannerSettings{pos:" + this.pos + ",upToDate:" + this.upToDate + ",lastCheckDate:" + this.lastCheckDate + "}";
        }
    }

    public enum BannerPos {
        MY_SERVICE { // from class: com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.BannerPos.1
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.BannerPos
            String posId() {
                return "0";
            }
        },
        NOTIFICATION { // from class: com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.BannerPos.2
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.BannerPos
            String posId() {
                return "1";
            }
        };

        String posId() {
            return null;
        }

        public static BannerPos resolvePos(String posId) {
            for (BannerPos bannerPos : values()) {
                if (bannerPos.posId().equals(posId)) {
                    return bannerPos;
                }
            }
            return null;
        }
    }

    public static class Banner {
        public String end;
        public String id;
        public byte[] imgBinary;
        public String imgName;
        public String posId;
        public int priority;
        public String scheme;
        public String start;
        public String update;

        public Banner(String posId, String id, int priority, byte[] imgBinary, String imgName, String scheme, String start, String end, String update) {
            this.posId = posId;
            this.id = id;
            this.priority = priority;
            this.imgBinary = (byte[]) imgBinary.clone();
            this.imgName = imgName;
            this.scheme = scheme;
            this.start = start;
            this.end = end;
            this.update = update;
        }

        public String toString() {
            return "DatabaseExpert$Banner{pos:" + this.posId + ",id:" + this.id + ",priority:" + this.priority + ",imgBinary size:" + this.imgBinary.length + ",imgName:" + this.imgName + ",scheme:" + this.scheme + ",start:" + this.start + ",end:" + this.end + ",update:" + this.update + "}";
        }
    }

    public static class BannerText {
        public BannerPos bannerPos;
        public String end;
        public String id;
        public String imgName;
        public int priority;
        public String scheme;
        public String start;

        public BannerText(BannerPos bannerPos, String id, int priority, String imgName, String scheme, String start, String end) {
            this.bannerPos = bannerPos;
            this.id = id;
            this.priority = priority;
            this.imgName = imgName;
            this.scheme = scheme;
            this.start = start;
            this.end = end;
        }

        public String toString() {
            return "DatabaseExpert$BannerText{pos:" + this.bannerPos.posId() + ",id:" + this.id + ",priority:" + this.priority + ",imgName:" + this.imgName + ",scheme:" + this.scheme + ",start:" + this.start + ",end:" + this.end + "}";
        }
    }

    public static class BannerImage {
        public BannerPos bannerPos;
        public String id;
        public byte[] imgBinary;
        public String update;

        public BannerImage(BannerPos bannerPos, String id, byte[] imgBinary, String update) {
            this.bannerPos = bannerPos;
            this.id = id;
            this.imgBinary = (byte[]) imgBinary.clone();
            this.update = update;
        }

        public String toString() {
            return "DatabaseExpert$BannerImage{pos:" + this.bannerPos.posId() + ",id:" + this.id + ",imgBinary size:" + this.imgBinary.length + ",update:" + this.update + "}";
        }
    }

    public static class CardFaceImageInfo {
        public String _cid;
        public byte[] _image;
        public String _url;

        public CardFaceImageInfo(String cid, String url, byte[] image) {
            this._cid = cid;
            this._url = url;
            this._image = image;
        }

        public String toString() {
            return "CardFaceImageInfo{cid:" + this._cid + ",url:" + this._url + ",image:" + Arrays.toString(this._image) + "}";
        }
    }

    public static class BgUpdateCardArtInfo {
        public String _cardId;

        public BgUpdateCardArtInfo(String cardId) {
            this._cardId = cardId;
        }

        public String toString() {
            return "BgUpdateCardArtInfo{cardId:" + this._cardId + "}";
        }
    }

    public static class MfiHashValueInfo {
        public String cardId;
        public String mfiHashValue;

        public MfiHashValueInfo(String cardId, String mfiHashValue) {
            this.cardId = cardId;
            this.mfiHashValue = mfiHashValue;
        }

        public String toString() {
            return "DatabaseExpert$MfiHashValueInfo{cardId:" + this.cardId + ",mfiHashValue" + this.mfiHashValue + "}";
        }
    }

    public synchronized List<AreaId> getAreaIds() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 17, e);
        }
        return new ArrayList(this.mfmdb.getAreaItem());
    }

    public synchronized List<AppId> getAppIds() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 33, e);
        }
        return new ArrayList(this.mfmdb.getApplicationItem());
    }

    public synchronized List<MultiId> getMultiIds() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 49, e);
        }
        return new ArrayList(this.mfmdb.getMultiPurposeIdentifierItem());
    }

    public synchronized List<Service> getService() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 65, e);
        }
        return new ArrayList(this.mfmdb.getServiceItem());
    }

    public synchronized Service getServiceWhereServiceId(String serviceId) throws DatabaseException {
        Service service;
        try {
            Iterator it = new ArrayList(this.mfmdb.getServiceItem()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    service = null;
                    break;
                }
                service = (Service) it.next();
                if (TextUtils.equals(serviceId, service.getServiceId())) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 66, e);
        }
        return service;
    }

    public synchronized IdsSettings getIdsSettings() throws DatabaseException {
        return this.mfmdb.getSettingItem();
    }

    public synchronized void setIds(IdsSettings settings, List<AreaId> areaIds, List<AppId> appIds, List<MultiId> multiIds) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                if (areaIds != null && appIds != null && multiIds != null) {
                    this.mfmdb.replaceAreaItem(areaIds);
                    this.mfmdb.replaceMultiPurposeIdentifierItem(multiIds);
                    this.mfmdb.replaceApplicationItem(appIds);
                }
                this.mfmdb.updateSettingItem(settings);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 113, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void delIds(List<String> serviceIds) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                if (serviceIds != null) {
                    this.mfmdb.deleteAreaInfo(serviceIds);
                    this.mfmdb.deleteAppInfo(serviceIds);
                    this.mfmdb.deleteMultiPurposeIdentifierItem(serviceIds);
                    this.mfmdb.deleteServiceItem(serviceIds);
                    this.mfmdb.deleteBookmarkItem(serviceIds);
                }
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 114, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void setServices(List<ServiceWithIcon> upList, List<String> delList) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                if (delList != null && !delList.isEmpty()) {
                    this.mfmdb.deleteServiceItem(delList);
                }
                if (upList != null && !upList.isEmpty()) {
                    this.mfmdb.addOrUpdateServiceItem(upList);
                }
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 129, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized UpgradeSettings getUpgradeSettings() throws DatabaseException {
        return this.mfmdb.getUpdateInfoItem();
    }

    public synchronized void setUpgradeSettings(UpgradeSettings settings) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.updateUpdateInfoItem(settings);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), MfiClientException.TYPE_SE_ACCESS_ERROR, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized TosVersionSettings getTosVersionSettings() throws DatabaseException {
        return this.mfmdb.getPolicyUpdateInfoItem();
    }

    public synchronized void setTosVersionSettings(TosVersionSettings settings) throws DatabaseException {
        try {
            this.mfmdb.beginTransaction();
            this.mfmdb.updatePolicyUpdateInfoItem(settings);
            this.mfmdb.setTransactionSuccessful();
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized BookmarkSettings getBookmarkSettings() throws DatabaseException {
        BookmarkSettings bookmarkSettings;
        bookmarkSettings = this.mfmdb.getBookmarkSettings();
        if (bookmarkSettings == null) {
            bookmarkSettings = new BookmarkSettings();
        }
        return bookmarkSettings;
    }

    public synchronized void setBookmarkSettings(BookmarkSettings settings) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.setBookmarkSettings(settings);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 278, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized List<Recommend> getRecommends() throws DatabaseException {
        return this.mfmdb.getBookmarkItem();
    }

    public synchronized void setRecommends(List<Recommend> list) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.replaceBookmarkItem(list);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 193, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized FpSetting getFelicaPocketSettings() throws DatabaseException {
        FpSetting fpSetting;
        try {
            fpSetting = this.mfmdb.getFpSetting();
            if (fpSetting == null) {
                fpSetting = new FpSetting();
            }
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 307, e);
        }
        return fpSetting;
    }

    public synchronized FelicaPocketService getFpService(int fpServiceNumber) throws DatabaseException {
        try {
            for (FelicaPocketService felicaPocketService : this.mfmdb.getFpServiceInfoList()) {
                if (String.format(Locale.US, "%08d", Integer.valueOf(fpServiceNumber)).equals(felicaPocketService.fpServiceNumber)) {
                    return felicaPocketService;
                }
            }
            return null;
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 306, e);
        }
    }

    public synchronized void setFelicaPocketSettings(FpSetting setting) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.setFpSetting(setting);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 81, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void setFelicaPocketService(List<FelicaPocketService> fpServices) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.replaceFpServiceItem(fpServices);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 194, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized List<MfiCardAdditionalInfo> getMfiCardAdditionalInfo() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 213, e);
        }
        return this.mfmdb.getMfiCardAdditionalInfo();
    }

    public synchronized List<MfiHashValueInfo> getMfiHashValueInfo(List<String> cidList) throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 215, e);
        }
        return this.mfmdb.getMfiHashValue(cidList);
    }

    private List<MfiCardAdditionalInfo> convertInfoToDb(List<MyCardAdditionalInfo> addInfos, List<MfiHashValueInfo> addHashValue) {
        ArrayList arrayList = new ArrayList();
        for (MyCardAdditionalInfo myCardAdditionalInfo : addInfos) {
            if (myCardAdditionalInfo.getAdditionalInfo() != null) {
                arrayList.add(new MfiCardAdditionalInfo(myCardAdditionalInfo.getCid(), myCardAdditionalInfo.getAdditionalInfo().languageCode, convertLinkInfoToDb(myCardAdditionalInfo.getAdditionalInfo().linkageInfoList), convertCommonInfoToDb(myCardAdditionalInfo.getAdditionalInfo().commonInformation), extractionMfiCardAdditionalInfoValue(myCardAdditionalInfo.getCid(), addHashValue), myCardAdditionalInfo.getAdditionalInfo().transitInfo));
            }
        }
        return arrayList;
    }

    private List<MfiCardAdditionalInfo> convertInfoToDb(List<MyCardAdditionalInfo> addInfos) {
        ArrayList arrayList = new ArrayList();
        for (MyCardAdditionalInfo myCardAdditionalInfo : addInfos) {
            if (myCardAdditionalInfo.getAdditionalInfo() != null) {
                arrayList.add(new MfiCardAdditionalInfo(myCardAdditionalInfo.getCid(), myCardAdditionalInfo.getAdditionalInfo().languageCode, convertLinkInfoToDb(myCardAdditionalInfo.getAdditionalInfo().linkageInfoList), convertCommonInfoToDb(myCardAdditionalInfo.getAdditionalInfo().commonInformation), myCardAdditionalInfo.getAdditionalInfoHash(), myCardAdditionalInfo.getAdditionalInfo().transitInfo));
            }
        }
        return arrayList;
    }

    private List<LinkageInfo> convertLinkInfoToDb(List<LinkageInfo> linkageInfoList) {
        ArrayList arrayList = new ArrayList();
        for (LinkageInfo linkageInfo : linkageInfoList) {
            arrayList.add(new LinkageInfo(linkageInfo.linkageKind == LinkageInfo.LinkageKind.WEB ? LinkageInfo.LinkageKind.WEB : LinkageInfo.LinkageKind.APP, linkageInfo.linkageName, linkageInfo.linkageApplicationInfo != null ? convertLinkAppInfoToDb(linkageInfo.linkageApplicationInfo) : null, linkageInfo.linkageWebsiteURL));
        }
        return arrayList;
    }

    private LinkageApplicationInfo convertLinkAppInfoToDb(LinkageApplicationInfo linkageApplicationInfo) {
        return new LinkageApplicationInfo(linkageApplicationInfo.appIconUrl, linkageApplicationInfo.appIdentifiableInfo, linkageApplicationInfo.appCallerInfo, linkageApplicationInfo.appGetKind, linkageApplicationInfo.appGetUrl);
    }

    private CommonInfo convertCommonInfoToDb(CommonInfo commonInformation) {
        return new CommonInfo(convertGeneralInfoToDb(commonInformation.generalInformation), commonInformation.contact != null ? convertContactInfoToDb(commonInformation.contact) : null);
    }

    private GeneralInfo convertGeneralInfoToDb(GeneralInfo generalInformation) {
        return new GeneralInfo(generalInformation.cardTypeName, generalInformation.cardArtUrl, generalInformation.contactName, generalInformation.specialCardArtUrl);
    }

    private ContactInfo convertContactInfoToDb(ContactInfo contact) {
        return new ContactInfo(contact.name, contact.phoneNumber, contact.url, contact.email);
    }

    private String extractionMfiCardAdditionalInfoValue(String cid, List<MfiHashValueInfo> mfiCardAdditionalInfoValueInfoList) {
        String str = null;
        for (MfiHashValueInfo mfiHashValueInfo : mfiCardAdditionalInfoValueInfoList) {
            if (mfiHashValueInfo.cardId.equals(cid)) {
                str = mfiHashValueInfo.mfiHashValue;
            }
        }
        return str;
    }

    public synchronized void setMfiCardAdditionalInfo(List<MyCardAdditionalInfo> addInfo, List<MfiHashValueInfo> addHashValue) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.updateMfiCardAdditionalInfo(convertInfoToDb(addInfo, addHashValue));
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 214, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void setMfiCardAdditionalInfo(List<MyCardAdditionalInfo> addInfo) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.updateMfiCardAdditionalInfo(convertInfoToDb(addInfo));
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 214, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void deleteMfiCardAdditionalInfo(String cid) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.deleteMfiCardAdditionalInfo(cid);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 216, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized List<Banner> getMyServiceBannerList() throws DatabaseException {
        return this.mfmdb.getBannerList(BannerPos.MY_SERVICE);
    }

    public synchronized BannerSettings getMyServiceBannerSettings() throws DatabaseException {
        BannerSettings bannerSettings;
        bannerSettings = this.mfmdb.getBannerSettings(BannerPos.MY_SERVICE);
        if (bannerSettings == null) {
            bannerSettings = new BannerSettings(BannerPos.MY_SERVICE);
        }
        return bannerSettings;
    }

    public synchronized List<Banner> getNoticeBannerList() throws DatabaseException {
        return this.mfmdb.getBannerList(BannerPos.NOTIFICATION);
    }

    public synchronized BannerSettings getNoticeBannerSettings() throws DatabaseException {
        BannerSettings bannerSettings;
        bannerSettings = this.mfmdb.getBannerSettings(BannerPos.NOTIFICATION);
        if (bannerSettings == null) {
            bannerSettings = new BannerSettings(BannerPos.NOTIFICATION);
        }
        return bannerSettings;
    }

    public synchronized void setBannerTexts(List<BannerText> bannerTexts) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.deleteBannerTexts(bannerTexts);
                this.mfmdb.setBannerTexts(bannerTexts);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 273, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void setBannerSettings(BannerSettings settngs) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.setBannerSettings(settngs);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 289, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void setBanner(BannerImage bannerImage) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.setBannerImage(bannerImage);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 305, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized byte[] getIcon(String id) throws DatabaseException {
        return this.mfmdb.getIconData(id, ServiceWithIcon.IconType.ICON);
    }

    public synchronized byte[] getIconEx1(String id) throws DatabaseException {
        return this.mfmdb.getIconData(id, ServiceWithIcon.IconType.ICONEx1);
    }

    public synchronized byte[] getIconEx2(String id) throws DatabaseException {
        return this.mfmdb.getIconData(id, ServiceWithIcon.IconType.ICONEx2);
    }

    public synchronized List<Boolean> isExists(List<String> ids) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator<String> it = ids.iterator();
        while (it.hasNext()) {
            arrayList.add(Boolean.valueOf(this.mfmdb.isExistService(it.next())));
        }
        return arrayList;
    }

    public synchronized void deleteAccountRelatedInfo() throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.deleteMfiCardAdditionalInfo();
                this.mfmdb.deleteCardFaceImage();
                this.mfmdb.deleteBgUpdateCardArtInfo();
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void addCardFaceImage(String cardId, String url, byte[] rawImage) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.addCardFaceImage(cardId, url, rawImage);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 321, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void updateCardFaceImage(String cardId, String url, byte[] rawImage) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.updateCardFaceImage(cardId, url, rawImage);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 322, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void deleteCardFaceImage(String cardId) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.deleteCardFaceImage(cardId);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 325, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void deleteRelatedCardFaceImage(String cardId) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.deleteRelatedCardFaceImage(cardId);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 333, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized CardFaceImageInfo getCardFaceImage(String id) throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 323, e);
        }
        return this.mfmdb.getCardFaceImage(id);
    }

    public synchronized void addBgUpdateCardArtInfo(String cardId) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.addBgUpdateCardArtInfo(cardId);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 326, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void deleteBgUpdateCardArtInfo(String cardId) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.deleteBgUpdateCardArtInfo(cardId);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 327, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void deleteAllBgUpdateCardArtInfo() throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.deleteBgUpdateCardArtInfo();
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 328, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized boolean isBgUpdateCardArtInfoExists(String cardId) throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 329, e);
        }
        return this.mfmdb.isBgUpdateCardArtInfoExists(cardId);
    }

    public synchronized List<BgUpdateCardArtInfo> getBgUpdateCardArtInfoList() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 330, e);
        }
        return this.mfmdb.getBgUpdateCardArtInfoList();
    }

    public synchronized Map<String, Object> getSpecificColumnData(String id, List<String> columns) throws DatabaseException {
        return this.mfmdb.getSpecificColumnData(id, columns);
    }

    public synchronized void updateSpecificColumnData(String id, Map<String, Object> map) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.updateSpecificColumnData(id, map);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 324, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized List<MfcExpert.Area> getCacheChipAreaList() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), ERR_INDEX_CACHE_AREA, e, EX_ERR_INFO_CACHE);
        }
        return new ArrayList(this.mfmdb.getCacheChipAreaItem());
    }

    public synchronized boolean getCacheChipAreaListSetting() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), ERR_INDEX_CACHE_AREA, e, EX_ERR_INFO_CACHE);
        }
        return this.mfmdb.getCacheChipAreaSetting();
    }

    public synchronized List<MfcExpert.Sas> getCacheChipSasList() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), ERR_INDEX_CACHE_SAS, e, EX_ERR_INFO_CACHE);
        }
        return new ArrayList(this.mfmdb.getCacheChipSasItem());
    }

    public synchronized boolean getCacheChipSasListSetting() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), ERR_INDEX_CACHE_SAS, e, EX_ERR_INFO_CACHE);
        }
        return this.mfmdb.getCacheChipSasSetting();
    }

    public synchronized List<PackageExpert.InstalledApp> getCacheInstalledAppList() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), ERR_INDEX_CACHE_APP, e, EX_ERR_INFO_CACHE);
        }
        return new ArrayList(this.mfmdb.getCacheInstalledAppList());
    }

    public synchronized boolean getCacheInstalledAppListSetting() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), ERR_INDEX_CACHE_APP, e, EX_ERR_INFO_CACHE);
        }
        return this.mfmdb.getCacheInstalledAppListSetting();
    }

    public synchronized List<MfcExpert.Asset> getCacheAssetList() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), ERR_INDEX_CACHE_ASSET, e, EX_ERR_INFO_CACHE);
        }
        return new ArrayList(this.mfmdb.getCacheAssetList());
    }

    public synchronized boolean getCacheAssetListSetting() throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), ERR_INDEX_CACHE_ASSET, e, EX_ERR_INFO_CACHE);
        }
        return this.mfmdb.getCacheAssetListSetting();
    }

    public synchronized void setCacheChipAreaAndSasList(List<MfcExpert.Area> area, List<MfcExpert.Sas> sas) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                if (area != null) {
                    this.mfmdb.replaceCacheChipAreaItem(area);
                    this.mfmdb.replaceCacheChipAreaSetting(true);
                    this.mfmdb.replaceCacheChipSasItem(sas);
                    this.mfmdb.replaceCacheChipSasSetting(true);
                }
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 330, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void setCacheInstalledAppList(List<PackageExpert.InstalledApp> app) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                if (app != null) {
                    this.mfmdb.replaceCacheInstalledAppList(app);
                    this.mfmdb.replaceCacheInstalledAppListSetting(true);
                }
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 331, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void setCacheAssetList(List<MfcExpert.Asset> assets) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                if (assets != null) {
                    this.mfmdb.replaceCacheAssetList(assets);
                    this.mfmdb.replaceCacheAssetListSetting(true);
                }
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 332, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void deleteCache() throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.deleteCache();
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public DatabaseExpert(ModelContext modelContext) {
        this._modelContext = modelContext;
    }

    public synchronized DatabaseExpert open() throws DatabaseException {
        this.mfmdb = new MfmDatabaseAccess(this._modelContext.getLegacyContext());
        return this;
    }

    public synchronized DatabaseExpert close() {
        MfmDatabaseAccess mfmDatabaseAccess = this.mfmdb;
        if (mfmDatabaseAccess != null) {
            try {
                try {
                    mfmDatabaseAccess.close();
                } catch (DatabaseException e) {
                    LogUtil.warning(e);
                }
            } finally {
                this.mfmdb = null;
            }
        }
        return this;
    }

    protected DatabaseExpert() {
    }
}

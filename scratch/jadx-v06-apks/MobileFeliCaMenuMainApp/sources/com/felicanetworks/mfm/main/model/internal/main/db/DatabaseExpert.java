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
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseExpert {
    private ModelContext _modelContext;
    private MfmDatabaseAccess mfmdb = null;

    public static abstract class ServiceId {
        public String id;
        public String version;

        protected ServiceId(String str, String str2) {
            this.id = str;
            this.version = str2;
        }
    }

    public static class AreaId extends ServiceId {
        public String areaCode;
        public String cache;
        public String sysCode;

        public AreaId(String str, String str2, String str3, String str4, String str5) {
            super(str3, str4);
            this.sysCode = str;
            this.areaCode = str2;
            this.cache = str5;
        }

        public String toString() {
            return "DatabaseExpert$AreaId{id:" + this.id + ",version:" + this.version + ",sysCode:" + this.sysCode + ",areaCode:" + this.areaCode + ",cache:" + this.cache + "}";
        }
    }

    public static class AppId extends ServiceId {
        public String pkgName;
        public String sigHash;

        public AppId(String str, String str2, String str3, String str4) {
            super(str3, str4);
            this.pkgName = str;
            this.sigHash = str2;
        }

        public String toString() {
            return "DatabaseExpert$AppId{id:" + this.id + ",version:" + this.version + ",pkgName:" + this.pkgName + ",sigHash:" + this.sigHash + "}";
        }
    }

    public static class MultiId extends ServiceId {
        public String cache;
        public String code;
        public String type;

        public MultiId(String str, String str2, String str3, String str4, String str5) {
            super(str3, str4);
            this.type = str;
            this.code = str2;
            this.cache = str5;
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

        public ServiceWithIcon(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, byte[] bArr, byte[] bArr2, byte[] bArr3, String str13, String str14, String str15, byte[] bArr4, String str16, String str17, String str18, String str19, List<DetailLinkageApp> list, List<DetailLinkageWeb> list2) {
            super(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15, bArr4, str16, str17, str18, str19, list, list2);
            this.icon = (byte[]) bArr.clone();
            this.iconEx1 = (byte[]) bArr2.clone();
            this.iconEx2 = (byte[]) bArr3.clone();
        }

        @Override // com.felicanetworks.mfm.main.model.info.Service
        public String toString() {
            return super.toString() + "with icon{icon size:" + this.icon.length + ",iconEx1 size:" + this.iconEx1.length + ",iconEx2 size:" + this.iconEx2.length + "}";
        }
    }

    public static class IdsSettings {
        public int interval;
        public String lastUpdate;

        public IdsSettings(int i, String str) {
            this.interval = i;
            this.lastUpdate = str;
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

        public UpgradeSettings(int i, String str, int i2, int i3) {
            this.interval = i;
            this.lastChkDate = str;
            this.chkCnt = i2;
            this.maxCnt = i3;
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

        public TosVersionSettings(int i, String str, int i2, int i3, int i4) {
            super(i, str, i2, i3);
            this.version = i4;
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.UpgradeSettings
        public String toString() {
            return "TosVersionSettings{interval=" + this.interval + ", lastChkDate='" + this.lastChkDate + "', chkCnt=" + this.chkCnt + ", maxCnt=" + this.maxCnt + ", version=" + this.version + '}';
        }
    }

    public static class BookmarkSettings {
        public String lastUpdateDate;

        public BookmarkSettings() {
            this("00000000");
        }

        public BookmarkSettings(String str) {
            this.lastUpdateDate = str;
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

        public Recommend(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18) {
            super(str, str2);
            this.categoryId = str3;
            this.categoryName = str4;
            this.name = str5;
            this.provider = str6;
            this.status = str7;
            this.linkType = str8;
            this.downloadType = str9;
            this.downloadUrl = str10;
            this.webUrl = str11;
            this.summary = str12;
            this.detail = str13;
            this.procedure1 = str14;
            this.procedure2 = str15;
            this.procedure3 = str16;
            this.procedure4 = str17;
            this.procedure5 = str18;
        }

        public String toString() {
            return "DatabaseExpert$Recommend{id:" + this.id + ",version:" + this.version + ",categoryId:" + this.categoryId + ",categoryName:" + this.categoryName + ",name:" + this.name + ",provider:" + this.provider + ",status:" + this.status + ",linkType:" + this.linkType + ",downloadType:" + this.downloadType + ",downloadUrl:" + this.downloadUrl + ",webUrl:" + this.webUrl + ",summary:" + this.summary + ",detail:" + this.detail + ",procedure1:" + this.procedure1 + ",procedure2:" + this.procedure2 + ",procedure3:" + this.procedure3 + ",procedure4:" + this.procedure4 + ",procedure5:" + this.procedure5 + "}";
        }
    }

    public static class FpSetting {
        public String lastUpdateDate;

        public FpSetting() {
            this("00000000");
        }

        public FpSetting(String str) {
            this.lastUpdateDate = str;
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

        public FelicaPocketService(String str, String str2, String str3, String str4, int i, String str5, String str6, int i2, String str7) {
            this.fpServiceNumber = str;
            this.fpServiceName = str2;
            this.fpServiceIconUrl = str3;
            this.fpProviderName = str4;
            this.serviceEnable = i;
            this.webUrl = str5;
            this.fpServiceKind = str6;
            this.needShowValue = i2;
            this.pointUnitName = str7;
        }

        public String toString() {
            return "FelicaPocketService{fpServiceNumber='" + this.fpServiceNumber + "', fpServiceName='" + this.fpServiceName + "', fpServiceIconUrl='" + this.fpServiceIconUrl + "', fpProviderName='" + this.fpProviderName + "', serviceEnable=" + this.serviceEnable + ", webUrl='" + this.webUrl + "', fpServiceKind=" + this.fpServiceKind + ", needShowValue=" + this.needShowValue + ", pointUnitName='" + this.pointUnitName + "'}";
        }

        public boolean isPointService() {
            return "01".equals(this.fpServiceKind);
        }

        public boolean needShowValue() {
            return 1 == this.needShowValue;
        }
    }

    public static class BannerSettings {
        public String lastCheckDate;
        public BannerPos pos;
        public String upToDate;

        public BannerSettings(BannerPos bannerPos) {
            this(bannerPos, "000000000000", "00000000");
        }

        public BannerSettings(BannerPos bannerPos, String str, String str2) {
            this.pos = bannerPos;
            this.upToDate = str;
            this.lastCheckDate = str2;
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

        public static BannerPos resolvePos(String str) {
            for (BannerPos bannerPos : values()) {
                if (bannerPos.posId().equals(str)) {
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

        public Banner(String str, String str2, int i, byte[] bArr, String str3, String str4, String str5, String str6, String str7) {
            this.posId = str;
            this.id = str2;
            this.priority = i;
            this.imgBinary = (byte[]) bArr.clone();
            this.imgName = str3;
            this.scheme = str4;
            this.start = str5;
            this.end = str6;
            this.update = str7;
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

        public BannerText(BannerPos bannerPos, String str, int i, String str2, String str3, String str4, String str5) {
            this.bannerPos = bannerPos;
            this.id = str;
            this.priority = i;
            this.imgName = str2;
            this.scheme = str3;
            this.start = str4;
            this.end = str5;
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

        public BannerImage(BannerPos bannerPos, String str, byte[] bArr, String str2) {
            this.bannerPos = bannerPos;
            this.id = str;
            this.imgBinary = (byte[]) bArr.clone();
            this.update = str2;
        }

        public String toString() {
            return "DatabaseExpert$BannerImage{pos:" + this.bannerPos.posId() + ",id:" + this.id + ",imgBinary size:" + this.imgBinary.length + ",update:" + this.update + "}";
        }
    }

    public static class CardFaceImageInfo {
        public String _cid;
        public byte[] _image;
        public String _url;

        public CardFaceImageInfo(String str, String str2, byte[] bArr) {
            this._cid = str;
            this._url = str2;
            this._image = bArr;
        }

        public String toString() {
            return "CardFaceImageInfo{cid:" + this._cid + ",url:" + this._url + ",image:" + Arrays.toString(this._image) + "}";
        }
    }

    public static class MfiHashValueInfo {
        public String cardId;
        public String mfiHashValue;

        public MfiHashValueInfo(String str, String str2) {
            this.cardId = str;
            this.mfiHashValue = str2;
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

    public synchronized Service getServiceWhereServiceId(String str) throws DatabaseException {
        Service service;
        try {
            Iterator it = new ArrayList(this.mfmdb.getServiceItem()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    service = null;
                    break;
                }
                service = (Service) it.next();
                if (TextUtils.equals(str, service.getServiceId())) {
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

    public synchronized void setIds(IdsSettings idsSettings, List<AreaId> list, List<AppId> list2, List<MultiId> list3) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                if (list != null && list2 != null && list3 != null) {
                    this.mfmdb.replaceAreaItem(list);
                    this.mfmdb.replaceMultiPurposeIdentifierItem(list3);
                    this.mfmdb.replaceApplicationItem(list2);
                }
                this.mfmdb.updateSettingItem(idsSettings);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 113, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void delIds(List<String> list) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                if (list != null) {
                    this.mfmdb.deleteAreaInfo(list);
                    this.mfmdb.deleteAppInfo(list);
                    this.mfmdb.deleteMultiPurposeIdentifierItem(list);
                    this.mfmdb.deleteServiceItem(list);
                    this.mfmdb.deleteBookmarkItem(list);
                }
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 114, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void setServices(List<ServiceWithIcon> list, List<String> list2) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                if (list2 != null && !list2.isEmpty()) {
                    this.mfmdb.deleteServiceItem(list2);
                }
                if (list != null && !list.isEmpty()) {
                    this.mfmdb.addOrUpdateServiceItem(list);
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

    public synchronized void setUpgradeSettings(UpgradeSettings upgradeSettings) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.updateUpdateInfoItem(upgradeSettings);
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

    public synchronized void setTosVersionSettings(TosVersionSettings tosVersionSettings) throws DatabaseException {
        try {
            this.mfmdb.beginTransaction();
            this.mfmdb.updatePolicyUpdateInfoItem(tosVersionSettings);
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

    public synchronized void setBookmarkSettings(BookmarkSettings bookmarkSettings) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.setBookmarkSettings(bookmarkSettings);
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

    public synchronized FelicaPocketService getFpService(int i) throws DatabaseException {
        try {
            for (FelicaPocketService felicaPocketService : this.mfmdb.getFpServiceInfoList()) {
                if (String.format(Locale.US, "%08d", Integer.valueOf(i)).equals(felicaPocketService.fpServiceNumber)) {
                    return felicaPocketService;
                }
            }
            return null;
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 306, e);
        }
    }

    public synchronized void setFelicaPocketSettings(FpSetting fpSetting) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.setFpSetting(fpSetting);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 81, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void setFelicaPocketService(List<FelicaPocketService> list) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.replaceFpServiceItem(list);
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

    public synchronized List<MfiHashValueInfo> getMfiHashValueInfo(List<String> list) throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 215, e);
        }
        return this.mfmdb.getMfiHashValue(list);
    }

    private List<MfiCardAdditionalInfo> convertInfoToDb(List<MyCardAdditionalInfo> list, List<MfiHashValueInfo> list2) {
        ArrayList arrayList = new ArrayList();
        for (MyCardAdditionalInfo myCardAdditionalInfo : list) {
            if (myCardAdditionalInfo.getAdditionalInfo() != null) {
                arrayList.add(new MfiCardAdditionalInfo(myCardAdditionalInfo.getCid(), myCardAdditionalInfo.getAdditionalInfo().languageCode, convertLinkInfoToDb(myCardAdditionalInfo.getAdditionalInfo().linkageInfoList), convertCommonInfoToDb(myCardAdditionalInfo.getAdditionalInfo().commonInformation), extractionMfiCardAdditionalInfoValue(myCardAdditionalInfo.getCid(), list2), myCardAdditionalInfo.getAdditionalInfo().transitInfo));
            }
        }
        return arrayList;
    }

    private List<LinkageInfo> convertLinkInfoToDb(List<LinkageInfo> list) {
        ArrayList arrayList = new ArrayList();
        for (LinkageInfo linkageInfo : list) {
            arrayList.add(new LinkageInfo(linkageInfo.linkageKind == LinkageInfo.LinkageKind.WEB ? LinkageInfo.LinkageKind.WEB : LinkageInfo.LinkageKind.APP, linkageInfo.linkageName, linkageInfo.linkageApplicationInfo != null ? convertLinkAppInfoToDb(linkageInfo.linkageApplicationInfo) : null, linkageInfo.linkageWebsiteURL));
        }
        return arrayList;
    }

    private LinkageApplicationInfo convertLinkAppInfoToDb(LinkageApplicationInfo linkageApplicationInfo) {
        return new LinkageApplicationInfo(linkageApplicationInfo.appIconUrl, linkageApplicationInfo.appIdentifiableInfo, linkageApplicationInfo.appCallerInfo, linkageApplicationInfo.appGetKind, linkageApplicationInfo.appGetUrl);
    }

    private CommonInfo convertCommonInfoToDb(CommonInfo commonInfo) {
        return new CommonInfo(convertGeneralInfoToDb(commonInfo.generalInformation), commonInfo.contact != null ? convertContactInfoToDb(commonInfo.contact) : null);
    }

    private GeneralInfo convertGeneralInfoToDb(GeneralInfo generalInfo) {
        return new GeneralInfo(generalInfo.cardTypeName, generalInfo.cardArtUrl, generalInfo.contactName);
    }

    private ContactInfo convertContactInfoToDb(ContactInfo contactInfo) {
        return new ContactInfo(contactInfo.name, contactInfo.phoneNumber, contactInfo.url, contactInfo.email);
    }

    private String extractionMfiCardAdditionalInfoValue(String str, List<MfiHashValueInfo> list) {
        String str2 = null;
        for (MfiHashValueInfo mfiHashValueInfo : list) {
            if (mfiHashValueInfo.cardId.equals(str)) {
                str2 = mfiHashValueInfo.mfiHashValue;
            }
        }
        return str2;
    }

    public synchronized void setMfiCardAdditionalInfo(List<MyCardAdditionalInfo> list, List<MfiHashValueInfo> list2) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.updateMfiCardAdditionalInfo(convertInfoToDb(list, list2));
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 214, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void deleteMfiCardAdditionalInfo(String str) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.deleteMfiCardAdditionalInfo(str);
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

    public synchronized void setBannerTexts(List<BannerText> list) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.deleteBannerTexts(list);
                this.mfmdb.setBannerTexts(list);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 273, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void setBannerSettings(BannerSettings bannerSettings) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.setBannerSettings(bannerSettings);
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

    public synchronized byte[] getIcon(String str) throws DatabaseException {
        return this.mfmdb.getIconData(str, ServiceWithIcon.IconType.ICON);
    }

    public synchronized byte[] getIconEx1(String str) throws DatabaseException {
        return this.mfmdb.getIconData(str, ServiceWithIcon.IconType.ICONEx1);
    }

    public synchronized byte[] getIconEx2(String str) throws DatabaseException {
        return this.mfmdb.getIconData(str, ServiceWithIcon.IconType.ICONEx2);
    }

    public synchronized List<Boolean> isExists(List<String> list) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator<String> it = list.iterator();
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
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void addCardFaceImage(String str, String str2, byte[] bArr) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.addCardFaceImage(str, str2, bArr);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 321, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized void updateCardFaceImage(String str, String str2, byte[] bArr) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.updateCardFaceImage(str, str2, bArr);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 322, e);
            }
        } finally {
            this.mfmdb.endTransaction();
        }
    }

    public synchronized CardFaceImageInfo getCardFaceImage(String str) throws DatabaseException {
        try {
        } catch (Exception e) {
            throw new DatabaseException(getClass(), 323, e);
        }
        return this.mfmdb.getCardFaceImage(str);
    }

    public synchronized Map<String, Object> getSpecificColumnData(String str, List<String> list) throws DatabaseException {
        return this.mfmdb.getSpecificColumnData(str, list);
    }

    public synchronized void updateSpecificColumnData(String str, Map<String, Object> map) throws DatabaseException {
        try {
            try {
                this.mfmdb.beginTransaction();
                this.mfmdb.updateSpecificColumnData(str, map);
                this.mfmdb.setTransactionSuccessful();
            } catch (Exception e) {
                throw new DatabaseException(getClass(), 324, e);
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
        if (this.mfmdb != null) {
            try {
                try {
                    this.mfmdb.close();
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

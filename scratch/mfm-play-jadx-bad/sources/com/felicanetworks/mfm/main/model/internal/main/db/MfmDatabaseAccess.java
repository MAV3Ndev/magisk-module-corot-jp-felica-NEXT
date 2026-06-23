package com.felicanetworks.mfm.main.model.internal.main.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.text.TextUtils;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.main.model.info.CommonInfo;
import com.felicanetworks.mfm.main.model.info.ContactInfo;
import com.felicanetworks.mfm.main.model.info.DetailLinkageApp;
import com.felicanetworks.mfm.main.model.info.DetailLinkageWeb;
import com.felicanetworks.mfm.main.model.info.GeneralInfo;
import com.felicanetworks.mfm.main.model.info.LinkageApplicationInfo;
import com.felicanetworks.mfm.main.model.info.LinkageInfo;
import com.felicanetworks.mfm.main.model.info.MfiCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.OptionalInfo;
import com.felicanetworks.mfm.main.model.info.Section1;
import com.felicanetworks.mfm.main.model.info.Section2;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.info.TermOfValidity;
import com.felicanetworks.mfm.main.model.info.TransitInfo;
import com.felicanetworks.mfm.main.model.info.TransitPassInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmlib.MfmAppContext;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.db.table.OptionalInformationListTable;
import com.felicanetworks.mfm.main.model.internal.main.db.table.TransitInformationListTable;
import com.felicanetworks.mfm.main.model.internal.main.mfc.AreaItemFelica;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.SasReadItem;
import com.felicanetworks.mfm.main.model.internal.main.pkg.PackageExpert;
import com.felicanetworks.mfm.main.policy.helper.db.table.StatementUtils;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.tis.datatype.NotificationJsonInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class MfmDatabaseAccess {
    private static final String INSERT_APPLIST_TABLE = "INSERT INTO AppList VALUES ( ?, ?, ?, ? )";
    private static final String INSERT_AREALIST_TABLE = "INSERT INTO AreaList VALUES ( ?, ?, ?, ?, ?)";
    private static final String INSERT_BANNERINFO_TABLE = "INSERT INTO BannerInfo VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    private static final String INSERT_BGUPDATECARDARTINFO_TABLE = "INSERT INTO BgUpdateCardArtInfo VALUES ( ? )";
    private static final String INSERT_BOOKMARKLIST_TABLE = "INSERT INTO BookmarkList VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    private static final String INSERT_CARDFACEIMAGE_TABLE = "INSERT INTO CardFaceInfo(CardFaceIdentifier, CardFaceImageUrl, CardFaceImage) VALUES ( ?, ?, ?)";
    private static final String INSERT_FPSERVICELIST_TABLE = "INSERT INTO FpServiceList VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    private static final String INSERT_MULTIPURPOSELIST_TABLE = "INSERT INTO MultipurposeList VALUES ( ?, ?, ?, ?, ?)";
    private static final String INSERT_SERVICELIST_LINKAGE_APP_TABLE = "INSERT INTO ServiceLinkageAppList VALUES ( ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_SERVICELIST_LINKAGE_WEB_TABLE = "INSERT INTO ServiceLinkageWebList VALUES ( ?, ?, ?)";
    private static final String INSERT_SERVICELIST_TABLE = "INSERT INTO ServiceList VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    private static final String INSERT_TABLE_CACHE_ASSET_LIST = "INSERT INTO CacheAssetList VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_TABLE_CACHE_ASSET_LIST_SETTING = "INSERT INTO CacheAssetListSetting VALUES ( ? )";
    private static final String INSERT_TABLE_CACHE_CHIP_AREA = "INSERT INTO CacheChipArea VALUES ( ?, ?)";
    private static final String INSERT_TABLE_CACHE_CHIP_AREA_SETTING = "INSERT INTO CacheChipAreaSetting VALUES ( ? )";
    private static final String INSERT_TABLE_CACHE_CHIP_SAS = "INSERT INTO CacheChipSas VALUES ( ?, ?)";
    private static final String INSERT_TABLE_CACHE_CHIP_SAS_SETTING = "INSERT INTO CacheChipSasSetting VALUES ( ? )";
    private static final String INSERT_TABLE_CACHE_INSTALLED_APP_LIST = "INSERT INTO CacheInstalledAppList VALUES ( ?, ?)";
    private static final String INSERT_TABLE_CACHE_INSTALLED_APP_LIST_SETTING = "INSERT INTO CacheInstalledAppListSetting VALUES ( ? )";
    private static final String TABLE_APPLIST = "AppList";
    private static final String TABLE_AREALIST = "AreaList";
    private static final String TABLE_BANNERINFO = "BannerInfo";
    private static final String TABLE_BANNERSETTING = "BannerSetting";
    private static final String TABLE_BGUPDATECARDARTINFO = "BgUpdateCardArtInfo";
    private static final String TABLE_BOOKMARKLIST = "BookmarkList";
    private static final String TABLE_BOOKMARKSETTING = "BookmarkSetting";
    private static final String TABLE_CACHE_ASSET_LIST = "CacheAssetList";
    private static final String TABLE_CACHE_ASSET_LIST_SETTING = "CacheAssetListSetting";
    private static final String TABLE_CACHE_CHIP_AREA = "CacheChipArea";
    private static final String TABLE_CACHE_CHIP_AREA_SETTING = "CacheChipAreaSetting";
    private static final String TABLE_CACHE_CHIP_SAS = "CacheChipSas";
    private static final String TABLE_CACHE_CHIP_SAS_SETTING = "CacheChipSasSetting";
    private static final String TABLE_CACHE_INSTALLED_APP_LIST = "CacheInstalledAppList";
    private static final String TABLE_CACHE_INSTALLED_APP_LIST_SETTING = "CacheInstalledAppListSetting";
    private static final String TABLE_CARDADDITIONALINFO = "CardAdditionalInfo";
    private static final String TABLE_CARDFACEINFO = "CardFaceInfo";
    private static final String TABLE_FPSERVICELIST = "FpServiceList";
    private static final String TABLE_FPSETTING = "FpSetting";
    private static final String TABLE_MULTIPURPOSELIST = "MultipurposeList";
    private static final String TABLE_POLICYUPDATEINFO = "PolicyUpdateInfo";
    private static final String TABLE_SERVICELIST = "ServiceList";
    private static final String TABLE_SERVICE_LINKAGEAPPLIST = "ServiceLinkageAppList";
    private static final String TABLE_SERVICE_LINKAGEWEBLIST = "ServiceLinkageWebList";
    private static final String TABLE_SETTING = "Setting";
    private static final String TABLE_UPDATEINFO = "UpdateInfo";
    private SQLiteDatabase db;
    private static final String[] COLUMNS_AREALIST = {"SystemCode", "AreaCode", "ServiceID", "ServiceVersion", "CacheFlag"};
    private static final String[] COLUMNS_MULTIPURPOSELIST = {"MultipurposeServiceKind", "MultipurposeIdentifierCode", "ServiceID", "ServiceVersion", "CacheFlag"};
    private static final String[] COLUMNS_APPLIST = {"PackageName", "HashValue", "ServiceID", "ServiceVersion"};
    private static final String[] COLUMNS_SETTING = {"UpdateInterval", "LastUpdateDate"};
    private static final String[] COLUMNS_UPDATEINFO = {"OfflineCheckInterval", "LastCheckDate", "OfflineCheckActualCnt", "OfflineCheckMaxCnt"};
    private static final String[] COLUMNS_POLICYUPDATEINFO = {"OfflineCheckInterval", "LastCheckDate", "OfflineCheckActualCnt", "OfflineCheckMaxCnt", "PolicySiteVersion"};
    private static final String[] COLUMNS_CACHE_AREA_LIST = {"sysCode", "areaCode"};
    private static final String[] COLUMNS_CACHE_AREA_LIST_SETTING = {"setting"};
    private static final String[] COLUMNS_CACHE_SAS_LIST = {"cpidSid", "blockName"};
    private static final String[] COLUMNS_CACHE_SAS_LIST_SETTING = {"setting"};
    private static final String[] COLUMNS_CACHE_INSTALLED_APP_LIST = {"pkgName", "sigHashList"};
    private static final String[] COLUMNS_CACHE_INSTALLED_APP_LIST_SETTING = {"setting"};
    private static final String[] COLUMNS_CACHE_ASSET = {"serviceId", NotificationJsonInfo.KEY_NAME_CARD_ID, "balanceValue", "balanceLimit", "point1", "point2", "date1", "date2", "isDcardMini"};
    private static final String[] COLUMNS_CACHE_ASSET_SETTING = {"setting"};

    enum EnumSelectFromCardAdditionalInfoLinkageInfoSql {
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_1 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.1
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String[] getColumns() {
                return new String[]{"LinkageKind1", "LinkageName1", "LinkageWebsiteUrl1"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_2 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.2
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String[] getColumns() {
                return new String[]{"LinkageKind2", "LinkageName2", "LinkageWebsiteUrl2"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_3 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.3
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String[] getColumns() {
                return new String[]{"LinkageKind3", "LinkageName3", "LinkageWebsiteUrl3"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_4 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.4
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String[] getColumns() {
                return new String[]{"LinkageKind4", "LinkageName4", "LinkageWebsiteUrl4"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_5 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.5
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String[] getColumns() {
                return new String[]{"LinkageKind5", "LinkageName5", "LinkageWebsiteUrl5"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_6 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.6
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String[] getColumns() {
                return new String[]{"LinkageKind6", "LinkageName6", "LinkageWebsiteUrl6"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_7 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.7
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String[] getColumns() {
                return new String[]{"LinkageKind7", "LinkageName7", "LinkageWebsiteUrl7"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_8 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.8
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String[] getColumns() {
                return new String[]{"LinkageKind8", "LinkageName8", "LinkageWebsiteUrl8"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_9 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.9
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String[] getColumns() {
                return new String[]{"LinkageKind9", "LinkageName9", "LinkageWebsiteUrl9"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_10 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.10
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String[] getColumns() {
                return new String[]{"LinkageKind10", "LinkageName10", "LinkageWebsiteUrl10"};
            }
        };

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 java.lang.String), (r2v0 int) A[MD:(java.lang.String, int):void (m)] (LINE:336) call: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.<init>(java.lang.String, int):void type: THIS */
        /* synthetic */ EnumSelectFromCardAdditionalInfoLinkageInfoSql(AnonymousClass1 anonymousClass1) {
            this();
        }

        String[] getColumns() {
            throw new UnsupportedOperationException("not defined.");
        }
    }

    enum EnumSelectFromCardAdditionalInfoLinkageAppInfoSql {
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_1 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.1
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String[] getColumns() {
                return new String[]{"AppIconUrl1", "AppIdentifiableInfo1", "AppCallerInfo1", "AppGetKind1", "AppGetUrl1"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_2 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.2
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String[] getColumns() {
                return new String[]{"AppIconUrl2", "AppIdentifiableInfo2", "AppCallerInfo2", "AppGetKind2", "AppGetUrl2"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_3 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.3
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String[] getColumns() {
                return new String[]{"AppIconUrl3", "AppIdentifiableInfo3", "AppCallerInfo3", "AppGetKind3", "AppGetUrl3"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_4 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.4
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String[] getColumns() {
                return new String[]{"AppIconUrl4", "AppIdentifiableInfo4", "AppCallerInfo4", "AppGetKind4", "AppGetUrl4"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_5 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.5
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String[] getColumns() {
                return new String[]{"AppIconUrl5", "AppIdentifiableInfo5", "AppCallerInfo5", "AppGetKind5", "AppGetUrl5"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_6 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.6
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String[] getColumns() {
                return new String[]{"AppIconUrl6", "AppIdentifiableInfo6", "AppCallerInfo6", "AppGetKind6", "AppGetUrl6"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_7 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.7
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String[] getColumns() {
                return new String[]{"AppIconUrl7", "AppIdentifiableInfo7", "AppCallerInfo7", "AppGetKind7", "AppGetUrl7"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_8 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.8
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String[] getColumns() {
                return new String[]{"AppIconUrl8", "AppIdentifiableInfo8", "AppCallerInfo8", "AppGetKind8", "AppGetUrl8"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_9 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.9
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String[] getColumns() {
                return new String[]{"AppIconUrl9", "AppIdentifiableInfo9", "AppCallerInfo9", "AppGetKind9", "AppGetUrl9"};
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_10 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.10
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String[] getColumns() {
                return new String[]{"AppIconUrl10", "AppIdentifiableInfo10", "AppCallerInfo10", "AppGetKind10", "AppGetUrl10"};
            }
        };

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 java.lang.String), (r2v0 int) A[MD:(java.lang.String, int):void (m)] (LINE:352) call: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.<init>(java.lang.String, int):void type: THIS */
        /* synthetic */ EnumSelectFromCardAdditionalInfoLinkageAppInfoSql(AnonymousClass1 anonymousClass1) {
            this();
        }

        String[] getColumns() {
            throw new UnsupportedOperationException("not defined.");
        }
    }

    private static final class TableColumns implements BaseColumns {
        private static final String COLUMN_APPLICATIONKIND = "ApplicationKind";
        private static final String COLUMN_APPLICATIONURL = "ApplicationUrl";
        private static final String COLUMN_AREACODE = "AreaCode";
        private static final String COLUMN_BANNER_BANNERIMG = "BannerImg";
        private static final String COLUMN_BANNER_END = "End";
        private static final String COLUMN_BANNER_ID = "Id";
        private static final String COLUMN_BANNER_IMG = "Img";
        private static final String COLUMN_BANNER_INFOFILEUPDATED = "BannerInfoFileUpdated";
        private static final String COLUMN_BANNER_INFOLASTUPDATEDDATE = "BannerInfoLastUpdatedDate";
        private static final String COLUMN_BANNER_KIND = "Kind";
        private static final String COLUMN_BANNER_LS = "Ls";
        private static final String COLUMN_BANNER_PRIORITY = "Priority";
        private static final String COLUMN_BANNER_START = "Start";
        private static final String COLUMN_BANNER_UPDATE = "Updated";
        private static final String COLUMN_BOOKMARK_LASTUPDATEDDATE = "BookmarkLastUpdatedDate";
        private static final String COLUMN_CACHEFLAG = "CacheFlag";
        private static final String COLUMN_CARD_CATEGORY = "CardCategory";
        private static final String COLUMN_CARD_FACE_IMAGE = "CardFaceImage";
        private static final String COLUMN_COLOR = "ServiceColor";
        private static final String COLUMN_CONTACT_EMAIL = "ContactMail";
        private static final String COLUMN_CONTACT_NAME = "ContactName";
        private static final String COLUMN_CONTACT_PHONE = "ContactPhone";
        private static final String COLUMN_CONTACT_URL = "ContactUrl";
        private static final String COLUMN_COOPERATIVEKIND = "CooperativeKind";
        private static final String COLUMN_DETAIL_IMAGE_PRESENCE = "DetailImagePresence";
        private static final String COLUMN_DISPLAYPRIORITY = "DisplayPriority";
        private static final String COLUMN_FP_LASTUPDATEDDATE = "FpLastUpdatedDate";
        private static final String COLUMN_HASHVALUE = "HashValue";
        private static final String COLUMN_ICONDATA = "IconData";
        private static final String COLUMN_ICONDATA1 = "IconData1";
        private static final String COLUMN_ICONDATA2 = "IconData2";
        private static final String COLUMN_LASTCHECKDATE = "LastCheckDate";
        private static final String COLUMN_LASTUPDATEDATE = "LastUpdateDate";
        private static final String COLUMN_LINKAGE_APP_GET_TYPE = "LinkageApp_GetType";
        private static final String COLUMN_LINKAGE_APP_GET_URL = "LinkageApp_GetUrl";
        private static final String COLUMN_LINKAGE_APP_HASH = "LinkageApp_Hash";
        private static final String COLUMN_LINKAGE_APP_ICON = "LinkageApp_Icon";
        private static final String COLUMN_LINKAGE_APP_NAME = "LinkageApp_AppName";
        private static final String COLUMN_LINKAGE_APP_PKG = "LinkageApp_PackageName";
        private static final String COLUMN_LINKAGE_APP_SERVICE_ID = "ServiceID";
        private static final String COLUMN_LINKAGE_WEB_NAME = "LinkageWeb_Name";
        private static final String COLUMN_LINKAGE_WEB_SERVICE_ID = "ServiceID";
        private static final String COLUMN_LINKAGE_WEB_URL = "LinkageWeb_Url";
        private static final String COLUMN_MFI_LASTUPDATEDDATE = "CaLastUpdatedDate";
        private static final String COLUMN_MULTIIDENTIFIERCODE = "MultipurposeIdentifierCode";
        private static final String COLUMN_MULTISERVICEKIND = "MultipurposeServiceKind";
        private static final String COLUMN_OFFLINECHECKACTUALCNT = "OfflineCheckActualCnt";
        private static final String COLUMN_OFFLINECHECKINTERVAL = "OfflineCheckInterval";
        private static final String COLUMN_OFFLINECHECKMAXCNT = "OfflineCheckMaxCnt";
        private static final String COLUMN_PACKAGENAME = "PackageName";
        private static final String COLUMN_POLICYSITEVERSION = "PolicySiteVersion";
        private static final String COLUMN_SERVICEID = "ServiceID";
        private static final String COLUMN_SERVICENAME = "ServiceName";
        private static final String COLUMN_SERVICEPROVIDERNAME = "ServiceProviderName";
        private static final String COLUMN_SERVICEVERSION = "ServiceVersion";
        private static final String COLUMN_SERVICE_TYPE = "ServiceType";
        private static final String COLUMN_SYSTEMCODE = "SystemCode";
        private static final String COLUMN_UPDATEINTERVAL = "UpdateInterval";
        private static final String COLUMN_WEBURL = "WebUrl";

        private TableColumns() {
        }
    }

    private static final class TableColumns_CardFaceImage implements BaseColumns {
        private static final String CARD_FACE_IDENTIFIER = "CardFaceIdentifier";
        private static final String CARD_FACE_IMAGE = "CardFaceImage";
        private static final String CARD_FACE_IMAGE_URL = "CardFaceImageUrl";

        private TableColumns_CardFaceImage() {
        }
    }

    private static final class TableColumns_BgUpdateCardArtInfo implements BaseColumns {
        private static final String CARD_ID = "CardId";

        private TableColumns_BgUpdateCardArtInfo() {
        }
    }

    private static final class TableColumns_CacheChipArea implements BaseColumns {
        private static final String AREA_CODE = "areaCode";
        private static final String SYS_CODE = "sysCode";

        private TableColumns_CacheChipArea() {
        }
    }

    private static final class TableColumns_CacheChipAreaSetting implements BaseColumns {
        private static final String SETTING = "setting";

        private TableColumns_CacheChipAreaSetting() {
        }
    }

    private static final class TableColumns_CacheChipSas implements BaseColumns {
        private static final String BLOCK_NAME = "blockName";
        private static final String CPID_SID = "cpidSid";

        private TableColumns_CacheChipSas() {
        }
    }

    private static final class TableColumns_CacheChipSasSetting implements BaseColumns {
        private static final String SETTING = "setting";

        private TableColumns_CacheChipSasSetting() {
        }
    }

    private static final class TableColumns_CacheInstalledApp implements BaseColumns {
        private static final String HASH = "sigHashList";
        private static final String PKG_NAME = "pkgName";

        private TableColumns_CacheInstalledApp() {
        }
    }

    private static final class TableColumns_CacheInstalledAppSetting implements BaseColumns {
        private static final String SETTING = "setting";

        private TableColumns_CacheInstalledAppSetting() {
        }
    }

    private static final class TableColumns_CacheAsset implements BaseColumns {
        private static final String BALANCE_LMT = "balanceLimit";
        private static final String BALANCE_VAL = "balanceValue";
        private static final String CARD_ID = "cardId";
        private static final String DATE1 = "date1";
        private static final String DATE2 = "date2";
        private static final String DCARD = "isDcardMini";
        private static final String POINT1 = "point1";
        private static final String POINT2 = "point2";
        private static final String SERVICE_ID = "serviceId";

        private TableColumns_CacheAsset() {
        }
    }

    private static final class TableColumns_CacheAssetSetting implements BaseColumns {
        private static final String SETTING = "setting";

        private TableColumns_CacheAssetSetting() {
        }
    }

    MfmDatabaseAccess(MfmAppContext context) throws DatabaseException {
        this.db = null;
        try {
            this.db = MfmDatabaseHelper.getInstance(context).getWritableDatabase();
        } catch (Exception e) {
            LogUtil.warning(e);
            SQLiteDatabase sQLiteDatabase = this.db;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                this.db.close();
            }
            throw new DatabaseException(getClass(), 17, e);
        }
    }

    public void close() throws DatabaseException {
        try {
            try {
                SQLiteDatabase sQLiteDatabase = this.db;
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                    this.db.close();
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 33, e);
            }
        } finally {
            this.db = null;
        }
    }

    public void beginTransaction() throws DatabaseException {
        try {
            this.db.beginTransaction();
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 49, e);
        }
    }

    public void setTransactionSuccessful() throws DatabaseException {
        try {
            this.db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 65, e);
        }
    }

    public void endTransaction() throws DatabaseException {
        try {
            this.db.endTransaction();
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 81, e);
        }
    }

    public void replaceAreaItem(List<DatabaseExpert.AreaId> itemList) throws DatabaseException {
        if (itemList == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                cleanAreaInfo();
                if (itemList.size() > 0) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_AREALIST_TABLE);
                    for (int i = 0; i < itemList.size(); i++) {
                        DatabaseExpert.AreaId areaId = itemList.get(i);
                        sQLiteStatementCompileStatement.bindString(1, areaId.sysCode);
                        sQLiteStatementCompileStatement.bindString(2, areaId.areaCode);
                        sQLiteStatementCompileStatement.bindString(3, areaId.id);
                        sQLiteStatementCompileStatement.bindString(4, areaId.version);
                        sQLiteStatementCompileStatement.bindString(5, areaId.cache);
                        sQLiteStatementCompileStatement.executeInsert();
                    }
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 97, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    public void replaceApplicationItem(List<DatabaseExpert.AppId> itemList) throws DatabaseException {
        if (itemList == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                cleanAppInfo();
                if (itemList.size() > 0) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_APPLIST_TABLE);
                    for (int i = 0; i < itemList.size(); i++) {
                        DatabaseExpert.AppId appId = itemList.get(i);
                        sQLiteStatementCompileStatement.bindString(1, appId.pkgName);
                        sQLiteStatementCompileStatement.bindString(2, appId.id);
                        sQLiteStatementCompileStatement.bindString(3, appId.version);
                        sQLiteStatementCompileStatement.bindString(4, appId.sigHash);
                        sQLiteStatementCompileStatement.executeInsert();
                    }
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 113, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    public void replaceMultiPurposeIdentifierItem(List<DatabaseExpert.MultiId> itemList) throws DatabaseException {
        if (itemList == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                cleanMultiPurposeIdentifierItem();
                if (itemList.size() > 0) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_MULTIPURPOSELIST_TABLE);
                    for (int i = 0; i < itemList.size(); i++) {
                        DatabaseExpert.MultiId multiId = itemList.get(i);
                        sQLiteStatementCompileStatement.bindString(1, multiId.type);
                        sQLiteStatementCompileStatement.bindString(2, multiId.code);
                        sQLiteStatementCompileStatement.bindString(3, multiId.id);
                        sQLiteStatementCompileStatement.bindString(4, multiId.version);
                        sQLiteStatementCompileStatement.bindString(5, multiId.cache);
                        sQLiteStatementCompileStatement.executeInsert();
                    }
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 129, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x049f  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x04a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void addOrUpdateServiceItem(List<DatabaseExpert.ServiceWithIcon> itemList) throws Throwable {
        Cursor cursor;
        SQLiteStatement sQLiteStatementCompileStatement;
        if (itemList == null || itemList.size() == 0) {
            return;
        }
        try {
            ArrayList arrayList = new ArrayList();
            Cursor cursorQuery = this.db.query(TABLE_SERVICELIST, new String[]{"ServiceID"}, null, null, null, null, null, null);
            try {
                cursorQuery.moveToFirst();
                int count = cursorQuery.getCount();
                for (int i = 0; i < count; i++) {
                    arrayList.add(cursorQuery.getString(0));
                    cursorQuery.moveToNext();
                }
                sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_SERVICELIST_TABLE);
            } catch (Exception e) {
                e = e;
                cursor = cursorQuery;
                sQLiteStatementCompileStatement = null;
                try {
                    LogUtil.warning(e);
                    throw new DatabaseException(getClass(), 145, e);
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteStatementCompileStatement != null) {
                        sQLiteStatementCompileStatement.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor = cursorQuery;
                sQLiteStatementCompileStatement = null;
                if (cursor != null) {
                }
                if (sQLiteStatementCompileStatement != null) {
                }
                throw th;
            }
            try {
                SQLiteStatement sQLiteStatementCompileStatement2 = this.db.compileStatement(INSERT_SERVICELIST_LINKAGE_APP_TABLE);
                SQLiteStatement sQLiteStatementCompileStatement3 = this.db.compileStatement(INSERT_SERVICELIST_LINKAGE_WEB_TABLE);
                for (int i2 = 0; i2 < itemList.size(); i2++) {
                    String serviceId = itemList.get(i2).getServiceId();
                    byte[] bArr = itemList.get(i2).iconEx1.length > 0 ? itemList.get(i2).iconEx1 : null;
                    byte[] bArr2 = itemList.get(i2).iconEx2.length > 0 ? itemList.get(i2).iconEx2 : null;
                    byte[] cardFaceImage = itemList.get(i2).getCardFaceImage().length > 0 ? itemList.get(i2).getCardFaceImage() : null;
                    if (arrayList.contains(serviceId)) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("ServiceVersion", itemList.get(i2).getVersion());
                        contentValues.put("ServiceName", itemList.get(i2).getServiceName());
                        contentValues.put("ServiceProviderName", itemList.get(i2).getProvider());
                        contentValues.put("CooperativeKind", itemList.get(i2).getLinkType());
                        contentValues.put("PackageName", itemList.get(i2).getPkg());
                        contentValues.put("HashValue", itemList.get(i2).getSigHash());
                        contentValues.put("ApplicationKind", itemList.get(i2).getDownloadType());
                        contentValues.put("ApplicationUrl", itemList.get(i2).getDownloadUrl());
                        contentValues.put("WebUrl", itemList.get(i2).getWebUrl());
                        contentValues.put("IconData", itemList.get(i2).icon);
                        contentValues.put("DisplayPriority", itemList.get(i2).getPriority());
                        contentValues.put("ServiceColor", itemList.get(i2).getColor());
                        contentValues.put("IconData1", bArr);
                        contentValues.put("IconData2", bArr2);
                        contentValues.put("ServiceType", itemList.get(i2).getServiceType());
                        contentValues.put("CardCategory", itemList.get(i2).getCardCategory());
                        contentValues.put("DetailImagePresence", itemList.get(i2).getDetailImagePresence());
                        contentValues.put("CardFaceImage", cardFaceImage);
                        contentValues.put("ContactName", itemList.get(i2).getContactName());
                        contentValues.put("ContactPhone", itemList.get(i2).getContactPhone());
                        contentValues.put("ContactUrl", itemList.get(i2).getContactUrl());
                        contentValues.put("ContactMail", itemList.get(i2).getContactEmail());
                        this.db.update(TABLE_SERVICELIST, contentValues, "ServiceID = ?", new String[]{serviceId});
                    } else {
                        sQLiteStatementCompileStatement.bindString(1, serviceId);
                        sQLiteStatementCompileStatement.bindString(2, itemList.get(i2).getVersion());
                        sQLiteStatementCompileStatement.bindString(3, itemList.get(i2).getServiceName());
                        sQLiteStatementCompileStatement.bindString(4, itemList.get(i2).getProvider());
                        sQLiteStatementCompileStatement.bindString(5, itemList.get(i2).getLinkType());
                        sQLiteStatementCompileStatement.bindString(6, itemList.get(i2).getPkg());
                        sQLiteStatementCompileStatement.bindString(7, itemList.get(i2).getSigHash());
                        sQLiteStatementCompileStatement.bindString(8, itemList.get(i2).getDownloadType());
                        sQLiteStatementCompileStatement.bindString(9, itemList.get(i2).getDownloadUrl());
                        sQLiteStatementCompileStatement.bindString(10, itemList.get(i2).getWebUrl());
                        sQLiteStatementCompileStatement.bindBlob(11, itemList.get(i2).icon);
                        sQLiteStatementCompileStatement.bindString(12, itemList.get(i2).getPriority());
                        sQLiteStatementCompileStatement.bindString(13, itemList.get(i2).getColor());
                        sQLiteStatementCompileStatement.bindBlob(14, itemList.get(i2).iconEx1);
                        sQLiteStatementCompileStatement.bindBlob(15, itemList.get(i2).iconEx2);
                        if (bArr != null) {
                            sQLiteStatementCompileStatement.bindBlob(14, bArr);
                        } else {
                            sQLiteStatementCompileStatement.bindNull(14);
                        }
                        if (bArr2 != null) {
                            sQLiteStatementCompileStatement.bindBlob(15, bArr2);
                        } else {
                            sQLiteStatementCompileStatement.bindNull(15);
                        }
                        sQLiteStatementCompileStatement.bindString(16, itemList.get(i2).getServiceType());
                        sQLiteStatementCompileStatement.bindString(17, itemList.get(i2).getCardCategory());
                        sQLiteStatementCompileStatement.bindString(18, itemList.get(i2).getDetailImagePresence());
                        sQLiteStatementCompileStatement.bindBlob(19, itemList.get(i2).getCardFaceImage());
                        if (cardFaceImage != null) {
                            sQLiteStatementCompileStatement.bindBlob(19, itemList.get(i2).getCardFaceImage());
                        } else {
                            sQLiteStatementCompileStatement.bindNull(19);
                        }
                        sQLiteStatementCompileStatement.bindString(20, itemList.get(i2).getContactName());
                        sQLiteStatementCompileStatement.bindString(21, itemList.get(i2).getContactPhone());
                        sQLiteStatementCompileStatement.bindString(22, itemList.get(i2).getContactUrl());
                        sQLiteStatementCompileStatement.bindString(23, itemList.get(i2).getContactEmail());
                        sQLiteStatementCompileStatement.executeInsert();
                    }
                    this.db.delete(TABLE_SERVICE_LINKAGEAPPLIST, "ServiceID = ?", new String[]{serviceId});
                    for (int i3 = 0; i3 < itemList.get(i2).getDetailLinkageAppList().size(); i3++) {
                        sQLiteStatementCompileStatement2.bindString(1, serviceId);
                        sQLiteStatementCompileStatement2.bindString(2, itemList.get(i2).getDetailLinkageAppList().get(i3).getLinkageAppPkg());
                        sQLiteStatementCompileStatement2.bindString(3, itemList.get(i2).getDetailLinkageAppList().get(i3).getLinkageAppHash());
                        sQLiteStatementCompileStatement2.bindString(4, String.valueOf(itemList.get(i2).getDetailLinkageAppList().get(i3).getLinkageAppGetType()));
                        sQLiteStatementCompileStatement2.bindString(5, itemList.get(i2).getDetailLinkageAppList().get(i3).getLinkageAppUrl());
                        sQLiteStatementCompileStatement2.bindBlob(6, itemList.get(i2).getDetailLinkageAppList().get(i3).getLinkageAppIcon());
                        sQLiteStatementCompileStatement2.bindString(7, itemList.get(i2).getDetailLinkageAppList().get(i3).getLinkageAppName());
                        sQLiteStatementCompileStatement2.executeInsert();
                    }
                    this.db.delete(TABLE_SERVICE_LINKAGEWEBLIST, "ServiceID = ?", new String[]{serviceId});
                    for (int i4 = 0; i4 < itemList.get(i2).getDetailLinkageWebList().size(); i4++) {
                        sQLiteStatementCompileStatement3.bindString(1, serviceId);
                        sQLiteStatementCompileStatement3.bindString(2, itemList.get(i2).getDetailLinkageWebList().get(i4).getLinkageWebUrl());
                        sQLiteStatementCompileStatement3.bindString(3, itemList.get(i2).getDetailLinkageWebList().get(i4).getLinkageWebName());
                        sQLiteStatementCompileStatement3.executeInsert();
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                if (sQLiteStatementCompileStatement != null) {
                    sQLiteStatementCompileStatement.close();
                }
            } catch (Exception e2) {
                e = e2;
                cursor = cursorQuery;
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 145, e);
            } catch (Throwable th3) {
                th = th3;
                cursor = cursorQuery;
                if (cursor != null) {
                }
                if (sQLiteStatementCompileStatement != null) {
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th4) {
            th = th4;
            cursor = null;
        }
    }

    public List<DatabaseExpert.AreaId> getAreaItem() throws DatabaseException {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_AREALIST, COLUMNS_AREALIST, null, null, null, null, null, (String) Sg.getValue(Sg.Key.DB_AREA_IDS_TABLE_SELECT_LIMIT));
                cursorQuery.moveToFirst();
                int count = cursorQuery.getCount();
                for (int i = 0; i < count; i++) {
                    arrayList.add(new DatabaseExpert.AreaId(cursorQuery.getString(0), cursorQuery.getString(1), cursorQuery.getString(2), cursorQuery.getString(3), cursorQuery.getString(4)));
                    cursorQuery.moveToNext();
                }
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), MfiClientException.TYPE_SE_ACCESS_ERROR, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public List<DatabaseExpert.AppId> getApplicationItem() throws DatabaseException {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_APPLIST, COLUMNS_APPLIST, null, null, null, null, null, (String) Sg.getValue(Sg.Key.DB_APP_IDS_TABLE_SELECT_LIMIT));
                cursorQuery.moveToFirst();
                int count = cursorQuery.getCount();
                for (int i = 0; i < count; i++) {
                    arrayList.add(new DatabaseExpert.AppId(cursorQuery.getString(0), cursorQuery.getString(1), cursorQuery.getString(2), cursorQuery.getString(3)));
                    cursorQuery.moveToNext();
                }
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 177, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public List<DatabaseExpert.MultiId> getMultiPurposeIdentifierItem() throws DatabaseException {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_MULTIPURPOSELIST, COLUMNS_MULTIPURPOSELIST, null, null, null, null, null, (String) Sg.getValue(Sg.Key.DB_MULTI_IDS_TABLE_SELECT_LIMIT));
                cursorQuery.moveToFirst();
                int count = cursorQuery.getCount();
                for (int i = 0; i < count; i++) {
                    arrayList.add(new DatabaseExpert.MultiId(cursorQuery.getString(0), cursorQuery.getString(1), cursorQuery.getString(2), cursorQuery.getString(3), cursorQuery.getString(4)));
                    cursorQuery.moveToNext();
                }
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 193, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01ed  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List<Service> getServiceItem() throws Throwable {
        Cursor cursor;
        Cursor cursorQuery;
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery2 = null;
        try {
            Cursor cursorQuery3 = this.db.query(TABLE_SERVICELIST, new String[]{"ServiceID", "ServiceVersion", "ServiceName", "ServiceProviderName", "CooperativeKind", "PackageName", "HashValue", "ApplicationKind", "ApplicationUrl", "WebUrl", "DisplayPriority", "ServiceColor", "ServiceType", "CardCategory", "DetailImagePresence", "CardFaceImage", "ContactName", "ContactPhone", "ContactUrl", "ContactMail"}, null, null, null, null, "DisplayPriority, ServiceID", (String) Sg.getValue(Sg.Key.DB_SERVICE_TABLE_SELECT_LIMIT));
            try {
                cursorQuery3.moveToFirst();
                int count = cursorQuery3.getCount();
                int i = 0;
                cursorQuery = null;
                int i2 = 0;
                while (i2 < count) {
                    try {
                        ArrayList arrayList2 = new ArrayList();
                        ArrayList arrayList3 = new ArrayList();
                        if (cursorQuery2 != null) {
                            cursorQuery2.close();
                        }
                        cursorQuery2 = this.db.query(TABLE_SERVICE_LINKAGEAPPLIST, new String[]{"ServiceID", "LinkageApp_PackageName", "LinkageApp_Hash", "LinkageApp_GetType", "LinkageApp_GetUrl", "LinkageApp_Icon", "LinkageApp_AppName"}, "ServiceID = ?", new String[]{cursorQuery3.getString(i)}, null, null, null, null);
                        cursorQuery2.moveToFirst();
                        int count2 = cursorQuery2.getCount();
                        for (int i3 = i; i3 < count2; i3++) {
                            arrayList2.add(new DetailLinkageApp(cursorQuery2.getString(1), cursorQuery2.getString(2), cursorQuery2.getInt(3), cursorQuery2.getString(4), cursorQuery2.getBlob(5), cursorQuery2.getString(6)));
                            cursorQuery2.moveToNext();
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        cursorQuery = this.db.query(TABLE_SERVICE_LINKAGEWEBLIST, new String[]{"ServiceID", "LinkageWeb_Url", "LinkageWeb_Name"}, "ServiceID = ?", new String[]{cursorQuery3.getString(0)}, null, null, null, null);
                        cursorQuery.moveToFirst();
                        int count3 = cursorQuery.getCount();
                        for (int i4 = 0; i4 < count3; i4++) {
                            arrayList3.add(new DetailLinkageWeb(cursorQuery.getString(1), cursorQuery.getString(2)));
                            cursorQuery.moveToNext();
                        }
                        arrayList.add(new Service(cursorQuery3.getString(0), cursorQuery3.getString(1), cursorQuery3.getString(2), cursorQuery3.getString(3), cursorQuery3.getString(4), cursorQuery3.getString(5), cursorQuery3.getString(6), cursorQuery3.getString(7), cursorQuery3.getString(8), cursorQuery3.getString(9), cursorQuery3.getString(10), cursorQuery3.getString(11), cursorQuery3.getString(12), cursorQuery3.getString(13), cursorQuery3.getString(14), cursorQuery3.getBlob(15), cursorQuery3.getString(16), cursorQuery3.getString(17), cursorQuery3.getString(18), cursorQuery3.getString(19), arrayList2, arrayList3));
                        cursorQuery3.moveToNext();
                        i2++;
                        i = 0;
                    } catch (Exception e) {
                        e = e;
                        cursor = cursorQuery2;
                        cursorQuery2 = cursorQuery3;
                        try {
                            LogUtil.warning(e);
                            throw new DatabaseException(getClass(), 209, e);
                        } catch (Throwable th) {
                            th = th;
                            if (cursorQuery2 != null) {
                                cursorQuery2.close();
                            }
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = cursorQuery2;
                        cursorQuery2 = cursorQuery3;
                        if (cursorQuery2 != null) {
                        }
                        if (cursor != null) {
                        }
                        if (cursorQuery != null) {
                        }
                        throw th;
                    }
                }
                if (cursorQuery3 != null) {
                    cursorQuery3.close();
                }
                if (cursorQuery2 != null) {
                    cursorQuery2.close();
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return arrayList;
            } catch (Exception e2) {
                e = e2;
                cursorQuery = null;
                cursorQuery2 = cursorQuery3;
                cursor = null;
            } catch (Throwable th3) {
                th = th3;
                cursorQuery = null;
                cursorQuery2 = cursorQuery3;
                cursor = null;
            }
        } catch (Exception e3) {
            e = e3;
            cursor = null;
            cursorQuery = null;
        } catch (Throwable th4) {
            th = th4;
            cursor = null;
            cursorQuery = null;
        }
    }

    public byte[] getIconData(String str, DatabaseExpert.ServiceWithIcon.IconType iconType) throws Throwable {
        Exception exc;
        Throwable th;
        Cursor cursorQuery;
        Cursor cursor = null;
        byte[] blob = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_SERVICELIST, new String[]{"IconData", "IconData1", "IconData2"}, "ServiceID = ?", new String[]{str}, null, null, null, "1");
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e) {
            exc = e;
        }
        try {
            cursorQuery.moveToFirst();
            int count = cursorQuery.getCount();
            for (int i = 0; i < count; i++) {
                int i2 = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$db$DatabaseExpert$ServiceWithIcon$IconType[iconType.ordinal()];
                if (i2 == 1) {
                    blob = cursorQuery.getBlob(0);
                } else if (i2 != 2) {
                    if (i2 == 3) {
                        if (cursorQuery.getBlob(2) == null) {
                            blob = cursorQuery.getBlob(0);
                        } else {
                            blob = cursorQuery.getBlob(2);
                        }
                    }
                } else if (cursorQuery.getBlob(1) == null) {
                    blob = cursorQuery.getBlob(0);
                } else {
                    blob = cursorQuery.getBlob(1);
                }
                cursorQuery.moveToNext();
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return blob;
        } catch (Exception e2) {
            exc = e2;
            LogUtil.warning(exc);
            throw new DatabaseException(getClass(), 225, exc);
        } catch (Throwable th3) {
            th = th3;
            cursor = cursorQuery;
            if (cursor != null) {
                cursor.close();
                throw th;
            }
            throw th;
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$db$DatabaseExpert$ServiceWithIcon$IconType;

        static {
            int[] iArr = new int[DatabaseExpert.ServiceWithIcon.IconType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$db$DatabaseExpert$ServiceWithIcon$IconType = iArr;
            try {
                iArr[DatabaseExpert.ServiceWithIcon.IconType.ICON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$db$DatabaseExpert$ServiceWithIcon$IconType[DatabaseExpert.ServiceWithIcon.IconType.ICONEx1.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$db$DatabaseExpert$ServiceWithIcon$IconType[DatabaseExpert.ServiceWithIcon.IconType.ICONEx2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public DatabaseExpert.IdsSettings getSettingItem() throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        DatabaseExpert.IdsSettings idsSettings = null;
        cursor = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_SETTING, COLUMNS_SETTING, null, null, null, null, null, "1");
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            cursorQuery.moveToFirst();
            if (cursorQuery.getCount() > 0) {
                idsSettings = new DatabaseExpert.IdsSettings(cursorQuery.getInt(0), cursorQuery.getString(1));
                cursorQuery.moveToNext();
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return idsSettings;
        } catch (Exception e2) {
            e = e2;
            cursor = cursorQuery;
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 241, e);
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQuery;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public void updateSettingItem(DatabaseExpert.IdsSettings settingItem) throws DatabaseException {
        if (settingItem != null) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("UpdateInterval", Integer.valueOf(settingItem.interval));
                contentValues.put("LastUpdateDate", settingItem.lastUpdate);
                this.db.update(TABLE_SETTING, contentValues, null, null);
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 257, e);
            }
        }
    }

    private void cleanAreaInfo() {
        this.db.delete(TABLE_AREALIST, null, null);
    }

    void deleteAreaInfo(List<String> serviceIds) {
        String where = StatementUtils.getWhere("ServiceID", serviceIds);
        if (where == null) {
            return;
        }
        this.db.delete(TABLE_AREALIST, where, (String[]) serviceIds.toArray(new String[0]));
    }

    private void cleanAppInfo() {
        this.db.delete(TABLE_APPLIST, null, null);
    }

    void deleteAppInfo(List<String> serviceIds) {
        String where = StatementUtils.getWhere("ServiceID", serviceIds);
        if (where == null) {
            return;
        }
        this.db.delete(TABLE_APPLIST, where, (String[]) serviceIds.toArray(new String[0]));
    }

    private void cleanMultiPurposeIdentifierItem() {
        this.db.delete(TABLE_MULTIPURPOSELIST, null, null);
    }

    void deleteMultiPurposeIdentifierItem(List<String> serviceIds) {
        String where = StatementUtils.getWhere("ServiceID", serviceIds);
        if (where == null) {
            return;
        }
        this.db.delete(TABLE_MULTIPURPOSELIST, where, (String[]) serviceIds.toArray(new String[0]));
    }

    public void deleteServiceItem(List<String> serviceIdList) throws DatabaseException {
        try {
            StringBuilder sb = new StringBuilder();
            if (serviceIdList == null || serviceIdList.size() <= 0) {
                return;
            }
            for (int i = 0; i < serviceIdList.size(); i++) {
                sb.append(String.format("'%s'", serviceIdList.get(i)));
                sb.append(",");
            }
            String str = "ServiceID in(" + sb.substring(0, sb.length() - 1) + ")";
            this.db.delete(TABLE_SERVICELIST, str, null);
            this.db.delete(TABLE_SERVICE_LINKAGEAPPLIST, str, null);
            this.db.delete(TABLE_SERVICE_LINKAGEWEBLIST, str, null);
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 273, e);
        }
    }

    public static void clearInstance() {
        MfmDatabaseHelper.clearInstance();
    }

    private void cleanBookmarkItem() {
        this.db.delete(TABLE_BOOKMARKLIST, null, null);
    }

    void deleteBookmarkItem(List<String> serviceIds) {
        String where = StatementUtils.getWhere("ServiceID", serviceIds);
        if (where == null) {
            return;
        }
        this.db.delete(TABLE_BOOKMARKLIST, where, (String[]) serviceIds.toArray(new String[0]));
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00e4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List<DatabaseExpert.Recommend> getBookmarkItem() throws Throwable {
        MfmDatabaseAccess mfmDatabaseAccess;
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                mfmDatabaseAccess = this;
                try {
                    Cursor cursorQuery = mfmDatabaseAccess.db.query(TABLE_BOOKMARKLIST, new String[]{"ServiceID", "ServiceVersion", "CategoryID", "CategoryName", "ServiceName", "ServiceProviderName", "StatusKind", "CooperativeKind", "ApplicationKind", "ApplicationUrl", "WebUrl", "Summary", "Detail", "Procedure1", "Procedure2", "Procedure3", "Procedure4", "Procedure5"}, null, null, null, null, "DisplayPriority ASC", (String) Sg.getValue(Sg.Key.DB_RECOMMEND_TABLE_SELECT_LIMIT));
                    cursorQuery.moveToFirst();
                    for (int i = 0; i < cursorQuery.getCount(); i++) {
                        arrayList.add(new DatabaseExpert.Recommend(cursorQuery.getString(0), cursorQuery.getString(1), cursorQuery.getString(2), cursorQuery.getString(3), cursorQuery.getString(4), cursorQuery.getString(5), cursorQuery.getString(6), cursorQuery.getString(7), cursorQuery.getString(8), cursorQuery.getString(9), cursorQuery.getString(10), cursorQuery.getString(11), cursorQuery.getString(12), cursorQuery.getString(13), cursorQuery.getString(14), cursorQuery.getString(15), cursorQuery.getString(16), cursorQuery.getString(17)));
                        cursorQuery.moveToNext();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayList;
                } catch (Exception e) {
                    e = e;
                    LogUtil.warning(e);
                    throw new DatabaseException(mfmDatabaseAccess.getClass(), 289, e);
                }
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            mfmDatabaseAccess = this;
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
            }
            throw th;
        }
    }

    public void replaceBookmarkItem(List<DatabaseExpert.Recommend> bookmarkList) throws DatabaseException {
        if (bookmarkList == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                cleanBookmarkItem();
                if (bookmarkList.size() > 0) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_BOOKMARKLIST_TABLE);
                    long j = 1;
                    for (int i = 0; i < bookmarkList.size(); i++) {
                        DatabaseExpert.Recommend recommend = bookmarkList.get(i);
                        sQLiteStatementCompileStatement.bindString(1, recommend.categoryId);
                        sQLiteStatementCompileStatement.bindString(2, recommend.categoryName);
                        sQLiteStatementCompileStatement.bindString(3, recommend.id);
                        sQLiteStatementCompileStatement.bindString(4, recommend.version);
                        sQLiteStatementCompileStatement.bindString(5, recommend.name);
                        sQLiteStatementCompileStatement.bindString(6, recommend.provider);
                        sQLiteStatementCompileStatement.bindString(7, recommend.status);
                        sQLiteStatementCompileStatement.bindString(8, recommend.linkType);
                        sQLiteStatementCompileStatement.bindString(9, recommend.downloadType);
                        sQLiteStatementCompileStatement.bindString(10, recommend.downloadUrl);
                        sQLiteStatementCompileStatement.bindString(11, recommend.webUrl);
                        sQLiteStatementCompileStatement.bindString(12, recommend.summary);
                        sQLiteStatementCompileStatement.bindString(13, recommend.detail);
                        if (recommend.procedure1 != null) {
                            sQLiteStatementCompileStatement.bindString(14, recommend.procedure1);
                        } else {
                            sQLiteStatementCompileStatement.bindNull(14);
                        }
                        if (recommend.procedure2 != null) {
                            sQLiteStatementCompileStatement.bindString(15, recommend.procedure2);
                        } else {
                            sQLiteStatementCompileStatement.bindNull(15);
                        }
                        if (recommend.procedure3 != null) {
                            sQLiteStatementCompileStatement.bindString(16, recommend.procedure3);
                        } else {
                            sQLiteStatementCompileStatement.bindNull(16);
                        }
                        if (recommend.procedure4 != null) {
                            sQLiteStatementCompileStatement.bindString(17, recommend.procedure4);
                        } else {
                            sQLiteStatementCompileStatement.bindNull(17);
                        }
                        if (recommend.procedure5 != null) {
                            sQLiteStatementCompileStatement.bindString(18, recommend.procedure5);
                        } else {
                            sQLiteStatementCompileStatement.bindNull(18);
                        }
                        sQLiteStatementCompileStatement.bindLong(19, j);
                        sQLiteStatementCompileStatement.executeInsert();
                        j++;
                    }
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 305, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    private void cleanFpServeInfoList() {
        this.db.delete(TABLE_FPSERVICELIST, null, null);
    }

    public List<DatabaseExpert.FelicaPocketService> getFpServiceInfoList() throws DatabaseException {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_FPSERVICELIST, new String[]{"FpServiceNumber", "FpServiceName", "FpServiceIconUrl", "FpServiceProviderName", "ServiceEnable", "WebUrl", "FpServiceKind", "NeedShowValue", "PointUnitName"}, null, null, null, null, null, (String) Sg.getValue(Sg.Key.DB_FP_SERVICE_TABLE_SELECT_LIMIT));
                cursorQuery.moveToFirst();
                for (int i = 0; i < cursorQuery.getCount(); i++) {
                    arrayList.add(new DatabaseExpert.FelicaPocketService(cursorQuery.getString(0), cursorQuery.getString(1), cursorQuery.getString(2), cursorQuery.getString(3), cursorQuery.getInt(4), cursorQuery.getString(5), cursorQuery.getString(6), cursorQuery.getInt(7), cursorQuery.getString(8)));
                    cursorQuery.moveToNext();
                }
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 306, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public void replaceFpServiceItem(List<DatabaseExpert.FelicaPocketService> fpServiceList) throws DatabaseException {
        if (fpServiceList == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                cleanFpServeInfoList();
                if (fpServiceList.size() > 0) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_FPSERVICELIST_TABLE);
                    for (int i = 0; i < fpServiceList.size(); i++) {
                        DatabaseExpert.FelicaPocketService felicaPocketService = fpServiceList.get(i);
                        sQLiteStatementCompileStatement.bindString(1, felicaPocketService.fpServiceNumber);
                        sQLiteStatementCompileStatement.bindString(2, felicaPocketService.fpServiceName);
                        sQLiteStatementCompileStatement.bindString(3, felicaPocketService.fpServiceIconUrl);
                        sQLiteStatementCompileStatement.bindString(4, felicaPocketService.fpProviderName);
                        sQLiteStatementCompileStatement.bindLong(5, felicaPocketService.serviceEnable);
                        sQLiteStatementCompileStatement.bindString(6, felicaPocketService.webUrl);
                        sQLiteStatementCompileStatement.bindString(7, felicaPocketService.fpServiceKind);
                        sQLiteStatementCompileStatement.bindLong(8, felicaPocketService.needShowValue);
                        sQLiteStatementCompileStatement.bindString(9, felicaPocketService.pointUnitName);
                        sQLiteStatementCompileStatement.executeInsert();
                    }
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 307, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    public DatabaseExpert.UpgradeSettings getUpdateInfoItem() throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        DatabaseExpert.UpgradeSettings upgradeSettings = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_UPDATEINFO, COLUMNS_UPDATEINFO, null, null, null, null, null, "1");
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            cursorQuery.moveToFirst();
            if (cursorQuery.getCount() > 0) {
                upgradeSettings = new DatabaseExpert.UpgradeSettings(cursorQuery.getInt(0), cursorQuery.getString(1), cursorQuery.getInt(2), cursorQuery.getInt(3));
                cursorQuery.moveToNext();
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return upgradeSettings;
        } catch (Exception e2) {
            e = e2;
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 321, e);
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQuery;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public void updateUpdateInfoItem(DatabaseExpert.UpgradeSettings updateInfoItem) throws DatabaseException {
        if (updateInfoItem != null) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("OfflineCheckInterval", Integer.valueOf(updateInfoItem.interval));
                contentValues.put("LastCheckDate", updateInfoItem.lastChkDate);
                contentValues.put("OfflineCheckActualCnt", Integer.valueOf(updateInfoItem.chkCnt));
                contentValues.put("OfflineCheckMaxCnt", Integer.valueOf(updateInfoItem.maxCnt));
                this.db.update(TABLE_UPDATEINFO, contentValues, null, null);
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 337, e);
            }
        }
    }

    public DatabaseExpert.TosVersionSettings getPolicyUpdateInfoItem() throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        DatabaseExpert.TosVersionSettings tosVersionSettings = null;
        cursor = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_POLICYUPDATEINFO, COLUMNS_POLICYUPDATEINFO, null, null, null, null, null, "1");
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            cursorQuery.moveToFirst();
            if (cursorQuery.getCount() > 0) {
                DatabaseExpert.TosVersionSettings tosVersionSettings2 = new DatabaseExpert.TosVersionSettings(cursorQuery.getInt(0), cursorQuery.getString(1), cursorQuery.getInt(2), cursorQuery.getInt(3), cursorQuery.getInt(4));
                cursorQuery.moveToNext();
                tosVersionSettings = tosVersionSettings2;
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return tosVersionSettings;
        } catch (Exception e2) {
            e = e2;
            cursor = cursorQuery;
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 577, e);
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQuery;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public void updatePolicyUpdateInfoItem(DatabaseExpert.TosVersionSettings item) throws DatabaseException {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("OfflineCheckInterval", Integer.valueOf(item.interval));
            contentValues.put("LastCheckDate", item.lastChkDate);
            contentValues.put("OfflineCheckActualCnt", Integer.valueOf(item.chkCnt));
            contentValues.put("OfflineCheckMaxCnt", Integer.valueOf(item.maxCnt));
            contentValues.put("PolicySiteVersion", Integer.valueOf(item.version));
            this.db.update(TABLE_POLICYUPDATEINFO, contentValues, null, null);
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 593, e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List<DatabaseExpert.Banner> getBannerList(DatabaseExpert.BannerPos bannerPos) throws Throwable {
        MfmDatabaseAccess mfmDatabaseAccess;
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                String str = new DateFormatter(DateFormatter.DATE_MINUTE, (String) Sg.getValue(Sg.Key.SETTING_TIMEZONE)).format(new Date());
                String[] strArr = {"Kind", JsonDocumentFields.POLICY_ID, "Priority", "BannerImg", "Img", "Ls", "Start", "End", "Updated"};
                String[] strArr2 = {bannerPos.posId(), str, str};
                mfmDatabaseAccess = this;
                try {
                    Cursor cursorQuery = mfmDatabaseAccess.db.query(TABLE_BANNERINFO, strArr, "BannerImg IS NOT NULL AND Kind = ? AND (Start <= ? AND ? <= End)", strArr2, null, null, "Priority", null);
                    cursorQuery.moveToFirst();
                    for (int i = 0; i < cursorQuery.getCount(); i++) {
                        arrayList.add(new DatabaseExpert.Banner(cursorQuery.getString(0), cursorQuery.getString(1), cursorQuery.getInt(2), cursorQuery.getBlob(3), cursorQuery.getString(4), cursorQuery.getString(5), cursorQuery.getString(6), cursorQuery.getString(7), cursorQuery.getString(8)));
                        cursorQuery.moveToNext();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayList;
                } catch (Exception e) {
                    e = e;
                    LogUtil.warning(e);
                    throw new DatabaseException(mfmDatabaseAccess.getClass(), 369, e);
                }
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            mfmDatabaseAccess = this;
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
            }
            throw th;
        }
    }

    public DatabaseExpert.BookmarkSettings getBookmarkSettings() throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        DatabaseExpert.BookmarkSettings bookmarkSettings = null;
        cursor = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_BOOKMARKSETTING, new String[]{"BookmarkLastUpdatedDate"}, null, null, null, null, null, null);
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            cursorQuery.moveToFirst();
            if (cursorQuery.getCount() > 0) {
                bookmarkSettings = new DatabaseExpert.BookmarkSettings(cursorQuery.getString(0));
                cursorQuery.moveToNext();
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return bookmarkSettings;
        } catch (Exception e2) {
            e = e2;
            cursor = cursorQuery;
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 375, e);
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQuery;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public void setBookmarkSettings(DatabaseExpert.BookmarkSettings bookmarkSettings) throws DatabaseException {
        try {
            ContentValues contentValues = new ContentValues();
            if (bookmarkSettings.lastUpdateDate != null) {
                contentValues.put("BookmarkLastUpdatedDate", bookmarkSettings.lastUpdateDate);
            }
            this.db.update(TABLE_BOOKMARKSETTING, contentValues, null, null);
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 384, e);
        }
    }

    public DatabaseExpert.BannerSettings getBannerSettings(DatabaseExpert.BannerPos bannerPos) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        DatabaseExpert.BannerSettings bannerSettings = null;
        try {
            try {
                String[] strArr = {bannerPos.posId()};
                cursorQuery = this.db.query(TABLE_BANNERSETTING, new String[]{"Kind", "BannerInfoFileUpdated", "BannerInfoLastUpdatedDate"}, "Kind = ?", strArr, null, null, null, null);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            cursorQuery.moveToFirst();
            if (cursorQuery.getCount() > 0) {
                bannerSettings = new DatabaseExpert.BannerSettings(DatabaseExpert.BannerPos.resolvePos(cursorQuery.getString(0)), cursorQuery.getString(1), cursorQuery.getString(2));
                cursorQuery.moveToNext();
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return bannerSettings;
        } catch (Exception e2) {
            e = e2;
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 385, e);
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQuery;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public void setBannerSettings(DatabaseExpert.BannerSettings bannerSettings) throws DatabaseException {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("Kind", bannerSettings.pos.posId());
            if (bannerSettings.lastCheckDate != null) {
                contentValues.put("BannerInfoLastUpdatedDate", bannerSettings.lastCheckDate);
            }
            if (bannerSettings.upToDate != null) {
                contentValues.put("BannerInfoFileUpdated", bannerSettings.upToDate);
            }
            this.db.update(TABLE_BANNERSETTING, contentValues, "Kind = ?", new String[]{bannerSettings.pos.posId()});
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 401, e);
        }
    }

    public void deleteBannerTexts(List<DatabaseExpert.BannerText> BannerList) throws DatabaseException {
        try {
            StringBuilder sb = new StringBuilder();
            if (BannerList == null || BannerList.size() <= 0) {
                return;
            }
            for (int i = 0; i < BannerList.size(); i++) {
                sb.append(String.format("'%s'", BannerList.get(i).id));
                sb.append(",");
            }
            this.db.delete(TABLE_BANNERINFO, "Id not in(" + sb.substring(0, sb.length() - 1) + ") And Kind = " + BannerList.get(0).bannerPos.ordinal(), null);
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 417, e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setBannerTexts(List<DatabaseExpert.BannerText> list) throws Throwable {
        Throwable th;
        SQLiteStatement sQLiteStatement;
        Exception exc;
        if (list == null || list.size() == 0) {
            return;
        }
        Cursor cursor = null;
        sQLiteStatementCompileStatement = null;
        SQLiteStatement sQLiteStatementCompileStatement = null;
        cursor = null;
        try {
            ArrayList arrayList = new ArrayList();
            Cursor cursorQuery = this.db.query(TABLE_BANNERINFO, new String[]{JsonDocumentFields.POLICY_ID, "Kind"}, null, null, null, null, null, null);
            try {
                cursorQuery.moveToFirst();
                int count = cursorQuery.getCount();
                for (int i = 0; i < count; i++) {
                    arrayList.add(cursorQuery.getString(0) + cursorQuery.getString(1));
                    cursorQuery.moveToNext();
                }
                sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_BANNERINFO_TABLE);
                for (int i2 = 0; i2 < list.size(); i2++) {
                    if (arrayList.contains(list.get(i2).id + list.get(i2).bannerPos.posId())) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("Priority", Integer.valueOf(list.get(i2).priority));
                        contentValues.put("Img", list.get(i2).imgName);
                        contentValues.put("Ls", list.get(i2).scheme);
                        contentValues.put("Start", list.get(i2).start);
                        contentValues.put("End", list.get(i2).end);
                        this.db.update(TABLE_BANNERINFO, contentValues, "Id = ? AND Kind = ?", new String[]{list.get(i2).id, list.get(i2).bannerPos.posId()});
                    } else {
                        sQLiteStatementCompileStatement.bindString(1, list.get(i2).id);
                        sQLiteStatementCompileStatement.bindString(2, list.get(i2).bannerPos.posId());
                        sQLiteStatementCompileStatement.bindLong(3, list.get(i2).priority);
                        sQLiteStatementCompileStatement.bindString(5, list.get(i2).imgName);
                        sQLiteStatementCompileStatement.bindString(6, list.get(i2).scheme);
                        sQLiteStatementCompileStatement.bindString(7, list.get(i2).start);
                        sQLiteStatementCompileStatement.bindString(8, list.get(i2).end);
                        sQLiteStatementCompileStatement.executeInsert();
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                if (sQLiteStatementCompileStatement != null) {
                    sQLiteStatementCompileStatement.close();
                }
            } catch (Exception e) {
                exc = e;
                sQLiteStatement = sQLiteStatementCompileStatement;
                cursor = cursorQuery;
                try {
                    LogUtil.warning(exc);
                    throw new DatabaseException(getClass(), 433, exc);
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteStatement == null) {
                        sQLiteStatement.close();
                        throw th;
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                sQLiteStatement = sQLiteStatementCompileStatement;
                cursor = cursorQuery;
                if (cursor != null) {
                }
                if (sQLiteStatement == null) {
                }
            }
        } catch (Exception e2) {
            exc = e2;
            sQLiteStatement = null;
        } catch (Throwable th4) {
            th = th4;
            sQLiteStatement = null;
        }
    }

    public void setBannerImage(DatabaseExpert.BannerImage bannerImg) throws DatabaseException {
        if (bannerImg != null) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("BannerImg", bannerImg.imgBinary);
                contentValues.put("Updated", bannerImg.update);
                this.db.update(TABLE_BANNERINFO, contentValues, "Id = ? AND Kind = ?", new String[]{bannerImg.id, bannerImg.bannerPos.posId()});
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 449, e);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isExistService(String serviceId) throws Throwable {
        Cursor cursor;
        Cursor cursorQuery;
        boolean z = false;
        Cursor cursorQuery2 = null;
        try {
            cursorQuery = this.db.query(TABLE_BOOKMARKLIST, new String[]{"ServiceID"}, "ServiceID= ?", new String[]{serviceId}, null, null, null, null);
        } catch (Exception e) {
            e = e;
            cursor = null;
        } catch (Throwable th) {
            th = th;
            cursor = null;
        }
        try {
            cursorQuery.moveToFirst();
            if (cursorQuery.getCount() > 0) {
                cursorQuery2 = this.db.query(TABLE_SERVICELIST, new String[]{"ServiceID"}, "ServiceID= ?", new String[]{cursorQuery.getString(0)}, null, null, null, "1");
                cursorQuery2.moveToFirst();
                if (cursorQuery2.getCount() > 0) {
                    z = true;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            if (cursorQuery2 != null) {
                cursorQuery2.close();
            }
            return z;
        } catch (Exception e2) {
            e = e2;
            Cursor cursor2 = cursorQuery2;
            cursorQuery2 = cursorQuery;
            cursor = cursor2;
            try {
                LogUtil.warning(e);
                if (cursorQuery2 != null) {
                    cursorQuery2.close();
                }
                if (cursor != null) {
                    cursor.close();
                }
                return false;
            } catch (Throwable th2) {
                th = th2;
                if (cursorQuery2 != null) {
                    cursorQuery2.close();
                }
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            Cursor cursor3 = cursorQuery2;
            cursorQuery2 = cursorQuery;
            cursor = cursor3;
            if (cursorQuery2 != null) {
            }
            if (cursor != null) {
            }
            throw th;
        }
    }

    public DatabaseExpert.FpSetting getFpSetting() throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        DatabaseExpert.FpSetting fpSetting = null;
        cursor = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_FPSETTING, new String[]{"FpLastUpdatedDate"}, null, null, null, null, null, "1");
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            cursorQuery.moveToFirst();
            if (cursorQuery.getCount() > 0) {
                fpSetting = new DatabaseExpert.FpSetting(cursorQuery.getString(0));
                cursorQuery.moveToNext();
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return fpSetting;
        } catch (Exception e2) {
            e = e2;
            cursor = cursorQuery;
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 465, e);
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQuery;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public void setFpSetting(DatabaseExpert.FpSetting fpSetting) throws DatabaseException {
        try {
            ContentValues contentValues = new ContentValues();
            if (fpSetting.lastUpdateDate != null) {
                contentValues.put("FpLastUpdatedDate", fpSetting.lastUpdateDate);
            }
            this.db.update(TABLE_FPSETTING, contentValues, null, null);
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 481, e);
        }
    }

    void deleteMfiCardAdditionalInfo() {
        this.db.delete(TABLE_CARDADDITIONALINFO, null, null);
        this.db.delete(TransitInformationListTable.TABLE_NAME, null, null);
        this.db.delete(OptionalInformationListTable.TABLE_NAME, null, null);
    }

    void deleteMfiCardAdditionalInfo(String cid) {
        this.db.delete(TABLE_CARDADDITIONALINFO, "CardId=?", new String[]{cid});
        this.db.delete(TransitInformationListTable.TABLE_NAME, String.format("%s=?", TransitInformationListTable.Column.CARD_ID.title()), new String[]{cid});
        this.db.delete(OptionalInformationListTable.TABLE_NAME, String.format("%s=?", OptionalInformationListTable.Column.CARD_ID.title()), new String[]{cid});
    }

    void updateMfiCardAdditionalInfo(List<MfiCardAdditionalInfo> list) throws DatabaseException {
        if (list == null) {
            return;
        }
        for (MfiCardAdditionalInfo mfiCardAdditionalInfo : list) {
            StringBuilderForValue stringBuilderForValue = new StringBuilderForValue();
            stringBuilderForValue.append("(");
            stringBuilderForValue.append("\"" + mfiCardAdditionalInfo.cardID + "\"");
            stringBuilderForValue.appendValue(mfiCardAdditionalInfo.cardAdditionalInfoHashValue);
            stringBuilderForValue.appendValue(mfiCardAdditionalInfo.languageCode);
            for (int i = 0; i < 10; i++) {
                String[] strArr = new String[8];
                if (i < mfiCardAdditionalInfo.linkageInfoList.size()) {
                    LinkageInfo linkageInfo = mfiCardAdditionalInfo.linkageInfoList.get(i);
                    strArr[0] = linkageInfo.linkageKind.value;
                    strArr[1] = linkageInfo.linkageName;
                    if (linkageInfo.linkageApplicationInfo != null) {
                        strArr[2] = linkageInfo.linkageApplicationInfo.appIconUrl;
                        strArr[3] = linkageInfo.linkageApplicationInfo.appIdentifiableInfo;
                        strArr[4] = linkageInfo.linkageApplicationInfo.appCallerInfo;
                        strArr[5] = linkageInfo.linkageApplicationInfo.appGetKind;
                        strArr[6] = linkageInfo.linkageApplicationInfo.appGetUrl;
                    } else {
                        strArr[7] = linkageInfo.linkageWebsiteURL;
                    }
                }
                for (int i2 = 0; i2 < 8; i2++) {
                    stringBuilderForValue.appendValue(strArr[i2]);
                }
            }
            GeneralInfo generalInfo = mfiCardAdditionalInfo.commonInformation.generalInformation;
            stringBuilderForValue.appendValue(generalInfo.cardTypeName);
            stringBuilderForValue.appendValue(generalInfo.cardArtUrl);
            stringBuilderForValue.appendValue(generalInfo.contactName);
            String[] strArr2 = new String[4];
            ContactInfo contactInfo = mfiCardAdditionalInfo.commonInformation.contact;
            if (contactInfo != null) {
                strArr2[0] = contactInfo.name;
                strArr2[1] = contactInfo.phoneNumber;
                strArr2[2] = contactInfo.url;
                strArr2[3] = contactInfo.email;
            }
            for (int i3 = 0; i3 < 4; i3++) {
                stringBuilderForValue.appendValue(strArr2[i3]);
            }
            String[] strArr3 = new String[2];
            TransitInfo transitInfo = mfiCardAdditionalInfo.transitInfo;
            if (transitInfo != null && transitInfo.cardNumber != null) {
                strArr3[0] = transitInfo.cardNumber.displayName;
                strArr3[1] = transitInfo.cardNumber.value;
            }
            for (int i4 = 0; i4 < 2; i4++) {
                stringBuilderForValue.appendValue(strArr3[i4]);
            }
            stringBuilderForValue.appendValue(generalInfo.specialCardArtUrl);
            stringBuilderForValue.append(")");
            try {
                this.db.execSQL(TransitInformationListTable.Statement.DELETE_WITH_CARD_ID, new String[]{mfiCardAdditionalInfo.cardID});
                this.db.execSQL(OptionalInformationListTable.Statement.DELETE_WITH_CARD_ID, new String[]{mfiCardAdditionalInfo.cardID});
                this.db.execSQL("REPLACE INTO CardAdditionalInfo VALUES " + stringBuilderForValue.getString());
                if (transitInfo != null) {
                    insertTransitInfo(mfiCardAdditionalInfo.cardID, transitInfo.transitPassInfoList);
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 514, e);
            }
        }
    }

    private void insertTransitInfo(String cardId, List<TransitPassInfo> list) throws DatabaseException {
        if (cardId == null || list == null) {
            return;
        }
        int i = 0;
        for (TransitPassInfo transitPassInfo : list) {
            String str = cardId + i;
            TransitInformationListTable.RecordBuilder commuterPassName = new TransitInformationListTable.RecordBuilder().setCardId(cardId).setRecordNumber(i).setTransitPassName(transitPassInfo.getTransitPassName()).setCategory(transitPassInfo.getCategory()).setCommuterPassName(transitPassInfo.getCommuterPassName());
            if (transitPassInfo.getSection1() != null) {
                commuterPassName.setFrom1(transitPassInfo.getSection1().from).setTo1(transitPassInfo.getSection1().to);
            }
            if (transitPassInfo.getSection2() != null) {
                commuterPassName.setFrom2(transitPassInfo.getSection2().from).setTo2(transitPassInfo.getSection2().to);
            }
            commuterPassName.setAdditionalInformation(transitPassInfo.getAdditionalInformation()).setVia(transitPassInfo.getVia()).setIssuerName(transitPassInfo.getIssuerName());
            if (transitPassInfo.getTermOfValidity() != null) {
                commuterPassName.setKey(transitPassInfo.getTermOfValidity().key).setStart(transitPassInfo.getTermOfValidity().start).setEnd(transitPassInfo.getTermOfValidity().end).setExtension(transitPassInfo.getTermOfValidity().extension);
            }
            try {
                insertOptionalInfo(cardId, str, transitPassInfo.getOptionalInfoList());
                this.db.insertOrThrow(TransitInformationListTable.TABLE_NAME, null, commuterPassName.build());
                i++;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 4083, e);
            }
        }
    }

    private void insertOptionalInfo(String cardId, String transitId, List<OptionalInfo> list) throws DatabaseException {
        if (cardId == null || transitId == null || list == null) {
            return;
        }
        for (OptionalInfo optionalInfo : list) {
            try {
                this.db.insertOrThrow(OptionalInformationListTable.TABLE_NAME, null, new OptionalInformationListTable.RecordBuilder().setCardId(cardId).setTransitId(transitId).setKey(optionalInfo.getOptionalInfoKey()).setValue(optionalInfo.getOptionalInfoValue()).build());
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 4084, e);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:164:0x0470  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0475  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x047a  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0487  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x049d  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x035d A[Catch: all -> 0x039a, TryCatch #24 {all -> 0x039a, blocks: (B:75:0x033a, B:76:0x033d, B:88:0x035d, B:89:0x0360, B:90:0x0361), top: B:194:0x033a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List<MfiCardAdditionalInfo> getMfiCardAdditionalInfo() throws Throwable {
        ArrayList<Cursor> arrayList;
        ArrayList<Cursor> arrayList2;
        Cursor cursor;
        Cursor cursor2;
        Cursor cursor3;
        Cursor cursor4;
        Cursor cursor5;
        CommonInfo commonInfo;
        LinkageInfo.LinkageKind linkageKind;
        MfmDatabaseAccess mfmDatabaseAccess = this;
        String str = "=?";
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        try {
            Cursor cursorQuery = mfmDatabaseAccess.db.query(TABLE_CARDADDITIONALINFO, new String[]{"CardID", "CardAdditionalInfoHashValue", "LanguageCode", "DisplayName", "Value"}, null, null, null, null, null, null);
            try {
                Cursor cursorQuery2 = mfmDatabaseAccess.db.query(TABLE_CARDADDITIONALINFO, new String[]{"CardTypeName", "CardArtUrl", "ContactName", "SpecialCardArtUrl"}, null, null, null, null, null, null);
                try {
                    Cursor cursorQuery3 = mfmDatabaseAccess.db.query(TABLE_CARDADDITIONALINFO, new String[]{"Name", "ContactPhoneNumber", "ContactURL", "ContactEmail"}, null, null, null, null, null, null);
                    try {
                        int i = 0;
                        for (EnumSelectFromCardAdditionalInfoLinkageInfoSql enumSelectFromCardAdditionalInfoLinkageInfoSql : EnumSelectFromCardAdditionalInfoLinkageInfoSql.values()) {
                            try {
                                arrayList4.add(mfmDatabaseAccess.db.query(TABLE_CARDADDITIONALINFO, enumSelectFromCardAdditionalInfoLinkageInfoSql.getColumns(), null, null, null, null, null, null));
                            } catch (Exception e) {
                                e = e;
                                arrayList = arrayList4;
                                arrayList2 = arrayList5;
                                cursor = cursorQuery;
                                cursor2 = cursorQuery2;
                                cursor3 = cursorQuery3;
                                try {
                                    LogUtil.warning(e);
                                    throw new DatabaseException(getClass(), 512, e);
                                } catch (Throwable th) {
                                    th = th;
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    if (cursor2 != null) {
                                        cursor2.close();
                                    }
                                    if (cursor3 != null) {
                                        cursor3.close();
                                    }
                                    for (Cursor cursor6 : arrayList) {
                                        if (cursor6 != null) {
                                            cursor6.close();
                                        }
                                    }
                                    for (Cursor cursor7 : arrayList2) {
                                        if (cursor7 != null) {
                                            cursor7.close();
                                        }
                                    }
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                arrayList = arrayList4;
                                arrayList2 = arrayList5;
                                cursor = cursorQuery;
                                cursor2 = cursorQuery2;
                                cursor3 = cursorQuery3;
                                if (cursor != null) {
                                }
                                if (cursor2 != null) {
                                }
                                if (cursor3 != null) {
                                }
                                while (r1.hasNext()) {
                                }
                                while (r1.hasNext()) {
                                }
                                throw th;
                            }
                        }
                        for (EnumSelectFromCardAdditionalInfoLinkageAppInfoSql enumSelectFromCardAdditionalInfoLinkageAppInfoSql : EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.values()) {
                            arrayList5.add(mfmDatabaseAccess.db.query(TABLE_CARDADDITIONALINFO, enumSelectFromCardAdditionalInfoLinkageAppInfoSql.getColumns(), null, null, null, null, null, null));
                        }
                        cursorQuery.moveToFirst();
                        cursorQuery2.moveToFirst();
                        cursorQuery3.moveToFirst();
                        for (int i2 = 0; i2 < arrayList4.size(); i2++) {
                            ((Cursor) arrayList4.get(i2)).moveToFirst();
                            ((Cursor) arrayList5.get(i2)).moveToFirst();
                        }
                        int i3 = 0;
                        while (i3 < cursorQuery.getCount()) {
                            String string = cursorQuery.getString(i);
                            String string2 = cursorQuery.getString(1);
                            String string3 = cursorQuery.getString(2);
                            int i4 = 3;
                            int i5 = i3;
                            cursor2 = cursorQuery2;
                            try {
                                CommonInfo commonInfo2 = new CommonInfo(new GeneralInfo(cursorQuery2.getString(i), cursorQuery2.getString(1), cursorQuery2.getString(2), cursorQuery2.getString(3)), new ContactInfo(cursorQuery3.getString(0), cursorQuery3.getString(1), cursorQuery3.getString(2), cursorQuery3.getString(3)));
                                ArrayList arrayList6 = new ArrayList();
                                int i6 = 0;
                                while (i6 < arrayList4.size()) {
                                    try {
                                        if (i6 >= arrayList5.size() || ((Cursor) arrayList5.get(i6)).getCount() <= 0) {
                                            commonInfo = commonInfo2;
                                            cursor3 = cursorQuery3;
                                        } else {
                                            LinkageApplicationInfo linkageApplicationInfo = new LinkageApplicationInfo(((Cursor) arrayList5.get(i6)).getString(0), ((Cursor) arrayList5.get(i6)).getString(1), ((Cursor) arrayList5.get(i6)).getString(2), ((Cursor) arrayList5.get(i6)).getString(i4), ((Cursor) arrayList5.get(i6)).getString(4));
                                            if (((Cursor) arrayList4.get(i6)).getString(0).equals("2")) {
                                                try {
                                                    linkageKind = LinkageInfo.LinkageKind.APP;
                                                } catch (Exception e2) {
                                                    e = e2;
                                                    arrayList = arrayList4;
                                                    arrayList2 = arrayList5;
                                                    cursor = cursorQuery;
                                                    cursor3 = cursorQuery3;
                                                    LogUtil.warning(e);
                                                    throw new DatabaseException(getClass(), 512, e);
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                    arrayList = arrayList4;
                                                    arrayList2 = arrayList5;
                                                    cursor = cursorQuery;
                                                    cursor3 = cursorQuery3;
                                                    if (cursor != null) {
                                                    }
                                                    if (cursor2 != null) {
                                                    }
                                                    if (cursor3 != null) {
                                                    }
                                                    while (r1.hasNext()) {
                                                    }
                                                    while (r1.hasNext()) {
                                                    }
                                                    throw th;
                                                }
                                            } else {
                                                linkageKind = LinkageInfo.LinkageKind.WEB;
                                            }
                                            commonInfo = commonInfo2;
                                            cursor3 = cursorQuery3;
                                            try {
                                                arrayList6.add(new LinkageInfo(linkageKind, ((Cursor) arrayList4.get(i6)).getString(1), linkageApplicationInfo, ((Cursor) arrayList4.get(i6)).getString(2)));
                                            } catch (Exception e3) {
                                                e = e3;
                                                arrayList = arrayList4;
                                                arrayList2 = arrayList5;
                                                cursor = cursorQuery;
                                                LogUtil.warning(e);
                                                throw new DatabaseException(getClass(), 512, e);
                                            } catch (Throwable th4) {
                                                th = th4;
                                                arrayList = arrayList4;
                                                arrayList2 = arrayList5;
                                                cursor = cursorQuery;
                                                if (cursor != null) {
                                                }
                                                if (cursor2 != null) {
                                                }
                                                if (cursor3 != null) {
                                                }
                                                while (r1.hasNext()) {
                                                }
                                                while (r1.hasNext()) {
                                                }
                                                throw th;
                                            }
                                        }
                                        ((Cursor) arrayList4.get(i6)).moveToNext();
                                        ((Cursor) arrayList5.get(i6)).moveToNext();
                                        i6++;
                                        commonInfo2 = commonInfo;
                                        cursorQuery3 = cursor3;
                                        i4 = 3;
                                    } catch (Exception e4) {
                                        e = e4;
                                        cursor3 = cursorQuery3;
                                    } catch (Throwable th5) {
                                        th = th5;
                                        cursor3 = cursorQuery3;
                                    }
                                }
                                CommonInfo commonInfo3 = commonInfo2;
                                cursor3 = cursorQuery3;
                                TransitInfo.CardNumber cardNumber = new TransitInfo.CardNumber(cursorQuery.getString(3), cursorQuery.getString(4));
                                try {
                                    Cursor cursorQuery4 = mfmDatabaseAccess.db.query(TransitInformationListTable.TABLE_NAME, null, TransitInformationListTable.Column.CARD_ID.title() + str, new String[]{string}, null, null, null, String.valueOf(5));
                                    try {
                                        cursorQuery4.moveToFirst();
                                        ArrayList arrayList7 = new ArrayList();
                                        while (!cursorQuery4.isAfterLast()) {
                                            try {
                                                Cursor cursorQuery5 = mfmDatabaseAccess.db.query(OptionalInformationListTable.TABLE_NAME, new String[]{OptionalInformationListTable.Column.KEY.title(), OptionalInformationListTable.Column.VALUE.title()}, OptionalInformationListTable.Column.TRANSIT_ID.title() + str, new String[]{TransitInformationListTable.Selector.findTransitId(cursorQuery4)}, null, null, null, String.valueOf(10));
                                                try {
                                                    cursorQuery5.moveToFirst();
                                                    ArrayList arrayList8 = new ArrayList();
                                                    while (!cursorQuery5.isAfterLast()) {
                                                        arrayList8.add(new OptionalInfo(OptionalInformationListTable.Selector.findKey(cursorQuery5), OptionalInformationListTable.Selector.findValue(cursorQuery5)));
                                                        cursorQuery5.moveToNext();
                                                        str = str;
                                                    }
                                                    String str2 = str;
                                                    arrayList = arrayList4;
                                                    try {
                                                        arrayList2 = arrayList5;
                                                        try {
                                                            arrayList7.add(new TransitPassInfo(TransitInformationListTable.Selector.findTransitPassName(cursorQuery4), TransitInformationListTable.Selector.findCategory(cursorQuery4), TransitInformationListTable.Selector.findCommuterPassName(cursorQuery4), new Section1(TransitInformationListTable.Selector.findFrom1(cursorQuery4), TransitInformationListTable.Selector.findTo1(cursorQuery4)), new Section2(TransitInformationListTable.Selector.findFrom2(cursorQuery4), TransitInformationListTable.Selector.findTo2(cursorQuery4)), TransitInformationListTable.Selector.findAdditionalInformation(cursorQuery4), TransitInformationListTable.Selector.findVia(cursorQuery4), TransitInformationListTable.Selector.findIssuerName(cursorQuery4), new TermOfValidity(TransitInformationListTable.Selector.findKey(cursorQuery4), TransitInformationListTable.Selector.findStart(cursorQuery4), TransitInformationListTable.Selector.findEnd(cursorQuery4), TransitInformationListTable.Selector.findExtension(cursorQuery4).booleanValue()), arrayList8));
                                                            if (cursorQuery5 != null) {
                                                                try {
                                                                    cursorQuery5.close();
                                                                } catch (Throwable th6) {
                                                                    th = th6;
                                                                    cursor4 = cursorQuery4;
                                                                    if (cursor4 != null) {
                                                                        cursor4.close();
                                                                    }
                                                                    throw th;
                                                                }
                                                            }
                                                            cursorQuery4.moveToNext();
                                                            mfmDatabaseAccess = this;
                                                            str = str2;
                                                            arrayList4 = arrayList;
                                                            arrayList5 = arrayList2;
                                                        } catch (Throwable th7) {
                                                            th = th7;
                                                            cursor5 = cursorQuery5;
                                                            if (cursor5 != null) {
                                                                cursor5.close();
                                                            }
                                                            throw th;
                                                        }
                                                    } catch (Throwable th8) {
                                                        th = th8;
                                                        arrayList2 = arrayList5;
                                                        cursor5 = cursorQuery5;
                                                        if (cursor5 != null) {
                                                        }
                                                        throw th;
                                                    }
                                                } catch (Throwable th9) {
                                                    th = th9;
                                                    arrayList = arrayList4;
                                                }
                                            } catch (Throwable th10) {
                                                th = th10;
                                                arrayList = arrayList4;
                                                arrayList2 = arrayList5;
                                                cursor5 = null;
                                            }
                                        }
                                        String str3 = str;
                                        arrayList = arrayList4;
                                        arrayList2 = arrayList5;
                                        arrayList3.add(new MfiCardAdditionalInfo(string, string3, arrayList6, commonInfo3, string2, new TransitInfo(cardNumber, arrayList7)));
                                        if (cursorQuery4 != null) {
                                            try {
                                                cursorQuery4.close();
                                            } catch (Exception e5) {
                                                e = e5;
                                                cursor = cursorQuery;
                                                LogUtil.warning(e);
                                                throw new DatabaseException(getClass(), 512, e);
                                            } catch (Throwable th11) {
                                                th = th11;
                                                cursor = cursorQuery;
                                                if (cursor != null) {
                                                }
                                                if (cursor2 != null) {
                                                }
                                                if (cursor3 != null) {
                                                }
                                                while (r1.hasNext()) {
                                                }
                                                while (r1.hasNext()) {
                                                }
                                                throw th;
                                            }
                                        }
                                        cursorQuery.moveToNext();
                                        cursor2.moveToNext();
                                        cursor3.moveToNext();
                                        i3 = i5 + 1;
                                        mfmDatabaseAccess = this;
                                        i = 0;
                                        cursorQuery2 = cursor2;
                                        str = str3;
                                        arrayList4 = arrayList;
                                        cursorQuery3 = cursor3;
                                        arrayList5 = arrayList2;
                                    } catch (Throwable th12) {
                                        th = th12;
                                        arrayList = arrayList4;
                                        arrayList2 = arrayList5;
                                    }
                                } catch (Throwable th13) {
                                    th = th13;
                                    arrayList = arrayList4;
                                    arrayList2 = arrayList5;
                                    cursor4 = null;
                                }
                            } catch (Exception e6) {
                                e = e6;
                                arrayList = arrayList4;
                                arrayList2 = arrayList5;
                                cursor3 = cursorQuery3;
                                cursor = cursorQuery;
                                LogUtil.warning(e);
                                throw new DatabaseException(getClass(), 512, e);
                            } catch (Throwable th14) {
                                th = th14;
                                arrayList = arrayList4;
                                arrayList2 = arrayList5;
                                cursor3 = cursorQuery3;
                                cursor = cursorQuery;
                                if (cursor != null) {
                                }
                                if (cursor2 != null) {
                                }
                                if (cursor3 != null) {
                                }
                                while (r1.hasNext()) {
                                }
                                while (r1.hasNext()) {
                                }
                                throw th;
                            }
                        }
                        ArrayList<Cursor> arrayList9 = arrayList4;
                        ArrayList<Cursor> arrayList10 = arrayList5;
                        Cursor cursor8 = cursorQuery2;
                        Cursor cursor9 = cursorQuery3;
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        if (cursor8 != null) {
                            cursor8.close();
                        }
                        if (cursor9 != null) {
                            cursor9.close();
                        }
                        for (Cursor cursor10 : arrayList9) {
                            if (cursor10 != null) {
                                cursor10.close();
                            }
                        }
                        for (Cursor cursor11 : arrayList10) {
                            if (cursor11 != null) {
                                cursor11.close();
                            }
                        }
                        return arrayList3;
                    } catch (Exception e7) {
                        e = e7;
                        arrayList = arrayList4;
                        arrayList2 = arrayList5;
                        cursor2 = cursorQuery2;
                    } catch (Throwable th15) {
                        th = th15;
                        arrayList = arrayList4;
                        arrayList2 = arrayList5;
                        cursor2 = cursorQuery2;
                    }
                } catch (Exception e8) {
                    e = e8;
                    arrayList = arrayList4;
                    arrayList2 = arrayList5;
                    cursor2 = cursorQuery2;
                    cursor = cursorQuery;
                    cursor3 = null;
                    LogUtil.warning(e);
                    throw new DatabaseException(getClass(), 512, e);
                } catch (Throwable th16) {
                    th = th16;
                    arrayList = arrayList4;
                    arrayList2 = arrayList5;
                    cursor2 = cursorQuery2;
                    cursor = cursorQuery;
                    cursor3 = null;
                    if (cursor != null) {
                    }
                    if (cursor2 != null) {
                    }
                    if (cursor3 != null) {
                    }
                    while (r1.hasNext()) {
                    }
                    while (r1.hasNext()) {
                    }
                    throw th;
                }
            } catch (Exception e9) {
                e = e9;
                arrayList = arrayList4;
                arrayList2 = arrayList5;
                cursor = cursorQuery;
                cursor2 = null;
                cursor3 = null;
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 512, e);
            } catch (Throwable th17) {
                th = th17;
                arrayList = arrayList4;
                arrayList2 = arrayList5;
                cursor = cursorQuery;
                cursor2 = null;
                cursor3 = null;
                if (cursor != null) {
                }
                if (cursor2 != null) {
                }
                if (cursor3 != null) {
                }
                while (r1.hasNext()) {
                }
                while (r1.hasNext()) {
                }
                throw th;
            }
        } catch (Exception e10) {
            e = e10;
            arrayList = arrayList4;
            arrayList2 = arrayList5;
            cursor = null;
        } catch (Throwable th18) {
            th = th18;
            arrayList = arrayList4;
            arrayList2 = arrayList5;
            cursor = null;
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public List<DatabaseExpert.MfiHashValueInfo> getMfiHashValue(List<String> cidList) throws DatabaseException {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                String[] strArr = {"CardId", "CardAdditionalInfoHashValue"};
                String[] strArr2 = new String[cidList.size()];
                String strConcat = "CardId =  ?";
                for (int i = 0; i < cidList.size(); i++) {
                    strArr2[i] = cidList.get(i);
                    if (i > 0) {
                        strConcat = strConcat.concat(" OR CardId = ?");
                    }
                }
                cursorQuery = this.db.query(TABLE_CARDADDITIONALINFO, strArr, strConcat, strArr2, null, null, null);
                cursorQuery.moveToFirst();
                int columnIndex = cursorQuery.getColumnIndex("CardId");
                int columnIndex2 = cursorQuery.getColumnIndex("CardAdditionalInfoHashValue");
                for (int i2 = 0; i2 < cursorQuery.getCount(); i2++) {
                    arrayList.add(new DatabaseExpert.MfiHashValueInfo(cursorQuery.getString(columnIndex), cursorQuery.getString(columnIndex2)));
                    cursorQuery.moveToNext();
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 824, e);
            }
        } finally {
        }
    }

    public void deleteCardFaceImage(String cardId) {
        cleanCardFaceImage("CardFaceIdentifier = ?", new String[]{cardId});
    }

    public void deleteRelatedCardFaceImage(String cardId) {
        cleanCardFaceImage("CardFaceIdentifier LIKE ?", new String[]{"%" + cardId});
    }

    public void deleteCardFaceImage() {
        cleanCardFaceImage(null, null);
    }

    private void cleanCardFaceImage(String whereClause, String[] whereArgs) {
        this.db.delete(TABLE_CARDFACEINFO, whereClause, whereArgs);
    }

    public DatabaseExpert.CardFaceImageInfo getCardFaceImage(String targetIdentfier) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_CARDFACEINFO, new String[]{"CardFaceIdentifier", "CardFaceImageUrl", "CardFaceImage"}, "CardFaceIdentifier = ?", new String[]{targetIdentfier}, null, null, null, null);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            cursorQuery.moveToFirst();
            if (cursorQuery.getCount() <= 0) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            }
            DatabaseExpert.CardFaceImageInfo cardFaceImageInfo = new DatabaseExpert.CardFaceImageInfo(cursorQuery.getString(cursorQuery.getColumnIndex("CardFaceIdentifier")), cursorQuery.getString(cursorQuery.getColumnIndex("CardFaceImageUrl")), cursorQuery.getBlob(cursorQuery.getColumnIndex("CardFaceImage")));
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return cardFaceImageInfo;
        } catch (Exception e2) {
            e = e2;
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 561, e);
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQuery;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public void updateCardFaceImage(String cardId, String url, byte[] rawImage) throws DatabaseException {
        if (TextUtils.isEmpty(cardId) || TextUtils.isEmpty(url) || rawImage == null) {
            return;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("CardFaceIdentifier", cardId);
            contentValues.put("CardFaceImageUrl", url);
            contentValues.put("CardFaceImage", rawImage);
            this.db.update(TABLE_CARDFACEINFO, contentValues, "CardFaceIdentifier = ?", new String[]{cardId});
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 562, e);
        }
    }

    public void addCardFaceImage(String cardId, String url, byte[] rawImage) throws DatabaseException {
        if (TextUtils.isEmpty(cardId) || TextUtils.isEmpty(url) || rawImage == null) {
            return;
        }
        try {
            SQLiteStatement sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_CARDFACEIMAGE_TABLE);
            try {
                sQLiteStatementCompileStatement.bindString(1, cardId);
                sQLiteStatementCompileStatement.bindString(2, url);
                sQLiteStatementCompileStatement.bindBlob(3, rawImage);
                sQLiteStatementCompileStatement.executeInsert();
                if (sQLiteStatementCompileStatement != null) {
                    sQLiteStatementCompileStatement.close();
                }
            } finally {
            }
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 563, e);
        }
    }

    public void deleteBgUpdateCardArtInfo(String cardId) {
        cleanBgUpdateCardArtInfo("CardId = ?", new String[]{cardId});
    }

    public void deleteBgUpdateCardArtInfo() {
        cleanBgUpdateCardArtInfo(null, null);
    }

    private void cleanBgUpdateCardArtInfo(String whereClause, String[] whereArgs) {
        this.db.delete(TABLE_BGUPDATECARDARTINFO, whereClause, whereArgs);
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public boolean isBgUpdateCardArtInfoExists(String cardId) throws DatabaseException {
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_BGUPDATECARDARTINFO, new String[]{"COUNT(*)"}, "CardId = ?", new String[]{cardId}, null, null, null, "1");
                cursorQuery.moveToFirst();
                boolean z = cursorQuery.getInt(0) > 0;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return z;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 564, e);
            }
        } finally {
        }
    }

    public List<DatabaseExpert.BgUpdateCardArtInfo> getBgUpdateCardArtInfoList() throws DatabaseException {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_BGUPDATECARDARTINFO, null, null, null, null, null, null, null);
                cursorQuery.moveToFirst();
                for (int i = 0; i < cursorQuery.getCount(); i++) {
                    int columnIndex = cursorQuery.getColumnIndex("CardId");
                    if (columnIndex >= 0) {
                        arrayList.add(new DatabaseExpert.BgUpdateCardArtInfo(cursorQuery.getString(columnIndex)));
                    }
                    cursorQuery.moveToNext();
                }
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 564, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public void addBgUpdateCardArtInfo(String cardId) throws DatabaseException {
        if (TextUtils.isEmpty(cardId)) {
            return;
        }
        try {
            SQLiteStatement sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_BGUPDATECARDARTINFO_TABLE);
            try {
                sQLiteStatementCompileStatement.bindString(1, cardId);
                sQLiteStatementCompileStatement.executeInsert();
                if (sQLiteStatementCompileStatement != null) {
                    sQLiteStatementCompileStatement.close();
                }
            } finally {
            }
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 565, e);
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public Map<String, Object> getSpecificColumnData(String serviceId, List<String> columns) throws DatabaseException {
        HashMap map = new HashMap();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_SERVICELIST, (String[]) columns.toArray(new String[0]), "ServiceID = ?", new String[]{serviceId}, null, null, null, "1");
                cursorQuery.moveToFirst();
                for (int i = 0; i < cursorQuery.getColumnCount(); i++) {
                    String columnName = cursorQuery.getColumnName(i);
                    if ("IconData".equals(columnName) || "IconData1".equals(columnName) || "IconData2".equals(columnName) || "CardFaceImage".equals(columnName)) {
                        map.put(cursorQuery.getColumnName(i), cursorQuery.getBlob(i));
                    } else {
                        map.put(cursorQuery.getColumnName(i), cursorQuery.getString(i));
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return map;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 225, e);
            }
        } finally {
        }
    }

    public void updateSpecificColumnData(String id, Map<String, Object> map) throws DatabaseException {
        try {
            ContentValues contentValues = new ContentValues();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                if ("IconData".equals(key) || "IconData1".equals(key) || "IconData2".equals(key) || "CardFaceImage".equals(key)) {
                    contentValues.put(key, (byte[]) entry.getValue());
                } else {
                    contentValues.put(key, (String) entry.getValue());
                }
            }
            this.db.update(TABLE_SERVICELIST, contentValues, "ServiceID = ?", new String[]{id});
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 658, e);
        }
    }

    public List<MfcExpert.Area> getCacheChipAreaItem() throws DatabaseException {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_CACHE_CHIP_AREA, COLUMNS_CACHE_AREA_LIST, null, null, null, null, null, null);
                cursorQuery.moveToFirst();
                int count = cursorQuery.getCount();
                for (int i = 0; i < count; i++) {
                    arrayList.add(new MfcExpert.Area(new AreaItemFelica(cursorQuery.getString(0), cursorQuery.getString(1))));
                    cursorQuery.moveToNext();
                }
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 177, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public void replaceCacheChipAreaItem(List<MfcExpert.Area> itemList) throws DatabaseException {
        if (itemList == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                deleteCacheChipAreaInfo();
                if (!itemList.isEmpty()) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_TABLE_CACHE_CHIP_AREA);
                    for (int i = 0; i < itemList.size(); i++) {
                        MfcExpert.Area area = itemList.get(i);
                        sQLiteStatementCompileStatement.bindString(1, area.sysCode);
                        sQLiteStatementCompileStatement.bindString(2, area.areaCode);
                        sQLiteStatementCompileStatement.executeInsert();
                    }
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 178, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    public void deleteCacheChipAreaInfo() {
        this.db.delete(TABLE_CACHE_CHIP_AREA, null, null);
    }

    public boolean getCacheChipAreaSetting() throws DatabaseException {
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_CACHE_CHIP_AREA_SETTING, COLUMNS_CACHE_AREA_LIST_SETTING, null, null, null, null, null, null);
                cursorQuery.moveToFirst();
                return cursorQuery.getInt(0) != 0;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), MfiClientException.TYPE_CHECK_AND_RECOVER_CRS_STATE_FAILED, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public void replaceCacheChipAreaSetting(boolean setting) throws DatabaseException {
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                deleteCacheChipAreaSetting();
                sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_TABLE_CACHE_CHIP_AREA_SETTING);
                if (setting) {
                    sQLiteStatementCompileStatement.bindLong(1, 1L);
                } else {
                    sQLiteStatementCompileStatement.bindLong(1, 0L);
                }
                sQLiteStatementCompileStatement.executeInsert();
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 180, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    public void deleteCacheChipAreaSetting() {
        this.db.delete(TABLE_CACHE_CHIP_AREA_SETTING, null, null);
    }

    public List<MfcExpert.Sas> getCacheChipSasItem() throws DatabaseException {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_CACHE_CHIP_SAS, COLUMNS_CACHE_SAS_LIST, null, null, null, null, null, null);
                cursorQuery.moveToFirst();
                int count = cursorQuery.getCount();
                for (int i = 0; i < count; i++) {
                    arrayList.add(new MfcExpert.Sas(new SasReadItem(cursorQuery.getString(0), cursorQuery.getString(1))));
                    cursorQuery.moveToNext();
                }
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 181, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public void replaceCacheChipSasItem(List<MfcExpert.Sas> itemList) throws DatabaseException {
        if (itemList == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                deleteCacheChipSasInfo();
                if (!itemList.isEmpty()) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_TABLE_CACHE_CHIP_SAS);
                    for (int i = 0; i < itemList.size(); i++) {
                        MfcExpert.Sas sas = itemList.get(i);
                        sQLiteStatementCompileStatement.bindString(1, sas.cpidSid);
                        sQLiteStatementCompileStatement.bindString(2, sas.blockName);
                        sQLiteStatementCompileStatement.executeInsert();
                    }
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 182, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    public void deleteCacheChipSasInfo() {
        this.db.delete(TABLE_CACHE_CHIP_SAS, null, null);
    }

    public boolean getCacheChipSasSetting() throws DatabaseException {
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_CACHE_CHIP_SAS_SETTING, COLUMNS_CACHE_SAS_LIST_SETTING, null, null, null, null, null, null);
                cursorQuery.moveToFirst();
                return cursorQuery.getInt(0) != 0;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 183, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public void replaceCacheChipSasSetting(boolean setting) throws DatabaseException {
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                deleteCacheChipSasSetting();
                sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_TABLE_CACHE_CHIP_SAS_SETTING);
                if (setting) {
                    sQLiteStatementCompileStatement.bindLong(1, 1L);
                } else {
                    sQLiteStatementCompileStatement.bindLong(1, 0L);
                }
                sQLiteStatementCompileStatement.executeInsert();
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 184, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    public void deleteCacheChipSasSetting() {
        this.db.delete(TABLE_CACHE_CHIP_SAS_SETTING, null, null);
    }

    public List<PackageExpert.InstalledApp> getCacheInstalledAppList() throws DatabaseException {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_CACHE_INSTALLED_APP_LIST, COLUMNS_CACHE_INSTALLED_APP_LIST, null, null, null, null, null, null);
                cursorQuery.moveToFirst();
                HashMap map = new HashMap();
                for (int i = 0; i < cursorQuery.getCount(); i++) {
                    String string = cursorQuery.getString(0);
                    byte[] blob = cursorQuery.getBlob(1);
                    List arrayList2 = (List) map.get(string);
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                    }
                    if (!arrayList2.contains(blob)) {
                        arrayList2.add(blob);
                        map.put(string, arrayList2);
                    }
                    cursorQuery.moveToNext();
                }
                for (Map.Entry entry : map.entrySet()) {
                    arrayList.add(new PackageExpert.InstalledApp((String) entry.getKey(), (List) entry.getValue()));
                }
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 185, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public void replaceCacheInstalledAppList(List<PackageExpert.InstalledApp> itemList) throws DatabaseException {
        if (itemList == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                deleteCacheInstalledAppList();
                if (!itemList.isEmpty()) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_TABLE_CACHE_INSTALLED_APP_LIST);
                    for (int i = 0; i < itemList.size(); i++) {
                        PackageExpert.InstalledApp installedApp = itemList.get(i);
                        for (int i2 = 0; i2 < installedApp.sigHash.size(); i2++) {
                            sQLiteStatementCompileStatement.bindString(1, installedApp.pkgName);
                            sQLiteStatementCompileStatement.bindBlob(2, installedApp.sigHash.get(i2));
                            sQLiteStatementCompileStatement.executeInsert();
                        }
                    }
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 186, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    public void deleteCacheInstalledAppList() {
        this.db.delete(TABLE_CACHE_INSTALLED_APP_LIST, null, null);
    }

    public boolean getCacheInstalledAppListSetting() throws DatabaseException {
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_CACHE_INSTALLED_APP_LIST_SETTING, COLUMNS_CACHE_INSTALLED_APP_LIST_SETTING, null, null, null, null, null, null);
                cursorQuery.moveToFirst();
                return cursorQuery.getInt(0) != 0;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 187, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public void replaceCacheInstalledAppListSetting(boolean setting) throws DatabaseException {
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                deleteCacheInstalledAppListSetting();
                sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_TABLE_CACHE_INSTALLED_APP_LIST_SETTING);
                if (setting) {
                    sQLiteStatementCompileStatement.bindLong(1, 1L);
                } else {
                    sQLiteStatementCompileStatement.bindLong(1, 0L);
                }
                sQLiteStatementCompileStatement.executeInsert();
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 188, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    public void deleteCacheInstalledAppListSetting() {
        this.db.delete(TABLE_CACHE_INSTALLED_APP_LIST_SETTING, null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List<MfcExpert.Asset> getCacheAssetList() throws Throwable {
        Cursor cursor;
        Cursor cursorQuery;
        ArrayList arrayList = new ArrayList();
        try {
            cursorQuery = this.db.query(TABLE_CACHE_ASSET_LIST, COLUMNS_CACHE_ASSET, null, null, null, null, null, null);
        } catch (Exception e) {
            e = e;
            cursor = null;
        } catch (Throwable th) {
            th = th;
            cursor = null;
        }
        try {
            cursorQuery.moveToFirst();
            int count = cursorQuery.getCount();
            int i = 0;
            int i2 = 0;
            while (i2 < count) {
                String string = cursorQuery.getString(i);
                String string2 = cursorQuery.getString(1);
                int i3 = cursorQuery.getInt(2);
                int i4 = cursorQuery.getInt(3);
                int i5 = cursorQuery.getInt(4);
                int i6 = cursorQuery.getInt(5);
                long j = cursorQuery.getLong(6);
                Date date = j != -1 ? new Date(j) : null;
                int i7 = i2;
                long j2 = cursorQuery.getLong(7);
                arrayList.add(new MfcExpert.Asset(string, string2, i3, i4, i5, i6, date, j2 != -1 ? new Date(j2) : null, cursorQuery.getInt(8) == 1));
                cursorQuery.moveToNext();
                i2 = i7 + 1;
                i = 0;
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return arrayList;
        } catch (Exception e2) {
            e = e2;
            cursor = cursorQuery;
            try {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 189, e);
            } catch (Throwable th2) {
                th = th2;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            cursor = cursorQuery;
            if (cursor != null) {
            }
            throw th;
        }
    }

    public void replaceCacheAssetList(List<MfcExpert.Asset> itemList) throws DatabaseException {
        if (itemList == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                deleteCacheAssetList();
                if (!itemList.isEmpty()) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_TABLE_CACHE_ASSET_LIST);
                    for (int i = 0; i < itemList.size(); i++) {
                        MfcExpert.Asset asset = itemList.get(i);
                        sQLiteStatementCompileStatement.bindString(1, asset.serviceId);
                        sQLiteStatementCompileStatement.bindString(2, asset.cardId);
                        sQLiteStatementCompileStatement.bindLong(3, asset.balanceValue);
                        sQLiteStatementCompileStatement.bindLong(4, asset.balanceLimit);
                        sQLiteStatementCompileStatement.bindLong(5, asset.point1);
                        sQLiteStatementCompileStatement.bindLong(6, asset.point2);
                        if (asset.date1 != null) {
                            sQLiteStatementCompileStatement.bindLong(7, asset.date1.getTime());
                        } else {
                            sQLiteStatementCompileStatement.bindLong(7, -1L);
                        }
                        if (asset.date2 != null) {
                            sQLiteStatementCompileStatement.bindLong(8, asset.date2.getTime());
                        } else {
                            sQLiteStatementCompileStatement.bindLong(8, -1L);
                        }
                        if (asset.isDcardMini) {
                            sQLiteStatementCompileStatement.bindLong(9, 1L);
                        } else {
                            sQLiteStatementCompileStatement.bindLong(9, 0L);
                        }
                        sQLiteStatementCompileStatement.executeInsert();
                    }
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 190, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    public void deleteCacheAssetList() {
        this.db.delete(TABLE_CACHE_ASSET_LIST, null, null);
    }

    public boolean getCacheAssetListSetting() throws DatabaseException {
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_CACHE_ASSET_LIST_SETTING, COLUMNS_CACHE_ASSET_SETTING, null, null, null, null, null, null);
                cursorQuery.moveToFirst();
                return cursorQuery.getInt(0) != 0;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 191, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public void replaceCacheAssetListSetting(boolean setting) throws DatabaseException {
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                deleteCacheAssetListSetting();
                sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_TABLE_CACHE_ASSET_LIST_SETTING);
                if (setting) {
                    sQLiteStatementCompileStatement.bindLong(1, 1L);
                } else {
                    sQLiteStatementCompileStatement.bindLong(1, 0L);
                }
                sQLiteStatementCompileStatement.executeInsert();
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), MfiClientException.TYPE_EXIST_SERVICE_FAILED, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    public void deleteCacheAssetListSetting() {
        this.db.delete(TABLE_CACHE_ASSET_LIST_SETTING, null, null);
    }

    public void deleteCache() {
        deleteCacheChipAreaInfo();
        deleteCacheChipSasInfo();
        deleteCacheInstalledAppList();
        deleteCacheAssetList();
        try {
            replaceCacheChipAreaSetting(false);
            replaceCacheChipSasSetting(false);
            replaceCacheInstalledAppListSetting(false);
            replaceCacheAssetListSetting(false);
        } catch (Exception e) {
            LogUtil.warning(e);
        }
    }

    private class StringBuilderForValue {
        private final String DOUBLE_QUOTATION = "\"";
        private final StringBuilder sb = new StringBuilder();

        StringBuilderForValue() {
        }

        void appendValue(Object obj) {
            this.sb.append(", ");
            this.sb.append("\"");
            if (obj != null) {
                this.sb.append(obj);
            }
            this.sb.append("\"");
        }

        void append(Object obj) {
            this.sb.append(obj);
        }

        String getString() {
            return this.sb.toString();
        }
    }
}

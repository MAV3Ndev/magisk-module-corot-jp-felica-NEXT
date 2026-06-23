package com.felicanetworks.mfm.main.model.internal.main.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.main.model.info.CommonInfo;
import com.felicanetworks.mfm.main.model.info.ContactInfo;
import com.felicanetworks.mfm.main.model.info.GeneralInfo;
import com.felicanetworks.mfm.main.model.info.LinkageApplicationInfo;
import com.felicanetworks.mfm.main.model.info.LinkageInfo;
import com.felicanetworks.mfm.main.model.info.MfiCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.OptionalInfo;
import com.felicanetworks.mfm.main.model.info.Section1;
import com.felicanetworks.mfm.main.model.info.Section2;
import com.felicanetworks.mfm.main.model.info.TermOfValidity;
import com.felicanetworks.mfm.main.model.info.TransitInfo;
import com.felicanetworks.mfm.main.model.info.TransitPassInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmlib.MfmAppContext;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.db.table.OptionalInformationListTable;
import com.felicanetworks.mfm.main.model.internal.main.db.table.TransitInformationListTable;
import com.felicanetworks.mfm.main.policy.helper.db.table.StatementUtils;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MfmDatabaseAccess {
    private static final String INSERT_APPLIST_TABLE = "INSERT INTO AppList VALUES ( ?, ?, ?, ? )";
    private static final String INSERT_AREALIST_TABLE = "INSERT INTO AreaList VALUES ( ?, ?, ?, ?, ?)";
    private static final String INSERT_BANNERINFO_TABLE = "INSERT INTO BannerInfo VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    private static final String INSERT_BOOKMARKLIST_TABLE = "INSERT INTO BookmarkList VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    private static final String INSERT_CARDFACEIMAGE_TABLE = "INSERT INTO CardFaceInfo(CardFaceIdentifier, CardFaceImageUrl, CardFaceImage) VALUES ( ?, ?, ?)";
    private static final String INSERT_FPSERVICELIST_TABLE = "INSERT INTO FpServiceList VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    private static final String INSERT_MULTIPURPOSELIST_TABLE = "INSERT INTO MultipurposeList VALUES ( ?, ?, ?, ?, ?)";
    private static final String INSERT_SERVICELIST_LINKAGE_APP_TABLE = "INSERT INTO ServiceLinkageAppList VALUES ( ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_SERVICELIST_LINKAGE_WEB_TABLE = "INSERT INTO ServiceLinkageWebList VALUES ( ?, ?, ?)";
    private static final String INSERT_SERVICELIST_TABLE = "INSERT INTO ServiceList VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    private static final String SELECT_FROMSERVICELINKAGE_APP_SQL = " SELECT ServiceID, LinkageApp_PackageName, LinkageApp_Hash, LinkageApp_GetType, LinkageApp_GetUrl, LinkageApp_Icon, LinkageApp_AppName FROM ServiceLinkageAppList WHERE ServiceID = ?";
    private static final String SELECT_FROMSERVICELINKAGE_WEB_SQL = " SELECT ServiceID, LinkageWeb_Url, LinkageWeb_Name FROM ServiceLinkageWebList WHERE ServiceID = ?";
    private static final String SELECT_FROMSERVICELIST_SQL = " SELECT ServiceID, ServiceVersion, ServiceName, ServiceProviderName, CooperativeKind, PackageName, HashValue, ApplicationKind, ApplicationUrl, WebUrl, DisplayPriority, ServiceColor, ServiceType, CardCategory, DetailImagePresence, CardFaceImage, ContactName, ContactPhone, ContactUrl, ContactMail FROM ServiceList ORDER BY DisplayPriority, ServiceID";
    private static final String SELECT_FROM_BANNERLIST_SQL = "SELECT Kind, Id, Priority, BannerImg, Img, Ls, Start, End, Updated FROM BannerInfo WHERE BannerImg IS NOT NULL AND Kind = ? AND (Start <= ? AND ? <= End) ORDER BY Priority";
    private static final String SELECT_FROM_BANNERSETTINGS_SQL = "SELECT Kind, BannerInfoFileUpdated, BannerInfoLastUpdatedDate FROM BannerSetting WHERE Kind = ?";
    private static final String SELECT_FROM_BOOKMARKLIST_SQL = "SELECT ServiceID, ServiceVersion, CategoryID, CategoryName, ServiceName, ServiceProviderName, StatusKind, CooperativeKind, ApplicationKind, ApplicationUrl, WebUrl, Summary, Detail, Procedure1, Procedure2, Procedure3, Procedure4, Procedure5 FROM BookmarkList ORDER BY DisplayPriority ASC";
    private static final String SELECT_FROM_BOOKMARKSETTINGS_SQL = "SELECT BookmarkLastUpdatedDate FROM BookmarkSetting ";
    private static final String SELECT_FROM_CARDADDITIONALINFO_SQL = "SELECT CardID, LanguageCode, DisplayName, Value FROM CardAdditionalInfo ";
    private static final String SELECT_FROM_CARDFACEIMAGE_IDENTIFIER_RES_SQL = "SELECT CardFaceIdentifier, CardFaceImageUrl, CardFaceImage FROM CardFaceInfo WHERE CardFaceIdentifier = ?";
    private static final String SELECT_FROM_FPSERVICELIST_SQL = "SELECT FpServiceNumber, FpServiceName, FpServiceIconUrl, FpServiceProviderName, ServiceEnable, WebUrl, FpServiceKind, NeedShowValue, PointUnitName FROM FpServiceList ";
    private static final String SELECT_FROM_FPSETTING_SQL = "SELECT FpLastUpdatedDate FROM FpSetting LIMIT 1";
    private static final String SELECT_SERVICEID_FROM_SERVICELIST_AND_BOOKMARKLIST_SQL = "SELECT ServiceID FROM ServiceList WHERE ServiceID = (SELECT ServiceID FROM BookmarkList WHERE ServiceID = ?) LIMIT 1";
    private static final String SELECT_SPEC_AID_FROM_SERVICELIST_SQL = "SELECT ServiceID, ServiceVersion, ServiceName, ServiceProviderName, CooperativeKind, PackageName, HashValue, ApplicationKind, ApplicationUrl, WebUrl, DisplayPriority, ServiceColor, ServiceType, CardCategory, DetailImagePresence, CardFaceImage, ContactName, ContactPhone, ContactUrl, ContactMail FROM ServiceList WHERE ServiceID = (SELECT ServiceID FROM MultipurposeList WHERE MultipurposeServiceKind = '002' AND MultipurposeIdentifierCode = ";
    private static final String SELEC_FROM_CARDADDITIONALINFO_CONTACTINFO_SQL = "SELECT Name, ContactPhoneNumber, ContactURL, ContactEmail FROM CardAdditionalInfo ";
    private static final String SELEC_FROM_CARDADDITIONALINFO_GENERALINFO_SQL = "SELECT CardTypeName, CardArtUrl, ContactName FROM CardAdditionalInfo ";
    private static final String TABLE_APPLIST = "AppList";
    private static final String TABLE_AREALIST = "AreaList";
    private static final String TABLE_BANNERINFO = "BannerInfo";
    private static final String TABLE_BANNERSETTING = "BannerSetting";
    private static final String TABLE_BOOKMARKLIST = "BookmarkList";
    private static final String TABLE_BOOKMARKSETTING = "BookmarkSetting";
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

    enum EnumSelectFromCardAdditionalInfoLinkageInfoSql {
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_1 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.1
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String getSql() {
                return "SELECT LinkageKind1, LinkageName1, LinkageWebsiteUrl1 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_2 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.2
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String getSql() {
                return "SELECT LinkageKind2, LinkageName2, LinkageWebsiteUrl2 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_3 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.3
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String getSql() {
                return "SELECT LinkageKind3, LinkageName3, LinkageWebsiteUrl3 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_4 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.4
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String getSql() {
                return "SELECT LinkageKind4, LinkageName4, LinkageWebsiteUrl4 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_5 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.5
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String getSql() {
                return "SELECT LinkageKind5, LinkageName5, LinkageWebsiteUrl5 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_6 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.6
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String getSql() {
                return "SELECT LinkageKind6, LinkageName6, LinkageWebsiteUrl6 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_7 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.7
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String getSql() {
                return "SELECT LinkageKind7, LinkageName7, LinkageWebsiteUrl7 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_8 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.8
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String getSql() {
                return "SELECT LinkageKind8, LinkageName8, LinkageWebsiteUrl8 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_9 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.9
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String getSql() {
                return "SELECT LinkageKind9, LinkageName9, LinkageWebsiteUrl9 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEINFO_SQL_10 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql.10
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageInfoSql
            String getSql() {
                return "SELECT LinkageKind10, LinkageName10, LinkageWebsiteUrl10 FROM CardAdditionalInfo ";
            }
        };

        /* synthetic */ EnumSelectFromCardAdditionalInfoLinkageInfoSql(AnonymousClass1 anonymousClass1) {
            this();
        }

        String getSql() {
            throw new UnsupportedOperationException("not defined.");
        }
    }

    enum EnumSelectFromCardAdditionalInfoLinkageAppInfoSql {
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_1 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.1
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String getSql() {
                return "SELECT AppIconUrl1, AppIdentifiableInfo1, AppCallerInfo1, AppGetKind1, AppGetUrl1 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_2 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.2
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String getSql() {
                return "SELECT AppIconUrl2, AppIdentifiableInfo2, AppCallerInfo2, AppGetKind2, AppGetUrl2 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_3 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.3
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String getSql() {
                return "SELECT AppIconUrl3, AppIdentifiableInfo3, AppCallerInfo3, AppGetKind3, AppGetUrl3 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_4 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.4
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String getSql() {
                return "SELECT AppIconUrl4, AppIdentifiableInfo4, AppCallerInfo4, AppGetKind4, AppGetUrl4 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_5 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.5
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String getSql() {
                return "SELECT AppIconUrl5, AppIdentifiableInfo5, AppCallerInfo5, AppGetKind5, AppGetUrl5 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_6 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.6
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String getSql() {
                return "SELECT AppIconUrl6, AppIdentifiableInfo6, AppCallerInfo6, AppGetKind6, AppGetUrl6 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_7 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.7
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String getSql() {
                return "SELECT AppIconUrl7, AppIdentifiableInfo7, AppCallerInfo7, AppGetKind7, AppGetUrl7 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_8 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.8
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String getSql() {
                return "SELECT AppIconUrl8, AppIdentifiableInfo8, AppCallerInfo8, AppGetKind8, AppGetUrl8 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_9 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.9
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String getSql() {
                return "SELECT AppIconUrl9, AppIdentifiableInfo9, AppCallerInfo9, AppGetKind9, AppGetUrl9 FROM CardAdditionalInfo ";
            }
        },
        SELEC_FROM_CARDADDITIONALINFO_LINKAGEAPPINFO_SQL_10 { // from class: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.10
            @Override // com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.EnumSelectFromCardAdditionalInfoLinkageAppInfoSql
            String getSql() {
                return "SELECT AppIconUrl10, AppIdentifiableInfo10, AppCallerInfo10, AppGetKind10, AppGetUrl10 FROM CardAdditionalInfo ";
            }
        };

        /* synthetic */ EnumSelectFromCardAdditionalInfoLinkageAppInfoSql(AnonymousClass1 anonymousClass1) {
            this();
        }

        String getSql() {
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

    MfmDatabaseAccess(MfmAppContext mfmAppContext) throws DatabaseException {
        this.db = null;
        try {
            this.db = MfmDatabaseHelper.getInstance(mfmAppContext).getWritableDatabase();
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
                if (this.db != null && this.db.isOpen()) {
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

    public void replaceAreaItem(List<DatabaseExpert.AreaId> list) throws DatabaseException {
        if (list == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                cleanAreaInfo();
                if (list.size() > 0) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_AREALIST_TABLE);
                    for (int i = 0; i < list.size(); i++) {
                        DatabaseExpert.AreaId areaId = list.get(i);
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

    public void replaceApplicationItem(List<DatabaseExpert.AppId> list) throws DatabaseException {
        if (list == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                cleanAppInfo();
                if (list.size() > 0) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_APPLIST_TABLE);
                    for (int i = 0; i < list.size(); i++) {
                        DatabaseExpert.AppId appId = list.get(i);
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

    public void replaceMultiPurposeIdentifierItem(List<DatabaseExpert.MultiId> list) throws DatabaseException {
        if (list == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                cleanMultiPurposeIdentifierItem();
                if (list.size() > 0) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_MULTIPURPOSELIST_TABLE);
                    for (int i = 0; i < list.size(); i++) {
                        DatabaseExpert.MultiId multiId = list.get(i);
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

    /* JADX WARN: Removed duplicated region for block: B:77:0x04b2  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x04b7  */
    /* JADX WARN: Removed duplicated region for block: B:95:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void addOrUpdateServiceItem(java.util.List<com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.ServiceWithIcon> r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 1212
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.addOrUpdateServiceItem(java.util.List):void");
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

    /* JADX WARN: Removed duplicated region for block: B:46:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<com.felicanetworks.mfm.main.model.info.Service> getServiceItem() throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 438
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.getServiceItem():java.util.List");
    }

    public byte[] getIconData(String str, DatabaseExpert.ServiceWithIcon.IconType iconType) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        byte[] blob = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_SERVICELIST, new String[]{"IconData", "IconData1", "IconData2"}, "ServiceID = ?", new String[]{str}, null, null, null, "1");
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
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
            e = e2;
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 225, e);
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQuery;
            if (cursor != null) {
                cursor.close();
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.IdsSettings getSettingItem() throws java.lang.Throwable {
        /*
            r11 = this;
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r11.db     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L3a
            java.lang.String r2 = "Setting"
            java.lang.String[] r3 = com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.COLUMNS_SETTING     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L3a
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r9 = "1"
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L3a
            r1.moveToFirst()     // Catch: java.lang.Exception -> L33 java.lang.Throwable -> L4d
            int r2 = r1.getCount()     // Catch: java.lang.Exception -> L33 java.lang.Throwable -> L4d
            if (r2 <= 0) goto L2d
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$IdsSettings r0 = new com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$IdsSettings     // Catch: java.lang.Exception -> L33 java.lang.Throwable -> L4d
            r2 = 0
            int r2 = r1.getInt(r2)     // Catch: java.lang.Exception -> L33 java.lang.Throwable -> L4d
            r3 = 1
            java.lang.String r3 = r1.getString(r3)     // Catch: java.lang.Exception -> L33 java.lang.Throwable -> L4d
            r0.<init>(r2, r3)     // Catch: java.lang.Exception -> L33 java.lang.Throwable -> L4d
            r1.moveToNext()     // Catch: java.lang.Exception -> L33 java.lang.Throwable -> L4d
        L2d:
            if (r1 == 0) goto L32
            r1.close()
        L32:
            return r0
        L33:
            r0 = move-exception
            goto L3e
        L35:
            r1 = move-exception
            r10 = r1
            r1 = r0
            r0 = r10
            goto L4e
        L3a:
            r1 = move-exception
            r10 = r1
            r1 = r0
            r0 = r10
        L3e:
            com.felicanetworks.mfm.main.policy.log.LogUtil.warning(r0)     // Catch: java.lang.Throwable -> L4d
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException r2 = new com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException     // Catch: java.lang.Throwable -> L4d
            java.lang.Class r3 = r11.getClass()     // Catch: java.lang.Throwable -> L4d
            r4 = 241(0xf1, float:3.38E-43)
            r2.<init>(r3, r4, r0)     // Catch: java.lang.Throwable -> L4d
            throw r2     // Catch: java.lang.Throwable -> L4d
        L4d:
            r0 = move-exception
        L4e:
            if (r1 == 0) goto L53
            r1.close()
        L53:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.getSettingItem():com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$IdsSettings");
    }

    public void updateSettingItem(DatabaseExpert.IdsSettings idsSettings) throws DatabaseException {
        if (idsSettings != null) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("UpdateInterval", Integer.valueOf(idsSettings.interval));
                contentValues.put("LastUpdateDate", idsSettings.lastUpdate);
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

    void deleteAreaInfo(List<String> list) {
        String where = StatementUtils.getWhere("ServiceID", list);
        if (where == null) {
            return;
        }
        this.db.delete(TABLE_AREALIST, where, (String[]) list.toArray(new String[0]));
    }

    private void cleanAppInfo() {
        this.db.delete(TABLE_APPLIST, null, null);
    }

    void deleteAppInfo(List<String> list) {
        String where = StatementUtils.getWhere("ServiceID", list);
        if (where == null) {
            return;
        }
        this.db.delete(TABLE_APPLIST, where, (String[]) list.toArray(new String[0]));
    }

    private void cleanMultiPurposeIdentifierItem() {
        this.db.delete(TABLE_MULTIPURPOSELIST, null, null);
    }

    void deleteMultiPurposeIdentifierItem(List<String> list) {
        String where = StatementUtils.getWhere("ServiceID", list);
        if (where == null) {
            return;
        }
        this.db.delete(TABLE_MULTIPURPOSELIST, where, (String[]) list.toArray(new String[0]));
    }

    public void deleteServiceItem(List<String> list) throws DatabaseException {
        try {
            StringBuilder sb = new StringBuilder();
            if (list == null || list.size() <= 0) {
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                sb.append(String.format("'%s'", list.get(i)));
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

    void deleteBookmarkItem(List<String> list) {
        String where = StatementUtils.getWhere("ServiceID", list);
        if (where == null) {
            return;
        }
        this.db.delete(TABLE_BOOKMARKLIST, where, (String[]) list.toArray(new String[0]));
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.Recommend> getBookmarkItem() throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 201
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.getBookmarkItem():java.util.List");
    }

    public void replaceBookmarkItem(List<DatabaseExpert.Recommend> list) throws DatabaseException {
        if (list == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                cleanBookmarkItem();
                if (list.size() > 0) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_BOOKMARKLIST_TABLE);
                    long j = 1;
                    for (int i = 0; i < list.size(); i++) {
                        DatabaseExpert.Recommend recommend = list.get(i);
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
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = this.db.rawQuery("SELECT FpServiceNumber, FpServiceName, FpServiceIconUrl, FpServiceProviderName, ServiceEnable, WebUrl, FpServiceKind, NeedShowValue, PointUnitName FROM FpServiceList  limit " + ((String) Sg.getValue(Sg.Key.DB_FP_SERVICE_TABLE_SELECT_LIMIT)), null);
                cursorRawQuery.moveToFirst();
                for (int i = 0; i < cursorRawQuery.getCount(); i++) {
                    arrayList.add(new DatabaseExpert.FelicaPocketService(cursorRawQuery.getString(0), cursorRawQuery.getString(1), cursorRawQuery.getString(2), cursorRawQuery.getString(3), cursorRawQuery.getInt(4), cursorRawQuery.getString(5), cursorRawQuery.getString(6), cursorRawQuery.getInt(7), cursorRawQuery.getString(8)));
                    cursorRawQuery.moveToNext();
                }
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 306, e);
            }
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    public void replaceFpServiceItem(List<DatabaseExpert.FelicaPocketService> list) throws DatabaseException {
        if (list == null) {
            return;
        }
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                cleanFpServeInfoList();
                if (list.size() > 0) {
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_FPSERVICELIST_TABLE);
                    for (int i = 0; i < list.size(); i++) {
                        DatabaseExpert.FelicaPocketService felicaPocketService = list.get(i);
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.UpgradeSettings getUpdateInfoItem() throws java.lang.Throwable {
        /*
            r11 = this;
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r11.db     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L44
            java.lang.String r2 = "UpdateInfo"
            java.lang.String[] r3 = com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.COLUMNS_UPDATEINFO     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L44
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r9 = "1"
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L44
            r1.moveToFirst()     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L57
            int r2 = r1.getCount()     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L57
            if (r2 <= 0) goto L37
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$UpgradeSettings r0 = new com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$UpgradeSettings     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L57
            r2 = 0
            int r2 = r1.getInt(r2)     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L57
            r3 = 1
            java.lang.String r3 = r1.getString(r3)     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L57
            r4 = 2
            int r4 = r1.getInt(r4)     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L57
            r5 = 3
            int r5 = r1.getInt(r5)     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L57
            r0.<init>(r2, r3, r4, r5)     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L57
            r1.moveToNext()     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L57
        L37:
            if (r1 == 0) goto L3c
            r1.close()
        L3c:
            return r0
        L3d:
            r0 = move-exception
            goto L48
        L3f:
            r1 = move-exception
            r10 = r1
            r1 = r0
            r0 = r10
            goto L58
        L44:
            r1 = move-exception
            r10 = r1
            r1 = r0
            r0 = r10
        L48:
            com.felicanetworks.mfm.main.policy.log.LogUtil.warning(r0)     // Catch: java.lang.Throwable -> L57
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException r2 = new com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException     // Catch: java.lang.Throwable -> L57
            java.lang.Class r3 = r11.getClass()     // Catch: java.lang.Throwable -> L57
            r4 = 321(0x141, float:4.5E-43)
            r2.<init>(r3, r4, r0)     // Catch: java.lang.Throwable -> L57
            throw r2     // Catch: java.lang.Throwable -> L57
        L57:
            r0 = move-exception
        L58:
            if (r1 == 0) goto L5d
            r1.close()
        L5d:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.getUpdateInfoItem():com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$UpgradeSettings");
    }

    public void updateUpdateInfoItem(DatabaseExpert.UpgradeSettings upgradeSettings) throws DatabaseException {
        if (upgradeSettings != null) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("OfflineCheckInterval", Integer.valueOf(upgradeSettings.interval));
                contentValues.put("LastCheckDate", upgradeSettings.lastChkDate);
                contentValues.put("OfflineCheckActualCnt", Integer.valueOf(upgradeSettings.chkCnt));
                contentValues.put("OfflineCheckMaxCnt", Integer.valueOf(upgradeSettings.maxCnt));
                this.db.update(TABLE_UPDATEINFO, contentValues, null, null);
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 337, e);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.TosVersionSettings getPolicyUpdateInfoItem() throws java.lang.Throwable {
        /*
            r11 = this;
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r11.db     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L4a
            java.lang.String r2 = "PolicyUpdateInfo"
            java.lang.String[] r3 = com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.COLUMNS_POLICYUPDATEINFO     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L4a
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r9 = "1"
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L4a
            r1.moveToFirst()     // Catch: java.lang.Exception -> L43 java.lang.Throwable -> L5d
            int r2 = r1.getCount()     // Catch: java.lang.Exception -> L43 java.lang.Throwable -> L5d
            if (r2 <= 0) goto L3d
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$TosVersionSettings r0 = new com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$TosVersionSettings     // Catch: java.lang.Exception -> L43 java.lang.Throwable -> L5d
            r2 = 0
            int r4 = r1.getInt(r2)     // Catch: java.lang.Exception -> L43 java.lang.Throwable -> L5d
            r2 = 1
            java.lang.String r5 = r1.getString(r2)     // Catch: java.lang.Exception -> L43 java.lang.Throwable -> L5d
            r2 = 2
            int r6 = r1.getInt(r2)     // Catch: java.lang.Exception -> L43 java.lang.Throwable -> L5d
            r2 = 3
            int r7 = r1.getInt(r2)     // Catch: java.lang.Exception -> L43 java.lang.Throwable -> L5d
            r2 = 4
            int r8 = r1.getInt(r2)     // Catch: java.lang.Exception -> L43 java.lang.Throwable -> L5d
            r3 = r0
            r3.<init>(r4, r5, r6, r7, r8)     // Catch: java.lang.Exception -> L43 java.lang.Throwable -> L5d
            r1.moveToNext()     // Catch: java.lang.Exception -> L43 java.lang.Throwable -> L5d
        L3d:
            if (r1 == 0) goto L42
            r1.close()
        L42:
            return r0
        L43:
            r0 = move-exception
            goto L4e
        L45:
            r1 = move-exception
            r10 = r1
            r1 = r0
            r0 = r10
            goto L5e
        L4a:
            r1 = move-exception
            r10 = r1
            r1 = r0
            r0 = r10
        L4e:
            com.felicanetworks.mfm.main.policy.log.LogUtil.warning(r0)     // Catch: java.lang.Throwable -> L5d
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException r2 = new com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException     // Catch: java.lang.Throwable -> L5d
            java.lang.Class r3 = r11.getClass()     // Catch: java.lang.Throwable -> L5d
            r4 = 577(0x241, float:8.09E-43)
            r2.<init>(r3, r4, r0)     // Catch: java.lang.Throwable -> L5d
            throw r2     // Catch: java.lang.Throwable -> L5d
        L5d:
            r0 = move-exception
        L5e:
            if (r1 == 0) goto L63
            r1.close()
        L63:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.getPolicyUpdateInfoItem():com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$TosVersionSettings");
    }

    public void updatePolicyUpdateInfoItem(DatabaseExpert.TosVersionSettings tosVersionSettings) throws DatabaseException {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("OfflineCheckInterval", Integer.valueOf(tosVersionSettings.interval));
            contentValues.put("LastCheckDate", tosVersionSettings.lastChkDate);
            contentValues.put("OfflineCheckActualCnt", Integer.valueOf(tosVersionSettings.chkCnt));
            contentValues.put("OfflineCheckMaxCnt", Integer.valueOf(tosVersionSettings.maxCnt));
            contentValues.put("PolicySiteVersion", Integer.valueOf(tosVersionSettings.version));
            this.db.update(TABLE_POLICYUPDATEINFO, contentValues, null, null);
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 593, e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x009d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.Banner> getBannerList(com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.BannerPos r22) throws java.lang.Throwable {
        /*
            r21 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            com.felicanetworks.mfm.main.policy.sg.Sg$Key r2 = com.felicanetworks.mfm.main.policy.sg.Sg.Key.SETTING_TIMEZONE     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L88
            java.lang.Object r2 = com.felicanetworks.mfm.main.policy.sg.Sg.getValue(r2)     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L88
            java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L88
            com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter r3 = new com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L88
            java.lang.String r4 = "yyyyMMddHHmm"
            r3.<init>(r4, r2)     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L88
            java.util.Date r2 = new java.util.Date     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L88
            r2.<init>()     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L88
            java.lang.String r2 = r3.format(r2)     // Catch: java.lang.Throwable -> L84 java.lang.Exception -> L88
            r3 = r21
            android.database.sqlite.SQLiteDatabase r4 = r3.db     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            java.lang.String r5 = "SELECT Kind, Id, Priority, BannerImg, Img, Ls, Start, End, Updated FROM BannerInfo WHERE BannerImg IS NOT NULL AND Kind = ? AND (Start <= ? AND ? <= End) ORDER BY Priority"
            r6 = 3
            java.lang.String[] r7 = new java.lang.String[r6]     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            java.lang.String r8 = r22.posId()     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r9 = 0
            r7[r9] = r8     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r8 = 1
            r7[r8] = r2     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r10 = 2
            r7[r10] = r2     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            android.database.Cursor r1 = r4.rawQuery(r5, r7)     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r1.moveToFirst()     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r2 = 0
        L3d:
            int r4 = r1.getCount()     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            if (r2 >= r4) goto L7c
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$Banner r4 = new com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$Banner     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            java.lang.String r12 = r1.getString(r9)     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            java.lang.String r13 = r1.getString(r8)     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            int r14 = r1.getInt(r10)     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            byte[] r15 = r1.getBlob(r6)     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r5 = 4
            java.lang.String r16 = r1.getString(r5)     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r5 = 5
            java.lang.String r17 = r1.getString(r5)     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r5 = 6
            java.lang.String r18 = r1.getString(r5)     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r5 = 7
            java.lang.String r19 = r1.getString(r5)     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r5 = 8
            java.lang.String r20 = r1.getString(r5)     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r11 = r4
            r11.<init>(r12, r13, r14, r15, r16, r17, r18, r19, r20)     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r0.add(r4)     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            r1.moveToNext()     // Catch: java.lang.Exception -> L82 java.lang.Throwable -> L9a
            int r2 = r2 + 1
            goto L3d
        L7c:
            if (r1 == 0) goto L81
            r1.close()
        L81:
            return r0
        L82:
            r0 = move-exception
            goto L8b
        L84:
            r0 = move-exception
            r3 = r21
            goto L9b
        L88:
            r0 = move-exception
            r3 = r21
        L8b:
            com.felicanetworks.mfm.main.policy.log.LogUtil.warning(r0)     // Catch: java.lang.Throwable -> L9a
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException r2 = new com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException     // Catch: java.lang.Throwable -> L9a
            java.lang.Class r4 = r21.getClass()     // Catch: java.lang.Throwable -> L9a
            r5 = 369(0x171, float:5.17E-43)
            r2.<init>(r4, r5, r0)     // Catch: java.lang.Throwable -> L9a
            throw r2     // Catch: java.lang.Throwable -> L9a
        L9a:
            r0 = move-exception
        L9b:
            if (r1 == 0) goto La0
            r1.close()
        La0:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.getBannerList(com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$BannerPos):java.util.List");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.BookmarkSettings getBookmarkSettings() throws java.lang.Throwable {
        /*
            r6 = this;
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r6.db     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L2c
            java.lang.String r2 = "SELECT BookmarkLastUpdatedDate FROM BookmarkSetting "
            android.database.Cursor r1 = r1.rawQuery(r2, r0)     // Catch: java.lang.Throwable -> L27 java.lang.Exception -> L2c
            r1.moveToFirst()     // Catch: java.lang.Exception -> L25 java.lang.Throwable -> L3f
            int r2 = r1.getCount()     // Catch: java.lang.Exception -> L25 java.lang.Throwable -> L3f
            if (r2 <= 0) goto L1f
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$BookmarkSettings r0 = new com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$BookmarkSettings     // Catch: java.lang.Exception -> L25 java.lang.Throwable -> L3f
            r2 = 0
            java.lang.String r2 = r1.getString(r2)     // Catch: java.lang.Exception -> L25 java.lang.Throwable -> L3f
            r0.<init>(r2)     // Catch: java.lang.Exception -> L25 java.lang.Throwable -> L3f
            r1.moveToNext()     // Catch: java.lang.Exception -> L25 java.lang.Throwable -> L3f
        L1f:
            if (r1 == 0) goto L24
            r1.close()
        L24:
            return r0
        L25:
            r0 = move-exception
            goto L30
        L27:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L40
        L2c:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
        L30:
            com.felicanetworks.mfm.main.policy.log.LogUtil.warning(r0)     // Catch: java.lang.Throwable -> L3f
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException r2 = new com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException     // Catch: java.lang.Throwable -> L3f
            java.lang.Class r3 = r6.getClass()     // Catch: java.lang.Throwable -> L3f
            r4 = 375(0x177, float:5.25E-43)
            r2.<init>(r3, r4, r0)     // Catch: java.lang.Throwable -> L3f
            throw r2     // Catch: java.lang.Throwable -> L3f
        L3f:
            r0 = move-exception
        L40:
            if (r1 == 0) goto L45
            r1.close()
        L45:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.getBookmarkSettings():com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$BookmarkSettings");
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0058  */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$BannerPos] */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v5, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.BannerSettings getBannerSettings(com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.BannerPos r8) throws java.lang.Throwable {
        /*
            r7 = this;
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r7.db     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L42
            java.lang.String r2 = "SELECT Kind, BannerInfoFileUpdated, BannerInfoLastUpdatedDate FROM BannerSetting WHERE Kind = ?"
            r3 = 1
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L42
            java.lang.String r8 = r8.posId()     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L42
            r5 = 0
            r4[r5] = r8     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L42
            android.database.Cursor r8 = r1.rawQuery(r2, r4)     // Catch: java.lang.Throwable -> L3d java.lang.Exception -> L42
            r8.moveToFirst()     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L55
            int r1 = r8.getCount()     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L55
            if (r1 <= 0) goto L35
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$BannerSettings r0 = new com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$BannerSettings     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L55
            java.lang.String r1 = r8.getString(r5)     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L55
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$BannerPos r1 = com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.BannerPos.resolvePos(r1)     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L55
            java.lang.String r2 = r8.getString(r3)     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L55
            r3 = 2
            java.lang.String r3 = r8.getString(r3)     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L55
            r0.<init>(r1, r2, r3)     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L55
            r8.moveToNext()     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L55
        L35:
            if (r8 == 0) goto L3a
            r8.close()
        L3a:
            return r0
        L3b:
            r0 = move-exception
            goto L46
        L3d:
            r8 = move-exception
            r6 = r0
            r0 = r8
            r8 = r6
            goto L56
        L42:
            r8 = move-exception
            r6 = r0
            r0 = r8
            r8 = r6
        L46:
            com.felicanetworks.mfm.main.policy.log.LogUtil.warning(r0)     // Catch: java.lang.Throwable -> L55
            com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException r1 = new com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException     // Catch: java.lang.Throwable -> L55
            java.lang.Class r2 = r7.getClass()     // Catch: java.lang.Throwable -> L55
            r3 = 385(0x181, float:5.4E-43)
            r1.<init>(r2, r3, r0)     // Catch: java.lang.Throwable -> L55
            throw r1     // Catch: java.lang.Throwable -> L55
        L55:
            r0 = move-exception
        L56:
            if (r8 == 0) goto L5b
            r8.close()
        L5b:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.getBannerSettings(com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$BannerPos):com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert$BannerSettings");
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
            throw new DatabaseException(getClass(), TypedValues.Cycle.TYPE_CURVE_FIT, e);
        }
    }

    public void deleteBannerTexts(List<DatabaseExpert.BannerText> list) throws DatabaseException {
        try {
            StringBuilder sb = new StringBuilder();
            if (list == null || list.size() <= 0) {
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                sb.append(String.format("'%s'", list.get(i).id));
                sb.append(",");
            }
            this.db.delete(TABLE_BANNERINFO, "Id not in(" + sb.substring(0, sb.length() - 1) + ") And Kind = " + list.get(0).bannerPos.ordinal(), null);
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 417, e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0187  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setBannerTexts(java.util.List<com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert.BannerText> r13) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 396
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.db.MfmDatabaseAccess.setBannerTexts(java.util.List):void");
    }

    public void setBannerImage(DatabaseExpert.BannerImage bannerImage) throws DatabaseException {
        if (bannerImage != null) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("BannerImg", bannerImage.imgBinary);
                contentValues.put("Updated", bannerImage.update);
                this.db.update(TABLE_BANNERINFO, contentValues, "Id = ? AND Kind = ?", new String[]{bannerImage.id, bannerImage.bannerPos.posId()});
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 449, e);
            }
        }
    }

    public boolean isExistService(String str) {
        try {
            Cursor cursorRawQuery = this.db.rawQuery(SELECT_SERVICEID_FROM_SERVICELIST_AND_BOOKMARKLIST_SQL, new String[]{str});
            try {
                cursorRawQuery.moveToFirst();
                z = cursorRawQuery.getCount() > 0;
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
            } finally {
            }
        } catch (Exception e) {
            LogUtil.warning(e);
        }
        return z;
    }

    public DatabaseExpert.FpSetting getFpSetting() throws DatabaseException {
        try {
            DatabaseExpert.FpSetting fpSetting = null;
            Cursor cursorRawQuery = this.db.rawQuery(SELECT_FROM_FPSETTING_SQL, null);
            try {
                cursorRawQuery.moveToFirst();
                if (cursorRawQuery.getCount() > 0) {
                    fpSetting = new DatabaseExpert.FpSetting(cursorRawQuery.getString(0));
                    cursorRawQuery.moveToNext();
                }
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return fpSetting;
            } finally {
            }
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 465, e);
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

    void deleteMfiCardAdditionalInfo(String str) {
        this.db.delete(TABLE_CARDADDITIONALINFO, "CardId=?", new String[]{str});
        this.db.delete(TransitInformationListTable.TABLE_NAME, String.format("%s=?", TransitInformationListTable.Column.CARD_ID.title()), new String[]{str});
        this.db.delete(OptionalInformationListTable.TABLE_NAME, String.format("%s=?", OptionalInformationListTable.Column.CARD_ID.title()), new String[]{str});
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

    private void insertTransitInfo(String str, List<TransitPassInfo> list) throws DatabaseException {
        if (str == null || list == null) {
            return;
        }
        int i = 0;
        for (TransitPassInfo transitPassInfo : list) {
            String str2 = str + i;
            TransitInformationListTable.RecordBuilder commuterPassName = new TransitInformationListTable.RecordBuilder().setCardId(str).setRecordNumber(i).setTransitPassName(transitPassInfo.getTransitPassName()).setCategory(transitPassInfo.getCategory()).setCommuterPassName(transitPassInfo.getCommuterPassName());
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
                insertOptionalInfo(str, str2, transitPassInfo.getOptionalInfoList());
                this.db.insertOrThrow(TransitInformationListTable.TABLE_NAME, null, commuterPassName.build());
                i++;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 4083, e);
            }
        }
    }

    private void insertOptionalInfo(String str, String str2, List<OptionalInfo> list) throws DatabaseException {
        if (str == null || str2 == null || list == null) {
            return;
        }
        for (OptionalInfo optionalInfo : list) {
            try {
                this.db.insertOrThrow(OptionalInformationListTable.TABLE_NAME, null, new OptionalInformationListTable.RecordBuilder().setCardId(str).setTransitId(str2).setKey(optionalInfo.getOptionalInfoKey()).setValue(optionalInfo.getOptionalInfoValue()).build());
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 4084, e);
            }
        }
    }

    public List<MfiCardAdditionalInfo> getMfiCardAdditionalInfo() throws Throwable {
        Cursor cursor;
        Throwable th;
        Cursor cursor2;
        Throwable th2;
        Cursor cursor3;
        Throwable th3;
        MfmDatabaseAccess mfmDatabaseAccess = this;
        ArrayList arrayList = new ArrayList();
        ArrayList<Cursor> arrayList2 = new ArrayList();
        ArrayList<Cursor> arrayList3 = new ArrayList();
        try {
        } catch (Throwable th4) {
            th = th4;
        }
        try {
            try {
                Cursor cursorRawQuery = mfmDatabaseAccess.db.rawQuery(SELECT_FROM_CARDADDITIONALINFO_SQL, null);
                try {
                    try {
                        Cursor cursorRawQuery2 = mfmDatabaseAccess.db.rawQuery(SELEC_FROM_CARDADDITIONALINFO_GENERALINFO_SQL, null);
                        try {
                            try {
                                Cursor cursorRawQuery3 = mfmDatabaseAccess.db.rawQuery(SELEC_FROM_CARDADDITIONALINFO_CONTACTINFO_SQL, null);
                                try {
                                    int i = 0;
                                    for (EnumSelectFromCardAdditionalInfoLinkageInfoSql enumSelectFromCardAdditionalInfoLinkageInfoSql : EnumSelectFromCardAdditionalInfoLinkageInfoSql.values()) {
                                        try {
                                            arrayList2.add(mfmDatabaseAccess.db.rawQuery(enumSelectFromCardAdditionalInfoLinkageInfoSql.getSql(), null));
                                        } catch (Throwable th5) {
                                            th3 = th5;
                                            cursor = cursorRawQuery;
                                            cursor2 = cursorRawQuery2;
                                            cursor3 = cursorRawQuery3;
                                            try {
                                                throw th3;
                                            } finally {
                                                th2 = th;
                                                try {
                                                    throw th2;
                                                } finally {
                                                    if (cursor != null) {
                                                        try {
                                                            cursor.close();
                                                            throw th;
                                                        } catch (Throwable th6) {
                                                            th2.addSuppressed(th6);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    for (EnumSelectFromCardAdditionalInfoLinkageAppInfoSql enumSelectFromCardAdditionalInfoLinkageAppInfoSql : EnumSelectFromCardAdditionalInfoLinkageAppInfoSql.values()) {
                                        arrayList3.add(mfmDatabaseAccess.db.rawQuery(enumSelectFromCardAdditionalInfoLinkageAppInfoSql.getSql(), null));
                                    }
                                    cursorRawQuery.moveToFirst();
                                    cursorRawQuery2.moveToFirst();
                                    cursorRawQuery3.moveToFirst();
                                    for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                                        ((Cursor) arrayList2.get(i2)).moveToFirst();
                                        ((Cursor) arrayList3.get(i2)).moveToFirst();
                                    }
                                    int i3 = 0;
                                    while (i3 < cursorRawQuery.getCount()) {
                                        String string = cursorRawQuery.getString(i);
                                        String string2 = cursorRawQuery.getString(1);
                                        int i4 = 3;
                                        int i5 = i3;
                                        CommonInfo commonInfo = new CommonInfo(new GeneralInfo(cursorRawQuery2.getString(i), cursorRawQuery2.getString(1), cursorRawQuery2.getString(2)), new ContactInfo(cursorRawQuery3.getString(i), cursorRawQuery3.getString(1), cursorRawQuery3.getString(2), cursorRawQuery3.getString(3)));
                                        ArrayList arrayList4 = new ArrayList();
                                        int i6 = 0;
                                        while (i6 < arrayList2.size()) {
                                            try {
                                                if (i6 >= arrayList3.size() || ((Cursor) arrayList3.get(i6)).getCount() <= 0) {
                                                    cursor2 = cursorRawQuery2;
                                                    cursor3 = cursorRawQuery3;
                                                } else {
                                                    cursor3 = cursorRawQuery3;
                                                    try {
                                                        cursor2 = cursorRawQuery2;
                                                    } catch (Throwable th7) {
                                                        th = th7;
                                                        cursor2 = cursorRawQuery2;
                                                        th3 = th;
                                                        cursor = cursorRawQuery;
                                                        throw th3;
                                                    }
                                                    try {
                                                        arrayList4.add(new LinkageInfo(((Cursor) arrayList2.get(i6)).getString(0).equals("2") ? LinkageInfo.LinkageKind.APP : LinkageInfo.LinkageKind.WEB, ((Cursor) arrayList2.get(i6)).getString(1), new LinkageApplicationInfo(((Cursor) arrayList3.get(i6)).getString(0), ((Cursor) arrayList3.get(i6)).getString(1), ((Cursor) arrayList3.get(i6)).getString(2), ((Cursor) arrayList3.get(i6)).getString(i4), ((Cursor) arrayList3.get(i6)).getString(4)), ((Cursor) arrayList2.get(i6)).getString(2)));
                                                    } catch (Throwable th8) {
                                                        th = th8;
                                                        th3 = th;
                                                        cursor = cursorRawQuery;
                                                        throw th3;
                                                    }
                                                }
                                                ((Cursor) arrayList2.get(i6)).moveToNext();
                                                ((Cursor) arrayList3.get(i6)).moveToNext();
                                                i6++;
                                                cursorRawQuery3 = cursor3;
                                                cursorRawQuery2 = cursor2;
                                                i4 = 3;
                                            } catch (Throwable th9) {
                                                th = th9;
                                                cursor2 = cursorRawQuery2;
                                                cursor3 = cursorRawQuery3;
                                            }
                                        }
                                        cursor2 = cursorRawQuery2;
                                        cursor3 = cursorRawQuery3;
                                        try {
                                            TransitInfo.CardNumber cardNumber = new TransitInfo.CardNumber(cursorRawQuery.getString(2), cursorRawQuery.getString(3));
                                            Cursor cursorRawQuery4 = mfmDatabaseAccess.db.rawQuery(TransitInformationListTable.Statement.SELECT_WITH_CARD_ID, new String[]{string});
                                            cursorRawQuery4.moveToFirst();
                                            ArrayList arrayList5 = new ArrayList();
                                            while (!cursorRawQuery4.isAfterLast()) {
                                                Cursor cursorRawQuery5 = mfmDatabaseAccess.db.rawQuery(OptionalInformationListTable.Statement.SELECT_WITH_TRANSIT_ID, new String[]{TransitInformationListTable.Selector.findTransitId(cursorRawQuery4)});
                                                ArrayList arrayList6 = new ArrayList();
                                                cursorRawQuery5.moveToFirst();
                                                while (!cursorRawQuery5.isAfterLast()) {
                                                    arrayList6.add(new OptionalInfo(OptionalInformationListTable.Selector.findKey(cursorRawQuery5), OptionalInformationListTable.Selector.findValue(cursorRawQuery5)));
                                                    cursorRawQuery5.moveToNext();
                                                }
                                                cursorRawQuery5.close();
                                                ArrayList arrayList7 = arrayList3;
                                                try {
                                                    ArrayList arrayList8 = arrayList2;
                                                    try {
                                                        cursor = cursorRawQuery;
                                                        try {
                                                            arrayList5.add(new TransitPassInfo(TransitInformationListTable.Selector.findTransitPassName(cursorRawQuery4), TransitInformationListTable.Selector.findCategory(cursorRawQuery4), TransitInformationListTable.Selector.findCommuterPassName(cursorRawQuery4), new Section1(TransitInformationListTable.Selector.findFrom1(cursorRawQuery4), TransitInformationListTable.Selector.findTo1(cursorRawQuery4)), new Section2(TransitInformationListTable.Selector.findFrom2(cursorRawQuery4), TransitInformationListTable.Selector.findTo2(cursorRawQuery4)), TransitInformationListTable.Selector.findAdditionalInformation(cursorRawQuery4), TransitInformationListTable.Selector.findVia(cursorRawQuery4), TransitInformationListTable.Selector.findIssuerName(cursorRawQuery4), new TermOfValidity(TransitInformationListTable.Selector.findKey(cursorRawQuery4), TransitInformationListTable.Selector.findStart(cursorRawQuery4), TransitInformationListTable.Selector.findEnd(cursorRawQuery4), TransitInformationListTable.Selector.findExtension(cursorRawQuery4).booleanValue()), arrayList6));
                                                            cursorRawQuery4.moveToNext();
                                                            mfmDatabaseAccess = this;
                                                            arrayList3 = arrayList7;
                                                            arrayList2 = arrayList8;
                                                            cursorRawQuery = cursor;
                                                            arrayList = arrayList;
                                                        } catch (Throwable th10) {
                                                            th = th10;
                                                            th3 = th;
                                                            throw th3;
                                                        }
                                                    } catch (Throwable th11) {
                                                        th = th11;
                                                        cursor = cursorRawQuery;
                                                        th3 = th;
                                                        throw th3;
                                                    }
                                                } catch (Throwable th12) {
                                                    th = th12;
                                                }
                                            }
                                            ArrayList arrayList9 = arrayList2;
                                            ArrayList arrayList10 = arrayList3;
                                            Cursor cursor4 = cursorRawQuery;
                                            cursorRawQuery4.close();
                                            arrayList = arrayList;
                                            arrayList.add(new MfiCardAdditionalInfo(string, string2, arrayList4, commonInfo, new TransitInfo(cardNumber, arrayList5)));
                                            cursor4.moveToNext();
                                            cursor2.moveToNext();
                                            cursor3.moveToNext();
                                            i3 = i5 + 1;
                                            mfmDatabaseAccess = this;
                                            arrayList3 = arrayList10;
                                            arrayList2 = arrayList9;
                                            cursorRawQuery3 = cursor3;
                                            cursorRawQuery2 = cursor2;
                                            cursorRawQuery = cursor4;
                                            i = 0;
                                        } catch (Throwable th13) {
                                            th = th13;
                                        }
                                    }
                                    ArrayList<Cursor> arrayList11 = arrayList2;
                                    ArrayList<Cursor> arrayList12 = arrayList3;
                                    Cursor cursor5 = cursorRawQuery;
                                    Cursor cursor6 = cursorRawQuery2;
                                    Cursor cursor7 = cursorRawQuery3;
                                    if (cursor7 != null) {
                                        cursor7.close();
                                    }
                                    if (cursor6 != null) {
                                        cursor6.close();
                                    }
                                    if (cursor5 != null) {
                                        cursor5.close();
                                    }
                                    for (Cursor cursor8 : arrayList11) {
                                        if (cursor8 != null) {
                                            cursor8.close();
                                        }
                                    }
                                    for (Cursor cursor9 : arrayList12) {
                                        if (cursor9 != null) {
                                            cursor9.close();
                                        }
                                    }
                                    return arrayList;
                                } catch (Throwable th14) {
                                    th = th14;
                                    cursor = cursorRawQuery;
                                    cursor2 = cursorRawQuery2;
                                    cursor3 = cursorRawQuery3;
                                }
                            } catch (Throwable th15) {
                                th = th15;
                                th2 = th;
                                throw th2;
                            }
                        } catch (Throwable th16) {
                            th = th16;
                            cursor = cursorRawQuery;
                            cursor2 = cursorRawQuery2;
                            th2 = th;
                            throw th2;
                        }
                    } catch (Throwable th17) {
                        th = th17;
                        th = th;
                        try {
                            throw th;
                        } finally {
                            if (cursor != null) {
                                try {
                                    cursor.close();
                                    throw th;
                                } catch (Throwable th18) {
                                    th.addSuppressed(th18);
                                }
                            }
                        }
                    }
                } catch (Throwable th19) {
                    th = th19;
                    cursor = cursorRawQuery;
                    th = th;
                    throw th;
                }
            } catch (Exception e) {
                e = e;
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 512, e);
            }
        } catch (Exception e2) {
            e = e2;
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 512, e);
        } catch (Throwable th20) {
            th = th20;
            for (Cursor cursor10 : arrayList2) {
                if (cursor10 != null) {
                    cursor10.close();
                }
            }
            for (Cursor cursor11 : arrayList3) {
                if (cursor11 != null) {
                    cursor11.close();
                }
            }
            throw th;
        }
    }

    public List<DatabaseExpert.MfiHashValueInfo> getMfiHashValue(List<String> list) throws DatabaseException {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                String[] strArr = {"CardId", "CardAdditionalInfoHashValue"};
                String[] strArr2 = new String[list.size()];
                String strConcat = "CardId =  ?";
                for (int i = 0; i < list.size(); i++) {
                    strArr2[i] = list.get(i);
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
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 824, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public void deleteCardFaceImage() {
        cleanCardFaceImage();
    }

    private void cleanCardFaceImage() {
        this.db.delete(TABLE_CARDFACEINFO, null, null);
    }

    public DatabaseExpert.CardFaceImageInfo getCardFaceImage(String str) throws DatabaseException {
        try {
            Cursor cursorRawQuery = this.db.rawQuery(SELECT_FROM_CARDFACEIMAGE_IDENTIFIER_RES_SQL, new String[]{str});
            try {
                cursorRawQuery.moveToFirst();
                if (cursorRawQuery.getCount() > 0) {
                    DatabaseExpert.CardFaceImageInfo cardFaceImageInfo = new DatabaseExpert.CardFaceImageInfo(cursorRawQuery.getString(cursorRawQuery.getColumnIndex("CardFaceIdentifier")), cursorRawQuery.getString(cursorRawQuery.getColumnIndex("CardFaceImageUrl")), cursorRawQuery.getBlob(cursorRawQuery.getColumnIndex("CardFaceImage")));
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    return cardFaceImageInfo;
                }
                if (cursorRawQuery == null) {
                    return null;
                }
                cursorRawQuery.close();
                return null;
            } finally {
            }
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 561, e);
        }
        LogUtil.warning(e);
        throw new DatabaseException(getClass(), 561, e);
    }

    public void updateCardFaceImage(String str, String str2, byte[] bArr) throws DatabaseException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || bArr == null) {
            return;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("CardFaceIdentifier", str);
            contentValues.put("CardFaceImageUrl", str2);
            contentValues.put("CardFaceImage", bArr);
            this.db.update(TABLE_CARDFACEINFO, contentValues, "CardFaceIdentifier = ?", new String[]{str});
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 562, e);
        }
    }

    public void addCardFaceImage(String str, String str2, byte[] bArr) throws DatabaseException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || bArr == null) {
            return;
        }
        try {
            SQLiteStatement sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_CARDFACEIMAGE_TABLE);
            try {
                sQLiteStatementCompileStatement.bindString(1, str);
                sQLiteStatementCompileStatement.bindString(2, str2);
                sQLiteStatementCompileStatement.bindBlob(3, bArr);
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

    public Map<String, Object> getSpecificColumnData(String str, List<String> list) throws DatabaseException {
        HashMap map = new HashMap();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_SERVICELIST, (String[]) list.toArray(new String[0]), "ServiceID = ?", new String[]{str}, null, null, null, "1");
                cursorQuery.moveToFirst();
                for (int i = 0; i < cursorQuery.getColumnCount(); i++) {
                    String columnName = cursorQuery.getColumnName(i);
                    if ("IconData".equals(columnName) || "IconData1".equals(columnName) || "IconData2".equals(columnName) || "CardFaceImage".equals(columnName)) {
                        map.put(cursorQuery.getColumnName(i), cursorQuery.getBlob(i));
                    } else {
                        map.put(cursorQuery.getColumnName(i), cursorQuery.getString(i));
                    }
                }
                return map;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseException(getClass(), 225, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public void updateSpecificColumnData(String str, Map<String, Object> map) throws DatabaseException {
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
            this.db.update(TABLE_SERVICELIST, contentValues, "ServiceID = ?", new String[]{str});
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseException(getClass(), 658, e);
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

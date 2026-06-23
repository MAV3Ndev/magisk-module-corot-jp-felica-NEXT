package com.felicanetworks.mfm.main.model.internal.main.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmlib.MfmAppContext;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.db.table.OptionalInformationListTable;
import com.felicanetworks.mfm.main.model.internal.main.db.table.SortCardInfoTable;
import com.felicanetworks.mfm.main.model.internal.main.db.table.TransitInformationListTable;
import com.felicanetworks.mfm.main.policy.sg.Sg;

/* JADX INFO: loaded from: classes.dex */
public class MfmDatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_APPLIST = "CREATE TABLE AppList(PackageName TEXT NOT NULL, ServiceID TEXT NOT NULL, ServiceVersion TEXT NOT NULL, HashValue TEXT NOT NULL)";
    private static final String CREATE_AREALIST = "CREATE TABLE AreaList(SystemCode TEXT NOT NULL, AreaCode TEXT NOT NULL, ServiceID TEXT NOT NULL, ServiceVersion TEXT NOT NULL, CacheFlag TEXT NOT NULL)";
    private static final String CREATE_BANNERINFO = "CREATE TABLE BannerInfo( Id TEXT NOT NULL, Kind TEXT NOT NULL, Priority TEXT NOT NULL, BannerImg BLOB, Img TEXT NOT NULL, Ls TEXT NOT NULL, Start TEXT NOT NULL, End TEXT NOT NULL, Updated TEXT, CONSTRAINT id_pky PRIMARY KEY (id, Kind))";
    private static final String CREATE_BANNERINFO_INDEX = "CREATE INDEX Banner_idx ON BannerInfo(Id)";
    private static final String CREATE_BANNER_SETTING = "CREATE TABLE BannerSetting( Kind TEXT NOT NULL, BannerInfoFileUpdated TEXT NOT NULL, BannerInfoLastUpdatedDate TEXT NOT NULL) ";
    private static final String CREATE_BOOKMARKLIST = "CREATE TABLE BookmarkList( CategoryID TEXT NOT NULL, CategoryName TEXT NOT NULL, ServiceID TEXT NOT NULL, ServiceVersion TEXT NOT NULL, ServiceName TEXT NOT NULL, ServiceProviderName TEXT NOT NULL, StatusKind TEXT NOT NULL, CooperativeKind TEXT NOT NULL, ApplicationKind TEXT NOT NULL, ApplicationUrl TEXT NOT NULL, WebUrl TEXT NOT NULL, Summary TEXT NOT NULL, Detail TEXT NOT NULL, Procedure1 TEXT, Procedure2 TEXT, Procedure3 TEXT, Procedure4 TEXT, Procedure5 TEXT, DisplayPriority INTEGER NOT NULL, CONSTRAINT disp_pky PRIMARY KEY (DisplayPriority))";
    private static final String CREATE_BOOKMARKLIST_INDEX = "CREATE INDEX ServiceID_BL_idx ON BookmarkList(ServiceID)";
    private static final String CREATE_BOOKMARK_SETTING = "CREATE TABLE BookmarkSetting( BookmarkLastUpdatedDate TEXT NOT NULL) ";
    private static final String CREATE_CARD_ADDITIONAL_INFO = "CREATE TABLE CardAdditionalInfo( CardId TEXT NOT NULL, CardAdditionalInfoHashValue TEXT NOT NULL, LanguageCode TEXT NOT NULL, LinkageKind1 TEXT NOT NULL, LinkageName1 TEXT NOT NULL, AppIconUrl1 TEXT NOT NULL, AppIdentifiableInfo1 TEXT NOT NULL, AppCallerInfo1 TEXT NOT NULL, AppGetKind1 TEXT NOT NULL, AppGetUrl1 TEXT NOT NULL, LinkageWebsiteUrl1 TEXT NOT NULL, LinkageKind2 TEXT NOT NULL, LinkageName2 TEXT NOT NULL, AppIconUrl2 TEXT NOT NULL, AppIdentifiableInfo2 TEXT NOT NULL, AppCallerInfo2 TEXT NOT NULL, AppGetKind2 TEXT NOT NULL, AppGetUrl2 TEXT NOT NULL, LinkageWebsiteUrl2 TEXT NOT NULL, LinkageKind3 TEXT NOT NULL, LinkageName3 TEXT NOT NULL, AppIconUrl3 TEXT NOT NULL, AppIdentifiableInfo3 TEXT NOT NULL, AppCallerInfo3 TEXT NOT NULL, AppGetKind3 TEXT NOT NULL, AppGetUrl3 TEXT NOT NULL, LinkageWebsiteUrl3 TEXT NOT NULL, LinkageKind4 TEXT NOT NULL, LinkageName4 TEXT NOT NULL, AppIconUrl4 TEXT NOT NULL, AppIdentifiableInfo4 TEXT NOT NULL, AppCallerInfo4 TEXT NOT NULL, AppGetKind4 TEXT NOT NULL, AppGetUrl4 TEXT NOT NULL, LinkageWebsiteUrl4 TEXT NOT NULL, LinkageKind5 TEXT NOT NULL, LinkageName5 TEXT NOT NULL, AppIconUrl5 TEXT NOT NULL, AppIdentifiableInfo5 TEXT NOT NULL, AppCallerInfo5 TEXT NOT NULL, AppGetKind5 TEXT NOT NULL, AppGetUrl5 TEXT NOT NULL, LinkageWebsiteUrl5 TEXT NOT NULL, LinkageKind6 TEXT NOT NULL, LinkageName6 TEXT NOT NULL, AppIconUrl6 TEXT NOT NULL, AppIdentifiableInfo6 TEXT NOT NULL, AppCallerInfo6 TEXT NOT NULL, AppGetKind6 TEXT NOT NULL, AppGetUrl6 TEXT NOT NULL, LinkageWebsiteUrl6 TEXT NOT NULL, LinkageKind7 TEXT NOT NULL, LinkageName7 TEXT NOT NULL, AppIconUrl7 TEXT NOT NULL, AppIdentifiableInfo7 TEXT NOT NULL, AppCallerInfo7 TEXT NOT NULL, AppGetKind7 TEXT NOT NULL, AppGetUrl7 TEXT NOT NULL, LinkageWebsiteUrl7 TEXT NOT NULL, LinkageKind8 TEXT NOT NULL, LinkageName8 TEXT NOT NULL, AppIconUrl8 TEXT NOT NULL, AppIdentifiableInfo8 TEXT NOT NULL, AppCallerInfo8 TEXT NOT NULL, AppGetKind8 TEXT NOT NULL, AppGetUrl8 TEXT NOT NULL, LinkageWebsiteUrl8 TEXT NOT NULL, LinkageKind9 TEXT NOT NULL, LinkageName9 TEXT NOT NULL, AppIconUrl9 TEXT NOT NULL, AppIdentifiableInfo9 TEXT NOT NULL, AppCallerInfo9 TEXT NOT NULL, AppGetKind9 TEXT NOT NULL, AppGetUrl9 TEXT NOT NULL, LinkageWebsiteUrl9 TEXT NOT NULL, LinkageKind10 TEXT NOT NULL, LinkageName10 TEXT NOT NULL, AppIconUrl10 TEXT NOT NULL, AppIdentifiableInfo10 TEXT NOT NULL, AppCallerInfo10 TEXT NOT NULL, AppGetKind10 TEXT NOT NULL, AppGetUrl10 TEXT NOT NULL, LinkageWebsiteUrl10 TEXT NOT NULL, CardTypeName TEXT NOT NULL, CardArtUrl TEXT NOT NULL, ContactName TEXT NOT NULL, Name TEXT NOT NULL, ContactPhoneNumber TEXT NOT NULL, ContactURL TEXT NOT NULL, ContactEmail TEXT NOT NULL, DisplayName TEXT NOT NULL,Value Text NOT NULL,CONSTRAINT cardid_pky PRIMARY KEY (CardId))";
    private static final String CREATE_CARD_ADDITIONAL_SETTING = "CREATE TABLE CardAdditionalSetting( CaLastUpdatedDate TEXT NOT NULL) ";
    private static final String CREATE_CARD_FACE_INFO = "CREATE TABLE CardFaceInfo( CardFaceIdentifier TEXT NOT NULL, CardFaceImageUrl TEXT NOT NULL, CardFaceImage BLOB, CONSTRAINT identifier_pky PRIMARY KEY (CardFaceIdentifier, CardFaceImageUrl))";
    private static final String CREATE_FPSERVICELIST = "CREATE TABLE FpServiceList( FpServiceNumber TEXT NOT NULL, FpServiceName TEXT NOT NULL, FpServiceIconUrl TEXT NOT NULL, FpServiceProviderName TEXT NOT NULL, ServiceEnable Integer NOT NULL, WebUrl TEXT NOT NULL, FpServiceKind TEXT NOT NULL, NeedShowValue Integer NOT NULL DEFAULT 0, PointUnitName TEXT NOT NULL, CONSTRAINT sevicenumber_pky PRIMARY KEY (FpServiceNumber))";
    private static final String CREATE_FPSETTING = "CREATE TABLE FpSetting( FpLastUpdatedDate TEXT NOT NULL) ";
    private static final String CREATE_MULTIPURPOSELIST = "CREATE TABLE MultipurposeList(MultipurposeServiceKind TEXT NOT NULL, MultipurposeIdentifierCode TEXT NOT NULL, ServiceID TEXT NOT NULL, ServiceVersion TEXT NOT NULL, CacheFlag TEXT NOT NULL)";
    private static final String CREATE_POLICY_UPDATEINFO = "CREATE TABLE PolicyUpdateInfo(OfflineCheckInterval INTEGER NOT NULL DEFAULT 0, LastCheckDate TEXT NOT NULL, OfflineCheckActualCnt INTEGER NOT NULL DEFAULT 0, OfflineCheckMaxCnt INTEGER NOT NULL DEFAULT 0, PolicySiteVersion INTEGER NOT NULL DEFAULT 0)";
    private static final String CREATE_SERVICELIST = "CREATE TABLE ServiceList( ServiceID TEXT NOT NULL, ServiceVersion TEXT NOT NULL, ServiceName TEXT NOT NULL, ServiceProviderName TEXT NOT NULL, CooperativeKind TEXT NOT NULL, PackageName TEXT NOT NULL, HashValue TEXT NOT NULL, ApplicationKind TEXT NOT NULL, ApplicationUrl TEXT NOT NULL, WebUrl TEXT NOT NULL, IconData BLOB NOT NULL,DisplayPriority INTEGER NOT NULL DEFAULT 0, ServiceColor NOT NULL, IconData1 BLOB, IconData2 BLOB, ServiceType TEXT NOT NULL, CardCategory TEXT NOT NULL, DetailImagePresence TEXT NOT NULL, CardFaceImage BLOB, ContactName TEXT NOT NULL, ContactPhone TEXT NOT NULL, ContactUrl TEXT NOT NULL, ContactMail TEXT NOT NULL, CONSTRAINT service_pky PRIMARY KEY (ServiceID))";
    private static final String CREATE_SERVICELIST_INDEX = "CREATE INDEX ServiceIV_idx ON ServiceList(ServiceID, ServiceVersion)";
    private static final String CREATE_SERVICE_LINKAGEAPPLIST = "CREATE TABLE ServiceLinkageAppList( ServiceID TEXT NOT NULL, LinkageApp_PackageName TEXT NOT NULL,LinkageApp_Hash TEXT NOT NULL, LinkageApp_GetType TEXT NOT NULL, LinkageApp_GetUrl TEXT NOT NULL, LinkageApp_Icon BLOB NOT NULL, LinkageApp_AppName TEXT NOT NULL)";
    private static final String CREATE_SERVICE_LINKAGEWEBLIST = "CREATE TABLE ServiceLinkageWebList( ServiceID TEXT NOT NULL,LinkageWeb_Url TEXT NOT NULL,LinkageWeb_Name TEXT NOT NULL)";
    private static final String CREATE_SETTING = "CREATE TABLE Setting(UpdateInterval INTEGER NOT NULL DEFAULT 0, LastUpdateDate TEXT NOT NULL)";
    private static final String CREATE_UPDATEINFO = "CREATE TABLE UpdateInfo(OfflineCheckInterval INTEGER NOT NULL DEFAULT 0, LastCheckDate TEXT NOT NULL, OfflineCheckActualCnt INTEGER NOT NULL DEFAULT 0, OfflineCheckMaxCnt INTEGER NOT NULL DEFAULT 0)";
    private static final String DATABASE_NAME = "mfm.db";
    private static final int DATABASE_VERSION = 12;
    private static final String DROP_TABLE_APPLIST = "DROP TABLE IF EXISTS AppList";
    private static final String DROP_TABLE_AREALIST = "DROP TABLE IF EXISTS AreaList";
    private static final String DROP_TABLE_BOOKMARKLIST = "DROP TABLE IF EXISTS BookmarkList";
    private static final String DROP_TABLE_BOOKMARK_SETTING = "DROP TABLE IF EXISTS BookmarkSetting";
    private static final String DROP_TABLE_CARD_ADDITIONAL_INFO = "DROP TABLE IF EXISTS CardAdditionalInfo";
    private static final String DROP_TABLE_CARD_ADDITIONAL_SETTING = "DROP TABLE IF EXISTS CardAdditionalSetting";
    private static final String DROP_TABLE_MULTIPURPOSELIST = "DROP TABLE IF EXISTS MultipurposeList";
    private static final String DROP_TABLE_SERVICELIST = "DROP TABLE IF EXISTS ServiceList";
    private static final String DROP_TABLE_SERVICE_ADDITIONAL_INFO = "DROP TABLE IF EXISTS ServiceAdditionalInfo";
    private static final String DROP_TABLE_SETTING = "DROP TABLE IF EXISTS Setting";
    private static final String DROP_TABLE_UPDATEINFO = "DROP TABLE IF EXISTS UpdateInfo";
    private static final String DROP_USELESS_TABLE_BOOKMARKICON = "DROP TABLE IF EXISTS BookmarkIcon";
    private static final String DROP_USELESS_TABLE_DBCOMPLETE = "DROP TABLE IF EXISTS DbComplete";
    private static final String DROP_USELESS_TABLE_VIEWSERVICELIST = "DROP TABLE IF EXISTS ViewServiceList";
    private static final String DROP_USELESS_VIEW_ICON = "DROP VIEW IF EXISTS IconView";
    private static final String INITILAL_DATE_DATA = "000000000000";
    private static final String INITILAL_DAY_DATA = "00000000";
    private static final int INITILAL_ZERO_DATA = 0;
    private static final String INSERT_BANNER_SETTING = "INSERT INTO BannerSetting VALUES ( ?, ?, ? )";
    private static final String INSERT_BOOKMARK_SETTING = "INSERT INTO BookmarkSetting VALUES ( ? )";
    private static final String INSERT_FP_SETTING = "INSERT INTO FpSetting VALUES ( ? )";
    private static final String INSERT_POLICY_UPDATEINFO_TABLE = "INSERT INTO PolicyUpdateInfo VALUES ( ?, ?, ?, ?, ? )";
    private static final String INSERT_SETTING_TABLE = "INSERT INTO Setting VALUES ( ?, ? )";
    private static final String INSERT_UPDATEINFO_TABLE = "INSERT INTO UpdateInfo VALUES ( ?, ?, ?, ? )";
    private static final String TABLE_SETTING = "Setting";
    private static volatile SQLiteDatabase db;
    private static volatile MfmDatabaseHelper dbHelper;
    private static final String COLUMN_UPDATEINTERVAL = "UpdateInterval";
    private static final String COLUMN_LASTUPDATEDATE = "LastUpdateDate";
    private static final String[] COLUMNS_SETTING = {COLUMN_UPDATEINTERVAL, COLUMN_LASTUPDATEDATE};

    private void upgradeFromV10ToV11(SQLiteDatabase sQLiteDatabase) {
    }

    static synchronized MfmDatabaseHelper getInstance(MfmAppContext mfmAppContext) throws DatabaseException {
        if (dbHelper == null) {
            dbHelper = new MfmDatabaseHelper(mfmAppContext);
            db = dbHelper.getWritableDatabase();
            if (!isRecoveryCheck()) {
                if (db != null) {
                    db.close();
                    db = null;
                }
                if (!mfmAppContext.androidContext.deleteDatabase(DATABASE_NAME)) {
                    throw new DatabaseException(MfmDatabaseHelper.class, 257, "MfmDatabase deletion failure");
                }
                db = dbHelper.getWritableDatabase();
            }
        }
        return dbHelper;
    }

    private MfmDatabaseHelper(MfmAppContext mfmAppContext) {
        super(mfmAppContext.androidContext, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 12);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_AREALIST);
        sQLiteDatabase.execSQL(CREATE_MULTIPURPOSELIST);
        sQLiteDatabase.execSQL(CREATE_APPLIST);
        sQLiteDatabase.execSQL(CREATE_SERVICELIST);
        sQLiteDatabase.execSQL(CREATE_SETTING);
        sQLiteDatabase.execSQL(CREATE_UPDATEINFO);
        sQLiteDatabase.execSQL(CREATE_BOOKMARKLIST);
        sQLiteDatabase.execSQL(CREATE_BANNER_SETTING);
        sQLiteDatabase.execSQL(CREATE_BANNERINFO);
        sQLiteDatabase.execSQL(CREATE_BOOKMARK_SETTING);
        sQLiteDatabase.execSQL(CREATE_FPSERVICELIST);
        sQLiteDatabase.execSQL(CREATE_FPSETTING);
        sQLiteDatabase.execSQL(CREATE_CARD_ADDITIONAL_INFO);
        sQLiteDatabase.execSQL(CREATE_CARD_FACE_INFO);
        sQLiteDatabase.execSQL(CREATE_POLICY_UPDATEINFO);
        sQLiteDatabase.execSQL(TransitInformationListTable.Statement.CREATE);
        sQLiteDatabase.execSQL(OptionalInformationListTable.Statement.CREATE);
        sQLiteDatabase.execSQL(CREATE_SERVICE_LINKAGEAPPLIST);
        sQLiteDatabase.execSQL(CREATE_SERVICE_LINKAGEWEBLIST);
        sQLiteDatabase.execSQL(CREATE_SERVICELIST_INDEX);
        sQLiteDatabase.execSQL(CREATE_BOOKMARKLIST_INDEX);
        sQLiteDatabase.execSQL(CREATE_BANNERINFO_INDEX);
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            String str = (String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_INTERVAL);
            String str2 = (String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_FACT_COUNT);
            String str3 = (String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_MAX_COUNT);
            SQLiteStatement sQLiteStatementCompileStatement2 = sQLiteDatabase.compileStatement(INSERT_UPDATEINFO_TABLE);
            sQLiteStatementCompileStatement2.bindLong(1, Integer.parseInt(str));
            sQLiteStatementCompileStatement2.bindString(2, INITILAL_DATE_DATA);
            sQLiteStatementCompileStatement2.bindLong(3, Integer.parseInt(str2));
            sQLiteStatementCompileStatement2.bindLong(4, Integer.parseInt(str3));
            sQLiteStatementCompileStatement2.executeInsert();
            SQLiteStatement sQLiteStatementCompileStatement3 = sQLiteDatabase.compileStatement(INSERT_POLICY_UPDATEINFO_TABLE);
            sQLiteStatementCompileStatement3.bindLong(1, 0L);
            sQLiteStatementCompileStatement3.bindString(2, "");
            sQLiteStatementCompileStatement3.bindLong(3, 0L);
            sQLiteStatementCompileStatement3.bindLong(4, 0L);
            sQLiteStatementCompileStatement3.bindLong(5, 0L);
            sQLiteStatementCompileStatement3.executeInsert();
            SQLiteStatement sQLiteStatementCompileStatement4 = sQLiteDatabase.compileStatement(INSERT_BOOKMARK_SETTING);
            sQLiteStatementCompileStatement4.bindString(1, INITILAL_DAY_DATA);
            sQLiteStatementCompileStatement4.executeInsert();
            SQLiteStatement sQLiteStatementCompileStatement5 = sQLiteDatabase.compileStatement(INSERT_FP_SETTING);
            sQLiteStatementCompileStatement5.bindString(1, INITILAL_DAY_DATA);
            sQLiteStatementCompileStatement5.executeInsert();
            SQLiteStatement sQLiteStatementCompileStatement6 = sQLiteDatabase.compileStatement(INSERT_BANNER_SETTING);
            sQLiteStatementCompileStatement6.bindString(1, DatabaseExpert.BannerPos.MY_SERVICE.posId());
            sQLiteStatementCompileStatement6.bindString(2, INITILAL_DATE_DATA);
            sQLiteStatementCompileStatement6.bindString(3, INITILAL_DAY_DATA);
            sQLiteStatementCompileStatement6.executeInsert();
            SQLiteStatement sQLiteStatementCompileStatement7 = sQLiteDatabase.compileStatement(INSERT_BANNER_SETTING);
            sQLiteStatementCompileStatement7.bindString(1, DatabaseExpert.BannerPos.NOTIFICATION.posId());
            sQLiteStatementCompileStatement7.bindString(2, INITILAL_DATE_DATA);
            sQLiteStatementCompileStatement7.bindString(3, INITILAL_DAY_DATA);
            sQLiteStatementCompileStatement7.executeInsert();
            sQLiteStatementCompileStatement = sQLiteDatabase.compileStatement(INSERT_SETTING_TABLE);
            sQLiteStatementCompileStatement.bindLong(1, 0L);
            sQLiteStatementCompileStatement.bindString(2, INITILAL_DATE_DATA);
            sQLiteStatementCompileStatement.executeInsert();
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Exception unused) {
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Throwable th) {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
            throw th;
        }
        sQLiteStatementCompileStatement.close();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
                deleteOlderTables(sQLiteDatabase);
                upgradeFromOlderToV6(sQLiteDatabase);
            case 6:
                upgradeFromV6ToV7(sQLiteDatabase);
            case 7:
                upgradeFromV7ToV8(sQLiteDatabase);
            case 8:
                upgradeFromV8ToV9(sQLiteDatabase);
            case 9:
                upgradeFromV9ToV10(sQLiteDatabase);
            case 10:
                upgradeFromV10ToV11(sQLiteDatabase);
            case 11:
                upgradeFromV11ToV12(sQLiteDatabase);
                return;
            default:
                throw new SQLiteDatabaseCorruptException("DB Version Injustice :" + i);
        }
    }

    private void deleteOlderTables(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(DROP_TABLE_AREALIST);
        sQLiteDatabase.execSQL(DROP_TABLE_MULTIPURPOSELIST);
        sQLiteDatabase.execSQL(DROP_TABLE_APPLIST);
        sQLiteDatabase.execSQL(DROP_TABLE_SERVICELIST);
        sQLiteDatabase.execSQL(DROP_TABLE_SETTING);
        sQLiteDatabase.execSQL(DROP_USELESS_TABLE_DBCOMPLETE);
        sQLiteDatabase.execSQL(DROP_USELESS_TABLE_VIEWSERVICELIST);
        sQLiteDatabase.execSQL(DROP_TABLE_UPDATEINFO);
        sQLiteDatabase.execSQL(DROP_TABLE_BOOKMARKLIST);
        sQLiteDatabase.execSQL(DROP_USELESS_VIEW_ICON);
        sQLiteDatabase.execSQL(DROP_USELESS_TABLE_BOOKMARKICON);
    }

    private void upgradeFromOlderToV6(SQLiteDatabase sQLiteDatabase) {
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            sQLiteDatabase.execSQL(CREATE_AREALIST);
            sQLiteDatabase.execSQL(CREATE_MULTIPURPOSELIST);
            sQLiteDatabase.execSQL(CREATE_APPLIST);
            sQLiteDatabase.execSQL(CREATE_SERVICELIST);
            sQLiteDatabase.execSQL(CREATE_SETTING);
            sQLiteDatabase.execSQL(CREATE_UPDATEINFO);
            sQLiteDatabase.execSQL(CREATE_BOOKMARKLIST);
            sQLiteDatabase.execSQL(CREATE_BANNER_SETTING);
            sQLiteDatabase.execSQL(CREATE_BANNERINFO);
            sQLiteDatabase.execSQL(CREATE_SERVICELIST_INDEX);
            sQLiteDatabase.execSQL(CREATE_BOOKMARKLIST_INDEX);
            sQLiteDatabase.execSQL(CREATE_BANNERINFO_INDEX);
            String str = (String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_FACT_COUNT);
            String str2 = (String) Sg.getValue(Sg.Key.NET_AIM_VERSION_DEFAULT_PARAM_MAX_COUNT);
            SQLiteStatement sQLiteStatementCompileStatement2 = sQLiteDatabase.compileStatement(INSERT_UPDATEINFO_TABLE);
            sQLiteStatementCompileStatement2.bindLong(1, 0L);
            sQLiteStatementCompileStatement2.bindString(2, INITILAL_DATE_DATA);
            sQLiteStatementCompileStatement2.bindLong(3, Integer.parseInt(str));
            sQLiteStatementCompileStatement2.bindLong(4, Integer.parseInt(str2));
            sQLiteStatementCompileStatement2.execute();
            sQLiteStatementCompileStatement = sQLiteDatabase.compileStatement(INSERT_SETTING_TABLE);
            sQLiteStatementCompileStatement.bindLong(1, 0L);
            sQLiteStatementCompileStatement.bindString(2, INITILAL_DATE_DATA);
            sQLiteStatementCompileStatement.execute();
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Exception unused) {
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Throwable th) {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
            throw th;
        }
        sQLiteStatementCompileStatement.close();
    }

    private void upgradeFromV6ToV7(SQLiteDatabase sQLiteDatabase) {
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            sQLiteDatabase.execSQL(DROP_TABLE_SETTING);
            sQLiteDatabase.execSQL(CREATE_SETTING);
            sQLiteDatabase.execSQL(CREATE_BOOKMARK_SETTING);
            SQLiteStatement sQLiteStatementCompileStatement2 = sQLiteDatabase.compileStatement(INSERT_BOOKMARK_SETTING);
            sQLiteStatementCompileStatement2.bindString(1, INITILAL_DAY_DATA);
            sQLiteStatementCompileStatement2.execute();
            sQLiteStatementCompileStatement = sQLiteDatabase.compileStatement(INSERT_SETTING_TABLE);
            sQLiteStatementCompileStatement.bindLong(1, 0L);
            sQLiteStatementCompileStatement.bindString(2, INITILAL_DATE_DATA);
            sQLiteStatementCompileStatement.execute();
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Exception unused) {
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Throwable th) {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
            throw th;
        }
        sQLiteStatementCompileStatement.close();
    }

    private void upgradeFromV7ToV8(SQLiteDatabase sQLiteDatabase) {
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            sQLiteDatabase.execSQL(CREATE_FPSERVICELIST);
            sQLiteDatabase.execSQL(CREATE_FPSETTING);
            sQLiteDatabase.execSQL(CREATE_CARD_ADDITIONAL_INFO);
            sQLiteDatabase.execSQL(CREATE_CARD_ADDITIONAL_SETTING);
            sQLiteStatementCompileStatement = sQLiteDatabase.compileStatement(INSERT_FP_SETTING);
            sQLiteStatementCompileStatement.bindString(1, INITILAL_DAY_DATA);
            sQLiteStatementCompileStatement.execute();
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Exception unused) {
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Throwable th) {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
            throw th;
        }
        sQLiteStatementCompileStatement.close();
    }

    private void upgradeFromV8ToV9(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(DROP_TABLE_CARD_ADDITIONAL_INFO);
            sQLiteDatabase.execSQL(CREATE_CARD_ADDITIONAL_INFO);
            sQLiteDatabase.execSQL(CREATE_CARD_FACE_INFO);
        } catch (Exception unused) {
        }
    }

    private void upgradeFromV9ToV10(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(DROP_TABLE_SERVICELIST);
            sQLiteDatabase.execSQL(DROP_TABLE_CARD_ADDITIONAL_INFO);
            sQLiteDatabase.execSQL(DROP_TABLE_CARD_ADDITIONAL_SETTING);
            sQLiteDatabase.execSQL(CREATE_SERVICELIST);
            sQLiteDatabase.execSQL(CREATE_CARD_ADDITIONAL_INFO);
        } catch (Exception unused) {
        }
    }

    private void upgradeFromV11ToV12(SQLiteDatabase sQLiteDatabase) {
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            sQLiteDatabase.execSQL(DROP_TABLE_AREALIST);
            sQLiteDatabase.execSQL(DROP_TABLE_MULTIPURPOSELIST);
            sQLiteDatabase.execSQL(DROP_TABLE_APPLIST);
            sQLiteDatabase.execSQL(DROP_TABLE_SERVICELIST);
            sQLiteDatabase.execSQL(DROP_TABLE_SETTING);
            sQLiteDatabase.execSQL(DROP_TABLE_CARD_ADDITIONAL_INFO);
            sQLiteDatabase.execSQL(DROP_TABLE_BOOKMARKLIST);
            sQLiteDatabase.execSQL(DROP_TABLE_BOOKMARK_SETTING);
            sQLiteDatabase.execSQL(DROP_TABLE_SERVICE_ADDITIONAL_INFO);
            SortCardInfoTable.migrate(sQLiteDatabase);
            sQLiteDatabase.execSQL(SortCardInfoTable.Statement.DROP);
            sQLiteDatabase.execSQL(CREATE_AREALIST);
            sQLiteDatabase.execSQL(CREATE_MULTIPURPOSELIST);
            sQLiteDatabase.execSQL(CREATE_APPLIST);
            sQLiteDatabase.execSQL(CREATE_SERVICELIST);
            sQLiteDatabase.execSQL(CREATE_SETTING);
            sQLiteDatabase.execSQL(CREATE_BOOKMARKLIST);
            sQLiteDatabase.execSQL(CREATE_BOOKMARK_SETTING);
            sQLiteDatabase.execSQL(CREATE_CARD_ADDITIONAL_INFO);
            sQLiteDatabase.execSQL(CREATE_POLICY_UPDATEINFO);
            sQLiteDatabase.execSQL(TransitInformationListTable.Statement.CREATE);
            sQLiteDatabase.execSQL(OptionalInformationListTable.Statement.CREATE);
            sQLiteDatabase.execSQL(CREATE_SERVICE_LINKAGEAPPLIST);
            sQLiteDatabase.execSQL(CREATE_SERVICE_LINKAGEWEBLIST);
            sQLiteDatabase.execSQL(CREATE_SERVICELIST_INDEX);
            sQLiteDatabase.execSQL(CREATE_BOOKMARKLIST_INDEX);
            SQLiteStatement sQLiteStatementCompileStatement2 = sQLiteDatabase.compileStatement(INSERT_POLICY_UPDATEINFO_TABLE);
            sQLiteStatementCompileStatement2.bindLong(1, 0L);
            sQLiteStatementCompileStatement2.bindString(2, "");
            sQLiteStatementCompileStatement2.bindLong(3, 0L);
            sQLiteStatementCompileStatement2.bindLong(4, 0L);
            sQLiteStatementCompileStatement2.bindLong(5, 0L);
            sQLiteStatementCompileStatement2.executeInsert();
            SQLiteStatement sQLiteStatementCompileStatement3 = sQLiteDatabase.compileStatement(INSERT_SETTING_TABLE);
            sQLiteStatementCompileStatement3.bindLong(1, 0L);
            sQLiteStatementCompileStatement3.bindString(2, INITILAL_DATE_DATA);
            sQLiteStatementCompileStatement3.executeInsert();
            sQLiteStatementCompileStatement = sQLiteDatabase.compileStatement(INSERT_BOOKMARK_SETTING);
            sQLiteStatementCompileStatement.bindString(1, INITILAL_DAY_DATA);
            sQLiteStatementCompileStatement.executeInsert();
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Exception unused) {
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Throwable th) {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
            throw th;
        }
        sQLiteStatementCompileStatement.close();
    }

    private static boolean isRecoveryCheck() {
        Cursor cursorQuery = null;
        try {
            cursorQuery = db.query(TABLE_SETTING, COLUMNS_SETTING, null, null, null, null, null, "1");
            cursorQuery.moveToFirst();
            z = cursorQuery.getCount() > 0;
        } catch (Exception unused) {
            if (cursorQuery != null) {
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return z;
    }

    public static void clearInstance() {
        if (db != null && db.isOpen()) {
            try {
                db.close();
            } catch (Exception unused) {
            }
        }
        if (dbHelper != null) {
            try {
                dbHelper.close();
            } catch (Exception unused2) {
            }
            db = null;
            dbHelper = null;
        }
    }
}

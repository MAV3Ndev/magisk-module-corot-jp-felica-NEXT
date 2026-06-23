package com.felicanetworks.mfm.main.model.internal.main;

import com.felicanetworks.mfm.main.model.CentralFunc;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeManager;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.FeliCaParams;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.BookmarkProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.FpServiceProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.IdsUpdateCheckProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.model.internal.main.net.ServiceIdsProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.ServiceProtocol;
import com.felicanetworks.mfm.main.model.internal.main.pkg.PackageExpert;
import com.felicanetworks.mfm.main.model.internal.main.pkg.PackageExpertException;
import com.felicanetworks.mfm.main.model.internal.main.pkg.SpecAppSignatures;
import com.felicanetworks.mfm.main.model.internal.main.text.TextUtils;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.service.ServiceGroupType;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import com.felicanetworks.mfm.main.policy.service.SupportServiceType;
import java.lang.Thread;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class CompileWorker extends Thread {
    private static final String DEFAULT_MESSAGEDIGEST = "SHA-256";
    private static final String INITILAL_DATE_DATA = "000000000000";
    private static final String LINK_APP = "2";
    private static final String TYPE_SAS = "001";
    private static final String TYPE_UICC = "002";
    private ModelContext _context;
    private DatabaseExpert _db;
    private boolean _isCheckNow;
    private Listener _listener;
    private PackageExpert _pe;

    interface Listener {

        public enum Pos {
            MYSERVICE_SIM_UC_END,
            MYSERVICE_SIM_UC_SKIP,
            MYSERVICE_SIM_GID_END,
            MYSERVICE_SIM_GID_SKIP,
            MYSERVICE_SIM_SID_CONNECTED,
            MYSERVICE_SIM_SID_RECEIVING_1,
            MYSERVICE_SIM_SID_RECEIVING_2,
            MYSERVICE_SIM_SID_RECEIVING_3,
            MYSERVICE_SIM_SID_RECEIVING_4,
            MYSERVICE_SIM_SID_RECEIVING_5,
            MYSERVICE_SIM_SID_RECEIVING_6,
            MYSERVICE_SIM_SID_RECEIVING_7,
            MYSERVICE_PARALLEL_MFC_END,
            MYSERVICE_PARALLEL_APP_END,
            MYSERVICE_PARALLEL_END,
            MYSERVICE_END,
            RECOMMEND_SIM_BOOKMARK_END,
            RECOMMEND_END,
            FPSERVICE_SIM_END,
            FPSERVICE_END
        }

        void completed(CentralFunc.CompiledResult compiledResult, CentralFunc.CompiledState compiledState);

        void error(ModelErrorInfo modelErrorInfo);

        void progress(Pos pos);
    }

    public static class Warnings {
        private List<Exception> _warnings = new ArrayList();
        public boolean isLackEmoneyInfo = false;

        public void add(Exception exc) throws Exception {
            if (exc instanceof MfcException) {
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[((MfcException) exc).getErrorType().ordinal()];
                if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
                    this._warnings.add(exc);
                    return;
                }
                throw exc;
            }
            if (exc instanceof NetworkExpertException) {
                this._warnings.add(exc);
                return;
            }
            throw exc;
        }

        public void markLackEmoneyInfo() {
            this.isLackEmoneyInfo = true;
        }

        public MfcException getMfcWarning() {
            for (Exception exc : this._warnings) {
                if (exc instanceof MfcException) {
                    return (MfcException) exc;
                }
            }
            return null;
        }

        public boolean hasMfcWarning() {
            return getMfcWarning() != null;
        }

        public CentralFunc.CompiledState.FelicaState getFelicaState() {
            CentralFunc.CompiledState.FelicaState felicaState = CentralFunc.CompiledState.FelicaState.NO_PROBLEM;
            if (!hasMfcWarning()) {
                return felicaState;
            }
            int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[getMfcWarning().getErrorType().ordinal()];
            if (i == 1) {
                return CentralFunc.CompiledState.FelicaState.LOCK_ERROR;
            }
            if (i == 2) {
                return CentralFunc.CompiledState.FelicaState.OPEN_ERROR;
            }
            if (i == 3) {
                return CentralFunc.CompiledState.FelicaState.TIMEOUT_ERROR;
            }
            if (i != 4) {
                return i != 5 ? felicaState : CentralFunc.CompiledState.FelicaState.RUNNING_BY_MFIC;
            }
            return CentralFunc.CompiledState.FelicaState.USED_BY_OTHER_APP;
        }

        public NetworkExpertException getNetworkWarning() {
            for (Exception exc : this._warnings) {
                if (exc instanceof NetworkExpertException) {
                    return (NetworkExpertException) exc;
                }
            }
            return null;
        }

        public boolean hasNetworkWarning() {
            return getNetworkWarning() != null;
        }

        public CentralFunc.CompiledState.NetworkState getNetworkState() {
            return hasNetworkWarning() ? CentralFunc.CompiledState.NetworkState.CONNECT_ERROR : CentralFunc.CompiledState.NetworkState.NO_PROBLEM;
        }
    }

    CompileWorker(ModelContext modelContext, boolean z, Listener listener) {
        this._context = modelContext;
        this._isCheckNow = z;
        this._listener = listener;
        this._db = modelContext.getOpenedDatabaseExpert();
        this._pe = new PackageExpert(this._context);
    }

    protected CompileWorker() {
    }

    private void checkInterrupted() throws InterruptedException {
        if (isInterrupted()) {
            throw new InterruptedException();
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        boolean z;
        try {
            try {
                try {
                    checkInterrupted();
                    Warnings warnings = new Warnings();
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new MainParallelWorker());
                    MfcParallelWorker mfcParallelWorker = new MfcParallelWorker(ModelContext.getInitializedMfcExpert());
                    arrayList.add(mfcParallelWorker);
                    PackageParallelWorker packageParallelWorker = new PackageParallelWorker(this._pe);
                    arrayList.add(packageParallelWorker);
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((ParallelWorker) it.next()).start();
                    }
                    while (true) {
                        Iterator it2 = arrayList.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                z = false;
                                break;
                            } else if (((ParallelWorker) it2.next()).isAlive()) {
                                z = true;
                                break;
                            }
                        }
                        if (!z) {
                            break;
                        }
                        try {
                            sleep(50L);
                        } catch (InterruptedException e) {
                            Iterator it3 = arrayList.iterator();
                            while (it3.hasNext()) {
                                ((ParallelWorker) it3.next()).interrupt();
                            }
                            throw e;
                        }
                    }
                    this._listener.progress(Listener.Pos.MYSERVICE_PARALLEL_END);
                    Iterator it4 = arrayList.iterator();
                    while (it4.hasNext()) {
                        try {
                            ((ParallelWorker) it4.next()).throwException();
                        } catch (Exception e2) {
                            warnings.add(e2);
                        }
                    }
                    List<MfcExpert.Area> areaList = mfcParallelWorker.getAreaList();
                    List<MfcExpert.Sas> sasList = mfcParallelWorker.getSasList();
                    List<PackageExpert.InstalledApp> installedAppList = packageParallelWorker.getInstalledAppList();
                    List<DatabaseExpert.AppId> appIds = this._db.getAppIds();
                    List<DatabaseExpert.AreaId> areaIds = this._db.getAreaIds();
                    List<DatabaseExpert.MultiId> multiIds = this._db.getMultiIds();
                    List<Service> service = this._db.getService();
                    List<String> listSearchArea = searchArea(areaIds, areaList);
                    List<String> listSearchSas = searchSas(multiIds, sasList);
                    List<String> listSearchApp = searchApp(appIds, installedAppList);
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    ArrayList arrayList5 = new ArrayList();
                    Iterator<DatabaseExpert.AreaId> it5 = areaIds.iterator();
                    while (it5.hasNext()) {
                        arrayList2.add(it5.next().id);
                    }
                    Iterator<DatabaseExpert.AppId> it6 = appIds.iterator();
                    while (it6.hasNext()) {
                        arrayList3.add(it6.next().id);
                    }
                    Iterator<DatabaseExpert.MultiId> it7 = multiIds.iterator();
                    while (it7.hasNext()) {
                        DatabaseExpert.MultiId next = it7.next();
                        Iterator<DatabaseExpert.MultiId> it8 = it7;
                        if (TYPE_SAS.equals(next.type)) {
                            arrayList4.add(next.id);
                        } else if (TYPE_UICC.equals(next.type)) {
                            arrayList5.add(next.id);
                        }
                        it7 = it8;
                    }
                    this._listener.progress(Listener.Pos.MYSERVICE_END);
                    checkInterrupted();
                    if (this._db.isExists(Arrays.asList("SV000013", "SV000075", FeliCaParams.SERVICE_ID_SUICA, "SV000011")).contains(false)) {
                        warnings.markLackEmoneyInfo();
                    }
                    ArrayList arrayList6 = new ArrayList();
                    arrayList6.addAll(areaIds);
                    arrayList6.addAll(appIds);
                    arrayList6.addAll(multiIds);
                    this._listener.completed(new CentralFunc.CompiledResult(listSearchArea, listSearchApp, listSearchSas, arrayList2, arrayList3, arrayList4, arrayList5, service), checkCompiledState(service, arrayList6, warnings));
                } catch (Exception e3) {
                    this._listener.error(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(CompileWorker.class, 1, e3)));
                }
            } catch (InterruptedException | NullPointerException unused) {
            }
        } catch (DatabaseException e4) {
            this._listener.error(new ModelErrorInfo(ModelErrorInfo.Type.DB_ACCESS_ERROR, e4));
        } catch (MfcException e5) {
            switch (e5.getErrorType()) {
                case MFC_INVALID_PERMISSION:
                    this._listener.error(new ModelErrorInfo(ModelErrorInfo.Type.PERM_INSPECT, e5));
                    return;
                case FELICA_INVALID_RESPONSE_ERROR:
                case MFC_OTHER_ERROR:
                    this._listener.error(new ModelErrorInfo(ModelErrorInfo.Type.MFC_OTHER_ERROR, e5));
                    return;
                case FELICA_HTTP_ERROR:
                    this._listener.error(new ModelErrorInfo(ModelErrorInfo.Type.FELICA_HTTP_ERROR, e5));
                    return;
                case MFIC_VERSION_ERROR:
                    this._listener.error(new ModelErrorInfo(ModelErrorInfo.Type.MFIC_VERSION_ERROR, e5));
                    return;
                default:
                    this._listener.error(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, e5));
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getRecommendInfo() throws InterruptedException, NetworkExpertException, DatabaseException {
        String strConvertFromCalendarToString = FuncUtil.convertFromCalendarToString("yyyyMMdd", FuncUtil.getCurrentCalendar());
        boolean z = this._isCheckNow || !strConvertFromCalendarToString.equals(this._db.getBookmarkSettings().lastUpdateDate);
        checkInterrupted();
        if (z) {
            try {
                this._db.setRecommends(convertRecommends(connectRecommendProtocol(this._context.getNetworkExpert()).result));
                this._db.setBookmarkSettings(new DatabaseExpert.BookmarkSettings(strConvertFromCalendarToString));
            } finally {
                this._listener.progress(Listener.Pos.RECOMMEND_SIM_BOOKMARK_END);
            }
        }
        this._listener.progress(Listener.Pos.RECOMMEND_END);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getFPInfo() throws InterruptedException, NetworkExpertException, DatabaseException {
        String strConvertFromCalendarToString = FuncUtil.convertFromCalendarToString("yyyyMMdd", FuncUtil.getCurrentCalendar());
        boolean z = this._isCheckNow || !strConvertFromCalendarToString.equals(this._db.getFelicaPocketSettings().lastUpdateDate);
        checkInterrupted();
        if (z) {
            try {
                TextUtils textUtils = new TextUtils(this._context.getLegacyContext());
                this._db.setFelicaPocketService(convertFpServices(connectFpServiceProtocol(this._context.getNetworkExpert(), textUtils.getApiCode(), textUtils.getApiCodeVersion()).getResult()));
                this._db.setFelicaPocketSettings(new DatabaseExpert.FpSetting(strConvertFromCalendarToString));
            } finally {
                this._listener.progress(Listener.Pos.FPSERVICE_SIM_END);
            }
        }
        this._listener.progress(Listener.Pos.FPSERVICE_END);
    }

    protected List<String> searchArea(List<DatabaseExpert.AreaId> list, List<MfcExpert.Area> list2) {
        ArrayList arrayList = new ArrayList();
        if (list != null && list2 != null) {
            for (DatabaseExpert.AreaId areaId : list) {
                for (MfcExpert.Area area : list2) {
                    if ((areaId.sysCode + areaId.areaCode).equals(area.sysCode + area.areaCode) && !arrayList.contains(areaId.id)) {
                        arrayList.add(areaId.id);
                    }
                }
            }
        }
        return arrayList;
    }

    protected List<String> searchSas(List<DatabaseExpert.MultiId> list, List<MfcExpert.Sas> list2) {
        ArrayList arrayList = new ArrayList();
        if (list != null && list2 != null) {
            for (DatabaseExpert.MultiId multiId : list) {
                if (TYPE_SAS.equals(multiId.type)) {
                    for (MfcExpert.Sas sas : list2) {
                        if (multiId.code.equals(sas.cpidSid + sas.blockName) && !arrayList.contains(multiId.id)) {
                            arrayList.add(multiId.id);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    protected List<String> searchApp(List<DatabaseExpert.AppId> list, List<PackageExpert.InstalledApp> list2) {
        MessageDigest messageDigest;
        ArrayList arrayList = new ArrayList();
        if (list != null && list2 != null) {
            for (DatabaseExpert.AppId appId : list) {
                List<String> listFindSignatures = SpecAppSignatures.findSignatures(appId.pkgName);
                ArrayList arrayList2 = new ArrayList();
                if (!listFindSignatures.isEmpty()) {
                    arrayList2.addAll(listFindSignatures);
                } else {
                    arrayList2.add(appId.sigHash);
                }
                for (PackageExpert.InstalledApp installedApp : list2) {
                    if (appId.pkgName.equals(installedApp.pkgName)) {
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            byte[] bArrHexStringToBin = CommonUtil.hexStringToBin(((String) it.next()).toUpperCase(Locale.US));
                            Iterator<byte[]> it2 = installedApp.sigHash.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    byte[] next = it2.next();
                                    try {
                                        messageDigest = MessageDigest.getInstance(DEFAULT_MESSAGEDIGEST);
                                        messageDigest.update(next);
                                    } catch (NoSuchAlgorithmException unused) {
                                    }
                                    if (Arrays.equals(bArrHexStringToBin, messageDigest.digest()) && !arrayList.contains(appId.id)) {
                                        arrayList.add(appId.id);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    protected List<String> pickupIds(List<MfcExpert.Asset> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (MfcExpert.Asset asset : list) {
                if (asset == null || !FeliCaParams.SERVICE_ID_DCARD.equals(asset.serviceId) || asset.isDcardMini) {
                    arrayList.add(asset.serviceId);
                }
            }
        }
        return arrayList;
    }

    protected FpServiceProtocol.Result connectFpServiceProtocol(NetworkExpert networkExpert, String str, String str2) throws NetworkExpertException {
        FpServiceProtocol fpServiceProtocol = networkExpert.getFpServiceProtocol();
        try {
            return fpServiceProtocol.parse(networkExpert.connect(fpServiceProtocol.create(new FpServiceProtocol.Parameter(Settings.idm(), str, str2))));
        } catch (NetworkExpertException e) {
            throw new IllegalStateException(e);
        }
    }

    protected BookmarkProtocol.Result connectRecommendProtocol(NetworkExpert networkExpert) throws NetworkExpertException {
        BookmarkProtocol bookmarkProtocol = networkExpert.getBookmarkProtocol();
        try {
            return bookmarkProtocol.parse(networkExpert.connect(bookmarkProtocol.create(new BookmarkProtocol.Parameter(Settings.idm()))));
        } catch (NetworkExpertException e) {
            throw new IllegalStateException(e);
        }
    }

    protected List<DatabaseExpert.Recommend> convertRecommends(List<BookmarkProtocol.Result.BookmarkCategoryItem> list) {
        String str;
        String next;
        ArrayList arrayList = new ArrayList();
        Iterator<BookmarkProtocol.Result.BookmarkCategoryItem> it = list.iterator();
        while (it.hasNext()) {
            BookmarkProtocol.Result.BookmarkCategoryItem next2 = it.next();
            Iterator<BookmarkProtocol.Result.BookmarkItem> it2 = next2.bookmarkItemList.iterator();
            while (it2.hasNext()) {
                BookmarkProtocol.Result.BookmarkItem next3 = it2.next();
                Iterator<String> it3 = next3.useProcedureList.iterator();
                String str2 = next3.serviceId;
                String str3 = next3.serviceVersion;
                String str4 = next2.categoryId;
                String str5 = next2.categoryTitle;
                String str6 = next3.serviceName;
                String str7 = next3.serviceProviderName;
                String str8 = next3.bookmarkStatusKind;
                String str9 = next3.bookmarkCooperKind;
                String str10 = next3.bookmarkApKind;
                Iterator<BookmarkProtocol.Result.BookmarkCategoryItem> it4 = it;
                String str11 = next3.bookmarkApUrl;
                BookmarkProtocol.Result.BookmarkCategoryItem bookmarkCategoryItem = next2;
                String str12 = next3.bookmarkWebUrl;
                Iterator<BookmarkProtocol.Result.BookmarkItem> it5 = it2;
                String str13 = next3.recommendedOverview;
                String str14 = next3.recommendedDetail;
                String next4 = it3.hasNext() ? it3.next() : null;
                String next5 = it3.hasNext() ? it3.next() : null;
                String next6 = it3.hasNext() ? it3.next() : null;
                String next7 = it3.hasNext() ? it3.next() : null;
                if (it3.hasNext()) {
                    next = it3.next();
                    str = str10;
                } else {
                    str = str10;
                    next = null;
                }
                ArrayList arrayList2 = arrayList;
                arrayList2.add(new DatabaseExpert.Recommend(str2, str3, str4, str5, str6, str7, str8, str9, str, str11, str12, str13, str14, next4, next5, next6, next7, next));
                arrayList = arrayList2;
                next2 = bookmarkCategoryItem;
                it2 = it5;
                it = it4;
            }
        }
        return arrayList;
    }

    protected CentralFunc.CompiledState checkCompiledState(List<Service> list, List<DatabaseExpert.ServiceId> list2, Warnings warnings) {
        boolean z = false;
        for (Service service : list) {
            Iterator<DatabaseExpert.ServiceId> it = list2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                DatabaseExpert.ServiceId next = it.next();
                if (service.getServiceId().equals(next.id) && !service.getVersion().equals(next.version)) {
                    z = true;
                    break;
                }
            }
            if (z) {
                break;
            }
        }
        return new CentralFunc.CompiledState(warnings.getFelicaState(), warnings.getNetworkState(), false, z, warnings.isLackEmoneyInfo);
    }

    protected List<DatabaseExpert.FelicaPocketService> convertFpServices(List<FpServiceProtocol.Result.FpServiceItem> list) {
        ArrayList arrayList = new ArrayList();
        for (FpServiceProtocol.Result.FpServiceItem fpServiceItem : list) {
            String fpServiceNumber = fpServiceItem.getFpServiceNumber();
            String fpServiceName = fpServiceItem.getFpServiceName();
            String fpServiceIconUrl = fpServiceItem.getFpServiceIconUrl();
            String fpServiceCorporation = fpServiceItem.getFpServiceCorporation();
            boolean zIsEnableService = fpServiceItem.isEnableService();
            String webUrl = fpServiceItem.getWebUrl();
            String fpServiceKind = fpServiceItem.getFpServiceKind();
            boolean zIsNeedShowValue = fpServiceItem.isNeedShowValue();
            arrayList.add(new DatabaseExpert.FelicaPocketService(fpServiceNumber, fpServiceName, fpServiceIconUrl, fpServiceCorporation, zIsEnableService ? 1 : 0, webUrl, fpServiceKind, zIsNeedShowValue ? 1 : 0, fpServiceItem.getPointUnitName()));
        }
        return arrayList;
    }

    protected abstract class ParallelWorker extends Thread implements Thread.UncaughtExceptionHandler {
        protected Exception _exception;

        protected ParallelWorker() {
            setUncaughtExceptionHandler(this);
        }

        public void throwException() throws Exception {
            Exception exc = this._exception;
            if (exc != null) {
                throw exc;
            }
        }

        void checkInterrupted() throws InterruptedException {
            if (isInterrupted()) {
                throw new InterruptedException();
            }
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
            if (th instanceof Exception) {
                this._exception = (Exception) th;
            }
        }
    }

    protected class MfcParallelWorker extends ParallelWorker {
        private List<MfcExpert.Area> _areas;
        private List<MfcExpert.Asset> _assets;
        private MfcExpert _mfc;
        private List<MfcExpert.Sas> _sass;

        public MfcParallelWorker(MfcExpert mfcExpert) {
            super();
            this._mfc = mfcExpert;
        }

        public List<MfcExpert.Area> getAreaList() {
            return this._areas;
        }

        public List<MfcExpert.Sas> getSasList() {
            return this._sass;
        }

        public List<MfcExpert.Asset> getAssetsList() {
            return this._assets;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                super.checkInterrupted();
                try {
                    this._mfc.mfcStart();
                    this._areas = this._mfc.getAreaList();
                    this._sass = this._mfc.getSasList();
                } catch (MfcException e) {
                    this._exception = e;
                    this._areas = null;
                    this._sass = null;
                    this._assets = null;
                }
            } catch (InterruptedException unused) {
            } catch (Throwable th) {
                CompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_PARALLEL_MFC_END);
                throw th;
            }
            CompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_PARALLEL_MFC_END);
        }
    }

    protected class PackageParallelWorker extends ParallelWorker {
        private List<PackageExpert.InstalledApp> _apps;
        private PackageExpert _pkg;

        public PackageParallelWorker(PackageExpert packageExpert) {
            super();
            this._pkg = packageExpert;
        }

        public List<PackageExpert.InstalledApp> getInstalledAppList() {
            return this._apps;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                try {
                    super.checkInterrupted();
                    this._apps = this._pkg.getInstalledApps();
                } catch (PackageExpertException e) {
                    this._exception = e;
                    this._apps = null;
                } catch (InterruptedException unused) {
                }
            } finally {
                CompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_PARALLEL_APP_END);
            }
        }
    }

    protected class MainParallelWorker extends ParallelWorker {
        private String _acb;
        private String _acv;
        private IdsCage _cage;

        protected class BasicElement implements ServiceIdPolicy.ServiceIdHolder {
            public String id;
            public String version;

            public BasicElement(String str, String str2) {
                this.id = str;
                this.version = str2;
            }

            @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdHolder
            public String sid() {
                return this.id;
            }
        }

        private class IdsCage {
            private Db _dbCage;
            private Net _netCage;

            private IdsCage() {
            }

            private class Db {
                private List<DatabaseExpert.AppId> appIds;
                private List<DatabaseExpert.AreaId> areaIds;
                private List<DatabaseExpert.MultiId> multiIds;

                public Db(List<DatabaseExpert.AreaId> list, List<DatabaseExpert.AppId> list2, List<DatabaseExpert.MultiId> list3) {
                    this.areaIds = list;
                    this.appIds = list2;
                    this.multiIds = list3;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public List<BasicElement> getBasicElements() {
                    ArrayList arrayList = new ArrayList();
                    for (DatabaseExpert.AreaId areaId : this.areaIds) {
                        arrayList.add(MainParallelWorker.this.new BasicElement(areaId.id, areaId.version));
                    }
                    for (DatabaseExpert.AppId appId : this.appIds) {
                        arrayList.add(MainParallelWorker.this.new BasicElement(appId.id, appId.version));
                    }
                    for (DatabaseExpert.MultiId multiId : this.multiIds) {
                        arrayList.add(MainParallelWorker.this.new BasicElement(multiId.id, multiId.version));
                    }
                    return arrayList;
                }
            }

            private class Net {
                private List<ServiceIdsProtocol.Result.ApplicationItem> appIds;
                private List<ServiceIdsProtocol.Result.AreaItem> areaIds;
                private List<ServiceIdsProtocol.Result.MultiPurposeIdentifierItem> multiIds;

                private Net(List<ServiceIdsProtocol.Result.AreaItem> list, List<ServiceIdsProtocol.Result.ApplicationItem> list2, List<ServiceIdsProtocol.Result.MultiPurposeIdentifierItem> list3) {
                    this.areaIds = list;
                    this.appIds = list2;
                    this.multiIds = list3;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public List<BasicElement> getBasicElements() {
                    ArrayList arrayList = new ArrayList();
                    for (ServiceIdsProtocol.Result.AreaItem areaItem : this.areaIds) {
                        arrayList.add(MainParallelWorker.this.new BasicElement(areaItem.serviceId, areaItem.serviceVersion));
                    }
                    for (ServiceIdsProtocol.Result.ApplicationItem applicationItem : this.appIds) {
                        arrayList.add(MainParallelWorker.this.new BasicElement(applicationItem.serviceId, applicationItem.serviceVersion));
                    }
                    for (ServiceIdsProtocol.Result.MultiPurposeIdentifierItem multiPurposeIdentifierItem : this.multiIds) {
                        arrayList.add(MainParallelWorker.this.new BasicElement(multiPurposeIdentifierItem.serviceId, multiPurposeIdentifierItem.serviceVersion));
                    }
                    return arrayList;
                }
            }

            List<BasicElement> getAllIds() {
                List<BasicElement> basicElements;
                ArrayList arrayList = new ArrayList();
                if (!isSyncServer()) {
                    basicElements = this._dbCage.getBasicElements();
                } else {
                    basicElements = this._netCage.getBasicElements();
                }
                for (BasicElement basicElement : basicElements) {
                    if (!ServiceIdPolicy.contains(arrayList, basicElement)) {
                        arrayList.add(basicElement);
                    }
                }
                return arrayList;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean isSyncServer() {
                return this._netCage != null;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void setDbIds(List<DatabaseExpert.AreaId> list, List<DatabaseExpert.AppId> list2, List<DatabaseExpert.MultiId> list3) {
                this._dbCage = new Db(list, list2, list3);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void setNetIds(List<ServiceIdsProtocol.Result.AreaItem> list, List<ServiceIdsProtocol.Result.ApplicationItem> list2, List<ServiceIdsProtocol.Result.MultiPurposeIdentifierItem> list3) {
                this._netCage = new Net(list, list2, list3);
            }
        }

        public MainParallelWorker() {
            super();
            this._cage = new IdsCage();
            TextUtils textUtils = new TextUtils(CompileWorker.this._context.getLegacyContext());
            this._acb = textUtils.getApiCode();
            this._acv = textUtils.getApiCodeVersion();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            ServiceIdsProtocol.Result resultConnectGidProtocol;
            try {
                try {
                    UcTerms ucTerms = getUcTerms(CompileWorker.this._db.getIdsSettings());
                    if (!ucTerms.isNeed) {
                        CompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_UC_SKIP);
                    } else {
                        try {
                            super.checkInterrupted();
                            try {
                                IdsUpdateCheckProtocol.Result resultConnectUcProtocol = connectUcProtocol(CompileWorker.this._context.getNetworkExpert(), ucTerms.lastUpdateDate);
                                CompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_UC_END);
                                super.checkInterrupted();
                                if (resultConnectUcProtocol.isNecessity()) {
                                    try {
                                        resultConnectGidProtocol = connectGidProtocol(CompileWorker.this._context.getNetworkExpert(), this._acb, this._acv);
                                        CompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_GID_END);
                                        this._cage.setNetIds(resultConnectGidProtocol.areaList, resultConnectGidProtocol.apliList, resultConnectGidProtocol.multiPurposeList);
                                    } catch (Throwable th) {
                                        CompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_GID_END);
                                        throw th;
                                    }
                                } else {
                                    CompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_GID_SKIP);
                                    resultConnectGidProtocol = null;
                                }
                                CompileWorker.this._db.setIds(new DatabaseExpert.IdsSettings(Integer.valueOf(resultConnectUcProtocol.updateInterval).intValue(), FuncUtil.convertFromCalendarToString(DateFormatter.DATE_MINUTE, ucTerms.checkedDate)), resultConnectGidProtocol != null ? convertAreaIds(resultConnectGidProtocol.areaList) : null, resultConnectGidProtocol != null ? convertAppIds(resultConnectGidProtocol.apliList) : null, resultConnectGidProtocol != null ? convertMultiIds(resultConnectGidProtocol.multiPurposeList) : null);
                            } catch (Throwable th2) {
                                CompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_UC_END);
                                throw th2;
                            }
                        } catch (NetworkExpertException e) {
                            this._exception = e;
                        }
                    }
                    if (!this._cage.isSyncServer()) {
                        this._cage.setDbIds(CompileWorker.this._db.getAreaIds(), CompileWorker.this._db.getAppIds(), CompileWorker.this._db.getMultiIds());
                    }
                    List<BasicElement> allIds = this._cage.getAllIds();
                    List<Service> service = CompileWorker.this._db.getService();
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    for (BasicElement basicElement : allIds) {
                        Iterator<Service> it = service.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Service next = it.next();
                            if (next.getServiceId().equals(basicElement.id)) {
                                if (next.getVersion().equals(basicElement.version)) {
                                    arrayList3.add(basicElement.id);
                                } else {
                                    arrayList.add(basicElement.id);
                                    arrayList2.add(basicElement);
                                }
                            }
                        }
                        if (!arrayList.contains(basicElement.id) && !arrayList3.contains(basicElement.id)) {
                            arrayList.add(basicElement.id);
                            arrayList2.add(basicElement);
                        }
                    }
                    for (Service service2 : service) {
                        if (!arrayList.contains(service2.getServiceId()) && !arrayList3.contains(service2.getServiceId())) {
                            arrayList4.add(service2.getServiceId());
                        }
                    }
                    try {
                        super.checkInterrupted();
                        ServiceProtocol.Result resultConnectSidProtocol = arrayList2.isEmpty() ? null : connectSidProtocol(CompileWorker.this._context.getNetworkExpert(), this._acb, this._acv, arrayList2);
                        List<ServiceProtocol.Result.ServiceItem> arrayList5 = resultConnectSidProtocol != null ? resultConnectSidProtocol.result : new ArrayList<>();
                        if (this._exception == null && (!arrayList5.isEmpty() || !arrayList4.isEmpty())) {
                            CompileWorker.this._db.setServices(convertServices(arrayList5), arrayList4);
                        }
                    } catch (NetworkExpertException e2) {
                        this._exception = e2;
                    }
                    try {
                        CompileWorker.this.getRecommendInfo();
                    } catch (NetworkExpertException e3) {
                        this._exception = e3;
                    }
                    List<Service> service3 = CompileWorker.this._db.getService();
                    List<DatabaseExpert.AppId> appIds = CompileWorker.this._db.getAppIds();
                    List<DatabaseExpert.AreaId> areaIds = CompileWorker.this._db.getAreaIds();
                    List<DatabaseExpert.MultiId> multiIds = CompileWorker.this._db.getMultiIds();
                    ArrayList arrayList6 = new ArrayList();
                    ArrayList arrayList7 = new ArrayList();
                    ArrayList arrayList8 = new ArrayList();
                    ArrayList arrayList9 = new ArrayList();
                    Iterator<DatabaseExpert.AreaId> it2 = areaIds.iterator();
                    while (it2.hasNext()) {
                        arrayList6.add(it2.next().id);
                    }
                    Iterator<DatabaseExpert.AppId> it3 = appIds.iterator();
                    while (it3.hasNext()) {
                        arrayList7.add(it3.next().id);
                    }
                    for (DatabaseExpert.MultiId multiId : multiIds) {
                        if (CompileWorker.TYPE_SAS.equals(multiId.type)) {
                            arrayList8.add(multiId.id);
                        } else if (CompileWorker.TYPE_UICC.equals(multiId.type)) {
                            arrayList9.add(multiId.id);
                        }
                    }
                    SupportServiceType.inject(service3, arrayList6, arrayList7, arrayList8, arrayList9);
                    List<String> m1M2TypeNotID = SupportServiceType.getM1M2TypeNotID();
                    if (!m1M2TypeNotID.isEmpty()) {
                        for (String str : m1M2TypeNotID) {
                            Map<String, Object> specificColumnData = CompileWorker.this._db.getSpecificColumnData(str, Collections.singletonList("ServiceType"));
                            if (specificColumnData.size() != 0) {
                                StringBuilder sb = new StringBuilder((String) Objects.requireNonNull(specificColumnData.get("ServiceType")));
                                sb.replace(0, 2, "UU");
                                HashMap map = new HashMap();
                                map.put("ServiceType", sb.toString());
                                CompileWorker.this._db.updateSpecificColumnData(str, map);
                            }
                        }
                        service3 = CompileWorker.this._db.getService();
                        SupportServiceType.inject(service3, arrayList6, arrayList7, arrayList8, arrayList9);
                    }
                    CompileWorker.this._db.delIds(SupportServiceType.US.targetIds);
                    NoticeManager.getInstance(CompileWorker.this._context.getAndroidContext()).deleteRegisteredServiceItem(SupportServiceType.US.targetIds);
                    ServiceGroupType.inject(service3);
                    try {
                        CompileWorker.this.getFPInfo();
                    } catch (NetworkExpertException e4) {
                        this._exception = e4;
                    }
                } catch (InterruptedException | NullPointerException unused) {
                }
            } catch (DatabaseException e5) {
                this._exception = e5;
            }
        }

        protected class UcTerms {
            public boolean isNeed = false;
            public String lastUpdateDate = null;
            public Calendar checkedDate = null;

            protected UcTerms() {
            }
        }

        protected UcTerms getUcTerms(DatabaseExpert.IdsSettings idsSettings) {
            UcTerms ucTerms = new UcTerms();
            Calendar currentCalendar = FuncUtil.getCurrentCalendar();
            ucTerms.checkedDate = currentCalendar;
            if (idsSettings != null) {
                if (CompileWorker.INITILAL_DATE_DATA.equals(idsSettings.lastUpdate)) {
                    ucTerms.isNeed = true;
                    ucTerms.lastUpdateDate = idsSettings.lastUpdate;
                } else {
                    Calendar calendarConvertFromStringToCalendar = FuncUtil.convertFromStringToCalendar(DateFormatter.DATE_MINUTE, idsSettings.lastUpdate);
                    if (calendarConvertFromStringToCalendar == null || currentCalendar.compareTo(calendarConvertFromStringToCalendar) < 0) {
                        ucTerms.isNeed = true;
                        ucTerms.lastUpdateDate = CompileWorker.INITILAL_DATE_DATA;
                    } else {
                        calendarConvertFromStringToCalendar.add(12, idsSettings.interval);
                        if (CompileWorker.this._isCheckNow || currentCalendar.compareTo(calendarConvertFromStringToCalendar) >= 0) {
                            ucTerms.isNeed = true;
                            ucTerms.lastUpdateDate = idsSettings.lastUpdate;
                        }
                    }
                }
            } else {
                ucTerms.isNeed = true;
                ucTerms.lastUpdateDate = CompileWorker.INITILAL_DATE_DATA;
            }
            return ucTerms;
        }

        protected IdsUpdateCheckProtocol.Result connectUcProtocol(NetworkExpert networkExpert, String str) throws NetworkExpertException {
            IdsUpdateCheckProtocol idsUpdateCheckProtocol = networkExpert.getIdsUpdateCheckProtocol();
            try {
                return idsUpdateCheckProtocol.parse(networkExpert.connect(idsUpdateCheckProtocol.create(new IdsUpdateCheckProtocol.Parameter(Settings.idm(), str))));
            } catch (NetworkExpertException e) {
                throw new IllegalStateException(e);
            }
        }

        protected ServiceIdsProtocol.Result connectGidProtocol(NetworkExpert networkExpert, String str, String str2) throws NetworkExpertException {
            ServiceIdsProtocol serviceIdsProtocol = networkExpert.getServiceIdsProtocol();
            try {
                return serviceIdsProtocol.parse(networkExpert.connect(serviceIdsProtocol.create(new ServiceIdsProtocol.Parameter(Settings.idm(), str, str2))));
            } catch (NetworkExpertException e) {
                throw new IllegalStateException(e);
            }
        }

        protected ServiceProtocol.Result connectSidProtocol(NetworkExpert networkExpert, String str, String str2, List<BasicElement> list) throws NetworkExpertException {
            ServiceProtocol serviceProtocol = networkExpert.getServiceProtocol();
            Collections.sort(list, new Comparator<BasicElement>() { // from class: com.felicanetworks.mfm.main.model.internal.main.CompileWorker.MainParallelWorker.1
                @Override // java.util.Comparator
                public int compare(BasicElement basicElement, BasicElement basicElement2) {
                    return basicElement.id.compareTo(basicElement2.id);
                }
            });
            ArrayList arrayList = new ArrayList();
            for (BasicElement basicElement : list) {
                arrayList.add(new ServiceProtocol.Parameter.ServiceData(basicElement.id, basicElement.version));
            }
            if (arrayList.size() > 0) {
                try {
                    return serviceProtocol.parse(networkExpert.connect(serviceProtocol.create(new ServiceProtocol.Parameter(Settings.idm(), str, str2, arrayList)), new NetworkExpert.NetworkAccessListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.CompileWorker.MainParallelWorker.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert.NetworkAccessListener
                        public void session() {
                            CompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_SID_CONNECTED);
                        }

                        @Override // com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert.NetworkAccessListener
                        public void receiveRatio(int i) {
                            Listener.Pos pos;
                            switch (i) {
                                case 1:
                                    pos = Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_1;
                                    break;
                                case 2:
                                    pos = Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_2;
                                    break;
                                case 3:
                                    pos = Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_3;
                                    break;
                                case 4:
                                    pos = Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_4;
                                    break;
                                case 5:
                                    pos = Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_5;
                                    break;
                                case 6:
                                    pos = Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_6;
                                    break;
                                case 7:
                                    pos = Listener.Pos.MYSERVICE_SIM_SID_RECEIVING_7;
                                    break;
                                default:
                                    pos = null;
                                    break;
                            }
                            if (pos != null) {
                                CompileWorker.this._listener.progress(pos);
                            }
                        }
                    }, 7));
                } catch (NetworkExpertException e) {
                    throw new IllegalStateException(e);
                }
            }
            HashMap map = new HashMap();
            map.put("content-length", "0");
            return serviceProtocol.parse(new NetworkExpert.Response(200, map, new byte[0]));
        }

        protected List<DatabaseExpert.AreaId> convertAreaIds(List<ServiceIdsProtocol.Result.AreaItem> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (ServiceIdsProtocol.Result.AreaItem areaItem : list) {
                arrayList.add(new DatabaseExpert.AreaId(areaItem.systemCode, areaItem.areaCode, areaItem.serviceId, areaItem.serviceVersion, areaItem.cacheFlg));
            }
            return arrayList;
        }

        protected List<DatabaseExpert.AppId> convertAppIds(List<ServiceIdsProtocol.Result.ApplicationItem> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (ServiceIdsProtocol.Result.ApplicationItem applicationItem : list) {
                arrayList.add(new DatabaseExpert.AppId(applicationItem.packageName, applicationItem.hashvalue, applicationItem.serviceId, applicationItem.serviceVersion));
            }
            return arrayList;
        }

        protected List<DatabaseExpert.MultiId> convertMultiIds(List<ServiceIdsProtocol.Result.MultiPurposeIdentifierItem> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (ServiceIdsProtocol.Result.MultiPurposeIdentifierItem multiPurposeIdentifierItem : list) {
                arrayList.add(new DatabaseExpert.MultiId(multiPurposeIdentifierItem.multipurposeServiceKind, multiPurposeIdentifierItem.multipurposeIdentifierCode, multiPurposeIdentifierItem.serviceId, multiPurposeIdentifierItem.serviceVersion, multiPurposeIdentifierItem.cacheFlg));
            }
            return arrayList;
        }

        protected List<DatabaseExpert.ServiceWithIcon> convertServices(List<ServiceProtocol.Result.ServiceItem> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Iterator<ServiceProtocol.Result.ServiceItem> it = list.iterator(); it.hasNext(); it = it) {
                ServiceProtocol.Result.ServiceItem next = it.next();
                ArrayList arrayList2 = arrayList;
                arrayList2.add(new DatabaseExpert.ServiceWithIcon(next.serviceId, next.serviceVersion, next.serviceName, next.serviceProviderName, next.cooperativeKind, next.packageName, next.hashValue, next.applicationKind, next.applicationUrl, next.webUrl, next.displayPriority, next.color, next.iconData, next.extensionIconData1, next.extensionIconData2, next.serviceType, next.cardCategory, next.detailImagePresence, next.cardFaceImage, next.contactName, next.contactNumber, next.contactUrl, next.contactEmail, next.linkageAppInfoList, next.linkageWebInfoList));
                arrayList = arrayList2;
            }
            return arrayList;
        }
    }
}

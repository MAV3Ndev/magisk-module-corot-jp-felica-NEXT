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

/* JADX INFO: loaded from: classes3.dex */
public class MemoryClearCompileWorker extends Thread {
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

    public interface Listener {

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

        void completed(CentralFunc.CompiledResult result, final CentralFunc.CompiledState state);

        void error(ModelErrorInfo error);

        void progress(Pos pos);
    }

    public static class Warnings {
        private List<Exception> _warnings = new ArrayList();
        public boolean isLackEmoneyInfo = false;

        public void add(Exception e) throws Exception {
            if (e instanceof MfcException) {
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[((MfcException) e).getErrorType().ordinal()];
                if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
                    this._warnings.add(e);
                    return;
                }
                throw e;
            }
            if (e instanceof NetworkExpertException) {
                this._warnings.add(e);
                return;
            }
            throw e;
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

    MemoryClearCompileWorker(ModelContext context, boolean isCheckNow, Listener listener) {
        this._context = context;
        this._isCheckNow = isCheckNow;
        this._listener = listener;
        this._db = context.getOpenedDatabaseExpert();
        this._pe = new PackageExpert(this._context);
    }

    protected MemoryClearCompileWorker() {
    }

    private void checkInterrupted() throws InterruptedException {
        if (isInterrupted()) {
            throw new InterruptedException();
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
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
                    loop1: while (true) {
                        Iterator it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            if (((ParallelWorker) it2.next()).isAlive()) {
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
                        }
                        break loop1;
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
                    for (DatabaseExpert.MultiId multiId : multiIds) {
                        if (TYPE_SAS.equals(multiId.type)) {
                            arrayList4.add(multiId.id);
                        } else if (TYPE_UICC.equals(multiId.type)) {
                            arrayList5.add(multiId.id);
                        }
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
                } catch (InterruptedException | NullPointerException unused) {
                }
            } catch (Exception e3) {
                this._listener.error(new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, new MfmException(MemoryClearCompileWorker.class, 1, e3)));
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

    private void getFPInfo() throws InterruptedException, NetworkExpertException, DatabaseException {
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

    protected List<String> searchArea(List<DatabaseExpert.AreaId> ids, List<MfcExpert.Area> src) {
        ArrayList arrayList = new ArrayList();
        if (ids != null && src != null) {
            for (DatabaseExpert.AreaId areaId : ids) {
                for (MfcExpert.Area area : src) {
                    if ((areaId.sysCode + areaId.areaCode).equals(area.sysCode + area.areaCode) && !arrayList.contains(areaId.id)) {
                        arrayList.add(areaId.id);
                    }
                }
            }
        }
        return arrayList;
    }

    protected List<String> searchSas(List<DatabaseExpert.MultiId> ids, List<MfcExpert.Sas> src) {
        ArrayList arrayList = new ArrayList();
        if (ids != null && src != null) {
            for (DatabaseExpert.MultiId multiId : ids) {
                if (TYPE_SAS.equals(multiId.type)) {
                    for (MfcExpert.Sas sas : src) {
                        if (multiId.code.equals(sas.cpidSid + sas.blockName) && !arrayList.contains(multiId.id)) {
                            arrayList.add(multiId.id);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    protected List<String> searchApp(List<DatabaseExpert.AppId> ids, List<PackageExpert.InstalledApp> src) {
        MessageDigest messageDigest;
        ArrayList arrayList = new ArrayList();
        if (ids != null && src != null) {
            for (DatabaseExpert.AppId appId : ids) {
                List<String> listFindSignatures = SpecAppSignatures.findSignatures(appId.pkgName);
                ArrayList arrayList2 = new ArrayList();
                if (!listFindSignatures.isEmpty()) {
                    arrayList2.addAll(listFindSignatures);
                } else {
                    arrayList2.add(appId.sigHash);
                }
                for (PackageExpert.InstalledApp installedApp : src) {
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

    protected List<String> pickupIds(List<MfcExpert.Asset> assets) {
        ArrayList arrayList = new ArrayList();
        if (assets != null) {
            for (MfcExpert.Asset asset : assets) {
                if (asset == null || !FeliCaParams.SERVICE_ID_DCARD.equals(asset.serviceId) || asset.isDcardMini) {
                    arrayList.add(asset.serviceId);
                }
            }
        }
        return arrayList;
    }

    protected FpServiceProtocol.Result connectFpServiceProtocol(NetworkExpert net, String apiCodeBeta, String apiCodeVersion) throws NetworkExpertException {
        FpServiceProtocol fpServiceProtocol = net.getFpServiceProtocol();
        try {
            return fpServiceProtocol.parse(net.connect(fpServiceProtocol.create(new FpServiceProtocol.Parameter(Settings.idm(), apiCodeBeta, apiCodeVersion))));
        } catch (NetworkExpertException e) {
            throw new IllegalStateException(e);
        }
    }

    protected BookmarkProtocol.Result connectRecommendProtocol(NetworkExpert net) throws NetworkExpertException {
        BookmarkProtocol bookmarkProtocol = net.getBookmarkProtocol();
        try {
            return bookmarkProtocol.parse(net.connect(bookmarkProtocol.create(new BookmarkProtocol.Parameter(Settings.idm()))));
        } catch (NetworkExpertException e) {
            throw new IllegalStateException(e);
        }
    }

    protected List<DatabaseExpert.Recommend> convertRecommends(List<BookmarkProtocol.Result.BookmarkCategoryItem> items) {
        ArrayList arrayList = new ArrayList();
        Iterator<BookmarkProtocol.Result.BookmarkCategoryItem> it = items.iterator();
        while (it.hasNext()) {
            BookmarkProtocol.Result.BookmarkCategoryItem next = it.next();
            for (BookmarkProtocol.Result.BookmarkItem bookmarkItem : next.bookmarkItemList) {
                Iterator<String> it2 = bookmarkItem.useProcedureList.iterator();
                String str = bookmarkItem.serviceId;
                String str2 = bookmarkItem.serviceVersion;
                String str3 = next.categoryId;
                String str4 = next.categoryTitle;
                String str5 = bookmarkItem.serviceName;
                String str6 = bookmarkItem.serviceProviderName;
                String str7 = bookmarkItem.bookmarkStatusKind;
                String str8 = bookmarkItem.bookmarkCooperKind;
                String str9 = bookmarkItem.bookmarkApKind;
                Iterator<BookmarkProtocol.Result.BookmarkCategoryItem> it3 = it;
                String str10 = bookmarkItem.bookmarkApUrl;
                String str11 = bookmarkItem.bookmarkWebUrl;
                String str12 = bookmarkItem.recommendedOverview;
                String str13 = bookmarkItem.recommendedDetail;
                String next2 = null;
                String next3 = it2.hasNext() ? it2.next() : null;
                String next4 = it2.hasNext() ? it2.next() : null;
                String next5 = it2.hasNext() ? it2.next() : null;
                String next6 = it2.hasNext() ? it2.next() : null;
                if (it2.hasNext()) {
                    next2 = it2.next();
                }
                arrayList.add(new DatabaseExpert.Recommend(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, next3, next4, next5, next6, next2));
                it = it3;
            }
        }
        return arrayList;
    }

    protected CentralFunc.CompiledState checkCompiledState(List<Service> allServices, List<DatabaseExpert.ServiceId> allIds, Warnings warnings) {
        boolean z = false;
        for (Service service : allServices) {
            Iterator<DatabaseExpert.ServiceId> it = allIds.iterator();
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
        return new CentralFunc.CompiledState(warnings.getFelicaState(), warnings.getNetworkState(), false, z, warnings.isLackEmoneyInfo, false);
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

        public void uncaughtException(Thread thread, Throwable ex) {
            if (ex instanceof Exception) {
                this._exception = (Exception) ex;
            }
        }
    }

    protected class MfcParallelWorker extends ParallelWorker {
        private List<MfcExpert.Area> _areas;
        private List<MfcExpert.Asset> _assets;
        private MfcExpert _mfc;
        private List<MfcExpert.Sas> _sass;

        public MfcParallelWorker(MfcExpert mfc) {
            super();
            this._mfc = mfc;
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
                MemoryClearCompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_PARALLEL_MFC_END);
                throw th;
            }
            MemoryClearCompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_PARALLEL_MFC_END);
        }
    }

    protected class PackageParallelWorker extends ParallelWorker {
        private List<PackageExpert.InstalledApp> _apps;
        private PackageExpert _pkg;

        public PackageParallelWorker(PackageExpert pkg) {
            super();
            this._pkg = pkg;
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
                MemoryClearCompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_PARALLEL_APP_END);
            }
        }
    }

    public class MainParallelWorker extends ParallelWorker {
        private String _acb;
        private String _acv;
        private IdsCage _cage;

        @Override // com.felicanetworks.mfm.main.model.internal.main.MemoryClearCompileWorker.ParallelWorker
        public /* bridge */ /* synthetic */ void throwException() throws Exception {
            super.throwException();
        }

        @Override // com.felicanetworks.mfm.main.model.internal.main.MemoryClearCompileWorker.ParallelWorker, java.lang.Thread.UncaughtExceptionHandler
        public /* bridge */ /* synthetic */ void uncaughtException(Thread thread, Throwable ex) {
            super.uncaughtException(thread, ex);
        }

        protected class BasicElement implements ServiceIdPolicy.ServiceIdHolder {
            public String id;
            public String version;

            public BasicElement(String id, String version) {
                this.id = id;
                this.version = version;
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

                public Db(List<DatabaseExpert.AreaId> areaIds, List<DatabaseExpert.AppId> appIds, List<DatabaseExpert.MultiId> multiIds) {
                    this.areaIds = areaIds;
                    this.appIds = appIds;
                    this.multiIds = multiIds;
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

                private Net(List<ServiceIdsProtocol.Result.AreaItem> areaIds, List<ServiceIdsProtocol.Result.ApplicationItem> appIds, List<ServiceIdsProtocol.Result.MultiPurposeIdentifierItem> multiIds) {
                    this.areaIds = areaIds;
                    this.appIds = appIds;
                    this.multiIds = multiIds;
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
            public void setDbIds(List<DatabaseExpert.AreaId> areaIds, List<DatabaseExpert.AppId> appIds, List<DatabaseExpert.MultiId> multiIds) {
                this._dbCage = new Db(areaIds, appIds, multiIds);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void setNetIds(List<ServiceIdsProtocol.Result.AreaItem> areaIds, List<ServiceIdsProtocol.Result.ApplicationItem> appIds, List<ServiceIdsProtocol.Result.MultiPurposeIdentifierItem> multiIds) {
                this._netCage = new Net(areaIds, appIds, multiIds);
            }
        }

        public MainParallelWorker() {
            super();
            this._cage = new IdsCage();
            TextUtils textUtils = new TextUtils(MemoryClearCompileWorker.this._context.getLegacyContext());
            this._acb = textUtils.getApiCode();
            this._acv = textUtils.getApiCodeVersion();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            ServiceIdsProtocol.Result resultConnectGidProtocol;
            List<ServiceProtocol.Result.ServiceItem> arrayList;
            try {
                try {
                    UcTerms ucTerms = getUcTerms(MemoryClearCompileWorker.this._db.getIdsSettings());
                    if (!ucTerms.isNeed) {
                        MemoryClearCompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_UC_SKIP);
                    } else {
                        try {
                            super.checkInterrupted();
                            try {
                                IdsUpdateCheckProtocol.Result resultConnectUcProtocol = connectUcProtocol(MemoryClearCompileWorker.this._context.getNetworkExpert(), ucTerms.lastUpdateDate);
                                MemoryClearCompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_UC_END);
                                super.checkInterrupted();
                                if (resultConnectUcProtocol.isNecessity()) {
                                    try {
                                        resultConnectGidProtocol = connectGidProtocol(MemoryClearCompileWorker.this._context.getNetworkExpert(), this._acb, this._acv);
                                        MemoryClearCompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_GID_END);
                                        this._cage.setNetIds(resultConnectGidProtocol.areaList, resultConnectGidProtocol.apliList, resultConnectGidProtocol.multiPurposeList);
                                    } catch (Throwable th) {
                                        MemoryClearCompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_GID_END);
                                        throw th;
                                    }
                                } else {
                                    MemoryClearCompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_GID_SKIP);
                                    resultConnectGidProtocol = null;
                                }
                                MemoryClearCompileWorker.this._db.setIds(new DatabaseExpert.IdsSettings(Integer.valueOf(resultConnectUcProtocol.updateInterval).intValue(), FuncUtil.convertFromCalendarToString(DateFormatter.DATE_MINUTE, ucTerms.checkedDate)), resultConnectGidProtocol != null ? convertAreaIds(resultConnectGidProtocol.areaList) : null, resultConnectGidProtocol != null ? convertAppIds(resultConnectGidProtocol.apliList) : null, resultConnectGidProtocol != null ? convertMultiIds(resultConnectGidProtocol.multiPurposeList) : null);
                            } catch (Throwable th2) {
                                MemoryClearCompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_UC_END);
                                throw th2;
                            }
                        } catch (NetworkExpertException e) {
                            this._exception = e;
                        }
                    }
                    if (!this._cage.isSyncServer()) {
                        this._cage.setDbIds(MemoryClearCompileWorker.this._db.getAreaIds(), MemoryClearCompileWorker.this._db.getAppIds(), MemoryClearCompileWorker.this._db.getMultiIds());
                    }
                    List<BasicElement> allIds = this._cage.getAllIds();
                    List<Service> service = MemoryClearCompileWorker.this._db.getService();
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    ArrayList arrayList5 = new ArrayList();
                    for (BasicElement basicElement : allIds) {
                        Iterator<Service> it = service.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Service next = it.next();
                            if (next.getServiceId().equals(basicElement.id)) {
                                if (next.getVersion().equals(basicElement.version)) {
                                    arrayList4.add(basicElement.id);
                                } else {
                                    arrayList2.add(basicElement.id);
                                    arrayList3.add(basicElement);
                                }
                            }
                        }
                        if (!arrayList2.contains(basicElement.id) && !arrayList4.contains(basicElement.id)) {
                            arrayList2.add(basicElement.id);
                            arrayList3.add(basicElement);
                        }
                    }
                    for (Service service2 : service) {
                        if (!arrayList2.contains(service2.getServiceId()) && !arrayList4.contains(service2.getServiceId())) {
                            arrayList5.add(service2.getServiceId());
                        }
                    }
                    try {
                        super.checkInterrupted();
                        ServiceProtocol.Result resultConnectSidProtocol = arrayList3.isEmpty() ? null : connectSidProtocol(MemoryClearCompileWorker.this._context.getNetworkExpert(), this._acb, this._acv, arrayList3);
                        if (resultConnectSidProtocol != null) {
                            arrayList = resultConnectSidProtocol.result;
                        } else {
                            arrayList = new ArrayList<>();
                        }
                        if (this._exception == null && (!arrayList.isEmpty() || !arrayList5.isEmpty())) {
                            MemoryClearCompileWorker.this._db.setServices(convertServices(arrayList), arrayList5);
                        }
                    } catch (NetworkExpertException e2) {
                        this._exception = e2;
                    }
                    try {
                        MemoryClearCompileWorker.this.getRecommendInfo();
                    } catch (NetworkExpertException e3) {
                        this._exception = e3;
                    }
                    List<Service> service3 = MemoryClearCompileWorker.this._db.getService();
                    List<DatabaseExpert.AppId> appIds = MemoryClearCompileWorker.this._db.getAppIds();
                    List<DatabaseExpert.AreaId> areaIds = MemoryClearCompileWorker.this._db.getAreaIds();
                    List<DatabaseExpert.MultiId> multiIds = MemoryClearCompileWorker.this._db.getMultiIds();
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
                        if (MemoryClearCompileWorker.TYPE_SAS.equals(multiId.type)) {
                            arrayList8.add(multiId.id);
                        } else if (MemoryClearCompileWorker.TYPE_UICC.equals(multiId.type)) {
                            arrayList9.add(multiId.id);
                        }
                    }
                    for (Service service4 : service3) {
                        String serviceId = service4.getServiceId();
                        if (serviceId.equals(FeliCaParams.SERVICE_ID_QP) && !service4.isType1()) {
                            Map<String, Object> specificColumnData = MemoryClearCompileWorker.this._db.getSpecificColumnData(serviceId, Collections.singletonList("ServiceType"));
                            if (specificColumnData.isEmpty()) {
                                break;
                            }
                            StringBuilder sb = new StringBuilder((String) Objects.requireNonNull(specificColumnData.get("ServiceType")));
                            sb.replace(0, 2, Service.SERVICE_TYPE_2_SIMPLE);
                            HashMap map = new HashMap();
                            map.put("ServiceType", sb.toString());
                            MemoryClearCompileWorker.this._db.updateSpecificColumnData(serviceId, map);
                            service3 = MemoryClearCompileWorker.this._db.getService();
                        }
                    }
                    SupportServiceType.inject(service3, arrayList6, arrayList7, arrayList8, arrayList9);
                    MemoryClearCompileWorker.this._db.delIds(SupportServiceType.US.targetIds);
                    NoticeManager.getInstance(MemoryClearCompileWorker.this._context.getAndroidContext()).deleteRegisteredServiceItem(SupportServiceType.US.targetIds);
                    ServiceGroupType.inject(service3);
                } catch (DatabaseException e4) {
                    this._exception = e4;
                }
            } catch (InterruptedException | NullPointerException unused) {
            }
        }

        protected class UcTerms {
            public boolean isNeed = false;
            public String lastUpdateDate = null;
            public Calendar checkedDate = null;

            protected UcTerms() {
            }
        }

        protected UcTerms getUcTerms(DatabaseExpert.IdsSettings setting) {
            UcTerms ucTerms = new UcTerms();
            Calendar currentCalendar = FuncUtil.getCurrentCalendar();
            ucTerms.checkedDate = currentCalendar;
            if (setting != null) {
                if (MemoryClearCompileWorker.INITILAL_DATE_DATA.equals(setting.lastUpdate)) {
                    ucTerms.isNeed = true;
                    ucTerms.lastUpdateDate = setting.lastUpdate;
                    return ucTerms;
                }
                Calendar calendarConvertFromStringToCalendar = FuncUtil.convertFromStringToCalendar(DateFormatter.DATE_MINUTE, setting.lastUpdate);
                if (calendarConvertFromStringToCalendar == null) {
                    ucTerms.isNeed = true;
                    ucTerms.lastUpdateDate = MemoryClearCompileWorker.INITILAL_DATE_DATA;
                    return ucTerms;
                }
                if (currentCalendar.compareTo(calendarConvertFromStringToCalendar) < 0) {
                    ucTerms.isNeed = true;
                    ucTerms.lastUpdateDate = MemoryClearCompileWorker.INITILAL_DATE_DATA;
                    return ucTerms;
                }
                calendarConvertFromStringToCalendar.add(12, setting.interval);
                if (!MemoryClearCompileWorker.this._isCheckNow && currentCalendar.compareTo(calendarConvertFromStringToCalendar) < 0) {
                    return ucTerms;
                }
                ucTerms.isNeed = true;
                ucTerms.lastUpdateDate = setting.lastUpdate;
                return ucTerms;
            }
            ucTerms.isNeed = true;
            ucTerms.lastUpdateDate = MemoryClearCompileWorker.INITILAL_DATE_DATA;
            return ucTerms;
        }

        protected IdsUpdateCheckProtocol.Result connectUcProtocol(NetworkExpert net, String lud) throws NetworkExpertException {
            IdsUpdateCheckProtocol idsUpdateCheckProtocol = net.getIdsUpdateCheckProtocol();
            try {
                return idsUpdateCheckProtocol.parse(net.connect(idsUpdateCheckProtocol.create(new IdsUpdateCheckProtocol.Parameter(Settings.idm(), lud))));
            } catch (NetworkExpertException e) {
                throw new IllegalStateException(e);
            }
        }

        protected ServiceIdsProtocol.Result connectGidProtocol(NetworkExpert net, String acb, String acv) throws NetworkExpertException {
            ServiceIdsProtocol serviceIdsProtocol = net.getServiceIdsProtocol();
            try {
                return serviceIdsProtocol.parse(net.connect(serviceIdsProtocol.create(new ServiceIdsProtocol.Parameter(Settings.idm(), acb, acv))));
            } catch (NetworkExpertException e) {
                throw new IllegalStateException(e);
            }
        }

        protected ServiceProtocol.Result connectSidProtocol(NetworkExpert net, String acb, String acv, List<BasicElement> requests) throws NetworkExpertException {
            ServiceProtocol serviceProtocol = net.getServiceProtocol();
            Collections.sort(requests, new Comparator<BasicElement>() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryClearCompileWorker.MainParallelWorker.1
                /* JADX DEBUG: Method merged with bridge method: compare(Ljava/lang/Object;Ljava/lang/Object;)I */
                @Override // java.util.Comparator
                public int compare(BasicElement lhs, BasicElement rhs) {
                    return lhs.id.compareTo(rhs.id);
                }
            });
            ArrayList arrayList = new ArrayList();
            for (BasicElement basicElement : requests) {
                arrayList.add(new ServiceProtocol.Parameter.ServiceData(basicElement.id, basicElement.version));
            }
            if (arrayList.size() > 0) {
                try {
                    return serviceProtocol.parse(net.connect(serviceProtocol.create(new ServiceProtocol.Parameter(Settings.idm(), acb, acv, arrayList)), new NetworkExpert.NetworkAccessListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.MemoryClearCompileWorker.MainParallelWorker.2
                        @Override // com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert.NetworkAccessListener
                        public void session() {
                            MemoryClearCompileWorker.this._listener.progress(Listener.Pos.MYSERVICE_SIM_SID_CONNECTED);
                        }

                        @Override // com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert.NetworkAccessListener
                        public void receiveRatio(int callCnt) {
                            Listener.Pos pos;
                            switch (callCnt) {
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
                                MemoryClearCompileWorker.this._listener.progress(pos);
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

        protected List<DatabaseExpert.AreaId> convertAreaIds(List<ServiceIdsProtocol.Result.AreaItem> items) {
            if (items == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (ServiceIdsProtocol.Result.AreaItem areaItem : items) {
                arrayList.add(new DatabaseExpert.AreaId(areaItem.systemCode, areaItem.areaCode, areaItem.serviceId, areaItem.serviceVersion, areaItem.cacheFlg));
            }
            return arrayList;
        }

        protected List<DatabaseExpert.AppId> convertAppIds(List<ServiceIdsProtocol.Result.ApplicationItem> items) {
            if (items == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (ServiceIdsProtocol.Result.ApplicationItem applicationItem : items) {
                arrayList.add(new DatabaseExpert.AppId(applicationItem.packageName, applicationItem.hashvalue, applicationItem.serviceId, applicationItem.serviceVersion));
            }
            return arrayList;
        }

        protected List<DatabaseExpert.MultiId> convertMultiIds(List<ServiceIdsProtocol.Result.MultiPurposeIdentifierItem> items) {
            if (items == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (ServiceIdsProtocol.Result.MultiPurposeIdentifierItem multiPurposeIdentifierItem : items) {
                arrayList.add(new DatabaseExpert.MultiId(multiPurposeIdentifierItem.multipurposeServiceKind, multiPurposeIdentifierItem.multipurposeIdentifierCode, multiPurposeIdentifierItem.serviceId, multiPurposeIdentifierItem.serviceVersion, multiPurposeIdentifierItem.cacheFlg));
            }
            return arrayList;
        }

        protected List<DatabaseExpert.ServiceWithIcon> convertServices(List<ServiceProtocol.Result.ServiceItem> items) {
            if (items == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Iterator<ServiceProtocol.Result.ServiceItem> it = items.iterator(); it.hasNext(); it = it) {
                ServiceProtocol.Result.ServiceItem next = it.next();
                arrayList.add(new DatabaseExpert.ServiceWithIcon(next.serviceId, next.serviceVersion, next.serviceName, next.serviceProviderName, next.cooperativeKind, next.packageName, next.hashValue, next.applicationKind, next.applicationUrl, next.webUrl, next.displayPriority, next.color, next.iconData, next.extensionIconData1, next.extensionIconData2, next.serviceType, next.cardCategory, next.detailImagePresence, next.cardFaceImage, next.contactName, next.contactNumber, next.contactUrl, next.contactEmail, next.linkageAppInfoList, next.linkageWebInfoList));
            }
            return arrayList;
        }
    }
}

package com.felicanetworks.mfm.main.model.internal.main;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import androidx.work.BackoffPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.felicanetworks.mfm.main.model.info.GeneralInfo;
import com.felicanetworks.mfm.main.model.info.MfiCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.MyCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.BasicAuthentication;
import com.felicanetworks.mfm.main.model.internal.main.net.ImageProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.presenter.internal.AppObserver;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
public class UpdateCardInfoWorker extends Worker {
    private static final Long DELAY_TIME = 15L;
    private static final Long DELAY_TIME_RETRY = 15L;
    private static final int RETRY_COUNT = 3;
    private static final String UNIQUE_WORKER_NAME = "UpdateCardInfoWorker";
    private static final String UPDATE_CARD_INFO_WORKER_TAG = "UpdateCardInfoWorkerTag";
    private final DatabaseExpert _db;
    private final MfcExpert _mfcExpert;
    private final ModelContext _modelContext;
    private ListenableWorker.Result _silentStartResult;

    public UpdateCardInfoWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        ModelContext modelContext = new ModelContext(context);
        this._modelContext = modelContext;
        modelContext.setNetworkExpert(new NetworkExpert(modelContext));
        this._db = getOpenedDatabaseExpert(modelContext);
        MfcExpert mfcExpert = new MfcExpert(modelContext);
        this._mfcExpert = mfcExpert;
        try {
            mfcExpert.initialize();
            ModelContext.setInitializedMfcExpert(mfcExpert);
        } catch (Exception unused) {
        }
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        try {
            ListenableWorker.Result resultUpdateCardArt = updateCardArt();
            return (!resultUpdateCardArt.equals(ListenableWorker.Result.retry()) || getRunAttemptCount() < 3) ? resultUpdateCardArt : ListenableWorker.Result.failure();
        } catch (Exception unused) {
            return ListenableWorker.Result.failure();
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IGET, INVOKE, IGET, INVOKE, IGET, INVOKE]}, finally: {[IGET, INVOKE, IGET, INVOKE, IGET, INVOKE, INVOKE, IGET, INVOKE, IF, INVOKE, IGET, INVOKE, IF, IGET, INVOKE, INVOKE, CHECK_CAST, INVOKE, INVOKE, IGET, INVOKE, IGET, INVOKE, INVOKE, CHECK_CAST, INVOKE, IF] complete} */
    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [352=7, 354=7, 357=7, 359=7, 360=7, 364=7, 366=7, 369=7, 371=4, 372=4] */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00fb, code lost:
    
        if (r2 != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0164, code lost:
    
        if (r2 != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x027d, code lost:
    
        if (r2 != null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private ListenableWorker.Result updateCardArt() {
        ListenableWorker.Result resultSuccess;
        DatabaseExpert openedDatabaseExpert;
        MyCardAdditionalInfo myCardAdditionalInfo;
        try {
            try {
                List<DatabaseExpert.BgUpdateCardArtInfo> bgUpdateCardArtInfoList = this._db.getBgUpdateCardArtInfoList();
                if (bgUpdateCardArtInfoList == null || bgUpdateCardArtInfoList.isEmpty()) {
                    resultSuccess = ListenableWorker.Result.success();
                    this._mfcExpert.finishFeliCaAccess();
                    this._mfcExpert.deinitialize();
                    NetworkExpert networkExpert = this._modelContext.getNetworkExpert();
                    if (networkExpert != null) {
                        networkExpert.disconnect();
                        this._modelContext.setNetworkExpert(null);
                    }
                    if (!((AppObserver) this._modelContext.getAndroidContext().getApplicationContext()).existServiceListActivity()) {
                        ModelContext.setInitializedMfcExpert(null);
                        openedDatabaseExpert = this._modelContext.getOpenedDatabaseExpert();
                    }
                    return resultSuccess;
                }
                this._mfcExpert.felicaStart();
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                this._mfcExpert.setSilentStartCode(0);
                this._mfcExpert.mfiStart(new MfcExpert.MfiStartListener() { // from class: com.felicanetworks.mfm.main.model.internal.main.UpdateCardInfoWorker.1
                    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                    public void onSuccess(boolean executedSilentStart) {
                        UpdateCardInfoWorker.this._silentStartResult = ListenableWorker.Result.success();
                        countDownLatch.countDown();
                    }

                    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                    public void onRequestActivity(Intent intent) {
                        UpdateCardInfoWorker.this._silentStartResult = ListenableWorker.Result.retry();
                        countDownLatch.countDown();
                    }

                    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                    public void errorResult(MfcException exception) {
                        UpdateCardInfoWorker.this._silentStartResult = ListenableWorker.Result.retry();
                        countDownLatch.countDown();
                    }

                    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.MfiStartListener
                    public void onSuccessNoLogin() {
                        UpdateCardInfoWorker.this._silentStartResult = ListenableWorker.Result.failure();
                        countDownLatch.countDown();
                    }
                });
                countDownLatch.await();
                if (!this._silentStartResult.equals(ListenableWorker.Result.success())) {
                    resultSuccess = this._silentStartResult;
                    this._mfcExpert.finishFeliCaAccess();
                    this._mfcExpert.deinitialize();
                    NetworkExpert networkExpert2 = this._modelContext.getNetworkExpert();
                    if (networkExpert2 != null) {
                        networkExpert2.disconnect();
                        this._modelContext.setNetworkExpert(null);
                    }
                    if (!((AppObserver) this._modelContext.getAndroidContext().getApplicationContext()).existServiceListActivity()) {
                        ModelContext.setInitializedMfcExpert(null);
                        openedDatabaseExpert = this._modelContext.getOpenedDatabaseExpert();
                        if (openedDatabaseExpert != null) {
                        }
                    }
                    return resultSuccess;
                }
                String strLoadMfiAccountHash = ServicePreference.getInstance().loadMfiAccountHash(this._modelContext.getAndroidContext());
                String accountHash = this._mfcExpert.getAccountHash();
                if (!TextUtils.equals(strLoadMfiAccountHash, accountHash)) {
                    this._db.deleteAccountRelatedInfo();
                    this._db.deleteCache();
                    MyServiceDatabaseAccess.getInstance().deleteRelatedToAccount();
                    NoticeDataManager.getInstance(this._modelContext.getAndroidContext()).invalidateDispService();
                    ServicePreference.getInstance().saveMfiAccountHash(this._modelContext.getAndroidContext(), accountHash);
                    resultSuccess = ListenableWorker.Result.success();
                    this._mfcExpert.finishFeliCaAccess();
                    this._mfcExpert.deinitialize();
                    NetworkExpert networkExpert3 = this._modelContext.getNetworkExpert();
                    if (networkExpert3 != null) {
                        networkExpert3.disconnect();
                        this._modelContext.setNetworkExpert(null);
                    }
                    if (!((AppObserver) this._modelContext.getAndroidContext().getApplicationContext()).existServiceListActivity()) {
                        ModelContext.setInitializedMfcExpert(null);
                        openedDatabaseExpert = this._modelContext.getOpenedDatabaseExpert();
                    }
                    return resultSuccess;
                }
                ArrayList<String> arrayList = new ArrayList();
                Iterator<DatabaseExpert.BgUpdateCardArtInfo> it = bgUpdateCardArtInfoList.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next()._cardId);
                }
                List<MyCardAdditionalInfo> listFetchCardAdditionalInfoList = this._mfcExpert.fetchCardAdditionalInfoList((String[]) arrayList.toArray(new String[0]));
                if (listFetchCardAdditionalInfoList == null) {
                    resultSuccess = ListenableWorker.Result.retry();
                    this._mfcExpert.finishFeliCaAccess();
                    this._mfcExpert.deinitialize();
                    NetworkExpert networkExpert4 = this._modelContext.getNetworkExpert();
                    if (networkExpert4 != null) {
                        networkExpert4.disconnect();
                        this._modelContext.setNetworkExpert(null);
                    }
                    if (!((AppObserver) this._modelContext.getAndroidContext().getApplicationContext()).existServiceListActivity()) {
                        ModelContext.setInitializedMfcExpert(null);
                        openedDatabaseExpert = this._modelContext.getOpenedDatabaseExpert();
                    }
                    return resultSuccess;
                }
                this._db.setMfiCardAdditionalInfo(listFetchCardAdditionalInfoList);
                for (String str : arrayList) {
                    int i = 0;
                    while (true) {
                        if (i >= listFetchCardAdditionalInfoList.size()) {
                            myCardAdditionalInfo = null;
                            break;
                        }
                        myCardAdditionalInfo = listFetchCardAdditionalInfoList.get(i);
                        if (str.equals(myCardAdditionalInfo.getCid())) {
                            listFetchCardAdditionalInfoList.remove(i);
                            break;
                        }
                        i++;
                    }
                    String str2 = GeneralInfo.SPECIAL_CARD_FACE_PREFIX + str;
                    if (myCardAdditionalInfo == null || myCardAdditionalInfo.getAdditionalInfo() == null) {
                        this._db.deleteMfiCardAdditionalInfo(str);
                        this._db.deleteRelatedCardFaceImage(str);
                    } else {
                        String str3 = myCardAdditionalInfo.getAdditionalInfo().commonInformation.generalInformation.specialCardArtUrl;
                        if (str3.isEmpty()) {
                            this._db.deleteCardFaceImage(str2);
                        } else {
                            DatabaseExpert.CardFaceImageInfo cardFaceImage = this._db.getCardFaceImage(str2);
                            if (cardFaceImage == null || !cardFaceImage._url.equals(str3)) {
                                byte[] cardImageFromServer = getCardImageFromServer(str3);
                                if (cardFaceImage == null) {
                                    this._db.addCardFaceImage(str2, str3, cardImageFromServer);
                                } else {
                                    this._db.updateCardFaceImage(str2, str3, cardImageFromServer);
                                }
                            }
                        }
                    }
                    this._db.deleteBgUpdateCardArtInfo(str);
                }
                this._mfcExpert.finishFeliCaAccess();
                this._mfcExpert.deinitialize();
                NetworkExpert networkExpert5 = this._modelContext.getNetworkExpert();
                if (networkExpert5 != null) {
                    networkExpert5.disconnect();
                    this._modelContext.setNetworkExpert(null);
                }
                if (!((AppObserver) this._modelContext.getAndroidContext().getApplicationContext()).existServiceListActivity()) {
                    ModelContext.setInitializedMfcExpert(null);
                    DatabaseExpert openedDatabaseExpert2 = this._modelContext.getOpenedDatabaseExpert();
                    if (openedDatabaseExpert2 != null) {
                        openedDatabaseExpert2.close();
                        this._modelContext.setOpenedDatabaseExpert(null);
                    }
                }
                return ListenableWorker.Result.success();
                openedDatabaseExpert.close();
                this._modelContext.setOpenedDatabaseExpert(null);
                return resultSuccess;
            } catch (Exception unused) {
                ListenableWorker.Result resultRetry = ListenableWorker.Result.retry();
                this._mfcExpert.finishFeliCaAccess();
                this._mfcExpert.deinitialize();
                NetworkExpert networkExpert6 = this._modelContext.getNetworkExpert();
                if (networkExpert6 != null) {
                    networkExpert6.disconnect();
                    this._modelContext.setNetworkExpert(null);
                }
                if (!((AppObserver) this._modelContext.getAndroidContext().getApplicationContext()).existServiceListActivity()) {
                    ModelContext.setInitializedMfcExpert(null);
                    DatabaseExpert openedDatabaseExpert3 = this._modelContext.getOpenedDatabaseExpert();
                    if (openedDatabaseExpert3 != null) {
                        openedDatabaseExpert3.close();
                        this._modelContext.setOpenedDatabaseExpert(null);
                    }
                }
                return resultRetry;
            }
        } catch (Throwable th) {
            this._mfcExpert.finishFeliCaAccess();
            this._mfcExpert.deinitialize();
            NetworkExpert networkExpert7 = this._modelContext.getNetworkExpert();
            if (networkExpert7 != null) {
                networkExpert7.disconnect();
                this._modelContext.setNetworkExpert(null);
            }
            if (!((AppObserver) this._modelContext.getAndroidContext().getApplicationContext()).existServiceListActivity()) {
                ModelContext.setInitializedMfcExpert(null);
                DatabaseExpert openedDatabaseExpert4 = this._modelContext.getOpenedDatabaseExpert();
                if (openedDatabaseExpert4 != null) {
                    openedDatabaseExpert4.close();
                    this._modelContext.setOpenedDatabaseExpert(null);
                }
            }
            throw th;
        }
    }

    private byte[] getCardImageFromServer(String url) throws Throwable {
        ImageProtocol.Parameter parameter = new ImageProtocol.Parameter(url);
        ImageProtocol imageProtocol = this._modelContext.getNetworkExpert().getImageProtocol();
        NetworkExpert.Request requestCreate = imageProtocol.create(parameter);
        if (BasicAuthentication.isNeedBasicAuthentication()) {
            requestCreate = BasicAuthentication.addBasicAuthorization(requestCreate);
        }
        return imageProtocol.parse(this._modelContext.getNetworkExpert().connect(requestCreate)).bytes;
    }

    public static void requestWorkHashedCid(final Context context, final String hashedCidEncode) {
        if (hashedCidEncode == null || hashedCidEncode.isEmpty()) {
            return;
        }
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.UpdateCardInfoWorker.2
            @Override // java.lang.Runnable
            public void run() {
                Context context2 = context;
                UpdateCardInfoWorker.requestWork(context2, UpdateCardInfoWorker.getCidFromAdditionalInfo(context2, hashedCidEncode));
            }
        }).start();
    }

    public static void requestWork(final Context context, final String cid) {
        if (cid == null || cid.isEmpty()) {
            return;
        }
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.main.UpdateCardInfoWorker.3
            @Override // java.lang.Runnable
            public void run() {
                UpdateCardInfoWorker.addBgUpdateCardArtInfo(context, cid);
                WorkManager.getInstance(context).enqueueUniqueWork(UpdateCardInfoWorker.UNIQUE_WORKER_NAME, ExistingWorkPolicy.REPLACE, new OneTimeWorkRequest.Builder((Class<? extends ListenableWorker>) UpdateCardInfoWorker.class).setInitialDelay(UpdateCardInfoWorker.DELAY_TIME.longValue(), TimeUnit.MINUTES).setBackoffCriteria(BackoffPolicy.LINEAR, UpdateCardInfoWorker.DELAY_TIME_RETRY.longValue(), TimeUnit.MINUTES).addTag(UpdateCardInfoWorker.UPDATE_CARD_INFO_WORKER_TAG).build());
            }
        }).start();
    }

    public static void cancelWork(Context context) {
        WorkManager.getInstance(context).cancelAllWorkByTag(UPDATE_CARD_INFO_WORKER_TAG);
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[INVOKE, CHECK_CAST, INVOKE]}, finally: {[INVOKE, CHECK_CAST, INVOKE, INVOKE, INVOKE, IF, IF] complete} */
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0045, code lost:
    
        r9 = r4.cardID;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getCidFromAdditionalInfo(Context context, String hashedCidEncode) {
        String str;
        ModelContext modelContext = new ModelContext(context);
        DatabaseExpert openedDatabaseExpert = getOpenedDatabaseExpert(modelContext);
        try {
            byte[] bArrDecode = Base64.decode(hashedCidEncode.getBytes(StandardCharsets.UTF_8), 8);
            Iterator<MfiCardAdditionalInfo> it = openedDatabaseExpert.getMfiCardAdditionalInfo().iterator();
            while (true) {
                if (!it.hasNext()) {
                    str = null;
                    break;
                }
                MfiCardAdditionalInfo next = it.next();
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(next.cardID.getBytes(StandardCharsets.UTF_8));
                if (Arrays.equals(bArrDecode, messageDigest.digest())) {
                    break;
                }
            }
            if (!((AppObserver) context.getApplicationContext()).existServiceListActivity() && openedDatabaseExpert != null) {
                openedDatabaseExpert.close();
                modelContext.setOpenedDatabaseExpert(null);
            }
            return str;
        } catch (Exception unused) {
            if (!((AppObserver) context.getApplicationContext()).existServiceListActivity() && openedDatabaseExpert != null) {
                openedDatabaseExpert.close();
                modelContext.setOpenedDatabaseExpert(null);
            }
            return null;
        } catch (Throwable th) {
            if (!((AppObserver) context.getApplicationContext()).existServiceListActivity() && openedDatabaseExpert != null) {
                openedDatabaseExpert.close();
                modelContext.setOpenedDatabaseExpert(null);
            }
            throw th;
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[INVOKE, CHECK_CAST, INVOKE]}, finally: {[INVOKE, CHECK_CAST, INVOKE, INVOKE, INVOKE, IF, IF] complete} */
    /* JADX INFO: Access modifiers changed from: private */
    public static void addBgUpdateCardArtInfo(Context context, String cid) {
        ModelContext modelContext = new ModelContext(context);
        DatabaseExpert openedDatabaseExpert = getOpenedDatabaseExpert(modelContext);
        try {
            if (!openedDatabaseExpert.isBgUpdateCardArtInfoExists(cid)) {
                openedDatabaseExpert.addBgUpdateCardArtInfo(cid);
            }
            if (((AppObserver) context.getApplicationContext()).existServiceListActivity() || openedDatabaseExpert == null) {
                return;
            }
            openedDatabaseExpert.close();
            modelContext.setOpenedDatabaseExpert(null);
        } catch (Exception unused) {
            if (((AppObserver) context.getApplicationContext()).existServiceListActivity() || openedDatabaseExpert == null) {
                return;
            }
            openedDatabaseExpert.close();
            modelContext.setOpenedDatabaseExpert(null);
        } catch (Throwable th) {
            if (!((AppObserver) context.getApplicationContext()).existServiceListActivity() && openedDatabaseExpert != null) {
                openedDatabaseExpert.close();
                modelContext.setOpenedDatabaseExpert(null);
            }
            throw th;
        }
    }

    private static DatabaseExpert getOpenedDatabaseExpert(ModelContext context) {
        try {
            context.setOpenedDatabaseExpert(new DatabaseExpert(context).open());
        } catch (DatabaseException unused) {
        }
        return context.getOpenedDatabaseExpert();
    }
}

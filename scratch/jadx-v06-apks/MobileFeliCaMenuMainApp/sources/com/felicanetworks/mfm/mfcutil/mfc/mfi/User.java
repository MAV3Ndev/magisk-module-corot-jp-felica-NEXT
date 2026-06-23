package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.CardAdditionalInfo;
import com.felicanetworks.mfc.mfi.CardAdditionalInfoJson;
import com.felicanetworks.mfc.mfi.CardInfo;
import com.felicanetworks.mfc.mfi.CardInfoJson;
import com.felicanetworks.mfc.mfi.CardInfoWithSpStatus;
import com.felicanetworks.mfc.mfi.CardInfoWithSpStatusJson;
import com.felicanetworks.mfc.mfi.ICardIssueV2EventCallback;
import com.felicanetworks.mfc.mfi.IDataListEventCallback;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.mfcutil.mfc.MfiClientAccess;
import com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class User {
    public static final String EXC_INVALID_CALLBACK = "The specified Callback is null.";
    public static final String EXC_INVALID_CID_LIST = "The specified cidList is null or invalid.";
    public static final String EXC_INVALID_LINKAGE_DATA = "The specified LinkageData is null or invalid.";
    public static final String EXC_INVALID_OTP = "The specified Otp is null or invalid.";
    public static final String EXC_INVALID_SERVICE_ID = "The specified serviceId is null or invalid.";
    private static final int LEN_CID = 63;
    private static final int LEN_SERVICE_ID = 8;
    private static final String PIPE_IO_EXCEPTION_MESSAGE_INTERRUPTED = "MFI_INTERRUPTED";
    private static final String PIPE_IO_EXCEPTION_MESSAGE_STOPPED = "MFI_STOPPED";
    private static final String REGEX_ALPHANUMERIC = "[0-9a-zA-Z]*";
    private final MfiClientAccess mMfiClientAccess;

    User(MfiClientAccess mfiClientAccess) {
        LogMgr.log(7, "%s", "000");
        this.mMfiClientAccess = mfiClientAccess;
        LogMgr.log(7, "%s", "999");
    }

    public synchronized void getCardList(CardListEventCallback cardListEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (cardListEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "700");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkUserLoggedIn(this);
        this.mMfiClientAccess.startOnline(cardListEventCallback);
        try {
            try {
                if (this.mMfiClientAccess.isDataListEventSupported()) {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getCardListV3(new LocalICardListV3EventCallback()));
                } else {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getCardListV2(new LocalICardListV2EventCallback()));
                }
                LogMgr.log(3, "%s", "999");
            } catch (Exception e) {
                LogMgr.log(2, "%s %s", "703", "Other Exception");
                LogMgr.printStackTrace(7, e);
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "701", e2.toString(), Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e2);
        } catch (IllegalArgumentException e3) {
            LogMgr.log(2, "%s %s", "702", e3.toString());
            this.mMfiClientAccess.stopOnline();
            throw e3;
        }
    }

    public synchronized void getCardAdditionalInfoList(String[] strArr, CardAdditionalInfoListEventCallback cardAdditionalInfoListEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (strArr == null) {
            LogMgr.log(2, "704 cidList is null");
            throw new IllegalArgumentException("The specified cidList is null or invalid.");
        }
        if (strArr.length == 0) {
            LogMgr.log(2, "705 cidList length = 0");
            throw new IllegalArgumentException("The specified cidList is null or invalid.");
        }
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i] == null) {
                LogMgr.log(2, "706 cid is null");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            if (!strArr[i].matches("[0-9a-zA-Z]*")) {
                LogMgr.log(2, "707 cid involves invalid character.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            if (strArr[i].length() != 63) {
                LogMgr.log(2, "708 cid length is invalid.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
        }
        if (cardAdditionalInfoListEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "700");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkUserLoggedIn(this);
        this.mMfiClientAccess.startOnline(cardAdditionalInfoListEventCallback);
        try {
            try {
                if (this.mMfiClientAccess.isDataListEventSupported()) {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getCardAdditionalInfoListV3(strArr, new LocalICardAdditionalInfoListV3EventCallback()));
                } else {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getCardAdditionalInfoListWithCidList(strArr, new LocalICardAdditionalInfoListV2EventCallback()));
                }
                LogMgr.log(3, "%s", "999");
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "%s %s", "702", e.toString());
                this.mMfiClientAccess.stopOnline();
                throw e;
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "701", e2.toString(), Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e2);
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s", "703", "Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void getCardInfoListWithSpStatus(String str, CardInfoListWithSpStatusEventCallback cardInfoListWithSpStatusEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (str == null) {
            LogMgr.log(2, "700 serviceId is null.");
            throw new IllegalArgumentException(EXC_INVALID_SERVICE_ID);
        }
        if (!str.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "701 serviceId involves invalid character.");
            throw new IllegalArgumentException(EXC_INVALID_SERVICE_ID);
        }
        if (str.length() != 8) {
            LogMgr.log(2, "702 serviceId length is invalid.");
            throw new IllegalArgumentException(EXC_INVALID_SERVICE_ID);
        }
        if (cardInfoListWithSpStatusEventCallback == null) {
            LogMgr.log(2, "703 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkUserLoggedIn(this);
        this.mMfiClientAccess.startOnline(cardInfoListWithSpStatusEventCallback);
        try {
            try {
                if (this.mMfiClientAccess.isDataListEventSupported()) {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getCardInfoListWithSpStatusV3(str, new LocalICardInfoListWithSpStatusV3EventCallback()));
                } else {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getCardInfoListWithSpStatus(str, new LocalICardInfoListWithSpStatusEventCallback()));
                }
                LogMgr.log(3, "999");
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "705 " + e.toString());
                this.mMfiClientAccess.stopOnline();
                throw e;
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "704 " + e2.toString() + " id:" + e2.getID() + " type:" + e2.getType());
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e2);
        } catch (Exception e3) {
            LogMgr.log(2, "706 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void issueCard(String str, CardIssueEventCallback cardIssueEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (str == null) {
            LogMgr.log(2, "%s linkageData is null.", "700");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "%s linkageData is empty.", "701");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (cardIssueEventCallback == null) {
            LogMgr.log(2, "%s callback is null.", "702");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkUserLoggedIn(this);
        this.mMfiClientAccess.startOnline(cardIssueEventCallback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().issueCardV2(str, new LocalICardIssueV2EventCallback()));
                LogMgr.log(3, "%s", "999");
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "%s %s", "704", e.toString());
                this.mMfiClientAccess.stopOnline();
                throw e;
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "703", e2.toString(), Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e2);
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s", "705", "Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    public synchronized void issueCard(String str, String str2, CardIssueEventCallback cardIssueEventCallback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (str == null) {
            LogMgr.log(2, "700 linkageData is null.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgr.log(2, "701 linkageData is empty.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str2 == null) {
            LogMgr.log(2, "702 otp is null.");
            throw new IllegalArgumentException("The specified Otp is null or invalid.");
        }
        if (str2.isEmpty()) {
            LogMgr.log(2, "703 otp is empty.");
            throw new IllegalArgumentException("The specified Otp is null or invalid.");
        }
        if (cardIssueEventCallback == null) {
            LogMgr.log(2, "704 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkUserLoggedIn(this);
        this.mMfiClientAccess.startOnline(cardIssueEventCallback);
        try {
            try {
                try {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().issueCardWithOtp(str, str2, new LocalICardIssueV2EventCallback()));
                    LogMgr.log(3, "999");
                } catch (IllegalArgumentException e) {
                    LogMgr.log(2, "706 " + e.toString());
                    this.mMfiClientAccess.stopOnline();
                    throw e;
                }
            } catch (FelicaException e2) {
                LogMgr.log(2, "705 " + e2.toString() + " id:" + e2.getID() + " type:" + e2.getType());
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(e2);
            }
        } catch (Exception e3) {
            LogMgr.log(2, "707 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    abstract class LocalIPipeEventCallback<T> extends IPipeEventCallback.Stub {
        abstract void onSuccess(List<T> list);

        /* JADX INFO: renamed from: parse */
        abstract T parse2(String str) throws JSONException;

        LocalIPipeEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.IPipeEventCallback
        public void onStart(final ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
            LogMgr.log(3, "000 fd=" + parcelFileDescriptor);
            ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
            try {
                executorServiceNewSingleThreadExecutor.submit(new Runnable() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback.1
                    /* JADX WARN: Removed duplicated region for block: B:68:0x011f  */
                    /* JADX WARN: Removed duplicated region for block: B:76:0x014d  */
                    /* JADX WARN: Removed duplicated region for block: B:79:0x0153  */
                    /* JADX WARN: Removed duplicated region for block: B:99:0x0159 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct add '--show-bad-code' argument
                    */
                    public void run() {
                        /*
                            Method dump skipped, instruction units count: 382
                            To view this dump add '--comments-level debug' option
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback.AnonymousClass1.run():void");
                    }
                });
                LogMgr.log(3, "001");
                executorServiceNewSingleThreadExecutor.shutdown();
                LogMgr.log(3, "999");
            } catch (Throwable th) {
                LogMgr.log(3, "001");
                executorServiceNewSingleThreadExecutor.shutdown();
                throw th;
            }
        }

        @Override // com.felicanetworks.mfc.mfi.IPipeEventCallback
        public void onError(int i, String str) throws RemoteException {
            onErrorInner(i, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onErrorInner(int i, String str) {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(i, str);
                    } catch (Exception e) {
                        LogMgr.log(2, "700 " + e.getMessage());
                    }
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800 " + e2.getMessage());
            }
            LogMgr.log(3, "999");
        }
    }

    class LocalICardListV2EventCallback extends LocalIPipeEventCallback<CardInfo> {
        LocalICardListV2EventCallback() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback
        /* JADX INFO: renamed from: parse */
        public CardInfo parse2(String str) throws JSONException {
            LogMgr.log(3, "000");
            CardInfo cardInfo = new CardInfoJson(str).getCardInfo();
            LogMgr.log(3, "999");
            return cardInfo;
        }

        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback
        void onSuccess(List<CardInfo> list) {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallback = null;
            Card[] cardArr = null;
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                if (list != null) {
                    try {
                        LogMgr.log(6, "001");
                        int size = list.size();
                        Card[] cardArr2 = new Card[size];
                        for (int i = 0; i < size; i++) {
                            if (list.get(i) != null) {
                                cardArr2[i] = new Card(list.get(i), User.this.mMfiClientAccess);
                            }
                        }
                        cardArr = cardArr2;
                    } catch (Exception e) {
                        e = e;
                        baseMfiEventCallback = baseMfiEventCallbackStopOnline;
                        LogMgr.log(1, "800 " + e.getMessage());
                        if (baseMfiEventCallback != null) {
                            try {
                                baseMfiEventCallback.onError(200, "Unknown error.");
                            } catch (Exception unused) {
                                LogMgr.log(2, "701 " + e.getMessage());
                            }
                        }
                    }
                }
                if (baseMfiEventCallbackStopOnline != null && (baseMfiEventCallbackStopOnline instanceof CardListEventCallback)) {
                    LogMgr.log(6, "002");
                    try {
                        ((CardListEventCallback) baseMfiEventCallbackStopOnline).onSuccess(cardArr);
                    } catch (Exception e2) {
                        LogMgr.log(2, "700 " + e2.getMessage());
                    }
                } else {
                    LogMgr.log(6, "003");
                }
            } catch (Exception e3) {
                e = e3;
            }
            LogMgr.log(3, "999");
        }
    }

    class LocalICardAdditionalInfoListV2EventCallback extends LocalIPipeEventCallback<CardAdditionalInfo> {
        LocalICardAdditionalInfoListV2EventCallback() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback
        /* JADX INFO: renamed from: parse */
        public CardAdditionalInfo parse2(String str) throws JSONException {
            LogMgr.log(3, "000");
            CardAdditionalInfo cardAdditionalInfo = new CardAdditionalInfoJson(str).getCardAdditionalInfo();
            LogMgr.log(3, "999");
            return cardAdditionalInfo;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x004e A[Catch: Exception -> 0x0054, TRY_LEAVE, TryCatch #3 {Exception -> 0x0054, blocks: (B:6:0x0015, B:8:0x0019, B:14:0x0035, B:15:0x004e, B:10:0x0023, B:11:0x0030), top: B:34:0x0015, inners: #2 }] */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        void onSuccess(java.util.List<com.felicanetworks.mfc.mfi.CardAdditionalInfo> r7) {
            /*
                r6 = this;
                r0 = 3
                java.lang.String r1 = "000"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r0, r1)
                r1 = 2
                r2 = 0
                com.felicanetworks.mfm.mfcutil.mfc.mfi.User r3 = com.felicanetworks.mfm.mfcutil.mfc.mfi.User.this     // Catch: java.lang.Exception -> L57
                com.felicanetworks.mfm.mfcutil.mfc.MfiClientAccess r3 = com.felicanetworks.mfm.mfcutil.mfc.mfi.User.access$100(r3)     // Catch: java.lang.Exception -> L57
                com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback r3 = r3.stopOnline()     // Catch: java.lang.Exception -> L57
                r4 = 6
                if (r3 == 0) goto L4e
                boolean r5 = r3 instanceof com.felicanetworks.mfm.mfcutil.mfc.mfi.CardAdditionalInfoListEventCallback     // Catch: java.lang.Exception -> L54
                if (r5 == 0) goto L4e
                java.lang.String r5 = "001"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r4, r5)     // Catch: java.lang.Exception -> L54
                r4 = r3
                com.felicanetworks.mfm.mfcutil.mfc.mfi.CardAdditionalInfoListEventCallback r4 = (com.felicanetworks.mfm.mfcutil.mfc.mfi.CardAdditionalInfoListEventCallback) r4     // Catch: java.lang.Exception -> L54
                if (r7 == 0) goto L30
                int r2 = r7.size()     // Catch: java.lang.Exception -> L34
                com.felicanetworks.mfc.mfi.CardAdditionalInfo[] r2 = new com.felicanetworks.mfc.mfi.CardAdditionalInfo[r2]     // Catch: java.lang.Exception -> L34
                java.lang.Object[] r7 = r7.toArray(r2)     // Catch: java.lang.Exception -> L34
                r2 = r7
                com.felicanetworks.mfc.mfi.CardAdditionalInfo[] r2 = (com.felicanetworks.mfc.mfi.CardAdditionalInfo[]) r2     // Catch: java.lang.Exception -> L34
            L30:
                r4.onSuccess(r2)     // Catch: java.lang.Exception -> L34
                goto L93
            L34:
                r7 = move-exception
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L54
                r2.<init>()     // Catch: java.lang.Exception -> L54
                java.lang.String r4 = "700 "
                r2.append(r4)     // Catch: java.lang.Exception -> L54
                java.lang.String r7 = r7.getMessage()     // Catch: java.lang.Exception -> L54
                r2.append(r7)     // Catch: java.lang.Exception -> L54
                java.lang.String r7 = r2.toString()     // Catch: java.lang.Exception -> L54
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r1, r7)     // Catch: java.lang.Exception -> L54
                goto L93
            L4e:
                java.lang.String r7 = "002"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r4, r7)     // Catch: java.lang.Exception -> L54
                goto L93
            L54:
                r7 = move-exception
                r2 = r3
                goto L58
            L57:
                r7 = move-exception
            L58:
                r3 = 1
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "800 "
                r4.append(r5)
                java.lang.String r5 = r7.getMessage()
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r3, r4)
                if (r2 == 0) goto L93
                r3 = 200(0xc8, float:2.8E-43)
                java.lang.String r4 = "Unknown error."
                r2.onError(r3, r4)     // Catch: java.lang.Exception -> L7b
                goto L93
            L7b:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "701 "
                r2.append(r3)
                java.lang.String r7 = r7.getMessage()
                r2.append(r7)
                java.lang.String r7 = r2.toString()
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r1, r7)
            L93:
                java.lang.String r7 = "999"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r0, r7)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalICardAdditionalInfoListV2EventCallback.onSuccess(java.util.List):void");
        }
    }

    class LocalICardInfoListWithSpStatusEventCallback extends LocalIPipeEventCallback<CardInfo> {
        LocalICardInfoListWithSpStatusEventCallback() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback
        /* JADX INFO: renamed from: parse, reason: merged with bridge method [inline-methods] */
        public CardInfo parse2(String str) throws JSONException {
            LogMgr.log(3, "000");
            CardInfoWithSpStatus cardInfoWithSpStatus = new CardInfoWithSpStatusJson(str).getCardInfoWithSpStatus();
            LogMgr.log(3, "999");
            return cardInfoWithSpStatus;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x004e A[Catch: Exception -> 0x0054, TRY_LEAVE, TryCatch #3 {Exception -> 0x0054, blocks: (B:6:0x0015, B:8:0x0019, B:14:0x0035, B:15:0x004e, B:10:0x0023, B:11:0x0030), top: B:34:0x0015, inners: #2 }] */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        void onSuccess(java.util.List<com.felicanetworks.mfc.mfi.CardInfo> r7) {
            /*
                r6 = this;
                r0 = 3
                java.lang.String r1 = "000"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r0, r1)
                r1 = 2
                r2 = 0
                com.felicanetworks.mfm.mfcutil.mfc.mfi.User r3 = com.felicanetworks.mfm.mfcutil.mfc.mfi.User.this     // Catch: java.lang.Exception -> L57
                com.felicanetworks.mfm.mfcutil.mfc.MfiClientAccess r3 = com.felicanetworks.mfm.mfcutil.mfc.mfi.User.access$100(r3)     // Catch: java.lang.Exception -> L57
                com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback r3 = r3.stopOnline()     // Catch: java.lang.Exception -> L57
                r4 = 6
                if (r3 == 0) goto L4e
                boolean r5 = r3 instanceof com.felicanetworks.mfm.mfcutil.mfc.mfi.CardInfoListWithSpStatusEventCallback     // Catch: java.lang.Exception -> L54
                if (r5 == 0) goto L4e
                java.lang.String r5 = "002"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r4, r5)     // Catch: java.lang.Exception -> L54
                r4 = r3
                com.felicanetworks.mfm.mfcutil.mfc.mfi.CardInfoListWithSpStatusEventCallback r4 = (com.felicanetworks.mfm.mfcutil.mfc.mfi.CardInfoListWithSpStatusEventCallback) r4     // Catch: java.lang.Exception -> L54
                if (r7 == 0) goto L30
                int r2 = r7.size()     // Catch: java.lang.Exception -> L34
                com.felicanetworks.mfc.mfi.CardInfoWithSpStatus[] r2 = new com.felicanetworks.mfc.mfi.CardInfoWithSpStatus[r2]     // Catch: java.lang.Exception -> L34
                java.lang.Object[] r7 = r7.toArray(r2)     // Catch: java.lang.Exception -> L34
                r2 = r7
                com.felicanetworks.mfc.mfi.CardInfoWithSpStatus[] r2 = (com.felicanetworks.mfc.mfi.CardInfoWithSpStatus[]) r2     // Catch: java.lang.Exception -> L34
            L30:
                r4.onSuccess(r2)     // Catch: java.lang.Exception -> L34
                goto L93
            L34:
                r7 = move-exception
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L54
                r2.<init>()     // Catch: java.lang.Exception -> L54
                java.lang.String r4 = "700 "
                r2.append(r4)     // Catch: java.lang.Exception -> L54
                java.lang.String r7 = r7.getMessage()     // Catch: java.lang.Exception -> L54
                r2.append(r7)     // Catch: java.lang.Exception -> L54
                java.lang.String r7 = r2.toString()     // Catch: java.lang.Exception -> L54
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r1, r7)     // Catch: java.lang.Exception -> L54
                goto L93
            L4e:
                java.lang.String r7 = "003"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r4, r7)     // Catch: java.lang.Exception -> L54
                goto L93
            L54:
                r7 = move-exception
                r2 = r3
                goto L58
            L57:
                r7 = move-exception
            L58:
                r3 = 1
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "800 "
                r4.append(r5)
                java.lang.String r5 = r7.getMessage()
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r3, r4)
                if (r2 == 0) goto L93
                r3 = 200(0xc8, float:2.8E-43)
                java.lang.String r4 = "Unknown error."
                r2.onError(r3, r4)     // Catch: java.lang.Exception -> L7b
                goto L93
            L7b:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "701 "
                r2.append(r3)
                java.lang.String r7 = r7.getMessage()
                r2.append(r7)
                java.lang.String r7 = r2.toString()
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r1, r7)
            L93:
                java.lang.String r7 = "999"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r0, r7)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalICardInfoListWithSpStatusEventCallback.onSuccess(java.util.List):void");
        }
    }

    class LocalICardIssueV2EventCallback extends ICardIssueV2EventCallback.Stub {
        LocalICardIssueV2EventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.ICardIssueV2EventCallback
        public void onSuccess(String str) throws RemoteException {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallback = null;
            Card card = null;
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                if (str != null) {
                    try {
                        LogMgr.log(6, "001");
                        card = new Card(new CardInfoJson(str).getCardInfo(), User.this.mMfiClientAccess);
                    } catch (Exception e) {
                        e = e;
                        baseMfiEventCallback = baseMfiEventCallbackStopOnline;
                        LogMgr.log(1, "800 " + e.getMessage());
                        if (baseMfiEventCallback != null) {
                            try {
                                baseMfiEventCallback.onError(200, "Unknown error.");
                            } catch (Exception unused) {
                                LogMgr.log(2, "701 " + e.getMessage());
                            }
                        }
                    }
                }
                if (baseMfiEventCallbackStopOnline != null && (baseMfiEventCallbackStopOnline instanceof CardIssueEventCallback)) {
                    LogMgr.log(6, "002");
                    try {
                        ((CardIssueEventCallback) baseMfiEventCallbackStopOnline).onSuccess(card);
                    } catch (Exception e2) {
                        LogMgr.log(2, "700 " + e2.getMessage());
                    }
                } else {
                    LogMgr.log(6, "003");
                }
            } catch (Exception e3) {
                e = e3;
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ICardIssueV2EventCallback
        public void onError(int i, String str) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(i, str);
                    } catch (Exception e) {
                        LogMgr.log(2, "700 " + e.getMessage());
                    }
                }
            } catch (Exception e2) {
                LogMgr.log(1, "800 " + e2.getMessage());
            }
            LogMgr.log(3, "999");
        }
    }

    abstract class LocalIDataListEventCallback<T> extends IDataListEventCallback.Stub {
        final List<String> mDataList = Collections.synchronizedList(new ArrayList());

        abstract void onSuccess(List<T> list);

        abstract T parse(String str) throws JSONException;

        LocalIDataListEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
        public void onPart(List<String> list, boolean z) {
            LogMgr.log(3, "000 more=" + z);
            this.mDataList.addAll(list);
            if (!z) {
                LogMgr.log(6, "001 size=" + this.mDataList.size());
                LogMgr.log(6, "002 wait for onFinished.");
            } else {
                LogMgr.log(6, "004 wait for next dataList.");
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
        public void onFinished(final int i) {
            LogMgr.log(3, "000 size=" + i);
            new Thread(new Runnable() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback.1
                /* JADX WARN: Removed duplicated region for block: B:34:0x00fa  */
                /* JADX WARN: Removed duplicated region for block: B:42:0x0100 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public void run() {
                    /*
                        Method dump skipped, instruction units count: 296
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback.AnonymousClass1.run():void");
                }
            }).start();
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
        public void onError(int i, String str) throws RemoteException {
            onErrorInner(i, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onErrorInner(int i, String str) {
            LogMgr.log(3, "000");
            try {
                try {
                    BaseMfiEventCallback baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                    if (baseMfiEventCallbackStopOnline != null) {
                        LogMgr.log(6, "001");
                        try {
                            baseMfiEventCallbackStopOnline.onError(i, str);
                        } catch (Exception e) {
                            LogMgr.log(2, "700 " + e.getMessage());
                        }
                    }
                } catch (Exception e2) {
                    LogMgr.log(1, "800 " + e2.getMessage());
                }
                LogMgr.log(3, "999");
            } finally {
                this.mDataList.clear();
            }
        }
    }

    class LocalICardListV3EventCallback extends LocalIDataListEventCallback<CardInfo> {
        LocalICardListV3EventCallback() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback
        public CardInfo parse(String str) throws JSONException {
            LogMgr.log(3, "000");
            CardInfo cardInfo = new CardInfoJson(str).getCardInfo();
            LogMgr.log(3, "999");
            return cardInfo;
        }

        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback
        public void onSuccess(List<CardInfo> list) {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallback = null;
            Card[] cardArr = null;
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                if (list != null) {
                    try {
                        LogMgr.log(6, "001");
                        int size = list.size();
                        Card[] cardArr2 = new Card[size];
                        for (int i = 0; i < size; i++) {
                            if (list.get(i) != null) {
                                cardArr2[i] = new Card(list.get(i), User.this.mMfiClientAccess);
                            }
                        }
                        cardArr = cardArr2;
                    } catch (Exception e) {
                        e = e;
                        baseMfiEventCallback = baseMfiEventCallbackStopOnline;
                        LogMgr.log(1, "800 " + e.getMessage());
                        if (baseMfiEventCallback != null) {
                            try {
                                baseMfiEventCallback.onError(200, "Unknown error.");
                            } catch (Exception unused) {
                                LogMgr.log(2, "701 " + e.getMessage());
                            }
                        }
                    }
                }
                if (baseMfiEventCallbackStopOnline instanceof CardListEventCallback) {
                    LogMgr.log(6, "002");
                    CardListEventCallback cardListEventCallback = (CardListEventCallback) baseMfiEventCallbackStopOnline;
                    try {
                        if (cardArr != null) {
                            cardListEventCallback.onSuccess(cardArr);
                        } else {
                            baseMfiEventCallbackStopOnline.onError(200, "Unknown error.");
                        }
                    } catch (Exception e2) {
                        LogMgr.log(2, "700 " + e2.getMessage());
                    }
                } else {
                    LogMgr.log(6, "003");
                }
            } catch (Exception e3) {
                e = e3;
            }
            LogMgr.log(3, "999");
        }
    }

    class LocalICardAdditionalInfoListV3EventCallback extends LocalIDataListEventCallback<CardAdditionalInfo> {
        LocalICardAdditionalInfoListV3EventCallback() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback
        public CardAdditionalInfo parse(String str) throws JSONException {
            LogMgr.log(3, "000");
            CardAdditionalInfo cardAdditionalInfo = new CardAdditionalInfoJson(str).getCardAdditionalInfo();
            LogMgr.log(3, "999");
            return cardAdditionalInfo;
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0034 A[Catch: Exception -> 0x0030, TryCatch #0 {Exception -> 0x0030, blocks: (B:9:0x0026, B:13:0x0034, B:14:0x0038), top: B:29:0x0026, outer: #3 }] */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0038 A[Catch: Exception -> 0x0030, TRY_LEAVE, TryCatch #0 {Exception -> 0x0030, blocks: (B:9:0x0026, B:13:0x0034, B:14:0x0038), top: B:29:0x0026, outer: #3 }] */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onSuccess(java.util.List<com.felicanetworks.mfc.mfi.CardAdditionalInfo> r9) {
            /*
                r8 = this;
                java.lang.String r0 = "Unknown error."
                r1 = 3
                java.lang.String r2 = "000"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r1, r2)
                r2 = 2
                r3 = 200(0xc8, float:2.8E-43)
                r4 = 0
                com.felicanetworks.mfm.mfcutil.mfc.mfi.User r5 = com.felicanetworks.mfm.mfcutil.mfc.mfi.User.this     // Catch: java.lang.Exception -> L5e
                com.felicanetworks.mfm.mfcutil.mfc.MfiClientAccess r5 = com.felicanetworks.mfm.mfcutil.mfc.mfi.User.access$100(r5)     // Catch: java.lang.Exception -> L5e
                com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback r5 = r5.stopOnline()     // Catch: java.lang.Exception -> L5e
                boolean r6 = r5 instanceof com.felicanetworks.mfm.mfcutil.mfc.mfi.CardAdditionalInfoListEventCallback     // Catch: java.lang.Exception -> L5b
                r7 = 6
                if (r6 == 0) goto L55
                java.lang.String r6 = "002"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r7, r6)     // Catch: java.lang.Exception -> L5b
                r6 = r5
                com.felicanetworks.mfm.mfcutil.mfc.mfi.CardAdditionalInfoListEventCallback r6 = (com.felicanetworks.mfm.mfcutil.mfc.mfi.CardAdditionalInfoListEventCallback) r6     // Catch: java.lang.Exception -> L5b
                if (r9 == 0) goto L32
                r4 = 0
                com.felicanetworks.mfc.mfi.CardAdditionalInfo[] r4 = new com.felicanetworks.mfc.mfi.CardAdditionalInfo[r4]     // Catch: java.lang.Exception -> L30
                java.lang.Object[] r9 = r9.toArray(r4)     // Catch: java.lang.Exception -> L30
                r4 = r9
                com.felicanetworks.mfc.mfi.CardAdditionalInfo[] r4 = (com.felicanetworks.mfc.mfi.CardAdditionalInfo[]) r4     // Catch: java.lang.Exception -> L30
                goto L32
            L30:
                r9 = move-exception
                goto L3c
            L32:
                if (r4 == 0) goto L38
                r6.onSuccess(r4)     // Catch: java.lang.Exception -> L30
                goto L96
            L38:
                r5.onError(r3, r0)     // Catch: java.lang.Exception -> L30
                goto L96
            L3c:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L5b
                r4.<init>()     // Catch: java.lang.Exception -> L5b
                java.lang.String r6 = "700 "
                r4.append(r6)     // Catch: java.lang.Exception -> L5b
                java.lang.String r9 = r9.getMessage()     // Catch: java.lang.Exception -> L5b
                r4.append(r9)     // Catch: java.lang.Exception -> L5b
                java.lang.String r9 = r4.toString()     // Catch: java.lang.Exception -> L5b
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r2, r9)     // Catch: java.lang.Exception -> L5b
                goto L96
            L55:
                java.lang.String r9 = "003"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r7, r9)     // Catch: java.lang.Exception -> L5b
                goto L96
            L5b:
                r9 = move-exception
                r4 = r5
                goto L5f
            L5e:
                r9 = move-exception
            L5f:
                r5 = 1
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "800 "
                r6.append(r7)
                java.lang.String r7 = r9.getMessage()
                r6.append(r7)
                java.lang.String r6 = r6.toString()
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r5, r6)
                if (r4 == 0) goto L96
                r4.onError(r3, r0)     // Catch: java.lang.Exception -> L7e
                goto L96
            L7e:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r3 = "701 "
                r0.append(r3)
                java.lang.String r9 = r9.getMessage()
                r0.append(r9)
                java.lang.String r9 = r0.toString()
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r2, r9)
            L96:
                java.lang.String r9 = "999"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r1, r9)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalICardAdditionalInfoListV3EventCallback.onSuccess(java.util.List):void");
        }
    }

    class LocalICardInfoListWithSpStatusV3EventCallback extends LocalIDataListEventCallback<CardInfoWithSpStatus> {
        LocalICardInfoListWithSpStatusV3EventCallback() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback
        public CardInfoWithSpStatus parse(String str) throws JSONException {
            LogMgr.log(3, "000");
            CardInfoWithSpStatus cardInfoWithSpStatus = new CardInfoWithSpStatusJson(str).getCardInfoWithSpStatus();
            LogMgr.log(3, "999");
            return cardInfoWithSpStatus;
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0034 A[Catch: Exception -> 0x0030, TryCatch #0 {Exception -> 0x0030, blocks: (B:9:0x0026, B:13:0x0034, B:14:0x0038), top: B:29:0x0026, outer: #3 }] */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0038 A[Catch: Exception -> 0x0030, TRY_LEAVE, TryCatch #0 {Exception -> 0x0030, blocks: (B:9:0x0026, B:13:0x0034, B:14:0x0038), top: B:29:0x0026, outer: #3 }] */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onSuccess(java.util.List<com.felicanetworks.mfc.mfi.CardInfoWithSpStatus> r9) {
            /*
                r8 = this;
                java.lang.String r0 = "Unknown error."
                r1 = 3
                java.lang.String r2 = "000"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r1, r2)
                r2 = 2
                r3 = 200(0xc8, float:2.8E-43)
                r4 = 0
                com.felicanetworks.mfm.mfcutil.mfc.mfi.User r5 = com.felicanetworks.mfm.mfcutil.mfc.mfi.User.this     // Catch: java.lang.Exception -> L5e
                com.felicanetworks.mfm.mfcutil.mfc.MfiClientAccess r5 = com.felicanetworks.mfm.mfcutil.mfc.mfi.User.access$100(r5)     // Catch: java.lang.Exception -> L5e
                com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback r5 = r5.stopOnline()     // Catch: java.lang.Exception -> L5e
                boolean r6 = r5 instanceof com.felicanetworks.mfm.mfcutil.mfc.mfi.CardInfoListWithSpStatusEventCallback     // Catch: java.lang.Exception -> L5b
                r7 = 6
                if (r6 == 0) goto L55
                java.lang.String r6 = "002"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r7, r6)     // Catch: java.lang.Exception -> L5b
                r6 = r5
                com.felicanetworks.mfm.mfcutil.mfc.mfi.CardInfoListWithSpStatusEventCallback r6 = (com.felicanetworks.mfm.mfcutil.mfc.mfi.CardInfoListWithSpStatusEventCallback) r6     // Catch: java.lang.Exception -> L5b
                if (r9 == 0) goto L32
                r4 = 0
                com.felicanetworks.mfc.mfi.CardInfoWithSpStatus[] r4 = new com.felicanetworks.mfc.mfi.CardInfoWithSpStatus[r4]     // Catch: java.lang.Exception -> L30
                java.lang.Object[] r9 = r9.toArray(r4)     // Catch: java.lang.Exception -> L30
                r4 = r9
                com.felicanetworks.mfc.mfi.CardInfoWithSpStatus[] r4 = (com.felicanetworks.mfc.mfi.CardInfoWithSpStatus[]) r4     // Catch: java.lang.Exception -> L30
                goto L32
            L30:
                r9 = move-exception
                goto L3c
            L32:
                if (r4 == 0) goto L38
                r6.onSuccess(r4)     // Catch: java.lang.Exception -> L30
                goto L96
            L38:
                r5.onError(r3, r0)     // Catch: java.lang.Exception -> L30
                goto L96
            L3c:
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L5b
                r4.<init>()     // Catch: java.lang.Exception -> L5b
                java.lang.String r6 = "700 "
                r4.append(r6)     // Catch: java.lang.Exception -> L5b
                java.lang.String r9 = r9.getMessage()     // Catch: java.lang.Exception -> L5b
                r4.append(r9)     // Catch: java.lang.Exception -> L5b
                java.lang.String r9 = r4.toString()     // Catch: java.lang.Exception -> L5b
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r2, r9)     // Catch: java.lang.Exception -> L5b
                goto L96
            L55:
                java.lang.String r9 = "003"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r7, r9)     // Catch: java.lang.Exception -> L5b
                goto L96
            L5b:
                r9 = move-exception
                r4 = r5
                goto L5f
            L5e:
                r9 = move-exception
            L5f:
                r5 = 1
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "800 "
                r6.append(r7)
                java.lang.String r7 = r9.getMessage()
                r6.append(r7)
                java.lang.String r6 = r6.toString()
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r5, r6)
                if (r4 == 0) goto L96
                r4.onError(r3, r0)     // Catch: java.lang.Exception -> L7e
                goto L96
            L7e:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r3 = "701 "
                r0.append(r3)
                java.lang.String r9 = r9.getMessage()
                r0.append(r9)
                java.lang.String r9 = r0.toString()
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r2, r9)
            L96:
                java.lang.String r9 = "999"
                com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr.log(r1, r9)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalICardInfoListWithSpStatusV3EventCallback.onSuccess(java.util.List):void");
        }
    }
}

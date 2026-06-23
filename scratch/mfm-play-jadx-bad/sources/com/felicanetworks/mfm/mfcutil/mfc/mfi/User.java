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
import com.felicanetworks.mfc.mfi.ICardKeyMigrateEventCallback;
import com.felicanetworks.mfc.mfi.IDataListEventCallback;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.mfcutil.mfc.MfiClientAccess;
import com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
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

    public synchronized void getCardList(CardListEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "700");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkUserLoggedIn(this);
        this.mMfiClientAccess.startOnline(callback);
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

    public synchronized void getCardAdditionalInfoList(String[] cidList, CardAdditionalInfoListEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (cidList == null) {
            LogMgr.log(2, "704 cidList is null");
            throw new IllegalArgumentException("The specified cidList is null or invalid.");
        }
        if (cidList.length == 0) {
            LogMgr.log(2, "705 cidList length = 0");
            throw new IllegalArgumentException("The specified cidList is null or invalid.");
        }
        for (int i = 0; i < cidList.length; i++) {
            String str = cidList[i];
            if (str == null) {
                LogMgr.log(2, "706 cid is null");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            if (!str.matches("[0-9a-zA-Z]*")) {
                LogMgr.log(2, "707 cid involves invalid character.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
            if (cidList[i].length() != 63) {
                LogMgr.log(2, "708 cid length is invalid.");
                throw new IllegalArgumentException("The specified cidList is null or invalid.");
            }
        }
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "700");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkUserLoggedIn(this);
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                if (this.mMfiClientAccess.isDataListEventSupported()) {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getCardAdditionalInfoListV3(cidList, new LocalICardAdditionalInfoListV3EventCallback()));
                } else {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getCardAdditionalInfoListWithCidList(cidList, new LocalICardAdditionalInfoListV2EventCallback()));
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

    public synchronized void getCardInfoListWithSpStatus(String serviceId, CardInfoListWithSpStatusEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (serviceId == null) {
            LogMgr.log(2, "700 serviceId is null.");
            throw new IllegalArgumentException(EXC_INVALID_SERVICE_ID);
        }
        if (!serviceId.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "701 serviceId involves invalid character.");
            throw new IllegalArgumentException(EXC_INVALID_SERVICE_ID);
        }
        if (serviceId.length() != 8) {
            LogMgr.log(2, "702 serviceId length is invalid.");
            throw new IllegalArgumentException(EXC_INVALID_SERVICE_ID);
        }
        if (callback == null) {
            LogMgr.log(2, "703 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkUserLoggedIn(this);
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                try {
                    if (this.mMfiClientAccess.isDataListEventSupported()) {
                        MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getCardInfoListWithSpStatusV3(serviceId, new LocalICardInfoListWithSpStatusV3EventCallback()));
                    } else {
                        MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().getCardInfoListWithSpStatus(serviceId, new LocalICardInfoListWithSpStatusEventCallback()));
                    }
                    LogMgr.log(3, "999");
                } catch (IllegalArgumentException e) {
                    LogMgr.log(2, "705 " + e.toString());
                    this.mMfiClientAccess.stopOnline();
                    throw e;
                }
            } catch (Exception e2) {
                LogMgr.log(2, "706 Other Exception");
                LogMgr.printStackTrace(7, e2);
                this.mMfiClientAccess.stopOnline();
                throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
            }
        } catch (FelicaException e3) {
            LogMgr.log(2, "704 " + e3.toString() + " id:" + e3.getID() + " type:" + e3.getType());
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(e3);
        }
    }

    public synchronized void issueCard(String linkageData, CardIssueEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "%s", "000");
        if (linkageData == null) {
            LogMgr.log(2, "%s linkageData is null.", "700");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (linkageData.isEmpty()) {
            LogMgr.log(2, "%s linkageData is empty.", "701");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (callback == null) {
            LogMgr.log(2, "%s callback is null.", "702");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkUserLoggedIn(this);
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().issueCardV2(linkageData, new LocalICardIssueV2EventCallback()));
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

    public synchronized void issueCard(String linkageData, String otp, CardIssueEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (linkageData == null) {
            LogMgr.log(2, "700 linkageData is null.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (linkageData.isEmpty()) {
            LogMgr.log(2, "701 linkageData is empty.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (otp == null) {
            LogMgr.log(2, "702 otp is null.");
            throw new IllegalArgumentException("The specified Otp is null or invalid.");
        }
        if (otp.isEmpty()) {
            LogMgr.log(2, "703 otp is empty.");
            throw new IllegalArgumentException("The specified Otp is null or invalid.");
        }
        if (callback == null) {
            LogMgr.log(2, "704 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkUserLoggedIn(this);
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                try {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().issueCardWithOtp(linkageData, otp, new LocalICardIssueV2EventCallback()));
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

    public synchronized void migrateCardKey(String serviceId, CardKeyMigrateEventCallback callback) throws IllegalArgumentException, MfiClientException {
        LogMgr.log(3, "000");
        if (serviceId == null) {
            LogMgr.log(2, "700 serviceId is null.");
            throw new IllegalArgumentException(EXC_INVALID_SERVICE_ID);
        }
        if (!serviceId.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "701 serviceId involves invalid character.");
            throw new IllegalArgumentException(EXC_INVALID_SERVICE_ID);
        }
        if (serviceId.length() != 8) {
            LogMgr.log(2, "702 serviceId length is invalid.");
            throw new IllegalArgumentException(EXC_INVALID_SERVICE_ID);
        }
        if (callback == null) {
            LogMgr.log(2, "703 callback is null.");
            throw new IllegalArgumentException("The specified Callback is null.");
        }
        this.mMfiClientAccess.checkActivated();
        this.mMfiClientAccess.checkUserLoggedIn(this);
        this.mMfiClientAccess.startOnline(callback);
        try {
            try {
                try {
                    MfiUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().migrateCardKey(serviceId, new LocalICardKeyMigrateEventCallback()));
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
            }
        } catch (Exception e3) {
            LogMgr.log(2, "706 Other Exception");
            LogMgr.printStackTrace(7, e3);
            this.mMfiClientAccess.stopOnline();
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    abstract class LocalIPipeEventCallback<T> extends IPipeEventCallback.Stub {
        abstract void onSuccess(List<T> list);

        /* JADX INFO: renamed from: parse */
        abstract T parse2(String json) throws JSONException;

        LocalIPipeEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.IPipeEventCallback
        public void onStart(final ParcelFileDescriptor fd) throws RemoteException {
            LogMgr.log(3, "000 fd=" + fd);
            ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
            try {
                executorServiceNewSingleThreadExecutor.submit(new Runnable() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback.1
                    /* JADX DEBUG: Another duplicated slice has different insns count: {[]}, finally: {[INVOKE, MOVE_EXCEPTION, INVOKE, MOVE_EXCEPTION] complete} */
                    /* JADX WARN: Removed duplicated region for block: B:46:0x00f8  */
                    /* JADX WARN: Removed duplicated region for block: B:54:0x0124  */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public void run() {
                        LogMgr.log(3, "000");
                        ArrayList arrayList = new ArrayList();
                        String str = "Unknown error.";
                        boolean z = false;
                        int i = 200;
                        try {
                            ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(fd);
                            try {
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(autoCloseInputStream);
                                try {
                                    ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
                                    try {
                                        int i2 = objectInputStream.readInt();
                                        LogMgr.log(3, "001 readInt size=" + i2);
                                        for (int i3 = 0; i3 < i2; i3++) {
                                            if (Thread.currentThread().isInterrupted()) {
                                                throw new IOException("INTERRUPTED");
                                            }
                                            arrayList.add(LocalIPipeEventCallback.this.parse2((String) objectInputStream.readObject()));
                                            LogMgr.log(3, "002 readObject" + i3);
                                        }
                                        LogMgr.log(3, "003 readObject: done!");
                                        z = true;
                                        objectInputStream.close();
                                        bufferedInputStream.close();
                                        autoCloseInputStream.close();
                                    } finally {
                                    }
                                } finally {
                                }
                            } catch (Throwable th) {
                                try {
                                    autoCloseInputStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        } catch (IOException e) {
                            e = e;
                            LogMgr.log(2, "700 " + e.getMessage());
                            if (e.getMessage() == null) {
                                if (e.getMessage().contains("MFI_INTERRUPTED")) {
                                    LogMgr.log(2, "701 MFI_INTERRUPTED");
                                    str = null;
                                    i = 215;
                                } else {
                                    if (e.getMessage().contains("MFI_STOPPED")) {
                                        LogMgr.log(2, "702 MFI_STOPPED");
                                        return;
                                    }
                                    LogMgr.printStackTrace(7, e);
                                }
                            } else {
                                LogMgr.printStackTrace(7, e);
                            }
                            str = MfiClientAccess.EXC_IO_ERROR;
                        } catch (ClassNotFoundException e2) {
                            e = e2;
                            LogMgr.log(2, "700 " + e.getMessage());
                            if (e.getMessage() == null) {
                            }
                            str = MfiClientAccess.EXC_IO_ERROR;
                        } catch (JSONException e3) {
                            LogMgr.log(2, "701 " + e3.getMessage());
                            LogMgr.printStackTrace(7, e3);
                            str = "Json parse error.";
                        } catch (Exception e4) {
                            LogMgr.log(2, "702 " + e4.getMessage());
                            LogMgr.printStackTrace(7, e4);
                        }
                        if (!z) {
                            try {
                                LocalIPipeEventCallback.this.onErrorInner(i, str);
                            } catch (Exception e5) {
                                LogMgr.log(2, "703 " + e5.getMessage());
                            }
                        } else {
                            LocalIPipeEventCallback.this.onSuccess(arrayList);
                        }
                        LogMgr.log(3, "999");
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
        public void onError(int id, String msg) throws RemoteException {
            onErrorInner(id, msg);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onErrorInner(int id, String msg) {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(id, msg);
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

        /* JADX DEBUG: Method merged with bridge method: parse(Ljava/lang/String;)Ljava/lang/Object; */
        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback
        /* JADX INFO: renamed from: parse */
        public CardInfo parse2(String json) throws JSONException {
            LogMgr.log(3, "000");
            CardInfo cardInfo = new CardInfoJson(json).getCardInfo();
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

        /* JADX DEBUG: Method merged with bridge method: parse(Ljava/lang/String;)Ljava/lang/Object; */
        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback
        /* JADX INFO: renamed from: parse */
        public CardAdditionalInfo parse2(String json) throws JSONException {
            LogMgr.log(3, "000");
            CardAdditionalInfo cardAdditionalInfo = new CardAdditionalInfoJson(json).getCardAdditionalInfo();
            LogMgr.log(3, "999");
            return cardAdditionalInfo;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x004b A[Catch: Exception -> 0x0051, TRY_LEAVE, TryCatch #3 {Exception -> 0x0051, blocks: (B:6:0x0017, B:8:0x001b, B:14:0x0037, B:15:0x004b, B:10:0x0025, B:11:0x0032), top: B:33:0x0017, inners: #0 }] */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        void onSuccess(List<CardAdditionalInfo> list) {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallback = null;
            CardAdditionalInfo[] cardAdditionalInfoArr = null;
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    try {
                        if (baseMfiEventCallbackStopOnline instanceof CardAdditionalInfoListEventCallback) {
                            LogMgr.log(6, "001");
                            CardAdditionalInfoListEventCallback cardAdditionalInfoListEventCallback = (CardAdditionalInfoListEventCallback) baseMfiEventCallbackStopOnline;
                            if (list != null) {
                                try {
                                    cardAdditionalInfoArr = (CardAdditionalInfo[]) list.toArray(new CardAdditionalInfo[list.size()]);
                                } catch (Exception e) {
                                    LogMgr.log(2, "700 " + e.getMessage());
                                }
                            }
                            cardAdditionalInfoListEventCallback.onSuccess(cardAdditionalInfoArr);
                        } else {
                            LogMgr.log(6, "002");
                        }
                    } catch (Exception e2) {
                        e = e2;
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
            } catch (Exception e3) {
                e = e3;
            }
            LogMgr.log(3, "999");
        }
    }

    class LocalICardInfoListWithSpStatusEventCallback extends LocalIPipeEventCallback<CardInfo> {
        LocalICardInfoListWithSpStatusEventCallback() {
            super();
        }

        /* JADX DEBUG: Method merged with bridge method: parse(Ljava/lang/String;)Ljava/lang/Object; */
        /* JADX DEBUG: Return type fixed from 'com.felicanetworks.mfc.mfi.CardInfoWithSpStatus' to match base method */
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback
        /* JADX INFO: renamed from: parse, reason: merged with bridge method [inline-methods] */
        public CardInfo parse2(String json) throws JSONException {
            LogMgr.log(3, "000");
            CardInfoWithSpStatus cardInfoWithSpStatus = new CardInfoWithSpStatusJson(json).getCardInfoWithSpStatus();
            LogMgr.log(3, "999");
            return cardInfoWithSpStatus;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x004b A[Catch: Exception -> 0x0051, TRY_LEAVE, TryCatch #3 {Exception -> 0x0051, blocks: (B:6:0x0017, B:8:0x001b, B:14:0x0037, B:15:0x004b, B:10:0x0025, B:11:0x0032), top: B:33:0x0017, inners: #0 }] */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIPipeEventCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        void onSuccess(List<CardInfo> list) {
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallback = null;
            CardInfoWithSpStatus[] cardInfoWithSpStatusArr = null;
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    try {
                        if (baseMfiEventCallbackStopOnline instanceof CardInfoListWithSpStatusEventCallback) {
                            LogMgr.log(6, "002");
                            CardInfoListWithSpStatusEventCallback cardInfoListWithSpStatusEventCallback = (CardInfoListWithSpStatusEventCallback) baseMfiEventCallbackStopOnline;
                            if (list != null) {
                                try {
                                    cardInfoWithSpStatusArr = (CardInfoWithSpStatus[]) list.toArray(new CardInfoWithSpStatus[list.size()]);
                                } catch (Exception e) {
                                    LogMgr.log(2, "700 " + e.getMessage());
                                }
                            }
                            cardInfoListWithSpStatusEventCallback.onSuccess(cardInfoWithSpStatusArr);
                        } else {
                            LogMgr.log(6, "003");
                        }
                    } catch (Exception e2) {
                        e = e2;
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
            } catch (Exception e3) {
                e = e3;
            }
            LogMgr.log(3, "999");
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
        public void onError(int id, String msg) throws RemoteException {
            LogMgr.log(3, "000");
            try {
                BaseMfiEventCallback baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                if (baseMfiEventCallbackStopOnline != null) {
                    LogMgr.log(6, "001");
                    try {
                        baseMfiEventCallbackStopOnline.onError(id, msg);
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

        abstract T parse(String json) throws JSONException;

        LocalIDataListEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
        public void onPart(final List<String> partialDataList, final boolean more) {
            LogMgr.log(3, "000 more=" + more);
            this.mDataList.addAll(partialDataList);
            if (!more) {
                LogMgr.log(6, "001 size=" + this.mDataList.size());
                LogMgr.log(6, "002 wait for onFinished.");
            } else {
                LogMgr.log(6, "004 wait for next dataList.");
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
        public void onFinished(final int size) {
            LogMgr.log(3, "000 size=" + size);
            new Thread(new Runnable() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback.1
                /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
                /* JADX WARN: Removed duplicated region for block: B:35:0x00ff  */
                /* JADX WARN: Removed duplicated region for block: B:44:0x0105 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void run() {
                    String str = MfiClientAccess.EXC_DATA_SIZE_ERROR;
                    LogMgr.log(3, "000");
                    String str2 = "Unknown error.";
                    ArrayList arrayList = new ArrayList();
                    boolean z = false;
                    try {
                        try {
                            try {
                                LogMgr.log(3, "001 dataList size=" + LocalIDataListEventCallback.this.mDataList.size());
                            } catch (JSONException e) {
                                LogMgr.log(2, "702 " + e.getMessage());
                                LogMgr.printStackTrace(7, e);
                                str2 = "Json parse error.";
                                if (!z) {
                                }
                            }
                        } catch (IOException e2) {
                            e = e2;
                        } catch (Exception e3) {
                            e = e3;
                        }
                        if (LocalIDataListEventCallback.this.mDataList.size() == size) {
                            Iterator<String> it = LocalIDataListEventCallback.this.mDataList.iterator();
                            while (it.hasNext()) {
                                arrayList.add(LocalIDataListEventCallback.this.parse(it.next()));
                                LogMgr.log(3, "002");
                            }
                            LogMgr.log(3, "003 parseDataList: done!");
                            LocalIDataListEventCallback.this.mDataList.clear();
                            z = true;
                            if (!z) {
                                LocalIDataListEventCallback.this.onSuccess(arrayList);
                                return;
                            }
                            try {
                                LocalIDataListEventCallback.this.onErrorInner(200, str2);
                                return;
                            } catch (Exception e4) {
                                LogMgr.log(2, "703 " + e4.getMessage());
                                return;
                            }
                        }
                        LogMgr.log(2, "700 invalid data size.");
                        try {
                            throw new IOException(MfiClientAccess.EXC_DATA_SIZE_ERROR);
                        } catch (IOException e5) {
                            e = e5;
                            str2 = MfiClientAccess.EXC_DATA_SIZE_ERROR;
                            LogMgr.log(2, "701 " + e.getMessage());
                            if (e.getMessage() == null) {
                                LogMgr.printStackTrace(7, e);
                                str = MfiClientAccess.EXC_IO_ERROR;
                            } else if (e.getMessage().contains(MfiClientAccess.EXC_DATA_SIZE_ERROR)) {
                                str = str2;
                            } else {
                                LogMgr.printStackTrace(7, e);
                            }
                            LocalIDataListEventCallback.this.mDataList.clear();
                            str2 = str;
                            if (!z) {
                            }
                        } catch (Exception e6) {
                            str2 = MfiClientAccess.EXC_DATA_SIZE_ERROR;
                            e = e6;
                            LogMgr.log(2, "703 " + e.getMessage());
                            LogMgr.printStackTrace(7, e);
                            if (!z) {
                            }
                        }
                    } finally {
                        LocalIDataListEventCallback.this.mDataList.clear();
                    }
                }
            }).start();
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
        public void onError(int id, String msg) throws RemoteException {
            onErrorInner(id, msg);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onErrorInner(int id, String msg) {
            LogMgr.log(3, "000");
            try {
                try {
                    BaseMfiEventCallback baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                    if (baseMfiEventCallbackStopOnline != null) {
                        LogMgr.log(6, "001");
                        try {
                            baseMfiEventCallbackStopOnline.onError(id, msg);
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

        /* JADX DEBUG: Method merged with bridge method: parse(Ljava/lang/String;)Ljava/lang/Object; */
        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback
        public CardInfo parse(String json) throws JSONException {
            LogMgr.log(3, "000");
            CardInfo cardInfo = new CardInfoJson(json).getCardInfo();
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

        /* JADX DEBUG: Method merged with bridge method: parse(Ljava/lang/String;)Ljava/lang/Object; */
        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback
        public CardAdditionalInfo parse(String json) throws JSONException {
            LogMgr.log(3, "000");
            CardAdditionalInfo cardAdditionalInfo = new CardAdditionalInfoJson(json).getCardAdditionalInfo();
            LogMgr.log(3, "999");
            return cardAdditionalInfo;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0036 A[Catch: Exception -> 0x0032, TryCatch #3 {Exception -> 0x0032, blocks: (B:9:0x0028, B:13:0x0036, B:14:0x003a), top: B:34:0x0028, outer: #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:14:0x003a A[Catch: Exception -> 0x0032, TRY_LEAVE, TryCatch #3 {Exception -> 0x0032, blocks: (B:9:0x0028, B:13:0x0036, B:14:0x003a), top: B:34:0x0028, outer: #2 }] */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onSuccess(List<CardAdditionalInfo> list) {
            BaseMfiEventCallback baseMfiEventCallbackStopOnline;
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallback = null;
            CardAdditionalInfo[] cardAdditionalInfoArr = null;
            try {
                baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
            } catch (Exception e) {
                e = e;
            }
            try {
                if (baseMfiEventCallbackStopOnline instanceof CardAdditionalInfoListEventCallback) {
                    LogMgr.log(6, "002");
                    CardAdditionalInfoListEventCallback cardAdditionalInfoListEventCallback = (CardAdditionalInfoListEventCallback) baseMfiEventCallbackStopOnline;
                    if (list != null) {
                        try {
                            cardAdditionalInfoArr = (CardAdditionalInfo[]) list.toArray(new CardAdditionalInfo[0]);
                            if (cardAdditionalInfoArr == null) {
                                cardAdditionalInfoListEventCallback.onSuccess(cardAdditionalInfoArr);
                            } else {
                                baseMfiEventCallbackStopOnline.onError(200, "Unknown error.");
                            }
                        } catch (Exception e2) {
                            LogMgr.log(2, "700 " + e2.getMessage());
                        }
                    } else if (cardAdditionalInfoArr == null) {
                    }
                } else {
                    LogMgr.log(6, "003");
                }
            } catch (Exception e3) {
                e = e3;
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
            LogMgr.log(3, "999");
        }
    }

    class LocalICardInfoListWithSpStatusV3EventCallback extends LocalIDataListEventCallback<CardInfoWithSpStatus> {
        LocalICardInfoListWithSpStatusV3EventCallback() {
            super();
        }

        /* JADX DEBUG: Method merged with bridge method: parse(Ljava/lang/String;)Ljava/lang/Object; */
        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback
        public CardInfoWithSpStatus parse(String json) throws JSONException {
            LogMgr.log(3, "000");
            CardInfoWithSpStatus cardInfoWithSpStatus = new CardInfoWithSpStatusJson(json).getCardInfoWithSpStatus();
            LogMgr.log(3, "999");
            return cardInfoWithSpStatus;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0036 A[Catch: Exception -> 0x0032, TryCatch #3 {Exception -> 0x0032, blocks: (B:9:0x0028, B:13:0x0036, B:14:0x003a), top: B:34:0x0028, outer: #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:14:0x003a A[Catch: Exception -> 0x0032, TRY_LEAVE, TryCatch #3 {Exception -> 0x0032, blocks: (B:9:0x0028, B:13:0x0036, B:14:0x003a), top: B:34:0x0028, outer: #2 }] */
        @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalIDataListEventCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onSuccess(List<CardInfoWithSpStatus> list) {
            BaseMfiEventCallback baseMfiEventCallbackStopOnline;
            LogMgr.log(3, "000");
            BaseMfiEventCallback baseMfiEventCallback = null;
            CardInfoWithSpStatus[] cardInfoWithSpStatusArr = null;
            try {
                baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
            } catch (Exception e) {
                e = e;
            }
            try {
                if (baseMfiEventCallbackStopOnline instanceof CardInfoListWithSpStatusEventCallback) {
                    LogMgr.log(6, "002");
                    CardInfoListWithSpStatusEventCallback cardInfoListWithSpStatusEventCallback = (CardInfoListWithSpStatusEventCallback) baseMfiEventCallbackStopOnline;
                    if (list != null) {
                        try {
                            cardInfoWithSpStatusArr = (CardInfoWithSpStatus[]) list.toArray(new CardInfoWithSpStatus[0]);
                            if (cardInfoWithSpStatusArr == null) {
                                cardInfoListWithSpStatusEventCallback.onSuccess(cardInfoWithSpStatusArr);
                            } else {
                                baseMfiEventCallbackStopOnline.onError(200, "Unknown error.");
                            }
                        } catch (Exception e2) {
                            LogMgr.log(2, "700 " + e2.getMessage());
                        }
                    } else if (cardInfoWithSpStatusArr == null) {
                    }
                } else {
                    LogMgr.log(6, "003");
                }
            } catch (Exception e3) {
                e = e3;
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
            LogMgr.log(3, "999");
        }
    }

    class LocalICardKeyMigrateEventCallback extends ICardKeyMigrateEventCallback.Stub {
        LocalICardKeyMigrateEventCallback() {
        }

        @Override // com.felicanetworks.mfc.mfi.ICardKeyMigrateEventCallback
        public void onSuccess(final String migratedCardInfoJson) throws RemoteException {
            LogMgr.log(3, "000");
            new Thread(new Runnable() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalICardKeyMigrateEventCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    BaseMfiEventCallback baseMfiEventCallbackStopOnline;
                    Card card;
                    LogMgr.log(6, "000");
                    try {
                        baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                        try {
                            try {
                                if (migratedCardInfoJson != null) {
                                    LogMgr.log(6, "001");
                                    card = new Card(new CardInfoJson(migratedCardInfoJson).getCardInfo(), User.this.mMfiClientAccess);
                                } else {
                                    card = null;
                                }
                                if (baseMfiEventCallbackStopOnline instanceof CardKeyMigrateEventCallback) {
                                    LogMgr.log(6, "002");
                                    try {
                                        ((CardKeyMigrateEventCallback) baseMfiEventCallbackStopOnline).onSuccess(card);
                                    } catch (Exception e) {
                                        LogMgr.log(2, "700 " + e.getMessage());
                                    }
                                } else {
                                    LogMgr.log(6, "003");
                                }
                            } catch (Exception e2) {
                                e = e2;
                                LogMgr.log(2, "703 " + e.getMessage());
                                if (baseMfiEventCallbackStopOnline != null) {
                                    try {
                                        ((CardKeyMigrateEventCallback) baseMfiEventCallbackStopOnline).onError(200, "Unknown error.", null);
                                    } catch (Exception e3) {
                                        LogMgr.log(2, "704 " + e3.getMessage());
                                    }
                                }
                            }
                        } catch (JSONException e4) {
                            e = e4;
                            LogMgr.log(2, "701 " + e.getMessage());
                            try {
                                ((CardKeyMigrateEventCallback) baseMfiEventCallbackStopOnline).onError(200, "Json parse error.", null);
                            } catch (Exception e5) {
                                LogMgr.log(2, "702 " + e5.getMessage());
                            }
                        }
                    } catch (JSONException e6) {
                        e = e6;
                        baseMfiEventCallbackStopOnline = null;
                    } catch (Exception e7) {
                        e = e7;
                        baseMfiEventCallbackStopOnline = null;
                    }
                    LogMgr.log(6, "999");
                }
            }).start();
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.ICardKeyMigrateEventCallback
        public void onError(final int type, final String msg, final String unfinishedCardInfoJson) throws RemoteException {
            LogMgr.log(3, "000");
            new Thread(new Runnable() { // from class: com.felicanetworks.mfm.mfcutil.mfc.mfi.User.LocalICardKeyMigrateEventCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    BaseMfiEventCallback baseMfiEventCallbackStopOnline;
                    Card card;
                    LogMgr.log(6, "000");
                    try {
                        baseMfiEventCallbackStopOnline = User.this.mMfiClientAccess.stopOnline();
                        try {
                            try {
                                if (unfinishedCardInfoJson != null) {
                                    LogMgr.log(6, "001");
                                    card = new Card(new CardInfoJson(unfinishedCardInfoJson).getCardInfo(), User.this.mMfiClientAccess);
                                } else {
                                    card = null;
                                }
                                if (baseMfiEventCallbackStopOnline instanceof CardKeyMigrateEventCallback) {
                                    LogMgr.log(6, "002");
                                    try {
                                        ((CardKeyMigrateEventCallback) baseMfiEventCallbackStopOnline).onError(type, msg, card);
                                    } catch (Exception e) {
                                        LogMgr.log(2, "700 " + e.getMessage());
                                    }
                                } else {
                                    LogMgr.log(6, "003");
                                }
                            } catch (Exception e2) {
                                e = e2;
                                LogMgr.log(2, "703 " + e.getMessage());
                                if (baseMfiEventCallbackStopOnline != null) {
                                    try {
                                        ((CardKeyMigrateEventCallback) baseMfiEventCallbackStopOnline).onError(200, "Unknown error.", null);
                                    } catch (Exception e3) {
                                        LogMgr.log(2, "704 " + e3.getMessage());
                                    }
                                }
                            }
                        } catch (JSONException e4) {
                            e = e4;
                            LogMgr.log(2, "701 " + e.getMessage());
                            try {
                                ((CardKeyMigrateEventCallback) baseMfiEventCallbackStopOnline).onError(200, "Json parse error.", null);
                            } catch (Exception e5) {
                                LogMgr.log(2, "702 " + e5.getMessage());
                            }
                        }
                    } catch (JSONException e6) {
                        e = e6;
                        baseMfiEventCallbackStopOnline = null;
                    } catch (Exception e7) {
                        e = e7;
                        baseMfiEventCallbackStopOnline = null;
                    }
                    LogMgr.log(6, "999");
                }
            }).start();
            LogMgr.log(3, "999");
        }
    }
}

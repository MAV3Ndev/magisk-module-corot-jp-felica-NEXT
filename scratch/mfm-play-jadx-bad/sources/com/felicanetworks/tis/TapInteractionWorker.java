package com.felicanetworks.tis;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.ForegroundInfo;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.felicanetworks.mfc.FSC$$ExternalSyntheticApiModelOutline0;
import com.felicanetworks.tis.ITapInteractionService;
import com.felicanetworks.tis.ProcessingInfo;
import com.felicanetworks.tis.util.AccessConfig;
import com.felicanetworks.tis.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class TapInteractionWorker extends Worker {
    private static final String CHANNEL_ID = "Tap Notice Worker";
    private static final int CHANNEL_IMPORTANCE = 2;
    private static final int NOTIFICATION_ID = 200;
    private static final int REQUEST_CODE_FOR_INTENT = 0;
    private static final String TAPINTERACTION_SERVICE_CLASS_NAME = "com.felicanetworks.tis.TapInteractionService";
    private static final String TAPINTERACTION_SERVICE_PACKAGE_NAME = "com.felicanetworks.mfm.main";
    private final Context mContext;

    public TapInteractionWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
        LogMgr.log(5, "000");
        this.mContext = context;
        LogMgr.log(5, "999");
    }

    static void requestWork(Context context, Intent intent) {
        Data dataBuild;
        LogMgr.log(5, "000");
        LogMgr.log(7, "[PFM]TapInteractionWorker#requestWork()");
        String action = intent.getAction();
        if (action == null) {
            LogMgr.log(2, "700 : action is null");
            return;
        }
        action.hashCode();
        switch (action) {
            case "com.felicanetworks.tis.action.NOTIFICATION_SETTING":
                LogMgr.log(7, "005");
                dataBuild = new Data.Builder().putString(ProcessingInfo.Key.KEY_ACTION.name(), action).putBoolean(ProcessingInfo.Key.KEY_SETTING.name(), intent.getBooleanExtra(TapInteractionConst.EXTRA_KEY_NOTIFICATION_SETTING, true)).build();
                break;
            case "android.nfc.action.TRANSACTION_DETECTED":
                LogMgr.log(7, "001");
                String stringExtra = intent.getStringExtra("android.nfc.extra.SECURE_ELEMENT_NAME");
                LogMgr.log(6, "002 : READER_NAME = " + stringExtra);
                byte[] byteArrayExtra = intent.getByteArrayExtra("android.nfc.extra.DATA");
                LogMgr.log(6, "003 : EXTRA_DATA");
                LogMgr.logArray(6, byteArrayExtra);
                byte[] byteArrayExtra2 = intent.getByteArrayExtra("android.nfc.extra.AID");
                LogMgr.log(6, "004 : EXTRA_AID");
                LogMgr.logArray(6, byteArrayExtra2);
                if (stringExtra == null || byteArrayExtra == null) {
                    LogMgr.log(2, "701 : Something wrong with reader name or event");
                    return;
                }
                if (byteArrayExtra2 == null) {
                    byteArrayExtra2 = new byte[0];
                }
                dataBuild = new Data.Builder().putString(ProcessingInfo.Key.KEY_ACTION.name(), action).putString(ProcessingInfo.Key.KEY_READER_NAME.name(), stringExtra).putByteArray(ProcessingInfo.Key.KEY_EVENT.name(), byteArrayExtra).putByteArray(ProcessingInfo.Key.KEY_AID.name(), byteArrayExtra2).build();
                break;
                break;
            case "com.felicanetworks.tis.action.STOP_CHIP_ACCESS_SETTING":
                LogMgr.log(7, "006");
                dataBuild = new Data.Builder().putString(ProcessingInfo.Key.KEY_ACTION.name(), action).putBoolean(ProcessingInfo.Key.KEY_SETTING.name(), intent.getBooleanExtra(TapInteractionConst.EXTRA_KEY_STOP_CHIP_ACCESS_SETTING, false)).build();
                break;
            case "android.intent.action.MY_PACKAGE_REPLACED":
                LogMgr.log(7, "007");
                dataBuild = new Data.Builder().putString(ProcessingInfo.Key.KEY_ACTION.name(), action).build();
                break;
            default:
                LogMgr.log(2, "702 : invalid action. action:" + action);
                return;
        }
        LogMgr.log(7, "[PFM]TapInteractionWorker#requestWork() : Work Request");
        WorkManager.getInstance(context).enqueue(new OneTimeWorkRequest.Builder((Class<? extends ListenableWorker>) TapInteractionWorker.class).setInputData(dataBuild).setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST).build());
        LogMgr.log(5, "999");
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        LogMgr.log(5, "000");
        LogMgr.log(7, "[PFM]TapInteractionWorker#doWork()");
        if (AccessConfig.isTisUnavailable()) {
            LogMgr.log(2, "700 : TapInteraction is unavailable in This Device");
            return ListenableWorker.Result.failure();
        }
        new TapInteractionServiceConnector().connect(this.mContext, getInputData());
        LogMgr.log(5, "999");
        return ListenableWorker.Result.success();
    }

    @Override // androidx.work.Worker
    public ForegroundInfo getForegroundInfo() {
        LogMgr.log(5, "000");
        Context applicationContext = getApplicationContext();
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", applicationContext.getPackageName(), null));
        NotificationManager notificationManager = (NotificationManager) applicationContext.getSystemService("notification");
        FSC$$ExternalSyntheticApiModelOutline0.m$4();
        notificationManager.createNotificationChannel(FSC$$ExternalSyntheticApiModelOutline0.m(CHANNEL_ID, applicationContext.getResources().getString(R.string.tis_fgs_channel_name), 2));
        Notification notificationBuild = new NotificationCompat.Builder(applicationContext, CHANNEL_ID).setContentTitle(applicationContext.getResources().getString(R.string.tis_fgs_notification_title)).setContentText(applicationContext.getResources().getString(R.string.tis_fgs_notification_text)).setSmallIcon(R.mipmap.ic_osaifu_touka).setLargeIcon(BitmapFactory.decodeResource(applicationContext.getResources(), R.drawable.app_icon)).setContentIntent(PendingIntent.getActivity(applicationContext, 0, intent, 201326592)).build();
        LogMgr.log(5, "999");
        return new ForegroundInfo(200, notificationBuild);
    }

    static class TapInteractionServiceConnector implements ServiceConnection {
        private static final long WAIT_COUNT = 1000;
        private static final long WAIT_INTERVAL = 10;
        private IBinder binder;

        TapInteractionServiceConnector() {
        }

        /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [289=6] */
        /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
        public void connect(Context context, Data data) {
            LogMgr.log(5, "000");
            LogMgr.log(7, "[PFM]TapInteractionServiceConnector#connect()");
            try {
                if (!bind(context)) {
                    synchronized (this) {
                        LogMgr.log(7, "001");
                        IBinder iBinder = this.binder;
                        if (iBinder == null) {
                            LogMgr.log(2, "700 binder is null.");
                        } else {
                            try {
                                ITapInteractionService.Stub.asInterface(iBinder).executeProcessing(new ProcessingInfo(data));
                            } catch (Exception unused) {
                                LogMgr.log(2, "701 TapInteractionService execution failed");
                            }
                        }
                    }
                    unbind(context);
                    return;
                }
                for (int i = 0; i < WAIT_COUNT; i++) {
                    if (this.binder != null) {
                        break;
                    }
                    SystemClock.sleep(WAIT_INTERVAL);
                }
                synchronized (this) {
                    LogMgr.log(7, "001");
                    IBinder iBinder2 = this.binder;
                    if (iBinder2 == null) {
                        LogMgr.log(2, "700 binder is null.");
                    } else {
                        try {
                            ITapInteractionService.Stub.asInterface(iBinder2).executeProcessing(new ProcessingInfo(data));
                        } catch (Exception unused2) {
                            LogMgr.log(2, "701 TapInteractionService execution failed");
                        }
                    }
                }
                unbind(context);
                LogMgr.log(5, "999");
            } catch (Throwable th) {
                synchronized (this) {
                    LogMgr.log(7, "001");
                    IBinder iBinder3 = this.binder;
                    if (iBinder3 == null) {
                        LogMgr.log(2, "700 binder is null.");
                    } else {
                        try {
                            ITapInteractionService.Stub.asInterface(iBinder3).executeProcessing(new ProcessingInfo(data));
                        } catch (Exception unused3) {
                            LogMgr.log(2, "701 TapInteractionService execution failed");
                        }
                    }
                    unbind(context);
                    throw th;
                }
            }
        }

        private boolean bind(Context context) {
            boolean zBindService;
            LogMgr.log(5, "000");
            LogMgr.log(7, "[PFM]TapInteractionServiceConnector#bind()");
            if (context == null) {
                LogMgr.log(2, "700 Parameter Error.");
                zBindService = false;
            } else {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.felicanetworks.mfm.main", TapInteractionWorker.TAPINTERACTION_SERVICE_CLASS_NAME));
                zBindService = context.bindService(intent, this, 1);
            }
            LogMgr.log(5, "999");
            return zBindService;
        }

        private void unbind(Context context) {
            LogMgr.log(5, "000");
            LogMgr.log(7, "[PFM]TapInteractionServiceConnector#unbind()");
            context.unbindService(this);
            LogMgr.log(5, "999");
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogMgr.log(5, "000");
            LogMgr.log(7, "[PFM]TapInteractionServiceConnector#onServiceConnected()");
            this.binder = iBinder;
            LogMgr.log(5, "999");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogMgr.log(5, "000");
            LogMgr.log(7, "[PFM]TapInteractionServiceConnector#onServiceDisconnected()");
            LogMgr.log(5, "999");
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            LogMgr.log(5, "000");
            LogMgr.log(7, "[PFM]TapInteractionServiceConnector#onBindingDied()");
            LogMgr.log(5, "999");
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            LogMgr.log(5, "000");
            LogMgr.log(7, "[PFM]TapInteractionServiceConnector#onNullBinding()");
            LogMgr.log(5, "999");
        }
    }
}

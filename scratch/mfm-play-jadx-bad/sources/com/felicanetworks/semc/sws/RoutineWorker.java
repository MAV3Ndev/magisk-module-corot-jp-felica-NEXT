package com.felicanetworks.semc.sws;

import android.content.Context;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.felicanetworks.semc.SemClient;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.SemClientInternal;
import com.felicanetworks.semc.sws.LogUploader;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.SharedPrefsProvider;
import java.security.SecureRandom;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes3.dex */
public class RoutineWorker extends Worker {
    private static final long CONNECT_TIMEOUT = 30000;
    public static final String EXT_KEY_PERIODIC_WORK_START_TIME_MAX = "periodicWorkStartTimeMax";
    public static final String EXT_KEY_PERIODIC_WORK_START_TIME_MIN = "periodicWorkStartTimeMin";
    public static final String TAG = "SemcWorkManagerRoutineWorkRequest";
    private final Context mContext;
    private AtomicBoolean mIsLogUploadExecuted;
    private LogUploader mLogUploader;
    private SemClientInternal mSemClient;
    private Timer mTimer;

    public RoutineWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
        this.mContext = context;
        LogUploader logUploader = new LogUploader(new OnUploadFinishListenerImpl());
        this.mLogUploader = logUploader;
        logUploader.start();
        this.mIsLogUploadExecuted = new AtomicBoolean(false);
    }

    public static void cancelRoutineWorkRequest(Context context) throws SemClientException {
        LogMgr.log(8, "000");
        if (context == null) {
            LogMgr.log(2, "800 context is null.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        WorkManager.getInstance(context.getApplicationContext()).cancelAllWorkByTag(TAG);
        LogMgr.log(8, "999");
    }

    public static void scheduleNext(Context context, int i, int i2) throws SemClientException {
        LogMgr.log(8, "000");
        try {
            if (context == null) {
                LogMgr.log(2, "800 context is null.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            if (i < 0 || 23 < i) {
                LogMgr.log(2, "801 Invalid const value PERIODIC_WORK_START_TIME_MIN was set.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            if (i2 < 1 || 24 < i2 || i2 < i) {
                LogMgr.log(2, "802 Invalid const value PERIODIC_WORK_START_TIME_MAX was set.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Tokyo");
            long jCurrentTimeMillis = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance(timeZone);
            Calendar calendar2 = Calendar.getInstance(timeZone);
            calendar.setTimeInMillis(jCurrentTimeMillis);
            calendar2.setTimeInMillis(jCurrentTimeMillis);
            int i3 = calendar.get(11);
            calendar2.set(11, i);
            calendar2.set(12, 0);
            calendar2.set(13, 0);
            calendar2.set(14, 0);
            if (i <= i3) {
                calendar2.add(5, 1);
            }
            long timeInMillis = calendar2.getTimeInMillis() - calendar.getTimeInMillis();
            long jNextLong = new SecureRandom().nextLong() % (((long) ((i2 - i) * 3600)) * 1000);
            long j = jNextLong >= 0 ? timeInMillis + jNextLong : timeInMillis - jNextLong;
            LogMgr.log(9, "001 initialDelayTimeMillis=" + j);
            OneTimeWorkRequest oneTimeWorkRequestBuild = new OneTimeWorkRequest.Builder((Class<? extends ListenableWorker>) RoutineWorker.class).setInputData(new Data.Builder().putInt(EXT_KEY_PERIODIC_WORK_START_TIME_MIN, i).putInt(EXT_KEY_PERIODIC_WORK_START_TIME_MAX, i2).build()).setInitialDelay(j, TimeUnit.MILLISECONDS).addTag(TAG).build();
            LogMgr.log(9, "002 OneTimeWorkRequest(RoutineWorker) ID=" + oneTimeWorkRequestBuild.getId());
            WorkManager.getInstance(context.getApplicationContext()).enqueue(oneTimeWorkRequestBuild);
            LogMgr.log(8, "999");
        } catch (SemClientException e) {
            throw e;
        } catch (Exception e2) {
            LogMgr.log(2, "700 Failed to Exec setRoutineWorkRequest.");
            LogMgr.printStackTrace(9, e2);
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        LogMgr.log(7, "000 ID=" + getId());
        if (!needsExecuteRoutineWork(getApplicationContext())) {
            LogMgr.log(7, "997 Skip RoutineWork on the same day.");
            return ListenableWorker.Result.success();
        }
        try {
            scheduleNext(getApplicationContext(), getInputData().getInt(EXT_KEY_PERIODIC_WORK_START_TIME_MIN, 0), getInputData().getInt(EXT_KEY_PERIODIC_WORK_START_TIME_MAX, 6));
        } catch (SemClientException unused) {
        }
        TimerTask timerTask = new TimerTask() { // from class: com.felicanetworks.semc.sws.RoutineWorker.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                RoutineWorker.this.callDisconnect();
                RoutineWorker.this.requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_CONNECT, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.ROUTINE_WORK_SERVICE, LogUploader.MessageCode.Process.CONNECT_PROCESS, LogUploader.MessageCode.ErrorInfo.TIMEOUT, RoutineWorker.this.mContext, LogUploader.DUMMY_SE_ID));
            }
        };
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(timerTask, 30000L);
        SemClientInternal semClientInternal = SemClientInternal.getInstance();
        this.mSemClient = semClientInternal;
        if (semClientInternal == null) {
            this.mTimer.cancel();
            requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_GETINSTANCE, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.ROUTINE_WORK_SERVICE, LogUploader.MessageCode.Process.GET_SEM_CLIENT_PROCESS, LogUploader.MessageCode.ErrorInfo.SEM_CLIENT_IS_NULL, this.mContext, LogUploader.DUMMY_SE_ID));
            LogMgr.log(8, "998");
            return ListenableWorker.Result.success();
        }
        try {
            semClientInternal.connect(this.mContext, true, (SemClient.OnConnectedListener) new OnConnectedListener());
        } catch (SemClientException e) {
            this.mTimer.cancel();
            requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_CONNECT, LogUploader.Message.ErrorType.SEM_CLIENT_EXCEPTION, Integer.toString(e.getErrorCode()), e.getMessage() != null ? e.getMessage() : "", LogUploader.MessageCode.SendTiming.ROUTINE_WORK_SERVICE, LogUploader.MessageCode.Process.CONNECT_PROCESS, LogUploader.MessageCode.ErrorInfo.SEM_CLIENT_EXCEPTION, this.mContext, LogUploader.DUMMY_SE_ID));
        } catch (IllegalArgumentException e2) {
            this.mTimer.cancel();
            requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_CONNECT, LogUploader.Message.ErrorType.ILLEGAL_ARGUMENT_EXCEPTION, null, e2.getMessage() != null ? e2.getMessage() : "", LogUploader.MessageCode.SendTiming.ROUTINE_WORK_SERVICE, LogUploader.MessageCode.Process.CONNECT_PROCESS, LogUploader.MessageCode.ErrorInfo.ILLEGAL_ARGUMENT_EXCEPTION, this.mContext, LogUploader.DUMMY_SE_ID));
        } catch (IllegalStateException e3) {
            this.mTimer.cancel();
            requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_CONNECT, LogUploader.Message.ErrorType.ILLEGAL_STATE_EXCEPTION, null, e3.getMessage() != null ? e3.getMessage() : "", LogUploader.MessageCode.SendTiming.ROUTINE_WORK_SERVICE, LogUploader.MessageCode.Process.CONNECT_PROCESS, LogUploader.MessageCode.ErrorInfo.ILLEGAL_STATE_EXCEPTION, this.mContext, LogUploader.DUMMY_SE_ID));
        }
        LogMgr.log(8, "999");
        return ListenableWorker.Result.success();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callDisconnect() {
        LogMgr.log(8, "000");
        try {
            if (this.mSemClient == null) {
                this.mSemClient = SemClientInternal.getInstance();
            }
            SemClientInternal semClientInternal = this.mSemClient;
            if (semClientInternal != null) {
                semClientInternal.disconnect();
            }
        } catch (SemClientException e) {
            LogMgr.log(2, "700 ErrorCode:" + e.getErrorCode() + " MSG:" + e.getMessage());
        }
        LogMgr.log(8, "999");
    }

    private class OnConnectedListener implements SemClient.OnConnectedListener {
        private OnConnectedListener() {
        }

        @Override // com.felicanetworks.semc.SemClient.OnConnectedListener
        public void onConnected() {
            LogMgr.log(8, "000");
            RoutineWorker.this.mTimer.cancel();
            try {
                RoutineWorker.this.mSemClient.disconnect();
            } catch (SemClientException unused) {
            }
            LogMgr.log(8, "999");
        }

        @Override // com.felicanetworks.semc.SemClient.OnConnectedListener
        public void onError(int i, String str, String str2) {
            LogMgr.log(8, "000");
            RoutineWorker.this.mTimer.cancel();
            synchronized (RoutineWorker.this) {
                if (!RoutineWorker.this.mIsLogUploadExecuted.get()) {
                    if (RoutineWorker.this.mLogUploader.isLogUpload(i)) {
                        LogUploader.Message.Api api = LogUploader.Message.Api.SEM_CONNECT;
                        LogUploader.Message.ErrorType errorType = LogUploader.Message.ErrorType.ON_ERROR;
                        String string = Integer.toString(i);
                        if (str2 == null) {
                            str2 = "";
                        }
                        RoutineWorker.this.requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(api, errorType, string, str2, LogUploader.MessageCode.SendTiming.ROUTINE_WORK_SERVICE, LogUploader.MessageCode.Process.CONNECT_PROCESS, LogUploader.MessageCode.ErrorInfo.ISEM_CLIENT_ERROR_NOTIFICATION, RoutineWorker.this.mContext, LogUploader.DUMMY_SE_ID));
                    }
                    LogMgr.log(8, "999");
                    return;
                }
                LogMgr.log(8, "998");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestLogUpload(String str, LogUploader.Request.LogInfoContent logInfoContent) {
        LogMgr.log(8, "000");
        try {
            LogUploader.Request requestBuild = LogUploader.Request.build(getApplicationContext(), str, logInfoContent);
            synchronized (this) {
                if (this.mIsLogUploadExecuted.get()) {
                    LogMgr.log(8, "998");
                    return;
                }
                this.mLogUploader.request(requestBuild);
                this.mIsLogUploadExecuted.compareAndSet(false, true);
                LogMgr.log(8, "999");
            }
        } catch (Exception e) {
            LogMgr.log(8, "998 " + e.getMessage());
        }
    }

    private class OnUploadFinishListenerImpl implements LogUploader.OnUploadFinishListener {
        private OnUploadFinishListenerImpl() {
        }

        @Override // com.felicanetworks.semc.sws.LogUploader.OnUploadFinishListener
        public void onFinished(String str, String str2) {
            LogMgr.log(8, "000 requestId=" + str + " result=" + str2);
            synchronized (RoutineWorker.this) {
                if (RoutineWorker.this.mLogUploader != null) {
                    RoutineWorker.this.mLogUploader.shutdown();
                    RoutineWorker.this.mLogUploader = null;
                }
            }
            LogMgr.log(8, "999");
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    private boolean needsExecuteRoutineWork(Context context) {
        LogMgr.log(8, "000");
        Cursor cursorQuery = null;
        try {
            LogMgr.log(9, "001 context.getContentResolver.query() in.");
            cursorQuery = context.getContentResolver().query(SharedPrefsProvider.getRoutineWorkerStatusContentsUri(context), null, null, null, null);
            LogMgr.log(9, "002 context.getContentResolver.query() out.");
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                int columnIndex = cursorQuery.getColumnIndex(SharedPrefsProvider.RoutineWorkerStatusContents.Setting.NEEDS_EXECUTE_ROUTINE_WORK);
                z = columnIndex >= 0 ? Boolean.parseBoolean(cursorQuery.getString(columnIndex)) : false;
                LogMgr.log(9, "003 : needsExecuteRoutineWork = " + z);
            }
        } catch (Exception unused) {
            if (cursorQuery != null) {
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
                throw th;
            }
            throw th;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        LogMgr.log(8, "999");
        return z;
    }
}

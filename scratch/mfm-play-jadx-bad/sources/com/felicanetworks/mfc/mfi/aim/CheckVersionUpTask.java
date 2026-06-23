package com.felicanetworks.mfc.mfi.aim;

import android.content.Context;
import android.os.Build;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.fws.StoppableTaskBase;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.VersionUpData;
import com.felicanetworks.mfc.mfi.util.CacheUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.File;
import java.util.Date;
import java.util.regex.Pattern;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class CheckVersionUpTask extends StoppableTaskBase<Result> {
    private static final int DEFAULT_TASK_ID = 0;
    private static final int INDEX_HEADER_COUNT_LIMIT = 0;
    private static final int INDEX_HEADER_PERIOD = 1;
    private static final int INDEX_VERSION_UP_TYPE = 3;
    private static final int MINUTE_IN_MILLIS = 60000;
    private static final int PARTS_SIZE = 5;
    private static final String PATTERN_HEADER_COUNT_LIMIT = "^[0-9]{1,8}$";
    private static final String PATTERN_HEADER_PERIOD = "^[0-9]{1,8}$";
    private static final String SEPARATOR = "\r\n";
    private static final String VERSION_UP_DATA_FILE_NAME = "version_up_data";
    private static final String VERSION_UP_DATA_FILE_SUFFIX = ".json";
    private static final String VERSION_UP_TYPE_NONE = "0";
    private static final String VERSION_UP_TYPE_OPTIONAL = "2";
    private static final String VERSION_UP_TYPE_REQUIRED = "1";
    private final AimClient mAimClient;
    private Result mResult;

    public class Result {
        public boolean isSuccess = false;
        public boolean needsUpdate = false;
        public int errType = 100;
        public String errMsg = null;

        public Result() {
        }
    }

    public CheckVersionUpTask(String sepId) {
        super(0);
        this.mAimClient = new AimClient(sepId);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
    public void start() throws Throwable {
        int iOptCount;
        int iOptCountLimit;
        int iOptReqDate;
        int i;
        boolean z;
        this.mResult = new Result();
        if (isStopped()) {
            this.mResult.isSuccess = false;
            this.mResult.errType = 104;
            return;
        }
        GetVersionUpDataSubTask getVersionUpDataSubTask = new GetVersionUpDataSubTask(0);
        getVersionUpDataSubTask.start();
        VersionUpData result = getVersionUpDataSubTask.getResult();
        if (isStopped()) {
            this.mResult.isSuccess = false;
            this.mResult.errType = 104;
            return;
        }
        if (result != null) {
            iOptCount = result.optCount();
            iOptCountLimit = result.optCountLimit();
            int iOptPeriod = result.optPeriod();
            long jOptReqDateLimit = result.optReqDateLimit();
            long time = new Date().getTime();
            if (result.optCount() < result.optCountLimit() && time <= jOptReqDateLimit) {
                try {
                    result.putCount(iOptCount + 1);
                    saveVersionUpData(result);
                    this.mResult.isSuccess = true;
                    this.mResult.needsUpdate = false;
                    return;
                } catch (JSONException e) {
                    LogMgr.log(1, "%s %s", "800", e.toString());
                    this.mResult.isSuccess = false;
                    this.mResult.errType = 200;
                    return;
                }
            }
            boolean z2 = result.optCount() < result.optCountLimit() && time > jOptReqDateLimit;
            i = iOptPeriod;
            iOptReqDate = (int) ((time - result.optReqDate()) / 60000);
            z = z2;
        } else {
            iOptCount = 0;
            iOptCountLimit = 0;
            iOptReqDate = 0;
            i = 0;
            z = false;
        }
        if (isStopped()) {
            this.mResult.isSuccess = false;
            this.mResult.errType = 104;
            return;
        }
        int i2 = iOptCount;
        AimCheckVersionUpSubTask aimCheckVersionUpSubTask = new AimCheckVersionUpSubTask(i2, iOptCountLimit, iOptReqDate, i);
        aimCheckVersionUpSubTask.start();
        AimCheckVersionUpSubTask.Result result2 = aimCheckVersionUpSubTask.getResult();
        if (result2.isSuccess) {
            this.mResult.isSuccess = true;
            this.mResult.needsUpdate = result2.needsUpdate;
            return;
        }
        if (z) {
            try {
                result.putCount(i2 + 1);
                saveVersionUpData(result);
                this.mResult.isSuccess = true;
                this.mResult.needsUpdate = false;
                return;
            } catch (JSONException e2) {
                LogMgr.log(1, "%s %s", "801", e2.toString());
                this.mResult.isSuccess = false;
                this.mResult.errType = 200;
                return;
            }
        }
        this.mResult.isSuccess = false;
        this.mResult.errType = result2.errType;
        this.mResult.errMsg = result2.errMsg;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public synchronized Result getResult() {
        return this.mResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveVersionUpData(VersionUpData versionUpData) throws Throwable {
        CacheUtil.writeFile(new File(FelicaAdapter.getInstance().getFilesDir(), getCacheFileName()), versionUpData.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCacheFileName() {
        return "version_up_data_" + FelicaAdapter.getInstance().getString(R.string.mfi_client_version) + VERSION_UP_DATA_FILE_SUFFIX;
    }

    class AimCheckVersionUpSubTask extends StoppableTaskBase<Result> {
        private int mCt;
        private int mIl;
        private int mMct;
        private int mMil;
        private Result mResult;

        class Result {
            boolean isSuccess = false;
            boolean needsUpdate = false;
            int errType = 200;
            String errMsg = null;

            Result() {
            }
        }

        AimCheckVersionUpSubTask(int ct, int mct, int il, int mil) {
            super(0);
            this.mCt = 0;
            this.mMct = 0;
            this.mIl = 0;
            this.mMil = 0;
            this.mResult = new Result();
            this.mCt = ct;
            this.mMct = mct;
            this.mIl = il;
            this.mMil = mil;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() throws Throwable {
            boolean z;
            LogMgr.log(5, "%s", "000");
            this.mResult = new Result();
            String strCheckVersionUp = null;
            try {
                strCheckVersionUp = CheckVersionUpTask.this.mAimClient.checkVersionUp(this.mCt, this.mMct, this.mIl, this.mMil);
                z = false;
            } catch (HttpException e) {
                this.mResult.errType = 203;
                this.mResult.errMsg = e.getMessage();
                z = true;
            } catch (ProtocolException e2) {
                this.mResult.errType = 202;
                this.mResult.errMsg = e2.getMessage();
                z = true;
            }
            if (z) {
                return;
            }
            String[] strArrSplit = strCheckVersionUp.split(CheckVersionUpTask.SEPARATOR);
            if (strArrSplit.length != 5) {
                LogMgr.log(1, "%s Invalid response format.", "800");
                this.mResult.errType = 202;
                this.mResult.errMsg = MfiClientCallbackConst.MSG_FORMAT_ERROR;
                return;
            }
            if (!checkPattern(strArrSplit[0], "^[0-9]{1,8}$")) {
                LogMgr.log(1, "%s Invalid count limit.", "801");
                this.mResult.errType = 202;
                this.mResult.errMsg = MfiClientCallbackConst.MSG_FORMAT_ERROR;
                return;
            }
            int i = Integer.parseInt(strArrSplit[0]);
            if (!checkPattern(strArrSplit[1], "^[0-9]{1,8}$")) {
                LogMgr.log(1, "%s Invalid period.", "802");
                this.mResult.errType = 202;
                this.mResult.errMsg = MfiClientCallbackConst.MSG_FORMAT_ERROR;
                return;
            }
            int i2 = Integer.parseInt(strArrSplit[1]);
            String str = strArrSplit[3];
            if (!"0".equals(str) && !"1".equals(str) && !"2".equals(str)) {
                LogMgr.log(1, "%s Invalid version up type.", "803");
                this.mResult.errType = 202;
                this.mResult.errMsg = MfiClientCallbackConst.MSG_FORMAT_ERROR;
                return;
            }
            long time = new Date().getTime();
            long j = ((long) (60000 * i2)) + time;
            VersionUpData versionUpData = new VersionUpData();
            try {
                versionUpData.putCount(0);
                versionUpData.putCountLimit(i);
                versionUpData.putPeriod(i2);
                versionUpData.putReqDate(time);
                versionUpData.putReqDateLimit(j);
                versionUpData.putPlatformVersion(Build.VERSION.RELEASE);
                this.mResult.needsUpdate = "1".equals(str);
                if (!this.mResult.needsUpdate) {
                    CheckVersionUpTask.this.saveVersionUpData(versionUpData);
                }
                this.mResult.isSuccess = true;
                LogMgr.log(5, "%s", "999");
            } catch (JSONException e3) {
                LogMgr.log(1, "%s Invalid version up type.", "803");
                this.mResult.errType = 200;
                this.mResult.errMsg = e3.getMessage();
            }
        }

        /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
        public Result getResult() {
            return this.mResult;
        }

        private boolean checkPattern(String value, String pattern) {
            return Pattern.compile(pattern).matcher(value).find();
        }
    }

    class GetVersionUpDataSubTask extends StoppableTaskBase<VersionUpData> {
        VersionUpData mVersionUpData;

        GetVersionUpDataSubTask(int taskId) {
            super(taskId);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() throws Throwable {
            String file;
            LogMgr.log(5, "000");
            Context applicationContext = FelicaAdapter.getInstance().getApplicationContext();
            File file2 = new File(applicationContext.getFilesDir(), CheckVersionUpTask.this.getCacheFileName());
            if (file2.exists()) {
                LogMgr.log(6, "001");
                file = CacheUtil.readFile(file2);
            } else {
                LogMgr.log(6, "002 File does not exists.");
                file = null;
            }
            CacheUtil.deleteFiles(applicationContext.getCacheDir(), CheckVersionUpTask.VERSION_UP_DATA_FILE_NAME, null);
            CacheUtil.deleteFiles(applicationContext.getFilesDir(), CheckVersionUpTask.VERSION_UP_DATA_FILE_NAME, null);
            if (file != null) {
                try {
                    VersionUpData versionUpData = new VersionUpData(file);
                    if (!Build.VERSION.RELEASE.equals(versionUpData.optPlatformVersion())) {
                        LogMgr.log(2, "701 Different platformVersion. lastTime:" + versionUpData.optPlatformVersion() + " thisTime:" + Build.VERSION.RELEASE);
                        return;
                    }
                    this.mVersionUpData = versionUpData;
                } catch (JSONException unused) {
                    LogMgr.log(2, "700 " + file2.getName() + " is invalid.");
                    return;
                }
            }
            LogMgr.log(5, "999");
        }

        /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
        public VersionUpData getResult() {
            return this.mVersionUpData;
        }
    }
}

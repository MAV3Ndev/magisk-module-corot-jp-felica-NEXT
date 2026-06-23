package com.felicanetworks.mfc.mfi.fws;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
abstract class WritePipeTask<T> extends AsyncTaskBase<ParcelFileDescriptor> {
    private static final int PIPE_READ_SIDE = 0;
    private static final int PIPE_WRITE_SIDE = 1;
    private final IPipeEventCallback mCallback;
    private final T[] mDataArray;
    private ParcelFileDescriptor mWritePipe;

    abstract String createJsonString(T t) throws JSONException;

    WritePipeTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, T[] tArr, IPipeEventCallback iPipeEventCallback) {
        super(i, executorService, listener);
        this.mWritePipe = null;
        this.mDataArray = tArr;
        this.mCallback = iPipeEventCallback;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        LogMgr.log(6, "000");
        try {
            ParcelFileDescriptor[] parcelFileDescriptorArrCreateReliablePipe = ParcelFileDescriptor.createReliablePipe();
            try {
                LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "IPipeEventCallback", "onStart");
                this.mCallback.onStart(parcelFileDescriptorArrCreateReliablePipe[0]);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "IPipeEventCallback", "onStart");
                setResult(parcelFileDescriptorArrCreateReliablePipe[1]);
                try {
                    ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptorArrCreateReliablePipe[1]);
                    try {
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(autoCloseOutputStream);
                        try {
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
                            try {
                                int length = this.mDataArray.length;
                                objectOutputStream.writeInt(length);
                                LogMgr.log(6, "001 writeInt:" + length);
                                for (T t : this.mDataArray) {
                                    String strCreateJsonString = createJsonString(t);
                                    objectOutputStream.writeObject(strCreateJsonString);
                                    LogMgr.log(6, "002 writeObject:" + strCreateJsonString);
                                    if (isStopped()) {
                                        LogMgr.log(2, "702 Already has stopped.");
                                        objectOutputStream.flush();
                                        onFinished(false, 215, null);
                                        objectOutputStream.close();
                                        bufferedOutputStream.close();
                                        autoCloseOutputStream.close();
                                        return;
                                    }
                                }
                                objectOutputStream.flush();
                                objectOutputStream.close();
                                bufferedOutputStream.close();
                                autoCloseOutputStream.close();
                                LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "WritePipeTask", "onFinished");
                                onFinished(true, 0, null);
                                LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "WritePipeTask", "onFinished");
                                LogMgr.log(6, "999");
                            } finally {
                            }
                        } finally {
                        }
                    } finally {
                    }
                } catch (IOException | JSONException e) {
                    LogMgr.log(2, "703 " + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
                }
            } catch (RemoteException unused) {
                LogMgr.log(2, "701 Fail to call onStart.");
                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
            }
        } catch (IOException unused2) {
            LogMgr.log(2, "700 Fail to create pipe.");
            onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(ParcelFileDescriptor parcelFileDescriptor) {
        this.mWritePipe = parcelFileDescriptor;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public ParcelFileDescriptor getResult() {
        return this.mWritePipe;
    }
}

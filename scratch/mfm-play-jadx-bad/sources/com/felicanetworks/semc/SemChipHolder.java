package com.felicanetworks.semc;

import android.content.Context;
import com.felicanetworks.semc.omapi.AmsdAppletInfo;
import com.felicanetworks.semc.omapi.AppletManager;
import com.felicanetworks.semc.omapi.GpController;
import com.felicanetworks.semc.omapi.GpException;
import com.felicanetworks.semc.util.ErrorCodeConverter;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.StringUtil;

/* JADX INFO: loaded from: classes3.dex */
public class SemChipHolder {
    public static final String KEY_DEVICE_IDENTIFICATION_DATA = "KEY_DEVICE_IDENTIFICATION_DATA";
    public static final String KEY_SEID_DATA = "KEY_SEID_DATA";
    private static final byte[] SEID_PREFIX = {-16};
    private Context mContext;
    private String mSeReaderName;
    private OnCanceledListener mOnCanceledListener = null;
    private GpController mGpController = new GpController();

    public interface OnCanceledListener {
        void onCanceled();
    }

    public SemChipHolder(String str, Context context) {
        this.mSeReaderName = str;
        this.mContext = context;
    }

    public void init() throws GpException {
        LogMgr.log(7, "000");
        if (isConnected()) {
            LogMgr.log(7, "998");
            return;
        }
        try {
            try {
                try {
                    this.mGpController.close();
                    this.mGpController.init(this.mContext, this.mSeReaderName);
                    LogMgr.log(7, "999");
                } catch (GpException e) {
                    LogMgr.log(1, "801 : Failed access to chip");
                    throw e;
                }
            } catch (InterruptedException e2) {
                LogMgr.log(1, "800 : cancel occured.");
                throw new GpException(901, ObfuscatedMsgUtil.executionPoint(e2), null);
            } catch (Exception e3) {
                LogMgr.log(1, "802 : Unexpected Exception occurred: " + e3.getClass().getSimpleName() + " " + e3.getMessage());
                throw new GpException(900, ObfuscatedMsgUtil.executionPoint(e3), "UnknownError");
            }
        } catch (Throwable th) {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.close();
            }
            throw th;
        }
    }

    public GpController getGpController() throws GpException {
        LogMgr.log(7, "000");
        if (isConnected()) {
            LogMgr.log(7, "998");
            return this.mGpController;
        }
        init();
        LogMgr.log(7, "999");
        return this.mGpController;
    }

    public boolean isConnected() {
        LogMgr.log(7, "000");
        GpController gpController = this.mGpController;
        if (gpController != null) {
            return gpController.isInitialized();
        }
        LogMgr.log(7, "999");
        return false;
    }

    public void cancel(OnCanceledListener onCanceledListener) {
        LogMgr.log(7, "000");
        this.mOnCanceledListener = onCanceledListener;
        GpController gpController = this.mGpController;
        if (gpController != null) {
            gpController.setOnCanceledListener(onCanceledListener);
            this.mGpController.cancel();
        }
        LogMgr.log(7, "999");
    }

    public void removeOnCanceledListener() {
        LogMgr.log(7, "000");
        if (this.mOnCanceledListener != null) {
            this.mOnCanceledListener = null;
        }
        GpController gpController = this.mGpController;
        if (gpController != null) {
            gpController.removeOnCanceledListener();
        }
        LogMgr.log(7, "999");
    }

    public void reset() {
        LogMgr.log(7, "000");
        GpController gpController = this.mGpController;
        if (gpController != null) {
            gpController.closeChannel();
            this.mGpController.clearCancelFlag();
        }
        LogMgr.log(7, "999");
    }

    public void discard() {
        LogMgr.log(7, "000");
        synchronized (this) {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.close();
            }
        }
        LogMgr.log(7, "999");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public String doGetSeId() throws Throwable {
        GpController gpController;
        LogMgr.log(8, "000");
        GpController gpController2 = null;
        try {
            try {
                gpController = getGpController();
            } catch (Throwable th) {
                th = th;
            }
        } catch (GpException e) {
            e = e;
        } catch (Exception e2) {
            e = e2;
        }
        try {
            AmsdAppletInfo amsdAppletInfo = new AppletManager(gpController).getAmsdAppletInfo();
            byte[] shipmentKeyDerivationVendor = amsdAppletInfo.getShipmentKeyDerivationVendor();
            byte[] vseId = amsdAppletInfo.getVseId();
            if (shipmentKeyDerivationVendor == null || vseId == null) {
                LogMgr.log(2, "700 : Failed to get SEID.");
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
            }
            byte[] bArr = new byte[shipmentKeyDerivationVendor.length + 1 + vseId.length];
            byte[] bArr2 = SEID_PREFIX;
            System.arraycopy(bArr2, 0, bArr, 0, bArr2.length);
            int length = bArr2.length;
            System.arraycopy(shipmentKeyDerivationVendor, 0, bArr, length, shipmentKeyDerivationVendor.length);
            System.arraycopy(vseId, 0, bArr, length + shipmentKeyDerivationVendor.length, vseId.length);
            String strBytesToHexString = StringUtil.bytesToHexString(bArr);
            LogMgr.log(8, "999 ret[" + strBytesToHexString + "]");
            if (gpController != null) {
                gpController.closeChannel();
            }
            return strBytesToHexString;
        } catch (GpException e3) {
            e = e3;
            LogMgr.log(2, "701GpException occurred. e[" + e + "]");
            LogMgr.printStackTrace(8, e);
            throw new SemClientException(ErrorCodeConverter.convertException(1, e.getType()), ObfuscatedMsgUtil.executionPoint(e));
        } catch (Exception e4) {
            e = e4;
            LogMgr.log(2, "702Exception occurred. e[" + e + "]");
            LogMgr.printStackTrace(8, e);
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
        } catch (Throwable th2) {
            th = th2;
            gpController2 = gpController;
            if (gpController2 != null) {
                gpController2.closeChannel();
            }
            throw th;
        }
    }
}

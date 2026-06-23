package com.felicanetworks.mfc.felica.offlineimpl;

import android.os.Bundle;
import android.os.RemoteException;
import com.felicanetworks.felica.IFelicaRf;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.felica.FeliCaChipException;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FelicaRfController extends ChipController {
    private IFelicaRf mChip;

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    public NodeInformation requestMaskedCodeList(byte[] bArr, int i, int i2, int i3) throws OfflineException {
        return null;
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    public void setPrivacyFlag(byte[] bArr, PrivacySettingData[] privacySettingDataArr, int i, int i2) throws OfflineException {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    public void open() throws OfflineException {
        try {
            this.mChip = getFelicaRf();
            registerBinder(this.mChip.asBinder());
            try {
                try {
                    try {
                        try {
                            try {
                                LogMgr.log(3, "%s", "001");
                                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "open");
                                Bundle bundleOpen = this.mChip.open(MFC_PACKAGE_NAME, this.mToken);
                                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "open");
                                LogMgr.log(3, "%s", "002");
                                int i = bundleOpen.getInt("out");
                                int i2 = bundleOpen.getInt("e");
                                if (i2 != 0) {
                                    LogMgr.log(1, "%s", "701");
                                    throw new OfflineException(convertNfcErrorToOfflineException(i2));
                                }
                                this.mOpenHandle = i;
                                if (i2 != 0) {
                                    unregisterBinder();
                                    this.mChip = null;
                                }
                            } catch (OfflineException e) {
                                LogMgr.log(1, "%s", "703");
                                throw e;
                            }
                        } catch (RemoteException unused) {
                            LogMgr.log(1, "%s", "702");
                            throw new OfflineException(0);
                        }
                    } catch (Exception unused2) {
                        LogMgr.log(1, "%s", "705");
                        throw new OfflineException(0);
                    }
                } catch (SecurityException unused3) {
                    LogMgr.log(1, "%s", "704");
                    throw new OfflineException(0);
                }
            } catch (Throwable th) {
                if (-99 != 0) {
                    unregisterBinder();
                    this.mChip = null;
                }
                throw th;
            }
        } catch (FeliCaChipException unused4) {
            LogMgr.log(1, "%s", "700");
            throw new OfflineException(0);
        } catch (Exception unused5) {
            LogMgr.log(1, "%s", "703");
            throw new OfflineException(0);
        }
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    public void close(boolean z) {
        if (this.mChip != null) {
            if (z) {
                try {
                    reset();
                } catch (OfflineException unused) {
                    LogMgr.log(2, "%s", "700");
                }
            }
            try {
                try {
                    try {
                        LogMgr.log(3, "%s", "001");
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "close");
                        this.mChip.close(MFC_PACKAGE_NAME, this.mOpenHandle, this.mToken);
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "close");
                        LogMgr.log(3, "%s", "002");
                    } catch (SecurityException unused2) {
                        LogMgr.log(1, "%s", "703");
                    }
                } catch (RemoteException unused3) {
                    LogMgr.log(1, "%s", "702");
                } catch (Exception unused4) {
                    LogMgr.log(2, "%s", "704");
                }
            } finally {
                unregisterBinder();
                this.mChip = null;
            }
        }
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    public void connect(int i) throws OfflineException {
        try {
            LogMgr.log(3, "%s", "001");
            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "connect");
            int iConnect = this.mChip.connect(MFC_PACKAGE_NAME, this.mOpenHandle, i);
            LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "connect");
            LogMgr.log(3, "%s", "002");
            if (iConnect < 0) {
                int iConvertNfcErrorToOfflineException = convertNfcErrorToOfflineException(iConnect);
                if (iConnect == -20) {
                    LogMgr.log(1, "%s", "704");
                    throw new OfflineException(iConvertNfcErrorToOfflineException, FeliCaChipException.FELICA_CHIP_RW_STOP_MESSAGE);
                }
                if (iConnect == -14) {
                    LogMgr.log(1, "%s", "704");
                    throw new OfflineException(iConvertNfcErrorToOfflineException, "Cannot change discovery-state.");
                }
                LogMgr.log(1, "%s", "702");
                throw new OfflineException(iConvertNfcErrorToOfflineException);
            }
            this.mConnected = true;
        } catch (RemoteException unused) {
            LogMgr.log(1, "%s", "700");
            throw new OfflineException(0);
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "703");
            throw e;
        } catch (SecurityException unused2) {
            LogMgr.log(1, "%s", "704");
            throw new OfflineException(0);
        } catch (Exception unused3) {
            LogMgr.log(1, "%s", "701");
            throw new OfflineException(0);
        }
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    public void disconnect() throws OfflineException {
        try {
            try {
                LogMgr.log(3, "%s", "001");
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "disconnect");
                this.mChip.disconnect(MFC_PACKAGE_NAME, this.mOpenHandle);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "disconnect");
                LogMgr.log(3, "%s", "002");
            } catch (RemoteException unused) {
                LogMgr.log(1, "%s", "700");
                throw new OfflineException(0);
            } catch (SecurityException unused2) {
                LogMgr.log(1, "%s", "702");
                throw new OfflineException(0);
            } catch (Exception unused3) {
                LogMgr.log(1, "%s", "701");
                throw new OfflineException(0);
            }
        } finally {
            this.mConnected = false;
        }
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    public void reset() throws OfflineException {
        reset(false, false, true);
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    public void reset(boolean z, boolean z2, boolean z3) throws OfflineException {
        if (z3) {
            disconnect();
        }
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    protected Response execute(Command command, int i, int i2) throws OfflineException {
        LogMgr.log(6, "%s timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        if (command == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException("Invalid Command(null).");
        }
        Response responseDoExecution = null;
        for (int i3 = 0; i3 <= i2; i3++) {
            try {
                responseDoExecution = doExecution(command, i);
                break;
            } catch (OfflineException e) {
                if (e.getType() == 5 && e.getMessage() != null && e.getMessage().equalsIgnoreCase(FeliCaChipException.FELICA_CHIP_RW_STOP_MESSAGE)) {
                    LogMgr.log(1, "%s", "702");
                    throw new OfflineException(5, FeliCaChipException.FELICA_CHIP_RW_STOP_MESSAGE);
                }
                if (this.mCancel) {
                    LogMgr.log(2, "%s", "703");
                    throw new OfflineException(8);
                }
                if (i3 == i2) {
                    LogMgr.log(1, "%s", "701");
                    throw e;
                }
            }
        }
        return responseDoExecution;
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    protected Response doExecution(Command command, int i) throws OfflineException {
        int i2;
        int i3;
        try {
            try {
                this.mByteBuffer.setLength(0);
                this.mByteBuffer.append((byte) 0);
                this.mByteBuffer.set(0, (byte) (command.set(this.mByteBuffer) + 1));
                byte[] bArr = new byte[this.mByteBuffer.getLength()];
                this.mByteBuffer.copy(0, bArr, 0, bArr.length);
                LogMgr.logArray(4, bArr);
                LogMgr.log(3, "%s", "001");
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "transceive", "command len = " + bArr.length);
                try {
                    Bundle bundleTransceive = this.mChip.transceive(MFC_PACKAGE_NAME, this.mOpenHandle, bArr, i);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "transceive");
                    LogMgr.log(3, "%s", "002");
                    byte[] byteArray = bundleTransceive.getByteArray("out");
                    if (byteArray == null) {
                        int iConvertNfcErrorToOfflineException = convertNfcErrorToOfflineException(bundleTransceive.getInt("e"));
                        if (bundleTransceive.getInt("e") == -20) {
                            LogMgr.log(1, "%s", "nfc r/w stop!");
                            throw new OfflineException(iConvertNfcErrorToOfflineException, FeliCaChipException.FELICA_CHIP_RW_STOP_MESSAGE);
                        }
                        LogMgr.log(1, "%s", "return value not found!");
                        throw new OfflineException(iConvertNfcErrorToOfflineException);
                    }
                    LogMgr.logArray(4, byteArray);
                    try {
                        this.mByteBuffer.setLength(0);
                        this.mByteBuffer.append(byteArray);
                        return command.get(this.mByteBuffer);
                    } catch (RemoteException unused) {
                        i3 = 0;
                        LogMgr.log(1, "%s", "704");
                        throw new OfflineException(i3);
                    } catch (SecurityException unused2) {
                        i2 = 0;
                        LogMgr.log(1, "%s", "705");
                        throw new OfflineException(i2);
                    }
                } catch (RemoteException unused3) {
                    i3 = 0;
                } catch (SecurityException unused4) {
                    i2 = 0;
                }
            } catch (RemoteException unused5) {
                i3 = 0;
            } catch (SecurityException unused6) {
                i2 = 0;
            }
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        } catch (Exception unused7) {
            LogMgr.log(1, "%s", "704");
            throw new OfflineException(0);
        }
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    protected byte[] executeInner(byte[] bArr, int i, int i2) throws OfflineException {
        LogMgr.log(6, "%s timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        if (bArr == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException("Invalid Command(null).");
        }
        byte[] bArrDoExecutionInner = null;
        for (int i3 = 0; i3 <= i2; i3++) {
            try {
                bArrDoExecutionInner = doExecutionInner(bArr, i);
                break;
            } catch (OfflineException e) {
                if (this.mCancel) {
                    LogMgr.log(2, "%s", "703");
                    throw new OfflineException(8);
                }
                if (i3 == i2) {
                    LogMgr.log(1, "%s", "701");
                    throw e;
                }
            }
        }
        return bArrDoExecutionInner;
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    protected byte[] doExecutionInner(byte[] bArr, int i) throws OfflineException {
        try {
            LogMgr.logArray(4, bArr);
            LogMgr.log(3, "%s", "001");
            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "transceive", "command len = " + bArr.length);
            Bundle bundleTransceive = this.mChip.transceive(MFC_PACKAGE_NAME, this.mOpenHandle, bArr, i);
            LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "transceive");
            LogMgr.log(3, "%s", "002");
            byte[] byteArray = bundleTransceive.getByteArray("out");
            if (byteArray == null) {
                int iConvertNfcErrorToOfflineException = convertNfcErrorToOfflineException(bundleTransceive.getInt("e"));
                if (bundleTransceive.getInt("e") == -20) {
                    LogMgr.log(1, "%s", "nfc r/w stop!");
                    throw new OfflineException(iConvertNfcErrorToOfflineException, FeliCaChipException.FELICA_CHIP_RW_STOP_MESSAGE);
                }
                LogMgr.log(1, "%s", "return value not found!");
                throw new OfflineException(0);
            }
            LogMgr.logArray(4, byteArray);
            if (byteArray.length < 2 || (byteArray[0] & 255) != byteArray.length) {
                throw new OfflineException(4);
            }
            return byteArray;
        } catch (RemoteException unused) {
            LogMgr.log(1, "%s", "704");
            throw new OfflineException(0);
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        } catch (SecurityException unused2) {
            LogMgr.log(1, "%s", "705");
            throw new OfflineException(0);
        } catch (Exception unused3) {
            LogMgr.log(1, "%s", "701");
            throw new OfflineException(0);
        }
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.ChipController
    public void cancelOffline() {
        if (this.mProtectCancel) {
            return;
        }
        this.mCancel = true;
        if (this.mChip != null) {
            try {
                LogMgr.log(3, "%s", "001");
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "cancel");
                this.mChip.cancel(MFC_PACKAGE_NAME, this.mOpenHandle);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "IFelicaRf", "cancel");
                LogMgr.log(3, "%s", "002");
            } catch (Exception unused) {
                LogMgr.log(2, "%s", "700");
            }
        }
    }

    private IFelicaRf getFelicaRf() throws FeliCaChipException {
        IFelicaRf felicaRfService = this.mAdapter.getFelicaRfService();
        if (felicaRfService == null && (felicaRfService = this.mAdapter.getFelicaRfService()) == null) {
            throw new FeliCaChipException(0);
        }
        return felicaRfService;
    }
}

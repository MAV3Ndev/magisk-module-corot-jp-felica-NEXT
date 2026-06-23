package com.felicanetworks.mfm.main.model.internal.main.mfc;

import android.nfc.Tag;
import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfc.mfi.BuildConfig;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mnlib.Felica;
import com.felicanetworks.mnlib.felica.BlockList;
import com.felicanetworks.mnlib.felica.Data;
import com.felicanetworks.mnlib.felica.FelicaException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MfcAdapterExpert {
    private static final int ICCODE_LENGTH = 2;
    private static final int IC_TYPE_INDEX = 1;
    private static final int LITE_CARD_SYSTEMCODE = 34996;
    private static final byte LITE_IC_CODE = -15;
    private static final int STANDARD_CARD_SERVICECODE = 14728;
    private static final int STANDARD_CARD_SYSTEMCODE = 65024;
    private static final List<Byte> UNSUPPORTED_IC_CODE = Arrays.asList((byte) 6, (byte) 7, (byte) 16, (byte) 17, (byte) 18, (byte) 19, (byte) 20, (byte) 21, (byte) 22, (byte) 23, (byte) 24, (byte) 25, (byte) 26, (byte) 27, (byte) 28, (byte) 29, (byte) 30, (byte) 31, (byte) -32, (byte) -31, (byte) -30, (byte) -29, (byte) -28, (byte) -27, (byte) -26, (byte) -25, (byte) -24, (byte) -23, (byte) -22, (byte) -21, (byte) -20, (byte) -19, (byte) -18, (byte) -17, (byte) -16, (byte) -14, (byte) -13, (byte) -12, (byte) -11, (byte) -10, (byte) -9, (byte) -8, (byte) -7, (byte) -6, (byte) -5, (byte) -4, (byte) -3, (byte) -2, (byte) -1);
    private static final int WILD_CARD_SYSTEMCODE = 65535;
    private Felica _mfcAdapter;
    private byte icType;

    public static class Asset {
        public int balanceValue;
        public Date date1;
        public Date date2;
        public int point1;
        public int point2;
        public String serviceId;

        public Asset(String str, int i, int i2, int i3, Date date, Date date2) {
            this.serviceId = str;
            this.balanceValue = i;
            this.point1 = i2;
            this.point2 = i3;
            this.date1 = date != null ? (Date) date.clone() : null;
            this.date2 = date2 != null ? (Date) date2.clone() : null;
        }

        public String toString() {
            return "Asset{serviceId='" + this.serviceId + "', balanceValue=" + this.balanceValue + ", point1=" + this.point1 + ", point2=" + this.point2 + ", date1=" + this.date1 + ", date2=" + this.date2 + '}';
        }
    }

    public static class FpArea {
        private long fpNum;
        private List<FpService> fpServiceList;
        private boolean isReadWrite;
        private int totalPocket;

        public FpArea(long j, int i, List<FpService> list, boolean z) {
            this.fpNum = j;
            this.totalPocket = i;
            this.fpServiceList = list;
            this.isReadWrite = z;
        }

        public String toString() {
            return "FpArea{fpNum=" + this.fpNum + ", totalPocket=" + this.totalPocket + ", fpServiceList=" + this.fpServiceList + ", isReadWrite=" + this.isReadWrite + '}';
        }

        public long getFpNum() {
            return this.fpNum;
        }

        public int getTotalPocket() {
            return this.totalPocket;
        }

        public List<FpService> getFpServiceList() {
            return this.fpServiceList;
        }

        public boolean isReadWrite() {
            return this.isReadWrite;
        }

        public static class FpService {
            private byte[] fpIndex;
            private byte[] fpServiceNum;

            public FpService(byte[] bArr, byte[] bArr2) {
                if (bArr == null) {
                    this.fpServiceNum = new byte[0];
                } else {
                    this.fpServiceNum = (byte[]) bArr.clone();
                }
                if (bArr2 == null) {
                    this.fpIndex = new byte[0];
                } else {
                    this.fpIndex = (byte[]) bArr2.clone();
                }
            }

            public String toString() {
                return "FpService{fpServiceNum=" + Arrays.toString(this.fpServiceNum) + ", fpIndex=" + Arrays.toString(this.fpIndex) + '}';
            }

            public byte[] getFpServiceNum() {
                return this.fpServiceNum;
            }

            public byte[] getFpIndex() {
                return this.fpIndex;
            }
        }
    }

    public MfcAdapterExpert(Tag tag) {
        this._mfcAdapter = Felica.getInstance(tag);
    }

    public void open() throws MfcException {
        try {
            this._mfcAdapter.setTimeout(1000);
            this._mfcAdapter.setRetryCount(0);
            this._mfcAdapter.open();
        } catch (IOException e) {
            throw new MfcAdapterException(getClass(), BuildConfig.VERSION_CODE, e, MfcException.Type.FELICA_OPEN_ERROR);
        } catch (Exception e2) {
            throw new MfcAdapterException(getClass(), 257, e2, MfcException.Type.OTHER_ERROR);
        }
    }

    public boolean canGetSystemCodeList() throws MfcException {
        try {
            this._mfcAdapter.select(65535);
            byte[] iCCode = this._mfcAdapter.getICCode();
            if (iCCode != null && 2 == iCCode.length) {
                byte b = iCCode[1];
                this.icType = b;
                if (-15 == b) {
                    return false;
                }
            }
            return true;
        } catch (FelicaException e) {
            if (e.getType() != 7) {
                throw new MfcAdapterException(getClass(), InputDeviceCompat.SOURCE_GAMEPAD, e, MfcException.Type.MFC_OTHER_ERROR);
            }
            throw new MfcAdapterException(getClass(), 1024, e, MfcException.Type.FELICA_TIMEOUT_ERROR);
        } catch (IOException e2) {
            throw new MfcAdapterException(getClass(), 1026, e2, MfcException.Type.NFC_TRANSCEIVE_IO_ERROR);
        } catch (IllegalStateException e3) {
            throw new MfcAdapterException(getClass(), 1027, e3, MfcException.Type.NFC_ILLEGALSTATE_ERROR);
        } catch (Exception e4) {
            throw new MfcAdapterException(getClass(), 1028, e4, MfcException.Type.OTHER_ERROR);
        }
    }

    public int[] getSystemCodeList() throws MfcException {
        try {
            int[] systemCodeList = !UNSUPPORTED_IC_CODE.contains(Byte.valueOf(this.icType)) ? this._mfcAdapter.getSystemCodeList() : null;
            return systemCodeList == null ? new int[0] : systemCodeList;
        } catch (FelicaException e) {
            if (e.getType() != 7) {
                throw new MfcAdapterException(getClass(), 514, e, MfcException.Type.MFC_OTHER_ERROR);
            }
            throw new MfcAdapterException(getClass(), InputDeviceCompat.SOURCE_DPAD, e, MfcException.Type.FELICA_TIMEOUT_ERROR);
        } catch (IOException e2) {
            throw new MfcAdapterException(getClass(), 516, e2, MfcException.Type.NFC_TRANSCEIVE_IO_ERROR);
        } catch (IllegalStateException e3) {
            throw new MfcAdapterException(getClass(), 517, e3, MfcException.Type.NFC_ILLEGALSTATE_ERROR);
        } catch (Exception e4) {
            throw new MfcAdapterException(getClass(), 515, e4, MfcException.Type.OTHER_ERROR);
        }
    }

    public Data[] read(int i, BlockList blockList) throws MfcException {
        try {
            this._mfcAdapter.select(i);
            return this._mfcAdapter.read(blockList);
        } catch (FelicaException e) {
            int type = e.getType();
            if (type == 4) {
                throw new MfcAdapterException(getClass(), 770, e, MfcException.Type.MFC_READ_FAILED);
            }
            if (type != 7) {
                throw new MfcAdapterException(getClass(), 771, e, MfcException.Type.MFC_OTHER_ERROR);
            }
            throw new MfcAdapterException(getClass(), 769, e, MfcException.Type.FELICA_TIMEOUT_ERROR);
        } catch (IOException e2) {
            throw new MfcAdapterException(getClass(), 773, e2, MfcException.Type.NFC_TRANSCEIVE_IO_ERROR);
        } catch (IllegalStateException e3) {
            throw new MfcAdapterException(getClass(), 774, e3, MfcException.Type.NFC_ILLEGALSTATE_ERROR);
        } catch (Exception e4) {
            throw new MfcAdapterException(getClass(), 772, e4, MfcException.Type.OTHER_ERROR);
        }
    }

    public FpArea getFpInfoLiteS() throws MfcException {
        try {
            try {
                try {
                    this._mfcAdapter.select(34996);
                    ExtFelicaPocketReader extFelicaPocketReader = new ExtFelicaPocketReader(this._mfcAdapter);
                    Data[] cardIdInfoLite = extFelicaPocketReader.readCardIdInfoLite();
                    BlockList cardInfoLite = extFelicaPocketReader.parseCardInfoLite(cardIdInfoLite);
                    if (cardInfoLite == null) {
                        return null;
                    }
                    return extFelicaPocketReader.parseIndexInfoLite(extFelicaPocketReader.parseIndexInfoA(cardIdInfoLite), extFelicaPocketReader.readIndexInfoBLite(cardInfoLite));
                } catch (FelicaException e) {
                    int type = e.getType();
                    if (type == 4) {
                        throw new MfcAdapterException(getClass(), 1026, e, MfcException.Type.MFC_READ_FAILED);
                    }
                    if (type != 7) {
                        throw new MfcAdapterException(getClass(), 1027, e, MfcException.Type.MFC_OTHER_ERROR);
                    }
                    throw new MfcAdapterException(getClass(), InputDeviceCompat.SOURCE_GAMEPAD, e, MfcException.Type.FELICA_TIMEOUT_ERROR);
                }
            } catch (FelicaException e2) {
                if (7 == e2.getType()) {
                    return null;
                }
                throw e2;
            }
        } catch (IOException e3) {
            throw new MfcAdapterException(getClass(), 1029, e3, MfcException.Type.NFC_TRANSCEIVE_IO_ERROR);
        } catch (IllegalStateException e4) {
            throw new MfcAdapterException(getClass(), 1028, e4, MfcException.Type.NFC_ILLEGALSTATE_ERROR);
        } catch (Exception e5) {
            throw new MfcAdapterException(getClass(), 1030, e5, MfcException.Type.OTHER_ERROR);
        }
    }

    public FpArea getFpInfoStandard() throws MfcException {
        try {
            this._mfcAdapter.select(65024);
            this._mfcAdapter.getKeyVersion(14728);
            ExtFelicaPocketReader extFelicaPocketReader = new ExtFelicaPocketReader(this._mfcAdapter);
            return extFelicaPocketReader.parseIndexInfoStandard(extFelicaPocketReader.readIndexInfoStandard(extFelicaPocketReader.parseCardInfoStandard(extFelicaPocketReader.readCardIdInfoStandard())));
        } catch (FelicaException e) {
            int type = e.getType();
            if (type == 4) {
                throw new MfcAdapterException(getClass(), 1282, e, MfcException.Type.MFC_READ_FAILED);
            }
            if (type == 7) {
                throw new MfcAdapterException(getClass(), 1281, e, MfcException.Type.FELICA_TIMEOUT_ERROR);
            }
            if (type == 11) {
                return null;
            }
            throw new MfcAdapterException(getClass(), 1283, e, MfcException.Type.MFC_OTHER_ERROR);
        } catch (IOException e2) {
            throw new MfcAdapterException(getClass(), 1285, e2, MfcException.Type.NFC_TRANSCEIVE_IO_ERROR);
        } catch (IllegalStateException e3) {
            throw new MfcAdapterException(getClass(), 1284, e3, MfcException.Type.NFC_ILLEGALSTATE_ERROR);
        } catch (Exception e4) {
            throw new MfcAdapterException(getClass(), 1286, e4, MfcException.Type.OTHER_ERROR);
        }
    }

    public void close() {
        try {
            if (this._mfcAdapter != null) {
                this._mfcAdapter.close();
                this._mfcAdapter = null;
            }
        } catch (IOException unused) {
        }
    }
}

package com.felicanetworks.mfm.main.model;

import android.util.SparseArray;
import com.felicanetworks.mfm.main.model.info.FpAreaInfo;
import com.felicanetworks.mfm.main.model.info.FpServiceInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcAdapterExpert;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface FelicaPocketFunc {
    void cancel();

    CompiledFpState getCompiledFpState();

    FpAreaInfo getFpAreaInfo();

    public static class CompiledFpState {
        private FelicaFpState _felicaFpState;
        private ModelErrorInfo modelErrorInfo;

        public enum FelicaFpState {
            NO_PROBLEM,
            OPEN_ERROR,
            TIMEOUT_ERROR,
            USED_BY_OTHER_APP,
            RUNNING_BY_MFIC,
            OTHER_ERROR,
            UNSUPPORTED
        }

        public String toString() {
            return "CompiledFpState{_felicaFpState=" + this._felicaFpState + '}';
        }

        public CompiledFpState(FelicaFpState felicaFpState) {
            this._felicaFpState = felicaFpState;
        }

        public CompiledFpState(MfmException mfmException) {
            Exception firstCaughtException = mfmException.getFirstCaughtException();
            if (firstCaughtException instanceof MfcException) {
                MfcException mfcException = (MfcException) firstCaughtException;
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[mfcException.getErrorType().ordinal()];
                if (i == 1) {
                    this._felicaFpState = FelicaFpState.TIMEOUT_ERROR;
                    this.modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR, mfcException);
                    return;
                }
                if (i == 2) {
                    this._felicaFpState = FelicaFpState.OPEN_ERROR;
                    this.modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.FELICA_OPEN_ERROR, mfcException);
                    return;
                }
                if (i == 3) {
                    this._felicaFpState = FelicaFpState.USED_BY_OTHER_APP;
                    this.modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.USED_BY_OTHER_APP, mfcException);
                    return;
                } else if (i == 4) {
                    this._felicaFpState = FelicaFpState.RUNNING_BY_MFIC;
                    this.modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF, mfcException);
                    return;
                } else if (i == 5) {
                    this._felicaFpState = FelicaFpState.OTHER_ERROR;
                    this.modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.LOCKED_FELICA, mfcException);
                    return;
                } else {
                    this._felicaFpState = FelicaFpState.OTHER_ERROR;
                    this.modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.MFC_OTHER_ERROR, mfcException);
                    return;
                }
            }
            this._felicaFpState = FelicaFpState.OTHER_ERROR;
            this.modelErrorInfo = new ModelErrorInfo(ModelErrorInfo.Type.OTHER_ERROR, mfmException);
        }

        public FelicaFpState getFelicaFPState() {
            return this._felicaFpState;
        }

        public ModelErrorInfo getModelErrorInfo() {
            return this.modelErrorInfo;
        }

        public void clearState() {
            this._felicaFpState = null;
        }
    }

    public static class FpParser {
        ModelContext _context;
        private long fpNum;
        private SparseArray<FpService> fpServiceHash;
        private List<FpService> fpServiceList;
        private boolean isReadWrite;
        private int totalPocket;

        private void makeHashMap() {
            this.fpServiceHash = new SparseArray<>();
            for (FpService fpService : this.fpServiceList) {
                this.fpServiceHash.put(fpService.getFpServiceNum(), fpService);
            }
        }

        public FpParser(MfcExpert.FpArea fpArea, ModelContext modelContext) {
            this._context = modelContext;
            this.fpNum = fpArea.getFpNum();
            this.totalPocket = fpArea.getTotalPocket();
            this.isReadWrite = false;
            this.fpServiceList = new ArrayList();
            for (MfcExpert.FpArea.FpService fpService : fpArea.getFpServiceList()) {
                this.fpServiceList.add(new FpService(fpService.getFpServiceNum(), fpService.getFpIndex()));
            }
            makeHashMap();
        }

        public String toString() {
            return "FpParser{_context=" + this._context + ", fpNum=" + this.fpNum + ", totalPocket=" + this.totalPocket + ", isReadWrite=" + this.isReadWrite + ", fpServiceList=" + this.fpServiceList + ", fpServiceHash=" + this.fpServiceHash + '}';
        }

        public FpParser(MfcAdapterExpert.FpArea fpArea, ModelContext modelContext) {
            this._context = modelContext;
            this.fpNum = fpArea.getFpNum();
            this.totalPocket = fpArea.getTotalPocket();
            this.isReadWrite = fpArea.isReadWrite();
            this.fpServiceList = new ArrayList();
            for (MfcAdapterExpert.FpArea.FpService fpService : fpArea.getFpServiceList()) {
                this.fpServiceList.add(new FpService(fpService.getFpServiceNum(), fpService.getFpIndex()));
            }
            makeHashMap();
        }

        public FpService getFpService(int i) {
            return this.fpServiceHash.get(i);
        }

        public static class FpService {
            private byte[] fpIndex;
            private byte[] fpServiceNum;

            public FpService(byte[] bArr, byte[] bArr2) {
                this.fpServiceNum = bArr;
                this.fpIndex = bArr2;
            }

            public String toString() {
                return "FpService{fpServiceNum=" + Arrays.toString(this.fpServiceNum) + ", fpIndex=" + Arrays.toString(this.fpIndex) + '}';
            }

            public int getFpServiceNum() {
                return convertIndexServiceNo(this.fpServiceNum, 0);
            }

            public long getFpPoint() {
                return getUInt32b(this.fpIndex, 0);
            }

            private static long getUInt32b(byte[] bArr, int i) {
                return ((long) (bArr[i + 3] & 255)) | (((long) (bArr[i] & 255)) << 24) | ((long) ((bArr[i + 1] & 255) << 16)) | ((long) ((bArr[i + 2] & 255) << 8));
            }

            private static short getInt16b(byte[] bArr, int i) {
                return (short) ((bArr[i + 1] & 255) | ((bArr[i] & 255) << 8));
            }

            private static int convertIndexServiceNo(byte[] bArr, int i) {
                int i2 = i + 1;
                if (bArr[i2] == 0 && bArr[i + 2] == 0) {
                    return 0;
                }
                if ((bArr[i2] & (-128)) != 0) {
                    return (bArr[i] & 255) | ((bArr[i2] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
                }
                return getInt16b(bArr, i2);
            }
        }

        public FpAreaInfo marshalFpAreaInfo() throws DatabaseException {
            DatabaseExpert databaseExpert;
            int i;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            ArrayList arrayList5 = new ArrayList();
            DatabaseExpert openedDatabaseExpert = this._context.getOpenedDatabaseExpert();
            int size = this.fpServiceList.size();
            Integer[] numArr = new Integer[size];
            int i2 = 0;
            for (int i3 = 0; i3 < this.fpServiceList.size(); i3++) {
                numArr[i3] = Integer.valueOf(this.fpServiceList.get(i3).getFpServiceNum());
            }
            Arrays.sort(numArr, Collections.reverseOrder());
            while (i2 < size) {
                FpService fpService = getFpService(numArr[i2].intValue());
                int fpServiceNum = fpService.getFpServiceNum();
                if (fpServiceNum == 0) {
                    databaseExpert = openedDatabaseExpert;
                    i = size;
                } else {
                    if ((1 > fpServiceNum || fpServiceNum > 32767) && (8388609 > fpServiceNum || fpServiceNum > 16777214)) {
                        return null;
                    }
                    DatabaseExpert.FelicaPocketService fpService2 = openedDatabaseExpert.getFpService(fpService.getFpServiceNum());
                    int i4 = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$info$FpServiceInfo$Type[getFpType(fpService2).ordinal()];
                    if (i4 == 1) {
                        databaseExpert = openedDatabaseExpert;
                        i = size;
                        arrayList2.add(new FpServiceInfo(fpService.getFpServiceNum(), fpService2.fpServiceName, fpService.getFpPoint(), fpService2.pointUnitName, fpService2.fpServiceIconUrl, fpService2.fpProviderName, fpService2.webUrl, new NetworkExpert(this._context), FpServiceInfo.Type.POINT));
                    } else if (i4 == 2) {
                        databaseExpert = openedDatabaseExpert;
                        i = size;
                        arrayList3.add(new FpServiceInfo(fpService.getFpServiceNum(), fpService2.fpServiceName, 0L, fpService2.pointUnitName, fpService2.fpServiceIconUrl, fpService2.fpProviderName, fpService2.webUrl, new NetworkExpert(this._context), FpServiceInfo.Type.OTHER));
                    } else if (i4 == 3) {
                        databaseExpert = openedDatabaseExpert;
                        i = size;
                        arrayList4.add(new FpServiceInfo(fpService.getFpServiceNum(), fpService2.fpServiceName, 0L, fpService2.pointUnitName, fpService2.fpServiceIconUrl, fpService2.fpProviderName, fpService2.webUrl, new NetworkExpert(this._context), FpServiceInfo.Type.UNSUPPORTED));
                    } else {
                        arrayList5.add(new FpServiceInfo(fpService.getFpServiceNum(), null, 0L, null, null, null, (String) Sg.getValue(Sg.Key.NET_FP_UNKNOWN_SERVICE_LINK_URL), null, FpServiceInfo.Type.UNKNOWN));
                        databaseExpert = openedDatabaseExpert;
                        i = size;
                    }
                }
                i2++;
                openedDatabaseExpert = databaseExpert;
                size = i;
            }
            arrayList.addAll(arrayList2);
            arrayList.addAll(arrayList3);
            arrayList.addAll(arrayList4);
            arrayList.addAll(arrayList5);
            if (this.isReadWrite) {
                int size2 = arrayList.size();
                int i5 = this.totalPocket;
                if (size2 >= i5) {
                    return null;
                }
                this.totalPocket = i5 - 1;
            }
            return new FpAreaInfo(this.fpNum, this.totalPocket, arrayList.size(), arrayList);
        }

        private FpServiceInfo.Type getFpType(DatabaseExpert.FelicaPocketService felicaPocketService) {
            if (felicaPocketService != null) {
                if (felicaPocketService.serviceEnable == 0) {
                    return FpServiceInfo.Type.UNSUPPORTED;
                }
                if (felicaPocketService.isPointService() && felicaPocketService.needShowValue()) {
                    return FpServiceInfo.Type.POINT;
                }
                if (!felicaPocketService.isPointService() || !felicaPocketService.needShowValue()) {
                    return FpServiceInfo.Type.OTHER;
                }
            }
            return FpServiceInfo.Type.UNKNOWN;
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.FelicaPocketFunc$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$FpServiceInfo$Type;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type;

        static {
            int[] iArr = new int[FpServiceInfo.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$FpServiceInfo$Type = iArr;
            try {
                iArr[FpServiceInfo.Type.POINT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$FpServiceInfo$Type[FpServiceInfo.Type.OTHER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$FpServiceInfo$Type[FpServiceInfo.Type.UNSUPPORTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[MfcException.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type = iArr2;
            try {
                iArr2[MfcException.Type.FELICA_TIMEOUT_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.FELICA_OPEN_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFC_USED_BY_OTHER_APP.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.MFIC_RUNNING_BY_ITSELF.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$Type[MfcException.Type.LOCKED_FELICA.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }
}

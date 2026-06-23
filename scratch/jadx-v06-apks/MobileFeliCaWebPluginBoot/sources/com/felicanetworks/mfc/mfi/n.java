package com.felicanetworks.mfc.mfi;

import android.os.IInterface;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.DeviceList;
import com.felicanetworks.mfc.FelicaResultInfo;
import com.felicanetworks.mfc.FelicaResultInfoByteArray;
import com.felicanetworks.mfc.FelicaResultInfoDataArray;
import com.felicanetworks.mfc.FelicaResultInfoInt;
import com.felicanetworks.mfc.PushSegmentParcelableWrapper;

/* JADX INFO: compiled from: IMfiFelica.java */
/* JADX INFO: loaded from: classes.dex */
public interface n extends IInterface {
    FelicaResultInfo C(String str, DeviceList deviceList, com.felicanetworks.mfc.l0 l0Var, n nVar);

    FelicaResultInfo D(String str, com.felicanetworks.mfc.q0 q0Var);

    FelicaResultInfo d(BlockDataList blockDataList, int i, int i2);

    FelicaResultInfo e();

    void f(String str);

    FelicaResultInfoByteArray i();

    FelicaResultInfo j(int i, int i2);

    FelicaResultInfoByteArray k();

    FelicaResultInfo l(PushSegmentParcelableWrapper pushSegmentParcelableWrapper);

    FelicaResultInfo m();

    void n();

    FelicaResultInfo o(int i);

    FelicaResultInfoByteArray p(int i, int i2);

    FelicaResultInfo r();

    FelicaResultInfoInt s(int i, int i2, int i3);

    FelicaResultInfoDataArray t(BlockList blockList, int i, int i2);

    FelicaResultInfo u();

    FelicaResultInfo x(int i, int i2, int i3);
}

package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.CyclicData;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.PurseData;
import com.felicanetworks.mfc.RandomData;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

/* JADX INFO: compiled from: ReadWithoutEncryption.java */
/* JADX INFO: loaded from: classes.dex */
public class s0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private h0 f175a;

    public s0(h0 h0Var) {
        this.f175a = h0Var;
    }

    private static i0 a(Data[] dataArr) {
        StringBuilder sb = new StringBuilder();
        for (Data data : dataArr) {
            sb.append(b1.K(d(data)));
        }
        return new i0("data", sb.toString());
    }

    private static Data[] c(Felica felica, int i, int i2, int i3) {
        BlockList blockList = new BlockList();
        while (i2 < i3) {
            blockList.a(new Block(i, i2));
            i2++;
        }
        return felica.F(blockList);
    }

    private static byte[] d(Data data) {
        int iC = data.c();
        if (iC == 1) {
            return ((RandomData) data).d();
        }
        if (iC == 2) {
            return ((CyclicData) data).d();
        }
        if (iC == 3) {
            return e((PurseData) data);
        }
        throw new c1(s0.class, "createItem");
    }

    private static byte[] e(PurseData purseData) {
        return ByteBuffer.allocate(16).put(f(purseData.f(), 4)).put(f(purseData.d(), 4)).put(purseData.g()).putChar((char) purseData.e()).array();
    }

    private static byte[] f(long j, int i) {
        byte[] bArr = new byte[i];
        System.arraycopy(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(j).array(), 0, bArr, 0, i);
        return bArr;
    }

    public void b(int i, int i2, int i3, g0 g0Var) throws e {
        try {
            i0 i0VarA = a(c(this.f175a.e().c(), i, i2, i3 + i2));
            ArrayList arrayList = new ArrayList();
            arrayList.add(i0VarA);
            g0Var.a(arrayList);
        } catch (com.felicanetworks.mfc.x e) {
            throw j.b(e, e.getMessage());
        }
    }
}

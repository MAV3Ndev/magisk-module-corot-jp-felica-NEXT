package com.felicanetworks.mfc;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public class Felica extends Service {
    static int m = 10000;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f78a;
    private int b;
    private v c;
    private w d;
    private String[] g;
    private r e = new r(this, Looper.myLooper());
    private u f = new u(this);
    private q0 h = new s(this);
    private o0 i = null;
    private final IBinder j = new t(this);
    private x0 k = null;
    private com.felicanetworks.mfc.mfi.j0 l = null;

    public Felica() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        this.b = 1000;
        this.f78a = 0;
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    private synchronized void k(w wVar) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        if (wVar == null) {
            throw new IllegalArgumentException("The specified Listener is null.");
        }
        q();
        this.k.o(getPackageName(), wVar);
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    private boolean n() {
        return (this.i == null && this.d == null) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean o() {
        return this.i == null && this.d != null;
    }

    public synchronized void A() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        if (C()) {
            this.k.z();
            return;
        }
        if (n()) {
            if (o()) {
                K();
                return;
            }
            try {
                t0.a(this.i.r());
                K();
                com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
            } catch (x e) {
                com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
                if (e.a() != 2 || e.e() != 5) {
                    throw e;
                }
                com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            } catch (Exception unused) {
                com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
                throw new x(1, 47);
            }
        }
    }

    boolean B() {
        try {
            if (this.k == null) {
                return false;
            }
            this.k.q();
            return true;
        } catch (x unused) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
            return false;
        }
    }

    boolean C() {
        x0 x0Var = this.k;
        if (x0Var == null) {
            return false;
        }
        return x0Var.r();
    }

    public synchronized void D() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            p();
            if (this.i != null) {
                t0.a(this.i.u());
            } else {
                t0.a(this.k.y().u());
            }
            com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        } catch (x e) {
            com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
            throw e;
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
            throw new x(1, 47);
        }
    }

    public synchronized void E(PushSegment pushSegment) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            p();
            if (pushSegment == null) {
                com.felicanetworks.mfc.s1.a.c(2, "%s %s", "710", "Parameter Error");
                throw new IllegalArgumentException("The specified parameter is invalid.");
            }
            if (this.i != null) {
                t0.a(this.i.l(new PushSegmentParcelableWrapper(pushSegment)));
            } else {
                t0.a(this.k.y().l(new PushSegmentParcelableWrapper(pushSegment)));
            }
            com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        } catch (x e) {
            com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
            throw e;
        } catch (IllegalArgumentException e2) {
            com.felicanetworks.mfc.s1.a.d(2, "%s %s %s", "702", "IllegalArgumentException", e2.getMessage());
            throw e2;
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
            throw new x(1, 47);
        }
    }

    public synchronized Data[] F(BlockList blockList) {
        Data[] dataArr;
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            try {
                p();
                if (blockList == null || blockList.d() == 0) {
                    com.felicanetworks.mfc.s1.a.c(2, "%s %s", "710", "Parameter Error");
                    throw new IllegalArgumentException("The specified BlockList is null or empty.");
                }
                FelicaResultInfoDataArray felicaResultInfoDataArrayT = this.i != null ? this.i.t(blockList, this.b, this.f78a) : this.k.y().t(blockList, this.b, this.f78a);
                t0.a(felicaResultInfoDataArrayT);
                dataArr = (Data[]) felicaResultInfoDataArrayT.i();
                com.felicanetworks.mfc.s1.a.c(3, "%s returned %d", "999", dataArr);
            } catch (IllegalArgumentException e) {
                com.felicanetworks.mfc.s1.a.b(2, "%s IllegalArgumentException", "702");
                throw e;
            }
        } catch (x e2) {
            com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e2.a()), Integer.valueOf(e2.e()));
            throw e2;
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
            throw new x(1, 47);
        }
        return dataArr;
    }

    public synchronized void G(int i) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            p();
            if (i < 0 || i > 65535) {
                com.felicanetworks.mfc.s1.a.c(2, "%s systemCode:%d", "710", Integer.valueOf(i));
                throw new IllegalArgumentException("The specified System Code is out of range.");
            }
            if (i == 65535 || (i & 65280) == 65280 || (i & 255) == 255) {
                com.felicanetworks.mfc.s1.a.c(2, "%s systemCode:%d", "711", Integer.valueOf(i));
                throw new IllegalArgumentException("The specified System Code is out of range.");
            }
            if (this.i != null) {
                t0.a(this.i.o(i));
            } else {
                t0.a(this.k.y().o(i));
            }
            com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        } catch (x e) {
            com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
            throw e;
        } catch (IllegalArgumentException e2) {
            com.felicanetworks.mfc.s1.a.c(2, "%s systemCode:%d", "702", Integer.valueOf(i));
            throw e2;
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
            throw new x(1, 47);
        }
    }

    public synchronized void H(int i, int i2) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            try {
                try {
                    p();
                    if (i != 0 && i != 1) {
                        com.felicanetworks.mfc.s1.a.c(2, "%s target:%d", "710", Integer.valueOf(i));
                        throw new IllegalArgumentException("The specified Target is invalid value.");
                    }
                    if (i2 < 0 || i2 > 65535) {
                        com.felicanetworks.mfc.s1.a.c(2, "%s systemCode:%d", "711", Integer.valueOf(i2));
                        throw new IllegalArgumentException("The specified System Code is out of range.");
                    }
                    if (i == 0 && (i2 == 65535 || (i2 & 65280) == 65280 || (i2 & 255) == 255)) {
                        com.felicanetworks.mfc.s1.a.c(2, "%s systemCode:%d", "712", Integer.valueOf(i2));
                        throw new IllegalArgumentException("The specified System Code is out of range.");
                    }
                    if (this.i != null) {
                        t0.a(this.i.j(i, i2));
                    } else {
                        t0.a(this.k.y().j(i, i2));
                    }
                    com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
                } catch (Exception unused) {
                    com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
                    throw new x(1, 47);
                }
            } catch (IllegalArgumentException e) {
                com.felicanetworks.mfc.s1.a.c(2, "%s systemCode:%d", "702", Integer.valueOf(i2));
                throw e;
            }
        } catch (x e2) {
            com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e2.a()), Integer.valueOf(e2.e()));
            throw e2;
        }
    }

    public synchronized void I(int i) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            p();
            if (i != 2 && i != 4) {
                com.felicanetworks.mfc.s1.a.d(2, "%s %s nodeCodeSize:%d", "710", "Parameter Error", Integer.valueOf(i));
                throw new IllegalArgumentException("The specified NodeCodeSize is invalid value.");
            }
            if (this.i != null) {
                t0.a(this.i.x(i, this.b, this.f78a));
            } else {
                t0.a(this.k.y().x(i, this.b, this.f78a));
            }
            com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        } catch (x e) {
            com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
            throw e;
        } catch (IllegalArgumentException e2) {
            com.felicanetworks.mfc.s1.a.c(2, "%s IllegalArgumentException nodeCodeSize:%d", "702", Integer.valueOf(i));
            throw e2;
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
            throw new x(1, 47);
        }
    }

    public synchronized void J(int i) {
        com.felicanetworks.mfc.s1.a.c(3, "%s timeout:%d", "000", Integer.valueOf(i));
        if (i < 0) {
            this.b = 0;
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
        } else if (i > 60000) {
            this.b = 60000;
            com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
        } else {
            this.b = i;
            com.felicanetworks.mfc.s1.a.b(7, "%s", "003");
        }
        com.felicanetworks.mfc.s1.a.c(3, "%s timeout:%d is set", "999", Integer.valueOf(this.b));
    }

    protected void K() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        try {
            try {
                com.felicanetworks.mfc.s1.a.b(3, "%s", "001");
                unbindService(this.f);
                com.felicanetworks.mfc.s1.a.b(3, "%s", "002");
            } catch (Exception unused) {
                com.felicanetworks.mfc.s1.a.c(7, "%s %s", "004", "Unbind failed");
            }
            com.felicanetworks.mfc.s1.a.b(7, "%s", "003");
            s();
            com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        } catch (Throwable th) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "003");
            s();
            throw th;
        }
    }

    public synchronized void L(BlockDataList blockDataList) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            try {
                p();
                if (blockDataList == null || blockDataList.c() == 0) {
                    com.felicanetworks.mfc.s1.a.c(2, "%s %s", "710", "Parameter Error");
                    throw new IllegalArgumentException("The specified BlockDataList is null or empty.");
                }
                if (this.i != null) {
                    t0.a(this.i.d(blockDataList, this.b, this.f78a));
                } else {
                    t0.a(this.k.y().d(blockDataList, this.b, this.f78a));
                }
                com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
            } catch (Exception unused) {
                com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
                throw new x(1, 47);
            }
        } catch (x e) {
            com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
            throw e;
        } catch (IllegalArgumentException e2) {
            com.felicanetworks.mfc.s1.a.b(2, "%s IllegalArgumentException", "702");
            throw e2;
        }
    }

    public synchronized void j(String[] strArr, w wVar) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        if (wVar == null) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "710", "Parameter Error");
            throw new IllegalArgumentException("The specified Listener is null.");
        }
        if (strArr != null && strArr.length > 50) {
            com.felicanetworks.mfc.s1.a.b(2, "%s permitList.length > MAX_PERMIT_LIST_SIZE", "711");
            throw new IllegalArgumentException("The size of permit list exceeds the maximum value.");
        }
        q();
        if (strArr != null && strArr.length >= 1 && "Y29tLmZlbGljYW5ldHdvcmtzLm1mYy5tZmkuTWZpQ2xpZW50".equals(strArr[0])) {
            k(wVar);
            return;
        }
        this.g = strArr;
        this.d = wVar;
        try {
            l();
            com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.b(2, "%s", "712");
            this.g = null;
            this.d = null;
            throw new x(1, 47);
        }
    }

    protected void l() throws x {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        if (!r1.b(this, "F6:F8:9C:84:F4:D6:D1:C9:39:0B:D2:D5:E5:11:81:00:0F:04:B7:4B:1A:2E:37:30:6F:2F:1B:EC:92:2B:2A:51", "com.felicanetworks.mfc")) {
            com.felicanetworks.mfc.s1.a.a(3, "700 Failed to connect for MFC Service. AppCertHash check failed.");
            throw new x(1, 47);
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.felicanetworks.mfc", "com.felicanetworks.mfc.FelicaAdapter"));
        if (bindService(intent, this.f, 1)) {
            this.e.a(m);
            com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        } else {
            com.felicanetworks.mfc.s1.a.a(3, "701 Failed to connect for MFC Service");
            unbindService(this.f);
            throw new x(1, 47);
        }
    }

    protected void m() throws x {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        if (this.i == null || this.d != null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            throw new x(2, 5);
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        ServiceInfo serviceInfo;
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            ServiceInfo[] serviceInfoArr = getPackageManager().getPackageInfo(getPackageName(), 4).services;
            int length = serviceInfoArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    serviceInfo = null;
                    break;
                }
                serviceInfo = serviceInfoArr[i];
                if (serviceInfo.name.equals(getClass().getName())) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                    break;
                }
                i++;
            }
            if (serviceInfo == null) {
                com.felicanetworks.mfc.s1.a.b(1, "%s", "800 service tag is not found.");
                return null;
            }
            if (serviceInfo.exported) {
                com.felicanetworks.mfc.s1.a.b(1, "%s", "801 exported tag is enable.");
                return null;
            }
            this.k = new x0(this);
            this.l = new com.felicanetworks.mfc.mfi.j0(this.k);
            com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
            return this.j;
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.b(1, "%s", "802");
            return null;
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            synchronized (this) {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                if (this.k != null) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "005");
                    this.k.x();
                    this.k = null;
                }
                if (this.l != null) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "006");
                    this.l.b();
                    this.l = null;
                }
                try {
                    if (this.i != null) {
                        com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
                        this.i.m();
                        this.i.r();
                    }
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.c(6, "%s %s", "003", e.getMessage());
                }
                K();
            }
        } catch (Exception e2) {
            com.felicanetworks.mfc.s1.a.c(6, "%s %s", "004", e2.getMessage());
        }
        super.onDestroy();
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            synchronized (this) {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                if (this.k != null) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "005");
                    this.k.x();
                    this.k = null;
                }
                if (this.l != null) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "006");
                    this.l.b();
                    this.l = null;
                }
                try {
                    if (this.i != null) {
                        com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
                        this.i.m();
                        this.i.r();
                    }
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.c(6, "%s %s", "003", e.getMessage());
                }
                K();
            }
        } catch (Exception e2) {
            com.felicanetworks.mfc.s1.a.c(6, "%s %s", "004", e2.getMessage());
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        return super.onUnbind(intent);
    }

    protected void p() throws x {
        boolean z;
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        try {
            m();
            z = true;
        } catch (x unused) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            z = false;
        }
        boolean zB = B();
        if (z || zB) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        } else {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "003");
            throw new x(2, 5);
        }
    }

    protected void q() throws x {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        if (this.d != null) {
            com.felicanetworks.mfc.s1.a.e(3, "%s %s id:%d type:%d", "700", "FelicaException", 2, 49);
            throw new x(2, 49);
        }
        if (this.i != null) {
            com.felicanetworks.mfc.s1.a.e(3, "%s %s id:%d type:%d", "701", "FelicaException", 2, 42);
            throw new x(2, 42);
        }
        x0 x0Var = this.k;
        if (x0Var != null) {
            x0Var.t();
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    synchronized void r() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            m();
            t0.a(this.i.e());
            com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        } catch (x e) {
            com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
            throw e;
        } catch (NumberFormatException e2) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "NumberFormatException");
            throw e2;
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "702", "Other Exception");
            throw new x(1, 47);
        }
    }

    protected void s() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        this.i = null;
        this.d = null;
        this.g = null;
        if (this.c != null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            this.c.E(null);
            throw null;
        }
        this.c = null;
        this.b = 1000;
        this.f78a = 0;
        this.e.b();
        com.felicanetworks.mfc.s1.a.d(7, "%s timeout = %d, retryCount = %d", "001", Integer.valueOf(this.b), Integer.valueOf(this.f78a));
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    public synchronized void t() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            try {
                p();
                if (this.i != null) {
                    t0.a(this.i.m());
                } else {
                    t0.a(this.k.y().m());
                }
                if (this.c != null) {
                    this.c.E(null);
                    throw null;
                }
                this.c = null;
                this.b = 1000;
                this.f78a = 0;
                com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
            } catch (x e) {
                com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
                throw e;
            }
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
            throw new x(1, 47);
        }
    }

    public synchronized byte[] u() {
        byte[] bArr;
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            p();
            FelicaResultInfoByteArray felicaResultInfoByteArrayP = this.i != null ? this.i.p(this.b, this.f78a) : this.k.y().p(this.b, this.f78a);
            t0.a(felicaResultInfoByteArrayP);
            bArr = (byte[]) felicaResultInfoByteArrayP.i();
            com.felicanetworks.mfc.s1.a.c(3, "%s returned %s", "999", bArr);
        } catch (x e) {
            com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
            throw e;
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
            throw new x(1, 47);
        }
        return bArr;
    }

    public synchronized byte[] v() {
        byte[] bArr;
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            try {
                p();
                FelicaResultInfoByteArray felicaResultInfoByteArrayI = this.i != null ? this.i.i() : this.k.y().i();
                t0.a(felicaResultInfoByteArrayI);
                bArr = (byte[]) felicaResultInfoByteArrayI.i();
                com.felicanetworks.mfc.s1.a.c(3, "%s returned %s", "999", bArr);
            } catch (x e) {
                com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
                throw e;
            }
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
            throw new x(1, 47);
        }
        return bArr;
    }

    public synchronized byte[] w() {
        byte[] bArr;
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            try {
                p();
                FelicaResultInfoByteArray felicaResultInfoByteArrayK = this.i != null ? this.i.k() : this.k.y().k();
                t0.a(felicaResultInfoByteArrayK);
                bArr = (byte[]) felicaResultInfoByteArrayK.i();
                com.felicanetworks.mfc.s1.a.c(3, "%s returned %s", "999", bArr);
            } catch (x e) {
                com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
                throw e;
            }
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
            throw new x(1, 47);
        }
        return bArr;
    }

    synchronized o0 x() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        return this.i;
    }

    public synchronized int y(int i) {
        int iIntValue;
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            p();
            FelicaResultInfoInt felicaResultInfoIntS = this.i != null ? this.i.s(i, this.b, this.f78a) : this.k.y().s(i, this.b, this.f78a);
            t0.a(felicaResultInfoIntS);
            iIntValue = ((Integer) felicaResultInfoIntS.i()).intValue();
            com.felicanetworks.mfc.s1.a.c(3, "%s returned %d", "999", Integer.valueOf(iIntValue));
        } catch (x e) {
            com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
            throw e;
        } catch (IllegalArgumentException e2) {
            com.felicanetworks.mfc.s1.a.c(2, "%s serviceCode:%d", "702", Integer.valueOf(i));
            throw e2;
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
            throw new x(1, 47);
        }
        return iIntValue;
    }

    x0 z() {
        return this.k;
    }
}

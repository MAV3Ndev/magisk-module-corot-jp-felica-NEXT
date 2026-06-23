package com.felicanetworks.mfc;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public class FSC extends Service {
    static int l = 10000;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Felica f77a;
    private q b;
    private DeviceList d;
    private String g;
    private m c = new m(this, Looper.myLooper());
    private boolean e = false;
    private boolean f = false;
    private l0 h = new l(this);
    private j0 i = null;
    private p j = new p(this);
    private final IBinder k = new o(this);

    public FSC() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void A() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        if (this.f77a != null) {
            this.f77a.z().v();
        }
        this.f = false;
        this.e = false;
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    private void q() throws x {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        if (this.e || u()) {
            com.felicanetworks.mfc.s1.a.c(2, "%s", "700", "online processing");
            throw new x(2, 2);
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean u() {
        if (this.f77a != null && this.f77a.z() != null) {
            if (this.f77a.z().A()) {
                return true;
            }
        }
        return false;
    }

    protected void B() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(3, "%s", "001");
        try {
            unbindService(this.j);
        } catch (Exception e) {
            com.felicanetworks.mfc.s1.a.d(7, "%s %s msg:", "002", "Exception", e.getMessage());
        }
        this.i = null;
        this.e = false;
        this.f = false;
        this.c.b();
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
            com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
            return this.k;
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
                try {
                    if (this.i != null) {
                        com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
                        this.i.n();
                    }
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.c(6, "%s %s", "003", e.getMessage());
                }
                B();
                this.b = null;
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
                try {
                    if (this.i != null) {
                        com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
                        this.i.n();
                    }
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.c(6, "%s %s", "003", e.getMessage());
                }
                B();
                this.b = null;
            }
        } catch (Exception e2) {
            com.felicanetworks.mfc.s1.a.c(6, "%s %s", "004", e2.getMessage());
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        return super.onUnbind(intent);
    }

    protected void p() throws x {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        if (!r1.b(this, "F6:F8:9C:84:F4:D6:D1:C9:39:0B:D2:D5:E5:11:81:00:0F:04:B7:4B:1A:2E:37:30:6F:2F:1B:EC:92:2B:2A:51", "com.felicanetworks.mfc")) {
            com.felicanetworks.mfc.s1.a.a(3, "700 Failed to connect for FSC Service. AppCertHash check failed.");
            throw new x(1, 47);
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.felicanetworks.mfc", "com.felicanetworks.mfc.FSCAdapter"));
        if (!bindService(intent, this.j, 1)) {
            com.felicanetworks.mfc.s1.a.a(3, "701 Failed to connect for MFC Service");
            throw new x(1, 47);
        }
        this.c.a(l);
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    protected n r(x xVar) {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        int iE = xVar.e();
        String str = "Unknown error.";
        if (iE == 1) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "005");
            str = "FeliCa chip is not opened yet.";
        } else if (iE != 2) {
            switch (iE) {
                case 24:
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
                    str = "Felica not set.";
                    break;
                case 25:
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "003");
                    str = "Device list not set.";
                    break;
                case 26:
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "004");
                    str = "Listener not set.";
                    break;
                case 27:
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "006");
                    break;
                default:
                    com.felicanetworks.mfc.s1.a.d(2, "%s id:%d type:%d", "700", Integer.valueOf(xVar.a()), Integer.valueOf(xVar.e()));
                    break;
            }
        } else {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            str = "Currently online.";
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        return new n(this, 1, str);
    }

    protected n s(Exception exc) {
        com.felicanetworks.mfc.s1.a.d(6, "%s %s msg:%s", "000", "Exception", exc.getMessage());
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        return new n(this, 1, exc.getMessage());
    }

    protected n t(IllegalArgumentException illegalArgumentException) {
        com.felicanetworks.mfc.s1.a.d(6, "%s %s msg:%s", "000", "IllegalArgumentException", illegalArgumentException.getMessage());
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        return new n(this, 1, illegalArgumentException.getMessage());
    }

    public synchronized void v(DeviceList deviceList) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        q();
        this.d = deviceList;
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    public synchronized void w(q qVar) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        q();
        this.b = qVar;
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    public synchronized void x(Felica felica) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        q();
        this.f77a = felica;
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    public synchronized void y(String str) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        if (str == null) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "700", "The specified URL is null.");
            throw new IllegalArgumentException("The specified URL is null.");
        }
        q();
        if (this.f77a == null) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Felica is null");
            throw new x(2, 24);
        }
        if (this.d == null) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "702", "DeviceList is null");
            throw new x(2, 25);
        }
        if (this.b == null) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "703", "FSCEventListener is null");
            throw new x(2, 26);
        }
        if (this.f77a.B()) {
            try {
                t0.a(this.f77a.z().y().e());
                try {
                    this.f77a.z().B(this.b);
                    t0.a(this.f77a.z().y().C(str, this.d, this.h, this.f77a.z().y()));
                    return;
                } catch (x e) {
                    com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%s type:%s", "723", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
                    this.f77a.z().v();
                    throw e;
                } catch (IllegalArgumentException e2) {
                    com.felicanetworks.mfc.s1.a.d(2, "%s %s msg:%s", "724", "IllegalArgumentException", e2.getMessage());
                    this.f77a.z().v();
                    throw e2;
                } catch (Exception e3) {
                    com.felicanetworks.mfc.s1.a.d(2, "%s %s msg:%s", "725", "Exception", e3.getMessage());
                    this.f77a.z().v();
                    throw new x(1, 47);
                }
            } catch (x e4) {
                com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%s type:%s", "721", "FelicaException", Integer.valueOf(e4.a()), Integer.valueOf(e4.e()));
                throw e4;
            } catch (Exception e5) {
                com.felicanetworks.mfc.s1.a.d(2, "%s %s msg:%s", "722", "Exception", e5.getMessage());
                throw new x(1, 47);
            }
        }
        try {
            this.f77a.r();
            try {
                if (this.i == null) {
                    this.f = false;
                    this.g = str;
                    p();
                } else {
                    t0.a(this.i.y(str, this.d, this.h, this.f77a.x()));
                }
                this.e = true;
                com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
                return;
            } catch (x e6) {
                com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%s type:%s", "710", "FelicaException", Integer.valueOf(e6.a()), Integer.valueOf(e6.e()));
                throw e6;
            } catch (IllegalArgumentException e7) {
                com.felicanetworks.mfc.s1.a.d(2, "%s %s msg:%s", "711", "IllegalArgumentException", e7.getMessage());
                throw e7;
            } catch (Exception e8) {
                com.felicanetworks.mfc.s1.a.d(2, "%s %s msg:%s", "799", "Exception", e8.getMessage());
                throw new x(1, 47);
            }
        } catch (x e9) {
            com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%s type:%s", "712", "FelicaException", Integer.valueOf(e9.a()), Integer.valueOf(e9.e()));
            throw e9;
        } catch (NumberFormatException unused) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "711", "NumberFormatException");
            throw new x(1, 27);
        }
    }

    public synchronized void z() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        if (!this.e && !u()) {
            com.felicanetworks.mfc.s1.a.c(3, "%s %s", "997", "Not online");
            return;
        }
        if (u()) {
            try {
                this.f77a.z().y().n();
            } catch (RemoteException e) {
                com.felicanetworks.mfc.s1.a.d(1, "%s %s msg:%s", "700", "RemoteException", e.getMessage());
                throw new x(1, 47);
            } catch (Exception e2) {
                com.felicanetworks.mfc.s1.a.d(1, "%s %s msg:%s", "799", "Exception", e2.getMessage());
            }
            return;
        }
        if (this.i == null) {
            com.felicanetworks.mfc.s1.a.c(3, "%s %s", "998", "Connecting now. canceled flag On");
            this.f = true;
            return;
        }
        try {
            this.i.n();
        } catch (RemoteException e3) {
            com.felicanetworks.mfc.s1.a.d(1, "%s %s msg:%s", "700", "RemoteException", e3.getMessage());
            throw new x(1, 47);
        } catch (Exception e4) {
            com.felicanetworks.mfc.s1.a.d(1, "%s %s msg:%s", "799", "Exception", e4.getMessage());
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        return;
    }
}

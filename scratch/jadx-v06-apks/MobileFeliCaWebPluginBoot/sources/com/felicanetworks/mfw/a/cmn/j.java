package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.Felica;

/* JADX INFO: compiled from: CommonCmd.java */
/* JADX INFO: loaded from: classes.dex */
public class j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Felica f158a;
    private boolean b;
    private int c;

    public j(h0 h0Var) {
    }

    public static e b(com.felicanetworks.mfc.x xVar, String str) {
        int iC = xVar.c();
        int iD = xVar.d();
        int iE = xVar.e();
        if (iE == 1 || iE == 2 || iE == 3 || iE == 5) {
            return new e(2, iC, iD, str);
        }
        if (iE == 7) {
            return new e(1, iC, iD, str);
        }
        if (iE == 15) {
            return new e(12, iC, iD, str);
        }
        if (iE == 16) {
            return new e(11, iC, iD, str);
        }
        switch (iE) {
            case 11:
                return new e(7, iC, iD, str);
            case 12:
                return new e(9, iC, iD, str);
            case 13:
                return new e(8, iC, iD, str);
            default:
                return new e(0, iC, iD, str);
        }
    }

    public void a() {
        try {
            this.f158a.t();
        } catch (Exception unused) {
        }
    }

    public Felica c() throws e {
        if (this.b) {
            try {
                this.f158a.G(this.c);
                this.f158a.I(4);
                this.b = false;
            } catch (Exception unused) {
                throw new e(0, null);
            }
        }
        return this.f158a;
    }

    public void d() {
        this.b = true;
    }

    public void e(Felica felica, int i) {
        this.b = false;
        this.c = i;
        this.f158a = felica;
    }
}

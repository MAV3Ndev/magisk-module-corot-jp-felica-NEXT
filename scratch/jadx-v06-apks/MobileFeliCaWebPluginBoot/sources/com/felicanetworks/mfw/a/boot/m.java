package com.felicanetworks.mfw.a.boot;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.felicanetworks.mfw.a.cmn.h0;
import com.felicanetworks.mfw.a.cmn.v;

/* JADX INFO: compiled from: ErrView.java */
/* JADX INFO: loaded from: classes.dex */
public class m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f134a;
    private boolean b;
    private v c;
    private h0 d;

    public m(h0 h0Var, int i, String str) {
        this.d = h0Var;
        if (str != null) {
            this.f134a = h0Var.h().a(i, str);
        } else {
            this.f134a = h0Var.h().c(i);
        }
    }

    public void d() {
        v vVar = this.c;
        if (vVar != null) {
            vVar.dismiss();
        }
    }

    public void e() {
        synchronized (this) {
            this.b = false;
        }
        v vVar = this.c;
        if (vVar != null) {
            if (vVar.isShowing()) {
                return;
            }
            this.c.show();
            return;
        }
        this.c = this.d.g().c();
        View viewP = this.d.g().p(R.layout.d013);
        ((TextView) viewP.findViewById(R.id.tv_dlg_msg1)).setText(this.f134a);
        ((Button) viewP.findViewById(R.id.b_dlg_btn_close)).setOnClickListener(new k(this, this.d));
        this.c.setOnDismissListener(new l(this, this.d));
        this.c.b(viewP);
        this.c.setCancelable(false);
        this.c.show();
    }
}

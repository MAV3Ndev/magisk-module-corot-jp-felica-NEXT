package com.felicanetworks.mfw.a.boot;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.felicanetworks.mfw.a.cmn.h0;
import com.felicanetworks.mfw.a.cmn.v;

/* JADX INFO: compiled from: SysErrView.java */
/* JADX INFO: loaded from: classes.dex */
public class u {
    private v c;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f140a = false;
    private boolean b = false;
    Handler d = new Handler(Looper.getMainLooper());

    public u(Activity activity, h0 h0Var) {
        this.c = null;
        View viewInflate = activity.getLayoutInflater().inflate(R.layout.d021, (ViewGroup) activity.findViewById(R.id.layout_root));
        ((Button) viewInflate.findViewById(R.id.b_dlg_btn_close)).setOnClickListener(new ViewOnClickListenerC0000r(this, h0Var));
        v vVarA = new com.felicanetworks.mfw.a.cmn.u(activity).a();
        vVarA.b(viewInflate);
        vVarA.setOnDismissListener(new s(this, h0Var));
        vVarA.setCancelable(false);
        this.c = vVarA;
    }

    public void d() {
        v vVar = this.c;
        if (vVar != null) {
            vVar.dismiss();
        }
    }

    public boolean e() {
        return this.f140a;
    }

    public void f() {
        this.f140a = true;
        this.d.post(new t(this));
    }
}

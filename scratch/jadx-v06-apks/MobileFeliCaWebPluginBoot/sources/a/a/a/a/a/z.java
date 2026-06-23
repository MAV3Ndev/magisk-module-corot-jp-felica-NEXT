package a.a.a.a.a;

import a.a.a.a.c.u1;
import android.view.View;
import android.widget.Button;
import com.felicanetworks.mfw.a.boot.R;
import com.felicanetworks.mfw.a.cmn.c1;

/* JADX INFO: compiled from: MsgBoxView.java */
/* JADX INFO: loaded from: classes.dex */
public class z extends u1 {
    public z(com.felicanetworks.mfw.a.cmn.h0 h0Var, String str) {
        super(h0Var, str);
    }

    @Override // a.a.a.a.c.u1
    protected void k() {
        View viewP;
        synchronized (this) {
            this.e = false;
        }
        com.felicanetworks.mfw.a.cmn.v vVar = this.d;
        if (vVar != null) {
            if (vVar.isShowing()) {
                return;
            }
            this.d.show();
            return;
        }
        this.d = this.f.g().c();
        if ("D011".equals(c())) {
            viewP = this.f.g().p(R.layout.d011);
        } else {
            if (!"D014".equals(c())) {
                throw new c1(z.class, "#paint");
            }
            viewP = this.f.g().p(R.layout.d014);
        }
        ((Button) viewP.findViewById(R.id.b_dlg_btn_yes)).setOnClickListener(new w(this, this.f));
        ((Button) viewP.findViewById(R.id.b_dlg_btn_no)).setOnClickListener(new x(this, this.f));
        this.d.setOnDismissListener(new y(this, this.f));
        this.d.b(viewP);
        this.d.setCancelable(false);
        this.d.show();
    }

    @Override // a.a.a.a.c.u1
    public void l(String str, Object obj, Object obj2) {
    }

    @Override // a.a.a.a.c.u1
    public void n(Object obj, Object obj2) {
    }
}

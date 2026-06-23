package a.a.a.a.a;

import a.a.a.a.c.u1;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.felicanetworks.mfw.a.boot.R;

/* JADX INFO: compiled from: ErrView.java */
/* JADX INFO: loaded from: classes.dex */
public class r extends u1 {
    private String g;
    private int h;

    public r(com.felicanetworks.mfw.a.cmn.h0 h0Var, String str) {
        super(h0Var, str);
    }

    private void y(int i, String str) {
        this.h = i;
        this.g = str != null ? this.f.h().a(i, str) : this.f.h().c(i);
    }

    @Override // a.a.a.a.c.u1
    protected void k() {
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
        View viewP = this.f.g().p(R.layout.d013);
        ((TextView) viewP.findViewById(R.id.tv_dlg_msg1)).setText(this.g);
        ((Button) viewP.findViewById(R.id.b_dlg_btn_close)).setOnClickListener(new p(this, this.f));
        this.d.setOnDismissListener(new q(this, this.f));
        this.d.b(viewP);
        this.d.setCancelable(false);
        this.d.show();
    }

    @Override // a.a.a.a.c.u1
    public void l(String str, Object obj, Object obj2) {
    }

    @Override // a.a.a.a.c.u1
    public void n(Object obj, Object obj2) {
        y(((Integer) obj).intValue(), (String) obj2);
    }
}

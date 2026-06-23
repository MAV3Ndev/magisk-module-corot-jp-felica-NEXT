package a.a.a.a.a;

import a.a.a.a.c.u1;
import android.view.View;
import android.widget.Button;
import com.felicanetworks.mfw.a.boot.R;

/* JADX INFO: compiled from: MsgNtfyView.java */
/* JADX INFO: loaded from: classes.dex */
public class m0 extends u1 {
    public m0(com.felicanetworks.mfw.a.cmn.h0 h0Var, String str) {
        super(h0Var, str);
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
        View viewP = null;
        if ("D012".equals(c())) {
            viewP = this.f.g().p(R.layout.d012);
            Button button = (Button) viewP.findViewById(R.id.b_dlg_btn_verup);
            Button button2 = (Button) viewP.findViewById(R.id.b_dlg_btn_close);
            button.setOnClickListener(new d0(this, this.f));
            button2.setOnClickListener(new e0(this, this.f));
            this.d.setOnDismissListener(new f0(this, this.f));
        } else if ("D010".equals(c())) {
            viewP = this.f.g().p(R.layout.d010);
            Button button3 = (Button) viewP.findViewById(R.id.b_dlg_btn_restart);
            Button button4 = (Button) viewP.findViewById(R.id.b_dlg_btn_break);
            button3.setOnClickListener(new g0(this, this.f));
            button4.setOnClickListener(new h0(this, this.f));
            this.d.setOnDismissListener(new i0(this, this.f));
        } else if ("D027".equals(c())) {
            viewP = this.f.g().p(R.layout.d027);
            Button button5 = (Button) viewP.findViewById(R.id.b_dlg_btn_verup);
            Button button6 = (Button) viewP.findViewById(R.id.b_dlg_btn_close);
            button5.setOnClickListener(new j0(this, this.f));
            button6.setOnClickListener(new k0(this, this.f));
            this.d.setOnDismissListener(new l0(this, this.f));
        } else if (c().equals("D028")) {
            viewP = this.f.g().p(R.layout.d028);
            Button button7 = (Button) viewP.findViewById(R.id.b_dlg_btn_verup);
            Button button8 = (Button) viewP.findViewById(R.id.b_dlg_btn_close);
            button7.setOnClickListener(new a0(this, this.f));
            button8.setOnClickListener(new b0(this, this.f));
            this.d.setOnDismissListener(new c0(this, this.f));
        }
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

package a.a.a.a.a;

import a.a.a.a.c.u1;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import com.felicanetworks.mfw.a.boot.R;
import com.felicanetworks.mfw.a.cmn.b1;
import java.util.Timer;

/* JADX INFO: compiled from: InProcessView.java */
/* JADX INFO: loaded from: classes.dex */
public class v extends u1 {
    private Timer g;
    private String h;
    private String i;
    AnimationDrawable j;

    public v(com.felicanetworks.mfw.a.cmn.h0 h0Var, String str) {
        super(h0Var, str);
        this.g = new Timer(false);
        this.j = null;
    }

    private String v(String str) {
        return str.equals("001") ? this.f.g().q(R.string.msg_D007) : str.equals("002") ? this.f.g().q(R.string.msg_D007) : str.equals("003") ? this.f.g().q(R.string.msg_D008) : str.equals("004") ? this.f.g().q(R.string.msg_D009) : str.equals("005") ? this.f.g().q(R.string.msg_D022) : "";
    }

    private boolean w(String str) {
        if (this.h == null) {
            return true;
        }
        if (str.equals("001") || str.equals("002") || str.equals("004")) {
            if (this.h.equals("001") || this.h.equals("002") || this.h.equals("004")) {
                return false;
            }
        } else if (str.equals("003")) {
            if (this.h.equals("003")) {
                return false;
            }
        } else if (str.equals("005") && this.h.equals("005")) {
            return false;
        }
        return true;
    }

    private void y(int i) {
        this.f.g().z(i);
        new Handler(Looper.getMainLooper()).post(new u(this));
    }

    private void z(String str, String str2) {
        m(true);
        if (str.equals("S001")) {
            this.f.f().c(new n(this.f, "S001"), null, null, true);
        }
        this.f.f().n(this, "001", v("001"));
    }

    @Override // a.a.a.a.c.u1, a.a.a.a.c.s1
    public void a(int i, Object obj, Object obj2) {
        if (this.f.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        if (i == 29184) {
            String str = (String) obj;
            String str2 = (String) obj2;
            this.f.f().n(this, str, (str2 == null || b1.l(str2)) ? v(str) : b1.h(str2, 25));
        } else if (i == 28931) {
            this.f.f().b(new z(this.f, "D011"), null, null);
        }
    }

    @Override // a.a.a.a.c.u1, a.a.a.a.c.s1
    public void b() {
        this.j.stop();
        this.g.cancel();
    }

    @Override // a.a.a.a.c.u1, a.a.a.a.c.s1
    public void g(Object obj, Object obj2) {
        if (this.f.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        n(obj, obj2);
    }

    @Override // a.a.a.a.c.u1, a.a.a.a.c.s1
    public void h() {
        super.h();
        AnimationDrawable animationDrawable = this.j;
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
        this.g.purge();
    }

    @Override // a.a.a.a.c.u1
    protected void k() {
        String str = this.h;
        String str2 = this.i;
        this.h = null;
        this.i = null;
        x(str, str2);
    }

    @Override // a.a.a.a.c.u1
    public void l(String str, Object obj, Object obj2) {
        if (this.f.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        String str2 = (String) obj;
        if ("D011".equals(str)) {
            if (str2.equals("01")) {
                this.f.f().m(c(), 28674, 1, null, false);
                return;
            } else {
                if (str2.equals("02")) {
                    this.f.f().m(c(), 28674, 0, null, false);
                    return;
                }
                return;
            }
        }
        if ("D010".equals(str)) {
            if (str2.equals("01")) {
                this.f.f().m(c(), 28674, 0, null, false);
            }
        } else if ("D014".equals(str)) {
            if (str2.equals("01")) {
                this.f.f().m(c(), 28674, 2, null, false);
            }
        } else if ("S001".equals(str)) {
            this.f.f().c(new g(this.f, "S002"), obj2, null, true);
        }
    }

    @Override // a.a.a.a.c.u1
    public void n(Object obj, Object obj2) {
        if (this.f.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        z((String) obj, (String) obj2);
    }

    public void x(String str, String str2) {
        synchronized (this) {
            this.e = false;
        }
        if ("001".equals(str)) {
            if (w(str)) {
                y(R.layout.d007);
                this.h = str;
                this.i = str2;
                return;
            }
            return;
        }
        if ("002".equals(str)) {
            if (w(str)) {
                y(R.layout.d007);
                this.h = str;
            }
            ((TextView) this.f.g().e(R.id.txt_processing)).setText(str2);
            this.i = str2;
            return;
        }
        if ("003".equals(str)) {
            if (w(str)) {
                y(R.layout.d008);
                this.h = str;
            }
            this.f.g().e(R.id.btn_cancel).setOnClickListener(new s(this, this.f));
            ((TextView) this.f.g().e(R.id.tv_msg1)).setText(str2);
            this.i = str2;
            return;
        }
        if ("004".equals(str)) {
            if (w(str)) {
                y(R.layout.d009);
                this.h = str;
            }
            ((TextView) this.f.g().e(R.id.txt_processing)).setText(str2);
            this.i = str2;
            return;
        }
        if ("005".equals(str)) {
            if (w(str)) {
                y(R.layout.d022);
                this.h = str;
            }
            this.f.g().e(R.id.btn_cancel).setOnClickListener(new t(this, this.f));
            ((TextView) this.f.g().e(R.id.tv_msg1)).setText(str2);
            this.i = str2;
        }
    }
}

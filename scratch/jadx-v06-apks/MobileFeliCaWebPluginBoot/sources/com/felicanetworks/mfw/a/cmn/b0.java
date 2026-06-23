package com.felicanetworks.mfw.a.cmn;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.felicanetworks.mfc.FSC;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfw.a.boot.Applet;
import com.felicanetworks.mfw.a.boot.R;
import com.felicanetworks.mfw.a.boot.WebPluginActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: DevcCoperateUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class b0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Applet f143a = null;
    private a0 b = null;
    private z c = null;

    public b0(h0 h0Var) {
    }

    private com.felicanetworks.mfw.a.boot.d B(Intent intent, int i) {
        if (!u()) {
            return this.f143a.j(intent, i);
        }
        v();
        throw null;
    }

    public static String g() {
        return Build.VERSION.RELEASE;
    }

    private a.a.a.a.c.a i() {
        a.a.a.a.c.a aVar = new a.a.a.a.c.a();
        aVar.g(-1);
        Bundle extras = this.f143a.getIntent().getExtras();
        int i = 0;
        String string = null;
        for (String str : extras.keySet()) {
            if (str.equals("param") || str.endsWith(".param")) {
                i++;
                string = extras.getString(str);
            }
        }
        if (string == null) {
            return aVar;
        }
        if (i > 1) {
            aVar.h(null);
        } else {
            aVar.h(string);
        }
        aVar.g(1);
        aVar.i(1);
        return aVar;
    }

    private a.a.a.a.c.a j() {
        String strG;
        a.a.a.a.c.a aVar = new a.a.a.a.c.a();
        aVar.g(-1);
        aVar.h("");
        Uri data = this.f143a.getIntent().getData();
        if (data != null && data.getScheme() != null) {
            String scheme = data.getScheme();
            if ((scheme.equals("mfwpluginboot") || scheme.equals("mfwplugin")) && (strG = b1.g(scheme, this.f143a.getIntent().getDataString())) != null) {
                aVar.g(1);
                aVar.h(strG);
                aVar.i(2);
                aVar.j(this.f143a.getIntent().getDataString());
            }
        }
        return aVar;
    }

    private a.a.a.a.c.a k() {
        a.a.a.a.c.a aVar = new a.a.a.a.c.a();
        String dataString = this.f143a.getIntent().getDataString();
        if (dataString == null) {
            aVar.g(-1);
            aVar.h("");
        } else {
            aVar.g(2);
            aVar.h(dataString);
        }
        return aVar;
    }

    public static void v() {
        throw new com.felicanetworks.mfw.a.boot.a();
    }

    public void A() {
    }

    public a.a.a.a.c.a C() {
        if (m() != 1) {
            return k();
        }
        if (this.f143a.getIntent().getExtras() == null) {
            return j();
        }
        a.a.a.a.c.a aVarI = i();
        return aVarI.c() == -1 ? j() : aVarI;
    }

    public void D(String str) throws IOException {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(b1.M(str)));
        intent.setPackage(k.e().b());
        intent.setFlags(268435456);
        if (u()) {
            v();
            throw null;
        }
        try {
            this.f143a.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            throw new IOException();
        }
    }

    public void E(String str) throws IOException {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(b1.M(str)));
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setFlags(268435456);
        if (u()) {
            v();
            throw null;
        }
        try {
            this.f143a.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            throw new IOException();
        }
    }

    public int F() throws a.a.a.a.b.b, IOException {
        try {
            if (this.f143a.getPackageManager().getApplicationEnabledSetting(p0.a().c("format.felica.package.name")) == 3) {
                throw new a.a.a.a.b.b(2);
            }
            if (!d1.f(this.f143a.getPackageManager())) {
                throw new a.a.a.a.b.b(5);
            }
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(p0.a().c("format.felica.package.name"), p0.a().c("format.felica.class.name")));
            try {
                String strD = C().d();
                if (m() == 1) {
                    strD = new String(b1.I(strD));
                }
                intent.putExtra("com.felicanetworks.mfs.ai", p0.a().c("application.id"));
                intent.putExtra("com.felicanetworks.mfs.log", b1.O(strD));
            } catch (Exception unused) {
            }
            com.felicanetworks.mfw.a.boot.d dVarB = B(intent, 2000);
            if (dVarB.e()) {
                return dVarB.d();
            }
            throw new IOException("failed to start activity");
        } catch (ActivityNotFoundException unused2) {
            throw new IOException("failed starting FeliCaMenuApp");
        } catch (IllegalArgumentException unused3) {
            throw new a.a.a.a.b.b(4);
        }
    }

    public void G(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(String.format("market://details?id=%s", str)));
        intent.setPackage(k.e().f());
        if (u()) {
            v();
            throw null;
        }
        try {
            this.f143a.startActivity(intent);
        } catch (Exception unused) {
            d();
        }
    }

    public void a() {
    }

    public int b(String str) {
        return this.f143a.getPackageManager().getApplicationEnabledSetting(str);
    }

    public v c() {
        return new u(this.f143a).a();
    }

    public void d() {
        if (u()) {
            return;
        }
        this.f143a.finish();
    }

    public View e(int i) {
        return this.f143a.findViewById(i);
    }

    public void f() {
        this.b.b();
        this.c.a();
        this.b = null;
        this.c = null;
    }

    public String h() {
        return this.f143a.getFilesDir().getPath() + "/";
    }

    public List l(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("");
        return arrayList;
    }

    public int m() {
        return this.f143a instanceof WebPluginActivity ? 2 : 1;
    }

    public FSC n() {
        return this.c.b(this.f143a.getApplicationContext());
    }

    public Felica o() {
        return this.b.c(this.f143a.getApplicationContext());
    }

    public View p(int i) {
        return this.f143a.getLayoutInflater().inflate(i, (ViewGroup) e(R.id.layout_root));
    }

    public String q(int i) {
        return this.f143a.getString(i);
    }

    public void r(Exception exc) {
        if (exc instanceof com.felicanetworks.mfw.a.boot.a) {
            d();
        } else {
            if (u()) {
                return;
            }
            this.f143a.e(exc);
        }
    }

    public void s(Applet applet) {
        this.f143a = applet;
        y yVar = null;
        this.b = new a0(this);
        this.c = new z(this);
    }

    public void t(WebPluginActivity webPluginActivity) {
        this.f143a = webPluginActivity;
        y yVar = null;
        this.b = new a0(this);
        this.c = new z(this);
    }

    public boolean u() {
        return this.f143a.isFinishing() || this.f143a.f();
    }

    public void w(int i) {
        ((WebPluginActivity) this.f143a).k(i);
    }

    public void x(int i, String str) {
        ((WebPluginActivity) this.f143a).l(i, str);
    }

    public void y(int i, List list) {
        ((WebPluginActivity) this.f143a).m(i, list);
    }

    public void z(int i) {
        this.f143a.setContentView(i);
    }
}

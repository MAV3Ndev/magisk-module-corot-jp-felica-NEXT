package com.felicanetworks.mfw.a.boot;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BootActivity extends Activity {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private List f125a;

    private Intent a(String str, String str2, Intent intent) {
        Intent intent2 = new Intent();
        intent2.setClassName(str, str + str2);
        if (intent != null && intent.getExtras() != null) {
            intent2.putExtras(intent.getExtras());
        }
        if (intent != null && intent.getData() != null) {
            intent2.setData(intent.getData());
        }
        return intent2;
    }

    private Dialog b(View view, Context context) {
        com.felicanetworks.mfw.a.view.k kVarA = new com.felicanetworks.mfw.a.view.j(context).a();
        kVarA.b(view);
        kVarA.setCancelable(false);
        kVarA.setOnDismissListener(new g(this));
        return kVarA;
    }

    private Dialog c(int i) {
        View viewInflate = getLayoutInflater().inflate(R.layout.d101, (ViewGroup) findViewById(R.id.layout_root));
        ((TextView) viewInflate.findViewById(R.id.tv_dlg_msg1)).setText(i);
        ((Button) viewInflate.findViewById(R.id.b_dlg_btn_close)).setOnClickListener(new f(this));
        return b(viewInflate, this);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);
        this.f125a = new ArrayList();
        try {
            a.a.a.a.d.a aVarA = a.a.a.a.d.a.a();
            startActivity(a(aVarA.b("execute.package.name"), aVarA.b("execute.class.name"), getIntent()));
            finish();
        } catch (Exception unused) {
            Dialog dialogC = c(R.string.msg_D021);
            this.f125a.add(dialogC);
            dialogC.show();
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.f125a = null;
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        Iterator it = this.f125a.iterator();
        while (it.hasNext()) {
            ((Dialog) it.next()).dismiss();
        }
        this.f125a.clear();
        if (isFinishing()) {
            return;
        }
        finish();
    }
}

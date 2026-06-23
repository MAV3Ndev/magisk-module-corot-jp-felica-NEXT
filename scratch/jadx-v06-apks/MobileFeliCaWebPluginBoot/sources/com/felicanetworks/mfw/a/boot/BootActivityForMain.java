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
import com.felicanetworks.mfw.a.cmn.p0;
import com.felicanetworks.mfw.a.cmn.v;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BootActivityForMain extends Activity {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private List f126a;

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
        v vVarA = new com.felicanetworks.mfw.a.cmn.u(context).a();
        vVarA.b(view);
        vVarA.setCancelable(false);
        vVarA.setOnDismissListener(new j(this));
        return vVarA;
    }

    private Dialog c(int i) {
        View viewInflate = getLayoutInflater().inflate(R.layout.d026, (ViewGroup) findViewById(R.id.layout_root));
        ((TextView) viewInflate.findViewById(R.id.tv_dlg_msg1)).setText(i);
        ((Button) viewInflate.findViewById(R.id.b_dlg_btn_close)).setOnClickListener(new i(this));
        return b(viewInflate, this);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);
        this.f126a = new ArrayList();
        try {
            p0 p0VarA = p0.a();
            startActivity(a(p0VarA.c("execute.package.name"), p0VarA.c("execute.class.name"), getIntent()));
            finish();
        } catch (Exception unused) {
            Dialog dialogC = c(R.string.msg_D021);
            this.f126a.add(dialogC);
            dialogC.show();
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.f126a = null;
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        Iterator it = this.f126a.iterator();
        while (it.hasNext()) {
            ((Dialog) it.next()).dismiss();
        }
        this.f126a.clear();
        if (isFinishing()) {
            return;
        }
        finish();
    }
}

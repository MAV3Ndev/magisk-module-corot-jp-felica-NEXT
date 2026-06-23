package com.felicanetworks.mfw.a.cmn;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.felicanetworks.mfw.a.boot.R;

/* JADX INFO: compiled from: CustomAlertDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class v extends Dialog implements DialogInterface {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private t f179a;

    protected v(Context context) {
        this(context, R.style.Theme_CustomAlert);
    }

    public void b(View view) {
        this.f179a.s(view);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f179a.n();
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.f179a.o(i, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (this.f179a.p(i, keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.f179a.x(charSequence);
    }

    protected v(Context context, int i) {
        super(context, i);
        this.f179a = new t(context, this, getWindow());
    }
}

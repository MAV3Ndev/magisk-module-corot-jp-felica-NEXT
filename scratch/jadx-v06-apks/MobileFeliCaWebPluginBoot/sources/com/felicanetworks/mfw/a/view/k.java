package com.felicanetworks.mfw.a.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.felicanetworks.mfw.a.boot.R;

/* JADX INFO: compiled from: CustomAlertDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class k extends Dialog implements DialogInterface {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private i f196a;

    protected k(Context context) {
        this(context, R.style.Theme_CustomAlert);
    }

    public void b(View view) {
        this.f196a.s(view);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f196a.n();
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.f196a.o(i, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (this.f196a.p(i, keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.f196a.x(charSequence);
    }

    protected k(Context context, int i) {
        super(context, i);
        this.f196a = new i(context, this, getWindow());
    }
}

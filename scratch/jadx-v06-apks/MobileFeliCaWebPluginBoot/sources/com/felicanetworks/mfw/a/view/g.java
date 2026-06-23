package com.felicanetworks.mfw.a.view;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import com.felicanetworks.mfw.a.boot.R;

/* JADX INFO: compiled from: CustomAlertController.java */
/* JADX INFO: loaded from: classes.dex */
public class g {
    public boolean A;
    public boolean B;
    public DialogInterface.OnMultiChoiceClickListener D;
    public Cursor E;
    public String F;
    public String G;
    public boolean H;
    public AdapterView.OnItemSelectedListener I;
    public f J;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f192a;
    public final LayoutInflater b;
    public Drawable d;
    public CharSequence e;
    public View f;
    public CharSequence g;
    public CharSequence h;
    public DialogInterface.OnClickListener i;
    public CharSequence j;
    public DialogInterface.OnClickListener k;
    public CharSequence l;
    public DialogInterface.OnClickListener m;
    public DialogInterface.OnCancelListener o;
    public DialogInterface.OnKeyListener p;
    public CharSequence[] q;
    public ListAdapter r;
    public DialogInterface.OnClickListener s;
    public View t;
    public int u;
    public int v;
    public int w;
    public int x;
    public boolean[] z;
    public int c = -1;
    public boolean y = false;
    public int C = -1;
    public boolean K = true;
    public boolean n = true;

    public g(Context context) {
        this.f192a = context;
        this.b = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    private void b(i iVar) {
        ListAdapter simpleCursorAdapter;
        CustomAlertController$RecycleListView customAlertController$RecycleListView = (CustomAlertController$RecycleListView) this.b.inflate(R.layout.select_dialog, (ViewGroup) null);
        if (this.A) {
            simpleCursorAdapter = this.E == null ? new b(this, this.f192a, R.layout.select_dialog_multichoice, R.id.text1, this.q, customAlertController$RecycleListView) : new c(this, this.f192a, this.E, false, customAlertController$RecycleListView);
        } else {
            int i = this.B ? R.layout.select_dialog_singlechoice : R.layout.select_dialog_item;
            if (this.E == null) {
                ListAdapter arrayAdapter = this.r;
                if (arrayAdapter == null) {
                    arrayAdapter = new ArrayAdapter(this.f192a, i, R.id.text1, this.q);
                }
                simpleCursorAdapter = arrayAdapter;
            } else {
                simpleCursorAdapter = new SimpleCursorAdapter(this.f192a, i, this.E, new String[]{this.F}, new int[]{R.id.text1}, 2);
            }
        }
        f fVar = this.J;
        if (fVar != null) {
            fVar.a(customAlertController$RecycleListView);
        }
        iVar.D = simpleCursorAdapter;
        iVar.E = this.C;
        if (this.s != null) {
            customAlertController$RecycleListView.setOnItemClickListener(new d(this, iVar));
        } else if (this.D != null) {
            customAlertController$RecycleListView.setOnItemClickListener(new e(this, customAlertController$RecycleListView, iVar));
        }
        AdapterView.OnItemSelectedListener onItemSelectedListener = this.I;
        if (onItemSelectedListener != null) {
            customAlertController$RecycleListView.setOnItemSelectedListener(onItemSelectedListener);
        }
        if (this.B) {
            customAlertController$RecycleListView.setChoiceMode(1);
        } else if (this.A) {
            customAlertController$RecycleListView.setChoiceMode(2);
        }
        customAlertController$RecycleListView.f186a = this.K;
        iVar.f = customAlertController$RecycleListView;
    }

    public void a(i iVar) {
        View view = this.f;
        if (view != null) {
            iVar.s(view);
        } else {
            CharSequence charSequence = this.e;
            if (charSequence != null) {
                iVar.x(charSequence);
            }
            Drawable drawable = this.d;
            if (drawable != null) {
                iVar.u(drawable);
            }
            int i = this.c;
            if (i >= 0) {
                iVar.t(i);
            }
        }
        CharSequence charSequence2 = this.g;
        if (charSequence2 != null) {
            iVar.w(charSequence2);
        }
        CharSequence charSequence3 = this.h;
        if (charSequence3 != null) {
            iVar.r(-1, charSequence3, this.i, null);
        }
        CharSequence charSequence4 = this.j;
        if (charSequence4 != null) {
            iVar.r(-2, charSequence4, this.k, null);
        }
        CharSequence charSequence5 = this.l;
        if (charSequence5 != null) {
            iVar.r(-3, charSequence5, this.m, null);
        }
        if (this.H) {
            iVar.v(true);
        }
        if (this.q != null || this.E != null || this.r != null) {
            b(iVar);
        }
        View view2 = this.t;
        if (view2 != null) {
            if (this.y) {
                iVar.z(view2, this.u, this.v, this.w, this.x);
            } else {
                iVar.y(view2);
            }
        }
    }
}

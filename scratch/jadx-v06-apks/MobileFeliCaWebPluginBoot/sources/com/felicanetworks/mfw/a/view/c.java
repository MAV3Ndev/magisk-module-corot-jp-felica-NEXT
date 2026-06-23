package com.felicanetworks.mfw.a.view;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import com.felicanetworks.mfw.a.boot.R;

/* JADX INFO: compiled from: CustomAlertController.java */
/* JADX INFO: loaded from: classes.dex */
class c extends CursorAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final int f189a;
    private final int b;
    final /* synthetic */ CustomAlertController$RecycleListView c;
    final /* synthetic */ g d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    c(g gVar, Context context, Cursor cursor, boolean z, CustomAlertController$RecycleListView customAlertController$RecycleListView) {
        super(context, cursor, z);
        this.d = gVar;
        this.c = customAlertController$RecycleListView;
        Cursor cursor2 = getCursor();
        this.f189a = cursor2.getColumnIndexOrThrow(this.d.F);
        this.b = cursor2.getColumnIndexOrThrow(this.d.G);
    }

    @Override // android.widget.CursorAdapter
    public void bindView(View view, Context context, Cursor cursor) {
        ((CheckedTextView) view.findViewById(R.id.text1)).setText(cursor.getString(this.f189a));
        this.c.setItemChecked(cursor.getPosition(), cursor.getInt(this.b) == 1);
    }

    @Override // android.widget.CursorAdapter
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.d.b.inflate(R.layout.select_dialog_multichoice, viewGroup, false);
    }
}

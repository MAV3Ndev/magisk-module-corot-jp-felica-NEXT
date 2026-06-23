package com.amazonaws.mobileconnectors.pinpoint.internal.core.system;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public class AndroidPreferences {
    private final SharedPreferences preferences;

    public AndroidPreferences() {
        this.preferences = null;
    }

    public AndroidPreferences(Context context, String str) {
        this.preferences = context.getSharedPreferences(str, 0);
    }

    public boolean getBoolean(String str, boolean z) {
        return this.preferences.getBoolean(str, z);
    }

    public int getInt(String str, int i) {
        return this.preferences.getInt(str, i);
    }

    public float getFloat(String str, float f) {
        return this.preferences.getFloat(str, f);
    }

    public long getLong(String str, long j) {
        return this.preferences.getLong(str, j);
    }

    public String getString(String str, String str2) {
        return this.preferences.getString(str, str2);
    }

    public void putBoolean(String str, boolean z) {
        SharedPreferences.Editor editorEdit = this.preferences.edit();
        editorEdit.putBoolean(str, z);
        editorEdit.commit();
    }

    public void putInt(String str, int i) {
        SharedPreferences.Editor editorEdit = this.preferences.edit();
        editorEdit.putInt(str, i);
        editorEdit.commit();
    }

    public void putFloat(String str, float f) {
        SharedPreferences.Editor editorEdit = this.preferences.edit();
        editorEdit.putFloat(str, f);
        editorEdit.commit();
    }

    public void putLong(String str, long j) {
        SharedPreferences.Editor editorEdit = this.preferences.edit();
        editorEdit.putLong(str, j);
        editorEdit.commit();
    }

    public void putString(String str, String str2) {
        SharedPreferences.Editor editorEdit = this.preferences.edit();
        editorEdit.putString(str, str2);
        editorEdit.commit();
    }
}

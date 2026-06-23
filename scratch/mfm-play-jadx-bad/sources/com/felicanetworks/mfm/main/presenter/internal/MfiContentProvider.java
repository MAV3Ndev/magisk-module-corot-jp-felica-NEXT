package com.felicanetworks.mfm.main.presenter.internal;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

/* JADX INFO: loaded from: classes3.dex */
public class MfiContentProvider extends ContentProvider {
    private static final int ACCOUNT_CODE = 1;
    private static final String AUTHORITY = "com.felicanetworks.mfm.main.presenter.internal.mficontentprovider";
    private static final String MFI_ACCOUNT = "account_data";
    private static final String PREFERENCE_NAME = "mfi_login_data";
    private static final UriMatcher sUriMatcher;

    interface Accounts {
        public static final Uri CONTENT_URI = Uri.parse("content://com.felicanetworks.mfm.main.presenter.internal.mficontentprovider/account");
        public static final String PATH = "account";

        public interface AccountsColumns {
            public static final String NAME = "name";
        }
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        sUriMatcher = uriMatcher;
        uriMatcher.addURI(AUTHORITY, Accounts.PATH, 1);
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (sUriMatcher.match(uri) == 1) {
            return getAccountCursor();
        }
        throw new IllegalArgumentException("Invalid URI：" + uri);
    }

    private Cursor getAccountCursor() {
        Context context = getContext();
        SharedPreferences sharedPreferences = context != null ? context.getSharedPreferences(PREFERENCE_NAME, 0) : null;
        String string = sharedPreferences != null ? sharedPreferences.getString(MFI_ACCOUNT, null) : null;
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"name"});
        if (string != null) {
            matrixCursor.addRow(new Object[]{string});
        }
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("ContentProvider.getType()");
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("ContentProvider.insert()");
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("ContentProvider.delete()");
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("ContentProvider.update()");
    }
}

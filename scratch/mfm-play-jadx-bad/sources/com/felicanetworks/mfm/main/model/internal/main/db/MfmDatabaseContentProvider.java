package com.felicanetworks.mfm.main.model.internal.main.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.policy.service.ServiceGroupType;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class MfmDatabaseContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.felicanetworks.mfm.main.model.internal.main.db";
    private static final int CACHE_ASSET_LIST_CODE = 10;
    private static final int CARD_FACE_INFO_CODE = 1;
    private static final String TABLE_CACHE_ASSET_LIST = "CacheAssetList";
    private static final String TABLE_CARD_FACE_INFO = "CardFaceInfo";
    private static final UriMatcher sUriMatcher;

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        sUriMatcher = uriMatcher;
        uriMatcher.addURI(AUTHORITY, TABLE_CARD_FACE_INFO, 1);
        uriMatcher.addURI(AUTHORITY, TABLE_CACHE_ASSET_LIST, 10);
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("ContentProvider.getType()");
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        try {
            MfmDatabaseHelper mfmDatabaseHelper = MfmDatabaseHelper.getInstance(new ModelContext((Context) Objects.requireNonNull(getContext())).getLegacyContext());
            if (mfmDatabaseHelper == null) {
                return null;
            }
            SQLiteDatabase readableDatabase = mfmDatabaseHelper.getReadableDatabase();
            int iMatch = sUriMatcher.match(uri);
            if (iMatch == 1) {
                return readableDatabase.query(TABLE_CARD_FACE_INFO, projection, appendSelection(uri, selection), appendSelectionArgs(uri, selectionArgs), null, null, sortOrder);
            }
            if (iMatch == 10) {
                return readableDatabase.query(TABLE_CACHE_ASSET_LIST, projection, appendSelection(uri, selection), appendSelectionArgs(uri, selectionArgs), null, null, sortOrder);
            }
            throw new IllegalArgumentException("Invalid URI：" + uri);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    private String appendSelection(Uri uri, String selection) {
        String str;
        if (uri.getPathSegments().size() == 1) {
            return selection;
        }
        StringBuilder sb = new StringBuilder("_id=?");
        if (selection == null) {
            str = "";
        } else {
            str = " AND name=" + selection;
        }
        sb.append(str);
        return sb.toString();
    }

    private String[] appendSelectionArgs(Uri uri, String[] selectionArgs) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() == 1) {
            return selectionArgs;
        }
        if (selectionArgs == null || selectionArgs.length == 0) {
            return new String[]{pathSegments.get(1)};
        }
        String[] strArr = new String[selectionArgs.length + 1];
        strArr[0] = pathSegments.get(1);
        System.arraycopy(selectionArgs, 0, strArr, 1, selectionArgs.length);
        return strArr;
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
        try {
            MfmDatabaseHelper mfmDatabaseHelper = MfmDatabaseHelper.getInstance(new ModelContext((Context) Objects.requireNonNull(getContext())).getLegacyContext());
            if (mfmDatabaseHelper == null) {
                return 0;
            }
            SQLiteDatabase readableDatabase = mfmDatabaseHelper.getReadableDatabase();
            if (sUriMatcher.match(uri) != 10) {
                throw new IllegalArgumentException("Invalid URI：" + uri);
            }
            if (values == null || selectionArgs == null) {
                return 0;
            }
            if (ServiceGroupType.resolve(selectionArgs[0]) != ServiceGroupType.TRANSPORTATION || selectionArgs.length >= 2) {
                return readableDatabase.update(TABLE_CACHE_ASSET_LIST, values, appendSelection(uri, selection), appendSelectionArgs(uri, selectionArgs));
            }
            return 0;
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }
}

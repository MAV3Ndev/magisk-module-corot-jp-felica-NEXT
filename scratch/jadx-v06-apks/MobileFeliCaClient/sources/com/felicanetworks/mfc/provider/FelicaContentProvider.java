package com.felicanetworks.mfc.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.felicanetworks.mfc.felica.access_control.AccessConfig;
import com.felicanetworks.mfw.i.fbl.Property;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class FelicaContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.felicanetworks.mfc.provider.FelicaContentProvider";
    private static final int GET_CONTENTS = 1;
    private static final int GET_TIS_CONTENTS = 2;
    private static final String TIS_CACHE_FILE = "tisCache";
    private static UriMatcher sUriMatcher = new UriMatcher(-1);

    private interface Contents {
        public static final String PATH = "Mfc";

        public interface Columns {
            public static final String IS_RW_SUPPORTED = "IS_RW_SUPPORTED";
            public static final String READER_NAME = "READER_NAME";
        }
    }

    interface tisContents {
        public static final String PATH = "tisData";

        public interface Columns {
            public static final String EXTRA_UUID = "com.felicanetworks.mfc.EXTRA_UUID";
        }
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    static {
        sUriMatcher.addURI(AUTHORITY, Contents.PATH, 1);
        sUriMatcher.addURI(AUTHORITY, tisContents.PATH, 2);
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        throw new UnsupportedOperationException(Property.URL_VERUP_SITE);
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        int iMatch = sUriMatcher.match(uri);
        if (iMatch == 1) {
            return getCursor();
        }
        if (iMatch == 2) {
            return getTisCursor(str);
        }
        throw new IllegalArgumentException("Invalid URI：" + uri);
    }

    private Cursor getCursor() {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{Contents.Columns.IS_RW_SUPPORTED, Contents.Columns.READER_NAME});
        matrixCursor.addRow(new Object[]{Boolean.valueOf(AccessConfig.isFelicaReaderWriterSupported()), AccessConfig.getEseReaderName()});
        return matrixCursor;
    }

    private Cursor getTisCursor(String str) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{tisContents.Columns.EXTRA_UUID});
        matrixCursor.addRow(new Object[]{Boolean.valueOf(isTisExtraContained(str))});
        return matrixCursor;
    }

    private boolean isTisExtraContained(String str) {
        LinkedList linkedList;
        try {
            linkedList = (LinkedList) readObjectFromCache(TIS_CACHE_FILE);
        } catch (Exception unused) {
            linkedList = null;
        }
        if (linkedList != null) {
            return linkedList.contains(str);
        }
        return false;
    }

    private Object readObjectFromCache(String str) throws Throwable {
        ObjectInputStream objectInputStream;
        ObjectInputStream objectInputStream2 = null;
        object = null;
        Object object = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(new File(getContext().getCacheDir(), str)));
            try {
                object = objectInputStream.readObject();
            } catch (Exception unused) {
                if (objectInputStream != null) {
                }
                return object;
            } catch (Throwable th) {
                th = th;
                objectInputStream2 = objectInputStream;
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Exception unused3) {
            objectInputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            objectInputStream.close();
        } catch (IOException unused4) {
        }
        return object;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException(Property.URL_VERUP_SITE);
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException(Property.URL_VERUP_SITE);
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException(Property.URL_VERUP_SITE);
    }
}

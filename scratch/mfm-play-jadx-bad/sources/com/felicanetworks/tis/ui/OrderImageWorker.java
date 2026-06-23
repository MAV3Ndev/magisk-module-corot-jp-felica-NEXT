package com.felicanetworks.tis.ui;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class OrderImageWorker extends Thread {
    private static final String AUTHORITY = "content://com.felicanetworks.mfm.main.model.internal.main.db";
    private static final String TABLE_CARD_FACE_INFO = "CardFaceInfo";
    private Context _context;
    private Listener _listener;
    private Request _request;

    public interface Listener {
        void onCompleted();

        void onGetImage(Bitmap bitmap);
    }

    private static final class TableColumns_CardFaceImage {
        private static final String CARD_FACE_IDENTIFIER = "CardFaceIdentifier";
        private static final String CARD_FACE_IMAGE = "CardFaceImage";

        private TableColumns_CardFaceImage() {
        }
    }

    public OrderImageWorker(Context context, Request request, Listener listener) {
        this._context = context;
        this._listener = listener;
        this._request = request;
    }

    protected OrderImageWorker() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public synchronized void run() {
        Listener listener;
        byte[] cardFaceImage;
        try {
            try {
                if (this._request._cardFaceIdentifiers != null && !this._request._cardFaceIdentifiers.isEmpty() && (cardFaceImage = getCardFaceImage(this._request._cardFaceIdentifiers)) != null && cardFaceImage.length > 0) {
                    this._listener.onGetImage(BitmapFactory.decodeByteArray(cardFaceImage, 0, cardFaceImage.length));
                } else {
                    this._listener.onGetImage(null);
                }
                listener = this._listener;
            } catch (Exception unused) {
                this._listener.onGetImage(null);
                listener = this._listener;
            }
            listener.onCompleted();
        } catch (Throwable th) {
            this._listener.onCompleted();
            throw th;
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[]}, finally: {[THROW, INVOKE, MOVE_EXCEPTION, THROW, INVOKE, MOVE_EXCEPTION, THROW, IF] complete} */
    /* JADX DEBUG: Finally have unexpected throw blocks count: 3, expect 1 */
    private byte[] getCardFaceImage(List<String> list) {
        Cursor cursorQuery;
        byte[] bArr = null;
        try {
            Uri uri = Uri.parse("content://com.felicanetworks.mfm.main.model.internal.main.db/CardFaceInfo");
            Iterator<String> it = list.iterator();
            byte[] blob = null;
            while (it.hasNext()) {
                try {
                    try {
                        cursorQuery = this._context.getContentResolver().query(uri, null, "CardFaceIdentifier = ?", new String[]{it.next()}, null);
                    } catch (Exception unused) {
                    }
                    if (cursorQuery != null) {
                        try {
                            if (cursorQuery.moveToFirst()) {
                                int columnIndex = cursorQuery.getColumnIndex("CardFaceImage");
                                if (columnIndex >= 0) {
                                    blob = cursorQuery.getBlob(columnIndex);
                                }
                                if (cursorQuery == null) {
                                    return blob;
                                }
                                cursorQuery.close();
                                return blob;
                            }
                        } finally {
                        }
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                } catch (Exception unused2) {
                    bArr = null;
                    return bArr;
                }
            }
            return null;
        } catch (Exception unused3) {
        }
    }

    public static class Request {
        public List<String> _cardFaceIdentifiers;

        public Request(List<String> list) {
            this._cardFaceIdentifiers = list;
        }

        public String toString() {
            return "Request cid = " + this._cardFaceIdentifiers.toString();
        }
    }
}

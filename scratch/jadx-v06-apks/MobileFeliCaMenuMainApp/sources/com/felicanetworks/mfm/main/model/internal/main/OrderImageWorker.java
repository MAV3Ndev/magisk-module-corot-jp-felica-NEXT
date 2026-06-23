package com.felicanetworks.mfm.main.model.internal.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.BasicAuthentication;
import com.felicanetworks.mfm.main.model.internal.main.net.ImageProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;

/* JADX INFO: loaded from: classes.dex */
public class OrderImageWorker extends Thread {
    private ModelContext _context;
    private DatabaseExpert _db;
    private Listener _listener;
    private Request _request;

    public interface Listener {
        void onCompleted(String str);

        void onGetImage(String str, String str2, Bitmap bitmap);
    }

    public OrderImageWorker(ModelContext modelContext, Request request, Listener listener) {
        this._context = modelContext;
        this._listener = listener;
        this._request = request;
        this._db = modelContext.getOpenedDatabaseExpert();
    }

    protected OrderImageWorker() {
    }

    private void checkInterrupted() throws InterruptedException {
        if (isInterrupted()) {
            throw new InterruptedException();
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public synchronized void run() {
        Listener listener;
        String str;
        byte[] cardImageFromServer;
        try {
            try {
                checkInterrupted();
                try {
                    DatabaseExpert.CardFaceImageInfo cardFaceImage = this._db.getCardFaceImage(this._request._cid);
                    if (cardFaceImage == null) {
                        cardImageFromServer = getCardImageFromServer(this._request._url);
                        this._db.addCardFaceImage(this._request._cid, this._request._url, cardImageFromServer);
                    } else if (!cardFaceImage._url.equals(this._request._url)) {
                        cardImageFromServer = getCardImageFromServer(this._request._url);
                        this._db.updateCardFaceImage(this._request._cid, this._request._url, cardImageFromServer);
                    } else {
                        cardImageFromServer = cardFaceImage._image;
                    }
                    if (cardImageFromServer != null && cardImageFromServer.length > 0) {
                        this._listener.onGetImage(this._request._cid, this._request._url, BitmapFactory.decodeByteArray(cardImageFromServer, 0, cardImageFromServer.length));
                    }
                } catch (DatabaseException unused) {
                    this._listener.onGetImage(this._request._cid, this._request._url, null);
                } catch (NetworkExpertException unused2) {
                    this._listener.onGetImage(this._request._cid, this._request._url, null);
                }
                listener = this._listener;
                str = this._request._cid;
            } catch (InterruptedException unused3) {
                this._listener.onGetImage(this._request._cid, this._request._url, null);
                listener = this._listener;
                str = this._request._cid;
            }
            listener.onCompleted(str);
        } catch (Throwable th) {
            this._listener.onCompleted(this._request._cid);
            throw th;
        }
    }

    private byte[] getCardImageFromServer(String str) throws Throwable {
        ImageProtocol.Parameter parameter = new ImageProtocol.Parameter(str);
        ImageProtocol imageProtocol = this._context.getNetworkExpert().getImageProtocol();
        NetworkExpert.Request requestCreate = imageProtocol.create(parameter);
        if (BasicAuthentication.isNeedBasicAuthentication()) {
            requestCreate = BasicAuthentication.addBasicAuthorization(requestCreate);
        }
        return imageProtocol.parse(this._context.getNetworkExpert().connect(requestCreate)).bytes;
    }

    public static class Request {
        public String _cid;
        public String _url;

        public Request(String str, String str2) {
            this._cid = str;
            this._url = str2;
        }

        public String toString() {
            return "Request cid = " + this._cid + ", url = " + this._url;
        }
    }
}

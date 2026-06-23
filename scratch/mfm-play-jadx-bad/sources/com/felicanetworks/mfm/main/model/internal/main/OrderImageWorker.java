package com.felicanetworks.mfm.main.model.internal.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseException;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.BasicAuthentication;
import com.felicanetworks.mfm.main.model.internal.main.net.ImageProtocol;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class OrderImageWorker extends Thread {
    private ModelContext _context;
    private DatabaseExpert _db;
    private Listener _listener;
    private List<Request> _requests;

    public interface Listener {
        void onCompleted(String id);

        void onGetImage(String id, String url, Bitmap image);
    }

    public OrderImageWorker(ModelContext context, List<Request> requests, Listener listener) {
        this._context = context;
        this._listener = listener;
        this._requests = requests;
        this._db = context.getOpenedDatabaseExpert();
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
        Bitmap bitmapDecodeByteArray;
        String str = "";
        List<Request> list = this._requests;
        if (list != null && !list.isEmpty()) {
            String str2 = this._requests.get(0)._cid;
            try {
                try {
                    checkInterrupted();
                    Iterator<Request> it = this._requests.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            bitmapDecodeByteArray = null;
                            break;
                        }
                        Request next = it.next();
                        byte[] cardFaceImageInfo = getCardFaceImageInfo(next);
                        if (cardFaceImageInfo != null && cardFaceImageInfo.length > 0) {
                            str = next._url;
                            bitmapDecodeByteArray = BitmapFactory.decodeByteArray(cardFaceImageInfo, 0, cardFaceImageInfo.length);
                            break;
                        }
                    }
                    this._listener.onGetImage(str2, str, bitmapDecodeByteArray);
                    listener = this._listener;
                } catch (InterruptedException unused) {
                    this._listener.onGetImage(str2, str, null);
                    listener = this._listener;
                }
                listener.onCompleted(str2);
                return;
            } catch (Throwable th) {
                this._listener.onCompleted(str2);
                throw th;
            }
        }
        this._listener.onGetImage("", "", null);
    }

    private byte[] getCardFaceImageInfo(Request request) {
        try {
            String str = request._cardArtPrefix + request._cid;
            DatabaseExpert.CardFaceImageInfo cardFaceImage = this._db.getCardFaceImage(str);
            if (cardFaceImage == null) {
                if (request._url != null && !request._url.isEmpty()) {
                    byte[] cardImageFromServer = getCardImageFromServer(request._url);
                    this._db.addCardFaceImage(str, request._url, cardImageFromServer);
                    return cardImageFromServer;
                }
            } else if (!cardFaceImage._url.equals(request._url)) {
                if (request._url != null && !request._url.isEmpty()) {
                    byte[] cardImageFromServer2 = getCardImageFromServer(request._url);
                    this._db.updateCardFaceImage(str, request._url, cardImageFromServer2);
                    return cardImageFromServer2;
                }
                if (!request._cardArtPrefix.isEmpty()) {
                    this._db.deleteCardFaceImage(str);
                }
            } else {
                return cardFaceImage._image;
            }
            return null;
        } catch (DatabaseException | NetworkExpertException | NullPointerException unused) {
            return null;
        }
    }

    private byte[] getCardImageFromServer(String url) throws Throwable {
        ImageProtocol.Parameter parameter = new ImageProtocol.Parameter(url);
        ImageProtocol imageProtocol = this._context.getNetworkExpert().getImageProtocol();
        NetworkExpert.Request requestCreate = imageProtocol.create(parameter);
        if (BasicAuthentication.isNeedBasicAuthentication()) {
            requestCreate = BasicAuthentication.addBasicAuthorization(requestCreate);
        }
        return imageProtocol.parse(this._context.getNetworkExpert().connect(requestCreate)).bytes;
    }

    public static class Request {
        public String _cardArtPrefix;
        public String _cid;
        public String _url;

        public Request(String cid, String cardArtPrefix, String url) {
            this._cid = cid;
            this._cardArtPrefix = cardArtPrefix;
            this._url = url;
        }

        public String toString() {
            return "Request cid = " + this._cid + ", cardArtPrefix = " + this._cardArtPrefix + ", url = " + this._url;
        }
    }
}

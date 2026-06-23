package com.felicanetworks.mfm.main.model;

import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public interface CardDetailFunc {

    public interface CardOperationListener {
        void onCompleted();

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface CreateDetailCardInfoListener {
        void onCompleted(List<MyServiceInfo> myServiceInfoList, List<MyCardInfo> myCardInfoList);

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface NotFoundImageListener {
        void onCompleted();

        void onGetImage(String id, String url, Bitmap image);
    }

    void cancel();

    void createDetailCardInfo(boolean isMfiLoggedIn, boolean forceUpdate, final CreateDetailCardInfoListener listener);

    void deleteCard(String cid, CardOperationListener deleteCardListener);

    void enableCard(String cid, String sid, CardOperationListener enableCardListener);

    void getNotFoundImage(String unionId, Map<Integer, String> urls, final NotFoundImageListener listener);

    boolean isUserOperation();

    void mfcFinish();

    boolean needWarningDelete(String targetCid);

    void recoveryCard(String cid, String sid, CardOperationListener recoveryCardListener);

    void setup(List<MyServiceInfo> myServiceInfoList, List<MyCardInfo> myCardInfoList);
}

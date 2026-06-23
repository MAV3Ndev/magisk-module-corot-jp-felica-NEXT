package com.felicanetworks.mfm.main.model;

import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface CardDetailFunc {

    public interface CardOperationListener {
        void onCompleted();

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface CreateDetailCardInfoListener {
        void onCompleted(List<MyServiceInfo> list, List<MyCardInfo> list2);

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface NotFoundImageListener {
        void onCompleted();

        void onGetImage(String str, String str2, Bitmap bitmap);
    }

    void cancel();

    void createDetailCardInfo(boolean z, boolean z2, CreateDetailCardInfoListener createDetailCardInfoListener);

    void deleteCard(String str, CardOperationListener cardOperationListener);

    void enableCard(String str, String str2, CardOperationListener cardOperationListener);

    void getNotFoundImage(String str, String str2, NotFoundImageListener notFoundImageListener);

    void mfcFinish();

    boolean needWarningDelete(String str);

    void recoveryCard(String str, String str2, CardOperationListener cardOperationListener);

    void setup(List<MyServiceInfo> list, List<MyCardInfo> list2);
}

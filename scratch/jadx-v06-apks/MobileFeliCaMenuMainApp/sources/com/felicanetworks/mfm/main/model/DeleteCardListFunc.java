package com.felicanetworks.mfm.main.model;

import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface DeleteCardListFunc {

    public interface CreateDeleteCardListInfoListener {
        void onCompleted(List<MyServiceInfo> list, List<MyCardInfo> list2);

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface LackCardFaceImageListener {
        void onGetImage(String str, String str2, Bitmap bitmap);
    }

    public interface ReissueCardListener {
        void onError(ModelErrorInfo modelErrorInfo);

        void onIssued(MyCardInfo myCardInfo);
    }

    void cancel();

    void createDeleteCardListInfo(CreateDeleteCardListInfoListener createDeleteCardListInfoListener);

    void getLackCardFaceImage(String str, String str2, LackCardFaceImageListener lackCardFaceImageListener);

    void mfcFinish();

    void reissueCard(String str, ReissueCardListener reissueCardListener);

    void setup(List<MyServiceInfo> list);
}

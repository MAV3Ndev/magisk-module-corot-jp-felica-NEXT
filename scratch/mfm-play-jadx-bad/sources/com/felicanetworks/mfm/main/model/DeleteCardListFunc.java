package com.felicanetworks.mfm.main.model;

import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public interface DeleteCardListFunc {

    public interface CreateDeleteCardListInfoListener {
        void onCompleted(List<MyServiceInfo> myServiceInfoList, List<MyCardInfo> myCardInfoList);

        void onError(ModelErrorInfo modelErrorInfo);
    }

    public interface LackCardFaceImageListener {
        void onGetImage(String id, String url, Bitmap image);
    }

    public interface ReissueCardListener {
        void onError(ModelErrorInfo modelErrorInfo);

        void onIssued(MyCardInfo card);
    }

    void cancel();

    void createDeleteCardListInfo(final CreateDeleteCardListInfoListener listener);

    void getLackCardFaceImage(String unionId, Map<Integer, String> urls, final LackCardFaceImageListener listener);

    void mfcFinish();

    void reissueCard(String cid, ReissueCardListener listener);

    void setup(List<MyServiceInfo> myServices);
}

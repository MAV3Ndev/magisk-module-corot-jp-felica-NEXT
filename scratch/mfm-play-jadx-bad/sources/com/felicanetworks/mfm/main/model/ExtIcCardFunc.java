package com.felicanetworks.mfm.main.model;

import android.graphics.Bitmap;
import android.nfc.Tag;
import com.felicanetworks.mfm.main.model.info.ExtIcCardInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;

/* JADX INFO: loaded from: classes3.dex */
public interface ExtIcCardFunc extends FelicaPocketFunc {

    public interface ExtIcCardListener {
        void onFailure(ModelErrorInfo error);

        void onSuccess(ExtIcCardInfo info);
    }

    public interface OrderPromotionImageListener {
        void onSuccess(Bitmap image);
    }

    @Override // com.felicanetworks.mfm.main.model.FelicaPocketFunc
    void cancel();

    void cancelPromotionImage();

    boolean isExistTargetCards();

    void orderInfo(Tag tag, ExtIcCardListener listener);

    void orderPromotionImage(String url, OrderPromotionImageListener listener);
}

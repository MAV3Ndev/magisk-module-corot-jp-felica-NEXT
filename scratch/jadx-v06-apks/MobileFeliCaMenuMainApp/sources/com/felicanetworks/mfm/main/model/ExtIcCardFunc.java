package com.felicanetworks.mfm.main.model;

import android.graphics.Bitmap;
import android.nfc.Tag;
import com.felicanetworks.mfm.main.model.info.ExtIcCardInfo;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;

/* JADX INFO: loaded from: classes.dex */
public interface ExtIcCardFunc extends FelicaPocketFunc {

    public interface ExtIcCardListener {
        void onFailure(ModelErrorInfo modelErrorInfo);

        void onSuccess(ExtIcCardInfo extIcCardInfo);
    }

    public interface OrderPromotionImageListener {
        void onSuccess(Bitmap bitmap);
    }

    @Override // com.felicanetworks.mfm.main.model.FelicaPocketFunc
    void cancel();

    void cancelPromotionImage();

    boolean isExistTargetCards();

    void orderInfo(Tag tag, ExtIcCardListener extIcCardListener);

    void orderPromotionImage(String str, OrderPromotionImageListener orderPromotionImageListener);
}

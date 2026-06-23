package com.felicanetworks.mfm.main.presenter.agent;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyDcardInfo;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess;

/* JADX INFO: loaded from: classes.dex */
public class MyDcardCardInfoAgent extends MyCardInfoAgent {
    private final MyDcardInfo.DcardType dcardType;

    MyDcardCardInfoAgent(MyDcardInfo myDcardInfo, MyCardInfo myCardInfo) {
        MyDcardInfo.DcardType dcardType;
        super(myCardInfo);
        if (myCardInfo.isActiveForeground()) {
            dcardType = myDcardInfo.getDcardType();
        } else {
            dcardType = MyServiceDatabaseAccess.getInstance().getDcardType(myCardInfo.getCardId());
        }
        this.dcardType = dcardType;
        MyServiceDatabaseAccess.getInstance().setDcardType(myCardInfo.getCardId(), this.dcardType);
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent
    String getCardName() {
        if (isDcardMini()) {
            return getDcardMiniName();
        }
        return super.getCardName();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent
    String getCardFaceImageUrl() {
        if (isDcardMini()) {
            return null;
        }
        return super.getCardFaceImageUrl();
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyCardInfoAgent
    Bitmap getCardFaceImage() {
        if (isDcardMini()) {
            return getDcardMiniFaceImage();
        }
        return super.getCardFaceImage();
    }

    String getDcardMiniName() {
        return (String) Sg.getValue(Sg.Key.SETTING_DCARD_SERVICE_NAME);
    }

    Bitmap getDcardMiniFaceImage() {
        Context context = PresenterData.getInstance().getContext();
        Resources resources = context.getResources();
        return BitmapFactory.decodeResource(resources, resources.getIdentifier((String) Sg.getValue(Sg.Key.SETTING_DCARD_ICON), "drawable", context.getPackageName()));
    }

    boolean isDcardMini() {
        return this.dcardType == MyDcardInfo.DcardType.DCARD_MINI;
    }
}

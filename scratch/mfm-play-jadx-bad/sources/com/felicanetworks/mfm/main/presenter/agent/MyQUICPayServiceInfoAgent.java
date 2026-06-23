package com.felicanetworks.mfm.main.presenter.agent;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.specific.MyQUICPayInfo;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MyQUICPayServiceInfoAgent extends MyServiceInfoAgent {
    private final MyQUICPayInfo client;

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    public boolean hasMoreInformation() {
        return false;
    }

    MyQUICPayServiceInfoAgent(MyQUICPayInfo service, List<MyCardInfo> cards, boolean isMfiLoggedIn, boolean isChaced) {
        super(service, cards, isMfiLoggedIn, isChaced);
        this.client = service;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent, com.felicanetworks.mfm.main.presenter.agent.ServiceInfoAgent
    public LinkageAgent getLinkage() {
        MyCardInfoAgent mainMyCardInfoAgent = getMainMyCardInfoAgent();
        if (mainMyCardInfoAgent == null || mainMyCardInfoAgent.getLinkage(0) == null || !mainMyCardInfoAgent.isActiveForeground()) {
            return new LinkageAgent(this.client.getLinkage());
        }
        return mainMyCardInfoAgent.getLinkage(0);
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.agent.MyQUICPayServiceInfoAgent$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$specific$MyQUICPayInfo$QPType;

        static {
            int[] iArr = new int[MyQUICPayInfo.QPType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$specific$MyQUICPayInfo$QPType = iArr;
            try {
                iArr[MyQUICPayInfo.QPType.QP_MOBILE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$specific$MyQUICPayInfo$QPType[MyQUICPayInfo.QPType.QP_LOCAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$specific$MyQUICPayInfo$QPType[MyQUICPayInfo.QPType.QP_MFI.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$specific$MyQUICPayInfo$QPType[MyQUICPayInfo.QPType.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    public String getServiceName() {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$info$specific$MyQUICPayInfo$QPType[this.client.getQPType().ordinal()];
        if (i == 1) {
            return getName();
        }
        if (i == 2) {
            return (String) Sg.getValue(Sg.Key.SETTING_QP_MIZUHO_SERVICE_NAME);
        }
        if (i == 3) {
            MyCardInfoAgent mainMyCardInfoAgent = getMainMyCardInfoAgent();
            if (mainMyCardInfoAgent == null || TextUtils.isEmpty(mainMyCardInfoAgent.getCardName()) || !mainMyCardInfoAgent.isActiveForeground()) {
                return (String) Sg.getValue(Sg.Key.SETTING_QP_SERVICE_NAME);
            }
            return mainMyCardInfoAgent.getCardName();
        }
        return (String) Sg.getValue(Sg.Key.SETTING_QP_SERVICE_NAME);
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    public Bitmap getCardFaceImage() {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$info$specific$MyQUICPayInfo$QPType[this.client.getQPType().ordinal()];
        if (i == 1) {
            return super.getCardFaceImage();
        }
        if (i == 2) {
            return getQPFaceImage((String) Sg.getValue(Sg.Key.SETTING_QP_MIZUHO_ICON));
        }
        return getQPFaceImage((String) Sg.getValue(Sg.Key.SETTING_QP_ICON));
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    public MyCardInfoAgent getMainMyCardInfoAgent() {
        if (this.client.getQPType() == MyQUICPayInfo.QPType.QP_MFI) {
            return super.getMainMyCardInfoAgent();
        }
        return null;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    public MyServiceInfoAgent.LeadType getLeadType() {
        if (this.client.getQPType() == MyQUICPayInfo.QPType.QP_MFI) {
            return super.getLeadType();
        }
        if (isNoRegister()) {
            return MyServiceInfoAgent.LeadType.REGISTER_SERVICE;
        }
        return MyServiceInfoAgent.LeadType.NONE;
    }

    @Override // com.felicanetworks.mfm.main.presenter.agent.MyServiceInfoAgent
    public boolean isShowCardShadowImage() {
        if (this.client.getQPType() == MyQUICPayInfo.QPType.QP_MFI) {
            return super.isShowCardShadowImage();
        }
        return false;
    }

    private Bitmap getQPFaceImage(String imageName) {
        Context context = PresenterData.getInstance().getContext();
        Resources resources = context.getResources();
        return BitmapFactory.decodeResource(resources, resources.getIdentifier(imageName, "drawable", context.getPackageName()));
    }
}

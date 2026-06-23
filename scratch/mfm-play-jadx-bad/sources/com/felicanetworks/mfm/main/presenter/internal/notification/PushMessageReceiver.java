package com.felicanetworks.mfm.main.presenter.internal.notification;

import android.content.Context;
import com.felicanetworks.mfm.main.model.PushGateway;
import com.felicanetworks.mfm.main.model.internal.main.UpdateCardInfoWorker;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.messenger.MessageProtocol;
import com.felicanetworks.mfm.messenger.MessageReceiver;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class PushMessageReceiver extends MessageReceiver {
    private static final String EXTRA_KEY_CID_HASH = "hashedCid";
    private static final String PROCESSING_TYPE_GET_CARD_ADDITIONAL_INFO_LIST = "getCardAdditionalInfoList";
    private static final String PROCESSING_TYPE_KEY = "processingType";

    @Override // com.felicanetworks.mfm.messenger.MessageReceiver
    protected boolean isAccepted(Context context, MessageProtocol.Action action) {
        return PushGateway.isRegisteredDeviceToken(context);
    }

    @Override // com.felicanetworks.mfm.messenger.MessageReceiver
    protected void onReceiveToken(Context context, String token) {
        try {
            if (PushGateway.isPermittedPush(context)) {
                PushMessageReceiverWorker.notifyToken(context, token);
            }
        } catch (Exception e) {
            LogUtil.warning(new MfmException(getClass(), 56, e));
        }
    }

    @Override // com.felicanetworks.mfm.messenger.MessageReceiver
    protected void onReceiveMessage(Context context, MessageProtocol.Action action, Map<String, String> messages) {
        AnalysisManager.stampReceive(context);
        try {
            if (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$messenger$MessageProtocol$Action[action.ordinal()] != 1) {
                return;
            }
            String str = messages.get(PROCESSING_TYPE_KEY);
            if (str == null) {
                PushMessageReceiverWorker.notifyMenuMessage(context, messages);
            } else if (str.equals(PROCESSING_TYPE_GET_CARD_ADDITIONAL_INFO_LIST)) {
                UpdateCardInfoWorker.requestWorkHashedCid(context, messages.get(EXTRA_KEY_CID_HASH));
            }
        } catch (Exception e) {
            LogUtil.warning(new MfmException(getClass(), 57, e));
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.internal.notification.PushMessageReceiver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$messenger$MessageProtocol$Action;

        static {
            int[] iArr = new int[MessageProtocol.Action.values().length];
            $SwitchMap$com$felicanetworks$mfm$messenger$MessageProtocol$Action = iArr;
            try {
                iArr[MessageProtocol.Action.RECEIVE_MESSAGE_TO_MENU.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$messenger$MessageProtocol$Action[MessageProtocol.Action.RECEIVE_MESSAGE_TO_MFIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$messenger$MessageProtocol$Action[MessageProtocol.Action.RECEIVE_MESSAGE_TO_SEMC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}

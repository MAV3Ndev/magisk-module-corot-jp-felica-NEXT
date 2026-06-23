package com.felicanetworks.mfm.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.felicanetworks.mfm.messenger.MessageProtocol;
import com.felicanetworks.mfm.messenger.MessageSender;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MessageReceiver extends BroadcastReceiver {
    protected boolean isAccepted(Context context, MessageProtocol.Action action) {
        return true;
    }

    protected void onDeletedMessage(Context context) {
    }

    protected void onReceiveMessage(Context context, MessageProtocol.Action action, Map<String, String> map) {
    }

    protected void onReceiveToken(Context context, String str) {
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Logger.info(context, intent);
        if (context == null || intent == null) {
            Logger.debug("Bad parameter.");
            return;
        }
        MessageProtocol.Action actionResolve = MessageProtocol.Action.resolve(intent.getAction());
        Logger.debug(actionResolve);
        if (!isAccepted(context, actionResolve)) {
            Logger.debug("Refused to receive.");
            return;
        }
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$messenger$MessageProtocol$Action[actionResolve.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            Map<String, String> mapFindMessages = MessageSender.MessagePacket.findMessages(intent);
            Logger.debug("Call onReceiveMessage().", mapFindMessages);
            onReceiveMessage(context, actionResolve, mapFindMessages);
        } else {
            if (i != 4) {
                if (i == 5) {
                    Logger.debug("Call onDeletedMessage().");
                    onDeletedMessage(context);
                    return;
                } else {
                    Logger.warning("Unknown action.");
                    return;
                }
            }
            String strFindToken = MessageSender.TokenPacket.findToken(intent);
            if (TextUtils.isEmpty(strFindToken)) {
                Logger.debug("'token' is empty.");
            } else {
                Logger.debug("Call onReceiveToken().", strFindToken);
                onReceiveToken(context, strFindToken);
            }
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.messenger.MessageReceiver$1, reason: invalid class name */
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
            try {
                $SwitchMap$com$felicanetworks$mfm$messenger$MessageProtocol$Action[MessageProtocol.Action.RECEIVE_TOKEN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$messenger$MessageProtocol$Action[MessageProtocol.Action.RECEIVE_DELETED_MESSAGE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$messenger$MessageProtocol$Action[MessageProtocol.Action.UNKNOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }
}

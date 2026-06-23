package com.felicanetworks.mfm.main.presenter.internal.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.core.app.JobIntentService;
import com.felicanetworks.mfm.main.model.PushGateway;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PushMessageReceiverWorker extends JobIntentService {

    private enum Action {
        NOTIFY_TOKEN,
        NOTIFY_MENU_MESSAGE
    }

    static void notifyToken(Context context, String str) {
        Intent intent = new Intent(Action.NOTIFY_TOKEN.name());
        intent.putExtra(Action.NOTIFY_TOKEN.name(), str);
        enqueue(context, intent);
    }

    static void notifyMenuMessage(Context context, Map<String, String> map) {
        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }
        Intent intent = new Intent(Action.NOTIFY_MENU_MESSAGE.name());
        intent.putExtras(bundle);
        enqueue(context, intent);
    }

    private static void enqueue(Context context, Intent intent) {
        enqueueWork(context, (Class<?>) PushMessageReceiverWorker.class, 829877, intent.setClass(context, PushMessageReceiverWorker.class));
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.internal.notification.PushMessageReceiverWorker$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$notification$PushMessageReceiverWorker$Action;

        static {
            int[] iArr = new int[Action.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$notification$PushMessageReceiverWorker$Action = iArr;
            try {
                iArr[Action.NOTIFY_TOKEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$presenter$internal$notification$PushMessageReceiverWorker$Action[Action.NOTIFY_MENU_MESSAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // androidx.core.app.JobIntentService
    protected void onHandleWork(Intent intent) {
        try {
            int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$presenter$internal$notification$PushMessageReceiverWorker$Action[Action.valueOf(intent.getAction()).ordinal()];
            if (i == 1) {
                String stringExtra = intent.getStringExtra(Action.NOTIFY_TOKEN.name());
                if (TextUtils.isEmpty(stringExtra)) {
                } else {
                    PushGateway.syncRegistrationId(this, stringExtra);
                }
            } else if (i == 2) {
                StateController.postStateEvent(StateMachine.Event.EI_PUSH, null, intent);
            }
        } catch (Exception e) {
            LogUtil.warning(new MfmException(getClass(), 1, e));
        }
    }
}

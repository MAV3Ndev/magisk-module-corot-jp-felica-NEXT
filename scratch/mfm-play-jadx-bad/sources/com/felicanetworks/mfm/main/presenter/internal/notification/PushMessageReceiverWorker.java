package com.felicanetworks.mfm.main.presenter.internal.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.felicanetworks.mfm.main.model.PushGateway;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class PushMessageReceiverWorker extends Worker {

    private enum Action {
        NOTIFY_TOKEN,
        NOTIFY_MENU_MESSAGE
    }

    private enum Key {
        KEY_ACTION,
        KEY_TOKEN,
        KEY_MASSAGE,
        KEY_MASSAGE_VALUE
    }

    public PushMessageReceiverWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    static void notifyToken(Context context, String token) {
        Data.Builder builder = new Data.Builder();
        builder.putString(Key.KEY_ACTION.name(), Action.NOTIFY_TOKEN.name());
        builder.putString(Key.KEY_TOKEN.name(), token);
        enqueue(context, builder.build());
    }

    static void notifyMenuMessage(Context context, Map<String, String> messages) {
        Data.Builder builder = new Data.Builder();
        builder.putString(Key.KEY_ACTION.name(), Action.NOTIFY_MENU_MESSAGE.name());
        builder.putAll(new HashMap(messages));
        enqueue(context, builder.build());
    }

    private static void enqueue(Context context, Data data) {
        OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder((Class<? extends ListenableWorker>) PushMessageReceiverWorker.class);
        builder.setInputData(data);
        WorkManager.getInstance(context).enqueue(builder.build());
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        Data inputData = getInputData();
        try {
            int iOrdinal = Action.valueOf(inputData.getString(Key.KEY_ACTION.name())).ordinal();
            if (iOrdinal == 0) {
                String string = inputData.getString(Key.KEY_TOKEN.name());
                if (TextUtils.isEmpty(string)) {
                    return ListenableWorker.Result.failure();
                }
                PushGateway.syncRegistrationId(getApplicationContext(), string);
            } else if (iOrdinal == 1) {
                Map<String, Object> keyValueMap = inputData.getKeyValueMap();
                Bundle bundle = new Bundle();
                for (Map.Entry<String, Object> entry : keyValueMap.entrySet()) {
                    String key = entry.getKey();
                    if (!key.equals(Key.KEY_ACTION.name())) {
                        bundle.putString(key, String.valueOf(entry.getValue()));
                    }
                }
                Intent intent = new Intent(Action.NOTIFY_MENU_MESSAGE.name());
                intent.putExtras(bundle);
                StateController.postStateEvent(StateMachine.Event.EI_PUSH, null, intent);
            }
        } catch (Exception e) {
            LogUtil.warning(new MfmException(getClass(), 1, e));
        }
        return ListenableWorker.Result.success();
    }
}

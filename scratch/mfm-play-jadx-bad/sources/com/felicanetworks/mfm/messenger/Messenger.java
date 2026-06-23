package com.felicanetworks.mfm.messenger;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.text.TextUtils;
import com.felicanetworks.mfm.messenger.FirebaseApiAccessor;
import com.felicanetworks.mfm.messenger.MessageProtocol;
import com.felicanetworks.mfm.messenger.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes3.dex */
public class Messenger {

    public interface FetchTokenListener {
        void onFailed(Exception exc);

        void onFetchedToken(String str);
    }

    public static int getApiVersion() {
        return 36;
    }

    private Messenger() {
    }

    static {
        Logger.info("<messenger module build configuration>", BuildConfig.LIBRARY_PACKAGE_NAME, BuildConfig.MSG_VERSION_NAME, "release", false);
    }

    public static String getModuleVersion() {
        return BuildConfig.MSG_VERSION_NAME;
    }

    static List<String> queryTargetReceivers(Context context, MessageProtocol.Action action) {
        return ReceiverDetector.detector(context).receivers(action);
    }

    public static void fetchToken(final Context context, final FetchTokenListener fetchTokenListener) {
        Logger.info(context, fetchTokenListener);
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.messenger.Messenger.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    String token = Messenger.getToken(context);
                    if (TextUtils.isEmpty(token)) {
                        throw new NullPointerException("Token is empty.");
                    }
                    Logger.debug("Call onFetchedToken()", token);
                    try {
                        fetchTokenListener.onFetchedToken(token);
                    } catch (Exception e) {
                        Logger.warning(e);
                    }
                } catch (Exception e2) {
                    Logger.debug("Call onFailed()", e2);
                    try {
                        fetchTokenListener.onFailed(e2);
                    } catch (Exception e3) {
                        Logger.warning(e3);
                    }
                }
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getToken(Context context) throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final HashMap map = new HashMap();
        FirebaseApiAccessor.access(context, new FirebaseApiAccessor.AccessTransaction() { // from class: com.felicanetworks.mfm.messenger.Messenger.2
            @Override // com.felicanetworks.mfm.messenger.FirebaseApiAccessor.AccessTransaction
            public void transaction(FirebaseApi firebaseApi) {
                try {
                    try {
                        map.put("token", firebaseApi.getToken().getToken());
                    } catch (Exception e) {
                        map.put("exception", e);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }
        });
        try {
            if (!countDownLatch.await(70L, TimeUnit.SECONDS)) {
                map.put("exception", new TimeoutException("Timeout 70 seconds for FirebaseMessaging#getToken()"));
            }
        } catch (InterruptedException e) {
            map.put("exception", e);
        }
        Exception exc = (Exception) map.get("exception");
        if (exc != null) {
            throw exc;
        }
        String str = (String) map.get("token");
        updateNewToken(context, str);
        return str;
    }

    static void updateNewToken(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Storage storageKeeper = Storage.keeper(context);
        if (TextUtils.equals(storageKeeper.refer(Storage.Item.TOKEN), str)) {
            return;
        }
        storageKeeper.store(Storage.Item.TOKEN, str);
    }

    private static class ReceiverDetector {
        private static ReceiverDetector self;
        private final Map<MessageProtocol.Action, List<String>> destinations = new HashMap();

        static ReceiverDetector detector(Context context) {
            if (self == null) {
                self = new ReceiverDetector(context);
            }
            return self;
        }

        private ReceiverDetector(Context context) {
            List<ResolveInfo> listQueryBroadcastReceivers;
            List<String> list;
            for (MessageProtocol.Action action : MessageProtocol.Action.values()) {
                this.destinations.put(action, new ArrayList());
            }
            PackageManager packageManager = context.getPackageManager();
            if (Build.VERSION.SDK_INT >= 33) {
                listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(new Intent().setPackage(context.getPackageName()), PackageManager.ResolveInfoFlags.of(64L));
            } else {
                listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(new Intent().setPackage(context.getPackageName()), 64);
            }
            for (ResolveInfo resolveInfo : listQueryBroadcastReceivers) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                IntentFilter intentFilter = resolveInfo.filter;
                for (MessageProtocol.Action action2 : MessageProtocol.Action.values()) {
                    if (intentFilter.hasAction(action2.value()) && (list = this.destinations.get(action2)) != null) {
                        list.add(activityInfo.name);
                    }
                }
            }
            Logger.info(this.destinations);
        }

        List<String> receivers(MessageProtocol.Action action) {
            return this.destinations.get(action);
        }
    }
}

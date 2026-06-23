package com.google.firebase.heartbeatinfo;

import android.content.Context;
import android.util.Base64OutputStream;
import androidx.core.os.UserManagerCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.annotations.concurrent.Background;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.firebase.components.Dependency;
import com.google.firebase.components.Qualified;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.inject.Provider;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultHeartBeatController implements HeartBeatController, HeartBeatInfo {
    private final Context applicationContext;
    private final Executor backgroundExecutor;
    private final Set<HeartBeatConsumer> consumers;
    private final Provider<HeartBeatInfoStorage> storageProvider;
    private final Provider<UserAgentPublisher> userAgentProvider;

    public Task<Void> registerHeartBeat() {
        if (this.consumers.size() <= 0) {
            return Tasks.forResult(null);
        }
        if (!UserManagerCompat.isUserUnlocked(this.applicationContext)) {
            return Tasks.forResult(null);
        }
        return Tasks.call(this.backgroundExecutor, new Callable() { // from class: com.google.firebase.heartbeatinfo.DefaultHeartBeatController$$ExternalSyntheticLambda3
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.m585x734756b4();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$registerHeartBeat$0$com-google-firebase-heartbeatinfo-DefaultHeartBeatController, reason: not valid java name */
    /* synthetic */ Void m585x734756b4() throws Exception {
        synchronized (this) {
            this.storageProvider.get().storeHeartBeat(System.currentTimeMillis(), this.userAgentProvider.get().getUserAgent());
        }
        return null;
    }

    @Override // com.google.firebase.heartbeatinfo.HeartBeatController
    public Task<String> getHeartBeatsHeader() {
        if (!UserManagerCompat.isUserUnlocked(this.applicationContext)) {
            return Tasks.forResult("");
        }
        return Tasks.call(this.backgroundExecutor, new Callable() { // from class: com.google.firebase.heartbeatinfo.DefaultHeartBeatController$$ExternalSyntheticLambda1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.m584x341e14f2();
            }
        });
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[]}, finally: {[INVOKE, MOVE_EXCEPTION, INVOKE, MOVE_EXCEPTION] complete} */
    /* JADX INFO: renamed from: lambda$getHeartBeatsHeader$1$com-google-firebase-heartbeatinfo-DefaultHeartBeatController, reason: not valid java name */
    /* synthetic */ String m584x341e14f2() throws Exception {
        String string;
        synchronized (this) {
            HeartBeatInfoStorage heartBeatInfoStorage = this.storageProvider.get();
            List<HeartBeatResult> allHeartBeats = heartBeatInfoStorage.getAllHeartBeats();
            heartBeatInfoStorage.deleteAllHeartBeats();
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < allHeartBeats.size(); i++) {
                HeartBeatResult heartBeatResult = allHeartBeats.get(i);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("agent", heartBeatResult.getUserAgent());
                jSONObject.put("dates", new JSONArray((Collection) heartBeatResult.getUsedDates()));
                jSONArray.put(jSONObject);
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("heartbeats", jSONArray);
            jSONObject2.put("version", "2");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Base64OutputStream base64OutputStream = new Base64OutputStream(byteArrayOutputStream, 11);
            try {
                GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(base64OutputStream);
                try {
                    gZIPOutputStream.write(jSONObject2.toString().getBytes(StringUtil.UTF_8));
                    gZIPOutputStream.close();
                    base64OutputStream.close();
                    string = byteArrayOutputStream.toString(StringUtil.UTF_8);
                } finally {
                }
            } finally {
            }
        }
        return string;
    }

    private DefaultHeartBeatController(final Context context, final String str, Set<HeartBeatConsumer> set, Provider<UserAgentPublisher> provider, Executor executor) {
        this((Provider<HeartBeatInfoStorage>) new Provider() { // from class: com.google.firebase.heartbeatinfo.DefaultHeartBeatController$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.google.firebase.inject.Provider
            public final Object get() {
                return DefaultHeartBeatController.lambda$new$2(context, str);
            }
        }, set, executor, provider, context);
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0002: CONSTRUCTOR (r1v0 android.content.Context), (r2v0 java.lang.String) A[MD:(android.content.Context, java.lang.String):void (m)] (LINE:119) call: com.google.firebase.heartbeatinfo.HeartBeatInfoStorage.<init>(android.content.Context, java.lang.String):void type: CONSTRUCTOR */
    static /* synthetic */ HeartBeatInfoStorage lambda$new$2(Context context, String str) {
        return new HeartBeatInfoStorage(context, str);
    }

    DefaultHeartBeatController(Provider<HeartBeatInfoStorage> provider, Set<HeartBeatConsumer> set, Executor executor, Provider<UserAgentPublisher> provider2, Context context) {
        this.storageProvider = provider;
        this.consumers = set;
        this.backgroundExecutor = executor;
        this.userAgentProvider = provider2;
        this.applicationContext = context;
    }

    public static Component<DefaultHeartBeatController> component() {
        final Qualified qualified = Qualified.qualified(Background.class, Executor.class);
        return Component.builder(DefaultHeartBeatController.class, HeartBeatController.class, HeartBeatInfo.class).add(Dependency.required((Class<?>) Context.class)).add(Dependency.required((Class<?>) FirebaseApp.class)).add(Dependency.setOf((Class<?>) HeartBeatConsumer.class)).add(Dependency.requiredProvider((Class<?>) UserAgentPublisher.class)).add(Dependency.required((Qualified<?>) qualified)).factory(new ComponentFactory() { // from class: com.google.firebase.heartbeatinfo.DefaultHeartBeatController$$ExternalSyntheticLambda2
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.google.firebase.components.ComponentFactory
            public final Object create(ComponentContainer componentContainer) {
                return DefaultHeartBeatController.lambda$component$3(qualified, componentContainer);
            }
        }).build();
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0029: CONSTRUCTOR 
  (wrap:android.content.Context:0x0008: CHECK_CAST (android.content.Context) (wrap:java.lang.Object:0x0004: INVOKE 
  (r7v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x0002: CONST_CLASS  A[WRAPPED] android.content.Context.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.get(java.lang.Class):java.lang.Object A[MD:<T>:(java.lang.Class<T>):T (m), WRAPPED] (LINE:152)))
  (wrap:java.lang.String:0x0012: INVOKE 
  (wrap:com.google.firebase.FirebaseApp:0x0010: CHECK_CAST (com.google.firebase.FirebaseApp) (wrap:java.lang.Object:0x000c: INVOKE 
  (r7v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x000a: CONST_CLASS  A[WRAPPED] com.google.firebase.FirebaseApp.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.get(java.lang.Class):java.lang.Object A[MD:<T>:(java.lang.Class<T>):T (m), WRAPPED] (LINE:153)))
 VIRTUAL call: com.google.firebase.FirebaseApp.getPersistenceKey():java.lang.String A[MD:():java.lang.String (m), WRAPPED] (LINE:153))
  (wrap:java.util.Set<com.google.firebase.heartbeatinfo.HeartBeatConsumer>:?: CAST (java.util.Set<com.google.firebase.heartbeatinfo.HeartBeatConsumer>) (wrap:java.util.Set:0x0018: INVOKE 
  (r7v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x0016: CONST_CLASS  A[WRAPPED] com.google.firebase.heartbeatinfo.HeartBeatConsumer.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.setOf(java.lang.Class):java.util.Set A[MD:<T>:(java.lang.Class<T>):java.util.Set<T> (m), WRAPPED] (LINE:154)))
  (wrap:com.google.firebase.inject.Provider<com.google.firebase.platforminfo.UserAgentPublisher>:?: CAST (com.google.firebase.inject.Provider<com.google.firebase.platforminfo.UserAgentPublisher>) (wrap:com.google.firebase.inject.Provider:0x001e: INVOKE 
  (r7v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x001c: CONST_CLASS  A[WRAPPED] com.google.firebase.platforminfo.UserAgentPublisher.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.getProvider(java.lang.Class):com.google.firebase.inject.Provider A[MD:<T>:(java.lang.Class<T>):com.google.firebase.inject.Provider<T> (m), WRAPPED] (LINE:155)))
  (wrap:java.util.concurrent.Executor:0x0027: CHECK_CAST (java.util.concurrent.Executor) (wrap:java.lang.Object:0x0022: INVOKE (r7v0 com.google.firebase.components.ComponentContainer), (r6v0 com.google.firebase.components.Qualified) INTERFACE call: com.google.firebase.components.ComponentContainer.get(com.google.firebase.components.Qualified):java.lang.Object A[MD:<T>:(com.google.firebase.components.Qualified<T>):T (m), WRAPPED] (LINE:156)))
 A[MD:(android.content.Context, java.lang.String, java.util.Set<com.google.firebase.heartbeatinfo.HeartBeatConsumer>, com.google.firebase.inject.Provider<com.google.firebase.platforminfo.UserAgentPublisher>, java.util.concurrent.Executor):void (m)] (LINE:151) call: com.google.firebase.heartbeatinfo.DefaultHeartBeatController.<init>(android.content.Context, java.lang.String, java.util.Set, com.google.firebase.inject.Provider, java.util.concurrent.Executor):void type: CONSTRUCTOR */
    static /* synthetic */ DefaultHeartBeatController lambda$component$3(Qualified qualified, ComponentContainer componentContainer) {
        return new DefaultHeartBeatController((Context) componentContainer.get(Context.class), ((FirebaseApp) componentContainer.get(FirebaseApp.class)).getPersistenceKey(), (Set<HeartBeatConsumer>) componentContainer.setOf(HeartBeatConsumer.class), (Provider<UserAgentPublisher>) componentContainer.getProvider(UserAgentPublisher.class), (Executor) componentContainer.get(qualified));
    }

    @Override // com.google.firebase.heartbeatinfo.HeartBeatInfo
    public synchronized HeartBeatInfo.HeartBeat getHeartBeatCode(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        HeartBeatInfoStorage heartBeatInfoStorage = this.storageProvider.get();
        if (heartBeatInfoStorage.shouldSendGlobalHeartBeat(jCurrentTimeMillis)) {
            heartBeatInfoStorage.postHeartBeatCleanUp();
            return HeartBeatInfo.HeartBeat.GLOBAL;
        }
        return HeartBeatInfo.HeartBeat.NONE;
    }
}

package com.google.firebase.messaging;

import com.google.android.datatransport.TransportFactory;
import com.google.firebase.FirebaseApp;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.components.Qualified;
import com.google.firebase.datatransport.TransportBackend;
import com.google.firebase.events.Subscriber;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class FirebaseMessagingRegistrar implements ComponentRegistrar {
    private static final String LIBRARY_NAME = "fire-fcm";

    @Override // com.google.firebase.components.ComponentRegistrar
    public List<Component<?>> getComponents() {
        final Qualified qualified = Qualified.qualified(TransportBackend.class, TransportFactory.class);
        return Arrays.asList(Component.builder(FirebaseMessaging.class).name(LIBRARY_NAME).add(Dependency.required((Class<?>) FirebaseApp.class)).add(Dependency.optional(FirebaseInstanceIdInternal.class)).add(Dependency.optionalProvider((Class<?>) UserAgentPublisher.class)).add(Dependency.optionalProvider((Class<?>) HeartBeatInfo.class)).add(Dependency.required((Class<?>) FirebaseInstallationsApi.class)).add(Dependency.optionalProvider((Qualified<?>) qualified)).add(Dependency.required((Class<?>) Subscriber.class)).factory(new ComponentFactory() { // from class: com.google.firebase.messaging.FirebaseMessagingRegistrar$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.google.firebase.components.ComponentFactory
            public final Object create(ComponentContainer componentContainer) {
                return FirebaseMessagingRegistrar.lambda$getComponents$0(qualified, componentContainer);
            }
        }).alwaysEager().build(), LibraryVersionComponent.create(LIBRARY_NAME, BuildConfig.VERSION_NAME));
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0033: CONSTRUCTOR 
  (wrap:com.google.firebase.FirebaseApp:0x0008: CHECK_CAST (com.google.firebase.FirebaseApp) (wrap:java.lang.Object:0x0004: INVOKE 
  (r9v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x0002: CONST_CLASS  A[WRAPPED] com.google.firebase.FirebaseApp.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.get(java.lang.Class):java.lang.Object A[MD:<T>:(java.lang.Class<T>):T (m), WRAPPED] (LINE:63)))
  (wrap:com.google.firebase.iid.internal.FirebaseInstanceIdInternal:0x0010: CHECK_CAST (com.google.firebase.iid.internal.FirebaseInstanceIdInternal) (wrap:java.lang.Object:0x000c: INVOKE 
  (r9v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x000a: CONST_CLASS  A[WRAPPED] com.google.firebase.iid.internal.FirebaseInstanceIdInternal.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.get(java.lang.Class):java.lang.Object A[MD:<T>:(java.lang.Class<T>):T (m), WRAPPED] (LINE:64)))
  (wrap:com.google.firebase.inject.Provider:0x0014: INVOKE 
  (r9v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x0012: CONST_CLASS  A[WRAPPED] com.google.firebase.platforminfo.UserAgentPublisher.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.getProvider(java.lang.Class):com.google.firebase.inject.Provider A[MD:<T>:(java.lang.Class<T>):com.google.firebase.inject.Provider<T> (m), WRAPPED] (LINE:65))
  (wrap:com.google.firebase.inject.Provider:0x001a: INVOKE 
  (r9v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x0018: CONST_CLASS  A[WRAPPED] com.google.firebase.heartbeatinfo.HeartBeatInfo.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.getProvider(java.lang.Class):com.google.firebase.inject.Provider A[MD:<T>:(java.lang.Class<T>):com.google.firebase.inject.Provider<T> (m), WRAPPED] (LINE:66))
  (wrap:com.google.firebase.installations.FirebaseInstallationsApi:0x0024: CHECK_CAST (com.google.firebase.installations.FirebaseInstallationsApi) (wrap:java.lang.Object:0x0020: INVOKE 
  (r9v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x001e: CONST_CLASS  A[WRAPPED] com.google.firebase.installations.FirebaseInstallationsApi.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.get(java.lang.Class):java.lang.Object A[MD:<T>:(java.lang.Class<T>):T (m), WRAPPED] (LINE:67)))
  (wrap:com.google.firebase.inject.Provider:0x0026: INVOKE (r9v0 com.google.firebase.components.ComponentContainer), (r8v0 com.google.firebase.components.Qualified) INTERFACE call: com.google.firebase.components.ComponentContainer.getProvider(com.google.firebase.components.Qualified):com.google.firebase.inject.Provider A[MD:<T>:(com.google.firebase.components.Qualified<T>):com.google.firebase.inject.Provider<T> (m), WRAPPED] (LINE:68))
  (wrap:com.google.firebase.events.Subscriber:0x0031: CHECK_CAST (com.google.firebase.events.Subscriber) (wrap:java.lang.Object:0x002c: INVOKE 
  (r9v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x002a: CONST_CLASS  A[WRAPPED] com.google.firebase.events.Subscriber.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.get(java.lang.Class):java.lang.Object A[MD:<T>:(java.lang.Class<T>):T (m), WRAPPED] (LINE:69)))
 A[MD:(com.google.firebase.FirebaseApp, com.google.firebase.iid.internal.FirebaseInstanceIdInternal, com.google.firebase.inject.Provider<com.google.firebase.platforminfo.UserAgentPublisher>, com.google.firebase.inject.Provider<com.google.firebase.heartbeatinfo.HeartBeatInfo>, com.google.firebase.installations.FirebaseInstallationsApi, com.google.firebase.inject.Provider<com.google.android.datatransport.TransportFactory>, com.google.firebase.events.Subscriber):void (m)] (LINE:62) call: com.google.firebase.messaging.FirebaseMessaging.<init>(com.google.firebase.FirebaseApp, com.google.firebase.iid.internal.FirebaseInstanceIdInternal, com.google.firebase.inject.Provider, com.google.firebase.inject.Provider, com.google.firebase.installations.FirebaseInstallationsApi, com.google.firebase.inject.Provider, com.google.firebase.events.Subscriber):void type: CONSTRUCTOR */
    static /* synthetic */ FirebaseMessaging lambda$getComponents$0(Qualified qualified, ComponentContainer componentContainer) {
        return new FirebaseMessaging((FirebaseApp) componentContainer.get(FirebaseApp.class), (FirebaseInstanceIdInternal) componentContainer.get(FirebaseInstanceIdInternal.class), componentContainer.getProvider(UserAgentPublisher.class), componentContainer.getProvider(HeartBeatInfo.class), (FirebaseInstallationsApi) componentContainer.get(FirebaseInstallationsApi.class), componentContainer.getProvider(qualified), (Subscriber) componentContainer.get(Subscriber.class));
    }
}

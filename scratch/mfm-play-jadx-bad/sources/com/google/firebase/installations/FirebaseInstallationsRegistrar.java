package com.google.firebase.installations;

import com.google.firebase.FirebaseApp;
import com.google.firebase.annotations.concurrent.Background;
import com.google.firebase.annotations.concurrent.Blocking;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.components.Qualified;
import com.google.firebase.concurrent.FirebaseExecutors;
import com.google.firebase.heartbeatinfo.HeartBeatConsumerComponent;
import com.google.firebase.heartbeatinfo.HeartBeatController;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes4.dex */
public class FirebaseInstallationsRegistrar implements ComponentRegistrar {
    private static final String LIBRARY_NAME = "fire-installations";

    @Override // com.google.firebase.components.ComponentRegistrar
    public List<Component<?>> getComponents() {
        return Arrays.asList(Component.builder(FirebaseInstallationsApi.class).name(LIBRARY_NAME).add(Dependency.required((Class<?>) FirebaseApp.class)).add(Dependency.optionalProvider((Class<?>) HeartBeatController.class)).add(Dependency.required((Qualified<?>) Qualified.qualified(Background.class, ExecutorService.class))).add(Dependency.required((Qualified<?>) Qualified.qualified(Blocking.class, Executor.class))).factory(new ComponentFactory() { // from class: com.google.firebase.installations.FirebaseInstallationsRegistrar$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.google.firebase.components.ComponentFactory
            public final Object create(ComponentContainer componentContainer) {
                return FirebaseInstallationsRegistrar.lambda$getComponents$0(componentContainer);
            }
        }).build(), HeartBeatConsumerComponent.create(), LibraryVersionComponent.create(LIBRARY_NAME, BuildConfig.VERSION_NAME));
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0030: CONSTRUCTOR 
  (wrap:com.google.firebase.FirebaseApp:0x0008: CHECK_CAST (com.google.firebase.FirebaseApp) (wrap:java.lang.Object:0x0004: INVOKE 
  (r6v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x0002: CONST_CLASS  A[WRAPPED] com.google.firebase.FirebaseApp.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.get(java.lang.Class):java.lang.Object A[MD:<T>:(java.lang.Class<T>):T (m), WRAPPED] (LINE:51)))
  (wrap:com.google.firebase.inject.Provider:0x000c: INVOKE 
  (r6v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x000a: CONST_CLASS  A[WRAPPED] com.google.firebase.heartbeatinfo.HeartBeatController.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.getProvider(java.lang.Class):com.google.firebase.inject.Provider A[MD:<T>:(java.lang.Class<T>):com.google.firebase.inject.Provider<T> (m), WRAPPED] (LINE:52))
  (wrap:java.util.concurrent.ExecutorService:0x001c: CHECK_CAST (java.util.concurrent.ExecutorService) (wrap:java.lang.Object:0x0018: INVOKE 
  (r6v0 com.google.firebase.components.ComponentContainer)
  (wrap:com.google.firebase.components.Qualified:0x0014: INVOKE 
  (wrap:java.lang.Class:0x0010: CONST_CLASS  A[WRAPPED] com.google.firebase.annotations.concurrent.Background.class)
  (wrap:java.lang.Class:0x0012: CONST_CLASS  A[WRAPPED] java.util.concurrent.ExecutorService.class)
 STATIC call: com.google.firebase.components.Qualified.qualified(java.lang.Class, java.lang.Class):com.google.firebase.components.Qualified A[MD:<T>:(java.lang.Class<? extends java.lang.annotation.Annotation>, java.lang.Class<T>):com.google.firebase.components.Qualified<T> (m), WRAPPED] (LINE:53))
 INTERFACE call: com.google.firebase.components.ComponentContainer.get(com.google.firebase.components.Qualified):java.lang.Object A[MD:<T>:(com.google.firebase.components.Qualified<T>):T (m), WRAPPED] (LINE:53)))
  (wrap:java.util.concurrent.Executor:0x002c: INVOKE 
  (wrap:java.util.concurrent.Executor:0x002a: CHECK_CAST (java.util.concurrent.Executor) (wrap:java.lang.Object:0x0026: INVOKE 
  (r6v0 com.google.firebase.components.ComponentContainer)
  (wrap:com.google.firebase.components.Qualified:0x0022: INVOKE 
  (wrap:java.lang.Class:0x001e: CONST_CLASS  A[WRAPPED] com.google.firebase.annotations.concurrent.Blocking.class)
  (wrap:java.lang.Class:0x0020: CONST_CLASS  A[WRAPPED] java.util.concurrent.Executor.class)
 STATIC call: com.google.firebase.components.Qualified.qualified(java.lang.Class, java.lang.Class):com.google.firebase.components.Qualified A[MD:<T>:(java.lang.Class<? extends java.lang.annotation.Annotation>, java.lang.Class<T>):com.google.firebase.components.Qualified<T> (m), WRAPPED] (LINE:55))
 INTERFACE call: com.google.firebase.components.ComponentContainer.get(com.google.firebase.components.Qualified):java.lang.Object A[MD:<T>:(com.google.firebase.components.Qualified<T>):T (m), WRAPPED] (LINE:55)))
 STATIC call: com.google.firebase.concurrent.FirebaseExecutors.newSequentialExecutor(java.util.concurrent.Executor):java.util.concurrent.Executor A[MD:(java.util.concurrent.Executor):java.util.concurrent.Executor (m), WRAPPED] (LINE:54))
 A[MD:(com.google.firebase.FirebaseApp, com.google.firebase.inject.Provider<com.google.firebase.heartbeatinfo.HeartBeatController>, java.util.concurrent.ExecutorService, java.util.concurrent.Executor):void (m)] (LINE:50) call: com.google.firebase.installations.FirebaseInstallations.<init>(com.google.firebase.FirebaseApp, com.google.firebase.inject.Provider, java.util.concurrent.ExecutorService, java.util.concurrent.Executor):void type: CONSTRUCTOR */
    static /* synthetic */ FirebaseInstallationsApi lambda$getComponents$0(ComponentContainer componentContainer) {
        return new FirebaseInstallations((FirebaseApp) componentContainer.get(FirebaseApp.class), componentContainer.getProvider(HeartBeatController.class), (ExecutorService) componentContainer.get(Qualified.qualified(Background.class, ExecutorService.class)), FirebaseExecutors.newSequentialExecutor((Executor) componentContainer.get(Qualified.qualified(Blocking.class, Executor.class))));
    }
}

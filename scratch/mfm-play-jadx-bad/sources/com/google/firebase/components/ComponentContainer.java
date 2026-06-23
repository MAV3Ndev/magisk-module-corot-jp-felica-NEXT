package com.google.firebase.components;

import com.google.firebase.inject.Deferred;
import com.google.firebase.inject.Provider;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public interface ComponentContainer {
    <T> T get(Qualified<T> qualified);

    <T> T get(Class<T> cls);

    <T> Deferred<T> getDeferred(Qualified<T> qualified);

    <T> Deferred<T> getDeferred(Class<T> cls);

    <T> Provider<T> getProvider(Qualified<T> qualified);

    <T> Provider<T> getProvider(Class<T> cls);

    <T> Set<T> setOf(Qualified<T> qualified);

    <T> Set<T> setOf(Class<T> cls);

    <T> Provider<Set<T>> setOfProvider(Qualified<T> qualified);

    <T> Provider<Set<T>> setOfProvider(Class<T> cls);

    /* JADX INFO: renamed from: com.google.firebase.components.ComponentContainer$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static Object $default$get(ComponentContainer _this, Qualified qualified) {
            Provider provider = _this.getProvider(qualified);
            if (provider == null) {
                return null;
            }
            return provider.get();
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0008: CHECK_CAST (java.util.Set) (wrap:java.lang.Object:0x0004: INVOKE 
  (wrap:com.google.firebase.inject.Provider:0x0000: INVOKE 
  (r0v0 '_this' com.google.firebase.components.ComponentContainer A[D('_this' com.google.firebase.components.ComponentContainer)])
  (r1v0 com.google.firebase.components.Qualified)
 INTERFACE call: com.google.firebase.components.ComponentContainer.setOfProvider(com.google.firebase.components.Qualified):com.google.firebase.inject.Provider A[MD:<T>:(com.google.firebase.components.Qualified<T>):com.google.firebase.inject.Provider<java.util.Set<T>> (m), WRAPPED] (LINE:56))
 INTERFACE call: com.google.firebase.inject.Provider.get():java.lang.Object A[MD:():T (m), WRAPPED] (LINE:56)) */
        public static Set $default$setOf(ComponentContainer _this, Qualified qualified) {
            return (Set) _this.setOfProvider(qualified).get();
        }
    }
}

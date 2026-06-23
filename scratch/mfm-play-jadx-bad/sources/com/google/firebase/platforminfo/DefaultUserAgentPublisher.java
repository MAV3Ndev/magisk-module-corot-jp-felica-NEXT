package com.google.firebase.platforminfo;

import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.firebase.components.Dependency;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultUserAgentPublisher implements UserAgentPublisher {
    private final GlobalLibraryVersionRegistrar gamesSDKRegistrar;
    private final String javaSDKVersionUserAgent;

    DefaultUserAgentPublisher(Set<LibraryVersion> set, GlobalLibraryVersionRegistrar globalLibraryVersionRegistrar) {
        this.javaSDKVersionUserAgent = toUserAgent(set);
        this.gamesSDKRegistrar = globalLibraryVersionRegistrar;
    }

    @Override // com.google.firebase.platforminfo.UserAgentPublisher
    public String getUserAgent() {
        if (this.gamesSDKRegistrar.getRegisteredVersions().isEmpty()) {
            return this.javaSDKVersionUserAgent;
        }
        return this.javaSDKVersionUserAgent + ' ' + toUserAgent(this.gamesSDKRegistrar.getRegisteredVersions());
    }

    private static String toUserAgent(Set<LibraryVersion> set) {
        StringBuilder sb = new StringBuilder();
        Iterator<LibraryVersion> it = set.iterator();
        while (it.hasNext()) {
            LibraryVersion next = it.next();
            sb.append(next.getLibraryName());
            sb.append('/');
            sb.append(next.getVersion());
            if (it.hasNext()) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public static Component<UserAgentPublisher> component() {
        return Component.builder(UserAgentPublisher.class).add(Dependency.setOf((Class<?>) LibraryVersion.class)).factory(new ComponentFactory() { // from class: com.google.firebase.platforminfo.DefaultUserAgentPublisher$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.google.firebase.components.ComponentFactory
            public final Object create(ComponentContainer componentContainer) {
                return DefaultUserAgentPublisher.lambda$component$0(componentContainer);
            }
        }).build();
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x000c: CONSTRUCTOR 
  (wrap:java.util.Set:0x0004: INVOKE 
  (r2v0 com.google.firebase.components.ComponentContainer)
  (wrap:java.lang.Class:0x0002: CONST_CLASS  A[WRAPPED] com.google.firebase.platforminfo.LibraryVersion.class)
 INTERFACE call: com.google.firebase.components.ComponentContainer.setOf(java.lang.Class):java.util.Set A[MD:<T>:(java.lang.Class<T>):java.util.Set<T> (m), WRAPPED] (LINE:73))
  (wrap:com.google.firebase.platforminfo.GlobalLibraryVersionRegistrar:0x0008: INVOKE  STATIC call: com.google.firebase.platforminfo.GlobalLibraryVersionRegistrar.getInstance():com.google.firebase.platforminfo.GlobalLibraryVersionRegistrar A[MD:():com.google.firebase.platforminfo.GlobalLibraryVersionRegistrar (m), WRAPPED])
 A[MD:(java.util.Set<com.google.firebase.platforminfo.LibraryVersion>, com.google.firebase.platforminfo.GlobalLibraryVersionRegistrar):void (m)] (LINE:72) call: com.google.firebase.platforminfo.DefaultUserAgentPublisher.<init>(java.util.Set, com.google.firebase.platforminfo.GlobalLibraryVersionRegistrar):void type: CONSTRUCTOR */
    static /* synthetic */ UserAgentPublisher lambda$component$0(ComponentContainer componentContainer) {
        return new DefaultUserAgentPublisher(componentContainer.setOf(LibraryVersion.class), GlobalLibraryVersionRegistrar.getInstance());
    }
}

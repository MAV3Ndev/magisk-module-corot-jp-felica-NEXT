package androidx.credentials;

import android.content.ComponentName;
import android.os.Bundle;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GetCustomCredentialOption.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001BA\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\rBK\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010BK\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0011¨\u0006\u0012"}, d2 = {"Landroidx/credentials/GetCustomCredentialOption;", "Landroidx/credentials/CredentialOption;", "type", "", "requestData", "Landroid/os/Bundle;", "candidateQueryData", "isSystemProviderRequired", "", "isAutoSelectAllowed", "allowedProviders", "", "Landroid/content/ComponentName;", "(Ljava/lang/String;Landroid/os/Bundle;Landroid/os/Bundle;ZZLjava/util/Set;)V", "typePriorityHint", "", "(Ljava/lang/String;Landroid/os/Bundle;Landroid/os/Bundle;ZZLjava/util/Set;I)V", "(Landroid/os/Bundle;Ljava/lang/String;Landroid/os/Bundle;ZZLjava/util/Set;I)V", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public class GetCustomCredentialOption extends CredentialOption {
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GetCustomCredentialOption(String type, Bundle requestData, Bundle candidateQueryData, boolean z) {
        this(type, requestData, candidateQueryData, z, false, null, 48, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(requestData, "requestData");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GetCustomCredentialOption(String type, Bundle requestData, Bundle candidateQueryData, boolean z, boolean z2) {
        this(type, requestData, candidateQueryData, z, z2, null, 32, null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(requestData, "requestData");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x001d: CONSTRUCTOR 
  (r9v0 android.os.Bundle)
  (r10v0 java.lang.String)
  (r11v0 android.os.Bundle)
  (r12v0 boolean)
  (wrap:boolean:0x0005: TERNARY null = ((wrap:int:0x0000: ARITH (r16v0 int) & (16 int) A[WRAPPED]) != (0 int)) ? false : (r13v0 boolean))
  (wrap:java.util.Set<android.content.ComponentName>:?: CAST (java.util.Set<android.content.ComponentName>) (wrap:java.util.Set:0x000e: TERNARY null = ((wrap:int:0x0006: ARITH (r16v0 int) & (32 int) A[WRAPPED]) != (0 int)) ? (wrap:java.util.Set:0x000a: INVOKE  STATIC call: kotlin.collections.SetsKt.emptySet():java.util.Set A[MD:<T>:():java.util.Set<T> (m), WRAPPED] (LINE:73)) : (r14v0 java.util.Set)))
  (wrap:int:?: TERNARY null = ((wrap:int:0x000f: ARITH (r16v0 int) & (64 int) A[WRAPPED]) != (0 int)) ? (2000 int) : (r15v0 int))
 A[MD:(android.os.Bundle, java.lang.String, android.os.Bundle, boolean, boolean, java.util.Set<android.content.ComponentName>, int):void (m)] (LINE:67) call: androidx.credentials.GetCustomCredentialOption.<init>(android.os.Bundle, java.lang.String, android.os.Bundle, boolean, boolean, java.util.Set, int):void type: THIS */
    public /* synthetic */ GetCustomCredentialOption(Bundle bundle, String str, Bundle bundle2, boolean z, boolean z2, Set set, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(bundle, str, bundle2, z, (i2 & 16) != 0 ? false : z2, (Set<ComponentName>) ((i2 & 32) != 0 ? SetsKt.emptySet() : set), (i2 & 64) != 0 ? 2000 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GetCustomCredentialOption(Bundle requestData, String type, Bundle candidateQueryData, boolean z, boolean z2, Set<ComponentName> allowedProviders, int i) {
        super(type, requestData, candidateQueryData, z, z2, allowedProviders, i);
        Intrinsics.checkNotNullParameter(requestData, "requestData");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
        Intrinsics.checkNotNullParameter(allowedProviders, "allowedProviders");
        if (type.length() <= 0) {
            throw new IllegalArgumentException("type should not be empty".toString());
        }
        if (i == 100) {
            throw new IllegalArgumentException("Custom types should not have passkey level priority.".toString());
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0014: CONSTRUCTOR 
  (r8v0 java.lang.String)
  (r9v0 android.os.Bundle)
  (r10v0 android.os.Bundle)
  (r11v0 boolean)
  (wrap:boolean:0x0005: TERNARY null = ((wrap:int:0x0000: ARITH (r14v0 int) & (16 int) A[WRAPPED]) != (0 int)) ? false : (r12v0 boolean))
  (wrap:java.util.Set:?: TERNARY null = ((wrap:int:0x0006: ARITH (r14v0 int) & (32 int) A[WRAPPED]) != (0 int)) ? (wrap:java.util.Set:0x000a: INVOKE  STATIC call: kotlin.collections.SetsKt.emptySet():java.util.Set A[MD:<T>:():java.util.Set<T> (m), WRAPPED] (LINE:132)) : (r13v0 java.util.Set))
 A[MD:(java.lang.String, android.os.Bundle, android.os.Bundle, boolean, boolean, java.util.Set<android.content.ComponentName>):void (m)] (LINE:126) call: androidx.credentials.GetCustomCredentialOption.<init>(java.lang.String, android.os.Bundle, android.os.Bundle, boolean, boolean, java.util.Set):void type: THIS */
    public /* synthetic */ GetCustomCredentialOption(String str, Bundle bundle, Bundle bundle2, boolean z, boolean z2, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, bundle, bundle2, z, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? SetsKt.emptySet() : set);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GetCustomCredentialOption(String type, Bundle requestData, Bundle candidateQueryData, boolean z, boolean z2, Set<ComponentName> allowedProviders) {
        this(requestData, type, candidateQueryData, z, z2, allowedProviders, 0, 64, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(requestData, "requestData");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
        Intrinsics.checkNotNullParameter(allowedProviders, "allowedProviders");
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x001d: CONSTRUCTOR 
  (r9v0 java.lang.String)
  (r10v0 android.os.Bundle)
  (r11v0 android.os.Bundle)
  (r12v0 boolean)
  (wrap:boolean:0x0005: TERNARY null = ((wrap:int:0x0000: ARITH (r16v0 int) & (16 int) A[WRAPPED]) != (0 int)) ? false : (r13v0 boolean))
  (wrap:java.util.Set<android.content.ComponentName>:?: CAST (java.util.Set<android.content.ComponentName>) (wrap:java.util.Set:0x000e: TERNARY null = ((wrap:int:0x0006: ARITH (r16v0 int) & (32 int) A[WRAPPED]) != (0 int)) ? (wrap:java.util.Set:0x000a: INVOKE  STATIC call: kotlin.collections.SetsKt.emptySet():java.util.Set A[MD:<T>:():java.util.Set<T> (m), WRAPPED] (LINE:191)) : (r14v0 java.util.Set)))
  (wrap:int:?: TERNARY null = ((wrap:int:0x000f: ARITH (r16v0 int) & (64 int) A[WRAPPED]) != (0 int)) ? (2000 int) : (r15v0 int))
 A[MD:(java.lang.String, android.os.Bundle, android.os.Bundle, boolean, boolean, java.util.Set<android.content.ComponentName>, int):void (m)] (LINE:185) call: androidx.credentials.GetCustomCredentialOption.<init>(java.lang.String, android.os.Bundle, android.os.Bundle, boolean, boolean, java.util.Set, int):void type: THIS */
    public /* synthetic */ GetCustomCredentialOption(String str, Bundle bundle, Bundle bundle2, boolean z, boolean z2, Set set, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, bundle, bundle2, z, (i2 & 16) != 0 ? false : z2, (Set<ComponentName>) ((i2 & 32) != 0 ? SetsKt.emptySet() : set), (i2 & 64) != 0 ? 2000 : i);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public GetCustomCredentialOption(String type, Bundle requestData, Bundle candidateQueryData, boolean z, boolean z2, Set<ComponentName> allowedProviders, int i) {
        this(requestData, type, candidateQueryData, z, z2, allowedProviders, i);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(requestData, "requestData");
        Intrinsics.checkNotNullParameter(candidateQueryData, "candidateQueryData");
        Intrinsics.checkNotNullParameter(allowedProviders, "allowedProviders");
    }
}

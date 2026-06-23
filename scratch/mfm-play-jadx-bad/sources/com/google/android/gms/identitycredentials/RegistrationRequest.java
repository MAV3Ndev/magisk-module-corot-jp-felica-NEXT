package com.google.android.gms.identitycredentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-identity-credentials@@16.0.0-alpha02 */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dBA\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0003\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0003\u0010\u0007\u001a\u00020\u0006\u0012\u000e\b\u0003\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t¢\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0007\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0010R\"\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t8\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0014\u0010\u0012\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001e"}, d2 = {"Lcom/google/android/gms/identitycredentials/RegistrationRequest;", "Lcom/google/android/gms/common/internal/safeparcel/AbstractSafeParcelable;", "credentials", "", "matcher", "type", "", "requestType", "protocolTypes", "", "<init>", "([B[BLjava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getCredentials", "()[B", "getMatcher", "getType", "()Ljava/lang/String;", "getRequestType$annotations", "()V", "getRequestType", "getProtocolTypes$annotations", "getProtocolTypes", "()Ljava/util/List;", "writeToParcel", "", "dest", "Landroid/os/Parcel;", "flags", "", "Companion", "java.com.google.android.gmscore.integ.client.identity_credentials_identity_credentials"}, k = 1, mv = {2, 0, 0}, xi = 48)
@SafeParcelable.Class(creator = "RegistrationRequestCreator")
public final class RegistrationRequest extends AbstractSafeParcelable {
    public static final String TAG = "RegistrationRequest";

    @SafeParcelable.Field(getter = "getCredentials", id = 1)
    private final byte[] credentials;

    @SafeParcelable.Field(getter = "getMatcher", id = 2)
    private final byte[] matcher;

    @SafeParcelable.Field(defaultValueUnchecked = "java.util.Collections.emptyList()", getter = "getProtocolTypes", id = 5)
    private final List<String> protocolTypes;

    @SafeParcelable.Field(defaultValue = "", getter = "getRequestType", id = 4)
    private final String requestType;

    @SafeParcelable.Field(defaultValue = "", getter = "getType", id = 3)
    private final String type;
    public static final Parcelable.Creator<RegistrationRequest> CREATOR = new RegistrationRequestCreator();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0063  */
    @SafeParcelable.Constructor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public RegistrationRequest(@SafeParcelable.Param(id = 1) byte[] credentials, @SafeParcelable.Param(id = 2) byte[] matcher, @SafeParcelable.Param(id = 3) String type, @SafeParcelable.Param(id = 4) String requestType, @SafeParcelable.Param(id = 5) List<String> protocolTypes) {
        boolean z;
        Intrinsics.checkNotNullParameter(credentials, "credentials");
        Intrinsics.checkNotNullParameter(matcher, "matcher");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(requestType, "requestType");
        Intrinsics.checkNotNullParameter(protocolTypes, "protocolTypes");
        this.credentials = credentials;
        this.matcher = matcher;
        this.type = type;
        this.requestType = requestType;
        this.protocolTypes = protocolTypes;
        if (StringsKt.isBlank(requestType) || protocolTypes.isEmpty()) {
            z = false;
        } else {
            List<String> list = protocolTypes;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (!StringsKt.isBlank((String) it.next())) {
                        z = true;
                        break;
                    }
                }
                z = false;
            }
        }
        boolean z2 = !StringsKt.isBlank(this.type) && this.requestType.length() == 0 && this.protocolTypes.isEmpty();
        if (z || z2) {
            return;
        }
        throw new IllegalArgumentException("Either type: " + this.type + ", or requestType: " + this.requestType + " and protocolTypes: " + this.protocolTypes + " must be specified, but all were blank, or for protocolTypes, empty or full of blank elements.");
    }

    public final byte[] getCredentials() {
        return this.credentials;
    }

    public final byte[] getMatcher() {
        return this.matcher;
    }

    public final List<String> getProtocolTypes() {
        return this.protocolTypes;
    }

    public final String getRequestType() {
        return this.requestType;
    }

    public final String getType() {
        return this.type;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        RegistrationRequestCreator.writeToParcel(this, dest, flags);
    }
}

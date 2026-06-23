package com.google.android.gms.fido.fido2.api.common;

/* JADX INFO: compiled from: com.google.android.gms:play-services-fido@@21.0.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzbc extends Exception {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public zzbc(String str) {
        super(String.format("User verification requirement %s not supported", str));
    }
}

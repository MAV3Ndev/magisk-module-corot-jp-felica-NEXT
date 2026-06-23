package com.google.android.gms.common;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class GooglePlayServicesNotAvailableException extends Exception {
    public final int errorCode;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public GooglePlayServicesNotAvailableException(int i) {
        this.errorCode = i;
    }
}

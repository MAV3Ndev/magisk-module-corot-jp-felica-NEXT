package com.google.android.gms.internal.p000authapi;

import android.util.Base64;
import java.security.SecureRandom;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@21.3.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zbas {
    private static final SecureRandom zba = new SecureRandom();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static String zba() {
        byte[] bArr = new byte[16];
        zba.nextBytes(bArr);
        return Base64.encodeToString(bArr, 11);
    }
}

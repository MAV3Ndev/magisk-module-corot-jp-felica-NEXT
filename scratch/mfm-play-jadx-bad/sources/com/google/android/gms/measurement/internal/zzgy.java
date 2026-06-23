package com.google.android.gms.measurement.internal;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import com.google.android.gms.common.internal.Preconditions;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzgy extends zzor {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public zzgy(zzpf zzpfVar) {
        super(zzpfVar);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzb() {
        zzay();
        ConnectivityManager connectivityManager = (ConnectivityManager) this.zzu.zzaY().getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            try {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            } catch (SecurityException unused) {
            }
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override // com.google.android.gms.measurement.internal.zzor
    protected final boolean zzbb() {
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzc(String str, zzos zzosVar, com.google.android.gms.internal.measurement.zzib zzibVar, zzgv zzgvVar) {
        zzgy zzgyVar;
        String str2;
        URL url;
        byte[] bArrZzcc;
        zzg();
        zzay();
        try {
            url = new URI(zzosVar.zza()).toURL();
            this.zzg.zzp();
            bArrZzcc = zzibVar.zzcc();
            zzgyVar = this;
            str2 = str;
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException unused) {
            zzgyVar = this;
            str2 = str;
        }
        try {
            this.zzu.zzaW().zzm(new zzgx(zzgyVar, str2, url, bArrZzcc, zzosVar.zzb(), zzgvVar));
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException unused2) {
            zzgyVar.zzu.zzaV().zzb().zzc("Failed to parse URL. Not uploading MeasurementBatch. appId", zzgt.zzl(str2), zzosVar.zza());
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzd(zzh zzhVar, Map map, zzgv zzgvVar) {
        zzgy zzgyVar;
        URL url;
        zzg();
        zzay();
        Preconditions.checkNotNull(zzhVar);
        Preconditions.checkNotNull(zzgvVar);
        zzot zzotVarZzf = this.zzg.zzf();
        Uri.Builder builder = new Uri.Builder();
        String strZzf = zzhVar.zzf();
        Uri.Builder builderEncodedAuthority = builder.scheme((String) zzfx.zze.zzb(null)).encodedAuthority((String) zzfx.zzf.zzb(null));
        String.valueOf(strZzf);
        Uri.Builder builderAppendQueryParameter = builderEncodedAuthority.path("config/app/".concat(String.valueOf(strZzf))).appendQueryParameter("platform", "android");
        zzotVarZzf.zzu.zzc().zzi();
        builderAppendQueryParameter.appendQueryParameter("gmp_version", String.valueOf(130000L)).appendQueryParameter("runtime_version", "0");
        String string = builder.build().toString();
        try {
            url = new URI(string).toURL();
            zzgyVar = this;
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException unused) {
            zzgyVar = this;
        }
        try {
            this.zzu.zzaW().zzm(new zzgx(zzgyVar, zzhVar.zzc(), url, null, map, zzgvVar));
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException unused2) {
            zzgyVar.zzu.zzaV().zzb().zzc("Failed to parse config URL. Not fetching. appId", zzgt.zzl(zzhVar.zzc()), string);
        }
    }
}

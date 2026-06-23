package com.google.android.gms.internal.fido;

import com.felicanetworks.mfm.main.presenter.agent.TransitPassInfoAgent;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-fido@@21.0.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzdv {
    private static final zzdz zza = new zzds();
    private static final zzdy zzb = new zzdt();
    private final zzdz zze;
    private final Map zzc = new HashMap();
    private final Map zzd = new HashMap();
    private zzdy zzf = null;

    public final zzdv zza(zzdy zzdyVar) {
        this.zzf = zzdyVar;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzea zzd() {
        return new zzdx(this, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzg(zzdk zzdkVar) {
        zzfk.zza(zzdkVar, TransitPassInfoAgent.OPTIONAL_INFO_KEY);
        if (!zzdkVar.zzb()) {
            zzdz zzdzVar = zza;
            zzfk.zza(zzdkVar, TransitPassInfoAgent.OPTIONAL_INFO_KEY);
            this.zzd.remove(zzdkVar);
            this.zzc.put(zzdkVar, zzdzVar);
            return;
        }
        zzdy zzdyVar = zzb;
        zzfk.zza(zzdkVar, TransitPassInfoAgent.OPTIONAL_INFO_KEY);
        if (!zzdkVar.zzb()) {
            throw new IllegalArgumentException("key must be repeating");
        }
        this.zzc.remove(zzdkVar);
        this.zzd.put(zzdkVar, zzdyVar);
    }
}

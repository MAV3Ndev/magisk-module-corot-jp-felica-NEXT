package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.Objects;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzkx implements Application.ActivityLifecycleCallbacks, zzkv {
    final /* synthetic */ zzli zza;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzkx(zzli zzliVar) {
        Objects.requireNonNull(zzliVar);
        this.zza = zzliVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) throws Throwable {
        zza(com.google.android.gms.internal.measurement.zzdf.zza(activity), bundle);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        zzb(com.google.android.gms.internal.measurement.zzdf.zza(activity));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
        zzc(com.google.android.gms.internal.measurement.zzdf.zza(activity));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        zzd(com.google.android.gms.internal.measurement.zzdf.zza(activity));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        zze(com.google.android.gms.internal.measurement.zzdf.zza(activity), bundle);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0077  */
    @Override // com.google.android.gms.measurement.internal.zzkv
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zza(com.google.android.gms.internal.measurement.zzdf zzdfVar, Bundle bundle) throws Throwable {
        zzkx zzkxVar;
        zzib zzibVar;
        zzli zzliVar;
        zzib zzibVar2;
        Intent intent;
        Uri uri;
        try {
            zzliVar = this.zza;
            zzibVar2 = zzliVar.zzu;
            zzibVar2.zzaV().zzk().zza("onActivityCreated");
            intent = zzdfVar.zzc;
        } catch (RuntimeException e) {
            e = e;
            zzkxVar = this;
        } catch (Throwable th) {
            th = th;
            zzkxVar = this;
            zzkxVar.zza.zzu.zzs().zzm(zzdfVar, bundle);
            throw th;
        }
        if (intent != null) {
            Uri data = intent.getData();
            if (data == null || !data.isHierarchical()) {
                Bundle extras = intent.getExtras();
                uri = null;
                if (extras != null) {
                    String string = extras.getString("com.android.vending.referral_url");
                    if (!TextUtils.isEmpty(string)) {
                        data = Uri.parse(string);
                        uri = data;
                    }
                }
                if (uri != null && uri.isHierarchical()) {
                    zzibVar2.zzk();
                    String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
                    String str = (!"android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(stringExtra) || "https://www.google.com".equals(stringExtra) || "android-app://com.google.appcrawler".equals(stringExtra)) ? "gs" : DebugKt.DEBUG_PROPERTY_VALUE_AUTO;
                    String queryParameter = uri.getQueryParameter("referrer");
                    zzkxVar = this;
                    try {
                        try {
                            zzibVar2.zzaW().zzj(new zzkw(zzkxVar, bundle != null, uri, str, queryParameter));
                        } catch (RuntimeException e2) {
                            e = e2;
                            zzkxVar.zza.zzu.zzaV().zzb().zzb("Throwable caught in onActivityCreated", e);
                        }
                        zzibVar = zzkxVar.zza.zzu;
                    } catch (Throwable th2) {
                        th = th2;
                        zzkxVar.zza.zzu.zzs().zzm(zzdfVar, bundle);
                        throw th;
                    }
                }
            } else {
                uri = data;
                if (uri != null) {
                    zzibVar2.zzk();
                    String stringExtra2 = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
                    if ("android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(stringExtra2)) {
                        String str2 = (!"android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(stringExtra2) || "https://www.google.com".equals(stringExtra2) || "android-app://com.google.appcrawler".equals(stringExtra2)) ? "gs" : DebugKt.DEBUG_PROPERTY_VALUE_AUTO;
                        String queryParameter2 = uri.getQueryParameter("referrer");
                        zzkxVar = this;
                        zzibVar2.zzaW().zzj(new zzkw(zzkxVar, bundle != null, uri, str2, queryParameter2));
                        zzibVar = zzkxVar.zza.zzu;
                    }
                }
            }
            zzibVar.zzs().zzm(zzdfVar, bundle);
        }
        zzibVar = zzliVar.zzu;
        zzibVar.zzs().zzm(zzdfVar, bundle);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzkv
    public final void zzb(com.google.android.gms.internal.measurement.zzdf zzdfVar) {
        this.zza.zzu.zzs().zzs(zzdfVar);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzkv
    public final void zzc(com.google.android.gms.internal.measurement.zzdf zzdfVar) {
        zzib zzibVar = this.zza.zzu;
        zzibVar.zzs().zzp(zzdfVar);
        zzob zzobVarZzh = zzibVar.zzh();
        zzib zzibVar2 = zzobVarZzh.zzu;
        zzibVar2.zzaW().zzj(new zznu(zzobVarZzh, zzibVar2.zzaZ().elapsedRealtime()));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzkv
    public final void zzd(com.google.android.gms.internal.measurement.zzdf zzdfVar) {
        zzib zzibVar = this.zza.zzu;
        zzob zzobVarZzh = zzibVar.zzh();
        zzib zzibVar2 = zzobVarZzh.zzu;
        zzibVar2.zzaW().zzj(new zznt(zzobVarZzh, zzibVar2.zzaZ().elapsedRealtime()));
        zzibVar.zzs().zzn(zzdfVar);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzkv
    public final void zze(com.google.android.gms.internal.measurement.zzdf zzdfVar, Bundle bundle) {
        this.zza.zzu.zzs().zzq(zzdfVar, bundle);
    }
}

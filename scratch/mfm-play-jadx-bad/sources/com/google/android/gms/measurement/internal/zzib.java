package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.app.BroadcastOptions;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzql;
import com.google.firebase.messaging.Constants;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import kotlinx.coroutines.DebugKt;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzib implements zzjf {
    private static volatile zzib zzb;
    private volatile Boolean zzA;
    private volatile boolean zzB;
    private int zzC;
    private int zzD;
    final long zza;
    private final Context zzc;
    private final boolean zzd;
    private final zzae zze;
    private final zzal zzf;
    private final zzhg zzg;
    private final zzgt zzh;
    private final zzhy zzi;
    private final zzob zzj;
    private final zzpo zzk;
    private final zzgm zzl;
    private final Clock zzm;
    private final zzma zzn;
    private final zzli zzo;
    private final zzd zzp;
    private final zzln zzq;
    private final String zzr;
    private zzgk zzs;
    private zznk zzt;
    private zzba zzu;
    private zzgh zzv;
    private zzlp zzw;
    private Boolean zzy;
    private long zzz;
    private boolean zzx = false;
    private final AtomicInteger zzE = new AtomicInteger(0);

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzib(zzjr zzjrVar) {
        Preconditions.checkNotNull(zzjrVar);
        Context context = zzjrVar.zza;
        zzae zzaeVar = new zzae(context);
        this.zze = zzaeVar;
        zzfr.zza = zzaeVar;
        this.zzc = context;
        this.zzd = zzjrVar.zze;
        this.zzA = zzjrVar.zzb;
        this.zzr = zzjrVar.zzg;
        this.zzB = true;
        com.google.android.gms.internal.measurement.zzkl.zzb(context);
        Clock defaultClock = DefaultClock.getInstance();
        this.zzm = defaultClock;
        Long l = zzjrVar.zzf;
        this.zza = l != null ? l.longValue() : defaultClock.currentTimeMillis();
        this.zzf = new zzal(this);
        zzhg zzhgVar = new zzhg(this);
        zzhgVar.zzx();
        this.zzg = zzhgVar;
        zzgt zzgtVar = new zzgt(this);
        zzgtVar.zzx();
        this.zzh = zzgtVar;
        zzpo zzpoVar = new zzpo(this);
        zzpoVar.zzx();
        this.zzk = zzpoVar;
        this.zzl = new zzgm(new zzjq(zzjrVar, this));
        this.zzp = new zzd(this);
        zzma zzmaVar = new zzma(this);
        zzmaVar.zzc();
        this.zzn = zzmaVar;
        zzli zzliVar = new zzli(this);
        zzliVar.zzc();
        this.zzo = zzliVar;
        zzob zzobVar = new zzob(this);
        zzobVar.zzc();
        this.zzj = zzobVar;
        zzln zzlnVar = new zzln(this);
        zzlnVar.zzx();
        this.zzq = zzlnVar;
        zzhy zzhyVar = new zzhy(this);
        zzhyVar.zzx();
        this.zzi = zzhyVar;
        com.google.android.gms.internal.measurement.zzdd zzddVar = zzjrVar.zzd;
        boolean z = zzddVar == null || zzddVar.zzb == 0;
        if (context.getApplicationContext() instanceof Application) {
            zzO(zzliVar);
            if (zzliVar.zzu.zzc.getApplicationContext() instanceof Application) {
                Application application = (Application) zzliVar.zzu.zzc.getApplicationContext();
                if (zzliVar.zza == null) {
                    zzliVar.zza = new zzkx(zzliVar);
                }
                if (z) {
                    application.unregisterActivityLifecycleCallbacks(zzliVar.zza);
                    application.registerActivityLifecycleCallbacks(zzliVar.zza);
                    zzgt zzgtVar2 = zzliVar.zzu.zzh;
                    zzP(zzgtVar2);
                    zzgtVar2.zzk().zza("Registered activity lifecycle callback");
                }
            }
        } else {
            zzP(zzgtVar);
            zzgtVar.zze().zza("Application context is not an Application");
        }
        zzhyVar.zzj(new zzhz(this, zzjrVar));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static final void zzL() {
        throw new IllegalStateException("Unexpected call on client side");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static final void zzM(zzf zzfVar) {
        if (zzfVar == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static final void zzN(zzjd zzjdVar) {
        if (zzjdVar == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static final void zzO(zzg zzgVar) {
        if (zzgVar == null) {
            throw new IllegalStateException("Component not created");
        }
        if (zzgVar.zza()) {
            return;
        }
        String strValueOf = String.valueOf(zzgVar.getClass());
        String.valueOf(strValueOf);
        throw new IllegalStateException("Component not initialized: ".concat(String.valueOf(strValueOf)));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static final void zzP(zzje zzjeVar) {
        if (zzjeVar == null) {
            throw new IllegalStateException("Component not created");
        }
        if (zzjeVar.zzv()) {
            return;
        }
        String strValueOf = String.valueOf(zzjeVar.getClass());
        String.valueOf(strValueOf);
        throw new IllegalStateException("Component not initialized: ".concat(String.valueOf(strValueOf)));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static zzib zzy(Context context, com.google.android.gms.internal.measurement.zzdd zzddVar, Long l) {
        Bundle bundle;
        if (zzddVar != null) {
            Bundle bundle2 = zzddVar.zzd;
            zzddVar = new com.google.android.gms.internal.measurement.zzdd(zzddVar.zza, zzddVar.zzb, zzddVar.zzc, bundle2, null);
        }
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzb == null) {
            synchronized (zzib.class) {
                if (zzb == null) {
                    zzb = new zzib(new zzjr(context, zzddVar, l));
                }
            }
        } else if (zzddVar != null && (bundle = zzddVar.zzd) != null && bundle.containsKey("dataCollectionDefaultEnabled")) {
            Preconditions.checkNotNull(zzb);
            zzb.zzA = Boolean.valueOf(bundle.getBoolean("dataCollectionDefaultEnabled"));
        }
        Preconditions.checkNotNull(zzb);
        return zzb;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzA() {
        return this.zzA != null && this.zzA.booleanValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzB() {
        return zzC() == 0;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int zzC() {
        zzhy zzhyVar = this.zzi;
        zzP(zzhyVar);
        zzhyVar.zzg();
        zzal zzalVar = this.zzf;
        if (zzalVar.zzt()) {
            return 1;
        }
        zzP(zzhyVar);
        zzhyVar.zzg();
        if (!this.zzB) {
            return 8;
        }
        zzhg zzhgVar = this.zzg;
        zzN(zzhgVar);
        Boolean boolZzi = zzhgVar.zzi();
        if (boolZzi != null) {
            return boolZzi.booleanValue() ? 0 : 3;
        }
        zzae zzaeVar = zzalVar.zzu.zze;
        Boolean boolZzr = zzalVar.zzr("firebase_analytics_collection_enabled");
        return boolZzr != null ? boolZzr.booleanValue() ? 0 : 4 : (this.zzA == null || this.zzA.booleanValue()) ? 0 : 7;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzD(boolean z) {
        zzhy zzhyVar = this.zzi;
        zzP(zzhyVar);
        zzhyVar.zzg();
        this.zzB = z;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzE() {
        zzhy zzhyVar = this.zzi;
        zzP(zzhyVar);
        zzhyVar.zzg();
        return this.zzB;
    }

    final void zzF() {
        this.zzC++;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzG() {
        this.zzE.incrementAndGet();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final boolean zzH() {
        if (!this.zzx) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
        zzhy zzhyVar = this.zzi;
        zzP(zzhyVar);
        zzhyVar.zzg();
        Boolean bool = this.zzy;
        if (bool == null || this.zzz == 0 || (!bool.booleanValue() && Math.abs(this.zzm.elapsedRealtime() - this.zzz) > 1000)) {
            this.zzz = this.zzm.elapsedRealtime();
            zzpo zzpoVar = this.zzk;
            zzN(zzpoVar);
            boolean z = false;
            if (zzpoVar.zzY("android.permission.INTERNET")) {
                zzN(zzpoVar);
                if (zzpoVar.zzY("android.permission.ACCESS_NETWORK_STATE")) {
                    Context context = this.zzc;
                    if (Wrappers.packageManager(context).isCallerInstantApp() || this.zzf.zzE() || (zzpo.zzau(context) && zzpo.zzQ(context, false))) {
                        z = true;
                    }
                }
            }
            Boolean boolValueOf = Boolean.valueOf(z);
            this.zzy = boolValueOf;
            if (boolValueOf.booleanValue()) {
                zzN(zzpoVar);
                this.zzy = Boolean.valueOf(zzpoVar.zzA(zzv().zzk()));
            }
        }
        return this.zzy.booleanValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean zzI() {
        NetworkInfo activeNetworkInfo;
        zzhy zzhyVar = this.zzi;
        zzP(zzhyVar);
        zzhyVar.zzg();
        zzln zzlnVar = this.zzq;
        zzP(zzlnVar);
        zzP(zzlnVar);
        String strZzj = zzv().zzj();
        if (!this.zzf.zzu()) {
            zzgt zzgtVar = this.zzh;
            zzP(zzgtVar);
            zzgtVar.zzk().zza("ADID collection is disabled from Manifest. Skipping");
            return false;
        }
        zzhg zzhgVar = this.zzg;
        zzN(zzhgVar);
        Pair pairZzb = zzhgVar.zzb(strZzj);
        if (((Boolean) pairZzb.second).booleanValue() || TextUtils.isEmpty((CharSequence) pairZzb.first)) {
            zzgt zzgtVar2 = this.zzh;
            zzP(zzgtVar2);
            zzgtVar2.zzk().zza("ADID unavailable to retrieve Deferred Deep Link. Skipping");
            return false;
        }
        zzP(zzlnVar);
        zzlnVar.zzw();
        ConnectivityManager connectivityManager = (ConnectivityManager) zzlnVar.zzu.zzc.getSystemService("connectivity");
        if (connectivityManager != null) {
            try {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            } catch (SecurityException unused) {
                activeNetworkInfo = null;
            }
        } else {
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            zzgt zzgtVar3 = this.zzh;
            zzP(zzgtVar3);
            zzgtVar3.zze().zza("Network is not available for Deferred Deep Link request. Skipping");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        zznk zznkVarZzt = zzt();
        zznkVarZzt.zzg();
        zznkVarZzt.zzb();
        if (zznkVarZzt.zzK()) {
            zzpo zzpoVar = zznkVarZzt.zzu.zzk;
            zzN(zzpoVar);
            if (zzpoVar.zzah() >= 234200) {
            }
        } else {
            zzli zzliVar = this.zzo;
            zzO(zzliVar);
            zzib zzibVar = zzliVar.zzu;
            zzliVar.zzg();
            zzao zzaoVarZzz = zzibVar.zzt().zzz();
            Bundle bundle = zzaoVarZzz != null ? zzaoVarZzz.zza : null;
            if (bundle == null) {
                int i = this.zzD;
                this.zzD = i + 1;
                boolean z = i < 10;
                zzgt zzgtVar4 = this.zzh;
                zzP(zzgtVar4);
                String str = i < 10 ? "Retrying." : "Skipping.";
                zzgr zzgrVarZzj = zzgtVar4.zzj();
                StringBuilder sb2 = new StringBuilder(str.length() + 60);
                sb2.append("Failed to retrieve DMA consent from the service, ");
                sb2.append(str);
                sb2.append(" retryCount");
                zzgrVarZzj.zzb(sb2.toString(), Integer.valueOf(this.zzD));
                return z;
            }
            zzjk zzjkVarZze = zzjk.zze(bundle, 100);
            sb.append("&gcs=");
            sb.append(zzjkVarZze.zzk());
            zzaz zzazVarZzh = zzaz.zzh(bundle, 100);
            sb.append("&dma=");
            sb.append(!Objects.equals(zzazVarZzh.zzj(), false) ? 1 : 0);
            if (!TextUtils.isEmpty(zzazVarZzh.zzk())) {
                sb.append("&dma_cps=");
                sb.append(zzazVarZzh.zzk());
            }
            int i2 = !Objects.equals(zzaz.zzi(bundle), true) ? 1 : 0;
            sb.append("&npa=");
            sb.append(i2);
            zzgt zzgtVar5 = this.zzh;
            zzP(zzgtVar5);
            zzgtVar5.zzk().zzb("Consent query parameters to Bow", sb);
        }
        zzpo zzpoVar2 = this.zzk;
        zzN(zzpoVar2);
        zzv().zzu.zzf.zzi();
        String str2 = (String) pairZzb.first;
        zzN(this.zzg);
        URL urlZzat = zzpoVar2.zzat(130000L, strZzj, str2, r1.zzp.zza() - 1, sb.toString());
        if (urlZzat != null) {
            zzln zzlnVar2 = this.zzq;
            zzP(zzlnVar2);
            zzlk zzlkVar = new zzlk() { // from class: com.google.android.gms.measurement.internal.zzia
                @Override // com.google.android.gms.measurement.internal.zzlk
                public final /* synthetic */ void zza(String str3, int i3, Throwable th, byte[] bArr, Map map) {
                    this.zza.zzJ(str3, i3, th, bArr, map);
                }
            };
            zzlnVar2.zzw();
            Preconditions.checkNotNull(urlZzat);
            Preconditions.checkNotNull(zzlkVar);
            zzhy zzhyVar2 = zzlnVar2.zzu.zzi;
            zzP(zzhyVar2);
            zzhyVar2.zzm(new zzlm(zzlnVar2, strZzj, urlZzat, null, null, zzlkVar));
        }
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzJ(String str, int i, Throwable th, byte[] bArr, Map map) {
        int i2;
        if (i != 200 && i != 204) {
            i2 = 304;
            if (i != 304) {
                i2 = i;
            }
            zzgt zzgtVar = this.zzh;
            zzP(zzgtVar);
            zzgtVar.zze().zzc("Network Request for Deferred Deep Link failed. response, exception", Integer.valueOf(i2), th);
        }
        i2 = i;
        if (th == null) {
            zzhg zzhgVar = this.zzg;
            zzN(zzhgVar);
            zzhgVar.zzo.zzb(true);
            if (bArr == null || bArr.length == 0) {
                zzgt zzgtVar2 = this.zzh;
                zzP(zzgtVar2);
                zzgtVar2.zzj().zza("Deferred Deep Link response empty.");
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(new String(bArr));
                String strOptString = jSONObject.optString("deeplink", "");
                if (TextUtils.isEmpty(strOptString)) {
                    zzgt zzgtVar3 = this.zzh;
                    zzP(zzgtVar3);
                    zzgtVar3.zzj().zza("Deferred Deep Link is empty.");
                    return;
                }
                String strOptString2 = jSONObject.optString("gclid", "");
                String strOptString3 = jSONObject.optString("gbraid", "");
                String strOptString4 = jSONObject.optString("gad_source", "");
                double dOptDouble = jSONObject.optDouble("timestamp", 0.0d);
                Bundle bundle = new Bundle();
                zzpo zzpoVar = this.zzk;
                zzN(zzpoVar);
                zzib zzibVar = zzpoVar.zzu;
                if (!TextUtils.isEmpty(strOptString)) {
                    Context context = zzibVar.zzc;
                    List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse(strOptString)), 0);
                    if (listQueryIntentActivities != null && !listQueryIntentActivities.isEmpty()) {
                        if (!TextUtils.isEmpty(strOptString3)) {
                            bundle.putString("gbraid", strOptString3);
                        }
                        if (!TextUtils.isEmpty(strOptString4)) {
                            bundle.putString("gad_source", strOptString4);
                        }
                        bundle.putString("gclid", strOptString2);
                        bundle.putString("_cis", "ddp");
                        this.zzo.zzF(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundle);
                        zzN(zzpoVar);
                        if (TextUtils.isEmpty(strOptString)) {
                            return;
                        }
                        try {
                            SharedPreferences.Editor editorEdit = context.getSharedPreferences("google.analytics.deferred.deeplink.prefs", 0).edit();
                            editorEdit.putString("deeplink", strOptString);
                            editorEdit.putLong("timestamp", Double.doubleToRawLongBits(dOptDouble));
                            if (editorEdit.commit()) {
                                Intent intent = new Intent("android.google.analytics.action.DEEPLINK_ACTION");
                                Context context2 = zzpoVar.zzu.zzc;
                                if (Build.VERSION.SDK_INT < 34) {
                                    context2.sendBroadcast(intent);
                                    return;
                                } else {
                                    context2.sendBroadcast(intent, null, BroadcastOptions.makeBasic().setShareIdentityEnabled(true).toBundle());
                                    return;
                                }
                            }
                            return;
                        } catch (RuntimeException e) {
                            zzgt zzgtVar4 = zzpoVar.zzu.zzh;
                            zzP(zzgtVar4);
                            zzgtVar4.zzb().zzb("Failed to persist Deferred Deep Link. exception", e);
                            return;
                        }
                    }
                }
                zzgt zzgtVar5 = this.zzh;
                zzP(zzgtVar5);
                zzgtVar5.zze().zzd("Deferred Deep Link validation failed. gclid, gbraid, deep link", strOptString2, strOptString3, strOptString);
                return;
            } catch (JSONException e2) {
                zzgt zzgtVar6 = this.zzh;
                zzP(zzgtVar6);
                zzgtVar6.zzb().zzb("Failed to parse the Deferred Deep Link response. exception", e2);
                return;
            }
        }
        zzgt zzgtVar7 = this.zzh;
        zzP(zzgtVar7);
        zzgtVar7.zze().zzc("Network Request for Deferred Deep Link failed. response, exception", Integer.valueOf(i2), th);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzK(zzjr zzjrVar) {
        zzhy zzhyVar = this.zzi;
        zzP(zzhyVar);
        zzhyVar.zzg();
        zzal zzalVar = this.zzf;
        zzalVar.zzb();
        zzba zzbaVar = new zzba(this);
        zzbaVar.zzx();
        this.zzu = zzbaVar;
        com.google.android.gms.internal.measurement.zzdd zzddVar = zzjrVar.zzd;
        zzgh zzghVar = new zzgh(this, zzjrVar.zzc, zzddVar == null ? 0L : zzddVar.zza);
        zzghVar.zzc();
        this.zzv = zzghVar;
        zzgk zzgkVar = new zzgk(this);
        zzgkVar.zzc();
        this.zzs = zzgkVar;
        zznk zznkVar = new zznk(this);
        zznkVar.zzc();
        this.zzt = zznkVar;
        zzpo zzpoVar = this.zzk;
        zzpoVar.zzy();
        this.zzg.zzy();
        this.zzv.zzd();
        zzlp zzlpVar = new zzlp(this);
        zzlpVar.zzc();
        this.zzw = zzlpVar;
        zzlpVar.zzd();
        zzgt zzgtVar = this.zzh;
        zzP(zzgtVar);
        zzgr zzgrVarZzi = zzgtVar.zzi();
        zzalVar.zzi();
        zzgrVarZzi.zzb("App measurement initialized, version", 130000L);
        zzP(zzgtVar);
        zzgtVar.zzi().zza("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        String strZzj = zzghVar.zzj();
        zzN(zzpoVar);
        if (zzpoVar.zzaa(strZzj, zzalVar.zzz())) {
            zzP(zzgtVar);
            zzgtVar.zzi().zza("Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.");
        } else {
            zzP(zzgtVar);
            zzgr zzgrVarZzi2 = zzgtVar.zzi();
            String.valueOf(strZzj);
            zzgrVarZzi2.zza("To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ".concat(String.valueOf(strZzj)));
        }
        zzP(zzgtVar);
        zzgtVar.zzj().zza("Debug-level message logging enabled");
        int i = this.zzC;
        AtomicInteger atomicInteger = this.zzE;
        if (i != atomicInteger.get()) {
            zzP(zzgtVar);
            zzgtVar.zzb().zzc("Not all components initialized", Integer.valueOf(this.zzC), Integer.valueOf(atomicInteger.get()));
        }
        this.zzx = true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected final void zza(com.google.android.gms.internal.measurement.zzdd zzddVar) {
        zzjk zzjkVarZza;
        Bundle bundle;
        zzhy zzhyVar = this.zzi;
        zzP(zzhyVar);
        zzhyVar.zzg();
        com.google.android.gms.internal.measurement.zzin zzinVarZzj = zzx().zzj();
        com.google.android.gms.internal.measurement.zzin zzinVar = com.google.android.gms.internal.measurement.zzin.CLIENT_UPLOAD_ELIGIBLE;
        zzql.zza();
        zzfw zzfwVar = zzfx.zzaQ;
        zzal zzalVar = this.zzf;
        boolean zZzp = zzalVar.zzp(null, zzfwVar);
        boolean z = zzinVarZzj == zzinVar;
        if (zZzp) {
            zzpo zzpoVar = this.zzk;
            zzN(zzpoVar);
            if (zzpoVar.zzS()) {
                zzpo zzpoVar2 = this.zzk;
                zzN(zzpoVar2);
                zzpoVar2.zzg();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.google.android.gms.measurement.TRIGGERS_AVAILABLE");
                intentFilter.addAction("com.google.android.gms.measurement.BATCHES_AVAILABLE");
                zzw zzwVar = new zzw(zzpoVar2.zzu);
                zzib zzibVar = zzpoVar2.zzu;
                ContextCompat.registerReceiver(zzibVar.zzc, zzwVar, intentFilter, 2);
                zzgt zzgtVar = zzibVar.zzh;
                zzP(zzgtVar);
                zzgtVar.zzj().zza("Registered app receiver");
                if (z) {
                }
            } else if (z) {
                z = true;
                zzpo zzpoVar22 = this.zzk;
                zzN(zzpoVar22);
                zzpoVar22.zzg();
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("com.google.android.gms.measurement.TRIGGERS_AVAILABLE");
                intentFilter2.addAction("com.google.android.gms.measurement.BATCHES_AVAILABLE");
                zzw zzwVar2 = new zzw(zzpoVar22.zzu);
                zzib zzibVar2 = zzpoVar22.zzu;
                ContextCompat.registerReceiver(zzibVar2.zzc, zzwVar2, intentFilter2, 2);
                zzgt zzgtVar2 = zzibVar2.zzh;
                zzP(zzgtVar2);
                zzgtVar2.zzj().zza("Registered app receiver");
                if (z) {
                    zzx().zzh(((Long) zzfx.zzB.zzb(null)).longValue());
                }
            }
        }
        zzhg zzhgVar = this.zzg;
        zzN(zzhgVar);
        zzjk zzjkVarZzl = zzhgVar.zzl();
        int iZzb = zzjkVarZzl.zzb();
        zzjh zzjhVarZzw = zzalVar.zzw("google_analytics_default_allow_ad_storage", false);
        zzjh zzjhVarZzw2 = zzalVar.zzw("google_analytics_default_allow_analytics_storage", false);
        zzjh zzjhVar = zzjh.UNINITIALIZED;
        if (zzjhVarZzw != zzjhVar || zzjhVarZzw2 != zzjhVar) {
            zzN(zzhgVar);
            if (zzhgVar.zzk(-10)) {
                zzjkVarZza = zzjk.zza(zzjhVarZzw, zzjhVarZzw2, -10);
            } else {
                if (!TextUtils.isEmpty(zzv().zzk()) && (iZzb == 0 || iZzb == 30 || iZzb == 10 || iZzb == 40)) {
                    zzli zzliVar = this.zzo;
                    zzO(zzliVar);
                    zzliVar.zzs(new zzjk(null, null, -10), false);
                }
                zzjkVarZza = null;
            }
        }
        if (zzjkVarZza != null) {
            zzli zzliVar2 = this.zzo;
            zzO(zzliVar2);
            zzliVar2.zzs(zzjkVarZza, true);
            zzjkVarZzl = zzjkVarZza;
        }
        zzli zzliVar3 = this.zzo;
        zzO(zzliVar3);
        zzliVar3.zzA(zzjkVarZzl);
        zzN(zzhgVar);
        int iZzb2 = zzhgVar.zzj().zzb();
        zzjh zzjhVarZzw3 = zzalVar.zzw("google_analytics_default_allow_ad_personalization_signals", true);
        if (zzjhVarZzw3 != zzjhVar) {
            zzgt zzgtVar3 = this.zzh;
            zzP(zzgtVar3);
            zzgtVar3.zzk().zzb("Default ad personalization consent from Manifest", zzjhVarZzw3);
        }
        zzjh zzjhVarZzw4 = zzalVar.zzw("google_analytics_default_allow_ad_user_data", true);
        if (zzjhVarZzw4 != zzjhVar && zzjk.zzu(-10, iZzb2)) {
            zzO(zzliVar3);
            zzliVar3.zzq(zzaz.zza(zzjhVarZzw4, -10), true);
        } else if (!TextUtils.isEmpty(zzv().zzk()) && (iZzb2 == 0 || iZzb2 == 30)) {
            zzO(zzliVar3);
            zzliVar3.zzq(new zzaz((Boolean) null, -10, (Boolean) null, (String) null), true);
        } else if (TextUtils.isEmpty(zzv().zzk()) && zzddVar != null && (bundle = zzddVar.zzd) != null && zzjk.zzu(30, iZzb2)) {
            zzaz zzazVarZzh = zzaz.zzh(bundle, 30);
            if (zzazVarZzh.zzd()) {
                zzO(zzliVar3);
                zzliVar3.zzq(zzazVarZzh, true);
            }
        }
        zzib zzibVar3 = zzalVar.zzu;
        Boolean boolZzr = zzalVar.zzr("google_analytics_tcf_data_enabled");
        if (boolZzr == null || boolZzr.booleanValue()) {
            zzgt zzgtVar4 = this.zzh;
            zzP(zzgtVar4);
            zzgtVar4.zzj().zza("TCF client enabled.");
            zzO(zzliVar3);
            zzliVar3.zzE();
            zzO(zzliVar3);
            zzliVar3.zzD();
        }
        zzN(zzhgVar);
        zzhd zzhdVar = zzhgVar.zzc;
        if (zzhdVar.zza() == 0) {
            zzgt zzgtVar5 = this.zzh;
            zzP(zzgtVar5);
            long j = this.zza;
            zzgtVar5.zzk().zzb("Persisting first open", Long.valueOf(j));
            zzN(zzhgVar);
            zzhdVar.zzb(j);
        }
        zzO(zzliVar3);
        zzliVar3.zzb.zzc();
        if (zzH()) {
            if (!TextUtils.isEmpty(zzv().zzk())) {
                zzpo zzpoVar3 = this.zzk;
                zzN(zzpoVar3);
                String strZzk = zzv().zzk();
                zzN(zzhgVar);
                zzhgVar.zzg();
                if (zzpoVar3.zzB(strZzk, zzhgVar.zzd().getString("gmp_app_id", null))) {
                    zzgt zzgtVar6 = this.zzh;
                    zzP(zzgtVar6);
                    zzgtVar6.zzi().zza("Rechecking which service to use due to a GMP App Id change");
                    zzN(zzhgVar);
                    zzhgVar.zzg();
                    Boolean boolZzi = zzhgVar.zzi();
                    SharedPreferences.Editor editorEdit = zzhgVar.zzd().edit();
                    editorEdit.clear();
                    editorEdit.apply();
                    if (boolZzi != null) {
                        zzhgVar.zzh(boolZzi);
                    }
                    zzm().zzh();
                    this.zzt.zzM();
                    this.zzt.zzI();
                    zzN(zzhgVar);
                    zzhdVar.zzb(this.zza);
                    zzN(zzhgVar);
                    zzhgVar.zze.zzb(null);
                }
                zzN(zzhgVar);
                String strZzk2 = zzv().zzk();
                zzhgVar.zzg();
                SharedPreferences.Editor editorEdit2 = zzhgVar.zzd().edit();
                editorEdit2.putString("gmp_app_id", strZzk2);
                editorEdit2.apply();
            }
            zzN(zzhgVar);
            if (!zzhgVar.zzl().zzo(zzjj.ANALYTICS_STORAGE)) {
                zzN(zzhgVar);
                zzhgVar.zze.zzb(null);
            }
            zzO(zzliVar3);
            zzN(zzhgVar);
            zzliVar3.zzR(zzhgVar.zze.zza());
            zzpo zzpoVar4 = this.zzk;
            zzN(zzpoVar4);
            try {
                zzpoVar4.zzu.zzc.getClassLoader().loadClass("com.google.firebase.remoteconfig.FirebaseRemoteConfig");
            } catch (ClassNotFoundException unused) {
                zzhg zzhgVar2 = this.zzg;
                zzN(zzhgVar2);
                zzhf zzhfVar = zzhgVar2.zzq;
                if (!TextUtils.isEmpty(zzhfVar.zza())) {
                    zzgt zzgtVar7 = this.zzh;
                    zzP(zzgtVar7);
                    zzgtVar7.zze().zza("Remote config removed with active feature rollouts");
                    zzN(zzhgVar2);
                    zzhfVar.zzb(null);
                }
            }
            if (!TextUtils.isEmpty(zzv().zzk())) {
                boolean zZzB = zzB();
                zzhg zzhgVar3 = this.zzg;
                zzN(zzhgVar3);
                if (!zzhgVar3.zzo() && !this.zzf.zzt()) {
                    zzN(zzhgVar3);
                    zzhgVar3.zzn(!zZzB);
                }
                if (zZzB) {
                    zzli zzliVar4 = this.zzo;
                    zzO(zzliVar4);
                    zzliVar4.zzU();
                }
                zzob zzobVar = this.zzj;
                zzO(zzobVar);
                zzobVar.zza.zza();
                zzt().zzC(new AtomicReference());
                zznk zznkVarZzt = zzt();
                zzN(zzhgVar3);
                zznkVarZzt.zzH(zzhgVar3.zzt.zza());
            }
        } else if (zzB()) {
            zzpo zzpoVar5 = this.zzk;
            zzN(zzpoVar5);
            if (!zzpoVar5.zzY("android.permission.INTERNET")) {
                zzgt zzgtVar8 = this.zzh;
                zzP(zzgtVar8);
                zzgtVar8.zzb().zza("App is missing INTERNET permission");
            }
            zzN(zzpoVar5);
            if (!zzpoVar5.zzY("android.permission.ACCESS_NETWORK_STATE")) {
                zzgt zzgtVar9 = this.zzh;
                zzP(zzgtVar9);
                zzgtVar9.zzb().zza("App is missing ACCESS_NETWORK_STATE permission");
            }
            Context context = this.zzc;
            if (!Wrappers.packageManager(context).isCallerInstantApp() && !this.zzf.zzE()) {
                if (!zzpo.zzau(context)) {
                    zzgt zzgtVar10 = this.zzh;
                    zzP(zzgtVar10);
                    zzgtVar10.zzb().zza("AppMeasurementReceiver not registered/enabled");
                }
                if (!zzpo.zzQ(context, false)) {
                    zzgt zzgtVar11 = this.zzh;
                    zzP(zzgtVar11);
                    zzgtVar11.zzb().zza("AppMeasurementService not registered/enabled");
                }
            }
            zzgt zzgtVar12 = this.zzh;
            zzP(zzgtVar12);
            zzgtVar12.zzb().zza("Uploading is not possible. App measurement disabled");
        }
        zzql.zza();
        if (this.zzf.zzp(null, zzfx.zzaQ)) {
            zzpo zzpoVar6 = this.zzk;
            zzN(zzpoVar6);
            if (zzpoVar6.zzS()) {
                long jMax = Math.max(500L, ((((long) ((Integer) zzfx.zzax.zzb(null)).intValue()) * 1000) + ((long) new Random().nextInt(5000))) - this.zzm.elapsedRealtime());
                if (jMax > 500) {
                    zzgt zzgtVar13 = this.zzh;
                    zzP(zzgtVar13);
                    zzgtVar13.zzk().zzb("Waiting to fetch trigger URIs until some time after boot. Delay in millis", Long.valueOf(jMax));
                }
                zzli zzliVar5 = this.zzo;
                zzO(zzliVar5);
                zzliVar5.zzu(jMax);
            }
        }
        zzhg zzhgVar4 = this.zzg;
        zzN(zzhgVar4);
        zzhgVar4.zzj.zzb(true);
    }

    @Override // com.google.android.gms.measurement.internal.zzjf
    @Pure
    public final zzae zzaU() {
        return this.zze;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzjf
    @Pure
    public final zzgt zzaV() {
        zzgt zzgtVar = this.zzh;
        zzP(zzgtVar);
        return zzgtVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzjf
    @Pure
    public final zzhy zzaW() {
        zzhy zzhyVar = this.zzi;
        zzP(zzhyVar);
        return zzhyVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzjf
    @Pure
    public final Context zzaY() {
        return this.zzc;
    }

    @Override // com.google.android.gms.measurement.internal.zzjf
    @Pure
    public final Clock zzaZ() {
        return this.zzm;
    }

    @Pure
    public final zzal zzc() {
        return this.zzf;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Pure
    public final zzhg zzd() {
        zzhg zzhgVar = this.zzg;
        zzN(zzhgVar);
        return zzhgVar;
    }

    public final zzgt zzf() {
        zzgt zzgtVar = this.zzh;
        if (zzgtVar == null || !zzgtVar.zzv()) {
            return null;
        }
        return zzgtVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Pure
    public final zzob zzh() {
        zzob zzobVar = this.zzj;
        zzO(zzobVar);
        return zzobVar;
    }

    @SideEffectFree
    final zzhy zzi() {
        return this.zzi;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Pure
    public final zzli zzj() {
        zzli zzliVar = this.zzo;
        zzO(zzliVar);
        return zzliVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Pure
    public final zzpo zzk() {
        zzpo zzpoVar = this.zzk;
        zzN(zzpoVar);
        return zzpoVar;
    }

    @Pure
    public final zzgm zzl() {
        return this.zzl;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Pure
    public final zzgk zzm() {
        zzO(this.zzs);
        return this.zzs;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Pure
    public final zzln zzn() {
        zzln zzlnVar = this.zzq;
        zzP(zzlnVar);
        return zzlnVar;
    }

    @Pure
    public final boolean zzp() {
        return this.zzd;
    }

    @Pure
    public final String zzq() {
        return this.zzr;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Pure
    public final zzma zzs() {
        zzma zzmaVar = this.zzn;
        zzO(zzmaVar);
        return zzmaVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Pure
    public final zznk zzt() {
        zzO(this.zzt);
        return this.zzt;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Pure
    public final zzba zzu() {
        zzP(this.zzu);
        return this.zzu;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Pure
    public final zzgh zzv() {
        zzO(this.zzv);
        return this.zzv;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Pure
    public final zzd zzw() {
        zzd zzdVar = this.zzp;
        zzM(zzdVar);
        return zzdVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Pure
    public final zzlp zzx() {
        zzM(this.zzw);
        return this.zzw;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzz(boolean z) {
        this.zzA = Boolean.valueOf(z);
    }
}

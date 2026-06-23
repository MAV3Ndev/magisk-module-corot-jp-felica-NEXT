package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.collection.ArrayMap;
import androidx.core.app.NotificationCompat;
import androidx.credentials.provider.CredentialEntry;
import androidx.privacysandbox.ads.adservices.java.measurement.MeasurementManagerFutures;
import androidx.work.WorkRequest;
import com.amazonaws.mobileconnectors.pinpoint.internal.event.ClientContext;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.measurement.zzql;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.Timestamp$$ExternalSyntheticApiModelOutline0;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.UByte$$ExternalSyntheticBackport0;
import kotlin.Unit;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzli extends zzg {
    protected zzkx zza;
    final zzx zzb;
    protected boolean zzc;
    private zzjo zzd;
    private final Set zze;
    private boolean zzf;
    private final AtomicReference zzg;
    private final Object zzh;
    private boolean zzi;
    private int zzj;
    private zzay zzk;
    private zzay zzl;
    private PriorityQueue zzm;
    private boolean zzn;
    private zzjk zzo;
    private final AtomicLong zzp;
    private long zzq;
    private zzay zzr;
    private SharedPreferences.OnSharedPreferenceChangeListener zzs;
    private zzay zzt;
    private final zzpn zzv;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected zzli(zzib zzibVar) {
        super(zzibVar);
        this.zze = new CopyOnWriteArraySet();
        this.zzh = new Object();
        this.zzi = false;
        this.zzj = 1;
        this.zzc = true;
        this.zzv = new zzkm(this);
        this.zzg = new AtomicReference();
        this.zzo = zzjk.zza;
        this.zzq = -1L;
        this.zzp = new AtomicLong(0L);
        this.zzb = new zzx(zzibVar);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final zzlq zzaq(final zzol zzolVar) {
        try {
            URL url = new URI(zzolVar.zzc).toURL();
            final AtomicReference atomicReference = new AtomicReference();
            String strZzl = this.zzu.zzv().zzl();
            zzib zzibVar = this.zzu;
            zzgr zzgrVarZzk = zzibVar.zzaV().zzk();
            Long lValueOf = Long.valueOf(zzolVar.zza);
            zzgrVarZzk.zzd("[sgtm] Uploading data from app. row_id, url, uncompressed size", lValueOf, zzolVar.zzc, Integer.valueOf(zzolVar.zzb.length));
            if (!TextUtils.isEmpty(zzolVar.zzg)) {
                zzibVar.zzaV().zzk().zzc("[sgtm] Uploading data from app. row_id", lValueOf, zzolVar.zzg);
            }
            HashMap map = new HashMap();
            Bundle bundle = zzolVar.zzd;
            for (String str : bundle.keySet()) {
                String string = bundle.getString(str);
                if (!TextUtils.isEmpty(string)) {
                    map.put(str, string);
                }
            }
            zzln zzlnVarZzn = zzibVar.zzn();
            byte[] bArr = zzolVar.zzb;
            zzlk zzlkVar = new zzlk() { // from class: com.google.android.gms.measurement.internal.zzky
                /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
                /* JADX WARN: Removed duplicated region for block: B:10:0x0016  */
                /* JADX WARN: Removed duplicated region for block: B:13:0x0064  */
                /* JADX WARN: Removed duplicated region for block: B:14:0x0067  */
                @Override // com.google.android.gms.measurement.internal.zzlk
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final /* synthetic */ void zza(String str2, int i, Throwable th, byte[] bArr2, Map map2) {
                    zzlq zzlqVar;
                    zzli zzliVar = this.zza;
                    zzliVar.zzg();
                    zzol zzolVar2 = zzolVar;
                    if (i != 200 && i != 204) {
                        if (i == 304) {
                            i = 304;
                            if (th != null) {
                            }
                        }
                        zzliVar.zzu.zzaV().zze().zzd("[sgtm] Upload failed for row_id. response, exception", Long.valueOf(zzolVar2.zza), Integer.valueOf(i), th);
                        if (!Arrays.asList(((String) zzfx.zzt.zzb(null)).split(",")).contains(String.valueOf(i))) {
                        }
                    } else if (th != null) {
                        zzliVar.zzu.zzaV().zzk().zzb("[sgtm] Upload succeeded for row_id", Long.valueOf(zzolVar2.zza));
                        zzlqVar = zzlq.SUCCESS;
                    } else {
                        zzliVar.zzu.zzaV().zze().zzd("[sgtm] Upload failed for row_id. response, exception", Long.valueOf(zzolVar2.zza), Integer.valueOf(i), th);
                        zzlqVar = !Arrays.asList(((String) zzfx.zzt.zzb(null)).split(",")).contains(String.valueOf(i)) ? zzlq.BACKOFF : zzlq.FAILURE;
                    }
                    AtomicReference atomicReference2 = atomicReference;
                    zznk zznkVarZzt = zzliVar.zzu.zzt();
                    long j = zzolVar2.zza;
                    zznkVarZzt.zzy(new zzaf(j, zzlqVar.zza(), zzolVar2.zzf));
                    zzliVar.zzu.zzaV().zzk().zzc("[sgtm] Updated status for row_id", Long.valueOf(j), zzlqVar);
                    synchronized (atomicReference2) {
                        atomicReference2.set(zzlqVar);
                        atomicReference2.notifyAll();
                    }
                }
            };
            zzlnVarZzn.zzw();
            Preconditions.checkNotNull(url);
            Preconditions.checkNotNull(bArr);
            Preconditions.checkNotNull(zzlkVar);
            zzlnVarZzn.zzu.zzaW().zzm(new zzlm(zzlnVarZzn, strZzl, url, bArr, map, zzlkVar));
            try {
                zzib zzibVar2 = zzibVar.zzk().zzu;
                long jCurrentTimeMillis = zzibVar2.zzaZ().currentTimeMillis() + 60000;
                synchronized (atomicReference) {
                    for (long jCurrentTimeMillis2 = 60000; atomicReference.get() == null && jCurrentTimeMillis2 > 0; jCurrentTimeMillis2 = jCurrentTimeMillis - zzibVar2.zzaZ().currentTimeMillis()) {
                        atomicReference.wait(jCurrentTimeMillis2);
                    }
                }
            } catch (InterruptedException unused) {
                this.zzu.zzaV().zze().zza("[sgtm] Interrupted waiting for uploading batch");
            }
            return atomicReference.get() == null ? zzlq.UNKNOWN : (zzlq) atomicReference.get();
        } catch (MalformedURLException | URISyntaxException e) {
            this.zzu.zzaV().zzb().zzd("[sgtm] Bad upload url for row_id", zzolVar.zzc, Long.valueOf(zzolVar.zza), e);
            return zzlq.FAILURE;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void zzar(Boolean bool, boolean z) {
        zzg();
        zzb();
        zzib zzibVar = this.zzu;
        zzibVar.zzaV().zzj().zzb("Setting app measurement enabled (FE)", bool);
        zzibVar.zzd().zzh(bool);
        if (z) {
            zzhg zzhgVarZzd = zzibVar.zzd();
            zzib zzibVar2 = zzhgVarZzd.zzu;
            zzhgVarZzd.zzg();
            SharedPreferences.Editor editorEdit = zzhgVarZzd.zzd().edit();
            if (bool != null) {
                editorEdit.putBoolean("measurement_enabled_from_api", bool.booleanValue());
            } else {
                editorEdit.remove("measurement_enabled_from_api");
            }
            editorEdit.apply();
        }
        if (this.zzu.zzE() || !(bool == null || bool.booleanValue())) {
            zzak();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Method merged with bridge method: zzak()V */
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: zzas, reason: merged with bridge method [inline-methods] */
    public final void zzak() {
        zzli zzliVar;
        zzg();
        zzib zzibVar = this.zzu;
        String strZza = zzibVar.zzd().zzh.zza();
        if (strZza == null) {
            zzliVar = this;
        } else if ("unset".equals(strZza)) {
            zzliVar = this;
            zzliVar.zzN("app", "_npa", null, zzibVar.zzaZ().currentTimeMillis());
        } else {
            zzN("app", "_npa", Long.valueOf(true != CredentialEntry.TRUE_STRING.equals(strZza) ? 0L : 1L), zzibVar.zzaZ().currentTimeMillis());
            zzliVar = this;
        }
        if (!zzliVar.zzu.zzB() || !zzliVar.zzc) {
            zzibVar.zzaV().zzj().zza("Updating Scion state (FE)");
            zzliVar.zzu.zzt().zzi();
        } else {
            zzibVar.zzaV().zzj().zza("Recording app launch after enabling measurement for the first time (FE)");
            zzU();
            zzliVar.zzu.zzh().zza.zza();
            zzibVar.zzaW().zzj(new zzjy(this));
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzA(zzjk zzjkVar) {
        zzg();
        boolean z = (zzjkVar.zzo(zzjj.ANALYTICS_STORAGE) && zzjkVar.zzo(zzjj.AD_STORAGE)) || this.zzu.zzt().zzO();
        zzib zzibVar = this.zzu;
        if (z != zzibVar.zzE()) {
            zzibVar.zzD(z);
            zzhg zzhgVarZzd = this.zzu.zzd();
            zzib zzibVar2 = zzhgVarZzd.zzu;
            zzhgVarZzd.zzg();
            Boolean boolValueOf = zzhgVarZzd.zzd().contains("measurement_enabled_from_api") ? Boolean.valueOf(zzhgVarZzd.zzd().getBoolean("measurement_enabled_from_api", true)) : null;
            if (!z || boolValueOf == null || boolValueOf.booleanValue()) {
                zzar(Boolean.valueOf(z), false);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzB(String str, String str2, Bundle bundle) {
        zzC(str, str2, bundle, true, true, this.zzu.zzaZ().currentTimeMillis());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzC(String str, String str2, Bundle bundle, boolean z, boolean z2, long j) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        Bundle bundle2 = bundle;
        if (Objects.equals(str2, FirebaseAnalytics.Event.SCREEN_VIEW)) {
            this.zzu.zzs().zzj(bundle2, j);
            return;
        }
        boolean z3 = true;
        if (z2 && this.zzd != null && !zzpo.zzZ(str2)) {
            z3 = false;
        }
        boolean z4 = z3;
        if (str == null) {
            str = "app";
        }
        zzJ(str, str2, j, bundle2, z2, z4, z, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r6v16, resolved type: E */
    /* JADX WARN: Multi-variable type inference failed */
    public final void zzD() {
        zzib zzibVar;
        zzoc zzocVar;
        zzoc zzocVar2;
        com.google.android.gms.internal.measurement.zzkp zzkpVar;
        zzg();
        zzib zzibVar2 = this.zzu;
        zzibVar2.zzaV().zzj().zza("Handle tcf update.");
        SharedPreferences sharedPreferencesZze = zzibVar2.zzd().zze();
        HashMap map = new HashMap();
        zzfw zzfwVar = zzfx.zzaZ;
        if (((Boolean) zzfwVar.zzb(null)).booleanValue()) {
            int i = zzoe.zzb;
            com.google.android.gms.internal.measurement.zzko zzkoVar = com.google.android.gms.internal.measurement.zzko.IAB_TCF_PURPOSE_STORE_AND_ACCESS_INFORMATION_ON_A_DEVICE;
            zzod zzodVar = zzod.CONSENT;
            Map.Entry entryM683m = UByte$$ExternalSyntheticBackport0.m683m((Object) zzkoVar, (Object) zzodVar);
            int i2 = 2;
            com.google.android.gms.internal.measurement.zzko zzkoVar2 = com.google.android.gms.internal.measurement.zzko.IAB_TCF_PURPOSE_SELECT_BASIC_ADS;
            int i3 = 1;
            zzod zzodVar2 = zzod.FLEXIBLE_LEGITIMATE_INTEREST;
            zzibVar = zzibVar2;
            ImmutableMap immutableMapOfEntries = ImmutableMap.ofEntries(entryM683m, UByte$$ExternalSyntheticBackport0.m683m((Object) zzkoVar2, (Object) zzodVar2), UByte$$ExternalSyntheticBackport0.m683m((Object) com.google.android.gms.internal.measurement.zzko.IAB_TCF_PURPOSE_CREATE_A_PERSONALISED_ADS_PROFILE, (Object) zzodVar), UByte$$ExternalSyntheticBackport0.m683m((Object) com.google.android.gms.internal.measurement.zzko.IAB_TCF_PURPOSE_SELECT_PERSONALISED_ADS, (Object) zzodVar), UByte$$ExternalSyntheticBackport0.m683m((Object) com.google.android.gms.internal.measurement.zzko.IAB_TCF_PURPOSE_MEASURE_AD_PERFORMANCE, (Object) zzodVar2), UByte$$ExternalSyntheticBackport0.m683m((Object) com.google.android.gms.internal.measurement.zzko.IAB_TCF_PURPOSE_APPLY_MARKET_RESEARCH_TO_GENERATE_AUDIENCE_INSIGHTS, (Object) zzodVar2), UByte$$ExternalSyntheticBackport0.m683m((Object) com.google.android.gms.internal.measurement.zzko.IAB_TCF_PURPOSE_DEVELOP_AND_IMPROVE_PRODUCTS, (Object) zzodVar2));
            ImmutableSet immutableSetOf = ImmutableSet.of("CH");
            char[] cArr = new char[5];
            boolean zContains = sharedPreferencesZze.contains("IABTCF_TCString");
            int iZzb = zzoe.zzb(sharedPreferencesZze, "IABTCF_CmpSdkID");
            int iZzb2 = zzoe.zzb(sharedPreferencesZze, "IABTCF_PolicyVersion");
            int iZzb3 = zzoe.zzb(sharedPreferencesZze, "IABTCF_gdprApplies");
            int iZzb4 = zzoe.zzb(sharedPreferencesZze, "IABTCF_PurposeOneTreatment");
            int iZzb5 = zzoe.zzb(sharedPreferencesZze, "IABTCF_EnableAdvertiserConsentMode");
            String strZza = zzoe.zza(sharedPreferencesZze, "IABTCF_PublisherCC");
            ImmutableMap.Builder builder = ImmutableMap.builder();
            UnmodifiableIterator it = immutableMapOfEntries.keySet().iterator();
            while (it.hasNext()) {
                com.google.android.gms.internal.measurement.zzko zzkoVar3 = (com.google.android.gms.internal.measurement.zzko) it.next();
                int iZza = zzkoVar3.zza();
                StringBuilder sb = new StringBuilder(String.valueOf(iZza).length() + 28);
                sb.append("IABTCF_PublisherRestrictions");
                sb.append(iZza);
                String strZza2 = zzoe.zza(sharedPreferencesZze, sb.toString());
                if (TextUtils.isEmpty(strZza2) || strZza2.length() < 755) {
                    zzkpVar = com.google.android.gms.internal.measurement.zzkp.PURPOSE_RESTRICTION_UNDEFINED;
                } else {
                    int iDigit = Character.digit(strZza2.charAt(754), 10);
                    zzkpVar = (iDigit < 0 || iDigit > com.google.android.gms.internal.measurement.zzkp.values().length || iDigit == 0) ? com.google.android.gms.internal.measurement.zzkp.PURPOSE_RESTRICTION_NOT_ALLOWED : iDigit != i3 ? iDigit != i2 ? com.google.android.gms.internal.measurement.zzkp.PURPOSE_RESTRICTION_UNDEFINED : com.google.android.gms.internal.measurement.zzkp.PURPOSE_RESTRICTION_REQUIRE_LEGITIMATE_INTEREST : com.google.android.gms.internal.measurement.zzkp.PURPOSE_RESTRICTION_REQUIRE_CONSENT;
                }
                builder.put(zzkoVar3, zzkpVar);
                i2 = 2;
                i3 = 1;
            }
            ImmutableMap immutableMapBuildOrThrow = builder.buildOrThrow();
            String strZza3 = zzoe.zza(sharedPreferencesZze, "IABTCF_PurposeConsents");
            String strZza4 = zzoe.zza(sharedPreferencesZze, "IABTCF_VendorConsents");
            boolean z = !TextUtils.isEmpty(strZza4) && strZza4.length() >= 755 && strZza4.charAt(754) == '1';
            String strZza5 = zzoe.zza(sharedPreferencesZze, "IABTCF_PurposeLegitimateInterests");
            String strZza6 = zzoe.zza(sharedPreferencesZze, "IABTCF_VendorLegitimateInterests");
            boolean z2 = !TextUtils.isEmpty(strZza6) && strZza6.length() >= 755 && strZza6.charAt(754) == '1';
            cArr[0] = '2';
            zzocVar = new zzoc(zzoe.zzd(immutableMapOfEntries, immutableMapBuildOrThrow, immutableSetOf, cArr, iZzb, iZzb5, iZzb3, iZzb2, iZzb4, strZza, strZza3, strZza5, z, z2, zContains));
        } else {
            zzibVar = zzibVar2;
            String strZza7 = zzoe.zza(sharedPreferencesZze, "IABTCF_VendorConsents");
            if (!"".equals(strZza7) && strZza7.length() > 754) {
                map.put("GoogleConsent", String.valueOf(strZza7.charAt(754)));
            }
            int iZzb6 = zzoe.zzb(sharedPreferencesZze, "IABTCF_gdprApplies");
            if (iZzb6 != -1) {
                map.put("gdprApplies", String.valueOf(iZzb6));
            }
            int iZzb7 = zzoe.zzb(sharedPreferencesZze, "IABTCF_EnableAdvertiserConsentMode");
            if (iZzb7 != -1) {
                map.put("EnableAdvertiserConsentMode", String.valueOf(iZzb7));
            }
            int iZzb8 = zzoe.zzb(sharedPreferencesZze, "IABTCF_PolicyVersion");
            if (iZzb8 != -1) {
                map.put("PolicyVersion", String.valueOf(iZzb8));
            }
            String strZza8 = zzoe.zza(sharedPreferencesZze, "IABTCF_PurposeConsents");
            if (!"".equals(strZza8)) {
                map.put("PurposeConsents", strZza8);
            }
            int iZzb9 = zzoe.zzb(sharedPreferencesZze, "IABTCF_CmpSdkID");
            if (iZzb9 != -1) {
                map.put("CmpSdkID", String.valueOf(iZzb9));
            }
            zzocVar = new zzoc(map);
        }
        zzibVar.zzaV().zzk().zzb("Tcf preferences read", zzocVar);
        if (!zzibVar.zzc().zzp(null, zzfwVar)) {
            if (zzibVar.zzd().zzm(zzocVar)) {
                Bundle bundleZzb = zzocVar.zzb();
                zzibVar.zzaV().zzk().zzb("Consent generated from Tcf", bundleZzb);
                if (bundleZzb != Bundle.EMPTY) {
                    zzp(bundleZzb, -30, zzibVar.zzaZ().currentTimeMillis());
                }
                Bundle bundle = new Bundle();
                bundle.putString("_tcfd", zzocVar.zze());
                zzF(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_tcf", bundle);
                return;
            }
            return;
        }
        zzhg zzhgVarZzd = zzibVar.zzd();
        zzhgVarZzd.zzg();
        String string = zzhgVarZzd.zzd().getString("stored_tcf_param", "");
        HashMap map2 = new HashMap();
        if (TextUtils.isEmpty(string)) {
            zzocVar2 = new zzoc(map2);
        } else {
            for (String str : string.split(";")) {
                String[] strArrSplit = str.split("=");
                if (strArrSplit.length >= 2 && zzoe.zza.contains(strArrSplit[0])) {
                    map2.put(strArrSplit[0], strArrSplit[1]);
                }
            }
            zzocVar2 = new zzoc(map2);
        }
        if (zzibVar.zzd().zzm(zzocVar)) {
            Bundle bundleZzb2 = zzocVar.zzb();
            zzibVar.zzaV().zzk().zzb("Consent generated from Tcf", bundleZzb2);
            if (bundleZzb2 != Bundle.EMPTY) {
                zzp(bundleZzb2, -30, zzibVar.zzaZ().currentTimeMillis());
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("_tcfm", zzocVar.zzd(zzocVar2));
            bundle2.putString("_tcfd2", zzocVar.zzc());
            bundle2.putString("_tcfd", zzocVar.zze());
            zzF(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_tcf", bundle2);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzE() {
        zzg();
        zzib zzibVar = this.zzu;
        zzibVar.zzaV().zzj().zza("Register tcfPrefChangeListener.");
        if (this.zzs == null) {
            this.zzt = new zzka(this, this.zzu);
            this.zzs = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: com.google.android.gms.measurement.internal.zzld
                @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
                public final /* synthetic */ void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
                    this.zza.zzaf(sharedPreferences, str);
                }
            };
        }
        zzibVar.zzd().zze().registerOnSharedPreferenceChangeListener(this.zzs);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzF(String str, String str2, Bundle bundle) {
        zzg();
        zzG(str, str2, this.zzu.zzaZ().currentTimeMillis(), bundle);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzG(String str, String str2, long j, Bundle bundle) {
        zzg();
        boolean z = true;
        if (this.zzd != null && !zzpo.zzZ(str2)) {
            z = false;
        }
        zzH(str, str2, j, bundle, true, z, true, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r1v16, resolved type: boolean */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:128:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x042e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected final void zzH(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        boolean z4;
        int i;
        long j2;
        ArrayList arrayList;
        long j3;
        long j4;
        String str4;
        int size;
        int i2;
        int i3;
        Bundle[] bundleArr;
        String str5 = str;
        Preconditions.checkNotEmpty(str5);
        Preconditions.checkNotNull(bundle);
        zzg();
        zzb();
        zzib zzibVar = this.zzu;
        if (!zzibVar.zzB()) {
            this.zzu.zzaV().zzj().zza("Event not sent since app measurement is disabled");
            return;
        }
        List listZzp = this.zzu.zzv().zzp();
        if (listZzp != null && !listZzp.contains(str2)) {
            this.zzu.zzaV().zzj().zzc("Dropping non-safelisted event. event name, origin", str2, str5);
            return;
        }
        if (!this.zzf) {
            this.zzf = true;
            try {
                try {
                    (!zzibVar.zzp() ? Class.forName("com.google.android.gms.tagmanager.TagManagerService", true, this.zzu.zzaY().getClassLoader()) : Class.forName("com.google.android.gms.tagmanager.TagManagerService")).getDeclaredMethod("initialize", Context.class).invoke(null, this.zzu.zzaY());
                } catch (Exception e) {
                    this.zzu.zzaV().zze().zzb("Failed to invoke Tag Manager's initialize() method", e);
                }
            } catch (ClassNotFoundException unused) {
                this.zzu.zzaV().zzi().zza("Tag Manager is not found and thus will not be used");
            }
        }
        zzib zzibVar2 = this.zzu;
        if (!zzibVar2.zzc().zzp(null, zzfx.zzbg) && Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN.equals(str2) && bundle.containsKey("gclid")) {
            zzibVar2.zzaU();
            zzN(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_lgclid", bundle.getString("gclid"), zzibVar2.zzaZ().currentTimeMillis());
        }
        zzli zzliVar = this;
        zzibVar2.zzaU();
        if (z && zzpo.zzaf(str2)) {
            zzibVar2.zzk().zzI(bundle, zzibVar2.zzd().zzt.zza());
        }
        if (!z3) {
            zzibVar2.zzaU();
            if (!"_iap".equals(str2)) {
                zzib zzibVar3 = zzliVar.zzu;
                zzpo zzpoVarZzk = zzibVar3.zzk();
                int i4 = 2;
                if (zzpoVarZzk.zzj(NotificationCompat.CATEGORY_EVENT, str2)) {
                    if (zzpoVarZzk.zzl(NotificationCompat.CATEGORY_EVENT, zzjl.zza, zzjl.zzb, str2)) {
                        zzpoVarZzk.zzu.zzc();
                        if (zzpoVarZzk.zzm(NotificationCompat.CATEGORY_EVENT, 40, str2)) {
                            i4 = 0;
                        }
                    } else {
                        i4 = 13;
                    }
                }
                if (i4 != 0) {
                    zzibVar2.zzaV().zzd().zzb("Invalid public event name. Event will not be logged (FE)", zzibVar2.zzl().zza(str2));
                    zzpo zzpoVarZzk2 = zzibVar3.zzk();
                    zzibVar3.zzc();
                    zzibVar3.zzk().zzN(zzliVar.zzv, null, i4, "_ev", zzpoVarZzk2.zzC(str2, 40, true), str2 != null ? str2.length() : 0);
                    return;
                }
            }
        }
        zzibVar2.zzaU();
        zzib zzibVar4 = zzliVar.zzu;
        zzlt zzltVarZzh = zzibVar4.zzs().zzh(false);
        if (zzltVarZzh != null && !bundle.containsKey("_sc")) {
            zzltVarZzh.zzd = true;
        }
        zzpo.zzav(zzltVarZzh, bundle, z && !z3);
        boolean zEquals = "am".equals(str5);
        boolean zZzZ = zzpo.zzZ(str2);
        if (!z || zzliVar.zzd == null || zZzZ) {
            z4 = zEquals;
        } else {
            if (!zEquals) {
                zzibVar2.zzaV().zzj().zzc("Passing event to registered event handler (FE)", zzibVar2.zzl().zza(str2), zzibVar2.zzl().zze(bundle));
                Preconditions.checkNotNull(zzliVar.zzd);
                zzliVar.zzd.interceptEvent(str5, str2, bundle, j);
                return;
            }
            z4 = true;
        }
        zzib zzibVar5 = zzliVar.zzu;
        if (zzibVar5.zzH()) {
            int iZzn = zzibVar2.zzk().zzn(str2);
            if (iZzn != 0) {
                zzibVar2.zzaV().zzd().zzb("Invalid event name. Event will not be logged (FE)", zzibVar2.zzl().zza(str2));
                zzpo zzpoVarZzk3 = zzibVar2.zzk();
                zzibVar2.zzc();
                zzibVar5.zzk().zzN(zzliVar.zzv, str3, iZzn, "_ev", zzpoVarZzk3.zzC(str2, 40, true), str2 != null ? str2.length() : 0);
                return;
            }
            Bundle bundleZzF = zzibVar2.zzk().zzF(str3, str2, bundle, CollectionUtils.listOf((Object[]) new String[]{"_o", "_sn", "_sc", "_si"}), z3);
            Preconditions.checkNotNull(bundleZzF);
            zzibVar2.zzaU();
            if (zzibVar4.zzs().zzh(false) == null || !"_ae".equals(str2)) {
                i = 0;
                j2 = 0;
            } else {
                zznz zznzVar = zzibVar4.zzh().zzb;
                j2 = 0;
                long jElapsedRealtime = zznzVar.zzc.zzu.zzaZ().elapsedRealtime();
                i = 0;
                long j5 = jElapsedRealtime - zznzVar.zzb;
                zznzVar.zzb = jElapsedRealtime;
                if (j5 > 0) {
                    zzibVar2.zzk().zzak(bundleZzF, j5);
                }
            }
            if (!DebugKt.DEBUG_PROPERTY_VALUE_AUTO.equals(str5) && "_ssr".equals(str2)) {
                zzpo zzpoVarZzk4 = zzibVar2.zzk();
                String string = bundleZzF.getString("_ffr");
                if (Strings.isEmptyOrWhitespace(string)) {
                    string = null;
                } else if (string != null) {
                    string = string.trim();
                }
                zzib zzibVar6 = zzpoVarZzk4.zzu;
                if (Objects.equals(string, zzibVar6.zzd().zzq.zza())) {
                    zzibVar6.zzaV().zzj().zza("Not logging duplicate session_start_with_rollout event");
                    return;
                }
                zzibVar6.zzd().zzq.zzb(string);
            } else if ("_ae".equals(str2)) {
                String strZza = zzibVar2.zzk().zzu.zzd().zzq.zza();
                if (!TextUtils.isEmpty(strZza)) {
                    bundleZzF.putString("_ffr", strZza);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(bundleZzF);
            boolean zZzi = zzibVar2.zzc().zzp(null, zzfx.zzaU) ? zzibVar4.zzh().zzi() : zzibVar2.zzd().zzn.zza();
            if (zzibVar2.zzd().zzk.zza() > j2) {
                j4 = j;
                if (zzibVar2.zzd().zzp(j4) && zZzi) {
                    zzibVar2.zzaV().zzk().zza("Current session is expired, remove the session number, ID, and engagement time");
                    long j6 = j2;
                    arrayList = arrayList2;
                    j3 = j6;
                    str4 = "_ae";
                    zzN(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_sid", null, zzibVar2.zzaZ().currentTimeMillis());
                    zzN(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_sno", null, zzibVar2.zzaZ().currentTimeMillis());
                    zzN(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_se", null, zzibVar2.zzaZ().currentTimeMillis());
                    zzliVar = this;
                    zzibVar2.zzd().zzl.zzb(j3);
                    if (bundleZzF.getLong(FirebaseAnalytics.Param.EXTEND_SESSION, j3) == 1) {
                        zzibVar2.zzaV().zzk().zza("EXTEND_SESSION param attached: initiate a new session or extend the current active session");
                        zzibVar5.zzh().zza.zzb(j4, true);
                    }
                    ArrayList arrayList3 = new ArrayList(bundleZzF.keySet());
                    Collections.sort(arrayList3);
                    size = arrayList3.size();
                    for (i2 = i; i2 < size; i2++) {
                        String str6 = (String) arrayList3.get(i2);
                        if (str6 != null) {
                            zzibVar2.zzk();
                            Object obj = bundleZzF.get(str6);
                            if (obj instanceof Bundle) {
                                bundleArr = new Bundle[1];
                                bundleArr[i] = (Bundle) obj;
                            } else if (obj instanceof Parcelable[]) {
                                Parcelable[] parcelableArr = (Parcelable[]) obj;
                                bundleArr = (Bundle[]) Arrays.copyOf(parcelableArr, parcelableArr.length, Bundle[].class);
                            } else if (obj instanceof ArrayList) {
                                ArrayList arrayList4 = (ArrayList) obj;
                                bundleArr = (Bundle[]) arrayList4.toArray(new Bundle[arrayList4.size()]);
                            } else {
                                bundleArr = null;
                            }
                            if (bundleArr != null) {
                                bundleZzF.putParcelableArray(str6, bundleArr);
                            }
                        }
                    }
                    i3 = i;
                    while (i3 < arrayList.size()) {
                        ArrayList arrayList5 = arrayList;
                        Bundle bundleZzab = (Bundle) arrayList5.get(i3);
                        String str7 = i3 != 0 ? "_ep" : str2;
                        bundleZzab.putString("_o", str5);
                        if (z2) {
                            bundleZzab = zzibVar2.zzk().zzab(bundleZzab, null);
                        }
                        String str8 = str5;
                        Bundle bundle2 = bundleZzab;
                        zzibVar4.zzt().zzn(new zzbg(str7, new zzbe(bundleZzab), str8, j4), str3);
                        if (!z4) {
                            Iterator it = zzliVar.zze.iterator();
                            while (it.hasNext()) {
                                ((zzjp) it.next()).onEvent(str, str2, new Bundle(bundle2), j);
                            }
                        }
                        i3++;
                        str5 = str;
                        j4 = j;
                        arrayList = arrayList5;
                    }
                    zzibVar2.zzaU();
                    if (zzibVar4.zzs().zzh(i) == null && str4.equals(str2)) {
                        zzibVar4.zzh().zzb.zzd(true, true, zzibVar2.zzaZ().elapsedRealtime());
                        return;
                    }
                }
                long j7 = j2;
                arrayList = arrayList2;
                j3 = j7;
            } else {
                long j8 = j2;
                arrayList = arrayList2;
                j3 = j8;
                j4 = j;
            }
            str4 = "_ae";
            if (bundleZzF.getLong(FirebaseAnalytics.Param.EXTEND_SESSION, j3) == 1) {
            }
            ArrayList arrayList32 = new ArrayList(bundleZzF.keySet());
            Collections.sort(arrayList32);
            size = arrayList32.size();
            while (i2 < size) {
            }
            i3 = i;
            while (i3 < arrayList.size()) {
            }
            zzibVar2.zzaU();
            if (zzibVar4.zzs().zzh(i) == null) {
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzI(String str, String str2, Bundle bundle, String str3) {
        zzib.zzL();
        zzJ(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, str2, this.zzu.zzaZ().currentTimeMillis(), bundle, false, true, true, str3);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzJ(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        int i = zzpo.zza;
        Bundle bundle2 = new Bundle(bundle);
        for (String str4 : bundle2.keySet()) {
            Object obj = bundle2.get(str4);
            if (obj instanceof Bundle) {
                bundle2.putBundle(str4, new Bundle((Bundle) obj));
            } else {
                int i2 = 0;
                if (obj instanceof Parcelable[]) {
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    while (i2 < parcelableArr.length) {
                        Parcelable parcelable = parcelableArr[i2];
                        if (parcelable instanceof Bundle) {
                            parcelableArr[i2] = new Bundle((Bundle) parcelable);
                        }
                        i2++;
                    }
                } else if (obj instanceof List) {
                    List list = (List) obj;
                    while (i2 < list.size()) {
                        Object obj2 = list.get(i2);
                        if (obj2 instanceof Bundle) {
                            list.set(i2, new Bundle((Bundle) obj2));
                        }
                        i2++;
                    }
                }
            }
        }
        this.zzu.zzaW().zzj(new zzkb(this, str, str2, j, bundle2, z, z2, z3, str3));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzK(String str, String str2, Object obj, boolean z) {
        zzL(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_ldl", obj, true, this.zzu.zzaZ().currentTimeMillis());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzM(String str, String str2, long j, Object obj) {
        this.zzu.zzaW().zzj(new zzkc(this, str, str2, obj, j));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void zzN(String str, String str2, Object obj, long j) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzg();
        zzb();
        if (FirebaseAnalytics.UserProperty.ALLOW_AD_PERSONALIZATION_SIGNALS.equals(str2)) {
            if (obj instanceof String) {
                String str3 = (String) obj;
                if (TextUtils.isEmpty(str3)) {
                    if (obj == null) {
                        this.zzu.zzd().zzh.zzb("unset");
                    }
                    this.zzu.zzaV().zzk().zzc("Setting user property(FE)", "non_personalized_ads(_npa)", obj);
                } else {
                    String lowerCase = str3.toLowerCase(Locale.ENGLISH);
                    String str4 = CredentialEntry.FALSE_STRING;
                    long j2 = true != CredentialEntry.FALSE_STRING.equals(lowerCase) ? 0L : 1L;
                    zzib zzibVar = this.zzu;
                    Long lValueOf = Long.valueOf(j2);
                    zzhf zzhfVar = zzibVar.zzd().zzh;
                    if (lValueOf.longValue() == 1) {
                        str4 = CredentialEntry.TRUE_STRING;
                    }
                    zzhfVar.zzb(str4);
                    obj = lValueOf;
                }
                str2 = "_npa";
                this.zzu.zzaV().zzk().zzc("Setting user property(FE)", "non_personalized_ads(_npa)", obj);
            }
        }
        String str5 = str2;
        Object obj2 = obj;
        zzib zzibVar2 = this.zzu;
        if (!zzibVar2.zzB()) {
            this.zzu.zzaV().zzk().zza("User property not set since app measurement is disabled");
        } else if (zzibVar2.zzH()) {
            this.zzu.zzt().zzA(new zzpk(str5, j, obj2, str));
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final List zzO(boolean z) {
        zzb();
        zzib zzibVar = this.zzu;
        zzibVar.zzaV().zzk().zza("Getting user properties (FE)");
        if (zzibVar.zzaW().zze()) {
            zzibVar.zzaV().zzb().zza("Cannot get all user properties from analytics worker thread");
            return Collections.EMPTY_LIST;
        }
        zzibVar.zzaU();
        if (zzae.zza()) {
            zzibVar.zzaV().zzb().zza("Cannot get all user properties from main thread");
            return Collections.EMPTY_LIST;
        }
        AtomicReference atomicReference = new AtomicReference();
        this.zzu.zzaW().zzk(atomicReference, 5000L, "get user properties", new zzke(this, atomicReference, z));
        List list = (List) atomicReference.get();
        if (list != null) {
            return list;
        }
        zzibVar.zzaV().zzb().zzb("Timed out waiting for get user properties, includeInternal", Boolean.valueOf(z));
        return Collections.EMPTY_LIST;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Map zzP(String str, String str2, boolean z) {
        zzib zzibVar = this.zzu;
        if (zzibVar.zzaW().zze()) {
            zzibVar.zzaV().zzb().zza("Cannot get user properties from analytics worker thread");
            return Collections.EMPTY_MAP;
        }
        zzibVar.zzaU();
        if (zzae.zza()) {
            zzibVar.zzaV().zzb().zza("Cannot get user properties from main thread");
            return Collections.EMPTY_MAP;
        }
        AtomicReference atomicReference = new AtomicReference();
        this.zzu.zzaW().zzk(atomicReference, 5000L, "get user properties", new zzkk(this, atomicReference, null, str, str2, z));
        List<zzpk> list = (List) atomicReference.get();
        if (list == null) {
            zzibVar.zzaV().zzb().zzb("Timed out waiting for handle get user properties, includeInternal", Boolean.valueOf(z));
            return Collections.EMPTY_MAP;
        }
        ArrayMap arrayMap = new ArrayMap(list.size());
        for (zzpk zzpkVar : list) {
            Object objZza = zzpkVar.zza();
            if (objZza != null) {
                arrayMap.put(zzpkVar.zzb, objZza);
            }
        }
        return arrayMap;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzQ() {
        return (String) this.zzg.get();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzR(String str) {
        this.zzg.set(str);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzS() {
        zzg();
        zzib zzibVar = this.zzu;
        if (zzibVar.zzd().zzo.zza()) {
            zzibVar.zzaV().zzj().zza("Deferred Deep Link already retrieved. Not fetching again.");
            return;
        }
        long jZza = zzibVar.zzd().zzp.zza();
        zzibVar.zzd().zzp.zzb(1 + jZza);
        zzibVar.zzc();
        if (jZza >= 5) {
            zzibVar.zzaV().zze().zza("Permanently failed to retrieve Deferred Deep Link. Reached maximum retries.");
            zzibVar.zzd().zzo.zzb(true);
        } else {
            if (this.zzr == null) {
                this.zzr = new zzkf(this, this.zzu);
            }
            this.zzr.zzb(0L);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzT(long j) {
        this.zzg.set(null);
        this.zzu.zzaW().zzj(new zzkg(this, j));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzU() {
        zzg();
        zzb();
        if (this.zzu.zzH()) {
            zzib zzibVar = this.zzu;
            zzal zzalVarZzc = zzibVar.zzc();
            zzalVarZzc.zzu.zzaU();
            Boolean boolZzr = zzalVarZzc.zzr("google_analytics_deferred_deep_link_enabled");
            if (boolZzr != null && boolZzr.booleanValue()) {
                zzibVar.zzaV().zzj().zza("Deferred Deep Link feature enabled.");
                zzibVar.zzaW().zzj(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzlg
                    @Override // java.lang.Runnable
                    public final /* synthetic */ void run() {
                        this.zza.zzS();
                    }
                });
            }
            this.zzu.zzt().zzE();
            this.zzc = false;
            zzhg zzhgVarZzd = zzibVar.zzd();
            zzhgVarZzd.zzg();
            String string = zzhgVarZzd.zzd().getString("previous_os_version", null);
            zzhgVarZzd.zzu.zzu().zzw();
            String str = Build.VERSION.RELEASE;
            if (!TextUtils.isEmpty(str) && !str.equals(string)) {
                SharedPreferences.Editor editorEdit = zzhgVarZzd.zzd().edit();
                editorEdit.putString("previous_os_version", str);
                editorEdit.apply();
            }
            if (TextUtils.isEmpty(string)) {
                return;
            }
            zzibVar.zzu().zzw();
            if (string.equals(Build.VERSION.RELEASE)) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("_po", string);
            zzF(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_ou", bundle);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzV(zzjo zzjoVar) {
        zzjo zzjoVar2;
        zzg();
        zzb();
        if (zzjoVar != null && zzjoVar != (zzjoVar2 = this.zzd)) {
            Preconditions.checkState(zzjoVar2 == null, "EventInterceptor already set.");
        }
        this.zzd = zzjoVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzW(zzjp zzjpVar) {
        zzb();
        Preconditions.checkNotNull(zzjpVar);
        if (this.zze.add(zzjpVar)) {
            return;
        }
        this.zzu.zzaV().zze().zza("OnEventListener already registered");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzX(zzjp zzjpVar) {
        zzb();
        Preconditions.checkNotNull(zzjpVar);
        if (this.zze.remove(zzjpVar)) {
            return;
        }
        this.zzu.zzaV().zze().zza("OnEventListener had not been registered");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int zzY(String str) {
        Preconditions.checkNotEmpty(str);
        this.zzu.zzc();
        return 25;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzZ(Bundle bundle) {
        zzaa(bundle, this.zzu.zzaZ().currentTimeMillis());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzaa(Bundle bundle, long j) {
        Preconditions.checkNotNull(bundle);
        Bundle bundle2 = new Bundle(bundle);
        if (!TextUtils.isEmpty(bundle2.getString(ClientContext.APP_ID_KEY))) {
            this.zzu.zzaV().zze().zza("Package name should be null when calling setConditionalUserProperty");
        }
        bundle2.remove(ClientContext.APP_ID_KEY);
        Preconditions.checkNotNull(bundle2);
        zzjg.zzb(bundle2, ClientContext.APP_ID_KEY, String.class, null);
        zzjg.zzb(bundle2, "origin", String.class, null);
        zzjg.zzb(bundle2, "name", String.class, null);
        zzjg.zzb(bundle2, "value", Object.class, null);
        zzjg.zzb(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, String.class, null);
        zzjg.zzb(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.class, 0L);
        zzjg.zzb(bundle2, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, String.class, null);
        zzjg.zzb(bundle2, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, Bundle.class, null);
        zzjg.zzb(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, String.class, null);
        zzjg.zzb(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, Bundle.class, null);
        zzjg.zzb(bundle2, AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.class, 0L);
        zzjg.zzb(bundle2, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, String.class, null);
        zzjg.zzb(bundle2, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, Bundle.class, null);
        Preconditions.checkNotEmpty(bundle2.getString("name"));
        Preconditions.checkNotEmpty(bundle2.getString("origin"));
        Preconditions.checkNotNull(bundle2.get("value"));
        bundle2.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, j);
        String string = bundle2.getString("name");
        Object obj = bundle2.get("value");
        zzib zzibVar = this.zzu;
        if (zzibVar.zzk().zzp(string) != 0) {
            zzibVar.zzaV().zzb().zzb("Invalid conditional user property name", zzibVar.zzl().zzc(string));
            return;
        }
        if (zzibVar.zzk().zzK(string, obj) != 0) {
            zzibVar.zzaV().zzb().zzc("Invalid conditional user property value", zzibVar.zzl().zzc(string), obj);
            return;
        }
        Object objZzL = zzibVar.zzk().zzL(string, obj);
        if (objZzL == null) {
            zzibVar.zzaV().zzb().zzc("Unable to normalize conditional user property value", zzibVar.zzl().zzc(string), obj);
            return;
        }
        zzjg.zza(bundle2, objZzL);
        long j2 = bundle2.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT);
        if (!TextUtils.isEmpty(bundle2.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME))) {
            zzibVar.zzc();
            if (j2 > 15552000000L || j2 < 1) {
                zzibVar.zzaV().zzb().zzc("Invalid conditional user property timeout", zzibVar.zzl().zzc(string), Long.valueOf(j2));
                return;
            }
        }
        long j3 = bundle2.getLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE);
        zzibVar.zzc();
        if (j3 > 15552000000L || j3 < 1) {
            zzibVar.zzaV().zzb().zzc("Invalid conditional user property time to live", zzibVar.zzl().zzc(string), Long.valueOf(j3));
        } else {
            zzibVar.zzaW().zzj(new zzkh(this, bundle2));
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzab(String str, String str2, Bundle bundle) {
        zzib zzibVar = this.zzu;
        long jCurrentTimeMillis = zzibVar.zzaZ().currentTimeMillis();
        Preconditions.checkNotEmpty(str);
        Bundle bundle2 = new Bundle();
        bundle2.putString("name", str);
        bundle2.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, jCurrentTimeMillis);
        if (str2 != null) {
            bundle2.putString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, str2);
            bundle2.putBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, bundle);
        }
        zzibVar.zzaW().zzj(new zzki(this, bundle2));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final ArrayList zzac(String str, String str2) {
        zzib zzibVar = this.zzu;
        if (zzibVar.zzaW().zze()) {
            zzibVar.zzaV().zzb().zza("Cannot get conditional user properties from analytics worker thread");
            return new ArrayList(0);
        }
        zzibVar.zzaU();
        if (zzae.zza()) {
            zzibVar.zzaV().zzb().zza("Cannot get conditional user properties from main thread");
            return new ArrayList(0);
        }
        AtomicReference atomicReference = new AtomicReference();
        this.zzu.zzaW().zzk(atomicReference, 5000L, "get conditional user properties", new zzkj(this, atomicReference, null, str, str2));
        List list = (List) atomicReference.get();
        if (list != null) {
            return zzpo.zzas(list);
        }
        zzibVar.zzaV().zzb().zzb("Timed out waiting for get conditional user properties", null);
        return new ArrayList();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzad() {
        zzlt zzltVarZzl = this.zzu.zzs().zzl();
        if (zzltVarZzl != null) {
            return zzltVarZzl.zza;
        }
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzae() {
        zzlt zzltVarZzl = this.zzu.zzs().zzl();
        if (zzltVarZzl != null) {
            return zzltVarZzl.zzb;
        }
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzaf(SharedPreferences sharedPreferences, String str) {
        zzib zzibVar = this.zzu;
        if (!zzibVar.zzc().zzp(null, zzfx.zzaZ)) {
            if (Objects.equals(str, "IABTCF_TCString")) {
                zzibVar.zzaV().zzk().zza("IABTCF_TCString change picked up in listener.");
                ((zzay) Preconditions.checkNotNull(this.zzt)).zzb(500L);
                return;
            }
            return;
        }
        if (Objects.equals(str, "IABTCF_TCString") || Objects.equals(str, "IABTCF_gdprApplies") || Objects.equals(str, "IABTCF_EnableAdvertiserConsentMode")) {
            zzibVar.zzaV().zzk().zza("IABTCF_TCString change picked up in listener.");
            ((zzay) Preconditions.checkNotNull(this.zzt)).zzb(500L);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzag(Bundle bundle) {
        Bundle bundle2;
        int i;
        if (bundle.isEmpty()) {
            bundle2 = bundle;
        } else {
            zzib zzibVar = this.zzu;
            bundle2 = new Bundle(zzibVar.zzd().zzt.zza());
            Iterator<String> it = bundle.keySet().iterator();
            while (true) {
                i = 0;
                if (!it.hasNext()) {
                    break;
                }
                String next = it.next();
                Object obj = bundle.get(next);
                if (obj != null && !(obj instanceof String) && !(obj instanceof Long) && !(obj instanceof Double)) {
                    if (zzibVar.zzk().zzt(obj)) {
                        zzibVar.zzk().zzN(this.zzv, null, 27, null, null, 0);
                    }
                    zzibVar.zzaV().zzh().zzc("Invalid default event parameter type. Name, value", next, obj);
                } else if (zzpo.zzZ(next)) {
                    zzibVar.zzaV().zzh().zzb("Invalid default event parameter name. Name", next);
                } else if (obj == null) {
                    bundle2.remove(next);
                } else if (zzibVar.zzk().zzu("param", next, zzibVar.zzc().zze(null, false), obj)) {
                    zzibVar.zzk().zzM(bundle2, next, obj);
                }
            }
            zzibVar.zzk();
            int iZzc = zzibVar.zzc().zzc();
            if (bundle2.size() > iZzc) {
                for (String str : new TreeSet(bundle2.keySet())) {
                    i++;
                    if (i > iZzc) {
                        bundle2.remove(str);
                    }
                }
                zzibVar.zzk().zzN(this.zzv, null, 26, null, null, 0);
                zzibVar.zzaV().zzh().zza("Too many default event parameters set. Discarding beyond event parameter limit");
            }
        }
        zzib zzibVar2 = this.zzu;
        zzibVar2.zzd().zzt.zzb(bundle2);
        if (!bundle.isEmpty() || zzibVar2.zzc().zzp(null, zzfx.zzaW)) {
            this.zzu.zzt().zzH(bundle2);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzah(int i) {
        if (this.zzk == null) {
            this.zzk = new zzjw(this, this.zzu);
        }
        this.zzk.zzb(((long) i) * 1000);
    }

    final /* synthetic */ void zzai(Boolean bool, boolean z) {
        zzar(bool, true);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzaj(zzjk zzjkVar, long j, boolean z, boolean z2) {
        zzg();
        zzb();
        zzib zzibVar = this.zzu;
        zzjk zzjkVarZzl = zzibVar.zzd().zzl();
        if (j <= this.zzq && zzjk.zzu(zzjkVarZzl.zzb(), zzjkVar.zzb())) {
            zzibVar.zzaV().zzi().zzb("Dropped out-of-date consent setting, proposed settings", zzjkVar);
            return;
        }
        zzhg zzhgVarZzd = zzibVar.zzd();
        zzib zzibVar2 = zzhgVarZzd.zzu;
        zzhgVarZzd.zzg();
        int iZzb = zzjkVar.zzb();
        if (!zzhgVarZzd.zzk(iZzb)) {
            zzibVar.zzaV().zzi().zzb("Lower precedence consent source ignored, proposed source", Integer.valueOf(zzjkVar.zzb()));
            return;
        }
        zzib zzibVar3 = this.zzu;
        SharedPreferences.Editor editorEdit = zzhgVarZzd.zzd().edit();
        editorEdit.putString("consent_settings", zzjkVar.zzl());
        editorEdit.putInt("consent_source", iZzb);
        editorEdit.apply();
        zzibVar.zzaV().zzk().zzb("Setting storage consent(FE)", zzjkVar);
        this.zzq = j;
        if (zzibVar3.zzt().zzP()) {
            zzibVar3.zzt().zzk(z);
        } else {
            zzibVar3.zzt().zzj(z);
        }
        if (z2) {
            zzibVar3.zzt().zzC(new AtomicReference());
        }
    }

    final /* synthetic */ void zzal(boolean z) {
        this.zzi = false;
    }

    final /* synthetic */ int zzam() {
        return this.zzj;
    }

    final /* synthetic */ void zzan(int i) {
        this.zzj = i;
    }

    final /* synthetic */ zzay zzao() {
        return this.zzr;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ int zzap(Throwable th) {
        String message = th.getMessage();
        this.zzn = false;
        if (message == null) {
            return 2;
        }
        if (!(th instanceof IllegalStateException) && !message.contains("garbage collected") && !th.getClass().getSimpleName().equals("ServiceUnavailableException")) {
            return (!(th instanceof SecurityException) || message.endsWith("READ_DEVICE_CONFIG")) ? 2 : 3;
        }
        if (!message.contains("Background")) {
            return 1;
        }
        this.zzn = true;
        return 1;
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zze() {
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzh() {
        zzib zzibVar = this.zzu;
        if (!(zzibVar.zzaY().getApplicationContext() instanceof Application) || this.zza == null) {
            return;
        }
        ((Application) zzibVar.zzaY().getApplicationContext()).unregisterActivityLifecycleCallbacks(this.zza);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Boolean zzi() {
        AtomicReference atomicReference = new AtomicReference();
        return (Boolean) this.zzu.zzaW().zzk(atomicReference, 15000L, "boolean test flag value", new zzkd(this, atomicReference));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzj() {
        AtomicReference atomicReference = new AtomicReference();
        return (String) this.zzu.zzaW().zzk(atomicReference, 15000L, "String test flag value", new zzkn(this, atomicReference));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Long zzk() {
        AtomicReference atomicReference = new AtomicReference();
        return (Long) this.zzu.zzaW().zzk(atomicReference, 15000L, "long test flag value", new zzko(this, atomicReference));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Integer zzl() {
        AtomicReference atomicReference = new AtomicReference();
        return (Integer) this.zzu.zzaW().zzk(atomicReference, 15000L, "int test flag value", new zzkp(this, atomicReference));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Double zzm() {
        AtomicReference atomicReference = new AtomicReference();
        return (Double) this.zzu.zzaW().zzk(atomicReference, 15000L, "double test flag value", new zzkq(this, atomicReference));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzn(Boolean bool) {
        zzb();
        this.zzu.zzaW().zzj(new zzkr(this, bool));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzp(Bundle bundle, int i, long j) {
        Object obj;
        String string;
        zzb();
        zzjk zzjkVar = zzjk.zza;
        zzjj[] zzjjVarArrZzb = zzji.STORAGE.zzb();
        int length = zzjjVarArrZzb.length;
        int i2 = 0;
        while (true) {
            obj = null;
            if (i2 >= length) {
                break;
            }
            String str = zzjjVarArrZzb[i2].zze;
            if (bundle.containsKey(str) && (string = bundle.getString(str)) != null) {
                if (string.equals("granted")) {
                    obj = true;
                } else if (string.equals("denied")) {
                    obj = false;
                }
                if (obj == null) {
                    obj = string;
                    break;
                }
            }
            i2++;
        }
        if (obj != null) {
            zzib zzibVar = this.zzu;
            zzibVar.zzaV().zzh().zzb("Ignoring invalid consent setting", obj);
            zzibVar.zzaV().zzh().zza("Valid consent values are 'granted', 'denied'");
        }
        boolean zZze = this.zzu.zzaW().zze();
        zzjk zzjkVarZze = zzjk.zze(bundle, i);
        if (zzjkVarZze.zzc()) {
            zzs(zzjkVarZze, zZze);
        }
        zzaz zzazVarZzh = zzaz.zzh(bundle, i);
        if (zzazVarZzh.zzd()) {
            zzq(zzazVarZzh, zZze);
        }
        Boolean boolZzi = zzaz.zzi(bundle);
        if (boolZzi != null) {
            String str2 = i == -30 ? "tcf" : "app";
            if (zZze) {
                zzN(str2, FirebaseAnalytics.UserProperty.ALLOW_AD_PERSONALIZATION_SIGNALS, boolZzi.toString(), j);
            } else {
                zzL(str2, FirebaseAnalytics.UserProperty.ALLOW_AD_PERSONALIZATION_SIGNALS, boolZzi.toString(), false, j);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzq(zzaz zzazVar, boolean z) {
        zzks zzksVar = new zzks(this, zzazVar);
        if (!z) {
            this.zzu.zzaW().zzj(zzksVar);
        } else {
            zzg();
            zzksVar.run();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Duplicate block (B:56:0x00ce) to fix multi-entry loop: BACK_EDGE: B:56:0x00ce -> B:53:0x00cb */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:56:0x00ce
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1182)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public final void zzs(com.google.android.gms.measurement.internal.zzjk r10, boolean r11) {
        /*
            r9 = this;
            r9.zzb()
            int r0 = r10.zzb()
            r1 = -10
            if (r0 == r1) goto L2a
            com.google.android.gms.measurement.internal.zzjh r2 = r10.zzp()
            com.google.android.gms.measurement.internal.zzjh r3 = com.google.android.gms.measurement.internal.zzjh.UNINITIALIZED
            if (r2 != r3) goto L2a
            com.google.android.gms.measurement.internal.zzjh r2 = r10.zzq()
            if (r2 == r3) goto L1a
            goto L2a
        L1a:
            com.google.android.gms.measurement.internal.zzib r10 = r9.zzu
            com.google.android.gms.measurement.internal.zzgt r10 = r10.zzaV()
            com.google.android.gms.measurement.internal.zzgr r10 = r10.zzh()
            java.lang.String r11 = "Ignoring empty consent settings"
            r10.zza(r11)
            return
        L2a:
            java.lang.Object r2 = r9.zzh
            monitor-enter(r2)
            com.google.android.gms.measurement.internal.zzjk r3 = r9.zzo     // Catch: java.lang.Throwable -> Lc9
            int r3 = r3.zzb()     // Catch: java.lang.Throwable -> Lc9
            boolean r3 = com.google.android.gms.measurement.internal.zzjk.zzu(r0, r3)     // Catch: java.lang.Throwable -> Lc9
            r4 = 0
            if (r3 == 0) goto L62
            com.google.android.gms.measurement.internal.zzjk r3 = r9.zzo     // Catch: java.lang.Throwable -> L5d
            boolean r3 = r10.zzr(r3)     // Catch: java.lang.Throwable -> L5d
            com.google.android.gms.measurement.internal.zzjj r5 = com.google.android.gms.measurement.internal.zzjj.ANALYTICS_STORAGE     // Catch: java.lang.Throwable -> L5d
            boolean r6 = r10.zzo(r5)     // Catch: java.lang.Throwable -> L5d
            r7 = 1
            if (r6 == 0) goto L52
            com.google.android.gms.measurement.internal.zzjk r6 = r9.zzo     // Catch: java.lang.Throwable -> L5d
            boolean r5 = r6.zzo(r5)     // Catch: java.lang.Throwable -> L5d
            if (r5 != 0) goto L52
            r4 = r7
        L52:
            com.google.android.gms.measurement.internal.zzjk r5 = r9.zzo     // Catch: java.lang.Throwable -> L5d
            com.google.android.gms.measurement.internal.zzjk r10 = r10.zzt(r5)     // Catch: java.lang.Throwable -> L5d
            r9.zzo = r10     // Catch: java.lang.Throwable -> L5d
            r8 = r4
            r4 = r7
            goto L64
        L5d:
            r0 = move-exception
            r10 = r0
            r4 = r9
            goto Lcc
        L62:
            r3 = r4
            r8 = r3
        L64:
            r5 = r10
            monitor-exit(r2)     // Catch: java.lang.Throwable -> Lc9
            if (r4 != 0) goto L78
            com.google.android.gms.measurement.internal.zzib r10 = r9.zzu
            com.google.android.gms.measurement.internal.zzgt r10 = r10.zzaV()
            com.google.android.gms.measurement.internal.zzgr r10 = r10.zzi()
            java.lang.String r11 = "Ignoring lower-priority consent settings, proposed settings"
            r10.zzb(r11, r5)
            return
        L78:
            java.util.concurrent.atomic.AtomicLong r10 = r9.zzp
            long r6 = r10.getAndIncrement()
            if (r3 == 0) goto L9f
            java.util.concurrent.atomic.AtomicReference r10 = r9.zzg
            r0 = 0
            r10.set(r0)
            com.google.android.gms.measurement.internal.zzkt r3 = new com.google.android.gms.measurement.internal.zzkt
            r4 = r9
            r3.<init>(r4, r5, r6, r8)
            if (r11 == 0) goto L95
            r9.zzg()
            r3.run()
            return
        L95:
            com.google.android.gms.measurement.internal.zzib r10 = r4.zzu
            com.google.android.gms.measurement.internal.zzhy r10 = r10.zzaW()
            r10.zzl(r3)
            return
        L9f:
            r4 = r9
            com.google.android.gms.measurement.internal.zzku r3 = new com.google.android.gms.measurement.internal.zzku
            r3.<init>(r4, r5, r6, r8)
            if (r11 == 0) goto Lae
            r9.zzg()
            r3.run()
            return
        Lae:
            r10 = 30
            if (r0 == r10) goto Lbf
            if (r0 != r1) goto Lb5
            goto Lbf
        Lb5:
            com.google.android.gms.measurement.internal.zzib r10 = r4.zzu
            com.google.android.gms.measurement.internal.zzhy r10 = r10.zzaW()
            r10.zzj(r3)
            return
        Lbf:
            com.google.android.gms.measurement.internal.zzib r10 = r4.zzu
            com.google.android.gms.measurement.internal.zzhy r10 = r10.zzaW()
            r10.zzl(r3)
            return
        Lc9:
            r0 = move-exception
            r4 = r9
        Lcb:
            r10 = r0
        Lcc:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> Lce
            throw r10
        Lce:
            r0 = move-exception
            goto Lcb
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzli.zzs(com.google.android.gms.measurement.internal.zzjk, boolean):void");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzt(Runnable runnable) {
        zzb();
        zzib zzibVar = this.zzu;
        if (zzibVar.zzaW().zze()) {
            zzibVar.zzaV().zzb().zza("Cannot retrieve and upload batches from analytics worker thread");
            return;
        }
        if (zzibVar.zzaW().zzf()) {
            zzibVar.zzaV().zzb().zza("Cannot retrieve and upload batches from analytics network thread");
            return;
        }
        zzibVar.zzaU();
        if (zzae.zza()) {
            zzibVar.zzaV().zzb().zza("Cannot retrieve and upload batches from main thread");
            return;
        }
        zzibVar.zzaV().zzk().zza("[sgtm] Started client-side batch upload work.");
        boolean z = false;
        int size = 0;
        int i = 0;
        while (!z) {
            zzibVar.zzaV().zzk().zza("[sgtm] Getting upload batches from service (FE)");
            final AtomicReference atomicReference = new AtomicReference();
            zzibVar.zzaW().zzk(atomicReference, WorkRequest.MIN_BACKOFF_MILLIS, "[sgtm] Getting upload batches", new Runnable() { // from class: com.google.android.gms.measurement.internal.zzlh
                /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
                @Override // java.lang.Runnable
                public final /* synthetic */ void run() {
                    this.zza.zzu.zzt().zzx(atomicReference, zzon.zza(zzlr.SGTM_CLIENT));
                }
            });
            zzop zzopVar = (zzop) atomicReference.get();
            if (zzopVar == null) {
                break;
            }
            List list = zzopVar.zza;
            if (!list.isEmpty()) {
                zzibVar.zzaV().zzk().zzb("[sgtm] Retrieved upload batches. count", Integer.valueOf(list.size()));
                size += list.size();
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    zzlq zzlqVarZzaq = zzaq((zzol) it.next());
                    if (zzlqVarZzaq == zzlq.SUCCESS) {
                        i++;
                    } else if (zzlqVarZzaq == zzlq.BACKOFF) {
                        z = true;
                        break;
                    }
                }
            } else {
                break;
            }
        }
        zzibVar.zzaV().zzk().zzc("[sgtm] Completed client-side batch upload work. total, success", Integer.valueOf(size), Integer.valueOf(i));
        runnable.run();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzu(long j) {
        zzg();
        if (this.zzl == null) {
            this.zzl = new zzjt(this, this.zzu);
        }
        this.zzl.zzb(j);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzv() {
        zzg();
        zzay zzayVar = this.zzl;
        if (zzayVar != null) {
            zzayVar.zzd();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzw() {
        zzql.zza();
        zzib zzibVar = this.zzu;
        if (zzibVar.zzc().zzp(null, zzfx.zzaQ)) {
            if (zzibVar.zzaW().zze()) {
                zzibVar.zzaV().zzb().zza("Cannot get trigger URIs from analytics worker thread");
                return;
            }
            zzibVar.zzaU();
            if (zzae.zza()) {
                zzibVar.zzaV().zzb().zza("Cannot get trigger URIs from main thread");
                return;
            }
            zzb();
            zzibVar.zzaV().zzk().zza("Getting trigger URIs (FE)");
            final AtomicReference atomicReference = new AtomicReference();
            zzibVar.zzaW().zzk(atomicReference, WorkRequest.MIN_BACKOFF_MILLIS, "get trigger URIs", new Runnable() { // from class: com.google.android.gms.measurement.internal.zzkz
                /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
                @Override // java.lang.Runnable
                public final /* synthetic */ void run() {
                    zzli zzliVar = this.zza;
                    zzliVar.zzu.zzt().zzw(atomicReference, zzliVar.zzu.zzd().zzi.zza());
                }
            });
            final List list = (List) atomicReference.get();
            if (list == null) {
                zzibVar.zzaV().zzd().zza("Timed out waiting for get trigger URIs");
            } else {
                zzibVar.zzaW().zzj(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzla
                    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
                    @Override // java.lang.Runnable
                    public final /* synthetic */ void run() {
                        zzli zzliVar = this.zza;
                        zzliVar.zzg();
                        if (Build.VERSION.SDK_INT < 30) {
                            return;
                        }
                        List<zzog> list2 = list;
                        SparseArray sparseArrayZzf = zzliVar.zzu.zzd().zzf();
                        for (zzog zzogVar : list2) {
                            int i = zzogVar.zzc;
                            if (!sparseArrayZzf.contains(i) || ((Long) sparseArrayZzf.get(i)).longValue() < zzogVar.zzb) {
                                zzliVar.zzy().add(zzogVar);
                            }
                        }
                        zzliVar.zzz();
                    }
                });
            }
        }
    }

    final boolean zzx() {
        return this.zzn;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final PriorityQueue zzy() {
        if (this.zzm == null) {
            Timestamp$$ExternalSyntheticApiModelOutline0.m$1();
            this.zzm = Timestamp$$ExternalSyntheticApiModelOutline0.m(Comparator.comparing(zzlb.zza, zzlc.zza));
        }
        return this.zzm;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzz() {
        zzog zzogVar;
        zzg();
        this.zzn = false;
        if (zzy().isEmpty() || this.zzi || (zzogVar = (zzog) zzy().poll()) == null) {
            return;
        }
        zzib zzibVar = this.zzu;
        MeasurementManagerFutures measurementManagerFuturesZzT = zzibVar.zzk().zzT();
        if (measurementManagerFuturesZzT != null) {
            this.zzi = true;
            zzgr zzgrVarZzk = zzibVar.zzaV().zzk();
            String str = zzogVar.zza;
            zzgrVarZzk.zzb("Registering trigger URI", str);
            ListenableFuture<Unit> listenableFutureRegisterTriggerAsync = measurementManagerFuturesZzT.registerTriggerAsync(Uri.parse(str));
            if (listenableFutureRegisterTriggerAsync != null) {
                Futures.addCallback(listenableFutureRegisterTriggerAsync, new zzjv(this, zzogVar), new zzju(this));
            } else {
                this.zzi = false;
                zzy().add(zzogVar);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzL(String str, String str2, Object obj, boolean z, long j) {
        int iZzp;
        int i;
        int length;
        if (!z) {
            zzpo zzpoVarZzk = this.zzu.zzk();
            if (zzpoVarZzk.zzj("user property", str2)) {
                if (zzpoVarZzk.zzl("user property", zzjn.zza, null, str2)) {
                    zzpoVarZzk.zzu.zzc();
                    i = !zzpoVarZzk.zzm("user property", 24, str2) ? 6 : 0;
                } else {
                    iZzp = 15;
                }
            }
            if (i == 0) {
                zzib zzibVar = this.zzu;
                zzpo zzpoVarZzk2 = zzibVar.zzk();
                zzibVar.zzc();
                String strZzC = zzpoVarZzk2.zzC(str2, 24, true);
                length = str2 != null ? str2.length() : 0;
                this.zzu.zzk().zzN(this.zzv, null, i, "_ev", strZzC, length);
                return;
            }
            String str3 = str == null ? "app" : str;
            if (obj == null) {
                zzM(str3, str2, j, null);
                return;
            }
            zzib zzibVar2 = this.zzu;
            int iZzK = zzibVar2.zzk().zzK(str2, obj);
            if (iZzK == 0) {
                Object objZzL = zzibVar2.zzk().zzL(str2, obj);
                if (objZzL != null) {
                    zzM(str3, str2, j, objZzL);
                    return;
                }
                return;
            }
            zzpo zzpoVarZzk3 = zzibVar2.zzk();
            zzibVar2.zzc();
            String strZzC2 = zzpoVarZzk3.zzC(str2, 24, true);
            length = ((obj instanceof String) || (obj instanceof CharSequence)) ? obj.toString().length() : 0;
            this.zzu.zzk().zzN(this.zzv, null, iZzK, "_ev", strZzC2, length);
            return;
        }
        iZzp = this.zzu.zzk().zzp(str2);
        i = iZzp;
        if (i == 0) {
        }
    }
}

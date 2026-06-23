package com.google.android.gms.measurement.internal;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import androidx.core.os.EnvironmentCompat;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.InstantApps;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzgh extends zzg {
    private String zza;
    private String zzb;
    private int zzc;
    private String zzd;
    private String zze;
    private long zzf;
    private final long zzg;
    private final long zzh;
    private List zzi;
    private String zzj;
    private int zzk;
    private String zzl;
    private String zzm;
    private long zzn;
    private String zzo;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzgh(zzib zzibVar, long j, long j2) {
        super(zzibVar);
        this.zzn = 0L;
        this.zzo = null;
        this.zzg = j;
        this.zzh = j2;
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zze() {
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Can't wrap try/catch for region: R(15:0|2|(1:4)(17:79|6|(1:10)(2:11|(1:13))|83|14|(4:16|(1:18)(1:20)|77|21)|26|(2:28|(2:30|(2:32|(2:34|(2:36|(2:38|(1:40)(1:41))(1:42))(1:43))(1:44))(1:45))(1:46))(1:47)|48|81|49|(1:51)(1:52)|53|(1:55)|59|(2:62|(1:64)(4:65|(3:68|(1:86)(1:87)|66)|85|71))(1:71)|(2:73|74)(2:75|76))|5|26|(0)(0)|48|81|49|(0)(0)|53|(0)|59|(0)(0)|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0199, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x019a, code lost:
    
        r11.zzu.zzaV().zzb().zzc("Fetching Google App Id failed with exception. appId", com.google.android.gms.measurement.internal.zzgt.zzl(r1), r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0187 A[Catch: IllegalStateException -> 0x0199, TRY_LEAVE, TryCatch #2 {IllegalStateException -> 0x0199, blocks: (B:49:0x016d, B:53:0x0183, B:55:0x0187), top: B:81:0x016d }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0202  */
    @Override // com.google.android.gms.measurement.internal.zzg
    @EnsuresNonNull({"appId", "appStore", "appName", "gmpAppId", "gaAppId"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected final void zzf() {
        String str;
        String string;
        int iZzC;
        List listZzs;
        String strZza;
        zzib zzibVar = this.zzu;
        zzibVar.zzaV().zzk().zzc("sdkVersion bundled with app, dynamiteVersion", Long.valueOf(this.zzh), Long.valueOf(this.zzg));
        String packageName = zzibVar.zzaY().getPackageName();
        PackageManager packageManager = zzibVar.zzaY().getPackageManager();
        String str2 = "";
        int i = Integer.MIN_VALUE;
        String str3 = "Unknown";
        String installerPackageName = EnvironmentCompat.MEDIA_UNKNOWN;
        if (packageManager != null) {
            try {
                installerPackageName = packageManager.getInstallerPackageName(packageName);
            } catch (IllegalArgumentException unused) {
                this.zzu.zzaV().zzb().zzb("Error retrieving app installer package name. appId", zzgt.zzl(packageName));
            }
            if (installerPackageName == null) {
                installerPackageName = "manual_install";
            } else if ("com.android.vending".equals(installerPackageName)) {
                installerPackageName = "";
            }
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(this.zzu.zzaY().getPackageName(), 0);
                if (packageInfo != null) {
                    CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                    string = !TextUtils.isEmpty(applicationLabel) ? applicationLabel.toString() : "Unknown";
                    try {
                        str3 = packageInfo.versionName;
                        i = packageInfo.versionCode;
                    } catch (PackageManager.NameNotFoundException unused2) {
                        str = str3;
                        str3 = string;
                        this.zzu.zzaV().zzb().zzc("Error retrieving package info. appId, appName", zzgt.zzl(packageName), str3);
                        string = str3;
                        str3 = str;
                    }
                }
            } catch (PackageManager.NameNotFoundException unused3) {
                str = "Unknown";
            }
            this.zza = packageName;
            this.zzd = installerPackageName;
            this.zzb = str3;
            this.zzc = i;
            this.zze = string;
            this.zzf = 0L;
            zzib zzibVar2 = this.zzu;
            iZzC = zzibVar2.zzC();
            if (iZzC != 0) {
                this.zzu.zzaV().zzk().zza("App measurement collection enabled");
            } else if (iZzC == 1) {
                this.zzu.zzaV().zzi().zza("App measurement deactivated via the manifest");
            } else if (iZzC == 3) {
                this.zzu.zzaV().zzi().zza("App measurement disabled by setAnalyticsCollectionEnabled(false)");
            } else if (iZzC == 4) {
                this.zzu.zzaV().zzi().zza("App measurement disabled via the manifest");
            } else if (iZzC == 6) {
                this.zzu.zzaV().zzh().zza("App measurement deactivated via resources. This method is being deprecated. Please refer to https://firebase.google.com/support/guides/disable-analytics");
            } else if (iZzC == 7) {
                this.zzu.zzaV().zzi().zza("App measurement disabled via the global data collection setting");
            } else if (iZzC != 8) {
                zzib zzibVar3 = this.zzu;
                zzibVar3.zzaV().zzi().zza("App measurement disabled");
                zzibVar3.zzaV().zzc().zza("Invalid scion state in identity");
            } else {
                this.zzu.zzaV().zzi().zza("App measurement disabled due to denied storage consent");
            }
            this.zzl = "";
            zzib zzibVar4 = this.zzu;
            zzibVar4.zzaU();
            strZza = zzls.zza(zzibVar4.zzaY(), "google_app_id", zzibVar2.zzq());
            if (TextUtils.isEmpty(strZza)) {
                str2 = strZza;
            }
            this.zzl = str2;
            if (iZzC == 0) {
                zzibVar4.zzaV().zzk().zzc("App measurement enabled for app package, google app id", this.zza, this.zzl);
            }
            this.zzi = null;
            zzib zzibVar5 = this.zzu;
            zzibVar5.zzaU();
            listZzs = zzibVar5.zzc().zzs("analytics.safelisted_events");
            if (listZzs == null) {
                this.zzi = listZzs;
            } else if (listZzs.isEmpty()) {
                zzibVar5.zzaV().zzh().zza("Safelisted event list is empty. Ignoring");
            } else {
                Iterator it = listZzs.iterator();
                while (it.hasNext()) {
                    if (!zzibVar5.zzk().zzk("safelisted event", (String) it.next())) {
                        break;
                    }
                }
                this.zzi = listZzs;
            }
            if (packageManager == null) {
                this.zzk = InstantApps.isInstantApp(zzibVar5.zzaY()) ? 1 : 0;
                return;
            } else {
                this.zzk = 0;
                return;
            }
        }
        zzibVar.zzaV().zzb().zzb("PackageManager is null, app identity information might be inaccurate. appId", zzgt.zzl(packageName));
        string = "Unknown";
        this.zza = packageName;
        this.zzd = installerPackageName;
        this.zzb = str3;
        this.zzc = i;
        this.zze = string;
        this.zzf = 0L;
        zzib zzibVar22 = this.zzu;
        iZzC = zzibVar22.zzC();
        if (iZzC != 0) {
        }
        this.zzl = "";
        zzib zzibVar42 = this.zzu;
        zzibVar42.zzaU();
        strZza = zzls.zza(zzibVar42.zzaY(), "google_app_id", zzibVar22.zzq());
        if (TextUtils.isEmpty(strZza)) {
        }
        this.zzl = str2;
        if (iZzC == 0) {
        }
        this.zzi = null;
        zzib zzibVar52 = this.zzu;
        zzibVar52.zzaU();
        listZzs = zzibVar52.zzc().zzs("analytics.safelisted_events");
        if (listZzs == null) {
        }
        if (packageManager == null) {
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r2v0 com.google.android.gms.measurement.internal.zzr, still in use, count: 4, list:
          (r2v0 com.google.android.gms.measurement.internal.zzr) from 0x0183: MOVE (r21v0 com.google.android.gms.measurement.internal.zzr) = (r2v0 com.google.android.gms.measurement.internal.zzr)
          (r2v0 com.google.android.gms.measurement.internal.zzr) from 0x0171: MOVE (r21v2 com.google.android.gms.measurement.internal.zzr) = (r2v0 com.google.android.gms.measurement.internal.zzr)
          (r2v0 com.google.android.gms.measurement.internal.zzr) from 0x0144: MOVE (r21v4 com.google.android.gms.measurement.internal.zzr) = (r2v0 com.google.android.gms.measurement.internal.zzr)
          (r2v0 com.google.android.gms.measurement.internal.zzr) from 0x00fd: MOVE (r21v7 com.google.android.gms.measurement.internal.zzr) = (r2v0 com.google.android.gms.measurement.internal.zzr)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
        	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
        	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:463)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:97)
        */
    final com.google.android.gms.measurement.internal.zzr zzh(java.lang.String r51) {
        /*
            r50 = this;
            r1 = r50
            r1.zzg()
            com.google.android.gms.measurement.internal.zzr r2 = new com.google.android.gms.measurement.internal.zzr
            java.lang.String r3 = r1.zzj()
            java.lang.String r4 = r1.zzk()
            r1.zzb()
            java.lang.String r5 = r1.zzb
            r1.zzb()
            int r0 = r1.zzc
            long r6 = (long) r0
            r1.zzb()
            java.lang.String r0 = r1.zzd
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)
            java.lang.String r8 = r1.zzd
            com.google.android.gms.measurement.internal.zzib r0 = r1.zzu
            com.google.android.gms.measurement.internal.zzal r9 = r0.zzc()
            r9.zzi()
            r1.zzb()
            r1.zzg()
            long r9 = r1.zzf
            r11 = 0
            int r13 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            r14 = 0
            if (r13 != 0) goto Lde
            com.google.android.gms.measurement.internal.zzib r9 = r1.zzu
            com.google.android.gms.measurement.internal.zzpo r9 = r9.zzk()
            android.content.Context r10 = r0.zzaY()
            android.content.Context r0 = r0.zzaY()
            java.lang.String r0 = r0.getPackageName()
            r9.zzg()
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r10)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r0)
            android.content.pm.PackageManager r13 = r10.getPackageManager()
            java.security.MessageDigest r15 = com.google.android.gms.measurement.internal.zzpo.zzO()
            r16 = -1
            if (r15 != 0) goto L77
            com.google.android.gms.measurement.internal.zzib r0 = r9.zzu
            com.google.android.gms.measurement.internal.zzgt r0 = r0.zzaV()
            com.google.android.gms.measurement.internal.zzgr r0 = r0.zzb()
            java.lang.String r9 = "Could not get MD5 instance"
            r0.zza(r9)
            r18 = r11
        L74:
            r9 = r16
            goto Ldb
        L77:
            if (r13 == 0) goto Ld7
            boolean r0 = r9.zzad(r10, r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lc4
            if (r0 != 0) goto Lbf
            com.google.android.gms.common.wrappers.PackageManagerWrapper r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r10)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lc4
            com.google.android.gms.measurement.internal.zzib r10 = r9.zzu     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lc4
            android.content.Context r13 = r10.zzaY()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lc4
            java.lang.String r13 = r13.getPackageName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lc4
            r18 = r11
            r11 = 64
            android.content.pm.PackageInfo r0 = r0.getPackageInfo(r13, r11)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbd
            android.content.pm.Signature[] r11 = r0.signatures     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbd
            if (r11 == 0) goto Laf
            android.content.pm.Signature[] r11 = r0.signatures     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbd
            int r11 = r11.length     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbd
            if (r11 <= 0) goto Laf
            android.content.pm.Signature[] r0 = r0.signatures     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbd
            r0 = r0[r14]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbd
            byte[] r0 = r0.toByteArray()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbd
            byte[] r0 = r15.digest(r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbd
            long r16 = com.google.android.gms.measurement.internal.zzpo.zzP(r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbd
            goto L74
        Laf:
            com.google.android.gms.measurement.internal.zzgt r0 = r10.zzaV()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbd
            com.google.android.gms.measurement.internal.zzgr r0 = r0.zze()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbd
            java.lang.String r10 = "Could not get signatures"
            r0.zza(r10)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> Lbd
            goto L74
        Lbd:
            r0 = move-exception
            goto Lc7
        Lbf:
            r18 = r11
            r16 = r18
            goto L74
        Lc4:
            r0 = move-exception
            r18 = r11
        Lc7:
            com.google.android.gms.measurement.internal.zzib r9 = r9.zzu
            com.google.android.gms.measurement.internal.zzgt r9 = r9.zzaV()
            com.google.android.gms.measurement.internal.zzgr r9 = r9.zzb()
            java.lang.String r10 = "Package name not found"
            r9.zzb(r10, r0)
            goto Ld9
        Ld7:
            r18 = r11
        Ld9:
            r9 = r18
        Ldb:
            r1.zzf = r9
            goto Le0
        Lde:
            r18 = r11
        Le0:
            r11 = r9
            com.google.android.gms.measurement.internal.zzib r0 = r1.zzu
            com.google.android.gms.measurement.internal.zzib r9 = r1.zzu
            boolean r10 = r0.zzB()
            com.google.android.gms.measurement.internal.zzhg r13 = r9.zzd()
            boolean r13 = r13.zzm
            r15 = 1
            r13 = r13 ^ r15
            r1.zzg()
            boolean r0 = r0.zzB()
            r16 = r14
            r14 = 0
            if (r0 != 0) goto L101
        Lfd:
            r21 = r2
            goto L186
        L101:
            com.google.android.gms.internal.measurement.zzrg.zza()
            com.google.android.gms.measurement.internal.zzal r0 = r9.zzc()
            com.google.android.gms.measurement.internal.zzfw r15 = com.google.android.gms.measurement.internal.zzfx.zzaH
            boolean r0 = r0.zzp(r14, r15)
            if (r0 == 0) goto L120
            com.google.android.gms.measurement.internal.zzib r0 = r1.zzu
            com.google.android.gms.measurement.internal.zzgt r0 = r0.zzaV()
            com.google.android.gms.measurement.internal.zzgr r0 = r0.zzk()
            java.lang.String r9 = "Disabled IID for tests."
            r0.zza(r9)
            goto Lfd
        L120:
            android.content.Context r0 = r9.zzaY()     // Catch: java.lang.ClassNotFoundException -> L183
            java.lang.ClassLoader r0 = r0.getClassLoader()     // Catch: java.lang.ClassNotFoundException -> L183
            java.lang.String r9 = "com.google.firebase.analytics.FirebaseAnalytics"
            java.lang.Class r0 = r0.loadClass(r9)     // Catch: java.lang.ClassNotFoundException -> L183
            if (r0 != 0) goto L131
            goto Lfd
        L131:
            java.lang.String r9 = "getInstance"
            r15 = 1
            java.lang.Class[] r14 = new java.lang.Class[r15]     // Catch: java.lang.Exception -> L171
            java.lang.Class<android.content.Context> r15 = android.content.Context.class
            r14[r16] = r15     // Catch: java.lang.Exception -> L171
            java.lang.reflect.Method r9 = r0.getDeclaredMethod(r9, r14)     // Catch: java.lang.Exception -> L171
            com.google.android.gms.measurement.internal.zzib r14 = r1.zzu     // Catch: java.lang.Exception -> L171
            android.content.Context r14 = r14.zzaY()     // Catch: java.lang.Exception -> L171
            r21 = r2
            r15 = 1
            java.lang.Object[] r2 = new java.lang.Object[r15]     // Catch: java.lang.Exception -> L173
            r2[r16] = r14     // Catch: java.lang.Exception -> L173
            r14 = 0
            java.lang.Object r2 = r9.invoke(r14, r2)     // Catch: java.lang.Exception -> L173
            if (r2 != 0) goto L153
            goto L186
        L153:
            java.lang.String r9 = "getFirebaseInstanceId"
            java.lang.reflect.Method r0 = r0.getDeclaredMethod(r9, r14)     // Catch: java.lang.Exception -> L161
            java.lang.Object r0 = r0.invoke(r2, r14)     // Catch: java.lang.Exception -> L161
            r14 = r0
            java.lang.String r14 = (java.lang.String) r14     // Catch: java.lang.Exception -> L161
            goto L186
        L161:
            com.google.android.gms.measurement.internal.zzib r0 = r1.zzu
            com.google.android.gms.measurement.internal.zzgt r0 = r0.zzaV()
            com.google.android.gms.measurement.internal.zzgr r0 = r0.zzh()
            java.lang.String r2 = "Failed to retrieve Firebase Instance Id"
            r0.zza(r2)
            goto L185
        L171:
            r21 = r2
        L173:
            com.google.android.gms.measurement.internal.zzib r0 = r1.zzu
            com.google.android.gms.measurement.internal.zzgt r0 = r0.zzaV()
            com.google.android.gms.measurement.internal.zzgr r0 = r0.zzf()
            java.lang.String r2 = "Failed to obtain Firebase Analytics instance"
            r0.zza(r2)
            goto L185
        L183:
            r21 = r2
        L185:
            r14 = 0
        L186:
            com.google.android.gms.measurement.internal.zzib r0 = r1.zzu
            com.google.android.gms.measurement.internal.zzhg r2 = r0.zzd()
            com.google.android.gms.measurement.internal.zzhd r2 = r2.zzc
            r9 = r3
            long r2 = r2.zza()
            int r15 = (r2 > r18 ? 1 : (r2 == r18 ? 0 : -1))
            if (r15 != 0) goto L19d
            long r2 = r0.zza
            r15 = r4
            r22 = r5
            goto L1a6
        L19d:
            r15 = r4
            r22 = r5
            long r4 = r0.zza
            long r2 = java.lang.Math.min(r4, r2)
        L1a6:
            r1.zzb()
            int r0 = r1.zzk
            com.google.android.gms.measurement.internal.zzib r4 = r1.zzu
            com.google.android.gms.measurement.internal.zzal r5 = r4.zzc()
            boolean r5 = r5.zzu()
            com.google.android.gms.measurement.internal.zzhg r23 = r4.zzd()
            r23.zzg()
            r24 = r0
            android.content.SharedPreferences r0 = r23.zzd()
            r25 = r2
            java.lang.String r2 = "deferred_analytics_collection"
            r3 = r16
            boolean r0 = r0.getBoolean(r2, r3)
            com.google.android.gms.measurement.internal.zzal r2 = r4.zzc()
            java.lang.String r3 = "google_analytics_default_allow_ad_personalization_signals"
            r23 = r0
            r0 = 1
            com.google.android.gms.measurement.internal.zzjh r2 = r2.zzw(r3, r0)
            com.google.android.gms.measurement.internal.zzjh r0 = com.google.android.gms.measurement.internal.zzjh.GRANTED
            if (r2 == r0) goto L1df
            r0 = 1
            goto L1e0
        L1df:
            r0 = 0
        L1e0:
            r2 = r4
            r27 = r5
            long r4 = r1.zzg
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            r28 = r0
            java.util.List r0 = r1.zzi
            com.google.android.gms.measurement.internal.zzhg r29 = r2.zzd()
            com.google.android.gms.measurement.internal.zzjk r29 = r29.zzl()
            java.lang.String r29 = r29.zzl()
            r30 = r0
            java.lang.String r0 = r1.zzj
            if (r0 != 0) goto L209
            com.google.android.gms.measurement.internal.zzpo r0 = r2.zzk()
            java.lang.String r0 = r0.zzaw()
            r1.zzj = r0
        L209:
            java.lang.String r0 = r1.zzj
            com.google.android.gms.measurement.internal.zzhg r31 = r2.zzd()
            r32 = r0
            com.google.android.gms.measurement.internal.zzjk r0 = r31.zzl()
            r31 = r2
            com.google.android.gms.measurement.internal.zzjj r2 = com.google.android.gms.measurement.internal.zzjj.ANALYTICS_STORAGE
            boolean r0 = r0.zzo(r2)
            if (r0 != 0) goto L223
            r33 = r4
            r0 = 0
            goto L258
        L223:
            r1.zzg()
            r33 = r4
            long r4 = r1.zzn
            int r0 = (r4 > r18 ? 1 : (r4 == r18 ? 0 : -1))
            if (r0 != 0) goto L22f
            goto L24f
        L22f:
            com.google.android.gms.common.util.Clock r0 = r31.zzaZ()
            long r4 = r0.currentTimeMillis()
            r35 = r4
            long r4 = r1.zzn
            long r4 = r35 - r4
            java.lang.String r0 = r1.zzm
            if (r0 == 0) goto L24f
            r35 = 86400000(0x5265c00, double:4.2687272E-316)
            int r0 = (r4 > r35 ? 1 : (r4 == r35 ? 0 : -1))
            if (r0 <= 0) goto L24f
            java.lang.String r0 = r1.zzo
            if (r0 != 0) goto L24f
            r1.zzi()
        L24f:
            java.lang.String r0 = r1.zzm
            if (r0 != 0) goto L256
            r1.zzi()
        L256:
            java.lang.String r0 = r1.zzm
        L258:
            com.google.android.gms.measurement.internal.zzal r2 = r31.zzc()
            boolean r2 = r2.zzx()
            com.google.android.gms.measurement.internal.zzpo r4 = r31.zzk()
            java.lang.String r5 = r1.zzj()
            r31 = r0
            com.google.android.gms.measurement.internal.zzib r0 = r4.zzu
            android.content.Context r35 = r0.zzaY()
            android.content.pm.PackageManager r35 = r35.getPackageManager()
            if (r35 != 0) goto L27c
            r35 = r2
            r4 = r18
            r2 = 0
            goto L2a7
        L27c:
            android.content.Context r0 = r0.zzaY()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L290
            com.google.android.gms.common.wrappers.PackageManagerWrapper r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L290
            r35 = r2
            r2 = 0
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo(r5, r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L293
            if (r0 == 0) goto L2a5
            int r0 = r0.targetSdkVersion     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L293
            goto L2a6
        L290:
            r35 = r2
            r2 = 0
        L293:
            com.google.android.gms.measurement.internal.zzib r0 = r4.zzu
            r0.zzaU()
            com.google.android.gms.measurement.internal.zzgt r0 = r0.zzaV()
            com.google.android.gms.measurement.internal.zzgr r0 = r0.zzi()
            java.lang.String r4 = "PackageManager failed to find running app: app_id"
            r0.zzb(r4, r5)
        L2a5:
            r0 = r2
        L2a6:
            long r4 = (long) r0
        L2a7:
            com.google.android.gms.measurement.internal.zzib r0 = r1.zzu
            com.google.android.gms.measurement.internal.zzhg r16 = r0.zzd()
            com.google.android.gms.measurement.internal.zzjk r16 = r16.zzl()
            int r16 = r16.zzb()
            com.google.android.gms.measurement.internal.zzhg r36 = r0.zzd()
            com.google.android.gms.measurement.internal.zzaz r36 = r36.zzj()
            java.lang.String r36 = r36.zze()
            com.google.android.gms.internal.measurement.zzql.zza()
            com.google.android.gms.measurement.internal.zzal r2 = r0.zzc()
            r38 = r0
            com.google.android.gms.measurement.internal.zzfw r0 = com.google.android.gms.measurement.internal.zzfx.zzaQ
            r39 = r4
            r4 = 0
            boolean r2 = r2.zzp(r4, r0)
            if (r2 == 0) goto L2de
            com.google.android.gms.measurement.internal.zzpo r2 = r38.zzk()
            int r2 = r2.zzU()
            goto L2df
        L2de:
            r2 = 0
        L2df:
            com.google.android.gms.internal.measurement.zzql.zza()
            com.google.android.gms.measurement.internal.zzal r5 = r38.zzc()
            boolean r0 = r5.zzp(r4, r0)
            if (r0 == 0) goto L2f6
            com.google.android.gms.measurement.internal.zzpo r0 = r38.zzk()
            long r4 = r0.zzV()
            r18 = r4
        L2f6:
            com.google.android.gms.measurement.internal.zzal r0 = r38.zzc()
            java.lang.String r37 = r0.zzz()
            com.google.android.gms.measurement.internal.zzal r0 = r38.zzc()
            r4 = 1
            com.google.android.gms.measurement.internal.zzjh r0 = r0.zzw(r3, r4)
            com.google.android.gms.measurement.internal.zze r3 = new com.google.android.gms.measurement.internal.zze
            r3.<init>(r0)
            java.lang.String r38 = r3.zzb()
            com.google.android.gms.measurement.internal.zzib r0 = r1.zzu
            com.google.android.gms.measurement.internal.zzib r3 = r1.zzu
            long r4 = r0.zza
            com.google.android.gms.measurement.internal.zzlp r0 = r3.zzx()
            com.google.android.gms.internal.measurement.zzin r0 = r0.zzj()
            int r41 = r0.zza()
            r3 = r9
            r20 = r27
            r27 = r32
            r32 = r16
            r16 = r14
            r14 = r10
            r9 = 130000(0x1fbd0, double:6.42285E-319)
            r42 = r13
            r13 = r51
            r43 = r33
            r34 = r2
            r2 = r21
            r21 = r23
            r33 = r36
            r45 = r15
            r15 = r42
            r46 = r4
            r4 = r45
            r5 = r22
            r22 = r28
            r28 = r31
            r48 = r18
            r19 = r24
            r17 = r25
            r26 = r29
            r25 = r30
            r23 = r43
            r29 = r35
            r30 = r39
            r39 = r46
            r35 = r48
            r2.<init>(r3, r4, r5, r6, r8, r9, r11, r13, r14, r15, r16, r17, r19, r20, r21, r22, r23, r25, r26, r27, r28, r29, r30, r32, r33, r34, r35, r37, r38, r39, r41)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzgh.zzh(java.lang.String):com.google.android.gms.measurement.internal.zzr");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzi() {
        String str;
        zzg();
        zzib zzibVar = this.zzu;
        if (zzibVar.zzd().zzl().zzo(zzjj.ANALYTICS_STORAGE)) {
            byte[] bArr = new byte[16];
            zzibVar.zzk().zzf().nextBytes(bArr);
            str = String.format(Locale.US, "%032x", new BigInteger(1, bArr));
        } else {
            zzibVar.zzaV().zzj().zza("Analytics Storage consent is not granted");
            str = null;
        }
        zzibVar.zzaV().zzj().zza(String.format("Resetting session stitching token to %s", str == null ? "null" : "not null"));
        this.zzm = str;
        this.zzn = zzibVar.zzaZ().currentTimeMillis();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final String zzj() {
        zzb();
        Preconditions.checkNotNull(this.zza);
        return this.zza;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final String zzk() {
        zzg();
        zzb();
        Preconditions.checkNotNull(this.zzl);
        return this.zzl;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final String zzl() {
        zzb();
        Preconditions.checkNotNull(this.zze);
        return this.zze;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final int zzm() {
        zzb();
        return this.zzc;
    }

    final long zzn() {
        return this.zzh;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final int zzo() {
        zzb();
        return this.zzk;
    }

    final List zzp() {
        return this.zzi;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final boolean zzq(String str) {
        String str2 = this.zzo;
        boolean z = false;
        if (str2 != null && !str2.equals(str)) {
            z = true;
        }
        this.zzo = str;
        return z;
    }
}

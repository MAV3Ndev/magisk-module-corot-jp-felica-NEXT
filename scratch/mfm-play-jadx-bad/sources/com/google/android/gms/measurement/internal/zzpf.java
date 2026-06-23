package com.google.android.gms.measurement.internal;

import android.app.BroadcastOptions;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import androidx.collection.ArrayMap;
import androidx.work.WorkRequest;
import com.amazonaws.mobileconnectors.pinpoint.internal.event.ClientContext;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzql;
import com.google.android.gms.internal.measurement.zzqu;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.common.net.HttpHeaders;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.GZIPInputStream;
import kotlin.time.DurationKt;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzpf implements zzjf {
    private static volatile zzpf zzb;
    private List zzA;
    private long zzB;
    private final Map zzC;
    private final Map zzD;
    private final Map zzE;
    private zzlt zzG;
    private String zzH;
    private zzay zzI;
    private long zzJ;
    long zza;
    private final zzhs zzc;
    private final zzgy zzd;
    private zzav zze;
    private zzha zzf;
    private zzoj zzg;
    private zzad zzh;
    private final zzpj zzi;
    private zzlo zzj;
    private zznm zzk;
    private final zzot zzl;
    private zzhj zzm;
    private final zzib zzn;
    private boolean zzp;
    private List zzq;
    private int zzs;
    private int zzt;
    private boolean zzu;
    private boolean zzv;
    private boolean zzw;
    private FileLock zzx;
    private FileChannel zzy;
    private List zzz;
    private final AtomicBoolean zzo = new AtomicBoolean(false);
    private final Deque zzr = new LinkedList();
    private final Map zzF = new HashMap();
    private final zzpn zzK = new zzpa(this);

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzpf(zzpg zzpgVar, zzib zzibVar) {
        Preconditions.checkNotNull(zzpgVar);
        this.zzn = zzib.zzy(zzpgVar.zza, null, null);
        this.zzB = -1L;
        this.zzl = new zzot(this);
        zzpj zzpjVar = new zzpj(this);
        zzpjVar.zzaz();
        this.zzi = zzpjVar;
        zzgy zzgyVar = new zzgy(this);
        zzgyVar.zzaz();
        this.zzd = zzgyVar;
        zzhs zzhsVar = new zzhs(this);
        zzhsVar.zzaz();
        this.zzc = zzhsVar;
        this.zzC = new HashMap();
        this.zzD = new HashMap();
        this.zzE = new HashMap();
        zzaW().zzj(new zzou(this, zzpgVar));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static zzpf zza(Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzb == null) {
            synchronized (zzpf.class) {
                if (zzb == null) {
                    zzb = new zzpf((zzpg) Preconditions.checkNotNull(new zzpg(context)), null);
                }
            }
        }
        return zzb;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static final void zzaA(com.google.android.gms.internal.measurement.zzhr zzhrVar, String str) {
        List listZza = zzhrVar.zza();
        for (int i = 0; i < listZza.size(); i++) {
            if (str.equals(((com.google.android.gms.internal.measurement.zzhw) listZza.get(i)).zzb())) {
                zzhrVar.zzj(i);
                return;
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final int zzaB(String str, zzan zzanVar) {
        zzjj zzjjVar;
        zzjh zzjhVarZzA;
        zzhs zzhsVar = this.zzc;
        if (zzhsVar.zzx(str) == null) {
            zzanVar.zzc(zzjj.AD_PERSONALIZATION, zzam.FAILSAFE);
            return 1;
        }
        zzh zzhVarZzu = zzj().zzu(str);
        if (zzhVarZzu != null && zze.zzc(zzhVarZzu.zzaH()).zza() == zzjh.POLICY && (zzjhVarZzA = zzhsVar.zzA(str, (zzjjVar = zzjj.AD_PERSONALIZATION))) != zzjh.UNINITIALIZED) {
            zzanVar.zzc(zzjjVar, zzam.REMOTE_ENFORCED_DEFAULT);
            return zzjhVarZzA == zzjh.GRANTED ? 0 : 1;
        }
        zzjj zzjjVar2 = zzjj.AD_PERSONALIZATION;
        zzanVar.zzc(zzjjVar2, zzam.REMOTE_DEFAULT);
        return zzhsVar.zzv(str, zzjjVar2) ? 0 : 1;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final Map zzaC(com.google.android.gms.internal.measurement.zzhs zzhsVar) {
        HashMap map = new HashMap();
        zzp();
        for (Map.Entry entry : zzpj.zzH(zzhsVar, "gad_").entrySet()) {
            map.put((String) entry.getKey(), String.valueOf(entry.getValue()));
        }
        return map;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final zzay zzaD() {
        if (this.zzI == null) {
            this.zzI = new zzox(this, this.zzn);
        }
        return this.zzI;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Method merged with bridge method: zzau()V */
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: zzaE, reason: merged with bridge method [inline-methods] */
    public final void zzau() {
        zzaW().zzg();
        if (this.zzr.isEmpty() || zzaD().zzc()) {
            return;
        }
        long jMax = Math.max(0L, ((long) ((Integer) zzfx.zzaB.zzb(null)).intValue()) - (zzaZ().elapsedRealtime() - this.zzJ));
        zzaV().zzk().zzb("Scheduling notify next app runnable, delay in ms", Long.valueOf(jMax));
        zzaD().zzb(jMax);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:111:0x03ab A[Catch: all -> 0x0ead, TryCatch #2 {all -> 0x0ead, blocks: (B:3:0x000f, B:5:0x0026, B:8:0x002e, B:9:0x0041, B:12:0x005b, B:15:0x0083, B:17:0x00b8, B:20:0x00c9, B:22:0x00d3, B:207:0x066e, B:24:0x0100, B:27:0x0112, B:29:0x0118, B:44:0x015a, B:46:0x0168, B:49:0x0188, B:51:0x018e, B:53:0x019e, B:55:0x01ac, B:57:0x01bc, B:58:0x01c9, B:59:0x01cc, B:61:0x01e0, B:114:0x03dc, B:115:0x03e8, B:118:0x03f4, B:124:0x0417, B:121:0x0406, B:147:0x049a, B:149:0x04a6, B:152:0x04b7, B:154:0x04c9, B:156:0x04d5, B:173:0x0543, B:175:0x0549, B:176:0x0555, B:178:0x055b, B:180:0x056b, B:182:0x0575, B:183:0x058a, B:185:0x0590, B:186:0x05ab, B:188:0x05b1, B:189:0x05cf, B:190:0x05d8, B:194:0x05fd, B:191:0x05dc, B:193:0x05ea, B:195:0x0606, B:196:0x061e, B:198:0x0624, B:200:0x0637, B:201:0x0644, B:202:0x0648, B:204:0x064e, B:206:0x065c, B:160:0x04eb, B:162:0x04f9, B:165:0x050c, B:167:0x051e, B:169:0x052a, B:128:0x0421, B:130:0x042d, B:132:0x0439, B:144:0x047e, B:136:0x0456, B:139:0x0468, B:141:0x046e, B:143:0x0478, B:70:0x020d, B:73:0x0217, B:75:0x0225, B:79:0x026c, B:76:0x0242, B:78:0x0252, B:83:0x0279, B:86:0x02a8, B:87:0x02d2, B:89:0x030b, B:91:0x0313, B:94:0x031f, B:96:0x0356, B:97:0x0371, B:99:0x0377, B:101:0x0387, B:105:0x039d, B:102:0x0391, B:108:0x03a4, B:111:0x03ab, B:112:0x03c3, B:33:0x0122, B:35:0x012f, B:37:0x013b, B:39:0x0141, B:43:0x014c, B:210:0x0686, B:212:0x0694, B:214:0x069d, B:225:0x06ce, B:215:0x06a5, B:217:0x06ae, B:219:0x06b4, B:222:0x06c0, B:224:0x06c8, B:226:0x06d1, B:227:0x06dd, B:230:0x06e5, B:232:0x06f7, B:233:0x0702, B:235:0x070a, B:239:0x0730, B:241:0x074a, B:243:0x075f, B:245:0x0779, B:247:0x078e, B:249:0x07d3, B:251:0x07d9, B:257:0x0800, B:259:0x0808, B:260:0x0811, B:262:0x0817, B:263:0x081d, B:265:0x0832, B:267:0x0842, B:269:0x0852, B:272:0x085b, B:274:0x0861, B:275:0x0873, B:277:0x0879, B:279:0x0889, B:281:0x08a1, B:283:0x08b3, B:285:0x08da, B:286:0x08f3, B:288:0x0905, B:290:0x0924, B:292:0x094b, B:294:0x097b, B:295:0x0986, B:297:0x0998, B:299:0x09b7, B:301:0x09de, B:303:0x0a0e, B:304:0x0a17, B:305:0x0a20, B:306:0x0a24, B:314:0x0a98, B:316:0x0ab1, B:318:0x0ac7, B:320:0x0acc, B:322:0x0ad0, B:324:0x0ad4, B:326:0x0ade, B:327:0x0ae4, B:329:0x0ae8, B:331:0x0aee, B:332:0x0afc, B:333:0x0b05, B:338:0x0b2a, B:341:0x0b30, B:439:0x0e1f, B:441:0x0e34, B:444:0x0e3b, B:449:0x0e6c, B:451:0x0e7e, B:445:0x0e43, B:447:0x0e4f, B:448:0x0e55, B:252:0x07e7, B:254:0x07ed, B:256:0x07f3, B:246:0x078b, B:242:0x075c, B:236:0x0710, B:238:0x0716, B:454:0x0e9c), top: B:465:0x000f, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x03c3 A[Catch: all -> 0x0ead, TryCatch #2 {all -> 0x0ead, blocks: (B:3:0x000f, B:5:0x0026, B:8:0x002e, B:9:0x0041, B:12:0x005b, B:15:0x0083, B:17:0x00b8, B:20:0x00c9, B:22:0x00d3, B:207:0x066e, B:24:0x0100, B:27:0x0112, B:29:0x0118, B:44:0x015a, B:46:0x0168, B:49:0x0188, B:51:0x018e, B:53:0x019e, B:55:0x01ac, B:57:0x01bc, B:58:0x01c9, B:59:0x01cc, B:61:0x01e0, B:114:0x03dc, B:115:0x03e8, B:118:0x03f4, B:124:0x0417, B:121:0x0406, B:147:0x049a, B:149:0x04a6, B:152:0x04b7, B:154:0x04c9, B:156:0x04d5, B:173:0x0543, B:175:0x0549, B:176:0x0555, B:178:0x055b, B:180:0x056b, B:182:0x0575, B:183:0x058a, B:185:0x0590, B:186:0x05ab, B:188:0x05b1, B:189:0x05cf, B:190:0x05d8, B:194:0x05fd, B:191:0x05dc, B:193:0x05ea, B:195:0x0606, B:196:0x061e, B:198:0x0624, B:200:0x0637, B:201:0x0644, B:202:0x0648, B:204:0x064e, B:206:0x065c, B:160:0x04eb, B:162:0x04f9, B:165:0x050c, B:167:0x051e, B:169:0x052a, B:128:0x0421, B:130:0x042d, B:132:0x0439, B:144:0x047e, B:136:0x0456, B:139:0x0468, B:141:0x046e, B:143:0x0478, B:70:0x020d, B:73:0x0217, B:75:0x0225, B:79:0x026c, B:76:0x0242, B:78:0x0252, B:83:0x0279, B:86:0x02a8, B:87:0x02d2, B:89:0x030b, B:91:0x0313, B:94:0x031f, B:96:0x0356, B:97:0x0371, B:99:0x0377, B:101:0x0387, B:105:0x039d, B:102:0x0391, B:108:0x03a4, B:111:0x03ab, B:112:0x03c3, B:33:0x0122, B:35:0x012f, B:37:0x013b, B:39:0x0141, B:43:0x014c, B:210:0x0686, B:212:0x0694, B:214:0x069d, B:225:0x06ce, B:215:0x06a5, B:217:0x06ae, B:219:0x06b4, B:222:0x06c0, B:224:0x06c8, B:226:0x06d1, B:227:0x06dd, B:230:0x06e5, B:232:0x06f7, B:233:0x0702, B:235:0x070a, B:239:0x0730, B:241:0x074a, B:243:0x075f, B:245:0x0779, B:247:0x078e, B:249:0x07d3, B:251:0x07d9, B:257:0x0800, B:259:0x0808, B:260:0x0811, B:262:0x0817, B:263:0x081d, B:265:0x0832, B:267:0x0842, B:269:0x0852, B:272:0x085b, B:274:0x0861, B:275:0x0873, B:277:0x0879, B:279:0x0889, B:281:0x08a1, B:283:0x08b3, B:285:0x08da, B:286:0x08f3, B:288:0x0905, B:290:0x0924, B:292:0x094b, B:294:0x097b, B:295:0x0986, B:297:0x0998, B:299:0x09b7, B:301:0x09de, B:303:0x0a0e, B:304:0x0a17, B:305:0x0a20, B:306:0x0a24, B:314:0x0a98, B:316:0x0ab1, B:318:0x0ac7, B:320:0x0acc, B:322:0x0ad0, B:324:0x0ad4, B:326:0x0ade, B:327:0x0ae4, B:329:0x0ae8, B:331:0x0aee, B:332:0x0afc, B:333:0x0b05, B:338:0x0b2a, B:341:0x0b30, B:439:0x0e1f, B:441:0x0e34, B:444:0x0e3b, B:449:0x0e6c, B:451:0x0e7e, B:445:0x0e43, B:447:0x0e4f, B:448:0x0e55, B:252:0x07e7, B:254:0x07ed, B:256:0x07f3, B:246:0x078b, B:242:0x075c, B:236:0x0710, B:238:0x0716, B:454:0x0e9c), top: B:465:0x000f, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x04a6 A[Catch: all -> 0x0ead, TryCatch #2 {all -> 0x0ead, blocks: (B:3:0x000f, B:5:0x0026, B:8:0x002e, B:9:0x0041, B:12:0x005b, B:15:0x0083, B:17:0x00b8, B:20:0x00c9, B:22:0x00d3, B:207:0x066e, B:24:0x0100, B:27:0x0112, B:29:0x0118, B:44:0x015a, B:46:0x0168, B:49:0x0188, B:51:0x018e, B:53:0x019e, B:55:0x01ac, B:57:0x01bc, B:58:0x01c9, B:59:0x01cc, B:61:0x01e0, B:114:0x03dc, B:115:0x03e8, B:118:0x03f4, B:124:0x0417, B:121:0x0406, B:147:0x049a, B:149:0x04a6, B:152:0x04b7, B:154:0x04c9, B:156:0x04d5, B:173:0x0543, B:175:0x0549, B:176:0x0555, B:178:0x055b, B:180:0x056b, B:182:0x0575, B:183:0x058a, B:185:0x0590, B:186:0x05ab, B:188:0x05b1, B:189:0x05cf, B:190:0x05d8, B:194:0x05fd, B:191:0x05dc, B:193:0x05ea, B:195:0x0606, B:196:0x061e, B:198:0x0624, B:200:0x0637, B:201:0x0644, B:202:0x0648, B:204:0x064e, B:206:0x065c, B:160:0x04eb, B:162:0x04f9, B:165:0x050c, B:167:0x051e, B:169:0x052a, B:128:0x0421, B:130:0x042d, B:132:0x0439, B:144:0x047e, B:136:0x0456, B:139:0x0468, B:141:0x046e, B:143:0x0478, B:70:0x020d, B:73:0x0217, B:75:0x0225, B:79:0x026c, B:76:0x0242, B:78:0x0252, B:83:0x0279, B:86:0x02a8, B:87:0x02d2, B:89:0x030b, B:91:0x0313, B:94:0x031f, B:96:0x0356, B:97:0x0371, B:99:0x0377, B:101:0x0387, B:105:0x039d, B:102:0x0391, B:108:0x03a4, B:111:0x03ab, B:112:0x03c3, B:33:0x0122, B:35:0x012f, B:37:0x013b, B:39:0x0141, B:43:0x014c, B:210:0x0686, B:212:0x0694, B:214:0x069d, B:225:0x06ce, B:215:0x06a5, B:217:0x06ae, B:219:0x06b4, B:222:0x06c0, B:224:0x06c8, B:226:0x06d1, B:227:0x06dd, B:230:0x06e5, B:232:0x06f7, B:233:0x0702, B:235:0x070a, B:239:0x0730, B:241:0x074a, B:243:0x075f, B:245:0x0779, B:247:0x078e, B:249:0x07d3, B:251:0x07d9, B:257:0x0800, B:259:0x0808, B:260:0x0811, B:262:0x0817, B:263:0x081d, B:265:0x0832, B:267:0x0842, B:269:0x0852, B:272:0x085b, B:274:0x0861, B:275:0x0873, B:277:0x0879, B:279:0x0889, B:281:0x08a1, B:283:0x08b3, B:285:0x08da, B:286:0x08f3, B:288:0x0905, B:290:0x0924, B:292:0x094b, B:294:0x097b, B:295:0x0986, B:297:0x0998, B:299:0x09b7, B:301:0x09de, B:303:0x0a0e, B:304:0x0a17, B:305:0x0a20, B:306:0x0a24, B:314:0x0a98, B:316:0x0ab1, B:318:0x0ac7, B:320:0x0acc, B:322:0x0ad0, B:324:0x0ad4, B:326:0x0ade, B:327:0x0ae4, B:329:0x0ae8, B:331:0x0aee, B:332:0x0afc, B:333:0x0b05, B:338:0x0b2a, B:341:0x0b30, B:439:0x0e1f, B:441:0x0e34, B:444:0x0e3b, B:449:0x0e6c, B:451:0x0e7e, B:445:0x0e43, B:447:0x0e4f, B:448:0x0e55, B:252:0x07e7, B:254:0x07ed, B:256:0x07f3, B:246:0x078b, B:242:0x075c, B:236:0x0710, B:238:0x0716, B:454:0x0e9c), top: B:465:0x000f, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x04eb A[Catch: all -> 0x0ead, TryCatch #2 {all -> 0x0ead, blocks: (B:3:0x000f, B:5:0x0026, B:8:0x002e, B:9:0x0041, B:12:0x005b, B:15:0x0083, B:17:0x00b8, B:20:0x00c9, B:22:0x00d3, B:207:0x066e, B:24:0x0100, B:27:0x0112, B:29:0x0118, B:44:0x015a, B:46:0x0168, B:49:0x0188, B:51:0x018e, B:53:0x019e, B:55:0x01ac, B:57:0x01bc, B:58:0x01c9, B:59:0x01cc, B:61:0x01e0, B:114:0x03dc, B:115:0x03e8, B:118:0x03f4, B:124:0x0417, B:121:0x0406, B:147:0x049a, B:149:0x04a6, B:152:0x04b7, B:154:0x04c9, B:156:0x04d5, B:173:0x0543, B:175:0x0549, B:176:0x0555, B:178:0x055b, B:180:0x056b, B:182:0x0575, B:183:0x058a, B:185:0x0590, B:186:0x05ab, B:188:0x05b1, B:189:0x05cf, B:190:0x05d8, B:194:0x05fd, B:191:0x05dc, B:193:0x05ea, B:195:0x0606, B:196:0x061e, B:198:0x0624, B:200:0x0637, B:201:0x0644, B:202:0x0648, B:204:0x064e, B:206:0x065c, B:160:0x04eb, B:162:0x04f9, B:165:0x050c, B:167:0x051e, B:169:0x052a, B:128:0x0421, B:130:0x042d, B:132:0x0439, B:144:0x047e, B:136:0x0456, B:139:0x0468, B:141:0x046e, B:143:0x0478, B:70:0x020d, B:73:0x0217, B:75:0x0225, B:79:0x026c, B:76:0x0242, B:78:0x0252, B:83:0x0279, B:86:0x02a8, B:87:0x02d2, B:89:0x030b, B:91:0x0313, B:94:0x031f, B:96:0x0356, B:97:0x0371, B:99:0x0377, B:101:0x0387, B:105:0x039d, B:102:0x0391, B:108:0x03a4, B:111:0x03ab, B:112:0x03c3, B:33:0x0122, B:35:0x012f, B:37:0x013b, B:39:0x0141, B:43:0x014c, B:210:0x0686, B:212:0x0694, B:214:0x069d, B:225:0x06ce, B:215:0x06a5, B:217:0x06ae, B:219:0x06b4, B:222:0x06c0, B:224:0x06c8, B:226:0x06d1, B:227:0x06dd, B:230:0x06e5, B:232:0x06f7, B:233:0x0702, B:235:0x070a, B:239:0x0730, B:241:0x074a, B:243:0x075f, B:245:0x0779, B:247:0x078e, B:249:0x07d3, B:251:0x07d9, B:257:0x0800, B:259:0x0808, B:260:0x0811, B:262:0x0817, B:263:0x081d, B:265:0x0832, B:267:0x0842, B:269:0x0852, B:272:0x085b, B:274:0x0861, B:275:0x0873, B:277:0x0879, B:279:0x0889, B:281:0x08a1, B:283:0x08b3, B:285:0x08da, B:286:0x08f3, B:288:0x0905, B:290:0x0924, B:292:0x094b, B:294:0x097b, B:295:0x0986, B:297:0x0998, B:299:0x09b7, B:301:0x09de, B:303:0x0a0e, B:304:0x0a17, B:305:0x0a20, B:306:0x0a24, B:314:0x0a98, B:316:0x0ab1, B:318:0x0ac7, B:320:0x0acc, B:322:0x0ad0, B:324:0x0ad4, B:326:0x0ade, B:327:0x0ae4, B:329:0x0ae8, B:331:0x0aee, B:332:0x0afc, B:333:0x0b05, B:338:0x0b2a, B:341:0x0b30, B:439:0x0e1f, B:441:0x0e34, B:444:0x0e3b, B:449:0x0e6c, B:451:0x0e7e, B:445:0x0e43, B:447:0x0e4f, B:448:0x0e55, B:252:0x07e7, B:254:0x07ed, B:256:0x07f3, B:246:0x078b, B:242:0x075c, B:236:0x0710, B:238:0x0716, B:454:0x0e9c), top: B:465:0x000f, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0549 A[Catch: all -> 0x0ead, TryCatch #2 {all -> 0x0ead, blocks: (B:3:0x000f, B:5:0x0026, B:8:0x002e, B:9:0x0041, B:12:0x005b, B:15:0x0083, B:17:0x00b8, B:20:0x00c9, B:22:0x00d3, B:207:0x066e, B:24:0x0100, B:27:0x0112, B:29:0x0118, B:44:0x015a, B:46:0x0168, B:49:0x0188, B:51:0x018e, B:53:0x019e, B:55:0x01ac, B:57:0x01bc, B:58:0x01c9, B:59:0x01cc, B:61:0x01e0, B:114:0x03dc, B:115:0x03e8, B:118:0x03f4, B:124:0x0417, B:121:0x0406, B:147:0x049a, B:149:0x04a6, B:152:0x04b7, B:154:0x04c9, B:156:0x04d5, B:173:0x0543, B:175:0x0549, B:176:0x0555, B:178:0x055b, B:180:0x056b, B:182:0x0575, B:183:0x058a, B:185:0x0590, B:186:0x05ab, B:188:0x05b1, B:189:0x05cf, B:190:0x05d8, B:194:0x05fd, B:191:0x05dc, B:193:0x05ea, B:195:0x0606, B:196:0x061e, B:198:0x0624, B:200:0x0637, B:201:0x0644, B:202:0x0648, B:204:0x064e, B:206:0x065c, B:160:0x04eb, B:162:0x04f9, B:165:0x050c, B:167:0x051e, B:169:0x052a, B:128:0x0421, B:130:0x042d, B:132:0x0439, B:144:0x047e, B:136:0x0456, B:139:0x0468, B:141:0x046e, B:143:0x0478, B:70:0x020d, B:73:0x0217, B:75:0x0225, B:79:0x026c, B:76:0x0242, B:78:0x0252, B:83:0x0279, B:86:0x02a8, B:87:0x02d2, B:89:0x030b, B:91:0x0313, B:94:0x031f, B:96:0x0356, B:97:0x0371, B:99:0x0377, B:101:0x0387, B:105:0x039d, B:102:0x0391, B:108:0x03a4, B:111:0x03ab, B:112:0x03c3, B:33:0x0122, B:35:0x012f, B:37:0x013b, B:39:0x0141, B:43:0x014c, B:210:0x0686, B:212:0x0694, B:214:0x069d, B:225:0x06ce, B:215:0x06a5, B:217:0x06ae, B:219:0x06b4, B:222:0x06c0, B:224:0x06c8, B:226:0x06d1, B:227:0x06dd, B:230:0x06e5, B:232:0x06f7, B:233:0x0702, B:235:0x070a, B:239:0x0730, B:241:0x074a, B:243:0x075f, B:245:0x0779, B:247:0x078e, B:249:0x07d3, B:251:0x07d9, B:257:0x0800, B:259:0x0808, B:260:0x0811, B:262:0x0817, B:263:0x081d, B:265:0x0832, B:267:0x0842, B:269:0x0852, B:272:0x085b, B:274:0x0861, B:275:0x0873, B:277:0x0879, B:279:0x0889, B:281:0x08a1, B:283:0x08b3, B:285:0x08da, B:286:0x08f3, B:288:0x0905, B:290:0x0924, B:292:0x094b, B:294:0x097b, B:295:0x0986, B:297:0x0998, B:299:0x09b7, B:301:0x09de, B:303:0x0a0e, B:304:0x0a17, B:305:0x0a20, B:306:0x0a24, B:314:0x0a98, B:316:0x0ab1, B:318:0x0ac7, B:320:0x0acc, B:322:0x0ad0, B:324:0x0ad4, B:326:0x0ade, B:327:0x0ae4, B:329:0x0ae8, B:331:0x0aee, B:332:0x0afc, B:333:0x0b05, B:338:0x0b2a, B:341:0x0b30, B:439:0x0e1f, B:441:0x0e34, B:444:0x0e3b, B:449:0x0e6c, B:451:0x0e7e, B:445:0x0e43, B:447:0x0e4f, B:448:0x0e55, B:252:0x07e7, B:254:0x07ed, B:256:0x07f3, B:246:0x078b, B:242:0x075c, B:236:0x0710, B:238:0x0716, B:454:0x0e9c), top: B:465:0x000f, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0658  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x06a5 A[Catch: all -> 0x0ead, TryCatch #2 {all -> 0x0ead, blocks: (B:3:0x000f, B:5:0x0026, B:8:0x002e, B:9:0x0041, B:12:0x005b, B:15:0x0083, B:17:0x00b8, B:20:0x00c9, B:22:0x00d3, B:207:0x066e, B:24:0x0100, B:27:0x0112, B:29:0x0118, B:44:0x015a, B:46:0x0168, B:49:0x0188, B:51:0x018e, B:53:0x019e, B:55:0x01ac, B:57:0x01bc, B:58:0x01c9, B:59:0x01cc, B:61:0x01e0, B:114:0x03dc, B:115:0x03e8, B:118:0x03f4, B:124:0x0417, B:121:0x0406, B:147:0x049a, B:149:0x04a6, B:152:0x04b7, B:154:0x04c9, B:156:0x04d5, B:173:0x0543, B:175:0x0549, B:176:0x0555, B:178:0x055b, B:180:0x056b, B:182:0x0575, B:183:0x058a, B:185:0x0590, B:186:0x05ab, B:188:0x05b1, B:189:0x05cf, B:190:0x05d8, B:194:0x05fd, B:191:0x05dc, B:193:0x05ea, B:195:0x0606, B:196:0x061e, B:198:0x0624, B:200:0x0637, B:201:0x0644, B:202:0x0648, B:204:0x064e, B:206:0x065c, B:160:0x04eb, B:162:0x04f9, B:165:0x050c, B:167:0x051e, B:169:0x052a, B:128:0x0421, B:130:0x042d, B:132:0x0439, B:144:0x047e, B:136:0x0456, B:139:0x0468, B:141:0x046e, B:143:0x0478, B:70:0x020d, B:73:0x0217, B:75:0x0225, B:79:0x026c, B:76:0x0242, B:78:0x0252, B:83:0x0279, B:86:0x02a8, B:87:0x02d2, B:89:0x030b, B:91:0x0313, B:94:0x031f, B:96:0x0356, B:97:0x0371, B:99:0x0377, B:101:0x0387, B:105:0x039d, B:102:0x0391, B:108:0x03a4, B:111:0x03ab, B:112:0x03c3, B:33:0x0122, B:35:0x012f, B:37:0x013b, B:39:0x0141, B:43:0x014c, B:210:0x0686, B:212:0x0694, B:214:0x069d, B:225:0x06ce, B:215:0x06a5, B:217:0x06ae, B:219:0x06b4, B:222:0x06c0, B:224:0x06c8, B:226:0x06d1, B:227:0x06dd, B:230:0x06e5, B:232:0x06f7, B:233:0x0702, B:235:0x070a, B:239:0x0730, B:241:0x074a, B:243:0x075f, B:245:0x0779, B:247:0x078e, B:249:0x07d3, B:251:0x07d9, B:257:0x0800, B:259:0x0808, B:260:0x0811, B:262:0x0817, B:263:0x081d, B:265:0x0832, B:267:0x0842, B:269:0x0852, B:272:0x085b, B:274:0x0861, B:275:0x0873, B:277:0x0879, B:279:0x0889, B:281:0x08a1, B:283:0x08b3, B:285:0x08da, B:286:0x08f3, B:288:0x0905, B:290:0x0924, B:292:0x094b, B:294:0x097b, B:295:0x0986, B:297:0x0998, B:299:0x09b7, B:301:0x09de, B:303:0x0a0e, B:304:0x0a17, B:305:0x0a20, B:306:0x0a24, B:314:0x0a98, B:316:0x0ab1, B:318:0x0ac7, B:320:0x0acc, B:322:0x0ad0, B:324:0x0ad4, B:326:0x0ade, B:327:0x0ae4, B:329:0x0ae8, B:331:0x0aee, B:332:0x0afc, B:333:0x0b05, B:338:0x0b2a, B:341:0x0b30, B:439:0x0e1f, B:441:0x0e34, B:444:0x0e3b, B:449:0x0e6c, B:451:0x0e7e, B:445:0x0e43, B:447:0x0e4f, B:448:0x0e55, B:252:0x07e7, B:254:0x07ed, B:256:0x07f3, B:246:0x078b, B:242:0x075c, B:236:0x0710, B:238:0x0716, B:454:0x0e9c), top: B:465:0x000f, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:345:0x0b66 A[Catch: all -> 0x0e98, TryCatch #0 {all -> 0x0e98, blocks: (B:308:0x0a66, B:309:0x0a79, B:311:0x0a7f, B:405:0x0d46, B:336:0x0b14, B:343:0x0b44, B:345:0x0b66, B:346:0x0b6e, B:348:0x0b74, B:350:0x0b84, B:357:0x0bac, B:359:0x0bd3, B:361:0x0bdf, B:363:0x0bf5, B:366:0x0c38, B:370:0x0c4e, B:372:0x0c55, B:374:0x0c63, B:376:0x0c67, B:378:0x0c6b, B:380:0x0c6f, B:381:0x0c7b, B:382:0x0c80, B:384:0x0c86, B:386:0x0ca0, B:387:0x0ca5, B:404:0x0d43, B:388:0x0cbc, B:390:0x0cc1, B:394:0x0ce8, B:396:0x0d0b, B:398:0x0d19, B:399:0x0d2c, B:401:0x0d33, B:391:0x0cce, B:355:0x0b98, B:406:0x0d50, B:408:0x0d5e, B:409:0x0d64, B:410:0x0d6c, B:412:0x0d72, B:414:0x0d8a, B:416:0x0d9a, B:436:0x0e17, B:417:0x0db2, B:419:0x0db8, B:421:0x0dc0, B:423:0x0dc7, B:429:0x0dd5, B:431:0x0ddc, B:433:0x0e08, B:435:0x0e0f, B:434:0x0e0c, B:430:0x0dd9, B:422:0x0dc4), top: B:461:0x0a66 }] */
    /* JADX WARN: Removed duplicated region for block: B:355:0x0b98 A[Catch: all -> 0x0e98, EDGE_INSN: B:517:0x0b98->B:355:0x0b98 BREAK  A[LOOP:16: B:346:0x0b6e->B:354:0x0b95], TryCatch #0 {all -> 0x0e98, blocks: (B:308:0x0a66, B:309:0x0a79, B:311:0x0a7f, B:405:0x0d46, B:336:0x0b14, B:343:0x0b44, B:345:0x0b66, B:346:0x0b6e, B:348:0x0b74, B:350:0x0b84, B:357:0x0bac, B:359:0x0bd3, B:361:0x0bdf, B:363:0x0bf5, B:366:0x0c38, B:370:0x0c4e, B:372:0x0c55, B:374:0x0c63, B:376:0x0c67, B:378:0x0c6b, B:380:0x0c6f, B:381:0x0c7b, B:382:0x0c80, B:384:0x0c86, B:386:0x0ca0, B:387:0x0ca5, B:404:0x0d43, B:388:0x0cbc, B:390:0x0cc1, B:394:0x0ce8, B:396:0x0d0b, B:398:0x0d19, B:399:0x0d2c, B:401:0x0d33, B:391:0x0cce, B:355:0x0b98, B:406:0x0d50, B:408:0x0d5e, B:409:0x0d64, B:410:0x0d6c, B:412:0x0d72, B:414:0x0d8a, B:416:0x0d9a, B:436:0x0e17, B:417:0x0db2, B:419:0x0db8, B:421:0x0dc0, B:423:0x0dc7, B:429:0x0dd5, B:431:0x0ddc, B:433:0x0e08, B:435:0x0e0f, B:434:0x0e0c, B:430:0x0dd9, B:422:0x0dc4), top: B:461:0x0a66 }] */
    /* JADX WARN: Removed duplicated region for block: B:357:0x0bac A[Catch: all -> 0x0e98, TryCatch #0 {all -> 0x0e98, blocks: (B:308:0x0a66, B:309:0x0a79, B:311:0x0a7f, B:405:0x0d46, B:336:0x0b14, B:343:0x0b44, B:345:0x0b66, B:346:0x0b6e, B:348:0x0b74, B:350:0x0b84, B:357:0x0bac, B:359:0x0bd3, B:361:0x0bdf, B:363:0x0bf5, B:366:0x0c38, B:370:0x0c4e, B:372:0x0c55, B:374:0x0c63, B:376:0x0c67, B:378:0x0c6b, B:380:0x0c6f, B:381:0x0c7b, B:382:0x0c80, B:384:0x0c86, B:386:0x0ca0, B:387:0x0ca5, B:404:0x0d43, B:388:0x0cbc, B:390:0x0cc1, B:394:0x0ce8, B:396:0x0d0b, B:398:0x0d19, B:399:0x0d2c, B:401:0x0d33, B:391:0x0cce, B:355:0x0b98, B:406:0x0d50, B:408:0x0d5e, B:409:0x0d64, B:410:0x0d6c, B:412:0x0d72, B:414:0x0d8a, B:416:0x0d9a, B:436:0x0e17, B:417:0x0db2, B:419:0x0db8, B:421:0x0dc0, B:423:0x0dc7, B:429:0x0dd5, B:431:0x0ddc, B:433:0x0e08, B:435:0x0e0f, B:434:0x0e0c, B:430:0x0dd9, B:422:0x0dc4), top: B:461:0x0a66 }] */
    /* JADX WARN: Removed duplicated region for block: B:359:0x0bd3 A[Catch: all -> 0x0e98, TryCatch #0 {all -> 0x0e98, blocks: (B:308:0x0a66, B:309:0x0a79, B:311:0x0a7f, B:405:0x0d46, B:336:0x0b14, B:343:0x0b44, B:345:0x0b66, B:346:0x0b6e, B:348:0x0b74, B:350:0x0b84, B:357:0x0bac, B:359:0x0bd3, B:361:0x0bdf, B:363:0x0bf5, B:366:0x0c38, B:370:0x0c4e, B:372:0x0c55, B:374:0x0c63, B:376:0x0c67, B:378:0x0c6b, B:380:0x0c6f, B:381:0x0c7b, B:382:0x0c80, B:384:0x0c86, B:386:0x0ca0, B:387:0x0ca5, B:404:0x0d43, B:388:0x0cbc, B:390:0x0cc1, B:394:0x0ce8, B:396:0x0d0b, B:398:0x0d19, B:399:0x0d2c, B:401:0x0d33, B:391:0x0cce, B:355:0x0b98, B:406:0x0d50, B:408:0x0d5e, B:409:0x0d64, B:410:0x0d6c, B:412:0x0d72, B:414:0x0d8a, B:416:0x0d9a, B:436:0x0e17, B:417:0x0db2, B:419:0x0db8, B:421:0x0dc0, B:423:0x0dc7, B:429:0x0dd5, B:431:0x0ddc, B:433:0x0e08, B:435:0x0e0f, B:434:0x0e0c, B:430:0x0dd9, B:422:0x0dc4), top: B:461:0x0a66 }] */
    /* JADX WARN: Removed duplicated region for block: B:368:0x0c4b  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x0c4d  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0c55 A[Catch: all -> 0x0e98, TryCatch #0 {all -> 0x0e98, blocks: (B:308:0x0a66, B:309:0x0a79, B:311:0x0a7f, B:405:0x0d46, B:336:0x0b14, B:343:0x0b44, B:345:0x0b66, B:346:0x0b6e, B:348:0x0b74, B:350:0x0b84, B:357:0x0bac, B:359:0x0bd3, B:361:0x0bdf, B:363:0x0bf5, B:366:0x0c38, B:370:0x0c4e, B:372:0x0c55, B:374:0x0c63, B:376:0x0c67, B:378:0x0c6b, B:380:0x0c6f, B:381:0x0c7b, B:382:0x0c80, B:384:0x0c86, B:386:0x0ca0, B:387:0x0ca5, B:404:0x0d43, B:388:0x0cbc, B:390:0x0cc1, B:394:0x0ce8, B:396:0x0d0b, B:398:0x0d19, B:399:0x0d2c, B:401:0x0d33, B:391:0x0cce, B:355:0x0b98, B:406:0x0d50, B:408:0x0d5e, B:409:0x0d64, B:410:0x0d6c, B:412:0x0d72, B:414:0x0d8a, B:416:0x0d9a, B:436:0x0e17, B:417:0x0db2, B:419:0x0db8, B:421:0x0dc0, B:423:0x0dc7, B:429:0x0dd5, B:431:0x0ddc, B:433:0x0e08, B:435:0x0e0f, B:434:0x0e0c, B:430:0x0dd9, B:422:0x0dc4), top: B:461:0x0a66 }] */
    /* JADX WARN: Removed duplicated region for block: B:382:0x0c80 A[Catch: all -> 0x0e98, TryCatch #0 {all -> 0x0e98, blocks: (B:308:0x0a66, B:309:0x0a79, B:311:0x0a7f, B:405:0x0d46, B:336:0x0b14, B:343:0x0b44, B:345:0x0b66, B:346:0x0b6e, B:348:0x0b74, B:350:0x0b84, B:357:0x0bac, B:359:0x0bd3, B:361:0x0bdf, B:363:0x0bf5, B:366:0x0c38, B:370:0x0c4e, B:372:0x0c55, B:374:0x0c63, B:376:0x0c67, B:378:0x0c6b, B:380:0x0c6f, B:381:0x0c7b, B:382:0x0c80, B:384:0x0c86, B:386:0x0ca0, B:387:0x0ca5, B:404:0x0d43, B:388:0x0cbc, B:390:0x0cc1, B:394:0x0ce8, B:396:0x0d0b, B:398:0x0d19, B:399:0x0d2c, B:401:0x0d33, B:391:0x0cce, B:355:0x0b98, B:406:0x0d50, B:408:0x0d5e, B:409:0x0d64, B:410:0x0d6c, B:412:0x0d72, B:414:0x0d8a, B:416:0x0d9a, B:436:0x0e17, B:417:0x0db2, B:419:0x0db8, B:421:0x0dc0, B:423:0x0dc7, B:429:0x0dd5, B:431:0x0ddc, B:433:0x0e08, B:435:0x0e0f, B:434:0x0e0c, B:430:0x0dd9, B:422:0x0dc4), top: B:461:0x0a66 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0208  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean zzaF(String str, long j) throws Throwable {
        boolean z;
        int i;
        long j2;
        boolean z2;
        long j3;
        long j4;
        int iZzm;
        Long l;
        long j5;
        Boolean bool;
        boolean z3;
        SecureRandom secureRandom;
        int i2;
        long jZzaj;
        int i3;
        String strZzaw;
        String strZzaw2;
        String str2;
        String str3;
        boolean z4;
        int i4;
        String str4;
        int i5;
        String str5;
        int i6;
        int i7;
        int i8;
        String str6;
        int i9;
        int i10;
        int i11;
        zzpf zzpfVar = this;
        String str7 = FirebaseAnalytics.Event.PURCHASE;
        String str8 = FirebaseAnalytics.Param.ITEMS;
        zzpfVar.zzj().zzb();
        try {
            zzpb zzpbVar = new zzpb(zzpfVar, null);
            zzpfVar.zzj().zzav(str, j, zzpfVar.zzB, zzpbVar);
            List list = zzpbVar.zzc;
            if (list == null || list.isEmpty()) {
                zzpfVar.zzj().zzc();
                z = false;
            } else {
                com.google.android.gms.internal.measurement.zzic zzicVar = (com.google.android.gms.internal.measurement.zzic) zzpbVar.zza.zzcl();
                zzicVar.zzi();
                com.google.android.gms.internal.measurement.zzhr zzhrVar = null;
                com.google.android.gms.internal.measurement.zzhr zzhrVar2 = null;
                int i12 = -1;
                int i13 = 0;
                int i14 = 0;
                boolean z5 = false;
                boolean z6 = false;
                int i15 = -1;
                while (true) {
                    i = i14;
                    boolean z7 = z5;
                    com.google.android.gms.internal.measurement.zzhr zzhrVar3 = zzhrVar;
                    int i16 = i15;
                    com.google.android.gms.internal.measurement.zzhr zzhrVar4 = zzhrVar2;
                    if (i13 >= zzpbVar.zzc.size()) {
                        break;
                    }
                    com.google.android.gms.internal.measurement.zzhr zzhrVar5 = (com.google.android.gms.internal.measurement.zzhr) ((com.google.android.gms.internal.measurement.zzhs) zzpbVar.zzc.get(i13)).zzcl();
                    boolean z8 = z6;
                    if (zzpfVar.zzh().zzj(zzpbVar.zza.zzA(), zzhrVar5.zzk())) {
                        zzpfVar.zzaV().zze().zzc("Dropping blocked raw event. appId", zzgt.zzl(zzpbVar.zza.zzA()), zzpfVar.zzn.zzl().zza(zzhrVar5.zzk()));
                        if (!zzpfVar.zzh().zzn(zzpbVar.zza.zzA()) && !zzpfVar.zzh().zzo(zzpbVar.zza.zzA()) && !"_err".equals(zzhrVar5.zzk())) {
                            zzpfVar.zzt().zzN(zzpfVar.zzK, zzpbVar.zza.zzA(), 11, "_ev", zzhrVar5.zzk(), 0);
                        }
                        i14 = i;
                        str3 = str7;
                        str6 = str8;
                        zzhrVar = zzhrVar3;
                        i15 = i16;
                        zzhrVar2 = zzhrVar4;
                        z6 = z8;
                        i8 = i12;
                        i9 = i13;
                    } else {
                        String strZzk = zzhrVar5.zzk();
                        int i17 = i13;
                        if (strZzk.equals(str7) || strZzk.equals("_iap") || strZzk.equals("ecommerce_purchase")) {
                            com.google.android.gms.internal.measurement.zzhv zzhvVarZzn = com.google.android.gms.internal.measurement.zzhw.zzn();
                            str2 = str8;
                            zzhvVarZzn.zzb("_ct");
                            if (!z7) {
                                String strZzA = zzpbVar.zza.zzA();
                                String str9 = (zzpfVar.zzaO(strZzA, str7) && zzpfVar.zzaO(strZzA, "_iap") && zzpfVar.zzaO(strZzA, "ecommerce_purchase")) ? "new" : "returning";
                                zzhvVarZzn.zzd(str9);
                                zzhrVar5.zzf((com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn.zzbc());
                                z7 = true;
                            }
                        } else {
                            str2 = str8;
                        }
                        if (zzhrVar5.zzk().equals(zzjl.zza("_ai"))) {
                            zzhrVar5.zzl("_ai");
                            zzpfVar.zzaV().zzk().zza("Renaming ad_impression to _ai");
                            if (Log.isLoggable(zzpfVar.zzaV().zzn(), 5)) {
                                for (int i18 = 0; i18 < zzhrVar5.zzb(); i18++) {
                                    if (FirebaseAnalytics.Param.AD_PLATFORM.equals(zzhrVar5.zzc(i18).zzb()) && !zzhrVar5.zzc(i18).zzd().isEmpty() && "admob".equalsIgnoreCase(zzhrVar5.zzc(i18).zzd())) {
                                        zzpfVar.zzaV().zzh().zza("AdMob ad impression logged from app. Potentially duplicative.");
                                    }
                                }
                            }
                        }
                        boolean zZzk = zzpfVar.zzh().zzk(zzpbVar.zza.zzA(), zzhrVar5.zzk());
                        if (!zZzk) {
                            zzpfVar.zzp();
                            String strZzk2 = zzhrVar5.zzk();
                            Preconditions.checkNotEmpty(strZzk2);
                            if (strZzk2.hashCode() == 95027 && strZzk2.equals("_ui")) {
                                str3 = str7;
                                int i19 = 0;
                                boolean z9 = false;
                                boolean z10 = false;
                                while (true) {
                                    z4 = zZzk;
                                    if (i19 >= zzhrVar5.zzb()) {
                                        break;
                                    }
                                    if ("_c".equals(zzhrVar5.zzc(i19).zzb())) {
                                        com.google.android.gms.internal.measurement.zzhv zzhvVar = (com.google.android.gms.internal.measurement.zzhv) zzhrVar5.zzc(i19).zzcl();
                                        i5 = i12;
                                        zzhvVar.zzf(1L);
                                        zzhrVar5.zzd(i19, (com.google.android.gms.internal.measurement.zzhw) zzhvVar.zzbc());
                                        z9 = true;
                                    } else {
                                        i5 = i12;
                                        if ("_r".equals(zzhrVar5.zzc(i19).zzb())) {
                                            com.google.android.gms.internal.measurement.zzhv zzhvVar2 = (com.google.android.gms.internal.measurement.zzhv) zzhrVar5.zzc(i19).zzcl();
                                            zzhvVar2.zzf(1L);
                                            zzhrVar5.zzd(i19, (com.google.android.gms.internal.measurement.zzhw) zzhvVar2.zzbc());
                                            z10 = true;
                                        }
                                    }
                                    i19++;
                                    zZzk = z4;
                                    i12 = i5;
                                }
                                i4 = i12;
                                if (z9 || !z4) {
                                    str4 = "_et";
                                } else {
                                    zzpfVar.zzaV().zzk().zzb("Marking event as conversion", zzpfVar.zzn.zzl().zza(zzhrVar5.zzk()));
                                    com.google.android.gms.internal.measurement.zzhv zzhvVarZzn2 = com.google.android.gms.internal.measurement.zzhw.zzn();
                                    zzhvVarZzn2.zzb("_c");
                                    str4 = "_et";
                                    zzhvVarZzn2.zzf(1L);
                                    zzhrVar5.zzg(zzhvVarZzn2);
                                }
                                if (!z10) {
                                    zzpfVar.zzaV().zzk().zzb("Marking event as real-time", zzpfVar.zzn.zzl().zza(zzhrVar5.zzk()));
                                    com.google.android.gms.internal.measurement.zzhv zzhvVarZzn3 = com.google.android.gms.internal.measurement.zzhw.zzn();
                                    zzhvVarZzn3.zzb("_r");
                                    zzhvVarZzn3.zzf(1L);
                                    zzhrVar5.zzg(zzhvVarZzn3);
                                }
                                if (zzpfVar.zzj().zzw(zzpfVar.zzC(), zzpbVar.zza.zzA(), false, false, false, false, true, false, false).zze > zzpfVar.zzd().zzm(zzpbVar.zza.zzA(), zzfx.zzo)) {
                                    zzaA(zzhrVar5, "_r");
                                    z6 = z8;
                                } else {
                                    z6 = true;
                                }
                                if (zzpo.zzh(zzhrVar5.zzk()) && z4 && zzpfVar.zzj().zzw(zzpfVar.zzC(), zzpbVar.zza.zzA(), false, false, true, false, false, false, false).zzc > zzpfVar.zzd().zzm(zzpbVar.zza.zzA(), zzfx.zzn)) {
                                    zzpfVar.zzaV().zze().zzb("Too many conversions. Not logging as conversion. appId", zzgt.zzl(zzpbVar.zza.zzA()));
                                    int i20 = 0;
                                    boolean z11 = false;
                                    int i21 = -1;
                                    com.google.android.gms.internal.measurement.zzhv zzhvVar3 = null;
                                    while (i20 < zzhrVar5.zzb()) {
                                        com.google.android.gms.internal.measurement.zzhw zzhwVarZzc = zzhrVar5.zzc(i20);
                                        int i22 = i20;
                                        if ("_c".equals(zzhwVarZzc.zzb())) {
                                            zzhvVar3 = (com.google.android.gms.internal.measurement.zzhv) zzhwVarZzc.zzcl();
                                            i21 = i22;
                                        } else if ("_err".equals(zzhwVarZzc.zzb())) {
                                            z11 = true;
                                        }
                                        i20 = i22 + 1;
                                    }
                                    if (z11) {
                                        if (zzhvVar3 != null) {
                                            zzhrVar5.zzj(i21);
                                        } else {
                                            zzhvVar3 = null;
                                            if (zzhvVar3 == null) {
                                            }
                                        }
                                    } else if (zzhvVar3 == null) {
                                        com.google.android.gms.internal.measurement.zzhv zzhvVar4 = (com.google.android.gms.internal.measurement.zzhv) zzhvVar3.clone();
                                        zzhvVar4.zzb("_err");
                                        zzhvVar4.zzf(10L);
                                        zzhrVar5.zzd(i21, (com.google.android.gms.internal.measurement.zzhw) zzhvVar4.zzbc());
                                    } else {
                                        zzpfVar.zzaV().zzb().zzb("Did not find conversion parameter. appId", zzgt.zzl(zzpbVar.zza.zzA()));
                                    }
                                }
                            } else {
                                str3 = str7;
                                i4 = i12;
                                str4 = "_et";
                                z6 = z8;
                                z4 = false;
                            }
                            if (z4) {
                                ArrayList arrayList = new ArrayList(zzhrVar5.zza());
                                int i23 = 0;
                                int i24 = -1;
                                int i25 = -1;
                                while (true) {
                                    str5 = str4;
                                    if (i23 >= arrayList.size()) {
                                        break;
                                    }
                                    if ("value".equals(((com.google.android.gms.internal.measurement.zzhw) arrayList.get(i23)).zzb())) {
                                        i24 = i23;
                                    } else if (FirebaseAnalytics.Param.CURRENCY.equals(((com.google.android.gms.internal.measurement.zzhw) arrayList.get(i23)).zzb())) {
                                        i25 = i23;
                                    }
                                    i23++;
                                    str4 = str5;
                                }
                                if (i24 != -1) {
                                    if (((com.google.android.gms.internal.measurement.zzhw) arrayList.get(i24)).zze() || ((com.google.android.gms.internal.measurement.zzhw) arrayList.get(i24)).zzi()) {
                                        if (i25 != -1) {
                                            String strZzd = ((com.google.android.gms.internal.measurement.zzhw) arrayList.get(i25)).zzd();
                                            if (strZzd.length() == 3) {
                                                int iCharCount = 0;
                                                while (iCharCount < strZzd.length()) {
                                                    int iCodePointAt = strZzd.codePointAt(iCharCount);
                                                    if (Character.isLetter(iCodePointAt)) {
                                                        iCharCount += Character.charCount(iCodePointAt);
                                                    }
                                                }
                                            }
                                        }
                                        zzpfVar.zzaV().zzh().zza("Value parameter discarded. You must also supply a 3-letter ISO_4217 currency code in the currency parameter.");
                                        zzhrVar5.zzj(i24);
                                        zzaA(zzhrVar5, "_c");
                                        zzaz(zzhrVar5, 19, FirebaseAnalytics.Param.CURRENCY);
                                        break;
                                    }
                                    zzpfVar.zzaV().zzh().zza("Value must be specified with a numeric type.");
                                    zzhrVar5.zzj(i24);
                                    zzaA(zzhrVar5, "_c");
                                    zzaz(zzhrVar5, 18, "value");
                                }
                                if ("_e".equals(zzhrVar5.zzk())) {
                                    i6 = i4;
                                    if ("_vs".equals(zzhrVar5.zzk())) {
                                        zzpfVar.zzp();
                                        if (zzpj.zzF((com.google.android.gms.internal.measurement.zzhs) zzhrVar5.zzbc(), str5) == null) {
                                            if (zzhrVar3 != null && Math.abs(zzhrVar3.zzn() - zzhrVar5.zzn()) <= 1000) {
                                                com.google.android.gms.internal.measurement.zzhr zzhrVar6 = (com.google.android.gms.internal.measurement.zzhr) zzhrVar3.clone();
                                                if (zzpfVar.zzaH(zzhrVar6, zzhrVar5)) {
                                                    zzicVar.zzf(i16, zzhrVar6);
                                                    i15 = i16;
                                                    i7 = i6;
                                                    zzhrVar = null;
                                                    zzhrVar2 = null;
                                                    if (zzhrVar5.zzb() == 0) {
                                                    }
                                                    i9 = i17;
                                                    zzpbVar.zzc.set(i9, (com.google.android.gms.internal.measurement.zzhs) zzhrVar5.zzbc());
                                                    zzicVar.zzg(zzhrVar5);
                                                    i14 = i + 1;
                                                }
                                            }
                                            i15 = i16;
                                            zzhrVar2 = zzhrVar5;
                                            zzhrVar = zzhrVar3;
                                            i7 = i;
                                            if (zzhrVar5.zzb() == 0) {
                                            }
                                            i9 = i17;
                                            zzpbVar.zzc.set(i9, (com.google.android.gms.internal.measurement.zzhs) zzhrVar5.zzbc());
                                            zzicVar.zzg(zzhrVar5);
                                            i14 = i + 1;
                                        }
                                    }
                                    i15 = i16;
                                    i7 = i6;
                                    zzhrVar = zzhrVar3;
                                    zzhrVar2 = zzhrVar4;
                                    if (zzhrVar5.zzb() == 0) {
                                    }
                                    i9 = i17;
                                    zzpbVar.zzc.set(i9, (com.google.android.gms.internal.measurement.zzhs) zzhrVar5.zzbc());
                                    zzicVar.zzg(zzhrVar5);
                                    i14 = i + 1;
                                } else {
                                    zzpfVar.zzp();
                                    if (zzpj.zzF((com.google.android.gms.internal.measurement.zzhs) zzhrVar5.zzbc(), "_fr") == null) {
                                        if (zzhrVar4 != null && Math.abs(zzhrVar4.zzn() - zzhrVar5.zzn()) <= 1000) {
                                            com.google.android.gms.internal.measurement.zzhr zzhrVar7 = (com.google.android.gms.internal.measurement.zzhr) zzhrVar4.clone();
                                            if (zzpfVar.zzaH(zzhrVar5, zzhrVar7)) {
                                                int i26 = i4;
                                                zzicVar.zzf(i26, zzhrVar7);
                                                i7 = i26;
                                                i15 = i16;
                                                zzhrVar = null;
                                                zzhrVar2 = null;
                                                if (zzhrVar5.zzb() == 0) {
                                                    zzpfVar.zzp();
                                                    Bundle bundleZzE = zzpj.zzE(zzhrVar5.zza());
                                                    int i27 = 0;
                                                    while (i27 < zzhrVar5.zzb()) {
                                                        com.google.android.gms.internal.measurement.zzhw zzhwVarZzc2 = zzhrVar5.zzc(i27);
                                                        String str10 = str2;
                                                        if (!zzhwVarZzc2.zzb().equals(str10) || zzhwVarZzc2.zzk().isEmpty()) {
                                                            i10 = i7;
                                                            i11 = i27;
                                                            if (!zzhwVarZzc2.zzb().equals(str10)) {
                                                                zzpfVar.zzT(zzhrVar5.zzk(), (com.google.android.gms.internal.measurement.zzhv) zzhwVarZzc2.zzcl(), bundleZzE, zzpbVar.zza.zzA());
                                                            }
                                                        } else {
                                                            String strZzA2 = zzpbVar.zza.zzA();
                                                            List listZzk = zzhwVarZzc2.zzk();
                                                            Bundle[] bundleArr = new Bundle[listZzk.size()];
                                                            i10 = i7;
                                                            i11 = i27;
                                                            int i28 = 0;
                                                            while (i28 < listZzk.size()) {
                                                                com.google.android.gms.internal.measurement.zzhw zzhwVar = (com.google.android.gms.internal.measurement.zzhw) listZzk.get(i28);
                                                                zzpfVar.zzp();
                                                                int i29 = i28;
                                                                Bundle bundleZzE2 = zzpj.zzE(zzhwVar.zzk());
                                                                Iterator it = zzhwVar.zzk().iterator();
                                                                while (it.hasNext()) {
                                                                    zzpfVar.zzT(zzhrVar5.zzk(), (com.google.android.gms.internal.measurement.zzhv) ((com.google.android.gms.internal.measurement.zzhw) it.next()).zzcl(), bundleZzE2, strZzA2);
                                                                    it = it;
                                                                    listZzk = listZzk;
                                                                }
                                                                bundleArr[i29] = bundleZzE2;
                                                                i28 = i29 + 1;
                                                                listZzk = listZzk;
                                                            }
                                                            bundleZzE.putParcelableArray(str10, bundleArr);
                                                        }
                                                        i27 = i11 + 1;
                                                        str2 = str10;
                                                        i7 = i10;
                                                    }
                                                    i8 = i7;
                                                    str6 = str2;
                                                    zzhrVar5.zzi();
                                                    zzpj zzpjVarZzp = zzpfVar.zzp();
                                                    ArrayList arrayList2 = new ArrayList();
                                                    for (String str11 : bundleZzE.keySet()) {
                                                        com.google.android.gms.internal.measurement.zzhv zzhvVarZzn4 = com.google.android.gms.internal.measurement.zzhw.zzn();
                                                        zzhvVarZzn4.zzb(str11);
                                                        Object obj = bundleZzE.get(str11);
                                                        if (obj != null) {
                                                            zzpjVarZzp.zzd(zzhvVarZzn4, obj);
                                                            arrayList2.add((com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn4.zzbc());
                                                        }
                                                    }
                                                    Iterator it2 = arrayList2.iterator();
                                                    while (it2.hasNext()) {
                                                        zzhrVar5.zzf((com.google.android.gms.internal.measurement.zzhw) it2.next());
                                                    }
                                                } else {
                                                    i8 = i7;
                                                    str6 = str2;
                                                }
                                                i9 = i17;
                                                zzpbVar.zzc.set(i9, (com.google.android.gms.internal.measurement.zzhs) zzhrVar5.zzbc());
                                                zzicVar.zzg(zzhrVar5);
                                                i14 = i + 1;
                                            }
                                        }
                                        i15 = i;
                                        zzhrVar = zzhrVar5;
                                        i7 = i4;
                                        zzhrVar2 = zzhrVar4;
                                        if (zzhrVar5.zzb() == 0) {
                                        }
                                        i9 = i17;
                                        zzpbVar.zzc.set(i9, (com.google.android.gms.internal.measurement.zzhs) zzhrVar5.zzbc());
                                        zzicVar.zzg(zzhrVar5);
                                        i14 = i + 1;
                                    } else {
                                        i6 = i4;
                                        i15 = i16;
                                        i7 = i6;
                                        zzhrVar = zzhrVar3;
                                        zzhrVar2 = zzhrVar4;
                                        if (zzhrVar5.zzb() == 0) {
                                        }
                                        i9 = i17;
                                        zzpbVar.zzc.set(i9, (com.google.android.gms.internal.measurement.zzhs) zzhrVar5.zzbc());
                                        zzicVar.zzg(zzhrVar5);
                                        i14 = i + 1;
                                    }
                                }
                            } else {
                                str5 = str4;
                            }
                            if ("_e".equals(zzhrVar5.zzk())) {
                            }
                        }
                    }
                    i13 = i9 + 1;
                    str8 = str6;
                    z5 = z7;
                    i12 = i8;
                    str7 = str3;
                }
                boolean z12 = z6;
                long j6 = 0;
                int i30 = i;
                long jLongValue = 0;
                int i31 = 0;
                while (i31 < i30) {
                    com.google.android.gms.internal.measurement.zzhs zzhsVarZzd = zzicVar.zzd(i31);
                    if ("_e".equals(zzhsVarZzd.zzd())) {
                        zzpfVar.zzp();
                        if (zzpj.zzF(zzhsVarZzd, "_fr") != null) {
                            zzicVar.zzj(i31);
                            i30--;
                            i31--;
                        } else {
                            zzpfVar.zzp();
                            com.google.android.gms.internal.measurement.zzhw zzhwVarZzF = zzpj.zzF(zzhsVarZzd, "_et");
                            if (zzhwVarZzF != null) {
                                Long lValueOf = zzhwVarZzF.zze() ? Long.valueOf(zzhwVarZzF.zzf()) : null;
                                if (lValueOf != null && lValueOf.longValue() > 0) {
                                    jLongValue += lValueOf.longValue();
                                }
                            }
                        }
                    }
                    i31++;
                }
                zzpfVar.zzaG(zzicVar, jLongValue, false);
                Iterator it3 = zzicVar.zzb().iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        break;
                    }
                    if ("_s".equals(((com.google.android.gms.internal.measurement.zzhs) it3.next()).zzd())) {
                        zzpfVar.zzj().zzk(zzicVar.zzK(), "_se");
                        break;
                    }
                }
                if (zzpj.zzx(zzicVar, "_sid") >= 0) {
                    zzpfVar.zzaG(zzicVar, jLongValue, true);
                } else {
                    int iZzx = zzpj.zzx(zzicVar, "_se");
                    if (iZzx >= 0) {
                        zzicVar.zzr(iZzx);
                        zzpfVar.zzaV().zzb().zzb("Session engagement user property is in the bundle without session ID. appId", zzgt.zzl(zzpbVar.zza.zzA()));
                    }
                }
                String strZzA3 = zzpbVar.zza.zzA();
                zzpfVar.zzaW().zzg();
                zzpfVar.zzu();
                zzh zzhVarZzu = zzpfVar.zzj().zzu(strZzA3);
                if (zzhVarZzu == null) {
                    zzpfVar.zzaV().zzb().zzb("Cannot fix consent fields without appInfo. appId", zzgt.zzl(strZzA3));
                } else {
                    zzpfVar.zzI(zzhVarZzu, zzicVar);
                }
                String strZzA4 = zzpbVar.zza.zzA();
                zzpfVar.zzaW().zzg();
                zzpfVar.zzu();
                zzh zzhVarZzu2 = zzpfVar.zzj().zzu(strZzA4);
                if (zzhVarZzu2 == null) {
                    zzpfVar.zzaV().zze().zzb("Cannot populate ad_campaign_info without appInfo. appId", zzgt.zzl(strZzA4));
                } else {
                    zzpfVar.zzJ(zzhVarZzu2, zzicVar);
                }
                zzaR(zzicVar);
                zzicVar.zzak();
                zzjk zzjkVar = zzjk.zza;
                zzjk zzjkVarZzs = zzpfVar.zzB(zzpbVar.zza.zzA()).zzs(zzjk.zzf(zzpbVar.zza.zzaf(), 100));
                zzjk zzjkVarZzaf = zzpfVar.zzj().zzaf(zzpbVar.zza.zzA());
                zzpfVar.zzj().zzae(zzpbVar.zza.zzA(), zzjkVarZzs);
                zzjj zzjjVar = zzjj.ANALYTICS_STORAGE;
                if (!zzjkVarZzs.zzo(zzjjVar) && zzjkVarZzaf.zzo(zzjjVar)) {
                    zzpfVar.zzj().zzi(zzpbVar.zza.zzA());
                } else if (zzjkVarZzs.zzo(zzjjVar) && !zzjkVarZzaf.zzo(zzjjVar)) {
                    zzpfVar.zzj().zzj(zzpbVar.zza.zzA());
                }
                zzjj zzjjVar2 = zzjj.AD_STORAGE;
                if (!zzjkVarZzs.zzo(zzjjVar2)) {
                    zzicVar.zzR();
                    zzicVar.zzU();
                    zzicVar.zzan();
                }
                if (!zzjkVarZzs.zzo(zzjjVar)) {
                    zzicVar.zzX();
                    zzicVar.zzav();
                }
                zzql.zza();
                if (zzpfVar.zzd().zzp(zzpbVar.zza.zzA(), zzfx.zzaP) && zzpfVar.zzt().zzX(zzpbVar.zza.zzA()) && zzpfVar.zzB(zzpbVar.zza.zzA()).zzo(zzjjVar2) && zzpbVar.zza.zzak()) {
                    for (int i32 = 0; i32 < zzicVar.zzc(); i32++) {
                        com.google.android.gms.internal.measurement.zzhr zzhrVar8 = (com.google.android.gms.internal.measurement.zzhr) zzicVar.zzd(i32).zzcl();
                        Iterator it4 = zzhrVar8.zza().iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                break;
                            }
                            if ("_c".equals(((com.google.android.gms.internal.measurement.zzhw) it4.next()).zzb())) {
                                if (zzpbVar.zza.zzar() >= zzpfVar.zzd().zzm(zzpbVar.zza.zzA(), zzfx.zzal)) {
                                    int iZzm2 = zzpfVar.zzd().zzm(zzpbVar.zza.zzA(), zzfx.zzay);
                                    if (iZzm2 <= 0) {
                                        if (zzpfVar.zzd().zzp(zzpbVar.zza.zzA(), zzfx.zzaR)) {
                                            strZzaw = zzpfVar.zzt().zzaw();
                                            com.google.android.gms.internal.measurement.zzhv zzhvVarZzn5 = com.google.android.gms.internal.measurement.zzhw.zzn();
                                            zzhvVarZzn5.zzb("_tu");
                                            zzhvVarZzn5.zzd(strZzaw);
                                            zzhrVar8.zzf((com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn5.zzbc());
                                        } else {
                                            strZzaw = null;
                                        }
                                        com.google.android.gms.internal.measurement.zzhv zzhvVarZzn6 = com.google.android.gms.internal.measurement.zzhw.zzn();
                                        zzhvVarZzn6.zzb("_tr");
                                        zzhvVarZzn6.zzf(1L);
                                        zzhrVar8.zzf((com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn6.zzbc());
                                        zzog zzogVarZzf = zzpfVar.zzp().zzf(zzpbVar.zza.zzA(), zzicVar, zzhrVar8, strZzaw);
                                        if (zzogVarZzf != null) {
                                            zzpfVar.zzaV().zzk().zzc("Generated trigger URI. appId, uri", zzpbVar.zza.zzA(), zzogVarZzf.zza);
                                            zzpfVar.zzj().zzaa(zzpbVar.zza.zzA(), zzogVarZzf);
                                            Deque deque = zzpfVar.zzr;
                                            if (!deque.contains(zzpbVar.zza.zzA())) {
                                                deque.add(zzpbVar.zza.zzA());
                                            }
                                        }
                                    } else if (zzpfVar.zzj().zzw(zzpfVar.zzC(), zzpbVar.zza.zzA(), false, false, false, false, false, false, true).zzg > iZzm2) {
                                        com.google.android.gms.internal.measurement.zzhv zzhvVarZzn7 = com.google.android.gms.internal.measurement.zzhw.zzn();
                                        zzhvVarZzn7.zzb("_tnr");
                                        zzhvVarZzn7.zzf(1L);
                                        zzhrVar8.zzf((com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn7.zzbc());
                                    } else {
                                        if (zzpfVar.zzd().zzp(zzpbVar.zza.zzA(), zzfx.zzaR)) {
                                            strZzaw2 = zzpfVar.zzt().zzaw();
                                            com.google.android.gms.internal.measurement.zzhv zzhvVarZzn8 = com.google.android.gms.internal.measurement.zzhw.zzn();
                                            zzhvVarZzn8.zzb("_tu");
                                            zzhvVarZzn8.zzd(strZzaw2);
                                            zzhrVar8.zzf((com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn8.zzbc());
                                        } else {
                                            strZzaw2 = null;
                                        }
                                        com.google.android.gms.internal.measurement.zzhv zzhvVarZzn9 = com.google.android.gms.internal.measurement.zzhw.zzn();
                                        zzhvVarZzn9.zzb("_tr");
                                        zzhvVarZzn9.zzf(1L);
                                        zzhrVar8.zzf((com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn9.zzbc());
                                        zzog zzogVarZzf2 = zzpfVar.zzp().zzf(zzpbVar.zza.zzA(), zzicVar, zzhrVar8, strZzaw2);
                                        if (zzogVarZzf2 != null) {
                                            zzpfVar.zzaV().zzk().zzc("Generated trigger URI. appId, uri", zzpbVar.zza.zzA(), zzogVarZzf2.zza);
                                            zzpfVar.zzj().zzaa(zzpbVar.zza.zzA(), zzogVarZzf2);
                                            Deque deque2 = zzpfVar.zzr;
                                            if (!deque2.contains(zzpbVar.zza.zzA())) {
                                                deque2.add(zzpbVar.zza.zzA());
                                            }
                                        }
                                    }
                                }
                                zzicVar.zze(i32, (com.google.android.gms.internal.measurement.zzhs) zzhrVar8.zzbc());
                            }
                        }
                    }
                }
                zzicVar.zzag();
                zzicVar.zzaf(zzpfVar.zzm().zzb(zzicVar.zzK(), zzicVar.zzb(), zzicVar.zzk(), Long.valueOf(zzicVar.zzu()), Long.valueOf(zzicVar.zzw()), !zzjkVarZzs.zzo(zzjjVar)));
                if (zzpfVar.zzd().zzD(zzpbVar.zza.zzA())) {
                    try {
                        HashMap map = new HashMap();
                        ArrayList arrayList3 = new ArrayList();
                        SecureRandom secureRandomZzf = zzpfVar.zzt().zzf();
                        int i33 = 0;
                        while (i33 < zzicVar.zzc()) {
                            com.google.android.gms.internal.measurement.zzhr zzhrVar9 = (com.google.android.gms.internal.measurement.zzhr) zzicVar.zzd(i33).zzcl();
                            if (zzhrVar9.zzk().equals("_ep")) {
                                zzpfVar.zzp();
                                String str12 = (String) zzpj.zzI((com.google.android.gms.internal.measurement.zzhs) zzhrVar9.zzbc(), "_en");
                                zzbc zzbcVarZzf = (zzbc) map.get(str12);
                                if (zzbcVarZzf == null && (zzbcVarZzf = zzpfVar.zzj().zzf(zzpbVar.zza.zzA(), (String) Preconditions.checkNotNull(str12))) != null) {
                                    map.put(str12, zzbcVarZzf);
                                }
                                if (zzbcVarZzf != null && zzbcVarZzf.zzi == null) {
                                    Long l2 = zzbcVarZzf.zzj;
                                    if (l2 != null && l2.longValue() > 1) {
                                        zzpfVar.zzp();
                                        zzpj.zzC(zzhrVar9, "_sr", l2);
                                    }
                                    Boolean bool2 = zzbcVarZzf.zzk;
                                    if (bool2 != null && bool2.booleanValue()) {
                                        zzpfVar.zzp();
                                        zzpj.zzC(zzhrVar9, "_efs", 1L);
                                    }
                                    arrayList3.add((com.google.android.gms.internal.measurement.zzhs) zzhrVar9.zzbc());
                                }
                                zzicVar.zzf(i33, zzhrVar9);
                                j4 = j6;
                                secureRandom = secureRandomZzf;
                                i3 = i33;
                            } else {
                                zzhs zzhsVarZzh = zzpfVar.zzh();
                                String strZzA5 = zzpbVar.zza.zzA();
                                String strZza = zzhsVarZzh.zza(strZzA5, "measurement.account.time_zone_offset_minutes");
                                if (TextUtils.isEmpty(strZza)) {
                                    j3 = j6;
                                    long jZzaj2 = zzpfVar.zzt().zzaj(zzhrVar9.zzn(), j3);
                                    com.google.android.gms.internal.measurement.zzhs zzhsVar = (com.google.android.gms.internal.measurement.zzhs) zzhrVar9.zzbc();
                                    j4 = j6;
                                    Long l3 = 1L;
                                    if (TextUtils.isEmpty("_dbg")) {
                                    }
                                } else {
                                    try {
                                        j3 = Long.parseLong(strZza);
                                    } catch (NumberFormatException e) {
                                        zzhsVarZzh.zzu.zzaV().zze().zzc("Unable to parse timezone offset. appId", zzgt.zzl(strZzA5), e);
                                        j3 = j6;
                                    }
                                    long jZzaj22 = zzpfVar.zzt().zzaj(zzhrVar9.zzn(), j3);
                                    com.google.android.gms.internal.measurement.zzhs zzhsVar2 = (com.google.android.gms.internal.measurement.zzhs) zzhrVar9.zzbc();
                                    j4 = j6;
                                    Long l32 = 1L;
                                    if (TextUtils.isEmpty("_dbg")) {
                                        Iterator it5 = zzhsVar2.zza().iterator();
                                        while (true) {
                                            if (!it5.hasNext()) {
                                                break;
                                            }
                                            com.google.android.gms.internal.measurement.zzhw zzhwVar2 = (com.google.android.gms.internal.measurement.zzhw) it5.next();
                                            if ("_dbg".equals(zzhwVar2.zzb())) {
                                                if (l32.equals(Long.valueOf(zzhwVar2.zzf()))) {
                                                    iZzm = 1;
                                                }
                                            }
                                        }
                                        iZzm = zzh().zzm(zzpbVar.zza.zzA(), zzhrVar9.zzk());
                                        if (iZzm > 0) {
                                            zzaV().zze().zzc("Sample rate must be positive. event, rate", zzhrVar9.zzk(), Integer.valueOf(iZzm));
                                            arrayList3.add((com.google.android.gms.internal.measurement.zzhs) zzhrVar9.zzbc());
                                            zzicVar.zzf(i33, zzhrVar9);
                                        } else {
                                            zzbc zzbcVarZzc = (zzbc) map.get(zzhrVar9.zzk());
                                            if (zzbcVarZzc == null) {
                                                l = l32;
                                                zzbcVarZzc = zzj().zzf(zzpbVar.zza.zzA(), zzhrVar9.zzk());
                                                if (zzbcVarZzc == null) {
                                                    j5 = j3;
                                                    zzaV().zze().zzc("Event being bundled has no eventAggregate. appId, eventName", zzpbVar.zza.zzA(), zzhrVar9.zzk());
                                                    zzbcVarZzc = new zzbc(zzpbVar.zza.zzA(), zzhrVar9.zzk(), 1L, 1L, 1L, zzhrVar9.zzn(), 0L, null, null, null, null);
                                                }
                                                zzp();
                                                Long l4 = (Long) zzpj.zzI((com.google.android.gms.internal.measurement.zzhs) zzhrVar9.zzbc(), "_eid");
                                                boolean z13 = l4 == null;
                                                Boolean boolValueOf = Boolean.valueOf(z13);
                                                if (iZzm != 1) {
                                                    arrayList3.add((com.google.android.gms.internal.measurement.zzhs) zzhrVar9.zzbc());
                                                    boolValueOf.getClass();
                                                    if (z13 && (zzbcVarZzc.zzi != null || zzbcVarZzc.zzj != null || zzbcVarZzc.zzk != null)) {
                                                        map.put(zzhrVar9.zzk(), zzbcVarZzc.zzc(null, null, null));
                                                    }
                                                    zzicVar.zzf(i33, zzhrVar9);
                                                } else {
                                                    if (secureRandomZzf.nextInt(iZzm) == 0) {
                                                        zzp();
                                                        boolean z14 = z13;
                                                        Long lValueOf2 = Long.valueOf(iZzm);
                                                        zzpj.zzC(zzhrVar9, "_sr", lValueOf2);
                                                        arrayList3.add((com.google.android.gms.internal.measurement.zzhs) zzhrVar9.zzbc());
                                                        boolValueOf.getClass();
                                                        if (z14) {
                                                            zzbcVarZzc = zzbcVarZzc.zzc(null, lValueOf2, null);
                                                        }
                                                        map.put(zzhrVar9.zzk(), zzbcVarZzc.zzb(zzhrVar9.zzn(), jZzaj22));
                                                        secureRandom = secureRandomZzf;
                                                        i3 = i33;
                                                    } else {
                                                        boolean z15 = z13;
                                                        Long l5 = zzbcVarZzc.zzh;
                                                        if (l5 != null) {
                                                            jZzaj = l5.longValue();
                                                            secureRandom = secureRandomZzf;
                                                            i2 = i33;
                                                            bool = boolValueOf;
                                                            z3 = z15;
                                                        } else {
                                                            bool = boolValueOf;
                                                            z3 = z15;
                                                            secureRandom = secureRandomZzf;
                                                            i2 = i33;
                                                            jZzaj = zzt().zzaj(zzhrVar9.zzp(), j5);
                                                        }
                                                        if (jZzaj != jZzaj22) {
                                                            zzp();
                                                            zzpj.zzC(zzhrVar9, "_efs", l);
                                                            zzp();
                                                            Long lValueOf3 = Long.valueOf(iZzm);
                                                            zzpj.zzC(zzhrVar9, "_sr", lValueOf3);
                                                            arrayList3.add((com.google.android.gms.internal.measurement.zzhs) zzhrVar9.zzbc());
                                                            bool.getClass();
                                                            if (z3) {
                                                                zzbcVarZzc = zzbcVarZzc.zzc(null, lValueOf3, true);
                                                            }
                                                            map.put(zzhrVar9.zzk(), zzbcVarZzc.zzb(zzhrVar9.zzn(), jZzaj22));
                                                            i3 = i2;
                                                        } else {
                                                            bool.getClass();
                                                            if (z3) {
                                                                map.put(zzhrVar9.zzk(), zzbcVarZzc.zzc(l4, null, null));
                                                            }
                                                            i3 = i2;
                                                        }
                                                    }
                                                    zzicVar.zzf(i3, zzhrVar9);
                                                }
                                            } else {
                                                l = l32;
                                            }
                                            j5 = j3;
                                            zzp();
                                            Long l42 = (Long) zzpj.zzI((com.google.android.gms.internal.measurement.zzhs) zzhrVar9.zzbc(), "_eid");
                                            if (l42 == null) {
                                            }
                                            Boolean boolValueOf2 = Boolean.valueOf(z13);
                                            if (iZzm != 1) {
                                            }
                                        }
                                        secureRandom = secureRandomZzf;
                                        i3 = i33;
                                    } else {
                                        iZzm = zzh().zzm(zzpbVar.zza.zzA(), zzhrVar9.zzk());
                                        if (iZzm > 0) {
                                        }
                                        secureRandom = secureRandomZzf;
                                        i3 = i33;
                                    }
                                }
                                i33 = i3 + 1;
                                zzpfVar = this;
                                j6 = j4;
                                secureRandomZzf = secureRandom;
                            }
                            i33 = i3 + 1;
                            zzpfVar = this;
                            j6 = j4;
                            secureRandomZzf = secureRandom;
                        }
                        j2 = j6;
                        z2 = true;
                        if (arrayList3.size() < zzicVar.zzc()) {
                            zzicVar.zzi();
                            zzicVar.zzh(arrayList3);
                        }
                        Iterator it6 = map.entrySet().iterator();
                        while (it6.hasNext()) {
                            zzj().zzh((zzbc) ((Map.Entry) it6.next()).getValue());
                        }
                    } catch (Throwable th) {
                        th = th;
                        zzpfVar = this;
                        zzpfVar.zzj().zzd();
                        throw th;
                    }
                } else {
                    j2 = 0;
                    z2 = true;
                }
                String strZzA6 = zzpbVar.zza.zzA();
                zzh zzhVarZzu3 = zzj().zzu(strZzA6);
                if (zzhVarZzu3 == null) {
                    zzaV().zzb().zzb("Bundling raw events w/o app info. appId", zzgt.zzl(zzpbVar.zza.zzA()));
                } else if (zzicVar.zzc() > 0) {
                    long jZzp = zzhVarZzu3.zzp();
                    if (jZzp != j2) {
                        zzicVar.zzA(jZzp);
                    } else {
                        zzicVar.zzB();
                    }
                    long jZzn = zzhVarZzu3.zzn();
                    if (jZzn != j2) {
                        jZzp = jZzn;
                    }
                    if (jZzp != j2) {
                        zzicVar.zzy(jZzp);
                    } else {
                        zzicVar.zzz();
                    }
                    zzhVarZzu3.zzM(zzicVar.zzc());
                    zzicVar.zzaJ((int) zzhVarZzu3.zzaF());
                    zzicVar.zzZ((int) zzhVarZzu3.zzG());
                    zzhVarZzu3.zzo(zzicVar.zzu());
                    zzhVarZzu3.zzq(zzicVar.zzw());
                    String strZzaa = zzhVarZzu3.zzaa();
                    if (strZzaa != null) {
                        zzicVar.zzaa(strZzaa);
                    } else {
                        zzicVar.zzab();
                    }
                    zzj().zzv(zzhVarZzu3, false, false);
                }
                if (zzicVar.zzc() > 0) {
                    zzpfVar = this;
                    zzpfVar.zzn.zzaU();
                    com.google.android.gms.internal.measurement.zzgl zzglVarZzb = zzpfVar.zzh().zzb(zzpbVar.zza.zzA());
                    if (zzglVarZzb != null && zzglVarZzb.zza()) {
                        zzicVar.zzal(zzglVarZzb.zzb());
                    } else if (zzpbVar.zza.zzP().isEmpty()) {
                        zzicVar.zzal(-1L);
                    } else {
                        zzpfVar.zzaV().zze().zzb("Did not find measurement config or missing version info. appId", zzgt.zzl(zzpbVar.zza.zzA()));
                    }
                    zzpfVar.zzj().zzz((com.google.android.gms.internal.measurement.zzid) zzicVar.zzbc(), z12);
                } else {
                    zzpfVar = this;
                }
                zzpfVar.zzj().zzS(zzpbVar.zzb);
                zzpfVar.zzj().zzT(strZzA6);
                zzpfVar.zzj().zzc();
                z = z2;
            }
            zzpfVar.zzj().zzd();
            return z;
        } catch (Throwable th2) {
            th = th2;
            zzpfVar.zzj().zzd();
            throw th;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void zzaG(com.google.android.gms.internal.measurement.zzic zzicVar, long j, boolean z) {
        Object obj;
        String str = true != z ? "_lte" : "_se";
        zzpm zzpmVarZzm = zzj().zzm(zzicVar.zzK(), str);
        zzpm zzpmVar = (zzpmVarZzm == null || (obj = zzpmVarZzm.zze) == null) ? new zzpm(zzicVar.zzK(), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, str, zzaZ().currentTimeMillis(), Long.valueOf(j)) : new zzpm(zzicVar.zzK(), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, str, zzaZ().currentTimeMillis(), Long.valueOf(((Long) obj).longValue() + j));
        com.google.android.gms.internal.measurement.zzit zzitVarZzm = com.google.android.gms.internal.measurement.zziu.zzm();
        zzitVarZzm.zzb(str);
        zzitVarZzm.zza(zzaZ().currentTimeMillis());
        Object obj2 = zzpmVar.zze;
        zzitVarZzm.zze(((Long) obj2).longValue());
        com.google.android.gms.internal.measurement.zziu zziuVar = (com.google.android.gms.internal.measurement.zziu) zzitVarZzm.zzbc();
        int iZzx = zzpj.zzx(zzicVar, str);
        if (iZzx >= 0) {
            zzicVar.zzn(iZzx, zziuVar);
        } else {
            zzicVar.zzo(zziuVar);
        }
        if (j > 0) {
            zzj().zzl(zzpmVar);
            zzaV().zzk().zzc("Updated engagement user property. scope, value", true != z ? "lifetime" : "session-scoped", obj2);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final boolean zzaH(com.google.android.gms.internal.measurement.zzhr zzhrVar, com.google.android.gms.internal.measurement.zzhr zzhrVar2) {
        Preconditions.checkArgument("_e".equals(zzhrVar.zzk()));
        zzp();
        com.google.android.gms.internal.measurement.zzhw zzhwVarZzF = zzpj.zzF((com.google.android.gms.internal.measurement.zzhs) zzhrVar.zzbc(), "_sc");
        String strZzd = zzhwVarZzF == null ? null : zzhwVarZzF.zzd();
        zzp();
        com.google.android.gms.internal.measurement.zzhw zzhwVarZzF2 = zzpj.zzF((com.google.android.gms.internal.measurement.zzhs) zzhrVar2.zzbc(), "_pc");
        String strZzd2 = zzhwVarZzF2 != null ? zzhwVarZzF2.zzd() : null;
        if (strZzd2 == null || !strZzd2.equals(strZzd)) {
            return false;
        }
        Preconditions.checkArgument("_e".equals(zzhrVar.zzk()));
        zzp();
        com.google.android.gms.internal.measurement.zzhw zzhwVarZzF3 = zzpj.zzF((com.google.android.gms.internal.measurement.zzhs) zzhrVar.zzbc(), "_et");
        if (zzhwVarZzF3 == null || !zzhwVarZzF3.zze() || zzhwVarZzF3.zzf() <= 0) {
            return true;
        }
        long jZzf = zzhwVarZzF3.zzf();
        zzp();
        com.google.android.gms.internal.measurement.zzhw zzhwVarZzF4 = zzpj.zzF((com.google.android.gms.internal.measurement.zzhs) zzhrVar2.zzbc(), "_et");
        if (zzhwVarZzF4 != null && zzhwVarZzF4.zzf() > 0) {
            jZzf += zzhwVarZzF4.zzf();
        }
        zzp();
        zzpj.zzC(zzhrVar2, "_et", Long.valueOf(jZzf));
        zzp();
        zzpj.zzC(zzhrVar, "_fr", 1L);
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final boolean zzaI() {
        zzaW().zzg();
        zzu();
        return zzj().zzP() || !TextUtils.isEmpty(zzj().zzF());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static String zzaJ(Map map, String str) {
        if (map == null) {
            return null;
        }
        for (Map.Entry entry : map.entrySet()) {
            if (str.equalsIgnoreCase((String) entry.getKey())) {
                if (((List) entry.getValue()).isEmpty()) {
                    return null;
                }
                return (String) ((List) entry.getValue()).get(0);
            }
        }
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void zzaK() {
        long jMax;
        long jMax2;
        zzaW().zzg();
        zzu();
        if (this.zza > 0) {
            long jAbs = 3600000 - Math.abs(zzaZ().elapsedRealtime() - this.zza);
            if (jAbs > 0) {
                zzaV().zzk().zzb("Upload has been suspended. Will update scheduling later in approximately ms", Long.valueOf(jAbs));
                zzk().zzb();
                zzl().zzd();
                return;
            }
            this.zza = 0L;
        }
        if (!this.zzn.zzH() || !zzaI()) {
            zzaV().zzk().zza("Nothing to upload or uploading impossible");
            zzk().zzb();
            zzl().zzd();
            return;
        }
        long jCurrentTimeMillis = zzaZ().currentTimeMillis();
        zzd();
        long jMax3 = Math.max(0L, ((Long) zzfx.zzO.zzb(null)).longValue());
        boolean z = true;
        if (!zzj().zzR() && !zzj().zzG()) {
            z = false;
        }
        if (z) {
            String strZzA = zzd().zzA();
            if (TextUtils.isEmpty(strZzA) || ".none.".equals(strZzA)) {
                zzd();
                jMax = Math.max(0L, ((Long) zzfx.zzI.zzb(null)).longValue());
            } else {
                zzd();
                jMax = Math.max(0L, ((Long) zzfx.zzJ.zzb(null)).longValue());
            }
        } else {
            zzd();
            jMax = Math.max(0L, ((Long) zzfx.zzH.zzb(null)).longValue());
        }
        long jZza = this.zzk.zzd.zza();
        long jZza2 = this.zzk.zze.zza();
        long j = 0;
        boolean z2 = z;
        long jMax4 = Math.max(zzj().zzM(), zzj().zzO());
        if (jMax4 == 0) {
            jMax2 = 0;
        } else {
            long jAbs2 = jCurrentTimeMillis - Math.abs(jMax4 - jCurrentTimeMillis);
            long jAbs3 = jCurrentTimeMillis - Math.abs(jZza - jCurrentTimeMillis);
            long jAbs4 = jCurrentTimeMillis - Math.abs(jZza2 - jCurrentTimeMillis);
            jMax2 = jMax3 + jAbs2;
            long jMax5 = Math.max(jAbs3, jAbs4);
            if (z2 && jMax5 > 0) {
                jMax2 = Math.min(jAbs2, jMax5) + jMax;
            }
            if (!zzp().zzs(jMax5, jMax)) {
                jMax2 = jMax5 + jMax;
            }
            if (jAbs4 != 0 && jAbs4 >= jAbs2) {
                int i = 0;
                while (true) {
                    zzd();
                    if (i >= Math.min(20, Math.max(0, ((Integer) zzfx.zzQ.zzb(null)).intValue()))) {
                        jMax2 = 0;
                        break;
                    }
                    zzd();
                    jMax2 += Math.max(j, ((Long) zzfx.zzP.zzb(null)).longValue()) * (1 << i);
                    if (jMax2 > jAbs4) {
                        break;
                    }
                    i++;
                    j = 0;
                }
            }
            j = 0;
        }
        if (jMax2 == j) {
            zzaV().zzk().zza("Next upload time is 0");
            zzk().zzb();
            zzl().zzd();
            return;
        }
        if (!zzi().zzb()) {
            zzaV().zzk().zza("No network");
            zzk().zza();
            zzl().zzd();
            return;
        }
        long jZza3 = this.zzk.zzc.zza();
        zzd();
        long jMax6 = Math.max(0L, ((Long) zzfx.zzF.zzb(null)).longValue());
        if (!zzp().zzs(jZza3, jMax6)) {
            jMax2 = Math.max(jMax2, jZza3 + jMax6);
        }
        zzk().zzb();
        long jCurrentTimeMillis2 = jMax2 - zzaZ().currentTimeMillis();
        if (jCurrentTimeMillis2 <= 0) {
            zzd();
            jCurrentTimeMillis2 = Math.max(0L, ((Long) zzfx.zzK.zzb(null)).longValue());
            this.zzk.zzd.zzb(zzaZ().currentTimeMillis());
        }
        zzaV().zzk().zzb("Upload scheduled in approximately ms", Long.valueOf(jCurrentTimeMillis2));
        zzl().zzc(jCurrentTimeMillis2);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void zzaL() {
        zzaW().zzg();
        if (this.zzu || this.zzv || this.zzw) {
            zzaV().zzk().zzd("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzu), Boolean.valueOf(this.zzv), Boolean.valueOf(this.zzw));
            return;
        }
        zzaV().zzk().zza("Stopping uploading service(s)");
        List list = this.zzq;
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        ((List) Preconditions.checkNotNull(this.zzq)).clear();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final Boolean zzaM(zzh zzhVar) {
        try {
            if (zzhVar.zzt() != -2147483648L) {
                if (zzhVar.zzt() == Wrappers.packageManager(this.zzn.zzaY()).getPackageInfo(zzhVar.zzc(), 0).versionCode) {
                    return true;
                }
            } else {
                String str = Wrappers.packageManager(this.zzn.zzaY()).getPackageInfo(zzhVar.zzc(), 0).versionName;
                String strZzr = zzhVar.zzr();
                if (strZzr != null && strZzr.equals(str)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final zzr zzaN(String str) {
        zzh zzhVarZzu = zzj().zzu(str);
        if (zzhVarZzu == null || TextUtils.isEmpty(zzhVarZzu.zzr())) {
            zzaV().zzj().zzb("No app data available; dropping", str);
            return null;
        }
        Boolean boolZzaM = zzaM(zzhVarZzu);
        if (boolZzaM == null || boolZzaM.booleanValue()) {
            return new zzr(str, zzhVarZzu.zzf(), zzhVarZzu.zzr(), zzhVarZzu.zzt(), zzhVarZzu.zzv(), zzhVarZzu.zzx(), zzhVarZzu.zzz(), (String) null, zzhVarZzu.zzD(), false, zzhVarZzu.zzl(), 0L, 0, zzhVarZzu.zzac(), false, zzhVarZzu.zzae(), zzhVarZzu.zzB(), zzhVarZzu.zzag(), zzB(str).zzl(), "", (String) null, zzhVarZzu.zzai(), zzhVarZzu.zzak(), zzB(str).zzb(), zzx(str).zze(), zzhVarZzu.zzao(), zzhVarZzu.zzaw(), zzhVarZzu.zzay(), zzhVarZzu.zzaH(), 0L, zzhVarZzu.zzaL());
        }
        zzaV().zzb().zzb("App version does not match; dropping. appId", zzgt.zzl(str));
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final boolean zzaO(String str, String str2) {
        zzbc zzbcVarZzf = zzj().zzf(str, str2);
        return zzbcVarZzf == null || zzbcVarZzf.zzc < 1;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX INFO: Access modifiers changed from: private */
    public static void zzaP(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT < 34) {
            context.sendBroadcast(intent);
        } else {
            context.sendBroadcast(intent, null, BroadcastOptions.makeBasic().setShareIdentityEnabled(true).toBundle());
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static final boolean zzaQ(zzr zzrVar) {
        return !TextUtils.isEmpty(zzrVar.zzb);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static final void zzaR(com.google.android.gms.internal.measurement.zzic zzicVar) {
        zzicVar.zzv(Long.MAX_VALUE);
        zzicVar.zzx(Long.MIN_VALUE);
        for (int i = 0; i < zzicVar.zzc(); i++) {
            com.google.android.gms.internal.measurement.zzhs zzhsVarZzd = zzicVar.zzd(i);
            if (zzhsVarZzd.zzf() < zzicVar.zzu()) {
                zzicVar.zzv(zzhsVarZzd.zzf());
            }
            if (zzhsVarZzd.zzf() > zzicVar.zzw()) {
                zzicVar.zzx(zzhsVarZzd.zzf());
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static final zzor zzaS(zzor zzorVar) {
        if (zzorVar == null) {
            throw new IllegalStateException("Upload Component not created");
        }
        if (zzorVar.zzax()) {
            return zzorVar;
        }
        String strValueOf = String.valueOf(zzorVar.getClass());
        String.valueOf(strValueOf);
        throw new IllegalStateException("Component not initialized: ".concat(String.valueOf(strValueOf)));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static final Boolean zzaT(zzr zzrVar) {
        Boolean bool = zzrVar.zzp;
        String str = zzrVar.zzC;
        if (!TextUtils.isEmpty(str)) {
            zzjh zzjhVarZza = zze.zzc(str).zza();
            zzjh zzjhVar = zzjh.UNINITIALIZED;
            int iOrdinal = zzjhVarZza.ordinal();
            if (iOrdinal == 0 || iOrdinal == 1) {
                return null;
            }
            if (iOrdinal == 2) {
                return true;
            }
            if (iOrdinal == 3) {
                return false;
            }
        }
        return bool;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static final void zzaz(com.google.android.gms.internal.measurement.zzhr zzhrVar, int i, String str) {
        List listZza = zzhrVar.zza();
        for (int i2 = 0; i2 < listZza.size(); i2++) {
            if ("_err".equals(((com.google.android.gms.internal.measurement.zzhw) listZza.get(i2)).zzb())) {
                return;
            }
        }
        com.google.android.gms.internal.measurement.zzhv zzhvVarZzn = com.google.android.gms.internal.measurement.zzhw.zzn();
        zzhvVarZzn.zzb("_err");
        long j = i;
        Long.valueOf(j).getClass();
        zzhvVarZzn.zzf(j);
        com.google.android.gms.internal.measurement.zzhw zzhwVar = (com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn.zzbc();
        com.google.android.gms.internal.measurement.zzhv zzhvVarZzn2 = com.google.android.gms.internal.measurement.zzhw.zzn();
        zzhvVarZzn2.zzb("_ev");
        zzhvVarZzn2.zzd(str);
        com.google.android.gms.internal.measurement.zzhw zzhwVar2 = (com.google.android.gms.internal.measurement.zzhw) zzhvVarZzn2.zzbc();
        zzhrVar.zzf(zzhwVar);
        zzhrVar.zzf(zzhwVar2);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzA(String str, zzjk zzjkVar) {
        zzaW().zzg();
        zzu();
        this.zzC.put(str, zzjkVar);
        zzj().zzab(str, zzjkVar);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final zzjk zzB(String str) {
        zzjk zzjkVar = zzjk.zza;
        zzaW().zzg();
        zzu();
        zzjk zzjkVarZzZ = (zzjk) this.zzC.get(str);
        if (zzjkVarZzZ == null) {
            zzjkVarZzZ = zzj().zzZ(str);
            if (zzjkVarZzZ == null) {
                zzjkVarZzZ = zzjk.zza;
            }
            zzA(str, zzjkVarZzZ);
        }
        return zzjkVarZzZ;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final long zzC() {
        long jCurrentTimeMillis = zzaZ().currentTimeMillis();
        zznm zznmVar = this.zzk;
        zznmVar.zzay();
        zznmVar.zzg();
        zzhd zzhdVar = zznmVar.zzf;
        long jZza = zzhdVar.zza();
        if (jZza == 0) {
            jZza = ((long) zznmVar.zzu.zzk().zzf().nextInt(86400000)) + 1;
            zzhdVar.zzb(jZza);
        }
        return ((((jCurrentTimeMillis + jZza) / 1000) / 60) / 60) / 24;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzD(zzbg zzbgVar, String str) {
        zzh zzhVarZzu = zzj().zzu(str);
        if (zzhVarZzu == null || TextUtils.isEmpty(zzhVarZzu.zzr())) {
            zzaV().zzj().zzb("No app data available; dropping event", str);
            return;
        }
        Boolean boolZzaM = zzaM(zzhVarZzu);
        if (boolZzaM == null) {
            if (!"_ui".equals(zzbgVar.zza)) {
                zzaV().zze().zzb("Could not find package. appId", zzgt.zzl(str));
            }
        } else if (!boolZzaM.booleanValue()) {
            zzaV().zzb().zzb("App version does not match; dropping event. appId", zzgt.zzl(str));
            return;
        }
        zzE(zzbgVar, new zzr(str, zzhVarZzu.zzf(), zzhVarZzu.zzr(), zzhVarZzu.zzt(), zzhVarZzu.zzv(), zzhVarZzu.zzx(), zzhVarZzu.zzz(), (String) null, zzhVarZzu.zzD(), false, zzhVarZzu.zzl(), 0L, 0, zzhVarZzu.zzac(), false, zzhVarZzu.zzae(), zzhVarZzu.zzB(), zzhVarZzu.zzag(), zzB(str).zzl(), "", (String) null, zzhVarZzu.zzai(), zzhVarZzu.zzak(), zzB(str).zzb(), zzx(str).zze(), zzhVarZzu.zzao(), zzhVarZzu.zzaw(), zzhVarZzu.zzay(), zzhVarZzu.zzaH(), 0L, zzhVarZzu.zzaL()));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzE(zzbg zzbgVar, zzr zzrVar) {
        String str = zzrVar.zza;
        Preconditions.checkNotEmpty(str);
        zzgu zzguVarZza = zzgu.zza(zzbgVar);
        zzt().zzI(zzguVarZza.zzd, zzj().zzW(str));
        zzt().zzG(zzguVarZza, zzd().zzd(str));
        zzbg zzbgVarZzb = zzguVarZza.zzb();
        if (!zzd().zzp(null, zzfx.zzbg) && Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN.equals(zzbgVarZzb.zza)) {
            zzbe zzbeVar = zzbgVarZzb.zzb;
            if ("referrer API v2".equals(zzbeVar.zzd("_cis"))) {
                String strZzd = zzbeVar.zzd("gclid");
                if (!TextUtils.isEmpty(strZzd)) {
                    zzab(new zzpk("_lgclid", zzbgVarZzb.zzd, strZzd, DebugKt.DEBUG_PROPERTY_VALUE_AUTO), zzrVar);
                }
            }
        }
        zzF(zzbgVarZzb, zzrVar);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzF(zzbg zzbgVar, zzr zzrVar) {
        zzbg zzbgVar2;
        List<zzah> listZzt;
        List<zzah> listZzt2;
        List<zzah> listZzt3;
        String str;
        Preconditions.checkNotNull(zzrVar);
        String str2 = zzrVar.zza;
        Preconditions.checkNotEmpty(str2);
        zzaW().zzg();
        zzu();
        long j = zzbgVar.zzd;
        zzgu zzguVarZza = zzgu.zza(zzbgVar);
        zzaW().zzg();
        zzpo.zzav((this.zzG == null || (str = this.zzH) == null || !str.equals(str2)) ? null : this.zzG, zzguVarZza.zzd, false);
        zzbg zzbgVarZzb = zzguVarZza.zzb();
        zzp();
        if (zzpj.zzD(zzbgVarZzb, zzrVar)) {
            if (!zzrVar.zzh) {
                zzan(zzrVar);
                return;
            }
            List list = zzrVar.zzr;
            if (list != null) {
                String str3 = zzbgVarZzb.zza;
                if (!list.contains(str3)) {
                    zzaV().zzj().zzd("Dropping non-safelisted event. appId, event name, origin", str2, zzbgVarZzb.zza, zzbgVarZzb.zzc);
                    return;
                } else {
                    Bundle bundleZzf = zzbgVarZzb.zzb.zzf();
                    bundleZzf.putLong("ga_safelisted", 1L);
                    zzbgVar2 = new zzbg(str3, new zzbe(bundleZzf), zzbgVarZzb.zzc, zzbgVarZzb.zzd);
                }
            } else {
                zzbgVar2 = zzbgVarZzb;
            }
            zzj().zzb();
            try {
                String str4 = zzbgVar2.zza;
                if ("_s".equals(str4) && !zzj().zzQ(str2, "_s") && zzbgVar2.zzb.zzb("_sid").longValue() != 0) {
                    if (zzj().zzQ(str2, "_f") || zzj().zzQ(str2, "_v")) {
                        zzj().zzY(str2, null, "_sid", zzG(str2, zzbgVar2));
                    } else {
                        zzj().zzY(str2, Long.valueOf(zzaZ().currentTimeMillis() - 15000), "_sid", zzG(str2, zzbgVar2));
                    }
                }
                zzav zzavVarZzj = zzj();
                Preconditions.checkNotEmpty(str2);
                zzavVarZzj.zzg();
                zzavVarZzj.zzay();
                if (j < 0) {
                    zzavVarZzj.zzu.zzaV().zze().zzc("Invalid time querying timed out conditional properties", zzgt.zzl(str2), Long.valueOf(j));
                    listZzt = Collections.EMPTY_LIST;
                } else {
                    listZzt = zzavVarZzj.zzt("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str2, String.valueOf(j)});
                }
                for (zzah zzahVar : listZzt) {
                    if (zzahVar != null) {
                        zzaV().zzk().zzd("User property timed out", zzahVar.zza, this.zzn.zzl().zzc(zzahVar.zzc.zzb), zzahVar.zzc.zza());
                        zzbg zzbgVar3 = zzahVar.zzg;
                        if (zzbgVar3 != null) {
                            zzH(new zzbg(zzbgVar3, j), zzrVar);
                        }
                        zzj().zzr(str2, zzahVar.zzc.zzb);
                    }
                }
                zzav zzavVarZzj2 = zzj();
                Preconditions.checkNotEmpty(str2);
                zzavVarZzj2.zzg();
                zzavVarZzj2.zzay();
                if (j < 0) {
                    zzavVarZzj2.zzu.zzaV().zze().zzc("Invalid time querying expired conditional properties", zzgt.zzl(str2), Long.valueOf(j));
                    listZzt2 = Collections.EMPTY_LIST;
                } else {
                    listZzt2 = zzavVarZzj2.zzt("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str2, String.valueOf(j)});
                }
                ArrayList arrayList = new ArrayList(listZzt2.size());
                for (zzah zzahVar2 : listZzt2) {
                    if (zzahVar2 != null) {
                        zzaV().zzk().zzd("User property expired", zzahVar2.zza, this.zzn.zzl().zzc(zzahVar2.zzc.zzb), zzahVar2.zzc.zza());
                        zzj().zzk(str2, zzahVar2.zzc.zzb);
                        zzbg zzbgVar4 = zzahVar2.zzk;
                        if (zzbgVar4 != null) {
                            arrayList.add(zzbgVar4);
                        }
                        zzj().zzr(str2, zzahVar2.zzc.zzb);
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    zzH(new zzbg((zzbg) it.next(), j), zzrVar);
                }
                zzav zzavVarZzj3 = zzj();
                Preconditions.checkNotEmpty(str2);
                Preconditions.checkNotEmpty(str4);
                zzavVarZzj3.zzg();
                zzavVarZzj3.zzay();
                if (j < 0) {
                    zzib zzibVar = zzavVarZzj3.zzu;
                    zzibVar.zzaV().zze().zzd("Invalid time querying triggered conditional properties", zzgt.zzl(str2), zzibVar.zzl().zza(str4), Long.valueOf(j));
                    listZzt3 = Collections.EMPTY_LIST;
                } else {
                    listZzt3 = zzavVarZzj3.zzt("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str2, str4, String.valueOf(j)});
                }
                ArrayList arrayList2 = new ArrayList(listZzt3.size());
                for (zzah zzahVar3 : listZzt3) {
                    if (zzahVar3 != null) {
                        zzpk zzpkVar = zzahVar3.zzc;
                        zzpm zzpmVar = new zzpm((String) Preconditions.checkNotNull(zzahVar3.zza), zzahVar3.zzb, zzpkVar.zzb, j, Preconditions.checkNotNull(zzpkVar.zza()));
                        if (zzj().zzl(zzpmVar)) {
                            zzaV().zzk().zzd("User property triggered", zzahVar3.zza, this.zzn.zzl().zzc(zzpmVar.zzc), zzpmVar.zze);
                        } else {
                            zzaV().zzb().zzd("Too many active user properties, ignoring", zzgt.zzl(zzahVar3.zza), this.zzn.zzl().zzc(zzpmVar.zzc), zzpmVar.zze);
                        }
                        zzbg zzbgVar5 = zzahVar3.zzi;
                        if (zzbgVar5 != null) {
                            arrayList2.add(zzbgVar5);
                        }
                        zzahVar3.zzc = new zzpk(zzpmVar);
                        zzahVar3.zze = true;
                        zzj().zzp(zzahVar3);
                    }
                }
                zzH(zzbgVar2, zzrVar);
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    zzH(new zzbg((zzbg) it2.next(), j), zzrVar);
                }
                zzj().zzc();
            } finally {
                zzj().zzd();
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final Bundle zzG(String str, zzbg zzbgVar) {
        Bundle bundle = new Bundle();
        bundle.putLong("_sid", zzbgVar.zzb.zzb("_sid").longValue());
        zzpm zzpmVarZzm = zzj().zzm(str, "_sno");
        if (zzpmVarZzm != null) {
            Object obj = zzpmVarZzm.zze;
            if (obj instanceof Long) {
                bundle.putLong("_sno", ((Long) obj).longValue());
            }
        }
        return bundle;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r0v117, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r0v118, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r0v123, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r13v20, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r13v21, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r13v22, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r44v0, resolved type: com.google.android.gms.measurement.internal.zzpf */
    /* JADX DEBUG: Multi-variable search result rejected for r4v73, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v74, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v76, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v77, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v79, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v80, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v82, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v83, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v85, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v86, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v88, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v89, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v91, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v92, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v93, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v94, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v95, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r4v96, resolved type: boolean */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x03e6  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x07b4  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0879  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x018a A[PHI: r28 r29
  0x018a: PHI (r28v6 java.lang.String) = (r28v1 java.lang.String), (r28v1 java.lang.String), (r28v7 java.lang.String) binds: [B:69:0x0209, B:71:0x0217, B:51:0x0186] A[DONT_GENERATE, DONT_INLINE]
  0x018a: PHI (r29v6 java.lang.String) = (r29v1 java.lang.String), (r29v1 java.lang.String), (r29v7 java.lang.String) binds: [B:69:0x0209, B:71:0x0217, B:51:0x0186] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x019a A[Catch: all -> 0x0b6e, TryCatch #7 {all -> 0x0b6e, blocks: (B:36:0x0155, B:39:0x0164, B:41:0x016c, B:46:0x0176, B:88:0x02f8, B:97:0x032e, B:99:0x036f, B:101:0x0374, B:102:0x038b, B:104:0x0396, B:106:0x03af, B:108:0x03b4, B:109:0x03cb, B:112:0x03ed, B:116:0x0410, B:117:0x0427, B:119:0x0433, B:122:0x0450, B:123:0x0464, B:125:0x046c, B:127:0x0478, B:129:0x047e, B:130:0x0485, B:132:0x0492, B:134:0x049a, B:136:0x04a2, B:138:0x04aa, B:139:0x04b6, B:140:0x04c3, B:146:0x0505, B:147:0x051a, B:149:0x053c, B:152:0x0553, B:155:0x058e, B:157:0x05b9, B:159:0x05f1, B:160:0x05f4, B:162:0x05fc, B:163:0x05ff, B:165:0x0607, B:166:0x060a, B:168:0x0612, B:169:0x0615, B:171:0x061e, B:172:0x0622, B:174:0x062f, B:175:0x0632, B:177:0x065e, B:179:0x0668, B:183:0x067d, B:188:0x0689, B:191:0x0692, B:195:0x069f, B:199:0x06ad, B:203:0x06bb, B:207:0x06c9, B:211:0x06d7, B:215:0x06e2, B:219:0x06ef, B:220:0x06fb, B:222:0x0701, B:223:0x0704, B:225:0x0727, B:228:0x0730, B:231:0x0738, B:232:0x0752, B:234:0x0758, B:236:0x076c, B:238:0x0778, B:240:0x0785, B:243:0x079e, B:244:0x07ae, B:248:0x07b7, B:249:0x07ba, B:251:0x07c7, B:252:0x07cc, B:254:0x07ea, B:256:0x07ee, B:258:0x07fe, B:260:0x0809, B:261:0x0814, B:263:0x081e, B:265:0x082a, B:267:0x0834, B:269:0x083a, B:271:0x0849, B:273:0x085f, B:275:0x0865, B:276:0x086e, B:278:0x087c, B:280:0x08b8, B:282:0x08c2, B:283:0x08c5, B:285:0x08cf, B:287:0x08eb, B:288:0x08f6, B:290:0x092e, B:292:0x0936, B:294:0x0940, B:295:0x094d, B:297:0x0957, B:298:0x0964, B:299:0x096d, B:301:0x0973, B:303:0x09af, B:305:0x09b9, B:307:0x09cb, B:309:0x09d1, B:310:0x0a16, B:311:0x0a21, B:312:0x0a2c, B:314:0x0a32, B:323:0x0a82, B:324:0x0acd, B:326:0x0ade, B:338:0x0b3d, B:329:0x0af4, B:331:0x0af8, B:317:0x0a42, B:319:0x0a6e, B:334:0x0b0f, B:335:0x0b26, B:337:0x0b28, B:226:0x072a, B:156:0x05ab, B:143:0x04ec, B:91:0x030c, B:92:0x0313, B:94:0x0319, B:96:0x0328, B:53:0x018e, B:55:0x019a, B:57:0x01af, B:63:0x01cf, B:68:0x0205, B:70:0x020b, B:72:0x0219, B:74:0x022e, B:77:0x0235, B:85:0x02c1, B:87:0x02cb, B:79:0x0260, B:80:0x027e, B:84:0x02a4, B:83:0x0291, B:66:0x01db, B:67:0x01f9), top: B:358:0x0155, inners: #0, #2, #3, #4, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01f9 A[Catch: all -> 0x0b6e, TryCatch #7 {all -> 0x0b6e, blocks: (B:36:0x0155, B:39:0x0164, B:41:0x016c, B:46:0x0176, B:88:0x02f8, B:97:0x032e, B:99:0x036f, B:101:0x0374, B:102:0x038b, B:104:0x0396, B:106:0x03af, B:108:0x03b4, B:109:0x03cb, B:112:0x03ed, B:116:0x0410, B:117:0x0427, B:119:0x0433, B:122:0x0450, B:123:0x0464, B:125:0x046c, B:127:0x0478, B:129:0x047e, B:130:0x0485, B:132:0x0492, B:134:0x049a, B:136:0x04a2, B:138:0x04aa, B:139:0x04b6, B:140:0x04c3, B:146:0x0505, B:147:0x051a, B:149:0x053c, B:152:0x0553, B:155:0x058e, B:157:0x05b9, B:159:0x05f1, B:160:0x05f4, B:162:0x05fc, B:163:0x05ff, B:165:0x0607, B:166:0x060a, B:168:0x0612, B:169:0x0615, B:171:0x061e, B:172:0x0622, B:174:0x062f, B:175:0x0632, B:177:0x065e, B:179:0x0668, B:183:0x067d, B:188:0x0689, B:191:0x0692, B:195:0x069f, B:199:0x06ad, B:203:0x06bb, B:207:0x06c9, B:211:0x06d7, B:215:0x06e2, B:219:0x06ef, B:220:0x06fb, B:222:0x0701, B:223:0x0704, B:225:0x0727, B:228:0x0730, B:231:0x0738, B:232:0x0752, B:234:0x0758, B:236:0x076c, B:238:0x0778, B:240:0x0785, B:243:0x079e, B:244:0x07ae, B:248:0x07b7, B:249:0x07ba, B:251:0x07c7, B:252:0x07cc, B:254:0x07ea, B:256:0x07ee, B:258:0x07fe, B:260:0x0809, B:261:0x0814, B:263:0x081e, B:265:0x082a, B:267:0x0834, B:269:0x083a, B:271:0x0849, B:273:0x085f, B:275:0x0865, B:276:0x086e, B:278:0x087c, B:280:0x08b8, B:282:0x08c2, B:283:0x08c5, B:285:0x08cf, B:287:0x08eb, B:288:0x08f6, B:290:0x092e, B:292:0x0936, B:294:0x0940, B:295:0x094d, B:297:0x0957, B:298:0x0964, B:299:0x096d, B:301:0x0973, B:303:0x09af, B:305:0x09b9, B:307:0x09cb, B:309:0x09d1, B:310:0x0a16, B:311:0x0a21, B:312:0x0a2c, B:314:0x0a32, B:323:0x0a82, B:324:0x0acd, B:326:0x0ade, B:338:0x0b3d, B:329:0x0af4, B:331:0x0af8, B:317:0x0a42, B:319:0x0a6e, B:334:0x0b0f, B:335:0x0b26, B:337:0x0b28, B:226:0x072a, B:156:0x05ab, B:143:0x04ec, B:91:0x030c, B:92:0x0313, B:94:0x0319, B:96:0x0328, B:53:0x018e, B:55:0x019a, B:57:0x01af, B:63:0x01cf, B:68:0x0205, B:70:0x020b, B:72:0x0219, B:74:0x022e, B:77:0x0235, B:85:0x02c1, B:87:0x02cb, B:79:0x0260, B:80:0x027e, B:84:0x02a4, B:83:0x0291, B:66:0x01db, B:67:0x01f9), top: B:358:0x0155, inners: #0, #2, #3, #4, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x020b A[Catch: all -> 0x0b6e, TryCatch #7 {all -> 0x0b6e, blocks: (B:36:0x0155, B:39:0x0164, B:41:0x016c, B:46:0x0176, B:88:0x02f8, B:97:0x032e, B:99:0x036f, B:101:0x0374, B:102:0x038b, B:104:0x0396, B:106:0x03af, B:108:0x03b4, B:109:0x03cb, B:112:0x03ed, B:116:0x0410, B:117:0x0427, B:119:0x0433, B:122:0x0450, B:123:0x0464, B:125:0x046c, B:127:0x0478, B:129:0x047e, B:130:0x0485, B:132:0x0492, B:134:0x049a, B:136:0x04a2, B:138:0x04aa, B:139:0x04b6, B:140:0x04c3, B:146:0x0505, B:147:0x051a, B:149:0x053c, B:152:0x0553, B:155:0x058e, B:157:0x05b9, B:159:0x05f1, B:160:0x05f4, B:162:0x05fc, B:163:0x05ff, B:165:0x0607, B:166:0x060a, B:168:0x0612, B:169:0x0615, B:171:0x061e, B:172:0x0622, B:174:0x062f, B:175:0x0632, B:177:0x065e, B:179:0x0668, B:183:0x067d, B:188:0x0689, B:191:0x0692, B:195:0x069f, B:199:0x06ad, B:203:0x06bb, B:207:0x06c9, B:211:0x06d7, B:215:0x06e2, B:219:0x06ef, B:220:0x06fb, B:222:0x0701, B:223:0x0704, B:225:0x0727, B:228:0x0730, B:231:0x0738, B:232:0x0752, B:234:0x0758, B:236:0x076c, B:238:0x0778, B:240:0x0785, B:243:0x079e, B:244:0x07ae, B:248:0x07b7, B:249:0x07ba, B:251:0x07c7, B:252:0x07cc, B:254:0x07ea, B:256:0x07ee, B:258:0x07fe, B:260:0x0809, B:261:0x0814, B:263:0x081e, B:265:0x082a, B:267:0x0834, B:269:0x083a, B:271:0x0849, B:273:0x085f, B:275:0x0865, B:276:0x086e, B:278:0x087c, B:280:0x08b8, B:282:0x08c2, B:283:0x08c5, B:285:0x08cf, B:287:0x08eb, B:288:0x08f6, B:290:0x092e, B:292:0x0936, B:294:0x0940, B:295:0x094d, B:297:0x0957, B:298:0x0964, B:299:0x096d, B:301:0x0973, B:303:0x09af, B:305:0x09b9, B:307:0x09cb, B:309:0x09d1, B:310:0x0a16, B:311:0x0a21, B:312:0x0a2c, B:314:0x0a32, B:323:0x0a82, B:324:0x0acd, B:326:0x0ade, B:338:0x0b3d, B:329:0x0af4, B:331:0x0af8, B:317:0x0a42, B:319:0x0a6e, B:334:0x0b0f, B:335:0x0b26, B:337:0x0b28, B:226:0x072a, B:156:0x05ab, B:143:0x04ec, B:91:0x030c, B:92:0x0313, B:94:0x0319, B:96:0x0328, B:53:0x018e, B:55:0x019a, B:57:0x01af, B:63:0x01cf, B:68:0x0205, B:70:0x020b, B:72:0x0219, B:74:0x022e, B:77:0x0235, B:85:0x02c1, B:87:0x02cb, B:79:0x0260, B:80:0x027e, B:84:0x02a4, B:83:0x0291, B:66:0x01db, B:67:0x01f9), top: B:358:0x0155, inners: #0, #2, #3, #4, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x030c A[Catch: all -> 0x0b6e, TryCatch #7 {all -> 0x0b6e, blocks: (B:36:0x0155, B:39:0x0164, B:41:0x016c, B:46:0x0176, B:88:0x02f8, B:97:0x032e, B:99:0x036f, B:101:0x0374, B:102:0x038b, B:104:0x0396, B:106:0x03af, B:108:0x03b4, B:109:0x03cb, B:112:0x03ed, B:116:0x0410, B:117:0x0427, B:119:0x0433, B:122:0x0450, B:123:0x0464, B:125:0x046c, B:127:0x0478, B:129:0x047e, B:130:0x0485, B:132:0x0492, B:134:0x049a, B:136:0x04a2, B:138:0x04aa, B:139:0x04b6, B:140:0x04c3, B:146:0x0505, B:147:0x051a, B:149:0x053c, B:152:0x0553, B:155:0x058e, B:157:0x05b9, B:159:0x05f1, B:160:0x05f4, B:162:0x05fc, B:163:0x05ff, B:165:0x0607, B:166:0x060a, B:168:0x0612, B:169:0x0615, B:171:0x061e, B:172:0x0622, B:174:0x062f, B:175:0x0632, B:177:0x065e, B:179:0x0668, B:183:0x067d, B:188:0x0689, B:191:0x0692, B:195:0x069f, B:199:0x06ad, B:203:0x06bb, B:207:0x06c9, B:211:0x06d7, B:215:0x06e2, B:219:0x06ef, B:220:0x06fb, B:222:0x0701, B:223:0x0704, B:225:0x0727, B:228:0x0730, B:231:0x0738, B:232:0x0752, B:234:0x0758, B:236:0x076c, B:238:0x0778, B:240:0x0785, B:243:0x079e, B:244:0x07ae, B:248:0x07b7, B:249:0x07ba, B:251:0x07c7, B:252:0x07cc, B:254:0x07ea, B:256:0x07ee, B:258:0x07fe, B:260:0x0809, B:261:0x0814, B:263:0x081e, B:265:0x082a, B:267:0x0834, B:269:0x083a, B:271:0x0849, B:273:0x085f, B:275:0x0865, B:276:0x086e, B:278:0x087c, B:280:0x08b8, B:282:0x08c2, B:283:0x08c5, B:285:0x08cf, B:287:0x08eb, B:288:0x08f6, B:290:0x092e, B:292:0x0936, B:294:0x0940, B:295:0x094d, B:297:0x0957, B:298:0x0964, B:299:0x096d, B:301:0x0973, B:303:0x09af, B:305:0x09b9, B:307:0x09cb, B:309:0x09d1, B:310:0x0a16, B:311:0x0a21, B:312:0x0a2c, B:314:0x0a32, B:323:0x0a82, B:324:0x0acd, B:326:0x0ade, B:338:0x0b3d, B:329:0x0af4, B:331:0x0af8, B:317:0x0a42, B:319:0x0a6e, B:334:0x0b0f, B:335:0x0b26, B:337:0x0b28, B:226:0x072a, B:156:0x05ab, B:143:0x04ec, B:91:0x030c, B:92:0x0313, B:94:0x0319, B:96:0x0328, B:53:0x018e, B:55:0x019a, B:57:0x01af, B:63:0x01cf, B:68:0x0205, B:70:0x020b, B:72:0x0219, B:74:0x022e, B:77:0x0235, B:85:0x02c1, B:87:0x02cb, B:79:0x0260, B:80:0x027e, B:84:0x02a4, B:83:0x0291, B:66:0x01db, B:67:0x01f9), top: B:358:0x0155, inners: #0, #2, #3, #4, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x036f A[Catch: all -> 0x0b6e, TryCatch #7 {all -> 0x0b6e, blocks: (B:36:0x0155, B:39:0x0164, B:41:0x016c, B:46:0x0176, B:88:0x02f8, B:97:0x032e, B:99:0x036f, B:101:0x0374, B:102:0x038b, B:104:0x0396, B:106:0x03af, B:108:0x03b4, B:109:0x03cb, B:112:0x03ed, B:116:0x0410, B:117:0x0427, B:119:0x0433, B:122:0x0450, B:123:0x0464, B:125:0x046c, B:127:0x0478, B:129:0x047e, B:130:0x0485, B:132:0x0492, B:134:0x049a, B:136:0x04a2, B:138:0x04aa, B:139:0x04b6, B:140:0x04c3, B:146:0x0505, B:147:0x051a, B:149:0x053c, B:152:0x0553, B:155:0x058e, B:157:0x05b9, B:159:0x05f1, B:160:0x05f4, B:162:0x05fc, B:163:0x05ff, B:165:0x0607, B:166:0x060a, B:168:0x0612, B:169:0x0615, B:171:0x061e, B:172:0x0622, B:174:0x062f, B:175:0x0632, B:177:0x065e, B:179:0x0668, B:183:0x067d, B:188:0x0689, B:191:0x0692, B:195:0x069f, B:199:0x06ad, B:203:0x06bb, B:207:0x06c9, B:211:0x06d7, B:215:0x06e2, B:219:0x06ef, B:220:0x06fb, B:222:0x0701, B:223:0x0704, B:225:0x0727, B:228:0x0730, B:231:0x0738, B:232:0x0752, B:234:0x0758, B:236:0x076c, B:238:0x0778, B:240:0x0785, B:243:0x079e, B:244:0x07ae, B:248:0x07b7, B:249:0x07ba, B:251:0x07c7, B:252:0x07cc, B:254:0x07ea, B:256:0x07ee, B:258:0x07fe, B:260:0x0809, B:261:0x0814, B:263:0x081e, B:265:0x082a, B:267:0x0834, B:269:0x083a, B:271:0x0849, B:273:0x085f, B:275:0x0865, B:276:0x086e, B:278:0x087c, B:280:0x08b8, B:282:0x08c2, B:283:0x08c5, B:285:0x08cf, B:287:0x08eb, B:288:0x08f6, B:290:0x092e, B:292:0x0936, B:294:0x0940, B:295:0x094d, B:297:0x0957, B:298:0x0964, B:299:0x096d, B:301:0x0973, B:303:0x09af, B:305:0x09b9, B:307:0x09cb, B:309:0x09d1, B:310:0x0a16, B:311:0x0a21, B:312:0x0a2c, B:314:0x0a32, B:323:0x0a82, B:324:0x0acd, B:326:0x0ade, B:338:0x0b3d, B:329:0x0af4, B:331:0x0af8, B:317:0x0a42, B:319:0x0a6e, B:334:0x0b0f, B:335:0x0b26, B:337:0x0b28, B:226:0x072a, B:156:0x05ab, B:143:0x04ec, B:91:0x030c, B:92:0x0313, B:94:0x0319, B:96:0x0328, B:53:0x018e, B:55:0x019a, B:57:0x01af, B:63:0x01cf, B:68:0x0205, B:70:0x020b, B:72:0x0219, B:74:0x022e, B:77:0x0235, B:85:0x02c1, B:87:0x02cb, B:79:0x0260, B:80:0x027e, B:84:0x02a4, B:83:0x0291, B:66:0x01db, B:67:0x01f9), top: B:358:0x0155, inners: #0, #2, #3, #4, #5 }] */
    /* JADX WARN: Type inference failed for: r5v46 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void zzH(zzbg zzbgVar, zzr zzrVar) {
        String strZzd;
        String str;
        String str2;
        long jLongValue;
        zzbg zzbgVar2;
        zzpm zzpmVar;
        zzbe zzbeVar;
        long length;
        long jZzH;
        long j;
        ?? r5;
        long jDelete;
        zzbb zzbbVar;
        zzbc zzbcVar;
        ArrayList arrayList;
        String str3;
        zzjj zzjjVar;
        int i;
        zzav zzavVarZzj;
        com.google.android.gms.internal.measurement.zzid zzidVar;
        long jZzt;
        ContentValues contentValues;
        String str4;
        int i2;
        zzh zzhVarZzu;
        zzpm zzpmVarZzm;
        Preconditions.checkNotNull(zzrVar);
        String str5 = zzrVar.zza;
        Preconditions.checkNotEmpty(str5);
        long jNanoTime = System.nanoTime();
        zzaW().zzg();
        zzu();
        zzp();
        if (zzpj.zzD(zzbgVar, zzrVar)) {
            if (!zzrVar.zzh) {
                zzan(zzrVar);
                return;
            }
            zzhs zzhsVarZzh = zzh();
            String str6 = zzbgVar.zza;
            if (zzhsVarZzh.zzj(str5, str6)) {
                zzaV().zze().zzc("Dropping blocked event. appId", zzgt.zzl(str5), this.zzn.zzl().zza(str6));
                if (!zzh().zzn(str5) && !zzh().zzo(str5)) {
                    if ("_err".equals(str6)) {
                        return;
                    }
                    zzt().zzN(this.zzK, str5, 11, "_ev", str6, 0);
                    return;
                }
                zzh zzhVarZzu2 = zzj().zzu(str5);
                if (zzhVarZzu2 != null) {
                    long jAbs = Math.abs(zzaZ().currentTimeMillis() - Math.max(zzhVarZzu2.zzJ(), zzhVarZzu2.zzH()));
                    zzd();
                    if (jAbs > ((Long) zzfx.zzN.zzb(null)).longValue()) {
                        zzaV().zzj().zza("Fetching config for blocked app");
                        zzV(zzhVarZzu2);
                        return;
                    }
                    return;
                }
                return;
            }
            zzgu zzguVarZza = zzgu.zza(zzbgVar);
            zzt().zzG(zzguVarZza, zzd().zzd(str5));
            int iZzn = zzd().zzn(str5, zzfx.zzag, 10, 35);
            Bundle bundle = zzguVarZza.zzd;
            for (String str7 : new TreeSet(bundle.keySet())) {
                if (FirebaseAnalytics.Param.ITEMS.equals(str7)) {
                    zzt().zzH(bundle.getParcelableArray(str7), iZzn);
                }
            }
            zzbg zzbgVarZzb = zzguVarZza.zzb();
            if (Log.isLoggable(zzaV().zzn(), 2)) {
                zzaV().zzk().zzb("Logging event", this.zzn.zzl().zzd(zzbgVarZzb));
            }
            zzj().zzb();
            try {
                zzan(zzrVar);
                String str8 = zzbgVarZzb.zza;
                boolean z = "ecommerce_purchase".equals(str8) || FirebaseAnalytics.Event.PURCHASE.equals(str8) || FirebaseAnalytics.Event.REFUND.equals(str8);
                if ("_iap".equals(str8)) {
                    zzbe zzbeVar2 = zzbgVarZzb.zzb;
                    strZzd = zzbeVar2.zzd(FirebaseAnalytics.Param.CURRENCY);
                    if (z) {
                        str = ClientContext.APP_ID_KEY;
                        str2 = "_fx";
                        jLongValue = zzbeVar2.zzb("value").longValue();
                    } else {
                        double dDoubleValue = zzbeVar2.zzc("value").doubleValue() * 1000000.0d;
                        if (dDoubleValue == 0.0d) {
                            Long lZzb = zzbeVar2.zzb("value");
                            str = ClientContext.APP_ID_KEY;
                            str2 = "_fx";
                            dDoubleValue = lZzb.longValue() * 1000000.0d;
                        } else {
                            str = ClientContext.APP_ID_KEY;
                            str2 = "_fx";
                        }
                        if (dDoubleValue > 9.223372036854776E18d || dDoubleValue < -9.223372036854776E18d) {
                            zzaV().zze().zzc("Data lost. Currency value is too big. appId", zzgt.zzl(str5), Double.valueOf(dDoubleValue));
                            zzj().zzc();
                        } else {
                            jLongValue = Math.round(dDoubleValue);
                            if (FirebaseAnalytics.Event.REFUND.equals(str8)) {
                                jLongValue = -jLongValue;
                            }
                        }
                    }
                    if (TextUtils.isEmpty(strZzd)) {
                        String upperCase = strZzd.toUpperCase(Locale.US);
                        if (upperCase.matches("[A-Z]{3}")) {
                            String.valueOf(upperCase);
                            String strConcat = "_ltv_".concat(String.valueOf(upperCase));
                            zzpm zzpmVarZzm2 = zzj().zzm(str5, strConcat);
                            if (zzpmVarZzm2 == null || !(zzpmVarZzm2.zze instanceof Long)) {
                                zzav zzavVarZzj2 = zzj();
                                int iZzm = zzd().zzm(str5, zzfx.zzT) - 1;
                                Preconditions.checkNotEmpty(str5);
                                zzavVarZzj2.zzg();
                                zzavVarZzj2.zzay();
                                try {
                                    zzavVarZzj2.zze().execSQL("delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '!_ltv!_%' escape '!'order by set_timestamp desc limit ?,10);", new String[]{str5, str5, String.valueOf(iZzm)});
                                } catch (SQLiteException e) {
                                    zzavVarZzj2.zzu.zzaV().zzb().zzc("Error pruning currencies. appId", zzgt.zzl(str5), e);
                                }
                                zzbgVar2 = zzbgVarZzb;
                                zzpmVar = new zzpm(str5, zzbgVarZzb.zzc, strConcat, zzaZ().currentTimeMillis(), Long.valueOf(jLongValue));
                                str5 = str5;
                            } else {
                                zzbgVar2 = zzbgVarZzb;
                                zzpmVar = new zzpm(str5, zzbgVarZzb.zzc, strConcat, zzaZ().currentTimeMillis(), Long.valueOf(((Long) zzpmVarZzm2.zze).longValue() + jLongValue));
                                str5 = str5;
                            }
                            zzpm zzpmVar2 = zzpmVar;
                            if (!zzj().zzl(zzpmVar2)) {
                                zzaV().zzb().zzd("Too many unique user properties are set. Ignoring user property. appId", zzgt.zzl(str5), this.zzn.zzl().zzc(zzpmVar2.zzc), zzpmVar2.zze);
                                zzt().zzN(this.zzK, str5, 9, null, null, 0);
                            }
                        } else {
                            zzbgVar2 = zzbgVarZzb;
                        }
                        String str9 = zzbgVar2.zza;
                        boolean zZzh = zzpo.zzh(str9);
                        boolean zEquals = "_err".equals(str9);
                        zzt();
                        zzbeVar = zzbgVar2.zzb;
                        if (zzbeVar != null) {
                            length = 0;
                        } else {
                            zzbd zzbdVar = new zzbd(zzbeVar);
                            length = 0;
                            while (zzbdVar.hasNext()) {
                                String next = zzbdVar.next();
                                String str10 = next;
                                Object objZza = zzbeVar.zza(next);
                                if (objZza instanceof Parcelable[]) {
                                    length += (long) ((Parcelable[]) objZza).length;
                                }
                            }
                        }
                        String str11 = str5;
                        zzbg zzbgVar3 = zzbgVar2;
                        zzar zzarVarZzx = zzj().zzx(zzC(), str11, length + 1, true, zZzh, false, zEquals, false, false, false);
                        long j2 = zzarVarZzx.zzb;
                        zzd();
                        jZzH = j2 - zzal.zzH();
                        if (jZzH <= 0) {
                            if (jZzH % 1000 == 1) {
                                zzaV().zzb().zzc("Data loss. Too many events logged. appId, count", zzgt.zzl(str11), Long.valueOf(zzarVarZzx.zzb));
                            }
                            zzj().zzc();
                        } else if (zZzh) {
                            long j3 = zzarVarZzx.zza;
                            zzd();
                            long jIntValue = j3 - ((long) ((Integer) zzfx.zzm.zzb(null)).intValue());
                            if (jIntValue <= 0) {
                                if (zEquals) {
                                    j = 1;
                                    r5 = 0;
                                    long jMax = zzarVarZzx.zzd - ((long) Math.max(0, Math.min(DurationKt.NANOS_IN_MILLIS, zzd().zzm(zzrVar.zza, zzfx.zzl))));
                                    if (jMax > 0) {
                                        if (jMax == 1) {
                                            zzaV().zzb().zzc("Too many error events logged. appId, count", zzgt.zzl(str11), Long.valueOf(zzarVarZzx.zzd));
                                        }
                                        zzj().zzc();
                                    }
                                } else {
                                    j = 1;
                                    r5 = 0;
                                }
                                Bundle bundleZzf = zzbeVar.zzf();
                                zzpo zzpoVarZzt = zzt();
                                String str12 = zzbgVar3.zzc;
                                zzpoVarZzt.zzM(bundleZzf, "_o", str12);
                                if (zzt().zzaa(str11, zzrVar.zzB)) {
                                    zzpo zzpoVarZzt2 = zzt();
                                    Long lValueOf = Long.valueOf(j);
                                    zzpoVarZzt2.zzM(bundleZzf, "_dbg", lValueOf);
                                    zzt().zzM(bundleZzf, "_r", lValueOf);
                                }
                                if ("_s".equals(str9) && (zzpmVarZzm = zzj().zzm(zzrVar.zza, "_sno")) != null) {
                                    Object obj = zzpmVarZzm.zze;
                                    if (obj instanceof Long) {
                                        zzt().zzM(bundleZzf, "_sno", obj);
                                    }
                                }
                                if (zzd().zzp(null, zzfx.zzaX) && Objects.equals(str12, "am") && Objects.equals(str9, "_ai")) {
                                    Object obj2 = bundleZzf.get("value");
                                    if (obj2 instanceof String) {
                                        try {
                                            double d = Double.parseDouble((String) obj2);
                                            bundleZzf.remove("value");
                                            bundleZzf.putDouble("value", d);
                                        } catch (NumberFormatException unused) {
                                        }
                                    }
                                }
                                zzav zzavVarZzj3 = zzj();
                                Preconditions.checkNotEmpty(str11);
                                zzavVarZzj3.zzg();
                                zzavVarZzj3.zzay();
                                try {
                                    jDelete = zzavVarZzj3.zze().delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str11, String.valueOf(Math.max((int) r5, Math.min(DurationKt.NANOS_IN_MILLIS, zzavVarZzj3.zzu.zzc().zzm(str11, zzfx.zzp))))});
                                } catch (SQLiteException e2) {
                                    zzavVarZzj3.zzu.zzaV().zzb().zzc("Error deleting over the limit events. appId", zzgt.zzl(str11), e2);
                                    jDelete = 0;
                                }
                                if (jDelete > 0) {
                                    zzaV().zze().zzc("Data lost. Too many events stored on disk, deleted. appId", zzgt.zzl(str11), Long.valueOf(jDelete));
                                }
                                zzib zzibVar = this.zzn;
                                zzbb zzbbVar2 = new zzbb(zzibVar, zzbgVar3.zzc, str11, zzbgVar3.zza, zzbgVar3.zzd, 0L, bundleZzf);
                                zzav zzavVarZzj4 = zzj();
                                String str13 = zzbbVar2.zzb;
                                zzbc zzbcVarZzf = zzavVarZzj4.zzf(str11, str13);
                                if (zzbcVarZzf != null) {
                                    zzbb zzbbVarZza = zzbbVar2.zza(zzibVar, zzbcVarZzf.zzf);
                                    zzbc zzbcVarZza = zzbcVarZzf.zza(zzbbVarZza.zzd);
                                    zzbbVar = zzbbVarZza;
                                    zzbcVar = zzbcVarZza;
                                } else if (zzj().zzU(str11) < zzd().zzh(str11) || !zZzh) {
                                    zzbcVar = new zzbc(str11, str13, 0L, 0L, 0L, zzbbVar2.zzd, 0L, null, null, null, null);
                                    zzbbVar = zzbbVar2;
                                } else {
                                    zzaV().zzb().zzd("Too many event names used, ignoring event. appId, name, supported count", zzgt.zzl(str11), zzibVar.zzl().zza(str13), Integer.valueOf(zzd().zzh(str11)));
                                    zzt().zzN(this.zzK, str11, 8, null, null, 0);
                                }
                                zzj().zzh(zzbcVar);
                                zzaW().zzg();
                                zzu();
                                Preconditions.checkNotNull(zzbbVar);
                                Preconditions.checkNotNull(zzrVar);
                                String str14 = zzbbVar.zza;
                                Preconditions.checkNotEmpty(str14);
                                String str15 = zzrVar.zza;
                                Preconditions.checkArgument(str14.equals(str15));
                                com.google.android.gms.internal.measurement.zzic zzicVarZzaE = com.google.android.gms.internal.measurement.zzid.zzaE();
                                zzicVarZzaE.zza(1);
                                zzicVarZzaE.zzC("android");
                                if (!TextUtils.isEmpty(str15)) {
                                    zzicVarZzaE.zzL(str15);
                                }
                                String str16 = zzrVar.zzd;
                                if (!TextUtils.isEmpty(str16)) {
                                    zzicVarZzaE.zzJ(str16);
                                }
                                String str17 = zzrVar.zzc;
                                if (!TextUtils.isEmpty(str17)) {
                                    zzicVarZzaE.zzM(str17);
                                }
                                String str18 = zzrVar.zzu;
                                if (!TextUtils.isEmpty(str18)) {
                                    zzicVarZzaE.zzau(str18);
                                }
                                long j4 = zzrVar.zzj;
                                if (j4 != -2147483648L) {
                                    zzicVarZzaE.zzaj((int) j4);
                                }
                                zzicVarZzaE.zzN(zzrVar.zze);
                                String str19 = zzrVar.zzb;
                                if (!TextUtils.isEmpty(str19)) {
                                    zzicVarZzaE.zzad(str19);
                                }
                                zzjk zzjkVarZzs = zzB((String) Preconditions.checkNotNull(str15)).zzs(zzjk.zzf(zzrVar.zzs, 100));
                                zzicVarZzaE.zzat(zzjkVarZzs.zzk());
                                zzql.zza();
                                if (zzd().zzp(str15, zzfx.zzaP) && zzt().zzX(str15)) {
                                    zzicVarZzaE.zzaH(zzrVar.zzz);
                                    long j5 = zzrVar.zzA;
                                    if (!zzjkVarZzs.zzo(zzjj.AD_STORAGE) && j5 != 0) {
                                        j5 = (j5 & (-2)) | 32;
                                    }
                                    zzicVarZzaE.zzaz(j5 == j ? 1 : r5);
                                    if (j5 != 0) {
                                        com.google.android.gms.internal.measurement.zzhd zzhdVarZzh = com.google.android.gms.internal.measurement.zzhe.zzh();
                                        zzhdVarZzh.zza((j5 & j) != 0 ? 1 : r5);
                                        zzhdVarZzh.zzb((j5 & 2) != 0 ? 1 : r5);
                                        zzhdVarZzh.zzc((j5 & 4) != 0 ? 1 : r5);
                                        zzhdVarZzh.zzd((j5 & 8) != 0 ? 1 : r5);
                                        zzhdVarZzh.zze((j5 & 16) != 0 ? 1 : r5);
                                        zzhdVarZzh.zzf((32 & j5) != 0 ? 1 : r5);
                                        zzhdVarZzh.zzg((j5 & 64) != 0 ? 1 : r5);
                                        zzicVarZzaE.zzaI((com.google.android.gms.internal.measurement.zzhe) zzhdVarZzh.zzbc());
                                    }
                                }
                                long j6 = zzrVar.zzf;
                                if (j6 != 0) {
                                    zzicVarZzaE.zzY(j6);
                                }
                                zzicVarZzaE.zzar(zzrVar.zzq);
                                zzpj zzpjVarZzp = zzp();
                                com.google.android.gms.internal.measurement.zzjq zzjqVarZza = com.google.android.gms.internal.measurement.zzjq.zza(zzpjVarZzp.zzg.zzn.zzaY().getContentResolver(), com.google.android.gms.internal.measurement.zzka.zza("com.google.android.gms.measurement"), zzfu.zza);
                                Map mapZzb = zzjqVarZza == null ? Collections.EMPTY_MAP : zzjqVarZza.zzb();
                                if (mapZzb == null || mapZzb.isEmpty()) {
                                    arrayList = null;
                                } else {
                                    arrayList = new ArrayList();
                                    int iIntValue = ((Integer) zzfx.zzaf.zzb(null)).intValue();
                                    for (Map.Entry entry : mapZzb.entrySet()) {
                                        if (((String) entry.getKey()).startsWith("measurement.id.")) {
                                            try {
                                                int i3 = Integer.parseInt((String) entry.getValue());
                                                if (i3 != 0) {
                                                    arrayList.add(Integer.valueOf(i3));
                                                    if (arrayList.size() >= iIntValue) {
                                                        zzpjVarZzp.zzu.zzaV().zze().zzb("Too many experiment IDs. Number of IDs", Integer.valueOf(arrayList.size()));
                                                        break;
                                                    }
                                                    continue;
                                                } else {
                                                    continue;
                                                }
                                            } catch (NumberFormatException e3) {
                                                zzpjVarZzp.zzu.zzaV().zze().zzb("Experiment ID NumberFormatException", e3);
                                            }
                                        }
                                    }
                                    if (arrayList.isEmpty()) {
                                    }
                                }
                                if (arrayList != null) {
                                    zzicVarZzaE.zzaq(arrayList);
                                }
                                if (zzd().zzp(null, zzfx.zzbb)) {
                                    zzicVarZzaE.zzaP("");
                                }
                                String str20 = zzrVar.zza;
                                zzjk zzjkVarZzs2 = zzB((String) Preconditions.checkNotNull(str20)).zzs(zzjk.zzf(zzrVar.zzs, 100));
                                zzjj zzjjVar2 = zzjj.AD_STORAGE;
                                if (zzjkVarZzs2.zzo(zzjjVar2) && zzrVar.zzn) {
                                    Pair pairZzc = this.zzk.zzc(str20, zzjkVarZzs2);
                                    if (!TextUtils.isEmpty((CharSequence) pairZzc.first)) {
                                        zzicVarZzaE.zzQ((String) pairZzc.first);
                                        if (pairZzc.second != null) {
                                            zzicVarZzaE.zzT(((Boolean) pairZzc.second).booleanValue());
                                        }
                                        String str21 = str2;
                                        if (!zzbbVar.zzb.equals(str21) && !((String) pairZzc.first).equals("00000000-0000-0000-0000-000000000000") && (zzhVarZzu = zzj().zzu(str20)) != null && zzhVarZzu.zzaq()) {
                                            zzR(str20, r5, null, null);
                                            Bundle bundle2 = new Bundle();
                                            Long lZzas = zzhVarZzu.zzas();
                                            if (lZzas != null) {
                                                str3 = "raw_events";
                                                long jLongValue2 = lZzas.longValue();
                                                zzjjVar = zzjjVar2;
                                                bundle2.putLong("_pfo", Math.max(0L, jLongValue2));
                                            } else {
                                                str3 = "raw_events";
                                                zzjjVar = zzjjVar2;
                                            }
                                            Long lZzau = zzhVarZzu.zzau();
                                            if (lZzau != null) {
                                                bundle2.putLong("_uwa", lZzau.longValue());
                                            }
                                            bundle2.putLong("_r", j);
                                            this.zzK.zza(str20, str21, bundle2);
                                        }
                                    }
                                } else {
                                    str3 = "raw_events";
                                    zzjjVar = zzjjVar2;
                                }
                                zzib zzibVar2 = this.zzn;
                                zzibVar2.zzu().zzw();
                                zzicVarZzaE.zzF(Build.MODEL);
                                zzibVar2.zzu().zzw();
                                zzicVarZzaE.zzE(Build.VERSION.RELEASE);
                                zzicVarZzaE.zzI((int) zzibVar2.zzu().zzb());
                                zzicVarZzaE.zzH(zzibVar2.zzu().zzc());
                                zzicVarZzaE.zzay(zzrVar.zzw);
                                if (zzibVar2.zzB()) {
                                    zzicVarZzaE.zzK();
                                    if (!TextUtils.isEmpty(null)) {
                                        zzicVarZzaE.zzam(null);
                                    }
                                }
                                zzh zzhVarZzu3 = zzj().zzu(str20);
                                if (zzhVarZzu3 == null) {
                                    zzhVarZzu3 = new zzh(zzibVar2, str20);
                                    zzhVarZzu3.zze(zzK(zzjkVarZzs2));
                                    zzhVarZzu3.zzm(zzrVar.zzk);
                                    zzhVarZzu3.zzg(zzrVar.zzb);
                                    if (zzjkVarZzs2.zzo(zzjjVar)) {
                                        zzhVarZzu3.zzk(this.zzk.zzf(str20, zzrVar.zzn));
                                    }
                                    zzhVarZzu3.zzF(0L);
                                    zzhVarZzu3.zzo(0L);
                                    zzhVarZzu3.zzq(0L);
                                    zzhVarZzu3.zzs(zzrVar.zzc);
                                    zzhVarZzu3.zzu(zzrVar.zzj);
                                    zzhVarZzu3.zzw(zzrVar.zzd);
                                    zzhVarZzu3.zzy(zzrVar.zze);
                                    zzhVarZzu3.zzA(zzrVar.zzf);
                                    zzhVarZzu3.zzE(zzrVar.zzh);
                                    zzhVarZzu3.zzC(zzrVar.zzq);
                                    i = 0;
                                    zzj().zzv(zzhVarZzu3, false, false);
                                } else {
                                    i = 0;
                                }
                                if (zzjkVarZzs2.zzo(zzjj.ANALYTICS_STORAGE) && !TextUtils.isEmpty(zzhVarZzu3.zzd())) {
                                    zzicVarZzaE.zzW((String) Preconditions.checkNotNull(zzhVarZzu3.zzd()));
                                }
                                if (!TextUtils.isEmpty(zzhVarZzu3.zzl())) {
                                    zzicVarZzaE.zzah((String) Preconditions.checkNotNull(zzhVarZzu3.zzl()));
                                }
                                List listZzn = zzj().zzn(str20);
                                for (int i4 = i; i4 < listZzn.size(); i4++) {
                                    com.google.android.gms.internal.measurement.zzit zzitVarZzm = com.google.android.gms.internal.measurement.zziu.zzm();
                                    zzitVarZzm.zzb(((zzpm) listZzn.get(i4)).zzc);
                                    zzitVarZzm.zza(((zzpm) listZzn.get(i4)).zzd);
                                    zzp().zzc(zzitVarZzm, ((zzpm) listZzn.get(i4)).zze);
                                    zzicVarZzaE.zzp(zzitVarZzm);
                                    if ("_sid".equals(((zzpm) listZzn.get(i4)).zzc) && zzhVarZzu3.zzam() != 0 && zzp().zzu(zzrVar.zzu) != zzhVarZzu3.zzam()) {
                                        zzicVarZzaE.zzav();
                                    }
                                }
                                try {
                                    zzavVarZzj = zzj();
                                    zzidVar = (com.google.android.gms.internal.measurement.zzid) zzicVarZzaE.zzbc();
                                    zzavVarZzj.zzg();
                                    zzavVarZzj.zzay();
                                    Preconditions.checkNotNull(zzidVar);
                                    Preconditions.checkNotEmpty(zzidVar.zzA());
                                    byte[] bArrZzcc = zzidVar.zzcc();
                                    jZzt = zzavVarZzj.zzg.zzp().zzt(bArrZzcc);
                                    contentValues = new ContentValues();
                                    str4 = str;
                                    contentValues.put(str4, zzidVar.zzA());
                                    contentValues.put("metadata_fingerprint", Long.valueOf(jZzt));
                                    contentValues.put("metadata", bArrZzcc);
                                } catch (IOException e4) {
                                    zzaV().zzb().zzc("Data loss. Failed to insert raw event metadata. appId", zzgt.zzl(zzicVarZzaE.zzK()), e4);
                                }
                                try {
                                    zzavVarZzj.zze().insertWithOnConflict("raw_events_metadata", null, contentValues, 4);
                                    zzav zzavVarZzj5 = zzj();
                                    zzbd zzbdVar2 = new zzbd(zzbbVar.zzf);
                                    while (true) {
                                        if (zzbdVar2.hasNext()) {
                                            String next2 = zzbdVar2.next();
                                            String str22 = next2;
                                            if ("_r".equals(next2)) {
                                                break;
                                            }
                                        } else {
                                            zzhs zzhsVarZzh2 = zzh();
                                            String str23 = zzbbVar.zza;
                                            boolean zZzk = zzhsVarZzh2.zzk(str23, zzbbVar.zzb);
                                            zzar zzarVarZzw = zzj().zzw(zzC(), str23, false, false, false, false, false, false, false);
                                            if (!zZzk || zzarVarZzw.zze >= zzd().zzm(str23, zzfx.zzo)) {
                                                i2 = i;
                                            }
                                        }
                                    }
                                    i2 = 1;
                                    zzavVarZzj5.zzg();
                                    zzavVarZzj5.zzay();
                                    Preconditions.checkNotNull(zzbbVar);
                                    String str24 = zzbbVar.zza;
                                    Preconditions.checkNotEmpty(str24);
                                    byte[] bArrZzcc2 = zzavVarZzj5.zzg.zzp().zzh(zzbbVar).zzcc();
                                    ContentValues contentValues2 = new ContentValues();
                                    contentValues2.put(str4, str24);
                                    contentValues2.put("name", zzbbVar.zzb);
                                    contentValues2.put("timestamp", Long.valueOf(zzbbVar.zzd));
                                    contentValues2.put("metadata_fingerprint", Long.valueOf(jZzt));
                                    contentValues2.put("data", bArrZzcc2);
                                    contentValues2.put("realtime", Integer.valueOf(i2));
                                    try {
                                        if (zzavVarZzj5.zze().insert(str3, null, contentValues2) == -1) {
                                            zzavVarZzj5.zzu.zzaV().zzb().zzb("Failed to insert raw event (got -1). appId", zzgt.zzl(str24));
                                        } else {
                                            this.zza = 0L;
                                        }
                                    } catch (SQLiteException e5) {
                                        zzavVarZzj5.zzu.zzaV().zzb().zzc("Error storing raw event. appId", zzgt.zzl(zzbbVar.zza), e5);
                                    }
                                    zzj().zzc();
                                    zzj().zzd();
                                    zzaK();
                                    zzaV().zzk().zzb("Background event processing time, ms", Long.valueOf(((System.nanoTime() - jNanoTime) + 500000) / 1000000));
                                    return;
                                } catch (SQLiteException e6) {
                                    zzavVarZzj.zzu.zzaV().zzb().zzc("Error storing raw event metadata. appId", zzgt.zzl(zzidVar.zzA()), e6);
                                    throw e6;
                                }
                            }
                            if (jIntValue % 1000 == 1) {
                                zzaV().zzb().zzc("Data loss. Too many public events logged. appId, count", zzgt.zzl(str11), Long.valueOf(zzarVarZzx.zza));
                            }
                            zzt().zzN(this.zzK, str11, 16, "_ev", zzbgVar3.zza, 0);
                            zzj().zzc();
                        }
                    }
                } else if (z) {
                    z = true;
                    zzbe zzbeVar22 = zzbgVarZzb.zzb;
                    strZzd = zzbeVar22.zzd(FirebaseAnalytics.Param.CURRENCY);
                    if (z) {
                    }
                    if (TextUtils.isEmpty(strZzd)) {
                    }
                } else {
                    str = ClientContext.APP_ID_KEY;
                    str2 = "_fx";
                    zzbgVar2 = zzbgVarZzb;
                    String str92 = zzbgVar2.zza;
                    boolean zZzh2 = zzpo.zzh(str92);
                    boolean zEquals2 = "_err".equals(str92);
                    zzt();
                    zzbeVar = zzbgVar2.zzb;
                    if (zzbeVar != null) {
                    }
                    String str112 = str5;
                    zzbg zzbgVar32 = zzbgVar2;
                    zzar zzarVarZzx2 = zzj().zzx(zzC(), str112, length + 1, true, zZzh2, false, zEquals2, false, false, false);
                    long j22 = zzarVarZzx2.zzb;
                    zzd();
                    jZzH = j22 - zzal.zzH();
                    if (jZzH <= 0) {
                    }
                }
            } finally {
                zzj().zzd();
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzI(zzh zzhVar, com.google.android.gms.internal.measurement.zzic zzicVar) {
        com.google.android.gms.internal.measurement.zziu zziuVar;
        zzaW().zzg();
        zzu();
        zzan zzanVarZzd = zzan.zzd(zzicVar.zzaA());
        String strZzc = zzhVar.zzc();
        zzaW().zzg();
        zzu();
        zzjk zzjkVarZzB = zzB(strZzc);
        zzjh zzjhVar = zzjh.UNINITIALIZED;
        int iOrdinal = zzjkVarZzB.zzp().ordinal();
        if (iOrdinal == 1) {
            zzanVarZzd.zzc(zzjj.AD_STORAGE, zzam.REMOTE_ENFORCED_DEFAULT);
        } else if (iOrdinal == 2 || iOrdinal == 3) {
            zzanVarZzd.zzb(zzjj.AD_STORAGE, zzjkVarZzB.zzb());
        } else {
            zzanVarZzd.zzc(zzjj.AD_STORAGE, zzam.FAILSAFE);
        }
        int iOrdinal2 = zzjkVarZzB.zzq().ordinal();
        if (iOrdinal2 == 1) {
            zzanVarZzd.zzc(zzjj.ANALYTICS_STORAGE, zzam.REMOTE_ENFORCED_DEFAULT);
        } else if (iOrdinal2 == 2 || iOrdinal2 == 3) {
            zzanVarZzd.zzb(zzjj.ANALYTICS_STORAGE, zzjkVarZzB.zzb());
        } else {
            zzanVarZzd.zzc(zzjj.ANALYTICS_STORAGE, zzam.FAILSAFE);
        }
        String strZzc2 = zzhVar.zzc();
        zzaW().zzg();
        zzu();
        zzaz zzazVarZzz = zzz(strZzc2, zzx(strZzc2), zzB(strZzc2), zzanVarZzd);
        zzicVar.zzaD(((Boolean) Preconditions.checkNotNull(zzazVarZzz.zzj())).booleanValue());
        if (!TextUtils.isEmpty(zzazVarZzz.zzk())) {
            zzicVar.zzaF(zzazVarZzz.zzk());
        }
        zzaW().zzg();
        zzu();
        Iterator it = zzicVar.zzk().iterator();
        while (true) {
            if (it.hasNext()) {
                zziuVar = (com.google.android.gms.internal.measurement.zziu) it.next();
                if ("_npa".equals(zziuVar.zzc())) {
                    break;
                }
            } else {
                zziuVar = null;
                break;
            }
        }
        if (zziuVar != null) {
            zzjj zzjjVar = zzjj.AD_PERSONALIZATION;
            if (zzanVarZzd.zza(zzjjVar) == zzam.UNSET) {
                zzpm zzpmVarZzm = zzj().zzm(zzhVar.zzc(), "_npa");
                if (zzpmVarZzm != null) {
                    String str = zzpmVarZzm.zzb;
                    if ("tcf".equals(str)) {
                        zzanVarZzd.zzc(zzjjVar, zzam.TCF);
                    } else if ("app".equals(str)) {
                        zzanVarZzd.zzc(zzjjVar, zzam.API);
                    } else {
                        zzanVarZzd.zzc(zzjjVar, zzam.MANIFEST);
                    }
                } else {
                    Boolean boolZzae = zzhVar.zzae();
                    if (boolZzae == null || ((boolZzae.booleanValue() && zziuVar.zzg() != 1) || !(boolZzae.booleanValue() || zziuVar.zzg() == 0))) {
                        zzanVarZzd.zzc(zzjjVar, zzam.API);
                    } else {
                        zzanVarZzd.zzc(zzjjVar, zzam.MANIFEST);
                    }
                }
            }
        } else {
            int iZzaB = zzaB(zzhVar.zzc(), zzanVarZzd);
            com.google.android.gms.internal.measurement.zzit zzitVarZzm = com.google.android.gms.internal.measurement.zziu.zzm();
            zzitVarZzm.zzb("_npa");
            zzitVarZzm.zza(zzaZ().currentTimeMillis());
            zzitVarZzm.zze(iZzaB);
            zzicVar.zzo((com.google.android.gms.internal.measurement.zziu) zzitVarZzm.zzbc());
            zzaV().zzk().zzc("Setting user property", "non_personalized_ads(_npa)", Integer.valueOf(iZzaB));
        }
        zzicVar.zzaB(zzanVarZzd.toString());
        boolean zZzy = this.zzc.zzy(zzhVar.zzc());
        List listZzb = zzicVar.zzb();
        int i = 0;
        for (int i2 = 0; i2 < listZzb.size(); i2++) {
            if ("_tcf".equals(((com.google.android.gms.internal.measurement.zzhs) listZzb.get(i2)).zzd())) {
                com.google.android.gms.internal.measurement.zzhr zzhrVar = (com.google.android.gms.internal.measurement.zzhr) ((com.google.android.gms.internal.measurement.zzhs) listZzb.get(i2)).zzcl();
                List listZza = zzhrVar.zza();
                int i3 = 0;
                while (true) {
                    if (i3 >= listZza.size()) {
                        break;
                    }
                    if ("_tcfd".equals(((com.google.android.gms.internal.measurement.zzhw) listZza.get(i3)).zzb())) {
                        String strZzd = ((com.google.android.gms.internal.measurement.zzhw) listZza.get(i3)).zzd();
                        if (zZzy && strZzd.length() > 4) {
                            char[] charArray = strZzd.toCharArray();
                            int i4 = 1;
                            while (true) {
                                if (i4 >= 64) {
                                    break;
                                }
                                if (charArray[4] == "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_".charAt(i4)) {
                                    i = i4;
                                    break;
                                }
                                i4++;
                            }
                            charArray[4] = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_".charAt(i | 1);
                            strZzd = String.valueOf(charArray);
                        }
                        com.google.android.gms.internal.measurement.zzhv zzhvVarZzn = com.google.android.gms.internal.measurement.zzhw.zzn();
                        zzhvVarZzn.zzb("_tcfd");
                        zzhvVarZzn.zzd(strZzd);
                        zzhrVar.zze(i3, zzhvVarZzn);
                    } else {
                        i3++;
                    }
                }
                zzicVar.zzf(i2, zzhrVar);
                return;
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzJ(zzh zzhVar, com.google.android.gms.internal.measurement.zzic zzicVar) {
        zzaW().zzg();
        zzu();
        com.google.android.gms.internal.measurement.zzgx zzgxVarZzr = com.google.android.gms.internal.measurement.zzha.zzr();
        byte[] bArrZzaJ = zzhVar.zzaJ();
        if (bArrZzaJ != null) {
            try {
                zzgxVarZzr = (com.google.android.gms.internal.measurement.zzgx) zzpj.zzw(zzgxVarZzr, bArrZzaJ);
            } catch (com.google.android.gms.internal.measurement.zzmq unused) {
                zzaV().zze().zzb("Failed to parse locally stored ad campaign info. appId", zzgt.zzl(zzhVar.zzc()));
            }
        }
        for (com.google.android.gms.internal.measurement.zzhs zzhsVar : zzicVar.zzb()) {
            if (zzhsVar.zzd().equals(Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN)) {
                String str = (String) zzpj.zzJ(zzhsVar, "gclid", "");
                String str2 = (String) zzpj.zzJ(zzhsVar, "gbraid", "");
                String str3 = (String) zzpj.zzJ(zzhsVar, "gad_source", "");
                String[] strArrSplit = ((String) zzfx.zzbh.zzb(null)).split(",");
                zzp();
                if (!zzpj.zzG(zzhsVar, strArrSplit).isEmpty()) {
                    long jLongValue = ((Long) zzpj.zzJ(zzhsVar, "click_timestamp", 0L)).longValue();
                    if (jLongValue <= 0) {
                        jLongValue = zzhsVar.zzf();
                    }
                    if ("referrer API v2".equals(zzpj.zzI(zzhsVar, "_cis"))) {
                        if (jLongValue > zzgxVarZzr.zzo()) {
                            if (str.isEmpty()) {
                                zzgxVarZzr.zzj();
                            } else {
                                zzgxVarZzr.zzi(str);
                            }
                            if (str2.isEmpty()) {
                                zzgxVarZzr.zzl();
                            } else {
                                zzgxVarZzr.zzk(str2);
                            }
                            if (str3.isEmpty()) {
                                zzgxVarZzr.zzn();
                            } else {
                                zzgxVarZzr.zzm(str3);
                            }
                            zzgxVarZzr.zzp(jLongValue);
                            zzgxVarZzr.zzs();
                            zzgxVarZzr.zzt(zzaC(zzhsVar));
                        }
                    } else if (jLongValue > zzgxVarZzr.zzg()) {
                        if (str.isEmpty()) {
                            zzgxVarZzr.zzb();
                        } else {
                            zzgxVarZzr.zza(str);
                        }
                        if (str2.isEmpty()) {
                            zzgxVarZzr.zzd();
                        } else {
                            zzgxVarZzr.zzc(str2);
                        }
                        if (str3.isEmpty()) {
                            zzgxVarZzr.zzf();
                        } else {
                            zzgxVarZzr.zze(str3);
                        }
                        zzgxVarZzr.zzh(jLongValue);
                        zzgxVarZzr.zzq();
                        zzgxVarZzr.zzr(zzaC(zzhsVar));
                    }
                }
            }
        }
        if (!((com.google.android.gms.internal.measurement.zzha) zzgxVarZzr.zzbc()).equals(com.google.android.gms.internal.measurement.zzha.zzs())) {
            zzicVar.zzaM((com.google.android.gms.internal.measurement.zzha) zzgxVarZzr.zzbc());
        }
        zzhVar.zzaI(((com.google.android.gms.internal.measurement.zzha) zzgxVarZzr.zzbc()).zzcc());
        if (zzhVar.zza()) {
            zzj().zzv(zzhVar, false, false);
        }
        if (zzd().zzp(null, zzfx.zzbg)) {
            zzj().zzk(zzhVar.zzc(), "_lgclid");
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final String zzK(zzjk zzjkVar) {
        if (!zzjkVar.zzo(zzjj.ANALYTICS_STORAGE)) {
            return null;
        }
        byte[] bArr = new byte[16];
        zzt().zzf().nextBytes(bArr);
        return String.format(Locale.US, "%032x", new BigInteger(1, bArr));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzL(List list) {
        Preconditions.checkArgument(!list.isEmpty());
        if (this.zzz != null) {
            zzaV().zzb().zza("Set uploading progress before finishing the previous upload");
        } else {
            this.zzz = new ArrayList(list);
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:61:0x017e */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:85:0x01ab */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:86:0x0136 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:89:0x00f6 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:90:? */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:94:0x015c */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0108 A[Catch: all -> 0x01b9, DONT_GENERATE, PHI: r5 r7
  0x0108: PHI (r5v10 long) = (r5v6 long), (r5v11 long), (r5v6 long) binds: [B:43:0x0125, B:37:0x0110, B:33:0x0106] A[DONT_GENERATE, DONT_INLINE]
  0x0108: PHI (r7v13 ??) = (r7v22 ??), (r7v23 ??), (r7v24 ??) binds: [B:43:0x0125, B:37:0x0110, B:33:0x0106] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #5 {all -> 0x01b9, blocks: (B:3:0x000e, B:5:0x001d, B:6:0x002c, B:8:0x0032, B:9:0x0041, B:11:0x0049, B:12:0x004e, B:14:0x0059, B:15:0x0068, B:17:0x0072, B:18:0x0084, B:20:0x00a3, B:22:0x00a9, B:23:0x00ac, B:25:0x00c5, B:26:0x00dc, B:28:0x00ec, B:30:0x00f2, B:34:0x0108, B:45:0x0128, B:47:0x012d, B:48:0x0130, B:49:0x0131, B:50:0x0136, B:56:0x0173, B:70:0x0198, B:72:0x019e, B:74:0x01a8, B:80:0x01b5, B:81:0x01b8, B:31:0x00f6, B:36:0x010c, B:42:0x0116), top: B:92:0x000e, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0173 A[Catch: all -> 0x01b9, PHI: r1 r3 r7
  0x0173: PHI (r1v20 ??) = (r1v41 ??), (r1v42 ??), (r1v43 ??) binds: [B:59:0x017b, B:55:0x0171, B:68:0x0195] A[DONT_GENERATE, DONT_INLINE]
  0x0173: PHI (r3v5 ??) = (r3v19 ??), (r3v20 ??), (r3v21 ??) binds: [B:59:0x017b, B:55:0x0171, B:68:0x0195] A[DONT_GENERATE, DONT_INLINE]
  0x0173: PHI (r7v2 ??) = (r7v33 ??), (r7v17 ?? I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), (r7v34 ??) binds: [B:59:0x017b, B:55:0x0171, B:68:0x0195] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #5 {all -> 0x01b9, blocks: (B:3:0x000e, B:5:0x001d, B:6:0x002c, B:8:0x0032, B:9:0x0041, B:11:0x0049, B:12:0x004e, B:14:0x0059, B:15:0x0068, B:17:0x0072, B:18:0x0084, B:20:0x00a3, B:22:0x00a9, B:23:0x00ac, B:25:0x00c5, B:26:0x00dc, B:28:0x00ec, B:30:0x00f2, B:34:0x0108, B:45:0x0128, B:47:0x012d, B:48:0x0130, B:49:0x0131, B:50:0x0136, B:56:0x0173, B:70:0x0198, B:72:0x019e, B:74:0x01a8, B:80:0x01b5, B:81:0x01b8, B:31:0x00f6, B:36:0x010c, B:42:0x0116), top: B:92:0x000e, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x019e A[Catch: all -> 0x01b9, TryCatch #5 {all -> 0x01b9, blocks: (B:3:0x000e, B:5:0x001d, B:6:0x002c, B:8:0x0032, B:9:0x0041, B:11:0x0049, B:12:0x004e, B:14:0x0059, B:15:0x0068, B:17:0x0072, B:18:0x0084, B:20:0x00a3, B:22:0x00a9, B:23:0x00ac, B:25:0x00c5, B:26:0x00dc, B:28:0x00ec, B:30:0x00f2, B:34:0x0108, B:45:0x0128, B:47:0x012d, B:48:0x0130, B:49:0x0131, B:50:0x0136, B:56:0x0173, B:70:0x0198, B:72:0x019e, B:74:0x01a8, B:80:0x01b5, B:81:0x01b8, B:31:0x00f6, B:36:0x010c, B:42:0x0116), top: B:92:0x000e, inners: #2 }] */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.google.android.gms.measurement.internal.zzpf] */
    /* JADX WARN: Type inference failed for: r1v12, types: [long] */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v19, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v20, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v24, types: [com.google.android.gms.measurement.internal.zzav] */
    /* JADX WARN: Type inference failed for: r1v26 */
    /* JADX WARN: Type inference failed for: r1v27 */
    /* JADX WARN: Type inference failed for: r1v28 */
    /* JADX WARN: Type inference failed for: r1v38 */
    /* JADX WARN: Type inference failed for: r1v39 */
    /* JADX WARN: Type inference failed for: r1v40 */
    /* JADX WARN: Type inference failed for: r1v41 */
    /* JADX WARN: Type inference failed for: r1v42 */
    /* JADX WARN: Type inference failed for: r1v43 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.google.android.gms.measurement.internal.zzav, com.google.android.gms.measurement.internal.zzjd, com.google.android.gms.measurement.internal.zzor] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.google.android.gms.measurement.internal.zzjd] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v10, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r7v14, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v18 */
    /* JADX WARN: Type inference failed for: r7v19 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v20 */
    /* JADX WARN: Type inference failed for: r7v21 */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v23 */
    /* JADX WARN: Type inference failed for: r7v24 */
    /* JADX WARN: Type inference failed for: r7v25 */
    /* JADX WARN: Type inference failed for: r7v26 */
    /* JADX WARN: Type inference failed for: r7v27 */
    /* JADX WARN: Type inference failed for: r7v28 */
    /* JADX WARN: Type inference failed for: r7v29 */
    /* JADX WARN: Type inference failed for: r7v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r7v30 */
    /* JADX WARN: Type inference failed for: r7v31 */
    /* JADX WARN: Type inference failed for: r7v32 */
    /* JADX WARN: Type inference failed for: r7v33 */
    /* JADX WARN: Type inference failed for: r7v34 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.lang.CharSequence, java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:85:0x01ab -> B:93:0x01ab). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void zzM() {
        long jZzF;
        Throwable th;
        SQLiteException e;
        ?? r3;
        ?? r1;
        boolean zIsEmpty;
        ?? r7;
        ?? r72;
        boolean zMoveToFirst;
        zzaW().zzg();
        zzu();
        this.zzw = true;
        try {
            zzib zzibVar = this.zzn;
            zzibVar.zzaU();
            Boolean boolZzJ = zzibVar.zzt().zzJ();
            if (boolZzJ == null) {
                zzaV().zze().zza("Upload data called on the client side before use of service was decided");
            } else if (boolZzJ.booleanValue()) {
                zzaV().zzb().zza("Upload called in the client side when service should be used");
            } else if (this.zza > 0) {
                zzaK();
            } else {
                zzaW().zzg();
                if (this.zzz != null) {
                    zzaV().zzk().zza("Uploading requested multiple times");
                } else if (zzi().zzb()) {
                    ?? CurrentTimeMillis = zzaZ().currentTimeMillis();
                    ?? r73 = 0;
                    RawQuery = 0;
                    RawQuery = 0;
                    RawQuery = 0;
                    RawQuery = 0;
                    ?? r74 = 0;
                    RawQuery = 0;
                    ?? RawQuery = 0;
                    int iZzm = zzd().zzm(null, zzfx.zzai);
                    zzd();
                    long jZzF2 = CurrentTimeMillis - zzal.zzF();
                    for (int i = 0; i < iZzm && zzaF(null, jZzF2); i++) {
                    }
                    zzql.zza();
                    zzaW().zzg();
                    zzau();
                    long jZza = this.zzk.zzd.zza();
                    if (jZza != 0) {
                        zzaV().zzj().zzb("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(CurrentTimeMillis - jZza)));
                    }
                    ?? ZzF = zzj().zzF();
                    long j = -1;
                    if (TextUtils.isEmpty(ZzF)) {
                        try {
                            this.zzB = -1L;
                            ZzF = zzj();
                            zzd();
                            jZzF = CurrentTimeMillis - zzal.zzF();
                            ZzF.zzg();
                            ZzF.zzay();
                        } catch (Throwable th2) {
                            th = th2;
                            r73 = CurrentTimeMillis;
                        }
                        try {
                            CurrentTimeMillis = ZzF.zze().rawQuery("select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;", new String[]{String.valueOf(jZzF)});
                            try {
                            } catch (SQLiteException e2) {
                                e = e2;
                                zzgr zzgrVarZzb = ZzF.zzu.zzaV().zzb();
                                zzgrVarZzb.zzb("Error selecting expired configs", e);
                                ZzF = zzgrVarZzb;
                                RawQuery = RawQuery;
                                r1 = CurrentTimeMillis;
                                r3 = zzgrVarZzb;
                                r74 = RawQuery;
                                if (CurrentTimeMillis != 0) {
                                }
                            }
                        } catch (SQLiteException e3) {
                            e = e3;
                            CurrentTimeMillis = 0;
                            zzgr zzgrVarZzb2 = ZzF.zzu.zzaV().zzb();
                            zzgrVarZzb2.zzb("Error selecting expired configs", e);
                            ZzF = zzgrVarZzb2;
                            RawQuery = RawQuery;
                            r1 = CurrentTimeMillis;
                            r3 = zzgrVarZzb2;
                            r74 = RawQuery;
                            if (CurrentTimeMillis != 0) {
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            if (r73 != 0) {
                                r73.close();
                            }
                            throw th;
                        }
                        if (CurrentTimeMillis.moveToFirst()) {
                            String string = CurrentTimeMillis.getString(0);
                            ZzF = ZzF;
                            RawQuery = string;
                            r1 = CurrentTimeMillis;
                            r3 = ZzF;
                            r74 = string;
                            if (CurrentTimeMillis != 0) {
                            }
                            zIsEmpty = TextUtils.isEmpty(RawQuery);
                            CurrentTimeMillis = zIsEmpty;
                            if (!zIsEmpty) {
                            }
                        } else {
                            ZzF.zzu.zzaV().zzk().zza("No expired configs for apps with pending events");
                            ZzF = ZzF;
                            r1 = CurrentTimeMillis;
                            r3 = ZzF;
                            if (CurrentTimeMillis != 0) {
                                r1.close();
                                ZzF = r3;
                                RawQuery = r74;
                            }
                            zIsEmpty = TextUtils.isEmpty(RawQuery);
                            CurrentTimeMillis = zIsEmpty;
                            if (!zIsEmpty) {
                                zzh zzhVarZzu = zzj().zzu(RawQuery);
                                CurrentTimeMillis = zzhVarZzu;
                                if (zzhVarZzu != null) {
                                    zzV(zzhVarZzu);
                                    CurrentTimeMillis = zzhVarZzu;
                                }
                            }
                        }
                    } else {
                        if (this.zzB == -1) {
                            zzav zzavVarZzj = zzj();
                            try {
                                try {
                                    RawQuery = zzavVarZzj.zze().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
                                    zMoveToFirst = RawQuery.moveToFirst();
                                    r72 = RawQuery;
                                    r7 = RawQuery;
                                } catch (SQLiteException e4) {
                                    zzavVarZzj.zzu.zzaV().zzb().zzb("Error querying raw events", e4);
                                    r72 = RawQuery;
                                    r7 = RawQuery;
                                    if (RawQuery != 0) {
                                    }
                                    this.zzB = j;
                                    RawQuery = r7;
                                    zzN(ZzF, CurrentTimeMillis);
                                }
                                if (zMoveToFirst) {
                                    j = RawQuery.getLong(0);
                                    r72 = RawQuery;
                                    r7 = RawQuery;
                                    if (RawQuery != 0) {
                                    }
                                    this.zzB = j;
                                    RawQuery = r7;
                                } else {
                                    this.zzB = j;
                                    RawQuery = r7;
                                }
                            } finally {
                                if (RawQuery != 0) {
                                    RawQuery.close();
                                }
                            }
                        }
                        zzN(ZzF, CurrentTimeMillis);
                    }
                } else {
                    zzaV().zzk().zza("Network not connected, ignoring upload request");
                    zzaK();
                }
            }
        } finally {
            this.zzw = false;
            zzaL();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:101:0x0205 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:220:0x054f */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:89:0x01ee */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b2, code lost:
    
        r22 = r10;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0226 A[PHI: r0 r10 r23
  0x0226: PHI (r0v118 java.util.List) = (r0v8 java.util.List), (r0v142 java.util.List) binds: [B:109:0x0224, B:18:0x006d] A[DONT_GENERATE, DONT_INLINE]
  0x0226: PHI (r10v57 android.database.Cursor) = (r10v5 android.database.Cursor), (r10v59 android.database.Cursor) binds: [B:109:0x0224, B:18:0x006d] A[DONT_GENERATE, DONT_INLINE]
  0x0226: PHI (r23v27 long) = (r23v34 long), (r23v33 long) binds: [B:109:0x0224, B:18:0x006d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x044e A[PHI: r10 r16 r23
  0x044e: PHI (r10v39 java.util.List) = (r10v53 java.util.List), (r10v38 java.util.List) binds: [B:183:0x0479, B:171:0x044c] A[DONT_GENERATE, DONT_INLINE]
  0x044e: PHI (r16v7 java.util.List) = (r16v12 java.util.List), (r16v13 java.util.List) binds: [B:183:0x0479, B:171:0x044c] A[DONT_GENERATE, DONT_INLINE]
  0x044e: PHI (r23v11 android.database.Cursor) = (r23v17 android.database.Cursor), (r23v21 android.database.Cursor) binds: [B:183:0x0479, B:171:0x044c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:223:0x0554  */
    /* JADX WARN: Removed duplicated region for block: B:377:0x09af  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x09fd  */
    /* JADX WARN: Removed duplicated region for block: B:476:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:477:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01f0  */
    /* JADX WARN: Type inference failed for: r13v17, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r13v18 */
    /* JADX WARN: Type inference failed for: r13v19 */
    /* JADX WARN: Type inference failed for: r23v19 */
    /* JADX WARN: Type inference failed for: r23v26 */
    /* JADX WARN: Type inference failed for: r23v3, types: [long] */
    /* JADX WARN: Type inference failed for: r23v30 */
    /* JADX WARN: Type inference failed for: r23v31 */
    /* JADX WARN: Type inference failed for: r23v32 */
    /* JADX WARN: Type inference failed for: r23v35 */
    /* JADX WARN: Type inference failed for: r23v36 */
    /* JADX WARN: Type inference failed for: r23v9 */
    /* JADX WARN: Type inference failed for: r32v0, types: [com.google.android.gms.measurement.internal.zzpf] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void zzN(String str, long j) throws Throwable {
        long j2;
        Cursor cursor;
        Cursor cursorQuery;
        long j3;
        List list;
        ?? r23;
        List<Pair> listSubList;
        boolean z;
        String strZzi;
        String string;
        zzos zzosVar;
        boolean z2;
        List list2;
        boolean z3;
        int i;
        String strZzG;
        ?? r13;
        List list3;
        Cursor cursor2;
        List list4;
        int i2;
        int i3;
        SQLiteDatabase sQLiteDatabaseZze;
        long jCurrentTimeMillis;
        Cursor cursorQuery2;
        long j4;
        Cursor cursor3;
        byte[] byteArray;
        com.google.android.gms.internal.measurement.zzid zzidVar;
        long jZzg;
        long j5;
        long jZzg2;
        int iZzm = zzd().zzm(str, zzfx.zzg);
        int i4 = 0;
        int iMax = Math.max(0, zzd().zzm(str, zzfx.zzh));
        zzav zzavVarZzj = zzj();
        zzavVarZzj.zzg();
        zzavVarZzj.zzay();
        int i5 = 1;
        Preconditions.checkArgument(iZzm > 0);
        Preconditions.checkArgument(iMax > 0);
        Preconditions.checkNotEmpty(str);
        try {
            try {
                j2 = -1;
                j4 = -1;
                r23 = -1;
                r23 = -1;
                r23 = -1;
                j3 = -1;
                try {
                    cursorQuery = zzavVarZzj.zze().query("queue", new String[]{"rowid", "data", "retry_count"}, "app_id=?", new String[]{str}, null, null, "rowid", String.valueOf(iZzm));
                    try {
                    } catch (SQLiteException e) {
                        e = e;
                        cursor3 = cursorQuery;
                    } catch (Throwable th) {
                        th = th;
                        cursor3 = cursorQuery;
                    }
                } catch (SQLiteException e2) {
                    e = e2;
                    cursorQuery = null;
                    j3 = j2;
                    try {
                        zzavVarZzj.zzu.zzaV().zzb().zzc("Error querying bundles. appId", zzgt.zzl(str), e);
                        list = Collections.EMPTY_LIST;
                        j4 = j3;
                        r23 = j3;
                        if (cursorQuery != null) {
                            cursorQuery.close();
                            r23 = j4;
                        }
                        listSubList = list;
                        if (listSubList.isEmpty()) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = cursorQuery;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                cursor = null;
            }
        } catch (SQLiteException e3) {
            e = e3;
            j2 = -1;
        }
        if (cursorQuery.moveToFirst()) {
            listSubList = new ArrayList();
            int length = 0;
            while (true) {
                long j6 = cursorQuery.getLong(i4);
                try {
                    byte[] blob = cursorQuery.getBlob(i5);
                    zzpj zzpjVarZzp = zzavVarZzj.zzg.zzp();
                    try {
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(blob);
                        GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i6 = gZIPInputStream.read(bArr);
                            if (i6 <= 0) {
                                break;
                            }
                            cursor3 = cursorQuery;
                            try {
                                try {
                                    byteArrayOutputStream.write(bArr, 0, i6);
                                    cursorQuery = cursor3;
                                } catch (IOException e4) {
                                    e = e4;
                                    try {
                                        zzpjVarZzp.zzu.zzaV().zzb().zzb("Failed to ungzip content", e);
                                        throw e;
                                    } catch (IOException e5) {
                                        e = e5;
                                        zzavVarZzj.zzu.zzaV().zzb().zzc("Failed to unzip queued bundle. appId", zzgt.zzl(str), e);
                                        if (cursor3.moveToNext()) {
                                            break;
                                        } else {
                                            break;
                                        }
                                        if (cursor3 != null) {
                                        }
                                        if (listSubList.isEmpty()) {
                                        }
                                    }
                                }
                            } catch (SQLiteException e6) {
                                e = e6;
                                cursorQuery = cursor3;
                                zzavVarZzj.zzu.zzaV().zzb().zzc("Error querying bundles. appId", zzgt.zzl(str), e);
                                list = Collections.EMPTY_LIST;
                                j4 = j3;
                                r23 = j3;
                                if (cursorQuery != null) {
                                }
                                listSubList = list;
                                if (listSubList.isEmpty()) {
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                cursor = cursor3;
                                if (cursor != null) {
                                }
                                throw th;
                            }
                        }
                        gZIPInputStream.close();
                        byteArrayInputStream.close();
                        byteArray = byteArrayOutputStream.toByteArray();
                    } catch (IOException e7) {
                        e = e7;
                        cursor3 = cursorQuery;
                    }
                } catch (IOException e8) {
                    e = e8;
                    cursor3 = cursorQuery;
                }
                if (!listSubList.isEmpty() && byteArray.length + length > iMax) {
                    break;
                }
                try {
                    com.google.android.gms.internal.measurement.zzic zzicVar = (com.google.android.gms.internal.measurement.zzic) zzpj.zzw(com.google.android.gms.internal.measurement.zzid.zzaE(), byteArray);
                    if (!listSubList.isEmpty()) {
                        com.google.android.gms.internal.measurement.zzid zzidVar2 = (com.google.android.gms.internal.measurement.zzid) ((Pair) listSubList.get(0)).first;
                        com.google.android.gms.internal.measurement.zzid zzidVar3 = (com.google.android.gms.internal.measurement.zzid) zzicVar.zzbc();
                        if (!zzidVar2.zzaf().equals(zzidVar3.zzaf()) || !zzidVar2.zzam().equals(zzidVar3.zzam()) || zzidVar2.zzao() != zzidVar3.zzao() || !zzidVar2.zzaq().equals(zzidVar3.zzaq())) {
                            break;
                        }
                        Iterator it = zzidVar2.zzf().iterator();
                        while (true) {
                            Iterator it2 = it;
                            if (!it.hasNext()) {
                                zzidVar = zzidVar3;
                                jZzg = -1;
                                break;
                            }
                            com.google.android.gms.internal.measurement.zziu zziuVar = (com.google.android.gms.internal.measurement.zziu) it2.next();
                            zzidVar = zzidVar3;
                            if ("_npa".equals(zziuVar.zzc())) {
                                jZzg = zziuVar.zzg();
                                break;
                            } else {
                                it = it2;
                                zzidVar3 = zzidVar;
                            }
                        }
                        Iterator it3 = zzidVar.zzf().iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                j5 = jZzg;
                                jZzg2 = -1;
                                break;
                            }
                            com.google.android.gms.internal.measurement.zziu zziuVar2 = (com.google.android.gms.internal.measurement.zziu) it3.next();
                            j5 = jZzg;
                            if ("_npa".equals(zziuVar2.zzc())) {
                                jZzg2 = zziuVar2.zzg();
                                break;
                            }
                            jZzg = j5;
                        }
                        if (j5 != jZzg2) {
                            break;
                        }
                    }
                    if (!cursorQuery.isNull(2)) {
                        zzicVar.zzao(cursorQuery.getInt(2));
                    }
                    length += byteArray.length;
                    listSubList.add(Pair.create((com.google.android.gms.internal.measurement.zzid) zzicVar.zzbc(), Long.valueOf(j6)));
                } catch (IOException e9) {
                    zzavVarZzj.zzu.zzaV().zzb().zzc("Failed to merge queued bundle. appId", zzgt.zzl(str), e9);
                }
                cursor3 = cursorQuery;
                if (cursor3.moveToNext() || length > iMax) {
                    break;
                    break;
                } else {
                    cursorQuery = cursor3;
                    i4 = 0;
                    i5 = 1;
                }
            }
            if (cursor3 != null) {
                cursor3.close();
            }
            if (listSubList.isEmpty()) {
            }
        } else {
            list = Collections.EMPTY_LIST;
            if (cursorQuery != null) {
                cursorQuery.close();
                r23 = j4;
            }
            listSubList = list;
            if (listSubList.isEmpty()) {
                return;
            }
            com.google.android.gms.internal.measurement.zzpk.zza();
            zzal zzalVarZzd = zzd();
            zzfw zzfwVar = zzfx.zzbi;
            if (zzalVarZzd.zzp(null, zzfwVar)) {
                com.google.android.gms.internal.measurement.zzpk.zza();
                if (zzd().zzp(null, zzfwVar)) {
                    if (zzB(str).zzo(zzjj.ANALYTICS_STORAGE) || !zzh().zzB(str)) {
                        ArrayList arrayList = new ArrayList(listSubList.size());
                        zzav zzavVarZzj2 = zzj();
                        Preconditions.checkNotEmpty(str);
                        zzavVarZzj2.zzg();
                        zzavVarZzj2.zzay();
                        List arrayList2 = new ArrayList();
                        try {
                        } catch (Throwable th5) {
                            th = th5;
                        }
                        try {
                            try {
                                sQLiteDatabaseZze = zzavVarZzj2.zze();
                                jCurrentTimeMillis = zzavVarZzj2.zzu.zzaZ().currentTimeMillis();
                                cursorQuery2 = sQLiteDatabaseZze.query("no_data_mode_events", new String[]{"data"}, "app_id=? AND timestamp_millis <= CAST(? AS INTEGER)", new String[]{str, String.valueOf(jCurrentTimeMillis)}, null, null, "rowid", null);
                                list3 = listSubList;
                            } catch (SQLiteException e10) {
                                e = e10;
                                list3 = listSubList;
                            }
                        } catch (Throwable th6) {
                            th = th6;
                            r13 = 0;
                            if (r13 != 0) {
                                r13.close();
                            }
                            throw th;
                        }
                        try {
                        } catch (SQLiteException e11) {
                            e = e11;
                            cursor2 = cursorQuery2;
                        } catch (Throwable th7) {
                            th = th7;
                            r23 = cursorQuery2;
                            r13 = r23;
                            if (r13 != 0) {
                            }
                            throw th;
                        }
                        if (cursorQuery2.moveToFirst()) {
                            while (true) {
                                try {
                                    arrayList2.add((com.google.android.gms.internal.measurement.zzhs) ((com.google.android.gms.internal.measurement.zzhr) zzpj.zzw(com.google.android.gms.internal.measurement.zzhs.zzk(), cursorQuery2.getBlob(0))).zzbc());
                                    cursor2 = cursorQuery2;
                                } catch (com.google.android.gms.internal.measurement.zzmq e12) {
                                    cursor2 = cursorQuery2;
                                    try {
                                        zzavVarZzj2.zzu.zzaV().zzh().zzc("Failed to parse stored NO_DATA mode event, appId", zzgt.zzl(str), e12);
                                    } catch (SQLiteException e13) {
                                        e = e13;
                                        zzavVarZzj2.zzu.zzaV().zzb().zzc("Error flushing NO_DATA mode events. appId", zzgt.zzl(str), e);
                                        arrayList2 = Collections.EMPTY_LIST;
                                        if (cursor2 != null) {
                                        }
                                    }
                                }
                                if (!cursor2.moveToNext()) {
                                    break;
                                } else {
                                    cursorQuery2 = cursor2;
                                }
                            }
                            cursor2.close();
                            try {
                                int iDelete = sQLiteDatabaseZze.delete("no_data_mode_events", "app_id=? AND timestamp_millis <= CAST(? AS INTEGER)", new String[]{str, String.valueOf(jCurrentTimeMillis)});
                                zzgr zzgrVarZzk = zzavVarZzj2.zzu.zzaV().zzk();
                                StringBuilder sb = new StringBuilder(String.valueOf(iDelete).length() + 34);
                                sb.append("Pruned ");
                                sb.append(iDelete);
                                sb.append(" NO_DATA mode events. appId");
                                zzgrVarZzk.zzb(sb.toString(), str);
                            } catch (SQLiteException e14) {
                                e = e14;
                                cursor2 = null;
                                zzavVarZzj2.zzu.zzaV().zzb().zzc("Error flushing NO_DATA mode events. appId", zzgt.zzl(str), e);
                                arrayList2 = Collections.EMPTY_LIST;
                                if (cursor2 != null) {
                                }
                            }
                        } else {
                            cursor2 = cursorQuery2;
                            if (cursor2 != null) {
                                cursor2.close();
                            }
                        }
                        Iterator it4 = list3.iterator();
                        boolean z4 = true;
                        while (it4.hasNext()) {
                            Pair pair = (Pair) it4.next();
                            com.google.android.gms.internal.measurement.zzic zzicVar2 = (com.google.android.gms.internal.measurement.zzic) ((com.google.android.gms.internal.measurement.zzid) pair.first).zzcl();
                            if (z4 && !arrayList2.isEmpty()) {
                                List listZzb = zzicVar2.zzb();
                                zzicVar2.zzi();
                                zzicVar2.zzh(arrayList2);
                                zzicVar2.zzh(listZzb);
                                z4 = false;
                            }
                            com.google.android.gms.internal.measurement.zzhh zzhhVarZzb = com.google.android.gms.internal.measurement.zzho.zzb();
                            com.google.android.gms.internal.measurement.zzgf zzgfVarZzx = zzh().zzx(str);
                            ArrayList arrayList3 = new ArrayList();
                            if (zzgfVarZzx != null) {
                                for (com.google.android.gms.internal.measurement.zzfu zzfuVar : zzgfVarZzx.zza()) {
                                    com.google.android.gms.internal.measurement.zzhk zzhkVarZza = com.google.android.gms.internal.measurement.zzhl.zza();
                                    int iZzb = zzfuVar.zzb();
                                    zzjh zzjhVar = zzjh.UNINITIALIZED;
                                    Iterator it5 = it4;
                                    int i7 = iZzb - 1;
                                    boolean z5 = z4;
                                    if (i7 == 1) {
                                        list4 = arrayList2;
                                        i2 = 3;
                                        i3 = 2;
                                    } else if (i7 != 2) {
                                        list4 = arrayList2;
                                        i2 = 3;
                                        i3 = i7 != 3 ? i7 != 4 ? 1 : 5 : 4;
                                    } else {
                                        list4 = arrayList2;
                                        i2 = 3;
                                        i3 = 3;
                                    }
                                    zzhkVarZza.zza(i3);
                                    int iZzd = zzfuVar.zzd() - 1;
                                    if (iZzd == 1) {
                                        i2 = 2;
                                    } else if (iZzd != 2) {
                                        i2 = 1;
                                    }
                                    zzhkVarZza.zzb(i2);
                                    arrayList3.add((com.google.android.gms.internal.measurement.zzhl) zzhkVarZza.zzbc());
                                    z4 = z5;
                                    it4 = it5;
                                    arrayList2 = list4;
                                }
                            }
                            Iterator it6 = it4;
                            boolean z6 = z4;
                            List list5 = arrayList2;
                            zzhhVarZzb.zza(arrayList3);
                            zzicVar2.zzaQ(zzhhVarZzb);
                            arrayList.add(Pair.create((com.google.android.gms.internal.measurement.zzid) zzicVar2.zzbc(), (Long) pair.second));
                            z4 = z6;
                            it4 = it6;
                            arrayList2 = list5;
                        }
                        listSubList = arrayList;
                    } else {
                        List listAsList = Arrays.asList(((String) zzfx.zzbj.zzb(null)).split(","));
                        for (Pair pair2 : listSubList) {
                            try {
                                zzj().zzH(((Long) pair2.second).longValue());
                                for (com.google.android.gms.internal.measurement.zzhs zzhsVar : ((com.google.android.gms.internal.measurement.zzid) pair2.first).zzc()) {
                                    if (listAsList.contains(zzhsVar.zzd())) {
                                        if (zzhsVar.zzd().equals("_f") || zzhsVar.zzd().equals("_v")) {
                                            com.google.android.gms.internal.measurement.zzhr zzhrVar = (com.google.android.gms.internal.measurement.zzhr) zzhsVar.zzcl();
                                            zzp();
                                            zzpj.zzC(zzhrVar, "_dac", 1L);
                                            zzhsVar = (com.google.android.gms.internal.measurement.zzhs) zzhrVar.zzbc();
                                        }
                                        zzav zzavVarZzj3 = zzj();
                                        zzavVarZzj3.zzg();
                                        zzavVarZzj3.zzay();
                                        Preconditions.checkNotNull(zzhsVar);
                                        Preconditions.checkNotEmpty(str);
                                        zzib zzibVar = zzavVarZzj3.zzu;
                                        zzibVar.zzaV().zzk().zzb("Caching events in NO_DATA mode", zzhsVar);
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put(ClientContext.APP_ID_KEY, str);
                                        contentValues.put("name", zzhsVar.zzd());
                                        contentValues.put("data", zzhsVar.zzcc());
                                        contentValues.put("timestamp_millis", Long.valueOf(zzhsVar.zzf()));
                                        try {
                                            if (zzavVarZzj3.zze().insert("no_data_mode_events", null, contentValues) == r23) {
                                                zzibVar.zzaV().zzb().zzb("Failed to insert NO_DATA mode event (got -1). appId", zzgt.zzl(str));
                                            }
                                        } catch (SQLiteException e15) {
                                            zzavVarZzj3.zzu.zzaV().zzb().zzc("Error storing NO_DATA mode event. appId", zzgt.zzl(str), e15);
                                        }
                                    }
                                }
                            } catch (SQLiteException unused) {
                                zzaV().zzh().zzb("Failed handling NO_DATA mode bundles. appId", str);
                            }
                        }
                        listSubList = Collections.EMPTY_LIST;
                    }
                }
                if (listSubList.isEmpty()) {
                    return;
                }
            }
            zzjk zzjkVarZzB = zzB(str);
            zzjj zzjjVar = zzjj.AD_STORAGE;
            if (zzjkVarZzB.zzo(zzjjVar)) {
                Iterator it7 = listSubList.iterator();
                while (true) {
                    if (!it7.hasNext()) {
                        strZzG = null;
                        break;
                    }
                    com.google.android.gms.internal.measurement.zzid zzidVar4 = (com.google.android.gms.internal.measurement.zzid) ((Pair) it7.next()).first;
                    if (!zzidVar4.zzG().isEmpty()) {
                        strZzG = zzidVar4.zzG();
                        break;
                    }
                }
                if (strZzG != null) {
                    int i8 = 0;
                    while (true) {
                        if (i8 >= listSubList.size()) {
                            break;
                        }
                        com.google.android.gms.internal.measurement.zzid zzidVar5 = (com.google.android.gms.internal.measurement.zzid) ((Pair) listSubList.get(i8)).first;
                        if (!zzidVar5.zzG().isEmpty() && !zzidVar5.zzG().equals(strZzG)) {
                            listSubList = listSubList.subList(0, i8);
                            break;
                        }
                        i8++;
                    }
                }
            }
            com.google.android.gms.internal.measurement.zzhz zzhzVarZzh = com.google.android.gms.internal.measurement.zzib.zzh();
            int size = listSubList.size();
            ArrayList arrayList4 = new ArrayList(listSubList.size());
            boolean z7 = zzd().zzC(str) && zzB(str).zzo(zzjjVar);
            boolean zZzo = zzB(str).zzo(zzjjVar);
            boolean zZzo2 = zzB(str).zzo(zzjj.ANALYTICS_STORAGE);
            zzqu.zza();
            boolean zZzp = zzd().zzp(str, zzfx.zzaM);
            zzot zzotVar = this.zzl;
            zzos zzosVarZza = zzotVar.zza(str);
            int i9 = 0;
            while (i9 < size) {
                boolean z8 = zZzo;
                com.google.android.gms.internal.measurement.zzic zzicVar3 = (com.google.android.gms.internal.measurement.zzic) ((com.google.android.gms.internal.measurement.zzid) ((Pair) listSubList.get(i9)).first).zzcl();
                int i10 = size;
                arrayList4.add((Long) ((Pair) listSubList.get(i9)).second);
                zzd().zzi();
                boolean z9 = z7;
                boolean z10 = zZzo2;
                zzicVar3.zzO(130000L);
                zzicVar3.zzs(j);
                this.zzn.zzaU();
                zzicVar3.zzae(false);
                if (!z9) {
                    zzicVar3.zzan();
                }
                if (!z8) {
                    zzicVar3.zzR();
                    zzicVar3.zzU();
                }
                if (!z10) {
                    zzicVar3.zzX();
                }
                zzS(str, zzicVar3);
                if (!zZzp) {
                    zzicVar3.zzav();
                }
                if (!z10) {
                    zzicVar3.zzag();
                }
                String strZzP = zzicVar3.zzP();
                if (TextUtils.isEmpty(strZzP) || strZzP.equals("00000000-0000-0000-0000-000000000000")) {
                    ArrayList arrayList5 = new ArrayList(zzicVar3.zzb());
                    Iterator it8 = arrayList5.iterator();
                    z2 = z9;
                    Long lValueOf = null;
                    Long lValueOf2 = null;
                    boolean z11 = false;
                    boolean z12 = false;
                    while (it8.hasNext()) {
                        List list6 = listSubList;
                        com.google.android.gms.internal.measurement.zzhs zzhsVar2 = (com.google.android.gms.internal.measurement.zzhs) it8.next();
                        boolean z13 = zZzp;
                        int i11 = i9;
                        if ("_fx".equals(zzhsVar2.zzd())) {
                            it8.remove();
                            zZzp = z13;
                            listSubList = list6;
                            i9 = i11;
                            z11 = true;
                        } else if ("_f".equals(zzhsVar2.zzd())) {
                            zzp();
                            com.google.android.gms.internal.measurement.zzhw zzhwVarZzF = zzpj.zzF(zzhsVar2, "_pfo");
                            if (zzhwVarZzF != null) {
                                lValueOf = Long.valueOf(zzhwVarZzF.zzf());
                            }
                            zzp();
                            com.google.android.gms.internal.measurement.zzhw zzhwVarZzF2 = zzpj.zzF(zzhsVar2, "_uwa");
                            if (zzhwVarZzF2 != null) {
                                lValueOf2 = Long.valueOf(zzhwVarZzF2.zzf());
                            }
                            zZzp = z13;
                            listSubList = list6;
                            i9 = i11;
                        } else {
                            zZzp = z13;
                            listSubList = list6;
                            i9 = i11;
                        }
                        z12 = true;
                    }
                    list2 = listSubList;
                    z3 = zZzp;
                    i = i9;
                    if (z11) {
                        zzicVar3.zzi();
                        zzicVar3.zzh(arrayList5);
                    }
                    if (z12) {
                        zzR(zzicVar3.zzK(), true, lValueOf, lValueOf2);
                    }
                } else {
                    z2 = z9;
                    list2 = listSubList;
                    z3 = zZzp;
                    i = i9;
                }
                if (zzicVar3.zzc() != 0) {
                    if (zzd().zzp(str, zzfx.zzaC)) {
                        zzicVar3.zzas(zzp().zzt(((com.google.android.gms.internal.measurement.zzid) zzicVar3.zzbc()).zzcc()));
                    }
                    com.google.android.gms.internal.measurement.zzis zzisVarZzd = zzosVarZza.zzd();
                    if (zzisVarZzd != null) {
                        zzicVar3.zzaN(zzisVarZzd);
                    }
                    zzhzVarZzh.zze(zzicVar3);
                }
                i9 = i + 1;
                zZzo2 = z10;
                zZzo = z8;
                size = i10;
                z7 = z2;
                zZzp = z3;
                listSubList = list2;
            }
            if (zzhzVarZzh.zzb() == 0) {
                zzL(arrayList4);
                zzU(false, 204, null, null, str, Collections.EMPTY_LIST);
                return;
            }
            com.google.android.gms.internal.measurement.zzib zzibVar2 = (com.google.android.gms.internal.measurement.zzib) zzhzVarZzh.zzbc();
            ArrayList arrayList6 = new ArrayList();
            boolean z14 = zzosVarZza.zzc() == zzlr.SGTM_CLIENT;
            if (zzosVarZza.zzc() == zzlr.SGTM) {
                z = z14;
            } else {
                if (!z14) {
                    strZzi = null;
                    if (zzi().zzb()) {
                        return;
                    }
                    if (Log.isLoggable(zzaV().zzn(), 2)) {
                        strZzi = zzp().zzi(zzibVar2);
                    }
                    zzp();
                    byte[] bArrZzcc = zzibVar2.zzcc();
                    zzL(arrayList4);
                    this.zzk.zze.zzb(j);
                    zzaV().zzk().zzd("Uploading data. app, uncompressed size, data", str, Integer.valueOf(bArrZzcc.length), strZzi);
                    this.zzv = true;
                    zzi().zzc(str, zzosVarZza, zzibVar2, new zzov(this, str, arrayList6));
                    return;
                }
                z = true;
            }
            Iterator it9 = ((com.google.android.gms.internal.measurement.zzib) zzhzVarZzh.zzbc()).zza().iterator();
            while (true) {
                if (it9.hasNext()) {
                    if (((com.google.android.gms.internal.measurement.zzid) it9.next()).zzY()) {
                        string = UUID.randomUUID().toString();
                        break;
                    }
                } else {
                    string = null;
                    break;
                }
            }
            com.google.android.gms.internal.measurement.zzib zzibVar3 = (com.google.android.gms.internal.measurement.zzib) zzhzVarZzh.zzbc();
            zzaW().zzg();
            zzu();
            com.google.android.gms.internal.measurement.zzhz zzhzVarZzi = com.google.android.gms.internal.measurement.zzib.zzi(zzibVar3);
            if (!TextUtils.isEmpty(string)) {
                zzhzVarZzi.zzi(string);
            }
            String strZzc = zzh().zzc(str);
            if (!TextUtils.isEmpty(strZzc)) {
                zzhzVarZzi.zzj(strZzc);
            }
            ArrayList arrayList7 = new ArrayList();
            Iterator it10 = zzibVar3.zza().iterator();
            while (it10.hasNext()) {
                com.google.android.gms.internal.measurement.zzic zzicVarZzaF = com.google.android.gms.internal.measurement.zzid.zzaF((com.google.android.gms.internal.measurement.zzid) it10.next());
                zzicVarZzaF.zzan();
                arrayList7.add((com.google.android.gms.internal.measurement.zzid) zzicVarZzaF.zzbc());
            }
            zzhzVarZzi.zzg();
            zzhzVarZzi.zzf(arrayList7);
            zzaV().zzk().zzb("[sgtm] Processed MeasurementBatch for sGTM with sgtmJoinId: ", TextUtils.isEmpty(string) ? "null" : zzhzVarZzi.zzh());
            com.google.android.gms.internal.measurement.zzib zzibVar4 = (com.google.android.gms.internal.measurement.zzib) zzhzVarZzi.zzbc();
            if (TextUtils.isEmpty(string)) {
                strZzi = null;
            } else {
                com.google.android.gms.internal.measurement.zzib zzibVar5 = (com.google.android.gms.internal.measurement.zzib) zzhzVarZzh.zzbc();
                zzaW().zzg();
                zzu();
                com.google.android.gms.internal.measurement.zzhz zzhzVarZzh2 = com.google.android.gms.internal.measurement.zzib.zzh();
                zzaV().zzk().zzb("[sgtm] Processing Google Signal, sgtmJoinId:", string);
                zzhzVarZzh2.zzi(string);
                for (com.google.android.gms.internal.measurement.zzid zzidVar6 : zzibVar5.zza()) {
                    com.google.android.gms.internal.measurement.zzic zzicVarZzaE = com.google.android.gms.internal.measurement.zzid.zzaE();
                    zzicVarZzaE.zzam(zzidVar6.zzZ());
                    zzicVarZzaE.zzaJ(zzidVar6.zzav());
                    zzhzVarZzh2.zze(zzicVarZzaE);
                }
                com.google.android.gms.internal.measurement.zzib zzibVar6 = (com.google.android.gms.internal.measurement.zzib) zzhzVarZzh2.zzbc();
                String strZzc2 = zzotVar.zzg.zzh().zzc(str);
                if (TextUtils.isEmpty(strZzc2)) {
                    strZzi = null;
                    zzosVar = new zzos((String) zzfx.zzr.zzb(null), Collections.EMPTY_MAP, z ? zzlr.GOOGLE_SIGNAL_PENDING : zzlr.GOOGLE_SIGNAL, null);
                } else {
                    Uri uri = Uri.parse((String) zzfx.zzr.zzb(null));
                    Uri.Builder builderBuildUpon = uri.buildUpon();
                    String authority = uri.getAuthority();
                    StringBuilder sb2 = new StringBuilder(String.valueOf(strZzc2).length() + 1 + String.valueOf(authority).length());
                    sb2.append(strZzc2);
                    sb2.append(".");
                    sb2.append(authority);
                    builderBuildUpon.authority(sb2.toString());
                    strZzi = null;
                    zzosVar = new zzos(builderBuildUpon.build().toString(), Collections.EMPTY_MAP, z ? zzlr.GOOGLE_SIGNAL_PENDING : zzlr.GOOGLE_SIGNAL, null);
                }
                arrayList6.add(Pair.create(zzibVar6, zzosVar));
            }
            if (z) {
                com.google.android.gms.internal.measurement.zzhz zzhzVar = (com.google.android.gms.internal.measurement.zzhz) zzibVar4.zzcl();
                for (int i12 = 0; i12 < zzibVar4.zzb(); i12++) {
                    com.google.android.gms.internal.measurement.zzic zzicVar4 = (com.google.android.gms.internal.measurement.zzic) zzibVar4.zzc(i12).zzcl();
                    zzicVar4.zzt();
                    zzicVar4.zzaO(j);
                    zzhzVar.zzd(i12, zzicVar4);
                }
                arrayList6.add(Pair.create((com.google.android.gms.internal.measurement.zzib) zzhzVar.zzbc(), zzosVarZza));
                zzL(arrayList4);
                zzU(false, 204, null, null, str, arrayList6);
                if (zzO(str, zzosVarZza.zza())) {
                    zzaV().zzk().zzb("[sgtm] Sending sgtm batches available notification to app", str);
                    Intent intent = new Intent();
                    intent.setAction("com.google.android.gms.measurement.BATCHES_AVAILABLE");
                    intent.setPackage(str);
                    zzaP(this.zzn.zzaY(), intent);
                    return;
                }
                return;
            }
            zzibVar2 = zzibVar4;
            if (zzi().zzb()) {
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final boolean zzO(String str, String str2) {
        zzh zzhVarZzu = zzj().zzu(str);
        if (zzhVarZzu != null && zzt().zzaa(str, zzhVarZzu.zzay())) {
            this.zzF.remove(str2);
            return true;
        }
        zzpd zzpdVar = (zzpd) this.zzF.get(str2);
        if (zzpdVar == null) {
            return true;
        }
        return zzpdVar.zzb();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzP(String str) {
        com.google.android.gms.internal.measurement.zzib zzibVarZzd;
        zzaW().zzg();
        zzu();
        this.zzw = true;
        try {
            zzib zzibVar = this.zzn;
            zzibVar.zzaU();
            Boolean boolZzJ = zzibVar.zzt().zzJ();
            if (boolZzJ == null) {
                zzaV().zze().zza("Upload data called on the client side before use of service was decided");
            } else if (boolZzJ.booleanValue()) {
                zzaV().zzb().zza("Upload called in the client side when service should be used");
            } else if (this.zza > 0) {
                zzaK();
            } else if (!zzi().zzb()) {
                zzaV().zzk().zza("Network not connected, ignoring upload request");
                zzaK();
            } else if (zzj().zzD(str)) {
                zzav zzavVarZzj = zzj();
                Preconditions.checkNotEmpty(str);
                zzavVarZzj.zzg();
                zzavVarZzj.zzay();
                List listZzC = zzavVarZzj.zzC(str, zzon.zza(zzlr.GOOGLE_SIGNAL), 1);
                zzpi zzpiVar = listZzC.isEmpty() ? null : (zzpi) listZzC.get(0);
                if (zzpiVar != null && (zzibVarZzd = zzpiVar.zzd()) != null) {
                    zzaV().zzk().zzd("[sgtm] Uploading data from upload queue. appId, type, url", str, zzpiVar.zzf(), zzpiVar.zze());
                    byte[] bArrZzcc = zzibVarZzd.zzcc();
                    if (Log.isLoggable(zzaV().zzn(), 2)) {
                        zzaV().zzk().zzd("[sgtm] Uploading data from upload queue. appId, uncompressed size, data", str, Integer.valueOf(bArrZzcc.length), zzp().zzi(zzibVarZzd));
                    }
                    zzos zzosVarZza = zzpiVar.zza();
                    this.zzv = true;
                    zzi().zzc(str, zzosVarZza, zzibVarZzd, new zzow(this, str, zzpiVar));
                }
            } else {
                zzaV().zzk().zzb("[sgtm] Upload queue has no batches for appId", str);
            }
        } finally {
            this.zzw = false;
            zzaL();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:13:0x001e A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:4:0x000d, B:19:0x005a, B:22:0x0080, B:13:0x001e, B:15:0x0048, B:17:0x0052, B:18:0x0056), top: B:27:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void zzQ(String str, int i, Throwable th, byte[] bArr, zzpi zzpiVar) {
        zzaW().zzg();
        zzu();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } catch (Throwable th2) {
                this.zzv = false;
                zzaL();
                throw th2;
            }
        }
        if (i != 200) {
            if (i == 204) {
                i = 204;
                if (th != null) {
                }
            }
            String str2 = new String(bArr, StandardCharsets.UTF_8);
            String strSubstring = str2.substring(0, Math.min(32, str2.length()));
            zzgr zzgrVarZzh = zzaV().zzh();
            Integer numValueOf = Integer.valueOf(i);
            Object obj = th;
            if (th == null) {
            }
            zzgrVarZzh.zzd("Network upload failed. Will retry later. appId, status, error", str, numValueOf, obj);
            zzj().zzK(Long.valueOf(zzpiVar.zzc()));
            zzaK();
        } else if (th != null) {
            zzj().zzE(Long.valueOf(zzpiVar.zzc()));
            zzaV().zzk().zzc("Successfully uploaded batch from upload queue. appId, status", str, Integer.valueOf(i));
            if (zzi().zzb() && zzj().zzD(str)) {
                zzP(str);
            } else {
                zzaK();
            }
        } else {
            String str22 = new String(bArr, StandardCharsets.UTF_8);
            String strSubstring2 = str22.substring(0, Math.min(32, str22.length()));
            zzgr zzgrVarZzh2 = zzaV().zzh();
            Integer numValueOf2 = Integer.valueOf(i);
            Object obj2 = th;
            if (th == null) {
                obj2 = strSubstring2;
            }
            zzgrVarZzh2.zzd("Network upload failed. Will retry later. appId, status, error", str, numValueOf2, obj2);
            zzj().zzK(Long.valueOf(zzpiVar.zzc()));
            zzaK();
        }
        this.zzv = false;
        zzaL();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzR(String str, boolean z, Long l, Long l2) {
        zzh zzhVarZzu = zzj().zzu(str);
        if (zzhVarZzu != null) {
            zzhVarZzu.zzar(z);
            zzhVarZzu.zzat(l);
            zzhVarZzu.zzav(l2);
            if (zzhVarZzu.zza()) {
                zzj().zzv(zzhVarZzu, false, false);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzS(String str, com.google.android.gms.internal.measurement.zzic zzicVar) {
        int iZzx;
        int iIndexOf;
        Set setZzl = zzh().zzl(str);
        if (setZzl != null) {
            zzicVar.zzaw(setZzl);
        }
        if (zzh().zzp(str)) {
            zzicVar.zzG();
        }
        if (zzh().zzq(str)) {
            String strZzD = zzicVar.zzD();
            if (!TextUtils.isEmpty(strZzD) && (iIndexOf = strZzD.indexOf(".")) != -1) {
                zzicVar.zzE(strZzD.substring(0, iIndexOf));
            }
        }
        if (zzh().zzr(str) && (iZzx = zzpj.zzx(zzicVar, "_id")) != -1) {
            zzicVar.zzr(iZzx);
        }
        if (zzh().zzs(str)) {
            zzicVar.zzan();
        }
        if (zzh().zzt(str)) {
            zzicVar.zzX();
            if (zzB(str).zzo(zzjj.ANALYTICS_STORAGE)) {
                Map map = this.zzE;
                zzpc zzpcVar = (zzpc) map.get(str);
                if (zzpcVar == null || zzpcVar.zzb + zzd().zzl(str, zzfx.zzak) < zzaZ().elapsedRealtime()) {
                    zzpcVar = new zzpc(this, (byte[]) null);
                    map.put(str, zzpcVar);
                }
                zzicVar.zzax(zzpcVar.zza);
            }
        }
        if (zzh().zzu(str)) {
            zzicVar.zzav();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzT(String str, com.google.android.gms.internal.measurement.zzhv zzhvVar, Bundle bundle, String str2) {
        List listListOf = CollectionUtils.listOf((Object[]) new String[]{"_o", "_sn", "_sc", "_si"});
        long jZzf = (zzpo.zzZ(zzhvVar.zza()) || zzpo.zzZ(str)) ? zzd().zzf(str2, true) : zzd().zze(str2, true);
        long jCodePointCount = zzhvVar.zzc().codePointCount(0, zzhvVar.zzc().length());
        zzpo zzpoVarZzt = zzt();
        String strZza = zzhvVar.zza();
        zzd();
        String strZzC = zzpoVarZzt.zzC(strZza, 40, true);
        if (jCodePointCount <= jZzf || listListOf.contains(zzhvVar.zza())) {
            return;
        }
        if ("_ev".equals(zzhvVar.zza())) {
            bundle.putString("_ev", zzt().zzC(zzhvVar.zzc(), zzd().zzf(str2, true), true));
            return;
        }
        zzaV().zzh().zzc("Param value is too long; discarded. Name, value length", strZzC, Long.valueOf(jCodePointCount));
        if (bundle.getLong("_err") == 0) {
            bundle.putLong("_err", 4L);
            if (bundle.getString("_ev") == null) {
                bundle.putString("_ev", strZzC);
                bundle.putLong("_el", jCodePointCount);
            }
        }
        bundle.remove(zzhvVar.zza());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:18:0x006e A[Catch: all -> 0x028f, TryCatch #3 {all -> 0x028f, blocks: (B:4:0x0013, B:6:0x0018, B:14:0x0031, B:19:0x007d, B:18:0x006e, B:20:0x0089, B:22:0x00a0, B:23:0x00af, B:25:0x00bd, B:27:0x00dd, B:67:0x0215, B:69:0x0228, B:71:0x0232, B:79:0x0252, B:73:0x0238, B:75:0x0242, B:77:0x0248, B:78:0x024c, B:81:0x0256, B:82:0x025d, B:26:0x00d0, B:84:0x025f), top: B:94:0x0013, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0089 A[Catch: all -> 0x028f, PHI: r0
  0x0089: PHI (r0v2 int) = (r0v0 int), (r0v37 int) binds: [B:7:0x0024, B:13:0x002f] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #3 {all -> 0x028f, blocks: (B:4:0x0013, B:6:0x0018, B:14:0x0031, B:19:0x007d, B:18:0x006e, B:20:0x0089, B:22:0x00a0, B:23:0x00af, B:25:0x00bd, B:27:0x00dd, B:67:0x0215, B:69:0x0228, B:71:0x0232, B:79:0x0252, B:73:0x0238, B:75:0x0242, B:77:0x0248, B:78:0x024c, B:81:0x0256, B:82:0x025d, B:26:0x00d0, B:84:0x025f), top: B:94:0x0013, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00bd A[Catch: SQLiteException -> 0x025e, all -> 0x028f, TryCatch #2 {SQLiteException -> 0x025e, blocks: (B:22:0x00a0, B:23:0x00af, B:25:0x00bd, B:27:0x00dd, B:67:0x0215, B:69:0x0228, B:71:0x0232, B:79:0x0252, B:73:0x0238, B:75:0x0242, B:77:0x0248, B:78:0x024c, B:81:0x0256, B:82:0x025d, B:26:0x00d0), top: B:93:0x00a0, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00d0 A[Catch: SQLiteException -> 0x025e, all -> 0x028f, TryCatch #2 {SQLiteException -> 0x025e, blocks: (B:22:0x00a0, B:23:0x00af, B:25:0x00bd, B:27:0x00dd, B:67:0x0215, B:69:0x0228, B:71:0x0232, B:79:0x0252, B:73:0x0238, B:75:0x0242, B:77:0x0248, B:78:0x024c, B:81:0x0256, B:82:0x025d, B:26:0x00d0), top: B:93:0x00a0, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00f5 A[Catch: all -> 0x0255, TryCatch #0 {all -> 0x0255, blocks: (B:28:0x00e4, B:29:0x00ed, B:31:0x00f5, B:33:0x010c, B:37:0x0136, B:39:0x0140, B:41:0x014e, B:42:0x0153, B:44:0x0159, B:46:0x0170, B:48:0x0195, B:50:0x01b0, B:52:0x01d3, B:53:0x01e4, B:54:0x01e8, B:56:0x01ee, B:57:0x01f5, B:60:0x0202, B:62:0x0206, B:65:0x020d, B:66:0x020e), top: B:90:0x00e4, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0159 A[Catch: all -> 0x0255, TryCatch #0 {all -> 0x0255, blocks: (B:28:0x00e4, B:29:0x00ed, B:31:0x00f5, B:33:0x010c, B:37:0x0136, B:39:0x0140, B:41:0x014e, B:42:0x0153, B:44:0x0159, B:46:0x0170, B:48:0x0195, B:50:0x01b0, B:52:0x01d3, B:53:0x01e4, B:54:0x01e8, B:56:0x01ee, B:57:0x01f5, B:60:0x0202, B:62:0x0206, B:65:0x020d, B:66:0x020e), top: B:90:0x00e4, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01b0 A[Catch: all -> 0x0255, TryCatch #0 {all -> 0x0255, blocks: (B:28:0x00e4, B:29:0x00ed, B:31:0x00f5, B:33:0x010c, B:37:0x0136, B:39:0x0140, B:41:0x014e, B:42:0x0153, B:44:0x0159, B:46:0x0170, B:48:0x0195, B:50:0x01b0, B:52:0x01d3, B:53:0x01e4, B:54:0x01e8, B:56:0x01ee, B:57:0x01f5, B:60:0x0202, B:62:0x0206, B:65:0x020d, B:66:0x020e), top: B:90:0x00e4, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01ee A[Catch: all -> 0x0255, TRY_LEAVE, TryCatch #0 {all -> 0x0255, blocks: (B:28:0x00e4, B:29:0x00ed, B:31:0x00f5, B:33:0x010c, B:37:0x0136, B:39:0x0140, B:41:0x014e, B:42:0x0153, B:44:0x0159, B:46:0x0170, B:48:0x0195, B:50:0x01b0, B:52:0x01d3, B:53:0x01e4, B:54:0x01e8, B:56:0x01ee, B:57:0x01f5, B:60:0x0202, B:62:0x0206, B:65:0x020d, B:66:0x020e), top: B:90:0x00e4, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0238 A[Catch: SQLiteException -> 0x025e, all -> 0x028f, TryCatch #2 {SQLiteException -> 0x025e, blocks: (B:22:0x00a0, B:23:0x00af, B:25:0x00bd, B:27:0x00dd, B:67:0x0215, B:69:0x0228, B:71:0x0232, B:79:0x0252, B:73:0x0238, B:75:0x0242, B:77:0x0248, B:78:0x024c, B:81:0x0256, B:82:0x025d, B:26:0x00d0), top: B:93:0x00a0, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x024c A[Catch: SQLiteException -> 0x025e, all -> 0x028f, TryCatch #2 {SQLiteException -> 0x025e, blocks: (B:22:0x00a0, B:23:0x00af, B:25:0x00bd, B:27:0x00dd, B:67:0x0215, B:69:0x0228, B:71:0x0232, B:79:0x0252, B:73:0x0238, B:75:0x0242, B:77:0x0248, B:78:0x024c, B:81:0x0256, B:82:0x025d, B:26:0x00d0), top: B:93:0x00a0, outer: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void zzU(boolean z, int i, Throwable th, byte[] bArr, String str, List list) {
        byte[] bArr2;
        Iterator it;
        Iterator it2;
        List listZzC;
        int i2 = i;
        zzaW().zzg();
        zzu();
        if (bArr == null) {
            try {
                bArr2 = new byte[0];
            } finally {
                this.zzv = false;
                zzaL();
            }
        } else {
            bArr2 = bArr;
        }
        List<Long> list2 = (List) Preconditions.checkNotNull(this.zzz);
        this.zzz = null;
        if (!z) {
            zzgr zzgrVarZzk = zzaV().zzk();
            Integer numValueOf = Integer.valueOf(i2);
            zzgrVarZzk.zzc("Network upload successful with code, uploadAttempted", numValueOf, Boolean.valueOf(z));
            if (z) {
                try {
                    this.zzk.zzd.zzb(zzaZ().currentTimeMillis());
                    this.zzk.zze.zzb(0L);
                    zzaK();
                    if (z) {
                        zzaV().zzk().zza("Purged empty bundles");
                    } else {
                        zzaV().zzk().zzc("Successful upload. Got network response. code, size", numValueOf, Integer.valueOf(bArr2.length));
                    }
                    zzj().zzb();
                    try {
                        HashMap map = new HashMap();
                        it = list.iterator();
                        while (it.hasNext()) {
                            Pair pair = (Pair) it.next();
                            com.google.android.gms.internal.measurement.zzib zzibVar = (com.google.android.gms.internal.measurement.zzib) pair.first;
                            zzos zzosVar = (zzos) pair.second;
                            if (zzosVar.zzc() != zzlr.SGTM_CLIENT) {
                                long jZzA = zzj().zzA(str, zzibVar, zzosVar.zza(), zzosVar.zzb(), zzosVar.zzc(), null);
                                if (zzosVar.zzc() == zzlr.GOOGLE_SIGNAL_PENDING && jZzA != -1 && !zzibVar.zze().isEmpty()) {
                                    map.put(zzibVar.zze(), Long.valueOf(jZzA));
                                }
                            }
                        }
                        it2 = list.iterator();
                        while (it2.hasNext()) {
                            Pair pair2 = (Pair) it2.next();
                            com.google.android.gms.internal.measurement.zzib zzibVar2 = (com.google.android.gms.internal.measurement.zzib) pair2.first;
                            zzos zzosVar2 = (zzos) pair2.second;
                            if (zzosVar2.zzc() == zzlr.SGTM_CLIENT) {
                                zzj().zzA(str, zzibVar2, zzosVar2.zza(), zzosVar2.zzb(), zzosVar2.zzc(), (Long) map.get(zzibVar2.zze()));
                            }
                        }
                        listZzC = zzj().zzC(str, zzon.zza(zzlr.SGTM_CLIENT), 1);
                        if (!listZzC.isEmpty()) {
                            long jZzg = ((zzpi) listZzC.get(0)).zzg();
                            if (zzaZ().currentTimeMillis() > ((Long) zzfx.zzE.zzb(null)).longValue() + jZzg) {
                                zzaV().zze().zzc("[sgtm] client batches are queued too long. appId, creationTime", str, Long.valueOf(jZzg));
                            }
                        }
                        for (Long l : list2) {
                            try {
                                zzj().zzH(l.longValue());
                            } catch (SQLiteException e) {
                                List list3 = this.zzA;
                                if (list3 == null || !list3.contains(l)) {
                                    throw e;
                                }
                            }
                        }
                        zzj().zzc();
                        zzj().zzd();
                        this.zzA = null;
                        if (!zzi().zzb() && zzj().zzD(str)) {
                            zzP(str);
                        } else if (zzi().zzb() || !zzaI()) {
                            this.zzB = -1L;
                            zzaK();
                        } else {
                            zzM();
                        }
                        this.zza = 0L;
                    } catch (Throwable th2) {
                        zzj().zzd();
                        throw th2;
                    }
                } catch (SQLiteException e2) {
                    zzaV().zzb().zzb("Database error while trying to delete uploaded bundles", e2);
                    this.zza = zzaZ().elapsedRealtime();
                    zzaV().zzk().zzb("Disable upload, time", Long.valueOf(this.zza));
                }
            } else {
                this.zzk.zze.zzb(0L);
                zzaK();
                if (z) {
                }
                zzj().zzb();
                HashMap map2 = new HashMap();
                it = list.iterator();
                while (it.hasNext()) {
                }
                it2 = list.iterator();
                while (it2.hasNext()) {
                }
                listZzC = zzj().zzC(str, zzon.zza(zzlr.SGTM_CLIENT), 1);
                if (!listZzC.isEmpty()) {
                }
                while (r2.hasNext()) {
                }
                zzj().zzc();
                zzj().zzd();
                this.zzA = null;
                if (!zzi().zzb()) {
                    if (zzi().zzb()) {
                        this.zzB = -1L;
                        zzaK();
                        this.zza = 0L;
                    }
                }
            }
        } else if (i2 != 200) {
            if (i2 == 204) {
                i2 = 204;
                if (th == null) {
                }
            }
            String str2 = new String(bArr2, StandardCharsets.UTF_8);
            zzaV().zzh().zzd("Network upload failed. Will retry later. code, error", Integer.valueOf(i2), th, str2.substring(0, Math.min(32, str2.length())));
            this.zzk.zze.zzb(zzaZ().currentTimeMillis());
            if (i2 != 503) {
                this.zzk.zzc.zzb(zzaZ().currentTimeMillis());
                zzj().zzJ(list2);
                zzaK();
            }
        } else if (th == null) {
            String str22 = new String(bArr2, StandardCharsets.UTF_8);
            zzaV().zzh().zzd("Network upload failed. Will retry later. code, error", Integer.valueOf(i2), th, str22.substring(0, Math.min(32, str22.length())));
            this.zzk.zze.zzb(zzaZ().currentTimeMillis());
            if (i2 != 503 || i2 == 429) {
                this.zzk.zzc.zzb(zzaZ().currentTimeMillis());
            }
            zzj().zzJ(list2);
            zzaK();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzV(zzh zzhVar) {
        zzaW().zzg();
        if (TextUtils.isEmpty(zzhVar.zzf())) {
            zzW((String) Preconditions.checkNotNull(zzhVar.zzc()), 204, null, null, null);
            return;
        }
        String str = (String) Preconditions.checkNotNull(zzhVar.zzc());
        zzaV().zzk().zzb("Fetching remote configuration", str);
        com.google.android.gms.internal.measurement.zzgl zzglVarZzb = zzh().zzb(str);
        String strZzd = zzh().zzd(str);
        ArrayMap arrayMap = null;
        if (zzglVarZzb != null) {
            if (!TextUtils.isEmpty(strZzd)) {
                ArrayMap arrayMap2 = new ArrayMap();
                arrayMap2.put(HttpHeaders.IF_MODIFIED_SINCE, strZzd);
                arrayMap = arrayMap2;
            }
            String strZze = zzh().zze(str);
            if (!TextUtils.isEmpty(strZze)) {
                if (arrayMap == null) {
                    arrayMap = new ArrayMap();
                }
                arrayMap.put(HttpHeaders.IF_NONE_MATCH, strZze);
            }
        }
        this.zzu = true;
        zzi().zzd(zzhVar, arrayMap, new zzgv() { // from class: com.google.android.gms.measurement.internal.zzpe
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // com.google.android.gms.measurement.internal.zzgv
            public final /* synthetic */ void zza(String str2, int i, Throwable th, byte[] bArr, Map map) {
                this.zza.zzW(str2, i, th, bArr, map);
            }
        });
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void zzW(String str, int i, Throwable th, byte[] bArr, Map map) {
        boolean z;
        zzaW().zzg();
        zzu();
        Preconditions.checkNotEmpty(str);
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } finally {
                this.zzu = false;
                zzaL();
            }
        }
        zzgr zzgrVarZzk = zzaV().zzk();
        Integer numValueOf = Integer.valueOf(bArr.length);
        zzgrVarZzk.zzb("onConfigFetched. Response size", numValueOf);
        zzj().zzb();
        try {
            zzh zzhVarZzu = zzj().zzu(str);
            if (i == 200 || i == 204) {
                z = th != null;
            } else {
                if (i == 304) {
                    i = 304;
                    if (th != null) {
                    }
                }
            }
            if (zzhVarZzu == null) {
                zzaV().zze().zzb("App does not exist in onConfigFetched. appId", zzgt.zzl(str));
            } else if (z || i == 404) {
                String strZzaJ = zzaJ(map, HttpHeaders.LAST_MODIFIED);
                String strZzaJ2 = zzaJ(map, HttpHeaders.ETAG);
                if (i != 404 && i != 304) {
                    zzh().zzi(str, bArr, strZzaJ, strZzaJ2);
                } else if (zzh().zzb(str) == null) {
                    zzh().zzi(str, null, null, null);
                }
                zzhVarZzu.zzI(zzaZ().currentTimeMillis());
                zzj().zzv(zzhVarZzu, false, false);
                if (i == 404) {
                    zzaV().zzh().zzb("Config not found. Using empty config. appId", str);
                } else {
                    zzaV().zzk().zzc("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), numValueOf);
                }
                if (zzi().zzb() && zzaI()) {
                    zzM();
                } else if (zzi().zzb() && zzj().zzD(zzhVarZzu.zzc())) {
                    zzP(zzhVarZzu.zzc());
                } else {
                    zzaK();
                }
            } else {
                zzhVarZzu.zzK(zzaZ().currentTimeMillis());
                zzj().zzv(zzhVarZzu, false, false);
                zzaV().zzk().zzc("Fetching config failed. code, error", Integer.valueOf(i), th);
                zzh().zzf(str);
                this.zzk.zze.zzb(zzaZ().currentTimeMillis());
                if (i == 503 || i == 429) {
                    this.zzk.zzc.zzb(zzaZ().currentTimeMillis());
                }
                zzaK();
            }
            zzj().zzc();
        } finally {
            zzj().zzd();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzX(Runnable runnable) {
        zzaW().zzg();
        if (this.zzq == null) {
            this.zzq = new ArrayList();
        }
        this.zzq.add(runnable);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzY() {
        zzaW().zzg();
        zzu();
        if (this.zzp) {
            return;
        }
        this.zzp = true;
        if (zzZ()) {
            FileChannel fileChannel = this.zzy;
            zzaW().zzg();
            int i = 0;
            if (fileChannel == null || !fileChannel.isOpen()) {
                zzaV().zzb().zza("Bad channel to read from");
            } else {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
                try {
                    fileChannel.position(0L);
                    int i2 = fileChannel.read(byteBufferAllocate);
                    if (i2 == 4) {
                        byteBufferAllocate.flip();
                        i = byteBufferAllocate.getInt();
                    } else if (i2 != -1) {
                        zzaV().zze().zzb("Unexpected data length. Bytes read", Integer.valueOf(i2));
                    }
                } catch (IOException e) {
                    zzaV().zzb().zzb("Failed to read from channel", e);
                }
            }
            int iZzm = this.zzn.zzv().zzm();
            zzaW().zzg();
            if (i > iZzm) {
                zzaV().zzb().zzc("Panic: can't downgrade version. Previous, current version", Integer.valueOf(i), Integer.valueOf(iZzm));
                return;
            }
            if (i < iZzm) {
                FileChannel fileChannel2 = this.zzy;
                zzaW().zzg();
                if (fileChannel2 == null || !fileChannel2.isOpen()) {
                    zzaV().zzb().zza("Bad channel to read from");
                } else {
                    ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(4);
                    byteBufferAllocate2.putInt(iZzm);
                    byteBufferAllocate2.flip();
                    try {
                        fileChannel2.truncate(0L);
                        fileChannel2.write(byteBufferAllocate2);
                        fileChannel2.force(true);
                        if (fileChannel2.size() != 4) {
                            zzaV().zzb().zzb("Error writing to channel. Bytes written", Long.valueOf(fileChannel2.size()));
                        }
                        zzaV().zzk().zzc("Storage version upgraded. Previous, current version", Integer.valueOf(i), Integer.valueOf(iZzm));
                        return;
                    } catch (IOException e2) {
                        zzaV().zzb().zzb("Failed to write to channel", e2);
                    }
                }
                zzaV().zzb().zzc("Storage version upgrade failed. Previous, current version", Integer.valueOf(i), Integer.valueOf(iZzm));
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final boolean zzZ() {
        zzaW().zzg();
        FileLock fileLock = this.zzx;
        if (fileLock != null && fileLock.isValid()) {
            zzaV().zzk().zza("Storage concurrent access okay");
            return true;
        }
        this.zze.zzu.zzc();
        File filesDir = this.zzn.zzaY().getFilesDir();
        com.google.android.gms.internal.measurement.zzbv.zza();
        int i = com.google.android.gms.internal.measurement.zzca.zzb;
        try {
            FileChannel channel = new RandomAccessFile(new File(new File(filesDir, "google_app_measurement.db").getPath()), "rw").getChannel();
            this.zzy = channel;
            FileLock fileLockTryLock = channel.tryLock();
            this.zzx = fileLockTryLock;
            if (fileLockTryLock != null) {
                zzaV().zzk().zza("Storage concurrent access okay");
                return true;
            }
            zzaV().zzb().zza("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e) {
            zzaV().zzb().zzb("Failed to acquire storage lock", e);
            return false;
        } catch (IOException e2) {
            zzaV().zzb().zzb("Failed to access storage lock file", e2);
            return false;
        } catch (OverlappingFileLockException e3) {
            zzaV().zze().zzb("Storage lock already acquired", e3);
            return false;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzjf
    public final zzae zzaU() {
        return this.zzn.zzaU();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzjf
    public final zzgt zzaV() {
        return ((zzib) Preconditions.checkNotNull(this.zzn)).zzaV();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzjf
    public final zzhy zzaW() {
        return ((zzib) Preconditions.checkNotNull(this.zzn)).zzaW();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzjf
    public final Context zzaY() {
        return this.zzn.zzaY();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzjf
    public final Clock zzaZ() {
        return ((zzib) Preconditions.checkNotNull(this.zzn)).zzaZ();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzaa(zzr zzrVar) {
        if (this.zzz != null) {
            ArrayList arrayList = new ArrayList();
            this.zzA = arrayList;
            arrayList.addAll(this.zzz);
        }
        zzav zzavVarZzj = zzj();
        String str = (String) Preconditions.checkNotNull(zzrVar.zza);
        Preconditions.checkNotEmpty(str);
        zzavVarZzj.zzg();
        zzavVarZzj.zzay();
        try {
            SQLiteDatabase sQLiteDatabaseZze = zzavVarZzj.zze();
            String[] strArr = {str};
            int iDelete = sQLiteDatabaseZze.delete("apps", "app_id=?", strArr) + sQLiteDatabaseZze.delete("events", "app_id=?", strArr) + sQLiteDatabaseZze.delete("events_snapshot", "app_id=?", strArr) + sQLiteDatabaseZze.delete("user_attributes", "app_id=?", strArr) + sQLiteDatabaseZze.delete("conditional_properties", "app_id=?", strArr) + sQLiteDatabaseZze.delete("raw_events", "app_id=?", strArr) + sQLiteDatabaseZze.delete("raw_events_metadata", "app_id=?", strArr) + sQLiteDatabaseZze.delete("queue", "app_id=?", strArr) + sQLiteDatabaseZze.delete("audience_filter_values", "app_id=?", strArr) + sQLiteDatabaseZze.delete("main_event_params", "app_id=?", strArr) + sQLiteDatabaseZze.delete("default_event_params", "app_id=?", strArr) + sQLiteDatabaseZze.delete("trigger_uris", "app_id=?", strArr) + sQLiteDatabaseZze.delete("upload_queue", "app_id=?", strArr);
            com.google.android.gms.internal.measurement.zzpk.zza();
            zzib zzibVar = zzavVarZzj.zzu;
            if (zzibVar.zzc().zzp(null, zzfx.zzbi)) {
                iDelete += sQLiteDatabaseZze.delete("no_data_mode_events", "app_id=?", strArr);
            }
            if (iDelete > 0) {
                zzibVar.zzaV().zzk().zzc("Reset analytics data. app, records", str, Integer.valueOf(iDelete));
            }
        } catch (SQLiteException e) {
            zzavVarZzj.zzu.zzaV().zzb().zzc("Error resetting analytics data. appId, error", zzgt.zzl(str), e);
        }
        if (zzrVar.zzh) {
            zzag(zzrVar);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ce  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void zzab(zzpk zzpkVar, zzr zzrVar) {
        zzpm zzpmVarZzm;
        long jLongValue;
        zzaW().zzg();
        zzu();
        if (zzaQ(zzrVar)) {
            if (!zzrVar.zzh) {
                zzan(zzrVar);
                return;
            }
            zzpo zzpoVarZzt = zzt();
            String str = zzpkVar.zzb;
            int iZzp = zzpoVarZzt.zzp(str);
            if (iZzp != 0) {
                zzpo zzpoVarZzt2 = zzt();
                zzd();
                zzt().zzN(this.zzK, zzrVar.zza, iZzp, "_ev", zzpoVarZzt2.zzC(str, 24, true), str != null ? str.length() : 0);
                return;
            }
            int iZzK = zzt().zzK(str, zzpkVar.zza());
            if (iZzK != 0) {
                zzpo zzpoVarZzt3 = zzt();
                zzd();
                String strZzC = zzpoVarZzt3.zzC(str, 24, true);
                Object objZza = zzpkVar.zza();
                if (objZza != null && ((objZza instanceof String) || (objZza instanceof CharSequence))) {
                    length = objZza.toString().length();
                }
                zzt().zzN(this.zzK, zzrVar.zza, iZzK, "_ev", strZzC, length);
                return;
            }
            Object objZzL = zzt().zzL(str, zzpkVar.zza());
            if (objZzL != null) {
                if ("_sid".equals(str)) {
                    long j = zzpkVar.zzc;
                    String str2 = zzpkVar.zzf;
                    String str3 = (String) Preconditions.checkNotNull(zzrVar.zza);
                    zzpm zzpmVarZzm2 = zzj().zzm(str3, "_sno");
                    if (zzpmVarZzm2 != null) {
                        Object obj = zzpmVarZzm2.zze;
                        if (obj instanceof Long) {
                            jLongValue = ((Long) obj).longValue();
                        } else {
                            if (zzpmVarZzm2 != null) {
                                zzaV().zze().zzb("Retrieved last session number from database does not contain a valid (long) value", zzpmVarZzm2.zze);
                            }
                            zzbc zzbcVarZzf = zzj().zzf(str3, "_s");
                            if (zzbcVarZzf != null) {
                                zzgr zzgrVarZzk = zzaV().zzk();
                                long j2 = zzbcVarZzf.zzc;
                                zzgrVarZzk.zzb("Backfill the session number. Last used session number", Long.valueOf(j2));
                                jLongValue = j2;
                            } else {
                                jLongValue = 0;
                            }
                        }
                        zzab(new zzpk("_sno", j, Long.valueOf(jLongValue + 1), str2), zzrVar);
                    }
                }
                String str4 = zzrVar.zza;
                zzpm zzpmVar = new zzpm((String) Preconditions.checkNotNull(str4), (String) Preconditions.checkNotNull(zzpkVar.zzf), str, zzpkVar.zzc, objZzL);
                zzgr zzgrVarZzk2 = zzaV().zzk();
                zzib zzibVar = this.zzn;
                String str5 = zzpmVar.zzc;
                zzgrVarZzk2.zzc("Setting user property", zzibVar.zzl().zzc(str5), objZzL);
                zzj().zzb();
                try {
                    if ("_id".equals(str5) && (zzpmVarZzm = zzj().zzm(str4, "_id")) != null && !zzpmVar.zze.equals(zzpmVarZzm.zze)) {
                        zzj().zzk(str4, "_lair");
                    }
                    zzan(zzrVar);
                    boolean zZzl = zzj().zzl(zzpmVar);
                    if ("_sid".equals(str)) {
                        long jZzu = zzp().zzu(zzrVar.zzu);
                        zzh zzhVarZzu = zzj().zzu(str4);
                        if (zzhVarZzu != null) {
                            zzhVarZzu.zzan(jZzu);
                            if (zzhVarZzu.zza()) {
                                zzj().zzv(zzhVarZzu, false, false);
                            }
                        }
                    }
                    zzj().zzc();
                    if (!zZzl) {
                        zzaV().zzb().zzc("Too many unique user properties are set. Ignoring user property", zzibVar.zzl().zzc(str5), zzpmVar.zze);
                        zzt().zzN(this.zzK, str4, 9, null, null, 0);
                    }
                } finally {
                    zzj().zzd();
                }
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzac(String str, zzr zzrVar) {
        zzaW().zzg();
        zzu();
        if (zzaQ(zzrVar)) {
            if (!zzrVar.zzh) {
                zzan(zzrVar);
                return;
            }
            Boolean boolZzaT = zzaT(zzrVar);
            if ("_npa".equals(str) && boolZzaT != null) {
                zzaV().zzj().zza("Falling back to manifest metadata value for ad personalization");
                zzab(new zzpk("_npa", zzaZ().currentTimeMillis(), Long.valueOf(true != boolZzaT.booleanValue() ? 0L : 1L), DebugKt.DEBUG_PROPERTY_VALUE_AUTO), zzrVar);
                return;
            }
            zzgr zzgrVarZzj = zzaV().zzj();
            zzib zzibVar = this.zzn;
            zzgrVarZzj.zzb("Removing user property", zzibVar.zzl().zzc(str));
            zzj().zzb();
            try {
                zzan(zzrVar);
                if ("_id".equals(str)) {
                    zzj().zzk((String) Preconditions.checkNotNull(zzrVar.zza), "_lair");
                }
                zzj().zzk((String) Preconditions.checkNotNull(zzrVar.zza), str);
                zzj().zzc();
                zzaV().zzj().zzb("User property removed", zzibVar.zzl().zzc(str));
            } finally {
                zzj().zzd();
            }
        }
    }

    final void zzad() {
        this.zzs++;
    }

    final void zzae() {
        this.zzt++;
    }

    final zzib zzaf() {
        return this.zzn;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzag(zzr zzrVar) {
        long j;
        long j2;
        zzbc zzbcVarZzf;
        boolean z;
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo;
        ApplicationInfo applicationInfo2;
        long j3;
        boolean z2;
        zzaW().zzg();
        zzu();
        Preconditions.checkNotNull(zzrVar);
        String str = zzrVar.zza;
        Preconditions.checkNotEmpty(str);
        if (zzaQ(zzrVar)) {
            zzh zzhVarZzu = zzj().zzu(str);
            if (zzhVarZzu != null && TextUtils.isEmpty(zzhVarZzu.zzf()) && !TextUtils.isEmpty(zzrVar.zzb)) {
                zzhVarZzu.zzI(0L);
                zzj().zzv(zzhVarZzu, false, false);
                zzh().zzh(str);
            }
            if (!zzrVar.zzh) {
                zzan(zzrVar);
                return;
            }
            long jCurrentTimeMillis = zzrVar.zzl;
            if (jCurrentTimeMillis == 0) {
                jCurrentTimeMillis = zzaZ().currentTimeMillis();
            }
            long j4 = jCurrentTimeMillis;
            int i = zzrVar.zzm;
            if (i != 0 && i != 1) {
                zzaV().zze().zzc("Incorrect app type, assuming installed app. appId, appType", zzgt.zzl(str), Integer.valueOf(i));
                i = 0;
            }
            zzj().zzb();
            try {
                zzpm zzpmVarZzm = zzj().zzm(str, "_npa");
                Boolean boolZzaT = zzaT(zzrVar);
                if (zzpmVarZzm != null && !DebugKt.DEBUG_PROPERTY_VALUE_AUTO.equals(zzpmVarZzm.zzb)) {
                    j = j4;
                    j2 = 1;
                } else if (boolZzaT != null) {
                    zzpk zzpkVar = new zzpk("_npa", j4, Long.valueOf(true != boolZzaT.booleanValue() ? 0L : 1L), DebugKt.DEBUG_PROPERTY_VALUE_AUTO);
                    j2 = 1;
                    j = j4;
                    if (zzpmVarZzm == null || !zzpmVarZzm.zze.equals(zzpkVar.zzd)) {
                        zzab(zzpkVar, zzrVar);
                    }
                } else {
                    j = j4;
                    j2 = 1;
                    if (zzpmVarZzm != null) {
                        zzac("_npa", zzrVar);
                    }
                }
                if (zzd().zzp(null, zzfx.zzbc)) {
                    zzam(zzrVar, zzrVar.zzD);
                } else {
                    zzam(zzrVar, j);
                }
                zzan(zzrVar);
                if (i == 0) {
                    zzbcVarZzf = zzj().zzf(str, "_f");
                    z = false;
                } else {
                    zzbcVarZzf = zzj().zzf(str, "_v");
                    z = true;
                }
                if (zzbcVarZzf == null) {
                    long j5 = ((j / 3600000) + j2) * 3600000;
                    if (z) {
                        long j6 = j;
                        zzab(new zzpk("_fvt", j6, Long.valueOf(j5), DebugKt.DEBUG_PROPERTY_VALUE_AUTO), zzrVar);
                        zzaW().zzg();
                        zzu();
                        Bundle bundle = new Bundle();
                        bundle.putLong("_c", 1L);
                        bundle.putLong("_r", 1L);
                        bundle.putLong("_et", 1L);
                        if (zzrVar.zzo) {
                            bundle.putLong("_dac", 1L);
                        }
                        zzE(new zzbg("_v", new zzbe(bundle), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j6), zzrVar);
                    } else {
                        Long lValueOf = Long.valueOf(j5);
                        long j7 = j;
                        zzab(new zzpk("_fot", j7, lValueOf, DebugKt.DEBUG_PROPERTY_VALUE_AUTO), zzrVar);
                        zzaW().zzg();
                        zzhj zzhjVar = (zzhj) Preconditions.checkNotNull(this.zzm);
                        if (str == null || str.isEmpty()) {
                            zzhjVar.zza.zzaV().zzf().zza("Install Referrer Reporter was called with invalid app package name");
                        } else {
                            zzib zzibVar = zzhjVar.zza;
                            zzibVar.zzaW().zzg();
                            if (zzhjVar.zza()) {
                                zzhi zzhiVar = new zzhi(zzhjVar, str);
                                zzibVar.zzaW().zzg();
                                Intent intent = new Intent("com.google.android.finsky.BIND_GET_INSTALL_REFERRER_SERVICE");
                                intent.setComponent(new ComponentName("com.android.vending", "com.google.android.finsky.externalreferrer.GetInstallReferrerService"));
                                PackageManager packageManager = zzibVar.zzaY().getPackageManager();
                                if (packageManager == null) {
                                    zzibVar.zzaV().zzf().zza("Failed to obtain Package Manager to verify binding conditions for Install Referrer");
                                } else {
                                    List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 0);
                                    if (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) {
                                        zzibVar.zzaV().zzi().zza("Play Service for fetching Install Referrer is unavailable on device");
                                    } else {
                                        ResolveInfo resolveInfo = listQueryIntentServices.get(0);
                                        if (resolveInfo.serviceInfo != null) {
                                            String str2 = resolveInfo.serviceInfo.packageName;
                                            if (resolveInfo.serviceInfo.name != null && "com.android.vending".equals(str2) && zzhjVar.zza()) {
                                                try {
                                                    zzibVar.zzaV().zzk().zzb("Install Referrer Service is", ConnectionTracker.getInstance().bindService(zzibVar.zzaY(), new Intent(intent), zzhiVar, 1) ? "available" : "not available");
                                                } catch (RuntimeException e) {
                                                    zzhjVar.zza.zzaV().zzb().zzb("Exception occurred while binding to Install Referrer Service", e.getMessage());
                                                }
                                            } else {
                                                zzibVar.zzaV().zze().zza("Play Store version 8.3.73 or higher required for Install Referrer");
                                            }
                                        }
                                    }
                                }
                            } else {
                                zzibVar.zzaV().zzi().zza("Install Referrer Reporter is not available");
                            }
                        }
                        zzaW().zzg();
                        zzu();
                        Bundle bundle2 = new Bundle();
                        long j8 = j2;
                        bundle2.putLong("_c", j8);
                        bundle2.putLong("_r", j8);
                        bundle2.putLong("_uwa", 0L);
                        bundle2.putLong("_pfo", 0L);
                        bundle2.putLong("_sys", 0L);
                        bundle2.putLong("_sysu", 0L);
                        bundle2.putLong("_et", j8);
                        if (zzrVar.zzo) {
                            bundle2.putLong("_dac", j8);
                        }
                        String str3 = (String) Preconditions.checkNotNull(zzrVar.zza);
                        zzav zzavVarZzj = zzj();
                        Preconditions.checkNotEmpty(str3);
                        zzavVarZzj.zzg();
                        zzavVarZzj.zzay();
                        long jZzN = zzavVarZzj.zzN(str3, "first_open_count");
                        zzib zzibVar2 = this.zzn;
                        if (zzibVar2.zzaY().getPackageManager() == null) {
                            zzaV().zzb().zzb("PackageManager is null, first open report might be inaccurate. appId", zzgt.zzl(str3));
                        } else {
                            try {
                                packageInfo = Wrappers.packageManager(zzibVar2.zzaY()).getPackageInfo(str3, 0);
                            } catch (PackageManager.NameNotFoundException e2) {
                                zzaV().zzb().zzc("Package info is null, first open report might be inaccurate. appId", zzgt.zzl(str3), e2);
                                packageInfo = null;
                            }
                            if (packageInfo == null || packageInfo.firstInstallTime == 0) {
                                applicationInfo = null;
                            } else {
                                if (packageInfo.firstInstallTime != packageInfo.lastUpdateTime) {
                                    applicationInfo = null;
                                    if (!zzd().zzp(null, zzfx.zzaI)) {
                                        bundle2.putLong("_uwa", 1L);
                                    } else if (jZzN == 0) {
                                        bundle2.putLong("_uwa", 1L);
                                        jZzN = 0;
                                    }
                                    z2 = false;
                                } else {
                                    applicationInfo = null;
                                    z2 = true;
                                }
                                zzab(new zzpk("_fi", j7, Long.valueOf(true != z2 ? 0L : 1L), DebugKt.DEBUG_PROPERTY_VALUE_AUTO), zzrVar);
                            }
                            try {
                                applicationInfo2 = Wrappers.packageManager(this.zzn.zzaY()).getApplicationInfo(str3, 0);
                            } catch (PackageManager.NameNotFoundException e3) {
                                zzaV().zzb().zzc("Application info is null, first open report might be inaccurate. appId", zzgt.zzl(str3), e3);
                                applicationInfo2 = applicationInfo;
                            }
                            if (applicationInfo2 != null) {
                                if ((applicationInfo2.flags & 1) != 0) {
                                    j3 = 1;
                                    bundle2.putLong("_sys", 1L);
                                } else {
                                    j3 = 1;
                                }
                                if ((applicationInfo2.flags & 128) != 0) {
                                    bundle2.putLong("_sysu", j3);
                                }
                            }
                        }
                        if (jZzN >= 0) {
                            bundle2.putLong("_pfo", jZzN);
                        }
                        zzE(new zzbg("_f", new zzbe(bundle2), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j7), zzrVar);
                    }
                } else {
                    long j9 = j;
                    if (zzrVar.zzi) {
                        zzE(new zzbg("_cd", new zzbe(new Bundle()), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j9), zzrVar);
                    }
                }
                zzj().zzc();
            } finally {
                zzj().zzd();
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzah(zzr zzrVar) throws Throwable {
        zzaW().zzg();
        zzu();
        Preconditions.checkNotNull(zzrVar);
        String str = zzrVar.zza;
        Preconditions.checkNotEmpty(str);
        int i = 0;
        if (zzd().zzp(null, zzfx.zzaz)) {
            long jCurrentTimeMillis = zzaZ().currentTimeMillis();
            int iZzm = zzd().zzm(null, zzfx.zzai);
            zzd();
            long jZzF = jCurrentTimeMillis - zzal.zzF();
            while (i < iZzm && zzaF(null, jZzF)) {
                i++;
            }
        } else {
            zzd();
            long jZzH = zzal.zzH();
            while (i < jZzH && zzaF(str, 0L)) {
                i++;
            }
        }
        if (zzd().zzp(null, zzfx.zzaA)) {
            zzaW().zzg();
            zzau();
        }
        if (this.zzl.zzc(str, com.google.android.gms.internal.measurement.zzin.zzb(zzrVar.zzE))) {
            zzaV().zzk().zzb("[sgtm] Going background, trigger client side upload. appId", str);
            zzN(str, zzaZ().currentTimeMillis());
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzai(zzah zzahVar) {
        zzr zzrVarZzaN = zzaN((String) Preconditions.checkNotNull(zzahVar.zza));
        if (zzrVarZzaN != null) {
            zzaj(zzahVar, zzrVarZzaN);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzaj(zzah zzahVar, zzr zzrVar) {
        Preconditions.checkNotNull(zzahVar);
        Preconditions.checkNotEmpty(zzahVar.zza);
        Preconditions.checkNotNull(zzahVar.zzb);
        Preconditions.checkNotNull(zzahVar.zzc);
        Preconditions.checkNotEmpty(zzahVar.zzc.zzb);
        zzaW().zzg();
        zzu();
        if (zzaQ(zzrVar)) {
            if (!zzrVar.zzh) {
                zzan(zzrVar);
                return;
            }
            zzah zzahVar2 = new zzah(zzahVar);
            boolean z = false;
            zzahVar2.zze = false;
            zzj().zzb();
            try {
                zzah zzahVarZzq = zzj().zzq((String) Preconditions.checkNotNull(zzahVar2.zza), zzahVar2.zzc.zzb);
                if (zzahVarZzq != null && !zzahVarZzq.zzb.equals(zzahVar2.zzb)) {
                    zzaV().zze().zzd("Updating a conditional user property with different origin. name, origin, origin (from DB)", this.zzn.zzl().zzc(zzahVar2.zzc.zzb), zzahVar2.zzb, zzahVarZzq.zzb);
                }
                if (zzahVarZzq != null && zzahVarZzq.zze) {
                    zzahVar2.zzb = zzahVarZzq.zzb;
                    zzahVar2.zzd = zzahVarZzq.zzd;
                    zzahVar2.zzh = zzahVarZzq.zzh;
                    zzahVar2.zzf = zzahVarZzq.zzf;
                    zzahVar2.zzi = zzahVarZzq.zzi;
                    zzahVar2.zze = true;
                    zzpk zzpkVar = zzahVar2.zzc;
                    zzahVar2.zzc = new zzpk(zzpkVar.zzb, zzahVarZzq.zzc.zzc, zzpkVar.zza(), zzahVarZzq.zzc.zzf);
                } else if (TextUtils.isEmpty(zzahVar2.zzf)) {
                    zzpk zzpkVar2 = zzahVar2.zzc;
                    zzahVar2.zzc = new zzpk(zzpkVar2.zzb, zzahVar2.zzd, zzpkVar2.zza(), zzahVar2.zzc.zzf);
                    zzahVar2.zze = true;
                    z = true;
                }
                if (zzahVar2.zze) {
                    zzpk zzpkVar3 = zzahVar2.zzc;
                    zzpm zzpmVar = new zzpm((String) Preconditions.checkNotNull(zzahVar2.zza), zzahVar2.zzb, zzpkVar3.zzb, zzpkVar3.zzc, Preconditions.checkNotNull(zzpkVar3.zza()));
                    if (zzj().zzl(zzpmVar)) {
                        zzaV().zzj().zzd("User property updated immediately", zzahVar2.zza, this.zzn.zzl().zzc(zzpmVar.zzc), zzpmVar.zze);
                    } else {
                        zzaV().zzb().zzd("(2)Too many active user properties, ignoring", zzgt.zzl(zzahVar2.zza), this.zzn.zzl().zzc(zzpmVar.zzc), zzpmVar.zze);
                    }
                    if (z && zzahVar2.zzi != null) {
                        zzH(new zzbg(zzahVar2.zzi, zzahVar2.zzd), zzrVar);
                    }
                }
                if (zzj().zzp(zzahVar2)) {
                    zzaV().zzj().zzd("Conditional property added", zzahVar2.zza, this.zzn.zzl().zzc(zzahVar2.zzc.zzb), zzahVar2.zzc.zza());
                } else {
                    zzaV().zzb().zzd("Too many conditional properties, ignoring", zzgt.zzl(zzahVar2.zza), this.zzn.zzl().zzc(zzahVar2.zzc.zzb), zzahVar2.zzc.zza());
                }
                zzj().zzc();
            } finally {
                zzj().zzd();
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzak(zzah zzahVar) {
        zzr zzrVarZzaN = zzaN((String) Preconditions.checkNotNull(zzahVar.zza));
        if (zzrVarZzaN != null) {
            zzal(zzahVar, zzrVarZzaN);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzal(zzah zzahVar, zzr zzrVar) {
        Preconditions.checkNotNull(zzahVar);
        Preconditions.checkNotEmpty(zzahVar.zza);
        Preconditions.checkNotNull(zzahVar.zzc);
        Preconditions.checkNotEmpty(zzahVar.zzc.zzb);
        zzaW().zzg();
        zzu();
        if (zzaQ(zzrVar)) {
            if (!zzrVar.zzh) {
                zzan(zzrVar);
                return;
            }
            zzj().zzb();
            try {
                zzan(zzrVar);
                String str = (String) Preconditions.checkNotNull(zzahVar.zza);
                zzah zzahVarZzq = zzj().zzq(str, zzahVar.zzc.zzb);
                if (zzahVarZzq != null) {
                    zzaV().zzj().zzc("Removing conditional user property", zzahVar.zza, this.zzn.zzl().zzc(zzahVar.zzc.zzb));
                    zzj().zzr(str, zzahVar.zzc.zzb);
                    if (zzahVarZzq.zze) {
                        zzj().zzk(str, zzahVar.zzc.zzb);
                    }
                    zzbg zzbgVar = zzahVar.zzk;
                    if (zzbgVar != null) {
                        zzbe zzbeVar = zzbgVar.zzb;
                        zzH((zzbg) Preconditions.checkNotNull(zzt().zzac(str, ((zzbg) Preconditions.checkNotNull(zzbgVar)).zza, zzbeVar != null ? zzbeVar.zzf() : null, zzahVarZzq.zzb, zzbgVar.zzd, true, true)), zzrVar);
                    }
                } else {
                    zzaV().zze().zzc("Conditional user property doesn't exist", zzgt.zzl(zzahVar.zza), this.zzn.zzl().zzc(zzahVar.zzc.zzb));
                }
                zzj().zzc();
            } finally {
                zzj().zzd();
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzam(zzr zzrVar, long j) {
        zzh zzhVarZzu = zzj().zzu((String) Preconditions.checkNotNull(zzrVar.zza));
        if (zzhVarZzu != null && zzt().zzB(zzrVar.zzb, zzhVarZzu.zzf())) {
            zzaV().zze().zzb("New GMP App Id passed in. Removing cached database data. appId", zzgt.zzl(zzhVarZzu.zzc()));
            zzav zzavVarZzj = zzj();
            String strZzc = zzhVarZzu.zzc();
            zzavVarZzj.zzay();
            zzavVarZzj.zzg();
            Preconditions.checkNotEmpty(strZzc);
            try {
                SQLiteDatabase sQLiteDatabaseZze = zzavVarZzj.zze();
                String[] strArr = {strZzc};
                int iDelete = sQLiteDatabaseZze.delete("events", "app_id=?", strArr) + sQLiteDatabaseZze.delete("user_attributes", "app_id=?", strArr) + sQLiteDatabaseZze.delete("conditional_properties", "app_id=?", strArr) + sQLiteDatabaseZze.delete("apps", "app_id=?", strArr) + sQLiteDatabaseZze.delete("raw_events", "app_id=?", strArr) + sQLiteDatabaseZze.delete("raw_events_metadata", "app_id=?", strArr) + sQLiteDatabaseZze.delete("event_filters", "app_id=?", strArr) + sQLiteDatabaseZze.delete("property_filters", "app_id=?", strArr) + sQLiteDatabaseZze.delete("audience_filter_values", "app_id=?", strArr) + sQLiteDatabaseZze.delete("consent_settings", "app_id=?", strArr) + sQLiteDatabaseZze.delete("default_event_params", "app_id=?", strArr) + sQLiteDatabaseZze.delete("trigger_uris", "app_id=?", strArr);
                com.google.android.gms.internal.measurement.zzpk.zza();
                zzib zzibVar = zzavVarZzj.zzu;
                if (zzibVar.zzc().zzp(null, zzfx.zzbi)) {
                    iDelete += sQLiteDatabaseZze.delete("no_data_mode_events", "app_id=?", strArr);
                }
                if (iDelete > 0) {
                    zzibVar.zzaV().zzk().zzc("Deleted application data. app, records", strZzc, Integer.valueOf(iDelete));
                }
            } catch (SQLiteException e) {
                zzavVarZzj.zzu.zzaV().zzb().zzc("Error deleting application data. appId, error", zzgt.zzl(strZzc), e);
            }
            zzhVarZzu = null;
        }
        if (zzhVarZzu != null) {
            boolean z = (zzhVarZzu.zzt() == -2147483648L || zzhVarZzu.zzt() == zzrVar.zzj) ? false : true;
            String strZzr = zzhVarZzu.zzr();
            if (z || ((zzhVarZzu.zzt() != -2147483648L || strZzr == null || strZzr.equals(zzrVar.zzc)) ? false : true)) {
                Bundle bundle = new Bundle();
                bundle.putString("_pv", strZzr);
                zzbg zzbgVar = new zzbg("_au", new zzbe(bundle), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j);
                if (zzd().zzp(null, zzfx.zzbd)) {
                    zzE(zzbgVar, zzrVar);
                } else {
                    zzF(zzbgVar, zzrVar);
                }
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final zzh zzan(zzr zzrVar) {
        boolean z;
        String str;
        long j;
        String str2;
        String str3;
        String str4;
        zzaW().zzg();
        zzu();
        Preconditions.checkNotNull(zzrVar);
        String str5 = zzrVar.zza;
        Preconditions.checkNotEmpty(str5);
        String str6 = zzrVar.zzt;
        byte[] bArr = null;
        if (!str6.isEmpty()) {
            this.zzE.put(str5, new zzpc(this, str6, bArr));
        }
        zzh zzhVarZzu = zzj().zzu(str5);
        zzjk zzjkVarZzs = zzB(str5).zzs(zzjk.zzf(zzrVar.zzs, 100));
        zzjj zzjjVar = zzjj.AD_STORAGE;
        String strZzf = zzjkVarZzs.zzo(zzjjVar) ? this.zzk.zzf(str5, zzrVar.zzn) : "";
        boolean z2 = true;
        if (zzhVarZzu == null) {
            zzh zzhVar = new zzh(this.zzn, str5);
            if (zzjkVarZzs.zzo(zzjj.ANALYTICS_STORAGE)) {
                zzhVar.zze(zzK(zzjkVarZzs));
            }
            if (zzjkVarZzs.zzo(zzjjVar)) {
                zzhVar.zzk(strZzf);
            }
            zzhVarZzu = zzhVar;
        } else if (zzjkVarZzs.zzo(zzjjVar) && strZzf != null && !strZzf.equals(zzhVarZzu.zzj())) {
            boolean zIsEmpty = TextUtils.isEmpty(zzhVarZzu.zzj());
            zzhVarZzu.zzk(strZzf);
            if (zzrVar.zzn && !"00000000-0000-0000-0000-000000000000".equals(this.zzk.zzc(str5, zzjkVarZzs).first) && !zIsEmpty) {
                if (zzjkVarZzs.zzo(zzjj.ANALYTICS_STORAGE)) {
                    zzhVarZzu.zze(zzK(zzjkVarZzs));
                    z = false;
                } else {
                    z = true;
                }
                if (zzj().zzm(str5, "_id") != null && zzj().zzm(str5, "_lair") == null) {
                    zzj().zzl(new zzpm(str5, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_lair", zzaZ().currentTimeMillis(), 1L));
                }
                zzhVarZzu.zzg(zzrVar.zzb);
                str = zzrVar.zzk;
                if (!TextUtils.isEmpty(str)) {
                    zzhVarZzu.zzm(str);
                }
                j = zzrVar.zze;
                if (j != 0) {
                    zzhVarZzu.zzy(j);
                }
                str2 = zzrVar.zzc;
                if (!TextUtils.isEmpty(str2)) {
                    zzhVarZzu.zzs(str2);
                }
                zzhVarZzu.zzu(zzrVar.zzj);
                str3 = zzrVar.zzd;
                if (str3 != null) {
                    zzhVarZzu.zzw(str3);
                }
                zzhVarZzu.zzA(zzrVar.zzf);
                zzhVarZzu.zzE(zzrVar.zzh);
                str4 = zzrVar.zzg;
                if (!TextUtils.isEmpty(str4)) {
                    zzhVarZzu.zzab(str4);
                }
                zzhVarZzu.zzad(zzrVar.zzn);
                zzhVarZzu.zzaf(zzrVar.zzp);
                zzhVarZzu.zzC(zzrVar.zzq);
                zzhVarZzu.zzi(zzrVar.zzu);
                com.google.android.gms.internal.measurement.zzpn.zza();
                if (zzd().zzp(null, zzfx.zzaL)) {
                    com.google.android.gms.internal.measurement.zzpn.zza();
                    if (zzd().zzp(null, zzfx.zzaK)) {
                        zzhVarZzu.zzah(null);
                    }
                } else {
                    zzhVarZzu.zzah(zzrVar.zzr);
                }
                zzhVarZzu.zzaj(zzrVar.zzv);
                zzhVarZzu.zzaz(zzrVar.zzB);
                zzql.zza();
                if (zzd().zzp(null, zzfx.zzaP)) {
                    zzhVarZzu.zzap(zzrVar.zzz);
                }
                zzhVarZzu.zzal(zzrVar.zzw);
                zzhVarZzu.zzaG(zzrVar.zzC);
                zzhVarZzu.zzaK(zzrVar.zzE);
                if (!zzhVarZzu.zza()) {
                    z2 = z;
                } else if (!z) {
                    return zzhVarZzu;
                }
                zzj().zzv(zzhVarZzu, z2, false);
                return zzhVarZzu;
            }
            if (TextUtils.isEmpty(zzhVarZzu.zzd()) && zzjkVarZzs.zzo(zzjj.ANALYTICS_STORAGE)) {
                zzhVarZzu.zze(zzK(zzjkVarZzs));
            }
        } else if (TextUtils.isEmpty(zzhVarZzu.zzd()) && zzjkVarZzs.zzo(zzjj.ANALYTICS_STORAGE)) {
            zzhVarZzu.zze(zzK(zzjkVarZzs));
        }
        z = false;
        zzhVarZzu.zzg(zzrVar.zzb);
        str = zzrVar.zzk;
        if (!TextUtils.isEmpty(str)) {
        }
        j = zzrVar.zze;
        if (j != 0) {
        }
        str2 = zzrVar.zzc;
        if (!TextUtils.isEmpty(str2)) {
        }
        zzhVarZzu.zzu(zzrVar.zzj);
        str3 = zzrVar.zzd;
        if (str3 != null) {
        }
        zzhVarZzu.zzA(zzrVar.zzf);
        zzhVarZzu.zzE(zzrVar.zzh);
        str4 = zzrVar.zzg;
        if (!TextUtils.isEmpty(str4)) {
        }
        zzhVarZzu.zzad(zzrVar.zzn);
        zzhVarZzu.zzaf(zzrVar.zzp);
        zzhVarZzu.zzC(zzrVar.zzq);
        zzhVarZzu.zzi(zzrVar.zzu);
        com.google.android.gms.internal.measurement.zzpn.zza();
        if (zzd().zzp(null, zzfx.zzaL)) {
        }
        zzhVarZzu.zzaj(zzrVar.zzv);
        zzhVarZzu.zzaz(zzrVar.zzB);
        zzql.zza();
        if (zzd().zzp(null, zzfx.zzaP)) {
        }
        zzhVarZzu.zzal(zzrVar.zzw);
        zzhVarZzu.zzaG(zzrVar.zzC);
        zzhVarZzu.zzaK(zzrVar.zzE);
        if (!zzhVarZzu.zza()) {
        }
        zzj().zzv(zzhVarZzu, z2, false);
        return zzhVarZzu;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final String zzao(zzr zzrVar) {
        try {
            return (String) zzaW().zzh(new zzoy(this, zzrVar)).get(WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            zzaV().zzb().zzc("Failed to get app instance id. appId", zzgt.zzl(zzrVar.zza), e);
            return null;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final List zzap(zzr zzrVar, Bundle bundle) {
        zzaW().zzg();
        zzql.zza();
        zzal zzalVarZzd = zzd();
        String str = zzrVar.zza;
        if (!zzalVarZzd.zzp(str, zzfx.zzaP) || str == null) {
            return new ArrayList();
        }
        if (bundle != null) {
            int[] intArray = bundle.getIntArray("uriSources");
            long[] longArray = bundle.getLongArray("uriTimestamps");
            if (intArray != null) {
                if (longArray == null || longArray.length != intArray.length) {
                    zzaV().zzb().zza("Uri sources and timestamps do not match");
                } else {
                    for (int i = 0; i < intArray.length; i++) {
                        zzav zzavVarZzj = zzj();
                        int i2 = intArray[i];
                        long j = longArray[i];
                        Preconditions.checkNotEmpty(str);
                        zzavVarZzj.zzg();
                        zzavVarZzj.zzay();
                        try {
                            int iDelete = zzavVarZzj.zze().delete("trigger_uris", "app_id=? and source=? and timestamp_millis<=?", new String[]{str, String.valueOf(i2), String.valueOf(j)});
                            zzgr zzgrVarZzk = zzavVarZzj.zzu.zzaV().zzk();
                            StringBuilder sb = new StringBuilder(String.valueOf(iDelete).length() + 46);
                            sb.append("Pruned ");
                            sb.append(iDelete);
                            sb.append(" trigger URIs. appId, source, timestamp");
                            zzgrVarZzk.zzd(sb.toString(), str, Integer.valueOf(i2), Long.valueOf(j));
                        } catch (SQLiteException e) {
                            zzavVarZzj.zzu.zzaV().zzb().zzc("Error pruning trigger URIs. appId", zzgt.zzl(str), e);
                        }
                    }
                }
            }
        }
        zzav zzavVarZzj2 = zzj();
        String str2 = zzrVar.zza;
        Preconditions.checkNotEmpty(str2);
        zzavVarZzj2.zzg();
        zzavVarZzj2.zzay();
        List arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = zzavVarZzj2.zze().query("trigger_uris", new String[]{"trigger_uri", "timestamp_millis", "source"}, "app_id=?", new String[]{str2}, null, null, "rowid", null);
                if (cursorQuery.moveToFirst()) {
                    do {
                        String string = cursorQuery.getString(0);
                        if (string == null) {
                            string = "";
                        }
                        arrayList.add(new zzog(string, cursorQuery.getLong(1), cursorQuery.getInt(2)));
                    } while (cursorQuery.moveToNext());
                }
            } catch (Throwable th) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            zzavVarZzj2.zzu.zzaV().zzb().zzc("Error querying trigger uris. appId", zzgt.zzl(str2), e2);
            arrayList = Collections.EMPTY_LIST;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return arrayList;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzaq(String str, zzaf zzafVar) {
        zzaW().zzg();
        zzu();
        zzav zzavVarZzj = zzj();
        long j = zzafVar.zza;
        zzpi zzpiVarZzB = zzavVarZzj.zzB(j);
        if (zzpiVarZzB == null) {
            zzaV().zze().zzc("[sgtm] Queued batch doesn't exist. appId, rowId", str, Long.valueOf(j));
            return;
        }
        String strZze = zzpiVarZzB.zze();
        if (zzafVar.zzb != zzlq.SUCCESS.zza()) {
            if (zzafVar.zzb == zzlq.BACKOFF.zza()) {
                Map map = this.zzF;
                zzpd zzpdVar = (zzpd) map.get(strZze);
                if (zzpdVar == null) {
                    zzpdVar = new zzpd(this);
                    map.put(strZze, zzpdVar);
                } else {
                    zzpdVar.zza();
                }
                zzaV().zzk().zzd("[sgtm] Putting sGTM server in backoff mode. appId, destination, nextRetryInSeconds", str, strZze, Long.valueOf((zzpdVar.zzc() - zzaZ().currentTimeMillis()) / 1000));
            }
            zzav zzavVarZzj2 = zzj();
            Long lValueOf = Long.valueOf(zzafVar.zza);
            zzavVarZzj2.zzK(lValueOf);
            zzaV().zzk().zzc("[sgtm] increased batch retry count after failed client upload. appId, rowId", str, lValueOf);
            return;
        }
        Map map2 = this.zzF;
        if (map2.containsKey(strZze)) {
            map2.remove(strZze);
        }
        zzav zzavVarZzj3 = zzj();
        Long lValueOf2 = Long.valueOf(j);
        zzavVarZzj3.zzE(lValueOf2);
        zzaV().zzk().zzc("[sgtm] queued batch deleted after successful client upload. appId, rowId", str, lValueOf2);
        long j2 = zzafVar.zzc;
        if (j2 > 0) {
            zzav zzavVarZzj4 = zzj();
            zzavVarZzj4.zzg();
            zzavVarZzj4.zzay();
            Long lValueOf3 = Long.valueOf(j2);
            Preconditions.checkNotNull(lValueOf3);
            ContentValues contentValues = new ContentValues();
            contentValues.put("upload_type", Integer.valueOf(zzlr.GOOGLE_SIGNAL.zza()));
            zzib zzibVar = zzavVarZzj4.zzu;
            contentValues.put(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, Long.valueOf(zzibVar.zzaZ().currentTimeMillis()));
            try {
                if (zzavVarZzj4.zze().update("upload_queue", contentValues, "rowid=? AND app_id=? AND upload_type=?", new String[]{String.valueOf(j2), str, String.valueOf(zzlr.GOOGLE_SIGNAL_PENDING.zza())}) != 1) {
                    zzibVar.zzaV().zze().zzc("Google Signal pending batch not updated. appId, rowId", str, lValueOf3);
                }
                zzaV().zzk().zzc("[sgtm] queued Google Signal batch updated. appId, signalRowId", str, Long.valueOf(zzafVar.zzc));
                zzP(str);
            } catch (SQLiteException e) {
                zzavVarZzj4.zzu.zzaV().zzb().zzd("Failed to update google Signal pending batch. appid, rowId", str, Long.valueOf(j2), e);
                throw e;
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzar(boolean z) {
        zzaK();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzas(String str, zzlt zzltVar) {
        zzaW().zzg();
        String str2 = this.zzH;
        if (str2 == null || str2.equals(str) || zzltVar != null) {
            this.zzH = str;
            this.zzG = zzltVar;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzat(zzpg zzpgVar) {
        zzaW().zzg();
        this.zzm = new zzhj(this);
        zzav zzavVar = new zzav(this);
        zzavVar.zzaz();
        this.zze = zzavVar;
        zzd().zza((zzak) Preconditions.checkNotNull(this.zzc));
        zznm zznmVar = new zznm(this);
        zznmVar.zzaz();
        this.zzk = zznmVar;
        zzad zzadVar = new zzad(this);
        zzadVar.zzaz();
        this.zzh = zzadVar;
        zzlo zzloVar = new zzlo(this);
        zzloVar.zzaz();
        this.zzj = zzloVar;
        zzoj zzojVar = new zzoj(this);
        zzojVar.zzaz();
        this.zzg = zzojVar;
        this.zzf = new zzha(this);
        if (this.zzs != this.zzt) {
            zzaV().zzb().zzc("Not all upload components initialized", Integer.valueOf(this.zzs), Integer.valueOf(this.zzt));
        }
        this.zzo.set(true);
        zzaV().zzk().zza("UploadController is now fully initialized");
    }

    final /* synthetic */ zzib zzaw() {
        return this.zzn;
    }

    final /* synthetic */ Deque zzax() {
        return this.zzr;
    }

    final /* synthetic */ void zzay(long j) {
        this.zzJ = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzc() {
        zzaW().zzg();
        zzj().zzI();
        zzav zzavVarZzj = zzj();
        zzavVarZzj.zzg();
        zzavVarZzj.zzay();
        if (zzavVarZzj.zzai()) {
            zzfw zzfwVar = zzfx.zzav;
            if (((Long) zzfwVar.zzb(null)).longValue() != 0) {
                SQLiteDatabase sQLiteDatabaseZze = zzavVarZzj.zze();
                zzib zzibVar = zzavVarZzj.zzu;
                int iDelete = sQLiteDatabaseZze.delete("trigger_uris", "abs(timestamp_millis - ?) > cast(? as integer)", new String[]{String.valueOf(zzibVar.zzaZ().currentTimeMillis()), String.valueOf(zzfwVar.zzb(null))});
                if (iDelete > 0) {
                    zzibVar.zzaV().zzk().zzb("Deleted stale trigger uris. rowsDeleted", Integer.valueOf(iDelete));
                }
            }
        }
        if (this.zzk.zzd.zza() == 0) {
            this.zzk.zzd.zzb(zzaZ().currentTimeMillis());
        }
        zzaK();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzal zzd() {
        return ((zzib) Preconditions.checkNotNull(this.zzn)).zzc();
    }

    public final zzot zzf() {
        return this.zzl;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzhs zzh() {
        zzhs zzhsVar = this.zzc;
        zzaS(zzhsVar);
        return zzhsVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzgy zzi() {
        zzgy zzgyVar = this.zzd;
        zzaS(zzgyVar);
        return zzgyVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzav zzj() {
        zzav zzavVar = this.zze;
        zzaS(zzavVar);
        return zzavVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzha zzk() {
        zzha zzhaVar = this.zzf;
        if (zzhaVar != null) {
            return zzhaVar;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzoj zzl() {
        zzoj zzojVar = this.zzg;
        zzaS(zzojVar);
        return zzojVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzad zzm() {
        zzad zzadVar = this.zzh;
        zzaS(zzadVar);
        return zzadVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzlo zzn() {
        zzlo zzloVar = this.zzj;
        zzaS(zzloVar);
        return zzloVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzpj zzp() {
        zzpj zzpjVar = this.zzi;
        zzaS(zzpjVar);
        return zzpjVar;
    }

    public final zznm zzq() {
        return this.zzk;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzgm zzs() {
        return this.zzn.zzl();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzpo zzt() {
        return ((zzib) Preconditions.checkNotNull(this.zzn)).zzk();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzu() {
        if (!this.zzo.get()) {
            throw new IllegalStateException("UploadController is not initialized");
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzv(zzr zzrVar) {
        zzaW().zzg();
        zzu();
        String str = zzrVar.zza;
        Preconditions.checkNotEmpty(str);
        zzjk zzjkVarZzf = zzjk.zzf(zzrVar.zzs, zzrVar.zzx);
        zzB(str);
        zzaV().zzk().zzc("Setting storage consent for package", str, zzjkVarZzf);
        zzA(str, zzjkVarZzf);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzw(zzr zzrVar) {
        zzaW().zzg();
        zzu();
        String str = zzrVar.zza;
        Preconditions.checkNotEmpty(str);
        zzaz zzazVarZzg = zzaz.zzg(zzrVar.zzy);
        zzaV().zzk().zzc("Setting DMA consent for package", str, zzazVarZzg);
        zzaW().zzg();
        zzu();
        zzjh zzjhVarZzc = zzaz.zzh(zzy(str), 100).zzc();
        this.zzD.put(str, zzazVarZzg);
        zzj().zzad(str, zzazVarZzg);
        zzjh zzjhVarZzc2 = zzaz.zzh(zzy(str), 100).zzc();
        zzaW().zzg();
        zzu();
        zzjh zzjhVar = zzjh.DENIED;
        boolean z = zzjhVarZzc == zzjhVar && zzjhVarZzc2 == zzjh.GRANTED;
        boolean z2 = zzjhVarZzc == zzjh.GRANTED && zzjhVarZzc2 == zzjhVar;
        if (z || z2) {
            zzaV().zzk().zzb("Generated _dcu event for", str);
            Bundle bundle = new Bundle();
            if (zzj().zzw(zzC(), str, false, false, false, false, false, false, false).zzf < zzd().zzm(str, zzfx.zzam)) {
                bundle.putLong("_r", 1L);
                zzaV().zzk().zzc("_dcu realtime event count", str, Long.valueOf(zzj().zzw(zzC(), str, false, false, false, false, false, true, false).zzf));
            }
            this.zzK.zza(str, "_dcu", bundle);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final zzaz zzx(String str) {
        zzaW().zzg();
        zzu();
        Map map = this.zzD;
        zzaz zzazVar = (zzaz) map.get(str);
        if (zzazVar != null) {
            return zzazVar;
        }
        zzaz zzazVarZzac = zzj().zzac(str);
        map.put(str, zzazVarZzac);
        return zzazVarZzac;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    final Bundle zzy(String str) {
        zzaW().zzg();
        zzu();
        if (zzh().zzx(str) == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        zzjk zzjkVarZzB = zzB(str);
        bundle.putAll(zzjkVarZzB.zzn());
        bundle.putAll(zzz(str, zzx(str), zzjkVarZzB, new zzan()).zzf());
        zzpm zzpmVarZzm = zzj().zzm(str, "_npa");
        bundle.putString("ad_personalization", 1 != (zzpmVarZzm != null ? zzpmVarZzm.zze.equals(1L) : zzaB(str, new zzan())) ? "granted" : "denied");
        return bundle;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final zzaz zzz(String str, zzaz zzazVar, zzjk zzjkVar, zzan zzanVar) {
        zzjh zzjhVar;
        int iZzb = 90;
        if (zzh().zzx(str) == null) {
            if (zzazVar.zzc() == zzjh.DENIED) {
                iZzb = zzazVar.zzb();
                zzanVar.zzb(zzjj.AD_USER_DATA, iZzb);
            } else {
                zzanVar.zzc(zzjj.AD_USER_DATA, zzam.FAILSAFE);
            }
            return new zzaz((Boolean) false, iZzb, (Boolean) true, "-");
        }
        zzjh zzjhVarZzc = zzazVar.zzc();
        zzjh zzjhVar2 = zzjh.GRANTED;
        if (zzjhVarZzc == zzjhVar2 || zzjhVarZzc == (zzjhVar = zzjh.DENIED)) {
            iZzb = zzazVar.zzb();
            zzanVar.zzb(zzjj.AD_USER_DATA, iZzb);
        } else if (zzjhVarZzc == zzjh.POLICY) {
            zzhs zzhsVar = this.zzc;
            zzjj zzjjVar = zzjj.AD_USER_DATA;
            zzjh zzjhVarZzA = zzhsVar.zzA(str, zzjjVar);
            if (zzjhVarZzA != zzjh.UNINITIALIZED) {
                zzanVar.zzc(zzjjVar, zzam.REMOTE_ENFORCED_DEFAULT);
                zzjhVarZzc = zzjhVarZzA;
            } else {
                zzhs zzhsVar2 = this.zzc;
                zzjj zzjjVar2 = zzjj.AD_USER_DATA;
                zzjj zzjjVarZzw = zzhsVar2.zzw(str, zzjjVar2);
                zzjh zzjhVarZzp = zzjkVar.zzp();
                boolean z = zzjhVarZzp == zzjhVar2 || zzjhVarZzp == zzjhVar;
                if (zzjjVarZzw == zzjj.AD_STORAGE && z) {
                    zzanVar.zzc(zzjjVar2, zzam.REMOTE_DELEGATION);
                    zzjhVarZzc = zzjhVarZzp;
                } else {
                    zzanVar.zzc(zzjjVar2, zzam.REMOTE_DEFAULT);
                    zzjhVarZzc = true != zzhsVar2.zzv(str, zzjjVar2) ? zzjhVar : zzjhVar2;
                }
            }
        }
        boolean zZzy = this.zzc.zzy(str);
        SortedSet sortedSetZzz = zzh().zzz(str);
        if (zzjhVarZzc == zzjh.DENIED || sortedSetZzz.isEmpty()) {
            return new zzaz((Boolean) false, iZzb, Boolean.valueOf(zZzy), "-");
        }
        return new zzaz((Boolean) true, iZzb, Boolean.valueOf(zZzy), zZzy ? TextUtils.join("", sortedSetZzz) : "");
    }
}

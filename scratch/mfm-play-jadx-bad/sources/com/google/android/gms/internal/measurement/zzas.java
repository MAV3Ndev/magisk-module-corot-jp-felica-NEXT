package com.google.android.gms.internal.measurement;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzas implements Iterable, zzao {
    private final String zza;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public zzas(String str) {
        if (str == null) {
            throw new IllegalArgumentException("StringValue cannot be null.");
        }
        this.zza = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzas) {
            return this.zza.equals(((zzas) obj).zza);
        }
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int hashCode() {
        return this.zza.hashCode();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new zzar(this);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String toString() {
        String str = this.zza;
        StringBuilder sb = new StringBuilder(str.length() + 2);
        sb.append("\"");
        sb.append(str);
        sb.append("\"");
        return sb.toString();
    }

    final /* synthetic */ String zzb() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzao
    public final String zzc() {
        return this.zza;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:264:? */
    /* JADX DEBUG: Multi-variable search result rejected for r14v10, resolved type: int */
    /* JADX DEBUG: Multi-variable search result rejected for r14v11, resolved type: int */
    /* JADX DEBUG: Multi-variable search result rejected for r14v12, resolved type: int */
    /* JADX DEBUG: Multi-variable search result rejected for r14v13, resolved type: int */
    /* JADX DEBUG: Multi-variable search result rejected for r14v16, resolved type: int */
    /* JADX DEBUG: Multi-variable search result rejected for r14v17, resolved type: int */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x03ba  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0405  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x04b1  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x04ff  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x055d  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x05b4  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x05f9  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0632  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0158  */
    @Override // com.google.android.gms.internal.measurement.zzao
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final zzao zzcA(String str, zzg zzgVar, List list) {
        String str2;
        int i;
        String str3;
        String str4;
        String str5;
        int i2;
        zzas zzasVar;
        int i3;
        String strZzc;
        int i4;
        int i5;
        zzg zzgVar2;
        int iZzi;
        if (!"charAt".equals(str) && !"concat".equals(str) && !"hasOwnProperty".equals(str) && !"indexOf".equals(str) && !"lastIndexOf".equals(str) && !"match".equals(str) && !"replace".equals(str) && !FirebaseAnalytics.Event.SEARCH.equals(str) && !"slice".equals(str) && !"split".equals(str) && !"substring".equals(str) && !"toLowerCase".equals(str) && !"toLocaleLowerCase".equals(str) && !"toString".equals(str) && !"toUpperCase".equals(str)) {
            str2 = "toLocaleUpperCase";
            i = 0;
            if (!str2.equals(str)) {
                str3 = "hasOwnProperty";
                if (!"trim".equals(str)) {
                    throw new IllegalArgumentException(String.format("%s is not a String function", str));
                }
            }
            switch (str.hashCode()) {
                case -1789698943:
                    str4 = "charAt";
                    str5 = str3;
                    i2 = str.equals(str5) ? 2 : -1;
                    break;
                case -1776922004:
                    str4 = "charAt";
                    if (str.equals("toString")) {
                        i2 = 14;
                        str5 = str3;
                    }
                    str5 = str3;
                    break;
                case -1464939364:
                    str4 = "charAt";
                    if (str.equals("toLocaleLowerCase")) {
                        i2 = 12;
                        str5 = str3;
                    }
                    str5 = str3;
                    break;
                case -1361633751:
                    str4 = "charAt";
                    if (str.equals(str4)) {
                        i2 = i;
                        str5 = str3;
                    }
                    str5 = str3;
                    break;
                case -1354795244:
                    if (str.equals("concat")) {
                        str4 = "charAt";
                        str5 = str3;
                        i2 = 1;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                case -1137582698:
                    if (str.equals("toLowerCase")) {
                        i2 = 13;
                        str4 = "charAt";
                        str5 = str3;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                case -906336856:
                    if (str.equals(FirebaseAnalytics.Event.SEARCH)) {
                        i2 = 7;
                        str4 = "charAt";
                        str5 = str3;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                case -726908483:
                    if (str.equals(str2)) {
                        i2 = 11;
                        str4 = "charAt";
                        str5 = str3;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                case -467511597:
                    if (str.equals("lastIndexOf")) {
                        i2 = 4;
                        str4 = "charAt";
                        str5 = str3;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                case -399551817:
                    if (str.equals("toUpperCase")) {
                        i2 = 15;
                        str4 = "charAt";
                        str5 = str3;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                case 3568674:
                    if (str.equals("trim")) {
                        i2 = 16;
                        str4 = "charAt";
                        str5 = str3;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                case 103668165:
                    if (str.equals("match")) {
                        i2 = 5;
                        str4 = "charAt";
                        str5 = str3;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                case 109526418:
                    if (str.equals("slice")) {
                        i2 = 8;
                        str4 = "charAt";
                        str5 = str3;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                case 109648666:
                    if (str.equals("split")) {
                        i2 = 9;
                        str4 = "charAt";
                        str5 = str3;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                case 530542161:
                    if (str.equals("substring")) {
                        i2 = 10;
                        str4 = "charAt";
                        str5 = str3;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                case 1094496948:
                    if (str.equals("replace")) {
                        i2 = 6;
                        str4 = "charAt";
                        str5 = str3;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                case 1943291465:
                    if (str.equals("indexOf")) {
                        i2 = 3;
                        str4 = "charAt";
                        str5 = str3;
                    } else {
                        str4 = "charAt";
                        str5 = str3;
                    }
                    break;
                default:
                    str4 = "charAt";
                    str5 = str3;
                    break;
            }
            String str6 = str4;
            switch (i2) {
                case 0:
                    zzh.zzc(str6, 1, list);
                    int iZzi2 = !list.isEmpty() ? (int) zzh.zzi(zzgVar.zza((zzao) list.get(0)).zzd().doubleValue()) : 0;
                    String str7 = this.zza;
                    return (iZzi2 < 0 || iZzi2 >= str7.length()) ? zzao.zzm : new zzas(String.valueOf(str7.charAt(iZzi2)));
                case 1:
                    zzasVar = this;
                    if (!list.isEmpty()) {
                        StringBuilder sb = new StringBuilder(zzasVar.zza);
                        for (int i6 = 0; i6 < list.size(); i6++) {
                            sb.append(zzgVar.zza((zzao) list.get(i6)).zzc());
                        }
                        return new zzas(sb.toString());
                    }
                    return zzasVar;
                case 2:
                    zzh.zza(str5, 1, list);
                    String str8 = this.zza;
                    zzao zzaoVarZza = zzgVar.zza((zzao) list.get(0));
                    if ("length".equals(zzaoVarZza.zzc())) {
                        return zzaf.zzk;
                    }
                    double dDoubleValue = zzaoVarZza.zzd().doubleValue();
                    return (dDoubleValue != Math.floor(dDoubleValue) || (i3 = (int) dDoubleValue) < 0 || i3 >= str8.length()) ? zzaf.zzl : zzaf.zzk;
                case 3:
                    zzh.zzc("indexOf", 2, list);
                    return new zzah(Double.valueOf(this.zza.indexOf(list.size() > 0 ? zzgVar.zza((zzao) list.get(0)).zzc() : "undefined", (int) zzh.zzi(list.size() < 2 ? 0.0d : zzgVar.zza((zzao) list.get(1)).zzd().doubleValue()))));
                case 4:
                    int i7 = i;
                    zzh.zzc("lastIndexOf", 2, list);
                    String str9 = this.zza;
                    String strZzc2 = list.size() > 0 ? zzgVar.zza((zzao) list.get(i7)).zzc() : "undefined";
                    return new zzah(Double.valueOf(str9.lastIndexOf(strZzc2, (int) (Double.isNaN(list.size() < 2 ? Double.NaN : zzgVar.zza((zzao) list.get(1)).zzd().doubleValue()) ? Double.POSITIVE_INFINITY : zzh.zzi(r0)))));
                case 5:
                    zzh.zzc("match", 1, list);
                    Matcher matcher = Pattern.compile(list.size() <= 0 ? "" : zzgVar.zza((zzao) list.get(0)).zzc()).matcher(this.zza);
                    return matcher.find() ? new zzae(Arrays.asList(new zzas(matcher.group()))) : zzao.zzg;
                case 6:
                    zzasVar = this;
                    zzh.zzc("replace", 2, list);
                    zzao zzaoVarZza2 = zzao.zzf;
                    if (!list.isEmpty()) {
                        strZzc = zzgVar.zza((zzao) list.get(0)).zzc();
                        if (list.size() > 1) {
                            zzaoVarZza2 = zzgVar.zza((zzao) list.get(1));
                        }
                    }
                    String str10 = strZzc;
                    String str11 = zzasVar.zza;
                    int iIndexOf = str11.indexOf(str10);
                    if (iIndexOf >= 0) {
                        if (zzaoVarZza2 instanceof zzai) {
                            zzaoVarZza2 = ((zzai) zzaoVarZza2).zza(zzgVar, Arrays.asList(new zzas(str10), new zzah(Double.valueOf(iIndexOf)), zzasVar));
                        }
                        String strSubstring = str11.substring(0, iIndexOf);
                        String strZzc3 = zzaoVarZza2.zzc();
                        String strSubstring2 = str11.substring(iIndexOf + str10.length());
                        StringBuilder sb2 = new StringBuilder(String.valueOf(strSubstring).length() + String.valueOf(strZzc3).length() + String.valueOf(strSubstring2).length());
                        sb2.append(strSubstring);
                        sb2.append(strZzc3);
                        sb2.append(strSubstring2);
                        return new zzas(sb2.toString());
                    }
                    return zzasVar;
                case 7:
                    int i8 = i;
                    zzh.zzc(FirebaseAnalytics.Event.SEARCH, 1, list);
                    return Pattern.compile(list.isEmpty() ? "undefined" : zzgVar.zza((zzao) list.get(i8)).zzc()).matcher(this.zza).find() ? new zzah(Double.valueOf(r0.start())) : new zzah(Double.valueOf(-1.0d));
                case 8:
                    zzh.zzc("slice", 2, list);
                    String str12 = this.zza;
                    double dZzi = zzh.zzi(!list.isEmpty() ? zzgVar.zza((zzao) list.get(0)).zzd().doubleValue() : 0.0d);
                    double dMax = dZzi < 0.0d ? Math.max(((double) str12.length()) + dZzi, 0.0d) : Math.min(dZzi, str12.length());
                    double dZzi2 = zzh.zzi(list.size() > 1 ? zzgVar.zza((zzao) list.get(1)).zzd().doubleValue() : str12.length());
                    int i9 = (int) dMax;
                    return new zzas(str12.substring(i9, Math.max(0, ((int) (dZzi2 < 0.0d ? Math.max(((double) str12.length()) + dZzi2, 0.0d) : Math.min(dZzi2, str12.length()))) - i9) + i9));
                case 9:
                    zzh.zzc("split", 2, list);
                    String str13 = this.zza;
                    if (str13.length() == 0) {
                        return new zzae(Arrays.asList(this));
                    }
                    ArrayList arrayList = new ArrayList();
                    if (list.isEmpty()) {
                        arrayList.add(this);
                    } else {
                        String strZzc4 = zzgVar.zza((zzao) list.get(0)).zzc();
                        long jZzh = list.size() > 1 ? zzh.zzh(zzgVar.zza((zzao) list.get(1)).zzd().doubleValue()) : 2147483647L;
                        if (jZzh == 0) {
                            return new zzae();
                        }
                        String[] strArrSplit = str13.split(Pattern.quote(strZzc4), ((int) jZzh) + 1);
                        int length = strArrSplit.length;
                        if (!strZzc4.isEmpty() || length <= 0) {
                            i4 = length;
                            i5 = 0;
                        } else {
                            boolean zIsEmpty = strArrSplit[0].isEmpty();
                            i4 = length - 1;
                            i5 = zIsEmpty;
                            if (!strArrSplit[i4].isEmpty()) {
                                i4 = length;
                                i5 = zIsEmpty;
                            }
                        }
                        if (length > jZzh) {
                            i4--;
                        }
                        while (i5 < i4) {
                            arrayList.add(new zzas(strArrSplit[i5]));
                            i5++;
                        }
                    }
                    return new zzae(arrayList);
                case 10:
                    int i10 = i;
                    zzh.zzc("substring", 2, list);
                    String str14 = this.zza;
                    if (list.isEmpty()) {
                        zzgVar2 = zzgVar;
                        iZzi = 0;
                    } else {
                        zzgVar2 = zzgVar;
                        iZzi = (int) zzh.zzi(zzgVar2.zza((zzao) list.get(i10)).zzd().doubleValue());
                    }
                    int iZzi3 = list.size() > 1 ? (int) zzh.zzi(zzgVar2.zza((zzao) list.get(1)).zzd().doubleValue()) : str14.length();
                    int iMin = Math.min(Math.max(iZzi, 0), str14.length());
                    int iMin2 = Math.min(Math.max(iZzi3, 0), str14.length());
                    return new zzas(str14.substring(Math.min(iMin, iMin2), Math.max(iMin, iMin2)));
                case 11:
                    zzh.zza(str2, i, list);
                    return new zzas(this.zza.toUpperCase());
                case 12:
                    zzh.zza("toLocaleLowerCase", i, list);
                    return new zzas(this.zza.toLowerCase());
                case 13:
                    zzh.zza("toLowerCase", i, list);
                    return new zzas(this.zza.toLowerCase(Locale.ENGLISH));
                case 14:
                    zzh.zza("toString", i, list);
                    return this;
                case 15:
                    zzh.zza("toUpperCase", i, list);
                    return new zzas(this.zza.toUpperCase(Locale.ENGLISH));
                case 16:
                    zzh.zza("toUpperCase", i, list);
                    return new zzas(this.zza.trim());
                default:
                    throw new IllegalArgumentException("Command not supported");
            }
        }
        str2 = "toLocaleUpperCase";
        i = 0;
        str3 = "hasOwnProperty";
        switch (str.hashCode()) {
            case -1789698943:
                break;
            case -1776922004:
                break;
            case -1464939364:
                break;
            case -1361633751:
                break;
            case -1354795244:
                break;
            case -1137582698:
                break;
            case -906336856:
                break;
            case -726908483:
                break;
            case -467511597:
                break;
            case -399551817:
                break;
            case 3568674:
                break;
            case 103668165:
                break;
            case 109526418:
                break;
            case 109648666:
                break;
            case 530542161:
                break;
            case 1094496948:
                break;
            case 1943291465:
                break;
        }
        String str62 = str4;
        switch (i2) {
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzao
    public final Double zzd() {
        String str = this.zza;
        if (str.isEmpty()) {
            return Double.valueOf(0.0d);
        }
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException unused) {
            return Double.valueOf(Double.NaN);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzao
    public final Boolean zze() {
        return Boolean.valueOf(!this.zza.isEmpty());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzao
    public final Iterator zzf() {
        return new zzaq(this);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzao
    public final zzao zzt() {
        return new zzas(this.zza);
    }
}

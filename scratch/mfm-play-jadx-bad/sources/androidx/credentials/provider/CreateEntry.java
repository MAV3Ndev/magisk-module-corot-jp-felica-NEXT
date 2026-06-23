package androidx.credentials.provider;

import android.app.PendingIntent;
import android.app.slice.Slice;
import android.app.slice.SliceItem;
import android.app.slice.SliceSpec;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.credentials.PasswordCredential;
import androidx.credentials.PublicKeyCredential;
import androidx.credentials.provider.utils.CryptoObjectUtils;
import androidx.emoji2.text.flatbuffer.Utf8Old$$ExternalSyntheticApiModelOutline0;
import com.google.android.gms.common.internal.BaseGmsClient;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

/* JADX INFO: compiled from: CreateEntry.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0017\b\u0007\u0018\u0000 -2\u00020\u0001:\u0005)*+,-Bi\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\f\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011Bu\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\f\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\u0002\u0010\u0014B_\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0014\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0017\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0016\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\u0002\u0010\u0018J\r\u0010%\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010&J\r\u0010'\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010&J\r\u0010(\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001c\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\u0017\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001aR\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010 R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$¨\u0006."}, d2 = {"Landroidx/credentials/provider/CreateEntry;", "", "accountName", "", BaseGmsClient.KEY_PENDING_INTENT, "Landroid/app/PendingIntent;", "description", "lastUsedTime", "Ljava/time/Instant;", "icon", "Landroid/graphics/drawable/Icon;", "passwordCredentialCount", "", "publicKeyCredentialCount", "totalCredentialCount", "isAutoSelectAllowed", "", "(Ljava/lang/CharSequence;Landroid/app/PendingIntent;Ljava/lang/CharSequence;Ljava/time/Instant;Landroid/graphics/drawable/Icon;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Z)V", "biometricPromptData", "Landroidx/credentials/provider/BiometricPromptData;", "(Ljava/lang/CharSequence;Landroid/app/PendingIntent;Ljava/lang/CharSequence;Ljava/time/Instant;Landroid/graphics/drawable/Icon;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;ZLandroidx/credentials/provider/BiometricPromptData;)V", "credentialCountInformationMap", "", "", "(Ljava/lang/CharSequence;Landroid/app/PendingIntent;Landroid/graphics/drawable/Icon;Ljava/lang/CharSequence;Ljava/time/Instant;Ljava/util/Map;ZLandroidx/credentials/provider/BiometricPromptData;)V", "getAccountName", "()Ljava/lang/CharSequence;", "getBiometricPromptData", "()Landroidx/credentials/provider/BiometricPromptData;", "getDescription", "getIcon", "()Landroid/graphics/drawable/Icon;", "()Z", "getLastUsedTime", "()Ljava/time/Instant;", "getPendingIntent", "()Landroid/app/PendingIntent;", "getPasswordCredentialCount", "()Ljava/lang/Integer;", "getPublicKeyCredentialCount", "getTotalCredentialCount", "Api28Impl", "Api34Impl", "Api35Impl", "Builder", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class CreateEntry {
    private static final String AUTO_SELECT_FALSE_STRING = "false";
    private static final String AUTO_SELECT_TRUE_STRING = "true";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int DESCRIPTION_MAX_CHAR_LIMIT = 300;
    private static final String EXTRA_CREATE_ACCOUNT_NAME_PREFIX = "androidx.credentials.provider.extra.ACCOUNT_NAME_";
    private static final String EXTRA_CREATE_CREDENTIAL_COUNT_INFO_PREFIX = "androidx.credentials.provider.extra.CREDENTIAL_COUNT_INFO_";
    private static final String EXTRA_CREATE_DESCRIPTION_PREFIX = "androidx.credentials.provider.extra.DESCRIPTION_";
    private static final String EXTRA_CREATE_ENTRY_IS_AUTO_SELECT_ALLOWED_PREFIX = "androidx.credentials.provider.extra.IS_AUTO_SELECT_ALLOWED_";
    private static final String EXTRA_CREATE_ENTRY_LAST_USED_TIME_PREFIX = "androidx.credentials.provider.extra.LAST_USED_TIME_";
    private static final String EXTRA_CREATE_ENTRY_PENDING_INTENT_PREFIX = "androidx.credentials.provider.extra.PENDING_INTENT_";
    private static final String EXTRA_CREATE_ENTRY_SIZE = "androidx.credentials.provider.extra.CREATE_ENTRY_SIZE";
    private static final String EXTRA_CREATE_TYPE_ICON_PREFIX = "androidx.credentials.provider.extra.ICON_";
    private static final int REVISION_ID = 1;
    private static final String SLICE_HINT_ACCOUNT_NAME = "androidx.credentials.provider.createEntry.SLICE_HINT_USER_PROVIDER_ACCOUNT_NAME";
    private static final String SLICE_HINT_ALLOWED_AUTHENTICATORS = "androidx.credentials.provider.createEntry.SLICE_HINT_ALLOWED_AUTHENTICATORS";
    private static final String SLICE_HINT_AUTO_SELECT_ALLOWED = "androidx.credentials.provider.createEntry.SLICE_HINT_AUTO_SELECT_ALLOWED";
    private static final String SLICE_HINT_BIOMETRIC_PROMPT_DATA = "androidx.credentials.provider.createEntry.SLICE_HINT_BIOMETRIC_PROMPT_DATA";
    private static final String SLICE_HINT_CREDENTIAL_COUNT_INFORMATION = "androidx.credentials.provider.createEntry.SLICE_HINT_CREDENTIAL_COUNT_INFORMATION";
    private static final String SLICE_HINT_CRYPTO_OP_ID = "androidx.credentials.provider.createEntry.SLICE_HINT_CRYPTO_OP_ID";
    private static final String SLICE_HINT_ICON = "androidx.credentials.provider.createEntry.SLICE_HINT_PROFILE_ICON";
    private static final String SLICE_HINT_LAST_USED_TIME_MILLIS = "androidx.credentials.provider.createEntry.SLICE_HINT_LAST_USED_TIME_MILLIS";
    private static final String SLICE_HINT_NOTE = "androidx.credentials.provider.createEntry.SLICE_HINT_NOTE";
    private static final String SLICE_HINT_PENDING_INTENT = "androidx.credentials.provider.createEntry.SLICE_HINT_PENDING_INTENT";
    private static final String SLICE_SPEC_TYPE = "CreateEntry";
    private static final String TAG = "CreateEntry";
    public static final String TYPE_TOTAL_CREDENTIAL = "TOTAL_CREDENTIAL_COUNT_TYPE";
    private final CharSequence accountName;
    private final BiometricPromptData biometricPromptData;
    private final Map<String, Integer> credentialCountInformationMap;
    private final CharSequence description;
    private final Icon icon;
    private final boolean isAutoSelectAllowed;
    private final Instant lastUsedTime;
    private final PendingIntent pendingIntent;

    @JvmStatic
    public static final CreateEntry fromCreateEntry(android.service.credentials.CreateEntry createEntry) {
        return INSTANCE.fromCreateEntry(createEntry);
    }

    @JvmStatic
    public static final CreateEntry fromSlice(Slice slice) {
        return INSTANCE.fromSlice(slice);
    }

    @JvmStatic
    public static final Slice toSlice(CreateEntry createEntry) {
        return INSTANCE.toSlice(createEntry);
    }

    public CreateEntry(CharSequence accountName, PendingIntent pendingIntent, Icon icon, CharSequence charSequence, Instant instant, Map<String, Integer> credentialCountInformationMap, boolean z, BiometricPromptData biometricPromptData) {
        Intrinsics.checkNotNullParameter(accountName, "accountName");
        Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
        Intrinsics.checkNotNullParameter(credentialCountInformationMap, "credentialCountInformationMap");
        this.accountName = accountName;
        this.pendingIntent = pendingIntent;
        this.icon = icon;
        this.description = charSequence;
        this.lastUsedTime = instant;
        this.credentialCountInformationMap = credentialCountInformationMap;
        this.isAutoSelectAllowed = z;
        this.biometricPromptData = biometricPromptData;
        if (accountName.length() <= 0) {
            throw new IllegalArgumentException("accountName must not be empty".toString());
        }
        if (charSequence != null && charSequence.length() > 300) {
            throw new IllegalArgumentException("Description must follow a limit of 300 characters.".toString());
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0015: CONSTRUCTOR 
  (r11v0 java.lang.CharSequence)
  (r12v0 android.app.PendingIntent)
  (r13v0 android.graphics.drawable.Icon)
  (r14v0 java.lang.CharSequence)
  (r15v0 java.time.Instant)
  (r16v0 java.util.Map)
  (r17v0 boolean)
  (wrap:androidx.credentials.provider.BiometricPromptData:?: TERNARY null = ((wrap:int:0x0002: ARITH (r19v0 int) & (128 int) A[WRAPPED]) != (0 int)) ? (null androidx.credentials.provider.BiometricPromptData) : (r18v0 androidx.credentials.provider.BiometricPromptData))
 A[MD:(java.lang.CharSequence, android.app.PendingIntent, android.graphics.drawable.Icon, java.lang.CharSequence, java.time.Instant, java.util.Map<java.lang.String, java.lang.Integer>, boolean, androidx.credentials.provider.BiometricPromptData):void (m)] (LINE:69) call: androidx.credentials.provider.CreateEntry.<init>(java.lang.CharSequence, android.app.PendingIntent, android.graphics.drawable.Icon, java.lang.CharSequence, java.time.Instant, java.util.Map, boolean, androidx.credentials.provider.BiometricPromptData):void type: THIS */
    public /* synthetic */ CreateEntry(CharSequence charSequence, PendingIntent pendingIntent, Icon icon, CharSequence charSequence2, Instant instant, Map map, boolean z, BiometricPromptData biometricPromptData, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, pendingIntent, icon, charSequence2, instant, map, z, (i & 128) != 0 ? null : biometricPromptData);
    }

    public final CharSequence getAccountName() {
        return this.accountName;
    }

    public final PendingIntent getPendingIntent() {
        return this.pendingIntent;
    }

    public final Icon getIcon() {
        return this.icon;
    }

    public final CharSequence getDescription() {
        return this.description;
    }

    public final Instant getLastUsedTime() {
        return this.lastUsedTime;
    }

    /* JADX INFO: renamed from: isAutoSelectAllowed, reason: from getter */
    public final boolean getIsAutoSelectAllowed() {
        return this.isAutoSelectAllowed;
    }

    public final BiometricPromptData getBiometricPromptData() {
        return this.biometricPromptData;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x002e: CONSTRUCTOR 
  (r2v0 java.lang.CharSequence)
  (r3v0 android.app.PendingIntent)
  (wrap:java.lang.CharSequence:?: TERNARY null = ((wrap:int:0x0000: ARITH (r11v0 int) & (4 int) A[WRAPPED]) != (0 int)) ? (null java.lang.CharSequence) : (r4v0 java.lang.CharSequence))
  (wrap:java.time.Instant:?: TERNARY null = ((wrap:int:0x0006: ARITH (r11v0 int) & (8 int) A[WRAPPED]) != (0 int)) ? (null java.time.Instant) : (r5v0 java.time.Instant))
  (wrap:android.graphics.drawable.Icon:?: TERNARY null = ((wrap:int:0x000b: ARITH (r11v0 int) & (16 int) A[WRAPPED]) != (0 int)) ? (null android.graphics.drawable.Icon) : (r6v0 android.graphics.drawable.Icon))
  (wrap:java.lang.Integer:?: TERNARY null = ((wrap:int:0x0010: ARITH (r11v0 int) & (32 int) A[WRAPPED]) != (0 int)) ? (null java.lang.Integer) : (r7v0 java.lang.Integer))
  (wrap:java.lang.Integer:?: TERNARY null = ((wrap:int:0x0015: ARITH (r11v0 int) & (64 int) A[WRAPPED]) != (0 int)) ? (null java.lang.Integer) : (r8v0 java.lang.Integer))
  (wrap:java.lang.Integer:?: TERNARY null = ((wrap:int:0x001a: ARITH (r11v0 int) & (128 int) A[WRAPPED]) != (0 int)) ? (null java.lang.Integer) : (r9v0 java.lang.Integer))
  (wrap:boolean:?: TERNARY null = ((wrap:int:0x001f: ARITH (r11v0 int) & (256 int) A[WRAPPED]) != (0 int)) ? false : (r10v0 boolean))
 A[MD:(java.lang.CharSequence, android.app.PendingIntent, java.lang.CharSequence, java.time.Instant, android.graphics.drawable.Icon, java.lang.Integer, java.lang.Integer, java.lang.Integer, boolean):void (m)] (LINE:106) call: androidx.credentials.provider.CreateEntry.<init>(java.lang.CharSequence, android.app.PendingIntent, java.lang.CharSequence, java.time.Instant, android.graphics.drawable.Icon, java.lang.Integer, java.lang.Integer, java.lang.Integer, boolean):void type: THIS */
    public /* synthetic */ CreateEntry(CharSequence charSequence, PendingIntent pendingIntent, CharSequence charSequence2, Instant instant, Icon icon, Integer num, Integer num2, Integer num3, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, pendingIntent, (i & 4) != 0 ? null : charSequence2, (i & 8) != 0 ? null : instant, (i & 16) != 0 ? null : icon, (i & 32) != 0 ? null : num, (i & 64) != 0 ? null : num2, (i & 128) != 0 ? null : num3, (i & 256) != 0 ? false : z);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreateEntry(CharSequence accountName, PendingIntent pendingIntent, CharSequence charSequence, Instant instant, Icon icon, Integer num, Integer num2, Integer num3, boolean z) {
        this(accountName, pendingIntent, icon, charSequence, instant, MapsKt.mutableMapOf(TuplesKt.to(PasswordCredential.TYPE_PASSWORD_CREDENTIAL, num), TuplesKt.to(PublicKeyCredential.TYPE_PUBLIC_KEY_CREDENTIAL, num2), TuplesKt.to(TYPE_TOTAL_CREDENTIAL, num3)), z, (BiometricPromptData) null, 128, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(accountName, "accountName");
        Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0035: CONSTRUCTOR 
  (r2v0 java.lang.CharSequence)
  (r3v0 android.app.PendingIntent)
  (wrap:java.lang.CharSequence:?: TERNARY null = ((wrap:int:0x0000: ARITH (r12v0 int) & (4 int) A[WRAPPED]) != (0 int)) ? (null java.lang.CharSequence) : (r4v0 java.lang.CharSequence))
  (wrap:java.time.Instant:?: TERNARY null = ((wrap:int:0x0006: ARITH (r12v0 int) & (8 int) A[WRAPPED]) != (0 int)) ? (null java.time.Instant) : (r5v0 java.time.Instant))
  (wrap:android.graphics.drawable.Icon:?: TERNARY null = ((wrap:int:0x000b: ARITH (r12v0 int) & (16 int) A[WRAPPED]) != (0 int)) ? (null android.graphics.drawable.Icon) : (r6v0 android.graphics.drawable.Icon))
  (wrap:java.lang.Integer:?: TERNARY null = ((wrap:int:0x0010: ARITH (r12v0 int) & (32 int) A[WRAPPED]) != (0 int)) ? (null java.lang.Integer) : (r7v0 java.lang.Integer))
  (wrap:java.lang.Integer:?: TERNARY null = ((wrap:int:0x0015: ARITH (r12v0 int) & (64 int) A[WRAPPED]) != (0 int)) ? (null java.lang.Integer) : (r8v0 java.lang.Integer))
  (wrap:java.lang.Integer:?: TERNARY null = ((wrap:int:0x001a: ARITH (r12v0 int) & (128 int) A[WRAPPED]) != (0 int)) ? (null java.lang.Integer) : (r9v0 java.lang.Integer))
  (wrap:boolean:?: TERNARY null = ((wrap:int:0x001f: ARITH (r12v0 int) & (256 int) A[WRAPPED]) != (0 int)) ? false : (r10v0 boolean))
  (wrap:androidx.credentials.provider.BiometricPromptData:?: TERNARY null = ((wrap:int:0x0024: ARITH (r12v0 int) & (512 int) A[WRAPPED]) != (0 int)) ? (null androidx.credentials.provider.BiometricPromptData) : (r11v0 androidx.credentials.provider.BiometricPromptData))
 A[MD:(java.lang.CharSequence, android.app.PendingIntent, java.lang.CharSequence, java.time.Instant, android.graphics.drawable.Icon, java.lang.Integer, java.lang.Integer, java.lang.Integer, boolean, androidx.credentials.provider.BiometricPromptData):void (m)] (LINE:162) call: androidx.credentials.provider.CreateEntry.<init>(java.lang.CharSequence, android.app.PendingIntent, java.lang.CharSequence, java.time.Instant, android.graphics.drawable.Icon, java.lang.Integer, java.lang.Integer, java.lang.Integer, boolean, androidx.credentials.provider.BiometricPromptData):void type: THIS */
    public /* synthetic */ CreateEntry(CharSequence charSequence, PendingIntent pendingIntent, CharSequence charSequence2, Instant instant, Icon icon, Integer num, Integer num2, Integer num3, boolean z, BiometricPromptData biometricPromptData, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, pendingIntent, (i & 4) != 0 ? null : charSequence2, (i & 8) != 0 ? null : instant, (i & 16) != 0 ? null : icon, (i & 32) != 0 ? null : num, (i & 64) != 0 ? null : num2, (i & 128) != 0 ? null : num3, (i & 256) != 0 ? false : z, (i & 512) != 0 ? null : biometricPromptData);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreateEntry(CharSequence accountName, PendingIntent pendingIntent, CharSequence charSequence, Instant instant, Icon icon, Integer num, Integer num2, Integer num3, boolean z, BiometricPromptData biometricPromptData) {
        this(accountName, pendingIntent, icon, charSequence, instant, MapsKt.mutableMapOf(TuplesKt.to(PasswordCredential.TYPE_PASSWORD_CREDENTIAL, num), TuplesKt.to(PublicKeyCredential.TYPE_PUBLIC_KEY_CREDENTIAL, num2), TuplesKt.to(TYPE_TOTAL_CREDENTIAL, num3)), z, biometricPromptData);
        Intrinsics.checkNotNullParameter(accountName, "accountName");
        Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
    }

    public final Integer getPasswordCredentialCount() {
        return this.credentialCountInformationMap.get(PasswordCredential.TYPE_PASSWORD_CREDENTIAL);
    }

    public final Integer getPublicKeyCredentialCount() {
        return this.credentialCountInformationMap.get(PublicKeyCredential.TYPE_PUBLIC_KEY_CREDENTIAL);
    }

    public final Integer getTotalCredentialCount() {
        return this.credentialCountInformationMap.get(TYPE_TOTAL_CREDENTIAL);
    }

    /* JADX INFO: compiled from: CreateEntry.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bJ\u0012\u0010\u001b\u001a\u00020\u00002\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007J\u0010\u0010\u001c\u001a\u00020\u00002\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003J\u0010\u0010\u001d\u001a\u00020\u00002\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u0010\u0010\u001e\u001a\u00020\u00002\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013J\u000e\u0010\u001f\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u000eJ\u000e\u0010!\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u000eJ\u000e\u0010\"\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0015R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0015R\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0015¨\u0006#"}, d2 = {"Landroidx/credentials/provider/CreateEntry$Builder;", "", "accountName", "", BaseGmsClient.KEY_PENDING_INTENT, "Landroid/app/PendingIntent;", "(Ljava/lang/CharSequence;Landroid/app/PendingIntent;)V", "autoSelectAllowed", "", "biometricPromptData", "Landroidx/credentials/provider/BiometricPromptData;", "credentialCountInformationMap", "", "", "", "description", "icon", "Landroid/graphics/drawable/Icon;", "lastUsedTime", "Ljava/time/Instant;", "passwordCredentialCount", "Ljava/lang/Integer;", "publicKeyCredentialCount", "totalCredentialCount", "build", "Landroidx/credentials/provider/CreateEntry;", "setAutoSelectAllowed", "setBiometricPromptData", "setDescription", "setIcon", "setLastUsedTime", "setPasswordCredentialCount", "count", "setPublicKeyCredentialCount", "setTotalCredentialCount", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder {
        private final CharSequence accountName;
        private boolean autoSelectAllowed;
        private BiometricPromptData biometricPromptData;
        private Map<String, Integer> credentialCountInformationMap;
        private CharSequence description;
        private Icon icon;
        private Instant lastUsedTime;
        private Integer passwordCredentialCount;
        private final PendingIntent pendingIntent;
        private Integer publicKeyCredentialCount;
        private Integer totalCredentialCount;

        public Builder(CharSequence accountName, PendingIntent pendingIntent) {
            Intrinsics.checkNotNullParameter(accountName, "accountName");
            Intrinsics.checkNotNullParameter(pendingIntent, "pendingIntent");
            this.accountName = accountName;
            this.pendingIntent = pendingIntent;
            this.credentialCountInformationMap = new LinkedHashMap();
        }

        public final Builder setAutoSelectAllowed(boolean autoSelectAllowed) {
            this.autoSelectAllowed = autoSelectAllowed;
            return this;
        }

        public final Builder setPasswordCredentialCount(int count) {
            this.passwordCredentialCount = Integer.valueOf(count);
            this.credentialCountInformationMap.put(PasswordCredential.TYPE_PASSWORD_CREDENTIAL, Integer.valueOf(count));
            return this;
        }

        public final Builder setPublicKeyCredentialCount(int count) {
            this.publicKeyCredentialCount = Integer.valueOf(count);
            this.credentialCountInformationMap.put(PublicKeyCredential.TYPE_PUBLIC_KEY_CREDENTIAL, Integer.valueOf(count));
            return this;
        }

        public final Builder setTotalCredentialCount(int count) {
            this.totalCredentialCount = Integer.valueOf(count);
            this.credentialCountInformationMap.put(CreateEntry.TYPE_TOTAL_CREDENTIAL, Integer.valueOf(count));
            return this;
        }

        public final Builder setIcon(Icon icon) {
            this.icon = icon;
            return this;
        }

        public final Builder setDescription(CharSequence description) {
            if ((description != null ? Integer.valueOf(description.length()) : null) != null && description.length() > 300) {
                throw new IllegalArgumentException("Description must follow a limit of 300 characters.");
            }
            this.description = description;
            return this;
        }

        public final Builder setLastUsedTime(Instant lastUsedTime) {
            this.lastUsedTime = lastUsedTime;
            return this;
        }

        public final Builder setBiometricPromptData(BiometricPromptData biometricPromptData) {
            this.biometricPromptData = biometricPromptData;
            return this;
        }

        public final CreateEntry build() {
            return new CreateEntry(this.accountName, this.pendingIntent, this.icon, this.description, this.lastUsedTime, this.credentialCountInformationMap, this.autoSelectAllowed, this.biometricPromptData);
        }
    }

    /* JADX INFO: compiled from: CreateEntry.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Landroidx/credentials/provider/CreateEntry$Api34Impl;", "", "()V", "fromCreateEntry", "Landroidx/credentials/provider/CreateEntry;", "createEntry", "Landroid/service/credentials/CreateEntry;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Api34Impl {
        public static final Api34Impl INSTANCE = new Api34Impl();

        private Api34Impl() {
        }

        @JvmStatic
        public static final CreateEntry fromCreateEntry(android.service.credentials.CreateEntry createEntry) {
            Intrinsics.checkNotNullParameter(createEntry, "createEntry");
            Slice slice = createEntry.getSlice();
            Intrinsics.checkNotNullExpressionValue(slice, "createEntry.slice");
            return CreateEntry.INSTANCE.fromSlice(slice);
        }
    }

    /* JADX INFO: compiled from: CreateEntry.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\r"}, d2 = {"Landroidx/credentials/provider/CreateEntry$Api35Impl;", "", "()V", "addToSlice", "", "createEntry", "Landroidx/credentials/provider/CreateEntry;", "sliceBuilder", "Landroid/app/slice/Slice$Builder;", "fromSlice", "slice", "Landroid/app/slice/Slice;", "toSlice", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Api35Impl {
        public static final Api35Impl INSTANCE = new Api35Impl();

        private Api35Impl() {
        }

        private final void addToSlice(CreateEntry createEntry, Slice.Builder sliceBuilder) {
            BiometricPromptData biometricPromptData = createEntry.getBiometricPromptData();
            if (biometricPromptData != null) {
                sliceBuilder.addInt(biometricPromptData.getAllowedAuthenticators(), null, CollectionsKt.listOf(CreateEntry.SLICE_HINT_ALLOWED_AUTHENTICATORS));
                if (biometricPromptData.getCryptoObject() != null) {
                    sliceBuilder.addLong(CryptoObjectUtils.INSTANCE.getOperationHandle(biometricPromptData.getCryptoObject()), null, CollectionsKt.listOf(CreateEntry.SLICE_HINT_CRYPTO_OP_ID));
                }
                sliceBuilder.addBundle(BiometricPromptData.INSTANCE.toBundle(biometricPromptData), null, CollectionsKt.listOf(CreateEntry.SLICE_HINT_BIOMETRIC_PROMPT_DATA));
            }
        }

        @JvmStatic
        public static final Slice toSlice(CreateEntry createEntry) {
            Intrinsics.checkNotNullParameter(createEntry, "createEntry");
            Slice.Builder builderAddToSlice = Api28Impl.INSTANCE.addToSlice(createEntry);
            INSTANCE.addToSlice(createEntry, builderAddToSlice);
            Slice sliceBuild = builderAddToSlice.build();
            Intrinsics.checkNotNullExpressionValue(sliceBuild, "sliceBuilder.build()");
            return sliceBuild;
        }

        @JvmStatic
        public static final CreateEntry fromSlice(Slice slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            CreateEntry createEntryFromSlice = Api28Impl.fromSlice(slice);
            if (createEntryFromSlice == null) {
                return null;
            }
            List<SliceItem> items = slice.getItems();
            Intrinsics.checkNotNullExpressionValue(items, "slice.items");
            Bundle bundle = null;
            for (SliceItem sliceItem : items) {
                if (sliceItem.hasHint(CredentialEntry.SLICE_HINT_BIOMETRIC_PROMPT_DATA)) {
                    bundle = sliceItem.getBundle();
                }
            }
            try {
                return new CreateEntry(createEntryFromSlice.getAccountName(), createEntryFromSlice.getPendingIntent(), createEntryFromSlice.getIcon(), createEntryFromSlice.getDescription(), createEntryFromSlice.getLastUsedTime(), createEntryFromSlice.credentialCountInformationMap, createEntryFromSlice.getIsAutoSelectAllowed(), bundle != null ? BiometricPromptData.INSTANCE.fromBundle(bundle) : null);
            } catch (Exception e) {
                Log.i("CreateEntry", "fromSlice failed with: " + e.getMessage());
                return null;
            }
        }
    }

    /* JADX INFO: compiled from: CreateEntry.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u000b"}, d2 = {"Landroidx/credentials/provider/CreateEntry$Api28Impl;", "", "()V", "addToSlice", "Landroid/app/slice/Slice$Builder;", "createEntry", "Landroidx/credentials/provider/CreateEntry;", "fromSlice", "slice", "Landroid/app/slice/Slice;", "toSlice", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Api28Impl {
        public static final Api28Impl INSTANCE = new Api28Impl();

        private Api28Impl() {
        }

        @JvmStatic
        public static final Slice toSlice(CreateEntry createEntry) {
            Intrinsics.checkNotNullParameter(createEntry, "createEntry");
            Slice sliceBuild = INSTANCE.addToSlice(createEntry).build();
            Intrinsics.checkNotNullExpressionValue(sliceBuild, "sliceBuilder.build()");
            return sliceBuild;
        }

        public final Slice.Builder addToSlice(CreateEntry createEntry) {
            String str;
            Intrinsics.checkNotNullParameter(createEntry, "createEntry");
            CharSequence accountName = createEntry.getAccountName();
            Icon icon = createEntry.getIcon();
            CharSequence description = createEntry.getDescription();
            Instant lastUsedTime = createEntry.getLastUsedTime();
            Map<String, Integer> map = createEntry.credentialCountInformationMap;
            PendingIntent pendingIntent = createEntry.getPendingIntent();
            Slice.Builder builder = new Slice.Builder(Uri.EMPTY, new SliceSpec("CreateEntry", 1));
            if (createEntry.getIsAutoSelectAllowed()) {
                str = "true";
            } else {
                str = "false";
            }
            builder.addText(accountName, null, CollectionsKt.listOf(CreateEntry.SLICE_HINT_ACCOUNT_NAME));
            if (lastUsedTime != null) {
                builder.addLong(lastUsedTime.toEpochMilli(), null, CollectionsKt.listOf(CreateEntry.SLICE_HINT_LAST_USED_TIME_MILLIS));
            }
            if (description != null) {
                builder.addText(description, null, CollectionsKt.listOf(CreateEntry.SLICE_HINT_NOTE));
            }
            if (icon != null) {
                builder.addIcon(icon, null, CollectionsKt.listOf(CreateEntry.SLICE_HINT_ICON));
            }
            if (CreateEntry.INSTANCE.convertCredentialCountInfoToBundle$credentials_release(map) != null) {
                builder.addBundle(CreateEntry.INSTANCE.convertCredentialCountInfoToBundle$credentials_release(map), null, CollectionsKt.listOf(CreateEntry.SLICE_HINT_CREDENTIAL_COUNT_INFORMATION));
            }
            builder.addAction(pendingIntent, new Slice.Builder(builder).addHints(Collections.singletonList(CreateEntry.SLICE_HINT_PENDING_INTENT)).build(), null).addText(str, null, CollectionsKt.listOf(CreateEntry.SLICE_HINT_AUTO_SELECT_ALLOWED));
            return builder;
        }

        @JvmStatic
        public static final CreateEntry fromSlice(Slice slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            List<SliceItem> items = slice.getItems();
            Intrinsics.checkNotNullExpressionValue(items, "slice.items");
            Map mapAsMutableMap = linkedHashMap;
            CharSequence text = null;
            PendingIntent action = null;
            Icon icon = null;
            CharSequence text2 = null;
            Instant instantOfEpochMilli = null;
            boolean z = false;
            for (SliceItem sliceItem : items) {
                if (sliceItem.hasHint(CreateEntry.SLICE_HINT_ACCOUNT_NAME)) {
                    text = sliceItem.getText();
                } else if (sliceItem.hasHint(CreateEntry.SLICE_HINT_ICON)) {
                    icon = sliceItem.getIcon();
                } else if (sliceItem.hasHint(CreateEntry.SLICE_HINT_PENDING_INTENT)) {
                    action = sliceItem.getAction();
                } else if (sliceItem.hasHint(CreateEntry.SLICE_HINT_CREDENTIAL_COUNT_INFORMATION)) {
                    Map<String, Integer> mapConvertBundleToCredentialCountInfo$credentials_release = CreateEntry.INSTANCE.convertBundleToCredentialCountInfo$credentials_release(sliceItem.getBundle());
                    Intrinsics.checkNotNull(mapConvertBundleToCredentialCountInfo$credentials_release, "null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.Int?>");
                    mapAsMutableMap = TypeIntrinsics.asMutableMap(mapConvertBundleToCredentialCountInfo$credentials_release);
                } else if (sliceItem.hasHint(CreateEntry.SLICE_HINT_LAST_USED_TIME_MILLIS)) {
                    instantOfEpochMilli = Instant.ofEpochMilli(sliceItem.getLong());
                } else if (sliceItem.hasHint(CreateEntry.SLICE_HINT_NOTE)) {
                    text2 = sliceItem.getText();
                } else if (sliceItem.hasHint(CreateEntry.SLICE_HINT_LAST_USED_TIME_MILLIS)) {
                    instantOfEpochMilli = Instant.ofEpochMilli(sliceItem.getLong());
                } else if (sliceItem.hasHint(CreateEntry.SLICE_HINT_AUTO_SELECT_ALLOWED) && Intrinsics.areEqual(sliceItem.getText(), "true")) {
                    z = true;
                }
            }
            try {
                Intrinsics.checkNotNull(text);
                Intrinsics.checkNotNull(action);
                return new CreateEntry(text, action, icon, text2, instantOfEpochMilli, mapAsMutableMap, z, (BiometricPromptData) null, 128, (DefaultConstructorMarker) null);
            } catch (Exception e) {
                Log.i("CreateEntry", "fromSlice failed with: " + e.getMessage());
                return null;
            }
        }
    }

    /* JADX INFO: compiled from: CreateEntry.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0017\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u001e\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0001¢\u0006\u0002\b\"J%\u0010#\u001a\u0004\u0018\u00010!2\u0014\u0010$\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u001fH\u0001¢\u0006\u0002\b%J\u0012\u0010&\u001a\u0004\u0018\u00010'2\u0006\u0010(\u001a\u00020)H\u0007J\u0012\u0010*\u001a\u0004\u0018\u00010'2\u0006\u0010+\u001a\u00020,H\u0007J\u0012\u0010-\u001a\u0004\u0018\u00010,2\u0006\u0010(\u001a\u00020'H\u0007J\u001f\u0010.\u001a\u00020/*\b\u0012\u0004\u0012\u00020'002\u0006\u0010 \u001a\u00020!H\u0001¢\u0006\u0002\b1J\u0017\u00102\u001a\b\u0012\u0004\u0012\u00020'00*\u00020!H\u0001¢\u0006\u0002\b3R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Landroidx/credentials/provider/CreateEntry$Companion;", "", "()V", "AUTO_SELECT_FALSE_STRING", "", "AUTO_SELECT_TRUE_STRING", "DESCRIPTION_MAX_CHAR_LIMIT", "", "EXTRA_CREATE_ACCOUNT_NAME_PREFIX", "EXTRA_CREATE_CREDENTIAL_COUNT_INFO_PREFIX", "EXTRA_CREATE_DESCRIPTION_PREFIX", "EXTRA_CREATE_ENTRY_IS_AUTO_SELECT_ALLOWED_PREFIX", "EXTRA_CREATE_ENTRY_LAST_USED_TIME_PREFIX", "EXTRA_CREATE_ENTRY_PENDING_INTENT_PREFIX", "EXTRA_CREATE_ENTRY_SIZE", "EXTRA_CREATE_TYPE_ICON_PREFIX", "REVISION_ID", "SLICE_HINT_ACCOUNT_NAME", "SLICE_HINT_ALLOWED_AUTHENTICATORS", "SLICE_HINT_AUTO_SELECT_ALLOWED", "SLICE_HINT_BIOMETRIC_PROMPT_DATA", "SLICE_HINT_CREDENTIAL_COUNT_INFORMATION", "SLICE_HINT_CRYPTO_OP_ID", "SLICE_HINT_ICON", "SLICE_HINT_LAST_USED_TIME_MILLIS", "SLICE_HINT_NOTE", "SLICE_HINT_PENDING_INTENT", "SLICE_SPEC_TYPE", "TAG", "TYPE_TOTAL_CREDENTIAL", "convertBundleToCredentialCountInfo", "", "bundle", "Landroid/os/Bundle;", "convertBundleToCredentialCountInfo$credentials_release", "convertCredentialCountInfoToBundle", "credentialCountInformationMap", "convertCredentialCountInfoToBundle$credentials_release", "fromCreateEntry", "Landroidx/credentials/provider/CreateEntry;", "createEntry", "Landroid/service/credentials/CreateEntry;", "fromSlice", "slice", "Landroid/app/slice/Slice;", "toSlice", "marshall", "", "", "marshall$credentials_release", "unmarshallCreateEntries", "unmarshallCreateEntries$credentials_release", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] call: androidx.credentials.provider.CreateEntry.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Slice toSlice(CreateEntry createEntry) {
            Intrinsics.checkNotNullParameter(createEntry, "createEntry");
            if (Build.VERSION.SDK_INT >= 35) {
                return Api35Impl.toSlice(createEntry);
            }
            if (Build.VERSION.SDK_INT >= 28) {
                return Api28Impl.toSlice(createEntry);
            }
            return null;
        }

        @JvmStatic
        public final CreateEntry fromSlice(Slice slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            if (Build.VERSION.SDK_INT >= 35) {
                return Api35Impl.fromSlice(slice);
            }
            if (Build.VERSION.SDK_INT >= 28) {
                return Api28Impl.fromSlice(slice);
            }
            return null;
        }

        @JvmStatic
        public final CreateEntry fromCreateEntry(android.service.credentials.CreateEntry createEntry) {
            Intrinsics.checkNotNullParameter(createEntry, "createEntry");
            if (Build.VERSION.SDK_INT >= 34) {
                return Api34Impl.fromCreateEntry(createEntry);
            }
            return null;
        }

        @JvmStatic
        public final Map<String, Integer> convertBundleToCredentialCountInfo$credentials_release(Bundle bundle) {
            HashMap map = new HashMap();
            if (bundle == null) {
                return map;
            }
            Set<String> setKeySet = bundle.keySet();
            Intrinsics.checkNotNullExpressionValue(setKeySet, "bundle.keySet()");
            for (String it : setKeySet) {
                try {
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    map.put(it, Integer.valueOf(bundle.getInt(it)));
                } catch (Exception e) {
                    Log.i("CreateEntry", "Issue unpacking credential count info bundle: " + e.getMessage());
                }
            }
            return map;
        }

        @JvmStatic
        public final Bundle convertCredentialCountInfoToBundle$credentials_release(Map<String, Integer> credentialCountInformationMap) {
            Intrinsics.checkNotNullParameter(credentialCountInformationMap, "credentialCountInformationMap");
            Bundle bundle = new Bundle();
            boolean z = false;
            for (Map.Entry<String, Integer> entry : credentialCountInformationMap.entrySet()) {
                if (entry.getValue() != null) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    Intrinsics.checkNotNull(value);
                    bundle.putInt(key, value.intValue());
                    z = true;
                }
            }
            if (z) {
                return bundle;
            }
            return null;
        }

        public final void marshall$credentials_release(List<CreateEntry> list, Bundle bundle) {
            Instant lastUsedTime;
            Intrinsics.checkNotNullParameter(list, "<this>");
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            bundle.putInt(CreateEntry.EXTRA_CREATE_ENTRY_SIZE, list.size());
            int i = 0;
            for (Object obj : list) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                CreateEntry createEntry = (CreateEntry) obj;
                bundle.putCharSequence(CreateEntry.EXTRA_CREATE_ACCOUNT_NAME_PREFIX + i, createEntry.getAccountName());
                bundle.putParcelable("androidx.credentials.provider.extra.PENDING_INTENT_" + i, createEntry.getPendingIntent());
                Icon icon = createEntry.getIcon();
                if (icon != null) {
                    bundle.putParcelable("androidx.credentials.provider.extra.ICON_" + i, icon);
                }
                CharSequence description = createEntry.getDescription();
                if (description != null) {
                    bundle.putCharSequence(CreateEntry.EXTRA_CREATE_DESCRIPTION_PREFIX + i, description);
                }
                if (Build.VERSION.SDK_INT >= 26 && (lastUsedTime = createEntry.getLastUsedTime()) != null) {
                    bundle.putSerializable("androidx.credentials.provider.extra.LAST_USED_TIME_" + i, lastUsedTime);
                }
                Bundle bundleConvertCredentialCountInfoToBundle$credentials_release = CreateEntry.INSTANCE.convertCredentialCountInfoToBundle$credentials_release(createEntry.credentialCountInformationMap);
                if (bundleConvertCredentialCountInfoToBundle$credentials_release != null) {
                    bundle.putBundle(CreateEntry.EXTRA_CREATE_CREDENTIAL_COUNT_INFO_PREFIX + i, bundleConvertCredentialCountInfoToBundle$credentials_release);
                }
                bundle.putBoolean("androidx.credentials.provider.extra.IS_AUTO_SELECT_ALLOWED_" + i, createEntry.getIsAutoSelectAllowed());
                i = i2;
            }
        }

        public final List<CreateEntry> unmarshallCreateEntries$credentials_release(Bundle bundle) {
            Intrinsics.checkNotNullParameter(bundle, "<this>");
            try {
                ArrayList arrayList = new ArrayList();
                int i = bundle.getInt(CreateEntry.EXTRA_CREATE_ENTRY_SIZE, 0);
                for (int i2 = 0; i2 < i; i2++) {
                    CharSequence charSequence = bundle.getCharSequence(CreateEntry.EXTRA_CREATE_ACCOUNT_NAME_PREFIX + i2);
                    if (charSequence == null) {
                        return CollectionsKt.emptyList();
                    }
                    PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("androidx.credentials.provider.extra.PENDING_INTENT_" + i2);
                    if (pendingIntent == null) {
                        return CollectionsKt.emptyList();
                    }
                    Icon icon = (Icon) bundle.getParcelable("androidx.credentials.provider.extra.ICON_" + i2);
                    CharSequence charSequence2 = bundle.getCharSequence(CreateEntry.EXTRA_CREATE_DESCRIPTION_PREFIX + i2);
                    Map mutableMap = MapsKt.toMutableMap(convertBundleToCredentialCountInfo$credentials_release(bundle.getBundle(CreateEntry.EXTRA_CREATE_CREDENTIAL_COUNT_INFO_PREFIX + i2)));
                    boolean z = bundle.getBoolean("androidx.credentials.provider.extra.IS_AUTO_SELECT_ALLOWED_" + i2, false);
                    if (Build.VERSION.SDK_INT >= 26) {
                        arrayList.add(new CreateEntry(charSequence, pendingIntent, icon, charSequence2, Utf8Old$$ExternalSyntheticApiModelOutline0.m249m((Object) bundle.getSerializable("androidx.credentials.provider.extra.LAST_USED_TIME_" + i2)), mutableMap, z, (BiometricPromptData) null, 128, (DefaultConstructorMarker) null));
                    } else {
                        arrayList.add(new CreateEntry(charSequence, pendingIntent, icon, charSequence2, (Instant) null, mutableMap, z, (BiometricPromptData) null, 128, (DefaultConstructorMarker) null));
                    }
                }
                return arrayList;
            } catch (Exception unused) {
                return CollectionsKt.emptyList();
            }
        }
    }
}

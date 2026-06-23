package com.google.android.gms.fido.fido2.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.fido.u2f.api.common.ClientData;
import com.google.android.gms.internal.fido.zzia;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.android.gms:play-services-fido@@21.0.0 */
/* JADX INFO: loaded from: classes3.dex */
public class PublicKeyCredentialRequestOptions extends RequestOptions {
    public static final Parcelable.Creator<PublicKeyCredentialRequestOptions> CREATOR = new zzar();
    private final byte[] zza;
    private final Double zzb;
    private final String zzc;
    private final List zzd;
    private final Integer zze;
    private final TokenBinding zzf;
    private final UserVerificationRequirement zzg;
    private final AuthenticationExtensions zzh;
    private final Long zzi;
    private ResultReceiver zzj;

    /* JADX INFO: compiled from: com.google.android.gms:play-services-fido@@21.0.0 */
    public static final class Builder {
        private byte[] zza;
        private Double zzb;
        private String zzc;
        private List zzd;
        private Integer zze;
        private TokenBinding zzf;
        private UserVerificationRequirement zzg;
        private AuthenticationExtensions zzh;
        private Long zzi;
        private ResultReceiver zzj;

        public Builder() {
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public Builder(PublicKeyCredentialRequestOptions publicKeyCredentialRequestOptions) {
            if (publicKeyCredentialRequestOptions != null) {
                this.zza = publicKeyCredentialRequestOptions.getChallenge();
                this.zzb = publicKeyCredentialRequestOptions.getTimeoutSeconds();
                this.zzc = publicKeyCredentialRequestOptions.getRpId();
                this.zzd = publicKeyCredentialRequestOptions.getAllowList();
                this.zze = publicKeyCredentialRequestOptions.getRequestId();
                this.zzf = publicKeyCredentialRequestOptions.getTokenBinding();
                this.zzg = publicKeyCredentialRequestOptions.zzb();
                this.zzh = publicKeyCredentialRequestOptions.getAuthenticationExtensions();
                this.zzi = publicKeyCredentialRequestOptions.zzc();
                this.zzj = publicKeyCredentialRequestOptions.zza();
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public PublicKeyCredentialRequestOptions build() {
            byte[] bArr = this.zza;
            Double d = this.zzb;
            String str = this.zzc;
            List list = this.zzd;
            Integer num = this.zze;
            TokenBinding tokenBinding = this.zzf;
            UserVerificationRequirement userVerificationRequirement = this.zzg;
            return new PublicKeyCredentialRequestOptions(bArr, d, str, list, num, tokenBinding, userVerificationRequirement == null ? null : userVerificationRequirement.toString(), this.zzh, this.zzi, null, this.zzj);
        }

        public Builder setAllowList(List<PublicKeyCredentialDescriptor> list) {
            this.zzd = list;
            return this;
        }

        public Builder setAuthenticationExtensions(AuthenticationExtensions authenticationExtensions) {
            this.zzh = authenticationExtensions;
            return this;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public Builder setChallenge(byte[] bArr) {
            this.zza = (byte[]) Preconditions.checkNotNull(bArr);
            return this;
        }

        public Builder setRequestId(Integer num) {
            this.zze = num;
            return this;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public Builder setRpId(String str) {
            this.zzc = (String) Preconditions.checkNotNull(str);
            return this;
        }

        public Builder setTimeoutSeconds(Double d) {
            this.zzb = d;
            return this;
        }

        public Builder setTokenBinding(TokenBinding tokenBinding) {
            this.zzf = tokenBinding;
            return this;
        }

        public final Builder zza(Long l) {
            this.zzi = l;
            return this;
        }

        public final Builder zzb(ResultReceiver resultReceiver) {
            this.zzj = null;
            return this;
        }

        public final Builder zzc(UserVerificationRequirement userVerificationRequirement) {
            this.zzg = userVerificationRequirement;
            return this;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    PublicKeyCredentialRequestOptions(byte[] bArr, Double d, String str, List list, Integer num, TokenBinding tokenBinding, String str2, AuthenticationExtensions authenticationExtensions, Long l, String str3, ResultReceiver resultReceiver) {
        this.zzj = resultReceiver;
        if (str3 == null || !zzia.zzc()) {
            this.zza = (byte[]) Preconditions.checkNotNull(bArr);
            this.zzb = d;
            this.zzc = (String) Preconditions.checkNotNull(str);
            this.zzd = list;
            this.zze = num;
            this.zzf = tokenBinding;
            this.zzi = l;
            if (str2 != null) {
                try {
                    this.zzg = UserVerificationRequirement.fromString(str2);
                } catch (zzbc e) {
                    throw new IllegalArgumentException(e);
                }
            } else {
                this.zzg = null;
            }
            this.zzh = authenticationExtensions;
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str3);
            Builder builder = new Builder();
            builder.setChallenge(Base64Utils.decodeUrlSafeNoPadding(jSONObject.getString(ClientData.KEY_CHALLENGE)));
            if (jSONObject.has("timeout")) {
                builder.setTimeoutSeconds(Double.valueOf(jSONObject.getDouble("timeout") / 1000.0d));
            } else if (jSONObject.has("timeoutSeconds")) {
                builder.setTimeoutSeconds(Double.valueOf(jSONObject.getDouble("timeoutSeconds")));
            }
            builder.setRpId(jSONObject.getString("rpId"));
            JSONArray jSONArray = jSONObject.has("allowList") ? jSONObject.getJSONArray("allowList") : jSONObject.has("allowCredentials") ? jSONObject.getJSONArray("allowCredentials") : null;
            if (jSONArray != null) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(PublicKeyCredentialDescriptor.zza(jSONArray.getJSONObject(i)));
                }
                builder.setAllowList(arrayList);
            }
            if (jSONObject.has("requestId")) {
                builder.setRequestId(Integer.valueOf(jSONObject.getInt("requestId")));
            }
            if (jSONObject.has("tokenBinding")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("tokenBinding");
                builder.setTokenBinding(new TokenBinding(jSONObject2.getString(NotificationCompat.CATEGORY_STATUS), jSONObject2.has("id") ? jSONObject2.getString("id") : null));
            }
            if (jSONObject.has("userVerification")) {
                builder.zzc(UserVerificationRequirement.fromString(jSONObject.getString("userVerification")));
            }
            if (jSONObject.has("authenticationExtensions")) {
                builder.setAuthenticationExtensions(AuthenticationExtensions.zza(jSONObject.getJSONObject("authenticationExtensions")));
            } else if (jSONObject.has("extensions")) {
                builder.setAuthenticationExtensions(AuthenticationExtensions.zza(jSONObject.getJSONObject("extensions")));
            }
            if (jSONObject.has("longRequestId")) {
                builder.zza(Long.valueOf(jSONObject.getLong("longRequestId")));
            }
            PublicKeyCredentialRequestOptions publicKeyCredentialRequestOptionsBuild = builder.build();
            this.zza = publicKeyCredentialRequestOptionsBuild.zza;
            this.zzb = publicKeyCredentialRequestOptionsBuild.zzb;
            this.zzc = publicKeyCredentialRequestOptionsBuild.zzc;
            this.zzd = publicKeyCredentialRequestOptionsBuild.zzd;
            this.zze = publicKeyCredentialRequestOptionsBuild.zze;
            this.zzf = publicKeyCredentialRequestOptionsBuild.zzf;
            this.zzg = publicKeyCredentialRequestOptionsBuild.zzg;
            this.zzh = publicKeyCredentialRequestOptionsBuild.zzh;
            this.zzi = publicKeyCredentialRequestOptionsBuild.zzi;
        } catch (zzbc | JSONException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static PublicKeyCredentialRequestOptions deserializeFromBytes(byte[] bArr) {
        return (PublicKeyCredentialRequestOptions) SafeParcelableSerializer.deserializeFromBytes(bArr, CREATOR);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean equals(Object obj) {
        List list;
        List list2;
        if (!(obj instanceof PublicKeyCredentialRequestOptions)) {
            return false;
        }
        PublicKeyCredentialRequestOptions publicKeyCredentialRequestOptions = (PublicKeyCredentialRequestOptions) obj;
        return Arrays.equals(this.zza, publicKeyCredentialRequestOptions.zza) && Objects.equal(this.zzb, publicKeyCredentialRequestOptions.zzb) && Objects.equal(this.zzc, publicKeyCredentialRequestOptions.zzc) && (((list = this.zzd) == null && publicKeyCredentialRequestOptions.zzd == null) || (list != null && (list2 = publicKeyCredentialRequestOptions.zzd) != null && list.containsAll(list2) && publicKeyCredentialRequestOptions.zzd.containsAll(this.zzd))) && Objects.equal(this.zze, publicKeyCredentialRequestOptions.zze) && Objects.equal(this.zzf, publicKeyCredentialRequestOptions.zzf) && Objects.equal(this.zzg, publicKeyCredentialRequestOptions.zzg) && Objects.equal(this.zzh, publicKeyCredentialRequestOptions.zzh) && Objects.equal(this.zzi, publicKeyCredentialRequestOptions.zzi);
    }

    public List<PublicKeyCredentialDescriptor> getAllowList() {
        return this.zzd;
    }

    @Override // com.google.android.gms.fido.fido2.api.common.RequestOptions
    public AuthenticationExtensions getAuthenticationExtensions() {
        return this.zzh;
    }

    @Override // com.google.android.gms.fido.fido2.api.common.RequestOptions
    public byte[] getChallenge() {
        return this.zza;
    }

    @Override // com.google.android.gms.fido.fido2.api.common.RequestOptions
    public Integer getRequestId() {
        return this.zze;
    }

    public String getRpId() {
        return this.zzc;
    }

    @Override // com.google.android.gms.fido.fido2.api.common.RequestOptions
    public Double getTimeoutSeconds() {
        return this.zzb;
    }

    @Override // com.google.android.gms.fido.fido2.api.common.RequestOptions
    public TokenBinding getTokenBinding() {
        return this.zzf;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(Arrays.hashCode(this.zza)), this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg, this.zzh, this.zzi);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.fido.fido2.api.common.RequestOptions
    public byte[] serializeToBytes() {
        if (!zzia.zzd()) {
            return SafeParcelableSerializer.serializeToBytes(this);
        }
        Builder builder = new Builder(this);
        builder.zzb(null);
        return SafeParcelableSerializer.serializeToBytes(builder.build());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String toString() {
        AuthenticationExtensions authenticationExtensions = this.zzh;
        UserVerificationRequirement userVerificationRequirement = this.zzg;
        TokenBinding tokenBinding = this.zzf;
        List list = this.zzd;
        return "PublicKeyCredentialRequestOptions{\n challenge=" + Base64Utils.encodeUrlSafeNoPadding(this.zza) + ", \n timeoutSeconds=" + this.zzb + ", \n rpId='" + this.zzc + "', \n allowList=" + String.valueOf(list) + ", \n requestId=" + this.zze + ", \n tokenBinding=" + String.valueOf(tokenBinding) + ", \n userVerification=" + String.valueOf(userVerificationRequirement) + ", \n authenticationExtensions=" + String.valueOf(authenticationExtensions) + ", \n longRequestId=" + this.zzi + "}";
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeByteArray(parcel, 2, getChallenge(), false);
        SafeParcelWriter.writeDoubleObject(parcel, 3, getTimeoutSeconds(), false);
        SafeParcelWriter.writeString(parcel, 4, getRpId(), false);
        SafeParcelWriter.writeTypedList(parcel, 5, getAllowList(), false);
        SafeParcelWriter.writeIntegerObject(parcel, 6, getRequestId(), false);
        SafeParcelWriter.writeParcelable(parcel, 7, getTokenBinding(), i, false);
        UserVerificationRequirement userVerificationRequirement = this.zzg;
        SafeParcelWriter.writeString(parcel, 8, userVerificationRequirement == null ? null : userVerificationRequirement.toString(), false);
        SafeParcelWriter.writeParcelable(parcel, 9, getAuthenticationExtensions(), i, false);
        SafeParcelWriter.writeLongObject(parcel, 10, this.zzi, false);
        SafeParcelWriter.writeString(parcel, 11, null, false);
        SafeParcelWriter.writeParcelable(parcel, 12, this.zzj, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final ResultReceiver zza() {
        return this.zzj;
    }

    public final UserVerificationRequirement zzb() {
        return this.zzg;
    }

    public final Long zzc() {
        return this.zzi;
    }
}

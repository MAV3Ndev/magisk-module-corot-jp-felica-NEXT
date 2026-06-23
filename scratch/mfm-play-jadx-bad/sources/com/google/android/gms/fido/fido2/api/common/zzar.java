package com.google.android.gms.fido.fido2.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

/* JADX INFO: compiled from: com.google.android.gms:play-services-fido@@21.0.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzar implements Parcelable.Creator {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        byte[] bArrCreateByteArray = null;
        Double doubleObject = null;
        String strCreateString = null;
        ArrayList arrayListCreateTypedList = null;
        Integer integerObject = null;
        TokenBinding tokenBinding = null;
        String strCreateString2 = null;
        AuthenticationExtensions authenticationExtensions = null;
        Long longObject = null;
        String strCreateString3 = null;
        ResultReceiver resultReceiver = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 2:
                    bArrCreateByteArray = SafeParcelReader.createByteArray(parcel, header);
                    break;
                case 3:
                    doubleObject = SafeParcelReader.readDoubleObject(parcel, header);
                    break;
                case 4:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 5:
                    arrayListCreateTypedList = SafeParcelReader.createTypedList(parcel, header, PublicKeyCredentialDescriptor.CREATOR);
                    break;
                case 6:
                    integerObject = SafeParcelReader.readIntegerObject(parcel, header);
                    break;
                case 7:
                    tokenBinding = (TokenBinding) SafeParcelReader.createParcelable(parcel, header, TokenBinding.CREATOR);
                    break;
                case 8:
                    strCreateString2 = SafeParcelReader.createString(parcel, header);
                    break;
                case 9:
                    authenticationExtensions = (AuthenticationExtensions) SafeParcelReader.createParcelable(parcel, header, AuthenticationExtensions.CREATOR);
                    break;
                case 10:
                    longObject = SafeParcelReader.readLongObject(parcel, header);
                    break;
                case 11:
                    strCreateString3 = SafeParcelReader.createString(parcel, header);
                    break;
                case 12:
                    resultReceiver = (ResultReceiver) SafeParcelReader.createParcelable(parcel, header, ResultReceiver.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new PublicKeyCredentialRequestOptions(bArrCreateByteArray, doubleObject, strCreateString, arrayListCreateTypedList, integerObject, tokenBinding, strCreateString2, authenticationExtensions, longObject, strCreateString3, resultReceiver);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new PublicKeyCredentialRequestOptions[i];
    }
}

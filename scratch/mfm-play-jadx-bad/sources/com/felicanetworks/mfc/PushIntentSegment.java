package com.felicanetworks.mfc;

import android.content.Intent;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PushIntentSegment extends PushSegment {
    public static final Parcelable.Creator<PushIntentSegment> CREATOR = new Parcelable.Creator<PushIntentSegment>() { // from class: com.felicanetworks.mfc.PushIntentSegment.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushIntentSegment createFromParcel(Parcel in) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushIntentSegment(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushIntentSegment[] newArray(int size) {
            LogMgr.log(6, "%s size=%d", "000", Integer.valueOf(size));
            LogMgr.log(6, "%s", "999");
            return new PushIntentSegment[size];
        }
    };
    private Intent intent;

    public PushIntentSegment(Intent intent) throws IllegalArgumentException {
        LogMgr.log(4, "%s In intent = %s", "000", intent);
        if (intent == null) {
            LogMgr.log(1, "%s intent == null", "800");
            throw new IllegalArgumentException();
        }
        this.activateType = 1;
        this.intent = intent;
        LogMgr.log(4, "%s", "999");
    }

    public Intent getIntentData() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s return = %s", "999", this.intent);
        return this.intent;
    }

    public void setIntentData(Intent intent) throws IllegalArgumentException {
        LogMgr.log(4, "%s In intent = %s", "000", intent);
        if (intent == null) {
            LogMgr.log(1, "%s intent == null", "800");
            throw new IllegalArgumentException();
        }
        this.intent = intent;
        LogMgr.log(4, "%s", "999");
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s", "000");
        if (in == null) {
            LogMgr.log(6, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.activateType = 1;
        if (Build.VERSION.SDK_INT >= 33) {
            this.intent = (Intent) in.readParcelable(Intent.class.getClassLoader(), Intent.class);
        } else {
            this.intent = (Intent) in.readParcelable(Intent.class.getClassLoader());
        }
        checkFormat();
        LogMgr.log(6, "%s", "999");
    }

    private PushIntentSegment(Parcel in) {
        LogMgr.log(6, "%s", "000");
        readFromParcel(in);
        LogMgr.log(6, "%s", "999");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s", "999");
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(6, "%s", "000");
        out.writeParcelable(this.intent, flag);
        LogMgr.log(6, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.PushSegment
    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(6, "%s", "000");
        if (this.intent == null) {
            LogMgr.log(1, "%s intent == null", "800");
            throw new IllegalArgumentException();
        }
        if (getType() != 1) {
            LogMgr.log(1, "%s activateType != 0x01", "801");
            throw new IllegalArgumentException();
        }
        LogMgr.log(6, "%s", "999");
    }
}

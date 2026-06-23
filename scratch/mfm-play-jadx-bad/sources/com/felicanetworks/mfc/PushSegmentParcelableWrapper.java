package com.felicanetworks.mfc;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PushSegmentParcelableWrapper implements Parcelable {
    public static final Parcelable.Creator<PushSegmentParcelableWrapper> CREATOR = new Parcelable.Creator<PushSegmentParcelableWrapper>() { // from class: com.felicanetworks.mfc.PushSegmentParcelableWrapper.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushSegmentParcelableWrapper createFromParcel(Parcel in) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushSegmentParcelableWrapper(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushSegmentParcelableWrapper[] newArray(int size) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushSegmentParcelableWrapper[size];
        }
    };
    protected PushSegment pushSegment;

    public PushSegmentParcelableWrapper(PushSegment pushSegment) {
        LogMgr.log(6, "%s pushSegment=%s", "000", pushSegment);
        this.pushSegment = pushSegment;
        LogMgr.log(6, "%s", "999");
    }

    private PushSegmentParcelableWrapper(Parcel in) {
        LogMgr.log(6, "%s in", "000");
        if (Build.VERSION.SDK_INT >= 33) {
            this.pushSegment = (PushSegment) in.readParcelable(PushSegment.class.getClassLoader(), PushSegment.class);
        } else {
            this.pushSegment = (PushSegment) in.readParcelable(PushSegment.class.getClassLoader());
        }
        LogMgr.log(6, "%s pushSegment=%s", "999", this.pushSegment);
    }

    public void setPushSegment(PushSegment pushSegment) {
        LogMgr.log(6, "%s pushSegment=%s", "000", pushSegment);
        this.pushSegment = pushSegment;
        LogMgr.log(6, "%s", "999");
    }

    public PushSegment getPushSegment() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s pushSegment=%s", "999", this.pushSegment);
        return this.pushSegment;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s", "999");
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        LogMgr.log(6, "%s dst", "000");
        dest.writeParcelable(this.pushSegment, flags);
        LogMgr.log(6, "%s", "999");
    }
}

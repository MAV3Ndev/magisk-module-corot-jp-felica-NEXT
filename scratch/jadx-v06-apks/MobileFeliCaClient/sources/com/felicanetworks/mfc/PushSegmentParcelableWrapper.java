package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class PushSegmentParcelableWrapper implements Parcelable {
    public static final Parcelable.Creator<PushSegmentParcelableWrapper> CREATOR = new Parcelable.Creator<PushSegmentParcelableWrapper>() { // from class: com.felicanetworks.mfc.PushSegmentParcelableWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushSegmentParcelableWrapper createFromParcel(Parcel parcel) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushSegmentParcelableWrapper(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushSegmentParcelableWrapper[] newArray(int i) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushSegmentParcelableWrapper[i];
        }
    };
    protected PushSegment pushSegment;

    public PushSegmentParcelableWrapper(PushSegment pushSegment) {
        LogMgr.log(6, "%s pushSegment=%s", "000", pushSegment);
        this.pushSegment = pushSegment;
        LogMgr.log(6, "%s", "999");
    }

    private PushSegmentParcelableWrapper(Parcel parcel) {
        LogMgr.log(6, "%s in", "000");
        this.pushSegment = (PushSegment) parcel.readParcelable(PushSegment.class.getClassLoader());
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
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(6, "%s dst", "000");
        parcel.writeParcelable(this.pushSegment, i);
        LogMgr.log(6, "%s", "999");
    }
}

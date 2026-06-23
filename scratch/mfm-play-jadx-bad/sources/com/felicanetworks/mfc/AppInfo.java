package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes3.dex */
public class AppInfo implements Parcelable {
    public static final Parcelable.Creator<AppInfo> CREATOR = new Parcelable.Creator<AppInfo>() { // from class: com.felicanetworks.mfc.AppInfo.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppInfo createFromParcel(Parcel in) {
            return new AppInfo(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };
    private int pid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AppInfo(int pid) {
        this.pid = pid;
    }

    public int getPid() {
        return this.pid;
    }

    private void readFromParcel(Parcel in) {
        this.pid = in.readInt();
    }

    private AppInfo(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.pid);
    }
}

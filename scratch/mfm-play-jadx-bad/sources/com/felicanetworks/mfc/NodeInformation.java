package com.felicanetworks.mfc;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class NodeInformation implements Parcelable {
    public static final Parcelable.Creator<NodeInformation> CREATOR = new Parcelable.Creator<NodeInformation>() { // from class: com.felicanetworks.mfc.NodeInformation.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NodeInformation createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new NodeInformation(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NodeInformation[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new NodeInformation[size];
        }
    };
    protected AreaInformation[] areaCodeList;
    protected int[] serviceCodeList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NodeInformation(AreaInformation[] areaCodeList, int[] serviceCodeList) throws IllegalArgumentException {
        LogMgr.log(4, "%s In areaCodeList = %s : serviceCodeList = %s", "000", areaCodeList, serviceCodeList);
        if (areaCodeList == null || serviceCodeList == null) {
            LogMgr.log(1, "%s areaCodeList == %s : serviceCodeList == %s", "800", areaCodeList, serviceCodeList);
            throw new IllegalArgumentException();
        }
        for (int i : serviceCodeList) {
            try {
                ServiceUtil.getInstance().checkServiceCode(i);
            } catch (Exception e) {
                LogMgr.log(1, "%s %s", "803", e.toString());
                throw new IllegalArgumentException();
            }
        }
        this.areaCodeList = areaCodeList;
        this.serviceCodeList = serviceCodeList;
        LogMgr.log(4, "%s", "999");
    }

    public AreaInformation[] getAreaInformationList() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return areaCode = %d", "999", this.areaCodeList);
        return this.areaCodeList;
    }

    public void setAreaInformationList(AreaInformation[] areaCodeList) throws IllegalArgumentException {
        LogMgr.log(4, "%s In areaCode = %s", "000", areaCodeList);
        if (areaCodeList == null) {
            LogMgr.log(1, "%s areaCodeList == %s : serviceCodeList == %s", "800", areaCodeList);
            throw new IllegalArgumentException();
        }
        this.areaCodeList = areaCodeList;
        LogMgr.log(4, "%s", "999");
    }

    public int[] getServiceCodeList() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return serviceCodeList = %d", "999", this.serviceCodeList);
        return this.serviceCodeList;
    }

    public void setServiceCodeList(int[] serviceCodeList) throws IllegalArgumentException {
        LogMgr.log(4, "%s In serviceCodeList = %s", "000", serviceCodeList);
        if (serviceCodeList == null) {
            LogMgr.log(1, "%s areaCodeList == %s : serviceCodeList == %s", "800", serviceCodeList);
            throw new IllegalArgumentException();
        }
        for (int i : serviceCodeList) {
            try {
                ServiceUtil.getInstance().checkServiceCode(i);
            } catch (Exception e) {
                LogMgr.log(1, "%s %s", "802", e.toString());
                throw new IllegalArgumentException();
            }
        }
        this.serviceCodeList = serviceCodeList;
        LogMgr.log(4, "%s", "999");
    }

    protected void readFromParcel(Parcel in) {
        Parcelable[] parcelableArray;
        LogMgr.log(6, "%s : in = %s", "000", in);
        if (Build.VERSION.SDK_INT >= 33) {
            parcelableArray = (Parcelable[]) in.readParcelableArray(AreaInformation.class.getClassLoader(), AreaInformation.class);
        } else {
            parcelableArray = in.readParcelableArray(AreaInformation.class.getClassLoader());
        }
        if (parcelableArray != null) {
            this.areaCodeList = new AreaInformation[parcelableArray.length];
            for (int i = 0; i < parcelableArray.length; i++) {
                this.areaCodeList[i] = (AreaInformation) parcelableArray[i];
            }
        }
        int i2 = in.readInt();
        if (i2 >= 0) {
            int[] iArr = new int[i2];
            this.serviceCodeList = iArr;
            in.readIntArray(iArr);
        }
        LogMgr.log(6, "999");
    }

    private NodeInformation(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        out.writeParcelableArray(this.areaCodeList, flag);
        int[] iArr = this.serviceCodeList;
        out.writeInt(iArr != null ? iArr.length : 0);
        out.writeIntArray(this.serviceCodeList);
        LogMgr.log(7, "999");
    }
}

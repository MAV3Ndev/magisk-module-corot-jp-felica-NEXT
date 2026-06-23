package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class NodeInformation implements Parcelable {
    public static final Parcelable.Creator<NodeInformation> CREATOR = new Parcelable.Creator<NodeInformation>() { // from class: com.felicanetworks.mfc.NodeInformation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NodeInformation createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new NodeInformation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NodeInformation[] newArray(int i) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new NodeInformation[i];
        }
    };
    protected AreaInformation[] areaCodeList;
    protected int[] serviceCodeList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NodeInformation(AreaInformation[] areaInformationArr, int[] iArr) throws IllegalArgumentException {
        LogMgr.log(4, "%s In areaCodeList = %s : serviceCodeList = %s", "000", areaInformationArr, iArr);
        if (areaInformationArr == null || iArr == null) {
            LogMgr.log(1, "%s areaCodeList == %s : serviceCodeList == %s", "800", areaInformationArr, iArr);
            throw new IllegalArgumentException();
        }
        for (int i : iArr) {
            try {
                ServiceUtil.getInstance().checkServiceCode(i);
            } catch (Exception e) {
                LogMgr.log(1, "%s %s", "803", e.toString());
                throw new IllegalArgumentException();
            }
        }
        this.areaCodeList = areaInformationArr;
        this.serviceCodeList = iArr;
        LogMgr.log(4, "%s", "999");
    }

    public AreaInformation[] getAreaInformationList() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return areaCode = %d", "999", this.areaCodeList);
        return this.areaCodeList;
    }

    public void setAreaInformationList(AreaInformation[] areaInformationArr) throws IllegalArgumentException {
        LogMgr.log(4, "%s In areaCode = %s", "000", areaInformationArr);
        if (areaInformationArr == null) {
            LogMgr.log(1, "%s areaCodeList == %s : serviceCodeList == %s", "800", areaInformationArr);
            throw new IllegalArgumentException();
        }
        this.areaCodeList = areaInformationArr;
        LogMgr.log(4, "%s", "999");
    }

    public int[] getServiceCodeList() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return serviceCodeList = %d", "999", this.serviceCodeList);
        return this.serviceCodeList;
    }

    public void setServiceCodeList(int[] iArr) throws IllegalArgumentException {
        LogMgr.log(4, "%s In serviceCodeList = %s", "000", iArr);
        if (iArr == null) {
            LogMgr.log(1, "%s areaCodeList == %s : serviceCodeList == %s", "800", iArr);
            throw new IllegalArgumentException();
        }
        for (int i : iArr) {
            try {
                ServiceUtil.getInstance().checkServiceCode(i);
            } catch (Exception e) {
                LogMgr.log(1, "%s %s", "802", e.toString());
                throw new IllegalArgumentException();
            }
        }
        this.serviceCodeList = iArr;
        LogMgr.log(4, "%s", "999");
    }

    protected void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        Parcelable[] parcelableArray = parcel.readParcelableArray(AreaInformation.class.getClassLoader());
        if (parcelableArray != null) {
            this.areaCodeList = new AreaInformation[parcelableArray.length];
            for (int i = 0; i < parcelableArray.length; i++) {
                this.areaCodeList[i] = (AreaInformation) parcelableArray[i];
            }
        }
        int i2 = parcel.readInt();
        if (i2 >= 0) {
            int[] iArr = new int[i2];
            this.serviceCodeList = iArr;
            parcel.readIntArray(iArr);
        }
        LogMgr.log(6, "999");
    }

    private NodeInformation(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        readFromParcel(parcel);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeParcelableArray(this.areaCodeList, i);
        int[] iArr = this.serviceCodeList;
        parcel.writeInt(iArr != null ? iArr.length : 0);
        parcel.writeIntArray(this.serviceCodeList);
        LogMgr.log(7, "999");
    }
}

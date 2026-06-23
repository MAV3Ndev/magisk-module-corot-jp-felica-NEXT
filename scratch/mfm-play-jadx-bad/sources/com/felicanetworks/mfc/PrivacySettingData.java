package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PrivacySettingData implements Parcelable {
    public static final Parcelable.Creator<PrivacySettingData> CREATOR = new Parcelable.Creator<PrivacySettingData>() { // from class: com.felicanetworks.mfc.PrivacySettingData.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrivacySettingData createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new PrivacySettingData(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrivacySettingData[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new PrivacySettingData[size];
        }
    };
    protected int nodeCode;
    protected boolean privacySetting;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PrivacySettingData(int nodeCode, boolean privacySetting) throws IllegalArgumentException {
        LogMgr.log(4, "%s nodeCode = %s : privacySetting = %s", "000", Integer.valueOf(nodeCode), Boolean.valueOf(privacySetting));
        try {
            ServiceUtil.getInstance().checkNodeCode(nodeCode);
            this.nodeCode = nodeCode;
            this.privacySetting = privacySetting;
            LogMgr.log(4, "%s", "999");
        } catch (Exception e) {
            LogMgr.log(1, "%s %s", "800", e.toString());
            throw new IllegalArgumentException();
        }
    }

    public int getNodeCode() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return nodeCode = %d", "999", Integer.valueOf(this.nodeCode));
        return this.nodeCode;
    }

    public void setNodeCode(int nodeCode) throws IllegalArgumentException {
        LogMgr.log(7, "%s nodeCode = %s", "000", Integer.valueOf(nodeCode));
        try {
            ServiceUtil.getInstance().checkNodeCode(nodeCode);
            this.nodeCode = nodeCode;
            LogMgr.log(4, "%s", "999");
        } catch (Exception e) {
            LogMgr.log(1, "%s %s", "800", e.toString());
            throw new IllegalArgumentException();
        }
    }

    public boolean getPrivacySetting() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return privacySetting = %d", "999", Boolean.valueOf(this.privacySetting));
        return this.privacySetting;
    }

    public void setPraivacySetting(boolean privacySetting) {
        LogMgr.log(6, "%s In privacySetting = %s", "000", Boolean.valueOf(privacySetting));
        this.privacySetting = privacySetting;
        LogMgr.log(6, "%s", "999");
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        this.nodeCode = in.readInt();
        this.privacySetting = in.readInt() != 0;
        LogMgr.log(6, "001 nodeCode=%d privacySetting=%b", Integer.valueOf(this.nodeCode), Boolean.valueOf(this.privacySetting));
        LogMgr.log(6, "999");
    }

    private PrivacySettingData(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        LogMgr.log(6, "001 nodeCode=%d privacySetting=%b", Integer.valueOf(this.nodeCode), Boolean.valueOf(this.privacySetting));
        parcel.writeInt(this.nodeCode);
        parcel.writeInt(this.privacySetting ? 1 : 0);
        LogMgr.log(4, "999");
    }

    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "000");
        ServiceUtil.getInstance().checkNodeCode(this.nodeCode);
        LogMgr.log(4, "999");
    }
}

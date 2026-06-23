package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PushStartMailerSegment extends PushSegment {
    public static final Parcelable.Creator<PushStartMailerSegment> CREATOR = new Parcelable.Creator<PushStartMailerSegment>() { // from class: com.felicanetworks.mfc.PushStartMailerSegment.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartMailerSegment createFromParcel(Parcel in) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartMailerSegment(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartMailerSegment[] newArray(int size) {
            LogMgr.log(6, "%s size=%d", "000", Integer.valueOf(size));
            LogMgr.log(6, "%s", "999");
            return new PushStartMailerSegment[size];
        }
    };
    private String body;
    private String[] ccAddress;
    private String mailerStartupParam;
    private String subject;
    private String[] toAddress;

    public PushStartMailerSegment(String[] toAddress, String[] ccAddress, String subject, String body, String mailerStartupParam) {
        LogMgr.log(6, "%s", "000");
        this.activateType = 3;
        this.toAddress = toAddress;
        this.ccAddress = ccAddress;
        this.subject = subject;
        this.body = body;
        this.mailerStartupParam = mailerStartupParam;
        checkFormat();
        LogMgr.log(6, "%s", "999");
    }

    public String[] getToAddress() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s", "999");
        return this.toAddress;
    }

    public String[] getCcAddress() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s", "999");
        return this.ccAddress;
    }

    public String getSubject() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return=%s", "999", this.subject);
        return this.subject;
    }

    public String getBody() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return=%s", "999", this.body);
        return this.body;
    }

    public String getMailerStartupParam() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return=%s", "999", this.mailerStartupParam);
        return this.mailerStartupParam;
    }

    public void setToAddress(String[] toAddress) {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s", "999");
        this.toAddress = toAddress;
    }

    public void setCcAddress(String[] ccAddress) {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s", "999");
        this.ccAddress = ccAddress;
    }

    public void setSubject(String subject) {
        LogMgr.log(6, "%s subject=%s", "000", subject);
        LogMgr.log(6, "%s", "999");
        this.subject = subject;
    }

    public void setBody(String body) {
        LogMgr.log(6, "%s body=%s", "000", body);
        LogMgr.log(6, "%s", "999");
        this.body = body;
    }

    public void setMailerStartupParam(String mailerStartupParam) {
        LogMgr.log(6, "%s mailerStartupParam=%s", "000", mailerStartupParam);
        LogMgr.log(6, "%s", "999");
        this.mailerStartupParam = mailerStartupParam;
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s", "000");
        if (in == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.activateType = 3;
        this.toAddress = in.createStringArray();
        this.ccAddress = in.createStringArray();
        this.subject = in.readString();
        this.body = in.readString();
        this.mailerStartupParam = in.readString();
        checkFormat();
        LogMgr.log(6, "%s", "999");
    }

    private PushStartMailerSegment(Parcel in) {
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
        out.writeStringArray(this.toAddress);
        out.writeStringArray(this.ccAddress);
        out.writeString(this.subject);
        out.writeString(this.body);
        out.writeString(this.mailerStartupParam);
        LogMgr.log(6, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.PushSegment
    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(6, "%s", "000");
        if (getType() != 3) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        LogMgr.log(6, "%s", "999");
    }
}

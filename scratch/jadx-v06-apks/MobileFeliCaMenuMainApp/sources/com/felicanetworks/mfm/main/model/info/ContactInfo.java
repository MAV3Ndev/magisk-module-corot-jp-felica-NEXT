package com.felicanetworks.mfm.main.model.info;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class ContactInfo {
    public String email;
    public String name;
    public String phoneNumber;
    public String url;

    public ContactInfo(String str, String str2, String str3, String str4) {
        this.name = str;
        this.phoneNumber = str2;
        this.url = str3;
        this.email = str4;
    }

    public String toString() {
        return "Contact{name='" + this.name + "', phoneNumber='" + this.phoneNumber + "', url='" + this.url + "', email='" + this.email + "'}";
    }

    public Intent getDefaultIntent() {
        if (!TextUtils.isEmpty(this.phoneNumber)) {
            return new Intent("android.intent.action.DIAL", Uri.parse("tel:" + this.phoneNumber));
        }
        if (!TextUtils.isEmpty(this.email)) {
            Intent intent = new Intent("android.intent.action.SENDTO");
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra("android.intent.extra.EMAIL", new String[]{this.email});
            return intent;
        }
        if (TextUtils.isEmpty(this.url)) {
            return null;
        }
        return new Intent("android.intent.action.VIEW", Uri.parse(this.url));
    }
}

package com.felicanetworks.mfm.main.model.info;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import androidx.core.net.MailTo;

/* JADX INFO: loaded from: classes3.dex */
public class ContactInfo {
    public String email;
    public String name;
    public String phoneNumber;
    public String url;

    public ContactInfo(String name, String phoneNumber, String url, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.url = url;
        this.email = email;
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
            intent.setData(Uri.parse(MailTo.MAILTO_SCHEME));
            intent.putExtra("android.intent.extra.EMAIL", new String[]{this.email});
            return intent;
        }
        if (TextUtils.isEmpty(this.url)) {
            return null;
        }
        return new Intent("android.intent.action.VIEW", Uri.parse(this.url));
    }
}

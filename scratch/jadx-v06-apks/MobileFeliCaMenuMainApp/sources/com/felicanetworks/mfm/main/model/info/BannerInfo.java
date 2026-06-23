package com.felicanetworks.mfm.main.model.info;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

/* JADX INFO: loaded from: classes.dex */
public class BannerInfo {
    private String _end;
    private String _id;
    private Bitmap _img;
    private String _imgName;
    private String _scheme;
    private String _start;
    private String _update;

    public BannerInfo(String str, String str2, Bitmap bitmap, String str3, String str4, String str5, String str6) {
        this._id = str;
        this._imgName = str2;
        this._img = bitmap;
        this._scheme = str3;
        this._start = str4;
        this._end = str5;
        this._update = str6;
    }

    public String toString() {
        return "BannerInfo{_id='" + this._id + "', _imgName='" + this._imgName + "', _img=" + this._img + ", _scheme='" + this._scheme + "', _start='" + this._start + "', _end='" + this._end + "', _update='" + this._update + "'}";
    }

    public String getId() {
        return this._id;
    }

    public Bitmap getImage() {
        return this._img;
    }

    public Intent getDefaultIntent() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(this._scheme));
        return intent;
    }

    public String getImgName() {
        return this._imgName;
    }
}

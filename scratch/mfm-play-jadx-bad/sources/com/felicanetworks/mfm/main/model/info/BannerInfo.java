package com.felicanetworks.mfm.main.model.info;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

/* JADX INFO: loaded from: classes3.dex */
public class BannerInfo {
    private String _end;
    private String _id;
    private Bitmap _img;
    private String _imgName;
    private String _scheme;
    private String _start;
    private String _update;

    public BannerInfo(String id, String imgName, Bitmap img, String scheme, String start, String end, String update) {
        this._id = id;
        this._imgName = imgName;
        this._img = img;
        this._scheme = scheme;
        this._start = start;
        this._end = end;
        this._update = update;
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

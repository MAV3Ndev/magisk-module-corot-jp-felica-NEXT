package com.felicanetworks.mfm.main.model.info;

import com.felicanetworks.mfm.main.model.info.Linkage;

/* JADX INFO: loaded from: classes.dex */
public class LinkageWeb extends Linkage {
    private String _linkageName;
    private String _url;

    @Override // com.felicanetworks.mfm.main.model.info.Linkage
    public String toString() {
        return "LinkageWeb{_url='" + this._url + "'} " + super.toString();
    }

    public LinkageWeb(String str) {
        super(Linkage.Type.WEB);
        this._url = str;
    }

    public LinkageWeb(String str, String str2) {
        super(Linkage.Type.WEB);
        this._url = str;
        this._linkageName = str2;
    }

    public String getUrl() {
        return this._url;
    }

    public String getLinkageName() {
        return this._linkageName;
    }

    @Override // com.felicanetworks.mfm.main.model.info.Linkage
    public Linkage.LaunchTarget getTarget() {
        return Linkage.LaunchTarget.createBrowserTarget(this._url);
    }
}

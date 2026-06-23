package com.felicanetworks.mfm.main.model.info;

import com.felicanetworks.mfm.main.model.info.Linkage;

/* JADX INFO: loaded from: classes3.dex */
public class LinkageWeb extends Linkage {
    private String _linkageName;
    private String _url;

    @Override // com.felicanetworks.mfm.main.model.info.Linkage
    public String toString() {
        return "LinkageWeb{_url='" + this._url + "'} " + super.toString();
    }

    public LinkageWeb(String url) {
        super(Linkage.Type.WEB);
        this._url = url;
    }

    public LinkageWeb(String url, String name) {
        super(Linkage.Type.WEB);
        this._url = url;
        this._linkageName = name;
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

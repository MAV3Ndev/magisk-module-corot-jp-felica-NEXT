package com.felicanetworks.mfm.main.model.info;

import android.content.Intent;
import com.felicanetworks.mfm.main.model.info.Linkage;

/* JADX INFO: loaded from: classes.dex */
public class LinkageContact extends Linkage {
    private Intent _intent;
    private String _linkageName;

    @Override // com.felicanetworks.mfm.main.model.info.Linkage
    public String toString() {
        return "LinkageContact{_intent='" + this._intent + "'} " + super.toString();
    }

    public LinkageContact(Intent intent, String str) {
        super(Linkage.Type.WEB);
        this._intent = intent;
        this._linkageName = str;
    }

    public String getLinkageName() {
        return this._linkageName;
    }

    public Intent getIntent() {
        return this._intent;
    }

    @Override // com.felicanetworks.mfm.main.model.info.Linkage
    public Linkage.LaunchTarget getTarget() {
        return Linkage.LaunchTarget.createContactTarget(this._intent);
    }
}

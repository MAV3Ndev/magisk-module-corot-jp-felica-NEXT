package com.felicanetworks.mfm.main.presenter.agent;

import android.content.Intent;
import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.Linkage;
import com.felicanetworks.mfm.main.model.info.LinkageApp;
import com.felicanetworks.mfm.main.model.info.LinkageContact;
import com.felicanetworks.mfm.main.model.info.LinkageDownload;
import com.felicanetworks.mfm.main.model.info.LinkageWeb;

/* JADX INFO: loaded from: classes.dex */
public class LinkageAgent {
    private final Linkage _client;
    private final LinkageType type;

    public enum ErrorType {
        NON_ERROR,
        NOT_FOUND_PACKAGE,
        NOT_FOUND_ACTIVITY,
        OTHER_ERROR
    }

    public enum LaunchTarget {
        PLAY_STORE,
        BROWSER,
        APPLICATION
    }

    public enum LinkageType {
        APP,
        WEB,
        CONTACT,
        DOWNLOAD,
        UNKNOWN
    }

    LinkageAgent(Linkage linkage) {
        if (linkage == null) {
            throw new IllegalArgumentException("client is null");
        }
        this._client = linkage;
        if (linkage instanceof LinkageApp) {
            this.type = LinkageType.APP;
            return;
        }
        if (linkage instanceof LinkageWeb) {
            this.type = LinkageType.WEB;
            return;
        }
        if (linkage instanceof LinkageContact) {
            this.type = LinkageType.CONTACT;
        } else if (linkage instanceof LinkageDownload) {
            this.type = LinkageType.DOWNLOAD;
        } else {
            this.type = LinkageType.UNKNOWN;
        }
    }

    public LinkageInfo getLinkageInfo() {
        Linkage.LinkageInfo linkageInfo = this._client.getLinkageInfo();
        return new LinkageInfo(linkageInfo.getIntent(), ErrorType.valueOf(linkageInfo.getError().name()));
    }

    public static class LinkageInfo {
        private final ErrorType errorType;
        private final Intent intent;

        public LinkageInfo(Intent intent, ErrorType errorType) {
            this.intent = intent;
            this.errorType = errorType;
        }

        public Intent getIntent() {
            return this.intent;
        }

        public ErrorType getError() {
            return this.errorType;
        }
    }

    public LinkageType getLinkageType() {
        return this.type;
    }

    public LaunchTarget getLaunchTarget() {
        return LaunchTarget.valueOf(this._client.getLaunchTargetType().name());
    }

    public String getAppIconUrl() {
        Linkage linkage = this._client;
        return linkage instanceof LinkageApp ? ((LinkageApp) linkage).getAppIconUrl() : "";
    }

    public Bitmap getAppIcon() {
        Linkage linkage = this._client;
        if (linkage instanceof LinkageApp) {
            return ((LinkageApp) linkage).getAppIcon();
        }
        return null;
    }

    public String getLinkageName() {
        Linkage linkage = this._client;
        if (linkage instanceof LinkageApp) {
            return ((LinkageApp) linkage).getLinkageName();
        }
        if (linkage instanceof LinkageWeb) {
            return ((LinkageWeb) linkage).getLinkageName();
        }
        return linkage instanceof LinkageContact ? ((LinkageContact) linkage).getLinkageName() : "";
    }
}

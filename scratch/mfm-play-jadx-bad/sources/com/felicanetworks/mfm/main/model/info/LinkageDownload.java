package com.felicanetworks.mfm.main.model.info;

import com.felicanetworks.mfm.main.model.info.Linkage;

/* JADX INFO: loaded from: classes3.dex */
public class LinkageDownload extends Linkage {
    private DownloadType _downloadType;
    private String _downloadUrl;

    @Override // com.felicanetworks.mfm.main.model.info.Linkage
    public String toString() {
        return "LinkageDownload{_downloadType=" + this._downloadType + ", _downloadUrl='" + this._downloadUrl + "'} " + super.toString();
    }

    public enum DownloadType {
        PLAY_STORE("2"),
        BROWSER("1");

        private String id;

        DownloadType(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }

    public LinkageDownload(String downloadType, String downloadUrl) {
        super(Linkage.Type.APPLICATION);
        this._downloadType = DownloadType.BROWSER.id.equals(downloadType) ? DownloadType.BROWSER : DownloadType.PLAY_STORE;
        this._downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {
        return this._downloadUrl;
    }

    public DownloadType getDownloadType() {
        return this._downloadType;
    }

    @Override // com.felicanetworks.mfm.main.model.info.Linkage
    public Linkage.LaunchTarget getTarget() {
        int iOrdinal = getDownloadType().ordinal();
        if (iOrdinal == 0) {
            return Linkage.LaunchTarget.createPlayStoreTarget(this._downloadUrl);
        }
        if (iOrdinal != 1) {
            return null;
        }
        return Linkage.LaunchTarget.createBrowserTarget(this._downloadUrl);
    }
}

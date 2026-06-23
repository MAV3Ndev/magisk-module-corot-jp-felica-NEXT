package com.felicanetworks.mfm.main.model.info;

import com.felicanetworks.mfm.main.model.info.Linkage;

/* JADX INFO: loaded from: classes.dex */
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

        DownloadType(String str) {
            this.id = str;
        }

        public String getId() {
            return this.id;
        }
    }

    public LinkageDownload(String str, String str2) {
        super(Linkage.Type.APPLICATION);
        this._downloadType = DownloadType.BROWSER.id.equals(str) ? DownloadType.BROWSER : DownloadType.PLAY_STORE;
        this._downloadUrl = str2;
    }

    public String getDownloadUrl() {
        return this._downloadUrl;
    }

    public DownloadType getDownloadType() {
        return this._downloadType;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.info.LinkageDownload$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$LinkageDownload$DownloadType;

        static {
            int[] iArr = new int[DownloadType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$LinkageDownload$DownloadType = iArr;
            try {
                iArr[DownloadType.BROWSER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$LinkageDownload$DownloadType[DownloadType.PLAY_STORE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.info.Linkage
    public Linkage.LaunchTarget getTarget() {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$info$LinkageDownload$DownloadType[getDownloadType().ordinal()];
        if (i == 1) {
            return Linkage.LaunchTarget.createBrowserTarget(this._downloadUrl);
        }
        if (i != 2) {
            return null;
        }
        return Linkage.LaunchTarget.createPlayStoreTarget(this._downloadUrl);
    }
}

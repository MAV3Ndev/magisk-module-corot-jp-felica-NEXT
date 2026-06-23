package com.felicanetworks.mfm.main.model.info;

import android.content.Intent;
import android.graphics.Bitmap;
import com.felicanetworks.mfm.main.model.info.Linkage;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.main.pkg.PackageExpert;
import com.felicanetworks.mfm.main.model.internal.main.pkg.PackageExpertException;
import com.felicanetworks.mfm.main.model.internal.main.pkg.SpecAppSignatures;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class LinkageApp extends LinkageDownload {
    private String _hash;
    private PackageExpert _pe;
    private String _pkg;
    private Bitmap appIcon;
    private String appIconUrl;
    private Intent intent;
    private String linkageName;

    @Override // com.felicanetworks.mfm.main.model.info.LinkageDownload, com.felicanetworks.mfm.main.model.info.Linkage
    public String toString() {
        return "LinkageApp{_pkg='" + this._pkg + "', _hash='" + this._hash + "', _pe=" + this._pe + "} " + super.toString();
    }

    public LinkageApp(String str, String str2, String str3, String str4, PackageExpert packageExpert) {
        super(str3, str4);
        this._pkg = str;
        this._hash = str2;
        this._pe = packageExpert;
        this.linkageName = "";
        this.appIconUrl = "";
        this.intent = null;
        this.appIcon = null;
    }

    public LinkageApp(String str, String str2, String str3, String str4, PackageExpert packageExpert, String str5, String str6, Intent intent, Bitmap bitmap) {
        super(str3, str4);
        this._pkg = str;
        this._hash = str2;
        this._pe = packageExpert;
        this.linkageName = str5;
        this.appIconUrl = str6;
        this.intent = intent;
        this.appIcon = bitmap;
    }

    public String get_pkg() {
        return this._pkg;
    }

    public String get_hash() {
        return this._hash;
    }

    public PackageExpert get_pe() {
        return this._pe;
    }

    @Override // com.felicanetworks.mfm.main.model.info.LinkageDownload, com.felicanetworks.mfm.main.model.info.Linkage
    public Linkage.LaunchTarget getTarget() {
        try {
            try {
                List<byte[]> list = this._pe.getInstalledApp(this._pkg).sigHash;
                List<String> listFindSignatures = SpecAppSignatures.findSignatures(this._pkg);
                if (listFindSignatures.isEmpty()) {
                    listFindSignatures = Collections.singletonList(this._hash);
                }
                Iterator<String> it = listFindSignatures.iterator();
                Linkage.LaunchTarget launchTargetCreateAppTarget = null;
                while (it.hasNext()) {
                    byte[] bArrHexStringToBin = CommonUtil.hexStringToBin(it.next().toUpperCase(Locale.US));
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    Iterator<byte[]> it2 = list.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        messageDigest.update(it2.next());
                        if (Arrays.equals(bArrHexStringToBin, messageDigest.digest())) {
                            Intent intent = new Intent((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_SERVICE_INTENT_FILTER_ACTION_NAME));
                            intent.setPackage(this._pkg);
                            launchTargetCreateAppTarget = Linkage.LaunchTarget.createAppTarget(this._pkg, this._pe.resolveActivity(intent));
                            break;
                        }
                    }
                    if (launchTargetCreateAppTarget != null) {
                        break;
                    }
                }
                return launchTargetCreateAppTarget == null ? Linkage.LaunchTarget.createAppTarget(null, null) : launchTargetCreateAppTarget;
            } catch (PackageExpertException unused) {
                return super.getTarget();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public String getLinkageName() {
        return this.linkageName;
    }

    public String getAppIconUrl() {
        return this.appIconUrl;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public Bitmap getAppIcon() {
        return this.appIcon;
    }
}

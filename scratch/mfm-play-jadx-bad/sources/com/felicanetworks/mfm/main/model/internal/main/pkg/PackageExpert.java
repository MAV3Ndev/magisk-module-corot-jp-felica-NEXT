package com.felicanetworks.mfm.main.model.internal.main.pkg;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.os.Build;
import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.model.internal.main.pkg.PackageExpertException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class PackageExpert {
    private ModelContext _modelContext;

    public static class InstalledApp {
        public String pkgName;
        public List<byte[]> sigHash;

        public InstalledApp(String pkgName, List<byte[]> sigHash) {
            this.pkgName = pkgName;
            this.sigHash = sigHash;
        }

        public String toString() {
            ArrayList arrayList = new ArrayList();
            for (byte[] bArr : this.sigHash) {
                if (bArr != null) {
                    if (bArr.length > 1024) {
                        byte[] bArr2 = new byte[1024];
                        System.arraycopy(bArr, 0, bArr2, 0, 1024);
                        arrayList.add(CommonUtil.binToHexString(bArr2));
                    } else {
                        arrayList.add(CommonUtil.binToHexString(bArr));
                    }
                }
            }
            return "InstalledApp{pkgName:" + this.pkgName + ",sigHash:" + arrayList + "}";
        }
    }

    public List<InstalledApp> getInstalledApps() throws PackageExpertException {
        List<PackageInfo> installedPackages;
        Signature[] apkContentsSigners;
        ArrayList arrayList = new ArrayList();
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                installedPackages = this._modelContext.getLegacyContext().androidContext.getPackageManager().getInstalledPackages(PackageManager.PackageInfoFlags.of(134217728L));
            } else if (Build.VERSION.SDK_INT >= 28) {
                installedPackages = this._modelContext.getLegacyContext().androidContext.getPackageManager().getInstalledPackages(134217728);
            } else {
                installedPackages = this._modelContext.getLegacyContext().androidContext.getPackageManager().getInstalledPackages(64);
            }
            for (int i = 0; i < installedPackages.size(); i++) {
                PackageInfo packageInfo = installedPackages.get(i);
                if (Build.VERSION.SDK_INT >= 28) {
                    if (!packageInfo.signingInfo.hasMultipleSigners()) {
                        apkContentsSigners = packageInfo.signingInfo.getSigningCertificateHistory();
                    } else {
                        apkContentsSigners = packageInfo.signingInfo.getApkContentsSigners();
                    }
                } else {
                    apkContentsSigners = packageInfo.signatures;
                }
                ArrayList arrayList2 = new ArrayList();
                if (apkContentsSigners != null) {
                    for (Signature signature : apkContentsSigners) {
                        arrayList2.add(signature.toByteArray());
                    }
                }
                arrayList.add(new InstalledApp(packageInfo.packageName, arrayList2));
            }
            return arrayList;
        } catch (Exception e) {
            throw new PackageExpertException(getClass(), 257, e, "get installed application information list failure.", PackageExpertException.Type.ID_OTHER_ERROR);
        }
    }

    public InstalledApp getInstalledApp(String pkgName) throws PackageExpertException {
        PackageInfo packageInfo;
        Signature[] apkContentsSigners;
        ArrayList arrayList = new ArrayList();
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                packageInfo = this._modelContext.getLegacyContext().androidContext.getPackageManager().getPackageInfo(pkgName, PackageManager.PackageInfoFlags.of(134217728L));
            } else if (Build.VERSION.SDK_INT >= 28) {
                packageInfo = this._modelContext.getLegacyContext().androidContext.getPackageManager().getPackageInfo(pkgName, 134217728);
            } else {
                packageInfo = this._modelContext.getLegacyContext().androidContext.getPackageManager().getPackageInfo(pkgName, 64);
            }
            if (Build.VERSION.SDK_INT >= 28) {
                if (!packageInfo.signingInfo.hasMultipleSigners()) {
                    apkContentsSigners = packageInfo.signingInfo.getSigningCertificateHistory();
                } else {
                    apkContentsSigners = packageInfo.signingInfo.getApkContentsSigners();
                }
            } else {
                apkContentsSigners = packageInfo.signatures;
            }
            if (apkContentsSigners != null) {
                for (Signature signature : apkContentsSigners) {
                    arrayList.add(signature.toByteArray());
                }
            }
            return new InstalledApp(pkgName, arrayList);
        } catch (PackageManager.NameNotFoundException e) {
            throw new PackageExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, e, "get application signer certificate information failure name not found.", PackageExpertException.Type.ID_PKG_NOTFOUND_ERROR);
        } catch (Exception e2) {
            throw new PackageExpertException(getClass(), 514, e2, "get application signer certificate information failure.", PackageExpertException.Type.ID_OTHER_ERROR);
        }
    }

    public String resolveActivity(Intent intent) {
        List<ResolveInfo> listQueryIntentActivities;
        if (Build.VERSION.SDK_INT >= 33) {
            listQueryIntentActivities = this._modelContext.getLegacyContext().androidContext.getPackageManager().queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(0L));
        } else {
            listQueryIntentActivities = this._modelContext.getLegacyContext().androidContext.getPackageManager().queryIntentActivities(intent, 0);
        }
        if (listQueryIntentActivities == null || listQueryIntentActivities.size() <= 0 || listQueryIntentActivities.get(0) == null) {
            return null;
        }
        return listQueryIntentActivities.get(0).activityInfo.name;
    }

    public PackageExpert(ModelContext modelContext) {
        this._modelContext = modelContext;
    }

    protected PackageExpert() {
    }
}

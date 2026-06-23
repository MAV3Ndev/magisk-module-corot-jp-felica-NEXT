package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data;

/* JADX INFO: loaded from: classes.dex */
public class TagInfo {
    public String accept;
    public String api_level;
    public String app_version;
    public String issuer;
    public String uid;

    public TagInfo(String str, String str2, String str3, String str4, String str5) {
        this.uid = str;
        this.app_version = str2;
        this.accept = str3;
        this.api_level = str4;
        this.issuer = str5;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void set(java.lang.String r6, java.lang.String r7) {
        /*
            r5 = this;
            int r0 = r6.hashCode()
            r1 = 4
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r0) {
                case -1423461112: goto L34;
                case -1179159879: goto L2a;
                case -901870406: goto L20;
                case -81920737: goto L16;
                case 115792: goto Lc;
                default: goto Lb;
            }
        Lb:
            goto L3e
        Lc:
            java.lang.String r0 = "uid"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3e
            r6 = 0
            goto L3f
        L16:
            java.lang.String r0 = "api_level"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3e
            r6 = 3
            goto L3f
        L20:
            java.lang.String r0 = "app_version"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3e
            r6 = 1
            goto L3f
        L2a:
            java.lang.String r0 = "issuer"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3e
            r6 = 4
            goto L3f
        L34:
            java.lang.String r0 = "accept"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3e
            r6 = 2
            goto L3f
        L3e:
            r6 = -1
        L3f:
            if (r6 == 0) goto L56
            if (r6 == r4) goto L53
            if (r6 == r3) goto L50
            if (r6 == r2) goto L4d
            if (r6 == r1) goto L4a
            goto L58
        L4a:
            r5.issuer = r7
            goto L58
        L4d:
            r5.api_level = r7
            goto L58
        L50:
            r5.accept = r7
            goto L58
        L53:
            r5.app_version = r7
            goto L58
        L56:
            r5.uid = r7
        L58:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.TagInfo.set(java.lang.String, java.lang.String):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String get(java.lang.String r6) {
        /*
            r5 = this;
            int r0 = r6.hashCode()
            r1 = 4
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r0) {
                case -1423461112: goto L34;
                case -1179159879: goto L2a;
                case -901870406: goto L20;
                case -81920737: goto L16;
                case 115792: goto Lc;
                default: goto Lb;
            }
        Lb:
            goto L3e
        Lc:
            java.lang.String r0 = "uid"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3e
            r6 = 0
            goto L3f
        L16:
            java.lang.String r0 = "api_level"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3e
            r6 = 3
            goto L3f
        L20:
            java.lang.String r0 = "app_version"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3e
            r6 = 1
            goto L3f
        L2a:
            java.lang.String r0 = "issuer"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3e
            r6 = 4
            goto L3f
        L34:
            java.lang.String r0 = "accept"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L3e
            r6 = 2
            goto L3f
        L3e:
            r6 = -1
        L3f:
            if (r6 == 0) goto L57
            if (r6 == r4) goto L54
            if (r6 == r3) goto L51
            if (r6 == r2) goto L4e
            if (r6 == r1) goto L4b
            r6 = 0
            goto L59
        L4b:
            java.lang.String r6 = r5.issuer
            goto L59
        L4e:
            java.lang.String r6 = r5.api_level
            goto L59
        L51:
            java.lang.String r6 = r5.accept
            goto L59
        L54:
            java.lang.String r6 = r5.app_version
            goto L59
        L57:
            java.lang.String r6 = r5.uid
        L59:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.TagInfo.get(java.lang.String):java.lang.String");
    }
}

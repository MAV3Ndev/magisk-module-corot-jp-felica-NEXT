package com.felicanetworks.mfw.a.cmn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: CommonConfig.java */
/* JADX INFO: loaded from: classes.dex */
public final class k {
    private static k b;
    private static final Object c = "00000004";

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Map f160a;

    private k() {
    }

    private BufferedReader a(String str, String str2) {
        return new BufferedReader(new InputStreamReader(new FileInputStream(new File(str)), str2));
    }

    private String d() {
        try {
            if (new File("/product/etc/felica/common.cfg").exists()) {
                return "/product/etc/felica/common.cfg";
            }
        } catch (Exception unused) {
        }
        try {
            return new File("/vendor/etc/felica/common.cfg").exists() ? "/vendor/etc/felica/common.cfg" : "/system/etc/felica/common.cfg";
        } catch (Exception unused2) {
            return "/system/etc/felica/common.cfg";
        }
    }

    public static k e() {
        if (b == null) {
            try {
                k kVar = new k();
                kVar.g();
                b = kVar;
            } catch (IOException unused) {
                throw new c1(k.class, "getInstance");
            }
        }
        return b;
    }

    private void g() throws IOException {
        BufferedReader bufferedReaderA = null;
        try {
            HashMap map = new HashMap();
            bufferedReaderA = a(d(), "UTF-8");
            while (true) {
                String line = bufferedReaderA.readLine();
                if (line == null) {
                    break;
                }
                String[] strArrSplit = line.split(",");
                map.put(strArrSplit[0], strArrSplit[1]);
            }
            this.f160a = map;
        } finally {
            if (bufferedReaderA != null) {
                bufferedReaderA.close();
            }
        }
    }

    public String b() {
        return (String) this.f160a.get("00000003");
    }

    public String c() {
        return (String) this.f160a.get("00000001");
    }

    public String f() {
        return (String) this.f160a.get(c);
    }
}

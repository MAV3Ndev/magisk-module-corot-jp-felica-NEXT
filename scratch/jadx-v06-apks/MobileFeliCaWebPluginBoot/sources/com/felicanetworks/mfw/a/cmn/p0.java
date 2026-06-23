package com.felicanetworks.mfw.a.cmn;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/* JADX INFO: compiled from: Property.java */
/* JADX INFO: loaded from: classes.dex */
public final class p0 {
    private static p0 b;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Properties f170a;

    private p0() {
    }

    public static p0 a() {
        if (b == null) {
            try {
                p0 p0Var = new p0();
                p0Var.d();
                b = p0Var;
            } catch (IOException e) {
                throw new c1(p0.class, "getInstance", e);
            }
        }
        return b;
    }

    private void d() throws IOException {
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = getClass().getResourceAsStream("/assets/application.props");
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            this.f170a = properties;
        } finally {
            if (resourceAsStream != null) {
                resourceAsStream.close();
            }
        }
    }

    public int b(String str) {
        return Integer.valueOf(this.f170a.getProperty(str)).intValue();
    }

    public String c(String str) {
        return this.f170a.getProperty(str);
    }
}

package a.a.a.a.d;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/* JADX INFO: compiled from: Property.java */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    private static a b;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Properties f65a;

    private a() {
    }

    public static a a() {
        if (b == null) {
            try {
                a aVar = new a();
                aVar.c();
                b = aVar;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return b;
    }

    private void c() throws IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = getClass().getResourceAsStream("/assets/application.props");
            properties.load(resourceAsStream);
            this.f65a = properties;
        } finally {
            if (resourceAsStream != null) {
                resourceAsStream.close();
            }
        }
    }

    public String b(String str) {
        return this.f65a.getProperty(str);
    }
}

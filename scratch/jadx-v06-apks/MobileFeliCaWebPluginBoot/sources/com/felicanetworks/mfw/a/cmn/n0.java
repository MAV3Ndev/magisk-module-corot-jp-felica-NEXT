package com.felicanetworks.mfw.a.cmn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: compiled from: NwConUtilWithHttpURLConnection.java */
/* JADX INFO: loaded from: classes.dex */
public class n0 {
    private n0() {
    }

    private static HttpURLConnection a(String str, String str2) throws IOException {
        StringBuilder sb = new StringBuilder(str);
        if (str2 != null) {
            if (str.contains("?")) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            sb.append(str2);
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(sb.toString()).openConnection();
        try {
            httpURLConnection.setReadTimeout(k0.f161a.intValue());
            httpURLConnection.setConnectTimeout(k0.f161a.intValue());
            httpURLConnection.setRequestProperty("User-Agent", k0.c());
            httpURLConnection.setRequestMethod("GET");
            return httpURLConnection;
        } catch (Exception e) {
            httpURLConnection.disconnect();
            throw new IOException(e.toString());
        }
    }

    public static j0 b() {
        return new j0();
    }

    private static HttpURLConnection c(String str, String str2) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        try {
            httpURLConnection.setReadTimeout(k0.f161a.intValue());
            httpURLConnection.setConnectTimeout(k0.f161a.intValue());
            httpURLConnection.setRequestProperty("User-Agent", k0.c());
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setDoOutput(true);
            if (str2 != null) {
                i(httpURLConnection, str2, "UTF-8");
            }
            return httpURLConnection;
        } catch (Exception e) {
            httpURLConnection.disconnect();
            throw new IOException(e.toString());
        }
    }

    public static void d(j0 j0Var) {
        ArrayList arrayList = new ArrayList();
        synchronized (j0Var) {
            arrayList.addAll(j0Var.c());
            j0Var.b();
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((HttpURLConnection) it.next()).disconnect();
        }
    }

    public static void e(j0 j0Var, String str, String str2, l0 l0Var) {
        if (str == null || l0Var == null) {
            throw new c1(n0.class, "get", "Illegal argument [url = " + str + ", parameter = " + str2 + ", listener = " + l0Var + "]");
        }
        try {
            HttpURLConnection httpURLConnectionA = a(str, str2);
            j0Var.a(httpURLConnectionA);
            ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
            Future futureSubmit = executorServiceNewSingleThreadExecutor.submit(new m0(httpURLConnectionA));
            executorServiceNewSingleThreadExecutor.shutdown();
            futureSubmit.get(k0.f161a.intValue(), TimeUnit.MILLISECONDS);
            y0 y0VarH = h(httpURLConnectionA);
            if (y0VarH.c() != 200) {
                throw new IOException();
            }
            l0Var.a(y0VarH);
        } catch (InterruptedException e) {
            d(j0Var);
            throw e;
        } catch (ExecutionException unused) {
            throw new IOException();
        } catch (TimeoutException unused2) {
            throw new IOException();
        }
    }

    public static void f(j0 j0Var, String str, String str2, l0 l0Var) {
        if (str == null || l0Var == null) {
            throw new c1(n0.class, "post", "Illegal argument [url = " + str + ", parameter = " + str2 + ", listener = " + l0Var + "]");
        }
        try {
            HttpURLConnection httpURLConnectionC = c(str, str2);
            j0Var.a(httpURLConnectionC);
            ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
            Future futureSubmit = executorServiceNewSingleThreadExecutor.submit(new m0(httpURLConnectionC));
            executorServiceNewSingleThreadExecutor.shutdown();
            futureSubmit.get(k0.f161a.intValue(), TimeUnit.MILLISECONDS);
            y0 y0VarH = h(httpURLConnectionC);
            if (y0VarH.c() != 200) {
                throw new IOException();
            }
            l0Var.a(y0VarH);
        } catch (InterruptedException e) {
            d(j0Var);
            throw e;
        } catch (ExecutionException unused) {
            throw new IOException();
        } catch (TimeoutException unused2) {
            throw new IOException();
        }
    }

    private static String g(HttpURLConnection httpURLConnection, String str) throws IOException {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                for (int i = bufferedInputStream.read(); i != -1; i = bufferedInputStream.read()) {
                    byteArrayOutputStream.write(i);
                }
                bufferedInputStream.close();
                byteArrayOutputStream.close();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                if (byteArray.length <= p0.a().b("conn.response.size")) {
                    return k0.a(byteArray, str);
                }
                throw new IOException();
            } catch (Throwable th) {
                bufferedInputStream.close();
                byteArrayOutputStream.close();
                throw th;
            }
        } catch (IOException unused) {
            return "";
        }
    }

    private static y0 h(HttpURLConnection httpURLConnection) throws IOException {
        int responseCode = httpURLConnection.getResponseCode();
        String contentType = httpURLConnection.getContentType();
        if (contentType == null) {
            throw new IOException();
        }
        String strG = g(httpURLConnection, "UTF-8");
        y0 y0Var = new y0();
        y0Var.f(responseCode);
        y0Var.d(contentType);
        y0Var.e(strG);
        return y0Var;
    }

    private static void i(HttpURLConnection httpURLConnection, String str, String str2) throws IOException {
        byte[] bytes = str.getBytes(str2);
        httpURLConnection.setFixedLengthStreamingMode(bytes.length);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
        try {
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
        } finally {
            bufferedOutputStream.close();
        }
    }
}

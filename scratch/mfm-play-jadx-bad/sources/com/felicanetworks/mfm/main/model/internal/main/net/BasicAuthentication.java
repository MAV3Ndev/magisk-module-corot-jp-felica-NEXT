package com.felicanetworks.mfm.main.model.internal.main.net;

import android.text.TextUtils;
import android.util.Base64;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class BasicAuthentication {
    private static final String FILE_PATH = "/data/local/tmp/basic_auth_settings.json";
    private static final String PARAM_KEY_PASSWORD = "password";
    private static final String PARAM_KEY_USERNAME = "userName";
    private static final String ROOT_KEY = "Basic";
    public static String password = "";
    public static String userName = "";

    public static boolean isNeedBasicAuthentication() {
        return false;
    }

    private static void load() throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(FILE_PATH)), StandardCharsets.UTF_8));
                try {
                    StringBuilder sb = new StringBuilder();
                    for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                        sb.append(line);
                    }
                    JSONObject jSONObject = new JSONObject(sb.toString()).getJSONObject(ROOT_KEY);
                    Iterator<String> itKeys = jSONObject.keys();
                    while (itKeys.hasNext()) {
                        String next = itKeys.next();
                        if (PARAM_KEY_USERNAME.equals(next)) {
                            userName = jSONObject.getString(next);
                        } else if (PARAM_KEY_PASSWORD.equals(next)) {
                            password = jSONObject.getString(next);
                        }
                    }
                    bufferedReader.close();
                } catch (Exception unused) {
                    bufferedReader2 = bufferedReader;
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused3) {
            }
        } catch (Exception unused4) {
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
        }
    }

    public static String getUserName() throws Throwable {
        if (TextUtils.isEmpty(userName)) {
            load();
        }
        return userName;
    }

    public static String getPassword() throws Throwable {
        if (TextUtils.isEmpty(password)) {
            load();
        }
        return password;
    }

    public static NetworkExpert.Request addBasicAuthorization(NetworkExpert.Request request) throws Throwable {
        String userName2 = getUserName();
        String password2 = getPassword();
        if (!TextUtils.isEmpty(userName2) && !TextUtils.isEmpty(password2)) {
            String strEncodeToString = Base64.encodeToString((userName2 + ":" + password2).getBytes(StandardCharsets.UTF_8), 2);
            Map<String, String> map = request.header;
            StringBuilder sb = new StringBuilder("Basic ");
            sb.append(strEncodeToString);
            map.put("Authorization", sb.toString());
        }
        return request;
    }
}

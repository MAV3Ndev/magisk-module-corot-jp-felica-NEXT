package com.amazonaws.services.cognitoidentityprovider.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum EventType {
    SignIn("SignIn"),
    SignUp("SignUp"),
    ForgotPassword("ForgotPassword"),
    PasswordChange("PasswordChange"),
    ResendCode("ResendCode");

    private static final Map<String, EventType> enumMap;
    private String value;

    static {
        EventType eventType = SignIn;
        EventType eventType2 = SignUp;
        EventType eventType3 = ForgotPassword;
        EventType eventType4 = PasswordChange;
        EventType eventType5 = ResendCode;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("SignIn", eventType);
        map.put("SignUp", eventType2);
        map.put("ForgotPassword", eventType3);
        map.put("PasswordChange", eventType4);
        map.put("ResendCode", eventType5);
    }

    EventType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static EventType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, EventType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}

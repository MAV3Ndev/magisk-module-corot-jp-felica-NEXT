package com.amazonaws.services.cognitoidentityprovider.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum CustomEmailSenderLambdaVersionType {
    V1_0("V1_0");

    private static final Map<String, CustomEmailSenderLambdaVersionType> enumMap;
    private String value;

    static {
        CustomEmailSenderLambdaVersionType customEmailSenderLambdaVersionType = V1_0;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("V1_0", customEmailSenderLambdaVersionType);
    }

    CustomEmailSenderLambdaVersionType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static CustomEmailSenderLambdaVersionType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, CustomEmailSenderLambdaVersionType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}

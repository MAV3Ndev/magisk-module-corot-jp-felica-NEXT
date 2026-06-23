package com.amazonaws.services.cognitoidentityprovider.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum VerifiedAttributeType {
    Phone_number("phone_number"),
    Email("email");

    private static final Map<String, VerifiedAttributeType> enumMap;
    private String value;

    static {
        VerifiedAttributeType verifiedAttributeType = Phone_number;
        VerifiedAttributeType verifiedAttributeType2 = Email;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("phone_number", verifiedAttributeType);
        map.put("email", verifiedAttributeType2);
    }

    VerifiedAttributeType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static VerifiedAttributeType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, VerifiedAttributeType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}

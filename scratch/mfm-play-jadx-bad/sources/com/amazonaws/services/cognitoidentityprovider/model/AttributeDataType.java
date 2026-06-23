package com.amazonaws.services.cognitoidentityprovider.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum AttributeDataType {
    String("String"),
    Number("Number"),
    DateTime("DateTime"),
    Boolean("Boolean");

    private static final Map<String, AttributeDataType> enumMap;
    private String value;

    static {
        AttributeDataType attributeDataType = String;
        AttributeDataType attributeDataType2 = Number;
        AttributeDataType attributeDataType3 = DateTime;
        AttributeDataType attributeDataType4 = Boolean;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("String", attributeDataType);
        map.put("Number", attributeDataType2);
        map.put("DateTime", attributeDataType3);
        map.put("Boolean", attributeDataType4);
    }

    AttributeDataType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static AttributeDataType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, AttributeDataType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}

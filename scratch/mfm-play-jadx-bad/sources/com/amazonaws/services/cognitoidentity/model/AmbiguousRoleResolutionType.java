package com.amazonaws.services.cognitoidentity.model;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public enum AmbiguousRoleResolutionType {
    AuthenticatedRole("AuthenticatedRole"),
    Deny("Deny");

    private static final Map<String, AmbiguousRoleResolutionType> enumMap;
    private String value;

    static {
        AmbiguousRoleResolutionType ambiguousRoleResolutionType = AuthenticatedRole;
        AmbiguousRoleResolutionType ambiguousRoleResolutionType2 = Deny;
        HashMap map = new HashMap();
        enumMap = map;
        map.put("AuthenticatedRole", ambiguousRoleResolutionType);
        map.put("Deny", ambiguousRoleResolutionType2);
    }

    AmbiguousRoleResolutionType(String str) {
        this.value = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.value;
    }

    public static AmbiguousRoleResolutionType fromValue(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }
        Map<String, AmbiguousRoleResolutionType> map = enumMap;
        if (map.containsKey(str)) {
            return map.get(str);
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }
}

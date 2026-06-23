package com.felicanetworks.tis.resolver;

import com.felicanetworks.mfc.mfi.FlavorConst;

/* JADX INFO: loaded from: classes.dex */
class ResolverConst {
    public static final int SYSTEM_CODE_COMMON = 65024;
    public static final int SYSTEM_CODE_SPECIAL = 3;
    public static final int SYSTEM_CODE_TEST = 24;

    ResolverConst() {
    }

    static class S11 {
        public static final int SF_WRITE_SERVICE_CODE = 2316;
        public static final Integer[] SF_READ_SERVICE_CODE = {2319, Integer.valueOf(SF_WRITE_SERVICE_CODE)};
        public static final int TICKET_WRITE_SERVICE_CODE = 4236;
        public static final Integer[] TICKET_READ_SERVICE_CODE = {Integer.valueOf(FlavorConst.INDIVIDUAL_SP_SERVICE_CODE_TICKET_PUNCHING_STATUS), Integer.valueOf(TICKET_WRITE_SERVICE_CODE)};
        public static final int[] SERVICE_TYPE_CHECK_NODE_CODE_LST = {FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE1, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE2};
        public static final byte[] SERVICE_ID_TYPE_1 = {83, 86, 48, 48, 48, 48, 50, 55};
        public static final byte[] SERVICE_ID_TYPE_2 = {83, 86, 48, 48, 48, 50, 53, 51};

        S11() {
        }
    }

    static class O01 {
        public static final int SERVICE_CODE = 3277;

        O01() {
        }
    }

    static class E12 {
        public static final int SERVICE_CODE = 5900;

        E12() {
        }
    }

    static class W13 {
        public static final int SERVICE_CODE = 26632;

        W13() {
        }
    }

    static class N14 {
        public static final int SERVICE_CODE = 22092;

        N14() {
        }
    }

    static class I15 {
        public static final Integer[] READ_SERVICE_CODE = {16523, 16584};
        public static final int WRITE_SERVICE_CODE = 16524;

        I15() {
        }
    }

    static class Q16 {
        public static final int SERVICE_CODE = 13771;

        Q16() {
        }
    }
}

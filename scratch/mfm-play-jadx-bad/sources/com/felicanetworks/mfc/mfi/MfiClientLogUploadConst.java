package com.felicanetworks.mfc.mfi;

/* JADX INFO: loaded from: classes3.dex */
public class MfiClientLogUploadConst {

    public enum Api {
        BIND_SERVICE("Context#bindService"),
        UNBIND_SERVICE("Context#unbindService"),
        ACTIVATE_FELICA("Felica#activateFelica"),
        INACTIVATE_FELICA("Felica#inactivateFelica"),
        GET_SE_INFORMATION("MfiClient#getSeInformation"),
        SERVER_OPERATION("MfiClient#executeServerOperation"),
        PROVISION_SERVER_OPERATION("MfiClient#provisionServerOperation"),
        UNKNOWN("UNKNOWN", false);

        public final String name;

        Api(String name) {
            this(name, true);
        }

        Api(String name, boolean obfuscation) {
            if (obfuscation) {
                this.name = Integer.toHexString(name.hashCode());
            } else {
                this.name = name;
            }
        }
    }
}

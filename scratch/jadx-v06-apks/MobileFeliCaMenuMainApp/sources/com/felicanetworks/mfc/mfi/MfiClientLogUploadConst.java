package com.felicanetworks.mfc.mfi;

/* JADX INFO: loaded from: classes.dex */
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

        Api(String str) {
            this(str, true);
        }

        Api(String str, boolean z) {
            if (z) {
                this.name = Integer.toHexString(str.hashCode());
            } else {
                this.name = str;
            }
        }
    }
}

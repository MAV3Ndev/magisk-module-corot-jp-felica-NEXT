package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.AreaInformation;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Collections;

/* JADX INFO: loaded from: classes3.dex */
class SimpleNodeInfo {
    private static final int SYSTEM_CODE_LEN = 4;
    private final ArrayList<SimpleAreaInfo> mSimpleAreaInfoList = new ArrayList<>();
    private final int mSystemCode;

    SimpleNodeInfo(int systemCode, AreaInformation[] aiList) {
        this.mSystemCode = systemCode;
        for (AreaInformation areaInformation : aiList) {
            this.mSimpleAreaInfoList.add(new SimpleAreaInfo(areaInformation.getAreaCode(), areaInformation.getEndServiceCode()));
        }
        Collections.sort(this.mSimpleAreaInfoList);
    }

    String getSystemCodeStr() {
        return StringUtil.intToHexString(this.mSystemCode, 4);
    }

    boolean validFirstNode() {
        return this.mSimpleAreaInfoList.get(0).getAreaCode() == 0;
    }

    ArrayList<String> getAreaCodeStrListDirectlyUnderArea0() {
        LogMgr.log(6, "000");
        ArrayList<String> arrayList = new ArrayList<>();
        int endServiceCode = -1;
        for (int i = 0; i < this.mSimpleAreaInfoList.size(); i++) {
            int areaCode = this.mSimpleAreaInfoList.get(i).getAreaCode();
            LogMgr.log(7, "001 areaCode = " + areaCode);
            if (areaCode > endServiceCode) {
                LogMgr.log(7, "002");
                arrayList.add(this.mSimpleAreaInfoList.get(i).getAreaCodeStr());
                if (areaCode != 0) {
                    endServiceCode = this.mSimpleAreaInfoList.get(i).getEndServiceCode();
                    LogMgr.log(7, "003 endServiceCode = " + endServiceCode);
                }
            }
        }
        LogMgr.log(6, "999");
        return arrayList;
    }

    private static class SimpleAreaInfo implements Comparable<SimpleAreaInfo> {
        private static final int NODE_CODE_LEN = 4;
        private final int mAreaCode;
        private final int mEndServiceCode;

        SimpleAreaInfo(int areaCode, int endServiceCode) {
            this.mAreaCode = areaCode;
            this.mEndServiceCode = endServiceCode;
        }

        int getAreaCode() {
            return this.mAreaCode;
        }

        int getEndServiceCode() {
            return this.mEndServiceCode;
        }

        String getAreaCodeStr() {
            return StringUtil.intToHexString(this.mAreaCode, 4);
        }

        /* JADX DEBUG: Method merged with bridge method: compareTo(Ljava/lang/Object;)I */
        @Override // java.lang.Comparable
        public int compareTo(SimpleAreaInfo areaInfo) {
            return Integer.compare(this.mAreaCode, areaInfo.getAreaCode());
        }
    }
}

package com.felicanetworks.tis.resolver;

import android.content.Context;
import com.felicanetworks.tis.datatype.NotificationInfo;
import com.felicanetworks.tis.datatype.TransactionInfo;
import com.felicanetworks.tis.datatype.TransactionInfoGroup;
import com.felicanetworks.tis.resolver.ResolverConst;
import com.felicanetworks.tis.util.AccessConfig;
import com.felicanetworks.tis.util.LogMgr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TransactionInfoResolver {
    private static final int CID_LENGTH = 63;
    private static final int SERVICE_ID_LENGTH = 8;
    private static final int SERVICE_ID_OFFSET = 7;
    private Context mContext;
    private List<TransactionInfoGroup> mTransactionInfoGroupList;
    private List<TransactionInfo> mTransactionInfoList;
    private Resolver[] SPECIAL_RESOLVERS = {new S11Resolver()};
    private Resolver[] SPECIAL_RESOLVERS_2 = {new O90Resolver()};
    private Resolver[] SPECIAL_RESOLVERS_NONE = new Resolver[0];
    private Resolver[] COMMON_RESOLVERS = {new E12Resolver(), new W13Resolver(), new N14Resolver(), new I15Resolver(), new Q16Resolver()};
    private Resolver[] TEST_RESOLVERS = {new O01Resolver()};
    private int mSpecialServiceType = 0;

    public interface Type {
        public static final int TYPE_1 = 1;
        public static final int TYPE_2 = 2;
        public static final int UNDEFINED = 0;
    }

    public int getSpecialSystemCode() {
        return 3;
    }

    public TransactionInfoResolver(Context context, List<TransactionInfo> list) {
        this.mContext = context;
        this.mTransactionInfoList = list;
        this.mTransactionInfoGroupList = groupingInfo(list);
    }

    public void setSpecialServiceType(int i) {
        this.mSpecialServiceType = i;
    }

    public void generateAndShowNotification() {
        Resolver[] specialResolvers;
        LogMgr.log(4, "000");
        NotificationDirector notificationDirector = new NotificationDirector(this.mContext);
        for (TransactionInfoGroup transactionInfoGroup : this.mTransactionInfoGroupList) {
            ArrayList arrayList = new ArrayList();
            int systemCode = transactionInfoGroup.getSystemCode();
            if (systemCode == 24) {
                specialResolvers = this.TEST_RESOLVERS;
            } else if (systemCode == 65024) {
                specialResolvers = this.COMMON_RESOLVERS;
            } else if (AccessConfig.isGP2Chip()) {
                if (transactionInfoGroup.getCid() == null) {
                    specialResolvers = this.SPECIAL_RESOLVERS;
                } else {
                    specialResolvers = getSpecialResolvers(getServiceIdType(transactionInfoGroup.getCid()));
                }
            } else {
                specialResolvers = getSpecialResolvers(this.mSpecialServiceType);
            }
            List<TransactionInfo> transactionInfoList = transactionInfoGroup.getTransactionInfoList();
            for (int i = 0; i < transactionInfoList.size(); i++) {
                TransactionInfo transactionInfo = transactionInfoList.get(i);
                for (Resolver resolver : specialResolvers) {
                    if (resolver.isMySp(transactionInfo)) {
                        resolver.addInfo(i, transactionInfo);
                    }
                }
            }
            for (Resolver resolver2 : specialResolvers) {
                resolver2.init(this.mContext);
                List<NotificationInfo> listCreateNotificationInfoList = resolver2.createNotificationInfoList(transactionInfoGroup.getIdm());
                if (!listCreateNotificationInfoList.isEmpty()) {
                    arrayList.addAll(listCreateNotificationInfoList);
                }
            }
            Collections.sort(arrayList);
            notificationDirector.showNotification(arrayList);
        }
        LogMgr.log(4, "%s", "999");
    }

    public boolean isSpecialSystemCodeExisted() {
        LogMgr.log(4, "000");
        Iterator<TransactionInfoGroup> it = this.mTransactionInfoGroupList.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (it.next().getSystemCode() == 3) {
                z = true;
            }
        }
        LogMgr.log(4, "999 result = " + z);
        return z;
    }

    public int[] getServiceTypeCheckNodeCodeList() {
        return ResolverConst.S11.SERVICE_TYPE_CHECK_NODE_CODE_LST;
    }

    private List<TransactionInfoGroup> groupingInfo(List<TransactionInfo> list) {
        LogMgr.log(6, "000");
        ArrayList arrayList = new ArrayList();
        for (TransactionInfo transactionInfo : list) {
            if (transactionInfo.isWirelessData()) {
                if (transactionInfo.isSystemInfo()) {
                    arrayList.add(new TransactionInfoGroup(transactionInfo.getSystemCode(), transactionInfo.getIdm(), transactionInfo.getCid(), new ArrayList()));
                } else if (transactionInfo.isWritingInfo() || transactionInfo.isReadingInfo()) {
                    if (!arrayList.isEmpty()) {
                        ((TransactionInfoGroup) arrayList.get(arrayList.size() - 1)).getTransactionInfoList().add(transactionInfo);
                    }
                }
            }
        }
        LogMgr.log(6, "999");
        return arrayList;
    }

    private Resolver[] getSpecialResolvers(int i) {
        LogMgr.log(6, "000 serviceType = " + i);
        Resolver[] resolverArr = this.SPECIAL_RESOLVERS_NONE;
        if (i != 1) {
            return i != 2 ? resolverArr : this.SPECIAL_RESOLVERS_2;
        }
        return this.SPECIAL_RESOLVERS;
    }

    private int getServiceIdType(byte[] bArr) {
        LogMgr.log(6, "000");
        int i = 2;
        if (bArr != null && bArr.length == 63) {
            LogMgr.logArray(6, bArr);
            byte[] bArr2 = new byte[8];
            try {
                System.arraycopy(bArr, 7, bArr2, 0, 8);
                if (Arrays.equals(bArr2, ResolverConst.S11.SERVICE_ID_TYPE_1)) {
                    i = 1;
                } else if (!Arrays.equals(bArr2, ResolverConst.S11.SERVICE_ID_TYPE_2)) {
                    i = 0;
                }
                return i;
            } catch (ArrayIndexOutOfBoundsException unused) {
                return 0;
            }
        }
        LogMgr.log(2, "700 Invalid cid. Service type is UNDEFINED.");
        return 0;
    }
}

package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class RequestServiceV2Response extends Response {
    private static final byte CODE = 51;
    private static final int IDM_LENGTH = 8;
    private int[] mAesKeyVersionList;
    private int[] mDesKeyVersionList;
    private byte[] mIdm;
    private KeyInformation[] mKeyInfoList;
    private int mKeyKind;
    private int mStatusFlag1;
    private int mStatusFlag2;

    RequestServiceV2Response() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Response
    Response get(Command command, ByteBuffer byteBuffer) throws OfflineException {
        int i;
        if (command == null || byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            if (!byteBuffer.check(1, CODE)) {
                throw new OfflineException(4);
            }
            this.mIdm = new byte[8];
            byteBuffer.copy(2, this.mIdm);
            this.mStatusFlag1 = byteBuffer.get(10) & 255;
            this.mStatusFlag2 = byteBuffer.get(11) & 255;
            this.mKeyKind = byteBuffer.get(12) & 255;
            RequestServiceV2Command requestServiceV2Command = (RequestServiceV2Command) command;
            int[] nodeCodeList = requestServiceV2Command.getNodeCodeList();
            int i2 = this.mKeyKind;
            if (i2 == 65 || i2 == 67) {
                i = 1 + (byteBuffer.get(13) * 2 * 2) + 13;
                this.mAesKeyVersionList = DataUtil.getInstance().createKeyVersionV2FirstList(byteBuffer, nodeCodeList);
                this.mDesKeyVersionList = DataUtil.getInstance().createKeyVersionV2SecondList(byteBuffer, nodeCodeList);
            } else if (i2 == 79) {
                i = 1 + (byteBuffer.get(13) * 2) + 13;
                this.mAesKeyVersionList = DataUtil.getInstance().createKeyVersionV2FirstList(byteBuffer, nodeCodeList);
                this.mDesKeyVersionList = new int[nodeCodeList.length];
            } else {
                i = 14;
                this.mAesKeyVersionList = new int[nodeCodeList.length];
                this.mDesKeyVersionList = new int[nodeCodeList.length];
            }
            if (i != byteBuffer.getLength()) {
                throw new OfflineException(4);
            }
            this.mKeyInfoList = new KeyInformation[nodeCodeList.length];
            int[] nonExistNodeCodeMask = requestServiceV2Command.getNonExistNodeCodeMask();
            for (int i3 = 0; i3 < this.mKeyInfoList.length; i3++) {
                this.mKeyInfoList[i3] = new KeyInformation(this.mKeyKind, this.mAesKeyVersionList[i3] | nonExistNodeCodeMask[i3], this.mDesKeyVersionList[i3] | nonExistNodeCodeMask[i3]);
            }
            return this;
        } catch (OfflineException e) {
            throw e;
        } catch (Exception unused) {
            throw new OfflineException(4);
        }
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    int getStatusFlag1() {
        return this.mStatusFlag1;
    }

    int getStatusFlag2() {
        return this.mStatusFlag2;
    }

    int getKeyKind() {
        return this.mKeyKind;
    }

    int[] getAesKeyVersionList() {
        return this.mAesKeyVersionList;
    }

    int[] getDesKeyVersionList() {
        return this.mDesKeyVersionList;
    }

    KeyInformation[] getKeyInformationList() {
        return this.mKeyInfoList;
    }
}

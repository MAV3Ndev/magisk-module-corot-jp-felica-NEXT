package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class SetPrivacyFlagCommand extends Command {
    private static final byte CODE = -52;
    private static final byte FCODE = 1;
    private byte[] mIdm;
    private PrivacySettingData[] mPrivacySettingData;
    private SetPrivacyFlagResponse mResponse = new SetPrivacyFlagResponse();

    SetPrivacyFlagCommand() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    void doSet(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            byteBuffer.append(CODE);
            byteBuffer.append((byte) 1);
            byteBuffer.append(this.mIdm);
            DataUtil.getInstance().append(byteBuffer, this.mPrivacySettingData);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new OfflineException(2);
        }
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    Response get(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        return this.mResponse.get(this, byteBuffer);
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    void setIdm(byte[] bArr) {
        this.mIdm = bArr;
    }

    PrivacySettingData[] getPrivacySettingData() {
        return this.mPrivacySettingData;
    }

    void setPrivacySettingData(PrivacySettingData[] privacySettingDataArr) {
        this.mPrivacySettingData = privacySettingDataArr;
    }
}

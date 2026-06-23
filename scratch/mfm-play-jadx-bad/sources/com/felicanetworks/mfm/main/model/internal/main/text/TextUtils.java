package com.felicanetworks.mfm.main.model.internal.main.text;

import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmlib.MfmAppContext;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes3.dex */
public class TextUtils {
    private String a;
    private String b;

    class a {
        private byte[] a = new byte[16];
        private byte[] b = new byte[16];

        public a(TextUtils textUtils) {
        }

        public void a(byte[] bArr) {
            byte[] bArr2 = this.a;
            System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
            int length = this.a.length;
            byte[] bArr3 = this.b;
            System.arraycopy(bArr, length, bArr3, 0, bArr3.length);
        }

        public byte[] a() {
            return this.a;
        }

        public byte[] b() {
            return this.b;
        }
    }

    public TextUtils(MfmAppContext mfmAppContext) {
        this.a = null;
        this.b = null;
        if (mfmAppContext == null) {
            return;
        }
        try {
            byte[] bArrHexStringToBin = CommonUtil.hexStringToBin(Settings.idm());
            byte[] bArr = (byte[]) Sg.getValue(Sg.Key.SETTING_API_CODE_ALPHA);
            a aVar = new a(this);
            aVar.a(a("Cmr9BNl20Skr4FH1".getBytes("ASCII")));
            byte[] bArrA = a(bArr, aVar.b(), aVar.a());
            aVar.a(a(bArrHexStringToBin));
            byte[] bArrB = b(bArrA, aVar.b(), aVar.a());
            aVar.a(a("mD03kC40DD013Acf".getBytes("ASCII")));
            this.a = CommonUtil.binToHexString(b(bArrB, aVar.b(), aVar.a()));
            this.b = (String) Sg.getValue(Sg.Key.SETTING_API_CODE_VERSION);
        } catch (Exception e) {
            LogUtil.error(e);
            this.a = null;
            this.b = null;
        }
    }

    private byte[] a(int i, byte[] bArr, byte[] bArr2, byte[] bArr3) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr3);
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(i, secretKeySpec, ivParameterSpec);
        return cipher.doFinal(bArr);
    }

    private byte[] a(byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(bArr);
        return messageDigest.digest();
    }

    private byte[] a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        return a(2, bArr, bArr2, bArr3);
    }

    private byte[] b(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        return a(1, bArr, bArr2, bArr3);
    }

    public String getApiCode() {
        return this.a;
    }

    public String getApiCodeVersion() {
        return this.b;
    }
}

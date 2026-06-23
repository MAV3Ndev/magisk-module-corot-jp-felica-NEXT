package com.felicanetworks.mfm.main.presenter.structure;

import android.nfc.NfcAdapter;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.PresenterData;

/* JADX INFO: loaded from: classes.dex */
public class NfcStatusDrawStructre extends PrimaryDrawStructure {
    protected NfcStatusDrawStructre(StructureType structureType, boolean z, boolean z2, boolean z3, String str) {
        super(structureType, z, z2, z3, str);
    }

    private boolean isEnabledNfcSettings() {
        try {
            NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(PresenterData.getInstance().getContext());
            if (defaultAdapter != null) {
                return defaultAdapter.isEnabled();
            }
            return true;
        } catch (Exception e) {
            LogUtil.warning(e);
            return true;
        }
    }

    public boolean canUseWirelessFeliCa() {
        Settings.FelicaChipVersion felicaChipVersion = Settings.getFelicaChipVersion();
        if (felicaChipVersion == Settings.FelicaChipVersion.FAVER_GP_4_0 || felicaChipVersion == Settings.FelicaChipVersion.FAVER_GP_4_1) {
            return isEnabledNfcSettings();
        }
        return true;
    }
}

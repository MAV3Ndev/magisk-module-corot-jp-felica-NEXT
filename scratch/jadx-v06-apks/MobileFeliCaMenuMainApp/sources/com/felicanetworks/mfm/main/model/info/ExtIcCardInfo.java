package com.felicanetworks.mfm.main.model.info;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.felicanetworks.mfm.main.model.internal.main.db.DatabaseExpert;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public abstract class ExtIcCardInfo extends ServiceInfo {
    private Guidance _guidance;
    private boolean hasFelicaPocket;

    public static class Guidance {
        private String description;
        private String id;
        private String imageName;

        public String toString() {
            return "Guidance{id='" + this.id + "', description='" + this.description + "', imageName=" + this.imageName + '}';
        }

        public Guidance(String str, String str2, String str3) {
            this.id = str;
            this.description = str2;
            this.imageName = str3;
        }

        public String getId() {
            return this.id;
        }

        public String getDescription() {
            return this.description;
        }

        public Bitmap getImage(Context context) {
            Resources resources = context.getResources();
            return BitmapFactory.decodeResource(resources, resources.getIdentifier(this.imageName, "drawable", context.getPackageName()));
        }
    }

    @Override // com.felicanetworks.mfm.main.model.info.ServiceInfo
    public String toString() {
        return "ExtIcCardInfo{_guidance=" + this._guidance + "} " + super.toString();
    }

    public ExtIcCardInfo(String str, String str2, String str3, String str4, Linkage linkage, DatabaseExpert databaseExpert, boolean z) {
        super(str, str2, str3, str4, linkage, databaseExpert);
        this.hasFelicaPocket = z;
        String[] strArr = (String[]) Sg.getValue(Sg.Key.SETTING_DEFAULT_GUIDANCE_ID_LIST);
        String[] strArr2 = (String[]) Sg.getValue(Sg.Key.SETTING_DEFAULT_GUIDANCE_TEXT_LIST);
        String[] strArr3 = (String[]) Sg.getValue(Sg.Key.SETTING_DEFAULT_GUIDANCE_ICON_LIST);
        int iNextInt = new Random().nextInt(strArr.length);
        this._guidance = new Guidance(strArr[iNextInt], strArr2[iNextInt], strArr3[iNextInt]);
    }

    public Guidance getGuidance() {
        return this._guidance;
    }

    public boolean hasFelicaPocket() {
        return this.hasFelicaPocket;
    }
}

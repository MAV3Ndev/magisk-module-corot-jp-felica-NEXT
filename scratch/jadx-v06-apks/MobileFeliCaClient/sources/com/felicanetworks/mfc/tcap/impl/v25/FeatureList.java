package com.felicanetworks.mfc.tcap.impl.v25;

import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class FeatureList {
    Vector<Feature> mFeatures;

    FeatureList() {
    }

    Feature get(int i) {
        return this.mFeatures.elementAt(i);
    }

    void add(Feature feature) {
        if (this.mFeatures == null) {
            this.mFeatures = new Vector<>();
        }
        this.mFeatures.addElement(feature);
    }

    int size() {
        Vector<Feature> vector = this.mFeatures;
        if (vector == null) {
            return 0;
        }
        return vector.size();
    }

    Feature query(int i, Vector<?> vector) {
        if (this.mFeatures != null) {
            for (int i2 = 0; i2 < this.mFeatures.size(); i2++) {
                Feature featureElementAt = this.mFeatures.elementAt(i2);
                if (featureElementAt.getVersion() == i && featureElementAt.getOptionNum() == vector.size()) {
                    for (int i3 = 0; i3 < featureElementAt.getOptionNum(); i3++) {
                        if (!featureElementAt.getOption(i3).equals((String) vector.elementAt(i3))) {
                            return null;
                        }
                    }
                    return featureElementAt;
                }
            }
        }
        return null;
    }
}

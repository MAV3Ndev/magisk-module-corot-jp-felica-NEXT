package com.google.android.material.color.utilities;

import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

/* JADX INFO: loaded from: classes3.dex */
public final class DynamicColor {
    public final Function<DynamicScheme, DynamicColor> background;
    public final ContrastCurve contrastCurve;
    private final HashMap<DynamicScheme, Hct> hctCache;
    public final boolean isBackground;
    public final String name;
    public final Function<DynamicScheme, Double> opacity;
    public final Function<DynamicScheme, TonalPalette> palette;
    public final Function<DynamicScheme, DynamicColor> secondBackground;
    public final Function<DynamicScheme, Double> tone;
    public final Function<DynamicScheme, ToneDeltaPair> toneDeltaPair;

    static /* synthetic */ TonalPalette lambda$fromArgb$0(TonalPalette tonalPalette, DynamicScheme dynamicScheme) {
        return tonalPalette;
    }

    public DynamicColor(String str, Function<DynamicScheme, TonalPalette> function, Function<DynamicScheme, Double> function2, boolean z, Function<DynamicScheme, DynamicColor> function3, Function<DynamicScheme, DynamicColor> function4, ContrastCurve contrastCurve, Function<DynamicScheme, ToneDeltaPair> function5) {
        this.hctCache = new HashMap<>();
        this.name = str;
        this.palette = function;
        this.tone = function2;
        this.isBackground = z;
        this.background = function3;
        this.secondBackground = function4;
        this.contrastCurve = contrastCurve;
        this.toneDeltaPair = function5;
        this.opacity = null;
    }

    public DynamicColor(String str, Function<DynamicScheme, TonalPalette> function, Function<DynamicScheme, Double> function2, boolean z, Function<DynamicScheme, DynamicColor> function3, Function<DynamicScheme, DynamicColor> function4, ContrastCurve contrastCurve, Function<DynamicScheme, ToneDeltaPair> function5, Function<DynamicScheme, Double> function6) {
        this.hctCache = new HashMap<>();
        this.name = str;
        this.palette = function;
        this.tone = function2;
        this.isBackground = z;
        this.background = function3;
        this.secondBackground = function4;
        this.contrastCurve = contrastCurve;
        this.toneDeltaPair = function5;
        this.opacity = function6;
    }

    public static DynamicColor fromPalette(String str, Function<DynamicScheme, TonalPalette> function, Function<DynamicScheme, Double> function2) {
        return new DynamicColor(str, function, function2, false, null, null, null, null);
    }

    public static DynamicColor fromPalette(String str, Function<DynamicScheme, TonalPalette> function, Function<DynamicScheme, Double> function2, boolean z) {
        return new DynamicColor(str, function, function2, z, null, null, null, null);
    }

    public static DynamicColor fromArgb(String str, int i) {
        final Hct hctFromInt = Hct.fromInt(i);
        final TonalPalette tonalPaletteFromInt = TonalPalette.fromInt(i);
        return fromPalette(str, new Function() { // from class: com.google.android.material.color.utilities.DynamicColor$$ExternalSyntheticLambda1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DynamicColor.lambda$fromArgb$0(tonalPaletteFromInt, (DynamicScheme) obj);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.DynamicColor$$ExternalSyntheticLambda2
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(hctFromInt.getTone());
            }
        });
    }

    public int getArgb(DynamicScheme dynamicScheme) {
        int i = getHct(dynamicScheme).toInt();
        Function<DynamicScheme, Double> function = this.opacity;
        if (function == null) {
            return i;
        }
        return (MathUtils.clampInt(0, 255, (int) Math.round(((Double) function.apply(dynamicScheme)).doubleValue() * 255.0d)) << 24) | (i & ViewCompat.MEASURED_SIZE_MASK);
    }

    public Hct getHct(DynamicScheme dynamicScheme) {
        Hct hct = this.hctCache.get(dynamicScheme);
        if (hct != null) {
            return hct;
        }
        Hct hct2 = ((TonalPalette) this.palette.apply(dynamicScheme)).getHct(getTone(dynamicScheme));
        if (this.hctCache.size() > 4) {
            this.hctCache.clear();
        }
        this.hctCache.put(dynamicScheme, hct2);
        return hct2;
    }

    public double getTone(DynamicScheme dynamicScheme) {
        double dMax;
        double dMin;
        boolean z = true;
        boolean z2 = dynamicScheme.contrastLevel < 0.0d;
        Function<DynamicScheme, ToneDeltaPair> function = this.toneDeltaPair;
        if (function != null) {
            ToneDeltaPair toneDeltaPair = (ToneDeltaPair) function.apply(dynamicScheme);
            DynamicColor roleA = toneDeltaPair.getRoleA();
            DynamicColor roleB = toneDeltaPair.getRoleB();
            double delta = toneDeltaPair.getDelta();
            TonePolarity polarity = toneDeltaPair.getPolarity();
            boolean stayTogether = toneDeltaPair.getStayTogether();
            double tone = ((DynamicColor) this.background.apply(dynamicScheme)).getTone(dynamicScheme);
            if (polarity != TonePolarity.NEARER && ((polarity != TonePolarity.LIGHTER || dynamicScheme.isDark) && (polarity != TonePolarity.DARKER || !dynamicScheme.isDark))) {
                z = false;
            }
            DynamicColor dynamicColor = z ? roleA : roleB;
            DynamicColor dynamicColor2 = z ? roleB : roleA;
            boolean zEquals = this.name.equals(dynamicColor.name);
            double d = dynamicScheme.isDark ? 1.0d : -1.0d;
            double contrast = dynamicColor.contrastCurve.getContrast(dynamicScheme.contrastLevel);
            double d2 = 60.0d;
            double contrast2 = dynamicColor2.contrastCurve.getContrast(dynamicScheme.contrastLevel);
            double dDoubleValue = ((Double) dynamicColor.tone.apply(dynamicScheme)).doubleValue();
            if (Contrast.ratioOfTones(tone, dDoubleValue) < contrast) {
                dDoubleValue = foregroundTone(tone, contrast);
            }
            double dDoubleValue2 = ((Double) dynamicColor2.tone.apply(dynamicScheme)).doubleValue();
            if (Contrast.ratioOfTones(tone, dDoubleValue2) < contrast2) {
                dDoubleValue2 = foregroundTone(tone, contrast2);
            }
            if (z2) {
                dDoubleValue = foregroundTone(tone, contrast);
                dDoubleValue2 = foregroundTone(tone, contrast2);
            }
            if ((dDoubleValue2 - dDoubleValue) * d < delta) {
                double d3 = delta * d;
                dDoubleValue2 = MathUtils.clampDouble(0.0d, 100.0d, dDoubleValue + d3);
                if ((dDoubleValue2 - dDoubleValue) * d < delta) {
                    dDoubleValue = MathUtils.clampDouble(0.0d, 100.0d, dDoubleValue2 - d3);
                }
            }
            if (50.0d > dDoubleValue || dDoubleValue >= 60.0d) {
                if (50.0d > dDoubleValue2 || dDoubleValue2 >= 60.0d) {
                    d2 = dDoubleValue;
                    dMax = dDoubleValue2;
                } else if (stayTogether) {
                    if (d > 0.0d) {
                        dMax = Math.max(dDoubleValue2, (delta * d) + 60.0d);
                    } else {
                        dMin = Math.min(dDoubleValue2, (delta * d) + 49.0d);
                        dMax = dMin;
                        d2 = 49.0d;
                    }
                } else if (d > 0.0d) {
                    d2 = dDoubleValue;
                    dMax = 60.0d;
                } else {
                    d2 = dDoubleValue;
                    dMax = 49.0d;
                }
            } else if (d > 0.0d) {
                dMax = Math.max(dDoubleValue2, (delta * d) + 60.0d);
            } else {
                dMin = Math.min(dDoubleValue2, (delta * d) + 49.0d);
                dMax = dMin;
                d2 = 49.0d;
            }
            return zEquals ? d2 : dMax;
        }
        double dDoubleValue3 = ((Double) this.tone.apply(dynamicScheme)).doubleValue();
        Function<DynamicScheme, DynamicColor> function2 = this.background;
        if (function2 == null) {
            return dDoubleValue3;
        }
        double tone2 = ((DynamicColor) function2.apply(dynamicScheme)).getTone(dynamicScheme);
        double contrast3 = this.contrastCurve.getContrast(dynamicScheme.contrastLevel);
        if (Contrast.ratioOfTones(tone2, dDoubleValue3) < contrast3) {
            dDoubleValue3 = foregroundTone(tone2, contrast3);
        }
        if (z2) {
            dDoubleValue3 = foregroundTone(tone2, contrast3);
        }
        double d4 = (!this.isBackground || 50.0d > dDoubleValue3 || dDoubleValue3 >= 60.0d) ? dDoubleValue3 : Contrast.ratioOfTones(49.0d, tone2) >= contrast3 ? 49.0d : 60.0d;
        if (this.secondBackground != null) {
            double tone3 = ((DynamicColor) this.background.apply(dynamicScheme)).getTone(dynamicScheme);
            double tone4 = ((DynamicColor) this.secondBackground.apply(dynamicScheme)).getTone(dynamicScheme);
            double dMax2 = Math.max(tone3, tone4);
            double dMin2 = Math.min(tone3, tone4);
            if (Contrast.ratioOfTones(dMax2, d4) < contrast3 || Contrast.ratioOfTones(dMin2, d4) < contrast3) {
                double dLighter = Contrast.lighter(dMax2, contrast3);
                double dDarker = Contrast.darker(dMin2, contrast3);
                ArrayList arrayList = new ArrayList();
                if (dLighter != -1.0d) {
                    arrayList.add(Double.valueOf(dLighter));
                }
                if (dDarker != -1.0d) {
                    arrayList.add(Double.valueOf(dDarker));
                }
                if (tonePrefersLightForeground(tone3) || tonePrefersLightForeground(tone4)) {
                    if (dLighter == -1.0d) {
                        return 100.0d;
                    }
                    return dLighter;
                }
                if (arrayList.size() == 1) {
                    return ((Double) arrayList.get(0)).doubleValue();
                }
                if (dDarker == -1.0d) {
                    return 0.0d;
                }
                return dDarker;
            }
        }
        return d4;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0044 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0045 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static double foregroundTone(double d, double d2) {
        double dLighterUnsafe = Contrast.lighterUnsafe(d, d2);
        double dDarkerUnsafe = Contrast.darkerUnsafe(d, d2);
        double dRatioOfTones = Contrast.ratioOfTones(dLighterUnsafe, d);
        double dRatioOfTones2 = Contrast.ratioOfTones(dDarkerUnsafe, d);
        if (tonePrefersLightForeground(d)) {
            return (dRatioOfTones >= d2 || dRatioOfTones >= dRatioOfTones2 || ((Math.abs(dRatioOfTones - dRatioOfTones2) > 0.1d ? 1 : (Math.abs(dRatioOfTones - dRatioOfTones2) == 0.1d ? 0 : -1)) < 0 && (dRatioOfTones > d2 ? 1 : (dRatioOfTones == d2 ? 0 : -1)) < 0 && (dRatioOfTones2 > d2 ? 1 : (dRatioOfTones2 == d2 ? 0 : -1)) < 0)) ? dLighterUnsafe : dDarkerUnsafe;
        }
        if (dRatioOfTones2 >= d2 || dRatioOfTones2 >= dRatioOfTones) {
        }
    }

    public static double enableLightForeground(double d) {
        if (!tonePrefersLightForeground(d) || toneAllowsLightForeground(d)) {
            return d;
        }
        return 49.0d;
    }

    public static boolean tonePrefersLightForeground(double d) {
        return Math.round(d) < 60;
    }

    public static boolean toneAllowsLightForeground(double d) {
        return Math.round(d) <= 49;
    }
}

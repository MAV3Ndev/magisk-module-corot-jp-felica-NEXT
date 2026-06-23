package androidx.constraintlayout.core.motion.utils;

/* JADX INFO: loaded from: classes.dex */
public interface TypedValues {
    public static final int BOOLEAN_MASK = 1;
    public static final int FLOAT_MASK = 4;
    public static final int INT_MASK = 2;
    public static final int STRING_MASK = 8;
    public static final String S_CUSTOM = "CUSTOM";
    public static final int TYPE_FRAME_POSITION = 100;
    public static final int TYPE_TARGET = 101;

    int getId(String str);

    boolean setValue(int i, float f);

    boolean setValue(int i, int i2);

    boolean setValue(int i, String str);

    boolean setValue(int i, boolean z);

    public interface Attributes {
        public static final String S_ALPHA = "alpha";
        public static final String S_CURVE_FIT = "curveFit";
        public static final String S_CUSTOM = "CUSTOM";
        public static final String S_EASING = "easing";
        public static final String S_ELEVATION = "elevation";
        public static final String S_PATH_ROTATE = "pathRotate";
        public static final String S_PIVOT_X = "pivotX";
        public static final String S_PIVOT_Y = "pivotY";
        public static final String S_PROGRESS = "progress";
        public static final String S_ROTATION_X = "rotationX";
        public static final String S_ROTATION_Y = "rotationY";
        public static final String S_ROTATION_Z = "rotationZ";
        public static final String S_SCALE_X = "scaleX";
        public static final String S_SCALE_Y = "scaleY";
        public static final String S_TRANSLATION_X = "translationX";
        public static final String S_TRANSLATION_Y = "translationY";
        public static final String S_TRANSLATION_Z = "translationZ";
        public static final String S_VISIBILITY = "visibility";
        public static final int TYPE_ALPHA = 303;
        public static final int TYPE_CURVE_FIT = 301;
        public static final int TYPE_EASING = 317;
        public static final int TYPE_ELEVATION = 307;
        public static final int TYPE_PATH_ROTATE = 316;
        public static final int TYPE_PIVOT_TARGET = 318;
        public static final int TYPE_PIVOT_X = 313;
        public static final int TYPE_PIVOT_Y = 314;
        public static final int TYPE_PROGRESS = 315;
        public static final int TYPE_ROTATION_X = 308;
        public static final int TYPE_ROTATION_Y = 309;
        public static final int TYPE_ROTATION_Z = 310;
        public static final int TYPE_SCALE_X = 311;
        public static final int TYPE_SCALE_Y = 312;
        public static final int TYPE_TRANSLATION_X = 304;
        public static final int TYPE_TRANSLATION_Y = 305;
        public static final int TYPE_TRANSLATION_Z = 306;
        public static final int TYPE_VISIBILITY = 302;
        public static final String S_FRAME = "frame";
        public static final String S_TARGET = "target";
        public static final String S_PIVOT_TARGET = "pivotTarget";
        public static final String[] KEY_WORDS = {"curveFit", "visibility", "alpha", "translationX", "translationY", "translationZ", "elevation", "rotationX", "rotationY", "rotationZ", "scaleX", "scaleY", "pivotX", "pivotY", "progress", "pathRotate", "easing", "CUSTOM", S_FRAME, S_TARGET, S_PIVOT_TARGET};

        /* JADX INFO: renamed from: androidx.constraintlayout.core.motion.utils.TypedValues$Attributes$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            public static int getType(int i) {
                if (i == 100) {
                    return 2;
                }
                if (i == 101) {
                    return 8;
                }
                switch (i) {
                    case 301:
                    case 302:
                        return 2;
                    case 303:
                    case 304:
                    case 305:
                    case 306:
                    case 307:
                    case 308:
                    case 309:
                    case 310:
                    case 311:
                    case 312:
                    case 313:
                    case 314:
                    case 315:
                    case Attributes.TYPE_PATH_ROTATE /* 316 */:
                        return 4;
                    case Attributes.TYPE_EASING /* 317 */:
                    case Attributes.TYPE_PIVOT_TARGET /* 318 */:
                        return 8;
                    default:
                        return -1;
                }
            }

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Removed duplicated region for block: B:65:0x00e9  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public static int getId(java.lang.String r2) {
                /*
                    Method dump skipped, instruction units count: 424
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.TypedValues.Attributes.CC.getId(java.lang.String):int");
            }
        }
    }

    public interface Cycle {
        public static final String S_ALPHA = "alpha";
        public static final String S_CURVE_FIT = "curveFit";
        public static final String S_EASING = "easing";
        public static final String S_ELEVATION = "elevation";
        public static final String S_PATH_ROTATE = "pathRotate";
        public static final String S_PIVOT_X = "pivotX";
        public static final String S_PIVOT_Y = "pivotY";
        public static final String S_PROGRESS = "progress";
        public static final String S_ROTATION_X = "rotationX";
        public static final String S_ROTATION_Y = "rotationY";
        public static final String S_ROTATION_Z = "rotationZ";
        public static final String S_SCALE_X = "scaleX";
        public static final String S_SCALE_Y = "scaleY";
        public static final String S_TRANSLATION_X = "translationX";
        public static final String S_TRANSLATION_Y = "translationY";
        public static final String S_TRANSLATION_Z = "translationZ";
        public static final String S_VISIBILITY = "visibility";
        public static final String S_WAVE_SHAPE = "waveShape";
        public static final int TYPE_ALPHA = 403;
        public static final int TYPE_CURVE_FIT = 401;
        public static final int TYPE_CUSTOM_WAVE_SHAPE = 422;
        public static final int TYPE_EASING = 420;
        public static final int TYPE_ELEVATION = 307;
        public static final int TYPE_PATH_ROTATE = 416;
        public static final int TYPE_PIVOT_X = 313;
        public static final int TYPE_PIVOT_Y = 314;
        public static final int TYPE_PROGRESS = 315;
        public static final int TYPE_ROTATION_X = 308;
        public static final int TYPE_ROTATION_Y = 309;
        public static final int TYPE_ROTATION_Z = 310;
        public static final int TYPE_SCALE_X = 311;
        public static final int TYPE_SCALE_Y = 312;
        public static final int TYPE_TRANSLATION_X = 304;
        public static final int TYPE_TRANSLATION_Y = 305;
        public static final int TYPE_TRANSLATION_Z = 306;
        public static final int TYPE_VISIBILITY = 402;
        public static final int TYPE_WAVE_OFFSET = 424;
        public static final int TYPE_WAVE_PERIOD = 423;
        public static final int TYPE_WAVE_PHASE = 425;
        public static final int TYPE_WAVE_SHAPE = 421;
        public static final String S_CUSTOM_WAVE_SHAPE = "customWave";
        public static final String S_WAVE_PERIOD = "period";
        public static final String S_WAVE_OFFSET = "offset";
        public static final String S_WAVE_PHASE = "phase";
        public static final String[] KEY_WORDS = {"curveFit", "visibility", "alpha", "translationX", "translationY", "translationZ", "elevation", "rotationX", "rotationY", "rotationZ", "scaleX", "scaleY", "pivotX", "pivotY", "progress", "pathRotate", "easing", "waveShape", S_CUSTOM_WAVE_SHAPE, S_WAVE_PERIOD, S_WAVE_OFFSET, S_WAVE_PHASE};

        /* JADX INFO: renamed from: androidx.constraintlayout.core.motion.utils.TypedValues$Cycle$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Removed duplicated region for block: B:53:0x00b9  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public static int getId(java.lang.String r2) {
                /*
                    Method dump skipped, instruction units count: 340
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.TypedValues.Cycle.CC.getId(java.lang.String):int");
            }
        }
    }

    public interface Trigger {
        public static final String CROSS = "CROSS";
        public static final String[] KEY_WORDS = {"viewTransitionOnCross", "viewTransitionOnPositiveCross", "viewTransitionOnNegativeCross", "postLayout", "triggerSlack", "triggerCollisionView", "triggerCollisionId", "triggerID", "positiveCross", "negativeCross", "triggerReceiver", "CROSS"};
        public static final String NEGATIVE_CROSS = "negativeCross";
        public static final String POSITIVE_CROSS = "positiveCross";
        public static final String POST_LAYOUT = "postLayout";
        public static final String TRIGGER_COLLISION_ID = "triggerCollisionId";
        public static final String TRIGGER_COLLISION_VIEW = "triggerCollisionView";
        public static final String TRIGGER_ID = "triggerID";
        public static final String TRIGGER_RECEIVER = "triggerReceiver";
        public static final String TRIGGER_SLACK = "triggerSlack";
        public static final int TYPE_CROSS = 312;
        public static final int TYPE_NEGATIVE_CROSS = 310;
        public static final int TYPE_POSITIVE_CROSS = 309;
        public static final int TYPE_POST_LAYOUT = 304;
        public static final int TYPE_TRIGGER_COLLISION_ID = 307;
        public static final int TYPE_TRIGGER_COLLISION_VIEW = 306;
        public static final int TYPE_TRIGGER_ID = 308;
        public static final int TYPE_TRIGGER_RECEIVER = 311;
        public static final int TYPE_TRIGGER_SLACK = 305;
        public static final int TYPE_VIEW_TRANSITION_ON_CROSS = 301;
        public static final int TYPE_VIEW_TRANSITION_ON_NEGATIVE_CROSS = 303;
        public static final int TYPE_VIEW_TRANSITION_ON_POSITIVE_CROSS = 302;
        public static final String VIEW_TRANSITION_ON_CROSS = "viewTransitionOnCross";
        public static final String VIEW_TRANSITION_ON_NEGATIVE_CROSS = "viewTransitionOnNegativeCross";
        public static final String VIEW_TRANSITION_ON_POSITIVE_CROSS = "viewTransitionOnPositiveCross";

        /* JADX INFO: renamed from: androidx.constraintlayout.core.motion.utils.TypedValues$Trigger$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Removed duplicated region for block: B:41:0x008b  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public static int getId(java.lang.String r2) {
                /*
                    Method dump skipped, instruction units count: 258
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.TypedValues.Trigger.CC.getId(java.lang.String):int");
            }
        }
    }

    public interface Position {
        public static final String[] KEY_WORDS = {"transitionEasing", "drawPath", "percentWidth", "percentHeight", "sizePercent", "percentX", "percentY"};
        public static final String S_DRAWPATH = "drawPath";
        public static final String S_PERCENT_HEIGHT = "percentHeight";
        public static final String S_PERCENT_WIDTH = "percentWidth";
        public static final String S_PERCENT_X = "percentX";
        public static final String S_PERCENT_Y = "percentY";
        public static final String S_SIZE_PERCENT = "sizePercent";
        public static final String S_TRANSITION_EASING = "transitionEasing";
        public static final int TYPE_CURVE_FIT = 508;
        public static final int TYPE_DRAWPATH = 502;
        public static final int TYPE_PATH_MOTION_ARC = 509;
        public static final int TYPE_PERCENT_HEIGHT = 504;
        public static final int TYPE_PERCENT_WIDTH = 503;
        public static final int TYPE_PERCENT_X = 506;
        public static final int TYPE_PERCENT_Y = 507;
        public static final int TYPE_POSITION_TYPE = 510;
        public static final int TYPE_SIZE_PERCENT = 505;
        public static final int TYPE_TRANSITION_EASING = 501;

        /* JADX INFO: renamed from: androidx.constraintlayout.core.motion.utils.TypedValues$Position$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Removed duplicated region for block: B:26:0x004f  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public static int getId(java.lang.String r2) {
                /*
                    int r0 = r2.hashCode()
                    r1 = -1
                    switch(r0) {
                        case -1812823328: goto L45;
                        case -1127236479: goto L3b;
                        case -1017587252: goto L31;
                        case -827014263: goto L27;
                        case -200259324: goto L1d;
                        case 428090547: goto L13;
                        case 428090548: goto L9;
                        default: goto L8;
                    }
                L8:
                    goto L4f
                L9:
                    java.lang.String r0 = "percentY"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 6
                    goto L50
                L13:
                    java.lang.String r0 = "percentX"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 5
                    goto L50
                L1d:
                    java.lang.String r0 = "sizePercent"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 4
                    goto L50
                L27:
                    java.lang.String r0 = "drawPath"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 1
                    goto L50
                L31:
                    java.lang.String r0 = "percentHeight"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 3
                    goto L50
                L3b:
                    java.lang.String r0 = "percentWidth"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 2
                    goto L50
                L45:
                    java.lang.String r0 = "transitionEasing"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 0
                    goto L50
                L4f:
                    r2 = -1
                L50:
                    switch(r2) {
                        case 0: goto L66;
                        case 1: goto L63;
                        case 2: goto L60;
                        case 3: goto L5d;
                        case 4: goto L5a;
                        case 5: goto L57;
                        case 6: goto L54;
                        default: goto L53;
                    }
                L53:
                    return r1
                L54:
                    r2 = 507(0x1fb, float:7.1E-43)
                    return r2
                L57:
                    r2 = 506(0x1fa, float:7.09E-43)
                    return r2
                L5a:
                    r2 = 505(0x1f9, float:7.08E-43)
                    return r2
                L5d:
                    r2 = 504(0x1f8, float:7.06E-43)
                    return r2
                L60:
                    r2 = 503(0x1f7, float:7.05E-43)
                    return r2
                L63:
                    r2 = 502(0x1f6, float:7.03E-43)
                    return r2
                L66:
                    r2 = 501(0x1f5, float:7.02E-43)
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.TypedValues.Position.CC.getId(java.lang.String):int");
            }
        }
    }

    public interface Motion {
        public static final int TYPE_ANIMATE_CIRCLEANGLE_TO = 606;
        public static final int TYPE_ANIMATE_RELATIVE_TO = 605;
        public static final int TYPE_DRAW_PATH = 608;
        public static final int TYPE_EASING = 603;
        public static final int TYPE_PATHMOTION_ARC = 607;
        public static final int TYPE_PATH_ROTATE = 601;
        public static final int TYPE_POLAR_RELATIVETO = 609;
        public static final int TYPE_QUANTIZE_INTERPOLATOR = 604;
        public static final int TYPE_QUANTIZE_INTERPOLATOR_ID = 612;
        public static final int TYPE_QUANTIZE_INTERPOLATOR_TYPE = 611;
        public static final int TYPE_QUANTIZE_MOTIONSTEPS = 610;
        public static final int TYPE_QUANTIZE_MOTION_PHASE = 602;
        public static final int TYPE_STAGGER = 600;
        public static final String S_STAGGER = "Stagger";
        public static final String S_PATH_ROTATE = "PathRotate";
        public static final String S_QUANTIZE_MOTION_PHASE = "QuantizeMotionPhase";
        public static final String S_EASING = "TransitionEasing";
        public static final String S_QUANTIZE_INTERPOLATOR = "QuantizeInterpolator";
        public static final String S_ANIMATE_RELATIVE_TO = "AnimateRelativeTo";
        public static final String S_ANIMATE_CIRCLEANGLE_TO = "AnimateCircleAngleTo";
        public static final String S_PATHMOTION_ARC = "PathMotionArc";
        public static final String S_DRAW_PATH = "DrawPath";
        public static final String S_POLAR_RELATIVETO = "PolarRelativeTo";
        public static final String S_QUANTIZE_MOTIONSTEPS = "QuantizeMotionSteps";
        public static final String S_QUANTIZE_INTERPOLATOR_TYPE = "QuantizeInterpolatorType";
        public static final String S_QUANTIZE_INTERPOLATOR_ID = "QuantizeInterpolatorID";
        public static final String[] KEY_WORDS = {S_STAGGER, S_PATH_ROTATE, S_QUANTIZE_MOTION_PHASE, S_EASING, S_QUANTIZE_INTERPOLATOR, S_ANIMATE_RELATIVE_TO, S_ANIMATE_CIRCLEANGLE_TO, S_PATHMOTION_ARC, S_DRAW_PATH, S_POLAR_RELATIVETO, S_QUANTIZE_MOTIONSTEPS, S_QUANTIZE_INTERPOLATOR_TYPE, S_QUANTIZE_INTERPOLATOR_ID};

        /* JADX INFO: renamed from: androidx.constraintlayout.core.motion.utils.TypedValues$Motion$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Removed duplicated region for block: B:44:0x0094  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public static int getId(java.lang.String r2) {
                /*
                    Method dump skipped, instruction units count: 276
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.TypedValues.Motion.CC.getId(java.lang.String):int");
            }
        }
    }

    public interface Custom {
        public static final String S_INT = "integer";
        public static final int TYPE_BOOLEAN = 904;
        public static final int TYPE_COLOR = 902;
        public static final int TYPE_DIMENSION = 905;
        public static final int TYPE_FLOAT = 901;
        public static final int TYPE_INT = 900;
        public static final int TYPE_REFERENCE = 906;
        public static final int TYPE_STRING = 903;
        public static final String S_FLOAT = "float";
        public static final String S_COLOR = "color";
        public static final String S_STRING = "string";
        public static final String S_BOOLEAN = "boolean";
        public static final String S_DIMENSION = "dimension";
        public static final String S_REFERENCE = "refrence";
        public static final String[] KEY_WORDS = {S_FLOAT, S_COLOR, S_STRING, S_BOOLEAN, S_DIMENSION, S_REFERENCE};

        /* JADX INFO: renamed from: androidx.constraintlayout.core.motion.utils.TypedValues$Custom$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Removed duplicated region for block: B:26:0x004f  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public static int getId(java.lang.String r2) {
                /*
                    int r0 = r2.hashCode()
                    r1 = -1
                    switch(r0) {
                        case -1095013018: goto L45;
                        case -891985903: goto L3b;
                        case -710953590: goto L31;
                        case 64711720: goto L27;
                        case 94842723: goto L1d;
                        case 97526364: goto L13;
                        case 1958052158: goto L9;
                        default: goto L8;
                    }
                L8:
                    goto L4f
                L9:
                    java.lang.String r0 = "integer"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 0
                    goto L50
                L13:
                    java.lang.String r0 = "float"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 1
                    goto L50
                L1d:
                    java.lang.String r0 = "color"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 2
                    goto L50
                L27:
                    java.lang.String r0 = "boolean"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 4
                    goto L50
                L31:
                    java.lang.String r0 = "refrence"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 6
                    goto L50
                L3b:
                    java.lang.String r0 = "string"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 3
                    goto L50
                L45:
                    java.lang.String r0 = "dimension"
                    boolean r2 = r2.equals(r0)
                    if (r2 == 0) goto L4f
                    r2 = 5
                    goto L50
                L4f:
                    r2 = -1
                L50:
                    switch(r2) {
                        case 0: goto L66;
                        case 1: goto L63;
                        case 2: goto L60;
                        case 3: goto L5d;
                        case 4: goto L5a;
                        case 5: goto L57;
                        case 6: goto L54;
                        default: goto L53;
                    }
                L53:
                    return r1
                L54:
                    r2 = 906(0x38a, float:1.27E-42)
                    return r2
                L57:
                    r2 = 905(0x389, float:1.268E-42)
                    return r2
                L5a:
                    r2 = 904(0x388, float:1.267E-42)
                    return r2
                L5d:
                    r2 = 903(0x387, float:1.265E-42)
                    return r2
                L60:
                    r2 = 902(0x386, float:1.264E-42)
                    return r2
                L63:
                    r2 = 901(0x385, float:1.263E-42)
                    return r2
                L66:
                    r2 = 900(0x384, float:1.261E-42)
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.TypedValues.Custom.CC.getId(java.lang.String):int");
            }
        }
    }
}

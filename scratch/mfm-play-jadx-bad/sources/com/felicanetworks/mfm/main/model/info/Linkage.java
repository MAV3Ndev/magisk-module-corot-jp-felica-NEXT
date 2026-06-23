package com.felicanetworks.mfm.main.model.info;

import android.content.Intent;
import android.net.Uri;
import com.felicanetworks.mfm.main.policy.sg.Sg;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Linkage {
    private Type _type;

    public enum ErrorType {
        NON_ERROR,
        NOT_FOUND_PACKAGE,
        NOT_FOUND_ACTIVITY,
        OTHER_ERROR
    }

    public enum LaunchType {
        PLAY_STORE,
        BROWSER,
        APPLICATION
    }

    protected abstract LaunchTarget getTarget();

    public String toString() {
        return "Linkage{_type=" + this._type + '}';
    }

    public enum Type {
        APPLICATION { // from class: com.felicanetworks.mfm.main.model.info.Linkage.Type.1
            @Override // com.felicanetworks.mfm.main.model.info.Linkage.Type
            public String getType() {
                return "2";
            }
        },
        WEB { // from class: com.felicanetworks.mfm.main.model.info.Linkage.Type.2
            @Override // com.felicanetworks.mfm.main.model.info.Linkage.Type
            public String getType() {
                return "3";
            }
        };

        public String getType() {
            throw new UnsupportedOperationException("not defined.");
        }
    }

    public static class LinkageInfo {
        private final ErrorType errorType;
        private final Intent intent;

        public LinkageInfo(Intent i, ErrorType e) {
            this.intent = i;
            this.errorType = e;
        }

        public Intent getIntent() {
            return this.intent;
        }

        public ErrorType getError() {
            return this.errorType;
        }
    }

    public Linkage(Type type) {
        this._type = type;
    }

    public Type getType() {
        return this._type;
    }

    public LaunchType getLaunchTargetType() {
        return getTarget().launchType;
    }

    public LinkageInfo getLinkageInfo() {
        ErrorType errorType;
        Intent intent;
        ErrorType errorType2 = ErrorType.NON_ERROR;
        LaunchTarget target = getTarget();
        if (target != null) {
            intent = target.intent;
            errorType = target.errorType;
        } else {
            errorType = errorType2;
            intent = null;
        }
        return new LinkageInfo(intent, errorType);
    }

    protected static class LaunchTarget {
        public ErrorType errorType = ErrorType.NON_ERROR;
        public Intent intent;
        public LaunchType launchType;

        protected LaunchTarget() {
        }

        public static LaunchTarget createBrowserTarget(String url) {
            LaunchTarget launchTarget = new LaunchTarget();
            launchTarget.launchType = LaunchType.BROWSER;
            Intent intent = new Intent("android.intent.action.VIEW");
            launchTarget.intent = intent;
            intent.setData(Uri.parse(url));
            return launchTarget;
        }

        public static LaunchTarget createPlayStoreTarget(String url) {
            LaunchTarget launchTarget = new LaunchTarget();
            launchTarget.launchType = LaunchType.PLAY_STORE;
            launchTarget.intent = new Intent("android.intent.action.VIEW");
            launchTarget.intent.setPackage((String) Sg.getValue(Sg.Key.SETTING_LAUNCH_LINK_APP_PKG_PLAY_STORE));
            launchTarget.intent.setData(Uri.parse(url));
            return launchTarget;
        }

        public static LaunchTarget createContactTarget(Intent intent) {
            LaunchTarget launchTarget = new LaunchTarget();
            launchTarget.intent = intent;
            return launchTarget;
        }

        public static LaunchTarget createAppTarget(String pkg, String cls) {
            LaunchTarget launchTarget = new LaunchTarget();
            launchTarget.launchType = LaunchType.APPLICATION;
            if (pkg == null || cls == null) {
                if (pkg == null) {
                    launchTarget.errorType = ErrorType.NOT_FOUND_PACKAGE;
                    return launchTarget;
                }
                if (cls == null) {
                    launchTarget.errorType = ErrorType.NOT_FOUND_ACTIVITY;
                }
                return launchTarget;
            }
            Intent intent = new Intent("android.intent.action.MAIN");
            launchTarget.intent = intent;
            intent.addCategory("android.intent.category.LAUNCHER");
            launchTarget.intent.setClassName(pkg, cls);
            launchTarget.intent.addFlags(268435456);
            launchTarget.intent.addFlags(2097152);
            return launchTarget;
        }
    }
}

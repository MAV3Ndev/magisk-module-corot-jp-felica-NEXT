package com.amazonaws.mobile.auth.core;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSBasicCognitoIdentityProvider;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.internal.keyvaluestore.AWSKeyValueStore;
import com.amazonaws.mobile.auth.core.internal.util.ThreadUtils;
import com.amazonaws.mobile.auth.core.signin.AuthException;
import com.amazonaws.mobile.auth.core.signin.CognitoAuthException;
import com.amazonaws.mobile.auth.core.signin.ProviderAuthException;
import com.amazonaws.mobile.auth.core.signin.SignInManager;
import com.amazonaws.mobile.auth.core.signin.SignInProvider;
import com.amazonaws.mobile.auth.core.signin.SignInProviderResultHandler;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class IdentityManager {
    private static final String AWS_CONFIGURATION_FILE = "awsconfiguration.json";
    private static final String EXPIRATION_KEY = "expirationDate";
    private static final String LOG_TAG = "IdentityManager";
    private static final String SHARED_PREF_NAME = "com.amazonaws.android.auth";
    private static IdentityManager defaultIdentityManager;
    private final Context appContext;
    private AWSConfiguration awsConfiguration;
    private AWSKeyValueStore awsKeyValueStore;
    private final ClientConfiguration clientConfiguration;
    private final AWSCredentialsProviderHolder credentialsProviderHolder;
    private SignInProviderResultAdapter resultsAdapter;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final CountDownLatch startupAuthTimeoutLatch = new CountDownLatch(1);
    private final Set<Class<? extends SignInProvider>> signInProviderClasses = new HashSet();
    private volatile IdentityProvider currentIdentityProvider = null;
    private final HashSet<SignInStateChangeListener> signInStateChangeListeners = new HashSet<>();
    private boolean isPersistenceEnabled = true;
    boolean shouldFederate = true;

    private class AWSCredentialsProviderHolder implements AWSCredentialsProvider {
        private volatile CognitoCachingCredentialsProvider underlyingProvider;

        private AWSCredentialsProviderHolder() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 com.amazonaws.mobile.auth.core.IdentityManager) A[MD:(com.amazonaws.mobile.auth.core.IdentityManager):void (m)] (LINE:74) call: com.amazonaws.mobile.auth.core.IdentityManager.AWSCredentialsProviderHolder.<init>(com.amazonaws.mobile.auth.core.IdentityManager):void type: THIS */
        /* synthetic */ AWSCredentialsProviderHolder(IdentityManager identityManager, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.amazonaws.auth.AWSCredentialsProvider
        public AWSCredentials getCredentials() {
            return this.underlyingProvider.getCredentials();
        }

        @Override // com.amazonaws.auth.AWSCredentialsProvider
        public void refresh() {
            this.underlyingProvider.refresh();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public CognitoCachingCredentialsProvider getUnderlyingProvider() {
            return this.underlyingProvider;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUnderlyingProvider(CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider) {
            this.underlyingProvider = cognitoCachingCredentialsProvider;
        }
    }

    private class AWSRefreshingCognitoIdentityProvider extends AWSBasicCognitoIdentityProvider {
        private final String LOG_TAG;

        public AWSRefreshingCognitoIdentityProvider(String str, String str2, ClientConfiguration clientConfiguration, Regions regions) {
            super(str, str2, clientConfiguration);
            this.LOG_TAG = AWSRefreshingCognitoIdentityProvider.class.getSimpleName();
            this.cib.setRegion(Region.getRegion(regions));
        }

        @Override // com.amazonaws.auth.AWSBasicCognitoIdentityProvider, com.amazonaws.auth.AWSAbstractCognitoIdentityProvider, com.amazonaws.auth.AWSIdentityProvider
        public String refresh() {
            if (IdentityManager.this.currentIdentityProvider != null) {
                Log.d(this.LOG_TAG, "Storing the Refresh token in the loginsMap.");
                getLogins().put(IdentityManager.this.currentIdentityProvider.getCognitoLoginKey(), IdentityManager.this.currentIdentityProvider.refreshToken());
            }
            return super.refresh();
        }
    }

    public IdentityManager(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.appContext = applicationContext;
        this.awsConfiguration = null;
        this.clientConfiguration = null;
        this.credentialsProviderHolder = null;
        this.awsKeyValueStore = new AWSKeyValueStore(applicationContext, SHARED_PREF_NAME, this.isPersistenceEnabled);
    }

    public IdentityManager(Context context, AWSConfiguration aWSConfiguration) {
        Context applicationContext = context.getApplicationContext();
        this.appContext = applicationContext;
        this.awsConfiguration = aWSConfiguration;
        ClientConfiguration clientConfigurationWithUserAgentOverride = new ClientConfiguration().withUserAgent(aWSConfiguration.getUserAgent()).withUserAgentOverride(aWSConfiguration.getUserAgentOverride());
        this.clientConfiguration = clientConfigurationWithUserAgentOverride;
        this.credentialsProviderHolder = new AWSCredentialsProviderHolder(this, null);
        createCredentialsProvider(applicationContext, clientConfigurationWithUserAgentOverride);
        this.awsKeyValueStore = new AWSKeyValueStore(applicationContext, SHARED_PREF_NAME, this.isPersistenceEnabled);
    }

    public IdentityManager(Context context, AWSConfiguration aWSConfiguration, ClientConfiguration clientConfiguration) {
        AnonymousClass1 anonymousClass1 = null;
        Context applicationContext = context.getApplicationContext();
        this.appContext = applicationContext;
        this.awsConfiguration = aWSConfiguration;
        this.clientConfiguration = clientConfiguration;
        String userAgent = aWSConfiguration.getUserAgent();
        String userAgent2 = clientConfiguration.getUserAgent();
        userAgent2 = userAgent2 == null ? "" : userAgent2;
        if (userAgent != null && userAgent != userAgent2) {
            clientConfiguration.setUserAgent(userAgent2.trim() + " " + userAgent);
        }
        this.credentialsProviderHolder = new AWSCredentialsProviderHolder(this, anonymousClass1);
        createCredentialsProvider(applicationContext, clientConfiguration);
        this.awsKeyValueStore = new AWSKeyValueStore(applicationContext, SHARED_PREF_NAME, this.isPersistenceEnabled);
    }

    public IdentityManager(Context context, CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider, ClientConfiguration clientConfiguration) {
        Context applicationContext = context.getApplicationContext();
        this.appContext = applicationContext;
        this.clientConfiguration = clientConfiguration;
        AWSCredentialsProviderHolder aWSCredentialsProviderHolder = new AWSCredentialsProviderHolder(this, null);
        this.credentialsProviderHolder = aWSCredentialsProviderHolder;
        aWSCredentialsProviderHolder.setUnderlyingProvider(cognitoCachingCredentialsProvider);
        this.awsKeyValueStore = new AWSKeyValueStore(applicationContext, SHARED_PREF_NAME, this.isPersistenceEnabled);
    }

    public void setPersistenceEnabled(boolean z) {
        this.isPersistenceEnabled = z;
        this.awsKeyValueStore.setPersistenceEnabled(z);
    }

    public void enableFederation(boolean z) {
        this.shouldFederate = z;
    }

    public static IdentityManager getDefaultIdentityManager() {
        return defaultIdentityManager;
    }

    public static void setDefaultIdentityManager(IdentityManager identityManager) {
        defaultIdentityManager = identityManager;
    }

    public AWSConfiguration getConfiguration() {
        return this.awsConfiguration;
    }

    public void setConfiguration(AWSConfiguration aWSConfiguration) {
        this.awsConfiguration = aWSConfiguration;
    }

    public boolean areCredentialsExpired() {
        if (this.shouldFederate) {
            Date sessionCredentialsExpiration = this.credentialsProviderHolder.getUnderlyingProvider().getSessionCredentialsExpiration();
            if (sessionCredentialsExpiration == null) {
                Log.d(LOG_TAG, "Credentials are EXPIRED.");
                return true;
            }
            boolean z = sessionCredentialsExpiration.getTime() - (System.currentTimeMillis() - (SDKGlobalConfiguration.getGlobalTimeOffset() * 1000)) < 0;
            Log.d(LOG_TAG, "Credentials are ".concat(z ? "EXPIRED." : "OK"));
            return z;
        }
        throw new IllegalStateException("Federation is not enabled and does not support credentials");
    }

    public AWSCredentialsProvider getCredentialsProvider() {
        return this.credentialsProviderHolder;
    }

    public CognitoCachingCredentialsProvider getUnderlyingProvider() {
        return this.credentialsProviderHolder.getUnderlyingProvider();
    }

    public String getCachedUserID() {
        if (this.shouldFederate) {
            return this.credentialsProviderHolder.getUnderlyingProvider().getCachedIdentityId();
        }
        throw new IllegalStateException("Federation is not enabled and does not support user id");
    }

    public void getUserID(IdentityHandler identityHandler) {
        if (!this.shouldFederate) {
            throw new IllegalStateException("Federation is not enabled and does not support user id");
        }
        this.executorService.submit(new AnonymousClass1(identityHandler));
    }

    /* JADX INFO: renamed from: com.amazonaws.mobile.auth.core.IdentityManager$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        Exception exception = null;
        final /* synthetic */ IdentityHandler val$handler;

        AnonymousClass1(IdentityHandler identityHandler) {
            this.val$handler = identityHandler;
        }

        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:21:0x0003 */
        /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r0v10, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r0v11, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r0v4, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r0v7, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r0v9, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r1v1, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r1v2, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r1v6, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r1v7, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r1v8, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r1v9, resolved type: java.lang.String */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            String str = "Got Amazon Cognito Federated Identity ID: null";
            final String str2 = 0;
            str2 = 0;
            str2 = 0;
            str2 = 0;
            try {
                try {
                    final String identityId = IdentityManager.this.credentialsProviderHolder.getUnderlyingProvider().getIdentityId();
                    Log.d(IdentityManager.LOG_TAG, "Got Amazon Cognito Federated Identity ID: " + identityId);
                    IdentityHandler identityHandler = this.val$handler;
                    str = identityId;
                    str2 = identityHandler;
                    if (identityHandler != null) {
                        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.amazonaws.mobile.auth.core.IdentityManager.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (AnonymousClass1.this.exception != null) {
                                    AnonymousClass1.this.val$handler.handleError(AnonymousClass1.this.exception);
                                } else {
                                    AnonymousClass1.this.val$handler.onIdentityId(identityId);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    this.exception = e;
                    Log.e(IdentityManager.LOG_TAG, e.getMessage(), e);
                    Log.d(IdentityManager.LOG_TAG, "Got Amazon Cognito Federated Identity ID: null");
                    IdentityHandler identityHandler2 = this.val$handler;
                    str = identityHandler2;
                    if (identityHandler2 != null) {
                        Runnable runnable = new Runnable() { // from class: com.amazonaws.mobile.auth.core.IdentityManager.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (AnonymousClass1.this.exception != null) {
                                    AnonymousClass1.this.val$handler.handleError(AnonymousClass1.this.exception);
                                } else {
                                    AnonymousClass1.this.val$handler.onIdentityId(str2);
                                }
                            }
                        };
                        ThreadUtils.runOnUiThread(runnable);
                        str = runnable;
                    }
                }
            } catch (Throwable th) {
                Log.d(IdentityManager.LOG_TAG, str);
                if (this.val$handler != null) {
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.amazonaws.mobile.auth.core.IdentityManager.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (AnonymousClass1.this.exception != null) {
                                AnonymousClass1.this.val$handler.handleError(AnonymousClass1.this.exception);
                            } else {
                                AnonymousClass1.this.val$handler.onIdentityId(str2);
                            }
                        }
                    });
                }
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public class SignInProviderResultAdapter implements SignInProviderResultHandler {
        private final SignInProviderResultHandler handler;

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR 
  (r1v0 com.amazonaws.mobile.auth.core.IdentityManager)
  (r2v0 com.amazonaws.mobile.auth.core.signin.SignInProviderResultHandler)
 A[MD:(com.amazonaws.mobile.auth.core.IdentityManager, com.amazonaws.mobile.auth.core.signin.SignInProviderResultHandler):void (m)] (LINE:439) call: com.amazonaws.mobile.auth.core.IdentityManager.SignInProviderResultAdapter.<init>(com.amazonaws.mobile.auth.core.IdentityManager, com.amazonaws.mobile.auth.core.signin.SignInProviderResultHandler):void type: THIS */
        /* synthetic */ SignInProviderResultAdapter(IdentityManager identityManager, SignInProviderResultHandler signInProviderResultHandler, AnonymousClass1 anonymousClass1) {
            this(signInProviderResultHandler);
        }

        private SignInProviderResultAdapter(SignInProviderResultHandler signInProviderResultHandler) {
            this.handler = signInProviderResultHandler;
        }

        @Override // com.amazonaws.mobile.auth.core.signin.SignInProviderResultHandler
        public void onSuccess(IdentityProvider identityProvider) {
            Log.d(IdentityManager.LOG_TAG, String.format("SignInProviderResultAdapter.onSuccess(): %s provider sign-in succeeded.", identityProvider.getDisplayName()));
            IdentityManager.this.federateWithProvider(identityProvider);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onCognitoSuccess() {
            Log.d(IdentityManager.LOG_TAG, "SignInProviderResultAdapter.onCognitoSuccess()");
            this.handler.onSuccess(IdentityManager.this.currentIdentityProvider);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onCognitoError(Exception exc) {
            Log.d(IdentityManager.LOG_TAG, "SignInProviderResultAdapter.onCognitoError()", exc);
            IdentityProvider identityProvider = IdentityManager.this.currentIdentityProvider;
            IdentityManager.this.signOut();
            this.handler.onError(identityProvider, new CognitoAuthException(identityProvider, exc));
        }

        @Override // com.amazonaws.mobile.auth.core.signin.SignInProviderResultHandler
        public void onCancel(IdentityProvider identityProvider) {
            Log.d(IdentityManager.LOG_TAG, String.format("SignInProviderResultAdapter.onCancel(): %s provider sign-in canceled.", identityProvider.getDisplayName()));
            this.handler.onCancel(identityProvider);
        }

        @Override // com.amazonaws.mobile.auth.core.signin.SignInProviderResultHandler
        public void onError(IdentityProvider identityProvider, Exception exc) {
            Log.e(IdentityManager.LOG_TAG, String.format("SignInProviderResultAdapter.onError(): %s provider error. %s", identityProvider.getDisplayName(), exc.getMessage()), exc);
            this.handler.onError(identityProvider, new ProviderAuthException(identityProvider, exc));
        }
    }

    public void addSignInStateChangeListener(SignInStateChangeListener signInStateChangeListener) {
        synchronized (this.signInStateChangeListeners) {
            this.signInStateChangeListeners.add(signInStateChangeListener);
        }
    }

    public void removeSignInStateChangeListener(SignInStateChangeListener signInStateChangeListener) {
        synchronized (this.signInStateChangeListeners) {
            this.signInStateChangeListeners.remove(signInStateChangeListener);
        }
    }

    public SignInProviderResultAdapter getResultsAdapter() {
        return this.resultsAdapter;
    }

    public void signOut() {
        Log.d(LOG_TAG, "Signing out...");
        if (this.currentIdentityProvider != null) {
            this.executorService.submit(new Runnable() { // from class: com.amazonaws.mobile.auth.core.IdentityManager.2
                @Override // java.lang.Runnable
                public void run() {
                    IdentityManager.this.currentIdentityProvider.signOut();
                    if (IdentityManager.this.shouldFederate) {
                        IdentityManager.this.credentialsProviderHolder.getUnderlyingProvider().clear();
                    }
                    IdentityManager.this.currentIdentityProvider = null;
                    synchronized (IdentityManager.this.signInStateChangeListeners) {
                        Iterator it = IdentityManager.this.signInStateChangeListeners.iterator();
                        while (it.hasNext()) {
                            ((SignInStateChangeListener) it.next()).onUserSignedOut();
                        }
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshCredentialWithLogins(Map<String, String> map) {
        CognitoCachingCredentialsProvider underlyingProvider = this.credentialsProviderHolder.getUnderlyingProvider();
        if (this.shouldFederate) {
            underlyingProvider.clear();
            underlyingProvider.withLogins(map);
            Log.d(LOG_TAG, "refresh credentials");
            underlyingProvider.refresh();
            this.awsKeyValueStore.put(underlyingProvider.getIdentityPoolId() + ".expirationDate", String.valueOf(System.currentTimeMillis() + 510000));
        }
    }

    public void setProviderResultsHandler(SignInProviderResultHandler signInProviderResultHandler) {
        if (signInProviderResultHandler == null) {
            throw new IllegalArgumentException("signInProviderResultHandler cannot be null.");
        }
        this.resultsAdapter = new SignInProviderResultAdapter(this, signInProviderResultHandler, null);
    }

    public void federateWithProvider(IdentityProvider identityProvider) {
        Log.d(LOG_TAG, "federate with provider: Populate loginsMap with token.");
        final HashMap map = new HashMap();
        map.put(identityProvider.getCognitoLoginKey(), identityProvider.getToken());
        this.currentIdentityProvider = identityProvider;
        this.executorService.submit(new Runnable() { // from class: com.amazonaws.mobile.auth.core.IdentityManager.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (IdentityManager.this.shouldFederate) {
                        IdentityManager.this.refreshCredentialWithLogins(map);
                    }
                    IdentityManager.this.resultsAdapter.onCognitoSuccess();
                    synchronized (IdentityManager.this.signInStateChangeListeners) {
                        Iterator it = IdentityManager.this.signInStateChangeListeners.iterator();
                        while (it.hasNext()) {
                            ((SignInStateChangeListener) it.next()).onUserSignedIn();
                        }
                    }
                } catch (Exception e) {
                    IdentityManager.this.resultsAdapter.onCognitoError(e);
                }
            }
        });
    }

    public IdentityProvider getCurrentIdentityProvider() {
        return this.currentIdentityProvider;
    }

    public void addSignInProvider(Class<? extends SignInProvider> cls) {
        this.signInProviderClasses.add(cls);
    }

    public Collection<Class<? extends SignInProvider>> getSignInProviderClasses() {
        return this.signInProviderClasses;
    }

    public boolean isUserSignedIn() {
        Map<String, String> logins = this.credentialsProviderHolder.getUnderlyingProvider().getLogins();
        return (logins == null || logins.size() == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void completeHandler(Activity activity, final StartupAuthResultHandler startupAuthResultHandler, final AuthException authException) {
        runAfterStartupAuthDelay(activity, new Runnable() { // from class: com.amazonaws.mobile.auth.core.IdentityManager.4
            @Override // java.lang.Runnable
            public void run() {
                startupAuthResultHandler.onComplete(new StartupAuthResult(IdentityManager.this, new StartupAuthErrorDetails(authException, null)));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runAfterStartupAuthDelay(final Activity activity, final Runnable runnable) {
        this.executorService.submit(new Runnable() { // from class: com.amazonaws.mobile.auth.core.IdentityManager.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    IdentityManager.this.startupAuthTimeoutLatch.await();
                } catch (InterruptedException unused) {
                    Log.d(IdentityManager.LOG_TAG, "Interrupted while waiting for startup auth minimum delay.");
                }
                activity.runOnUiThread(runnable);
            }
        });
    }

    public void resumeSession(Activity activity, StartupAuthResultHandler startupAuthResultHandler, long j) {
        Log.d(LOG_TAG, "Resume Session called.");
        this.executorService.submit(new AnonymousClass6(activity, startupAuthResultHandler, j));
    }

    /* JADX INFO: renamed from: com.amazonaws.mobile.auth.core.IdentityManager$6, reason: invalid class name */
    class AnonymousClass6 implements Runnable {
        final /* synthetic */ Activity val$callingActivity;
        final /* synthetic */ long val$minimumDelay;
        final /* synthetic */ StartupAuthResultHandler val$startupAuthResultHandler;

        AnonymousClass6(Activity activity, StartupAuthResultHandler startupAuthResultHandler, long j) {
            this.val$callingActivity = activity;
            this.val$startupAuthResultHandler = startupAuthResultHandler;
            this.val$minimumDelay = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            Log.d(IdentityManager.LOG_TAG, "Looking for a previously signed-in session.");
            SignInManager signInManager = SignInManager.getInstance(this.val$callingActivity.getApplicationContext());
            SignInProvider previouslySignedInProvider = signInManager.getPreviouslySignedInProvider();
            if (previouslySignedInProvider != null) {
                Log.d(IdentityManager.LOG_TAG, "Refreshing credentials with sign-in provider " + previouslySignedInProvider.getDisplayName());
                signInManager.refreshCredentialsWithProvider(this.val$callingActivity, previouslySignedInProvider, new SignInProviderResultHandler() { // from class: com.amazonaws.mobile.auth.core.IdentityManager.6.1
                    @Override // com.amazonaws.mobile.auth.core.signin.SignInProviderResultHandler
                    public void onSuccess(IdentityProvider identityProvider) {
                        Log.d(IdentityManager.LOG_TAG, "Successfully got AWS Credentials.");
                        IdentityManager.this.runAfterStartupAuthDelay(AnonymousClass6.this.val$callingActivity, new Runnable() { // from class: com.amazonaws.mobile.auth.core.IdentityManager.6.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass6.this.val$startupAuthResultHandler.onComplete(new StartupAuthResult(IdentityManager.this, null));
                            }
                        });
                    }

                    @Override // com.amazonaws.mobile.auth.core.signin.SignInProviderResultHandler
                    public void onCancel(IdentityProvider identityProvider) {
                        Log.wtf(IdentityManager.LOG_TAG, "Cancel can't happen when handling a previously signed-in user.");
                    }

                    @Override // com.amazonaws.mobile.auth.core.signin.SignInProviderResultHandler
                    public void onError(IdentityProvider identityProvider, Exception exc) {
                        if (identityProvider != null) {
                            Log.e(IdentityManager.LOG_TAG, String.format("Federate with Cognito with %s Sign-in provider failed. Error: %s", identityProvider.getDisplayName(), exc.getMessage()), exc);
                        } else {
                            Log.e(IdentityManager.LOG_TAG, String.format("Federate with Cognito failed. Error: %s", exc.getMessage()), exc);
                        }
                        if (exc instanceof AuthException) {
                            IdentityManager.this.completeHandler(AnonymousClass6.this.val$callingActivity, AnonymousClass6.this.val$startupAuthResultHandler, (AuthException) exc);
                        } else {
                            IdentityManager.this.completeHandler(AnonymousClass6.this.val$callingActivity, AnonymousClass6.this.val$startupAuthResultHandler, new AuthException(identityProvider, exc));
                        }
                    }
                });
            } else {
                IdentityManager.this.completeHandler(this.val$callingActivity, this.val$startupAuthResultHandler, null);
            }
            long j = this.val$minimumDelay;
            if (j > 0) {
                try {
                    Thread.sleep(j);
                } catch (InterruptedException unused) {
                    Log.i(IdentityManager.LOG_TAG, "Interrupted while waiting for resume session timeout.");
                }
            }
            IdentityManager.this.startupAuthTimeoutLatch.countDown();
        }
    }

    public void resumeSession(Activity activity, StartupAuthResultHandler startupAuthResultHandler) {
        resumeSession(activity, startupAuthResultHandler, 0L);
    }

    @Deprecated
    public void doStartupAuth(Activity activity, StartupAuthResultHandler startupAuthResultHandler) {
        resumeSession(activity, startupAuthResultHandler, 0L);
    }

    @Deprecated
    public void doStartupAuth(Activity activity, StartupAuthResultHandler startupAuthResultHandler, long j) {
        resumeSession(activity, startupAuthResultHandler, j);
    }

    public void expireSignInTimeout() {
        this.startupAuthTimeoutLatch.countDown();
    }

    @Deprecated
    public void setUpToAuthenticate(Context context, SignInResultHandler signInResultHandler) {
        login(context, signInResultHandler);
    }

    public void login(Context context, SignInResultHandler signInResultHandler) {
        try {
            SignInManager.getInstance(context.getApplicationContext()).setResultHandler(signInResultHandler);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in instantiating SignInManager. Check the context and completion handler.", e);
        }
    }

    private void createCredentialsProvider(Context context, ClientConfiguration clientConfiguration) {
        Log.d(LOG_TAG, "Creating the Cognito Caching Credentials Provider with a refreshing Cognito Identity Provider.");
        if (this.shouldFederate) {
            JSONObject cognitoIdentityPoolConfig = getCognitoIdentityPoolConfig();
            try {
                String string = cognitoIdentityPoolConfig.getString("Region");
                String string2 = cognitoIdentityPoolConfig.getString("PoolId");
                Regions regionsFromName = Regions.fromName(string);
                CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider = new CognitoCachingCredentialsProvider(context, new AWSRefreshingCognitoIdentityProvider(null, string2, clientConfiguration, regionsFromName), regionsFromName, clientConfiguration);
                cognitoCachingCredentialsProvider.setPersistenceEnabled(this.isPersistenceEnabled);
                if (clientConfiguration.getUserAgentOverride() != null) {
                    cognitoCachingCredentialsProvider.setUserAgentOverride(clientConfiguration.getUserAgentOverride());
                }
                this.credentialsProviderHolder.setUnderlyingProvider(cognitoCachingCredentialsProvider);
            } catch (JSONException e) {
                throw new IllegalArgumentException("Failed to read configuration for CognitoIdentity", e);
            }
        }
    }

    private JSONObject getCognitoIdentityPoolConfig() throws IllegalArgumentException {
        try {
            return this.awsConfiguration.optJsonObject("CredentialsProvider").getJSONObject("CognitoIdentity").getJSONObject(this.awsConfiguration.getConfiguration());
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot access Cognito IdentityPoolId from the awsconfiguration.json file.", e);
        }
    }
}

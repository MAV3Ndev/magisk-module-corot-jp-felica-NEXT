package com.amazonaws.internal.config;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.regions.ServiceAbbreviations;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class InternalConfig {
    private static final String SERVICE_REGION_DELIMITOR = "/";
    private static final Log log = LogFactory.getLog((Class<?>) InternalConfig.class);
    private final SignerConfig defaultSignerConfig = getDefaultSigner();
    private final Map<String, SignerConfig> regionSigners = getDefaultRegionSigners();
    private final Map<String, SignerConfig> serviceSigners = getDefaultServiceSigners();
    private final Map<String, SignerConfig> serviceRegionSigners = getDefaultServiceRegionSigners();
    private final Map<String, HttpClientConfig> httpClients = getDefaultHttpClients();
    private final List<HostRegexToRegionMapping> hostRegexToRegionMappings = getDefaultHostRegexToRegionMappings();

    InternalConfig() {
    }

    public SignerConfig getSignerConfig(String str) {
        return getSignerConfig(str, null);
    }

    public HttpClientConfig getHttpClientConfig(String str) {
        return this.httpClients.get(str);
    }

    public SignerConfig getSignerConfig(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException();
        }
        if (str2 != null) {
            SignerConfig signerConfig = this.serviceRegionSigners.get(str + SERVICE_REGION_DELIMITOR + str2);
            if (signerConfig != null) {
                return signerConfig;
            }
            SignerConfig signerConfig2 = this.regionSigners.get(str2);
            if (signerConfig2 != null) {
                return signerConfig2;
            }
        }
        SignerConfig signerConfig3 = this.serviceSigners.get(str);
        return signerConfig3 == null ? this.defaultSignerConfig : signerConfig3;
    }

    public List<HostRegexToRegionMapping> getHostRegexToRegionMappings() {
        return Collections.unmodifiableList(this.hostRegexToRegionMappings);
    }

    private static Map<String, HttpClientConfig> getDefaultHttpClients() {
        HashMap map = new HashMap();
        map.put("AmazonCloudWatchClient", new HttpClientConfig(ServiceAbbreviations.CloudWatch));
        map.put("AmazonCloudWatchLogsClient", new HttpClientConfig("logs"));
        map.put("AmazonCognitoIdentityClient", new HttpClientConfig("cognito-identity"));
        map.put("AmazonCognitoIdentityProviderClient", new HttpClientConfig("cognito-idp"));
        map.put("AmazonCognitoSyncClient", new HttpClientConfig("cognito-sync"));
        map.put("AmazonComprehendClient", new HttpClientConfig("comprehend"));
        map.put("AmazonConnectClient", new HttpClientConfig("connect"));
        map.put("AmazonKinesisFirehoseClient", new HttpClientConfig("firehose"));
        map.put("AWSKinesisVideoArchivedMediaClient", new HttpClientConfig("kinesisvideo"));
        map.put("AWSKinesisVideoSignalingClient", new HttpClientConfig("kinesisvideo"));
        map.put("AWSIotClient", new HttpClientConfig("execute-api"));
        map.put("AmazonLexRuntimeClient", new HttpClientConfig("lex"));
        map.put("AmazonPinpointClient", new HttpClientConfig("mobiletargeting"));
        map.put("AmazonPinpointAnalyticsClient", new HttpClientConfig("mobileanalytics"));
        map.put("AmazonSageMakerRuntimeClient", new HttpClientConfig("sagemaker"));
        map.put("AmazonSimpleDBClient", new HttpClientConfig(ServiceAbbreviations.SimpleDB));
        map.put("AmazonSimpleEmailServiceClient", new HttpClientConfig("email"));
        map.put("AWSSecurityTokenServiceClient", new HttpClientConfig(ServiceAbbreviations.STS));
        map.put("AmazonTextractClient", new HttpClientConfig("textract"));
        map.put("AmazonTranscribeClient", new HttpClientConfig("transcribe"));
        map.put("AmazonTranslateClient", new HttpClientConfig("translate"));
        return map;
    }

    private static Map<String, SignerConfig> getDefaultRegionSigners() {
        HashMap map = new HashMap();
        map.put("eu-central-1", new SignerConfig("AWS4SignerType"));
        map.put("cn-north-1", new SignerConfig("AWS4SignerType"));
        return map;
    }

    private static Map<String, SignerConfig> getDefaultServiceRegionSigners() {
        HashMap map = new HashMap();
        map.put("s3/eu-central-1", new SignerConfig("AWSS3V4SignerType"));
        map.put("s3/cn-north-1", new SignerConfig("AWSS3V4SignerType"));
        map.put("s3/us-east-2", new SignerConfig("AWSS3V4SignerType"));
        map.put("s3/ca-central-1", new SignerConfig("AWSS3V4SignerType"));
        map.put("s3/ap-south-1", new SignerConfig("AWSS3V4SignerType"));
        map.put("s3/ap-northeast-2", new SignerConfig("AWSS3V4SignerType"));
        map.put("s3/eu-west-2", new SignerConfig("AWSS3V4SignerType"));
        map.put("lex/eu-central-1", new SignerConfig("AmazonLexV4Signer"));
        map.put("lex/cn-north-1", new SignerConfig("AmazonLexV4Signer"));
        map.put("polly/eu-central-1", new SignerConfig("AmazonPollyCustomPresigner"));
        map.put("polly/cn-north-1", new SignerConfig("AmazonPollyCustomPresigner"));
        return map;
    }

    private static Map<String, SignerConfig> getDefaultServiceSigners() {
        HashMap map = new HashMap();
        map.put(ServiceAbbreviations.EC2, new SignerConfig("QueryStringSignerType"));
        map.put("email", new SignerConfig("AWS4SignerType"));
        map.put(ServiceAbbreviations.S3, new SignerConfig("AWSS3V4SignerType"));
        map.put(ServiceAbbreviations.SimpleDB, new SignerConfig("QueryStringSignerType"));
        map.put("lex", new SignerConfig("AmazonLexV4Signer"));
        map.put("polly", new SignerConfig("AmazonPollyCustomPresigner"));
        return map;
    }

    private static SignerConfig getDefaultSigner() {
        return new SignerConfig("AWS4SignerType");
    }

    private static List<HostRegexToRegionMapping> getDefaultHostRegexToRegionMappings() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new HostRegexToRegionMapping("(.+\\.)?s3\\.amazonaws\\.com", "us-east-1"));
        arrayList.add(new HostRegexToRegionMapping("(.+\\.)?s3-external-1\\.amazonaws\\.com", "us-east-1"));
        arrayList.add(new HostRegexToRegionMapping("(.+\\.)?s3-fips-us-gov-west-1\\.amazonaws\\.com", "us-gov-west-1"));
        return arrayList;
    }

    void dump() {
        log.debug("defaultSignerConfig: " + this.defaultSignerConfig + "\nserviceRegionSigners: " + this.serviceRegionSigners + "\nregionSigners: " + this.regionSigners + "\nserviceSigners: " + this.serviceSigners + "\nhostRegexToRegionMappings: " + this.hostRegexToRegionMappings);
    }

    public static class Factory {
        private static final InternalConfig SINGELTON;

        static {
            try {
                SINGELTON = new InternalConfig();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new IllegalStateException("Fatal: Failed to load the internal config for AWS Android SDK", e2);
            }
        }

        public static InternalConfig getInternalConfig() {
            return SINGELTON;
        }
    }
}

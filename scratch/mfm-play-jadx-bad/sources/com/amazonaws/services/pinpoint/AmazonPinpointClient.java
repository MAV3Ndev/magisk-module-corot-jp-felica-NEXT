package com.amazonaws.services.pinpoint;

import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.handlers.HandlerChainFactory;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpClient;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.http.JsonResponseHandler;
import com.amazonaws.http.UrlHttpClient;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.services.pinpoint.model.CreateAppRequest;
import com.amazonaws.services.pinpoint.model.CreateAppResult;
import com.amazonaws.services.pinpoint.model.CreateCampaignRequest;
import com.amazonaws.services.pinpoint.model.CreateCampaignResult;
import com.amazonaws.services.pinpoint.model.CreateExportJobRequest;
import com.amazonaws.services.pinpoint.model.CreateExportJobResult;
import com.amazonaws.services.pinpoint.model.CreateImportJobRequest;
import com.amazonaws.services.pinpoint.model.CreateImportJobResult;
import com.amazonaws.services.pinpoint.model.CreateSegmentRequest;
import com.amazonaws.services.pinpoint.model.CreateSegmentResult;
import com.amazonaws.services.pinpoint.model.DeleteAdmChannelRequest;
import com.amazonaws.services.pinpoint.model.DeleteAdmChannelResult;
import com.amazonaws.services.pinpoint.model.DeleteApnsChannelRequest;
import com.amazonaws.services.pinpoint.model.DeleteApnsChannelResult;
import com.amazonaws.services.pinpoint.model.DeleteApnsSandboxChannelRequest;
import com.amazonaws.services.pinpoint.model.DeleteApnsSandboxChannelResult;
import com.amazonaws.services.pinpoint.model.DeleteApnsVoipChannelRequest;
import com.amazonaws.services.pinpoint.model.DeleteApnsVoipChannelResult;
import com.amazonaws.services.pinpoint.model.DeleteApnsVoipSandboxChannelRequest;
import com.amazonaws.services.pinpoint.model.DeleteApnsVoipSandboxChannelResult;
import com.amazonaws.services.pinpoint.model.DeleteAppRequest;
import com.amazonaws.services.pinpoint.model.DeleteAppResult;
import com.amazonaws.services.pinpoint.model.DeleteBaiduChannelRequest;
import com.amazonaws.services.pinpoint.model.DeleteBaiduChannelResult;
import com.amazonaws.services.pinpoint.model.DeleteCampaignRequest;
import com.amazonaws.services.pinpoint.model.DeleteCampaignResult;
import com.amazonaws.services.pinpoint.model.DeleteEmailChannelRequest;
import com.amazonaws.services.pinpoint.model.DeleteEmailChannelResult;
import com.amazonaws.services.pinpoint.model.DeleteEndpointRequest;
import com.amazonaws.services.pinpoint.model.DeleteEndpointResult;
import com.amazonaws.services.pinpoint.model.DeleteEventStreamRequest;
import com.amazonaws.services.pinpoint.model.DeleteEventStreamResult;
import com.amazonaws.services.pinpoint.model.DeleteGcmChannelRequest;
import com.amazonaws.services.pinpoint.model.DeleteGcmChannelResult;
import com.amazonaws.services.pinpoint.model.DeleteSegmentRequest;
import com.amazonaws.services.pinpoint.model.DeleteSegmentResult;
import com.amazonaws.services.pinpoint.model.DeleteSmsChannelRequest;
import com.amazonaws.services.pinpoint.model.DeleteSmsChannelResult;
import com.amazonaws.services.pinpoint.model.DeleteUserEndpointsRequest;
import com.amazonaws.services.pinpoint.model.DeleteUserEndpointsResult;
import com.amazonaws.services.pinpoint.model.DeleteVoiceChannelRequest;
import com.amazonaws.services.pinpoint.model.DeleteVoiceChannelResult;
import com.amazonaws.services.pinpoint.model.GetAdmChannelRequest;
import com.amazonaws.services.pinpoint.model.GetAdmChannelResult;
import com.amazonaws.services.pinpoint.model.GetApnsChannelRequest;
import com.amazonaws.services.pinpoint.model.GetApnsChannelResult;
import com.amazonaws.services.pinpoint.model.GetApnsSandboxChannelRequest;
import com.amazonaws.services.pinpoint.model.GetApnsSandboxChannelResult;
import com.amazonaws.services.pinpoint.model.GetApnsVoipChannelRequest;
import com.amazonaws.services.pinpoint.model.GetApnsVoipChannelResult;
import com.amazonaws.services.pinpoint.model.GetApnsVoipSandboxChannelRequest;
import com.amazonaws.services.pinpoint.model.GetApnsVoipSandboxChannelResult;
import com.amazonaws.services.pinpoint.model.GetAppRequest;
import com.amazonaws.services.pinpoint.model.GetAppResult;
import com.amazonaws.services.pinpoint.model.GetApplicationSettingsRequest;
import com.amazonaws.services.pinpoint.model.GetApplicationSettingsResult;
import com.amazonaws.services.pinpoint.model.GetAppsRequest;
import com.amazonaws.services.pinpoint.model.GetAppsResult;
import com.amazonaws.services.pinpoint.model.GetBaiduChannelRequest;
import com.amazonaws.services.pinpoint.model.GetBaiduChannelResult;
import com.amazonaws.services.pinpoint.model.GetCampaignActivitiesRequest;
import com.amazonaws.services.pinpoint.model.GetCampaignActivitiesResult;
import com.amazonaws.services.pinpoint.model.GetCampaignRequest;
import com.amazonaws.services.pinpoint.model.GetCampaignResult;
import com.amazonaws.services.pinpoint.model.GetCampaignVersionRequest;
import com.amazonaws.services.pinpoint.model.GetCampaignVersionResult;
import com.amazonaws.services.pinpoint.model.GetCampaignVersionsRequest;
import com.amazonaws.services.pinpoint.model.GetCampaignVersionsResult;
import com.amazonaws.services.pinpoint.model.GetCampaignsRequest;
import com.amazonaws.services.pinpoint.model.GetCampaignsResult;
import com.amazonaws.services.pinpoint.model.GetChannelsRequest;
import com.amazonaws.services.pinpoint.model.GetChannelsResult;
import com.amazonaws.services.pinpoint.model.GetEmailChannelRequest;
import com.amazonaws.services.pinpoint.model.GetEmailChannelResult;
import com.amazonaws.services.pinpoint.model.GetEndpointRequest;
import com.amazonaws.services.pinpoint.model.GetEndpointResult;
import com.amazonaws.services.pinpoint.model.GetEventStreamRequest;
import com.amazonaws.services.pinpoint.model.GetEventStreamResult;
import com.amazonaws.services.pinpoint.model.GetExportJobRequest;
import com.amazonaws.services.pinpoint.model.GetExportJobResult;
import com.amazonaws.services.pinpoint.model.GetExportJobsRequest;
import com.amazonaws.services.pinpoint.model.GetExportJobsResult;
import com.amazonaws.services.pinpoint.model.GetGcmChannelRequest;
import com.amazonaws.services.pinpoint.model.GetGcmChannelResult;
import com.amazonaws.services.pinpoint.model.GetImportJobRequest;
import com.amazonaws.services.pinpoint.model.GetImportJobResult;
import com.amazonaws.services.pinpoint.model.GetImportJobsRequest;
import com.amazonaws.services.pinpoint.model.GetImportJobsResult;
import com.amazonaws.services.pinpoint.model.GetSegmentExportJobsRequest;
import com.amazonaws.services.pinpoint.model.GetSegmentExportJobsResult;
import com.amazonaws.services.pinpoint.model.GetSegmentImportJobsRequest;
import com.amazonaws.services.pinpoint.model.GetSegmentImportJobsResult;
import com.amazonaws.services.pinpoint.model.GetSegmentRequest;
import com.amazonaws.services.pinpoint.model.GetSegmentResult;
import com.amazonaws.services.pinpoint.model.GetSegmentVersionRequest;
import com.amazonaws.services.pinpoint.model.GetSegmentVersionResult;
import com.amazonaws.services.pinpoint.model.GetSegmentVersionsRequest;
import com.amazonaws.services.pinpoint.model.GetSegmentVersionsResult;
import com.amazonaws.services.pinpoint.model.GetSegmentsRequest;
import com.amazonaws.services.pinpoint.model.GetSegmentsResult;
import com.amazonaws.services.pinpoint.model.GetSmsChannelRequest;
import com.amazonaws.services.pinpoint.model.GetSmsChannelResult;
import com.amazonaws.services.pinpoint.model.GetUserEndpointsRequest;
import com.amazonaws.services.pinpoint.model.GetUserEndpointsResult;
import com.amazonaws.services.pinpoint.model.GetVoiceChannelRequest;
import com.amazonaws.services.pinpoint.model.GetVoiceChannelResult;
import com.amazonaws.services.pinpoint.model.PhoneNumberValidateRequest;
import com.amazonaws.services.pinpoint.model.PhoneNumberValidateResult;
import com.amazonaws.services.pinpoint.model.PutEventStreamRequest;
import com.amazonaws.services.pinpoint.model.PutEventStreamResult;
import com.amazonaws.services.pinpoint.model.PutEventsRequest;
import com.amazonaws.services.pinpoint.model.PutEventsResult;
import com.amazonaws.services.pinpoint.model.RemoveAttributesRequest;
import com.amazonaws.services.pinpoint.model.RemoveAttributesResult;
import com.amazonaws.services.pinpoint.model.SendMessagesRequest;
import com.amazonaws.services.pinpoint.model.SendMessagesResult;
import com.amazonaws.services.pinpoint.model.SendUsersMessagesRequest;
import com.amazonaws.services.pinpoint.model.SendUsersMessagesResult;
import com.amazonaws.services.pinpoint.model.UpdateAdmChannelRequest;
import com.amazonaws.services.pinpoint.model.UpdateAdmChannelResult;
import com.amazonaws.services.pinpoint.model.UpdateApnsChannelRequest;
import com.amazonaws.services.pinpoint.model.UpdateApnsChannelResult;
import com.amazonaws.services.pinpoint.model.UpdateApnsSandboxChannelRequest;
import com.amazonaws.services.pinpoint.model.UpdateApnsSandboxChannelResult;
import com.amazonaws.services.pinpoint.model.UpdateApnsVoipChannelRequest;
import com.amazonaws.services.pinpoint.model.UpdateApnsVoipChannelResult;
import com.amazonaws.services.pinpoint.model.UpdateApnsVoipSandboxChannelRequest;
import com.amazonaws.services.pinpoint.model.UpdateApnsVoipSandboxChannelResult;
import com.amazonaws.services.pinpoint.model.UpdateApplicationSettingsRequest;
import com.amazonaws.services.pinpoint.model.UpdateApplicationSettingsResult;
import com.amazonaws.services.pinpoint.model.UpdateBaiduChannelRequest;
import com.amazonaws.services.pinpoint.model.UpdateBaiduChannelResult;
import com.amazonaws.services.pinpoint.model.UpdateCampaignRequest;
import com.amazonaws.services.pinpoint.model.UpdateCampaignResult;
import com.amazonaws.services.pinpoint.model.UpdateEmailChannelRequest;
import com.amazonaws.services.pinpoint.model.UpdateEmailChannelResult;
import com.amazonaws.services.pinpoint.model.UpdateEndpointRequest;
import com.amazonaws.services.pinpoint.model.UpdateEndpointResult;
import com.amazonaws.services.pinpoint.model.UpdateEndpointsBatchRequest;
import com.amazonaws.services.pinpoint.model.UpdateEndpointsBatchResult;
import com.amazonaws.services.pinpoint.model.UpdateGcmChannelRequest;
import com.amazonaws.services.pinpoint.model.UpdateGcmChannelResult;
import com.amazonaws.services.pinpoint.model.UpdateSegmentRequest;
import com.amazonaws.services.pinpoint.model.UpdateSegmentResult;
import com.amazonaws.services.pinpoint.model.UpdateSmsChannelRequest;
import com.amazonaws.services.pinpoint.model.UpdateSmsChannelResult;
import com.amazonaws.services.pinpoint.model.UpdateVoiceChannelRequest;
import com.amazonaws.services.pinpoint.model.UpdateVoiceChannelResult;
import com.amazonaws.services.pinpoint.model.transform.BadRequestExceptionUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.CreateAppRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.CreateAppResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.CreateCampaignRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.CreateCampaignResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.CreateExportJobRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.CreateExportJobResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.CreateImportJobRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.CreateImportJobResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.CreateSegmentRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.CreateSegmentResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteAdmChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteAdmChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteApnsChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteApnsChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteApnsSandboxChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteApnsSandboxChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteApnsVoipChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteApnsVoipChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteApnsVoipSandboxChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteApnsVoipSandboxChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteAppRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteAppResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteBaiduChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteBaiduChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteCampaignRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteCampaignResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteEmailChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteEmailChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteEndpointRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteEndpointResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteEventStreamRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteEventStreamResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteGcmChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteGcmChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteSegmentRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteSegmentResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteSmsChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteSmsChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteUserEndpointsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteUserEndpointsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteVoiceChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.DeleteVoiceChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.ForbiddenExceptionUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetAdmChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetAdmChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetApnsChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetApnsChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetApnsSandboxChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetApnsSandboxChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetApnsVoipChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetApnsVoipChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetApnsVoipSandboxChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetApnsVoipSandboxChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetAppRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetAppResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetApplicationSettingsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetApplicationSettingsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetAppsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetAppsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetBaiduChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetBaiduChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetCampaignActivitiesRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetCampaignActivitiesResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetCampaignRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetCampaignResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetCampaignVersionRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetCampaignVersionResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetCampaignVersionsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetCampaignVersionsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetCampaignsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetCampaignsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetChannelsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetChannelsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetEmailChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetEmailChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetEndpointRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetEndpointResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetEventStreamRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetEventStreamResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetExportJobRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetExportJobResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetExportJobsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetExportJobsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetGcmChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetGcmChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetImportJobRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetImportJobResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetImportJobsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetImportJobsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSegmentExportJobsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSegmentExportJobsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSegmentImportJobsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSegmentImportJobsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSegmentRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSegmentResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSegmentVersionRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSegmentVersionResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSegmentVersionsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSegmentVersionsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSegmentsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSegmentsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSmsChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetSmsChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetUserEndpointsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetUserEndpointsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetVoiceChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.GetVoiceChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.InternalServerErrorExceptionUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.MethodNotAllowedExceptionUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.NotFoundExceptionUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.PhoneNumberValidateRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.PhoneNumberValidateResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.PutEventStreamRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.PutEventStreamResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.PutEventsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.PutEventsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.RemoveAttributesRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.RemoveAttributesResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.SendMessagesRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.SendMessagesResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.SendUsersMessagesRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.SendUsersMessagesResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.TooManyRequestsExceptionUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateAdmChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateAdmChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateApnsChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateApnsChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateApnsSandboxChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateApnsSandboxChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateApnsVoipChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateApnsVoipChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateApnsVoipSandboxChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateApnsVoipSandboxChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateApplicationSettingsRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateApplicationSettingsResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateBaiduChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateBaiduChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateCampaignRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateCampaignResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateEmailChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateEmailChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateEndpointRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateEndpointResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateEndpointsBatchRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateEndpointsBatchResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateGcmChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateGcmChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateSegmentRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateSegmentResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateSmsChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateSmsChannelResultJsonUnmarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateVoiceChannelRequestMarshaller;
import com.amazonaws.services.pinpoint.model.transform.UpdateVoiceChannelResultJsonUnmarshaller;
import com.amazonaws.transform.JsonErrorUnmarshaller;
import com.amazonaws.util.AWSRequestMetrics;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AmazonPinpointClient extends AmazonWebServiceClient implements AmazonPinpoint {
    private AWSCredentialsProvider awsCredentialsProvider;
    protected List<JsonErrorUnmarshaller> jsonErrorUnmarshallers;

    private static ClientConfiguration adjustClientConfiguration(ClientConfiguration clientConfiguration) {
        return clientConfiguration;
    }

    @Deprecated
    public AmazonPinpointClient() {
        this(new DefaultAWSCredentialsProviderChain(), new ClientConfiguration());
    }

    @Deprecated
    public AmazonPinpointClient(ClientConfiguration clientConfiguration) {
        this(new DefaultAWSCredentialsProviderChain(), clientConfiguration);
    }

    public AmazonPinpointClient(AWSCredentials aWSCredentials) {
        this(aWSCredentials, new ClientConfiguration());
    }

    public AmazonPinpointClient(AWSCredentials aWSCredentials, ClientConfiguration clientConfiguration) {
        this(new StaticCredentialsProvider(aWSCredentials), clientConfiguration);
    }

    public AmazonPinpointClient(AWSCredentialsProvider aWSCredentialsProvider) {
        this(aWSCredentialsProvider, new ClientConfiguration());
    }

    public AmazonPinpointClient(AWSCredentialsProvider aWSCredentialsProvider, ClientConfiguration clientConfiguration) {
        this(aWSCredentialsProvider, clientConfiguration, new UrlHttpClient(clientConfiguration));
    }

    @Deprecated
    public AmazonPinpointClient(AWSCredentialsProvider aWSCredentialsProvider, ClientConfiguration clientConfiguration, RequestMetricCollector requestMetricCollector) {
        super(adjustClientConfiguration(clientConfiguration), requestMetricCollector);
        this.awsCredentialsProvider = aWSCredentialsProvider;
        init();
    }

    public AmazonPinpointClient(AWSCredentialsProvider aWSCredentialsProvider, ClientConfiguration clientConfiguration, HttpClient httpClient) {
        super(adjustClientConfiguration(clientConfiguration), httpClient);
        this.awsCredentialsProvider = aWSCredentialsProvider;
        init();
    }

    private void init() {
        ArrayList arrayList = new ArrayList();
        this.jsonErrorUnmarshallers = arrayList;
        arrayList.add(new BadRequestExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ForbiddenExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InternalServerErrorExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new MethodNotAllowedExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new NotFoundExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new TooManyRequestsExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new JsonErrorUnmarshaller());
        setEndpoint("pinpoint.us-east-1.amazonaws.com");
        this.endpointPrefix = "pinpoint";
        HandlerChainFactory handlerChainFactory = new HandlerChainFactory();
        this.requestHandler2s.addAll(handlerChainFactory.newRequestHandlerChain("/com/amazonaws/services/pinpoint/request.handlers"));
        this.requestHandler2s.addAll(handlerChainFactory.newRequestHandler2Chain("/com/amazonaws/services/pinpoint/request.handler2s"));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public CreateAppResult createApp(CreateAppRequest createAppRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(createAppRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<CreateAppRequest> requestMarshall2 = new CreateAppRequestMarshaller().marshall(createAppRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new CreateAppResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        CreateAppResult createAppResult = (CreateAppResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return createAppResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = createAppRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public CreateCampaignResult createCampaign(CreateCampaignRequest createCampaignRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(createCampaignRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<CreateCampaignRequest> requestMarshall2 = new CreateCampaignRequestMarshaller().marshall(createCampaignRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new CreateCampaignResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        CreateCampaignResult createCampaignResult = (CreateCampaignResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return createCampaignResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = createCampaignRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public CreateExportJobResult createExportJob(CreateExportJobRequest createExportJobRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(createExportJobRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<CreateExportJobRequest> requestMarshall2 = new CreateExportJobRequestMarshaller().marshall(createExportJobRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new CreateExportJobResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        CreateExportJobResult createExportJobResult = (CreateExportJobResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return createExportJobResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = createExportJobRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public CreateImportJobResult createImportJob(CreateImportJobRequest createImportJobRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(createImportJobRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<CreateImportJobRequest> requestMarshall2 = new CreateImportJobRequestMarshaller().marshall(createImportJobRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new CreateImportJobResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        CreateImportJobResult createImportJobResult = (CreateImportJobResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return createImportJobResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = createImportJobRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public CreateSegmentResult createSegment(CreateSegmentRequest createSegmentRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(createSegmentRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<CreateSegmentRequest> requestMarshall2 = new CreateSegmentRequestMarshaller().marshall(createSegmentRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new CreateSegmentResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        CreateSegmentResult createSegmentResult = (CreateSegmentResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return createSegmentResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = createSegmentRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteAdmChannelResult deleteAdmChannel(DeleteAdmChannelRequest deleteAdmChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteAdmChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteAdmChannelRequest> requestMarshall = new DeleteAdmChannelRequestMarshaller().marshall(deleteAdmChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteAdmChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteAdmChannelResult deleteAdmChannelResult = (DeleteAdmChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteAdmChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteAdmChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteApnsChannelResult deleteApnsChannel(DeleteApnsChannelRequest deleteApnsChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteApnsChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteApnsChannelRequest> requestMarshall = new DeleteApnsChannelRequestMarshaller().marshall(deleteApnsChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteApnsChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteApnsChannelResult deleteApnsChannelResult = (DeleteApnsChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteApnsChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteApnsChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteApnsSandboxChannelResult deleteApnsSandboxChannel(DeleteApnsSandboxChannelRequest deleteApnsSandboxChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteApnsSandboxChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteApnsSandboxChannelRequest> requestMarshall = new DeleteApnsSandboxChannelRequestMarshaller().marshall(deleteApnsSandboxChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteApnsSandboxChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteApnsSandboxChannelResult deleteApnsSandboxChannelResult = (DeleteApnsSandboxChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteApnsSandboxChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteApnsSandboxChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteApnsVoipChannelResult deleteApnsVoipChannel(DeleteApnsVoipChannelRequest deleteApnsVoipChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteApnsVoipChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteApnsVoipChannelRequest> requestMarshall = new DeleteApnsVoipChannelRequestMarshaller().marshall(deleteApnsVoipChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteApnsVoipChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteApnsVoipChannelResult deleteApnsVoipChannelResult = (DeleteApnsVoipChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteApnsVoipChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteApnsVoipChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteApnsVoipSandboxChannelResult deleteApnsVoipSandboxChannel(DeleteApnsVoipSandboxChannelRequest deleteApnsVoipSandboxChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteApnsVoipSandboxChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteApnsVoipSandboxChannelRequest> requestMarshall = new DeleteApnsVoipSandboxChannelRequestMarshaller().marshall(deleteApnsVoipSandboxChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteApnsVoipSandboxChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteApnsVoipSandboxChannelResult deleteApnsVoipSandboxChannelResult = (DeleteApnsVoipSandboxChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteApnsVoipSandboxChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteApnsVoipSandboxChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteAppResult deleteApp(DeleteAppRequest deleteAppRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteAppRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteAppRequest> requestMarshall = new DeleteAppRequestMarshaller().marshall(deleteAppRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteAppResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteAppResult deleteAppResult = (DeleteAppResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteAppResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteAppRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteBaiduChannelResult deleteBaiduChannel(DeleteBaiduChannelRequest deleteBaiduChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteBaiduChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteBaiduChannelRequest> requestMarshall = new DeleteBaiduChannelRequestMarshaller().marshall(deleteBaiduChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteBaiduChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteBaiduChannelResult deleteBaiduChannelResult = (DeleteBaiduChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteBaiduChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteBaiduChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteCampaignResult deleteCampaign(DeleteCampaignRequest deleteCampaignRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteCampaignRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteCampaignRequest> requestMarshall = new DeleteCampaignRequestMarshaller().marshall(deleteCampaignRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteCampaignResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteCampaignResult deleteCampaignResult = (DeleteCampaignResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteCampaignResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteCampaignRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteEmailChannelResult deleteEmailChannel(DeleteEmailChannelRequest deleteEmailChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteEmailChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteEmailChannelRequest> requestMarshall = new DeleteEmailChannelRequestMarshaller().marshall(deleteEmailChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteEmailChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteEmailChannelResult deleteEmailChannelResult = (DeleteEmailChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteEmailChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteEmailChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteEndpointResult deleteEndpoint(DeleteEndpointRequest deleteEndpointRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteEndpointRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteEndpointRequest> requestMarshall = new DeleteEndpointRequestMarshaller().marshall(deleteEndpointRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteEndpointResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteEndpointResult deleteEndpointResult = (DeleteEndpointResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteEndpointResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteEndpointRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteEventStreamResult deleteEventStream(DeleteEventStreamRequest deleteEventStreamRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteEventStreamRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteEventStreamRequest> requestMarshall = new DeleteEventStreamRequestMarshaller().marshall(deleteEventStreamRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteEventStreamResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteEventStreamResult deleteEventStreamResult = (DeleteEventStreamResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteEventStreamResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteEventStreamRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteGcmChannelResult deleteGcmChannel(DeleteGcmChannelRequest deleteGcmChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteGcmChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteGcmChannelRequest> requestMarshall = new DeleteGcmChannelRequestMarshaller().marshall(deleteGcmChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteGcmChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteGcmChannelResult deleteGcmChannelResult = (DeleteGcmChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteGcmChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteGcmChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteSegmentResult deleteSegment(DeleteSegmentRequest deleteSegmentRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteSegmentRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteSegmentRequest> requestMarshall = new DeleteSegmentRequestMarshaller().marshall(deleteSegmentRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteSegmentResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteSegmentResult deleteSegmentResult = (DeleteSegmentResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteSegmentResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteSegmentRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteSmsChannelResult deleteSmsChannel(DeleteSmsChannelRequest deleteSmsChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteSmsChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteSmsChannelRequest> requestMarshall = new DeleteSmsChannelRequestMarshaller().marshall(deleteSmsChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteSmsChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteSmsChannelResult deleteSmsChannelResult = (DeleteSmsChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteSmsChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteSmsChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteUserEndpointsResult deleteUserEndpoints(DeleteUserEndpointsRequest deleteUserEndpointsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteUserEndpointsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteUserEndpointsRequest> requestMarshall = new DeleteUserEndpointsRequestMarshaller().marshall(deleteUserEndpointsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteUserEndpointsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteUserEndpointsResult deleteUserEndpointsResult = (DeleteUserEndpointsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteUserEndpointsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteUserEndpointsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public DeleteVoiceChannelResult deleteVoiceChannel(DeleteVoiceChannelRequest deleteVoiceChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(deleteVoiceChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<DeleteVoiceChannelRequest> requestMarshall = new DeleteVoiceChannelRequestMarshaller().marshall(deleteVoiceChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new DeleteVoiceChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        DeleteVoiceChannelResult deleteVoiceChannelResult = (DeleteVoiceChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return deleteVoiceChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = deleteVoiceChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetAdmChannelResult getAdmChannel(GetAdmChannelRequest getAdmChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getAdmChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetAdmChannelRequest> requestMarshall = new GetAdmChannelRequestMarshaller().marshall(getAdmChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetAdmChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetAdmChannelResult getAdmChannelResult = (GetAdmChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getAdmChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getAdmChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetApnsChannelResult getApnsChannel(GetApnsChannelRequest getApnsChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getApnsChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetApnsChannelRequest> requestMarshall = new GetApnsChannelRequestMarshaller().marshall(getApnsChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetApnsChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetApnsChannelResult getApnsChannelResult = (GetApnsChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getApnsChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getApnsChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetApnsSandboxChannelResult getApnsSandboxChannel(GetApnsSandboxChannelRequest getApnsSandboxChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getApnsSandboxChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetApnsSandboxChannelRequest> requestMarshall = new GetApnsSandboxChannelRequestMarshaller().marshall(getApnsSandboxChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetApnsSandboxChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetApnsSandboxChannelResult getApnsSandboxChannelResult = (GetApnsSandboxChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getApnsSandboxChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getApnsSandboxChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetApnsVoipChannelResult getApnsVoipChannel(GetApnsVoipChannelRequest getApnsVoipChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getApnsVoipChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetApnsVoipChannelRequest> requestMarshall = new GetApnsVoipChannelRequestMarshaller().marshall(getApnsVoipChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetApnsVoipChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetApnsVoipChannelResult getApnsVoipChannelResult = (GetApnsVoipChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getApnsVoipChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getApnsVoipChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetApnsVoipSandboxChannelResult getApnsVoipSandboxChannel(GetApnsVoipSandboxChannelRequest getApnsVoipSandboxChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getApnsVoipSandboxChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetApnsVoipSandboxChannelRequest> requestMarshall = new GetApnsVoipSandboxChannelRequestMarshaller().marshall(getApnsVoipSandboxChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetApnsVoipSandboxChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetApnsVoipSandboxChannelResult getApnsVoipSandboxChannelResult = (GetApnsVoipSandboxChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getApnsVoipSandboxChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getApnsVoipSandboxChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetAppResult getApp(GetAppRequest getAppRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getAppRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetAppRequest> requestMarshall = new GetAppRequestMarshaller().marshall(getAppRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetAppResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetAppResult getAppResult = (GetAppResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getAppResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getAppRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetApplicationSettingsResult getApplicationSettings(GetApplicationSettingsRequest getApplicationSettingsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getApplicationSettingsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetApplicationSettingsRequest> requestMarshall = new GetApplicationSettingsRequestMarshaller().marshall(getApplicationSettingsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetApplicationSettingsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetApplicationSettingsResult getApplicationSettingsResult = (GetApplicationSettingsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getApplicationSettingsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getApplicationSettingsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetAppsResult getApps(GetAppsRequest getAppsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getAppsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetAppsRequest> requestMarshall = new GetAppsRequestMarshaller().marshall(getAppsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetAppsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetAppsResult getAppsResult = (GetAppsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getAppsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getAppsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetBaiduChannelResult getBaiduChannel(GetBaiduChannelRequest getBaiduChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getBaiduChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetBaiduChannelRequest> requestMarshall = new GetBaiduChannelRequestMarshaller().marshall(getBaiduChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetBaiduChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetBaiduChannelResult getBaiduChannelResult = (GetBaiduChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getBaiduChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getBaiduChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetCampaignResult getCampaign(GetCampaignRequest getCampaignRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getCampaignRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetCampaignRequest> requestMarshall = new GetCampaignRequestMarshaller().marshall(getCampaignRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetCampaignResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetCampaignResult getCampaignResult = (GetCampaignResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getCampaignResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getCampaignRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetCampaignActivitiesResult getCampaignActivities(GetCampaignActivitiesRequest getCampaignActivitiesRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getCampaignActivitiesRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetCampaignActivitiesRequest> requestMarshall = new GetCampaignActivitiesRequestMarshaller().marshall(getCampaignActivitiesRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetCampaignActivitiesResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetCampaignActivitiesResult getCampaignActivitiesResult = (GetCampaignActivitiesResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getCampaignActivitiesResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getCampaignActivitiesRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetCampaignVersionResult getCampaignVersion(GetCampaignVersionRequest getCampaignVersionRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getCampaignVersionRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetCampaignVersionRequest> requestMarshall = new GetCampaignVersionRequestMarshaller().marshall(getCampaignVersionRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetCampaignVersionResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetCampaignVersionResult getCampaignVersionResult = (GetCampaignVersionResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getCampaignVersionResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getCampaignVersionRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetCampaignVersionsResult getCampaignVersions(GetCampaignVersionsRequest getCampaignVersionsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getCampaignVersionsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetCampaignVersionsRequest> requestMarshall = new GetCampaignVersionsRequestMarshaller().marshall(getCampaignVersionsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetCampaignVersionsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetCampaignVersionsResult getCampaignVersionsResult = (GetCampaignVersionsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getCampaignVersionsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getCampaignVersionsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetCampaignsResult getCampaigns(GetCampaignsRequest getCampaignsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getCampaignsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetCampaignsRequest> requestMarshall = new GetCampaignsRequestMarshaller().marshall(getCampaignsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetCampaignsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetCampaignsResult getCampaignsResult = (GetCampaignsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getCampaignsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getCampaignsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetChannelsResult getChannels(GetChannelsRequest getChannelsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getChannelsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetChannelsRequest> requestMarshall = new GetChannelsRequestMarshaller().marshall(getChannelsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetChannelsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetChannelsResult getChannelsResult = (GetChannelsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getChannelsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getChannelsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetEmailChannelResult getEmailChannel(GetEmailChannelRequest getEmailChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getEmailChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetEmailChannelRequest> requestMarshall = new GetEmailChannelRequestMarshaller().marshall(getEmailChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetEmailChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetEmailChannelResult getEmailChannelResult = (GetEmailChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getEmailChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getEmailChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetEndpointResult getEndpoint(GetEndpointRequest getEndpointRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getEndpointRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetEndpointRequest> requestMarshall = new GetEndpointRequestMarshaller().marshall(getEndpointRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetEndpointResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetEndpointResult getEndpointResult = (GetEndpointResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getEndpointResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getEndpointRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetEventStreamResult getEventStream(GetEventStreamRequest getEventStreamRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getEventStreamRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetEventStreamRequest> requestMarshall = new GetEventStreamRequestMarshaller().marshall(getEventStreamRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetEventStreamResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetEventStreamResult getEventStreamResult = (GetEventStreamResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getEventStreamResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getEventStreamRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetExportJobResult getExportJob(GetExportJobRequest getExportJobRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getExportJobRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetExportJobRequest> requestMarshall = new GetExportJobRequestMarshaller().marshall(getExportJobRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetExportJobResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetExportJobResult getExportJobResult = (GetExportJobResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getExportJobResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getExportJobRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetExportJobsResult getExportJobs(GetExportJobsRequest getExportJobsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getExportJobsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetExportJobsRequest> requestMarshall = new GetExportJobsRequestMarshaller().marshall(getExportJobsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetExportJobsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetExportJobsResult getExportJobsResult = (GetExportJobsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getExportJobsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getExportJobsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetGcmChannelResult getGcmChannel(GetGcmChannelRequest getGcmChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getGcmChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetGcmChannelRequest> requestMarshall = new GetGcmChannelRequestMarshaller().marshall(getGcmChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetGcmChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetGcmChannelResult getGcmChannelResult = (GetGcmChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getGcmChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getGcmChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetImportJobResult getImportJob(GetImportJobRequest getImportJobRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getImportJobRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetImportJobRequest> requestMarshall = new GetImportJobRequestMarshaller().marshall(getImportJobRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetImportJobResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetImportJobResult getImportJobResult = (GetImportJobResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getImportJobResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getImportJobRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetImportJobsResult getImportJobs(GetImportJobsRequest getImportJobsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getImportJobsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetImportJobsRequest> requestMarshall = new GetImportJobsRequestMarshaller().marshall(getImportJobsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetImportJobsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetImportJobsResult getImportJobsResult = (GetImportJobsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getImportJobsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getImportJobsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetSegmentResult getSegment(GetSegmentRequest getSegmentRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getSegmentRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetSegmentRequest> requestMarshall = new GetSegmentRequestMarshaller().marshall(getSegmentRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetSegmentResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetSegmentResult getSegmentResult = (GetSegmentResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getSegmentResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getSegmentRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetSegmentExportJobsResult getSegmentExportJobs(GetSegmentExportJobsRequest getSegmentExportJobsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getSegmentExportJobsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetSegmentExportJobsRequest> requestMarshall = new GetSegmentExportJobsRequestMarshaller().marshall(getSegmentExportJobsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetSegmentExportJobsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetSegmentExportJobsResult getSegmentExportJobsResult = (GetSegmentExportJobsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getSegmentExportJobsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getSegmentExportJobsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetSegmentImportJobsResult getSegmentImportJobs(GetSegmentImportJobsRequest getSegmentImportJobsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getSegmentImportJobsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetSegmentImportJobsRequest> requestMarshall = new GetSegmentImportJobsRequestMarshaller().marshall(getSegmentImportJobsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetSegmentImportJobsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetSegmentImportJobsResult getSegmentImportJobsResult = (GetSegmentImportJobsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getSegmentImportJobsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getSegmentImportJobsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetSegmentVersionResult getSegmentVersion(GetSegmentVersionRequest getSegmentVersionRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getSegmentVersionRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetSegmentVersionRequest> requestMarshall = new GetSegmentVersionRequestMarshaller().marshall(getSegmentVersionRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetSegmentVersionResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetSegmentVersionResult getSegmentVersionResult = (GetSegmentVersionResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getSegmentVersionResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getSegmentVersionRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetSegmentVersionsResult getSegmentVersions(GetSegmentVersionsRequest getSegmentVersionsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getSegmentVersionsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetSegmentVersionsRequest> requestMarshall = new GetSegmentVersionsRequestMarshaller().marshall(getSegmentVersionsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetSegmentVersionsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetSegmentVersionsResult getSegmentVersionsResult = (GetSegmentVersionsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getSegmentVersionsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getSegmentVersionsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetSegmentsResult getSegments(GetSegmentsRequest getSegmentsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getSegmentsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetSegmentsRequest> requestMarshall = new GetSegmentsRequestMarshaller().marshall(getSegmentsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetSegmentsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetSegmentsResult getSegmentsResult = (GetSegmentsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getSegmentsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getSegmentsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetSmsChannelResult getSmsChannel(GetSmsChannelRequest getSmsChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getSmsChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetSmsChannelRequest> requestMarshall = new GetSmsChannelRequestMarshaller().marshall(getSmsChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetSmsChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetSmsChannelResult getSmsChannelResult = (GetSmsChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getSmsChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getSmsChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetUserEndpointsResult getUserEndpoints(GetUserEndpointsRequest getUserEndpointsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getUserEndpointsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetUserEndpointsRequest> requestMarshall = new GetUserEndpointsRequestMarshaller().marshall(getUserEndpointsRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetUserEndpointsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetUserEndpointsResult getUserEndpointsResult = (GetUserEndpointsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getUserEndpointsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getUserEndpointsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public GetVoiceChannelResult getVoiceChannel(GetVoiceChannelRequest getVoiceChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(getVoiceChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<GetVoiceChannelRequest> requestMarshall = new GetVoiceChannelRequestMarshaller().marshall(getVoiceChannelRequest);
                    try {
                        requestMarshall.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall, new JsonResponseHandler(new GetVoiceChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        GetVoiceChannelResult getVoiceChannelResult = (GetVoiceChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall, responseInvoke, true);
                        return getVoiceChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = getVoiceChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public PhoneNumberValidateResult phoneNumberValidate(PhoneNumberValidateRequest phoneNumberValidateRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(phoneNumberValidateRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<PhoneNumberValidateRequest> requestMarshall2 = new PhoneNumberValidateRequestMarshaller().marshall(phoneNumberValidateRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new PhoneNumberValidateResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        PhoneNumberValidateResult phoneNumberValidateResult = (PhoneNumberValidateResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return phoneNumberValidateResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = phoneNumberValidateRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public PutEventStreamResult putEventStream(PutEventStreamRequest putEventStreamRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(putEventStreamRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<PutEventStreamRequest> requestMarshall2 = new PutEventStreamRequestMarshaller().marshall(putEventStreamRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new PutEventStreamResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        PutEventStreamResult putEventStreamResult = (PutEventStreamResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return putEventStreamResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = putEventStreamRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public PutEventsResult putEvents(PutEventsRequest putEventsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(putEventsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<PutEventsRequest> requestMarshall2 = new PutEventsRequestMarshaller().marshall(putEventsRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new PutEventsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        PutEventsResult putEventsResult = (PutEventsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return putEventsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = putEventsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public RemoveAttributesResult removeAttributes(RemoveAttributesRequest removeAttributesRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(removeAttributesRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<RemoveAttributesRequest> requestMarshall2 = new RemoveAttributesRequestMarshaller().marshall(removeAttributesRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new RemoveAttributesResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        RemoveAttributesResult removeAttributesResult = (RemoveAttributesResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return removeAttributesResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = removeAttributesRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public SendMessagesResult sendMessages(SendMessagesRequest sendMessagesRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(sendMessagesRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<SendMessagesRequest> requestMarshall2 = new SendMessagesRequestMarshaller().marshall(sendMessagesRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new SendMessagesResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        SendMessagesResult sendMessagesResult = (SendMessagesResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return sendMessagesResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = sendMessagesRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public SendUsersMessagesResult sendUsersMessages(SendUsersMessagesRequest sendUsersMessagesRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(sendUsersMessagesRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<SendUsersMessagesRequest> requestMarshall2 = new SendUsersMessagesRequestMarshaller().marshall(sendUsersMessagesRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new SendUsersMessagesResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        SendUsersMessagesResult sendUsersMessagesResult = (SendUsersMessagesResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return sendUsersMessagesResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = sendUsersMessagesRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateAdmChannelResult updateAdmChannel(UpdateAdmChannelRequest updateAdmChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateAdmChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateAdmChannelRequest> requestMarshall2 = new UpdateAdmChannelRequestMarshaller().marshall(updateAdmChannelRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateAdmChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateAdmChannelResult updateAdmChannelResult = (UpdateAdmChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateAdmChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateAdmChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateApnsChannelResult updateApnsChannel(UpdateApnsChannelRequest updateApnsChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateApnsChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateApnsChannelRequest> requestMarshall2 = new UpdateApnsChannelRequestMarshaller().marshall(updateApnsChannelRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateApnsChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateApnsChannelResult updateApnsChannelResult = (UpdateApnsChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateApnsChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateApnsChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateApnsSandboxChannelResult updateApnsSandboxChannel(UpdateApnsSandboxChannelRequest updateApnsSandboxChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateApnsSandboxChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateApnsSandboxChannelRequest> requestMarshall2 = new UpdateApnsSandboxChannelRequestMarshaller().marshall(updateApnsSandboxChannelRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateApnsSandboxChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateApnsSandboxChannelResult updateApnsSandboxChannelResult = (UpdateApnsSandboxChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateApnsSandboxChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateApnsSandboxChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateApnsVoipChannelResult updateApnsVoipChannel(UpdateApnsVoipChannelRequest updateApnsVoipChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateApnsVoipChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateApnsVoipChannelRequest> requestMarshall2 = new UpdateApnsVoipChannelRequestMarshaller().marshall(updateApnsVoipChannelRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateApnsVoipChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateApnsVoipChannelResult updateApnsVoipChannelResult = (UpdateApnsVoipChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateApnsVoipChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateApnsVoipChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateApnsVoipSandboxChannelResult updateApnsVoipSandboxChannel(UpdateApnsVoipSandboxChannelRequest updateApnsVoipSandboxChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateApnsVoipSandboxChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateApnsVoipSandboxChannelRequest> requestMarshall2 = new UpdateApnsVoipSandboxChannelRequestMarshaller().marshall(updateApnsVoipSandboxChannelRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateApnsVoipSandboxChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateApnsVoipSandboxChannelResult updateApnsVoipSandboxChannelResult = (UpdateApnsVoipSandboxChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateApnsVoipSandboxChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateApnsVoipSandboxChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateApplicationSettingsResult updateApplicationSettings(UpdateApplicationSettingsRequest updateApplicationSettingsRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateApplicationSettingsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateApplicationSettingsRequest> requestMarshall2 = new UpdateApplicationSettingsRequestMarshaller().marshall(updateApplicationSettingsRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateApplicationSettingsResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateApplicationSettingsResult updateApplicationSettingsResult = (UpdateApplicationSettingsResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateApplicationSettingsResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateApplicationSettingsRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateBaiduChannelResult updateBaiduChannel(UpdateBaiduChannelRequest updateBaiduChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateBaiduChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateBaiduChannelRequest> requestMarshall2 = new UpdateBaiduChannelRequestMarshaller().marshall(updateBaiduChannelRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateBaiduChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateBaiduChannelResult updateBaiduChannelResult = (UpdateBaiduChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateBaiduChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateBaiduChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateCampaignResult updateCampaign(UpdateCampaignRequest updateCampaignRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateCampaignRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateCampaignRequest> requestMarshall2 = new UpdateCampaignRequestMarshaller().marshall(updateCampaignRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateCampaignResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateCampaignResult updateCampaignResult = (UpdateCampaignResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateCampaignResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateCampaignRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateEmailChannelResult updateEmailChannel(UpdateEmailChannelRequest updateEmailChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateEmailChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateEmailChannelRequest> requestMarshall2 = new UpdateEmailChannelRequestMarshaller().marshall(updateEmailChannelRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateEmailChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateEmailChannelResult updateEmailChannelResult = (UpdateEmailChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateEmailChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateEmailChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateEndpointResult updateEndpoint(UpdateEndpointRequest updateEndpointRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateEndpointRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateEndpointRequest> requestMarshall2 = new UpdateEndpointRequestMarshaller().marshall(updateEndpointRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateEndpointResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateEndpointResult updateEndpointResult = (UpdateEndpointResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateEndpointResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateEndpointRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateEndpointsBatchResult updateEndpointsBatch(UpdateEndpointsBatchRequest updateEndpointsBatchRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateEndpointsBatchRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateEndpointsBatchRequest> requestMarshall2 = new UpdateEndpointsBatchRequestMarshaller().marshall(updateEndpointsBatchRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateEndpointsBatchResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateEndpointsBatchResult updateEndpointsBatchResult = (UpdateEndpointsBatchResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateEndpointsBatchResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateEndpointsBatchRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateGcmChannelResult updateGcmChannel(UpdateGcmChannelRequest updateGcmChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateGcmChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateGcmChannelRequest> requestMarshall2 = new UpdateGcmChannelRequestMarshaller().marshall(updateGcmChannelRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateGcmChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateGcmChannelResult updateGcmChannelResult = (UpdateGcmChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateGcmChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateGcmChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateSegmentResult updateSegment(UpdateSegmentRequest updateSegmentRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateSegmentRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateSegmentRequest> requestMarshall2 = new UpdateSegmentRequestMarshaller().marshall(updateSegmentRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateSegmentResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateSegmentResult updateSegmentResult = (UpdateSegmentResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateSegmentResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateSegmentRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateSmsChannelResult updateSmsChannel(UpdateSmsChannelRequest updateSmsChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateSmsChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateSmsChannelRequest> requestMarshall2 = new UpdateSmsChannelRequestMarshaller().marshall(updateSmsChannelRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateSmsChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateSmsChannelResult updateSmsChannelResult = (UpdateSmsChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateSmsChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateSmsChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.amazonaws.Request] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.amazonaws.services.pinpoint.AmazonPinpointClient] */
    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    public UpdateVoiceChannelResult updateVoiceChannel(UpdateVoiceChannelRequest updateVoiceChannelRequest) throws Throwable {
        Response response;
        ExecutionContext executionContextCreateExecutionContext = createExecutionContext(updateVoiceChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContextCreateExecutionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.ClientExecuteTime);
        ?? r3 = 0;
        try {
            try {
                awsRequestMetrics.startEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                try {
                    Request<UpdateVoiceChannelRequest> requestMarshall2 = new UpdateVoiceChannelRequestMarshaller().marshall(updateVoiceChannelRequest);
                    try {
                        requestMarshall2.setAWSRequestMetrics(awsRequestMetrics);
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        Response responseInvoke = invoke(requestMarshall2, new JsonResponseHandler(new UpdateVoiceChannelResultJsonUnmarshaller()), executionContextCreateExecutionContext);
                        UpdateVoiceChannelResult updateVoiceChannelResult = (UpdateVoiceChannelResult) responseInvoke.getAwsResponse();
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                        endClientExecution(awsRequestMetrics, requestMarshall2, responseInvoke, true);
                        return updateVoiceChannelResult;
                    } catch (Throwable th) {
                        th = th;
                        awsRequestMetrics.endEvent(AWSRequestMetrics.Field.RequestMarshallTime);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                response = null;
                awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
                endClientExecution(awsRequestMetrics, r3, response, true);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            r3 = updateVoiceChannelRequest;
            response = null;
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, r3, response, true);
            throw th;
        }
    }

    @Override // com.amazonaws.services.pinpoint.AmazonPinpoint
    @Deprecated
    public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest amazonWebServiceRequest) {
        return this.client.getResponseMetadataForRequest(amazonWebServiceRequest);
    }

    private <X, Y extends AmazonWebServiceRequest> Response<X> invoke(Request<Y> request, HttpResponseHandler<AmazonWebServiceResponse<X>> httpResponseHandler, ExecutionContext executionContext) {
        request.setEndpoint(this.endpoint);
        request.setTimeOffset(this.timeOffset);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        awsRequestMetrics.startEvent(AWSRequestMetrics.Field.CredentialsRequestTime);
        try {
            AWSCredentials credentials = this.awsCredentialsProvider.getCredentials();
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.CredentialsRequestTime);
            AmazonWebServiceRequest originalRequest = request.getOriginalRequest();
            if (originalRequest != null && originalRequest.getRequestCredentials() != null) {
                credentials = originalRequest.getRequestCredentials();
            }
            executionContext.setCredentials(credentials);
            return this.client.execute(request, httpResponseHandler, new JsonErrorResponseHandler(this.jsonErrorUnmarshallers), executionContext);
        } catch (Throwable th) {
            awsRequestMetrics.endEvent(AWSRequestMetrics.Field.CredentialsRequestTime);
            throw th;
        }
    }
}

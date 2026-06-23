package com.amazonaws.services.pinpoint;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
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

/* JADX INFO: loaded from: classes.dex */
public interface AmazonPinpoint {
    CreateAppResult createApp(CreateAppRequest createAppRequest) throws AmazonClientException;

    CreateCampaignResult createCampaign(CreateCampaignRequest createCampaignRequest) throws AmazonClientException;

    CreateExportJobResult createExportJob(CreateExportJobRequest createExportJobRequest) throws AmazonClientException;

    CreateImportJobResult createImportJob(CreateImportJobRequest createImportJobRequest) throws AmazonClientException;

    CreateSegmentResult createSegment(CreateSegmentRequest createSegmentRequest) throws AmazonClientException;

    DeleteAdmChannelResult deleteAdmChannel(DeleteAdmChannelRequest deleteAdmChannelRequest) throws AmazonClientException;

    DeleteApnsChannelResult deleteApnsChannel(DeleteApnsChannelRequest deleteApnsChannelRequest) throws AmazonClientException;

    DeleteApnsSandboxChannelResult deleteApnsSandboxChannel(DeleteApnsSandboxChannelRequest deleteApnsSandboxChannelRequest) throws AmazonClientException;

    DeleteApnsVoipChannelResult deleteApnsVoipChannel(DeleteApnsVoipChannelRequest deleteApnsVoipChannelRequest) throws AmazonClientException;

    DeleteApnsVoipSandboxChannelResult deleteApnsVoipSandboxChannel(DeleteApnsVoipSandboxChannelRequest deleteApnsVoipSandboxChannelRequest) throws AmazonClientException;

    DeleteAppResult deleteApp(DeleteAppRequest deleteAppRequest) throws AmazonClientException;

    DeleteBaiduChannelResult deleteBaiduChannel(DeleteBaiduChannelRequest deleteBaiduChannelRequest) throws AmazonClientException;

    DeleteCampaignResult deleteCampaign(DeleteCampaignRequest deleteCampaignRequest) throws AmazonClientException;

    DeleteEmailChannelResult deleteEmailChannel(DeleteEmailChannelRequest deleteEmailChannelRequest) throws AmazonClientException;

    DeleteEndpointResult deleteEndpoint(DeleteEndpointRequest deleteEndpointRequest) throws AmazonClientException;

    DeleteEventStreamResult deleteEventStream(DeleteEventStreamRequest deleteEventStreamRequest) throws AmazonClientException;

    DeleteGcmChannelResult deleteGcmChannel(DeleteGcmChannelRequest deleteGcmChannelRequest) throws AmazonClientException;

    DeleteSegmentResult deleteSegment(DeleteSegmentRequest deleteSegmentRequest) throws AmazonClientException;

    DeleteSmsChannelResult deleteSmsChannel(DeleteSmsChannelRequest deleteSmsChannelRequest) throws AmazonClientException;

    DeleteUserEndpointsResult deleteUserEndpoints(DeleteUserEndpointsRequest deleteUserEndpointsRequest) throws AmazonClientException;

    DeleteVoiceChannelResult deleteVoiceChannel(DeleteVoiceChannelRequest deleteVoiceChannelRequest) throws AmazonClientException;

    GetAdmChannelResult getAdmChannel(GetAdmChannelRequest getAdmChannelRequest) throws AmazonClientException;

    GetApnsChannelResult getApnsChannel(GetApnsChannelRequest getApnsChannelRequest) throws AmazonClientException;

    GetApnsSandboxChannelResult getApnsSandboxChannel(GetApnsSandboxChannelRequest getApnsSandboxChannelRequest) throws AmazonClientException;

    GetApnsVoipChannelResult getApnsVoipChannel(GetApnsVoipChannelRequest getApnsVoipChannelRequest) throws AmazonClientException;

    GetApnsVoipSandboxChannelResult getApnsVoipSandboxChannel(GetApnsVoipSandboxChannelRequest getApnsVoipSandboxChannelRequest) throws AmazonClientException;

    GetAppResult getApp(GetAppRequest getAppRequest) throws AmazonClientException;

    GetApplicationSettingsResult getApplicationSettings(GetApplicationSettingsRequest getApplicationSettingsRequest) throws AmazonClientException;

    GetAppsResult getApps(GetAppsRequest getAppsRequest) throws AmazonClientException;

    GetBaiduChannelResult getBaiduChannel(GetBaiduChannelRequest getBaiduChannelRequest) throws AmazonClientException;

    ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest amazonWebServiceRequest);

    GetCampaignResult getCampaign(GetCampaignRequest getCampaignRequest) throws AmazonClientException;

    GetCampaignActivitiesResult getCampaignActivities(GetCampaignActivitiesRequest getCampaignActivitiesRequest) throws AmazonClientException;

    GetCampaignVersionResult getCampaignVersion(GetCampaignVersionRequest getCampaignVersionRequest) throws AmazonClientException;

    GetCampaignVersionsResult getCampaignVersions(GetCampaignVersionsRequest getCampaignVersionsRequest) throws AmazonClientException;

    GetCampaignsResult getCampaigns(GetCampaignsRequest getCampaignsRequest) throws AmazonClientException;

    GetChannelsResult getChannels(GetChannelsRequest getChannelsRequest) throws AmazonClientException;

    GetEmailChannelResult getEmailChannel(GetEmailChannelRequest getEmailChannelRequest) throws AmazonClientException;

    GetEndpointResult getEndpoint(GetEndpointRequest getEndpointRequest) throws AmazonClientException;

    GetEventStreamResult getEventStream(GetEventStreamRequest getEventStreamRequest) throws AmazonClientException;

    GetExportJobResult getExportJob(GetExportJobRequest getExportJobRequest) throws AmazonClientException;

    GetExportJobsResult getExportJobs(GetExportJobsRequest getExportJobsRequest) throws AmazonClientException;

    GetGcmChannelResult getGcmChannel(GetGcmChannelRequest getGcmChannelRequest) throws AmazonClientException;

    GetImportJobResult getImportJob(GetImportJobRequest getImportJobRequest) throws AmazonClientException;

    GetImportJobsResult getImportJobs(GetImportJobsRequest getImportJobsRequest) throws AmazonClientException;

    GetSegmentResult getSegment(GetSegmentRequest getSegmentRequest) throws AmazonClientException;

    GetSegmentExportJobsResult getSegmentExportJobs(GetSegmentExportJobsRequest getSegmentExportJobsRequest) throws AmazonClientException;

    GetSegmentImportJobsResult getSegmentImportJobs(GetSegmentImportJobsRequest getSegmentImportJobsRequest) throws AmazonClientException;

    GetSegmentVersionResult getSegmentVersion(GetSegmentVersionRequest getSegmentVersionRequest) throws AmazonClientException;

    GetSegmentVersionsResult getSegmentVersions(GetSegmentVersionsRequest getSegmentVersionsRequest) throws AmazonClientException;

    GetSegmentsResult getSegments(GetSegmentsRequest getSegmentsRequest) throws AmazonClientException;

    GetSmsChannelResult getSmsChannel(GetSmsChannelRequest getSmsChannelRequest) throws AmazonClientException;

    GetUserEndpointsResult getUserEndpoints(GetUserEndpointsRequest getUserEndpointsRequest) throws AmazonClientException;

    GetVoiceChannelResult getVoiceChannel(GetVoiceChannelRequest getVoiceChannelRequest) throws AmazonClientException;

    PhoneNumberValidateResult phoneNumberValidate(PhoneNumberValidateRequest phoneNumberValidateRequest) throws AmazonClientException;

    PutEventStreamResult putEventStream(PutEventStreamRequest putEventStreamRequest) throws AmazonClientException;

    PutEventsResult putEvents(PutEventsRequest putEventsRequest) throws AmazonClientException;

    RemoveAttributesResult removeAttributes(RemoveAttributesRequest removeAttributesRequest) throws AmazonClientException;

    SendMessagesResult sendMessages(SendMessagesRequest sendMessagesRequest) throws AmazonClientException;

    SendUsersMessagesResult sendUsersMessages(SendUsersMessagesRequest sendUsersMessagesRequest) throws AmazonClientException;

    void setEndpoint(String str) throws IllegalArgumentException;

    void setRegion(Region region) throws IllegalArgumentException;

    void shutdown();

    UpdateAdmChannelResult updateAdmChannel(UpdateAdmChannelRequest updateAdmChannelRequest) throws AmazonClientException;

    UpdateApnsChannelResult updateApnsChannel(UpdateApnsChannelRequest updateApnsChannelRequest) throws AmazonClientException;

    UpdateApnsSandboxChannelResult updateApnsSandboxChannel(UpdateApnsSandboxChannelRequest updateApnsSandboxChannelRequest) throws AmazonClientException;

    UpdateApnsVoipChannelResult updateApnsVoipChannel(UpdateApnsVoipChannelRequest updateApnsVoipChannelRequest) throws AmazonClientException;

    UpdateApnsVoipSandboxChannelResult updateApnsVoipSandboxChannel(UpdateApnsVoipSandboxChannelRequest updateApnsVoipSandboxChannelRequest) throws AmazonClientException;

    UpdateApplicationSettingsResult updateApplicationSettings(UpdateApplicationSettingsRequest updateApplicationSettingsRequest) throws AmazonClientException;

    UpdateBaiduChannelResult updateBaiduChannel(UpdateBaiduChannelRequest updateBaiduChannelRequest) throws AmazonClientException;

    UpdateCampaignResult updateCampaign(UpdateCampaignRequest updateCampaignRequest) throws AmazonClientException;

    UpdateEmailChannelResult updateEmailChannel(UpdateEmailChannelRequest updateEmailChannelRequest) throws AmazonClientException;

    UpdateEndpointResult updateEndpoint(UpdateEndpointRequest updateEndpointRequest) throws AmazonClientException;

    UpdateEndpointsBatchResult updateEndpointsBatch(UpdateEndpointsBatchRequest updateEndpointsBatchRequest) throws AmazonClientException;

    UpdateGcmChannelResult updateGcmChannel(UpdateGcmChannelRequest updateGcmChannelRequest) throws AmazonClientException;

    UpdateSegmentResult updateSegment(UpdateSegmentRequest updateSegmentRequest) throws AmazonClientException;

    UpdateSmsChannelResult updateSmsChannel(UpdateSmsChannelRequest updateSmsChannelRequest) throws AmazonClientException;

    UpdateVoiceChannelResult updateVoiceChannel(UpdateVoiceChannelRequest updateVoiceChannelRequest) throws AmazonClientException;
}

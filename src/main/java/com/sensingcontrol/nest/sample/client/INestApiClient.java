package com.sensingcontrol.nest.sample.client;

import com.sensingcontrol.nest.sample.client.requests.authentication.LoginRequest;
import com.sensingcontrol.nest.sample.client.requests.authentication.RefreshTokenRequest;
import com.sensingcontrol.nest.sample.client.requests.devices.SendCommandRequest;
import com.sensingcontrol.nest.sample.client.requests.devices.SendRawCommandRequest;
import com.sensingcontrol.nest.sample.client.requests.signalr.SignalRSubscribeAllRequest;
import com.sensingcontrol.nest.sample.client.requests.signalr.SignalRSubscribeRequest;
import com.sensingcontrol.nest.sample.client.requests.signalr.SignalRUnsubscribeAllRequest;
import com.sensingcontrol.nest.sample.client.requests.signalr.SignalRUnsubscribeRequest;
import com.sensingcontrol.nest.sample.client.responses.authentication.LoginResponse;
import com.sensingcontrol.nest.sample.client.responses.authentication.RefreshTokenResponse;
import com.sensingcontrol.nest.sample.client.responses.devices.GetAllDevicesResponse;
import com.sensingcontrol.nest.sample.client.responses.devices.GetSensorHistoricDataResponse;
import com.sensingcontrol.nest.sample.client.responses.gateways.GetAllGatewaysResponse;
import com.sensingcontrol.nest.sample.client.responses.users.GetAllUsersResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface INestApiClient {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("auth/refreshtoken")
    Call<RefreshTokenResponse> refreshToken(@Body RefreshTokenRequest request);

    @GET("gateways")
    Call<GetAllGatewaysResponse> getAllGateways();

    @GET("devices")
    Call<GetAllDevicesResponse> getAllDevices();

    @GET("devices/{deviceId}/{sensorId}/history")
    Call<GetSensorHistoricDataResponse> getDeviceHistoricData(@Path("deviceId") String deviceId, @Path("sensorId") String sensorId, @Query("fromDate") Long fromDate, @Query("toDate") Long toDate);

    @POST("devices/{deviceId}/command")
    Call<Void> sendCommand(@Path("deviceId") String deviceId, @Body SendCommandRequest request);

    @POST("devices/{deviceId}/rawcommand")
    Call<Void> sendRawCommand(@Path("deviceId") String deviceId, @Body SendRawCommandRequest request);

    @GET("users")
    Call<GetAllUsersResponse> getAllUsers();

    @POST("signalr/subscribe")
    Call<Void> signalrSubscribe(@Body SignalRSubscribeRequest request);

    @POST("signalr/subscribeall")
    Call<Void> signalrSubscribeAll(@Body SignalRSubscribeAllRequest request);

    @POST("signalr/unsubscribe")
    Call<Void> signalrUnsubscribe(@Body SignalRUnsubscribeRequest request);

    @POST("signalr/unsubscribeall")
    Call<Void> signalrUnsubscribeAll(@Body SignalRUnsubscribeAllRequest request);
}

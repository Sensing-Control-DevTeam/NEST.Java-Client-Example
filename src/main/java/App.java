import com.google.gson.Gson;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.sensingcontrol.nest.sample.client.*;
import com.sensingcontrol.nest.sample.client.auth.TokenAuthenticator;
import com.sensingcontrol.nest.sample.client.auth.TokenInterceptor;
import com.sensingcontrol.nest.sample.client.auth.TokenStore;
import com.sensingcontrol.nest.sample.client.model.devices.LoraCommand;
import com.sensingcontrol.nest.sample.client.model.devices.LoraCommandParam;
import com.sensingcontrol.nest.sample.client.requests.authentication.LoginRequest;
import com.sensingcontrol.nest.sample.client.requests.devices.SendCommandRequest;
import com.sensingcontrol.nest.sample.client.requests.devices.SendRawCommandRequest;
import com.sensingcontrol.nest.sample.client.requests.signalr.SignalRSubscribeAllRequest;
import com.sensingcontrol.nest.sample.client.requests.signalr.SignalRUnsubscribeAllRequest;
import com.sensingcontrol.nest.sample.client.signalr.*;
import com.sensingcontrol.nest.sample.client.signalr.messages.*;
import okhttp3.*;
import org.slf4j.impl.SimpleLogger;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;

import static com.sensingcontrol.nest.sample.client.model.constants.NestHubCommands.*;
import static com.sensingcontrol.nest.sample.client.model.constants.NestHubConstants.*;

public class App {
    private static final String BASE_URL = "https://nest.encontrol.io/api/";
    private static final String SIGNALR_URL = BASE_URL + "signalr";
    private static final String EMAIL = "[YOUR_EMAIL_HERE]";
    private static final String PASSWORD = "[YOUR_PASSWORD_HERE]";
    private final INestApiClient nestClient;
    private final TokenStore tokenStore;

    public static void main(String[] args) {
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

        App app = new App();
        app.run();
    }

    public App() {
        tokenStore = new TokenStore();
        NestClientHolder clientHolder = new NestClientHolder();

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor(tokenStore));

        okHttpClientBuilder.authenticator(new TokenAuthenticator(tokenStore, clientHolder));

        nestClient = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build()
                .create(INestApiClient.class);

        clientHolder.setClient(nestClient);
    }

    public void run() {
        try {
            login();

            getGateways();

            getDevicesAndHistoricAndSendCommands();

            getUsers();

            subscribeToRealTimeUpdates();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.exit(0);
    }

    private void login() throws IOException {
        System.out.println("-------------- LOG IN --------------");
        var loginResponse = nestClient.login(new LoginRequest(EMAIL, PASSWORD)).execute();
        if (loginResponse.isSuccessful() && loginResponse.body() != null) {
            System.out.println(loginResponse.body().getData().getAccessToken());
            tokenStore.updateStore(loginResponse.body().getData());
        }
    }

    private void getGateways() throws IOException {
        System.out.println("-------------- GATEWAYS --------------");
        var gatewaysResponse = nestClient.getAllGateways().execute();
        if (gatewaysResponse.isSuccessful() && gatewaysResponse.body() != null) {
            for (var gw : gatewaysResponse.body().getData()) {
                System.out.println(gw.getName());
            }
        }
    }

    private void getDevicesAndHistoricAndSendCommands() throws IOException {
        System.out.println("-------------- DEVICES --------------");
        var devicesResponse = nestClient.getAllDevices().execute();
        if (devicesResponse.isSuccessful() && devicesResponse.body() != null) {
            for (var device : devicesResponse.body().getData()) {
                System.out.println(device.getName());
            }

            System.out.println("-------------- FIRST DEVICE - FIRST SENSOR HISTORIC DATA --------------");
            var device = devicesResponse.body().getData().get(0);
            var sensorHistoryResponse = nestClient.getDeviceHistoricData(
                    device.getId(),
                    device.getSensors().get(0).getId(),
                    0L,
                    new Date().getTime()
            ).execute();

            if (sensorHistoryResponse.isSuccessful() && sensorHistoryResponse.body() != null) {
                for (var entry : sensorHistoryResponse.body().getData()) {
                    System.out.println(entry.getTimestamp() + " " + new Gson().toJson(entry.getValue()));
                }
            }

            // Commands are separated in 3 logical blocks:
            //
            // Device -> Commands
            // Device -> Configuration
            // Device -> Sensor -> Configuration
            //
            // They all have the same structure which can be sent directly to the API

            // Send a device command using existent Device Command from the model
            System.out.println("-------------- FIRST DEVICE - SEND GET ALARMS COMMAND (device command) -----------");
            var command = device.getCommands().stream()
                    .filter(x -> x.getId().equals(GET_ALARMS))
                    .findFirst()
                    .orElse(null);
            if (command != null) {
                nestClient.sendCommand(device.getId(), new SendCommandRequest(command)).execute();
            }

            // Send a device configuration command using existent Device Configuration from the model
            System.out.println("-------------- FIRST DEVICE - SEND SET ALIVE RATE (device configuration command) -----------");
            var setAliveRateCommand = device.getConfiguration().stream()
                    .filter(x -> x.getId().equals(SET_HUB_ALIVE_SEND_RATE))
                    .findFirst()
                    .orElse(null);
            if (setAliveRateCommand != null) {
                // To simplify we use the object directly but if sending commands using this method,
                // fully copy the LoraCommand + LoraCommandParams to avoid mutating your model objects
                // since LoRaWAN it's not a synchronous protocol
                var rateParam = setAliveRateCommand.getParams().stream().filter(x -> x.getId().equals(HubAliveParamId)).findFirst().orElse(null);
                if (rateParam != null) {
                    rateParam.setValue(10L);
                }
                nestClient.sendCommand(device.getId(), new SendCommandRequest(setAliveRateCommand)).execute();
            }

            // Send a sensor configuration command manually building LoraCommand + LoraCommandParams (Only LoraCommand id and params Id + Value are required, the rest of the fields are ignored)
            System.out.println("-------------- FIRST DEVICE - SET ENVIRONMENTAL THRESHOLDS COMMAND (sensor configuration command) -----------");
            var configCommand = new LoraCommand(SET_ENVIRONMENTAL_THRESHOLDS);

            var configCommandParams = new ArrayList<LoraCommandParam>();
            configCommandParams.add(new LoraCommandParam(EnvironmentalTemperatureThresholdId, 30L));
            configCommandParams.add(new LoraCommandParam(EnvironmentalPressureThresholdId, 40L));
            configCommandParams.add(new LoraCommandParam(EnvironmentalHumidityThresholdId, 50L));

            configCommand.setParams(configCommandParams);

            nestClient.sendCommand(device.getId(), new SendCommandRequest(configCommand)).execute();

            // You can also send RAW commands to the device, it MUST be Base64 encoded
            nestClient.sendRawCommand(
                    device.getId(),
                    new SendRawCommandRequest(
                        Base64.getEncoder().encodeToString(new byte[] { 0x00 })
                    )
            ).execute();
        }
    }

    private void getUsers() throws IOException {
        System.out.println("-------------- USERS --------------");
        var usersResponse = nestClient.getAllUsers().execute();
        if (usersResponse.isSuccessful() && usersResponse.body() != null) {
            for (var user : usersResponse.body().getData()) {
                System.out.println(user.getFirstName() + " " + user.getLastName());
            }
        }
    }

    private void subscribeToRealTimeUpdates() throws IOException {
        System.out.println("-------------- REAL TIME DATA HANDLING --------------");
        HubConnection nestHub = HubConnectionBuilder
                .create(SIGNALR_URL)
                .withHeader("x-custom-auth", tokenStore.getAccessToken())
                .build();

        // 'on' methods will be invoked whenever the server has relevant data related
        // to the devices you're subscribed to

        // Callback for new readings
        nestHub.on(SignalRMethods.NewReadings, newReadingsMessage -> {
            System.out.println("\nNew readings for Device: " + newReadingsMessage.getDeviceId());
            newReadingsMessage.getReadings().forEach((key, data) -> {
                System.out.println(
                        "Sensor Id: " + key.split("-")[0]
                                + " Reading Id: " +  key.split("-")[1]
                                + " Value: " + data.getValue()
                                + " Timestamp: " + new Date(data.getDate())
                );
            });
        }, NewReadingsMessage.class);

        // Callback for sensor online / offline change
        nestHub.on(SignalRMethods.NewConnectivityStatus, newConnectivityStatusMessage -> {
            System.out.println("\nNew connectivity status for Device: " + newConnectivityStatusMessage.getDeviceId());
            newConnectivityStatusMessage.getConnectivityStatus().forEach((sensorId, isOnline) -> {
                System.out.println("Sensor " + sensorId + " is " + (isOnline ? "ONLINE" : "OFFLINE"));
            });
        }, NewConnectivityStatusMessage.class);

        // Callback for new configuration changes (both, device configuration and sensors configuration)
        nestHub.on(SignalRMethods.NewConfiguration, newConfigurationMessage -> {
            System.out.println("\nNew configuration for Device: " + newConfigurationMessage.getDeviceId());

            if (newConfigurationMessage.getDeviceConfiguration() != null) {
                newConfigurationMessage.getDeviceConfiguration().forEach((key, entry) -> {
                    System.out.println(
                            "\nDevice Id: " + key.split("-")[0] +
                                    " Config Id: " + key.split("-")[1]
                    );

                    entry.getFieldEntries().forEach(field -> {
                        System.out.println("Field " + field.getId() + " has new value: " + field.getValue());
                    });
                });
            }

            if (newConfigurationMessage.getSensorsConfiguration() != null) {
                newConfigurationMessage.getSensorsConfiguration().forEach((key, entry) -> {
                    System.out.println(
                            "\nSensor Id: " + key.split("-")[0] +
                                    " Config Id: " + key.split("-")[1]
                    );

                    entry.getFieldEntries().forEach(field -> {
                        System.out.println("Field " + field.getId() + " has new value: " + field.getValue());
                    });
                });
            }
        }, NewConfigurationMessage.class);

        // Callback for new metadata (for both device and sensors)
        nestHub.on(SignalRMethods.NewMetadata, newMetadataMessage -> {
            System.out.println("\nNew metadata for Device: " + newMetadataMessage.getDeviceId());

            System.out.println("DEVICE METADATA:");
            newMetadataMessage.getDeviceMetadata().forEach((metaKey, metaValue) -> {
                System.out.println(metaKey + " -> " + metaValue);
            });

            System.out.println("SENSORS METADATA:");
            newMetadataMessage.getSensorsMetadata().forEach((sensorId, meta) -> {
                System.out.println("Sensor " + sensorId + " metadata:");
                meta.forEach((metaKey, metaValue) -> {
                    System.out.println(metaKey + " -> " + metaValue);
                });
            });
        }, NewMetadataMessage.class);

        // Callback for new device uplink message (raw data)
        nestHub.on(SignalRMethods.NewUplink, newUplinkMessage -> {
            System.out.println("\nNew uplink message for device " + newUplinkMessage.getDeviceId());
            System.out.println("Raw payload (Base64): " + newUplinkMessage.getPayload() + " with timestamp: " + new Date(newUplinkMessage.getTimestamp()));
        }, NewUplinkMessage.class);

        // Callback for new gateway connectivity status + last uplink message received
        nestHub.on(SignalRMethods.NewGatewayStatus, newGatewayStatusMessage -> {
            System.out.println("\nNew status for Gateway: " + newGatewayStatusMessage.getGatewayId());

            System.out.println("Gateway is " + (newGatewayStatusMessage.getOnline() ? "ONLINE" : "OFFLINE"));
            System.out.println("Gateway's last uplink " + (newGatewayStatusMessage.getLastUplinkDate() != null ? new Date(newGatewayStatusMessage.getLastUplinkDate()) : "UNKNOWN"));
        }, NewGatewayStatusMessage.class);

        nestHub.start().blockingAwait();
        System.out.println(nestHub.getConnectionState());

        // You can subscribe to devices individually sending their ID
        //var subscribeResponse = nestClient.signalrSubscribe(new SignalRSubscribeRequest(nestHub.getConnectionId(), devicesResponse.body().getData().get(0).getId())).execute();

        // Or subscribe to all devices at once
        var subscribeResponse = nestClient.signalrSubscribeAll(new SignalRSubscribeAllRequest(nestHub.getConnectionId())).execute();

        System.out.println(subscribeResponse.isSuccessful() ? "Subscribed successfully!" : "Error subscribing, try again");

        System.out.println("Waiting for real time data, press ENTER key to finish");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        sc.close();

        // You can unsubscribe to real time updates whenever you want, also by individual device
        //var unsubscribeResponse = nestClient.signalrUnsubscribe(new SignalRUnsubscribeRequest(nestHub.getConnectionId(), devicesResponse.body().getData().get(0).getId())).execute();

        // Or unsubscribe from all devices at once
        var unsubscribeResponse = nestClient.signalrUnsubscribeAll(new SignalRUnsubscribeAllRequest(nestHub.getConnectionId())).execute();
        System.out.println(unsubscribeResponse.isSuccessful() ? "Unsubscribed successfully!" : "Error unsubscribing, try again");

        nestHub.close();
    }
}

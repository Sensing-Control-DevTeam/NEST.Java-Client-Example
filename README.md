Requirements
------------
Java 11+

Maven

(Optional) IDE (IntelliJ, VS Code, Eclipse...)

Example details
---------------
This example has two main dependencies, Retrofit2 and SignalR. 

Retrofit2 is used to handle REST API calls, including a simple token refresh mechanism.

SignalR is used to receive real time updates from the server.

Feel free to use any other libraries that you see fit.

Authentication
--------------
Authentication is handled using JWT tokens, after a successful login the server grants an access token and a refresh
token. 

Any requests (other than login itself) sent to the server must include "x-custom-auth" header with the 
access token in it. After expiration time (currently around 12h) the access token must be refreshed using the refresh
token, see TokenAuthenticator.java + TokenInterceptor.java for a concrete example. 

**NOTE:** Refresh tokens are "one use" and a new one is generated after a successful token refresh (or login).

REST API
--------
The methods available are the following (see INestApiClient.java for more details):

**POST** -> "auth/login" (send user + password to get the access and refresh tokens)

**POST** -> "auth/refreshtoken" (send refresh token to get a new set of tokens)

**GET** -> "gateways" (get all gateways)

**GET** -> "devices" (get all devices)

**GET** -> "devices/{deviceId}/{sensorId}/history" (get all data history for a specific device-sensor)

**POST** -> "devices/{deviceId}/command" (send a LoraCommand to the specified device)

**POST** -> "devices/{deviceId}/rawcommand" (send a RAW command to the specified device)

**GET** -> "users" (get all users)

**POST** -> "signalr/subscribe" (subscribe to real time updates for a specific device, needs SignalR connection first)

**POST** -> "signalr/subscribeall" (subscribe to real time updates for all devices, needs SignalR connection first)

**POST** -> "signalr/unsubscribe" (unsubscribe to real time updates for a specific device, needs SignalR connection first)

**POST** -> "signalr/unsubscribeall" (unsubscribe to real time updates for all devices, needs SignalR connection first)

SignalR (real time updates)
---------------------------
The example shows how to subscribe to all possible messages that the server will send with real time data. In order
to be able to subscribe you need to first be logged in. 

Once you're connected and get a ConnectionId you can send it to the server through a REST API call to indicate that
you want to receive real time updates either for one (or more) specific device(s) or to all the devices
at once (subscribe / subscribeAll methods). Whenever you don't want to receive more updates just call the analogous
unsubscribe / unsubscribeAll methods.

Data structure
--------------
All data structures needed are under "model" package. The main data structures are:

- **User** -> Information about a user (id, name, role, etc).

- **Gateway** -> Data to identify a gateway and its current status (connectivity updated every 5 minutes).

- **Device** -> Complete data on a specific Nest Hub.

Devices are a complex structure, besides general information such as id, name, etc they also hold the following:

**Metadata** -> A dictionary holding any additional data sent by the device such as Firmware Id.

**Commands** -> A list of all "device" commands available, including any parameters and their min-max values.

**Configuration** -> A list of all "device" configuration available, including the latest reported value and 
any parameters and their min-max values.

**Sensors** -> A list of all sensors that compose a Nest Hub.

Sensors are also a complex structure and besides general information such as id, name, etc they also hold the following:

**Metadata** -> A dictionary holding any additional data sent by the sensor such as Firmware Id.

**Configuration** -> A list of all "sensor" configuration available, including the latest reported value and
any parameters and their min-max values.

**Readings** -> A list of all data reported by the sensor such as alarm, temperature, humidity, gps, etc. Anything that
the sensor measures and report (See LoRa_CommunicationProtocol_vxx.pdf for more details).

**NOTE:** All dates and timestamps are specified as Epoch Milliseconds.

Sending Commands
----------------
There's up to three ways to send commands to a device, all of them present in the example code. Two of them use
LoraCommand data structure (either from the device/sensor or created from scratch) and the last one uses 
RAW bytes input. See the example and LoRa_CommunicationProtocol_vxx.pdf for more in-depth details.

The commands have been divided in 3 logical groups:

- **Device command** -> Most commands that request some value or direct action (such as reboot) from the device.
- **Device configuration** -> Commands that change the device configuration (such as alive report rate).
- **Sensor configuration** -> Commands that change a specific sensor configuration (such as alarm thresholds).

**NOTE:** You can find a complete list of command identifiers in NestHubCommands.java.

**NOTE:** You can find all sensors and configuration fields/parameters identifiers in NestHubConstants.java.
# MuServe: Android app to connect to Muse EEG

The app can connect to a Muse EEG device, save the data locally to a CSV and stream to an arbitrary IP address/port using UDP. This was the result of a 2 day hack at MannLab in Toronto.

## How

This app shows a list of available Bluetooth LE devices. Select your Muse from the list.

Upon connection the app creates a Service for managing connection and data communication with a GATT server
hosted on a given Bluetooth LE device.

The Activities communicate with the Service, which in turn interacts with the [Bluetooth LE API][2].

The Service sends the preset byte codes and subscribes to the Muse BLE Gatt notifications channels for the 5 electrodes.

## Todo

- networking is currently a bit hacky. Make a service that handles net connection and call the AsyncTask from the service

## Pre-requisites

- Android SDK 28
- Android Build Tools v28.0.3
- Android Support Repository

## Ref

[1] Built with much help from Android BluetoothLeGatt Sample [1].
[2] http://developer.android.com/reference/android/app/Service.html

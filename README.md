# MuServe: Android app to connect to Muse EEG


Built on top of Android BluetoothLeGatt Sample [1].

This sample demonstrates how to use the Bluetooth LE Generic Attribute Profile (GATT)
to transmit arbitrary data between devices.

## How

This sample shows a list of available Bluetooth LE devices and provides
an interface to connect, display data and display GATT services and
characteristics supported by the devices.

It creates a Service for managing connection and data communication with a GATT server
hosted on a given Bluetooth LE device.

The Activities communicate with the Service, which in turn interacts with the [Bluetooth LE API][2].

The Service sends the preset byte codes and subscribes to the Muse BLE Gatt notifications channels for the 5 electrodes.

## Todo

Get streaming going (BLE -> IP converter)a

## Pre-requisites

- Android SDK 28
- Android Build Tools v28.0.3
- Android Support Repository

## Ref

[1] http://developer.android.com/reference/android/app/Service.html

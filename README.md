# BeaconSearchApp
### [Description]: 
An application which is showing nearby BLE devices</br>

### [Concept]:

**What is Beacon ?**

Bluetooth beacons are hardware transmitters — a class of Bluetooth Low Energy(LE) devices that broadcast their identifier to nearby portable electronic devices. 
The technology enables smartphones, tablets and other devices to perform actions when in close proximity to a beacon.
Bluetooth beacons use Bluetooth Low Energy proximity sensing to transmit a universally unique identifier picked up by a compatible app or operating system.

### [Task]:
There are requirements that should be implemented in the application

There are 2 modes in the app:
  * **BLE mode:** 
    * show a list of all visible BLE devices nearby by providing MAC and RSSI details for each item
  * **BEACON mode:** 
    * show a list of all visible beacons nearby by providing UUID, MAJOR, MINOR, RSSI, DISTANCE details for each item
    * show the list of beacons sorted by DISTANCE
    * the beacons list should be updated continiously in real time 
    * implement beacons list item click event by showing information details about the beacon: UUID, MAJOR, MINOR, RSSI and DISTANCE in meters
    
    
### [Tools]:
* AltBeacon - library for detecting beacons on Android
* Android BLE built-in APIs to discover devices which support Bluetooth Low Energy (BLE) 
  
### [Resources]:
* [AltBeacon](https://altbeacon.github.io/android-beacon-library/index.html)
  * [AndroidBeaconLibrary reference app](https://github.com/AltBeacon/android-beacon-library-reference)
* [YouTube: Working with Android Beacons](https://youtu.be/BGNXwWGoR2o)
* [Android built-in Bluetooth LE API](https://developer.android.com/guide/topics/connectivity/bluetooth-le#find)
* [Starting Beacons in Android](https://medium.com/@anmoldua/starting-beacons-in-android-d23c8b388d35)
  * [Code example](https://github.com/anmolduainter/BeaconPlay)
* [The Ultimate Guide to Android Bluetooth Low Energy](https://punchthrough.com/android-ble-guide/)
* [Making Android BLE work (ENG)](https://medium.com/@martijn.van.welie/making-android-ble-work-part-1-a736dcd53b02)
  * [Android Bluetooth Low Energy — готовим правильно (RU)](https://habr.com/ru/post/536392/)
* [Android-BLE-Scan-Example](https://github.com/joelwass/Android-BLE-Scan-Example)
* [BeaconTransmitter: how to transmit as a beacon](https://github.com/jaisonfdo/BeaconTransmitter)
* [Android Beacon Scanner: an advanced example](https://github.com/Bridouille/android-beacon-scanner)
  

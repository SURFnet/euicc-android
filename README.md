Android ICC Tool
===================================

This app allows you to test Android Lollipop TelephonyManager ICC functions

Introduction
------------

1. With the "APDU" Tab you can test the [iccTransmitApduBasicChannel][1] and [iccTransmitApduLogicalChannel][2] functions
2. With the "SIM_IO" Tab you can test the [iccExchangeSimIO][3] function
3. With the "JAVA" Tab you can launch a javacard applet by it's AID and pass parameters in cla, instruction, p1, p2, and Data

Pre-requisites
--------------

- Android SDK 26
- Android Build Tools v27.0.2
- Android Support Repository
- ARA-M applet which supports [UICC Carrier Privileges][4] to be delivered soon

Screenshots
-------------

<img src="screenshots/1-tab1.jpg" height="600" alt="Screenshot"/>
<img src="screenshots/2-tab2.jpg" height="600" alt="Screenshot"/> 
<img src="screenshots/3-tab3.jpg" height="600" alt="Screenshot"/> 

License
-------

Copyright 2017 SURFnet

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.

[1]: https://developer.android.com/reference/android/telephony/TelephonyManager.html#iccTransmitApduBasicChannel(int,%20int,%20int,%20int,%20int,%20java.lang.String)
[2]:
https://developer.android.com/reference/android/telephony/TelephonyManager.html#iccTransmitApduLogicalChannel(int,%20int,%20int,%20int,%20int,%20int,%20java.lang.String)
[3]: https://developer.android.com/reference/android/telephony/TelephonyManager.html#iccExchangeSimIO(int,%20int,%20int,%20int,%20int,%20java.lang.String)
[4]:
https://source.android.com/devices/tech/config/uicc


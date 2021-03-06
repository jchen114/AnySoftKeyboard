ActiveAuthKeyboard
====================
A keyboard that records touches, keystrokes and uploads to Amazon AWS S3 under the current username.

## Functions to note
Inside com -> anysoftkeyboard -> keyboards -> views, there is a java file called AnyKeyboardBaseView. The function onTouch tracks the
touches of the user and prints it to a file.

Inside com -> anysoftkeyboard, there is a file called AnySoftKeyboard.java. Inside this file are functions called onKey, onKeyDown, onKeyUp. These
provide information about the keystrokes and are written into a file.

I believe the files for touches and keystrokes are located in /sdcard/ActiveAuthKeyboard 

AnySoftKeyboard
====================
[![Build Status](https://api.shippable.com/projects/540f72bf21c97efdb898a192/badge?branchName=master)](https://app.shippable.com/projects/540f72bf21c97efdb898a192/builds/latest)

Android (f/w 1.5+) on screen keyboard for multiple languages.  
This application is also available in the Android Play Store, and it is recommended to download it from there:  
[Here](http://market.android.com/details?id=com.menny.android.anysoftkeyboard)  
More on AnySoftKeyboard can be found [here](http://softkeyboard.googlecode.com)

# Features
 * All kinds of keyboards:
  * supporting lots of languages via external packages. E.g., English (QWERTY, DVORAK, AZERTY and Colemak), Hebrew, Russian, Arabic, Lao, Bulgarian, Swiss, German, Swedish, Spanish, Catalan, Belorussian, Portuguese, Ukrainian and many more.
  * special keyboard for text fields which require only numbers.
  * special keyboard for text fields which require email or URI addresses.
 * Physical keyboard is supported as-well.
 * Auto capitalization.
 * Word suggestions.
 * special key-press effects:
  * Sound on key press (if phone is not muted).
  * Vibrate on key press.
 * Voice input. 
 * Any many more features.

License
-------

    Copyright 2013 Menny Even-Danan
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    

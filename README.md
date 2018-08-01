# OnyxFx
Gradle source code repository containing source code and binaries for cross-platform mobile app to complement my blog @ https://bit.ly/JavaFXMobile.
Built and tested on Fedora Linux 28/macOS High Sierra.

Pre-built binaries can be found in the following directories:

Desktop: /build/Distributions/OnyxFxMobile.tar or /""/""/OnyxFxMobile.zip
Android: /build/javafxports/OnyxFxMobile.apk or /""/""/OnyxFxMobile-unaligned.apk
iOS:	/build/javafxports/ios/OnyxFxMobile.ipa or /""/""/""/OnyxFxMobile.app

![alt text](https://raw.githubusercontent.com/afinlay5/OnyxFx/master/blog.png)

# Platform 
	#For Development
		- MacOS X 10.11.5 (or greater) for compilation of iOS project. 	
		- Any platform supporting Java 8 for rest of the proejct.
	#For Execution
		- Mobile: Android 7.1 Nougat (API Level 25)
		- Mobile: iOS Device (iOS 11)
		- Desktop: Any supporting Java SE 8/JavaFX 8.

# Gradle Tasks
	# Android Tasks
	- ./gradlew android will create an Android package signed with a debug keystore

	- ./gradlew androidRelease will create an Android package signed with the configured signingConfig

	- ./gradlew androidInstall installs your Android application on an Android device that is 
	  connected to your development system via a USB cable

	# iOS Tasks
	- ./gradlew launchIOSDevice launches your application on an iOS device that is 
	  connected to your development system

	- ./gradlew launchIPadSimulator launces your application in an iPad simulator

	- ./gradlew launchIPhoneSimulator launches your application in an iPhone simulator

	- ./gradlew createIpa creates an iOS IPA package

	#General 
	- ./gradlew build will build the project
	- ./gradlew run will launch your application on your development system

	#Embedded
	- ./gradlew runEmbedded will launch your application on an embedded device

# Requirements
- Exaclty Oracle JDK 1.8.
- Android Command Line Tools(SDK v.27)
- XCode 9.2 (or greater)
- Gradle 4.2 (or greater)
- Git Large File Storage (v.2.5.0)

# Known Problems
- N/A (08/01/2018).

# Execution Screenshots
#Desktop
![alt text](https://raw.githubusercontent.com/afinlay5/OnyxFx/master/exc_screenshots/DESKTOP_1.png)
![alt text](https://raw.githubusercontent.com/afinlay5/OnyxFx/master/exc_screenshots/DESKTOP_2.png)
#Android
![alt text](https://raw.githubusercontent.com/afinlay5/OnyxFx/master/exc_screenshots/Android_1.png)
![alt text](https://raw.githubusercontent.com/afinlay5/OnyxFx/master/exc_screenshots/Android_2.png)
#iOS
![alt text](https://raw.githubusercontent.com/afinlay5/OnyxFx/master/exc_screenshots/iPhone6_1.png)
![alt text](https://raw.githubusercontent.com/afinlay5/OnyxFx/master/exc_screenshots/iPhone6_2.png)
![alt text](https://raw.githubusercontent.com/afinlay5/OnyxFx/master/exc_screenshots/iPad_1.png)

# Resources to explore
-	JavaFxMobile Plugin Git Repo: https://github.com/javafxports/javafxmobile-plugin
-	JavaFxPorts Documentation: http://docs.gluonhq.com/javafxports/#_how_it_works
-	JavaFxPorts Homepage: http://javafxports.org/
-	Gluon Documentation: https://gluonhq.com/developers/documentation/
-	Google Groups Page for JavaFxPorts: https://groups.google.com/forum/#!forum/javafxports
-	StackOverflow Page for JavaFxPorts: https://stackoverflow.com/questions/tagged/javafxports
-	Gluon Mobile Pricing/License Options: https://gluonhq.com/products/mobile/buy/
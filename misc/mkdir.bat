:: This Script automates the creating of our project directories.
@ECHO OFF

ECHO "Creating platform independent directories...."
MD src/main/java
MD src/main/resources

ECHO "Creating directories for Android...."
MD src/android/java
MD src/android/resources
MD src/android/assets
MD src/android/jniLibs

ECHO "Creating directories for iOS...."
MD src/ios/java
MD src/ios/resources
MD src/ios/assets
MD src/ios/jniLibs
MD src/ios/frameworks

ECHO "Creating directories for Desktop(Windows/Mac/Linux)...."
MD src/desktop/java
MD src/desktop/resources

ECHO "Creating directories for Embedded...."
MD src/embedded/java
MD src/embedded/resources

ECHO "Creating application package directories...."
MD src/main/java/me/adriandavid
MD src/android/java/me/adriandavid
MD src/ios/java/me/adriandavid
MD src/desktop/java/me/adriandavid
MD src/embedded/java/me/adriandavid

ECHO "Done"
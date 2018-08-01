#!/bin/bash

echo "Creating platform independent directories...."
mkdir -p src/main/java
mkdir -p src/main/resources

echo "Creating directories for Android...."
mkdir -p src/android/java
mkdir -p src/android/resources
mkdir -p src/android/assets
mkdir -p src/android/jniLibs

echo "Creating directories for iOS...."
mkdir -p src/ios/java
mkdir -p src/ios/resources
mkdir -p src/ios/assets
mkdir -p src/ios/jniLibs
mkdir -p src/ios/frameworks

echo "Creating directories for Desktop(Windows/Mac/Linux)...."
mkdir -p src/desktop/java
mkdir -p src/desktop/resources

echo "Creating directories for Embedded...."
mkdir -p src/embedded/java
mkdir -p src/embedded/resources

echo "Creating application package directories...."
mkdir -p src/main/java/me/adriandavid
mkdir -p src/android/java/me/adriandavid
mkdir -p src/ios/java/me/adriandavid
mkdir -p src/desktop/java/me/adriandavid
mkdir -p src/embedded/java/me/adriandavid

echo "Done"
# Description

Gitty is an Android mobile app taking advantage of Github API to fetch basic data about existing repositories, like repository id or commits details.
One might also peek the search history and revisit repository details by choosing one of the history entries, as well as email the selected commits data.

# Running Gitty

## Android Studio

Select Run -> Run `app` (or Debug `app`) from the menu bar
Select the device you wish to run the app on and click 'OK'

## Gradle

To nstall the debug APK on your device execute task `./gradlew installDebug`

Start the APK:
```
<path to Android SDK>/platform-tools/adb -d shell am start com.apap.gitty.presentation.MainActivity
```

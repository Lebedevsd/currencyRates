---
marp: true
---

# CurrencyRates is a test application

As an architecture for the application is was choosen MVI + Redux.
As far as rates should be always up to date I havent implemented caching layer.

Set of libraries that were used:
* Rerofit
* Dagger
* RxJava2
* Moshi
* Glide
* Navigation
* ReactiveNetwork
* Adapterdelegates4
* ViewModels

---

To test please execute `./gradlew test`

To build release please execute: `./gradlew assembleRelease`

To deploy release version of the application please run `adb install app/build/outputs/apk/release/app-release.apk` from pleject directory.

It will use release_key, note **NEVER** put your own release keys under version control.

---


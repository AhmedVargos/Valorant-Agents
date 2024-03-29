<h1 align="center"> Valorant Agents </h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://kotlinlang.org"><img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-1.4.xxx-blue"/></a>
  <img alt="MVVM" src="https://img.shields.io/badge/MVVM-Architecture-orange"/>
  <a href="https://developer.android.com/kotlin/coroutines"><img alt="Coroutines" src="https://img.shields.io/badge/Coroutines-Asynchronous-red"/></a>
</p>

![UI](https://github.com/AhmedVargos/Valorant-Agents/blob/master/screenshots/basic_design.jpg)
<p align="center">
  Design by: <a href="https://dribbble.com/shots/14073476-Valorant-Agents">Malik Abimanyu</a>
</p>

<p align="center">
Valorant Agents app is a small Multi-Module demo application to demonstrate modern Android application tech-stacks with a Multi-module and MVVM architecture, with emphasize on Unit & UI testing.
</p>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
- [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/index.html)
- [Koin](https://insert-koin.io) for dependency injection.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) is a dependency injection library for Android, it can be viewed in a different branch [here](https://github.com/AhmedVargos/Valorant-Agents/blob/using_hilt_for_di/README.md)
- [JetPack](https://developer.android.com/jetpack)
  - LiveData - Notify domain layer data to views.
  - Lifecycle - Dispose of observing data when lifecycle state changes.
  - Fragment-ktx - A set of Kotlin extensions that helps with fragment lifecycle.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Allows you to more easily write code that interacts with views
  - ViewPager2 - ViewPager2 replaces ViewPager, addressing most of its predecessor’s pain-points.
  - ViewModel - UI related data holder, lifecycle aware.
  - SharedViewModel - To make communication between fragments and host activity.
  - Room Persistence - construct a database using the abstract layer.
- Architecture
  - Multi-module design for the app.
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern (NetworkBoundResource)
  - Clean Architecture approach.
- [Gradle KotlinDsl](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [Moshi](https://github.com/square/moshi) - A Modern JSON library for Android and Java.
- [Glide](https://github.com/bumptech/glide) - For Loading images from Urls.
- [Barista](https://github.com/AdevintaSpain/Barista) - Barista makes developing UI test faster, easier and more predictable.
- [Ktlint](https://github.com/pinterest/ktlint)- An anti-bikeshedding Kotlin linter with a built-in formatter.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components.
- [CI/CD with github Actions](https://docs.github.com/en/actions) - Automate, customize, and execute your software development workflows.

## Architecture
Valorant Agents is Multi-modular application with a meaningful separation for layers and features with the necessary grouping.
With MVVM architecture with an additional Domain layer for each module by itself.

Modules Design:
- App
- Core:
    - Base
    - Local
    - Remote
    - Navigator
    - UiComponents
- Features:
    - Home
    - Agent_Details
    - Favorites
    - Agent_List

![architecture](https://github.com/AhmedVargos/Valorant-Agents/blob/master/screenshots/arch_diagram.jpg)

## Open API
Valorant Agents uses the [Valorant-api](https://dash.valorant-api.com/) for required data.
Valorant-api provides an extensive API containing data of most in-game items, assets and more!

## Tasks
- [x] Creating the project structure

- [x] Implementing all features

- [x] Writing test cases

# License
```xml
Designed and developed by 2020 AhmedVargos

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

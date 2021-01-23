# Valorant Agents
[![kotlin](https://img.shields.io/badge/Kotlin-1.4.xxx-blue)](https://kotlinlang.org/) [![MVVM](https://img.shields.io/badge/MVVM-Architecture-orange)]() [![coroutines](https://img.shields.io/badge/Coroutines-Asynchronous-red)](https://developer.android.com/kotlin/coroutines)

![UI](https://github.com/AhmedVargos/Valorant-Agents/blob/master/screenshots/basic_design.jpg)

Valorant Agents app is a small Multi-Module demo application to demonstrate modern Android application tech-stacks with a Multi-module and MVVM architecture.

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
- [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/index.html)
- [Koin](https://insert-koin.io) for dependency injection.
- [JetPack](https://developer.android.com/jetpack)
  - LiveData - Notify domain layer data to views.
  - Lifecycle - Dispose of observing data when lifecycle state changes.
  - Fragment-ktx
  - ViewPager2
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
- [Gson](https://github.com/google/gson)
- [Glide](https://github.com/bumptech/glide) - Loading images.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components.

## Architecture
Valorant Agents is Multi-moduled application with a meaningful separation for layers and features with the necessary grouping.
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
[x] Creating the project structure

[-] Implementing all features

[-] Writing test cases

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
# ![launcher icon](logo.png) Rick and Morty [![Actions Status](https://github.com/mohsenoid/Rick-and-Morty/workflows/Android%20CI/badge.svg)](https://github.com/mohsenoid/Rick-and-Morty/actions) [![codecov](https://codecov.io/gh/mohsenoid/Rick-and-Morty/branch/master/graph/badge.svg)](https://codecov.io/gh/mohsenoid/Rick-and-Morty)

This repository contains Rick and Morty Android application which I am using as training material.

![Screenshot](SCREENSHOT1.png) ![Screenshot](SCREENSHOT2.png)

## Technical details

The Application implemented and structured based on **Clean Architecture** and **SOLID** principles best practices and the presentation layer is implemented based on the **MVP** pattern.

The **Data** layer contains **Network Client** implemented by Retrofit library to get access to remote data and **DB** implemented by Room library to persist those data locally in case of offline usage.

The **Domain** layer consist of **Repository** which allows access to the Data layer. It also uses Coroutines **IO Dispatcher** and **Main Dispatcher** to run long-running tasks in the background and reflect the result on UI. There is also a **Test Dispatcher Provider** which runs immediately on same thread in unit tests.

The **View** layer multiple Activity and Fragment which use their contract to implement the view interface and use presenter to respond to user interactions.

The  **Dependencies Provider** does the Dependency Injection in the whole app. It also uses **Base** objects to inject dependencies into **Activities** and **Fragments**.

[**GitHub Actions CI service**](https://github.com/mohsenoid/Rick-and-Morty/actions) is running the repo test and build Gradle tasks and **jacoco** plugin generates and submit the code coverage reports to [**codecov.io**](https://codecov.io/gh/mohsenoid/Rick-and-Morty).

There are some unit tests using **Mockito** and some Android tests using **Robolectric**.

## References

Data provided by [Rick And Morty API](https://rickandmortyapi.com/)

Endless Recycler View made by [@nesquena](https://gist.github.com/nesquena/d09dc68ff07e845cc622)

App Launcher Icon made by [freepngimg.com](http://freepngimg.com)

Dead/Alive Icons made by [Freepik](https://flaticon.com/authors/freepik) from [flaticon.com](https://flaticon.com)

Timber logger library made by [Jake Wharton](https://github.com/JakeWharton/timber)

Picasso image downloading and caching library made by [square](https://github.com/square/picasso)

Retrofit HTTP API library made by [square](https://github.com/square/retrofit)

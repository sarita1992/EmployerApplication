# EmployerApplication

A simple Android application that displays a searchable list of employers using data from a remote API. Built with **MVVM architecture**, **Jetpack Compose**, **Kotlin Coroutines**, **Flow**, **Retrofit**, and **Hilt (DI)** — with robust error handling and clean architecture principles.

---

## 🚀 Features

- 🔍 Search employers by name
- 📋 View list of employers with their name
- 📄 Employer detail screen with name, Id, place and discount
- 🧠 Built with Clean Architecture & MVVM
- 💉 Hilt for dependency injection
- 🌐 Retrofit for API calls
- 🔄 StateFlow and sealed classes for reactive UI state handling
- 📱 Jetpack Compose for modern UI
- ✅ Error and empty state handling
- 🔁 Navigation between screens using `NavHost`

---

## 🧱 Architecture

- Presentation (Jetpack Compose + ViewModel + Navigation)
- ↓
- ViewModel (StateFlow<ResultState>)
- ↓
- UseCase (business logic)
- ↓
- Repository (abstracts data source)
- ↓
- Remote API (Retrofit service)


State is communicated using:

```kotlin
sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    data class Success<T>(val data: T) : ResultState<T>()
    object Empty : ResultState<Nothing>()
    data class Error(val message: String) : ResultState<Nothing>()
}
```

## API
 ```http
 GET https://cba.kooijmans.nl/CBAEmployerservice.svc/rest/employers?filter=Achmea&maxRows=100
 ```

```
#### Response includes:
Name
EmployerId
DiscountPercentage
Place
```

## Tech Stack
- Kotlin
- Jetpack Compose
- MVVM + Clean Arch
- Hilt
- Retrofit
- Coroutines + Flow
- Navigation Compose
- StateFlow

## How to Run
- Clone the repository:
  https://github.com/sarita1992/EmployerApplication.git
- Open in Android Studio
- Sync Gradle
- Run on emulator/device (min SDK 21+)

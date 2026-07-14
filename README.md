# PicoBotella

## Overview
PicoBotella is a modern Android application designed to bring the classic "spin the bottle" game to your mobile device. The app combines social interaction with entertaining challenges and Pokémon integration, providing a fun and engaging experience for users. Developed using the latest Android development standards, it ensures a smooth and robust performance.

## Main Features
*   **Bottle Spinning System:** Realistic bottle rotation physics and animations for a classic game feel.
*   **Random Challenges:** A dynamic system that selects fun challenges from a local database.
*   **Pokémon Integration:** Integration with the PokeAPI (via Biuni's Pokedex) to display random Pokémon alongside challenges.
*   **Local Challenge Management:** Users can fully manage their own set of challenges stored locally.
*   **CRUD Operations:** Complete Create, Read, Update, and Delete functionality for custom challenges.
*   **Animations:** Interactive UI elements with polished animations and transitions.
*   **Audio System:** Background music management with toggle functionality to enhance the game atmosphere.
*   **Navigation System:** Seamless transitions between screens using the Android Navigation Component.

## Architecture
The project follows a modern and scalable architecture:
*   **MVVM (Model-View-ViewModel):** Decouples the UI logic from the business logic for better maintainability.
*   **Repository Pattern:** Abstracts the data source, managing both local (Room) and remote (Retrofit) data.
*   **Single Activity Architecture:** Utilizes a single activity with multiple fragments for optimized performance.
*   **Navigation Component:** Centralized navigation management for clear and efficient app flow.
*   **Room Database:** Local persistence for storing and managing user-defined challenges.
*   **Retrofit:** Type-safe HTTP client for consuming the Pokémon API.
*   **Coroutines:** Efficient management of asynchronous tasks and background operations.
*   **LiveData:** Observable data holders that ensure the UI stays updated with the latest state.

## Project Structure
```
app/
├── data/          # Room database and DAO definitions
├── model/         # Data models and API response entities
├── repository/    # Data source abstraction layer
├── view/          # UI components (Activities, Fragments, Adapters, Dialogs)
├── viewmodel/     # Business logic and UI state management
├── webservice/    # Retrofit API service and client configuration
└── utils/         # Helper classes and constant definitions
```

## Technologies Used
*   **Kotlin:** The primary programming language used for development.
*   **Android SDK:** Core framework for Android application development.
*   **Room:** Persistence library for local database management.
*   **Retrofit:** REST client for API integration.
*   **Glide:** Powerful image loading and caching library.
*   **Coroutines:** Kotlin's solution for non-blocking asynchronous code.
*   **Material Design 3:** Modern design system for a polished look and feel.
*   **Navigation Component:** Fragment-based navigation management.
*   **LiveData & ViewModel:** Architecture components for reactive UI.
*   **RecyclerView:** Efficient display of large data sets.
*   **Lottie:** Support for high-quality JSON-based animations.

## Screens and Flow
*   **Splash Screen:** Animated entry point that initializes app resources.
*   **Home:** The main game screen where users spin the bottle and start the fun.
*   **Challenge List:** A management screen to view all currently available challenges.
*   **Add/Edit/Delete Dialogs:** Intuitive interfaces for managing the challenge database.
*   **Random Challenge Dialog:** Displays the result of the spin, showing a random Pokémon and a challenge.
*   **Instructions:** Clear guidance on how to play the game and its rules.
*   **Rate App:** Integrated feedback system for users to rate their experience.
*   **Share App:** Functionality to easily share the app with friends and family.

## Installation
To get started with the project locally:
1.  Clone the repository:
    ```bash
    git clone https://github.com/your-repo/PicoBotella.git
    ```
2.  Open the project in **Android Studio**.
3.  Let the **Gradle** sync complete.
4.  Build and run the application on an emulator or a physical device.

## Team / Academic Context
This project was developed as part of an academic initiative focused on implementing professional Android development practices. It demonstrates the integration of complex data flows, local persistence, and remote API consumption within a clean and maintainable codebase.

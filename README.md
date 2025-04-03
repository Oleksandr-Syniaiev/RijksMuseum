# Rijks Museum Android Application

An Android application that showcases artworks from the Rijks Museum collection using their public API.

## Features

- Browse through a collection of artworks from the Rijksmuseum
- View detailed information about each artwork
- Clean Architecture implementation with a modular project structure

## Architecture

The application follows Clean Architecture principles and is organized into the following modules:

- **app**: Main application module, contains the entry point and DI setup
- **core**: Contains shared utilities, UI components, and base classes
  - **core:ui**: UI components, themes, and navigation utilities
  - **core:utils**: Common utilities and error handling
- **data**: Data layer implementation
  - **data:museum**: Contains the Rijks Museum API service and repository implementation
- **domain**: Domain layer with business logic
  - **domain:museum**: Contains use cases and repository interfaces
- **feature**: Feature modules for different screens
  - **feature:museum**: Museum-related features including list and details screens

## Technologies

- **UI**: Jetpack Compose with Material 3
- **Architecture**: MVI with Clean Architecture
- **Dependency Injection**: Hilt
- **Navigation**: Jetpack Navigation Compose
- **Image Loading**: Coil
- **Networking**: Retrofit with OkHttp
- **Serialization**: Kotlin Serialization
- **Code Quality**: Detekt, Ktlint
- **Concurrency**: Kotlin Coroutines and Flow

## Getting Started

### Prerequisites

- Android Studio Flamingo or newer
- JDK 17 or higher
- Android SDK 33 or higher

### Setup

1. Clone the repository
2. Open the project in Android Studio
3. For test purposes I've shared API key, however it is bad practice to keep API keys in git. In real production project,
you should use a secure way to store and access API keys. For this project, you can use the provided API key for testing purposes.
**Note**: The provided API key is for demonstration purposes only.
You can add your own Rijks Museum API key in `\build-system\plugins\src\main\resources\credentials\api_keys.properties`:
   ```
   API_KEY=your_api_key_here
   ```
4. Build and run the application

## Project Structure

The project follows a modular, multi-layer architecture:

```
├── app                     # Main application module
├── build-system            # Build configurations and dependencies
├── core                    # Core modules
│   ├── ui                  # UI components and themes
│   └── utils               # Common utilities
├── data                    # Data layer modules
│   └── museum              # Museum data sources and repositories
├── domain                  # Domain layer modules
│   └── museum              # Museum use cases and models
└── feature                 # Feature modules
    └── museum              # Museum feature implementation
```

## Testing

The project includes unit tests for the domain and data layers. Run tests with:

```
./gradlew test       # Run unit tests
```

## Code Quality

The project uses Detekt and Ktlint for static code analysis. Run the checks with:

```
./gradlew detekt     # Run Detekt
./gradlew ktlintCheck  # Run Ktlint
```

## Versioning

The application follows a versioning system defined in `build-system/plugins/src/main/resources/version/version.properties`:

```
MAJOR=2025
MINOR=04
PATCH=0
BUILD=0
VERSION_CODE=1
```

Versioning follows these conventions:
- **MAJOR**: Represents the year of release
- **MINOR**: Represents the month of release
- **PATCH**: Increments for bug fixes and changes
- **BUILD**: Increments for each build within the same version
- **VERSION_CODE**: Android's internal version number 

To update the version for a new release, modify the appropriate values in the version.properties file.

## License

This project is a code assessment and is not licensed for public use.

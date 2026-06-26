# Pull Request Details

## 1. Branch Name
`feature/modernization-and-settings-backup`

## 2. PR Title
`Modernize Architecture and Implement Settings Export/Import`

## 3. PR Description
This Pull Request modernized the legacy Java codebase and introduces a new Settings Backup & Restore feature.

### Key Changes:
- **Modernization**: Upgraded the project to **target SDK 37** and **JDK 21**. Migrated build scripts (`build.gradle`, `settings.gradle`) to **Kotlin DSL (.kts)** and upgraded Gradle to **9.6.0** with AGP **9.2.1**.
- **Hybrid Support**: Configured the environment for Kotlin/Java interoperability, allowing future development to be entirely in Kotlin.
- **Settings Export/Import**: Added a robust backup feature in the settings screen. Users can now export their configuration to a JSON file and import it back.
- **Security & Stability**:
    - Implemented strict validation for imported settings files to prevent crashes.
    - Added a 512KB file size limit for imports to ensure memory safety.
    - Updated broadcast receiver registration in `CodeBoardIME.java` to use `ContextCompat` with explicit export flags for API 34+ compliance.
- **Bug Fixes**: Resolved the Release APK installation issue by correcting the `AndroidManifest.xml` structure and ensuring proper signing.

## 4. UI Library Version
The project is currently using **Material Components (MDC) version 1.12.0**. The theme is based on `Theme.MaterialComponents.DayNight.DarkActionBar`.
It is **not** using Material 3 (M3) yet, but it is using the latest stable Material 2 (MDC) components.

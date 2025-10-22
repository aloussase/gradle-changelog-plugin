# AGENTS.md - Development Guidelines

## Build/Test Commands
- `./gradlew build` - Build all modules
- `./gradlew run` - Build and run the application  
- `./gradlew check` - Run all checks including tests
- `./gradlew test` - Run all tests
- `./gradlew :app:test` - Run tests for app module only
- `./gradlew clean` - Clean build outputs

## Code Style Guidelines
- **Language**: Kotlin with JVM toolchain 21
- **Package naming**: Use reverse domain notation (`io.github.aloussase.*`)
- **Imports**: Standard Kotlin imports, use kotlinx ecosystem libraries when available
- **Formatting**: Follow Kotlin coding conventions, 4-space indentation
- **Types**: Explicit types where clarity improves, leverage Kotlin's type inference
- **Naming**: camelCase for functions/variables, PascalCase for classes
- **Error handling**: Use Kotlin's Result type or exceptions appropriately

## Project Structure
- Multi-module Gradle project with convention plugins in `buildSrc`
- Version catalog in `gradle/libs.versions.toml` for dependency management
- Build logic shared via convention plugin `buildsrc.convention.kotlin-jvm`
- Uses Gradle wrapper (`./gradlew`) - always use this over system Gradle

## Dependencies
- Core: Kotlin 2.1.21, kotlinx-datetime, kotlinx-serialization, kotlinx-coroutines
- Test framework: JUnit Platform (configured in convention plugin)
# Social-Jaw

![App Logo](app_logo.png)

## Description

The Social Media App is a platform where users can connect, share updates, and engage with each other. It allows users to create profiles, post updates, follow other users, like and comment on posts, and stay connected with their social network.

## Features

- User Registration and Login with Google Sign in ✅
- Home Feed containing posts from fellow users ✅
- Post Creation and Viewing ✅
- Shorts/Reels Screen ✅
- User Profile ✅
- Follow and Unfollow Users ✅
- Like and Comment on Posts ✅
- Notifications (Like, Follow, Comment, Promotional, App Update, Security) ✅
- Options to turn on/off one or more category of notifications ✅
- User Account settings ✅
- App Preferences settings (themes and more) ✅
- Help Center ✅
- Privacy Settings ✅
- Terms and Conditions ✅
- Onboarding Screen ✅
- Open Source ✅

## Screenshots

![Screenshot 1](screenshots/screenshot1.png)
![Screenshot 2](screenshots/screenshot2.png)
![Screenshot 3](screenshots/screenshot3.png)

## Demo Video

Check out the demo video of the Social Media app on YouTube:

[![Demo Video](https://img.youtube.com/vi/YOUR_VIDEO_ID_HERE/0.jpg)](https://www.youtube.com/watch?v=YOUR_VIDEO_ID_HERE)

## Architecture

The Social Media app follows the **MVVM (Model-View-ViewModel)** architecture pattern. The app's codebase is organized into the following packages:

- `di`: Contains the AppModule class for providing dependecies needed for app
- `data`: Handles data retrieval and manipulation, including interaction with Firebase Firestore.
- `services`: Contains Firebase messaging service for handling app notifications
- `ui`: Implements the UI using Jetpack Compose and handles user interactions.
- `utils`: Cotains utility classes for use throught the app module

The app utilizes the following architectural components:

- **ViewModel**: Manages and provides data to the UI components, maintaining separation between the UI and business logic.
- **Repository**: Acts as a single source of truth for data, abstracting the data sources and providing data to the ViewModel.
- **Dependency Injection**: Uses Dagger Hilt for dependency injection, enabling easy management of dependencies throughout the app.

## Technologies Used

- Kotlin
- Android Jetpack (Compose, Navigation, ViewModel, etc.)
- Firebase (Authentication, Firestore, Storage, Cloud Messaging)
- Material Design Components
- Retrofit and Json

## Getting Started

To run the app locally, follow these steps:

1. Clone the repository: (https://github.com/pareekdevansh/Social-Jaw/new/master)

2. Open the project in Android Studio.

3. Set up Firebase project and configure the necessary services (Authentication, Firestore, Storage) by following the Firebase documentation.

4. Update the Firebase configuration file (`google-services.json`) with your own project details.

5. Build and run the app on an Android device or emulator.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvement, please create a new issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Co-Developed By

- [Devansh Pareek](https://github.com/pareekdevansh)
- [Vibhu](https://github.com/Joaquin144)

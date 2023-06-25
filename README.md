# Social-Jaw

<img src="https://github.com/Joaquin144/CoinTrivia/assets/80385154/975453a2-5276-44f8-9b89-2a1ca895d9ed" height="200">

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
- Search Users ✅
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

|   HomeScreen🏡    | Dark Mode support🌛   |   Post Comments💬   
|---	|---	|---
|  ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/9195b18b-dd96-472c-83ae-e6a27df5cc69)    |  ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/511dd3f9-c467-4f5a-940b-58f2987edbaa)    |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/77c2bd17-5315-43ea-834e-b58af6ce09cb)    

|   Scroll Shorts📹  |   More Shorts🎥    | Create Posts📤   |
|---    |---	|---	|
|   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/4f50f49e-0590-43bb-904a-4701993b399e)    |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/3e3be1f9-a4b2-4641-af08-85f571348f6f)      |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/af0e9046-27ab-4bc8-a2f0-fdd0599b806f)

|   Control Permissions👮‍♂️    | User Profile🪞    |   User Settings ⚙️  
|---	|---	|---
|  ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/a9d0ec8d-0038-4b0a-ad6e-1dfd48feff68)    |  ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/5da85f6d-1ef1-456c-825d-9c2d12eb8bcd)    |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/711945cf-2d65-4a05-9f9f-b7c272150050)    

|   Notification Settings 🔔 |   Notification 📩   | Update Details 📝  |
|---    |---	|---	|
|   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/c858b1fa-da5e-43d0-89cb-9b60abbb0167)    |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/291d91a4-b4a4-4d41-b2d7-2ef318188834)      |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/5e644938-e1e3-45ea-9626-47262b66d408)

|   Switch Themes 🕚 |   Privacy Policy 🔐   | Search Users 🔎  |
|---    |---	|---	|
|   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/c1fe3771-8d0b-4cf9-8de8-82e0cd1922e0)    |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/8ce78105-1a4b-419f-8819-cacb2e7a19f6)      |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/0d89693d-fc4b-40d8-85d8-cc850eef76d0)
<br />

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

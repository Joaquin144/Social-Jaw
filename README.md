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
|  ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/90f9e70f-b3a5-46e0-a076-08763d00d7ee)    |  ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/9964d7a9-66c0-42cd-925b-ab3e80a64e81)    |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/76c30947-69d3-4946-b6a0-5fe385511866)    

|   Scroll Shorts📹  |   More Shorts🎥    | Create Posts📤   |
|---    |---	|---	|
|   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/1d5d3abb-4258-4de3-8560-895e5e3f9a25)    |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/e606dbbb-64c1-4d87-b956-1ec938ee25f3)      |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/4efda8ba-cbc5-4e98-aa2e-945e7a7ca9e8))

|   Control Permissions👮‍♂️    | User Profile🪞    |   User Settings ⚙️  
|---	|---	|---
|  ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/60c39ae1-5fec-403e-8df9-cbb620c5832e)    |  ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/83b1ef74-0f0a-4cd8-811d-e68ecba72cae)    |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/1851b59f-28cf-41b6-a345-f9bc8318babd)    

|   Notification Settings 🔔 |   Notification 📩   | Update Details 📝  |
|---    |---	|---	|
|   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/080a9056-8295-4594-82d6-fa136ab7a756)    |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/35e6fae0-b405-4ee3-98c0-2432e288e7ab)      |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/5d17d9e6-81b9-42d1-8e3f-318913a1914b)

|   Switch Themes 🕚 |   Privacy Policy 🔐   | Search Users 🔎  |
|---    |---	|---	|
|   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/50bf06ab-f290-45ad-8fbe-75f1ecb9f75e)    |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/a2a711a3-35ea-4f1f-9253-37ca511ae5f5)      |   ![](https://github.com/Joaquin144/CoinTrivia/assets/80385154/248177c7-9eea-4b79-a5bc-48a31c9e417a)
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

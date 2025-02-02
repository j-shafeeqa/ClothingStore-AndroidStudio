# Clothing Store Mobile App
The e-commerce clothing store app facilitates seamless management for the store by enabling them to effortlessly add or modify product information using Firebase. Users can create accounts, browse products, interact with the shopping cart, and conveniently edit their profile details provided during sign-in and checkout. The application also ensures robust data validations to enhance security and accuracy throughout the application.

## Overview
The **Clothing Store Mobile App** is a fully functional e-commerce application designed to provide users with a seamless shopping experience. Built using Android technologies, the app allows users to browse products, add items to their cart, and securely complete purchases.

## Features
- **User Authentication**: Secure login and signup functionality with Firebase Authentication.
- **Product Catalog**: Display of clothing items with images, descriptions, and prices using Firestore.
- **Add to Cart**: Users can add/remove items from the cart, with real-time price updates.
- **Checkout Process**: Secure checkout with address management and card payment verification.
- **Navigation System**: Implemented using MeowBottomNavigation for a smooth user experience.
- **Data Validation**: Integrated validation for user inputs and stored data to ensure accuracy and security.
- **Profile Management**: Users can edit their profile details provided during sign-in and checkout.
- **Store Management**: Store owners can effortlessly add or modify product information using Firebase.

## Tech Stack
- **Frontend**: Android (Java, XML, Material 3)
- **Backend**: Firebase Firestore for real-time data management
- **Authentication**: Firebase Authentication
- **Image Handling**: Picasso Library
- **Navigation**: MeowBottomNavigation

## Architecture
The project follows an **MVVM (Model-View-ViewModel)** architecture to separate concerns and maintain a clean codebase:
- **Model**: Data models representing products, users, and orders.
- **View**: XML-based UI elements designed with Material 3 components.
- **ViewModel**: Manages UI logic and interacts with Firestore for fetching and updating data.

## Installation & Setup

### Clone the repository:
```sh
git clone https://github.com/your-repo/clothing-store-app.git
```

## Future Enhancements
- Implement personalized recommendations using Firebase ML Kit.
- Introduce order tracking functionality.
- Improve UI responsiveness with Jetpack Compose.
- Enable third-party payment gateways like Stripe.

## Screenshots
<p align="center">
  <img src="https://github.com/user-attachments/assets/01a044e7-2256-44ee-8778-081c067f5ae3" width="200">
  <img src="https://github.com/user-attachments/assets/27fe1322-411f-44ca-9860-70b3c327a132" width="200">
  <img src="https://github.com/user-attachments/assets/ca8a9ab9-0af2-44d6-b143-986ff1cebd55" width="200">
  <img src="https://github.com/user-attachments/assets/fbee84a0-6c99-4b72-9146-9ffa012b67b6" width="200">
</p>



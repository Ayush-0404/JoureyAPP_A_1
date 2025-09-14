# Multi-Stop Flight Journey Tracker

This project is a mobile app that tracks a **multi-stop journey**. The app displays **routes with stops**, visa requirements, **distance and time remaining**, and overall **journey progress**. 

There are **two versions** of the app:

- **XML & Kotlin Version**  
  Uses traditional Android XML layouts and Kotlin for UI and logic.
  
- **Jetpack Compose Version**  
  Uses modern **Jetpack Compose** for a clean, reactive UI.

---

## 🚀 Features

 **User Journey Setup** – Enter a start location and destination.  
 **Route Selection** – Choose from possible routes.  
 **Dynamic Stop List** – Stops are loaded from `stops.txt` dynamically.  
 **Distance Unit Toggle** – Switch between **KM** and **Miles**.  
 **Progress Tracking** – A **progress bar** and text summary show remaining distance.  
 **Visual Feedback for Stops** – Stops are **grayed out** when reached.  
 **Reset Journey** – Restart the journey anytime.  

---

## 📂 Project Structure

This repository has **two main branches**:

1. **`xml-version`** – Traditional XML UI with Kotlin.  
2. **`compose-version`** – Jetpack Compose UI.  

###  Directory Structure (Jetpack Compose Version)
app/
└── src/
    └── main/
        ├── java/com/example/yourapp/
        │   ├── MainActivity.kt         // App entry point and Compose navigation
        │   ├── AppNavigation.kt        // Navigation setup
        │   ├── StartScreen.kt          // Screen to enter journey details
        │   ├── RouteSelectionScreen.kt // Screen to choose a route
        │   ├── JourneyScreen.kt        // Journey progress screen with stops list
        │   ├── StopItem.kt             // Composable for displaying a stop
        │   └── Stop.kt                 // Data class for a journey stop
        └── assets/
            └── stops.txt               // File containing stops data

For **XML & Kotlin**, similar files exist with **activity XML layouts**.

---

## 🔧 Getting Started

###  Prerequisites

- **Android Studio** (latest version recommended)
- **Android SDK** (with correct minimum SDK)
- **Git** (for cloning and version control)

### 📥 Installation

1. **Clone the repository**
   ```sh
   git clone https://github.com/yourusername/multi-stop-journey-tracker.git
   cd multi-stop-journey-tracker
2. **Checkout the desired branch**

*XML & Kotlin version*:
git checkout xml-version

*Jetpack Compose version*:
git checkout compose-version

3.*Open the project in Android Studio*

4.*Sync Gradle to download dependencies*


## 🌟 App Versions

###  XML & Kotlin Version (`xml-version` branch)

#### 🔹 Implementation:
Uses **XML layouts** for UI and **Kotlin** for logic and UI handling via `findViewById()`.

#### 🔹 Key Files:
- `activity_main.xml` – Layout file.
- `MainActivity.kt` – Entry point.
- Adapter files for handling stop lists.

### 🎨 Jetpack Compose Version (`compose-version` branch)

#### 🔹 Implementation:
UI is fully built with **Jetpack Compose** for a modern, declarative UI experience.

#### 🔹 Key Files:
- `MainActivity.kt` – Entry point with Compose navigation.
- `AppNavigation.kt` – Handles navigation between screens.
- `StartScreen.kt` – Input journey details.
- `RouteSelectionScreen.kt` – Select a route.
- `JourneyScreen.kt` – Displays journey stops, progress, and controls.
- `StopItem.kt` – Composable UI for stop display.
- `Stop.kt` – Data class for a journey stop.
- `stops.txt` (inside `assets/`) – Contains stop names, distances, and visa details.

---

## 🌟 `stops.txt` (Sample Data)

The `stops.txt` file contains stop data in a structured format:

```yaml
Stop1, 20, Visa Required
Stop2, 35, No Visa
Stop3, 50, No Visa
Stop4, 80, Visa Required
Stop5, 120, No Visa
```

Each line represents:
- **Stop Name**, **Distance** (in KM), **Visa Requirement**

---

##  Usage Instructions

1. **Launch the app** on an Android device or emulator.
2. **Enter your journey details** (start and destination).
3. **Select a route** from the available options.
4. **Track your journey progress**:
   - Tap **"Next Stop Reached"** to mark stops.
   - Tap **"Switch to Miles/KM"** to toggle units.
   - Use **"Reset Journey"** to restart the trip.

---

##  Customization & Further Development

🔹 **Enhance UI** – Improve styling, animations, or **Material 3** theming.  
🔹 **Data Persistence** – Save progress using **Room DB** or **SharedPreferences**.  
🔹 **Real-time Navigation** – Integrate **Google Maps API** for live tracking. 
🔹 **Testing** – Add **unit tests & UI tests** for reliability.  

--

## 📞 Connect Us - (github) 

[GitHub](#) 
or email us at - ayush22125@iiitd.ac.in

---





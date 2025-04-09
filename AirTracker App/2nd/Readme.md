PART - 2

Flight Track App ✈️


A real-time flight tracking application that monitors routes and calculates average flight durations, built with modern Android technologies.

Features : 

- Live Flight Tracking: Monitor active flights via AviationStack API
- Route Analytics: Calculate average durations for popular routes (DEL↔BOM, BOM↔BLR)
- Background Sync: Daily automatic data collection via WorkManager
- Modern UI: Jetpack Compose with Material 3 design
- Offline Support: Room database persistence

Tech Stack 🛠️

| Component               | Technology                          |
|-------------------------|-------------------------------------|
| Language                | Kotlin                              |
| UI Framework            | Jetpack Compose                     |
| Database                | Room                                |
| Networking              | Retrofit + Gson                     |
| Background Tasks        | WorkManager                         |
| Dependency Injection    | Manual DI                           |
| Architecture            | MVVM                                |

Setup Guide ⚙️

Prerequisites
- Android Studio Giraffe+
- [AviationStack API Key](https://aviationstack.com/) (Free tier available)

Installation
1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/airtrack.git


Add API key:
Create local.properties in root directory
Add:
properties
Copy
aviationstack.api.key=YOUR_API_KEY
Build and run!

Configuration 🔧
Environment Variables
Variable
Description
aviationstack.api.key
Required for flight data API access

Build Variants
debug: Development build with debug logging
release: Production build with minification
Architecture Overview 🏗️:
Copy
com.example.airtrack
├── data/            # Database and models
├── network/         # API clients
├── repository/      # Data layer
├── ui/              # Composable components
├── viewmodels/      # Business logic
└── workers/         # Background tasks
Contributing 🤝
Pull requests welcome! For major changes, please open an issue first.


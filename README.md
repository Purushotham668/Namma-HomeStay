# ನಮ್ಮ HomeStay — Modern Rural Hospitality App

A comprehensive, vibrant, and dynamic village-tourism hospitality application designed for rural homestead owners (Providers) and Travelers. Built entirely with **Kotlin, Jetpack Compose, and Firebase**, Namma-HomeStay bridges the gap between authentic rural experiences and modern digital convenience.

---

## 🌟 Key Features

### 🧑‍🌾 Host / Provider Side
A complete management suite to digitize rural hospitality effortlessly:
- **Dashboard & Analytics**: Real-time overview of current occupancy, upcoming bookings, and food analytics.
- **Booking Management**: View reservations, update statuses, and check-in guests.
- **Smart QR Check-in**: Integrated **CameraX & ML Kit** to scan traveler QR codes for instant, paperless check-ins.
- **Provider Food Terminal**: Scan digital food tickets from travelers and track meal fulfillment.
- **Home Profile & Verification**: Manage property details, photos, and compliance checklists.
- **Dynamic Pricing & Menu**: Update per-night pricing, availability, and daily meals in seconds.
- **Inquiry Management**: View traveler messages, track inquiries, and initiate direct calls.
- **Local Guide Curation**: Add nearby attractions (waterfalls, viewpoints) to attract more guests.

### 🎒 Traveler Side
A highly premium, glassmorphic, and animated booking experience:
- **Explore & Maps Integration**: Discover homestays near you using Google Maps API with real-time distance calculation.
- **Seamless Booking Flow**: Select dates, calculate pricing, and proceed through a simulated checkout and payment gateway.
- **QR Code Boarding Passes**: Every booking generates a unique QR code for seamless property check-in.
- **Modern Review System**: A dynamic, real-time rating engine with interactive emoji feedback (😞 to 😍) and auto-updating Firestore streams.
- **Meal Booking System**: Order specific meals (Breakfast, Lunch, Dinner) in advance and generate Digital Food Tickets.
- **My Bookings Dashboard**: Track Upcoming, Past, and Cancelled stays. Edit reviews for past stays.
- **Direct Inquiries**: Send pre-filled queries directly to property owners.

---

## 🛠️ Tech Stack & Architecture

Namma-HomeStay employs modern Android development best practices to ensure scalability, performance, and a stunning UI.

| Category | Technology |
|---|---|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose + Material 3 |
| **Architecture** | MVVM + Repository Pattern |
| **Concurrency** | Kotlin Coroutines & StateFlow/SharedFlow |
| **Dependency Injection**| Dagger Hilt |
| **Backend & Auth** | Firebase (Auth, Firestore, Storage) |
| **Image Loading** | Coil |
| **Navigation** | Jetpack Navigation Compose |
| **Maps & Location** | Google Maps Compose + Play Services Location |
| **Camera & Scanning** | CameraX + Google ML Kit Barcode Scanning + ZXing |

---

## 🎨 UI & Design Philosophy

The application strictly adheres to modern design aesthetics to provide a "WOW" factor:
- **Glassmorphism**: Semi-transparent overlays with blur effects for a premium feel.
- **Dynamic Micro-Animations**: Interactive buttons, spring-animated rating stars, and fluid screen transitions.
- **Curated Color Palette**:
  - `Earth Brown (#6D4C41)`: Grounded primary branding.
  - `Leaf Green (#558B2F)`: Accents, availability, and success states.
  - `Warm Orange (#FF8F00)`: Interactive Call-to-Actions.
  - `Midnight Blue (#1A237E)`: Professional, high-contrast text and headers.

---

## 🚀 Getting Started

### 1. Firebase Setup (REQUIRED)

1. Go to [Firebase Console](https://console.firebase.google.com).
2. Create a new project named **NammaHomeStay**.
3. Add an Android app with the package name: `com.nammahomestay.app`.
4. Download `google-services.json` and place it in the `app/` directory.
5. Enable the following Firebase services:
   - **Authentication** (Email/Password).
   - **Firestore Database** (Start in Test Mode).
   - **Storage** (Start in Test Mode - required for uploading property/review photos).

### 2. Firestore Security Rules (Recommended for Production)

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null; // Basic security for testing
    }
  }
}
```

### 3. Build & Run

**Prerequisites:**
- Android Studio Hedgehog (or newer)
- JDK 17
- Android SDK 35

**Steps:**
1. Clone the repository: `git clone https://github.com/Purushotham668/Namma-HomeStay.git`
2. Open the project in Android Studio and wait for Gradle sync.
3. Connect a physical Android device or start an emulator (API 24+).
4. Run the app.

---

## 📁 Project Structure

```
app/src/main/java/com/nammahomestay/app/
├── data/
│   ├── model/          ← Data schemas (HomeStay, Booking, Review, DailyMenu, etc.)
│   └── repository/     ← Firebase data access (Auth, HomeStay, Booking, Food, etc.)
├── di/                 ← Hilt Modules (AppModule, FirebaseModule)
├── ui/
│   ├── auth/           ← Onboarding, Login, Register, Role Selection
│   ├── components/     ← Reusable UI (NammaCard, Maps, QR Generators, PhotoPickers)
│   ├── navigation/     ← NavGraph & Routing logic
│   ├── provider/       ← Host features (Dashboard, Booking Terminal, Verification, Menu)
│   ├── traveler/       ← Guest features (Explore, Booking, Reviews, Food Ordering)
│   └── theme/          ← Color tokens, Typography, MaterialTheme definitions
└── util/               ← Extensions, Resource wrappers (Success/Error/Loading states)
```

---

## 🤝 Contribution & Support

For questions, feature requests, or technical support, please contact the development team. 

*Built with ❤️ for the beautiful homestays of rural Karnataka.*

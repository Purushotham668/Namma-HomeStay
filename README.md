# ನಮ್ಮ HomeStay — Android App

A warm, village-tourism hospitality app for rural homestead owners (Providers) and Travelers built with **Kotlin + Jetpack Compose + Firebase**.

---

## 🏡 Features

### Host / Provider Side
| Screen | Feature |
|---|---|
| Home Profile | Manage name, address, description, photos, verification checklist |
| Daily Menu | Update breakfast, lunch, dinner & special dish in under 1 minute |
| Pricing | Set per-day/per-person rates and toggle availability |
| Inquiry Box | View traveler messages, tap to call directly |
| Local Guide | Add/remove nearby waterfalls, viewpoints, trekking spots |
| Settings | Edit profile, change password, logout |

### Traveler Side
| Screen | Feature |
|---|---|
| Explore | Browse & search available homestays |
| HomeStay Detail | Full profile, menu, verification badges, local spots |
| Send Inquiry | Contact host with auto-filled user details |
| My Inquiries | Track sent messages and host response status |

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI Framework | Jetpack Compose + Material 3 |
| Architecture | MVVM + Repository pattern |
| DI | Hilt |
| Auth | Firebase Authentication |
| Database | Firebase Firestore |
| Storage | Firebase Storage |
| Image Loading | Coil |
| Navigation | Jetpack Navigation Compose |

---

## 🎨 UI Theme

| Color Name | Hex | Usage |
|---|---|---|
| Earth Brown | `#6D4C41` | Primary, top bars |
| Leaf Green | `#558B2F` | Secondary, availability |
| Warm Orange | `#FF8F00` | CTA buttons, accents |
| Soft Cream | `#FFF8E7` | Background, card surface |

---

## 🚀 Getting Started

### 1. Firebase Setup (REQUIRED)

1. Go to [Firebase Console](https://console.firebase.google.com)
2. Create a new project named **NammaHomeStay**
3. Add an Android app with package name: `com.nammahomestay.app`
4. Download `google-services.json` and place it at: `app/google-services.json`
5. Enable the following Firebase services:
   - **Authentication** → Email/Password
   - **Firestore Database** → Start in test mode
   - **Storage** → Start in test mode (requires Blaze plan for photo uploads)

### 2. Firestore Security Rules (Recommended)

```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    match /homestays/{stayId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.uid == stayId;
    }
    match /inquiries/{inquiryId} {
      allow create: if request.auth != null;
      allow read, update: if request.auth != null;
    }
  }
}
```

### 3. Build & Run

**Prerequisites:**
- Android Studio Hedgehog or newer
- JDK 17
- Android SDK 35

**Steps:**
```bash
# Open project in Android Studio
# Wait for Gradle sync to complete
# Connect a device or start an emulator (API 24+)
# Run the app
```

Or via command line:
```bash
./gradlew assembleDebug
```

---

## 📁 Project Structure

```
app/src/main/java/com/nammahomestay/app/
├── data/
│   ├── model/          ← Data classes (User, HomeStay, DailyMenu, Pricing, Inquiry, LocalSpot)
│   └── repository/     ← Firebase data access layer
├── di/                 ← Hilt dependency injection modules
├── ui/
│   ├── auth/           ← Splash, Login, Register
│   ├── components/     ← Shared composables (NammaCard, NammaButton, etc.)
│   ├── navigation/     ← NavGraph + Screen sealed class
│   ├── provider/       ← All 6 provider screens + ViewModels
│   ├── theme/          ← Color, Typography, Theme
│   └── traveler/       ← All traveler screens + ViewModels
└── util/               ← Resource wrapper, Extensions
```

---

## 📋 Firestore Data Schema

```
users/{uid}
  name, email, phone, role

homestays/{providerId}
  name, ownerName, contact, address, description
  photoUrls[], checklist{}, menu{}, pricing{}, localSpots[]

inquiries/{inquiryId}
  homestayId, travelerName, travelerPhone, message, timestamp, responded
```

---

## ✅ Success Criteria

- [x] Daily menu updatable in < 1 minute (single-screen form)
- [x] Verification checklist integrated in Home Profile
- [x] Warm & welcoming Earth Brown / Leaf Green / Orange UI
- [x] Large touch-friendly buttons (54dp height)
- [x] Role-based navigation (Provider ↔ Traveler)
- [x] One-tap call button for inquiry responses

---

## 📞 Support

For questions, please contact the development team.

*Made with ❤️ for rural Karnataka homestead owners*

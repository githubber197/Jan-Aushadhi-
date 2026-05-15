# Jan-Aushadhi Finder

Jan-Aushadhi Finder is a comprehensive full-stack solution (Mobile + Web + Backend API) designed to help Indian citizens access the Pradhan Mantri Bhartiya Janaushadhi Pariyojana (PMBJP) generic medicine network. It bridges the information and trust gaps by providing an easy-to-use search engine for generic medicines, a real-time store locator, a savings calculator, and a refill management system.

## 🚀 Key Features

*   **Intelligent Medicine Search:** Uses fuzzy string matching (Levenshtein distance) for robust typo-tolerant search and falls back to Google's **Gemini AI** to suggest relevant generic medicines when exact matches are not found.
*   **Dynamic Location-Aware Store Locator:** Connects to a live API to find nearby Jan-Aushadhi kendras (stores) using the user's real-time GPS coordinates. Features a fallback mechanism to a standard list if the location service times out.
*   **Savings Calculator:** Helps users estimate how much money they can save by switching from branded drugs to affordable PMBJP generic alternatives.
*   **Refill Management:** Tracks medication schedules and notifies users when a refill is due, utilizing local persistence and background tasks.
*   **Cross-Platform Accessibility:** Available as a fully-featured native Android app and a lightweight responsive Web App.

---

## 🛠️ Technology Stack

The project is divided into three primary components: a Native Android Application, a Web Frontend, and a Node.js REST API.

### 📱 Android Application (`/android`)
A modern, native Android application built with Kotlin, following MVVM architecture and Material Design 3 principles.

*   **Language & Concurrency:** Kotlin, Coroutines
*   **Architecture & UI:**
    *   Fragments & Navigation Component
    *   ViewBinding
    *   Material Design 3 (Cards, ViewPager2, RecyclerView)
*   **Local Storage & Background Tasks:**
    *   **Room Database:** For caching medicine and store data locally, featuring auto-seeding.
    *   **DataStore Preferences:** For lightweight settings and user preferences.
    *   **WorkManager:** For background scheduling and refill notifications.
*   **Networking:** Retrofit, OkHttp (with Logging Interceptor), Gson
*   **Maps & Location:** 
    *   **OSMDroid:** For free, OpenStreetMap-based map rendering (replacing Google Maps to eliminate API costs).
    *   **Play Services Location:** For fetching dynamic user coordinates.
*   **AI & Search Processing:**
    *   **Gemini AI SDK:** (`generativeai`) Powers the intelligent search fallback.
    *   **Apache Commons Text:** Used for Levenshtein fuzzy search algorithms.
*   **Logging:** Timber
*   **Testing:** JUnit 4, Mockito, Robolectric, Coroutines Test, Espresso, Room Testing.

### 🌐 Web Frontend (Root Directory)
A lightweight web application deployed on GitHub Pages for universal access.

*   **Core:** Vanilla HTML5, CSS3, and JavaScript (`app.js`, `index.html`, `style.css`).
*   **Features:** Interfaces with the Node.js backend to provide search and store location services on desktop and mobile web browsers.

### ⚙️ Backend API (`/backend`)
A lightweight, fast REST API pre-seeded with a comprehensive database of 150+ generic Indian medicines. Deployed to Render.com.

*   **Runtime & Framework:** Node.js, Express.js
*   **Database:** SQLite (managed via `sql.js`) for portable and efficient data storage.
*   **Middleware:** CORS, dotenv for environment variable management.
*   **Tools:** Nodemon (for development), seed scripts for database initialization.

---

## 📁 Project Structure

```text
project_demo/
├── android/            # Native Android application source code
│   └── app/            # Main Android app module (MVVM, Room, OSMDroid, Gemini)
├── backend/            # Node.js Express REST API
│   ├── src/            # Controllers, Routes, and DB seed scripts
│   ├── data/           # SQLite database file (database.sqlite)
│   └── package.json    # Backend dependencies
├── index.html          # Web Frontend HTML
├── style.css           # Web Frontend Stylesheet
└── app.js              # Web Frontend Logic
```

## 🌐 Deployment
*   **Backend:** The Express/SQLite backend is configured for cloud deployment and is currently hosted on **Render.com**.
*   **Web App:** Hosted on **GitHub Pages**.
*   **Android App:** Fully compiling APK ready for emulator testing and physical device deployment via Android Studio.

## ⚙️ Setup & Installation

### Running the Backend Locally
1. Navigate to the backend directory: `cd backend`
2. Install dependencies: `npm install`
3. (Optional) Seed the database: `npm run seed`
4. Start the development server: `npm run dev`

### Running the Android App
1. Open the `/android` folder in **Android Studio**.
2. Sync Project with Gradle Files.
3. Ensure you have an active emulator or physical device connected.
4. Click **Run** (`Shift + F10`).
*Note: Make sure to provide Location Permissions in the app to test the dynamic store locator.*

### Running the Web App Locally
Simply open `index.html` in any modern web browser or run a local static server in the root directory (e.g., `npx serve .`).

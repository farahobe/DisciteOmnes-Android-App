# 🎓 Discite Omnes (Semester Project – In Development)

**Discite Omnes** (Latin for "Learn, Everyone") is a native Android app (work in progress) designed as a platform for collaborative learning. The goal is to enable students to create study groups, manage tasks, and track their learning progress together.

This project is currently under development as part of a semester assignment and demonstrates the practical application of Android development with Java and a client-server architecture.

---

## ⭐ Current Status & Implemented Features

The app is at a functional prototype stage. The following core features are already implemented:

- **User Authentication:** Secure registration and login. Sessions are managed via a locally stored token.
- **Group Management:** Users can create new study groups, join existing groups via a search function, and view an overview of their groups.
- **Task Overview:** A basic view for tasks within a group is available.
- **Basic Dashboard:** Shows a list of groups the user belongs to.
- **REST API Communication:** Backend integration using Retrofit is implemented and working.

---

## 🗺️ Roadmap & Planned Features

The project is not yet complete. The following features are planned to reach full functionality:

**Complete Task Management:**
- Create, edit, and delete tasks.
- Assign tasks to specific group members.
- Set due dates for tasks.

**Interactive Dashboard:**
- Display of upcoming tasks and deadlines.
- Visualization of personal learning progress.

**Communication:**
- A simple chat within study groups.

**Notifications:**
- Push notifications for new tasks or messages.

**Profile Management:**
- Users can edit their own profile.

---

## 🛠️ Architecture & Technologies

- **Language:** Java
- **Platform:** Android SDK
- **Architecture:** Client-Server

**Core Libraries:**
- **AndroidX AppCompat & Material Components:** For UI design.
- **Retrofit 2 & Gson:** For type-safe REST API communication.

**Data & Session Management:**
- **SharedPreferences:** For locally storing the authentication token.

---

## 🚀 Setup & Installation

To run the current version of the project:

### 1. Set up the backend

- The app expects a backend at `http://192.168.0.148:8080/` (see `RetrofitClient.java`).
- A JSON server with the `db.json` file can be used to simulate the API.

### 2. Open the Android Project

- Open the project in Android Studio.
- Sync Gradle dependencies.

### 3. Run the App

- Start the app on an emulator or a physical Android device.

---

## ℹ️ Notes

This project is a work in progress for a university course and is not yet production-ready. Contributions and feedback are welcome!
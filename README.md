# ⏰ Android WorkManager Notification Demo

A simple Android demo project showcasing how to use **WorkManager** to schedule **one-time** and **periodic background notifications** that work reliably even when the app is in the background or killed.

This project is written in **Java** and follows modern Android background execution rules (Android 13+ compatible).

---

## 📌 Features

* ✅ One-time notification with delay (15 seconds)
* 🔁 Periodic notification every 15 minutes (minimum allowed by WorkManager)
* 🔔 Notification Channel support (Android 8+)
* 🛡 Runtime notification permission handling (Android 13+)
* ⚙️ Works in background, foreground, or app killed state
* 🚫 No foreground service required

---

## 🧠 How It Works

### 1️⃣ One-Time Work

Uses `OneTimeWorkRequest` with an initial delay to trigger a notification after 15 seconds.

```java
OneTimeWorkRequest work =
        new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(15, TimeUnit.SECONDS)
                .build();

WorkManager.getInstance(this).enqueue(work);
```

---

### 2️⃣ Periodic Work

Uses `PeriodicWorkRequest` to trigger a notification every **15 minutes** (WorkManager minimum interval).

```java
PeriodicWorkRequest work =
        new PeriodicWorkRequest.Builder(NotificationWorker.class, 15, TimeUnit.MINUTES)
                .build();

WorkManager.getInstance(this).enqueue(work);
```

---

### 3️⃣ Background Execution

All background logic runs inside a `Worker` class:

```java
public class NotificationWorker extends Worker {

    @NonNull
    @Override
    public Result doWork() {
        showNotification();
        return Result.success();
    }
}
```

This guarantees execution even if:

* App is in background
* App is swiped away
* Device restarts (if constraints are added later)

---

## 🔐 Permissions

### Android 13+ (API 33)

Requires runtime notification permission:

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

The app automatically opens **notification settings** if permission is not granted.

---

## 🧩 Tech Stack

* **Language:** Java
* **Background Tasks:** WorkManager
* **UI:** XML + AppCompat
* **Min SDK:** 21+
* **Target SDK:** 34+
* **Architecture:** Worker-based background execution

---

## 📱 Compatibility Notes

* Periodic workers **cannot run less than 15 minutes**
* Notifications will not appear if the user disables them manually
* No battery optimization whitelist required
* No exact alarm permission needed

---

## 🚀 Getting Started

1. Clone the repository

   ```bash
   git clone https://github.com/your-username/android-worker-demo.git
   ```
2. Open in **Android Studio**
3. Run on a real device or emulator (Android 8+ recommended)
4. Grant notification permission
5. Observe scheduled notifications 🎉

---

## 📂 Project Structure

```
app/
 ├── MainActivity.java
 ├── NotificationWorker.java
 ├── res/
 │   ├── layout/
 │   └── drawable/
 └── AndroidManifest.xml
```

---

## 📜 License

MIT License
You are free to use, modify, and distribute this project.

---

## 🤝 Contributing

Pull requests are welcome.
Feel free to fork and improve the demo.

---

## ⭐ If you find this useful

Give the repo a star — it helps a lot!

---
=

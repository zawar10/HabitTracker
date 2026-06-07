# 🔥 Habit Tracker

A Java Swing desktop application for tracking daily habits, monitoring streaks, and building consistency. Built as a semester project for SCD Lab.

---

## 📋 Project Description

Habit Tracker is a GUI-based Java application that allows users to:

- Add and manage daily habits
- Mark habits as done each day
- Track streaks for each habit
- View today's completion progress
- Delete habits or reset all for a new day

The project demonstrates five core software engineering concepts:

| Concept | Implementation |
|---|---|
| **Event Handling** | Button clicks, Enter key, table row selection |
| **Exception Handling** | Input validation with try/catch and error dialogs |
| **Code Refactoring** | Modular methods, no code duplication, clean naming |
| **Unit Testing** | 15 JUnit test cases covering all business logic |
| **Git & GitHub** | Version controlled with meaningful commit history |

---

## 🛠️ Tech Stack

- **Language:** Java
- **GUI:** Java Swing
- **Testing:** JUnit 4
- **IDE:** Eclipse
- **Version Control:** Git & GitHub

---

## ⚙️ Setup Instructions

### Prerequisites
- Java JDK 8 or higher installed
- Eclipse IDE installed

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/habit-tracker.git
   ```

2. **Open in Eclipse**
   - Open Eclipse
   - Go to `File → Open Projects from File System`
   - Select the cloned folder

3. **Add JUnit 4 library**
   - Right-click the project → `Properties`
   - Go to `Java Build Path → Libraries`
   - Click `Add Library → JUnit → JUnit 4`
   - Click `Finish → Apply and Close`

4. **Run the application**
   - Right-click `HabitTrackerApp.java`
   - Select `Run As → Java Application`

5. **Run unit tests**
   - Right-click `HabitTrackerTest.java`
   - Select `Run As → JUnit Test`
   - All 15 tests should pass ✅

---

## ✅ Features

- **Add Habit** — Enter a name and press Add or hit Enter
- **Mark Done** — Select a habit and mark it complete for today
- **Delete** — Remove a habit with confirmation dialog
- **Reset All** — Start fresh for a new day
- **Streak Counter** — Tracks how many days in a row each habit is done
- **Progress Display** — Shows X/Y habits done with percentage
- **Input Validation** — Empty names, duplicates, and long names are rejected

---

## 🧪 Unit Tests

Tests are written using JUnit 4 and cover:

- `calculateProgress()` — progress percentage calculation
- `incrementStreak()` — streak counter logic
- `validateHabitName()` — input validation rules
- `isDuplicate()` — duplicate habit detection

---

## 👤 Author

- **Name:** Zawar Ahmed
- **Roll No:** L1F23BSSE0407
- **Course:** Software Construction and Development (SCD) Lab
- **Semester:** Spring 2026

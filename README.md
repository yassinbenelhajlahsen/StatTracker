# 🏀 NBA Stat Tracker CLI App

A Java-based command-line application that connects to a SQL database to manage and query NBA data including players, teams, games, stats, viewers, and followers. The app supports CRUD operations, custom SQL queries, user login/account creation, and admin-only data manipulation tools.

---

## 📦 Features

### 📋 Main Menu
- `1. List All Entities`
   View all players, coaches, teams, games, viewers, and player stats.


- `2. Star Performers`  
  Display players averaging **≥20 PPG, 5 RPG, and 5 APG**.


- `3. Games by Date`  
  Query and display all games on a specific date.


- `4. Top 5 Teams`  
  List top 5 teams based on win/loss records.


- `5. Triple-Doubles`  
  View players with triple-doubles and how many they've had.


- `6. Most Followed Players`  
  Display the top 5 most followed players.


- `7. Viewer Login / Create Account`  
  Users can:
    - Follow/unfollow players
    - View who they follow
    - See player follower lists


- `8. Admin Panel (Password Protected)`  
  Admins can:
    - Add/Remove players
    - Add/Remove games
    - Add/Remove player stats


- `0. Exit`

---

## 🧑‍💻 Tech Stack

- **Language**: Java
- **Database**: MySQL
- **Libraries**: JDBC (Java Database Connectivity)


---

## 🚀 Getting Started

### Prerequisites

- Java 8 or higher
- MySQL database

### Setup

1. Clone this repository to your local machine:
    ```bash
    git clone https://github.com/yassinbenelhajlahsen/nba-stat-tracker.git
    ```

2. Install the necessary MySQL database and set up the following tables:
    - `players`
    - `teams`
    - `games`
    - `stats`
    - `viewers`
    - `followers`


3. Configure the `DatabaseConnection` class with your database credentials (username, password, URL).


4. Run the `Main.java` file to start the CLI application.

---

## 🔧 Usage

1. When you launch the application, you'll be greeted with the main menu where you can choose various actions.


2. For the **Viewer Login/Create Account** feature, you will be prompted to enter your username and password. If it's your first time, an account will be created.


3. In the **Admin Panel**, you can perform administrative tasks like adding/removing players, games, or stats. Admin access is password-protected.
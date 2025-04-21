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

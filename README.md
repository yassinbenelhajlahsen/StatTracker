# 🏀 NBA Stat Tracker CLI

A command-line Java application that connects to a **MariaDB** database to track NBA players, coaches, teams, games, viewers, and player stats. Users can view game info, player averages, team wins, and more — all from the terminal.

---

## 📦 Features

- 📋 List all entities: Players, Coaches, Teams, Games, Viewers, Player Stats  
- 📊 Show players who average at least **20 points, 5 rebounds, and 5 assists**
- 📅 Lookup games by date
- 🏆 View team win rankings using dynamic SQL
- 🔒 Viewer login & follow system
- ⚙️ Admin panel to insert/remove data without SQL access

---

## 💻 Tech Stack

- **Java 17+**
- **MariaDB / MySQL**
- **JDBC**
- **OOP Design (CRUD Classes, Models, Relationships)**

---

## 📁 Project Structure

```
NBAStatTrackerCLI/
├── src/
│   ├── Main.java                  # Main CLI menu + options
│   ├── DBConnection.java          # Database connection handler
│   ├── CRUD/
│   │   ├── PlayerCRUD.java
│   │   ├── CoachCRUD.java
│   │   ├── TeamCRUD.java
│   │   ├── GameCRUD.java
│   │   ├── ViewerCRUD.java
│   │   └── PlayerStatsCRUD.java
│   ├── Models/
│   │   ├── Player.java
│   │   ├── Coach.java
│   │   ├── Team.java
│   │   ├── Game.java
│   │   ├── Viewer.java
│   │   └── PlayerStats.java
│   └── README.md
```

---

## 🧠 Sample SQL Queries

### 🔥 Players averaging 20 PTS, 5 REB, 5 AST:
```sql
SELECT PlayerID
FROM PlayerStats
GROUP BY PlayerID
HAVING AVG(points) >= 20 AND AVG(rebounds) >= 5 AND AVG(assists) >= 5;
```

### 🏆 Team win rankings:
```sql
SELECT t.teamID, t.teamName, COUNT(*) AS wins
FROM (
    SELECT team1ID AS teamID FROM Game WHERE team1score > team2score
    UNION ALL
    SELECT team2ID AS teamID FROM Game WHERE team2score > team1score
) AS winners
JOIN Team t ON winners.teamID = t.teamID
GROUP BY t.teamID, t.teamName
ORDER BY wins DESC;
```

---


## 🌱 Future Enhancements

- [ ] Secure viewer login system
- [ ] Filters by team, player, position, or season
- [ ] JavaFX or web-based frontend
- [ ] RESTful API backend support

---

## 🙌 Contributors

- **Yassin Benelhajlahsen** – Lead Developer  


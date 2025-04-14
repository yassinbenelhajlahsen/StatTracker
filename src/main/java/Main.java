import java.util.*;
import java.sql.Date;
import java.time.LocalDate;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LocalDate today = LocalDate.now();
    public static void main(String[] args) {
        
        int choice;

        do {
            printMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    option1();
                    break;
                case 2:
                    option2();
                    break;
                case 3:
                    option3();
                    break;
                case 4:
                    option4();
                    break;
                case 5:
                    option5();
                    break;
                case 6:
                    option6();
                    break;

            }
        } while (choice != 0);
        scanner.close();
    }

    static void option1() {
        
        int choice;
        do {
            System.out.println();
            System.out.println("1. List all players");
            System.out.println("2. List all coaches");
            System.out.println("3. List all teams");
            System.out.println("4. List all games");
            System.out.println("5. List all viewers");
            System.out.println("6. List all player stats");
            System.out.println("0. Exit");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    printAllPlayers();
                    break;
                case 2:
                    printAllCoaches();
                    break;
                case 3:
                    printAllTeams();
                    break;
                case 4:
                    printAllGames();
                    break;
                case 5:
                    printAllViewers();
                    break;
                case 6:
                    printAllPlayerStats();
                    break;
            }
        } while (choice != 0);
    }

    static void option2() {
        PlayerCRUD playerCRUD = new PlayerCRUD();
        PlayerStatsCRUD playerStatsCRUD = new PlayerStatsCRUD();
        String sql = "SELECT playerID\n" +
                "FROM PlayerStats\n" +
                "GROUP BY playerID\n" +
                "HAVING AVG(points) >= 20 AND AVG(rebounds) >= 5 AND AVG(assists) >= 5;";
        List<Player> players = playerStatsCRUD.get(sql);
        System.out.println();
        System.out.println("Player who average at least 20 points, 5 rebounds, and 5 assists per game.");
        for (Player p : players) {
            String name = getNameFromPlayer(p);
            System.out.println(name);
        }
    }

    static void option3() {
        
        System.out.println();
        System.out.println("Enter a date. (YYYY-MM-DD)");
        scanner.nextLine();
        String date = scanner.nextLine();
        String sql = "SELECT * FROM Game WHERE Date = '" + date + "'";

        GameCRUD gameOperations = new GameCRUD();
        List<Game> games = gameOperations.get(sql);
        if (games.isEmpty()) System.out.println("There were no games on that date, or the format was incorrect");
        else {
            System.out.printf("%-12s %-12s %-12s %-10s %-20s %-25s%n",
                    "Date", "Home Score", "Away Score", "GameID", "Home", "Away");
            for (Game game : games) {
                String home = getNameFromTeam(game.getTeam1ID());
                String away = getNameFromTeam(game.getTeam2ID());
                System.out.printf("%-12s %-12s %-12s %-10s %-20s %-25s%n", game.getDate(), game.getTeam1score(), game.getTeam2score(),
                        game.getGameID(), home, away);
            }
        }
    }

    static void option4() {
        String sql = "SELECT \n" +
                "    t.teamName,\n" +
                "    COALESCE(w.wins, 0) AS wins,\n" +
                "    COALESCE(l.losses, 0) AS losses\n" +
                "FROM team t\n" +
                "LEFT JOIN (\n" +
                "    SELECT teamID, COUNT(*) AS wins\n" +
                "    FROM (\n" +
                "        SELECT team1ID AS teamID FROM game WHERE team1score > team2score\n" +
                "        UNION ALL\n" +
                "        SELECT team2ID AS teamID FROM game WHERE team2score > team1score\n" +
                "    ) AS win_data\n" +
                "    GROUP BY teamID\n" +
                ") w ON t.teamID = w.teamID\n" +
                "LEFT JOIN (\n" +
                "    SELECT teamID, COUNT(*) AS losses\n" +
                "    FROM (\n" +
                "        SELECT team1ID AS teamID FROM game WHERE team1score < team2score\n" +
                "        UNION ALL\n" +
                "        SELECT team2ID AS teamID FROM game WHERE team2score < team1score\n" +
                "    ) AS loss_data\n" +
                "    GROUP BY teamID\n" +
                ") l ON t.teamID = l.teamID\n" +
                "ORDER BY wins DESC\n" +
                "Limit 5;\n";

        TeamCRUD teamOperations = new TeamCRUD();
        System.out.printf("%-25s %-10s%n", "Team", "Record");
        teamOperations.getRecord(sql);

    }

    static void option5(){
        int choice;
        do {
            System.out.println("1. Login");
            System.out.println("2. Create account");
            System.out.println("0. Exit");
            choice = scanner.nextInt();
            if (choice == 1) login();
            if (choice == 2) createAccount();
        } while (choice != 0);
    }

    static void login() {
        ViewerCRUD viewerOps = new ViewerCRUD();
        System.out.print("Enter username: ");
        String username = scanner.next();

        Viewer viewer = new Viewer(username);
        List<Viewer> viewerList = viewerOps.get();

        boolean found = false;
        for (Viewer v : viewerList) {
            if (v.getUsername().equals(username)) {
                viewer = v;
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Oops! Looks like you don't have an account, or there was a typo in your response.");
        } else {
            followMenu(viewer);
        }
    }
    static void createAccount(){
        ViewerCRUD viewerOps = new ViewerCRUD();
        List<Viewer> viewerList = viewerOps.get();

        boolean isTaken;
        String username;
        do {
            System.out.print("Enter desired username: ");
            username = scanner.next();
            isTaken=false;
            for (Viewer v : viewerList) {
                if (v.getUsername().equals(username)) {
                    System.out.println("Username already taken. Try another.");
                    isTaken = true;
                }
            }
        } while (isTaken);
        insertViewer(new Viewer(username));
        System.out.println("Account created successfully.");
    }

    static void followMenu(Viewer viewer){
        int choice;
        FollowCRUD followOps = new FollowCRUD();
        PlayerCRUD playerOps = new PlayerCRUD();
        do {
            System.out.println();
            System.out.println("Welcome: " + viewer.getUsername() + ". Select an option from below.");
            System.out.println("1. Follow player");
            System.out.println("2. Unfollow player");
            System.out.println("3. View following list");
            System.out.println("4. View player's followers");
            System.out.println("0. Logout");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 3) printFollowing(viewer);
            else if (choice == 4) {
                System.out.print("Enter player name: ");
                String playerName = scanner.nextLine();
                printFollowers(new Player(playerOps.getPlayerIDByName(playerName)));

            } else if (choice == 1 || choice == 2) {
                System.out.print("Enter player name: ");
                String playerName = scanner.nextLine();
                Player player = new Player(playerOps.getPlayerIDByName(playerName));

                if (choice == 1) {
                    Follow follow = new Follow(viewer, player, Date.valueOf(today));
                    insertFollow(follow);
                } else {
                    Follow follow = new Follow(viewer, player);
                    followOps.unfollow(follow);
                }
            }

        } while (choice != 0);
    }
    static void printFollowing(Viewer viewer){
        FollowCRUD followOps = new FollowCRUD();
        List<Player> followingList = followOps.get(viewer);
        System.out.println("Username: "+ viewer.getUsername());
        if (followingList.isEmpty()) System.out.println("Not following anyone yet.");
        else {
            System.out.println("Players");
            for (Player p : followingList) {
                System.out.println(getNameFromPlayer(p));
            }
        }
    }

    static void printFollowers(Player player){
        FollowCRUD followOps = new FollowCRUD();
        List<Viewer> followerList = followOps.get(player);

        System.out.println("Player: "+getNameFromPlayer(player));
        if (followerList.isEmpty()) System.out.println("No followers yet.");
        else {
            System.out.println("Followers");
            for (Viewer v : followerList) {
                System.out.println(v.getUsername());
            }
        }
    }
    static boolean authentication() {
        
        String password = "admin";
        int tries = 0;
        boolean authenticated = false;

        while (tries < 3) {
            System.out.print("Welcome to admin settings. Enter password to continue: ");
            String attempt = scanner.next();
            if (attempt.equals(password)) {
                authenticated = true;
                break;
            } else {
                tries++;
                if (tries < 3) System.out.println("Incorrect. Try again. " + (3 - tries) + " attempt(s) remaining.");
                else System.out.println("Too many wrong attempts. Returning to main menu.");
            }
        }
        return authenticated;
    }

    static void option6() {
        int choice;
        int input;
        if (authentication()) {
            do {
                System.out.println();
                System.out.println("Access granted! Select an option from below.");
                System.out.println("1. Add/Remove player");
                System.out.println("2. Add/Remove game");
                System.out.println("3. Add/Remove playerStat");
                System.out.println("0. Exit");

                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("1. Add player");
                        System.out.println("2. Remove player (NOTE: This will also delete any stats the player has recorded.)");
                        input = scanner.nextInt();
                        if (input == 1) adminInsertPlayer();
                        if (input == 2) adminRemovePlayer();
                        break;
                    case 2:
                        System.out.println("1. Add game");
                        System.out.println("2. Delete game (NOTE: This will also delete any stats in those games that were recorded.)");
                        input = scanner.nextInt();
                        if (input == 1) adminInsertGame();
                        if (input == 2) adminRemoveGame();
                        break;
                    case 3:
                        System.out.println("1. Add stat");
                        System.out.println("2. Delete stat");
                        input = scanner.nextInt();
                        if (input == 1) adminInsertPlayerStat();
                        if (input == 2) adminRemovePlayerStat();
                        break;

                }
            } while (choice != 0);
        }
    }

    static void adminInsertPlayer() {
        
        System.out.print("Enter the player's name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Enter the player's teamID, followed by the playerID. (Ex: 142 18): ");
        Team team = new Team(scanner.next());
        String playerID = scanner.next();
        System.out.println();
        insertPlayer(new Player(playerID, team, name));
    }

    static void adminRemovePlayer() {
        System.out.print("Enter the PlayerID: ");
        
        String playerID = scanner.next();
        System.out.println();
        removePlayer(new Player(playerID));
    }

    static void adminInsertGame() {
        Date date = null;
        
        System.out.println("Enter date (YYYY-MM-DD): ");
        try {
            date = Date.valueOf(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
        }
        System.out.println("Enter the home team's score, away, gameID, homeTeamID, awayTeamID" +
                " (Ex: 101 109 22 1 3): ");
        int homeScore = scanner.nextInt();
        int awayScore = scanner.nextInt();
        String gameID = scanner.next();
        Team homeTeam = new Team(scanner.next());
        Team awayTeam = new Team(scanner.next());
        insertGame(new Game(date, homeScore, awayScore, gameID, homeTeam, awayTeam));
    }

    static void adminRemoveGame(){
        
        System.out.print("Enter gameID: ");
        scanner.nextLine();
        removeGame(new Game(scanner.next()));
    }

    static void adminInsertPlayerStat(){
        
        System.out.print("Enter PlayerID, GameID, StatID, Points, Rebounds, Assists (Ex: 13 1 19 8 12 22): ");
        scanner.nextLine();
        Player p = new Player(scanner.next());
        Game game = new Game(scanner.next());
        String statID = scanner.next();
        int pts = scanner.nextInt();
        int reb = scanner.nextInt();
        int ast = scanner.nextInt();
        insertPlayerStat(new PlayerStats(p ,game, statID, pts, ast, reb));
    }

    static void adminRemovePlayerStat(){
        
        System.out.println("Enter StatID: ");
        scanner.nextLine();
        removePlayerStat(new PlayerStats(scanner.next()));
    }

    static void printMenu() {
        System.out.println("\n--- NBA Stat Tracker ---");
        System.out.println("Select an option from below.");
        System.out.println("1. List all entities.");
        System.out.println("2. List players who average at least 20 points, 5 rebounds, and 5 assists per game.");
        System.out.println("3. Lookup game by date.");
        System.out.println("4. List the top 5 teams and their record (Sorted by total wins).");
        System.out.println("5. Login/Create Account"); //user can create an account or login, to follow players
        System.out.println("6. Admin Settings"); //allows user to enter/remove data as they please without needing access to database
        System.out.println("0. Exit");
    }

    static String getNameFromPlayer(Player player) {
        Map<String, String> map = new HashMap<>();
        PlayerCRUD playerOperations = new PlayerCRUD();
        List<Player> players = playerOperations.get();
        for (Player player1 : players) {
            map.put(player1.getID(), player1.getName());
        }
        return map.get(player.getID());
    }

    static String getNameFromTeam(Team team) {
        Map<String, String> map = new HashMap<>();
        TeamCRUD teamOperations = new TeamCRUD();
        List<Team> teams = teamOperations.get();
        for (Team t : teams) {
            map.put(t.getTeamID(), t.getTeamName());
        }
        return map.get(team.getTeamID());
    }

    static void insertPlayer(Player player) {
        PlayerCRUD playerOperations = new PlayerCRUD();
        playerOperations.addPlayer(player);
    }

    static void removePlayer(Player player) {
        PlayerCRUD playerOperations = new PlayerCRUD();
        playerOperations.deletePlayer(player);
    }


    static void insertGame(Game game) {
        GameCRUD gameOperations = new GameCRUD();
        gameOperations.addGame(game);
    }

    static void removeGame(Game game){
        GameCRUD gameOperations = new GameCRUD();
        gameOperations.deleteGame(game);
    }
    static void insertPlayerStat(PlayerStats ps){
        PlayerStatsCRUD playerStatsOperations = new PlayerStatsCRUD();
        playerStatsOperations.addPlayerStat(ps);
    }

    static void removePlayerStat(PlayerStats ps){
        PlayerStatsCRUD playerStatsOp = new PlayerStatsCRUD();
        playerStatsOp.removePlayerStat(ps);
    }
    static void insertViewer(Viewer viewer) {
        ViewerCRUD viewerOperations = new ViewerCRUD();
        viewerOperations.addViewer(viewer);
    }

    static void insertFollow(Follow follow) {
        FollowCRUD followOperations = new FollowCRUD();
        followOperations.follow(follow);
    }


    static void printAllPlayers() {
        PlayerCRUD playerOperations = new PlayerCRUD();
        List<Player> players = playerOperations.get();
        System.out.printf("%-10s %-18s %-6s%n", "PlayerID", "Name", "Team");
        for (Player player : players) {
            System.out.printf("%-10s %-18s %-6s%n", player.getID(), player.getName(), player.getTeam());
        }
    }

    static void printAllCoaches() {
        CoachCRUD coachOperations = new CoachCRUD();
        List<Coach> coaches = coachOperations.get();
        System.out.printf("%-10s %-18s %-6s%n", "CoachID", "Name", "Team");
        for (Coach coach : coaches) {
            System.out.printf("%-10s %-18s %-6s%n", coach.getID(), coach.getName(), coach.getTeam());

        }
    }

    static void printAllTeams() {
        TeamCRUD teamOperations = new TeamCRUD();
        List<Team> teams = teamOperations.get();
        System.out.printf("%-10s %-25s %-10s %-15s%n", "TeamID", "Name", "CoachID", "Conference");
        for (Team team : teams) {
            System.out.printf("%-10s %-25s %-10s %-15s%n", team.getTeamID(), team.getTeamName(), team.getCoach().getID(), team.getConf());

        }
    }

    static void printAllGames() {
        GameCRUD gameOperations = new GameCRUD();
        List<Game> games = gameOperations.get();
        System.out.printf("%-12s %-12s %-12s %-10s %-10s %-10s%n",
                "Date", "Home Score", "Away Score", "GameID", "HomeID", "AwayID");
        for (Game game : games) {
            System.out.printf("%-12s %-12s %-12s %-10s %-10s %-10s%n", game.getDate(), game.getTeam1score(), game.getTeam2score(),
                    game.getGameID(), game.getTeam1ID(), game.getTeam2ID());

        }
    }

    static void printAllViewers() {
        ViewerCRUD viewerOperations = new ViewerCRUD();
        List<Viewer> viewers = viewerOperations.get();
        System.out.println("Username");
        for (Viewer viewer : viewers) {
            System.out.println(viewer.getUsername());
        }
    }

    static void printAllPlayerStats() {
        PlayerStatsCRUD playerStatsCRUD = new PlayerStatsCRUD();
        List<PlayerStats> playerStats = playerStatsCRUD.get();
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s%n",
                "PlayerID", "GameID", "Points", "Assists", "Rebounds", "StatID");

        for (PlayerStats ps : playerStats) {
            System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s%n", ps.getPlayerID().getID(), ps.getGameID().getGameID(), ps.getPoints()
                    , ps.getAssists(), ps.getRebounds(), ps.getStatID());
        }
    }
}
import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
            printMenu();
            choice = scanner.nextInt();
            switch (choice){
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
            }
        } while (choice != 0);
    }

    static void option1(){
        Scanner scanner = new Scanner(System.in);
        int choice;
        do{
        System.out.println("\nOption 1 selected.");
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
        } while (choice !=0);
    }

    static void option2(){
        PlayerStatsCRUD playerStatsCRUD = new PlayerStatsCRUD();
        String sql = "SELECT playerID\n" +
                "FROM PlayerStats\n" +
                "GROUP BY playerID\n" +
                "HAVING AVG(points) >= 20 AND AVG(rebounds) >= 5 AND AVG(assists) >= 5;";
        List<Player> players = playerStatsCRUD.get(sql);
        System.out.println();
        System.out.println("Player who average at least 20 points, 5 rebounds, and 5 assists per game.");
        for(Player p : players){
            String name = getNameFromPlayer(p);
            System.out.println(name);
        }
    }

    static void option3() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter a date. (YYYY-MM-DD)");
        String date = scanner.nextLine();
        String sql = "SELECT * FROM Game WHERE Date = '" + date + "'";

        GameCRUD gameOperations = new GameCRUD();
        List<Game> games = gameOperations.get(sql);
        if (games.isEmpty()) System.out.println("There were no games on that date, or the format was incorrect");
            else {
            System.out.printf("%-12s %-12s %-12s %-10s %-20s %-25s%n",
                    "Date", "Home Score", "Away Score", "GameID", "Home", "Away");
            for(Game game : games){
                String home = getNameFromTeam(game.getTeam1ID());
                String away = getNameFromTeam(game.getTeam2ID());
                System.out.printf("%-12s %-12s %-12s %-10s %-20s %-25s%n", game.getDate(), game.getTeam1score(), game.getTeam2score(),
                        game.getGameID(), home, away);
            }
            }
        }

    static void option4() {
        String sql = "SELECT\n" +
                "t.teamID,\n" +
                "t.teamName,\n" +
                "COUNT(*) AS wins\n" +
                "FROM (\n" +
                "SELECT team1ID AS teamID FROM game\n" +
                "WHERE team1score > team2score\n" +
                "UNION ALL\n" +
                "SELECT team2ID AS teamID FROM game\n" +
                "WHERE team2score > team1score\n" +
                ") AS winners\n" +
                "JOIN team t ON winners.teamID = t.teamID\n" +
                "GROUP BY t.teamID, t.teamName\n" +
                "ORDER BY wins DESC;";

        TeamCRUD teamOperations = new TeamCRUD();
        Map<String, Integer> wins = teamOperations.getWinRecord(sql);

        System.out.printf("%-25s %-10s%n", "Team", "Wins");
        for (Map.Entry<String, Integer> entry : wins.entrySet()) {
            System.out.printf("%-25s %-10d%n", entry.getKey(), entry.getValue());
        }
    }

    static void printMenu(){
        System.out.println("\n--- NBA Stat Tracker ---");
        System.out.println("Select an option from below.");
        System.out.println("[Easy; Yassin, Nour] 1. List all entities.");
        System.out.println("[Medium; Yassin, Sena, Frederico] 2. List players who average at least 20 points, 5 rebounds, and 5 assists per game.");
        System.out.println("[Easy; Yassin Colvin] 3. Lookup game by date.");
        System.out.println("[Medium; Yassin] 4. List the teams and their total wins.");
        System.out.println("[Hard, Frederico, Yassin, Colvin] TODO 5. Login/Create Account"); //user can create an account or login, to follow players
        System.out.println("[Hard; Yassin, Nour] TODO 6. Admin Settings"); //allows user to enter/remove data as they please without needing access to database
        System.out.println("0. Exit");
    }

    static String getNameFromPlayer(Player player){
        Map<String, String> map = new HashMap<>();
        PlayerCRUD playerOperations = new PlayerCRUD();
        List<Player> players = playerOperations.get();
        for(Player player1:players){
            map.put(player1.getID(), player1.getName());
        }
        return map.get(player.getID());
    }

    static String getNameFromTeam(Team team){
        Map<String, String> map = new HashMap<>();
        TeamCRUD teamOperations = new TeamCRUD();
        List<Team> teams = teamOperations.get();
        for(Team t:teams){
            map.put(t.getTeamID(), t.getTeamName());
        }
        return map.get(team.getTeamID());
    }
    static void insertPlayer(Player player){
        PlayerCRUD playerOperations = new PlayerCRUD();
        playerOperations.addPlayer(player);
    }

    static void insertTeam(Team team){
        TeamCRUD teamOperations = new TeamCRUD();
        teamOperations.addTeam(team);
    }

    static void insertGame(Game game){
        GameCRUD gameOperations = new GameCRUD();
        gameOperations.addGame(game);
    }

    static void insertViewer(Viewer viewer){
        ViewerCRUD viewerOperations = new ViewerCRUD();
        viewerOperations.addViewer(viewer);
    }

    static void insertFollow(Follow follow){
        FollowCRUD followOperations = new FollowCRUD();
        followOperations.follow(follow);
    }

    static void printAllPlayers() {
        PlayerCRUD playerOperations = new PlayerCRUD();
        List<Player> players = playerOperations.get();
        System.out.printf("%-10s %-18s %-6s%n", "PlayerID", "Name", "Team");
        for (Player player : players) {
            System.out.printf("%-10s %-18s %-6s%n",player.getID(), player.getName(), player.getTeam());
        }
    }

    static void printAllCoaches() {
        CoachCRUD coachOperations = new CoachCRUD();
        List<Coach> coaches = coachOperations.get();
        System.out.printf("%-10s %-18s %-6s%n", "CoachID", "Name", "Team");
        for (Coach coach : coaches) {
            System.out.printf("%-10s %-18s %-6s%n",coach.getID(), coach.getName(), coach.getTeam());

        }
    }

    static void printAllTeams(){
        TeamCRUD teamOperations = new TeamCRUD();
        List<Team> teams = teamOperations.get();
        System.out.printf("%-10s %-25s %-10s %-15s%n", "TeamID", "Name", "CoachID", "Conference");
        for(Team team : teams){
            System.out.printf("%-10s %-25s %-10s %-15s%n", team.getTeamID(), team.getTeamName(), team.getCoach().getID(), team.getConf());

        }
    }

    static void printAllGames(){
        GameCRUD gameOperations = new GameCRUD();
        List<Game> games = gameOperations.get();
        System.out.printf("%-12s %-12s %-12s %-10s %-10s %-10s%n",
                "Date", "Home Score", "Away Score", "GameID", "HomeID", "AwayID");
        for(Game game : games){
            System.out.printf("%-12s %-12s %-12s %-10s %-10s %-10s%n", game.getDate(), game.getTeam1score(), game.getTeam2score(),
                   game.getGameID(), game.getTeam1ID(), game.getTeam2ID());

        }
    }

    static void printAllViewers(){
        ViewerCRUD viewerOperations = new ViewerCRUD();
        List<Viewer> viewers = viewerOperations.get();
        System.out.println("Username");
        for(Viewer viewer : viewers){
            System.out.println(viewer.getUsername());
        }
    }

    static void printAllPlayerStats(){
        PlayerStatsCRUD playerStatsCRUD = new PlayerStatsCRUD();
        List<PlayerStats> playerStats = playerStatsCRUD.get();
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s%n",
                "PlayerID", "GameID", "Points", "Assists", "Rebounds", "StatID");

        for (PlayerStats ps : playerStats) {
            System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s%n", ps.getPlayerID().getID(), ps.getGameID().getGameID(), ps.getPoints()
                    ,ps.getAssists(), ps.getRebounds(), ps.getStatID());
        }
    }
}
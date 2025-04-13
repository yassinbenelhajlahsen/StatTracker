public class Game {
    private java.sql.Date date;
    private int team1score;
    private int team2score;
    private String gameID;
    private Team team1ID;
    private Team team2ID;

    public Game(java.sql.Date date, int team1score, int team2score, String gameID, Team team1ID, Team team2ID) {
        this.date = date;
        this.team1score = team1score;
        this.team2score = team2score;
        this.gameID = gameID;
        this.team1ID = team1ID;
        this.team2ID = team2ID;
    }

    public Game(String gameID) {
        this.gameID = gameID;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public int getTeam1score() {
        return team1score;
    }

    public int getTeam2score() {
        return team2score;
    }

    public String getGameID() {
        return gameID;
    }

    public Team getTeam1ID() {
        return team1ID;
    }

    public Team getTeam2ID() {
        return team2ID;
    }
}

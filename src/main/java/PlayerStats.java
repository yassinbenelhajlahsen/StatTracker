public class PlayerStats {
    private Player playerID;
    private Game gameID;
    private String statID;
    private int points;
    private int assists;
    private int rebounds;

    public PlayerStats(Player playerID, Game gameID, String statID, int points, int assists, int rebounds) {
        this.playerID = playerID;
        this.gameID = gameID;
        this.statID = statID;
        this.points = points;
        this.assists = assists;
        this.rebounds = rebounds;
    }

    public Player getPlayerID() {
        return playerID;
    }

    public Game getGameID() {
        return gameID;
    }

    public String getStatID() {
        return statID;
    }

    public int getPoints() {
        return points;
    }

    public int getAssists() {
        return assists;
    }

    public int getRebounds() {
        return rebounds;
    }
}

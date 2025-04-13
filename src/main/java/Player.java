public class Player{
    private String playerID;
    private Team team;
    private String name;

    public Player(String playerID, Team team, String name) {
        this.playerID = playerID;
        this.team = team;
        this.name = name;
    }

    public Player(String playerID) {
        this.playerID = playerID;
    }

    public String getID() {
        return playerID;
    }

    public Team getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }
}

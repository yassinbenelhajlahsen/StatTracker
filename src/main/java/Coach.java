public class Coach{
    private final String coachID;
    private Team team;
    private String name;

    public Coach(String coachID, String name, Team team) {
        this.coachID = coachID;
        this.team = team;
        this.name = name;
    }

    public Coach(String coachID){
        this.coachID=coachID;
    }

    public String getID() {
        return coachID;
    }

    public Team getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }


}

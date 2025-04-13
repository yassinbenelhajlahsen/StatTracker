public class Team {
    private String teamID;
    private String teamName;
    private Coach coach;
    private String conf;


    public Team(String teamID, String teamName, Coach coach, String conf){
        this.teamID=teamID;
        this.teamName=teamName;
        this.coach=coach;
        this.conf = conf;
    }

    public Team(String teamID){
        this.teamID=teamID;
    }
    public String getTeamID(){return teamID;}

    public String getTeamName() {return teamName;}

    public Coach getCoach() {return coach;}

    public String getConf() {return conf;}

    @Override
    public String toString() {
        return teamID;
    }
}

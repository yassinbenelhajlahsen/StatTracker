import java.sql.*;
import java.util.*;

public class TeamCRUD {
    private final Connection connection;

    public TeamCRUD() {
        this.connection = DBConnection.getConnection();
    }



    public void addTeam(Team team){
        try{
            String sql = "insert into Team (teamID, teamName, coachID, conference) values (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, team.getTeamID());
            stmt.setString(2, team.getTeamName());
            stmt.setObject(3, team.getCoach());
            stmt.setString(4, team.getConf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Team> get(){
        List<Team> teams = new ArrayList<>();
        try {
            String sql = "select * from Team";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Team team = new Team(
                        rs.getString("teamID"),
                        rs.getString("teamName"),
                        new Coach(rs.getString("coachID")),
                        rs.getString("conference")
                );
                teams.add(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    public Map<String, Integer> getWinRecord(String sql){
        Map<String, Integer> records = new LinkedHashMap<>();

        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String teamID = rs.getString("teamID");
                String teamName = rs.getString("teamName");
                int wins = rs.getInt("wins");
                records.put(teamName, wins);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }



}

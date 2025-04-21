import java.sql.*;
import java.util.*;

public class TeamCRUD {
    private final Connection connection;

    public TeamCRUD() {
        this.connection = DBConnection.getConnection();
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
        }catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return teams;
    }

    public void getRecord(String sql){
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String teamName = rs.getString("teamName");
                int wins = rs.getInt("wins");
                int losses = rs.getInt("losses");
                System.out.printf("%-25s %-10s%n", teamName, wins + "-" + losses);
            }

        }catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

}


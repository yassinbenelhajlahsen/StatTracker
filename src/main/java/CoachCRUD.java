import java.sql.*;
import java.util.*;

public class CoachCRUD {
    private final Connection connection;

    public CoachCRUD() {
        this.connection = DBConnection.getConnection();
    }

    public void setTeamOperations() {
    }

    public Coach getCoachByID(String coachID) {

        try {
            String sql = "SELECT * FROM Coach WHERE coachID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, coachID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Coach(
                        rs.getString("coachID"),
                        rs.getString("name"),
                        new Team(rs.getString("teamID"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addCoach(Coach coach){
        try{
            String sql = "insert into Coach (coachID, name, team) values (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, coach.getID());
            stmt.setString(2, coach.getName());
            stmt.setObject(3, coach.getTeam().getTeamID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Coach> get(){ //returns arraylist ALL coaches
        List<Coach> coaches = new ArrayList<>();

        try {
            String sql = "select * from Coach";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Coach coach = new Coach(
                        rs.getString("coachID"),
                        rs.getString("name"),
                        new Team(rs.getString("teamID"))
                        );
                coaches.add(coach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coaches;
    }
}


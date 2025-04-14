import java.sql.*;
import java.util.*;

public class CoachCRUD {
    private final Connection connection;

    public CoachCRUD() {
        this.connection = DBConnection.getConnection();
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
            System.out.println("SQL error "+ e.getMessage() );
        }
        return coaches;
    }
}


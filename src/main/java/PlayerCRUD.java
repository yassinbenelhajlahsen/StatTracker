import java.sql.*;
import java.util.*;

public class PlayerCRUD {
    private final Connection connection;

    public PlayerCRUD() {
        this.connection = DBConnection.getConnection();
    }

    public void addPlayer(Player player){
        try{
            String sql = "insert into Player (playerID, teamID, name) values (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, player.getID());
            stmt.setObject(2, player.getTeam().getTeamID());
            stmt.setString(3, player.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Player> get(){ //returns arraylist ALL coaches
        List<Player> players = new ArrayList<>();

        try {
            String sql = "select * from Player"; //for specific queries, we overload this method and pass in the sql statment
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                Player player = new Player(
                        rs.getString("playerID"),
                        new Team(rs.getString("teamID")),
                        rs.getString("name")
                );
                players.add(player);
            }
        } catch (SQLException e) {
                e.printStackTrace();
            }
            return players;
        }
    }


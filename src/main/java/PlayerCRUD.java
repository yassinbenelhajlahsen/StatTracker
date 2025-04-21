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
            System.out.println("Player: "+player.getName()+ " inserted successfully.");
        } catch (SQLException e) {
            System.out.println("SQL error "+ e.getMessage() );
        }
    }

    public void deletePlayer(Player player) {
        try {
            String sql = "DELETE FROM Player WHERE PlayerID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, player.getID());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) System.out.println("Player deleted successfully.");
            else System.out.println("No player found with that ID");

        } catch (SQLException e) {
            System.out.println("SQL error "+ e.getMessage() );
        }
    }


    public List<Player> get(){ //returns arraylist ALL players
        List<Player> players = new ArrayList<>();

        try {
            String sql = "select * from Player";
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
        }catch (SQLException e) {
            System.out.println("SQL error "+ e.getMessage() );
        }
            return players;
        }

    public String getPlayerIDByName(String name) {
        String playerID = null;
        String sql = "SELECT playerID FROM Player WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                playerID = rs.getString("playerID");
            } else {
                System.out.println("Player not found.");
            }

        } catch (SQLException e) {
            System.out.println("SQL error "+ e.getMessage() );
        }

        return playerID;
    }
    }


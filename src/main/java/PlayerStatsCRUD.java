import java.sql.*;
import java.util.*;


public class PlayerStatsCRUD {
    private final Connection connection;

    public PlayerStatsCRUD() {
        this.connection = DBConnection.getConnection();
    }

    public void addPlayerStat(PlayerStats playerStats){
        try{
            String sql = "insert into PlayerStats (PlayerID, GameID, points, rebounds, assists, StatID) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, playerStats.getPlayerID().getID());
            stmt.setString(2, playerStats.getGameID().getGameID());
            stmt.setInt(3, playerStats.getPoints());
            stmt.setInt(4, playerStats.getRebounds());
            stmt.setInt(5, playerStats.getAssists());
            stmt.setString(6, playerStats.getStatID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlayerStats> get() {
        List<PlayerStats> playerStatsList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PlayerStats";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                PlayerStats ps = new PlayerStats(
                        new Player(rs.getString("PlayerID")),
                        new Game(rs.getString("GameID")),
                        rs.getString("statID"),
                        rs.getInt("points"),
                        rs.getInt("assists"),
                        rs.getInt("rebounds")
                );
                playerStatsList.add(ps);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerStatsList;
    }

    public List<Player> get(String sql) {
        List<Player> players = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Player p = new Player(rs.getString("PlayerID"));
                players.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

}

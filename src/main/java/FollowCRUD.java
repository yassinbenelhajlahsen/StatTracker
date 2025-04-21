import java.sql.*;
import java.util.*;

import static javax.swing.UIManager.getString;

public class FollowCRUD {
    private final Connection connection;

    String getNameFromPlayer(Player player) {
        return getString(player);
    }

    public FollowCRUD() {
        this.connection = DBConnection.getConnection();
    }

    public void follow(Follow follow) {
        try {
            String playerCheckSql = "SELECT COUNT(*) FROM Player WHERE playerID = ?";
            PreparedStatement playerCheckStmt = connection.prepareStatement(playerCheckSql);
            playerCheckStmt.setString(1, follow.getPlayerID().getID());
            ResultSet rs = playerCheckStmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Player not found. Unable to follow.");
                return;
            }

            String sql = "insert into Follow (ViewerID, PlayerID, FollowDate) values (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, follow.getViewerID().username());
            stmt.setString(2, follow.getPlayerID().getID());
            stmt.setDate(3, follow.getDate());
            stmt.executeUpdate();
            System.out.println("You successfully followed: " + getNameFromPlayer(follow.getPlayerID()));
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }


    public List<Player> get(Viewer viewer) {
        List<Player> following = new ArrayList<>();
        try {
            String sql = "select PlayerID from Follow where ViewerID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, viewer.username());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Player player = new Player(rs.getString("PlayerID"));
                following.add(player);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return following;
    }

    public List<Viewer> get(Player player) {
        List<Viewer> followers = new ArrayList<>();

        try {
            String sql = "select ViewerID from follow where playerID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, player.getID());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Viewer viewer = new Viewer(rs.getString("ViewerID"));
                followers.add(viewer);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return followers;
    }

    public Map<Player, Integer> getMostFollowed(String sql){
        Map<Player, Integer> playerFollowerMap = new LinkedHashMap<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                Player player = new Player(rs.getString(("playerID")));
                int followers = rs.getInt("followers");
                playerFollowerMap.put(player, followers);
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return  playerFollowerMap;
    }


    public void unfollow(Follow follow) {
        try {
            String sql = "DELETE FROM Follow WHERE ViewerID = ? AND PlayerID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, follow.getViewerID().username());
            stmt.setString(2, follow.getPlayerID().getID());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0)
                System.out.println("You successfully unfollowed: " + follow.getPlayerID().getName());
            else System.out.println("No player found with that name");
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }
}





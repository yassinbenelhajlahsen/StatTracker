import java.sql.*;
import java.util.*;

public class FollowCRUD {
    private final Connection connection;

    public FollowCRUD(){
        this.connection= DBConnection.getConnection();
    }

    public void follow(Follow follow){
        try{
            String sql = "insert into Follow (ViewerID, PlayerID, FollowDate) values (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, follow.getViewerID().getUsername());
            stmt.setString(1, follow.getPlayerID().getID());
            stmt.setDate(1, follow.getDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

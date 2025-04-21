import java.sql.*;
import java.util.*;

public class ViewerCRUD {
    private final Connection connection;

    public ViewerCRUD() {
        this.connection = DBConnection.getConnection();
    }

    public void addViewer(Viewer viewer) {
        try {
            String sql = "insert into Viewer (username) values (?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, viewer.username());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL error "+ e.getMessage() );
        }
    }

    public List<Viewer> get() {
        List<Viewer> viewers = new ArrayList<>();

        try {
            String sql = "select * from Viewer";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Viewer viewer = new Viewer(
                        rs.getString("username"));
                viewers.add(viewer);
            }
        } catch (SQLException e) {
            System.out.println("SQL error "+ e.getMessage() );
        }
        return viewers;

    }
}

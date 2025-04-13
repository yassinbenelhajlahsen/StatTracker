import java.sql.*;
import java.util.*;

public class GameCRUD {
    private final Connection connection;

    public GameCRUD(){this.connection=DBConnection.getConnection();}

    public void addGame(Game game){
        try{
            String sql = "insert into Game (date, team1score, team2score, gameID, " +
                    "team1ID, team2ID) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, game.getDate());
            stmt.setInt(2, game.getTeam1score());
            stmt.setInt(3, game.getTeam2score());
            stmt.setString(4, game.getGameID());
            stmt.setObject(5, game.getTeam1ID().getTeamID());
            stmt.setObject(6, game.getTeam2ID().getTeamID());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Game> get(){
        List<Game> games = new ArrayList<>();

        try{
            String sql = "select * from Game";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                Game game = new Game(
                        rs.getDate("date"),
                        rs.getInt("team1score"),
                        rs.getInt("team2score"),
                        rs.getString("gameID"),
                        new Team(rs.getString("team1ID")),
                        new Team(rs.getString("team2ID"))
                );
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return games;
    }

    public List<Game> get(String sql){
        List<Game> games = new ArrayList<>();

        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                Game game = new Game(
                        rs.getDate("date"),
                        rs.getInt("team1score"),
                        rs.getInt("team2score"),
                        rs.getString("gameID"),
                        new Team(rs.getString("team1ID")),
                        new Team(rs.getString("team2ID"))
                );
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return games;
    }

}

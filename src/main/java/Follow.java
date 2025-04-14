public class Follow {
    private Viewer viewerID;
    private Player playerID;
    private java.sql.Date date;

    public Follow(Viewer viewerID, Player playerID, java.sql.Date date) {
        this.viewerID = viewerID;
        this.playerID = playerID;
        this.date = date;
    }

    public Follow(Viewer viewerID, Player playerID) {
        this.viewerID = viewerID;
        this.playerID=playerID;
    }

    public Viewer getViewerID() {
        return viewerID;
    }

    public Player getPlayerID() {
        return playerID;
    }

    public java.sql.Date getDate() {
        return date;
    }
}

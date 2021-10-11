package za.ac.nplinnovations.nplpoker.connection.entities;

import java.util.List;

public class MainResponse {
    private List<Player> winners;
    private List<Player> players;

    public MainResponse() {
    }

    public List<Player> getWinners() {
        return winners;
    }

    public List<Player> getPlayers() {
        return players;
    }
}

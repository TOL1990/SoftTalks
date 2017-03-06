package service;

import model.dao.PlayerDao;
import model.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Taras on 03.03.2017.
 */
public class DaoService {
    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> players = null;
        try {
            players = PlayerDao.getAllPlayers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public void addPlayer(String nick, String password) {
        PlayerDao.addPlayer(new Player(nick, password));
    }
}

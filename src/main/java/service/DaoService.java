package service;

import model.dao.PlayerDao;
import model.entity.Player;

import java.util.ArrayList;

/**
 * Created by Taras on 03.03.2017.
 */
public class DaoService {
    public ArrayList<Player> getAllPlayers() {
        return PlayerDao.getPlayersList();
    }

    public void addPlayer(String nick, String password) {
        PlayerDao.addPlayer(new Player(nick, password));
    }
}

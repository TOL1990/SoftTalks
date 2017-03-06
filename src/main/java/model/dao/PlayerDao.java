package model.dao;

import model.dao.utils.DaoUtils;
import model.entity.Player;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Taras on 03.03.2017.
 */
public class PlayerDao {

    public static final String GET_ALL_PLAYERS = "SELECT id_player, nick_name, password FROM softchatdb.players";
    public static final String ADD_PLAYER = "INSERT INTO softchatdb.players (nick_name, password) VALUES (?,?)";


    private static ArrayList<Player> playersList;

    public static ArrayList<Player> getAllPlayersFromDB() {

        Connection connection = DaoUtils.getConnection();
        ArrayList<Player> players = new ArrayList<Player>();


        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_PLAYERS);

            while (rs.next()) {
                int id = rs.getInt("id_player");
                String nick = rs.getString("nick_name");
                String password = rs.getString("password");
                players.add(new Player(id, nick, password));
            }

            DaoUtils.close(connection, statement, rs);
        } catch (SQLException e) {
            System.err.println("Can't getting players from DB");
            e.printStackTrace();
        }
        return players;
    }

    public static void addPlayer(Player player) {
        Connection connection = DaoUtils.getConnection();

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(ADD_PLAYER);

            statement.setString(1, player.getNickName());
            statement.setString(2, player.getPasword());
            statement.executeUpdate();
            updatePlayersCash();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DaoUtils.close(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Player> getPlayersList() {
        if (playersList == null) {
            playersList = getAllPlayersFromDB();
            return playersList;
        } else
            return playersList;
    }

    public static void updatePlayersCash() {
        PlayerDao.playersList = getAllPlayersFromDB();
    }
}

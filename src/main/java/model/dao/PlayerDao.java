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


    public static ArrayList<Player> getAllPlayers() throws SQLException {

        Connection connection = DaoUtils.getConnection();
        ArrayList<Player> players = new ArrayList<Player>();


        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(GET_ALL_PLAYERS);

        while (rs.next()) {
            int id = rs.getInt("id_player");
            String nick = rs.getString("nick_name");
            String password = rs.getString("password");
            players.add(new Player(id, nick, password));
        }

        DaoUtils.close(connection, statement, rs);

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                DaoUtils.close(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

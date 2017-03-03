import client.Client;
import server.Server;
import service.DBPlayers;
import service.DaoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Taras on 02.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        verse1();

    }


    public static void verse1() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        getUsersFromDB();

        System.out.println("Choose server  \" S \" or client \" C \"  ");

        while (true) {
            String answer = "";
            try {
                answer = reader.readLine().toLowerCase();

                if (answer.equals("s")) {
                    new Server();
                    break;
                } else if (answer.equals("c")) {
                    new Client();
                    break;
                } else
                    System.out.println("Wrong symbol. Please, enter correct symbol ");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void getUsersFromDB() {
        DBPlayers.playersList = new DaoService().getAllPlayers();

        System.out.println(DBPlayers.playersList);
    }
}

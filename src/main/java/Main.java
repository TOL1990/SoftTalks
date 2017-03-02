import client.Client;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Created by Taras on 02.03.2017.
 */
public class Main {
    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose server  \" S \" or client \" C \"  ");

        while (true) {
            String answer = null;
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
}

package client;


import model.entity.Player;
import service.DBPlayers;
import service.DaoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Taras on 02.03.2017.
 */
public class Client {
    private static final int PORT = 7000;
    private Socket socket;
    private BufferedReader in; //incomming stream
    private PrintWriter out; //outpuut stream

    public Client() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Insert the IP");
        String ip = "";

        try {
            ip = reader.readLine();

            socket = new Socket(ip, PORT);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            // Если прошел валидацию то
            String nikName = playerValidation(); // добавить проверку если получили null то беда
            out.println(nikName);

            Requester resender = new Requester(in);
            resender.start();

            String msgStr = "";
            while (!msgStr.equals("exit")) {
                msgStr = reader.readLine();
                out.println(msgStr);
            }
            resender.setStop();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }

    private void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Threads were't be close");
            e.printStackTrace();
        }
    }

    /**
     * @return nick if validation is pass, otherwise return null
     * @throws IOException
     */
    private String playerValidation() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Choose registration  \" R \" or Log in \" L \"  ");//залогиниться или зарегаться

        while (true) {
            String answer = "";
            try {
                answer = reader.readLine().toLowerCase();

                if (answer.equals("r")) {
                    ////////Registration\\\\\\\\\\
                    System.out.println("Insert your nickname");
                    String nikName = reader.readLine();

                    System.out.println("Insert your password");
                    String password = reader.readLine();

                    //добавляем в БД
                    DaoService ds = new DaoService();
                    ds.addPlayer(nikName, password);

                    //Обновляем список учасников
                    DBPlayers.playersList = ds.getAllPlayers();

                    return nikName;

                } else if (answer.equals("l")) {

                    ////////Log in\\\\\\\\\\

                    System.out.println("Insert your nickname");
                    String nikName = reader.readLine();

                    Player player = getPlayer(nikName);

                    System.out.println("Insert your password");
                        String password = reader.readLine();
                        if(player.getPasword().equals(password)) return nikName;

//                    for (Player p :
//                            DBPlayers.playersList) {
//                        if (p.getNickName().equals(nikName)) // if nick name already exist, check password
//                        {
//                            System.out.println("Insert your password");
//                            String password = reader.readLine();
//                            if (password.equals(p.getPasword())) {
//                                return nikName;
//                                break;
//                            }
//
//                            while (true) {
//                                System.out.println("Wrong password. Try again");
//                                password = reader.readLine();
//                                if (password.equals(p.getPasword())) return nikName;
//                            }
//
//                        }
//                    }

                    break;

                } else
                    System.out.println("Wrong symbol. Please, enter correct symbol ");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    private boolean isPlayerExist(String name) {
        for (Player p :
                DBPlayers.playersList) {
            if (p.getNickName().equals(name)) return true;
        }

        return false;
    }

    /**
     * @param nick
     * @return Player by nick if he exist. Or null
     */
    private Player getPlayer(String nick) {

        synchronized (DBPlayers.playersList) {
            for (Player p :
                    DBPlayers.playersList) {

                if (p.getNickName().equals(nick)) return p;
            }
        }
        return null;
    }

}

package server;

import model.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * Created by Taras on 02.03.2017.
 */
public class ServerThread extends Thread {
    List<ServerThread> connections;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private String nik = "";
    private Server server;

    public ServerThread(Socket socket, List<ServerThread> connections, Server server) {
        this.socket = socket;
        this.connections = connections;
        this.server = server;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            close(); // ServerThread must be closed
        }
    }

    @Override
    public void run() {
        try {
            nik = in.readLine();
            String pass = in.readLine();
            checkAuthorisation(nik, pass);
                synchronized (connections) {

                    for (ServerThread con :
                            connections) {
                        con.out.println(nik + " connected to Soft chat");
                    }
                }

                String msg = "";
                while (true) {
                    msg = in.readLine();
                    if (msg.equals("exit")) break;

                    synchronized (connections) {
                        for (ServerThread con :
                                connections) {
                            con.out.println(nik + ": " + msg);
                        }

                    }
                }
                synchronized (connections) {
                    synchronized (connections) {
                        for (ServerThread con :
                                connections) {
                            con.out.println(nik + " has left.");
                        }
                    }
                }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();

        }
    }

    /**
     * If We haven't player with login nickName, we create new user with password
     * haven't pasword cheking
     */
    private boolean checkAuthorisation(String nickName, String password) {
        Player loginedPlayer = new Player(nickName, password);

        if (server.playersList.contains(loginedPlayer))
        {
            return true;
        }
        else {
             server.addNewUser(nickName, password);
        }
        return false;

    }


    public void close() {
        try {
            in.close();
            out.close();
            socket.close();

            connections.remove(this);
            if (connections.size() == 0) {
                server.closeAll();
                System.exit(0);
            }

        } catch (IOException e) {
            System.out.println("Threads weren't close");
            e.printStackTrace();
        }
    }

}

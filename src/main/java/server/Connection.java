package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * Created by Taras on 02.03.2017.
 */
public class Connection extends Thread {
    List<Connection> connections;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private String nik = "";
    private Server server;

    public Connection(Socket socket, List<Connection> connections, Server server) {
        this.socket = socket;
        this.connections = connections;
        this.server = server;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            close(); // Connection must be closed
        }
    }

    @Override
    public void run() {
        try {
            nik = in.readLine();

            synchronized (connections) {

                for (Connection con :
                        connections) {
                    con.out.println(nik + " connected to Soft chat");
                }

            }

            String msg = "";
            while (true) {
                msg = in.readLine();
                if (msg.equals("exit")) break;

                synchronized (connections) {
                    for (Connection con :
                            connections) {
                        con.out.println(nik + ": " + msg);
                    }

                }
            }
            synchronized (connections) {
                synchronized (connections) {
                    for (Connection con :
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

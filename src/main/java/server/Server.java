package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Taras on 02.03.2017.
 */
public class Server {
    public static final int PORT = 7000;

    private List<Connection> connections =
            Collections.synchronizedList(new ArrayList<Connection>());
    private ServerSocket server;

    public Server() {
        try {
            server = new ServerSocket(PORT);

            while (true) {
                Socket socket = server.accept();

                Connection con = new Connection(socket, connections, this);
                connections.add(con);
                con.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    public void closeAll() {
        try {
            server.close();

            //Run allow all connections and shut them down
            synchronized (connections) {
                Iterator<Connection> iter = connections.iterator();
                while (iter.hasNext()) {
                    ((Connection) iter.next()).close();
                }
            }
        } catch (Exception e) {
            System.err.println("Threads weren't closed!");
        }
    }

}

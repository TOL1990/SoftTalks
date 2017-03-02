package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    public Server()
    {
        try {
            server = new ServerSocket(PORT);

            while (true)
            {
                Socket socket = server.accept();

                Connection con = new Connection(socket);
                connections.add(con);
                con.start();
            }
        } catch (IOException e) {
            e.printStackTrace();    
        }
        finally {
            closeAll();
        }
    }

    private void closeAll() {
        try {
            server.close();

            //Run allow all connections and shut them down
        synchronized(connections) {
            Iterator<Server.Connection> iter = connections.iterator();
            while(iter.hasNext()) {
                ((Server.Connection) iter.next()).close();
            }
        }
    } catch (Exception e) {
        System.err.println("Threads weren't closed!");
    }
    }

    public class Connection extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private Socket socket;

        private String nik = "";

      public  Connection(Socket socket) {
            this.socket = socket;
            try {
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
//            close(); // Connection must be closed
            }
        }
        @Override
        public void run() {
            try {
                nik = in.readLine();

                synchronized (connections)
                {
                    Iterator<Connection> iter = connections.iterator();
                    while(iter.hasNext()) {

                        iter.next().out.println(nik + " connected to Soft chat");

                    }
                }

                String msg = "";

                while (true)
                {
                    if (msg.equals("exit"))
                        out.println(nik + " has left.");

                    for (Connection c :
                            connections) {
                        out.println(nik + ": " + msg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                close();

            }
        }

        private void close() {
            try {
                in.close();
                out.close();
                socket.close();

                connections.remove(this);
                if (connections.size() == 0) {
//                    Server.this.closeAll(); // дописать метод
                    System.exit(0);
                }

            } catch (IOException e) {
            System.out.println("Threads weren't close");
                e.printStackTrace();
            }
        }

    }
}

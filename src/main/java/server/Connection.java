package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Taras on 02.03.2017.
 */
public class Connection extends Thread {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    List<Connection> connections;

    private String nik = "";

    public Connection(Socket socket, List<Connection> connections) {
        this.socket = socket;
        this.connections = connections;
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
                Iterator<Connection> iter = connections.iterator();
                while (iter.hasNext()) {
                    ((Connection) iter.next()).out.println(nik + " connected to Soft chat");
                }
            }

            String msg = "";
            while (true) {
                msg = in.readLine();
                if (msg.equals("exit")) break;

                synchronized (connections) {
                    Iterator<Connection> iterator = connections.iterator();
                    while (iterator.hasNext()) {
                        ((Connection) iterator.next()).out.println(nik + ": " + msg);
                    }
                }
            }
            synchronized (connections)
            {
                Iterator<Connection> iterator = connections.iterator();
                while (iterator.hasNext())
                {
                    ((Connection)iterator.next()).out.println(nik + " has left.");
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

//            connections.remove(this);
//            if (connections.size() == 0) {
//                Server.this.closeAll(); // дописать метод
//                System.exit(0);
//            }

        } catch (IOException e) {
            System.out.println("Threads weren't close");
            e.printStackTrace();
        }
    }


}

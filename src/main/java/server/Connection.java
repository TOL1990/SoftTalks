package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Taras on 02.03.2017.
 */
public class Connection extends Thread {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    private String nik = "";

    Connection(Socket socket) {
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

//            synchronized (connections)
//            {
//
//            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

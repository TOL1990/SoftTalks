package client;


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

            System.out.println("Insert your nickname");
            out.println(reader.readLine());

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


}

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
    public static int PORT = 7000;
    private Socket socket;
    private BufferedReader in; //incomming stream
    private PrintWriter out; //outpuut stream

    public Client() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String ip = "192.168.31.22";

        try {
//        ip = reader.readLine();

            socket = new Socket(ip, PORT);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);




            ClientThread resender = new ClientThread(in);
            resender.start();
            String nikName = reader.readLine();
            out.println(nikName);
            String password = reader.readLine();
            out.println(password);

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

    private void userLogin() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Nick please");
            String nikName = reader.readLine();
            out.println(nikName);

            System.out.println("Password");
            String password = reader.readLine();
            out.println(password);

            ClientThread resender = new ClientThread(in);
            resender.start();
            if (in.readLine().equals("Wrong login or pass"))
                System.out.println();
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

    private void userRegistration() {

    }
}
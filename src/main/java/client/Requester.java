package client;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Taras on 02.03.2017.
 */
public class Requester extends Thread {
    private boolean isStop;
    BufferedReader in;
    public  Requester(BufferedReader in){
        this.in = in;
    }

    public void setStop()
    {
        isStop = true;
    }

    @Override
    public void run() {
        try {
            while (!isStop)
            {
                String str = in.readLine();

                System.out.println(str);
            }
        } catch (IOException e) {
            System.out.println("Fail during getting msg");
            e.printStackTrace();
        }
    }
}

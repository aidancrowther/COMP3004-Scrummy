package COMP3004;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import COMP3004.controllers.*;

public class SyncServer{

    public static void main (String[] args) throws Exception{

        ServerSocket listener = new ServerSocket(4000);

        System.out.println("Server is listening on port 4000");

        try{
            String gameType = "g";
            if (args.length != 0){
                args = parseArgs(args);
                Controller controller = new Controller(args);
                //controller.run(false);
            } else {
                Controller controller = new Controller(false);
                //controller.run(false);
            }

            while(true);
        } finally {
            listener.close();
        }
    }

    public static String[] parseArgs(String[] args){
        return new String[0];
    }

}

class SyncPlayer extends Thread{
    
}
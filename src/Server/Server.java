
package Server;

import java.net.*;
import java.io.*;
import javax.swing.*;

public class Server extends JFrame {

    ServerSocket serversock;
    private Socket socket;

    public void go() {
        Socket sock1;
        Socket sock2;
        try {
            serversock = new ServerSocket(5555);
            System.out.println("server running");

            while (true) {
                sock1 = serversock.accept();
                System.out.println("Player 1 Connected");
                sock2 = serversock.accept();
                System.out.println("Player 2 Connected");
                System.out.println("Making New Session");
                Thread t1 = new Thread(new MakeSession(sock1, sock2));
                t1.run();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Server game = new Server();
        game.go();
    }
}

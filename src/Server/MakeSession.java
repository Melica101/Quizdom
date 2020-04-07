/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author HP
 */
public class MakeSession implements Runnable {

    Socket sock1;
    Socket sock2;
    DataOutputStream dataOutputStreamPlayer1;
    DataOutputStream dataOutputStreamPlayer2;
    DataInputStream dataInputStreamPlayer1;
    DataInputStream dataInputStreamPlayer2;

    public MakeSession(Socket sock1, Socket sock2) {
        this.sock1 = sock1;
        this.sock2 = sock2;
        System.out.println("Made New Session!");
    }

    @Override
    public void run() {

        try {
            dataOutputStreamPlayer1 = new DataOutputStream(sock1.getOutputStream());
            dataOutputStreamPlayer2 = new DataOutputStream(sock2.getOutputStream());

            dataInputStreamPlayer1 = new DataInputStream(sock1.getInputStream());
            dataInputStreamPlayer2 = new DataInputStream(sock2.getInputStream());

            String player1 = dataInputStreamPlayer1.readUTF();
            String player2 = dataInputStreamPlayer2.readUTF();

            dataOutputStreamPlayer2.writeUTF(player1);
            dataOutputStreamPlayer1.writeUTF(player2);

            String player1Score = dataInputStreamPlayer1.readUTF();
            String player2Score = dataInputStreamPlayer2.readUTF();

            dataOutputStreamPlayer1.writeUTF(player2Score);
            dataOutputStreamPlayer2.writeUTF(player1Score);

            sock1.close();
            sock2.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

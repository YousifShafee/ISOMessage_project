package network;

import exceptions.WrongMessageException;
import iso8583.Message;
import commons.Utility;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiThreadServer implements Runnable {

    Socket csocket;

    MultiThreadServer(Socket csocket) {
        this.csocket = csocket;
    }

    public static void main(String args[]) throws Exception {
        ServerSocket ssock = new ServerSocket(9999);
        System.out.println("Listening");

        while (true) {
            Socket sock = ssock.accept();
            System.out.println("Connected");
            new Thread(new MultiThreadServer(sock)).start();
        }
    }

    public void run() {
        try {
            DataInputStream mDataInputStream = new DataInputStream(csocket.getInputStream());
            DataOutputStream mDataOutputStream = new DataOutputStream(csocket.getOutputStream());

            while (true) {
                boolean validMsg = false;
//____________________Get message______________________
                String Msg = "";
                Message m = null;

                Msg = mDataInputStream.readUTF();
                try {
                    m = new Message();
                    m = m.MessageSplite(Msg);
                    validMsg = true;
                    System.out.println("client :" + Msg);
                    System.out.println(m);
                } catch (WrongMessageException ex) {
                    Utility.logError(ex);
                    Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("This is Invalid Message.");
                }

                try {
                    //___________________Response_________________________________

                    mDataOutputStream.writeUTF(m.response());

                } catch (WrongMessageException ex) {
                    Utility.logError(ex);
                    mDataOutputStream.writeUTF("Input Message Not Valid.");
                    Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!validMsg) {
                    mDataInputStream.markSupported();
                    continue;
                }
                if (Msg.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (IOException ex) {
            Utility.logError(ex);
            Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Utility.logError(ex);
            Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Utility.logError(ex);
            Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            csocket.close();
        } catch (IOException ex) {
            Utility.logError(ex);
            Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

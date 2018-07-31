package network;

import iso8583.Message;
import iso8583.Utility;
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
            BufferedReader mBufferReader = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream mDataOutputStream = new DataOutputStream(csocket.getOutputStream());

            while (true) {
//____________________Get message______________________
                String Msg = "";
                Message m = null;
                // try{
                Msg = mDataInputStream.readUTF();
                m = new Message(Msg);
                System.out.println("client :" + Msg);
                System.out.println(m);
                // }catch(Exception e){
                //   String out = "Wrong Message";
                // mDataOutputStream.writeUTF(out);
                //}

//___________________Response_________________________________
                //String so = mBufferReader.readLine();
                mDataOutputStream.writeUTF(m.response().getMsg());
                if (Msg.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            csocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

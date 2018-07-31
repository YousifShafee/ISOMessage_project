package network;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws Exception {

        Socket mSocket = new Socket("localhost", 9999);
        DataOutputStream mDataOutputStream = new DataOutputStream(mSocket.getOutputStream());
         DataInputStream mDataInputStream = new DataInputStream(mSocket.getInputStream());
        BufferedReader mBufferReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String so = mBufferReader.readLine();
            mDataOutputStream.writeUTF(so);
              String Msg = mDataInputStream.readUTF();
               // m = new Message(Msg);
                System.out.println("Response :" + Msg);
            if (so.equalsIgnoreCase("exit")) {
                break;
            }
        }
        mSocket.close();
    }

}

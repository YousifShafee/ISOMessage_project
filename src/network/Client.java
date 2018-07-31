package network;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws Exception {

        Socket mSocket = new Socket("localhost", 9999);
        DataOutputStream mDataOutputStream = new DataOutputStream(mSocket.getOutputStream());
        BufferedReader mBufferReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String so = mBufferReader.readLine();
            mDataOutputStream.writeUTF(so);
            if (so.equalsIgnoreCase("exit")) {
                break;
            }
        }
        mSocket.close();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
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
         DataInputStream dout=new DataInputStream(csocket.getInputStream());
      BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
      DataOutputStream dout1=new DataOutputStream(csocket.getOutputStream());

while(true)
{
    
String yoo= dout.readUTF();
 
System.out.println("client :"+yoo);

String so=br.readLine();
dout1.writeUTF(so);
if(yoo.equalsIgnoreCase("exit"))
break;
}
         } catch (IOException ex) {
           Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
       }
 
       try {
           csocket.close();
       } catch (IOException ex) {
           Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
       }
    
      
   }
}
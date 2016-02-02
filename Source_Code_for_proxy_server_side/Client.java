/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;
import java.net.*;
import java.io.*;
/**
 *
 * @author Dev
 */
public class Client
{
   public static void main(String [] args)
   {
      String serverName = "localhost";
      int port = 6550;
      try
      {
         System.out.println("Connecting to " + serverName
                             + " on port " + port);
         Socket client = new Socket(serverName, port);
         System.out.println("Just connected to "
                      + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out =
                       new DataOutputStream(outToServer);

         out.writeUTF("http://www.cacs.louisiana.edu/~wu/");
         /*InputStream inFromServer = client.getInputStream();
         DataInputStream in =
                        new DataInputStream(inFromServer);
         System.out.println("Server says " + in.readUTF());*/
         client.close();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}

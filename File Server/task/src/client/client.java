package client;

import packets.packetServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class client {


     public static void start() {
             try (Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 11111);
                  ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                  ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

                 outputStream.writeObject(new getInput().execute());
                 new responseHandler().execute((packetServer) inputStream.readObject());
             } catch (Exception e) {
                 e.printStackTrace();
             }
    }

    public static void main(String[] arg){
        client.start();
    }

}

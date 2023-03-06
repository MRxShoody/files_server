package server;

import packets.answerType;
import packets.packetClient;
import packets.packetServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


class inputHandler extends Thread{

    static boolean running = true;
    private final Socket socket;
    public inputHandler(Socket clientSocket) {
        this.socket = clientSocket;
    }

     packetServer execute(packetClient packet){

        switch (packet.getRT()){
            case save -> {
                return new fileStorage().putFile(packet);
            }
            case exit -> {
                server.running = false;
                return new packetServer(answerType.EXITED);
            }
            case deleteByID, deleteByName -> {
                return new fileStorage().deleteFile(packet);
            }
            case getByName, getByID -> {
                return new fileStorage().getFile(packet);
            }
            default -> {
                return new packetServer(answerType.ERROR);
            }
        }
    }

    @Override
    public void run() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())){


            outputStream.writeObject(execute((packetClient) inputStream.readObject()));

            socket.close();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }
}

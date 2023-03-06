package server;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class server {

    public static boolean running = true;
    static ExecutorService ES = Executors.newFixedThreadPool(2);
    public static void start() {

        try (ServerSocket server = new ServerSocket(11111)) {
            server.setSoTimeout(100);
            fileStorage.takeHashMap();
            while (running) {
                try {
                    Socket socket = server.accept();
                    ES.submit(new inputHandler(socket));

                    /*
                    implement Runnable
                    ES.submit(()->new inputHandler(socket).run()); //pas nécéssaire de implement

                    new Thread(new inputHandler(socket)).start();

                    Thread thread = new Thread(new inputHandler(socket));
                    thread.start();

                    ES.submit(new inputHandler(socket));

                    OR

                    extends Thread
                    ES.submit(()-> new inputHandler(socket).start()); //weird ??? un thread qui créer un autre thread

                    new inputHandler(socket).start();

                    ES.submit(new inputHandler(socket));
                     */
                } catch (IOException ignored) {}
            }
            fileStorage.storeHashMap();
            ES.shutdownNow();
            }catch (Exception ignored){
        }
    }

    public static void main(String[] args) {
        server.start();
    }
}

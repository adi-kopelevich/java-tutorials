package sample.java.net.servers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kopelevi on 29/09/2015.
 */
public class MultithreadedServerExample implements Runnable {

    private final ServerSocket serverSocket;

    private volatile boolean isEnabled = true;

    MultithreadedServerExample(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Starting Server...");
        } catch (IOException e) {
            throw new RuntimeException("Failed to init socket server: ", e);
        }
    }

    @Override
    public void run() {
        while (isEnabled) {
            try {                // blocking - wait for a client request
                Socket socket = serverSocket.accept();
                System.out.println("Server got a request...");
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new ProcessingWorker(socket));
                executorService.shutdown();                // process the client request
                System.out.println("Server released request processing...");
            } catch (IOException e) {
                if (!isEnabled) {
                    System.out.println("Server Stopped.");
                    break;
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopServer() throws IOException {
        this.isEnabled = false;
        System.out.println("Server is going down...");
        serverSocket.close();
    }
}

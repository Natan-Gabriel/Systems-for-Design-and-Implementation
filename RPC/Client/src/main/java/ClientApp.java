
//import Common.src.main.java.TCP.Service;
import TCP.Service;
import TCP.TCPClient;
import UI.ClientConsole;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TCPClient tcpClient = new TCPClient();
        Service clientService = new ClientService(executorService, tcpClient);
        ClientConsole console = new ClientConsole(clientService);
        console.runConsole();

        executorService.shutdown();

        System.out.println("bye client");
    }
}

import TCP.Message;
import TCP.TCPClient;
//import Server.src.main.java.domain.Client;
import TCP.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ClientService implements Service {
    private ExecutorService executorService;
    private TCPClient tcpClient;

    public ClientService(ExecutorService executorService, TCPClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<String> communicate(String head, String body) {
        return CompletableFuture.supplyAsync(() -> {
            //create a request
            //send request to server
            //get response

            Message request = new Message(head, body);
            System.out.println("sending request: "+request);
            Message response = null;
            try {
                response = tcpClient.sendAndReceive(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("received response: "+response);

            return response.getBody();
        });
    }


}

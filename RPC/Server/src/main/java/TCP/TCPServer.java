package TCP;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

public class TCPServer {
    private ExecutorService executorService;
    private Map<String, UnaryOperator<Message>> methodHandlers;

    public TCPServer(ExecutorService executorService) {
        this.methodHandlers = new HashMap<>();
        this.executorService = executorService;
    }

    public void addHandler(String methodName, UnaryOperator<Message> handler) {
        methodHandlers.put(methodName, handler);
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(Message.PORT)) {
            while (true) {
                Socket client = serverSocket.accept();
                executorService.submit(new ClientHandler(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket client) {
            this.socket = client;
        }

        @Override
        public void run() {
            try (InputStream is = socket.getInputStream();
                 OutputStream os = socket.getOutputStream()) {
                Message request = new Message();
                request.readFrom(is);
                System.out.println("received request: " + request);
                Message response = methodHandlers.get(request.getHeader())
                        .apply(request);
                response.writeTo(os);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
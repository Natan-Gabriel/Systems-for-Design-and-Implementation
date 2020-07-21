package UI;


import TCP.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ClientConsole {
    Service service;

    public ClientConsole(Service s) {
        service = s;
    }

    public void runConsole() throws IOException, ExecutionException, InterruptedException {
        while (true) {
            System.out.println("Input command:\n1.Add a client\n2.Add a movie\n3.Delete a client\n4.Delete a movie\n5.Update a client\n6.Update a movie\n" +
                    "7.Print all clients\n8.Print all movies\n9.Rent movie.\n10.Return movie.\n11.Delete rental.\n12.Update rental.\n" +
                    "13.Print all rentals.\n14.Filter movies by name\n15.Filter clients by name\n16.Get most rented movie\n17.Get most active client\n18.Get client with most returned movies\n" +
                    "19.# of clients without movies - # of movies without clients=? (are there more clients without movies than movies without clients?)\n"
                    + "20.Print sorted movies\n21.Print sorted clients\n22.Print sorted rentals\n0.Exit");
            BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
            String selection = buf.readLine();
            if (selection.equals("0"))
                break;
            else if (selection.equals("1"))
            {
                String body="";
                System.out.println("Insert client name:");
                String name = buf.readLine();
                System.out.println("Insert client age: ");
                int age = Integer.parseInt(buf.readLine());
                System.out.println("Insert client serial number: ");
                String serial = buf.readLine();
                System.out.println("Insert client id: ");
                long id = Long.valueOf(buf.readLine());
                body+=name+","+Integer.toString(age)+","+serial+","+Long.toString(id);
                String finalBody = body;
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, body);
                com.thenAcceptAsync(s -> System.out.println(s));
            }
            else if (selection.equals("2")){
                String body="";
                System.out.println("Insert movie name:");
                String name = buf.readLine();
                System.out.println("Insert movie year: ");
                int year = Integer.parseInt(buf.readLine());
                System.out.println("Insert movie serial number: ");
                String serial = buf.readLine();
                System.out.println("Insert movie rating: ");
                int rating = Integer.parseInt(buf.readLine());
                System.out.println("Insert movie id: ");
                long id = Long.valueOf(buf.readLine());
                body+=name+","+Integer.toString(year)+","+serial+","+Integer.toString(rating)+","+Long.toString(id);
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, body);
                com.thenAcceptAsync(s -> System.out.println(s));
            }
            else if (selection.equals("3")){
                String body="";
                System.out.println("Insert client id: ");
                long id = Long.valueOf(buf.readLine());
                body+=Long.toString(id);
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, body);
                com.thenAcceptAsync(s -> System.out.println(s));
            }
            else if (selection.equals("4"))
            {
                String body="";
                System.out.println("Insert movie id: ");
                long id = Long.valueOf(buf.readLine());
                body+=Long.toString(id);
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, body);
                com.thenAcceptAsync(s -> System.out.println(s));
            }
            else if (selection.equals("5"))
            {
                String body="";
                System.out.println("Insert client name:");
                String name = buf.readLine();
                System.out.println("Insert client age: ");
                int age = Integer.parseInt(buf.readLine());
                System.out.println("Insert client serial number: ");
                String serial = buf.readLine();
                System.out.println("Insert client id: ");
                long id = Long.valueOf(buf.readLine());
                body+=name+","+Integer.toString(age)+","+serial+","+Long.toString(id);
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, body);
                com.thenAcceptAsync(s -> System.out.println(s));
            }
            else if (selection.equals("6"))
            {
                String body="";
                System.out.println("Insert movie name:");
                String name = buf.readLine();
                System.out.println("Insert movie year: ");
                int year = Integer.parseInt(buf.readLine());
                System.out.println("Insert movie serial number: ");
                String serial = buf.readLine();
                System.out.println("Insert movie rating: ");
                int rating = Integer.parseInt(buf.readLine());
                System.out.println("Insert movie id: ");
                long id = Long.valueOf(buf.readLine());
                body+=name+","+Integer.toString(year)+","+serial+","+Integer.toString(rating)+","+Long.toString(id);
                CompletableFuture<String> result = (CompletableFuture<String>) service.communicate(selection, body);
                result.thenAcceptAsync(s-> System.out.println(s));
            }
            else if (selection.equals("7"))
            {
                CompletableFuture<String> result = (CompletableFuture<String>) service.communicate(selection, "");
                result.thenAcceptAsync(s-> System.out.println(s));
            }
            else if (selection.equals("8"))
            {
                CompletableFuture<String> result = (CompletableFuture<String>) service.communicate(selection, "");
                result.thenAcceptAsync(s-> System.out.println(s));
            }
            else if (selection.equals("9"))
            {
                String body="";
                System.out.println("Insert rental serial number: ");
                String rentalSerial = buf.readLine();
                System.out.println("Insert movie ID: ");
                long movieID = Long.valueOf(buf.readLine());
                System.out.println("Insert client ID: ");
                long clientID = Long.valueOf(buf.readLine());
                System.out.println("Insert rental id: ");
                long id = Long.valueOf(buf.readLine());
                body+=rentalSerial+","+Long.toString(movieID)+","+Long.toString(clientID)+","+Long.toString(id);
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, body);
                com.thenAcceptAsync(s -> System.out.println(s));
            }
            else if (selection.equals("10"))
            {
                String body="";
                System.out.println("Insert rental serial number: ");
                String rentalSerial = buf.readLine();
                body+=rentalSerial;
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, body);
                com.thenAcceptAsync(s -> System.out.println(s));
            }
            else if (selection.equals("11"))
            {
                String body="";
                System.out.println("Insert rental id: ");
                long id = Long.valueOf(buf.readLine());
                body+=Long.toString(id);
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, body);
                com.thenAcceptAsync(s -> System.out.println(s));
            }
            else if (selection.equals("12"))
            {
                String body="";
                System.out.println("Insert rental serial number: ");
                String rentalSerial = buf.readLine();
                System.out.println("Insert movie ID: ");
                long movieID = Long.valueOf(buf.readLine());
                System.out.println("Insert client ID: ");
                long clientID = Long.valueOf(buf.readLine());
                System.out.println("Insert rental id: ");
                long id = Long.valueOf(buf.readLine());
                body+=rentalSerial+","+Long.toString(movieID)+","+Long.toString(clientID)+","+Long.toString(id);
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, body);
                com.thenAcceptAsync(s -> System.out.println(s));
            }
            else if (selection.equals("13"))
            {
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, "");
                com.thenAcceptAsync(s -> System.out.println(s));
            }
            else if (selection.equals("14"))
            {
                String body="";
                System.out.println("Insert filter");
                String filter = buf.readLine();
                body+=filter;
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, body);
                com.thenAcceptAsync(s -> System.out.println(s));
            }
            else if (selection.equals("15"))
            {
                String body="";
                System.out.println("Insert filter");
                String filter = buf.readLine();
                body+=filter;
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, body);
                com.thenAcceptAsync(s -> System.out.println(s));
            }
            else {
                String body="";
                CompletableFuture<String> com = (CompletableFuture<String>) service.communicate(selection, body);
                com.thenAcceptAsync(s -> System.out.println(s));
            }
        }
    }
}

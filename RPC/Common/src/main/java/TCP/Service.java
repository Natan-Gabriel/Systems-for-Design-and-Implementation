package TCP;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface Service {

    String SAY_HELLO = "hello";

    Future<String> communicate(String head, String body);
}

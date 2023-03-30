import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class HttpFetcher {

    public String fetch(String url) {
        try {
            var client = HttpClient.newHttpClient();
            
            var req = HttpRequest.newBuilder(URI.create(url)).GET().build();
            HttpResponse<String> res = client.send(req, BodyHandlers.ofString());

            return res.body();
        } catch (IOException | InterruptedException e) {
            throw new HttpFectherException("Erro ao consultar a URL :(");
        }
    }
}

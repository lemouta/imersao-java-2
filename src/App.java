import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        var url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        var client = HttpClient.newHttpClient();
        
        var req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> res = client.send(req, BodyHandlers.ofString());

        String body = res.body();

        var parser = new JsonParser();
        List<Map<String, String>> movieList = parser.parse(body);

        var stickersDir = "output/";
        new File(stickersDir).mkdir();

        var generator = new StickerGenerator();
        for (Map<String, String> movie : movieList) {
            InputStream inputStream = new URL(movie.get("image")).openStream();
            var fileName = stickersDir + movie.get("title") + ".png";            
            generator.create(inputStream, fileName);

            printAttr("Título", movie.get("title"), false);
            printAttr("Poster", movie.get("image"), false);
            printAttr("Classificação", movie.get("imDbRating"), true);

            var stars = (int) Math.floor(Double.valueOf(movie.get("imDbRating")));
            for (int i = 0; i < stars; i++) {
                System.out.print("\u2B50");
            }

            System.out.println();
        }
    }

    private static void printAttr(String name, String val, Boolean colored) {
        if (colored) {
            System.out.print("\u001b[37m\u001b[44m");
        }
        
        System.out.println(name + ": \u001b[1m" + val + "\u001b[m");
    }
}

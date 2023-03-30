import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    
    public static void main(String[] args) throws Exception {
        var stickersDir = "output/";
        new File(stickersDir).mkdir();

        API api = API.NASA;
        
        var http = new HttpFetcher();
        String json = http.fetch(api.getUrl());

        ContentExtractor extractor = api.getExtractor();
        List<Content> contentList = extractor.extractContent(json);

        var generator = new StickerGenerator();
        for (Content content : contentList) {
            InputStream inputStream = new URL(content.imageUrl()).openStream();
            var fileName = stickersDir + content.title() + ".png";            
            generator.create(inputStream, fileName);

            System.out.println(content.title());
            System.out.println();
        }
    }
}

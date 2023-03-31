import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NasaContentExtractor implements ContentExtractor {
    
    public List<Content> extractContent(String json) {
        var parser = new JsonParser();
        List<Map<String, String>> attrList = parser.parse(json);

        List<Content> contentList = attrList.stream()
            .map(attr -> {
                String title = attr.get("title");
                String imageUrl = attr.get("url");
                return new Content(title, imageUrl);
            })
            .collect(Collectors.toList());

        return contentList;
    }
}

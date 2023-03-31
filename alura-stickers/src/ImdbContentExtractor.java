import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImdbContentExtractor implements ContentExtractor {

    public List<Content> extractContent(String json) {
        var parser = new JsonParser();
        List<Map<String, String>> attrList = parser.parse(json);

        List<Content> contentList = attrList.stream()
            .map(attr -> {
                String title = attr.get("title");
                String imageUrl = attr.get("image").replaceAll("(@+)(.*).jpg$", "$1.jpg");
                return new Content(title, imageUrl);
            })
            .collect(Collectors.toList());

        return contentList;
    }
}

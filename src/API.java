public enum API {
    IMDB_TOP_MOVIES("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json", new ImdbContentExtractor()),
    NASA("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date=2023-03-05&end_date=2023-03-07", new NasaContentExtractor());

    private String url;
    private ContentExtractor extractor;

    API(String url, ContentExtractor extractor) {
        this.url = url;
        this.extractor = extractor;
    }

    public String getUrl() {
        return this.url;
    }

    public ContentExtractor getExtractor() {
        return this.extractor;
    }
}

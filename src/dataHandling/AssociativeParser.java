package dataHandling;

public abstract class AssociativeParser extends Parser {
    private final String format = getFormat();

    public AssociativeParser(String path) {
        super(path);
    }

    public void parseData() {
        super.parseData(format);
    }

    @Override
    protected String[] getLineData(String[] columns) {
        return new String[]{
                columns[0], //id
                columns[2], //artist
                columns[3], //song
        };
    }

    /**
     * here are specific parsers for the associative tables we need
     */

    public static class SongsAlbumsParser extends AssociativeParser {
        @Override
        public String getFormat() {
            return "id, song, album";
        }

        public SongsAlbumsParser() {
            super("l_songs_album_versions");
        }
    }

    public static class ArtistsSongsParser extends AssociativeParser {
        @Override
        public String getFormat() {
            return "id, artist, song";
        }

        public ArtistsSongsParser() {
            super("l_artists_songs");
        }
    }

    public static class ArtistsAlbumsParser extends AssociativeParser {
        @Override
        public String getFormat() {
            return "id, artist, song";
        }

        public ArtistsAlbumsParser() {
            super("l_artists_album_versions");
        }
    }
}

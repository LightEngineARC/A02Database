package dataHandling;

/**
 * Specifies format for artists table and utilizes parent class to parse song data from dirty_data/artists
 * and insert into database
 * <p>
 *
 * @author Alec Mills
 */
public abstract class AssociativeParser extends Parser {
    private final String format = getFormat();

    AssociativeParser(String path) {
        super(path);
    }

    /**
     * see parent class
     */
    public void parseData() {
        super.parseData(format);
    }

    @Override
    protected Tuple[] getLineData(String[] columns) {
        return new Tuple[]{
                new Tuple(columns[0], false), //primary key
                new Tuple(columns[2], false), //foreign key
                new Tuple(columns[3], false) //foreign key
        };
    }

    /*
     * below are specific parsers for the associative tables we need
     */
    public static class SongsAlbumsParser extends AssociativeParser {
        @Override
        public String getFormat() {
            return "id, song, album_version";
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
            return "id, artist, album_version";
        }

        public ArtistsAlbumsParser() {
            super("l_artists_album_versions");
        }
    }
}

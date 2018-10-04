package dataHandling;

public class SQLFactory {
    public static void fillDB(Boolean databaseFilled) {
        if (!databaseFilled) {
            Parser[] parsers = new Parser[]{
                    new ArtistsParser(), new SongsParser(), new AlbumsParser(), new AlbumVersionsParser(),
                    new AssociativeParser.ArtistsAlbumsParser(),
                    new AssociativeParser.ArtistsSongsParser(),
                    new AssociativeParser.SongsAlbumsParser(),
            };

            for (Parser parser : parsers) {
                parser.parseData(parser.getFormat());
            }
        }
    }
}

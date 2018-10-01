package dataHandling;

public class SQLFactory {
    public static void main(String[] args) {
        Parser[] parsers = new Parser[]{
                new ArtistsParser(), new SongsParser(), new AlbumsParser(), new AlbumVersionsParser(),
//                new AssociativeParser.ArtistsAlbumsParser(),
//                new AssociativeParser.ArtistsSongsParser(),
//                new AssociativeParser.SongsAlbumsParser(),
        };

        for (Parser parser : parsers) {
            parser.parseData(parser.getFormat());
        }
    }
}

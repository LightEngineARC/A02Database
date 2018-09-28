package dataHandling;

/**
 * Specifies format for album table and utilizies parent class to parse song data from data_wanted/album
 * FIXME needs getLineData() to be finished in order to function, currently will throw exceptions
 * <p>
 * @author Alec Mills
 */
public class AlbumParser extends Parser {
    private String format = "ALBUM_NAME, GENRE, RELEASE_YEAR, ARTIST_ID";

    public AlbumParser() {
        super("songs");
    }

    protected void parseData() {
        super.parseData(format);
    }

    /**
     * defines what data and what format to take from data soure
     * @param columns
     * @return
     */
    @Override
    protected String[] getLineData(String[] columns) {
        //FIXME
//        String[] result = new String[] {
//                columns[2],
//        };
//        return result;
        return null;
    }

    public static void main(String[] args) {
        new AlbumParser().parseData();
    }
}

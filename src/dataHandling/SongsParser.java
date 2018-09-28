package dataHandling;

/**
 * Specifies format for songs table and utilizes parent class to parse song data from data_wanted/songs
 * FIXME needs getLineData() to be finished in order to function, currently will throw exceptions
 * <p>
 *
 * @author Alec Mills
 */
public class SongsParser extends Parser {
    private final String format = "TITLE, LENGTH, ARTIST_ID, ALBUM_ID";

    public SongsParser() {
        super("album");
    }

    protected void parseData() {
        super.parseData(format);
    }

    /**
     * defines what data and what format to take from data source
     *
     * @param columns raw data
     * @return desired data
     */
    @Override
    protected String[] getLineData(String[] columns) {
        //FIXME
//        String[] result = new String[] {
//                columns[2], columns[4]
//        };
//        return result;
        return null;
    }

    public static void main(String[] args) {
        new SongsParser().parseData();
    }
}

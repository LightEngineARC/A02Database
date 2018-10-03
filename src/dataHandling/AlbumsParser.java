package dataHandling;

/**
 * Specifies format for albums table and utilizes parent class to parse song data from data_wanted/albums
 * FIXME needs getLineData() to be finished in order to function, currently will throw exceptions
 * <p>
 *
 * @author Alec Mills
 */
public class AlbumsParser extends Parser {

    private AlbumsParser() {
        super("songs");
    }

    private void parseData() {
        String format = "ALBUM_NAME, GENRE, RELEASE_YEAR, ARTIST_ID";
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
//                columns[2],
//        };
//        return result;
        return null;
    }

    public static void main(String[] args) {
        new AlbumsParser().parseData();
    }
}
